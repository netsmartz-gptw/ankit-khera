package com.g1app.engine.controller;

import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.models.*;
import com.g1app.engine.repositories.FamilyMasterRepository;
import com.g1app.engine.repositories.FamilyMembersRepository;
import com.g1app.engine.repositories.UserProfileRepository;
import com.g1app.engine.repositories.UserRepository;
import com.g1app.engine.security.AuthScope;
import com.g1app.engine.security.JwtTokenProvider;
import com.g1app.engine.services.UUIDService;
import java.sql.Timestamp;
import com.g1app.engine.userdirectory.response.ResponseSocialUser;
import com.g1app.engine.utils.CommonConstant;
import com.g1app.engine.utils.UrlConstant;
import com.g1app.engine.utils.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * Created By. Mayank S.
 *
 */
@RestController
@RequestMapping(UrlConstant.SOCIAL)
public class SocialLoginController {
    @Autowired
    UserRepository repository;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    FamilyMasterRepository familyMasterRepository;
    @Autowired
    FamilyMembersRepository familyMembersRepository;

    @Autowired
    UUIDService uuidService;

    @PostMapping(UrlConstant.FACEBOOK_TOKEN)
    public ResponseEntity<ResponseSocialUser> facebookLogin(@RequestHeader("Authorization") String token) throws ParseException {
        ResponseSocialUser response = new ResponseSocialUser();

        if(token == null || token.isEmpty()){
            response.message = ResponseStatusMessages.EMPTY_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        RestTemplate rest = new RestTemplate();
        String jsonResponse = rest.getForObject(CommonConstant.FACEBOOK_GRAPH_API_URL +
                CommonConstant.FACEBOOK_URL_FIELDS +  token, String.class);
        if(jsonResponse != null) {
            JSONObject jsonResult = new JSONObject(jsonResponse);


            User user = new User();
            UUID newUUID = uuidService.getUUID();
            String userName = jsonResult.getString("name");
            String[] parts = userName.split(" ", 2);
            user.setFirstName(parts[0]);
            user.setLastName(parts[1]);
            user.setEmailID(jsonResult.getString("email"));
            String email = jsonResult.getString("email");
            if(email != null) {
                User checkUser = repository.getFacebookUserDetails(email);
                if (checkUser != null) {
                    UUID socialUserId = checkUser.getCustomerID();
                    response.firstName = checkUser.getFirstName();
                    response.lastName = checkUser.getLastName();
                    response.email = checkUser.getEmailID();
                    response.customerID = String.valueOf(checkUser.getCustomerID());

                    UserProfile up = userProfileRepository.findByCustomerID(checkUser.getCustomerID());
                    response.city = up.getCityName();
                    response.gender = up.getGender();

                    response.token = tokenProvider.createToken(checkUser.getCustomerID().toString(), AuthScope.USER);
                    response.message = ResponseStatusMessages.SUCCESS.getValue();
                    response.statusCode = ResponseStatusCode.SUCCESS.getValue();

                    UserProfile checkUserProfile = userProfileRepository.findByCustomerID(socialUserId);
                    if (checkUserProfile == null) {
                        response.message = "Error";
                        response.statusCode = -7;
                    }
                    response.isUserDetailUpdated = !ObjectUtils.isEmpty(checkUser.getFirstName()) && !ObjectUtils.isEmpty(checkUser.getLastName())
                            && !ObjectUtils.isEmpty(checkUserProfile.getGender()) && !ObjectUtils.isEmpty(checkUserProfile.getDob())
                            && !ObjectUtils.isEmpty(checkUserProfile.getCityName());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }

            user.setfTokn(token);
            user.setCustomerID(newUUID);

            UserProfile userProfile = new UserProfile();
            userProfile.setCustomerID(newUUID);
            userProfile.setProfileID(uuidService.getUUID());
            String gender = jsonResult.getString("gender");
            String firstLetter = gender.substring(0, 1);
            String remainingLetters = gender.substring(1);

            String genderNew = firstLetter.toUpperCase() + remainingLetters.toLowerCase();
            userProfile.setGender(genderNew);
            String fbDOB = jsonResult.getString("birthday");

            long epochTime = getEpochTimeFromDate(fbDOB);
            userProfile.setDob(epochTime);

            FamilyMaster familyMaster = new FamilyMaster();
            familyMaster.setFamilyID(uuidService.getUUID());
            familyMaster.setFamilyNumberPrefix(Utils.YYMMPrefix());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            familyMaster.setCreatedAt(timestamp);
            familyMaster.setPrimaryUser(newUUID);
            familyMaster.setCreatedBy(UrlConstant.SELF);
            long postfix = familyMasterRepository.getFamilyCurrentPostfixNumber(Utils.YYMMPrefix());
            familyMaster.setFamilyNumberPostfix(postfix+1);

            FamilyMember familyMember = new FamilyMember();
            familyMember.setReferenceID(uuidService.getUUID());
            familyMember.setFamilyID(familyMaster.getFamilyID());
            familyMember.setCustomerID(newUUID);
            familyMember.setParentUserID(newUUID);
            familyMember.setRelationship(UrlConstant.SELF);
            familyMember.setRelationVerified(true);

            repository.save(user);
            userProfileRepository.save(userProfile);
            familyMasterRepository.save(familyMaster);
            familyMembersRepository.save(familyMember);

            response.message = ResponseStatusMessages.NEW_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.SUCCESS.getValue();
            response.token = tokenProvider.createToken(user.getCustomerID().toString(), AuthScope.USER);
            response.customerID = String.valueOf(newUUID);
            response.firstName = user.getFirstName();
            response.lastName = user.getFirstName();
            response.gender = userProfile.getGender();
            response.dob = userProfile.getDob();
            response.email = user.getEmailID();
            response.city = userProfile.getCityName();

            response.isUserDetailUpdated = !ObjectUtils.isEmpty(user.getFirstName()) && !ObjectUtils.isEmpty(user.getLastName())
                    && !ObjectUtils.isEmpty(userProfile.getGender()) && !ObjectUtils.isEmpty(userProfile.getDob())
                    && !ObjectUtils.isEmpty(userProfile.getCityName());

        }else{
            response.message = ResponseStatusMessages.NEW_TOKEN.getValue();
            response.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PostMapping(UrlConstant.GOOGLE_TOKEN)
    public ResponseEntity<ResponseSocialUser> googleLogin(@RequestHeader("Authorization") String token){
        ResponseSocialUser responseSocialUser = new ResponseSocialUser();

        if(token == null || token.isEmpty()){
            responseSocialUser.message = ResponseStatusMessages.EMPTY_TOKEN.getValue();
            responseSocialUser.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        String url = CommonConstant.GOOGLE_LOGIN_API_URL;
        ResponseEntity<String> response = rest.exchange(url, HttpMethod.GET, request, String.class);
        String json = response.getBody();

        if(json != null) {
            JSONObject jsonResult = new JSONObject(json);

            User user = new User();
            UUID newUUID = uuidService.getUUID();
            String email = jsonResult.getString("email");
            if(email != null) {
                User checkUser = repository.getGoogleUserDetails(email);
                if (checkUser != null) {
                    UUID socialUserId = checkUser.getCustomerID();
                    responseSocialUser.firstName = checkUser.getFirstName();
                    responseSocialUser.lastName = checkUser.getLastName();
                    responseSocialUser.email = checkUser.getEmailID();
                    responseSocialUser.customerID = String.valueOf(checkUser.getCustomerID());

                    UserProfile up = userProfileRepository.findByCustomerID(checkUser.getCustomerID());
                    responseSocialUser.city = up.getCityName();
                    responseSocialUser.gender = up.getGender();

                    responseSocialUser.token = tokenProvider.createToken(checkUser.getCustomerID().toString(), AuthScope.USER);
                    responseSocialUser.message = ResponseStatusMessages.SUCCESS.getValue();
                    responseSocialUser.statusCode = ResponseStatusCode.SUCCESS.getValue();

                    UserProfile checkUserProfile = userProfileRepository.findByCustomerID(socialUserId);
                    if (checkUserProfile == null) {
                        responseSocialUser.message = "Error";
                    }
                    responseSocialUser.isUserDetailUpdated = !ObjectUtils.isEmpty(checkUser.getFirstName()) && !ObjectUtils.isEmpty(checkUser.getLastName())
                            && !ObjectUtils.isEmpty(checkUserProfile.getGender()) && !ObjectUtils.isEmpty(checkUserProfile.getDob())
                            && !ObjectUtils.isEmpty(checkUserProfile.getCityName());
                    return new ResponseEntity<>(responseSocialUser, HttpStatus.OK);
                }
            }


            String userName = jsonResult.getString("name");
            String parts[] = userName.split(" ", 2);
            user.setFirstName(parts[0]);
            user.setLastName(parts[1]);
            user.setEmailID(jsonResult.getString("email"));
            user.setgToken(token);
            user.setCustomerID(newUUID);

            UserProfile userProfile = new UserProfile();
            userProfile.setCustomerID(newUUID);
            userProfile.setProfileID(uuidService.getUUID());
            String gender = "";
            userProfile.setSalutation("");

            FamilyMaster familyMaster = new FamilyMaster();
            familyMaster.setFamilyID(uuidService.getUUID());
            familyMaster.setFamilyNumberPrefix(Utils.YYMMPrefix());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            familyMaster.setCreatedAt(timestamp);
            familyMaster.setPrimaryUser(newUUID);
            familyMaster.setCreatedBy(UrlConstant.SELF);
            long postfix = familyMasterRepository.getFamilyCurrentPostfixNumber(Utils.YYMMPrefix());
            familyMaster.setFamilyNumberPostfix(postfix+1);

            FamilyMember familyMember = new FamilyMember();
            familyMember.setReferenceID(uuidService.getUUID());
            familyMember.setFamilyID(familyMaster.getFamilyID());
            familyMember.setCustomerID(newUUID);
            familyMember.setParentUserID(newUUID);
            familyMember.setRelationship(UrlConstant.SELF);
            familyMember.setRelationVerified(true);

            repository.save(user);
            userProfileRepository.save(userProfile);
            familyMasterRepository.save(familyMaster);
            familyMembersRepository.save(familyMember);

            responseSocialUser.message = ResponseStatusMessages.NEW_TOKEN.getValue();
            responseSocialUser.statusCode = ResponseStatusCode.SUCCESS.getValue();
            responseSocialUser.token = tokenProvider.createToken(user.getCustomerID().toString(), AuthScope.USER);
            responseSocialUser.customerID = String.valueOf(newUUID);
            responseSocialUser.firstName = user.getFirstName();
            responseSocialUser.lastName = user.getFirstName();
            responseSocialUser.gender = userProfile.getGender();
            responseSocialUser.dob = userProfile.getDob();
            responseSocialUser.email = user.getEmailID();
            responseSocialUser.city = userProfile.getCityName();
            User uDetails = repository.findByCustomerID(user.getCustomerID());
            UserProfile uProfile = userProfileRepository.findByCustomerID(user.getCustomerID());
            responseSocialUser.isUserDetailUpdated = !ObjectUtils.isEmpty(uDetails.getFirstName()) && !ObjectUtils.isEmpty(uDetails.getLastName())
                    && !ObjectUtils.isEmpty(uProfile.getGender()) && !ObjectUtils.isEmpty(uProfile.getDob())
                    && !ObjectUtils.isEmpty(uProfile.getCityName());
        }else{

            responseSocialUser.message = ResponseStatusMessages.NEW_TOKEN.getValue();
            responseSocialUser.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
            return new ResponseEntity<>(responseSocialUser,HttpStatus.OK);
        }
        return new ResponseEntity<>(responseSocialUser
                ,HttpStatus.OK);
    }


    /**
     * This method is to convert Date into Epoch Timing(Timestamp)
     * @param fbDOB
     * @return epochTime
     * @throws ParseException
     */
    private long getEpochTimeFromDate(String fbDOB) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = formatter.parse(fbDOB);
        long epochTime = date.getTime();
        return epochTime;
    }
}

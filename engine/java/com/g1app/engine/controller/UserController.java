package com.g1app.engine.controller;

import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.models.*;
import com.g1app.engine.repositories.*;
import com.g1app.engine.security.AuthScope;
import com.g1app.engine.security.JwtTokenProvider;
import com.g1app.engine.services.OtpService;
import com.g1app.engine.services.UUIDService;
import com.g1app.engine.userdirectory.request.RequestCity;
import com.g1app.engine.userdirectory.request.RequestCreateUser;
import com.g1app.engine.userdirectory.request.RequestUserDetail;
import com.g1app.engine.userdirectory.request.RequestVerifyOtp;
import com.g1app.engine.userdirectory.response.*;
import com.g1app.engine.utils.CommonConstant;
import com.g1app.engine.utils.UrlConstant;
import com.g1app.engine.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping(UrlConstant.USER)
public class UserController {

    @Autowired UserRepository repository;
    @Autowired UUIDService uuidService;
    @Autowired JwtTokenProvider tokenProvider;
    @Autowired UserDevicesRepository userDevicesRepository;
    @Autowired OtpService otpService;
    @Autowired UserProfileRepository userProfileRepository;
    @Autowired FamilyMasterRepository familyMasterRepository;
    @Autowired FamilyMembersRepository familyMembersRepository;
    @Autowired RelationMasterRepository relationMasterRepository;

    @PostMapping(UrlConstant.USER_CREATE)
    public ResponseEntity<ResponseCreateUser> createUser(@RequestBody RequestCreateUser request) throws IOException {
        ResponseCreateUser responseCreateUser = new ResponseCreateUser();
        List<User> existingUsers = repository.findFirst1ByMobileNumber(request.mobileNumber);

        if(existingUsers.isEmpty()){
            User newUser = new User();
            newUser.setCustomerID(uuidService.getUUID());
            newUser.setMobileNumber(request.mobileNumber);
            //int otp = 123456;
            int otp = otpService.generateOTP(newUser.getCustomerID().toString());
            sendOtpOnMobileNumber(request, otp);

            newUser.setOtp(otp);
            newUser.setOtpExpiry(System.currentTimeMillis() + (1000 * 60 * OtpService.OTP_EXPIRY)); //2 minutes
            responseCreateUser.token = tokenProvider.createToken(newUser.getCustomerID().toString(), AuthScope.VERIFY_OTP);
            repository.save(newUser);
            responseCreateUser.statusCode = ResponseStatusCode.SUCCESS.getValue();
            responseCreateUser.message = ResponseStatusMessages.OTP_SENT_FOR_REGISTRATION.getValue();
        }else{
            User existingUser = existingUsers.get(0);
           // int otp = 123456;
            int otp = otpService.generateOTP(existingUser.getCustomerID().toString());
            sendOtpOnMobileNumber(request, otp);
            existingUser.setOtp(otp);
            existingUser.setOtpExpiry(System.currentTimeMillis() + (1000 * 60 * OtpService.OTP_EXPIRY)); //2 minutes
            responseCreateUser.token = tokenProvider.createToken(existingUser.getCustomerID().toString(), AuthScope.VERIFY_OTP);
            repository.save(existingUser);
            responseCreateUser.statusCode = ResponseStatusCode.SUCCESS.getValue();
            responseCreateUser.message = ResponseStatusMessages.OTP_SENT_FOR_REGISTRATION.getValue();

        }
        return new ResponseEntity<>(responseCreateUser,HttpStatus.OK);
    }


    @PostMapping(UrlConstant.OTP_VERIFY)
    public ResponseEntity<ResponseLogin> verifyOtp(@RequestBody RequestVerifyOtp body, @RequestHeader("Authorization") String token){
        if(token == null || token.isEmpty()){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        String bearer = tokenProvider.resolveToken(token);
        ResponseLogin responseLogin = new ResponseLogin();

        if(tokenProvider.validateToken(bearer, AuthScope.VERIFY_OTP)) {
            String customerId = tokenProvider.getUsername(bearer);//get user UUID
            User user = repository.findByCustomerID(uuidService.getUUID(customerId));
            UserProfile userProfile = userProfileRepository.findByCustomerID(uuidService.getUUID(customerId));

            if(user != null){
                if(user.getOtpExpiry() < System.currentTimeMillis()){
                    responseLogin.message = ResponseStatusMessages.OTP_EXPIRED.getValue();
                    responseLogin.statusCode = ResponseStatusCode.OTP_EXPIRED.getValue();
                }else if(body.otp != user.getOtp()){
                    responseLogin.message = ResponseStatusMessages.INVALID_OTP.getValue();
                    responseLogin.statusCode = ResponseStatusCode.INVALID_OTP.getValue();
                }else if(user.isUserRegistered()){
                    responseLogin.message = ResponseStatusMessages.TOKEN_EXPIRED.getValue();
                    responseLogin.statusCode = ResponseStatusCode.SUCCESS.getValue();
                    responseLogin.token = tokenProvider.createToken(user.getCustomerID().toString(), AuthScope.USER);

                    responseLogin.isUserDetailUpdated = !ObjectUtils.isEmpty(user.getFirstName()) && !ObjectUtils.isEmpty(user.getLastName())
                            && !ObjectUtils.isEmpty(userProfile.getGender()) && !ObjectUtils.isEmpty(userProfile.getDob())
                            && !ObjectUtils.isEmpty(userProfile.getCityName());

                    responseLogin.CustomerID = customerId;

                }
                else{

                    UserDevice device = new UserDevice();
                    device.setDeviceId(body.deviceId);
                    device.setPushToken(body.pushToken);
                    device.setDeviceType(body.deviceType);
                    device.setId(uuidService.getUUID());
                    device.setCustomerID(user.getCustomerID());
                    user.setUserRegistered(true);
                    //int otp = otpService.generateOTP(user.getCustomerID().toString());
                    //sendOtpOnMobileNumber(request, otp);
                    user.setOtp(123456);

                    FamilyMaster familyMaster = new FamilyMaster();
                    familyMaster.setFamilyID(uuidService.getUUID());
                    familyMaster.setFamilyNumberPrefix(Utils.YYMMPrefix());
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    familyMaster.setCreatedAt(timestamp);
                    familyMaster.setPrimaryUser(UUID.fromString(customerId));
                    familyMaster.setCreatedBy(UrlConstant.SELF);
                    long postfix = familyMasterRepository.getFamilyCurrentPostfixNumber(Utils.YYMMPrefix());
                    familyMaster.setFamilyNumberPostfix(postfix+1);

                    FamilyMember familyMember = new FamilyMember();
                    familyMember.setReferenceID(uuidService.getUUID());
                    familyMember.setFamilyID(familyMaster.getFamilyID());
                    familyMember.setCustomerID(UUID.fromString(customerId));
                    familyMember.setParentUserID(UUID.fromString(customerId));
                    familyMember.setRelationship(UrlConstant.SELF);
                    familyMember.setRelationVerified(true);

                    userDevicesRepository.save(device);
                    repository.save(user);
                    familyMasterRepository.save(familyMaster);
                    familyMembersRepository.save(familyMember);

                    responseLogin.isUserDetailUpdated = !ObjectUtils.isEmpty(user.getFirstName()) && !ObjectUtils.isEmpty(user.getLastName())
                            && !ObjectUtils.isEmpty(userProfile.getGender()) && !ObjectUtils.isEmpty(userProfile.getDob())
                            && !ObjectUtils.isEmpty(userProfile.getCityName());

                    responseLogin.message = ResponseStatusMessages.TOKEN_EXPIRED.getValue();
                    responseLogin.statusCode = ResponseStatusCode.SUCCESS.getValue();
                    responseLogin.token = tokenProvider.createToken(user.getCustomerID().toString(), AuthScope.USER);
                    responseLogin.CustomerID = customerId;

                }

            }else{
                responseLogin.message = ResponseStatusMessages.TOKEN_EXPIRED.getValue();
                responseLogin.statusCode = ResponseStatusCode.TOKEN_EXPIRED.getValue();
            }
        }else{
            responseLogin.message = ResponseStatusMessages.TOKEN_EXPIRED.getValue();
            responseLogin.statusCode = ResponseStatusCode.TOKEN_EXPIRED.getValue();
        }

        return new ResponseEntity<>(responseLogin, HttpStatus.OK);

    }

    /*
     * This API is for login with password
     * with token authentication
     * @Param : password
     *
     *  */

    @PostMapping(UrlConstant.LOGIN_WITH_PASSWORD)
    public ResponseEntity<ResponseCreateUser> loginWithPassword(@RequestBody RequestCreateUser body, @RequestHeader("Authorization") String token){
        ResponseCreateUser responseCreateUser = new ResponseCreateUser();
        if(token == null || token.isEmpty()){
            responseCreateUser.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            responseCreateUser.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        String bearer = tokenProvider.resolveToken(token);
        if(tokenProvider.validateToken(bearer, AuthScope.VERIFY_OTP)) {
            String customerId = tokenProvider.getUsername(bearer);//get user UUID
            User user = repository.findByCustomerID(uuidService.getUUID(customerId));
            UserProfile userProfile = userProfileRepository.findByCustomerID(uuidService.getUUID(customerId));

            if(user!= null){
                responseCreateUser.isUserDetailUpdated = isUserDetailUpdated(user, userProfile);

                if(body.password.equals(user.getPassword())){
                    responseCreateUser.message = ResponseStatusMessages.VALID_USER.getValue();
                    responseCreateUser.statusCode = ResponseStatusCode.SUCCESS.getValue();
                    responseCreateUser.token = tokenProvider.createToken(user.getCustomerID().toString(), AuthScope.USER);
                    responseCreateUser.CustomerID = customerId;
                }else {
                    return getResponseEntity(responseCreateUser);
                }
            }else{
                responseCreateUser.message = ResponseStatusMessages.SIGN_UP.getValue();
                responseCreateUser.statusCode = ResponseStatusCode.WRONG_PASSWORD.getValue();
                responseCreateUser.token = "";
                return new ResponseEntity<>(responseCreateUser, HttpStatus.OK);
            }
        }else{
            return getResponseEntity(responseCreateUser);
        }

        return new ResponseEntity<> ( responseCreateUser, HttpStatus.OK);
    }

    /**
     * This API is for updating user Password
     *  with token authentication
     * @Param : password
     * **/

    @PostMapping(UrlConstant.UPDATE_PASSWORD)
    public ResponseEntity<ResponseCreateUser> updatePassword(@RequestBody RequestCreateUser requestBody, @RequestHeader("Authorization") String token) {
        ResponseCreateUser responseCreateUser = new ResponseCreateUser();
        if (token == null || token.isEmpty()) {
            responseCreateUser.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            responseCreateUser.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String bearer = tokenProvider.resolveToken(token);
        //|| tokenProvider.validateToken(bearer, AuthScope.USER)
        if (tokenProvider.validateToken(bearer, AuthScope.VERIFY_OTP) || tokenProvider.validateToken(bearer, AuthScope.USER)) {
            String customerId = tokenProvider.getUsername(bearer);//get user UUID
            User user = repository.findByCustomerID(uuidService.getUUID(customerId));
            UserProfile userProfile = userProfileRepository.findByCustomerID(UUID.fromString(customerId));

            responseCreateUser.isUserDetailUpdated = !ObjectUtils.isEmpty(user.getFirstName()) && !ObjectUtils.isEmpty(user.getLastName())
                    && !ObjectUtils.isEmpty(user.getPassword())
                    && !ObjectUtils.isEmpty(userProfile.getGender()) && !ObjectUtils.isEmpty(userProfile.getDob())
                    && !ObjectUtils.isEmpty(userProfile.getCityName());

                if(requestBody.password == null || requestBody.password.isEmpty()){
                    responseCreateUser.message = ResponseStatusMessages.INVALID_PASSWORD.getValue();
                    responseCreateUser.statusCode = ResponseStatusCode.WRONG_PASSWORD.getValue();
                    responseCreateUser.CustomerID = "";
                    return new ResponseEntity<>(responseCreateUser, HttpStatus.BAD_REQUEST);
                }else {
                    user.setPassword(requestBody.password);
                    repository.save(user);
                    responseCreateUser.message = ResponseStatusMessages.NEW_PASSWORD_UPDATED.getValue();
                    responseCreateUser.statusCode = ResponseStatusCode.SUCCESS.getValue();
                    responseCreateUser.token = tokenProvider.createToken(user.getCustomerID().toString(), AuthScope.USER);
                    responseCreateUser.CustomerID = customerId;
                    return new ResponseEntity<>(responseCreateUser, HttpStatus.OK);
                }
            }
        return new ResponseEntity<>(responseCreateUser,HttpStatus.NOT_FOUND);
    }

    /*
     *
     * This API is for updating user details in user & profile table.
     *
     * **/
    @PostMapping(UrlConstant.UPDATE_USER_DETAILS)
    public ResponseEntity<ResponseCreateUser> updateUserDetails(@RequestBody RequestUserDetail requestBody,
                                                                @RequestHeader("Authorization") String token){
        ResponseCreateUser responseCreateUser = new ResponseCreateUser();

        if (token == null || token.isEmpty()) {
            responseCreateUser.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            responseCreateUser.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(ObjectUtils.isEmpty(requestBody.firstName)  || ObjectUtils.isEmpty(requestBody.lastName)||
                ObjectUtils.isEmpty(requestBody.gender) || ObjectUtils.isEmpty( requestBody.dob) ||
                ObjectUtils.isEmpty(requestBody.city)){
            responseCreateUser.message = ResponseStatusMessages.INCOMPLETE_DATA.getValue();
            responseCreateUser.statusCode = ResponseStatusCode.INCOMPLETE_DATA.getValue();
            return new ResponseEntity<>(responseCreateUser,HttpStatus.BAD_REQUEST);
        }
        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            String customerId = tokenProvider.getUsername(bearer);//get user UUID
            User user = repository.findByCustomerID(uuidService.getUUID(customerId));
            UserProfile userProfile = userProfileRepository.findByCustomerID(uuidService.getUUID(customerId));

            if(userProfile == null){
                userProfile = new UserProfile();
                userProfile.setCustomerID(UUID.fromString(customerId));
                userProfile.setProfileID(uuidService.getUUID());
            }
                user.setFirstName(requestBody.firstName);
                user.setLastName(requestBody.lastName);
                user.setEmailID(requestBody.email);
                user.setPassword(requestBody.password);
                repository.save(user);

                userProfile.setGender(requestBody.gender);
                userProfile.setDob(requestBody.dob);
                if(requestBody.gender.equalsIgnoreCase("male")){
                    userProfile.setSalutation("Mr.");
                }else{
                    userProfile.setSalutation("Ms.");
                }
                userProfile.setCityName(requestBody.city);

                userProfileRepository.save(userProfile);
                responseCreateUser.token = tokenProvider.createToken(userProfile.getCustomerID().toString(), AuthScope.USER);
                responseCreateUser.message = ResponseStatusMessages.DETAILS_UPDATED.getValue();
                responseCreateUser.statusCode = ResponseStatusCode.SUCCESS.getValue();
                responseCreateUser.CustomerID = customerId;
                responseCreateUser.isUserDetailUpdated = true;
                return new ResponseEntity<>(responseCreateUser,HttpStatus.OK);

        }
        return new ResponseEntity<>(responseCreateUser, HttpStatus.BAD_REQUEST);
    }

    @PostMapping (UrlConstant.FORGOT_PASSWORD)
    public ResponseEntity<ResponseCreateUser> forgotPassword(@RequestBody RequestCreateUser request) throws IOException {

        ResponseCreateUser responseCreateUser = new ResponseCreateUser();
        List<User> existingUsers = repository.findFirst1ByMobileNumber(request.mobileNumber);
        if (existingUsers.isEmpty()) {
          responseCreateUser.message = ResponseStatusMessages.MOBILE_NOT_FOUND.getValue();
          responseCreateUser.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
          responseCreateUser.isUserDetailUpdated = false;
          responseCreateUser.token="";
          responseCreateUser.CustomerID = "";

        }else{
            User user = repository.findBymobileNumber(request.mobileNumber);
            User existingUser = existingUsers.get(0);
            if((user.getFirstName() == null || user.getLastName() == null)){
                responseCreateUser.message = ResponseStatusMessages.MOBILE_NOT_FOUND.getValue();
                responseCreateUser.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
                return new ResponseEntity<>(responseCreateUser,HttpStatus.OK);
            }
            String customerID = String.valueOf(user.getCustomerID());
            UserProfile userProfile = userProfileRepository.findByCustomerID(UUID.fromString(customerID));

            responseCreateUser.isUserDetailUpdated = !ObjectUtils.isEmpty(user.getFirstName()) && !ObjectUtils.isEmpty(user.getLastName()) &&
                    !ObjectUtils.isEmpty(user.getPassword()) &&
                    !ObjectUtils.isEmpty(userProfile.getGender()) && !ObjectUtils.isEmpty(userProfile.getDob()) &&
                    !ObjectUtils.isEmpty(userProfile.getCityName());

            //user.setOtp(123456);
            int otp = otpService.generateOTP(existingUser.getCustomerID().toString());
            sendOtpOnMobileNumber(request, otp);
            user.setOtp(otp);
            user.setOtpExpiry(System.currentTimeMillis() + (OtpService.OTP_EXPIRY_MILLIS)); //2 minutes
            repository.save(user);
            responseCreateUser.message = ResponseStatusMessages.OTP_SENT_FOR_REGISTRATION.getValue();
            responseCreateUser.statusCode = ResponseStatusCode.SUCCESS.getValue();
            responseCreateUser.CustomerID = customerID;
            responseCreateUser.token = tokenProvider.createToken(user.getCustomerID().toString(), AuthScope.VERIFY_OTP);

        }
        return new ResponseEntity<>(responseCreateUser
                , HttpStatus.OK);
    }


    @PostMapping(UrlConstant.GET_RELATION)
    public ResponseEntity<ResponseRelations> getRelation(){
        ResponseRelations responseRelations = new ResponseRelations();
        List<RelationMaster> relation= relationMasterRepository.findAll();

            for(RelationMaster rm : relation){
                ResponseRelation responseRelation = new ResponseRelation();
                responseRelation.relation = rm.getRelation();
                responseRelations.relation.add(responseRelation);
            }
        return new ResponseEntity<>(responseRelations,HttpStatus.OK);
    }


    @PostMapping(UrlConstant.GET_CITY)
    public ResponseEntity<ResponseCityList> getCity(@RequestBody RequestCity requestBody) {
        ResponseCityList responseCityList = new ResponseCityList();
        double locationLat = requestBody.lat;
        double locationLon = requestBody.lon;
        if (requestBody.cityName.isEmpty() && locationLat == 0 && locationLon == 0) {
            responseCityList.message = ResponseStatusMessages.WRONG_INPUT.getValue();
            responseCityList.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
            return new ResponseEntity<>(responseCityList, HttpStatus.OK);
        }

        if (locationLat != 0 && locationLon != 0) {

            RestTemplate rest = new RestTemplate();
            String jsonResponse = rest.getForObject("https://maps.googleapis.com/maps/api/geocode/json?latlng="
            +locationLat + "," +locationLon + "&key=" +
                    CommonConstant.GOOGLE_PLACES_API_KEY,String.class);

            if (jsonResponse != null) {
                JSONObject jsonObj = new JSONObject(jsonResponse);
                JSONObject plus_code = jsonObj.getJSONObject("plus_code");
                String cityValue = plus_code.getString("compound_code");
                String finalCityName = remove(cityValue.substring(cityValue.indexOf(" ")).trim(), "India");
                ResponseCityInfo responseCityInfo = new ResponseCityInfo();
                responseCityInfo.cityName = finalCityName;
                responseCityList.city.add(responseCityInfo);

                responseCityList.message = ResponseStatusMessages.SUCCESS.getValue();
                responseCityList.statusCode = ResponseStatusCode.SUCCESS.getValue();
                return new ResponseEntity<>(responseCityList, HttpStatus.OK);
            }
        }

        String firstLetter = requestBody.cityName.substring(0, 1);
        String remainingLetters = requestBody.cityName.substring(1);
        String cityName = firstLetter.toUpperCase() + remainingLetters.toLowerCase();

            RestTemplate rest = new RestTemplate();
            String jsonResponse = rest.getForObject("https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" +
                    requestBody.cityName + "&components=country:in&types=(cities)&language=en_in&key="
                    + CommonConstant.GOOGLE_PLACES_API_KEY, String.class);

            if (jsonResponse != null) {
                JSONObject jsonObj = new JSONObject(jsonResponse);
                JSONArray jsonArray = jsonObj.getJSONArray("predictions");
                for (int i = 0; i < jsonArray.length(); i++) {
                    String getCityValue = jsonArray.getJSONObject(i).getString("description");
                    ResponseCityInfo responseCityInfo = new ResponseCityInfo();
                    String str = getCityValue;
                    String new_str = remove(str, "India");
                    responseCityInfo.cityName = new_str;
                    responseCityList.city.add(responseCityInfo);
                }
                responseCityList.message = ResponseStatusMessages.SUCCESS.getValue();
                responseCityList.statusCode = ResponseStatusCode.SUCCESS.getValue();
            }

        return new ResponseEntity<>(responseCityList, HttpStatus.OK);
    }

    private ResponseEntity<ResponseCreateUser> getResponseEntity (ResponseCreateUser responseCreateUser){
        responseCreateUser.message = ResponseStatusMessages.INCORRECT_PASSWORD.getValue();
        responseCreateUser.statusCode = ResponseStatusCode.WRONG_PASSWORD.getValue();
        responseCreateUser.token = "";
        return new ResponseEntity<>(responseCreateUser, HttpStatus.OK);
    }

    private boolean isUserDetailUpdated(User user, UserProfile userProfile) {
        return !ObjectUtils.isEmpty(user.getFirstName()) && !ObjectUtils.isEmpty(user.getLastName())
                && !ObjectUtils.isEmpty(userProfile.getGender()) && !ObjectUtils.isEmpty(userProfile.getDob())
                && !ObjectUtils.isEmpty(userProfile.getCityName());
    }

    private String remove(String str, String word)
    {
        String[] msg = str.split(" ");
        String new_str = "";
        for (String words : msg) {
            if (!words.equals(word)) {
                new_str += words + " ";
            }
        }
        return new_str.substring(0, new_str.length() -2);
    }

    private void sendOtpOnMobileNumber(RequestCreateUser request, int otp) throws IOException {
        HttpHeaders headers = new HttpHeaders();

        try {
            HttpEntity<String> req = new HttpEntity<>(headers);
            String url = "https://japi.instaalerts.zone/httpapi/QueryStringReceiver?ver=1.0&key=EJ2IW0WZxbN3ksBYQuCMsw==&encrpt=0&dest="
                    + request.mobileNumber +
                    "&send=HNDSTN&text=Your%20One%20Time%20Password(OTP)%20is%20" + otp + "%0aRegards%0aHindustan+Wellness";
            URL url1 = new URL(url);
            URLConnection conn = url1.openConnection();
            conn.connect();
            //get response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String sResult = "";
            while ((line = rd.readLine()) != null) {
                // Process line...
                sResult = sResult + line + " ";
            }
            rd.close();
        }catch (Exception ex){
            ex.getMessage();
        }
    }
}
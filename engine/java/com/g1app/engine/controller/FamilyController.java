package com.g1app.engine.controller;

import com.g1app.engine.base.ResponseStatusCode;
import com.g1app.engine.base.ResponseStatusMessages;
import com.g1app.engine.models.*;
import com.g1app.engine.repositories.*;
import com.g1app.engine.security.AuthScope;
import com.g1app.engine.security.JwtTokenProvider;
import com.g1app.engine.services.OtpService;
import com.g1app.engine.services.UUIDService;
import com.g1app.engine.userdirectory.request.*;
import com.g1app.engine.userdirectory.response.*;
import com.g1app.engine.utils.UrlConstant;
import com.g1app.engine.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

import java.util.List;
import java.util.UUID;

/**
 * Created By. Mayank S.
 */
@RestController
@RequestMapping(UrlConstant.FAMILY)
public class FamilyController {

    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    UserRepository repository;
    @Autowired
    UUIDService uuidService;
    @Autowired
    UserProfileRepository userProfileRepository;
    @Autowired
    FamilyMasterRepository familyMasterRepository;
    @Autowired
    FamilyMembersRepository familyMembersRepository;

    @Autowired
    OtpService otpService;

    enum MemberRelation {
        DEPENDENT_ABOVE_18_OWN_NUMBER,
        DEPENDENT_BELOW_18_PARENT_NUMBER,
        DEPENDENT_BELOW_18_OWN_NUMBER,
        OTHERS_WITH_OWN_NUMBER,
        INVALID_RELATION
    }

    @PostMapping(UrlConstant.GET_USER_FAMILY_LIST)
    public ResponseEntity<ResponseFamilyListing> getFamily(@RequestHeader("Authorization") String token) {

        ResponseFamilyListing responseFamilyListing = new ResponseFamilyListing();

        if (token == null || token.isEmpty()) {
            responseFamilyListing.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            responseFamilyListing.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }

        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            String parentCustomerId = tokenProvider.getUsername(bearer);//get user UUID
            FamilyMaster familyMasterModels = familyMasterRepository.findByPrimaryUser(UUID.fromString(parentCustomerId));
            UUID familyId = familyMasterModels.getFamilyID();  //get familyId

           // List<FamilyMembers> listFamilyMembers = familyMembersRepository.findByFamilyID(familyId);
            List<FamilyMember> listFamilyMembers = familyMembersRepository.findByFamilyIDAndIsRelationVerified(familyId,true);
            if (listFamilyMembers.isEmpty()) {
                responseFamilyListing.message = ResponseStatusMessages.FAMILY_NOT_FOUND.getValue();
                responseFamilyListing.statusCode = ResponseStatusCode.FAMILY_NOT_FOUND.getValue();
            }
            for (FamilyMember fm : listFamilyMembers) {
                UUID customerId = fm.getCustomerID();
                User member = repository.findByCustomerID(customerId);
                ResponseFamilyMemberInfo info = new ResponseFamilyMemberInfo();
                info.customerID = member.getCustomerID();
                info.firstName = member.getFirstName();
                info.lastName = member.getLastName();
                info.profilePicture = member.getProfileImage();
                info.email = member.getEmailID();
                if(member.getMobileNumber() == null){
                    info.mobile = member.getAlternateMobile();
                }else {
                    info.mobile = member.getMobileNumber();
                }
                UserProfile userProfile = userProfileRepository.findByCustomerID(customerId);
                info.dob = userProfile.getDob();
                info.gender = userProfile.getGender();
                info.location = userProfile.getCityName();

                FamilyMember fmm = familyMembersRepository.findByCustomerIDAndParentUserID(customerId, UUID.fromString(parentCustomerId));
                info.relation = fmm.getRelationship();
                responseFamilyListing.members.add(info);

            }

            responseFamilyListing.message = ResponseStatusMessages.SUCCESS.getValue();
            responseFamilyListing.statusCode = ResponseStatusCode.SUCCESS.getValue();
        }

        return new ResponseEntity<>(responseFamilyListing, HttpStatus.OK);
    }

    @PostMapping(UrlConstant.GET_USER_FAMILY_MEMBER)
    public ResponseEntity<ResponseFamilyMember> getFamilyDetails(@RequestBody RequestUserID request, @RequestHeader("Authorization") String token) {
        ResponseFamilyMember responseFamilyMember = new ResponseFamilyMember();
        ResponseCreateUser responseCreateUser = new ResponseCreateUser();

        ResponseEntity<ResponseFamilyMember> BAD_REQUEST = checkToken(token, responseCreateUser);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            String parentCustomerId = tokenProvider.getUsername(bearer);
            User userDetails = repository.findByCustomerID(request.customerID);
            String childMobileNo = userDetails.getAlternateMobile();
            UserProfile userProfile = userProfileRepository.findByCustomerID(request.customerID);

            if ((userDetails == null) || (request.customerID == null)) {
                responseCreateUser.message = ResponseStatusMessages.UUID_NULL.getValue();
                responseCreateUser.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
                return new ResponseEntity<>(responseFamilyMember, HttpStatus.BAD_REQUEST);
            } else {
                FamilyMember familyMember = familyMembersRepository.findByCustomerIDAndParentUserID(request.customerID, UUID.fromString(parentCustomerId));
                return familyMemberData(responseFamilyMember, userDetails, userProfile, familyMember);
            }
        }
        return new ResponseEntity<>(responseFamilyMember, HttpStatus.OK);
    }

//    @PostMapping(UrlConstant.UPDATE_USER_FAMILY_MEMBER)
//    public ResponseEntity<ResponseUpdateFamily> updateUserDetails(@RequestBody RequestUpdateFamily requestBody,
//                                                                  @RequestHeader("Authorization") String token) {
//        ResponseUpdateFamily responseUpdateFamily = new ResponseUpdateFamily();
//        if (token == null || token.isEmpty()) {
//            responseUpdateFamily.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
//            responseUpdateFamily.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
//        }
//        String bearer = tokenProvider.resolveToken(token);
//
//        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
//            String parentCustomerId = tokenProvider.getUsername(bearer);
//            UUID customerId = requestBody.customerID;
//            User parentUser = repository.findByCustomerID(UUID.fromString(parentCustomerId));
//            String parentMobileNo = parentUser.getMobileNumber();
//
//            User user = repository.findByCustomerID(customerId);
//            UserProfile userProfile = userProfileRepository.findByCustomerID(customerId);
//            FamilyMembers familyMembers = familyMembersRepository.findByCustomerIDAndParentUserID(customerId, UUID.fromString(parentCustomerId));
//            String relation = requestBody.relation;
//            boolean isUserAboveEighteen = FamilyImpl.isAgeAboveEighteen(requestBody.dob);
//
//            // (relation.equals("Child") || relation.equals("Brother") || relation.equals("Sister"))  &&
//            if(isUserAboveEighteen && parentMobileNo.equals(requestBody.mobileNumber)){
//                responseUpdateFamily.message = ResponseStatusMessages.MOBILE_NUMBER_SAME.getValue();
//                responseUpdateFamily.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
//                return new ResponseEntity<>(responseUpdateFamily, HttpStatus.OK);
//            }
//            // if((relation.equals("Father") || relation.equals("Mother") || relation.equals("Spouse") || relation.equals("Self"))
//            if((relation.equals("Father") || relation.equals("Mother") || relation.equals("Spouse"))
//                    && !isUserAboveEighteen){
//                responseUpdateFamily.message = ResponseStatusMessages.PARENT_AGE_SHOULD_ABOVE_EIGHTEEN.getValue();
//                responseUpdateFamily.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
//                return new ResponseEntity<>(responseUpdateFamily, HttpStatus.OK);
//            }
//
//            if (user != null) {
//                if (customerId != null) {
//
//                    if(requestBody.mobileNumber.equals(requestBody.oldMobileNumber)){
//                        responseUpdateFamily.isMobileNumberChanged = false;
//                    }else{
//                        responseUpdateFamily.isMobileNumberChanged= true;
//                        responseUpdateFamily.token = tokenProvider.createToken(user.getCustomerID().toString(), AuthScope.VERIFY_OTP);
//                        user.setOtp(123456);
//                        user.setOtpExpiry(System.currentTimeMillis() + (1000 * 60 * OtpService.OTP_EXPIRY));
//                    }
//
//                    user.setFirstName(requestBody.firstName);
//                    user.setLastName(requestBody.lastName);
//                    user.setEmailID(requestBody.email);
//                    userProfile.setGender(requestBody.gender);
//                    userProfile.setDob(requestBody.dob);
//                    userProfile.setCityName(requestBody.cityName);
//
//                    familyMembers.setRelationship(requestBody.relation);
//
//                    if (requestBody.relation.equals("Child")) {
//                        user.setAlternateMobile(requestBody.mobileNumber);
//                    } else {
//                        user.setMobileNumber(requestBody.mobileNumber);
//                    }
//                    repository.save(user);
//                    userProfileRepository.save(userProfile);
//                    familyMembersRepository.save(familyMembers);
//
//
//                   // responseUpdateFamily.isMobileNumberChanged = false;
//                    responseUpdateFamily.message = ResponseStatusMessages.DETAILS_UPDATED.getValue();
//                    responseUpdateFamily.statusCode = ResponseStatusCode.SUCCESS.getValue();
//                    return new ResponseEntity<>(responseUpdateFamily, HttpStatus.OK);
//                }
//            }
//        } else {
//            responseUpdateFamily.isMobileNumberChanged = false;
//            responseUpdateFamily.message = ResponseStatusMessages.USER_EXISTS_WITH_MOBILE.getValue();
//            responseUpdateFamily.statusCode = ResponseStatusCode.SUCCESS.getValue();
//        }
//        return new ResponseEntity<>(responseUpdateFamily, HttpStatus.OK);
//    }

    @PostMapping(UrlConstant.REMOVE_FAMILY)
    public ResponseEntity<ResponseCreateUser> removeFamilyMembers(@RequestBody RequestRemoveFamilyInfo requestBody,
                                                                  @RequestHeader("Authorization") String token) {
        ResponseCreateUser responseCreateUser = new ResponseCreateUser();
        if (token == null || token.isEmpty()) {
            responseCreateUser.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            responseCreateUser.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }
        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            String parentCustomerID = tokenProvider.getUsername(bearer);//get user UUID
            UUID customerID = requestBody.customerID;
            FamilyMember familyMember =
                    familyMembersRepository.findByCustomerIDAndParentUserID(customerID, UUID.fromString(parentCustomerID));
            if (familyMember == null) {
                responseCreateUser.message = ResponseStatusMessages.FAMILY_NOT_FOUND.getValue();
                responseCreateUser.statusCode = ResponseStatusCode.FAMILY_NOT_FOUND.getValue();
                return new ResponseEntity<>(responseCreateUser, HttpStatus.NOT_FOUND);
            } else {
                familyMembersRepository.delete(familyMember);
                responseCreateUser.message = ResponseStatusMessages.MEMBER_REMOVED.getValue();
                responseCreateUser.statusCode = ResponseStatusCode.SUCCESS.getValue();
            }
        }
        return new ResponseEntity<>(responseCreateUser, HttpStatus.OK);
    }

    User addUser(String firstName, String lastName, String email, String mobileNumber, boolean isDependent){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailID(email);
        user.setCustomerID(uuidService.getUUID());

        if(isDependent){
            user.setAlternateMobile(mobileNumber);
        }else{
            user.setMobileNumber(mobileNumber);
        }

        repository.save(user);

        return user;
    }

    UserProfile createUserProfile(User user, long dob, String city, String gender){

        UserProfile userProfile = new UserProfile();
        userProfile.setProfileID(uuidService.getUUID());
        userProfile.setCustomerID(user.getCustomerID());
        userProfile.setDob(dob);
        userProfile.setCityName(city);
        userProfile.setGender(gender);

        userProfileRepository.save(userProfile);

        return userProfile;
    }

    User findUsersWithMobile(String mobileNumber, String firstName, String lastName, long dob, String gender, boolean isDependent){
        List<User> users;

        if(isDependent){
            users = repository.findAllByAlternateMobile(mobileNumber);
            for(User u : users){
                UserProfile profile = userProfileRepository.findByCustomerID(u.getCustomerID());
                if(u.getFirstName().equalsIgnoreCase(firstName) && u.getLastName().equalsIgnoreCase(lastName)
                        && profile.getGender().equalsIgnoreCase(gender) && profile.getDob() == dob){
                    return u;
                }
            }

            return null;

        }else{
            users = repository.findAllByMobileNumber(mobileNumber);
        }

        if(users == null || users.size() == 0){
            return null;
        }

        return users.get(0);
    }

    public ResponseDetails addDependentBelow18ParentNumber(RequestAddFamily requestBody, String parentCustomerId, String familyId){

        ResponseDetails responseDetails = new ResponseDetails();

        User user = findUsersWithMobile(requestBody.mobileNumber, requestBody.firstName, requestBody.lastName, requestBody.dob, requestBody.gender, true);

        if(user == null){
            user = addUser(requestBody.firstName, requestBody.lastName, requestBody.email, requestBody.mobileNumber, true);
            createUserProfile(user, requestBody.dob, requestBody.city, requestBody.gender );
        }

        UUID memberId = addUserToFamily(parentCustomerId, user, requestBody.relation);

        responseDetails.customerID = String.valueOf(user.getCustomerID());
        responseDetails.parentID = parentCustomerId;
        responseDetails.familyID = String.valueOf(familyId);
        responseDetails.relation = requestBody.relation;
        responseDetails.familyMemberID = memberId.toString();
        responseDetails.token = tokenProvider.createToken(parentCustomerId, AuthScope.VERIFY_OTP);

        setAndSendOtp(parentCustomerId);

        responseDetails.message = ResponseStatusMessages.FAMILY_MEMBER_ADDED.getValue();
        responseDetails.statusCode = ResponseStatusCode.SUCCESS.getValue();

        return responseDetails;

    }

    public ResponseDetails addDependentOwnNumber(RequestAddFamily requestBody, String parentCustomerId, String familyId, MemberRelation relation){

        ResponseDetails responseDetails = new ResponseDetails();

        boolean isDependent = false;

        if(relation == MemberRelation.DEPENDENT_BELOW_18_PARENT_NUMBER){
            isDependent = true;
        }

        User user = findUsersWithMobile(requestBody.mobileNumber, requestBody.firstName, requestBody.lastName, requestBody.dob, requestBody.gender, isDependent);

        if(user == null){
            user = addUser(requestBody.firstName, requestBody.lastName, requestBody.email, requestBody.mobileNumber, isDependent);
            createUserProfile(user, requestBody.dob, requestBody.city, requestBody.gender );
        }

        UUID memberId = addUserToFamily(parentCustomerId, user, requestBody.relation);

        responseDetails.customerID = String.valueOf(user.getCustomerID());
        responseDetails.parentID = parentCustomerId;
        responseDetails.familyID = String.valueOf(familyId);
        responseDetails.relation = requestBody.relation;
        responseDetails.familyMemberID = memberId.toString();
        responseDetails.token = tokenProvider.createToken(parentCustomerId, AuthScope.VERIFY_OTP);

        setAndSendOtp(user);

        responseDetails.message = ResponseStatusMessages.FAMILY_MEMBER_ADDED.getValue();
        responseDetails.statusCode = ResponseStatusCode.SUCCESS.getValue();

        return responseDetails;

    }

    void setAndSendOtp(User user){
        user.setOtp(otpService.generateOTPAndSendToMobile(user.getMobileNumber()));
        user.setOtpExpiry(System.currentTimeMillis() + (OtpService.OTP_EXPIRY_MILLIS));
        repository.save(user);

    }

    void setAndSendOtp(String customerId){

        User existingUser = repository.findByCustomerID(UUID.fromString(customerId));
        setAndSendOtp(existingUser);

    }

    UUID addUserToFamily(String primaryUserId, User user, String relationship){

        UUID primaryUserUUID = UUID.fromString(primaryUserId);

        FamilyMaster familyMaster = familyMasterRepository.findByPrimaryUser(primaryUserUUID);

        FamilyMember member = new FamilyMember();
        member.setFamilyID(familyMaster.getFamilyID());
        member.setCustomerID(user.getCustomerID());
        member.setRelationship(UrlConstant.SELF);
        member.setReferenceID(uuidService.getUUID());
        member.setParentUserID(primaryUserUUID);
        member.setRelationship(relationship);
        member.setRelationVerified(false);

        familyMasterRepository.save(familyMaster);
        familyMembersRepository.save(member);

        return member.getReferenceID();

    }

    MemberRelation getMemberRelation(RequestAddFamily requestBody, User parentUser){
        MemberRelation relation = MemberRelation.OTHERS_WITH_OWN_NUMBER;

        String relationToAdd = requestBody.relation;

        if(relationToAdd.equalsIgnoreCase("brother") || relationToAdd.equalsIgnoreCase("sister")
        || relationToAdd.equalsIgnoreCase("child")){

            boolean isAbove18 = FamilyImpl.isAgeAboveEighteen(requestBody.dob);
            boolean isParentNumber = parentUser.getMobileNumber().equalsIgnoreCase(requestBody.mobileNumber);

            if(isAbove18 && isParentNumber){
                return MemberRelation.INVALID_RELATION;
            }else if(isAbove18){
                return MemberRelation.DEPENDENT_ABOVE_18_OWN_NUMBER;
            }else if(!isParentNumber){
                return MemberRelation.DEPENDENT_BELOW_18_OWN_NUMBER;
            }else {
                return MemberRelation.DEPENDENT_BELOW_18_PARENT_NUMBER;
            }

        }else if(requestBody.mobileNumber.equalsIgnoreCase(parentUser.getMobileNumber()) ){
            return MemberRelation.INVALID_RELATION;
        }else if(!FamilyImpl.isAgeAboveEighteen(requestBody.dob)){
            return MemberRelation.INVALID_RELATION;
        }

        return relation;
    }

    boolean isChildUserExistsInFamily(RequestAddFamily requestBody, MemberRelation relation, UUID familyId){

        List<FamilyMember> familyMembers = familyMembersRepository.findByFamilyIDAndIsRelationVerified(familyId, true);

        if(relation == MemberRelation.DEPENDENT_BELOW_18_PARENT_NUMBER){
                for(FamilyMember fm : familyMembers){
                User u = repository.findByCustomerID(fm.getCustomerID());
                UserProfile profile = userProfileRepository.findByCustomerID(u.getCustomerID());
                if( u.getFirstName().equalsIgnoreCase(requestBody.firstName) &&
                        u.getLastName().equalsIgnoreCase(requestBody.lastName) &&
                        profile.getDob() == requestBody.dob &&
                        profile.getGender().equalsIgnoreCase(requestBody.gender)){
                    return true;
                }
            }
        }else{
            for(FamilyMember fm : familyMembers){
                User u = repository.findByCustomerID(fm.getCustomerID());
                if(u.getMobileNumber().equalsIgnoreCase(requestBody.mobileNumber)){
                    return true;
                }
            }
        }

        return false;
    }

    @PostMapping(UrlConstant.ADD_USER_FAMILY)
    public ResponseEntity<ResponseDetails> addFamily(@RequestBody RequestAddFamily requestBody,
                                                        @RequestHeader("Authorization") String token) {
        ResponseDetails responseDetails = new ResponseDetails();

        if (token == null || token.isEmpty()) {
            responseDetails.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            responseDetails.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
            return new ResponseEntity<>(responseDetails, HttpStatus.OK);
        }

        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {

            String parentCustomerId = tokenProvider.getUsername(bearer);//get user UUID
            User parentUser = repository.findByCustomerID(UUID.fromString(parentCustomerId));
            FamilyMaster familyMaster = familyMasterRepository.findByPrimaryUser(UUID.fromString(parentCustomerId));

            MemberRelation relation = getMemberRelation(requestBody, parentUser);

            if(relation == MemberRelation.INVALID_RELATION){
                responseDetails.message = ResponseStatusMessages.MOBILE_NUMBER_SAME_AS_FAMILY.getValue();
                responseDetails.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
                return new ResponseEntity<>(responseDetails, HttpStatus.OK);
            }

            if(isChildUserExistsInFamily(requestBody, relation, familyMaster.getFamilyID())){
                responseDetails.message = ResponseStatusMessages.USER_EXISTS_WITH_MOBILE.getValue();
                responseDetails.statusCode = ResponseStatusCode.USER_EXISTS_WITH_MOBILE.getValue();
                return new ResponseEntity<>(responseDetails, HttpStatus.OK);
            }

            if(relation == MemberRelation.DEPENDENT_BELOW_18_PARENT_NUMBER){
                responseDetails = addDependentBelow18ParentNumber(requestBody, parentCustomerId, familyMaster.getFamilyID().toString());
                return new ResponseEntity<>(responseDetails, HttpStatus.OK);

            }else if(relation == MemberRelation.DEPENDENT_BELOW_18_OWN_NUMBER
                    || relation == MemberRelation.DEPENDENT_ABOVE_18_OWN_NUMBER
                    || relation == MemberRelation.OTHERS_WITH_OWN_NUMBER){

                responseDetails = addDependentOwnNumber(requestBody, parentCustomerId, familyMaster.getFamilyID().toString(), relation);
                return new ResponseEntity<>(responseDetails, HttpStatus.OK);

            }

        }
        return new ResponseEntity<>(responseDetails,HttpStatus.OK);
    }

    @PostMapping(UrlConstant.ADD_FAMILY_OTP_VERIFY)
    public ResponseEntity<ResponseLogin> addFamilyVerifyOtp(@RequestBody RequestFamilyVerifyOtp body, @RequestHeader("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String bearer = tokenProvider.resolveToken(token);
        ResponseLogin responseLogin = new ResponseLogin();

        if (tokenProvider.validateToken(bearer, AuthScope.VERIFY_OTP)) {
            String customerId = tokenProvider.getUsername(bearer);//get user UUID
            User user = repository.findByCustomerID(uuidService.getUUID(customerId));
            if (user != null) {
                if (user.getOtpExpiry() < System.currentTimeMillis()) {
                    responseLogin.message = ResponseStatusMessages.OTP_EXPIRED.getValue();
                    responseLogin.statusCode = ResponseStatusCode.OTP_EXPIRED.getValue();
                } else if (body.otp != user.getOtp()) {
                    responseLogin.message = ResponseStatusMessages.INVALID_OTP.getValue();
                    responseLogin.statusCode = ResponseStatusCode.INVALID_OTP.getValue();
                }else if(body.type.equalsIgnoreCase("update") && body.type !=null){
                    user.setUserRegistered(true);
                    user.setOtp(123456);
                    repository.save(user);
                    responseLogin.message = ResponseStatusMessages.FAMILY_MEMBER_ADDED.getValue();
                    responseLogin.statusCode = ResponseStatusCode.SUCCESS.getValue();
                    responseLogin.token = tokenProvider.createToken(user.getCustomerID().toString(), AuthScope.USER);
                }
                else {
                    FamilyMember familyMember = familyMembersRepository.findByReferenceID(body.familyMemberID);
                    familyMember.setRelationVerified(true);
                    familyMembersRepository.save(familyMember);
                    responseLogin.message = ResponseStatusMessages.FAMILY_MEMBER_ADDED.getValue();
                    responseLogin.statusCode = ResponseStatusCode.SUCCESS.getValue();
                }
            } else {
                responseLogin.message = ResponseStatusMessages.TOKEN_EXPIRED.getValue();
                responseLogin.statusCode = ResponseStatusCode.TOKEN_EXPIRED.getValue();
            }
        } else {
            responseLogin.message = ResponseStatusMessages.TOKEN_EXPIRED.getValue();
            responseLogin.statusCode = ResponseStatusCode.TOKEN_EXPIRED.getValue();
        }
        return new ResponseEntity<>(responseLogin, HttpStatus.OK);
    }

    private ResponseEntity<ResponseFamilyMember> checkToken(String token, ResponseCreateUser responseCreateUser) {
        if (token == null || token.isEmpty()) {
            responseCreateUser.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            responseCreateUser.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    private ResponseEntity<ResponseFamilyMember> familyMemberData(ResponseFamilyMember responseFamilyMember, User userDetails, UserProfile userProfile, FamilyMember familyMember) {

        responseFamilyMember.customerID = userDetails.getCustomerID();
        responseFamilyMember.firstName = userDetails.getFirstName();
        responseFamilyMember.lastName = userDetails.getLastName();
        responseFamilyMember.email = userDetails.getEmailID();
        responseFamilyMember.profilePicture = userDetails.getProfileImage();
        responseFamilyMember.dob = userProfile.getDob();
        responseFamilyMember.gender = userProfile.getGender();
        responseFamilyMember.cityName = userProfile.getCityName();
        responseFamilyMember.mobileNumber = userDetails.getMobileNumber();

        String relation = familyMember.getRelationship();
        responseFamilyMember.relation = relation;


        if(userDetails != null){
            if(userDetails.getMobileNumber() != null){
                responseFamilyMember.mobileNumber = userDetails.getMobileNumber();
            }else{
                responseFamilyMember.mobileNumber = userDetails.getAlternateMobile();
            }
        }
        responseFamilyMember.message = ResponseStatusMessages.SUCCESS.getValue();
        responseFamilyMember.statusCode =ResponseStatusCode.SUCCESS.getValue();
        return new ResponseEntity<>(responseFamilyMember, HttpStatus.OK);
    }


    /** test start */
    @PostMapping(UrlConstant.UPDATE_USER_FAMILY_MEMBER)
    public ResponseEntity<ResponseUpdateFamily> updateFamily(@RequestBody RequestUpdateFamily body,
                                                             @RequestHeader("Authorization") String token) {
        ResponseUpdateFamily responseUpdateFamily = new ResponseUpdateFamily();
        if (token == null || token.isEmpty()) {
            responseUpdateFamily.message = ResponseStatusMessages.INVALID_TOKEN.getValue();
            responseUpdateFamily.statusCode = ResponseStatusCode.INVALID_TOKEN.getValue();
        }
        String bearer = tokenProvider.resolveToken(token);
        if (tokenProvider.validateToken(bearer, AuthScope.USER)) {
            String parentCustomerId = tokenProvider.getUsername(bearer);
            UUID customerId = body.customerID;

            User parentUser = repository.findByCustomerID(UUID.fromString(parentCustomerId));
            String parentMobileNo = parentUser.getMobileNumber();

            User user = repository.findByCustomerID(customerId);

            UserProfile userProfile = userProfileRepository.findByCustomerID(customerId);
            FamilyMember familyMember = familyMembersRepository.findByCustomerIDAndParentUserID(customerId, UUID.fromString(parentCustomerId));
            String relation = body.relation;
            boolean isUserAboveEighteen = FamilyImpl.isAgeAboveEighteen(body.dob);

            if(!body.mobileNumber.equals(body.oldMobileNumber)){ // check number
               // User newUser =  repository.findBymobileNumber(body.mobileNumber); //check number exists in db
                List<User> newUser =  repository.findAllByMobileNumber(body.mobileNumber); //check number exists in db
                if(newUser != null && newUser.size() >0){ // existing customer
                    responseUpdateFamily.message = "Mobile No. is already exist." +
                            "Please contact to Customer Support";
                    responseUpdateFamily.statusCode = -7;
                    responseUpdateFamily.isMobileNumberChanged = false;
                    return new ResponseEntity<>(responseUpdateFamily,HttpStatus.OK);
                }else{ //new number
                    //User updateUser = repository.findBymobileNumber(body.oldMobileNumber);
                    User updateUser = repository.findByCustomerID(body.customerID);
                    updateUser.setFirstName(body.firstName);
                    updateUser.setLastName(body.lastName);
                    updateUser.setEmailID(body.email);
                    updateUser.setMobileNumber(body.mobileNumber);
                    int otp = 123456;
                    updateUser.setOtp(otp);
                    updateUser.setOtpExpiry(System.currentTimeMillis() + (1000 * 60 * OtpService.OTP_EXPIRY)); //2 minutes

                    UserProfile newUserProfile = userProfileRepository.findByCustomerID(updateUser.getCustomerID());
                    newUserProfile.setGender(body.gender);
                    newUserProfile.setCityName(body.cityName);
                    newUserProfile.setDob(body.dob);

                    FamilyMember newMember = familyMembersRepository.findByCustomerIDAndParentUserID(customerId, UUID.fromString(parentCustomerId));
                    newMember.setRelationship(body.relation);

                    repository.save(updateUser);
                    userProfileRepository.save(newUserProfile);
                    familyMembersRepository.save(newMember);

                    responseUpdateFamily.token = tokenProvider.createToken(updateUser.getCustomerID().toString(), AuthScope.VERIFY_OTP);
                    responseUpdateFamily.statusCode =1;
                    responseUpdateFamily.message = ResponseStatusMessages.OTP_SENT_FOR_REGISTRATION.getValue();
                    responseUpdateFamily.isMobileNumberChanged = true;
                    return new ResponseEntity<>(responseUpdateFamily,HttpStatus.OK);
                }
            }

            if(isUserAboveEighteen && parentMobileNo.equals(body.mobileNumber)){
                responseUpdateFamily.message = ResponseStatusMessages.MOBILE_NUMBER_SAME.getValue();
                responseUpdateFamily.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
                return new ResponseEntity<>(responseUpdateFamily, HttpStatus.OK);
            }
            if((relation.equals("Father") || relation.equals("Mother") || relation.equals("Spouse"))
                    && !isUserAboveEighteen){
                responseUpdateFamily.message = ResponseStatusMessages.PARENT_AGE_SHOULD_ABOVE_EIGHTEEN.getValue();
                responseUpdateFamily.statusCode = ResponseStatusCode.WRONG_INPUT.getValue();
                return new ResponseEntity<>(responseUpdateFamily, HttpStatus.OK);
            }

            if (user != null) {
                if (customerId != null) {
                    user.setFirstName(body.firstName);
                    user.setLastName(body.lastName);
                    user.setEmailID(body.email);
                    userProfile.setGender(body.gender);
                    userProfile.setDob(body.dob);
                    userProfile.setCityName(body.cityName);

                    familyMember.setRelationship(body.relation);

                    if (body.relation.equals("Child")) {
                        user.setAlternateMobile(body.mobileNumber);
                    } else {
                        user.setMobileNumber(body.mobileNumber);
                    }
                    repository.save(user);
                    userProfileRepository.save(userProfile);
                    familyMembersRepository.save(familyMember);

                    responseUpdateFamily.message = ResponseStatusMessages.DETAILS_UPDATED.getValue();
                    responseUpdateFamily.statusCode = ResponseStatusCode.SUCCESS.getValue();
                    return new ResponseEntity<>(responseUpdateFamily, HttpStatus.OK);
                }
            }
        } else {
            responseUpdateFamily.message = ResponseStatusMessages.USER_EXISTS_WITH_MOBILE.getValue();
            responseUpdateFamily.statusCode = ResponseStatusCode.SUCCESS.getValue();
        }
        return new ResponseEntity<>(responseUpdateFamily, HttpStatus.OK);
    }
/** /** test ends */

}

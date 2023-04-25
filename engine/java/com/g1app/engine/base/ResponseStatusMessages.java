package com.g1app.engine.base;

public enum ResponseStatusMessages {

    USER_EXISTS_WITH_MOBILE("User already exists with the given mobile number"),
    OTP_SENT_FOR_REGISTRATION("An OTP has been sent for the verification"),
    OTP_EXPIRED("The OTP has been expired"),
    INVALID_OTP("The OTP entered is not valid"),
    TOKEN_EXPIRED("The token has been expired"),
    INVALID_TOKEN("Token not found"),
    USER_NOT_FOUND("User Not Found"),
    VALID_USER("Valid User"),
    INCOMPLETE_DATA("Please Fill User Profile Details."),
    INCORRECT_PASSWORD("Incorrect Password"),
    NEW_PASSWORD_UPDATED("New Password updated"),
    INVALID_PASSWORD("Invalid Password"),
    SUCCESS("Success"),
    FAMILY_NOT_FOUND("Family not found"),
    UUID_NULL("Customer UUID is null/empty"),
    DETAILS_UPDATED("Details Updated "),
    MOBILE_NOT_FOUND("Mobile number doesn't exist, Please Signup"),
    FAMILY_MEMBER_ADDED("Family Member Added Successfully"),
    NO_PACKAGE_FOUND("No Package found"),
    NO_TEST_FOUND("No Test found"),
    MEMBER_REMOVED("Member Successfully Removed"),
    PARENT_AND_FAMILY_MOBILE_NUMBER("Parent and Family member number can't be same"),
    MOBILE_NUMBER_SAME("Mobile no. can't be same as of Parent."),
    MOBILE_NUMBER_SAME_AS_FAMILY("Mobile no. can't be same as of Family."),
    CHILD_AGE_SHOULD_BELOW_EIGHTEEN("Child Age should below 18"),
    PASS_PLATFORM("Platform is missing"),
    REQUEST_HEADER_IS_MISSING("Header value is missing"),
    APP_DETAILS_UPDATED("App Details Updated"),
    EMPTY_TOKEN("Empty Token"),
    NEW_TOKEN("New Token Created"),
    PARENT_AGE_SHOULD_ABOVE_EIGHTEEN("Age should not be less then 18."),
    AGE_LESS_THEN_EIGHTEEN("You are below 18. Ask your Parent to SignUp"),
    DUPLICATE_MEMBER("Adding Duplicate Members are not allowed."),
    PROFILE_IMAGE_UPLOADED("Profile Image Uploaded."),
    WRONG_INPUT("Wrong Data"),
    PLEASE_TRY_AGAIN("Please try Again"),
    UPDATE_MOBILE_NUMBER("Please Update your Mobile Number"),
    ERROR("Unknown error occurred. Please try again"),
    SIGN_UP("Please SignUp"),
    PASS_TEST_ID("Please Pass Test Id/Name."),
    NOT_FOUND("Not Found"),
    MISSING_PARAMETER("Missing Parameter");

    final String value;

    ResponseStatusMessages(final String newValue){
        value = newValue;
    }

    public String getValue(){
        return value;
    }
    
}

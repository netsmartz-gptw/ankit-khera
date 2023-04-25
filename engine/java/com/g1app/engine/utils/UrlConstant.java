package com.g1app.engine.utils;

import java.security.PublicKey;

public class UrlConstant {

    public static final String NUMERIC_PATTERN = "0123456789";
    public static final String ALPHANUMERIC_PATTERN = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final String SELF = "Self";
    public static final String CHILD = "Child";


    // User related URL
    public static final String USER = "/user";
    public static final String GET_USER_OTP = "/otp/get";
    public static final String USER_CREATE = "/create" ;
    public static final String IS_MOBILE_NUMBER_REGISTER = "/checkmobilenumber";
    public static final String UPDATE_USER_DETAILS = "/updateuserdetail";
    public static final String UPDATE_PASSWORD = "/updatepassword";
    public static final String OTP_VERIFY = "/signup/otp/verify";
    public static final String ADD_FAMILY_OTP_VERIFY = "/add/otp/verify";
    public static final String UPDATE_OTP_VERIFY = "/update/otp/verify";

    public static final String  LOGIN_WITH_PASSWORD= "/loginwithpassword";
    public static final String FORGOT_PASSWORD = "/forgotpassword";
    public static final String GET_CITY ="/getcity";
    public static final String GET_RELATION = "relation";


    // Family related URL
    public static final String FAMILY = "/family";
    public static final String GET_USER_FAMILY_LIST = "/getfamilylist";
    public static final String ADD_USER_FAMILY = "/adduserfamily";
    public static final String GET_USER_FAMILY_MEMBER ="/getfamilydetails";
    public static final String UPDATE_USER_FAMILY_MEMBER = "/updatefamilydetails";
    public static final String REMOVE_FAMILY = "/removefamily";

    //App related URL
    public static final String APP = "/app";
    public static final String UPDATE_APP_INFO = "/updateappinfo";
    public static final String CHECK_APP_UPDATE = "/appupdatecheck";

    //sOCIAL RELATED URL CONSTANT
    public static final String SOCIAL ="/social";
    public static final String FACEBOOK_TOKEN="/facebooktoken";
    public static final String GOOGLE_TOKEN="/googletoken";

    // Files related Url's
    public static final String FILE ="/file";
    public static final String FILES ="/files";

    public static final String UPLOAD_FILES ="/uploadFile";
    public static final String UPLOAD_MULTIPLE_FILES ="/uploadMultipleFiles";

    public static final String DOWNLOAD_FILES ="/downloadFile/";

    public static final String SMS_API_URL = "https://japi.instaalerts.zone/httpapi/QueryStringReceiver?ver=1.0&key=EJ2IW0WZxbN3ksBYQuCMsw==&encrpt=0&dest=9582103447&send=HNDSTN&text=Your%20One%20Time%20Password(OTP)%20is%20437922%0aRegards%0aHindustan+Wellness";
    public static final String SMS_API_KEY = "EJ2IW0WZxbN3ksBYQuCMsw=";

    //Packages Listing controller
    public static final String PACKAGE = "/package";
    public static final String COMMON_SEARCH = "/commonSearch";
    public static final String GET_PACKAGE_DETAILS = "/getPackageDetails";
    public static final String GET_TEST_DETAILS = "/getTestDetails";
    public static final String PROFILES_LIST = "/getProfileList";
    public static final String TESTS_LIST = "/getTestList";
    public static final String PACKAGES_LIST = "/getPackagesList";
    public static final String PACKAGES_FOR_TEST = "/getPackagesForTest";
    public static final String LIST_PACKAGES_TEST_PROFILES= "/list-packages-test-profile";
    public static final String FILTER_SEARCH = "/filterSearch";

    // Booking Related Url's
    public static final String BOOKING ="/booking";
    public static final String SAVE_ADDRESS = "/addUserAddress";
    public static final String GET_USER_ADDRESS = "/getUserAddress";
    public static final String DELETE_ADDRESS = "/deleteAddress";
    public static final String GET_TIME_SLOTS="/getTimeSlots";
    public static final String GET_FILTERS ="/filters";
    public static final String GET_BOOKING_SLOTS = "/getSlots";
    public static final String ORDER_BOOKING = "/orderBooking";
    public static final String GET_ORDER_HISTORY = "/getOrderHistory";
    public static final String GET_ONGOING_ORDER = "/getOngoingOrder";
    public static final String GET_ORDER_DETAILS = "/getOrderDetails";

    //Payment related uri
    public static final String PAYMENT="/payment";

    // Order Feedback
    public static final String FEEDBACK = "/feedback";




}

package com.g1app.engine.thirdparty.redis;

public enum RedisKeyConstants {

    APP_USER_CALL_QUEUE("APP_USER_CALL_QUEUE"),
    CALL_INFO("CALL_INFO"),
    ONLINE_DOCTORS("ONLINE_DOCTORS"),
    CRM_LOGIN_OTP("CRM_LOGIN_OTP"),
    DOCTOR_CALL("DOCTOR_CALL"),
    APP_USER_ID("APP_USER_ID"),
    CALL_TO_USER_ID("CALL_TO_USER_ID"),
    INIT_AT("INIT_AT"),
    STARTED_AT("STARTED_AT"),
    USER_JOINED_AT("USER_JOINED_AT"),
    DOCTOR_JOINED_AT("DOCTOR_JOINED_AT"),
    DOCTOR_ID("DOCTOR_ID"),
    ROOM_ID("ROOM_ID"),
    ROOM_TOKEN("ROOM_TOKEN"),
    LAST_PING_AT("LAST_PING_AT"),
    ONLINE_DOCTORS_FOR_CALL("ONLINE_DOCTORS_FOR_CALL");


    final String value;

    RedisKeyConstants(final String newValue){
        value = newValue;
    }

    public String getValue(){
        return value;
    }
}

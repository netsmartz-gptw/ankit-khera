package com.g1app.engine.base;

public enum ResponseStatusCode {

    USER_EXISTS_WITH_MOBILE(0),
    SUCCESS(1),
    TOKEN_EXPIRED(-2),
    INVALID_OTP(-3),
    OTP_EXPIRED(-1),
    WRONG_PASSWORD(-4),
    INCOMPLETE_DATA(-5),
    INVALID_TOKEN(-6),
    WRONG_INPUT(-7),
    FAMILY_NOT_FOUND(-8),
    ERROR(-9),
    NOT_FOUND(-10);


    private int value;
    ResponseStatusCode(final int newValue){
        value = newValue;
    }

    public int getValue(){
        return value;
    }

}

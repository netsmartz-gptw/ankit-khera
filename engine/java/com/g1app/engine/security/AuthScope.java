package com.g1app.engine.security;

public enum AuthScope {

    VERIFY_OTP("548796"),
    USER("564875");

    final String value;

    AuthScope(final String newValue){
        value = newValue;
    }

    public String getValue(){
        return value;
    }

}

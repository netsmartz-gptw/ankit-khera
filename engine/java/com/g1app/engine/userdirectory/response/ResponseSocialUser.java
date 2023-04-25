package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

public class ResponseSocialUser extends ResponseBase {
    public String token;
    public boolean isUserDetailUpdated;
    public String customerID;
    public String firstName;
    public String lastName;
    public String gender;
    public long dob;
    public String email;
    public String city;
}

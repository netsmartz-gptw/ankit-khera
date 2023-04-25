package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

public class CommonConfigResponse extends ResponseBase {
    public int otpTimeout;
    public int otpDigitLength;
    public int otpRetryCount;
    public String privacyPolicyUrl;
    public String termsAndConditions;
    public String sessionToken;
    public String marketAppVersion;
    public String updateType;
}

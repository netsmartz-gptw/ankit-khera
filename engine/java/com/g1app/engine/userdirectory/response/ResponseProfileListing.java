package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseProfileListing extends ResponseBase {
    public List<ResponseProfileInfo> profile;
    public ResponseProfileListing(){
        profile = new ArrayList<>();
    }
}

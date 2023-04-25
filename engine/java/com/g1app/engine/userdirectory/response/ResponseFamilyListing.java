package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseFamilyListing extends ResponseBase {

    public List<ResponseFamilyMemberInfo> members;
    public ResponseFamilyListing(){
        members = new ArrayList<>();
    }

}

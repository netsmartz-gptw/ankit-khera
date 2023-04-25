package com.g1app.engine.userdirectory.request;

import com.g1app.engine.userdirectory.response.ResponseFamilyMemberInfo;

import java.util.ArrayList;
import java.util.List;

public class RequestRemoveFamilyListing {

    public List<RequestRemoveFamilyInfo> members;

    public RequestRemoveFamilyListing(){
        members = new ArrayList<>();
    }
}

package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseSearchListing extends ResponseBase{

    public List<ResponseSearchInfo> results;
    public ResponseSearchListing(){results = new ArrayList<>();}
}

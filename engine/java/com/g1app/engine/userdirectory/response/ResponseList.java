package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseList extends ResponseBase {


    public List<ResponseInfo> results;
    public ResponseList(){
        results = new ArrayList<>();
    }
}

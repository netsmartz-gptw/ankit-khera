package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseAllPackagesList extends ResponseBase{
    public List<ResponseAllPackageInfo> result;
    public ResponseAllPackagesList(){
        result = new ArrayList<>();
    }
}

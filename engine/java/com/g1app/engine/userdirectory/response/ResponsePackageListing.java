package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponsePackageListing extends ResponseBase {
    public List<ResponsePackageInfo> packages;
    public ResponsePackageListing(){
        packages = new ArrayList<>();
    }
}

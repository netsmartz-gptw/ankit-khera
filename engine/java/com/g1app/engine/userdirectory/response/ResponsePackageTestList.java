package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponsePackageTestList extends ResponseBase {

    public String packageTitle;
    public String packageDescription;
    public long packageReportTime;
    public String recommendedFor;
    public int packagePrice;
    public int discountType;
    public int packageDiscountValue;
    public int totalTestCount;

    public List<ResponsePackageTestInfo> testList;
    public List<ResponseProfileDetails> profileList;

    public ResponsePackageTestList(){
        testList = new ArrayList<>();
        profileList = new ArrayList<ResponseProfileDetails>();
    }
}

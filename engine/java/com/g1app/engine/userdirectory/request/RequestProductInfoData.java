package com.g1app.engine.userdirectory.request;

import java.util.ArrayList;
import java.util.List;

public class RequestProductInfoData {
    public List<RequestProductDetail> product_details;
    public RequestProductInfoData(){
        product_details = new ArrayList<>();
    }

}

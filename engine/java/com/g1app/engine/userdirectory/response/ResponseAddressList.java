package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseAddressList extends ResponseBase {

    public List<ResponseAddressInfo> addressList;
    public ResponseAddressList(){
        addressList = new ArrayList<>();
    }



}

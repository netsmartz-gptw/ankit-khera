package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseCityList extends ResponseBase {
    public List<ResponseCityInfo> city;
    public ResponseCityList(){
        city = new ArrayList<>();
    }
}

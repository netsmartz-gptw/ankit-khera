package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseTestListing extends ResponseBase {
    public List<ResponseTestInfo> test;

    public ResponseTestListing(){ test = new ArrayList<>();
    }
}

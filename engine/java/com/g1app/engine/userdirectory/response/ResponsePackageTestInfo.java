package com.g1app.engine.userdirectory.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResponsePackageTestInfo {
    public String testDisplayName;
    public UUID testId;

    public List<ResponseTestDetails> testDetails;
    public ResponsePackageTestInfo(){
        testDetails = new ArrayList<>();
    }
}

package com.g1app.engine.userdirectory.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResponseInfo {
    public UUID testId;
    public String testTitle;
    public String testDescription;
    public String testTags;
    public int testType;
    public List<ResponseSearchInfo> list;
    public ResponseInfo(){list = new ArrayList<>();}

}

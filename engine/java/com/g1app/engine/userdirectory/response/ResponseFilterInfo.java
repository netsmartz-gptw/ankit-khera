package com.g1app.engine.userdirectory.response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResponseFilterInfo {

    public String categoryId;
    public String categoryName;
    public String categoryType;

    public List<ResponseFilterSubCategory> subCategory ;
    public ResponseFilterInfo(){ subCategory = new ArrayList<>();}

}

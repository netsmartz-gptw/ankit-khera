package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseOngoingOrderDetails extends ResponseBase {
    public boolean editOrder;
    public boolean cancelOrder;
    public List<ResponseOngoingOrderInfo> orders;
    public ResponseOngoingOrderDetails(){
        orders = new ArrayList<>();
    }
}

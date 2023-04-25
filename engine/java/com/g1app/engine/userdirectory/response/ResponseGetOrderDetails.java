package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.UUID;

public class ResponseGetOrderDetails extends ResponseBase {
    public UUID orderId;
    public float totalAmount;
    public long orderDate;
    public String modeOfPayment;
    public String username;
    public String address1;
    public String address2;
    public int pincode;
    public String landmark;

}

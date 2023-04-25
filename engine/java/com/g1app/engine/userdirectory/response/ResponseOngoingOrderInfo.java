package com.g1app.engine.userdirectory.response;

import java.util.UUID;

public class ResponseOngoingOrderInfo {
    public UUID orderID;
    public String userName;
    public String addressLine1;
    public String addressLine2;
    public String cityName;
    public int pincode;
    public String itemType;
    public String itemName;
    public UUID itemId;

    public long date;
    public String time;
    public float orderAmount;
    public long orderPlacedAt;
    public String paymentStatus;

}

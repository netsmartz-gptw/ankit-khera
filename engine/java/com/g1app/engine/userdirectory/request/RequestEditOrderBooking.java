package com.g1app.engine.userdirectory.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RequestEditOrderBooking{
    public UUID orderId;
    public UUID addressId ;
    public long appointmentDate;
    public String appointmentTimeSlot;
    public String couponCode;
    public boolean payAtPickUp;
    public List<ResponseOrderInfo> items;
    public RequestEditOrderBooking(){
        items = new ArrayList<>();
    }
}

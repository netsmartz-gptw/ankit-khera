package com.g1app.engine.userdirectory.response;

import com.g1app.engine.base.ResponseBase;

import java.util.ArrayList;
import java.util.List;

public class ResponseCoupons extends ResponseBase {
    public List<ResponseCoupon> coupon;
    public ResponseCoupons(){
        coupon = new ArrayList<>();
    }
}

package com.g1app.engine.controller;

import com.g1app.engine.models.CouponMaster;
import com.g1app.engine.repositories.CouponRepository;
import com.g1app.engine.userdirectory.response.ResponseCoupon;
import com.g1app.engine.userdirectory.response.ResponseCoupons;
import com.g1app.engine.userdirectory.response.ResponseRelations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coupon")
public class CouponController {

    @Autowired
    CouponRepository couponRepository;

    @PostMapping("/getAllCoupons")
    public ResponseEntity<ResponseRelations> getRelation(){
        ResponseCoupons response = new ResponseCoupons();
        List<CouponMaster> couponMaster= couponRepository.findAll();

        for(CouponMaster cm : couponMaster){
            ResponseCoupon info = new ResponseCoupon();
            info.couponName = cm.getCouponName();
            response.coupon.add(info);
        }
        return null;
    }



}

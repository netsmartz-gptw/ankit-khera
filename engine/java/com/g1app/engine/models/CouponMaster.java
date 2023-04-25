package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "coupon_master")
public class CouponMaster {

    @Id
    @Column(name = "coupon_id")
    public UUID couponId;

    @Column(name = "coupon_name")
    public String couponName;

    @Column(name = "is_active")
    public boolean isActive;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "coupon_description")
    public String couponDescription;

    public UUID getCouponId() {
        return couponId;
    }

    public void setCouponId(UUID couponId) {
        this.couponId = couponId;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    @Override
    public String toString() {
        return "CouponMaster{" +
                "couponId=" + couponId +
                ", couponName='" + couponName + '\'' +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", couponDescription="+couponDescription+
                '}';
    }
}

package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "order_master")
public class OrderMaster {

    @Id
    @Column(name ="order_id")
    public UUID orderId;

    @Column(name ="order_date_time")
    public long orderDateTime;

    @Column(name ="customer_Id")
    public UUID customerId ;

    @Column(name ="payment_status")
    public String paymentStatus;

    @Column(name ="total_order_amount")
    public float totalOrderAmount;

    @Column(name ="order_discount_amount")
    public float orderDiscountAmount;

    @Column(name ="address_id")
    public UUID addressId;

    @Column(name ="coupon_code")
    public String couponCode;

    @Column(name ="pickup_date_time")
    public long pickupDateTime;

    @Column(name ="booked_by")
    public UUID bookedBy;

    @Column(name ="is_fully_paid")
    public boolean isFullyPaid;

    @Column(name ="amount_received")
    public float amountReceived;

    @Column(name ="primary_order_status")
    public String primaryOrderStatus;

    @Column(name ="refund_amount")
    public float refundAmount;

    @Column(name ="refund_date")
    public long refundDate;

    @Column(name ="is_refunded")
    public boolean isRefunded;

    @Column(name ="is_eligible_for_refund")
    public boolean isEligibleForRefund;

    @Column(name ="eligible_refund_amount")
    public boolean eligibleRefundAmount;

    @Column(name ="status_when_cancelled")
    public String statusWhenCancelled;

    @Column(name = "visible_order_id")
    public String visibleOrderId;

    @Column(name = "booking_reference")
    public UUID bookingReference;

    @Column(name="order_status")
    public int orderStatus;

    @Column(name = "crm_order_id")
    public int crmOrderId;


    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public long getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(long orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public float getTotalOrderAmount() {
        return totalOrderAmount;
    }

    public void setTotalOrderAmount(float totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    public float getOrderDiscountAmount() {
        return orderDiscountAmount;
    }

    public void setOrderDiscountAmount(float orderDiscountAmount) {
        this.orderDiscountAmount = orderDiscountAmount;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public long getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(long pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public UUID getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(UUID bookedBy) {
        this.bookedBy = bookedBy;
    }

    public boolean isFullyPaid() {
        return isFullyPaid;
    }

    public void setFullyPaid(boolean fullyPaid) {
        isFullyPaid = fullyPaid;
    }

    public float getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(float amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getPrimaryOrderStatus() {
        return primaryOrderStatus;
    }

    public void setPrimaryOrderStatus(String primaryOrderStatus) {
        this.primaryOrderStatus = primaryOrderStatus;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public long getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(long refundDate) {
        this.refundDate = refundDate;
    }

    public boolean isRefunded() {
        return isRefunded;
    }

    public void setRefunded(boolean refunded) {
        isRefunded = refunded;
    }

    public boolean isEligibleForRefund() {
        return isEligibleForRefund;
    }

    public void setEligibleForRefund(boolean eligibleForRefund) {
        isEligibleForRefund = eligibleForRefund;
    }

    public boolean isEligibleRefundAmount() {
        return eligibleRefundAmount;
    }

    public void setEligibleRefundAmount(boolean eligibleRefundAmount) {
        this.eligibleRefundAmount = eligibleRefundAmount;
    }

    public String getStatusWhenCancelled() {
        return statusWhenCancelled;
    }

    public void setStatusWhenCancelled(String statusWhenCancelled) {
        this.statusWhenCancelled = statusWhenCancelled;
    }

    public String getVisibleOrderId() {
        return visibleOrderId;
    }

    public void setVisibleOrderId(String visibleOrderId) {
        this.visibleOrderId = visibleOrderId;
    }

    public UUID getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(UUID bookingReference) {
        this.bookingReference = bookingReference;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getCrmOrderId() {
        return crmOrderId;
    }

    public void setCrmOrderId(int crmOrderId) {
        this.crmOrderId = crmOrderId;
    }

    @Override
    public String toString() {
        return "OrderMaster{" +
                "orderId=" + orderId +
                ", orderDateTime=" + orderDateTime +
                ", customerId=" + customerId +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", totalOrderAmount=" + totalOrderAmount +
                ", orderDiscountAmount=" + orderDiscountAmount +
                ", addressId=" + addressId +
                ", couponCode='" + couponCode + '\'' +
                ", pickupDateTime=" + pickupDateTime +
                ", bookedBy=" + bookedBy +
                ", isFullyPaid=" + isFullyPaid +
                ", amountReceived=" + amountReceived +
                ", primaryOrderStatus='" + primaryOrderStatus + '\'' +
                ", refundAmount=" + refundAmount +
                ", refundDate=" + refundDate +
                ", isRefunded=" + isRefunded +
                ", isEligibleForRefund=" + isEligibleForRefund +
                ", eligibleRefundAmount=" + eligibleRefundAmount +
                ", statusWhenCancelled='" + statusWhenCancelled + '\'' +
                ", visibleOrderId='" + visibleOrderId + '\'' +
                ", bookingReference=" + bookingReference +
                ", orderStatus=" + orderStatus +
                ", crmOrderId=" + crmOrderId +
                '}';
    }
}

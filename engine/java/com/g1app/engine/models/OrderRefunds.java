package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "order_refunds")
public class OrderRefunds {

    @Id
    @Column(name = "order_refund_id")
    public UUID orderRefundId;

    @Column(name = "order_id")
    public UUID orderId ;

    @Column(name = "refund_date")
    public long refundDate;

    @Column(name = "refund_amount")
    public float refundAmount ;

    @Column(name = "refund_payment_mode")
    public String refundPaymentMode;

    @Column(name = "refund_transaction_id")
    public String refundTransactionId ;

    @Column(name = "refund_account_number")
    public String refundAccountNumber ;

    @Column(name = "refund_account_ifsc_code")
    public String refundAccountIfscCode ;

    @Column(name = "refund_account_bank_name")
    public String refundAccountBankName ;

    @Column(name = "refund_comments")
    public String refundComments ;

    public UUID getOrderRefundId() {
        return orderRefundId;
    }

    public void setOrderRefundId(UUID orderRefundId) {
        this.orderRefundId = orderRefundId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public long getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(long refundDate) {
        this.refundDate = refundDate;
    }

    public float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundPaymentMode() {
        return refundPaymentMode;
    }

    public void setRefundPaymentMode(String refundPaymentMode) {
        this.refundPaymentMode = refundPaymentMode;
    }

    public String getRefundTransactionId() {
        return refundTransactionId;
    }

    public void setRefundTransactionId(String refundTransactionId) {
        this.refundTransactionId = refundTransactionId;
    }

    public String getRefundAccountNumber() {
        return refundAccountNumber;
    }

    public void setRefundAccountNumber(String refundAccountNumber) {
        this.refundAccountNumber = refundAccountNumber;
    }

    public String getRefundAccountIfscCode() {
        return refundAccountIfscCode;
    }

    public void setRefundAccountIfscCode(String refundAccountIfscCode) {
        this.refundAccountIfscCode = refundAccountIfscCode;
    }

    public String getRefundAccountBankName() {
        return refundAccountBankName;
    }

    public void setRefundAccountBankName(String refundAccountBankName) {
        this.refundAccountBankName = refundAccountBankName;
    }

    public String getRefundComments() {
        return refundComments;
    }

    public void setRefundComments(String refundComments) {
        this.refundComments = refundComments;
    }

    @Override
    public String toString() {
        return "OrderRefunds{" +
                "orderRefundId=" + orderRefundId +
                ", orderId=" + orderId +
                ", refundDate=" + refundDate +
                ", refundAmount=" + refundAmount +
                ", refundPaymentMode='" + refundPaymentMode + '\'' +
                ", refundTransactionId='" + refundTransactionId + '\'' +
                ", refundAccountNumber='" + refundAccountNumber + '\'' +
                ", refundAccountIfscCode='" + refundAccountIfscCode + '\'' +
                ", refundAccountBankName='" + refundAccountBankName + '\'' +
                ", refundComments='" + refundComments + '\'' +
                '}';
    }
}

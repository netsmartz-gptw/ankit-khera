package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "order_payments")
public class OrderPayments {

    @Id
    @Column(name = "order_payment_id")
    public UUID orderPaymentId;

    @Column(name = "order_id")
    public UUID orderId;

    @Column(name = "payment_timestamp")
    public long paymentTimestamp;

    @Column(name = "transaction_id")
    public String transactionId;

    @Column(name = "payment_type")
    public String paymentType;

    public UUID getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(UUID orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public long getPaymentTimestamp() {
        return paymentTimestamp;
    }

    public void setPaymentTimestamp(long paymentTimestamp) {
        this.paymentTimestamp = paymentTimestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "OrderPayments{" +
                "orderPaymentId=" + orderPaymentId +
                ", orderId=" + orderId +
                ", paymentTimestamp=" + paymentTimestamp +
                ", transactionId='" + transactionId + '\'' +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }
}

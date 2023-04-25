package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "order_rating")
public class OrderRating {

    @Id
    @Column(name = "order_id")
    public UUID orderId;

    @Column(name = "rating")
    public float rating;

    @Column(name = "remark")
    public String remark;

    @Column(name = "order_feedback")
    public String orderFeedback;

    @Column(name = "order_feedback_comment")
    public String orderFeedbackComment;

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderFeedback() {
        return orderFeedback;
    }

    public void setOrderFeedback(String orderFeedback) {
        this.orderFeedback = orderFeedback;
    }

    public String getOrderFeedbackComment() {
        return orderFeedbackComment;
    }

    public void setOrderFeedbackComment(String orderFeedbackComment) {
        this.orderFeedbackComment = orderFeedbackComment;
    }

    @Override
    public String toString() {
        return "OrderRating{" +
                "orderId=" + orderId +
                ", rating=" + rating +
                ", remark='" + remark + '\'' +
                ", orderFeedback='" + orderFeedback + '\'' +
                ", orderFeedbackComment='" + orderFeedbackComment + '\'' +
                '}';
    }
}

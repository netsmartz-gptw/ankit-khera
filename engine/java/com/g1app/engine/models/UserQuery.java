package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_query")
public class UserQuery {

    @Id
    @Column(name = "customer_id")
    public UUID customerId;

    @Column(name = "customer_comment")
    public String customerComment;

    @Column(name = "revert_comment")
    public String revertComment;

    @Column(name = "comment_date")
    public  long commentDate;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public String getRevertComment() {
        return revertComment;
    }

    public void setRevertComment(String revertComment) {
        this.revertComment = revertComment;
    }

    public long getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(long commentDate) {
        this.commentDate = commentDate;
    }

    @Override
    public String toString() {
        return "UserQuery{" +
                "customerId=" + customerId +
                ", customerComment='" + customerComment + '\'' +
                ", revertComment='" + revertComment + '\'' +
                ", commentDate=" + commentDate +
                '}';
    }
}

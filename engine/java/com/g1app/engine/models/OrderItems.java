package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;


@Entity
@Table(name = "order_items")
public class OrderItems {

    @Id
    @Column(name = "order_items_id")
    public UUID orderItemsId;

    @Column(name = "order_id")
    public UUID orderId;

    @Column(name = "item_id")
    public UUID itemId;

    @Column(name = "item_type")
    public String itemType;

    @Column(name = "is_combo")
    public boolean isCombo;

    @Column(name = "booked_for_customer_id")
    public UUID bookedForCustomerId;

    @Column(name = "item_amount")
    public float itemAmount;

    @Column(name = "visible_order_id")
    public String visibleOrderId;

    @Column(name = "item_mrp")
    public int itemMrp;

    @Column(name="item_discount")
    public int itemDiscount;

    public String getVisibleOrderId() {
        return visibleOrderId;
    }

    public void setVisibleOrderId(String visibleOrderId) {
        this.visibleOrderId = visibleOrderId;
    }

    public UUID getOrderItemsId() {
        return orderItemsId;
    }

    public void setOrderItemsId(UUID orderItemsId) {
        this.orderItemsId = orderItemsId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public boolean isCombo() {
        return isCombo;
    }

    public void setCombo(boolean combo) {
        isCombo = combo;
    }

    public UUID getBookedForCustomerId() {
        return bookedForCustomerId;
    }

    public void setBookedForCustomerId(UUID bookedForCustomerId) {
        this.bookedForCustomerId = bookedForCustomerId;
    }

    public float getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(float itemAmount) {
        this.itemAmount = itemAmount;
    }

    public int getItemMrp() {
        return itemMrp;
    }

    public void setItemMrp(int itemMrp) {
        this.itemMrp = itemMrp;
    }

    public int getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(int itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "orderItemsId=" + orderItemsId +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", itemType='" + itemType + '\'' +
                ", isCombo=" + isCombo +
                ", bookedForCustomerId=" + bookedForCustomerId +
                ", itemAmount=" + itemAmount +
                ", visibleOrderId='" + visibleOrderId + '\'' +
                ", itemMrp=" + itemMrp +
                ", itemDiscount=" + itemDiscount +
                '}';
    }
}

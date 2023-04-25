package com.g1app.engine.models;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_devices")
public class UserDevice {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "device_type")
    private String deviceType;

    @Column(name = "push_token")
    private String pushToken;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "customer_id")
    private UUID customerID;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public UUID getCustomerID() {
        return customerID;
    }

    public void setCustomerID(UUID customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "UserDevices{" +
                "id=" + id +
                ", deviceType='" + deviceType + '\'' +
                ", pushToken='" + pushToken + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
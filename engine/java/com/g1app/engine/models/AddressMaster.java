package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "address_master")
public class AddressMaster {

    @Id
    @Column(name = "address_id")
    public UUID addressId ;

    @Column(name = "customer_id")
    public UUID customerId ;

    @Column(name = "address_line_1")
    public String addressLine1;

    @Column(name = "address_line_2")
    public String addressLine2;

    @Column(name = "landmark")
    public String landmark;

    @Column(name = "latitude")
    public float latitude;

    @Column(name = "longitude")
    public float longitude ;

    @Column(name = "pincode")
    public int pincode;

    @Column(name = "city")
    public String city;

    @Column(name = "address_type")
    public String addressType;

    @Column(name = "map_static_url")
    public String mapStaticUrl;

    @Column(name = "geo_address")
    public String geoAddress;

    @Column(name = "is_primary_add")
    public boolean isPrimaryAdd ;

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getMapStaticUrl() {
        return mapStaticUrl;
    }

    public void setMapStaticUrl(String mapStaticUrl) {
        this.mapStaticUrl = mapStaticUrl;
    }

    public String getGeoAddress() {
        return geoAddress;
    }

    public void setGeoAddress(String geoAddress) {
        this.geoAddress = geoAddress;
    }

    public boolean isPrimaryAdd() {
        return isPrimaryAdd;
    }

    public void setPrimaryAdd(boolean primaryAdd) {
        isPrimaryAdd = primaryAdd;
    }

    @Override
    public String toString() {
        return "AddressMaster{" +
                "addressId=" + addressId +
                ", customerId=" + customerId +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", landmark='" + landmark + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", pincode=" + pincode +
                ", city='" + city + '\'' +
                ", addressType='" + addressType + '\'' +
                ", mapStaticUrl='" + mapStaticUrl + '\'' +
                ", geoAddress='" + geoAddress + '\'' +
                ", isPrimaryAdd=" + isPrimaryAdd +
                '}';
    }
}

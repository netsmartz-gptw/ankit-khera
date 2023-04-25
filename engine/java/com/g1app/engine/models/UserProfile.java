package com.g1app.engine.models;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @Column(name = "profile_id", nullable = false)
    private UUID profileID;

    @Column(name = "dob")
    private long dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "customer_id")
    private UUID customerID;

    @Column(name = "city_name")
    private String cityName;

    public UUID getProfileID() {
        return profileID;
    }

    public void setProfileID(UUID profileID) {
        this.profileID = profileID;
    }

    public long getDob() {
        return dob;
    }

    public void setDob(long dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public UUID getCustomerID() {
        return customerID;
    }

    public void setCustomerID(UUID customerID) {
        this.customerID = customerID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "profileID=" + profileID +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", salutation='" + salutation + '\'' +
                ", customerID=" + customerID +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}

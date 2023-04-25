package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @Column(name = "customer_id")
    UUID customerID;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "mobile_number", length = 10)
    String mobileNumber;

    @Column(name = "email_id")
    String emailID;

    @Column(name = "password")
    String password;

    @Column(name = "g_token")
    String gToken;

    @Column(name = "f_token")
    String fTokn;

    @Column(name = "otp", length= 6)
    int otp;

    @Column(name = "otp_expiry")
    long otpExpiry;

    @Column(name = "jwt_token")
    String jwtToken;

    @Column(name = "is_user_registered")
    boolean isUserRegistered;

    @Column(name = "alternate_mobile")
    String alternateMobile;

    @Column(name = "alternate_email")
    String alternateEmail;

    @Column(name="profile_image")
    String profileImage;

    public UUID getCustomerID() {
        return customerID;
    }

    public void setCustomerID(UUID customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getgToken() {
        return gToken;
    }

    public void setgToken(String gToken) {
        this.gToken = gToken;
    }

    public String getfTokn() {
        return fTokn;
    }

    public void setfTokn(String fTokn) {
        this.fTokn = fTokn;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    public long getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(long otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public boolean isUserRegistered() {
        return isUserRegistered;
    }

    public void setUserRegistered(boolean userRegistered) {
        isUserRegistered = userRegistered;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "customerID=" + customerID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailID='" + emailID + '\'' +
                ", password='" + password + '\'' +
                ", gToken='" + gToken + '\'' +
                ", fTokn='" + fTokn + '\'' +
                ", otp=" + otp +
                ", otpExpiry=" + otpExpiry +
                ", jwtToken='" + jwtToken + '\'' +
                ", isUserRegistered=" + isUserRegistered +
                ", alternateMobile='" + alternateMobile + '\'' +
                ", alternateEmail='" + alternateEmail + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}

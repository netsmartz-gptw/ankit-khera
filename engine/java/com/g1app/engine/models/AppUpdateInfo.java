package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "app_update_info")
public class AppUpdateInfo {

    @Id
    @Column(name="id")
    UUID ID;

    @Column(name = "date")
    long date;

    @Column(name = "android_market_version")
    String androidMarketVersion;

    @Column(name = "android_update_type")
    String androidUpdateType;

    @Column(name = "ios_market_version")
    String iosMarketVersion;

    @Column(name = "ios_update_type")
    String iosUpdateType;

    @Column(name= "is_active")
    boolean isActive;

    @Column(name="otp_timeout")
    int otpTimeout;

    @Column(name="otp_digit_length")
    int otpDigitLength;

    @Column(name="otp_retry_count")
    int otpRetryCount;

    @Column(name="privacy_policy_url")
    String privacyPolicyUrl;

    @Column(name="terms_and_conditions_url")
    String termsAndConditionsUrl;

    @Column(name="about_us_url")
    String aboutUsUrl;

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAndroidMarketVersion() {
        return androidMarketVersion;
    }

    public void setAndroidMarketVersion(String androidMarketVersion) {
        this.androidMarketVersion = androidMarketVersion;
    }

    public String getAndroidUpdateType() {
        return androidUpdateType;
    }

    public void setAndroidUpdateType(String androidUpdateType) {
        this.androidUpdateType = androidUpdateType;
    }

    public String getIosMarketVersion() {
        return iosMarketVersion;
    }

    public void setIosMarketVersion(String iosMarketVersion) {
        this.iosMarketVersion = iosMarketVersion;
    }

    public String getIosUpdateType() {
        return iosUpdateType;
    }

    public void setIosUpdateType(String iosUpdateType) {
        this.iosUpdateType = iosUpdateType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getOtpTimeout() {
        return otpTimeout;
    }

    public void setOtpTimeout(int otpTimeout) {
        this.otpTimeout = otpTimeout;
    }

    public int getOtpDigitLength() {
        return otpDigitLength;
    }

    public void setOtpDigitLength(int otpDigitLength) {
        this.otpDigitLength = otpDigitLength;
    }

    public int getOtpRetryCount() {
        return otpRetryCount;
    }

    public void setOtpRetryCount(int otpRetryCount) {
        this.otpRetryCount = otpRetryCount;
    }

    public String getPrivacyPolicyUrl() {
        return privacyPolicyUrl;
    }

    public void setPrivacyPolicyUrl(String privacyPolicyUrl) {
        this.privacyPolicyUrl = privacyPolicyUrl;
    }

    public String getTermsAndConditionsUrl() {
        return termsAndConditionsUrl;
    }

    public void setTermsAndConditionsUrl(String termsAndConditionsUrl) {
        this.termsAndConditionsUrl = termsAndConditionsUrl;
    }

    public String getAboutUsUrl() {
        return aboutUsUrl;
    }

    public void setAboutUsUrl(String aboutUsUrl) {
        this.aboutUsUrl = aboutUsUrl;
    }

    @Override
    public String
    toString() {
        return "AppUpdateInfo{" +
                "ID=" + ID +
                ", date=" + date +
                ", androidMarketVersion='" + androidMarketVersion + '\'' +
                ", androidUpdateType='" + androidUpdateType + '\'' +
                ", iosMarketVersion='" + iosMarketVersion + '\'' +
                ", iosUpdateType='" + iosUpdateType + '\'' +
                ", isActive=" + isActive +
                ", otpTimeout=" + otpTimeout +
                ", otpDigitLength=" + otpDigitLength +
                ", otpRetryCount=" + otpRetryCount +
                ", privacyPolicyUrl='" + privacyPolicyUrl + '\'' +
                ", termsAndConditionsUrl='" + termsAndConditionsUrl + '\'' +
                ", aboutUsUrl='" + aboutUsUrl + '\'' +
                '}';
    }
}

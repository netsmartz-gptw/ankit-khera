package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "device_info")
public class DeviceInfo {

    @Id
    @Column(name="id")
    UUID id;

    @Column (name="imei")
    String imei;

    @Column(name = "session_token")
    String sessionToken;

    @Column (name="app_version")
    String appVersion;

    @Column(name="os_version")
    String osVersion;

    @Column (name = "platform")
    String platform;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "id=" + id +
                ", imei='" + imei + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", platform='" + platform + '\'' +
                '}';
    }
}

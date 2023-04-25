package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "city_master")
public class CityMaster {

    @Id
    @Column(name="city_id")
    int cityID;

    @Column(name="city_name")
    String cityName;

    @Column(name="is_active")
    int isActive;

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "CityMaster{" +
                "cityID=" + cityID +
                ", cityName='" + cityName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}


package com.g1app.engine.repositories;

import com.g1app.engine.models.DeviceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfo, UUID> {
    DeviceInfo findByImeiAndOsVersionAndAppVersionAndPlatform(String imei, String osVersion, String appVersion, String platform);
}

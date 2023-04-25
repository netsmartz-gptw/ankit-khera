package com.g1app.engine.repositories;

import com.g1app.engine.models.AppUpdateInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppUpdateInfoRepository extends JpaRepository<AppUpdateInfo , UUID> {

    AppUpdateInfo findByIsActive(boolean value);

}

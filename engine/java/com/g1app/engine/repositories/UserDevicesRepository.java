package com.g1app.engine.repositories;

import com.g1app.engine.models.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDevicesRepository extends JpaRepository<UserDevice, UUID> {
}
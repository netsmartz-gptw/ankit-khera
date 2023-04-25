package com.g1app.engine.repositories;

import com.g1app.engine.models.ProfileTestMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProfileTestMasterRepository extends JpaRepository<ProfileTestMaster, UUID> {
    List<ProfileTestMaster> findByProfileIdAndIsActiveAndIsDeleted(UUID packageId, boolean valActive, boolean valDeleted);

}

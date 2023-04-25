package com.g1app.engine.repositories;

import com.g1app.engine.models.PackageTestMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PackageTestMasterRepository extends JpaRepository<PackageTestMaster, UUID> {
    List<PackageTestMaster> findByPackageIdAndIsActiveAndIsDeleted(UUID pkgId, boolean valActive, boolean valDeleted);

    @Query("select COUNT(*)  from PackageTestMaster as ptm where ptm.packageId = :packageId" +
            " and is_active=true and is_deleted =false")
    int getTestCount(@Param("packageId") UUID packageId);

    @Query("FROM PackageTestMaster as pm where pm.testId = :value")
    List<PackageTestMaster> getPackages(@Param("value") UUID value);


}

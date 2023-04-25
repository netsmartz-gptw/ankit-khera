package com.g1app.engine.repositories;

import com.g1app.engine.models.PackageMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PackageMasterRepository extends JpaRepository<PackageMaster, UUID> {
    List<PackageMaster> findByIsActiveAndIsDeletedOrderBySortOrderDesc(boolean valActive, boolean valDeleted);
    PackageMaster findByPackageIdAndIsActiveAndIsDeleted(UUID packageId,boolean valActive, boolean valDeleted);

    PackageMaster findByPackageId(UUID packageId);

    @Query("FROM PackageMaster as pm WHERE lower(pm.packageTitle) LIKE %?1% OR lower(pm.packageDescription)" +
            " LIKE %?1% OR lower(pm.packageRecommendedFor) LIKE %?1% OR lower(pm.packageTags) LIKE %?1% ")
    List<PackageMaster> getPackages(String search);

}

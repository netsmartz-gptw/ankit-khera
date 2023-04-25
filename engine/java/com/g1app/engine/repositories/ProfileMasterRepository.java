package com.g1app.engine.repositories;

import com.g1app.engine.models.ProfileMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProfileMasterRepository extends JpaRepository<ProfileMaster, UUID> {
    List<ProfileMaster> findByIsActiveAndIsDeletedOrderBySortOrderDesc(boolean valActive, boolean valDeleted);
    ProfileMaster findByProfileId(UUID profileId);

    @Query("FROM ProfileMaster as pm WHERE lower(pm.profileTitle) LIKE %?1% OR lower(pm.profileDescription)" +
            " LIKE %?1% OR lower(pm.profileRecommendedFor) LIKE %?1% OR lower(pm.profileTags) LIKE %?1% ")
    List<ProfileMaster> getProfiles(String search);


}

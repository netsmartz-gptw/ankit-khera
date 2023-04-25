package com.g1app.engine.repositories;

import com.g1app.engine.models.FamilyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface FamilyMasterRepository extends JpaRepository<FamilyMaster, UUID> {
    FamilyMaster findByPrimaryUser(UUID customerID);

    @Query("select COALESCE(MAX(fm.familyNumberPostfix),0) from FamilyMaster as fm where fm.familyNumberPrefix=:prefix")
    int getFamilyCurrentPostfixNumber(@Param("prefix") int prefix);
}

package com.g1app.engine.services;


import com.g1app.engine.models.FamilyMaster;
import com.g1app.engine.models.FamilyMember;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FamilyMasterService extends CRUDService<FamilyMaster> {

    @Query("SELECT MAX(family_number_postfix) AS MaxNumber from family_master where family_number_prefix = :prefix")
   // @Query("select cd from family_members cd where cd.customerID = :customerID")
    List<FamilyMember> findByCustomerID(UUID customerID);
}

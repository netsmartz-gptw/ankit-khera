package com.g1app.engine.services;

import com.g1app.engine.models.FamilyMember;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FamilyMembersServices extends CRUDService<FamilyMember> {

    FamilyMember save(FamilyMember entity);

    @Query("select cd from family_members cd where cd.customerID = :customerID")
    List<FamilyMember> findByCustomerID(UUID customerID);

    public void deleteFamilyMember(UUID customerID);
}

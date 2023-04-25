package com.g1app.engine.repositories;

import com.g1app.engine.models.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FamilyMembersRepository extends JpaRepository<FamilyMember, UUID> {

    FamilyMember findByCustomerID(UUID customerID);
    List<FamilyMember> findByFamilyIDAndIsRelationVerified(UUID familyID, boolean value);

    FamilyMember findByCustomerIDAndParentUserID(UUID customerID, UUID ParentUserID);

    List<FamilyMember> findByParentUserID(UUID customerId);

    FamilyMember findByReferenceID(UUID referenceId);



}

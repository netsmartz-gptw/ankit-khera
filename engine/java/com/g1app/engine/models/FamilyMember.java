package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "family_members")
public class FamilyMember {

    @Id
    @Column(name = "reference_id")
    private UUID referenceID;

    @Column(name = "family_id")
    private UUID familyID;

    @Column(name = "customer_id")
    private UUID customerID;

    @Column(name = "parent_user_id")
    private UUID parentUserID;

    @Column(name = "relationship")
    private String relationship;

    @Column(name = "isRelationVerified")
    private boolean isRelationVerified;

    public UUID getReferenceID() {
        return referenceID;
    }

    public void setReferenceID(UUID referenceID) {
        this.referenceID = referenceID;
    }

    public UUID getFamilyID() {
        return familyID;
    }

    public void setFamilyID(UUID familyID) {
        this.familyID = familyID;
    }

    public UUID getCustomerID() {
        return customerID;
    }

    public void setCustomerID(UUID customerID) {
        this.customerID = customerID;
    }

    public UUID getParentUserID() {
        return parentUserID;
    }

    public void setParentUserID(UUID parentUserID) {
        this.parentUserID = parentUserID;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public boolean isRelationVerified() {
        return isRelationVerified;
    }

    public void setRelationVerified(boolean relationVerified) {
        isRelationVerified = relationVerified;
    }

    @Override
    public String toString() {
        return "FamilyMembers{" +
                "referenceID=" + referenceID +
                ", familyID=" + familyID +
                ", customerID=" + customerID +
                ", parentUserID=" + parentUserID +
                ", relationship='" + relationship + '\'' +
                ", isRelationVerified=" + isRelationVerified +
                '}';
    }
}

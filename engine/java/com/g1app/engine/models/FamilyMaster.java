package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "family_master")
public class FamilyMaster {

    @Id
    @Column(name = "family_id")
    private UUID familyID;

    @Column(name = "family_number_prefix")
    private int familyNumberPrefix;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "primary_user")
    private UUID primaryUser;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "family_number_postfix")
    private Long familyNumberPostfix;

    @Column(name = "crm_user_id")
    private UUID crmUserID;

    public UUID getFamilyID() {
        return familyID;
    }

    public void setFamilyID(UUID familyID) {
        this.familyID = familyID;
    }

    public int getFamilyNumberPrefix() {
        return familyNumberPrefix;
    }

    public void setFamilyNumberPrefix(int familyNumberPrefix) {
        this.familyNumberPrefix = familyNumberPrefix;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public UUID getPrimaryUser() {
        return primaryUser;
    }

    public void setPrimaryUser(UUID primaryUser) {
        this.primaryUser = primaryUser;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getFamilyNumberPostfix() {
        return familyNumberPostfix;
    }

    public void setFamilyNumberPostfix(Long familyNumberPostfix) {
        this.familyNumberPostfix = familyNumberPostfix;
    }

    public UUID getCrmUserID() {
        return crmUserID;
    }

    public void setCrmUserID(UUID crmUserID) {
        this.crmUserID = crmUserID;
    }

    @Override
    public String toString() {
        return "FamilyMasterModel{" +
                "familyID=" + familyID +
                ", familyNumberPrefix=" + familyNumberPrefix +
                ", createdAt=" + createdAt +
                ", primaryUser=" + primaryUser +
                ", createdBy='" + createdBy + '\'' +
                ", familyNumberPostfix=" + familyNumberPostfix +
                ", crmUserID=" + crmUserID +
                '}';
    }
}

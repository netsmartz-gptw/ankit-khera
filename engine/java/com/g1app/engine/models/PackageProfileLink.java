package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "package_profile_link")
public class PackageProfileLink {

    @Id
    @Column(name = "package_id")
    public UUID packageId;

    @Column(name = "profile_id")
    public UUID profileId;

    @Column(name = "updated_by")
    public UUID updatedBy;

    @Column(name = "is_active")
    public boolean isActive;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public UUID getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UUID updatedBy) {
        this.updatedBy = updatedBy;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "PackageProfileLink{" +
                "packageId=" + packageId +
                ", profileId=" + profileId +
                ", updatedBy=" + updatedBy +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                '}';
    }
}



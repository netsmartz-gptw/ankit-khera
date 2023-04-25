package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "profile_test_master")
public class ProfileTestMaster {

    @Id
    @Column(name = "id")
    public UUID id;

    @Column(name ="profile_id" )
    public UUID profileId;

    @Column(name = "test_display_name")
    public  String testDisplayName;

    @Column(name = "test_id")
    public UUID testId;

    @Column(name = "last_updated_by")
    public UUID lastUpdatedBy;

    @Column(name = "is_active")
    public boolean isActive;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public String getTestDisplayName() {
        return testDisplayName;
    }

    public void setTestDisplayName(String testDisplayName) {
        this.testDisplayName = testDisplayName;
    }

    public UUID getTestId() {
        return testId;
    }

    public void setTestId(UUID testId) {
        this.testId = testId;
    }

    public UUID getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(UUID lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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
        return "ProfileTestMaster{" +
                "id=" + id +
                ", profileId=" + profileId +
                ", testDisplayName='" + testDisplayName + '\'' +
                ", testId=" + testId +
                ", lastUpdatedBy=" + lastUpdatedBy +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                '}';
    }
}

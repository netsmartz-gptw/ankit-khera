package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "package_test_master")
public class PackageTestMaster {

    @Id
    @Column(name = "id")
    public UUID id;

    @Column(name = "package_id")
    public UUID packageId;

    @Column(name = "test_id")
    public UUID testId;

    @Column(name = "test_display_name")
    public String testDisplayName;

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

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    public UUID getTestId() {
        return testId;
    }

    public void setTestId(UUID testId) {
        this.testId = testId;
    }

    public String getTestDisplayName() {
        return testDisplayName;
    }

    public void setTestDisplayName(String testDisplayName) {
        this.testDisplayName = testDisplayName;
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
        return "PackageTestMaster{" +
                "id=" + id +
                ", packageId=" + packageId +
                ", testId=" + testId +
                ", testDisplayName='" + testDisplayName + '\'' +
                ", lastUpdatedBy=" + lastUpdatedBy +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                '}';
    }
}

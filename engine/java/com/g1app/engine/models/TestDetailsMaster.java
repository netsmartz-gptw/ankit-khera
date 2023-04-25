package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "test_details_master")
public class TestDetailsMaster {

    @Id
    @Column(name="id")
    public java.util.UUID id;

    @Column(name="test_id")
    public UUID testId;

    @Column(name="test_details")
    public String testDetails;

    @Column(name="is_active")
    public boolean isActive;

    @Column( name = "last_updated_at")
    long lastUpdatedAt;

    @Column(name= "last_updated_by")
    public UUID lastUpdatedBy;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTestId() {
        return testId;
    }

    public void setTestId(UUID testId) {
        this.testId = testId;
    }

    public String getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(String testDetails) {
        this.testDetails = testDetails;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(long lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public UUID getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(UUID lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString() {
        return "TestDetailsMaster{" +
                "id=" + id +
                ", testId=" + testId +
                ", testDetails='" + testDetails + '\'' +
                ", isActive=" + isActive +
                ", lastUpdatedAt=" + lastUpdatedAt +
                ", lastUpdatedBy=" + lastUpdatedBy +
                '}';
    }
}
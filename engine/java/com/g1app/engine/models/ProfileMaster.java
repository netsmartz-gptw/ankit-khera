package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "profile_master")
public class ProfileMaster {

    @Id
    @Column(name = "profile_id")
    public UUID profileId;

    @Column(name = "package_id")
    public UUID packageId;

    @Column(name = "old_crm_profile_id")
    public int oldCrmProfileId;

    @Column(name = "profile_title")
    public String profileTitle;

    @Column(name = "profile_description")
    public String profileDescription;

    @Column(name = "profile_report_time")
    public long profileReportTime;

    @Column(name = "profile_recommended_for")
    public String profileRecommendedFor;

    @Column(name = "profile_tags")
    public String profileTags;

    @Column(name = "profile_price")
    public int profilePrice;

    @Column(name = "profile_discount_type")
    public int profileDiscountType;

    @Column(name = "profile_discount_value")
    public int profileDiscountValue;

    @Column(name = "profile_updated_at")
    public long profileUpdatedAt;

    @Column(name = "profile_updated_by")
    public UUID profileUpdatedBy;

    @Column(name = "is_active")
    public boolean isActive;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "profile_main_element")
    public String profileMainElement;

    @Column(name = "sort_order")
    public int sortOrder;

    @Column(name = "total_test_count")
    public int totalTestCount;

    @Column(name = "mrp_price")
    public int mrpPrice;

    @Column(name = "profile_type")
    public int profileType;

    public UUID getProfileId() {
        return profileId;
    }

    public void setProfileId(UUID profileId) {
        this.profileId = profileId;
    }

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    public int getOldCrmProfileId() {
        return oldCrmProfileId;
    }

    public void setOldCrmProfileId(int oldCrmProfileId) {
        this.oldCrmProfileId = oldCrmProfileId;
    }

    public String getProfileTitle() {
        return profileTitle;
    }

    public void setProfileTitle(String profileTitle) {
        this.profileTitle = profileTitle;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public long getProfileReportTime() {
        return profileReportTime;
    }

    public void setProfileReportTime(long profileReportTime) {
        this.profileReportTime = profileReportTime;
    }

    public String getProfileRecommendedFor() {
        return profileRecommendedFor;
    }

    public void setProfileRecommendedFor(String profileRecommendedFor) {
        this.profileRecommendedFor = profileRecommendedFor;
    }

    public String getProfileTags() {
        return profileTags;
    }

    public void setProfileTags(String profileTags) {
        this.profileTags = profileTags;
    }

    public String getProfileMainElement() {
        return profileMainElement;
    }

    public void setProfileMainElement(String profileMainElement) {
        this.profileMainElement = profileMainElement;
    }

    public int getProfilePrice() {
        return profilePrice;
    }

    public void setProfilePrice(int profilePrice) {
        this.profilePrice = profilePrice;
    }

    public int getProfileDiscountType() {
        return profileDiscountType;
    }

    public void setProfileDiscountType(int profileDiscountType) {
        this.profileDiscountType = profileDiscountType;
    }

    public int getProfileDiscountValue() {
        return profileDiscountValue;
    }

    public void setProfileDiscountValue(int profileDiscountValue) {
        this.profileDiscountValue = profileDiscountValue;
    }

    public long getProfileUpdatedAt() {
        return profileUpdatedAt;
    }

    public void setProfileUpdatedAt(long profileUpdatedAt) {
        this.profileUpdatedAt = profileUpdatedAt;
    }

    public UUID getProfileUpdatedBy() {
        return profileUpdatedBy;
    }

    public void setProfileUpdatedBy(UUID profileUpdatedBy) {
        this.profileUpdatedBy = profileUpdatedBy;
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

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public int getTotalTestCount() {
        return totalTestCount;
    }

    public void setTotalTestCount(int totalTestCount) {
        this.totalTestCount = totalTestCount;
    }

    public int getMrpPrice() {
        return mrpPrice;
    }

    public void setMrpPrice(int mrpPrice) {
        this.mrpPrice = mrpPrice;
    }

    public int getProfileType() {
        return profileType;
    }

    public void setProfileType(int profileType) {
        this.profileType = profileType;
    }

    @Override
    public String toString() {
        return "ProfileMaster{" +
                "profileId=" + profileId +
                ", packageId=" + packageId +
                ", oldCrmProfileId=" + oldCrmProfileId +
                ", profileTitle='" + profileTitle + '\'' +
                ", profileDescription='" + profileDescription + '\'' +
                ", profileReportTime=" + profileReportTime +
                ", profileRecommendedFor='" + profileRecommendedFor + '\'' +
                ", profileTags='" + profileTags + '\'' +
                ", profilePrice=" + profilePrice +
                ", profileDiscountType=" + profileDiscountType +
                ", profileDiscountValue=" + profileDiscountValue +
                ", profileUpdatedAt=" + profileUpdatedAt +
                ", profileUpdatedBy=" + profileUpdatedBy +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", profileMainElement='" + profileMainElement + '\'' +
                ", sortOrder=" + sortOrder +
                ", totalTestCount=" + totalTestCount +
                ", mrpPrice=" + mrpPrice +
                ", profileType=" + profileType +
                '}';
    }
}

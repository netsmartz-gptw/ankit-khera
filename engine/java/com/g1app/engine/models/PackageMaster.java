package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "package_master")
public class PackageMaster {

    @Id
    @Column(name = "package_id")
    public UUID packageId;

    @Column(name = "old_crm_package_id")
    public int oldCrmPackageId;

    @Column(name = "package_title")
    public String packageTitle;

    @Column(name = "package_description")
    public String packageDescription;

    @Column(name = "package_report_time")
    public long packageReportTime;

    @Column(name = "package_recommended_for")
    public String packageRecommendedFor;

    @Column(name = "package_tags")
    public String packageTags;

    @Column(name = "package_price")
    public int packagePrice;

    @Column(name = "package_discount_type")
    public int packageDiscountType;

    @Column(name = "package_discount_value")
    public int packageDiscountValue;

    @Column(name = "package_updated_at")
    public long packageUpdatedAt;

    @Column(name = "package_updated_by")
    public UUID packageUpdatedBy;

    @Column(name = "is_active")
    public boolean isActive;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "sort_order")
    public int sortOrder;

    @Column(name = "total_test_count")
    public int totalTestCount;

    @Column(name = "filter_value")
    public int filterValue;

    @Column(name = "package_type")
    public String packageType;

    @Column(name = "mrp_price")
    public int mrpPrice;

    @Column(name = "doctor_consultation")
    public int doctorConsultation;

    @Column(name = "dietician_consultation")
    public int dieticianConsultation;

    public UUID getPackageId() {
        return packageId;
    }

    public void setPackageId(UUID packageId) {
        this.packageId = packageId;
    }

    public int getOldCrmPackageId() {
        return oldCrmPackageId;
    }

    public void setOldCrmPackageId(int oldCrmPackageId) {
        this.oldCrmPackageId = oldCrmPackageId;
    }

    public String getPackageTitle() {
        return packageTitle;
    }

    public void setPackageTitle(String packageTitle) {
        this.packageTitle = packageTitle;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public long getPackageReportTime() {
        return packageReportTime;
    }

    public void setPackageReportTime(long packageReportTime) {
        this.packageReportTime = packageReportTime;
    }

    public String getPackageRecommendedFor() {
        return packageRecommendedFor;
    }

    public void setPackageRecommendedFor(String packageRecommendedFor) {
        this.packageRecommendedFor = packageRecommendedFor;
    }

    public String getPackageTags() {
        return packageTags;
    }

    public void setPackageTags(String packageTags) {
        this.packageTags = packageTags;
    }

    public int getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(int packagePrice) {
        this.packagePrice = packagePrice;
    }

    public int getPackageDiscountType() {
        return packageDiscountType;
    }

    public void setPackageDiscountType(int packageDiscountType) {
        this.packageDiscountType = packageDiscountType;
    }

    public int getPackageDiscountValue() {
        return packageDiscountValue;
    }

    public void setPackageDiscountValue(int packageDiscountValue) {
        this.packageDiscountValue = packageDiscountValue;
    }

    public long getPackageUpdatedAt() {
        return packageUpdatedAt;
    }

    public void setPackageUpdatedAt(long packageUpdatedAt) {
        this.packageUpdatedAt = packageUpdatedAt;
    }

    public UUID getPackageUpdatedBy() {
        return packageUpdatedBy;
    }

    public void setPackageUpdatedBy(UUID packageUpdatedBy) {
        this.packageUpdatedBy = packageUpdatedBy;
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

    public int getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(int filterValue) {
        this.filterValue = filterValue;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
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

    public int getDoctorConsultation() {
        return doctorConsultation;
    }

    public void setDoctorConsultation(int doctorConsultation) {
        this.doctorConsultation = doctorConsultation;
    }

    public int getDieticianConsultation() {
        return dieticianConsultation;
    }

    public void setDieticianConsultation(int dieticianConsultation) {
        this.dieticianConsultation = dieticianConsultation;
    }

    @Override
    public String toString() {
        return "PackageMaster{" +
                "packageId=" + packageId +
                ", oldCrmPackageId=" + oldCrmPackageId +
                ", packageTitle='" + packageTitle + '\'' +
                ", packageDescription='" + packageDescription + '\'' +
                ", packageReportTime=" + packageReportTime +
                ", packageRecommendedFor='" + packageRecommendedFor + '\'' +
                ", packageTags='" + packageTags + '\'' +
                ", packagePrice=" + packagePrice +
                ", packageDiscountType=" + packageDiscountType +
                ", packageDiscountValue=" + packageDiscountValue +
                ", packageUpdatedAt=" + packageUpdatedAt +
                ", packageUpdatedBy=" + packageUpdatedBy +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", sortOrder=" + sortOrder +
                ", totalTestCount=" + totalTestCount +
                ", filterValue=" + filterValue +
                ", packageType='" + packageType + '\'' +
                ", mrpPrice=" + mrpPrice +
                ", doctorConsultation=" + doctorConsultation +
                ", dieticianConsultation=" + dieticianConsultation +
                '}';
    }
}

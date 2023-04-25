package com.g1app.engine.models;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

@TypeDefs({
        @TypeDef(
                name = "int-array",
                typeClass = IntArrayType.class
        )
})

@Entity
@Table(name = "test_master")
public class TestMaster {

    @Id
    @Column(name = "test_id")
    public UUID testId;

    @Column(name = "old_crm_test_id")
    public int oldCrmTestId;

    @Column(name = "test_title")
    public String testTitle;

    @Column(name = "test_description")
    public String testDescription;

    @Column(name = "report_time")
    public long reportTime;

    @Column(name = "test_recommended_for")
    public String testRecommendedFor;

    @Column(name = "test_tags")
    public  String testTags;

    @Column(name = "test_main_element")
    public String testMainElement;

    @Column(name = "test_price")
    public int testPrice;

    @Column(name = "test_discount_type")
    public int testDiscountType;

    @Column(name = "test_discount_value")
    public int testDiscountValue;

    @Column(name = "test_updated_at")
    public long testUpdatedAt;

    @Column(name = "test_updated_by")
    public UUID testUpdatedBy;

    @Column(name = "is_active")
    public boolean isActive;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "sort_order")
    public int sortOrder;

    @Type(type = "int-array")
    @Column(name = "filter_value", columnDefinition = "integer[]")
    public int[] filterValue;

    @Column(name = "test_type")
    public int testType;

    @Column(name = "mrp_price")
    public int mrpPrice;

    public UUID getTestId() {
        return testId;
    }

    public void setTestId(UUID testId) {
        this.testId = testId;
    }

    public int getOldCrmTestId() {
        return oldCrmTestId;
    }

    public void setOldCrmTestId(int oldCrmTestId) {
        this.oldCrmTestId = oldCrmTestId;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public long getReportTime() {
        return reportTime;
    }

    public void setReportTime(long reportTime) {
        this.reportTime = reportTime;
    }

    public String getTestRecommendedFor() {
        return testRecommendedFor;
    }

    public void setTestRecommendedFor(String testRecommendedFor) {
        this.testRecommendedFor = testRecommendedFor;
    }

    public String getTestTags() {
        return testTags;
    }

    public void setTestTags(String testTags) {
        this.testTags = testTags;
    }

    public String getTestMainElement() {
        return testMainElement;
    }

    public void setTestMainElement(String testMainElement) {
        this.testMainElement = testMainElement;
    }

    public int getTestPrice() {
        return testPrice;
    }

    public void setTestPrice(int testPrice) {
        this.testPrice = testPrice;
    }

    public int getTestDiscountType() {
        return testDiscountType;
    }

    public void setTestDiscountType(int testDiscountType) {
        this.testDiscountType = testDiscountType;
    }

    public int getTestDiscountValue() {
        return testDiscountValue;
    }

    public void setTestDiscountValue(int testDiscountValue) {
        this.testDiscountValue = testDiscountValue;
    }

    public long getTestUpdatedAt() {
        return testUpdatedAt;
    }

    public void setTestUpdatedAt(long testUpdatedAt) {
        this.testUpdatedAt = testUpdatedAt;
    }

    public UUID getTestUpdatedBy() {
        return testUpdatedBy;
    }

    public void setTestUpdatedBy(UUID testUpdatedBy) {
        this.testUpdatedBy = testUpdatedBy;
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

    public int[] getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(int[] filterValue) {
        this.filterValue = filterValue;
    }

    public int getTestType() {
        return testType;
    }

    public void setTestType(int testType) {
        this.testType = testType;
    }

    public int getMrpPrice() {
        return mrpPrice;
    }

    public void setMrpPrice(int mrpPrice) {
        this.mrpPrice = mrpPrice;
    }

    @Override
    public String toString() {
        return "TestMaster{" +
                "testId=" + testId +
                ", oldCrmTestId=" + oldCrmTestId +
                ", testTitle='" + testTitle + '\'' +
                ", testDescription='" + testDescription + '\'' +
                ", reportTime=" + reportTime +
                ", testRecommendedFor='" + testRecommendedFor + '\'' +
                ", testTags='" + testTags + '\'' +
                ", testMainElement='" + testMainElement + '\'' +
                ", testPrice=" + testPrice +
                ", testDiscountType=" + testDiscountType +
                ", testDiscountValue=" + testDiscountValue +
                ", testUpdatedAt=" + testUpdatedAt +
                ", testUpdatedBy=" + testUpdatedBy +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", sortOrder=" + sortOrder +
                ", filterValue=" + Arrays.toString(filterValue) +
                ", testType=" + testType +
                ", mrpPrice=" + mrpPrice +
                '}';
    }
}

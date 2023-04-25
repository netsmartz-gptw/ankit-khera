package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "filter_category_master")
public class FilterCategoryMaster {

    @Id
    @Column(name="category_id")
    public UUID categoryId;

    @Column(name="category_name")
    public String categoryName;

    @Column(name="category_type")
    public String categoryType;

    @Column(name="is_active")
    public boolean isActive;

    @Column(name="is_deleted")
    public boolean isDeleted ;

    @Column(name="sort_order")
    public int sortOrder;

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
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

    @Override
    public String toString() {
        return "FilterCategoryMaster{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", isActive=" + isActive +
                ", isDeleted=" + isDeleted +
                ", sortOrder=" + sortOrder +
                '}';
    }
}

package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "filter_master")
public class FilterMaster {
    @Id
    @Column(name = "sub_category_id")
    UUID subCategoryId ;

    @Column(name = "category_id")
    String categoryId;

    @Column(name = "sub_category")
    String subCategory;

    @Column(name = "sub_category_value")
    double subCategoryValue;

    @Column(name = "category_type")
    String categoryType;

    public UUID getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(UUID subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public double getSubCategoryValue() {
        return subCategoryValue;
    }

    public void setSubCategoryValue(double subCategoryValue) {
        this.subCategoryValue = subCategoryValue;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        return "FilterMaster{" +
                "subCategoryId=" + subCategoryId +
                ", categoryId='" + categoryId + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", subCategoryValue=" + subCategoryValue +
                ", categoryType='" + categoryType + '\'' +
                '}';
    }
}

package com.g1app.engine.repositories;

import com.g1app.engine.models.FilterCategoryMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FilterCategoryMasterRepository extends JpaRepository<FilterCategoryMaster, UUID> {

    List<FilterCategoryMaster> findAll();

    @Query("select fcm.categoryName from FilterCategoryMaster as fcm")
    List<String> getFilterCategory();
}

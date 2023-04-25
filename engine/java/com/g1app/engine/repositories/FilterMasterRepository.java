package com.g1app.engine.repositories;

import com.g1app.engine.models.FilterMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FilterMasterRepository extends JpaRepository<FilterMaster, UUID> {

    List<FilterMaster> findByCategoryId(String categoryId);
}

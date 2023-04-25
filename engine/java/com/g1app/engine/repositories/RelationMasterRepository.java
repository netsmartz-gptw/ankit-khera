package com.g1app.engine.repositories;

import com.g1app.engine.models.RelationMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RelationMasterRepository extends JpaRepository<RelationMaster, UUID> {
}

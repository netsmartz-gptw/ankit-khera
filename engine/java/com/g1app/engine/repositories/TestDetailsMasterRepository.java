package com.g1app.engine.repositories;

import com.g1app.engine.models.TestDetailsMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestDetailsMasterRepository extends JpaRepository<TestDetailsMaster, UUID> {
    List<TestDetailsMaster> findByTestId(UUID testID);
}

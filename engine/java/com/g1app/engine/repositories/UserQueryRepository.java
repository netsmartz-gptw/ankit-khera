package com.g1app.engine.repositories;

import com.g1app.engine.models.UserQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserQueryRepository extends JpaRepository<UserQuery, UUID> {
}

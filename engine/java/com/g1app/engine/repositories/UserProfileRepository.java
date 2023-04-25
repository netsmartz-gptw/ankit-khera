package com.g1app.engine.repositories;

import com.g1app.engine.models.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

    UserProfile findByCustomerID(UUID customerID);

}

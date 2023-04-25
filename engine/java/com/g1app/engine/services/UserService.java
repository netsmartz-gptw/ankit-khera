package com.g1app.engine.services;

import com.g1app.engine.models.User;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserService extends CRUDService<User> {

    Optional<User> findUserMobileFromOtp(String mobile_number);

    @Query("select cd from app_user cd where cd.mobile_number = :mobile_number")
    User findBymobileNumber(String mobile_number);

}

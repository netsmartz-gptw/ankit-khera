package com.g1app.engine.repositories;


import java.util.List;
import java.util.UUID;

import com.g1app.engine.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<User> findFirst1ByMobileNumber(String mobileNumber);

    User findByCustomerID(UUID customerID);

    User findBygToken(String token);

    User findBymobileNumber(String mobile_number);

    List<User> findAllByMobileNumber(String mobile_number);
    List<User> findAllByAlternateMobile(String mobile_number);

    @Query(value = "select case when mobile_number isnull then 0 else 1 end as status from family_members a \n" +
            "left outer  join app_user b on a.customer_id=b.customer_id \n" +
            "and mobile_number=?2 \n" +
            "where parent_user_id= ?1  \n" +
            "order by case when mobile_number isnull then 0 else 1 end desc\n" +
            "limit 1" , nativeQuery = true)
    int checkNumberIsAlreadyExistsInFamily(UUID parentUserID, String mobileNo);


    @Query(value = "select * from app_user where email_id = ?1 and g_token  IS NOT NULL", nativeQuery = true)
    User getGoogleUserDetails(String email);

    @Query(value = "select * from app_user where email_id = ?1 and f_token  IS NOT NULL", nativeQuery = true)
    User getFacebookUserDetails(String email);


}
package com.g1app.engine.repositories;

import com.g1app.engine.models.AddressMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AddressMasterRepository extends JpaRepository<AddressMaster , UUID> {

    List<AddressMaster> findByCustomerId(UUID customerId);
    AddressMaster findByCustomerIdAndAddressId(UUID customerId, UUID addressId);
    AddressMaster findByAddressId(UUID addressId);

}

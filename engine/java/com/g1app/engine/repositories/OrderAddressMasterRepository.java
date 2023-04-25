package com.g1app.engine.repositories;

import com.g1app.engine.models.OrderAddressMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderAddressMasterRepository extends JpaRepository<OrderAddressMaster , UUID> {
    OrderAddressMaster findByAddressId(UUID addressId);
}

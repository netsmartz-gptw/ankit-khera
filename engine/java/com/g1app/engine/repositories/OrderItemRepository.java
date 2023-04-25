package com.g1app.engine.repositories;

import com.g1app.engine.models.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItems, UUID> {
    OrderItems findByOrderId(UUID orderId);
}

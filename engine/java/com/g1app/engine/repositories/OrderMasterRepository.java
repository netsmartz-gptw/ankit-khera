package com.g1app.engine.repositories;

import com.g1app.engine.models.OrderMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderMasterRepository extends JpaRepository<OrderMaster , UUID> {

    OrderMaster findByBookingReference(UUID bookingReference);

    @Query("SELECT SUM(o.totalOrderAmount) FROM OrderMaster as o WHERE bookingReference= ?1")
    int getAmountByReference(UUID bookingReference);

    List<OrderMaster> findByCustomerIdAndBookedBy(UUID customerId,UUID parentId);

    OrderMaster findByOrderId(UUID orderId);

    @Query("select o from OrderMaster as o where customerId = ?1")
    OrderMaster orderDetail(UUID customerId);

    @Query("select o from OrderMaster as o where customerId = ?1")
    OrderMaster customerOrderDetails(UUID customerId);


}

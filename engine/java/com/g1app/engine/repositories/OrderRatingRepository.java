package com.g1app.engine.repositories;

import com.g1app.engine.models.OrderRating;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRatingRepository extends CrudRepository<OrderRating, UUID> {
}

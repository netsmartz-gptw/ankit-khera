package com.g1app.engine.repositories;

import com.g1app.engine.models.CouponMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CouponRepository extends JpaRepository<CouponMaster,UUID> {

}

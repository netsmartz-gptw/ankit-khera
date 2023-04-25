package com.g1app.engine.repositories;

import com.g1app.engine.models.CityMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CityMasterRepository extends JpaRepository<CityMaster, UUID> {

    List<CityMaster> findByCityNameStartsWith(String cityName);
    CityMaster findByCityID(UUID cityID);
    CityMaster findByCityName(UUID cityID);

    @Query(value = "SELECT pg_terminate_backend(pid)\n" +
            "FROM pg_stat_activity\n" +
            "WHERE datname = 'g1app'\n" +
            "AND pid <> pg_backend_pid()\n" +
            "AND state in ('idle', 'idle in transaction', 'idle in transaction (aborted)', 'disabled')\n" +
            "AND state_change < current_timestamp - INTERVAL '2' MINUTE;",nativeQuery = true)
    boolean cleanAllIdleConnection();

}

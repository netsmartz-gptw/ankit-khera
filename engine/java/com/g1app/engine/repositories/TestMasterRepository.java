package com.g1app.engine.repositories;

import com.g1app.engine.models.TestMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TestMasterRepository extends JpaRepository<TestMaster, UUID> {

    List<TestMaster> findByIsActiveAndIsDeletedOrderBySortOrderDesc(boolean valActive, boolean valDeleted);
    TestMaster findByTestId(UUID testID);

    //TestMaster findByFilterValue(List<Integer> filterValue);
   // Student findByRollNo(List<Integer> rollNos);

    //List<TestMaster> findByFilterValue(int[] filterValue);

//    @Query("FROM TestMaster as tm WHERE tm.filterValue @> '?1'")
//    List<TestMaster> getTestId(int[] filterValue);

//    @Query(value = "SELECT * FROM test_master WHERE filter_value @> ?1", nativeQuery = true)
//    List<TestMaster> query(String filterValue);

    @Query(value = "SELECT * FROM test_master where test_recommended_for= :value", nativeQuery = true)
    List<TestMaster> query(@Param("value") String value);

    @Query("FROM TestMaster as pm WHERE lower(pm.testTitle) LIKE %?1% OR lower(pm.testDescription)" +
            " LIKE %?1% OR lower(pm.testRecommendedFor) LIKE %?1% OR lower(pm.testTags) LIKE %?1% ")
    List<TestMaster> getTests(String search);
}

package com.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.backend.entity.TestEntity;

@Repository
public interface TestRepository extends CrudRepository<TestEntity, Long> {
    
    List<TestEntity> findByValueContainingIgnoreCase(String value);

    List<TestEntity> findByValueIgnoreCase(String value);

    @Query("SELECT t FROM Test t WHERE t.value LIKE %?1%")
    List<TestEntity> findByValue(String value);
}

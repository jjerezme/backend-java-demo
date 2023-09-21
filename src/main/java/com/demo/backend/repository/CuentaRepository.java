package com.demo.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.backend.entity.CuentaEntity;

@Repository
public interface CuentaRepository extends CrudRepository<CuentaEntity, Long> {
    
    Optional<CuentaEntity> findByNumero(String value);

    @Modifying
    @Transactional
    @Query("update Cuenta c set c.estado = ?2 where c.id = ?1")
    void update(Long id, Boolean value);
}

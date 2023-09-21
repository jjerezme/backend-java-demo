package com.demo.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.backend.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByDni(String value);

    List<ClienteEntity> findByNombreContainingIgnoreCase(String value);

    @Modifying
    @Transactional
    @Query("update Cliente c set c.estado = ?2 where c.id = ?1")
    void update(Long id, Boolean value);
}

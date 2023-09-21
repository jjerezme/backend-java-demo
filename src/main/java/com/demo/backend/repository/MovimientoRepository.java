package com.demo.backend.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.demo.backend.entity.CuentaEntity;
import com.demo.backend.entity.MovimientoEntity;

@Repository
public interface MovimientoRepository extends CrudRepository<MovimientoEntity, Long> {

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta = ?1 ORDER BY m.fecha DESC")
    List<MovimientoEntity> findByCuenta(CuentaEntity cuenta);

    @Query("SELECT m FROM Movimiento m WHERE m.cuenta = ?1 AND m.fecha BETWEEN ?2 AND ?3 ORDER BY m.fecha DESC")
    List<MovimientoEntity> findByCuenta(CuentaEntity cuenta, Date from, Date to);

    @Query("SELECT SUM(m.valor) FROM Movimiento m WHERE m.cuenta = ?1 AND m.valor < 0 AND m.fecha BETWEEN ?2 AND ?3")
    Optional<Double> getWithdrawalByDay(CuentaEntity cuenta, Date from, Date to);
}
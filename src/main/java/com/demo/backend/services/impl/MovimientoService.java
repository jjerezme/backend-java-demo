package com.demo.backend.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.backend.ConfigProperties;
import com.demo.backend.dto.MovimientoDTO;
import com.demo.backend.entity.CuentaEntity;
import com.demo.backend.entity.MovimientoEntity;
import com.demo.backend.exception.BusinessException;
import com.demo.backend.repository.CuentaRepository;
import com.demo.backend.repository.MovimientoRepository;
import com.demo.backend.services.IMovimientoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MovimientoService implements IMovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ConfigProperties config;

    @Override
    public List<MovimientoDTO> all() {
        Iterable<MovimientoEntity> it = this.movimientoRepository.findAll();
        List<MovimientoDTO> result = new ArrayList<MovimientoDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    @Override
    public MovimientoDTO get(Long id) {
        Optional<MovimientoEntity> findById = this.movimientoRepository.findById(id);

        if (!findById.isPresent()) {
            log.debug("Not found {}", id);
            return null;
        }

        MovimientoEntity entity = findById.get();
        log.debug("Found {}", entity);
        return toDTO(entity);
    }

    @Override
    public List<MovimientoDTO> findByCuenta(String num) {
        Optional<CuentaEntity> findBy = this.cuentaRepository.findByNumero(num);

        log.debug("Cuenta {}", findBy);

        // Valida si la cuenta existe
        if (!findBy.isPresent()) {
            throw new BusinessException("Cuenta no encontrada");
        }

        CuentaEntity cuentaEntity = findBy.get();
        Iterable<MovimientoEntity> it = this.movimientoRepository.findByCuenta(cuentaEntity);
        List<MovimientoDTO> result = new ArrayList<MovimientoDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    @Override
    public MovimientoDTO add(Double valor, String cuenta) {
        if (valor == 0) {
            throw new BusinessException("No se puede hacer un movimiento de valor 0");
        }

        Optional<CuentaEntity> findBy = this.cuentaRepository.findByNumero(cuenta);

        log.debug("Cuenta {}", findBy);

        // Valida si la cuenta existe
        if (!findBy.isPresent()) {
            throw new BusinessException("Cuenta no encontrada");
        }

        CuentaEntity cuentaEntity = findBy.get();

        // Valida si la cuenta esta activa
        if (cuentaEntity.getEstado() != Boolean.TRUE) {
            throw new BusinessException("La cuenta esta inactiva");
        }

        Double saldo = cuentaEntity.getSaldo();

        // Valida si tiene saldo disponible
        if (saldo == 0 && valor < 0) {
            throw new BusinessException("Saldo no disponible");
        }

        // Valida si le alcanza el saldo
        if (saldo + valor < 0) {
            throw new BusinessException("No tienes suficiente saldo");
        }

        // Valida si es un retiro
        if (valor < 0) {
            Double withdrawalByCurrentDay = this.getWithdrawalByCurrentDay(cuentaEntity);

            log.info("Retiros del día {}", withdrawalByCurrentDay);

            // Valida si supera el número de retiros diarios permitidos
            if (withdrawalByCurrentDay <= config.getMaxWithdrawalPerDay()) {
                throw new BusinessException("Supera el límite de retiros permitidos por día");
            }
        }

        MovimientoEntity movimientoEntity = MovimientoEntity.builder().build();
        movimientoEntity.setFecha(new Date());
        movimientoEntity.setCuenta(cuentaEntity);
        movimientoEntity.setSaldo(saldo);
        movimientoEntity.setValor(valor);

        MovimientoEntity movimientoResult = this.movimientoRepository.save(movimientoEntity);
        log.debug("Insert {}", movimientoResult);

        cuentaEntity.setSaldo(saldo + valor);

        CuentaEntity cuentaResult = this.cuentaRepository.save(cuentaEntity);
        log.debug("Insert {}", cuentaResult);

        return toDTO(movimientoResult);
    }

    @Override
    public MovimientoDTO update(MovimientoDTO dto) {
        throw new BusinessException("No es posible actualizar un movimiento");
    }

    @Override
    public MovimientoDTO patch(Long id, Map<String, Object> fields) {
        throw new BusinessException("No es posible actualizar un movimiento");
    }

    @Override
    public void delete(Long id) {
        throw new BusinessException("No es posible eliminar un movimiento");
    }

    /**
     * Obtiene la suma de retiros del día actual
     * 
     * @param cuenta Número de la cuenta
     * @return Suma de retiros
     */
    private Double getWithdrawalByCurrentDay(CuentaEntity cuenta) {
        Date dateFrom = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
        Date dateTo = DateUtils.addDays(dateFrom, 1);

        return this.getWithdrawalByDay(cuenta, dateFrom, dateTo);
    }

    /**
     * Obtiene la suma de retiros entre un rango de días
     * 
     * @param cuenta Número de la cuenta
     * @param from   Fecha desde
     * @param to     Fecha hasta
     * @return Suma de retiros
     */
    private Double getWithdrawalByDay(CuentaEntity cuenta, Date from, Date to) {
        Optional<Double> withdrawalByDay = this.movimientoRepository
                .getWithdrawalByDay(cuenta, from, to);

        if (withdrawalByDay.isPresent()) {
            return withdrawalByDay.get();
        }

        return 0d;
    }

    /**
     * Convierte un objeto Entity a DTO
     * 
     * @param entity Entity
     * @return DTO
     */
    private MovimientoDTO toDTO(MovimientoEntity entity) {
        if (entity == null) {
            return null;
        }

        return MovimientoDTO.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .valor(entity.getValor())
                .saldo(entity.getSaldo())
                .build();
    }

}

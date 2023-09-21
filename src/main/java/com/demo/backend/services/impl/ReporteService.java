package com.demo.backend.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.backend.dto.ReporteDTO;
import com.demo.backend.entity.CuentaEntity;
import com.demo.backend.entity.MovimientoEntity;
import com.demo.backend.exception.BusinessException;
import com.demo.backend.repository.CuentaRepository;
import com.demo.backend.repository.MovimientoRepository;
import com.demo.backend.services.IReportesService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReporteService implements IReportesService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public List<ReporteDTO> all() {
        Iterable<MovimientoEntity> it = this.movimientoRepository.findAll();
        List<ReporteDTO> result = new ArrayList<ReporteDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    @Override
    public List<ReporteDTO> getByCuentas(String cuenta) {
        Optional<CuentaEntity> findBy = this.cuentaRepository.findByNumero(cuenta);

        if (!findBy.isPresent()) {
            throw new BusinessException("Cuenta no encontrada");
        }

        CuentaEntity cuentaEntity = findBy.get();
        log.debug("Cuenta {}", cuentaEntity);

        Iterable<MovimientoEntity> it = this.movimientoRepository.findByCuenta(cuentaEntity);
        List<ReporteDTO> result = new ArrayList<ReporteDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    @Override
    public List<ReporteDTO> getByCuentas(String cuenta, Date desde, Date hasta) {
        Optional<CuentaEntity> findBy = this.cuentaRepository.findByNumero(cuenta);

        if (!findBy.isPresent()) {
            throw new BusinessException("Cuenta no encontrada");
        }

        CuentaEntity cuentaEntity = findBy.get();
        log.debug("Cuenta {}", cuentaEntity);
        log.debug("Fechas {}, {}", desde, hasta);

        Iterable<MovimientoEntity> it = this.movimientoRepository.findByCuenta(cuentaEntity, desde, hasta);
        List<ReporteDTO> result = new ArrayList<ReporteDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    /**
     * Convert Entity to DTO
     * 
     * @param entity Entity
     * @return DTO
     */
    private ReporteDTO toDTO(MovimientoEntity entity) {
        if (entity == null) {
            return null;
        }

        return ReporteDTO.builder()
                .fecha(entity.getFecha().toString())
                .cuenta(entity.getCuenta().getNumero())
                .cliente(entity.getCuenta().getCliente().getNombre())
                .tipo(entity.getCuenta().getTipo())
                .movimiento(entity.getValor())
                .saldoInicial(entity.getSaldo())
                .saldoDisponible(entity.getSaldo() + entity.getValor())
                .build();
    }

}

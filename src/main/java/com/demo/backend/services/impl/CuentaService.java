package com.demo.backend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.backend.dto.CuentaDTO;
import com.demo.backend.dto.TestDTO;
import com.demo.backend.entity.ClienteEntity;
import com.demo.backend.entity.CuentaEntity;
import com.demo.backend.entity.TestEntity;
import com.demo.backend.exception.BusinessException;
import com.demo.backend.repository.ClienteRepository;
import com.demo.backend.repository.CuentaRepository;
import com.demo.backend.services.ICuentaService;
import com.demo.backend.utils.Helpers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CuentaService implements ICuentaService {

    @Autowired
    CuentaRepository cuentaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public List<CuentaDTO> all() {
        Iterable<CuentaEntity> it = this.cuentaRepository.findAll();
        List<CuentaDTO> result = new ArrayList<CuentaDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    @Override
    public CuentaDTO get(Long id) {
        Optional<CuentaEntity> findBy = this.cuentaRepository.findById(id);

        if (!findBy.isPresent()) {
            log.debug("Not found {}", id);
            return null;
        }

        CuentaEntity entity = findBy.get();
        log.debug("Found {}", entity);
        return toDTO(entity);
    }

    @Override
    public CuentaDTO findByNumero(String value) {
        Optional<CuentaEntity> findBy = this.cuentaRepository.findByNumero(value);

        if (!findBy.isPresent()) {
            log.debug("Not found {}", findBy);
            return null;
        }

        CuentaEntity entity = findBy.get();
        log.debug("Found {}", entity);
        return toDTO(entity);
    }

    @Override
    public CuentaDTO add(CuentaDTO dto, String dni) {
        Optional<ClienteEntity> findBy = this.clienteRepository.findByDni(dni);

        log.debug("Cliente {}", findBy);

        // Valida si el cliente existe
        if (!findBy.isPresent()) {
            throw new BusinessException("Cliente no encontrado");
        }

        ClienteEntity clienteEntity = findBy.get();
        CuentaEntity cuentaEntity = toEntity(dto);
        cuentaEntity.setCliente(clienteEntity);
        if (cuentaEntity.getEstado() == null) {
            cuentaEntity.setEstado(true);
        }

        CuentaEntity result = this.cuentaRepository.save(cuentaEntity);
        log.debug("Insert {}", result);
        return toDTO(result);
    }

    @Override
    public CuentaDTO update(CuentaDTO dto) {
        Optional<CuentaEntity> result = this.cuentaRepository.findById(dto.getId());

        if (!result.isPresent()) {
            throw new BusinessException("La cuenta no existe");
        }

        CuentaEntity cuentaEntity = result.get();
        CuentaEntity newEntity = toEntity(dto);
        newEntity.setCliente(cuentaEntity.getCliente());

        CuentaEntity entity = this.cuentaRepository.save(newEntity);
        log.debug("Update {}", entity);
        return toDTO(entity);
    }

    @Override
    public CuentaDTO patch(Long id, Map<String, Object> fields) {
        Optional<CuentaEntity> findById = this.cuentaRepository.findById(id);

        if (!findById.isPresent()) {
            throw new BusinessException("La cuenta no existe");
        }

        CuentaEntity cuentaEntity = findById.get();
        Helpers.patchFields(fields, cuentaEntity);

        CuentaEntity entity = this.cuentaRepository.save(cuentaEntity);
        log.debug("Patch {}", entity);
        return toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        Optional<CuentaEntity> result = this.cuentaRepository.findById(id);

        if (!result.isPresent()) {
            throw new BusinessException("La cuenta no existe");
        }

        CuentaEntity cuentaEntity = result.get();
        cuentaEntity.setEstado(false);
        this.cuentaRepository.save(cuentaEntity);
        log.debug("Delete {}", id);
    }

    /**
     * Convert Entity to DTO
     * 
     * @param entity Entity
     * @return DTO
     */
    private CuentaDTO toDTO(CuentaEntity entity) {
        if (entity == null) {
            return null;
        }

        return CuentaDTO.builder()
                .id(entity.getId())
                .numero(entity.getNumero())
                .tipo(entity.getTipo())
                .saldo(entity.getSaldo())
                .estado(entity.getEstado())
                .build();
    }

    /**
     * Convert DTO to Entity
     * 
     * @param dto DTO
     * @return Entity
     */
    private CuentaEntity toEntity(CuentaDTO dto) {
        if (dto == null) {
            return null;
        }

        return CuentaEntity.builder()
                .id(dto.getId())
                .numero(StringUtils.trimToNull(dto.getNumero()))
                .tipo(StringUtils.trimToNull(dto.getTipo()))
                .saldo(dto.getSaldo())
                .estado(dto.getEstado())
                .build();
    }

}

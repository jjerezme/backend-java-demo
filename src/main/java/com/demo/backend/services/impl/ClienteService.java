package com.demo.backend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.backend.dto.ClienteDTO;
import com.demo.backend.entity.ClienteEntity;
import com.demo.backend.exception.BusinessException;
import com.demo.backend.repository.ClienteRepository;
import com.demo.backend.services.IClienteService;
import com.demo.backend.utils.Helpers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<ClienteDTO> all() {
        Iterable<ClienteEntity> it = this.clienteRepository.findAll();
        List<ClienteDTO> result = new ArrayList<ClienteDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    @Override
    public ClienteDTO get(Long id) {
        Optional<ClienteEntity> findById = this.clienteRepository.findById(id);

        if (!findById.isPresent()) {
            log.debug("Not found {}", id);
            return null;
        }

        ClienteEntity entity = findById.get();
        log.debug("Found {}", entity);
        return toDTO(entity);
    }

    @Override
    public ClienteDTO findByDni(String value) {
        Optional<ClienteEntity> result = this.clienteRepository.findByDni(value);

        if (!result.isPresent()) {
            log.debug("Not found {}", value);
            return null;
        }

        ClienteEntity entity = result.get();
        log.debug("Found {}", entity);
        return toDTO(entity);
    }

    @Override
    public List<ClienteDTO> findByNombre(String value) {
        Iterable<ClienteEntity> it = this.clienteRepository.findByNombreContainingIgnoreCase(value);
        List<ClienteDTO> result = new ArrayList<ClienteDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    @Override
    public ClienteDTO add(ClienteDTO dto) {
        ClienteEntity entity = this.clienteRepository.save(toEntity(dto));
        log.debug("Insert {}", entity);
        return toDTO(entity);
    }

    @Override
    public ClienteDTO update(ClienteDTO dto) {
        boolean result = this.clienteRepository.existsById(dto.getId());

        if (!result) {
            throw new BusinessException("El cliente no existe");
        }

        ClienteEntity entity = this.clienteRepository.save(toEntity(dto));
        log.debug("Update {}", entity);
        return toDTO(entity);
    }

    @Override
    public ClienteDTO patch(Long id, Map<String, Object> fields) {
        Optional<ClienteEntity> findById = this.clienteRepository.findById(id);

        if (!findById.isPresent()) {
            throw new BusinessException("El cliente no existe");
        }

        ClienteEntity clienteEntity = findById.get();
        Helpers.patchFields(fields, clienteEntity);

        ClienteEntity entity = this.clienteRepository.save(clienteEntity);
        log.debug("Patch {}", entity);
        return toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        Optional<ClienteEntity> result = this.clienteRepository.findById(id);

        if (!result.isPresent()) {
            throw new BusinessException("El cliente no existe");
        }

        ClienteEntity clienteEntity = result.get();
        clienteEntity.setEstado(false);
        this.clienteRepository.save(clienteEntity);
        log.debug("Delete {}", id);
    }

    /**
     * Convert Entity to DTO
     * 
     * @param entity Entity
     * @return DTO
     */
    private ClienteDTO toDTO(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        return ClienteDTO.builder()
                .id(entity.getId())
                .dni(entity.getDni())
                .nombre(entity.getNombre())
                .genero(entity.getGenero())
                .edad(entity.getEdad())
                .telefono(entity.getTelefono())
                .direccion(entity.getDireccion())
                .password(entity.getPassword())
                .estado(entity.getEstado())
                .build();
    }

    /**
     * Convert DTO to Entity
     * 
     * @param dto DTO
     * @return Entity
     */
    private ClienteEntity toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        return ClienteEntity.builder()
                .id(dto.getId())
                .dni(StringUtils.trimToNull(dto.getDni()))
                .nombre(StringUtils.trimToNull(dto.getNombre()))
                .genero(StringUtils.trimToNull(dto.getGenero()))
                .edad(dto.getEdad())
                .telefono(StringUtils.trimToNull(dto.getTelefono()))
                .direccion(StringUtils.trimToNull(dto.getDireccion()))
                .password(dto.getPassword())
                .estado(dto.getEstado())
                .build();
    }

}

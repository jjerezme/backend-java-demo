package com.demo.backend.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.backend.dto.TestDTO;
import com.demo.backend.entity.TestEntity;
import com.demo.backend.exception.BusinessException;
import com.demo.backend.repository.TestRepository;
import com.demo.backend.services.ITestService;
import com.demo.backend.utils.Helpers;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestService implements ITestService {

    @Autowired
    private TestRepository testRepository;

    @Override
    public List<TestDTO> all() {
        Iterable<TestEntity> it = this.testRepository.findAll();
        List<TestDTO> result = new ArrayList<TestDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    @Override
    public TestDTO get(Long id) {
        Optional<TestEntity> findById = this.testRepository.findById(id);

        if (!findById.isPresent()) {
            log.debug("Not found {}", id);
            return null;
        }

        TestEntity ent = findById.get();
        log.debug("Found {}", ent);
        return toDTO(ent);
    }

    @Override
    public List<TestDTO> search(String value) {
        Iterable<TestEntity> it = this.testRepository.findByValueContainingIgnoreCase(value);
        List<TestDTO> result = new ArrayList<TestDTO>();
        it.forEach(i -> result.add(toDTO(i)));
        log.debug("Found {}", result);
        return result;
    }

    @Override
    public TestDTO add(TestDTO dto) {
        TestEntity entity = this.testRepository.save(toEntity(dto));
        log.debug("Insert {}", entity);
        return toDTO(entity);
    }

    @Override
    public TestDTO update(TestDTO dto) {
        boolean result = this.testRepository.existsById(dto.getId());

        if (!result) {
            throw new BusinessException("El registro no existe");
        }

        TestEntity entity = this.testRepository.save(toEntity(dto));
        log.debug("Update {}", entity);
        return toDTO(entity);
    }

    @Override
    public TestDTO patch(Long id, Map<String, Object> fields) {
        Optional<TestEntity> findById = this.testRepository.findById(id);

        if (!findById.isPresent()) {
            throw new BusinessException("El registro no existe");
        }

        TestEntity testEntity = findById.get();
        Helpers.patchFields(fields, testEntity);
        
        TestEntity entity = this.testRepository.save(testEntity);
        log.debug("Patch {}", entity);
        return toDTO(entity);
    }

    @Override
    public void delete(Long id) {
        boolean result = this.testRepository.existsById(id);

        if (!result) {
            throw new BusinessException("El registro no existe");
        }

        this.testRepository.deleteById(id);
        log.debug("Delete {}", id);
    }

    /**
     * Convert Entity to DTO
     * 
     * @param entity Entity
     * @return DTO
     */
    private TestDTO toDTO(TestEntity entity) {
        if (entity == null) {
            return null;
        }

        return TestDTO.builder()
                .id(entity.getId())
                .value(entity.getValue())
                .build();
    }

    /**
     * Convert DTO to Entity
     * 
     * @param dto DTO
     * @return Entity
     */
    private TestEntity toEntity(TestDTO dto) {
        if (dto == null) {
            return null;
        }

        return TestEntity.builder()
                .id(dto.getId())
                .value(StringUtils.trimToNull(dto.getValue()))
                .build();
    }

}

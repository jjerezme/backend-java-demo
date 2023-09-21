package com.demo.backend.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.demo.backend.dto.TestDTO;
import com.demo.backend.entity.TestEntity;
import com.demo.backend.repository.TestRepository;

@SpringBootTest
public class TestServiceTest {

    @Autowired
    private TestService testService;

    @MockBean
    private TestRepository testRepository;

    @BeforeEach
    void setup() {
        TestEntity test = TestEntity.builder()
                .id(1L)
                .value("Test")
                .build();

        Mockito.when(this.testRepository.findById(1L)).thenReturn(Optional.of(test));
    }

    @Test
    @DisplayName("Prueba la obtención de un registro")
    void testGet() {
        Long id = 1L;
        TestDTO result = this.testService.get(id);
        assertEquals(id, result.getId());
    }
}

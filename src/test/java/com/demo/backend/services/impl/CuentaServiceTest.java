package com.demo.backend.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.demo.backend.dto.CuentaDTO;
import com.demo.backend.entity.CuentaEntity;
import com.demo.backend.repository.CuentaRepository;

@SpringBootTest
public class CuentaServiceTest {

    @Autowired
    private CuentaService cuentaService;

    @MockBean
    private CuentaRepository cuentaRepository;

    @BeforeEach
    void setup() {
        CuentaEntity entity = CuentaEntity.builder()
                .id(1L)
                .numero("999000")
                .tipo("Ahorros")
                .saldo(1000)
                .estado(true)
                .build();

        Mockito.when(this.cuentaRepository.findById(1L)).thenReturn(Optional.of(entity));
    }

    @Test
    @DisplayName("Prueba la obtención de una cuenta")
    void testGet() {
        Long id = 1L;
        CuentaDTO result = this.cuentaService.get(id);
        assertEquals(id, result.getId());
        assertNotNull(result.getNumero());
        assertTrue(result.getSaldo() > 0);
    }

}

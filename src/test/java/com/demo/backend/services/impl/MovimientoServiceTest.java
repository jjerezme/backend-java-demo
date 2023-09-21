package com.demo.backend.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.demo.backend.dto.MovimientoDTO;
import com.demo.backend.entity.MovimientoEntity;
import com.demo.backend.repository.MovimientoRepository;

@SpringBootTest
public class MovimientoServiceTest {

    @Autowired
    private MovimientoService movimientoService;

    @MockBean
    private MovimientoRepository movimientoRepository;

    @BeforeEach
    void setup() {
        MovimientoEntity entity = MovimientoEntity.builder()
                .id(1L)
                .fecha(new Date())
                .saldo(3000d)
                .valor(100d)
                .build();

        Mockito.when(this.movimientoRepository.findById(1L)).thenReturn(Optional.of(entity));
    }

    @Test
    @DisplayName("Prueba la obtención de un movimiento")
    void testGet() {
        Long id = 1L;
        MovimientoDTO result = this.movimientoService.get(id);
        assertEquals(id, result.getId());
        assertNotNull(result.getFecha());
        assertTrue(result.getSaldo() > 0);
        assertTrue(result.getValor() > 0);
    }

}

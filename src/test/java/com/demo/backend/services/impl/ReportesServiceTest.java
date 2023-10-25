package com.demo.backend.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.demo.backend.dto.ReporteDTO;
import com.demo.backend.entity.ClienteEntity;
import com.demo.backend.entity.CuentaEntity;
import com.demo.backend.entity.MovimientoEntity;
import com.demo.backend.repository.CuentaRepository;
import com.demo.backend.repository.MovimientoRepository;

@SpringBootTest
public class ReportesServiceTest {

    @Autowired
    private ReporteService reporteService;

    @MockBean
    private CuentaRepository cuentaRepository;

    @MockBean
    private MovimientoRepository movimientoRepository;

    @BeforeEach
    void setup() {
        ClienteEntity clienteEntity = ClienteEntity.builder()
                .id(1L)
                .nombre("Juan Pablo")
                .dni("664178")
                .genero("M")
                .edad(36)
                .telefono("3153497998")
                .direccion("CRA10a#138-10")
                .password("12345")
                .estado(true)
                .build();

        CuentaEntity cuentaEntity = CuentaEntity.builder()
                .id(1L)
                .numero("999000")
                .tipo("Ahorros")
                .saldo(1000)
                .estado(true)
                .cliente(clienteEntity)
                .build();

        List<MovimientoEntity> list = new ArrayList<>();
        list.add(MovimientoEntity.builder()
                .id(1L)
                .fecha(new Date())
                .saldo(3000d)
                .valor(100d)
                .cuenta(cuentaEntity)
                .build());
        list.add(MovimientoEntity.builder()
                .id(2L)
                .fecha(new Date())
                .saldo(2900d)
                .valor(100d)
                .cuenta(cuentaEntity)
                .build());

        Mockito.when(this.cuentaRepository.findByNumero("999000")).thenReturn(Optional.of(cuentaEntity));
        Mockito.when(this.movimientoRepository.findByCuenta(cuentaEntity)).thenReturn(list);
    }

    @Test
    @DisplayName("Prueba la obtención de un movimiento")
    void testGet() {
        String num = "999000";
        List<ReporteDTO> result = this.reporteService.getByCuentas(num);
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

}

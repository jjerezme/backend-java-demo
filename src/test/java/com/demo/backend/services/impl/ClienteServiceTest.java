package com.demo.backend.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.demo.backend.dto.ClienteDTO;
import com.demo.backend.entity.ClienteEntity;
import com.demo.backend.repository.ClienteRepository;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setup() {
        ClienteEntity entity = ClienteEntity.builder()
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

        Mockito.when(this.clienteRepository.findById(1L)).thenReturn(Optional.of(entity));
    }

    @Test
    @DisplayName("Prueba la obtención de un cliente")
    void testGet() {
        Long id = 1L;
        ClienteDTO result = this.clienteService.get(id);
        assertEquals(id, result.getId());
        assertNotNull(result.getNombre());
        assertNotNull(result.getDni());
        assertNotNull(result.getTelefono());
        assertNotNull(result.getDireccion());
    }

}

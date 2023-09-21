package com.demo.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.demo.backend.constants.ResponseStatus;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CuentasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String PATH = "/cuentas";

    @Test
    void testGetAllSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status")
                        .value(ResponseStatus.SUCCESS));
    }

    @Test
    void testGetByIdSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PATH + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status")
                        .value(ResponseStatus.SUCCESS));
    }

    @Test
    void testGetByIdZeroError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PATH + "/0")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status")
                        .value(ResponseStatus.ERROR));
    }

    @Test
    void testGetByIdError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PATH + "/foo")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status")
                        .value(ResponseStatus.ERROR));
    }
}

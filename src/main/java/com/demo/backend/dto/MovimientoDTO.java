package com.demo.backend.dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDTO {
    private Long id;
    private Date fecha;

    @NotNull
    private Double valor;

    @NotNull
    private Double saldo;

}

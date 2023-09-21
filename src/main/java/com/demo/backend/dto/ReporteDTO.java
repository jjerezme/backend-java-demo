package com.demo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReporteDTO {
    private String fecha;
    private String cliente;
    private String cuenta;
    private String tipo;
    private Double saldoInicial;
    private Double movimiento;
    private Double saldoDisponible;
}

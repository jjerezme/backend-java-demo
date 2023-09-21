package com.demo.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDTO {
    private Long id;

    @NotBlank
    @Size(min = 5, max = 30)
    private String numero;

    @NotBlank
    @Size(min = 1, max = 10)
    private String tipo;

    @NotNull
    private Double saldo;
    
    private Boolean estado;
}

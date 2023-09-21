package com.demo.backend.dto;

import jakarta.validation.constraints.Min;
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
public class CuentaCreateDTO {
    @NotNull
    private CuentaDTO cuenta;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 30)
    private String dni;
}

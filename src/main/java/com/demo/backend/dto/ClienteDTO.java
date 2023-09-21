package com.demo.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;

    @NotBlank
    @Size(min = 5, max = 20)
    private String dni;

    @NotBlank
    @Size(min = 5, max = 255)
    private String nombre;

    @NotBlank
    @Size(min = 1, max = 1)
    private String genero;

    @Min(1)
    @Max(100)
    private Integer edad;

    @NotBlank
    @Size(min = 10, max = 255)
    private String direccion;

    @NotBlank
    @Size(min = 3, max = 20)
    private String telefono;

    @NotBlank
    @Size(min = 3, max = 255)
    private String password;

    private Boolean estado;
}

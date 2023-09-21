package com.demo.backend.services;

import java.util.List;
import java.util.Map;

import com.demo.backend.dto.CuentaDTO;

public interface ICuentaService {
    /**
     * Obtiene todas las cuentas
     * 
     * @return Lista de cuentas
     */
    List<CuentaDTO> all();

    /**
     * Obtiene una cuenta
     * 
     * @param id ID de la cuenta
     * @return Cuenta
     */
    CuentaDTO get(Long id);

    /**
     * Busca una cuenta por el numero
     * 
     * @param value Numero de la cuenta
     * @return Cuenta
     */
    CuentaDTO findByNumero(String value);

    /**
     * Inserta una nueva cuenta
     * 
     * @param dto  Datos de la cuenta
     * @param name DNI del cliente
     * @return Cuenta
     */
    CuentaDTO add(CuentaDTO dto, String dni);

    /**
     * Actualiza una cuenta
     * 
     * @param dto Datos de la cuenta
     * @return Cuenta
     */
    CuentaDTO update(CuentaDTO dto);

    /**
     * Actualiza una cuenta
     * 
     * @param id     ID de la cuenta
     * @param fields Campos a actualizar
     * @return Cuenta
     */
    CuentaDTO patch(Long id, Map<String, Object> fields);

    /**
     * Elimina una cuenta
     * 
     * @param id ID de la cuenta
     */
    void delete(Long id);

}

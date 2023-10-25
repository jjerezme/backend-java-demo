package com.demo.backend.services;

import java.util.List;
import java.util.Map;

import com.demo.backend.dto.MovimientoDTO;

public interface IMovimientoService {
    /**
     * Obtiene todos los movimientos
     * 
     * @return Lista de movimientos
     */
    List<MovimientoDTO> all();

    /**
     * Obtiene un movimiento
     * 
     * @param id ID del movimiento
     * @return Movimiento
     */
    MovimientoDTO get(Long id);

    /**
     * Busca los movimientos de una cuenta
     * 
     * @param num Numero de la cuenta
     * @return Lista de movimientos
     */
    List<MovimientoDTO> findByCuenta(String num);

    /**
     * Inserta un movimiento
     * 
     * @param valor  Valor del movimiento. Positivo para bonos y negativo para
     *               retiros
     * @param cuenta Numero de la cuenta
     * @return Movimientos
     */
    MovimientoDTO add(Double valor, String cuenta);

    /**
     * Actualiza un movimiento
     * 
     * @param dto Datos del movimiento
     * @return Movimiento
     */
    MovimientoDTO update(MovimientoDTO dto);

    /**
     * Actualiza un movimiento
     * 
     * @param id     ID del movimiento
     * @param fields Campos a actualizar
     * @return Movimiento
     */
    MovimientoDTO patch(Long id, Map<String, Object> fields);

    /**
     * Elimina un movimiento
     * 
     * @param id ID del movimiento
     */
    void delete(Long id);
}

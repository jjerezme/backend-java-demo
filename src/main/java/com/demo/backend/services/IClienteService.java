package com.demo.backend.services;

import java.util.List;
import java.util.Map;

import com.demo.backend.dto.ClienteDTO;

public interface IClienteService {

    /**
     * Obtiene todos los clientes
     * 
     * @return Lista de clientes
     */
    List<ClienteDTO> all();

    /**
     * Obtiene un cliente
     * 
     * @param id ID del cliente
     * @return Cliente
     */
    ClienteDTO get(Long id);

    /**
     * Busca un cliente por su DNI
     * 
     * @param value DNI del cliente
     * @return Cliente
     */
    ClienteDTO findByDni(String value);

    /**
     * Busca clientes por su nombre
     * 
     * @param value Nombre del cliente
     * @return Lista de clientes
     */
    List<ClienteDTO> findByNombre(String value);

    /**
     * Inserta un nuevo cliente
     * 
     * @param dto Datos del cliente
     * @return Cliente
     */
    ClienteDTO add(ClienteDTO dto);

    /**
     * Actualiza un cliente
     * 
     * @param dto Datos del cliente
     * @return Cliente
     */
    ClienteDTO update(ClienteDTO dto);

    /**
     * Actualiza un cliente
     * 
     * @param id     ID del cliente
     * @param fields Campos a actualizar
     * @return Cliente
     */
    ClienteDTO patch(Long id, Map<String, Object> fields);

    /**
     * Elimina un cliente
     * 
     * @param id ID del cliente
     */
    void delete(Long id);
}

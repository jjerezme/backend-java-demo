package com.demo.backend.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.backend.dto.ClienteDTO;
import com.demo.backend.dto.ResponseDTO;
import com.demo.backend.services.IClienteService;
import com.demo.backend.utils.Validation;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClientesController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping
	public ResponseEntity<ResponseDTO> all() {

		log.info("Request all");
		return ResponseEntity.ok(ResponseDTO.success(this.clienteService.all()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO> get(@PathVariable(name = "id") Long id) {

		log.info("Request get {}", id);
		Validation.greaterThanZero(id);
		return ResponseEntity.ok(ResponseDTO.success(this.clienteService.get(id)));
	}

	@GetMapping("/dni/{dni}")
	public ResponseEntity<ResponseDTO> dni(@PathVariable(name = "dni") String value) {

		log.info("Request dni {}", value);
		value = StringUtils.trimToNull(value);
		Validation.notEmpty(value);
		Validation.min(value, 5);
		return ResponseEntity.ok(ResponseDTO.success(this.clienteService.findByDni(value)));
	}

	@GetMapping("/find/")
	public ResponseEntity<ResponseDTO> find(@RequestParam(name = "q") String query) {

		log.info("Request find {}", query);
		query = StringUtils.trimToNull(query);
		Validation.notEmpty(query);
		Validation.min(query, 3);
		return ResponseEntity.ok(ResponseDTO.success(this.clienteService.findByNombre(query)));
	}

	@PostMapping
	public ResponseEntity<ResponseDTO> add(@RequestBody @Valid ClienteDTO dto) {

		log.info("Request add {}", dto);
		return ResponseEntity.ok(ResponseDTO.success(this.clienteService.add(dto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO> update(
			@RequestBody @Valid ClienteDTO dto,
			@PathVariable(name = "id") Long id) {

		log.info("Request update {}, {}", id, dto);
		Validation.greaterThanZero(id);
		dto.setId(id);
		return ResponseEntity.ok(ResponseDTO.success(this.clienteService.update(dto)));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ResponseDTO> patch(
			@PathVariable(name = "id") Long id,
			@RequestBody Map<String, Object> fields) {

		log.info("Request patch {}, {}", id, fields);
		Validation.greaterThanZero(id);
		return ResponseEntity.ok(ResponseDTO.success(this.clienteService.patch(id, fields)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable(name = "id") Long id) {

		log.info("Request delete {}", id);
		Validation.greaterThanZero(id);
		this.clienteService.delete(id);
		return ResponseEntity.ok(ResponseDTO.success(true));
	}

}

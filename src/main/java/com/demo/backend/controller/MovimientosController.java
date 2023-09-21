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
import org.springframework.web.bind.annotation.RestController;

import com.demo.backend.dto.MovimientoCreateDTO;
import com.demo.backend.dto.MovimientoDTO;
import com.demo.backend.dto.ResponseDTO;
import com.demo.backend.services.IMovimientoService;
import com.demo.backend.utils.Validation;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/movimientos")
public class MovimientosController {

	@Autowired
	private IMovimientoService movimientoService;

	@GetMapping
	public ResponseEntity<ResponseDTO> all() {
		
		log.info("Request all");
		return ResponseEntity.ok(ResponseDTO.success(this.movimientoService.all()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO> get(@PathVariable(name = "id") Long id) {

		log.info("Request get {}", id);
		Validation.greaterThanZero(id);
		return ResponseEntity.ok(ResponseDTO.success(this.movimientoService.get(id)));
	}

	@GetMapping("/num/{num}")
	public ResponseEntity<ResponseDTO> num(@PathVariable(name = "num") String num) {

		log.info("Request num {}", num);
		num = StringUtils.trimToNull(num);
		Validation.notEmpty(num);
		Validation.size(num, 5, 30);
		return ResponseEntity.ok(ResponseDTO.success(this.movimientoService.findByCuenta(num)));
	}

	@PostMapping
	public ResponseEntity<ResponseDTO> add(@RequestBody @Valid MovimientoCreateDTO dto) {

		log.info("Request add {}", dto);
		String cuenta = StringUtils.trimToNull(dto.getCuenta());
		Validation.notEmpty(cuenta);
		Validation.notNaN(dto.getValor());
		return ResponseEntity.ok(ResponseDTO.success(this.movimientoService.add(dto.getValor(), cuenta)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO> update(
			@RequestBody @Valid MovimientoDTO dto,
			@PathVariable(name = "id") Long id) {

		log.info("Request update {}, {}", id, dto);
		Validation.greaterThanZero(id);
		dto.setId(id);
		return ResponseEntity.ok(ResponseDTO.success(this.movimientoService.update(dto)));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ResponseDTO> patch(
			@PathVariable(name = "id") Long id,
			@RequestBody Map<String, Object> fields) {

		log.info("Request patch {}, {}", id, fields);
		Validation.greaterThanZero(id);
		return ResponseEntity.ok(ResponseDTO.success(this.movimientoService.patch(id, fields)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable(name = "id") Long id) {

		log.info("Request delete '{}'", id);
		Validation.greaterThanZero(id);
		this.movimientoService.delete(id);
		return ResponseEntity.ok(ResponseDTO.success());
	}
}

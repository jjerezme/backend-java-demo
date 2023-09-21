package com.demo.backend.controller;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.backend.dto.ResponseDTO;
import com.demo.backend.services.IReportesService;
import com.demo.backend.utils.Validation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/reportes")
public class ReportesController {

	@Autowired
	private IReportesService reporteService;

	@GetMapping
	public ResponseEntity<ResponseDTO> all() {

		log.info("Request all");
		return ResponseEntity.ok(ResponseDTO.success(this.reporteService.all()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO> all(@PathVariable(name = "id") String cuenta,
			@RequestParam(name = "fechaDesde", required = false) String fechaDesde,
			@RequestParam(name = "fechaHasta", required = false) String fechaHasta) {

		log.info("Request all {}, {}, {}", cuenta, fechaDesde, fechaHasta);

		cuenta = StringUtils.trimToNull(cuenta);
		Validation.notEmpty(cuenta);

		if (fechaDesde == null && fechaHasta == null) {
			return ResponseEntity.ok(ResponseDTO.success(this.reporteService.getByCuentas(cuenta)));
		}

		Date dateFrom = Validation.isDate(fechaDesde, "dd/MM/yyyy");
		Date dateTo = Validation.isDate(fechaHasta, "dd/MM/yyyy");;
		
		return ResponseEntity.ok(ResponseDTO.success(this.reporteService.getByCuentas(cuenta, dateFrom, dateTo)));
	}

}

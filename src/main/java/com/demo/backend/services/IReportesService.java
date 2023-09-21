package com.demo.backend.services;

import java.util.Date;
import java.util.List;

import com.demo.backend.dto.ReporteDTO;

public interface IReportesService {
    List<ReporteDTO> all();

    List<ReporteDTO> getByCuentas(String cuenta);

    List<ReporteDTO> getByCuentas(String cuenta, Date desde, Date hasta);
}

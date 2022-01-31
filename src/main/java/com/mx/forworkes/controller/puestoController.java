package com.mx.forworkes.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.forworkes.dto.puestoDto;
import com.mx.forworkes.service.IpuestoService;

/**
 * @author IgnacioCisnerosJuare
 *
 */

@RestController
@RequestMapping(value = "/puesto")
public class puestoController {
	
	private static final Logger LOGGER = LogManager.getLogger(puestoController.class);
	
	private IpuestoService puestoService;

	/**
	 * @return Renorna el listado de puestos existentes
	 */
	@GetMapping(value = "/mostrar")
	public List<puestoDto> muestraPuestos() {
		LOGGER.info("::CONSUMIENDO SERVICIO PARA EL LISTADO DE LOS PUESTOS EXISTENTES::");
		try {
			return puestoService.extrerPuestos();
		} catch (Exception e) {

		}

		return null;
	}
}

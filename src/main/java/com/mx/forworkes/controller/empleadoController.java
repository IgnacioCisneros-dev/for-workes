package com.mx.forworkes.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.forworkes.dto.empleadoDto;

/**
 * @author IgnacioCisnerosJuare
 * 
 */

@RestController
@RequestMapping(value = "empleado")
public class empleadoController {

	private static final Logger LOGGER = LogManager.getLogger(empleadoController.class);

	/**
	 * @return Lista de empleados existentes
	 */
	public List<empleadoDto> muetraEmpleados() {
		LOGGER.info("::CONSUMIENDO SERVICIO PARA EL LISTADO DE LOS PUESTOS EXISTENTES::");
		return null;
	}

}

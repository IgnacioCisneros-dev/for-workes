package com.mx.forworkes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.forworkes.dto.empleadoDto;
import com.mx.forworkes.exception.ExceptionGlobal;
import com.mx.forworkes.service.IEmpleadoService;

/**
 * @author IgnacioCisnerosJuare
 * 
 */

@RestController
@RequestMapping(value = "empleado")
public class empleadoController {

	private static final Logger LOGGER = LogManager.getLogger(empleadoController.class);
	@Autowired
	private IEmpleadoService empleadoService;

	/**
	 * @return Lista de empleados existentes
	 * @throws IOException
	 */
	@GetMapping(value = "obtener")
	public List<empleadoDto> muetraEmpleados(HttpServletResponse response) throws IOException {
		LOGGER.info("::CONSULTANDO EL LISTADO DE EMPLEADOS EXISTENTES::");
		try {
			return empleadoService.obtenerEmpleados();
		} catch (ExceptionGlobal e) {
			int code = e.getCodeError();
			LOGGER.error("::ERROR: " + e.getMessage() + " ::");
			((HttpServletResponse) response).sendError(code, e.getMessage());
			return null;
		}
	}

}

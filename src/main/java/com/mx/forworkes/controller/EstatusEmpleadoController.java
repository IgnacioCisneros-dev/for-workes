package com.mx.forworkes.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.forworkes.dto.EstatusEmpleadoDto;
import com.mx.forworkes.exception.ExceptionGlobal;
import com.mx.forworkes.service.IEstatusEmpleadoService;

/**
 * @author IgnacioCisnerosJuare
 *
 */

@RestController
@RequestMapping(value = "/estatus-empleado")
public class EstatusEmpleadoController {

	@Autowired
	private IEstatusEmpleadoService estatusEmpleado;
	/**
	 * Servicio que realiza el calculo estatus por empleado
	 * 
	 * @param response
	 * @param empleado_id
	 * @return
	 * @throws IOException 
	 */
	@GetMapping(value = "/buscar-estatus/{empleado_id}")
	public EstatusEmpleadoDto buscarEstatusEmpleado(HttpServletResponse response, @PathVariable long empleado_id) throws IOException {
		try {
			return estatusEmpleado.buscarEstatus(empleado_id);
		} catch (ExceptionGlobal e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		}
	}
}

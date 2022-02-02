package com.mx.forworkes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.forworkes.dto.InformacionPersonalDto;
import com.mx.forworkes.exception.ExceptionGlobal;
import com.mx.forworkes.service.IInformacionPersonalService;

/**
 * @author IgnacioCisnerosJuare
 *
 */
@RestController
@RequestMapping(value = "/informacion-personal")
public class InformacionPersonalController {

	@Autowired
	private IInformacionPersonalService informacionService;

	/**
	 * @return Servicio encargado de registrar informacion personal del empleado
	 * @throws IOException
	 */
	@PostMapping(value = "/guardar")
	public String guardarInformacion(HttpServletResponse response,
			@RequestBody InformacionPersonalDto informacionPersonal) throws IOException {
		try {
			return informacionService.guardarInformacionPersonal(informacionPersonal);
		} catch (ExceptionGlobal e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		}
	}

	/**
	 * @param response
	 * @param id para buscar la informacion
	 * @return la informacion del empleado
	 * @throws IOException 
	 */
	@GetMapping(value = "/buscar/{id}")
	public List<InformacionPersonalDto> buscarInformacion(HttpServletResponse response, @PathVariable long id) throws IOException {

		try {
			return informacionService.buscarInformacionPersonal(id);
		} catch (ExceptionGlobal e) {
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
			return null;
		}
	}
}

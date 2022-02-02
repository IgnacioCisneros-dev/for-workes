package com.mx.forworkes.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mx.forworkes.dto.puestoDto;
import com.mx.forworkes.exception.PuestoException;
import com.mx.forworkes.service.IpuestoService;

/**
 * @author IgnacioCisnerosJuare
 *
 */

@RestController
@RequestMapping(value = "/puesto")
public class puestoController {

	private static final Logger LOGGER = LogManager.getLogger(puestoController.class);

	@Autowired
	private IpuestoService puestoService;

	/**
	 * @return Renorna el listado de puestos existentes
	 * @throws IOException
	 */
	@GetMapping(value = "/mostrar")
	public List<puestoDto> muestraPuestos(HttpServletResponse response) throws IOException {
		LOGGER.info("::CONSUMIENDO SERVICIO PARA EL LISTADO DE LOS PUESTOS EXISTENTES::");
		try {
			return puestoService.extrerPuestos();
		} catch (PuestoException e) {
			int code = e.getCodeError();
			LOGGER.error("::ERROR: " + e.getMessage() + " ::");
			((HttpServletResponse) response).sendError(code, e.getMessage());
			return null;
		}
	}

	/**
	 * @param response
	 * @param puesto_id
	 * @return Puesto buscado por id
	 * @throws IOException
	 */
	@GetMapping(value = "/buscar/{puesto_id}")
	public List<puestoDto> buscarPuesto(HttpServletResponse response, @PathVariable int puesto_id) throws IOException {
		LOGGER.info("::BUSCANDO PUESTO POR ID::");
		try {
			return puestoService.buscarPuesto(puesto_id);
		} catch (PuestoException e) {
			int code = e.getCodeError();
			LOGGER.error("::ERROR: " + e.getMessage() + " ::");
			((HttpServletResponse) response).sendError(code, e.getMessage());
			return null;
		}
	}

	/**
	 * @param response
	 * @param puesto
	 * @throws IOException
	 */
	@PostMapping(value = "/guardar")
	public void guardarPuesto(HttpServletResponse response, @RequestBody puestoDto puesto) throws IOException {
		LOGGER.info("::GUARDANDO NUEVO PUESTO EN BASE DE DATOS::");
		try {
			puestoService.guardarPuesto(puesto);
		} catch (PuestoException e) {
			LOGGER.error("::ERROR: " + e.getMessage() + " ::");
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		}

	}

	/**
	 * @param response
	 * @param puesto_id
	 * @return confirmacion si es que se pudo realizar la modificacion con exito
	 */
	@PutMapping(value = "/modificar/{puesto_id}")
	public String modificarPuesto(HttpServletResponse response, @PathVariable int puesto_id,
			@RequestBody puestoDto puesto) throws IOException {
		LOGGER.info("::MODIFICACION DE PUESTO::");
		try {
			return puestoService.modificarPuesto(puesto_id, puesto);
		} catch (PuestoException e) {
			LOGGER.error("::ERROR: " + e.getMessage() + " ::");
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		}
		return null;
	}

	/**
	 * @param response
	 * @param puesto_id
	 * @return elimina un puesto(baja logica)
	 * @throws IOException
	 */
	@DeleteMapping(value = "/eliminar/{puesto_id}")
	public String eliminaPuesto(HttpServletResponse response, @PathVariable int puesto_id) throws IOException {
		LOGGER.info("::ELIMINACION DE PUESTO::");
		try {
			return puestoService.eliminarpuesto(puesto_id);
		} catch (PuestoException e) {
			LOGGER.error("::ERROR: " + e.getMessage() + " ::");
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		}
		return null;
	}

	/**
	 * @param response (exporta los puestos existentes a excel)
	 * @throws IOException
	 */
	@GetMapping(value = "/exportar-excel")
	public void exportarDatosAExcel(HttpServletResponse response) throws IOException {
		LOGGER.info("::EXPORTANDO LOS PUESTOS A EXCEL::");
		try {
			puestoService.exportarPuestosAExcel(response);
		} catch (PuestoException e) {
			LOGGER.error("::ERROR AL GENERARL EL REPORTE ===> " + e.getMessage() + " ::");
			((HttpServletResponse) response).sendError(e.getCodeError(), e.getMessage());
		}
	}

}

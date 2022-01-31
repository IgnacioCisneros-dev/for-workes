package com.mx.forworkes.service;

import java.util.List;

import com.mx.forworkes.dto.puestoDto;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public interface IpuestoService {

	/**
	 * @return la lista de puesto existentes.
	 */
	public List<puestoDto> extrerPuestos();
}

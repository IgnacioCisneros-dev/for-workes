package com.mx.forworkes.service;

import java.util.List;

import com.mx.forworkes.dto.puestoDto;
import com.mx.forworkes.exception.PuestoException;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public interface IpuestoService {

	/**
	 * @return la lista de puesto existentes.
	 */
	public List<puestoDto> extrerPuestos() throws PuestoException;

	/**
	 * @param puesto_id
	 * @return puesto filtrado por el id
	 * @throws PuestoException
	 */
	public List<puestoDto> buscarPuesto(int puesto_id) throws PuestoException;

	/**
	 * @param Objeto de puesto a guardar
	 */
	public void guardarPuesto(puestoDto puesto) throws PuestoException;

	/**
	 * @param puesto_id
	 * @param puesto
	 * @return mensaje de confirmacion en caso de modificar correctamente
	 * @throws PuestoException
	 */
	public String modificarPuesto(int puesto_id, puestoDto puesto) throws PuestoException;

	/**
	 * @param puesto_id
	 * @return mensaje de confirmacion en caso de que se elimine correctamente el
	 *         puesto
	 * @throws PuestoException
	 */
	public String eliminarpuesto(int puesto_id) throws PuestoException;
}

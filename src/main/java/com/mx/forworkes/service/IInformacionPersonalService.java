package com.mx.forworkes.service;

import java.util.List;

import com.mx.forworkes.dto.InformacionPersonalDto;
import com.mx.forworkes.exception.ExceptionGlobal;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public interface IInformacionPersonalService {

	/**
	 * @param informacion para guardar
	 * @return mensaje de confirmacion en caso de guardado correcto.
	 * @throws ExceptionGlobal
	 */
	public String guardarInformacionPersonal(InformacionPersonalDto informacion) throws ExceptionGlobal;

	/**
	 * @param informacion_personal_id
	 * @return la informacion personal del empleado
	 */
	public List<InformacionPersonalDto> buscarInformacionPersonal(long informacion_personal_id) throws ExceptionGlobal;
}

package com.mx.forworkes.service;

import com.mx.forworkes.dto.EstatusEmpleadoDto;
import com.mx.forworkes.exception.ExceptionGlobal;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public interface IEstatusEmpleadoService {

	/**
	 * @return las propiedades del objeto EstatusEmpleadoDto
	 * @throws ExceptionGlobal
	 */
	public EstatusEmpleadoDto buscarEstatus(long empleado_id) throws ExceptionGlobal;
}

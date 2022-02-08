package com.mx.forworkes.service;

import java.util.List;
import com.mx.forworkes.dto.empleadoDto;
import com.mx.forworkes.exception.ExceptionGlobal;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public interface IEmpleadoService {

    public List<empleadoDto> obtenerEmpleados() throws ExceptionGlobal;

}

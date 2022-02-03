package com.mx.forworkes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mx.forworkes.entity.EmpleadoEntity;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public interface IEmpleadoRepository extends JpaRepository<EmpleadoEntity, Long> {

	/**
	 * @param empleado_id para realizar la busqueda en BD
	 * @return los datos del empleado
	 */
	EmpleadoEntity findByEmpleadoId(long empleado_id);

}

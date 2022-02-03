package com.mx.forworkes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mx.forworkes.entity.EstatusEmpleadoEntity;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public interface IEstatusEmpleadoRepository extends JpaRepository<EstatusEmpleadoEntity, Long> {

	/**
	 * @param estatus_empleado_id
	 * @return las propiedades del estatus del empleado
	 */
	EstatusEmpleadoEntity findByEstatusEmpleadoId(Long estatus_empleado_id);
}

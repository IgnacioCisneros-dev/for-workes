package com.mx.forworkes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mx.forworkes.entity.PuestoEntity;

/**
 * @author IgnacioCisnerosJuare
 *
 */
@Repository
public interface IPuestoRepository extends JpaRepository<PuestoEntity, Long> {

	/**
	 * @return retorna la lista de puestos que esten activos
	 */
	public List<PuestoEntity> findByActivoTrue();
	
	/**
	 * @param puesto_id
	 * @return Objeto de la entidad puesto
	 */
	PuestoEntity findById(long puesto_id);
}

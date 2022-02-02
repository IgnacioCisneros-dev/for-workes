package com.mx.forworkes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mx.forworkes.entity.InformacionGeneralEntity;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public interface IInformacionPersonalRepository extends JpaRepository<InformacionGeneralEntity, Long> {

	/**
	 * @param cuenta
	 * @return Busca en basa de datos la informacion personal por medio del numero
	 *         de cuenta
	 */
	InformacionGeneralEntity findByNumeroCuenta(String cuenta);

	/**
	 * @param informacion_personal_id
	 * @return Busca en base de datos la informacion personal por el id recibido
	 */
	InformacionGeneralEntity findByInformacionPersonalId(long informacion_personal_id);

}

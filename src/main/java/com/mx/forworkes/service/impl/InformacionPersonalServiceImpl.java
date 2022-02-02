package com.mx.forworkes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mx.forworkes.dto.InformacionPersonalDto;
import com.mx.forworkes.entity.InformacionGeneralEntity;
import com.mx.forworkes.exception.ExceptionGlobal;
import com.mx.forworkes.repository.IInformacionPersonalRepository;
import com.mx.forworkes.service.IInformacionPersonalService;
import com.mx.forworkes.utils.Encriptacion;

/**
 * @author IgnacioCisnerosJuare
 *
 */
@Service
public class InformacionPersonalServiceImpl implements IInformacionPersonalService {

	private static final Logger LOGGER = LogManager.getLogger(InformacionPersonalServiceImpl.class);

	@Autowired
	private IInformacionPersonalRepository informacionRepository;

	Encriptacion encriptacion = new Encriptacion();

	/**
	 * Metodo encargado de hacer el guardar de la informacion personal del empleado.
	 */
	@Override
	public String guardarInformacionPersonal(InformacionPersonalDto informacion) throws ExceptionGlobal {

		InformacionGeneralEntity informacionAGuardar = new InformacionGeneralEntity();

		try {
			// SE VALIDA QUE EL NUMERO DE CUENTA NO EXISTA EN LA BD
			InformacionGeneralEntity info = informacionRepository.findByNumeroCuenta(informacion.getNumeroCuenta());
			if (info != null) {
				throw new ExceptionGlobal("El numero de cuenta ingresado ya se encuentra registrado.", 409);
			}
			LOGGER.error("::GUARDANDO INFORMACION PERSONAL EN BASE DE DATOS::");
			informacionAGuardar.setNumeroCuenta(encriptacion.codificar(informacion.getNumeroCuenta().toUpperCase()));
			informacionAGuardar
					.setClaveInterbancaria(encriptacion.codificar(informacion.getClaveInterbancaria().toUpperCase()));
			informacionAGuardar.setNss(informacion.getNss().toUpperCase());
			informacionAGuardar.setNombreFamiliarEmergencia(informacion.getNombreFamiliarEmergencia().toUpperCase());
			informacionAGuardar.setNumeroFamiliarEmergencia(informacion.getNumeroFamiliarEmergencia());
			informacionAGuardar.setTipoSangre(informacion.getTipoSangre().toUpperCase());
			informacionAGuardar.setPadecimientosMedicos(informacion.getPadecimientosMedicos().toUpperCase());
			informacionRepository.save(informacionAGuardar);
			return "Informacion guardada correctamente.";
		} catch (Exception e) {
			LOGGER.error("::ERROR AL GUARDAR LA INFORMACION:: " + e.getMessage());
			throw new ExceptionGlobal(
					"Ocurrio un error al intentar guardar la informacion personal, favor de intentar nuevamente", 409);
		}
	}

	/**
	 * Metodo encargado de buscar la informacion personal del empleado, por mediob
	 * del id que recibe
	 * @throws ExceptionGlobal 
	 */
	@Override
	public List<InformacionPersonalDto> buscarInformacionPersonal(long informacion_personal_id) throws ExceptionGlobal {
		List<InformacionPersonalDto> listaInformacion = new ArrayList<>();
		try {
			InformacionGeneralEntity informacion = informacionRepository.findByInformacionPersonalId(informacion_personal_id);
			if(informacion != null) {
				InformacionPersonalDto informacionDto = new InformacionPersonalDto();
				informacionDto.setInformacionPersonalId(informacion.getInformacionPersonalId());
				informacionDto.setNumeroCuenta(encriptacion.decodificar(informacion.getNumeroCuenta()));
				informacionDto.setClaveInterbancaria(encriptacion.decodificar(informacion.getClaveInterbancaria()));
				informacionDto.setNss(informacion.getNss());
				informacionDto.setNombreFamiliarEmergencia(informacion.getNombreFamiliarEmergencia());
				informacionDto.setNumeroFamiliarEmergencia(informacion.getNumeroFamiliarEmergencia());
				informacionDto.setTipoSangre(informacion.getTipoSangre());
				informacionDto.setPadecimientosMedicos(informacion.getPadecimientosMedicos());				
				listaInformacion.add(informacionDto);
				return listaInformacion;
			}
			
			
		} catch (Exception e) {
			throw new ExceptionGlobal("Ocurrio un error al buscar la informacion, favor de intentar nuevamente.", 409);
		}
		
		
		return null;
	}

}

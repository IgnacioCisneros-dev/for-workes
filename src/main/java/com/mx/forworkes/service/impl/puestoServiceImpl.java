package com.mx.forworkes.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mx.forworkes.dto.puestoDto;
import com.mx.forworkes.entity.PuestoEntity;
import com.mx.forworkes.exception.PuestoException;
import com.mx.forworkes.repository.IPuestoRepository;
import com.mx.forworkes.service.IpuestoService;

/**
 * @author IgnacioCisnerosJuare
 *
 */

@Service
public class puestoServiceImpl implements IpuestoService {

	private static final Logger LOGGER = LogManager.getLogger(puestoServiceImpl.class);

	@Autowired
	private IPuestoRepository puestoRepository;

	/**
	 * Metodo encargado de extraer el listado de los puestos existentes
	 * 
	 * @throws PuestoException
	 */
	@Override
	public List<puestoDto> extrerPuestos() throws PuestoException {

		List<puestoDto> listaPuestosDTO = new ArrayList<>();

		try {
			List<PuestoEntity> listaPuestos = puestoRepository.findByActivoTrue();

			if (listaPuestos.size() > 0) {
				LOGGER.info("::NUMERO DE PUESTOS ENCONTRADOS EN LA BASE DE DATOS: " + listaPuestos.size() + " ::");
				for (PuestoEntity elemento : listaPuestos) {

					puestoDto puesto = new puestoDto();
					puesto.setPuesto_id(elemento.getPuestoId());
					puesto.setDescripcion(elemento.getDescripcion());
					listaPuestosDTO.add(puesto);

				}
				return listaPuestosDTO;
			} else {
				LOGGER.info("::NO SE ENCONTRARON PUESTOS EN LA BASE DE DATOS::");
				return null;
			}

		} catch (Exception e) {
			throw new PuestoException("Ocurrio un error al consultar los puesto, por favor intente mas tarde.", 404);
		}
	}

	/**
	 * Metodo que realiza el guardado de los puestos.
	 */
	@Override
	public void guardarPuesto(puestoDto puesto) throws PuestoException {

		if (puesto != null) {
			try {
				PuestoEntity nuevoPuesto = new PuestoEntity();
				nuevoPuesto.setDescripcion(puesto.getDescripcion());
				puestoRepository.save(nuevoPuesto);
			} catch (Exception e) {
				throw new PuestoException("Ocurrio un error al registrar nuevo puesto, Comuníquese con soporte.", 500);
			}
		} else {
			throw new PuestoException("Los datos ingresados para guardar no son correctos, intente nuevamente.", 409);
		}
	}

	/**
	 * Metodo encargado de la logica de negocio para guardar un nuevo puesto.
	 */
	@Override
	public String modificarPuesto(int puesto_id, puestoDto puesto) throws PuestoException {

		if (puesto_id > 0) {
			try {
				PuestoEntity puestoEntity = puestoRepository.findById(puesto_id);
				if (puestoEntity == null) {
					throw new PuestoException(
							"No se encontro puesto con el No.: " + puesto_id + " para modificar, intertar nuevamente.",
							409);
				}
				puestoEntity.setDescripcion(puesto.getDescripcion());
				puestoRepository.save(puestoEntity);
				return "Puesto modificado correctamente.";
			} catch (Exception e) {
				throw new PuestoException("Ocurrio un error al modificar el puesto con id " + puesto_id
						+ " favor de comunicarse con soporte.", 500);
			}
		}
		return null;
	}

	/**
	 * Metodo encargado de la logica de negocio para eliminar el puesto (baja
	 * logica).
	 */
	@Override
	public String eliminarpuesto(int puesto_id) throws PuestoException {

		if (puesto_id > 0) {
			try {
				PuestoEntity puesto = puestoRepository.findById(puesto_id);
				if (puesto.getActivo().booleanValue() == false) {
					return "El puesto con id: " + puesto_id + " ya se encuentra dado de baja.";
				} else {
					puesto.setActivo(false);
					puestoRepository.save(puesto);
					return "El puesto con id: " + puesto_id + " se dio de baja correctamente.";
				}
			} catch (Exception e) {
				throw new PuestoException("Ocurrio un error al eliminar el puesto con id: " + puesto_id
						+ " favor de intentar nuevamente.", 409);
			}
		} else {
			return "Favor de ingresar el id del puesto a eliminar.";
		}
	}

	@Override
	public List<puestoDto> buscarPuesto(int puesto_id) throws PuestoException {
	
		List<puestoDto> listaPuesto = new ArrayList<>();
		
		if(puesto_id > 0) {
			PuestoEntity puestoEntity = puestoRepository.findById(puesto_id);
			if(puestoEntity == null) {
				throw new PuestoException("No se encontro puesto con los datos proporcionados", 404);
			}			
			puestoDto puesto = new puestoDto();
			puesto.setPuesto_id(puestoEntity.getPuestoId());
			puesto.setDescripcion(puestoEntity.getDescripcion());
			listaPuesto.add(puesto);
			return listaPuesto;
		}else {
			throw new PuestoException("No se encontro puesto con los datos proporcionados.", 404);
		}		
	}
}

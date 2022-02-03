package com.mx.forworkes.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mx.forworkes.dto.EstatusEmpleadoDto;
import com.mx.forworkes.entity.EmpleadoEntity;
import com.mx.forworkes.entity.EstatusEmpleadoEntity;
import com.mx.forworkes.exception.ExceptionGlobal;
import com.mx.forworkes.repository.IEmpleadoRepository;
import com.mx.forworkes.repository.IEstatusEmpleadoRepository;
import com.mx.forworkes.service.IEstatusEmpleadoService;

/**
 * @author IgnacioCisnerosJuare
 *
 */
@Service
public class EstatusEmpleadoServiceImpl implements IEstatusEmpleadoService {

	private static final Logger LOGGER = LogManager.getLogger(EstatusEmpleadoServiceImpl.class);

	@Autowired
	private IEstatusEmpleadoRepository estatusRepository;
	@Autowired
	private IEmpleadoRepository empleadoRepository;

	private static int diasGanados = 0;
	private static int aniosAntiguedad = 0;

	/**
	 * Metodo para hacer el buscado y calculo hacia los empleados.
	 */
	@Override
	public EstatusEmpleadoDto buscarEstatus(long empleado_id) throws ExceptionGlobal {

		EstatusEmpleadoEntity statusEmpleado = estatusRepository.findByEstatusEmpleadoId(empleado_id);
		if (statusEmpleado != null) {

			EmpleadoEntity datosEmpleado = empleadoRepository.findByEmpleadoId(empleado_id);
			if (datosEmpleado != null) {
				EstatusEmpleadoDto statusDto = new EstatusEmpleadoDto();
				EstatusEmpleadoDto estatusDto = new EstatusEmpleadoDto();
				statusDto.setEstatusEmpleadoId(statusEmpleado.getEstatusEmpleadoId());
				statusDto.setDiasVacaciones(calcularDiasTotalesDeVacaciones(empleado_id));
				statusDto.setDiasTomados(statusEmpleado.getDiasTomados());
				statusDto.setDiasPorTomar(statusEmpleado.getDiasPorTomar());
				statusDto.setDiasPendientesPorAutorizar(statusEmpleado.getDiasPendientesPorAutorizar());
				statusDto.setAntiguedad(calcularAntiguedad(datosEmpleado.getFechaIngreso()));
//			statusDto.setAniversario(calcularAniversario(fechaIngreso));
				statusDto.setAguinaldo(121);
				statusDto.setPrimaVacacional(1);
				return statusDto;
			} else {
				throw new ExceptionGlobal("Ocurrio un error al buscar los datos del empleado.", 404);
			}
		} else {
			throw new ExceptionGlobal("Ocurrio un error al buscar los datos del empleado.", 404);
		}

	}

	/**
	 * Metodo encargado de hacer el calculo de los dias de vacaciones que tiene el
	 * empleado.
	 * 
	 * @param empleado_id
	 * @return los dias que tiene de vacaciones el empleado
	 * @throws ExceptionGlobal
	 */
	private int calcularDiasTotalesDeVacaciones(long empleado_id) throws ExceptionGlobal {
		LOGGER.info("::CALCULANDO LOS DIAS DE VACACIONES TOTALES::");
		EstatusEmpleadoEntity statusEmpleado = estatusRepository.findByEstatusEmpleadoId(empleado_id);
		if (statusEmpleado != null) {

			EmpleadoEntity empleado = empleadoRepository.findByEmpleadoId(empleado_id);
			if (empleado != null) {

				aniosAntiguedad = calcularAntiguedad(empleado.getFechaIngreso());
			}

			if (aniosAntiguedad == 1) {
				diasGanados = 6;
			} else if (aniosAntiguedad == 2) {
				diasGanados = 8;
			} else if (aniosAntiguedad == 3) {
				diasGanados = 10;
			} else if (aniosAntiguedad == 4) {
				diasGanados = 12;
			} else if (aniosAntiguedad >= 5 && aniosAntiguedad <= 9) {
				diasGanados = 14;
			} else if (aniosAntiguedad >= 10 && aniosAntiguedad <= 14) {
				diasGanados = 16;
			} else if (aniosAntiguedad >= 15 && aniosAntiguedad <= 19) {
				diasGanados = 18;
			} else if (aniosAntiguedad >= 20 && aniosAntiguedad <= 24) {
				diasGanados = 20;
			} else if (aniosAntiguedad > 25) {
				diasGanados = 22;
			}
			int diasTotales = diasGanados - statusEmpleado.getDiasTomados();
			return diasTotales;

		} else {
			LOGGER.info("::NO SE ENCONTRO INFORMACION DEL EMPLEADO::");
			throw new ExceptionGlobal("No se encontro informacion con el numero de empleado", 404);
		}
	}

	/**
	 * Metodo encargado de calcular la antiguedad del empleado
	 * 
	 * @param empleado_id
	 * @return
	 * @throws ExceptionGlobal
	 */
	public int calcularAntiguedad(Date fechaIngreso) throws ExceptionGlobal {
		try {
			LocalDate fechaActual = LocalDate.now();
			LocalDate fechaAlta = Instant.ofEpochMilli(fechaIngreso.getTime()).atZone(ZoneId.systemDefault())
					.toLocalDate();
			Period antiguedad = Period.between(fechaAlta, fechaActual);
			LOGGER.error("::ANTIGUEDAD::" + antiguedad.getYears());
			return antiguedad.getYears();

		} catch (Exception e) {
			LOGGER.error("::ERROR AL CALCULAR LA ANTIGUEDAD DEL EMPLEADO ===> ::" + e.getMessage());
			throw new ExceptionGlobal("Ocurrio un error al calcular la antiguedad del empleado.", 404);
		}
	}

	/**
	 * Metodo encargado de calcular la fecha de aniversario del empleado
	 * 
	 * @param fechaIngreso
	 * @return
	 */
	public String calcularAniversario(Date fechaIngreso) {

		return "";

	}

}

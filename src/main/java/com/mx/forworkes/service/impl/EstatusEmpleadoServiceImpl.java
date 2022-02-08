package com.mx.forworkes.service.impl;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
	private static final DecimalFormat df = new DecimalFormat("0.00");

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
				statusDto.setEstatusEmpleadoId(statusEmpleado.getEstatusEmpleadoId());
				statusDto.setDiasVacaciones(calcularDiasTotalesDeVacaciones(empleado_id));
				statusDto.setDiasTomados(statusEmpleado.getDiasTomados());
				statusDto.setDiasPorTomar(statusEmpleado.getDiasPorTomar());
				statusDto.setDiasPendientesPorAutorizar(statusEmpleado.getDiasPendientesPorAutorizar());
				statusDto.setAntiguedad(calcularAntiguedad(datosEmpleado.getFechaIngreso()));
				statusDto.setAniversario(calcularAniversario(datosEmpleado.getFechaIngreso()));
				statusDto.setAguinaldo(
						calcularAguinaldo(datosEmpleado.getFechaIngreso(), datosEmpleado.getSueldoMensual()));
				statusDto.setPrimaVacacional(calcularPrivaVacacional(calcularDiasTotalesDeVacaciones(empleado_id),
						datosEmpleado.getSueldoMensual()));
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
	 * @throws ExceptionGlobal
	 */
	public Date calcularAniversario(Date fechaIngreso) throws ExceptionGlobal {
		LOGGER.info("::CALCULANDO ANIVERSARION DE EMPLEADO::");
		try {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("d/MM/yyyy");
			String fechaActual = LocalDate.now().toString();
			String fechaAlta = Instant.ofEpochMilli(fechaIngreso.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
					.toString();
			LOGGER.info("::FECHAS ACTUAL::" + fechaActual);
			String anioActual = fechaActual.substring(0, 4);
			String subFechaIngreso = fechaAlta.substring(5);
			String fechaFinal = anioActual + "-" + subFechaIngreso;
			LOGGER.info("::FECHA DE ANIVERSARIO::" + fechaFinal);
			LocalDate fechaAniversario = LocalDate.parse(fechaFinal);
			LocalDate hoy = LocalDate.parse(fechaActual);
			if (fechaAniversario.isAfter(hoy)) {
				return convertirLocalDateToDate(fechaAniversario);
			} else if (fechaAniversario.isBefore(hoy)) {
				// SE LE AGREGA UN AÑO
				fechaAniversario.plusYears(1);
				return convertirLocalDateToDate(fechaAniversario);
			}
		} catch (Exception e) {
			LOGGER.error("::ERROR -- OCURRIO EL SIGUIENTE ERROR AL CALCULAR EL ANIVERSARIO DEL EMPLEADO ====> ::"
					+ e.getMessage());
			throw new ExceptionGlobal(
					"Ocurrio un error al calcular el aniversario del empleado, favor de comunicarse con soporte.", 500);
		}
		return null;
	}

	/**
	 * Metodo que se encarga de convertir una fecha LocalDate a tipo Date
	 * 
	 * @param fechaAConvertir
	 * @return fecha convertida a tipo Date
	 */
	public Date convertirLocalDateToDate(LocalDate fechaAConvertir) {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(fechaAConvertir.atStartOfDay(defaultZoneId).toInstant());
		return date;
	}

	/**
	 * Metodo que calcula el aguinaldo del empleado de acuerdo a si antiguedad y
	 * salario mensual
	 * 
	 * @param fechaIngre
	 * @param sueldoMensual
	 * @return el aguinaldo correspondiente del empleado
	 */
	public Double calcularAguinaldo(Date fechaIngreso, Double sueldoMensual) {
		LOGGER.info("::CALCULANDO AGUINALDO DEL EMPLEADO::");
		// FORMULA PARA CALCULAR EL AGUINALDO
		// SALARIO DIARIO * 15 / 365 * DIAS TRABAJADOS

		// FORMULA PARA SALARIO DIARIO: SALARIO AL MES / 30
		double salarioDiario = sueldoMensual / 30;
		// CALCULADO LOS DIAS TRABAJADOS
		LocalDate fechaActual = LocalDate.now();
		LocalDate fechaAlta = Instant.ofEpochMilli(fechaIngreso.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
//		Period dias = Period.between(fechaAlta, fechaActual);
		long dias = fechaAlta.until(fechaActual, ChronoUnit.DAYS);
		int diasTrabajados = (int) dias;
		double aguinaldo = salarioDiario * 15 / 365 * diasTrabajados;
		String AguinaldoCorrespondiente = df.format(aguinaldo);
		return Double.parseDouble(AguinaldoCorrespondiente);
	}

	/**
	 * Metodo que calcula el procentaje de prima vacacional para el empleado
	 * 
	 * @param diasVacaciones
	 * @param sueldoMensual
	 * @return prima vacacional del empleado
	 */
	public Double calcularPrivaVacacional(int diasVacaciones, double sueldoMensual) {
		// FORMULA PARA CALCULAR LA PRIMA VACACIONAL
		// PRIMA = salarioDiario * dias de vacaciones * .25
		double porcentaje = .25;
		double salarioDiario = sueldoMensual / 30;
		double salarioPorVacaciones = salarioDiario * diasVacaciones;
		double total = salarioPorVacaciones * porcentaje;
		String primavacacional = df.format(total);
		return Double.parseDouble(primavacacional);
	}

}

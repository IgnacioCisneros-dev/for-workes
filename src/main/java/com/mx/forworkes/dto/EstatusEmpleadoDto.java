package com.mx.forworkes.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author IgnacioCisnerosJuare
 *
 */

@Setter
@Getter
public class EstatusEmpleadoDto {

	private Long estatusEmpleadoId;
	private int diasVacaciones;
	private int diasTomados;
	private int diasPorTomar;
	private int diasPendientesPorAutorizar;
	private int antiguedad;
	private Float aguinaldo;
	private Date aniversario;
	private Float primaVacacional;

}

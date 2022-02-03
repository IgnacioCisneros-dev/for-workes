package com.mx.forworkes.dto;

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
	private int aguinaldo;
	private int aniversario;
	private int primaVacacional;

}

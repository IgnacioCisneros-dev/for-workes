package com.mx.forworkes.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
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
	private Double aguinaldo;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date aniversario;
	private Double primaVacacional;

}

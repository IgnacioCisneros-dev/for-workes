package com.mx.forworkes.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * @author IgnacioCisnerosJuare
 *
 */

@Entity
@Table(name = "estatus_empleado")
@Data
public class EstatusEmpleadoEntity {

	@Id
	@Column(name = "estatus_empleado_id")
	private long estatusEmpleadoId;

	@Column(name = "dias_totales_vacaciones")
	private int diasVacaciones;

	@Column(name = "dias_tomados")
	private int diasTomados;

	@Column(name = "dias_por_tomar")
	private int diasPorTomar;

	@Column(name = "dias_pendientes_por_autorizar")
	private int diasPendientesPorAutorizar;

	@Column(name = "antiguedad")
	private int antiguedad;

	@Column(name = "aguinaldo")
	private Double aguinaldo;

	@Column(name = "aniversario")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date aniversario;

	@Column(name = "prima_vacacional")
	private Double primaVacacional;

	@OneToMany(mappedBy = "estatus_empleado")
	private Set<EmpleadoEntity> empleado;

}

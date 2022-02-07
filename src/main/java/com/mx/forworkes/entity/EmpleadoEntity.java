package com.mx.forworkes.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author IgnacioCisnerosJuare
 *
 */

@Entity
@Table(name = "empleados")
@Data
public class EmpleadoEntity {

	@Id
	@Column(name = "empleado_id")
	private long empleadoId;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido_paterno")
	private String apellidoPaterno;

	@Column(name = "apellido_matero")
	private String apellidoMaterno;

	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;

	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;

	@Column(name = "domicilio")
	private String domicilio;

	@Column(name = "correo")
	private String correo;

	@Column(name = "sueldo_mensual")
	private Double sueldoMensual;

	@ManyToOne
	@JoinColumn(name = "puesto_id", nullable = false)
	private PuestoEntity puesto;

	@ManyToOne
	@JoinColumn(name = "informacion_personal_id", nullable = false)
	private InformacionGeneralEntity informacion_personal;

	@ManyToOne
	@JoinColumn(name = "estatus_empleado_id")
	private EstatusEmpleadoEntity estatus_empleado;

	public EmpleadoEntity() {
	}

}

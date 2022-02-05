package com.mx.forworkes.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author IgnacioCisnerosJuare
 */

@Setter
@Getter
public class empleadoDto {

	private long empleadoId;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Date fechaNacimiento;
	private Date fechaIngreso;
	private String domicilio;
	private String correo;

}

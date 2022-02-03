package com.mx.forworkes.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author IgnacioCisnerosJuare
 *
 */
@Entity
@Data
@Table(name = "informacion_personal")
public class InformacionGeneralEntity {

	@Id
	@Column(name = "informacion_personal_id")
	private long informacionPersonalId;

	@Column(name = "numero_cuenta")
	private String numeroCuenta;

	@Column(name = "clave_interbancaria")
	private String claveInterbancaria;

	@Column(name = "nss")
	private String nss;

	@Column(name = "nombre_familiar_emergencia")
	private String nombreFamiliarEmergencia;

	@Column(name = "numero_familiar_emergencia")
	private String numeroFamiliarEmergencia;

	@Column(name = "tipo_sangre")
	private String tipoSangre;

	@Column(name = "padecimientos_medicos")
	private String padecimientosMedicos;

	@OneToMany(mappedBy = "informacion_personal")
	private Set<EmpleadoEntity> empleado;
}

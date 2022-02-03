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
@Table(name = "puesto")
public class PuestoEntity {

	@Id
	@Column(name = "puesto_id")
	private long puestoId;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "activo")
	private Boolean activo;

	@OneToMany(mappedBy = "puesto")
	private Set<EmpleadoEntity> empleado;

}

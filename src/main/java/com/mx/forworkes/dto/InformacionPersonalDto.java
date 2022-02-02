package com.mx.forworkes.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author IgnacioCisnerosJuare
 *
 */
@Setter
@Getter
public class InformacionPersonalDto {

	private long informacionPersonalId;
	private String numeroCuenta;
	private String claveInterbancaria;
	private String nss;
	private String nombreFamiliarEmergencia;
	private String numeroFamiliarEmergencia;
	private String tipoSangre;
	private String padecimientosMedicos;
}

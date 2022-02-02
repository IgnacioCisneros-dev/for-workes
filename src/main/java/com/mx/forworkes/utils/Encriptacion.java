package com.mx.forworkes.utils;

import java.util.Base64;

import com.mx.forworkes.exception.ExceptionGlobal;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public class Encriptacion {

	/**
	 * @param cadenaACodificar
	 * @return cadena codificada
	 * @throws ExceptionGlobal
	 */
	public String codificar(String cadenaACodificar) throws ExceptionGlobal {
		try {
			String cadenaCodificada = Base64.getEncoder().encodeToString(cadenaACodificar.getBytes());
			return cadenaCodificada;
		} catch (Exception e) {
			throw new ExceptionGlobal("Ocurrio un problema en de codificacion de la cadena", 409);
		}

	}

	/**
	 * @param cadenaADecodificar
	 * @return cadena descodificada.
	 * @throws ExceptionGlobal
	 */
	public String decodificar(String cadenaADecodificar) throws ExceptionGlobal {
		try {
			String cadenaDecodificada = "";
			byte[] byteCodificados = Base64.getDecoder().decode(cadenaADecodificar);
			cadenaDecodificada = new String(byteCodificados);
			return cadenaDecodificada;
		} catch (Exception e) {
			throw new ExceptionGlobal("Ocurrio un problema en la decodificacion de la cadena", 409);
		}

	}
}

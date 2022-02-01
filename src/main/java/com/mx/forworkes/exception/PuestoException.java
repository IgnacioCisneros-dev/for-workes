package com.mx.forworkes.exception;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public class PuestoException extends Exception {

	private static final long serialVersionUID = 1L;

	private int codeError;

	public PuestoException(String errorMessage, int codeError) {
		super(errorMessage);
		this.codeError = codeError;
	}

	public int getCodeError() {
		return codeError;
	}

	public void setCodeError(int codeError) {
		this.codeError = codeError;
	}

	public int getCodigoError() {

		return 0;
	}
}

package com.mx.forworkes.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author IgnacioCisnerosJuare
 *
 */
public class errorInfo {

	@JsonProperty("message")
	private String message;
	@JsonProperty("status_code")
	private int statusCode;
	@JsonProperty("uri")
	private String uriRequested;

//	public errorInfo(ApiException exception, String uriRequested) {
//	       this.message = exception.getMessage();
//	       this.statusCode = exception.getStatusCode().value();
//	       this.uriRequested = uriRequested;
//	   }

	public errorInfo(int statusCode, String message, String uriRequested) {
	       this.message = message;
	       this.statusCode = statusCode;
	       this.uriRequested = uriRequested;
	   }

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getUriRequested() {
		return uriRequested;
	}
}

package br.example.net.exception;

import java.io.IOException;

public class HttpRequestUtilsException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HttpRequestUtilsException() {
	}

	public HttpRequestUtilsException(String message) {
		super(message);
	}

	public HttpRequestUtilsException(Throwable cause) {
		super(cause);
	}

	public HttpRequestUtilsException(String message, Throwable cause) {
		super(message, cause);
	}

}

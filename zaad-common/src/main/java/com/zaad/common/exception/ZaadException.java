package com.zaad.common.exception;

public class ZaadException extends RuntimeException {
	private static final long serialVersionUID = -1848528751365411884L;

	public ZaadException() {
		super();
	}
	
	public ZaadException(String message) {
		super(message);
	}
}

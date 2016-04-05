package com.zaad.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class ZaadContentNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 7719764694387753139L;

}

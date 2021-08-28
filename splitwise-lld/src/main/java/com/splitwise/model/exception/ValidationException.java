package com.splitwise.model.exception;

import lombok.Getter;

public class ValidationException extends Exception {
	
	public enum Type{
		INVALID_INPUT, INVALID_EQUAL_SPLIT, DUPLICATE_USER
	}
	
	@Getter private  Type type;

	private static final long serialVersionUID = 1L;

	public ValidationException() {
		super();
	}
	
	public ValidationException(Type type) {
		super();
		this.type = type;
	}

	public ValidationException(Type type, String arg0) {
		super(arg0);
		this.type = type;
	}
}

package com.app.snakeandladder.exception;

@SuppressWarnings("serial")
public class UserThresholdBreachedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserThresholdBreachedException(String message, Throwable cause) {
		super(message, cause);
	}

}

/**
 * 
 */
package com.n26.transaction.exception;

import lombok.Getter;

/**
 * @author SridharRaj
 *
 */
public class InvalidTransactionException extends Exception {

	private static final long serialVersionUID = 1L;

	public enum ErrorType {
		INVALID_EVENT,
		NOT_ACCEPTED
	}

	@Getter private final ErrorType error;
	
	/**
	 * @param arg0
	 */
	public InvalidTransactionException(ErrorType error, String arg0) {
		super(arg0);
		this.error = error;
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidTransactionException(ErrorType error, String message, Throwable cause) {
		super(message, cause);
		this.error = error;
	}

}

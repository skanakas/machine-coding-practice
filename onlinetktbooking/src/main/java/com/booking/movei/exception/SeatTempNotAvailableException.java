package com.booking.movei.exception;

public class SeatTempNotAvailableException extends RuntimeException {

	public SeatTempNotAvailableException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SeatTempNotAvailableException(String message) {
		super(message);
	}

}

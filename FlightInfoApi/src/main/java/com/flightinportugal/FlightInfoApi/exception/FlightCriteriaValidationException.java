package com.flightinportugal.FlightInfoApi.exception;


/**
 * Exception for flight query parameters errors
 *
 */
public class FlightCriteriaValidationException extends RuntimeException{

	private static final long serialVersionUID = -160283273454157986L;
	
	public FlightCriteriaValidationException() {
		super();
	}
	
	public FlightCriteriaValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlightCriteriaValidationException(String message) {
		super(message);
	}

	public FlightCriteriaValidationException(Throwable cause) {
		super(cause);
	}
}

package com.flightinportugal.FlightInfoApi.exception;

/**
 * The default exception class for this API
 *
 */
public class FlightInfoApiException extends RuntimeException{

	private static final long serialVersionUID = 6943984260522063165L;

	public FlightInfoApiException() {
		super();
	}
	
	public FlightInfoApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public FlightInfoApiException(String message) {
		super(message);
	}

	public FlightInfoApiException(Throwable cause) {
		super(cause);
	}


}

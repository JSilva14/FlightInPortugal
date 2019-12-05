package com.flightinportugal.FlightInfoApi.error;

import org.springframework.http.HttpStatus;

import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;

public class FlightInfoApiError {

	/**
	 * The http status of a request
	 */
	private HttpStatus status;
	/**
	 * The message associated with an ErrorMessage
	 */
	private String message;

	public FlightInfoApiError(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	

	public FlightInfoApiError(HttpStatus status, ErrorMessage errorMessage) {
		super();
		this.status = status;
		this.message = errorMessage.getMessage();
	}
	
	public FlightInfoApiError(HttpStatus status, Exception e) {
		super();
		this.status = status;
		this.message = "Unexpected Error";
	}



	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}

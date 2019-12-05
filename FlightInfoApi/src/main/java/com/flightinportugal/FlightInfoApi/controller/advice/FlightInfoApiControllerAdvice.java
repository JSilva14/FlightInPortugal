package com.flightinportugal.FlightInfoApi.controller.advice;

import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flightinportugal.FlightInfoApi.error.FlightInfoApiError;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import com.flightinportugal.FlightInfoApi.exception.FlightCriteriaValidationException;

/**
 * Class that works as a centralized place to handle all application level
 * exceptions
 * 
 */
@RestControllerAdvice
public class FlightInfoApiControllerAdvice {

	Logger log = Logger.getLogger(FlightInfoApiControllerAdvice.class);

	// TODO: check how multiple exceptions can be grouped and use if applied
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	@ExceptionHandler({ DogsServiceException.class, SQLException.class, NullPointerException.class })
//	public void handle() {
//	}

	@ExceptionHandler({ FlightCriteriaValidationException.class })
	public ResponseEntity<FlightInfoApiError> handleFlightCriteriaValidationException(
			FlightCriteriaValidationException e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<FlightInfoApiError>(new FlightInfoApiError(HttpStatus.BAD_REQUEST, e.getMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<FlightInfoApiError> handleException(Exception e) {
		log.error(e.getMessage(), e);
		return new ResponseEntity<FlightInfoApiError>(new FlightInfoApiError(HttpStatus.INTERNAL_SERVER_ERROR,
				ErrorMessage.UNEXPECTED_ERROR_RETRIEVING_FLIGHTS.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

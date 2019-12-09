package com.flightinportugal.FlightInfoApi.controller.advice;

import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.flightinportugal.FlightInfoApi.error.FlightInfoApiError;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import com.flightinportugal.FlightInfoApi.exception.ExternalApiException;
import com.flightinportugal.FlightInfoApi.exception.FlightCriteriaValidationException;
import com.flightinportugal.FlightInfoApi.exception.RequestNotFoundException;

/**
 * Class that works as a centralized place to handle all application level exceptions
 * 
 */
@RestControllerAdvice
public class FlightInfoApiControllerAdvice {

  Logger log = Logger.getLogger(FlightInfoApiControllerAdvice.class);

  @ExceptionHandler({FlightCriteriaValidationException.class})
  public ResponseEntity<FlightInfoApiError> handleFlightCriteriaValidationException(
      FlightCriteriaValidationException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<FlightInfoApiError>(
        new FlightInfoApiError(HttpStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({ExternalApiException.class})
  public ResponseEntity<FlightInfoApiError> handleExternalApiException(ExternalApiException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<FlightInfoApiError>(
        new FlightInfoApiError(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({RequestNotFoundException.class})
  public ResponseEntity<FlightInfoApiError> handleRequestNotFoundException(
      RequestNotFoundException e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<FlightInfoApiError>(
        new FlightInfoApiError(HttpStatus.NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<FlightInfoApiError> handleException(Exception e) {
    log.error(e.getMessage(), e);
    return new ResponseEntity<FlightInfoApiError>(
        new FlightInfoApiError(HttpStatus.INTERNAL_SERVER_ERROR,
            ErrorMessage.UNEXPECTED_ERROR.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

}

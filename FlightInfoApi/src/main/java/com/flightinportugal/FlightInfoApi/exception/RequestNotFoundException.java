package com.flightinportugal.FlightInfoApi.exception;

/**
 * Exception for errors in external API communication
 *
 */
public class RequestNotFoundException extends RuntimeException {


  private static final long serialVersionUID = 8429155961254755722L;

  public RequestNotFoundException() {
    super();
  }

  public RequestNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public RequestNotFoundException(String message) {
    super(message);
  }

  public RequestNotFoundException(Throwable cause) {
    super(cause);
  }



}

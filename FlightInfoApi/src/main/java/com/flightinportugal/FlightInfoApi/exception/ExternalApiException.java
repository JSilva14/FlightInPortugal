package com.flightinportugal.FlightInfoApi.exception;

/**
 * Exception for errors in external API communication
 *
 */
public class ExternalApiException extends RuntimeException {

  private static final long serialVersionUID = -4155810063715017375L;

  public ExternalApiException() {
    super();
  }

  public ExternalApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExternalApiException(String message) {
    super(message);
  }

  public ExternalApiException(Throwable cause) {
    super(cause);
  }



}

package com.flightinportugal.FlightInfoApi.error.message;

import java.text.MessageFormat;

public enum ErrorMessage {

  UNEXPECTED_ERROR("An unexpected error has occurred"), //
  MISSING_REQUIRED_PARAMS("Request is missing required query parameters"), //
  SAME_ORIGIN_AND_DESTINATION("The value of to and from cannot be equal."), //
  INVALID_AIRPORT_CODE("Invalid airport code."), //
  INVALID_DATE("Date inserted is invalid."), //
  DATE_COMPARISON("The date in dateFrom must be before dateTo"), //
  DATE_IN_PAST("The date specified is in the past"), //
  ERROR_REQUESTING_EXTERNAL_DATA("An error occurred while retrieving data from external source"), //
  REQUEST_DATA_NOT_FOUND("Request data could not be found"), //
  UNEXPECTED_ERROR_STORING_REQUEST("An error occurred during request storage procedure");

  private final String message;

  private ErrorMessage(String message) {
    this.message = message;
  }

  public String getMessage(Object... params) {
    return MessageFormat.format(this.message, params);
  }

}

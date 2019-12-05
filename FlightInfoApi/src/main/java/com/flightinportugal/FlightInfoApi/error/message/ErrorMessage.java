package com.flightinportugal.FlightInfoApi.error.message;

import java.text.MessageFormat;

public enum ErrorMessage {

	UNEXPECTED_ERROR_RETRIEVING_FLIGHTS("An unexpected error occurred while attempting to retrieve flights"),
	FLIGHT_NOT_FOUND("No flights were found that match the specified parameters."),
	MISSING_REQUIRED("Request is missing the required parameter: {0}"),
	EQUAL_VALUES("The value of {0} and {1} cannot be equal."),
	INVALID_AIRPORT_CODE("The airport code specified in {0} is not valid."),
	DATE_IN_PAST("The date specified in {0} must not be in the past."),
	UNRECONGNIZED_PARAM("Unrecognized parameter {0}!");
	
	private final String message;

	private ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage(Object... params) {
		return MessageFormat.format(this.message, params);
	}

}

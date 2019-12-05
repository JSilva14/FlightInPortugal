package com.flightinportugal.FlightInfoApi.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flightinportugal.FlightInfoApi.criteria.FlightCriteria;
import com.flightinportugal.FlightInfoApi.criteria.validator.FlightCriteriaValidator;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import com.flightinportugal.FlightInfoApi.exception.FlightCriteriaValidationException;
import com.flightinportugal.FlightInfoApi.exception.FlightInfoApiException;
import com.flightinportugal.FlightInfoApi.kiwiclient.KiwiWebClient;
import com.flightinportugal.FlightInfoApi.kiwiclient.model.KiwiFlightsResponse;
import com.flightinportugal.FlightInfoApi.model.FlightsAverageResponse;
import com.flightinportugal.FlightInfoApi.model.FlightsResponse;

/**
 * Contains mappings for the HTTP requests provided by this API
 * 
 */
@RestController
public class FlightInfoController {

	@Autowired
	FlightCriteriaValidator validator;

	@Autowired
	KiwiWebClient kiwiWebClient;

	// TODO: Swagger
	// TODO: tests

	@GetMapping(path = "/flights", produces = "application/json")
	public ResponseEntity<List<FlightsResponse>> getFlights(FlightCriteria flightCriteria,
			BindingResult bindingResult) {

		ResponseEntity<List<FlightsResponse>> flightsResponse = null;
		KiwiFlightsResponse kiwiFlights = null;

		validator.validate(flightCriteria, bindingResult);

		if (bindingResult.hasErrors()) {
			// Should pass all the errors instead of the
			// first so that all of them are shown to the user at
			// the same time
			throw new FlightCriteriaValidationException(bindingResult.getFieldError().getCode());
		}

		try {
			// Request flights from Kiwi API
			kiwiFlights = kiwiWebClient.getFlights(flightCriteria);
			// Map retrieved response to a list of FlightInfo
			List<FlightsResponse> flights = new ArrayList<FlightsResponse>();
			kiwiFlights.getData().forEach(
					kiwiFlight -> flights.add(FlightsResponse.fromKiwiFlightData(kiwiFlight)));

			// Create response entity from list
			flightsResponse = new ResponseEntity<List<FlightsResponse>>(flights, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			throw new FlightInfoApiException(
					ErrorMessage.UNEXPECTED_ERROR_RETRIEVING_FLIGHTS.getMessage(), e);
		}

		return flightsResponse;

	}

	@GetMapping(path = "/flights/avg", produces = "application/json")
	public ResponseEntity<FlightsAverageResponse> getFlightsAverage(
			FlightCriteria flightCriteria, BindingResult bindingResult) {

		ResponseEntity<FlightsAverageResponse> flightsAverageResponse = null;
		KiwiFlightsResponse kiwiFlights = null;

		validator.validate(flightCriteria, bindingResult);

		if (bindingResult.hasErrors()) {
			// Should pass all the errors instead of the
			// first so that all of them are shown to the user at
			// the same time
			throw new FlightCriteriaValidationException(bindingResult.getFieldError().getCode());
		}

		try {
			// Request flights from Kiwi API
			kiwiFlights = kiwiWebClient.getFlights(flightCriteria);

			// Create response entity from list
			flightsAverageResponse = new ResponseEntity<FlightsAverageResponse>(
					FlightsAverageResponse.fromKiwiFlightsResponse(kiwiFlights), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			throw new FlightInfoApiException(
					ErrorMessage.UNEXPECTED_ERROR_RETRIEVING_FLIGHTS.getMessage(), e);
		}

		return flightsAverageResponse;

	}

}

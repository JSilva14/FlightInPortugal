package com.flightinportugal.FlightInfoApi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flightinportugal.FlightInfoApi.criteria.FlightCriteria;
import com.flightinportugal.FlightInfoApi.criteria.validator.FlightCriteriaValidator;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import com.flightinportugal.FlightInfoApi.exception.FlightInfoApiException;
import com.flightinportugal.FlightInfoApi.kiwiclient.KiwiWebClient;
import com.flightinportugal.FlightInfoApi.kiwiclient.model.KiwiFlightData;
import com.flightinportugal.FlightInfoApi.kiwiclient.model.KiwiFlightsResponse;
import com.flightinportugal.FlightInfoApi.model.FlightsAverageResponse;
import com.flightinportugal.FlightInfoApi.model.FlightsResponse;

@Service
public class FlightInfoService {

	@Autowired
	FlightCriteriaValidator validator;

	@Autowired
	KiwiWebClient kiwiWebClient;

	public List<FlightsResponse> getFlights(FlightCriteria flightCriteria) {

		KiwiFlightsResponse kiwiFlights = null;
		List<FlightsResponse> flights = new ArrayList<FlightsResponse>();

		try {
			// Request flights from Kiwi API
			kiwiFlights = kiwiWebClient.getFlights(flightCriteria);

			// Map retrieved response to a list of FlightInfo
			for (KiwiFlightData kiwiFlight : kiwiFlights.getData()) {
				flights.add(FlightsResponse.fromKiwiFlightData(kiwiFlight));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new FlightInfoApiException(
					ErrorMessage.UNEXPECTED_ERROR_RETRIEVING_FLIGHTS.getMessage(), e);
		}

		return flights;
	}
	
	
	public FlightsAverageResponse getAverageFlightPrices(FlightCriteria flightCriteria) {

		FlightsAverageResponse flightsAverageResponse = null;
		KiwiFlightsResponse kiwiFlights = null;

		try {
			// Request flights from Kiwi API
			kiwiFlights = kiwiWebClient.getFlights(flightCriteria);

			// Create response entity from list
			flightsAverageResponse = FlightsAverageResponse.fromKiwiFlightsResponse(kiwiFlights);

		} catch (Exception e) {
			e.printStackTrace();
			throw new FlightInfoApiException(
					ErrorMessage.UNEXPECTED_ERROR_RETRIEVING_FLIGHTS.getMessage(), e);
		}

		return flightsAverageResponse;
	}

}

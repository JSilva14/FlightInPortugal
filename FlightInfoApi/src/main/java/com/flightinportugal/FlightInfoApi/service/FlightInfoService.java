package com.flightinportugal.FlightInfoApi.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flightinportugal.FlightInfoApi.criteria.FlightCriteria;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import com.flightinportugal.FlightInfoApi.exception.ExternalApiException;
import com.flightinportugal.FlightInfoApi.exception.FlightInfoApiException;
import com.flightinportugal.FlightInfoApi.external.api.KiwiWebClient;
import com.flightinportugal.FlightInfoApi.external.api.model.KiwiFlightData;
import com.flightinportugal.FlightInfoApi.external.api.model.KiwiFlightsResponse;
import com.flightinportugal.FlightInfoApi.model.FlightsAverageResponse;
import com.flightinportugal.FlightInfoApi.model.FlightsResponse;

@Service
public class FlightInfoService {

  @Autowired
  KiwiWebClient kiwiWebClient;

  /**
   * Requests flight data from {@link KiwiWebClient} and builds a list of {@link FlightsResponse} to
   * sent back to the controller
   * 
   * @param flightCriteria a set of parameters to request flight information
   * @return a list of flights that match the criteria
   */
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

    } catch (ExternalApiException e) {
      throw e;
    } catch (Exception e) {
      throw new FlightInfoApiException(ErrorMessage.UNEXPECTED_ERROR.getMessage(), e);
    }

    return flights;
  }


  /**
   * Requests flight data from {@link KiwiWebClient} and builds a {@link FlightsAverageResponse}
   * containing average flight and bag prices for all the flights that match the criteria
   * 
   * @param flightCriteria a set of parameters to request flight information
   * @return an object containing average flight and bag prices for flights that match the criteria
   */
  public FlightsAverageResponse getAverageFlightPrices(FlightCriteria flightCriteria) {

    FlightsAverageResponse flightsAverageResponse = null;
    KiwiFlightsResponse kiwiFlights = null;

    try {
      // Request flights from Kiwi API
      kiwiFlights = kiwiWebClient.getFlights(flightCriteria);


      flightsAverageResponse =
          FlightsAverageResponse.fromCriteriaAndKiwiFlightsResponse(flightCriteria, kiwiFlights);


    } catch (Exception e) {
      e.printStackTrace();
      throw new FlightInfoApiException(ErrorMessage.UNEXPECTED_ERROR.getMessage(), e);
    }

    return flightsAverageResponse;
  }

}

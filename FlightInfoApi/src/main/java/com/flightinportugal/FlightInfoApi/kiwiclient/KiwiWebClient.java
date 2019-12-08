package com.flightinportugal.FlightInfoApi.kiwiclient;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightinportugal.FlightInfoApi.configuration.FlightInfoApiProperties;
import com.flightinportugal.FlightInfoApi.criteria.FlightCriteria;
import com.flightinportugal.FlightInfoApi.kiwiclient.model.KiwiFlightsResponse;

/**
 * Provides methods for performing requests to the Kiwi.com flights search API.
 * <p>
 * All HTTP requests are made using Spring WebClient
 * 
 * @see <a href="https://docs.kiwi.com/">https://docs.kiwi.com/</a>
 *
 */
@Component
public class KiwiWebClient {

  private final String PARTNER = "partner";
  private final String SELECT_AIRLINES = "select_airlines";
  private final String COMMA_DELIMITER = ",";

  @Autowired
  WebClient client;

  @Autowired
  FlightInfoApiProperties properties;

  @Autowired
  ObjectMapper mapper;

  /**
   * Requests flights from Kiwi API's "flights" endpoint using {@link FlightCriteria} as query
   * parameters
   * 
   * @param flightCriteria an object containing query paramaters to apply
   * @return the response body mapped to a {@link KiwiFlightsResponse}
   * @throws Exception
   */
  public KiwiFlightsResponse getFlights(FlightCriteria flightCriteria) throws Exception {

    // Convert flightCriteria to a Map of queryParameters
    // This makes use of the jackson annotations in FlightCriteria
    Map<String, String> queryParametersMap =
        mapper.convertValue(flightCriteria, new TypeReference<Map<String, String>>() {});

    queryParametersMap.put(PARTNER, properties.getKiwiPartner());
    queryParametersMap.put(SELECT_AIRLINES,
        String.join(COMMA_DELIMITER, properties.getValidAirlineCodes()));

    // client.get().queryParams() takes in a MultivalueMap so we have to create one
    // and place queryParametersMap inside it
    MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
    queryParameters.setAll(queryParametersMap);

    KiwiFlightsResponse flightsResponse = client.get()
        .uri(uriBuilder -> uriBuilder.path(properties.getKiwiApiFlightsEndpoint())
            .queryParams(queryParameters).build())
        .retrieve().bodyToFlux(KiwiFlightsResponse.class).blockFirst();

    return flightsResponse;
  }

}

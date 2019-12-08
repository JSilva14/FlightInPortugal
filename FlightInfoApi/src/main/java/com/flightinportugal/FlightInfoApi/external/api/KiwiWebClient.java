package com.flightinportugal.FlightInfoApi.external.api;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightinportugal.FlightInfoApi.configuration.FlightInfoApiProperties;
import com.flightinportugal.FlightInfoApi.criteria.FlightCriteria;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import com.flightinportugal.FlightInfoApi.exception.ExternalApiException;
import com.flightinportugal.FlightInfoApi.external.api.model.KiwiFlightsResponse;

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

  private static final Logger log = LoggerFactory.getLogger(KiwiWebClient.class);

  private final String FROM = "fly_from";
  private final String TO = "fly_to";
  private final String DATE_FROM = "date_from";
  private final String DATE_TO = "date_to";
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

    KiwiFlightsResponse flightsResponse = null;

    MultiValueMap<String, String> queryParameters = generateQueryParameters(flightCriteria);

    log.info("Requesting flight data from external API...");

    try {
      flightsResponse = client.get()
          .uri(uriBuilder -> uriBuilder.path(properties.getKiwiApiFlightsEndpoint())
              .queryParams(queryParameters).build())
          .retrieve().bodyToFlux(KiwiFlightsResponse.class).blockFirst();
    } catch (Exception e) {
      log.error(ErrorMessage.ERROR_REQUESTING_EXTERNAL_DATA.getMessage(), e);
      throw new ExternalApiException(ErrorMessage.ERROR_REQUESTING_EXTERNAL_DATA.getMessage(), e);
    }

    log.info("External flight data retrieved");

    return flightsResponse;
  }

  /**
   * Generates a {@link MultiValueMap} containing the queryParameters to be applied to the 'GET
   * FLIGHTS' request made to Kiwi
   * 
   * @param flightCriteria a set of parameters to add
   * @return {@link MultiValueMap} the query parameters to apply
   */
  private MultiValueMap<String, String> generateQueryParameters(FlightCriteria flightCriteria) {

    MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
    Map<String, String> queryParametersMap = new HashMap<String, String>();

    if (flightCriteria.getFrom() != null) {
      queryParametersMap.put(FROM, flightCriteria.getFrom());
    }
    if (flightCriteria.getTo() != null) {
      queryParametersMap.put(TO, flightCriteria.getTo());
    }
    if (flightCriteria.getDateFrom() != null) {
      queryParametersMap.put(DATE_FROM, flightCriteria.getDateFrom());
    }
    if (flightCriteria.getDateTo() != null) {
      queryParametersMap.put(DATE_TO, flightCriteria.getDateTo());
    }
    queryParametersMap.put(PARTNER, properties.getKiwiPartner());
    queryParametersMap.put(SELECT_AIRLINES,
        String.join(COMMA_DELIMITER, properties.getValidAirlineCodes()));

    queryParameters.setAll(queryParametersMap);

    return queryParameters;
  }



}

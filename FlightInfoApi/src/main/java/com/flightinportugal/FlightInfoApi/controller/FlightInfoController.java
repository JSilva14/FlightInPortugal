package com.flightinportugal.FlightInfoApi.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flightinportugal.FlightInfoApi.criteria.FlightCriteria;
import com.flightinportugal.FlightInfoApi.criteria.validator.FlightCriteriaValidator;
import com.flightinportugal.FlightInfoApi.exception.FlightCriteriaValidationException;
import com.flightinportugal.FlightInfoApi.model.FlightsAverageResponse;
import com.flightinportugal.FlightInfoApi.model.FlightsResponse;
import com.flightinportugal.FlightInfoApi.service.FlightInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Contains mappings for the HTTP requests provided by this API
 * 
 */
@Tag(name = "Flights", description = "Flight Information")
@RestController
public class FlightInfoController {

  private static final Logger log = LoggerFactory.getLogger(FlightInfoController.class);

  @Autowired
  FlightCriteriaValidator validator;

  @Autowired
  FlightInfoService service;

  /**
   * Retrieves information for all flights that match the parameters specified in
   * {@code flightCriteria}
   * 
   * @param flightCriteria Query parameters to apply
   * @param bindingResult Used for criteria validation purposes
   * @return {@link ResponseEntity} containing a list of flights
   */
  @Operation(summary = "Get Flights",
      description = "Retrieves a list of Flights based on the specified parameters")
  @GetMapping(path = "/flights", produces = "application/json")
  public ResponseEntity<List<FlightsResponse>> getFlights(FlightCriteria flightCriteria,
      BindingResult bindingResult) {

    validator.validate(flightCriteria, bindingResult);
    if (bindingResult.hasErrors()) {
      throw new FlightCriteriaValidationException(bindingResult.getFieldError().getCode());
    }
    log.info("Valid criteria");

    return new ResponseEntity<List<FlightsResponse>>(service.getFlights(flightCriteria),
        HttpStatus.OK);
  }

  /**
   * Retrieves average flight prices and bag prices for flights that match the specified criteria
   * 
   * @param flightCriteria a set of parameters to filter flights
   * @param bindingResult Used for criteria validation purposes
   * @return {@link ResponseEntity} containing a {@link FlightsAverageResponse} instance
   */
  @Operation(summary = "Get Average Flight Prices",
      description = "Returns the average prices for the list of flights that match the specified parameters")
  @GetMapping(path = "/flights/avg", produces = "application/json")
  public ResponseEntity<FlightsAverageResponse> getAverageFlightPrices(
      FlightCriteria flightCriteria, BindingResult bindingResult) {

    validator.validate(flightCriteria, bindingResult);
    if (bindingResult.hasErrors()) {
      throw new FlightCriteriaValidationException(bindingResult.getFieldError().getCode());
    }
    log.info("Valid criteria");

    return new ResponseEntity<FlightsAverageResponse>(
        service.getAverageFlightPrices(flightCriteria), HttpStatus.OK);
  }

}

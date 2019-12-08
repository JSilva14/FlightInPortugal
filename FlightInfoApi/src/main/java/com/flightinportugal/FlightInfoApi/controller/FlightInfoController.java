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

/**
 * Contains mappings for the HTTP requests provided by this API
 * 
 */
@RestController
public class FlightInfoController {

  private static final Logger log = LoggerFactory.getLogger(FlightInfoController.class);

  @Autowired
  FlightCriteriaValidator validator;

  @Autowired
  FlightInfoService service;

  // TODO: Swagger
  @GetMapping(path = "/flights", produces = "application/json")
  public ResponseEntity<List<FlightsResponse>> getFlights(FlightCriteria flightCriteria,
      BindingResult bindingResult) {

    validator.validate(flightCriteria, bindingResult);
    if (bindingResult.hasErrors()) {
      throw new FlightCriteriaValidationException(bindingResult.getFieldError().getCode());
    }

    return new ResponseEntity<List<FlightsResponse>>(service.getFlights(flightCriteria),
        HttpStatus.OK);
  }

  @GetMapping(path = "/flights/avg", produces = "application/json")
  public ResponseEntity<FlightsAverageResponse> getAverageFlightPrices(
      FlightCriteria flightCriteria, BindingResult bindingResult) {

    validator.validate(flightCriteria, bindingResult);
    if (bindingResult.hasErrors()) {
      throw new FlightCriteriaValidationException(bindingResult.getFieldError().getCode());
    }

    return new ResponseEntity<FlightsAverageResponse>(
        service.getAverageFlightPrices(flightCriteria), HttpStatus.OK);
  }

}

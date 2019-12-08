package com.flightinportugal.FlightInfoApi.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flightinportugal.FlightInfoApi.entity.FlightApiRequestEntity;
import com.flightinportugal.FlightInfoApi.repository.FlightsRequestRepository;

/**
 * Contains mappings for requests to get data abou HTTP calls made to "/flights" and "/flights/avg"
 * 
 */
@RestController
public class FlightInfoRequestController {

  @Autowired
  FlightsRequestRepository requestRepository;

  // TODO: Swagger
  @GetMapping(path = "/requests", produces = "application/json")
  public ResponseEntity<List<FlightApiRequestEntity>> getApiRequests() {

    return new ResponseEntity<List<FlightApiRequestEntity>>(requestRepository.findAll(),
        HttpStatus.OK);
  }
}

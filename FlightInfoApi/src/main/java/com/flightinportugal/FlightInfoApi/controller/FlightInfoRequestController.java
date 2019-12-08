package com.flightinportugal.FlightInfoApi.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flightinportugal.FlightInfoApi.model.FlightApiRequestEntity;
import com.flightinportugal.FlightInfoApi.repository.FlightsRequestRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Contains mappings for requests to get data abou HTTP calls made to "/flights" and "/flights/avg"
 * 
 */
@Tag(name = "Requests", description = "Get info or delete requests made to the 'Flights' endpoints")
@RestController
public class FlightInfoRequestController {

  private static final Logger log = LoggerFactory.getLogger(FlightInfoRequestController.class);

  @Autowired
  FlightsRequestRepository requestRepository;

  /**
   * Retrieves all request data from MongoDB
   * 
   * @return a ResponseEntity containing a list of all the request data in the database
   */
  @Operation(summary = "Get All Requests",
      description = "Retrieves a list of requests made to 'Flights' endpoints")
  @GetMapping(path = "/requests", produces = "application/json")
  public ResponseEntity<List<FlightApiRequestEntity>> getApiRequests() {

    log.info("Retrieving all request data...");
    return new ResponseEntity<List<FlightApiRequestEntity>>(requestRepository.findAll(),
        HttpStatus.OK);
  }

  /**
   * Deletes all request data stored in the database
   * 
   * @return a ResponseEntity with status 204 NO CONTENT
   */
  @Operation(summary = "Delete all Requests",
      description = "Deletes all request info from database")
  @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Requests deleted",
      content = @Content(schema = @Schema(implementation = Void.class)))})
  @DeleteMapping(path = "/requests", produces = "application/json")
  public ResponseEntity<?> deleteApiRequests() {

    log.info("Deleting all request data...");

    requestRepository.deleteAll();

    log.info("Requests deleted...");

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}

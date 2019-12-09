package com.flightinportugal.FlightInfoApi.controller;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import com.flightinportugal.FlightInfoApi.exception.RequestNotFoundException;
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
  @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Operation complete",
      content = @Content(schema = @Schema(implementation = Void.class)))})
  @CacheEvict(cacheNames = "request", allEntries = true)
  @DeleteMapping(path = "/requests")
  public ResponseEntity<?> deleteApiRequests() {

    log.info("Deleting all request data...");

    requestRepository.deleteAll();

    log.info("Delete operation complete");

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * Retrieves the request with the given id
   * 
   * @param id The id of the request to retrieve
   * @return a {@link ResponseEntity} containing a {@link FlightApiRequestEntity} with the givern id
   */
  @Operation(summary = "Get Request By Id", description = "Retrieves the request with the given id")
  @Cacheable("request")
  @GetMapping(path = "/requests/{id}", produces = "application/json")
  public ResponseEntity<Optional<FlightApiRequestEntity>> getApiRequestById(
      @PathVariable("id") String id) {

    log.info("Retrieving request with id: " + id);

    Optional<FlightApiRequestEntity> retrievedRequest = requestRepository.findById(id);

    if (retrievedRequest.isEmpty()) {
      throw new RequestNotFoundException(ErrorMessage.REQUEST_DATA_NOT_FOUND.getMessage());
    }

    return new ResponseEntity<Optional<FlightApiRequestEntity>>(retrievedRequest, HttpStatus.OK);
  }

  /**
   * Deletes the request with the given id
   * 
   * @param id The id of the request to delete
   * @return a ResponseEntity with status 204 NO CONTENT
   */
  @Operation(summary = "Delete Request By Id",
      description = "Deletes the request with the given id")
  @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Operation complete",
      content = @Content(schema = @Schema(implementation = Void.class)))})
  @CacheEvict(cacheNames = "request", key = "#root.args")
  @DeleteMapping(path = "/requests/{id}")
  public ResponseEntity<?> deleteApiRequestById(@PathVariable("id") String id) {

    log.info("Deleting request with id: " + id);

    requestRepository.deleteById(id);

    log.info("Delete operation complete");

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}

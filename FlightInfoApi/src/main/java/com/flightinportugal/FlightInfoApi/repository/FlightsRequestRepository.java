package com.flightinportugal.FlightInfoApi.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.flightinportugal.FlightInfoApi.entity.FlightApiRequestEntity;;

public interface FlightsRequestRepository extends MongoRepository<FlightApiRequestEntity, String> {

  public List<FlightApiRequestEntity> findByEndpoint(String endpoint);


}

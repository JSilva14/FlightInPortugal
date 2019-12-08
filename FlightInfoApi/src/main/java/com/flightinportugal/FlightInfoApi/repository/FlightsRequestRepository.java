package com.flightinportugal.FlightInfoApi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.flightinportugal.FlightInfoApi.model.FlightApiRequestEntity;;

@Repository
public interface FlightsRequestRepository extends MongoRepository<FlightApiRequestEntity, String> {

}

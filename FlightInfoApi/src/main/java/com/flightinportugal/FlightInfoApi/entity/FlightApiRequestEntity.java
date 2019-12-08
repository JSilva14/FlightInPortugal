package com.flightinportugal.FlightInfoApi.entity;

import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.flightinportugal.FlightInfoApi.controller.FlightInfoController;

/**
 * Model class for incoming requests in {@link FlightInfoController}. Used to store requests in
 * MongoDB
 */
@Document(collection = "apirequests")
public class FlightApiRequestEntity {

  @Id
  public String id;
  /**
   * The request headers
   */
  public Map<String, Object> headers;
  /**
   * The endpoint to which this request was made
   */
  public String endpoint;
  public String queryString;

  public FlightApiRequestEntity() {
    super();
  }

  public FlightApiRequestEntity(String endpoint, String queryString) {
    super();
    this.endpoint = endpoint;
    this.queryString = queryString;
  }

  public String getId() {
    return id;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public String getqueryString() {
    return queryString;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public void setqueryString(String queryString) {
    this.queryString = queryString;
  }
}

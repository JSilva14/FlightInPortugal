package com.flightinportugal.FlightInfoApi.model;

import java.time.Instant;
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
  private String id;
  /**
   * The timestamp for when this request occurred
   */
  private Instant timestamp;
  /**
   * The request headers
   */
  private Map<String, Object> headers;
  /**
   * The endpoint to which this request was made
   */
  private String endpoint;
  /**
   * Query string attached to the url string
   */
  private String queryString;

  /**
   * The response status
   */
  private Integer responseStatus;


  public FlightApiRequestEntity() {
    super();
  }

  public FlightApiRequestEntity(String id, Instant timestamp, Map<String, Object> headers,
      String endpoint, String queryString, Integer responseStatus) {
    super();
    this.id = id;
    this.timestamp = timestamp;
    this.headers = headers;
    this.endpoint = endpoint;
    this.queryString = queryString;
    this.responseStatus = responseStatus;
  }

  public FlightApiRequestEntity(Instant timestamp, Map<String, Object> headers, String endpoint,
      String queryString, Integer responseStatus) {
    super();
    this.timestamp = timestamp;
    this.headers = headers;
    this.endpoint = endpoint;
    this.queryString = queryString;
    this.responseStatus = responseStatus;
  }

  public String getId() {
    return id;
  }

  public Map<String, Object> getHeaders() {
    return headers;
  }

  public String getEndpoint() {
    return endpoint;
  }

  public String getQueryString() {
    return queryString;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setHeaders(Map<String, Object> headers) {
    this.headers = headers;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public void setQueryString(String queryString) {
    this.queryString = queryString;
  }

  public Integer getResponseStatus() {
    return responseStatus;
  }

  public void setResponseStatus(Integer responseStatus) {
    this.responseStatus = responseStatus;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }
}

package com.flightinportugal.FlightInfoApi.configuration;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Provides easy access to properties stored in application.properties through getter methods
 *
 */
@ConfigurationProperties(prefix = "flightinfoapi.config")
@Component
public class FlightInfoApiProperties {
  private String kiwiApiBaseEndpoint;
  private String kiwiApiFlightsEndpoint;
  private String kiwiPartner;
  private String currency;
  private String datePattern;
  private List<String> validAirportCodes;
  private List<String> validAirlineCodes;

  public String getKiwiApiBaseEndpoint() {
    return kiwiApiBaseEndpoint;
  }

  public void setKiwiApiBaseEndpoint(String kiwiApiBaseEndpoint) {
    this.kiwiApiBaseEndpoint = kiwiApiBaseEndpoint;
  }

  public String getKiwiApiFlightsEndpoint() {
    return kiwiApiFlightsEndpoint;
  }

  public void setKiwiApiFlightsEndpoint(String kiwiApiFlightsEndpoint) {
    this.kiwiApiFlightsEndpoint = kiwiApiFlightsEndpoint;
  }

  public String getKiwiPartner() {
    return kiwiPartner;
  }

  public void setKiwiPartner(String kiwiPartner) {
    this.kiwiPartner = kiwiPartner;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getDatePattern() {
    return datePattern;
  }

  public void setDatePattern(String datePattern) {
    this.datePattern = datePattern;
  }

  public List<String> getValidAirportCodes() {
    return validAirportCodes;
  }

  public void setValidAirportCodes(List<String> validAirportCodes) {
    this.validAirportCodes = validAirportCodes;
  }

  public List<String> getValidAirlineCodes() {
    return validAirlineCodes;
  }

  public void setValidAirlineCodes(List<String> validAirlineCodes) {
    this.validAirlineCodes = validAirlineCodes;
  }

}

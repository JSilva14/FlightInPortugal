package com.flightinportugal.FlightInfoApi.criteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * A POJO representation of the possible query params of this API's "Get Flights" endpoint.
 * 
 */
@JsonInclude(Include.NON_NULL)
public class FlightCriteria {

  /**
   * <strong>Required</strong>
   * <p>
   * Flight origin city.
   * <p>
   * This parameter is set automatically based on the value of {@code to} but it was added to be
   * used in possible future versions of this API that may not refer only to flights from OPO->LIS
   * and LIS->OPO
   */
  private String from;
  /**
   * <strong>Optional</strong>
   * <p>
   * Flight destination city.
   */
  private String to;
  /**
   * <strong>Required</strong>
   * <p>
   * Search for flights after this date in dd/MM/yyyy format.
   */
  @JsonFormat(pattern = "dd/MM/yyyy")
  private String dateFrom;
  /**
   * <strong>Required</strong>
   * <p>
   * Search for flights until this date.
   */
  @JsonFormat(pattern = "dd/MM/yyyy")
  private String dateTo;

  public FlightCriteria() {
    super();
  }

  public FlightCriteria(String to, String from, String dateFrom, String dateTo) {
    super();
    this.to = to;
    this.from = from;
    this.dateFrom = dateFrom;
    this.dateTo = dateTo;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
    // automatically set "to" based on the value of "from"
    // should be removed if LIS->OPO and OPO->LIS restriction is no longed relevant
    if (to == null) {
      to = (from.equals("OPO")) ? "LIS" : "OPO";
    }
  }

  public String getDateFrom() {
    return dateFrom;
  }

  public void setDateFrom(String dateFrom) {
    this.dateFrom = dateFrom;
  }

  public String getDateTo() {
    return dateTo;
  }

  public void setDateTo(String dateTo) {
    this.dateTo = dateTo;
  }

}

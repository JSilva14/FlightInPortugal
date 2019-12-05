package com.flightinportugal.FlightInfoApi.criteria;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

/**
 * A POJO representation of the possible query params of this API's "Get
 * Flights" endpoint.
 * 
 */
@JsonInclude(Include.NON_NULL)
public class FlightCriteria {

	/**
	 * <strong>Required</strong>
	 * <p>
	 * Flight origin city.
	 * <p>
	 * This parameter is set automatically based on the value of {@code to} but it
	 * was added to be used in possible future versions of this API that may not
	 * refer only to flights from OPO->LIS and LIS->OPO
	 */
	@JsonProperty("fly_from")
	private String from;
	/**
	 * <strong>Optional</strong>
	 * <p>
	 * Flight destination city.
	 */
	@JsonProperty("fly_to")
	private String to;
	/**
	 * <strong>Required</strong>
	 * <p>
	 * Search for flights after this date in dd/MM/yyyy format.
	 */
	@JsonProperty("date_from")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateFrom;
	/**
	 * <strong>Required</strong>
	 * <p>
	 * Search for flights until this date.
	 */
	@JsonProperty("date_to")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateTo;

	public FlightCriteria() {
		super();
	}

	public FlightCriteria(String to, String from, LocalDate dateFrom, LocalDate dateTo) {
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

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

}

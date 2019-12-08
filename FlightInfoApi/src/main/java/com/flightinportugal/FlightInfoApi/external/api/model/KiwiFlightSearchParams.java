package com.flightinportugal.FlightInfoApi.external.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KiwiFlightSearchParams {

	@JsonProperty("to_type")
	private String toType;
	@JsonProperty("flyFrom_type")
	private String flyFromType;

	public KiwiFlightSearchParams() {
		super();
	}

	public KiwiFlightSearchParams(String toType, String flyFromType) {
		super();
		this.toType = toType;
		this.flyFromType = flyFromType;
	}

	public String getToType() {
		return toType;
	}

	public void setToType(String toType) {
		this.toType = toType;
	}

	public String getFlyFromType() {
		return flyFromType;
	}

	public void setFlyFromType(String flyFromType) {
		this.flyFromType = flyFromType;
	}

	@Override
	public String toString() {
		return "FlightSearchParams [toType=" + toType + ", flyFromType=" + flyFromType + "]";
	}

}
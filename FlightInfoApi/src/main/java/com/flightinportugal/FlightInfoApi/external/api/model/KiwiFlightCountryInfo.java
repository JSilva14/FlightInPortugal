package com.flightinportugal.FlightInfoApi.external.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KiwiFlightCountryInfo {
	
	@JsonProperty("code")
	private String code;
	@JsonProperty("name")
	private String name;

	public KiwiFlightCountryInfo() {
		super();
	}

	public KiwiFlightCountryInfo(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CountryInfo [code=" + code + ", name=" + name + "]";
	}
	
	
	
}
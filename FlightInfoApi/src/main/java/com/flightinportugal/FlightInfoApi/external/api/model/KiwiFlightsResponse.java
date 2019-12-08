package com.flightinportugal.FlightInfoApi.external.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KiwiFlightsResponse {

	@JsonProperty("search_params")
	private KiwiFlightSearchParams searchParams;
	@JsonProperty("time")
	private Integer time;
	// TODO: create POJO for connections
	@JsonProperty("connections")
	private List<String> connections;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("currency_rate")
	private Double currencyRate;
	@JsonProperty("data")
	private List<KiwiFlightData> data;

	public KiwiFlightsResponse() {
		super();
	}

	public KiwiFlightsResponse(KiwiFlightSearchParams searchParams, Integer time, List<String> connections, String currency,
			Double currencyRate, List<KiwiFlightData> data) {
		super();
		this.searchParams = searchParams;
		this.time = time;
		this.connections = connections;
		this.currency = currency;
		this.currencyRate = currencyRate;
		this.data = data;
	}

	public KiwiFlightSearchParams getSearchParams() {
		return searchParams;
	}

	public void setSearchParams(KiwiFlightSearchParams searchParams) {
		this.searchParams = searchParams;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public List<String> getConnections() {
		return connections;
	}

	public void setConnections(List<String> connections) {
		this.connections = connections;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(Double currencyRate) {
		this.currencyRate = currencyRate;
	}

	public List<KiwiFlightData> getData() {
		return data;
	}

	public void setData(List<KiwiFlightData> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "FlightsResponse [searchParams=" + searchParams + ", time=" + time + ", connections=" + connections
				+ ", currency=" + currency + ", currencyRate=" + currencyRate + ", data=" + data + "]";
	}

	
	
}

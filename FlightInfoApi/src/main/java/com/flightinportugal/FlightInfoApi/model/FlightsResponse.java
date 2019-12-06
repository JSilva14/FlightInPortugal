package com.flightinportugal.FlightInfoApi.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flightinportugal.FlightInfoApi.kiwiclient.model.KiwiFlightData;
import com.flightinportugal.FlightInfoApi.kiwiclient.model.KiwiFlightsResponse;

/**
 * Model class for this API's "Get Flights" response
 *
 */
public class FlightsResponse {

	private static final String CURRENCY = "EUR";

	/**
	 * Flight origin
	 */
	private String origin;
	/**
	 * Flight destination
	 */
	private String destination;
	/**
	 * Flights departure time
	 */
	private Long departureTime;
	/**
	 * FLights arrival time
	 */

	private Long arrivalTime;
	/**
	 * Currency for the specified price
	 */
	private String currency;
	/**
	 * The price of the flight
	 */
	private Double price;
	/**
	 * Information regarding prices for checking one and two bags
	 */
	private BagPrices bagPrices;
	/**
	 * Flight travel distance in kilometers
	 */
	private Double distance;
	/**
	 * Flight duration in hours and minutes
	 */
	private String duration;

	public FlightsResponse() {
		super();
	}

	public FlightsResponse(String origin, String destination, Long departureTime, Long arrivalTime,
			String currency, Double price, BagPrices bagPrices, Double distance, String duration) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.currency = currency;
		this.price = price;
		this.bagPrices = bagPrices;
		this.distance = distance;
		this.duration = duration;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Long getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Long departureTime) {
		this.departureTime = departureTime;
	}

	public Long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public BagPrices getBagPrices() {
		return bagPrices;
	}

	public void setBagPrices(BagPrices bagPrices) {
		this.bagPrices = bagPrices;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Factory constructor to instantiate a {@link FlightsResponse} object using a
	 * {@link KiwiFlightData} instance
	 * 
	 * @param kiwiFlightData An instance of {@link KiwiFlightData} extracted from
	 *                       {@link KiwiFlightsResponse}
	 * @return the instance of {@link FlightsResponse} that was built
	 */
	public static FlightsResponse fromKiwiFlightData(KiwiFlightData kiwiFlightData) {

		BagPrices bagPrices = new BagPrices(kiwiFlightData.getBagsPrice().get("1"),
				kiwiFlightData.getBagsPrice().get("2"));

		FlightsResponse flightInfo = new FlightsResponse(kiwiFlightData.getFlyFrom(),
				kiwiFlightData.getFlyTo(), kiwiFlightData.getdTime(), kiwiFlightData.getaTime(),
				CURRENCY, kiwiFlightData.getPrice(), bagPrices,
				kiwiFlightData.getDistance(), kiwiFlightData.getFlyDuration());

		return flightInfo;
	}

}

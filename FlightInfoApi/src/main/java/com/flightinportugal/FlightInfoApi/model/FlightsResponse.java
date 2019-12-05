package com.flightinportugal.FlightInfoApi.model;

import org.springframework.beans.factory.annotation.Autowired;

import com.flightinportugal.FlightInfoApi.configuration.FlightInfoApiConfiguration;
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

	public FlightsResponse(String origin, String destination, String currency, Double price,
			BagPrices bagPrices, Double distance, String duration) {
		super();
		this.origin = origin;
		this.destination = destination;
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

		FlightsResponse flightInfo = new FlightsResponse(kiwiFlightData.getFlyFrom(),
				kiwiFlightData.getFlyTo(), CURRENCY, kiwiFlightData.getPrice(), null,
				kiwiFlightData.getDistance(), kiwiFlightData.getFlyDuration());

		return flightInfo;
	}

}

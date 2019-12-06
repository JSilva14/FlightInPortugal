package com.flightinportugal.FlightInfoApi.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.flightinportugal.FlightInfoApi.kiwiclient.model.KiwiFlightData;
import com.flightinportugal.FlightInfoApi.kiwiclient.model.KiwiFlightsResponse;

public class FlightsAverageResponse {

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
	 * The average price of the flights between the specified origin and destination
	 */
	private Double priceAverage;
	/**
	 * Information regarding prices for checking one and two bags
	 */
	private BagPrices bagPricesAverage;

	public FlightsAverageResponse() {
		super();
	}

	public FlightsAverageResponse(String origin, String destination, String currency,
			Double priceAverage, BagPrices bagPricesAverage) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.currency = currency;
		this.priceAverage = priceAverage;
		this.bagPricesAverage = bagPricesAverage;
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

	public Double getPriceAverage() {
		return priceAverage;
	}

	public void setPriceAverage(Double priceAverage) {
		this.priceAverage = priceAverage;
	}

	public BagPrices getBagPricesAverage() {
		return bagPricesAverage;
	}

	public void setBagPricesAverage(BagPrices bagPricesAverage) {
		this.bagPricesAverage = bagPricesAverage;
	}

	/**
	 * Factory constructor to instantiate a FlightInfo object using a
	 * {@link KiwiFlightData} instance
	 * 
	 * @param kiwiFlightData An instance of {@link KiwiFlightData} extracted from
	 *                       {@link KiwiFlightsResponse}
	 * @return the instance of {@link FlightsResponse} that was built
	 */

	public static FlightsAverageResponse fromKiwiFlightsResponse(
			KiwiFlightsResponse kiwiFlightsResponse) {

		List<KiwiFlightData> kiwiFlights = kiwiFlightsResponse.getData();

		return new FlightsAverageResponse(kiwiFlights.get(0).getFlyFrom(),
				kiwiFlights.get(1).getFlyTo(), CURRENCY, calculateAveragePriceFromList(kiwiFlights),
				calculateAverageBagPrices(kiwiFlights));
	}

	private static Double calculateAveragePriceFromList(List<KiwiFlightData> kiwiFlights) {

		Double sumPrices = 0.0;

		for (KiwiFlightData kiwiFlight : kiwiFlights) {
			sumPrices += kiwiFlight.getPrice();
		}

		BigDecimal bd = BigDecimal.valueOf(sumPrices / kiwiFlights.size());
		bd = bd.setScale(2, RoundingMode.HALF_UP);

		return bd.doubleValue();

	}

	private static BagPrices calculateAverageBagPrices(List<KiwiFlightData> kiwiFlights) {

		Double sumBagOne = 0.0;
		Double sumBagTwo = 0.0;

		for (KiwiFlightData kiwiFlight : kiwiFlights) {
			sumBagOne += kiwiFlight.getBagsPrice().get("1");
			sumBagTwo += kiwiFlight.getBagsPrice().get("2");
		}

		BigDecimal averageOne = BigDecimal.valueOf(sumBagOne / kiwiFlights.size());
		averageOne = averageOne.setScale(2, RoundingMode.HALF_UP);
		BigDecimal averageTwo = BigDecimal.valueOf(sumBagTwo / kiwiFlights.size());
		averageTwo = averageTwo.setScale(2, RoundingMode.HALF_UP);

		return new BagPrices(averageOne.doubleValue(), averageTwo.doubleValue());
	}
}

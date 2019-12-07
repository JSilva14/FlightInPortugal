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
				kiwiFlights.get(1).getFlyTo(), CURRENCY, calculateAverageFlightPrice(kiwiFlights),
				calculateAverageBagPrices(kiwiFlights));
	}

	/**
	 * Calculates the average price of flights in a List of {@link KiwiFlightData}
	 * instances
	 * 
	 * @param kiwiFlights the list of flights to use in the calculation
	 * @return the average price of all flights
	 */
	private static Double calculateAverageFlightPrice(List<KiwiFlightData> kiwiFlights) {

		Double sumPrices = 0.0;

		for (KiwiFlightData kiwiFlight : kiwiFlights) {
			sumPrices += kiwiFlight.getPrice();
		}

		BigDecimal bd = BigDecimal.valueOf(sumPrices / kiwiFlights.size());
		bd = bd.setScale(2, RoundingMode.HALF_UP);

		return bd.doubleValue();

	}

	/**
	 * Calculates the average price of bag one and bag two for flights in a List of
	 * {@link KiwiFlightData} instances
	 * 
	 * @param kiwiFlights the list of flights to use in the calculation
	 * @return a {@link BagPrices} instance containing the average prices of bags
	 */
	private static BagPrices calculateAverageBagPrices(List<KiwiFlightData> kiwiFlights) {

		Double sumBagOne = 0.0;
		Double sumBagTwo = 0.0;

		// These are used as the T value in calculating avg as sum/T
		// There may be some cases where the value of a bag is not present in the
		// response and therefore we can't just divide the sum by the total amount of
		// flights.
		// It is necessary to check the total amount of times that a bag price was
		// present
		int totalOccurrencesOfBagOne = 0;
		int totalOccurencesOfBagTwo = 0;

		for (KiwiFlightData kiwiFlight : kiwiFlights) {

			Double priceOfOne = kiwiFlight.getBagsPrice().get("1");
			Double priceOfTwo = kiwiFlight.getBagsPrice().get("2");

			if (priceOfOne != null) {
				sumBagOne += priceOfOne;
				totalOccurrencesOfBagOne += 1;
			}

			if (priceOfTwo != null) {
				sumBagTwo += priceOfTwo;
				totalOccurencesOfBagTwo += 1;
			}
		}

		BigDecimal averageOne = BigDecimal.valueOf(sumBagOne / totalOccurrencesOfBagOne);
		averageOne = averageOne.setScale(2, RoundingMode.HALF_UP);
		BigDecimal averageTwo = BigDecimal.valueOf(sumBagTwo / totalOccurencesOfBagTwo);
		averageTwo = averageTwo.setScale(2, RoundingMode.HALF_UP);

		return new BagPrices(averageOne.doubleValue(), averageTwo.doubleValue());
	}
}

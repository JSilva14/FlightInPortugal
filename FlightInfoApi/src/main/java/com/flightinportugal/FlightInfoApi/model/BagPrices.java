package com.flightinportugal.FlightInfoApi.model;

/**
 * Contains information about bag costs
 *
 */
public class BagPrices {

	/**
	 * Currency applied to bag prices
	 */
	private String currency;
	/**
	 * The price of one checked bag
	 */
	private Double bagOne;
	/**
	 * The price of two checked bags
	 */
	private Double bagTwo;

	public BagPrices() {
		super();
	}

	public BagPrices(String currency, Double bagOne, Double bagTwo) {
		super();
		this.currency = currency;
		this.bagOne = bagOne;
		this.bagTwo = bagTwo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getBagOne() {
		return bagOne;
	}

	public void setBagOne(Double bagOne) {
		this.bagOne = bagOne;
	}

	public Double getBagTwo() {
		return bagTwo;
	}

	public void setBagTwo(Double bagTwo) {
		this.bagTwo = bagTwo;
	}

	
}

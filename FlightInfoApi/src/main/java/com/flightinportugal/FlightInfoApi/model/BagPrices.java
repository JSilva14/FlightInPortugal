package com.flightinportugal.FlightInfoApi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Contains information about bag costs
 *
 */
public class BagPrices {

	/**
	 * The price of one checked bag
	 */
	private Double oneBag;
	/**
	 * The price of two checked bags
	 */
	private Double twoBags;

	public BagPrices() {
		super();
	}

	public BagPrices(Double oneBag, Double twoBags) {
		super();
		this.oneBag = oneBag;
		this.twoBags = twoBags;
	}

	public Double getOneBag() {
		return oneBag;
	}

	public void setOneBag(Double oneBag) {
		this.oneBag = oneBag;
	}

	public Double getTwoBags() {
		return twoBags;
	}

	public void setTwoBags(Double twoBags) {
		this.twoBags = twoBags;
	}

}

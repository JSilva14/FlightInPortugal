package com.flightinportugal.FlightInfoApi.kiwiclient.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KiwiFlightData {

	@JsonProperty("mapIdfrom")
	private String mapIdFrom;
	@JsonProperty("return_duration")
	private String returnDuration;
	@JsonProperty("flyTo")
	private String flyTo;
	@JsonProperty("conversion")
	private Map<String, String> conversion;
	@JsonProperty("deep_link")
	private String deepLink;
	@JsonProperty("mapIdTo")
	private String mapIdTo;
	@JsonProperty("nightsInDest")
	private Integer nightsInDest;
	@JsonProperty("id")
	private String id;
	@JsonProperty("fly_duration")
	private String flyDuration;
	@JsonProperty("countryTo")
	private KiwiFlightCountryInfo countryTo;
	@JsonProperty("bags_price")
	private Map<String, Double> bagsPrice;
	@JsonProperty("aTimeUTC")
	private Long aTimeUTC;
	@JsonProperty("distance")
	private Double distance;
	@JsonProperty("price")
	private Double price;
	@JsonProperty("type_flights")
	private List<String> typeFlights;
	@JsonProperty("cityTo")
	private String cityTo;
	@JsonProperty("flyFrom")
	private String flyFrom;
	@JsonProperty("dTimeUTC")
	private Long dTimeUTC;
	@JsonProperty("countryFrom")
	private KiwiFlightCountryInfo countryFrom;
	@JsonProperty("dTime")
	private Long dTime;
	@JsonProperty("booking_token")
	private String bookingToken;
	@JsonProperty("cityFrom")
	private String cityFrom;
	@JsonProperty("aTime")
	private Long aTime;
	@JsonProperty("virtual_interlining")
	private Boolean virtualInterlining;
	@JsonProperty("throw_away_ticketing")
	private Boolean throwAwayTicketing;
	@JsonProperty("route")
	private List<KiwiFlightRoute> route;

	public KiwiFlightData() {
		super();
	}

	public KiwiFlightData(String mapIdFrom, String returnDuration, String flyTo,
			Map<String, String> conversion, String deepLink, String mapIdTo, Integer nightsInDest,
			String id, String flyDuration, KiwiFlightCountryInfo countryTo,
			Map<String, Double> bagsPrice, Long aTimeUTC, Double distance, Double price,
			List<String> typeFlights, String cityTo, String flyFrom, Long dTimeUTC,
			KiwiFlightCountryInfo countryFrom, Long dTime, String bookingToken, String cityFrom,
			Long aTime, Boolean virtualInterlining, Boolean throwAwayTicketing,
			List<KiwiFlightRoute> route) {
		super();
		this.mapIdFrom = mapIdFrom;
		this.returnDuration = returnDuration;
		this.flyTo = flyTo;
		this.conversion = conversion;
		this.deepLink = deepLink;
		this.mapIdTo = mapIdTo;
		this.nightsInDest = nightsInDest;
		this.id = id;
		this.flyDuration = flyDuration;
		this.countryTo = countryTo;
		this.bagsPrice = bagsPrice;
		this.aTimeUTC = aTimeUTC;
		this.distance = distance;
		this.price = price;
		this.typeFlights = typeFlights;
		this.cityTo = cityTo;
		this.flyFrom = flyFrom;
		this.dTimeUTC = dTimeUTC;
		this.countryFrom = countryFrom;
		this.dTime = dTime;
		this.bookingToken = bookingToken;
		this.cityFrom = cityFrom;
		this.aTime = aTime;
		this.virtualInterlining = virtualInterlining;
		this.throwAwayTicketing = throwAwayTicketing;
		this.route = route;
	}

	public String getMapIdFrom() {
		return mapIdFrom;
	}

	public void setMapIdFrom(String mapIdFrom) {
		this.mapIdFrom = mapIdFrom;
	}

	public String getReturnDuration() {
		return returnDuration;
	}

	public void setReturnDuration(String returnDuration) {
		this.returnDuration = returnDuration;
	}

	public String getFlyTo() {
		return flyTo;
	}

	public void setFlyTo(String flyTo) {
		this.flyTo = flyTo;
	}

	public Map<String, String> getConversion() {
		return conversion;
	}

	public void setConversion(Map<String, String> conversion) {
		this.conversion = conversion;
	}

	public String getDeepLink() {
		return deepLink;
	}

	public void setDeepLink(String deepLink) {
		this.deepLink = deepLink;
	}

	public String getMapIdTo() {
		return mapIdTo;
	}

	public void setMapIdTo(String mapIdTo) {
		this.mapIdTo = mapIdTo;
	}

	public Integer getNightsInDest() {
		return nightsInDest;
	}

	public void setNightsInDest(Integer nightsInDest) {
		this.nightsInDest = nightsInDest;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFlyDuration() {
		return flyDuration;
	}

	public void setFlyDuration(String flyDuration) {
		this.flyDuration = flyDuration;
	}

	public KiwiFlightCountryInfo getCountryTo() {
		return countryTo;
	}

	public void setCountryTo(KiwiFlightCountryInfo countryTo) {
		this.countryTo = countryTo;
	}

	public Map<String, Double> getBagsPrice() {
		return bagsPrice;
	}

	public void setBagsPrice(Map<String, Double> bagsPrice) {
		this.bagsPrice = bagsPrice;
	}

	public Long getaTimeUTC() {
		return aTimeUTC;
	}

	public void setaTimeUTC(Long aTimeUTC) {
		this.aTimeUTC = aTimeUTC;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<String> getTypeFlights() {
		return typeFlights;
	}

	public void setTypeFlights(List<String> typeFlights) {
		this.typeFlights = typeFlights;
	}

	public String getCityTo() {
		return cityTo;
	}

	public void setCityTo(String cityTo) {
		this.cityTo = cityTo;
	}

	public String getFlyFrom() {
		return flyFrom;
	}

	public void setFlyFrom(String flyFrom) {
		this.flyFrom = flyFrom;
	}

	public Long getdTimeUTC() {
		return dTimeUTC;
	}

	public void setdTimeUTC(Long dTimeUTC) {
		this.dTimeUTC = dTimeUTC;
	}

	public KiwiFlightCountryInfo getCountryFrom() {
		return countryFrom;
	}

	public void setCountryFrom(KiwiFlightCountryInfo countryFrom) {
		this.countryFrom = countryFrom;
	}

	public Long getdTime() {
		return dTime;
	}

	public void setdTime(Long dTime) {
		this.dTime = dTime;
	}

	public String getBookingToken() {
		return bookingToken;
	}

	public void setBookingToken(String bookingToken) {
		this.bookingToken = bookingToken;
	}

	public String getCityFrom() {
		return cityFrom;
	}

	public void setCityFrom(String cityFrom) {
		this.cityFrom = cityFrom;
	}

	public Long getaTime() {
		return aTime;
	}

	public void setaTime(Long aTime) {
		this.aTime = aTime;
	}

	public Boolean getVirtualInterlining() {
		return virtualInterlining;
	}

	public void setVirtualInterlining(Boolean virtualInterlining) {
		this.virtualInterlining = virtualInterlining;
	}

	public Boolean getThrowAwayTicketing() {
		return throwAwayTicketing;
	}

	public void setThrowAwayTicketing(Boolean throwAwayTicketing) {
		this.throwAwayTicketing = throwAwayTicketing;
	}

	public List<KiwiFlightRoute> getRoute() {
		return route;
	}

	public void setRoute(List<KiwiFlightRoute> route) {
		this.route = route;
	}

	@Override
	public String toString() {
		return "FlightData [mapIdFrom=" + mapIdFrom + ", returnDuration=" + returnDuration
				+ ", flyTo=" + flyTo + ", conversion=" + conversion + ", deepLink=" + deepLink
				+ ", mapIdTo=" + mapIdTo + ", nightsInDest=" + nightsInDest + ", id=" + id
				+ ", flyDuration=" + flyDuration + ", countryTo=" + countryTo + ", aTimeUTC="
				+ aTimeUTC + ", distance=" + distance + ", price=" + price + ", typeFlights="
				+ typeFlights + ", cityTo=" + cityTo + ", flyFrom=" + flyFrom + ", dTimeUTC="
				+ dTimeUTC + ", countryFrom=" + countryFrom + ", dTime=" + dTime + ", bookingToken="
				+ bookingToken + ", cityFrom=" + cityFrom + ", aTime=" + aTime
				+ ", virtualInterlining=" + virtualInterlining + ", throwAwayTicketing="
				+ throwAwayTicketing + ", route=" + route + "]";
	}

}
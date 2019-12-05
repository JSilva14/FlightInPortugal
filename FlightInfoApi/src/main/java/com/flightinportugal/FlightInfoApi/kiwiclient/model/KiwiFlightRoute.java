package com.flightinportugal.FlightInfoApi.kiwiclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KiwiFlightRoute {

	@JsonProperty("bags_recheck_required")
	private Boolean bagsRecheckRequired;
	@JsonProperty("aTimeUTC")
	private Long aTimeUTC;
	@JsonProperty("mapIdFrom")
	private String mapIdFrom;
	@JsonProperty("mapIdTo")
	private String mapIdTo;
	@JsonProperty("flight_no")
	private Integer flightNo;
	@JsonProperty("dTime")
	private Long dTime;
	@JsonProperty("latTo")
	private Double latTo;
	@JsonProperty("flyTo")
	private String flyTo;
	@JsonProperty("return")
	private Byte returnFlag;
	@JsonProperty("id")
	private String id;
	@JsonProperty("airline")
	private String airline;
	@JsonProperty("fare_basis")
	private String fareBasis;
	@JsonProperty("fare_family")
	private String fareFamily;
	@JsonProperty("fare_classes")
	private String fareClasses;
	@JsonProperty("lngTo")
	private Double lngTo;
	@JsonProperty("cityTo")
	private String cityTo;
	@JsonProperty("cityFrom")
	private String cityFrom;
	@JsonProperty("lngFrom")
	private Double lngFrom;
	@JsonProperty("aTime")
	private Long aTime;
	@JsonProperty("flyFrom")
	private String flyFrom;
	@JsonProperty("latFrom")
	private Double latFrom;
	@JsonProperty("dTimeUTC")
	private Long dTimeUTC;
	@JsonProperty("operating_carrier")
	private String operatingCarrier;

	public KiwiFlightRoute() {
		super();
	}

	public KiwiFlightRoute(Boolean bagsRecheckRequired, Long aTimeUTC, String mapIdFrom, String mapIdTo, Integer flightNo,
			Long dTime, Double latTo, String flyTo, Byte returnFlag, String id, String airline, String fareBasis,
			String fareFamily, String fareClasses, Double lngTo, String cityTo, String cityFrom, Double lngFrom,
			Long aTime, String flyFrom, Double latFrom, Long dTimeUTC, String operatingCarrier) {
		super();
		this.bagsRecheckRequired = bagsRecheckRequired;
		this.aTimeUTC = aTimeUTC;
		this.mapIdFrom = mapIdFrom;
		this.mapIdTo = mapIdTo;
		this.flightNo = flightNo;
		this.dTime = dTime;
		this.latTo = latTo;
		this.flyTo = flyTo;
		this.returnFlag = returnFlag;
		this.id = id;
		this.airline = airline;
		this.fareBasis = fareBasis;
		this.fareFamily = fareFamily;
		this.fareClasses = fareClasses;
		this.lngTo = lngTo;
		this.cityTo = cityTo;
		this.cityFrom = cityFrom;
		this.lngFrom = lngFrom;
		this.aTime = aTime;
		this.flyFrom = flyFrom;
		this.latFrom = latFrom;
		this.dTimeUTC = dTimeUTC;
		this.operatingCarrier = operatingCarrier;
	}

	public Boolean getBagsRecheckRequired() {
		return bagsRecheckRequired;
	}

	public void setBagsRecheckRequired(Boolean bagsRecheckRequired) {
		this.bagsRecheckRequired = bagsRecheckRequired;
	}

	public Long getaTimeUTC() {
		return aTimeUTC;
	}

	public void setaTimeUTC(Long aTimeUTC) {
		this.aTimeUTC = aTimeUTC;
	}

	public String getMapIdFrom() {
		return mapIdFrom;
	}

	public void setMapIdFrom(String mapIdFrom) {
		this.mapIdFrom = mapIdFrom;
	}

	public String getMapIdTo() {
		return mapIdTo;
	}

	public void setMapIdTo(String mapIdTo) {
		this.mapIdTo = mapIdTo;
	}

	public Integer getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(Integer flightNo) {
		this.flightNo = flightNo;
	}

	public Long getdTime() {
		return dTime;
	}

	public void setdTime(Long dTime) {
		this.dTime = dTime;
	}

	public Double getLatTo() {
		return latTo;
	}

	public void setLatTo(Double latTo) {
		this.latTo = latTo;
	}

	public String getFlyTo() {
		return flyTo;
	}

	public void setFlyTo(String flyTo) {
		this.flyTo = flyTo;
	}

	public Byte getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(Byte returnFlag) {
		this.returnFlag = returnFlag;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getFareBasis() {
		return fareBasis;
	}

	public void setFareBasis(String fareBasis) {
		this.fareBasis = fareBasis;
	}

	public String getFareFamily() {
		return fareFamily;
	}

	public void setFareFamily(String fareFamily) {
		this.fareFamily = fareFamily;
	}

	public String getFareClasses() {
		return fareClasses;
	}

	public void setFareClasses(String fareClasses) {
		this.fareClasses = fareClasses;
	}

	public Double getLngTo() {
		return lngTo;
	}

	public void setLngTo(Double lngTo) {
		this.lngTo = lngTo;
	}

	public String getCityTo() {
		return cityTo;
	}

	public void setCityTo(String cityTo) {
		this.cityTo = cityTo;
	}

	public String getCityFrom() {
		return cityFrom;
	}

	public void setCityFrom(String cityFrom) {
		this.cityFrom = cityFrom;
	}

	public Double getLngFrom() {
		return lngFrom;
	}

	public void setLngFrom(Double lngFrom) {
		this.lngFrom = lngFrom;
	}

	public Long getaTime() {
		return aTime;
	}

	public void setaTime(Long aTime) {
		this.aTime = aTime;
	}

	public String getFlyFrom() {
		return flyFrom;
	}

	public void setFlyFrom(String flyFrom) {
		this.flyFrom = flyFrom;
	}

	public Double getLatFrom() {
		return latFrom;
	}

	public void setLatFrom(Double latFrom) {
		this.latFrom = latFrom;
	}

	public Long getdTimeUTC() {
		return dTimeUTC;
	}

	public void setdTimeUTC(Long dTimeUTC) {
		this.dTimeUTC = dTimeUTC;
	}

	public String getOperatingCarrier() {
		return operatingCarrier;
	}

	public void setOperatingCarrier(String operatingCarrier) {
		this.operatingCarrier = operatingCarrier;
	}

	@Override
	public String toString() {
		return "FlightRoute [bagsRecheckRequired=" + bagsRecheckRequired + ", aTimeUTC=" + aTimeUTC + ", mapIdFrom="
				+ mapIdFrom + ", mapIdTo=" + mapIdTo + ", flightNo=" + flightNo + ", dTime=" + dTime + ", latTo="
				+ latTo + ", flyTo=" + flyTo + ", returnFlag=" + returnFlag + ", id=" + id + ", airline=" + airline
				+ ", fareBasis=" + fareBasis + ", fareFamily=" + fareFamily + ", fareClasses=" + fareClasses
				+ ", lngTo=" + lngTo + ", cityTo=" + cityTo + ", cityFrom=" + cityFrom + ", lngFrom=" + lngFrom
				+ ", aTime=" + aTime + ", flyFrom=" + flyFrom + ", latFrom=" + latFrom + ", dTimeUTC=" + dTimeUTC
				+ ", operatingCarrier=" + operatingCarrier + "]";
	}

	
}

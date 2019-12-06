package com.flightinportugal.FlightInfoApi.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.flightinportugal.FlightInfoApi.criteria.FlightCriteria;
import com.flightinportugal.FlightInfoApi.exception.FlightCriteriaValidationException;
import com.flightinportugal.FlightInfoApi.model.BagPrices;
import com.flightinportugal.FlightInfoApi.model.FlightsAverageResponse;
import com.flightinportugal.FlightInfoApi.model.FlightsResponse;
import com.flightinportugal.FlightInfoApi.service.FlightInfoService;

@SpringBootTest()
@AutoConfigureMockMvc
public class FlightInfoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	FlightInfoService service;

	private final List<FlightsResponse> mockFlightsResponseList = List.of(new FlightsResponse("OPO",
			"LIS", 1575784800L, 1575788400L, "EUR", 100.0, new BagPrices(10.0, 20.0), 200.0, "1h"));

	private final FlightsAverageResponse mockFlightsAverageResponse = new FlightsAverageResponse(
			"OPO", "LIS", "EUR", 100.0, new BagPrices(10.0, 20.0));

	private final String expectedGetFlightsResponse = "[{\"origin\":\"OPO\","
			+ "\"destination\":\"LIS\"," + "\"departureTime\":1575784800,"
			+ "\"arrivalTime\":1575788400," + "\"currency\":\"EUR\"," + "\"price\":100.0,"
			+ "\"bagPrices\":{" + "\"oneBag\":10.0," + "\"twoBags\":20.0}," + "\"distance\":200.0,"
			+ "\"duration\":\"1h\"}]";

	private final String expectedGetAverageFlightPricesResponse = "{\"origin\":\"OPO\","
			+ "\"destination\":\"LIS\"," + "\"currency\":\"EUR\"," + "\"priceAverage\":100.0,"
			+ "\"bagPricesAverage\":{" + "\"oneBag\":10.0," + "\"twoBags\":20.0}}";

	/*
	 * getFlights() tests
	 */

	@Test
	public void getFlights_ValidRequest_ShouldReturn200AndListOfFlights() throws Exception {

		Mockito.when(service.getFlights(Mockito.any(FlightCriteria.class)))
				.thenReturn(mockFlightsResponseList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/flights?from=LIS&dateFrom=08/12/2019&dateTo=08/12/2019")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getStatus(), 200);
		assertEquals(result.getResponse().getContentAsString(), expectedGetFlightsResponse);

	}
	
	@Test
	public void getFlights_InvalidFromParameter_ShouldReturn400AndMessage() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/flights?from=CZ&dateFrom=08/12/2019&dateTo=08/12/2019")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(result.getResponse().getStatus(), 400);
	}

	/*
	 * getAverageFlightPrices Tests
	 */

	@Test
	public void getAverageFlightPrices_ValidRequest_ShouldReturn200AndFlightAverage()
			throws Exception {

		Mockito.when(service.getAverageFlightPrices(Mockito.any(FlightCriteria.class)))
				.thenReturn(mockFlightsAverageResponse);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/flights/avg?from=LIS&dateFrom=08/12/2019&dateTo=08/12/2019")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getStatus(), 200);
		assertEquals(result.getResponse().getContentAsString(),
				expectedGetAverageFlightPricesResponse);
	}
}

package com.flightinportugal.FlightInfoApi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
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
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import com.flightinportugal.FlightInfoApi.model.BagPrices;
import com.flightinportugal.FlightInfoApi.model.FlightsAverageResponse;
import com.flightinportugal.FlightInfoApi.model.FlightsResponse;
import com.flightinportugal.FlightInfoApi.repository.FlightsRequestRepository;
import com.flightinportugal.FlightInfoApi.service.FlightInfoService;

@SpringBootTest
@AutoConfigureMockMvc
public class FlightInfoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  FlightsRequestRepository repository;

  @MockBean
  FlightInfoService service;

  private final List<FlightsResponse> mockFlightsResponseList = List.of(new FlightsResponse("OPO",
      "LIS", 1575784800L, 1575788400L, "EUR", 100.0, new BagPrices(10.0, 20.0), 200.0, "1h"));

  private final String expectedGetFlightsResponseBody = "[{\"origin\":\"OPO\","
      + "\"destination\":\"LIS\"," + "\"departureTime\":1575784800," + "\"arrivalTime\":1575788400,"
      + "\"currency\":\"EUR\"," + "\"price\":100.0," + "\"bagPrices\":{" + "\"oneBag\":10.0,"
      + "\"twoBags\":20.0}," + "\"distance\":200.0," + "\"duration\":\"1h\"}]";

  private final String expectedGetAverageFlightPricesResponseBody = "{\"origin\":\"OPO\","
      + "\"destination\":\"LIS\"," + "\"currency\":\"EUR\"," + "\"priceAverage\":100.0,"
      + "\"bagPricesAverage\":{" + "\"oneBag\":10.0," + "\"twoBags\":20.0}}";

  /*
   * Tomorrow's date
   * 
   * Tomorrow is used instead of today to eliminate errors where the date is generated at 23:59 and
   * the request is made at 00:00 which makes the generated date be in the past and therefore
   * invalid
   */
  private static String tomorrowDate;

  /*
   * Yesterday's date
   */
  private static String yesterdayDate;

  /*
   * Day after tomorrow's date
   */
  private static String afterTomorrowDate;

  @BeforeAll
  private static void init() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    tomorrowDate = LocalDate.now().plusDays(1).format(formatter);
    yesterdayDate = LocalDate.now().minusDays(1).format(formatter);
    afterTomorrowDate = LocalDate.now().plusDays(2).format(formatter);
  }

  /*
   * getFlights() tests
   */

  @Test
  public void getFlights_ValidRequest_ShouldReturn200AndListOfFlights() throws Exception {

    Mockito.when(service.getFlights(Mockito.any(FlightCriteria.class)))
        .thenReturn(mockFlightsResponseList);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(200, result.getResponse().getStatus());
    JSONAssert.assertEquals(result.getResponse().getContentAsString(),
        expectedGetFlightsResponseBody, JSONCompareMode.LENIENT);

  }

  @Test
  public void getFlights_NoParametersGiven_ShouldReturn400AndRelevantMessage() throws Exception {
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/flights").accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage()));
  }

  @Test
  public void getFlights_InvalidFromParameter_ShouldReturn400AndRelevantMessage() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=12345&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.INVALID_AIRPORT_CODE.getMessage()));
  }

  @Test
  public void getFlights_MissingFromParameter_ShouldReturn400AndRelevantMessage() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage()));
  }

  @Test
  public void getFlights_InvalidToParameter_ShouldReturn400AndRelevantMessage() throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&to=12345&dateFrom=" + tomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.INVALID_AIRPORT_CODE.getMessage()));
  }

  @Test
  public void getFlights_MissingToParameter_ShouldReturn200AndListOfFlights() throws Exception {
    Mockito.when(service.getFlights(Mockito.any(FlightCriteria.class)))
        .thenReturn(mockFlightsResponseList);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&dateFrom=" + tomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(200, result.getResponse().getStatus());
    JSONAssert.assertEquals(result.getResponse().getContentAsString(),
        expectedGetFlightsResponseBody, JSONCompareMode.LENIENT);
  }

  @Test
  public void getFlights_InvalidDateFromParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&to=OPO&dateFrom=invalid&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(
        result.getResponse().getContentAsString().contains(ErrorMessage.INVALID_DATE.getMessage()));
  }

  @Test
  public void getFlights_DateFromParameterIsInPast_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&to=OPO&dateFrom=" + yesterdayDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(
        result.getResponse().getContentAsString().contains(ErrorMessage.DATE_IN_PAST.getMessage()));
  }

  @Test
  public void getFlights_MissingDateFromParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&to=OPO&dateTo=" + tomorrowDate).accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage()));
  }

  @Test
  public void getFlights_InvalidDateToParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=invalid")
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(
        result.getResponse().getContentAsString().contains(ErrorMessage.INVALID_DATE.getMessage()));
  }

  @Test
  public void getFlights_DateToParameterIsInPast_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + yesterdayDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(
        result.getResponse().getContentAsString().contains(ErrorMessage.DATE_IN_PAST.getMessage()));
  }

  @Test
  public void getFlights_MissingDateToParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/flights?from=LIS&to=OPO&dateFrom=" + tomorrowDate)
            .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage()));
  }

  @Test
  public void getFlights_DateToIsBeforeDateFrom_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&to=OPO&dateFrom=" + afterTomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.DATE_COMPARISON.getMessage()));
  }

  /*
   * getAverageFlightPrices Tests
   */

  @Test
  public void getAverageFlightPrices_ValidRequest_ShouldReturn200AndFlightAverageResponse()
      throws Exception {

    FlightsAverageResponse mockFlightsAverageResponse = new FlightsAverageResponse(tomorrowDate,
        afterTomorrowDate, "OPO", "LIS", "EUR", 100.0, new BagPrices(10.0, 20.0));

    Mockito.when(service.getAverageFlightPrices(Mockito.any(FlightCriteria.class)))
        .thenReturn(mockFlightsAverageResponse);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/flights/avg?from=LIS&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + afterTomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(200, result.getResponse().getStatus());
    JSONAssert.assertEquals(expectedGetAverageFlightPricesResponseBody,
        result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
  }

  @Test
  public void getAverageFlightPrices_NoParametersGiven_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/flights/avg").accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_InvalidFromParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights/avg?from=12345&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.INVALID_AIRPORT_CODE.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_MissingFromParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights/avg?to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_InvalidToParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights/avg?from=LIS&to=12345&dateFrom=" + tomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.INVALID_AIRPORT_CODE.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_MissingToParameter_ShouldReturn200AndFlightAverageResponse()
      throws Exception {

    FlightsAverageResponse mockFlightsAverageResponse = new FlightsAverageResponse(tomorrowDate,
        afterTomorrowDate, "OPO", "LIS", "EUR", 100.0, new BagPrices(10.0, 20.0));

    Mockito.when(service.getAverageFlightPrices(Mockito.any(FlightCriteria.class)))
        .thenReturn(mockFlightsAverageResponse);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights/avg?from=LIS&dateFrom=" + tomorrowDate + "&dateTo=" + afterTomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(200, result.getResponse().getStatus());
    JSONAssert.assertEquals(expectedGetAverageFlightPricesResponseBody,
        result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);
  }

  @Test
  public void getAverageFlightPrices_InvalidDateFromParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights/avg?from=LIS&to=OPO&dateFrom=invalid&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(
        result.getResponse().getContentAsString().contains(ErrorMessage.INVALID_DATE.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_DateFromParameterIsInPast_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights/avg?from=LIS&to=OPO&dateFrom=" + yesterdayDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(
        result.getResponse().getContentAsString().contains(ErrorMessage.DATE_IN_PAST.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_MissingDateFromParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/flights/avg?from=LIS&to=OPO&dateTo=" + tomorrowDate)
            .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_InvalidDateToParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights/avg?from=LIS&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=invalid")
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(
        result.getResponse().getContentAsString().contains(ErrorMessage.INVALID_DATE.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_DateToParameterIsInPast_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights/avg?from=LIS&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + yesterdayDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(
        result.getResponse().getContentAsString().contains(ErrorMessage.DATE_IN_PAST.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_MissingDateToParameter_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/flights/avg?from=LIS&to=OPO&dateFrom=" + tomorrowDate)
            .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage()));
  }

  @Test
  public void getAverageFlightPrices_DateToIsBeforeDateFrom_ShouldReturn400AndRelevantMessage()
      throws Exception {
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/flights/avg?from=LIS&to=OPO&dateFrom=" + afterTomorrowDate + "&dateTo=" + tomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(400, result.getResponse().getStatus());
    assertTrue(result.getResponse().getContentAsString()
        .contains(ErrorMessage.DATE_COMPARISON.getMessage()));
  }
}

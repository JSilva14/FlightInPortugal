package com.flightinportugal.FlightInfoApi.requestpersistence;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.flightinportugal.FlightInfoApi.model.FlightApiRequestEntity;
import com.flightinportugal.FlightInfoApi.repository.FlightsRequestRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RequestPersistenceIntegrationTest {

  private final FlightApiRequestEntity mockRequest = new FlightApiRequestEntity("123",
      Instant.ofEpochSecond(1575839073), Collections.emptyMap(), "/flights", "from=LIS", 400);

  private final List<FlightApiRequestEntity> mockRequestList = List.of(mockRequest);

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FlightsRequestRepository mockRepository;


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

  @Test
  public void requestPersistence_GetRequests_PerformsFindAllOnce() throws Exception {

    Mockito.when(mockRepository.findAll()).thenReturn(mockRequestList);

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/requests").accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder);

    Mockito.verify(mockRepository, Mockito.times(1)).findAll();
  }

  @Test
  public void requestPersistence_GivenValidGetFlightsRequest_ShouldPersistRequestData()
      throws Exception {

    Mockito.when(mockRepository.save(Mockito.any(FlightApiRequestEntity.class)))
        .thenReturn(mockRequest);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=LIS&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + afterTomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder);

    Mockito.verify(mockRepository, Mockito.times(1))
        .save(Mockito.any(FlightApiRequestEntity.class));

  }

  @Test
  public void requestPersistence_GivenValidGetAvgFlightPricesRequest_ShouldPersistRequestData()
      throws Exception {

    Mockito.when(mockRepository.save(Mockito.any(FlightApiRequestEntity.class)))
        .thenReturn(mockRequest);

    RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
        "/flights/avg?from=LIS&to=OPO&dateFrom=" + tomorrowDate + "&dateTo=" + afterTomorrowDate)
        .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder);

    Mockito.verify(mockRepository, Mockito.times(1))
        .save(Mockito.any(FlightApiRequestEntity.class));

  }

  @Test
  public void requestPersistence_GivenInvalidGetFlightsRequest_ShouldPersistRequestData()
      throws Exception {

    Mockito.when(mockRepository.save(Mockito.any(FlightApiRequestEntity.class)))
        .thenReturn(mockRequest);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/flights?from=123&to=123&dateFrom=123&dateTo=123").accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder);

    Mockito.verify(mockRepository, Mockito.times(1))
        .save(Mockito.any(FlightApiRequestEntity.class));

  }

  @Test
  public void requestPersistence_GivenInvalidGetAvgFlightPricesRequest_ShouldPersistRequestData()
      throws Exception {

    Mockito.when(mockRepository.save(Mockito.any(FlightApiRequestEntity.class)))
        .thenReturn(mockRequest);

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/flights/avg?from=123&to=123&dateFrom=123&dateTo=123")
            .accept(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder);

    Mockito.verify(mockRepository, Mockito.times(1))
        .save(Mockito.any(FlightApiRequestEntity.class));
  }

}

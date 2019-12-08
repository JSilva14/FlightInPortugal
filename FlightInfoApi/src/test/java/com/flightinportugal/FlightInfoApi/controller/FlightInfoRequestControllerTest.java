package com.flightinportugal.FlightInfoApi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.flightinportugal.FlightInfoApi.model.FlightApiRequestEntity;
import com.flightinportugal.FlightInfoApi.repository.FlightsRequestRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FlightInfoRequestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  FlightsRequestRepository repository;


  private final List<FlightApiRequestEntity> mockFlightApiRequestList =
      List.of(new FlightApiRequestEntity("123", Instant.ofEpochSecond(1575839073),
          Collections.emptyMap(), "/flights", "from=LIS", 400));

  private final String expectedGetRequestsResponseBody = "[{\"id\":\"123\","
      + "\"timestamp\":\"2019-12-08T21:04:33Z\"," + "\"headers\":{}," + "\"endpoint\":\"/flights\","
      + "\"queryString\":\"from=LIS\"," + "\"responseStatus\":400}]";


  @Test
  public void getApiRequests_ValidRequest_ShouldReturn200AndListOfRequests() throws Exception {

    Mockito.when(repository.findAll()).thenReturn(mockFlightApiRequestList);

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/requests").accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(200, result.getResponse().getStatus());
    JSONAssert.assertEquals(expectedGetRequestsResponseBody,
        result.getResponse().getContentAsString(), JSONCompareMode.LENIENT);

  }

  @Test
  public void deleteApiRequests_ValidRequest_ShouldReturn204NoContent() throws Exception {

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.delete("/requests").accept(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();

    assertEquals(204, result.getResponse().getStatus());
    assertEquals("", result.getResponse().getContentAsString());

  }


}

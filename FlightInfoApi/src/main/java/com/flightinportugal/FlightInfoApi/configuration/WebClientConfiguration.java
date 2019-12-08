package com.flightinportugal.FlightInfoApi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientConfiguration {

  @Autowired
  FlightInfoApiProperties properties;

  @Bean
  public WebClient defaultWebClient() {

    // Increase the byte limit allocated to the WebClient's data buffer to about 1Mb.
    // This is used to allow serialization of large request bodies with jackson
    // since the default maximum limit of bytes per buffer is 262144 (0,26 Mb)
    ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
        .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1000)).build();

    return WebClient.builder().exchangeStrategies(exchangeStrategies)
        .baseUrl(properties.getKiwiApiBaseEndpoint())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
  }
}

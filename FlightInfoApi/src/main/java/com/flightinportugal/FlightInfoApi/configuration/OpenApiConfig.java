package com.flightinportugal.FlightInfoApi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI flightInfoOpenAPI() {
    return new OpenAPI().components(new Components())
        .info(new Info().title("Flight Information API").description(
            "API that provides flight information for flights between OPO and LIS by TAP and Ryanair"));
  }
}

package com.flightinportugal.FlightInfoApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FlightInfoApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(FlightInfoApiApplication.class, args);
  }
}

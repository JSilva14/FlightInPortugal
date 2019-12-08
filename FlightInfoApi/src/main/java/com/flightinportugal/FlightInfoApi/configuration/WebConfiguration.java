package com.flightinportugal.FlightInfoApi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.flightinportugal.FlightInfoApi.controller.interceptor.FlightInfoApiInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new FlightInfoApiInterceptor()).addPathPatterns("/flights",
        "/flights/avg");
  }

}

package com.flightinportugal.FlightInfoApi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.flightinportugal.FlightInfoApi.controller.interceptor.FlightInfoApiInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

  @Bean
  public FlightInfoApiInterceptor interceptor() {
    return new FlightInfoApiInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(interceptor()).addPathPatterns("/flights", "/flights/avg");
  }

}

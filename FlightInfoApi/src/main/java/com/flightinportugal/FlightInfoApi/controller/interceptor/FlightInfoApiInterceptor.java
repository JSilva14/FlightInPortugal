package com.flightinportugal.FlightInfoApi.controller.interceptor;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.flightinportugal.FlightInfoApi.repository.FlightsRequestRepository;

/**
 * Intercepts requests made to FlightInfoApiController methods and performs before and after
 * operations
 *
 */
@Component
public class FlightInfoApiInterceptor extends HandlerInterceptorAdapter {

  private static final Logger log = LoggerFactory.getLogger(FlightInfoApiInterceptor.class);

  @Autowired
  FlightsRequestRepository repository;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

    Enumeration<String> headerNames = request.getHeaderNames();
    Map<String, Object> headers = new HashMap<String, Object>();

    log.info("Received request on: " + request.getRequestURI());
    log.info("Query String: " + request.getQueryString());
    log.info("Headers: ");
    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        String currentHeader = headerNames.nextElement();
        log.info(currentHeader + ": " + request.getHeader(currentHeader));
        headers.put(headerNames.nextElement(), request.getHeader(headerNames.nextElement()));
      }
    }
    super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

    HttpHeaders headers = response.getHeaderNames().stream()
        .collect(Collectors.toMap(Function.identity(), h -> new ArrayList<>(response.getHeaders(h)),
            (oldValue, newValue) -> newValue, HttpHeaders::new));

    log.info(
        "Request handling finished with status code: " + HttpStatus.valueOf(response.getStatus()));


    super.afterCompletion(request, response, handler, ex);
  }


}

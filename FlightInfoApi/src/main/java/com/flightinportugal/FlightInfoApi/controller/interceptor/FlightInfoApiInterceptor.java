package com.flightinportugal.FlightInfoApi.controller.interceptor;

import java.time.Instant;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import com.flightinportugal.FlightInfoApi.model.FlightApiRequestEntity;
import com.flightinportugal.FlightInfoApi.repository.FlightsRequestRepository;

/**
 * Intercepts requests made to FlightInfoApiController methods and performs before and after
 * operations. It is mainly used to log request info and store it in MongoDB
 *
 */
@Component
public class FlightInfoApiInterceptor extends HandlerInterceptorAdapter {

  private static final Logger log = LoggerFactory.getLogger(FlightInfoApiInterceptor.class);

  @Autowired
  FlightsRequestRepository repository;

  /**
   * Used to log info about the intercepted request
   */
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
        headers.put(currentHeader, request.getHeader(currentHeader));
      }
    }
    super.postHandle(request, response, handler, modelAndView);
  }

  /**
   * After the request is complete, provides relevant logging and stores the request data in the
   * database through {@link FlightsRequestRepository}
   */
  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

    Enumeration<String> headerNames = request.getHeaderNames();
    Map<String, Object> headers = new HashMap<String, Object>();

    if (headerNames != null) {
      while (headerNames.hasMoreElements()) {
        String currentHeader = headerNames.nextElement();
        headers.put(currentHeader, request.getHeader(currentHeader));
      }
    }

    log.info(
        "Request handling finished with status code: " + HttpStatus.valueOf(response.getStatus()));

    try {
      log.info("Attempting to store request data in database: ");

      repository.save(new FlightApiRequestEntity(Instant.now(), headers, request.getRequestURI(),
          request.getQueryString(), response.getStatus()));

      log.info("Request stored successfully");
    } catch (Exception e) {
      log.error(ErrorMessage.UNEXPECTED_ERROR_STORING_REQUEST.getMessage(), e);
    }

    super.afterCompletion(request, response, handler, ex);
  }


}

package com.flightinportugal.FlightInfoApi.criteria.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.flightinportugal.FlightInfoApi.configuration.FlightInfoApiProperties;
import com.flightinportugal.FlightInfoApi.criteria.FlightCriteria;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;
import reactor.util.StringUtils;

/**
 * Used to perform complex validation for the query params of the "Get Flight" request that cannot
 * be accomplished with the use of javax.validation.constraints annotations
 * 
 * @see FlightCriteria
 */
@Component
public class FlightCriteriaValidator implements Validator {

  @Autowired
  FlightInfoApiProperties properties;

  @Override
  public boolean supports(Class<?> clazz) {

    return FlightCriteria.class.equals(clazz);
  }

  // TODO: cleanup method
  @Override
  public void validate(Object target, Errors errors) {

    FlightCriteria flightCriteria = (FlightCriteria) target;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(properties.getDatePattern());

    // Check if required params are null
    if (flightCriteria.getFrom() == null || flightCriteria.getDateTo() == null
        || flightCriteria.getDateFrom() == null) {

      errors.rejectValue("from", ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage());

    } else {

      // ==============================
      // "from" parameter validations
      // ==============================
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "from",
          ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage());
      // Valid airport code
      if (!properties.getValidAirportCodes().contains(flightCriteria.getFrom())) {
        errors.rejectValue("from", ErrorMessage.INVALID_AIRPORT_CODE.getMessage());
      }

      // ===============================
      // "to" parameter validations.
      // ===============================
      if (!StringUtils.isEmpty(flightCriteria.getTo())) {
        // Check if airport code is valid
        if (!properties.getValidAirportCodes().contains(flightCriteria.getTo())) {
          errors.rejectValue("to", ErrorMessage.INVALID_AIRPORT_CODE.getMessage());
          // Check if it is the same as "from"
        } else if (flightCriteria.getTo().equals(flightCriteria.getFrom())) {
          errors.rejectValue("to", ErrorMessage.SAME_ORIGIN_AND_DESTINATION.getMessage());
        }
      }

      // =================================
      // "dateFrom" parameter validations
      // =================================
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateFrom",
          ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage());
      if (!isValidDatePattern(flightCriteria.getDateFrom())) {
        errors.rejectValue("dateFrom", ErrorMessage.INVALID_DATE.getMessage());
      } else if (LocalDate.parse(flightCriteria.getDateFrom(), dateFormatter)
          .isBefore(LocalDate.now())) {
        errors.rejectValue("dateFrom", ErrorMessage.DATE_IN_PAST.getMessage());
      }

      // ==============================
      // dateTo parameter validation
      // ==============================
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateTo",
          ErrorMessage.MISSING_REQUIRED_PARAMS.getMessage());
      if (!isValidDatePattern(flightCriteria.getDateTo())) {
        errors.rejectValue("dateTo", ErrorMessage.INVALID_DATE.getMessage());
      } else if (LocalDate.parse(flightCriteria.getDateTo(), dateFormatter)
          .isBefore(LocalDate.now())) {
        errors.rejectValue("dateTo", ErrorMessage.DATE_IN_PAST.getMessage());
      } else if (isValidDatePattern(flightCriteria.getDateFrom())
          && LocalDate.parse(flightCriteria.getDateTo(), dateFormatter)
              .isBefore(LocalDate.parse(flightCriteria.getDateFrom(), dateFormatter))) {
        errors.rejectValue("dateTo", ErrorMessage.DATE_COMPARISON.getMessage());
      }
    }
  }

  /**
   * Checks if a String representation of a date can be converted to a {@link LocalDate} and if it
   * has the correct format
   * 
   * @param date a String representation of a date in dd/MM/yyyy
   * @return true is date is valid. false if it is not
   */
  private boolean isValidDatePattern(String date) {

    LocalDate localDate = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(properties.getDatePattern());

    try {
      localDate = LocalDate.parse(date, formatter);
      String result = localDate.format(formatter);
      return result.equals(date);
    } catch (DateTimeParseException e) {
      return false;
    }

  }

}

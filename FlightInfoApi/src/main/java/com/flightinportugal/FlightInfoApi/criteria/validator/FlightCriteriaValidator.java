package com.flightinportugal.FlightInfoApi.criteria.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.flightinportugal.FlightInfoApi.configuration.FlightInfoApiConfiguration;
import com.flightinportugal.FlightInfoApi.criteria.FlightCriteria;
import com.flightinportugal.FlightInfoApi.error.message.ErrorMessage;

import reactor.util.StringUtils;

/**
 * Used to perform complex validation for the query params of the "Get Flight"
 * request that cannot be accomplished with the use of
 * javax.validation.constraints annotations
 * 
 * @see FlightCriteria
 */
@Component
public class FlightCriteriaValidator implements Validator {

	@Autowired
	FlightInfoApiConfiguration configuration;

	@Override
	public boolean supports(Class<?> clazz) {

		return FlightCriteria.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		FlightCriteria flightCriteria = (FlightCriteria) target;

		// Check if all attributes are null which means no query parameters were specified
		if (flightCriteria.getTo() == null && flightCriteria.getFrom() == null
				&& flightCriteria.getDateTo() == null && flightCriteria.getDateFrom() == null) {
			
			errors.rejectValue("from",
					ErrorMessage.MISSING_REQUIRED.getMessage("to, dateFrom and dateTo"));
			
		} else {

			// "from" parameter validations
			if (StringUtils.isEmpty(flightCriteria.getFrom())) {
				errors.rejectValue("from", ErrorMessage.MISSING_REQUIRED.getMessage("from"));
			} else if (!configuration.getValidAirportCodes().contains(flightCriteria.getFrom())) {
				errors.rejectValue("from", ErrorMessage.INVALID_AIRPORT_CODE.getMessage("from"));
			}

			// "to" parameter validations. Parameter is ignored if null or empty
			if (!StringUtils.isEmpty(flightCriteria.getTo())) {
				if (!configuration.getValidAirportCodes().contains(flightCriteria.getTo())) {
					errors.rejectValue("to",
							ErrorMessage.INVALID_AIRPORT_CODE.getMessage("to"));
				} else if (flightCriteria.getTo().equals(flightCriteria.getFrom())) {
					errors.rejectValue("to", ErrorMessage.EQUAL_VALUES.getMessage("from", "to"));
				}
			}

			// "dateFrom" parameter validations
			if (flightCriteria.getDateFrom() == null) {
				errors.rejectValue("dateFrom",
						ErrorMessage.MISSING_REQUIRED.getMessage("dateFrom"));
			} else if (flightCriteria.getDateFrom().isBefore(LocalDate.now())) {
				errors.rejectValue("dateFrom", ErrorMessage.DATE_IN_PAST.getMessage("dateFrom"));
			}

			// dateTo parameter validation
			if (flightCriteria.getDateTo() == null) {
				errors.rejectValue("dateTo", ErrorMessage.MISSING_REQUIRED.getMessage("dateTo"));
			} else if (flightCriteria.getDateTo().isBefore(LocalDate.now())) {
				errors.rejectValue("dateTo", ErrorMessage.DATE_IN_PAST.getMessage("dateTo"));
			}
		}

	}

}

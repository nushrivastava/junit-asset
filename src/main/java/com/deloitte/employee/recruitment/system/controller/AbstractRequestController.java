package com.deloitte.employee.recruitment.system.controller;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.deloitte.employee.recruitment.system.exception.BadRequestException;
import com.deloitte.employee.recruitment.system.exception.EntityAlreadyExistsException;
import com.deloitte.employee.recruitment.system.exception.ValidationError;
import com.deloitte.employee.recruitment.system.model.ErrorEnv;
import com.deloitte.employee.recruitment.system.model.ResponseEnvelopeError;

public class AbstractRequestController {

	Logger LOGGER = LoggerFactory.getLogger(AbstractRequestController.class);
	/**
	 * Exception handler which builds failure api response object with with
	 * error code and error cause.
	 * 
	 * @param responsePayload
	 * @return
	 */
	@ExceptionHandler
	public ResponseEnvelopeError buildFailureResponse(Exception e, HttpServletResponse response) {
		ResponseEnvelopeError responseEnvelopeError = new ResponseEnvelopeError();
		LOGGER.error("An exception occured.", e);
		if (e instanceof EntityNotFoundException) {
			response.setStatus(HTTP_NOT_FOUND);
			buildResponseEnvelopeError(responseEnvelopeError, HTTP_NOT_FOUND);
		} else if (e instanceof MethodArgumentNotValidException || e instanceof BadRequestException
				|| e instanceof ServletRequestBindingException || e instanceof MethodArgumentTypeMismatchException
				|| e instanceof MissingServletRequestParameterException
				|| e instanceof UnsatisfiedServletRequestParameterException
				|| e instanceof ConstraintViolationException) {
			response.setStatus(HTTP_BAD_REQUEST);
			handleBadRequestException(responseEnvelopeError, e);
		} else if (e instanceof EntityAlreadyExistsException) {
			response.setStatus(HTTP_CONFLICT);
			buildResponseEnvelopeError(responseEnvelopeError, ((EntityAlreadyExistsException) e).getErrorCode());
		} else {
			response.setStatus(HTTP_INTERNAL_ERROR);
			buildResponseEnvelopeError(responseEnvelopeError, HTTP_INTERNAL_ERROR);
		}
		return responseEnvelopeError;
	}

	/**
	 * Creates response envelope error object.
	 * 
	 * @param responseEnvelopeError
	 * @param errorCode
	 * @return
	 */
	private ResponseEnvelopeError buildResponseEnvelopeError(ResponseEnvelopeError responseEnvelopeError,
			int errorCode) {
		ErrorEnv errorEnv = new ErrorEnv(errorCode);
		responseEnvelopeError.setError(errorEnv);
		return responseEnvelopeError;
	}

	/**
	 * Request validation exception handler.
	 * 
	 * @param responseEnvelopeError
	 * @param exception
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEnvelopeError handleBadRequestException(ResponseEnvelopeError responseEnvelopeError,
			Exception exception) {
		ValidationError validationError = new ValidationError();
		if (exception instanceof MethodArgumentNotValidException) {
			final BindingResult errors = ((MethodArgumentNotValidException) exception).getBindingResult();
			for (ObjectError objectError : errors.getAllErrors()) {
				validationError.addValidationError(objectError.getDefaultMessage());
			}
		} else if (exception instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) exception)
					.getConstraintViolations();
			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
				validationError.addValidationError(constraintViolation.getMessage());
			}
		} else {
			validationError.addValidationError(exception.getMessage());
		}
		responseEnvelopeError.setPayload(validationError);
		return buildResponseEnvelopeError(responseEnvelopeError, HTTP_INTERNAL_ERROR);
	}

}

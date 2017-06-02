package com.petstore.exception;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.petstore.dto.base.RESTResponse;
import com.petstore.dto.base.SimpleFieldError;


@ControllerAdvice
public class GlobalControllerExceptionHandler  {

	private static Log logger = LogFactory.getLog(GlobalControllerExceptionHandler.class);
	
	
	@ExceptionHandler({MethodArgumentNotValidException.class,ConstraintViolationException.class,MethodArgumentTypeMismatchException.class})
	public ResponseEntity<RESTResponse> validationException(MethodArgumentNotValidException e, HttpServletResponse response) throws IOException {
		logger.error(e, e);
		
		RESTResponse r = new RESTResponse();
		
		BindingResult result = e.getBindingResult();
		
		List<FieldError> fieldErrors = result.getFieldErrors();
		
		for(FieldError error : fieldErrors) {
			SimpleFieldError fieldError = new SimpleFieldError(error.getField(), error.getDefaultMessage());
			r.addErrorField(fieldError);
		}
		
		r.addErrorMessage("Invalid input");
		
		return new ResponseEntity<RESTResponse>(r, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	/**
	 * 
	 * @param e
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<RESTResponse> accessDenied(Exception e, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		RESTResponse r = new RESTResponse();
		r.set_status(RESTResponse.STATUS_ERROR);
		if (auth == null || auth instanceof AnonymousAuthenticationToken) {
			r.addErrorMessage("User not logged : "+e.getMessage());
			return new ResponseEntity<RESTResponse>(r,HttpStatus.UNAUTHORIZED);
		} else {
			r.addErrorMessage("User not authorized : "+e.getMessage());
			return new ResponseEntity<RESTResponse>(r,HttpStatus.FORBIDDEN);
		}
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RESTResponse> internalException(Exception e, HttpServletResponse response) throws IOException {
		logger.error(e, e);
		
		RESTResponse r = new RESTResponse();
		r.set_status(RESTResponse.STATUS_ERROR);
		r.addErrorMessage("Erreur inattendue "+e.getMessage());
		return new ResponseEntity<RESTResponse>(r, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<RESTResponse> hibernateException(Exception e, HttpServletResponse response) throws IOException {
		logger.error(e, e);
		
		RESTResponse r = new RESTResponse();
		r.set_status(RESTResponse.STATUS_ERROR);
		r.addErrorMessage("DataAccessException "+e.getMessage());
		return new ResponseEntity<RESTResponse>(r, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(PetStoreException.class)
	public ResponseEntity<RESTResponse> psException(PetStoreException e, HttpServletResponse response) throws IOException {
		logger.error("---------------PetStoreException --------"+e.getMessage());

		e.getViewModel().set_status(RESTResponse.STATUS_ERROR);
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		if(e.getViewModel().getHttpStatus() != null) {
			httpStatus = e.getViewModel().getHttpStatus();
		}
		
		return new ResponseEntity<RESTResponse>(e.getViewModel(), httpStatus);
	}
	
	@ExceptionHandler(PetStoreRulesException.class)
	public ResponseEntity<RESTResponse> psRulesException(PetStoreRulesException e, HttpServletResponse response) throws IOException {
		logger.error("---------------PetStoreRulesException --------"+e.getMessage());
		
		e.getViewModel().set_status(RESTResponse.STATUS_ERROR);
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		if(e.getViewModel().getHttpStatus() != null) {
			httpStatus = e.getViewModel().getHttpStatus();
		}
		
		return new ResponseEntity<RESTResponse>(e.getViewModel(), httpStatus);
	}
}

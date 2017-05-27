package com.petstore.exception;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.petstore.dto.base.RESTResponse;


@ControllerAdvice
public class GlobalControllerExceptionHandler  {

	private static Log logger = LogFactory.getLog(GlobalControllerExceptionHandler.class);
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public void acessDenied(Exception e) {
		logger.error("------------------- ACESS DENIED ----------------");
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
		return new ResponseEntity<RESTResponse>(e.getViewModel(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PetStoreRulesException.class)
	public ResponseEntity<RESTResponse> psRulesException(PetStoreRulesException e, HttpServletResponse response) throws IOException {
		logger.error("---------------PetStoreRulesException --------"+e.getMessage());
		
		e.getViewModel().set_status(RESTResponse.STATUS_ERROR);
		return new ResponseEntity<RESTResponse>(e.getViewModel(), HttpStatus.BAD_REQUEST);
	}
}

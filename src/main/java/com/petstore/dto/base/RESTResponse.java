package com.petstore.dto.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petstore.exception.PetStoreException;
import com.petstore.exception.PetStoreRulesException;



/**
 * 
 * Superclass for all DTO objects,
 * This is use for add/edit operation on entity for return the new/edited entity
 * 
 * You can remove metadata ( error, success warning ) if you want using setIncludeMetaAttributes(false)
 * use setIncludeMetaAttributes for child of the main entity, we don't want any redundance
 * 
 * A 201 response MAY contain an ETag response header field indicating 
 * the current value of the entity tag for the requested variant just created, 
 * https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.19
 * 
 */
@JsonFilter("psFilter")
public class RESTResponse {

	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_WARNING = 0;
	public static final int STATUS_ERROR = -1;

	public RESTResponse() {
		super();
	}

	public RESTResponse(int status, String successMsg, String warningMsg, String errorMsg) {
		this.set_status(status);
		if (successMsg != null)
			this.addSuccessMessage(successMsg);
		if (warningMsg != null)
			this.addWarningMessage(warningMsg);
		if (errorMsg != null)
			this.addErrorMessage(errorMsg);
	}

	public RESTResponse(int status, String successMsg) {
		this.set_status(status);
		if (successMsg != null)
			this.addSuccessMessage(successMsg);

	}

	public RESTResponse(int status) {
		this.set_status(status);

	}

	/**
	 * Statut de réussite de la requete
	 * <ul>
	 * <li>-1 : echec</li>
	 * <li>0 : succès avec avertissement</li>
	 * <li>1 : succès</li>
	 * </ul>
	 */
	private int _status = STATUS_SUCCESS;

	/**
	 * Message de retour de la requète
	 */
	 @JsonIgnore
	protected boolean includeMetaAttributes=true;

	private List<String> _successMessage = new ArrayList<String>();
	
	/**
	 * These are used for thraw others error or warning than field
	 */
	private List<String> _warningMessage = new ArrayList<String>();
	private List<String> _errorMessage = new ArrayList<String>();

	/**
	 * This is used for thraw field error in formulary
	 */
	private List<SimpleFieldError> _errorFields = new ArrayList<SimpleFieldError>();

	/**
	 * If we don't want the default status we can override it
	 */
	@JsonIgnore
	private HttpStatus httpStatus;
	
	public boolean isIncludeMetaAttributes() {
		return includeMetaAttributes;
	}

	public void setIncludeMetaAttributes(boolean includeMetaAttributes) {
		this.includeMetaAttributes = includeMetaAttributes;
	}

	public int get_status() {
		return _status;
	}

	public void set_status(int _status) {
		this._status = _status;
	}

	public List<SimpleFieldError> get_errorFields() {
		return _errorFields;
	}

	public void set_errorFields(List<SimpleFieldError> _errorFields) {
		this._errorFields = _errorFields;
	}

	public void addErrorField(SimpleFieldError field) {
		boolean found = false;
		
		for(SimpleFieldError error : this.get_errorFields()) {
			if(error.getField().equals(field.getField())) {
				found = true;
			}
		}
		
		if(!found) {
			this._errorFields.add(field);
		}
	}

	public void addWarningMessage(String mess) {
		this._warningMessage.add(mess);

	}

	public void addSuccessMessage(String mess) {
		this._successMessage.add(mess);

	}

	public void addErrorMessage(String mess) {
		this._errorMessage.add(mess);

	}

	public List<String> get_successMessage() {
		return _successMessage;
	}

	@SuppressWarnings("unused")
	private void set_successMessage(List<String> _successMessage) {
		this._successMessage = _successMessage;
	}

	public List<String> get_warningMessage() {
		return _warningMessage;
	}

	@SuppressWarnings("unused")
	private void set_warningMessage(List<String> _warningMessage) {
		this._warningMessage = _warningMessage;
	}

	public void validate() throws PetStoreRulesException {
		if (!this.get_errorFields().isEmpty() || !this.get_errorMessage().isEmpty()) {
			PetStoreRulesException e = new PetStoreRulesException("Erreur de validation " + this.getClass().getName());
			e.setRESTResponse(this);
			throw e;
		} else if (!this._warningMessage.isEmpty()) {
			this._status = 0;
		} else {
			//
		}
	}

	/*
	 * 
	 */
	public void throwRacException(String message) throws PetStoreException {
		PetStoreException e = new PetStoreException("Erreur lancée " + this.getClass().getName());
		this.set_status(STATUS_ERROR);
		this.addErrorMessage(message);
		e.setRESTResponse(this);
		throw e;
	}

	public List<String> get_errorMessage() {
		return _errorMessage;
	}

	@SuppressWarnings("unused")
	private void set_errorMessage(List<String> _errorMessage) {
		this._errorMessage = _errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	

}

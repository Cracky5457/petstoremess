package com.petstore.exception;

import com.petstore.dto.base.RESTResponse;

public class PetStoreException extends Exception {

	private static final long serialVersionUID = 1L;
	private RESTResponse response;

	public PetStoreException(String message){
		super(message);
	}
	
	public RESTResponse getRESTResponse() {
		return response;
	}

	public void setRESTResponse(RESTResponse viewModel) {
		this.response = viewModel;
	}
	
	

}


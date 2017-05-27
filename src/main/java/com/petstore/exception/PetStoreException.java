package com.petstore.exception;

import com.petstore.dto.base.RESTResponse;

public class PetStoreException extends Exception {

	private static final long serialVersionUID = 1L;
	private RESTResponse response;

	public PetStoreException(String message){
		super(message);
	}
	
	public RESTResponse getViewModel() {
		return response;
	}

	public void setViewModel(RESTResponse viewModel) {
		this.response = viewModel;
	}

}


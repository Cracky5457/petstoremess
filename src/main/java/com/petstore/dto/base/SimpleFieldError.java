package com.petstore.dto.base;

public class SimpleFieldError {

	private String field;
	private String message;
	
	public SimpleFieldError(String field, String message) {
		super();
		this.field = field;
		this.message = message;
	}
	
	public SimpleFieldError() {
		super();
	}

	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

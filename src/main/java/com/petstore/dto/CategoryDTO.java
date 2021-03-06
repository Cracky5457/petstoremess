package com.petstore.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class CategoryDTO {

	private long id;
	
	@NotBlank
	@Size(max=50)
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

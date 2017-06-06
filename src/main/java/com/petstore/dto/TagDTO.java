package com.petstore.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class TagDTO {

	private Long id;
	
	@NotBlank
	@Size(max=50)
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

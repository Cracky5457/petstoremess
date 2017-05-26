package com.petstore.dto;

import com.petstore.entity.PetEntity;

public class PetDTO {

	private Long id; 
	
	private String name;
	
	private String status;

	public PetDTO(PetEntity entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.status = entity.getStatus();
	}

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

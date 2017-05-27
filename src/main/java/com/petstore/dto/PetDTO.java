package com.petstore.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.petstore.dto.base.RESTResponse;
import com.petstore.entity.PetEntity;

public class PetDTO extends RESTResponse{

	private Long id; 

	private String name;
	
	private String status;
	
	private List<TagDTO> tags;
	
	private CategoryDTO category;

	public PetDTO() {
		super();
	}

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

	public List<TagDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}
	
	
	
}

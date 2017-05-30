package com.petstore.dto;

import java.util.ArrayList;
import java.util.List;

import com.petstore.dto.base.RESTResponse;
import com.petstore.entity.PetEntity;
import com.petstore.entity.PetTagEntity;

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
		
		if(entity.getCategory() != null) {
			this.category = new CategoryDTO();
			this.category.setId(entity.getCategory().getId());
			this.category.setName(entity.getCategory().getName());
		}
		
		if(entity.getListTags() != null && entity.getListTags().size() > 0) {

			this.tags = new ArrayList<TagDTO>();
			
			for(PetTagEntity ptEntity : entity.getListTags()) {
				TagDTO tagDTO = new TagDTO();
				
				tagDTO.setId(ptEntity.getTag().getId());
				tagDTO.setName(ptEntity.getTag().getName());
				
				this.tags.add(tagDTO);
			}
		}

		
		
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

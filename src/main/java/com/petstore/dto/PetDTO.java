package com.petstore.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.petstore.dto.base.RESTResponse;
import com.petstore.entity.PetEntity;
import com.petstore.entity.PetImageEntity;
import com.petstore.entity.PetTagEntity;

public class PetDTO extends RESTResponse{

	private Long id; 

	@NotBlank
    @Size(max = 50)
	private String name;
	
	@NotBlank
	@Pattern(message="Invalid status" , regexp="(Sold|Available|Pending)")
	private String status;
	
	@Valid
	private List<TagDTO> tags;
	
	@Valid
	private CategoryDTO category;
	
	private List<String> photoUrls;

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
		
		if(entity.getListTags() != null && !entity.getListTags().isEmpty()) {

			this.tags = new ArrayList<TagDTO>();
			
			for(PetTagEntity ptEntity : entity.getListTags()) {
				TagDTO tagDTO = new TagDTO();
				
				tagDTO.setId(ptEntity.getTag().getId());
				tagDTO.setName(ptEntity.getTag().getName());
				
				this.tags.add(tagDTO);
			}
		}

		this.photoUrls= new ArrayList<String>();
		if(entity.getListImages() != null && !entity.getListImages().isEmpty()) {
			for(PetImageEntity petImage : entity.getListImages()) {
				this.photoUrls.add("/pet/image/"+petImage.getId());
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

	public List<String> getPhotoUrls() {
		return photoUrls;
	}

	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}

	@Override
	public String toString() {
		return "PetDTO [id=" + id + ", name=" + name + ", status=" + status + ", tags=" + tags + ", category="
				+ category + ", photoUrls=" + photoUrls + "]";
	}
	
	

}

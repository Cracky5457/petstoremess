package com.petstore.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "T_PET")
public class PetEntity extends AbstractEntity {

	@Id
	@Column(name = "IDT_PET")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
    @NotBlank
    @Size(max = 50)
	@Column(name = "NAME",length = 50, nullable = false)
	private String name;
	
    @NotBlank
    @Column(name = "STATUS")
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name = "CATEGORY_PET", referencedColumnName = "IDT_CATEGORY")
	private CategoryEntity category;
	
	/* We could have use @ManyToMany but I prefer create an intermediate entity manually with a total control on it*/
	@OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PetTagEntity> listTags = new ArrayList<PetTagEntity>();
    
	@OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PetImageEntity> listImages = new ArrayList<PetImageEntity>();
	
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

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public List<PetTagEntity> getListTags() {
		return listTags;
	}

	public void setListTags(List<PetTagEntity> tags) {
		this.listTags.clear();
		this.listTags.addAll(tags);
	}

	public List<PetImageEntity> getListImages() {
		return listImages;
	}

	public void setListImages(List<PetImageEntity> listImages) {
		this.listImages.clear();
		this.listImages.addAll(listImages);
	}
	
	public void addImage(PetImageEntity image) {
		this.listImages.add(image);
	}
	
	
	
	
	
	
}

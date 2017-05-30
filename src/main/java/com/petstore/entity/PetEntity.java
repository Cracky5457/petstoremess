package com.petstore.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "T_PET")
public class PetEntity extends AbstractEntity {

	@Id
	@Column(name = "IDT_PET")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
    @NotNull
    @NotEmpty
    @Size(max = 10)
	@Column(name = "NAME",length = 50, nullable = false)
	private String name;
	
    @NotNull
    @NotEmpty
    @Column(name = "STATUS")
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = {javax.persistence.CascadeType.PERSIST, javax.persistence.CascadeType.REFRESH, javax.persistence.CascadeType.MERGE})
	@JoinColumn(name = "CATEGORY_PET", referencedColumnName = "IDT_CATEGORY")
	private CategoryEntity category;
	
	/* We could have use @ManyToMany but I prefer create an intermediate entity manually with a total control on it*/
    @Cascade(CascadeType.ALL)
	@OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PetTagEntity> listTags = new ArrayList<PetTagEntity>();
	
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
	
	
	
	
}

package com.petstore.entity;

import java.util.List;

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

@Entity
@Table(name = "T_PET")
public class PetEntity extends AbstractEntity {

	@Id
	@Column(name = "IDT_PET")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
    @NotNull
    @Size(max = 10)
	@Column(name = "NAME",length = 50, nullable = false)
	private String name;
	
    @Column(name = "STATUS")
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_PET", referencedColumnName = "IDT_CATEGORY")
	private CategoryEntity category;
	
	/* We could have use @ManyToMany but I prefer create an intermediate entity manually with a total control on it*/
	@OneToMany(mappedBy = "tag")
	private List<PetTagEntity> listTags;
	
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

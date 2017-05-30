package com.petstore.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(
	name = "T_TAG",
	uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})}
)
public class TagEntity extends AbstractEntity {
	
	@Id
	@Column(name = "IDT_TAG")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@NotNull
	@NotEmpty
	@Column(name = "NAME") 
	private String name;
	
//	@OneToMany(mappedBy = "tag",fetch = FetchType.LAZY)
//	private List<PetTagEntity> listPets;

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

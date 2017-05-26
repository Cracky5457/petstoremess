package com.petstore.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_PET")
public class PetEntity extends AbstractEntity {

	@Id
	@Column(name = "IDT_PET")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "STATUS")
	private String status;
	
	/* We could use @ManyToMany but I prefer create an intermediate entity manually */
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

package com.petstore.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(
	name = "T_PET_TAG"
)
public class PetTagEntity {

	
	@Id
	@Column(name = "IDT_PET_TAG")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PET", referencedColumnName="IDT_PET")
	private PetEntity pet;
	
	@ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
	@JoinColumn(name="FK_TAG", referencedColumnName="IDT_TAG")
	private TagEntity tag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PetEntity getPet() {
		return pet;
	}

	public void setPet(PetEntity pet) {
		this.pet = pet;
	}

	public TagEntity getTag() {
		return tag;
	}

	public void setTag(TagEntity tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "PetTagEntity [id=" + id + ", pet=" + pet + ", tag=" + tag + "]";
	}
	
	
	
	
}

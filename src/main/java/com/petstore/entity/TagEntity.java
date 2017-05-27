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
@Table(name = "T_TAG")
public class TagEntity extends AbstractEntity {
	
	@Id
	@Column(name = "IDT_TAG")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(name = "NAME") 
	private String name;
	
	@OneToMany(mappedBy = "pet")
	private List<PetTagEntity> listPets;

}

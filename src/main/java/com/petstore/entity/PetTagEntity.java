package com.petstore.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "T_PET_TAG")
public class PetTagEntity {

	
	@Id
	@Column(name = "IDT_PET_TAG")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Cascade(CascadeType.SAVE_UPDATE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK_PET", referencedColumnName="IDT_PET")
	private PetEntity pet;
	
	@Cascade(CascadeType.SAVE_UPDATE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK_TAG", referencedColumnName="IDT_TAG")
	private TagEntity tag;
	
	
}

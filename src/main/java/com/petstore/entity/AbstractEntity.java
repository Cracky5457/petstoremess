package com.petstore.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

	@Column(name = "DATE_CREATION", updatable = false)
	protected Date dateCreation;
	
	@Column(name = "USER_CREATION", updatable = false)
	private String userCreation;

	@Column(name = "DATE_UPDATE", nullable = true, insertable = false)
	protected Date dateUpdate;
	
	@Column(name = "USER_UPDATE", nullable = true, insertable = false)
	private String userUpdate;

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Date getDateUpdate() {
		return dateUpdate;
	}

	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

}
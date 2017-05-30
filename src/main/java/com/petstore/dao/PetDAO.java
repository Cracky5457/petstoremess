package com.petstore.dao;

import com.petstore.entity.PetEntity;

public interface PetDAO extends GenericDAO<Long,PetEntity> {

	public PetEntity findFullPetById(Long PK);
	
}

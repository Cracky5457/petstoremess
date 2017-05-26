package com.petstore.service;

import java.util.List;

import com.petstore.dto.PetDTO;
import com.petstore.entity.PetEntity;

public interface PetService {

	public List<PetDTO> findAllPets();
	public PetDTO findPetById(Long id);
	public PetEntity saveOrUpdatePet(PetEntity pet);
}

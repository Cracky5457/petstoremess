package com.petstore.service;

import java.util.List;

import com.petstore.dto.PetDTO;
import com.petstore.entity.PetEntity;
import com.petstore.exception.PetStoreRulesException;

public interface PetService {

	public List<PetDTO> findAllPets();
	public PetDTO findPetById(Long id);
	public PetDTO saveOrUpdatePet(PetDTO pet);
	public void deleteById(Long petId) throws PetStoreRulesException;
}

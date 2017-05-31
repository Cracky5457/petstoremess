package com.petstore.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.petstore.dto.PetDTO;
import com.petstore.dto.base.RESTResponse;
import com.petstore.exception.PetStoreRulesException;

public interface PetService {

	public List<PetDTO> findAllPets();
	public PetDTO findPetById(Long id);
	public PetDTO savePet(PetDTO pet);
	public RESTResponse addImageToPet(Long petId, MultipartFile file) throws PetStoreRulesException, IOException;
	public void deleteById(Long petId) throws PetStoreRulesException;
	public ResponseEntity<byte[]> getImageById(Long imageId);
}

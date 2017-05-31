package com.petstore.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.petstore.dao.PetImageDAO;
import com.petstore.dto.PetDTO;
import com.petstore.dto.base.RESTResponse;
import com.petstore.entity.PetImageEntity;
import com.petstore.exception.PetStoreRulesException;
import com.petstore.service.PetService;

@Controller  // we could use @RestController and remove @ResponseBody
@RequestMapping(value = "/pet")
public class PetController {

	@Autowired
	private PetService petService;
	
	@Autowired
    PetImageDAO petImageDao;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public PetDTO savePet(@Valid @RequestBody PetDTO dto) {
		return petService.saveOrUpdatePet(dto);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public PetDTO updatePet(@RequestBody PetDTO dto) {
		return petService.saveOrUpdatePet(dto);
	}
	
	@RequestMapping(value = "/{petId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePetById(@PathVariable Long petId) throws PetStoreRulesException {
		petService.deleteById(petId);
	}
	
	@RequestMapping(value = "/{petId}/uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public RESTResponse uploadImageForPet(@PathVariable Long petId,@RequestParam("file") MultipartFile file) throws PetStoreRulesException, IOException {
		
		return petService.addImageToPet(petId, file);
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/image/{imageId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) throws IOException {
		return petService.getImageById(imageId);
	}
	
	
	@RequestMapping(value = "/{petId}", method = RequestMethod.GET)
	@ResponseBody
	public PetDTO findPetById(@PathVariable Long petId) {
		return petService.findPetById(petId);
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<PetDTO> findAllPets() {
		
		return petService.findAllPets();
		
	}

}

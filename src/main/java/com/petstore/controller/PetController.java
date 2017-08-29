package com.petstore.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseStatus;
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
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public PetDTO savePet(@Valid @RequestBody PetDTO dto) throws PetStoreRulesException {
		return petService.savePet(dto,null);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public PetDTO editPet(@Valid @RequestBody PetDTO dto, HttpServletRequest request) throws PetStoreRulesException {
		
		String ifMatchValue = request.getHeader("If-Match");
		if(ifMatchValue.isEmpty()) {
			dto.addErrorMessage("Bad request, if-match should be provided for an update");
			dto.setHttpStatus(HttpStatus.BAD_REQUEST);
			dto.validate();
		}
		
		return petService.savePet(dto,ifMatchValue);
	}
	
	@RequestMapping(value = "/{petId}", method = RequestMethod.DELETE)
	@ResponseBody
	public void deletePetById(@PathVariable Long petId) throws PetStoreRulesException {
		petService.deleteById(petId);
	}
	
	@RequestMapping(value = "/{petId}/uploadImage", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public PetDTO uploadImageForPet(@PathVariable Long petId,@RequestParam("file") MultipartFile file) throws PetStoreRulesException, IOException {
		
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
	public PetDTO findPetById(@PathVariable Long petId, HttpServletResponse response) {
		PetDTO pet = petService.findPetById(petId);
		
		response.setHeader("ETag", pet.getVersion().toString());
		
		return pet;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<PetDTO> findAllPets(HttpServletResponse response) {
		
		List<PetDTO> pets = petService.findAllPets();
		
		if(pets.isEmpty()) {
			response.setStatus(204); // successed but no content
		} else {
			response.setStatus(200); // ok
		}
		
		return pets;
	}

}

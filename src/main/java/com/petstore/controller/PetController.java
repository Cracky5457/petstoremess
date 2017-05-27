package com.petstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petstore.dto.PetDTO;
import com.petstore.service.PetService;

@Controller  // we could use @RestController and remove @ResponseBody
@RequestMapping(value = "/pet")
public class PetController {

	@Autowired
	private PetService petService;
	
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

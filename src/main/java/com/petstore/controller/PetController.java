package com.petstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petstore.dto.PetDTO;
import com.petstore.entity.PetEntity;
import com.petstore.service.PetService;

@Controller
@RequestMapping(value = "/pet")
public class PetController {

	@Autowired
	private PetService petService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public PetEntity savePet(@RequestParam("name") String name, @RequestParam("status") String status) {
		
		PetEntity pet = new PetEntity();
		pet.setName(name);
		pet.setStatus(status);
		
		return petService.saveOrUpdatePet(pet);
		
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

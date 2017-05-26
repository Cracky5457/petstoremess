package com.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petstore.dao.PetDAO;
import com.petstore.dto.PetDTO;
import com.petstore.entity.PetEntity;
import com.petstore.service.PetService;

@Service("petService")
public class PetServiceImpl implements PetService {

	@Autowired
	private PetDAO petDao;
	
	@Override
	@Transactional
	public List<PetDTO> findAllPets() {
		List<PetEntity> listPetEntity = petDao.findAll();
		
		List<PetDTO> listPetDto = new ArrayList<PetDTO>();
		
		for(PetEntity entity : listPetEntity) {
			PetDTO dto = new PetDTO(entity);
			
			listPetDto.add(dto);
		}
		
		return listPetDto;
 	}
	
	@Override
	@Transactional
	public PetDTO findPetById(Long id) {
		PetEntity entity =  petDao.findById(id);
		
		return new PetDTO(entity);
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public PetEntity saveOrUpdatePet(PetEntity pet) {
		petDao.save(pet);
		
		return pet;
	}


}

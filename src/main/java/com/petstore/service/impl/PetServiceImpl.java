package com.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petstore.dao.PetDAO;
import com.petstore.dto.PetDTO;
import com.petstore.entity.PetEntity;
import com.petstore.exception.PetStoreRulesException;
import com.petstore.service.PetService;

@Service("petService")
public class PetServiceImpl implements PetService {

	@Autowired
	private PetDAO petDao;
	
	private PetDTO checkPet(PetDTO dto) throws PetStoreRulesException {
		
		if(dto.getName()==null || dto.getName().isEmpty()) {
			dto.addErrorField("name");
			dto.addErrorMessage("Name can't be empty");
		}
		
		if(dto.getStatus()==null || dto.getStatus().isEmpty()) {
			dto.addErrorField("status");
			dto.addErrorMessage("Status can't be empty");
		} else {
			
		}
		
		dto.validate();
		
		return dto;
	}
	
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
	public PetDTO saveOrUpdatePet(PetDTO dto) {
		
		PetEntity petEntity = new PetEntity();
		petEntity.setName(dto.getName());
		petEntity.setStatus(dto.getStatus());
		
		petDao.save(petEntity);
		
		dto.setId(petEntity.getId());
		
		return dto;
	}


}

package com.petstore.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.petstore.dao.CategoryDAO;
import com.petstore.dao.PetDAO;
import com.petstore.dao.TagDAO;
import com.petstore.dto.PetDTO;
import com.petstore.dto.TagDTO;
import com.petstore.dto.base.RESTResponse;
import com.petstore.entity.CategoryEntity;
import com.petstore.entity.PetEntity;
import com.petstore.entity.PetTagEntity;
import com.petstore.entity.TagEntity;
import com.petstore.exception.PetStoreRulesException;
import com.petstore.service.PetService;

@Service("petService")
public class PetServiceImpl implements PetService {

	@Autowired
	private PetDAO petDao;
	
	@Autowired
	private TagDAO tagDao;
	
	@Autowired
	private CategoryDAO categoryDao;
	
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
		
		if(dto.getId() != null) {
			petEntity = petDao.findFullPetById(dto.getId());
		}
		
		petEntity.setName(dto.getName());
		petEntity.setStatus(dto.getStatus());
		
		/** (create) attach tags **/
		
		List<PetTagEntity> listPtEntity = new ArrayList<PetTagEntity>();
		
		if(dto.getTags() != null && !dto.getTags().isEmpty()) {
			for(TagDTO tag : dto.getTags()) {
				TagEntity tagEntity = tagDao.findTagByName(tag.getName());
				
				if(tagEntity == null){
					tagEntity = new TagEntity();
					
					tagEntity.setName(tag.getName());
				}
				
				// I don't understand why there is an error without that
				// when I save ptEntity, @Cascade is define to SAVE_UPDATE, so I shouldn't have to persist it manually
				tagDao.saveOrUpdate(tagEntity);
				
				PetTagEntity ptEntity = new PetTagEntity();
				
				ptEntity.setTag(tagEntity);
				ptEntity.setPet(petEntity);
				
				listPtEntity.add(ptEntity);
			}
		}
		
		petEntity.setListTags(listPtEntity);
	
		/** (create) attach category **/
		CategoryEntity categoryEntity = null;
		
		categoryEntity = categoryDao.findCategoryByName(dto.getCategory().getName());
		
		if(categoryEntity == null) {
			categoryEntity = new CategoryEntity();
			categoryEntity.setName(dto.getCategory().getName());
		}

		petEntity.setCategory(categoryEntity);
		

		petDao.saveOrUpdate(petEntity);
		
		dto.setId(petEntity.getId());
		
		return dto;
	}

	@Override
	@Transactional
	public void deleteById(Long petId) throws PetStoreRulesException {
		
		RESTResponse response = new RESTResponse();
	
		PetEntity pet = null;
		
		if(petId != null) {
			pet = petDao.findById(petId);
			
			if(pet == null) {
				response.addErrorMessage("Pet not found");
			}
		} else {
			response.addErrorMessage("Invalid ID supplied");
		}
		
		response.validate();
		
		petDao.delete(pet);
	}


}

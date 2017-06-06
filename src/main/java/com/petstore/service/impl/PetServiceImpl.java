package com.petstore.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.petstore.dao.CategoryDAO;
import com.petstore.dao.PetDAO;
import com.petstore.dao.PetImageDAO;
import com.petstore.dao.TagDAO;
import com.petstore.dto.PetDTO;
import com.petstore.dto.TagDTO;
import com.petstore.dto.base.RESTResponse;
import com.petstore.entity.CategoryEntity;
import com.petstore.entity.PetEntity;
import com.petstore.entity.PetImageEntity;
import com.petstore.entity.PetTagEntity;
import com.petstore.entity.TagEntity;
import com.petstore.exception.PetStoreRulesException;
import com.petstore.service.PetService;
import com.petstore.utils.Constants;

@Service("petService")
public class PetServiceImpl implements PetService {

	@Autowired
	private PetDAO petDao;
	
	@Autowired
	private TagDAO tagDao;
	
	@Autowired
	private CategoryDAO categoryDao;
	
	@Autowired
	private PetImageDAO petImageDao;
	
	@Override
	@Transactional
	@Secured("ROLE_LIST_PET")
	public List<PetDTO> findAllPets() {
		List<PetEntity> listPetEntity = petDao.findAll();
		
		List<PetDTO> listPetDto = new ArrayList<PetDTO>();
		
		if(listPetEntity != null) {
			for(PetEntity entity : listPetEntity) {
				PetDTO dto = new PetDTO(entity);
				
				listPetDto.add(dto);
			}
		}
		
		return listPetDto;
 	}
	
	@Override
	@Transactional
	@Secured("ROLE_LIST_PET")
	public PetDTO findPetById(Long id) {
		PetEntity entity =  petDao.findById(id);
		
		return new PetDTO(entity);
	}
	
	/**
	 * We isolate the save function just for define the secured save in case of we don't wan't allow edit to someone who can create
	 * @param dto
	 * @return
	 * @throws PetStoreRulesException 
	 */
	@Override
	@Secured("ROLE_ADD_PET")
	@Transactional(rollbackOn = Exception.class)
	public PetDTO savePet(PetDTO dto) throws PetStoreRulesException {
		return saveOrUpdatePet(dto);
	}
	
	private PetDTO saveOrUpdatePet(PetDTO dto) throws PetStoreRulesException {
		
		PetEntity petEntity = new PetEntity();
		
		/* update case */
		if(dto.getId() != null) {
			
			if(dto.getId() <= 0) {
				dto.addErrorMessage("Invalid ID supplied");
				dto.setHttpStatus(HttpStatus.BAD_REQUEST);
				dto.validate();
			}
			
			petEntity = petDao.findFullPetById(dto.getId());
			
			/* we are in update but we don't find the entity, we thraw error */
			if(petEntity == null) {
				dto.addErrorMessage("Pet not found");
				dto.setHttpStatus(HttpStatus.NOT_FOUND);
				dto.validate();
			}
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
				
				PetTagEntity ptEntity = new PetTagEntity();
				
				ptEntity.setTag(tagEntity);
				ptEntity.setPet(petEntity);
				
				listPtEntity.add(ptEntity);
			}
		}
		
		petEntity.setListTags(listPtEntity);
	
		/** (create) attach category **/
		CategoryEntity categoryEntity = null;
		
		if(dto.getCategory() != null)
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
	@Secured("ROLE_DELETE_PET")
	public void deleteById(Long petId) throws PetStoreRulesException {
		
		RESTResponse response = new RESTResponse();
	
		PetEntity pet = null;
		
		if(petId != null && petId > 0) {
			pet = petDao.findById(petId);
			
			if(pet == null) {
				response.addErrorMessage("Pet not found");
				response.setHttpStatus(HttpStatus.NOT_FOUND);
				
			}
		} else {
			response.addErrorMessage("Invalid ID supplied");
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			
		}
		
		response.validate();
		
		petDao.delete(pet);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	@Secured("ROLE_EDIT_PET")
	public PetDTO addImageToPet(Long petId, MultipartFile file) throws PetStoreRulesException, IOException {
		
		PetEntity petEntity = petDao.findById(petId);
		
		PetDTO response = new PetDTO(petEntity);
		
		if(petEntity == null) {
			response.addErrorMessage("Pet not found");
		}
		
        if (file.isEmpty()) {
        	response.addErrorMessage("Empty file");
        }
        
		response.validate();
        
		PetImageEntity petImage = new PetImageEntity();
		
		petImage.setPet(petEntity);
		petImage.setFileName(file.getOriginalFilename());
		
		byte[] bytes = file.getBytes();
		
		petImage.setData(bytes);
		
		petEntity.addImage(petImage);
		
		petDao.saveOrUpdate(petEntity);
		
		List<String> photoUrls= new ArrayList<String>();
		if(petEntity.getListImages() != null && !petEntity.getListImages().isEmpty()) {
			for(PetImageEntity cpetImage : petEntity.getListImages()) {
				photoUrls.add("/pet/image/"+cpetImage.getId());
			}
		}
		
		response.setPhotoUrls(photoUrls);
		
		return response;
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public ResponseEntity<byte[]> getImageById(Long imageId) {
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);

	    PetImageEntity image = petImageDao.findById(imageId);
	    
	    if(image == null) {
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	    
	    byte[] data = image.getData();

	    headers.set("content-length",Integer.toString(data.length));

	    return new ResponseEntity<byte[]>(data, headers, HttpStatus.CREATED);
	}

	@Override
	@Transactional(rollbackOn=Exception.class)
	public void deleteAll() {
		petDao.deleteAll();
	}
	
	


}

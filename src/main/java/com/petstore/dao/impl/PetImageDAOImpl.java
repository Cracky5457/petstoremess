package com.petstore.dao.impl;

import org.springframework.stereotype.Repository;

import com.petstore.dao.PetImageDAO;
import com.petstore.entity.PetImageEntity;

@Repository("petImageDao")
public class PetImageDAOImpl extends GenericDAOImpl<Long, PetImageEntity> implements PetImageDAO {
	
}

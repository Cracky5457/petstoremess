package com.petstore.dao.impl;

import org.springframework.stereotype.Repository;

import com.petstore.dao.PetDAO;
import com.petstore.entity.PetEntity;

@Repository("petDao")
public class PetDAOImpl extends GenericDAOImpl<Long, PetEntity> implements PetDAO {

}

package com.petstore.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.petstore.dao.PetDAO;
import com.petstore.entity.PetEntity;
import com.petstore.entity.PetTagEntity;

@Repository("petDao")
public class PetDAOImpl extends GenericDAOImpl<Long, PetEntity> implements PetDAO {
	@Override
	public PetEntity findFullPetById(Long PK) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", PK));

		PetEntity pet = (PetEntity) criteria.uniqueResult();

		if (pet != null) {
			Hibernate.initialize(pet.getCategory());;
			
			for(PetTagEntity petTag : pet.getListTags()) {
				Hibernate.initialize(petTag.getTag());
			}
		}

		return pet;
	}
}

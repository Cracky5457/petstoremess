package com.petstore.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.petstore.dao.CategoryDAO;
import com.petstore.entity.CategoryEntity;

@Repository("categoryDao")
public class CategoryDAOImpl extends GenericDAOImpl<Long, CategoryEntity> implements CategoryDAO {
	
	@Override
	public CategoryEntity findCategoryByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		
		return (CategoryEntity) criteria.uniqueResult();
	}
}

package com.petstore.dao;

import com.petstore.entity.CategoryEntity;

public interface CategoryDAO extends GenericDAO<Long,CategoryEntity> {

	public CategoryEntity findCategoryByName(String name);
	
}
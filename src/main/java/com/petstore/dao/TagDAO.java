package com.petstore.dao;

import com.petstore.entity.TagEntity;

public interface TagDAO extends GenericDAO<Long,TagEntity> {

	public TagEntity findTagByName(String name);
	
}
package com.petstore.dao;

import java.io.Serializable;
import java.util.List;

import com.petstore.entity.AbstractEntity;

public interface GenericDAO<PK extends Serializable, T> {

	public T findById(PK key);

	public void save(AbstractEntity entity);

	public void delete(T entity);

	public List<T> findAll();
	
	public void saveOrUpdate(AbstractEntity entity);

	public void merge(AbstractEntity entity);

}

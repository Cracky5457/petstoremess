package com.petstore.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.petstore.dao.GenericDAO;
import com.petstore.entity.AbstractEntity;

public class GenericDAOImpl<PK extends Serializable, T> implements GenericDAO<PK, T> {

	private final Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	@Autowired
	private SessionFactory sessionFactory;

	protected Session getSession() {
		Session s = sessionFactory.getCurrentSession();
		return s;
	}
	
	public void delete(T entity) {
		getSession().delete(entity);
	}
	
	public void deleteAll() {
		
		Session s = sessionFactory.getCurrentSession();
		
		List<T> entities = findAll();
		
		for(T entity : entities) {
			s.delete(entity);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		Criteria criteria = createEntityCriteria();
		return (List<T>) criteria.list();
	}

	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}

	@Override
	public T findById(PK key) {
		return (T) getSession().get(persistentClass, key);
	}

	@Override
	public void save(AbstractEntity entity) {
		getSession().persist(entity);
	}

	@Override
	public void saveOrUpdate(AbstractEntity entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void merge(AbstractEntity entity) {
		getSession().merge(entity);

	}
}
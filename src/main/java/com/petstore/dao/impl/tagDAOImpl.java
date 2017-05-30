package com.petstore.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.petstore.dao.TagDAO;
import com.petstore.entity.TagEntity;

@Repository("tagDao")
public class tagDAOImpl extends GenericDAOImpl<Long, TagEntity> implements TagDAO {
	
	@Override
	public TagEntity findTagByName(String name) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("name", name));
		
		return (TagEntity) criteria.uniqueResult();
	}
	
}

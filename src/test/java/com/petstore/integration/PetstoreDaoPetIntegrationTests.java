package com.petstore.integration;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.petstore.dao.CategoryDAO;
import com.petstore.dao.PetDAO;
import com.petstore.dao.TagDAO;
import com.petstore.entity.CategoryEntity;
import com.petstore.entity.PetEntity;
import com.petstore.entity.PetTagEntity;
import com.petstore.entity.TagEntity;
import com.petstore.exception.PetStoreRulesException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
public class PetstoreDaoPetIntegrationTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	
    @Autowired
    TagDAO tagDao;
    
    @Autowired
    CategoryDAO categoryDao;
    
    @Autowired
    PetDAO petDao;
    
	@Autowired
	private SessionFactory sessionFactory;


    @Before
    public void setup() throws Exception {

    }
    
    @After
    /**
     * We clean the DB after each test
     */
    public void after() {

    	sessionFactory.getCurrentSession().flush();
		
		petDao.deleteAll();
		categoryDao.deleteAll();
		tagDao.deleteAll();
    	
    	sessionFactory.getCurrentSession().flush();

    }
    
    /**
     * Add a pet in the H2 in memory database
     * @throws PetStoreRulesException
     */
    private PetEntity addPet() {
    	PetEntity pet = new PetEntity();
		pet.setName("Nala");
		pet.setStatus("Sold");
		
		CategoryEntity catEntity = new CategoryEntity();
		catEntity.setName("Cat");
		
		pet.setCategory(catEntity);
		
		List<PetTagEntity> petTags = new ArrayList<PetTagEntity>();
		

		TagEntity tag1 = new TagEntity();
		tag1.setName("Kitten");
		
		PetTagEntity pt1 = new PetTagEntity();
		pt1.setTag(tag1);
		pt1.setPet(pet);
		
		petTags.add(pt1);
		
		TagEntity tag2 = new TagEntity();
		tag2.setName("Sterelised");
		
		PetTagEntity pt2 = new PetTagEntity();
		pt2.setTag(tag2);
		pt2.setPet(pet);
		
		petTags.add(pt2);
		
		pet.setListTags(petTags);
		
		petDao.saveOrUpdate(pet);
		
		return pet;
    }
    
    @Test
    @Transactional
    public void deleteMultiplesPets() {
    	PetEntity pet = addPet();
    	PetEntity pet2 = addPet();
    	
    	assertNotNull(pet.getId());
    	assertNotNull(pet2.getId());

    	List<PetEntity> pets = petDao.findAll();
    	assertEquals(2, pets.size());
    	
    	petDao.deleteAll();
    	
    	sessionFactory.getCurrentSession().flush();
    	
    	pets = petDao.findAll();
    	assertEquals(0, pets.size());
    	
    }
    
    @Test
    @Transactional
    public void savePet() {
    	PetEntity pet = addPet();
    	
    	sessionFactory.getCurrentSession().flush();
    	
    	assertEquals("Nala", pet.getName());
    	assertNotNull(pet.getId());
    	assertNotNull(pet.getCategory().getId());
    	assertEquals("Cat", pet.getCategory().getName());
    	assertEquals(pet.getListTags().size(), 2);
    	
    	List<PetEntity> pets = petDao.findAll();
    	assertEquals(1, pets.size());
    }
 
    
    @Test
    @Transactional
    public void updatePet() {
    	PetEntity pet = addPet();
    	
    	assertEquals("Nala", pet.getName());
    	assertNotNull(pet.getId());
    	assertEquals("Cat", pet.getCategory().getName());
    	assertEquals(pet.getListTags().size(), 2);
    	
    	sessionFactory.getCurrentSession().flush();
    	
    	TagEntity kittenTag = tagDao.findTagByName("Kitten");
    	    	
    	pet.setName("Sylla");
    	
    	List<PetTagEntity> petTags = pet.getListTags();
    	
    	petTags.get(0).getTag().setName("RenamedTag");
    	
    	PetTagEntity pt3 = new PetTagEntity();
    	TagEntity t3 = new TagEntity();
    	t3.setName("NewTag");
    	
    	pt3.setTag(t3);
    	pt3.setPet(pet);
    	
    	pet.setListTags(petTags);

    	petDao.saveOrUpdate(pet);
    	
    	sessionFactory.getCurrentSession().flush();
    	
    	TagEntity renamedTag = tagDao.findTagByName("RenamedTag");
    	
    	// It should be the same TagEntity just renamed
    	assertEquals(renamedTag.getId(), kittenTag.getId());
    	
    	assertEquals("Sylla", pet.getName());
    	
    	List<PetEntity> pets = petDao.findAll();
    	assertEquals(1, pets.size());
    }
    

    
}

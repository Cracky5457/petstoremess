package com.petstore;



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
import com.petstore.entity.CategoryEntity;
import com.petstore.entity.PetEntity;
import com.petstore.entity.PetTagEntity;
import com.petstore.entity.TagEntity;
import com.petstore.exception.PetStoreRulesException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
public class PetstoreDaoPetTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	
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
    public void after() {

    	sessionFactory.getCurrentSession().flush();
    	
    	System.out.println("delete");
		List<PetEntity> pets = petDao.findAll();
		
		if(pets != null) {
			System.out.println("size before : " + pets.size());
		}
    	
		petDao.deleteAll();
    	
    	sessionFactory.getCurrentSession().flush();
    	
		List<PetEntity> petsAfter = petDao.findAll();
		
		if(pets != null) {
			System.out.println("size after : " + petsAfter.size());
		}

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
		
		categoryDao.saveOrUpdate(catEntity);
		
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
    public void savePet() {
    	PetEntity pet = addPet();
    	
    	assertEquals(new Long(1), pet.getId());
    	assertNotNull(pet.getCategory());
    	assertEquals(pet.getCategory().getId(), new Long(1));
    	
    	List<PetEntity> pets = petDao.findAll();
    	assertEquals(1, pets.size());
    }
    
    @Test
    @Transactional
    public void updatePet() {
//    	PetEntity pet = addPet();
//    	
//    	assertEquals("Nala", pet.getName());
//    	assertEquals(new Long(1), pet.getId());
//    	
//    	assertEquals("Cat", pet.getCategory().getName());
//    	
//    	sessionFactory.getCurrentSession().flush();
//    	
//    	pet.setName("Sylla");
//    	
//    	petDao.saveOrUpdate(pet);
//    	
//    	assertEquals("Sylla", pet.getName());
//    	
//    	List<PetEntity> pets = petDao.findAll();
//    	assertEquals(1, pets.size());
    }
    

    
}

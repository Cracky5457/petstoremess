package com.petstore;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
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
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petstore.dao.CategoryDAO;
import com.petstore.dao.PetDAO;
import com.petstore.dao.TagDAO;
import com.petstore.dto.CategoryDTO;
import com.petstore.dto.PetDTO;
import com.petstore.dto.TagDTO;
import com.petstore.dto.base.RESTResponse;
import com.petstore.entity.PetEntity;
import com.petstore.exception.PetStoreRulesException;
import com.petstore.service.PetService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
@Transactional
public class PetstoreApiPetTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

	
	@Autowired
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext webApplicationContext;
    
	@Autowired
	private SessionFactory sessionFactory;
    
    @Autowired
    PetService petService;
    
    @Autowired
    CategoryDAO categoryDAO;
    
    @Autowired
    TagDAO tagDao;
    
    @Autowired
    PetDAO petDao;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
		mockMvc.perform(get("/user/login?user=admin&password=password")
                .content("")
                .contentType(contentType))
				.andExpect(status().isOk());
    }
    
    @After
    public void after() {
    	
    	sessionFactory.getCurrentSession().flush();
    	
    	petDao.deleteAll();
    	categoryDAO.deleteAll();
    	tagDao.deleteAll();
    	
    	sessionFactory.getCurrentSession().flush();
    }
   
    
    /**
     * Add a pet in the H2 in memory database
     * @throws PetStoreRulesException
     */
    private PetDTO addPet() throws PetStoreRulesException {
		PetDTO pet = new PetDTO();
		pet.setName("Nala");
		pet.setStatus("Sold");
		
		CategoryDTO catDto = new CategoryDTO();
		catDto.setName("Cat");
		
		pet.setCategory(catDto);
		
		List<TagDTO> tagsDTO = new ArrayList<TagDTO>();
		
		TagDTO tag1 = new TagDTO();
		tag1.setName("Kitten");
		
		TagDTO tag2 = new TagDTO();
		tag2.setName("Sterelised");
		
		tagsDTO.add(tag1);
		tagsDTO.add(tag2);
		
		pet.setTags(tagsDTO);
		
		return petService.savePet(pet);
    }
    
    /**
     * Invalid add pet form
     * 
     * @throws IOException
     * @throws Exception Should throw  405 exception
     */
	@Test
	public void addPetApiTest_ValidationFailed() throws IOException, Exception {
		
		PetDTO dto = new PetDTO();
		dto.setName("");
		
		CategoryDTO catDto = new CategoryDTO();
		catDto.setName("");
		
		dto.setCategory(catDto);
		
		
        MvcResult result = 
        		mockMvc.perform(post("/pet")
                .content(json(dto))
                .contentType(contentType))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
        
        MockHttpServletResponse mResponse = result.getResponse();
        String jsonResponse = mResponse.getContentAsString();
        RESTResponse response = jsonToRESTResponse(jsonResponse);
        
        assertEquals("Invalid input", response.get_errorMessage().get(0));
        
	}
	
    /**
     * Valid add pet form, should return 201
     * 
     * @throws IOException
     * @throws Exception 
     */
	@Test
	public void addPetApiTest_ValidationSuccessed() throws IOException, Exception {
		
		PetDTO dto = new PetDTO();
		dto.setName("Jack");
		dto.setStatus("Sold");
		
		CategoryDTO catDto = new CategoryDTO();
		catDto.setName("Cat");
		
		dto.setCategory(catDto);
		
		mockMvc.perform(post("/pet")
                .content(json(dto))
                .contentType(contentType))
                .andExpect(status().isCreated());
        
	}
	
    /**
     * 
     * @throws IOException
     * @throws Exception 
     */
	@Test
	public void addPetApiTest_UpdateSuccessed() throws IOException, Exception {
		
		PetDTO dto = addPet();
		
		dto.setName("Jack");

		
		mockMvc.perform(post("/pet")
                .content(json(dto))
                .contentType(contentType))
                .andExpect(status().isCreated());
        
	}

	
    /**
     * 
     * @throws IOException
     * @throws Exception 
     */
	@Test
	public void deletePetApiTest_Failed400() throws IOException, Exception {
		
		addPet();

		mockMvc.perform(delete("/pet/-54")
                .content("")
                .contentType(contentType))
                .andExpect(status().isBadRequest());
        
	}
	
    /**
     * 
     * @throws IOException
     * @throws Exception 
     */
	@Test
	public void deletePetApiTest_Failed404() throws IOException, Exception {
		
		addPet();

		mockMvc.perform(delete("/pet/587844")
                .content("")
                .contentType(contentType))
                .andExpect(status().isNotFound());
        
	}
	
    /**
     * 
     * @throws IOException
     * @throws Exception 
     */
	@Test
	public void deletePetApiTest_Successed() throws IOException, Exception {
		
		PetDTO pet = addPet();

		mockMvc.perform(delete("/pet/"+pet.getId())
                .content("")
                .contentType(contentType))
                .andExpect(status().isOk());
		
		sessionFactory.getCurrentSession().flush();
		
		List<PetEntity> pets = petDao.findAll();
		
		assertEquals(0, pets.size());
        
	}
	
    /**
     * Return list of pets ( expected 2 )
     * 
     * @throws IOException
     * @throws Exception 
     */
	@Test
	public void listPetApiTest_ok() throws IOException, Exception {
		
		PetDTO pet = addPet();
		
		sessionFactory.getCurrentSession().flush();
		
		PetDTO pet2 = addPet();
		
		sessionFactory.getCurrentSession().flush();

		mockMvc.perform(get("/pet/list")
                .content("")
                .contentType(contentType))
				.andExpect(status().isOk());
		
		List<PetEntity> pets = petDao.findAll();
		
		assertEquals(2, pets.size());
        
	}
	
    /**
     * Return empty list of pets ( expected 0 )
     * 
     * @throws IOException
     * @throws Exception 
     */
	@Test
	public void listPetApiTest_nocontent() throws IOException, Exception {

		mockMvc.perform(get("/pet/list")
                .content("")
                .contentType(contentType))
				.andExpect(status().isNoContent());
		
		List<PetEntity> pets = petDao.findAll();
		
		assertEquals(0, pets.size());
        
	}
	
	
    @SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
     
        return mockHttpOutputMessage.getBodyAsString();
    }
    
    protected RESTResponse jsonToRESTResponse(String jsonResponse) throws JsonParseException, JsonMappingException, IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	
    	return mapper.readValue(jsonResponse, RESTResponse.class);
    }
    
}

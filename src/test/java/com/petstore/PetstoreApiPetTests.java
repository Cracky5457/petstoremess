package com.petstore;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    PetService petService;

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
    	petService.deleteAll();
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
     * Valid add pet form, should return 200
     * 
     * @throws IOException
     * @throws Exception 
     */
	@Test
	public void addPetApiTest_ValidationSuccessed() throws IOException, Exception {
		
		PetDTO dto = new PetDTO();
		dto.setName("Nala");
		dto.setStatus("Sold");
		
		CategoryDTO catDto = new CategoryDTO();
		catDto.setName("Cat");
		
		dto.setCategory(catDto);
		
		mockMvc.perform(post("/pet")
                .content(json(dto))
                .contentType(contentType))
                .andExpect(status().isOk());
        
	}
	
    /**
     * Valid add pet form, should return 200
     * 
     * @throws IOException
     * @throws Exception 
     */
	@Test
	public void addPetApiTest_UpdateSuccessed() throws IOException, Exception {
		
		PetDTO dto = addPet();
		
		System.out.println(dto);
		
//		PetDTO dto = new PetDTO();
//		dto.setId(new Long(1));
//		dto.setName("Nala");
//		dto.setStatus("Available");
//		
//		CategoryDTO catDto = new CategoryDTO();
//		catDto.setName("Cat");
//		
//		dto.setCategory(catDto);
		
		mockMvc.perform(post("/pet")
                .content(json(dto))
                .contentType(contentType))
                .andExpect(status().isOk());
        
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

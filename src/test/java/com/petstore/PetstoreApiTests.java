package com.petstore;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

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
import com.petstore.dto.PetDTO;
import com.petstore.dto.base.RESTResponse;
import com.petstore.entity.PetEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
public class PetstoreApiTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    
	private PetEntity petEntity = null;
	
	@Autowired
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
		dto.setName("Nala");
		
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

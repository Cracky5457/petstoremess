package com.petstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.petstore.dto.PetDTO;
import com.petstore.entity.PetEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
public class PetstoreApiTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    
	private PetEntity petEntity = null;
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private String baseUrl = "http://localhost:8089";

	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        this.mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
	@Test
	public void addPetApiTest() throws IOException, Exception {
		
		PetDTO dto = new PetDTO();
		dto.setName("Nala");
		
        mockMvc.perform(post("/pet")
                .content("{\"name\":\"naladddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddnaladddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddnaladdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd\"}")
                .contentType(contentType))
                .andExpect(status().isMethodNotAllowed());

	}

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}

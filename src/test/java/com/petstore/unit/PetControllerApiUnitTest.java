package com.petstore.unit;

import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.petstore.PetStoreConfiguration;
import com.petstore.controller.PetController;
import com.petstore.dto.CategoryDTO;
import com.petstore.dto.PetDTO;
import com.petstore.dto.base.RESTResponse;
import com.petstore.exception.GlobalControllerExceptionHandler;
import com.petstore.service.PetService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PetControllerApiUnitTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	@Autowired
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
    @Autowired
    private WebApplicationContext webApplicationContext;

	@InjectMocks 
	private PetController petController;
	
	@Mock
	private PetService petService;
	
	@Before
	public void setup() throws Exception {
		
		MockitoAnnotations.initMocks(this);
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//		
//		mockMvc.perform(get("/user/login?user=admin&password=password")
//                .content("")
//                .contentType(contentType))
//				.andExpect(status().isOk());
		
		/* Stand alone version		*/
		PropertyFilter theFilter = PetStoreConfiguration.getPsFilter();
		FilterProvider filters = new SimpleFilterProvider().addFilter("psFilter", theFilter);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setFilterProvider(filters);
		
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(objectMapper);
		
		/**/
		this.mockMvc = MockMvcBuilders.standaloneSetup(petController)
				.setControllerAdvice(new GlobalControllerExceptionHandler())
				.setMessageConverters(converter)
				.build();
				
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
		dto.setStatus("");
		
		CategoryDTO catDto = new CategoryDTO();
		catDto.setName("");
		
		dto.setCategory(catDto);
		
		Mockito.when(petService.savePet(any(PetDTO.class),any(String.class))).thenReturn(dto);
		
        mockMvc.perform(post("/pet")
                .content(json(dto))
                .contentType(contentType))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$._errorMessage[0]").value("Invalid input"));
        
	}
	
    /**
     * Invalid add pet form
     * 
     * @throws IOException
     * @throws Exception Should throw  405 exception
     */
	@Test
	public void addPetApiTest_ValidationSuccesses() throws IOException, Exception {
		
		PetDTO dto = new PetDTO();
		dto.setName("Jack");
		dto.setStatus("Sold");
		
		CategoryDTO catDto = new CategoryDTO();
		catDto.setName("Cat");
		
		dto.setCategory(catDto);
		
		Mockito.when(petService.savePet(dto,null)).thenReturn(dto);
		
        mockMvc.perform(post("/pet")
                .content(json(dto))
                .contentType(contentType))
                .andExpect(status().isCreated());
        
	}
	
    /**
     * Invalid add pet form
     * 
     * @throws IOException
     * @throws Exception Should throw  405 exception
     */
	@Test
	public void addPetApiTest_UpdateFailed() throws IOException, Exception {
		
		PetDTO dto = new PetDTO();
		dto.setId(new Long(1));
		dto.setName("Jack");
		dto.setStatus("Sold");
		
		CategoryDTO catDto = new CategoryDTO();
		catDto.setName("Cat");
		
		dto.setCategory(catDto);
		
		Mockito.when(petService.savePet(any(PetDTO.class),any(String.class))).thenReturn(dto);
		
        mockMvc.perform(put("/pet")
                .content(json(dto))
                .contentType(contentType))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$._errorMessage[0]").value("Bad request, if-match should be provided for an update"));
        
	}
	
    /**
     * Invalid add pet form
     * 
     * @throws IOException
     * @throws Exception Should throw  405 exception
     */
	@Test
	public void addPetApiTest_UpdateSuccess() throws IOException, Exception {
		
		PetDTO dto = new PetDTO();
		dto.setId(new Long(1));
		dto.setName("Jack");
		dto.setStatus("Sold");
		
		CategoryDTO catDto = new CategoryDTO();
		catDto.setName("Cat");
		
		dto.setCategory(catDto);
		
		Mockito.when(petService.savePet(any(PetDTO.class),any(String.class))).thenReturn(dto);
		
        mockMvc.perform(put("/pet")
        		.header("If-Match", "1")
                .content(json(dto))
                .contentType(contentType))
                .andExpect(status().isCreated());
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

package com.petstore.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestEntityManager
public class PetstoreApiUserTests {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));


	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @After
    public void after() throws Exception{
		mockMvc.perform(get("/user/logout")
                .content("")
                .contentType(contentType))
				.andExpect(status().isFound()); // movedTemporaly seem deprecated
    }
    

    @Test
    public void login_Successed() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
		mockMvc.perform(get("/user/login?user=admin&password=password")
                .content("")
                .contentType(contentType))
				.andExpect(status().isOk());
    }
    
    @Test
    public void login_Failed() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
		mockMvc.perform(get("/user/login?user=admin&password=badpassword")
                .content("")
                .contentType(contentType))
				.andExpect(status().isBadRequest());
    }
    
}
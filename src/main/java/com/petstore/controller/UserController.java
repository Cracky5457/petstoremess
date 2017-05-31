package com.petstore.controller;

import java.util.ArrayList;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.petstore.PetStoreWebSecurityConfiguration;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	PetStoreWebSecurityConfiguration psWebSecurityConfiguration;
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public ResponseEntity<String> login(@RequestParam("user") String username, 
										@RequestParam("password") String password, 
										HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		

		if(username != null && password != null && !username.isEmpty() && !username.isEmpty()) {
			
			try {
				User user = new User(username, password, new ArrayList<GrantedAuthority>());
	
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
						user.getPassword());
				authentication.setDetails(new WebAuthenticationDetails(request));
				
				Authentication returnAuth = psWebSecurityConfiguration.authenticate(authentication);
	
				SecurityContextHolder.getContext().setAuthentication(returnAuth);
				request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
				
				System.out.println(SecurityContextHolder.getContext().getAuthentication());
				return new ResponseEntity<String>("successful operation",HttpStatus.OK);
			} catch (BadCredentialsException e) {
				return new ResponseEntity<String>("Invalid username/password supplied",HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<String>("Invalid username/password supplied",HttpStatus.BAD_REQUEST);
		}


	}

}
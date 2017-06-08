package com.petstore.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petstore.PetStoreWebSecurityConfiguration;
import com.petstore.dto.UserDTO;
import com.petstore.dto.base.RESTResponse;
import com.petstore.exception.PetStoreException;

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
	public ResponseEntity<RESTResponse> login(@RequestParam("user") String username, 
										@RequestParam("password") String password, 
										HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		

		RESTResponse r = new RESTResponse();
		
		if(username != null && password != null && !username.isEmpty() && !username.isEmpty()) {
			
			try {
				User user = new User(username, password, new ArrayList<GrantedAuthority>());
	
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
						user.getPassword());
				authentication.setDetails(new WebAuthenticationDetails(request));
				
				Authentication returnAuth = psWebSecurityConfiguration.authenticate(authentication);
	
				SecurityContextHolder.getContext().setAuthentication(returnAuth);
				request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
				
				UserDTO userDto = new UserDTO();
				
				userDto.setUsername(username);
				
				request.getSession().setAttribute("logged_user", userDto);
				
				userDto.set_status(RESTResponse.STATUS_SUCCESS);
				userDto.addSuccessMessage("successful operation");
				
				return new ResponseEntity<RESTResponse>(userDto,HttpStatus.OK);

			} catch (BadCredentialsException e) {
				r.set_status(RESTResponse.STATUS_ERROR);
				r.addErrorMessage("Invalid username/password supplied");
				return new ResponseEntity<RESTResponse>(r,HttpStatus.BAD_REQUEST);
			}
		} else {
			r.set_status(RESTResponse.STATUS_ERROR);
			r.addErrorMessage("Invalid username/password supplied");
			return new ResponseEntity<RESTResponse>(r,HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		SecurityContextHolder.getContext().setAuthentication(null);

		response.sendRedirect(getLoginUrl(request));
	}
	
	/**
	 * Get the user connected, return null if not logged in
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public UserDTO getUser(HttpServletRequest request) throws AccessDeniedException {
		
		return (UserDTO) request.getSession().getAttribute("logged_user");
	}
	
	/**
	 * @param request
	 * @return
	 * @throws MalformedURLException
	 */
	private String getLoginUrl(HttpServletRequest request) throws MalformedURLException {
		return getContextPath(request) + "#/login";
	}

	
	private String getContextPath(HttpServletRequest request) throws MalformedURLException {
		// case for custom header grunt
		if (request.getHeader("x-custom-dev-header") != null) {
			return "/";
		}
		
		String url = new URL(request.getScheme(), request.getServerName(), request.getServerPort(),
				request.getContextPath()).toString();

		if (url != null && !url.isEmpty() && !url.endsWith("/"))
			url += "/";

		return url;
	}

}
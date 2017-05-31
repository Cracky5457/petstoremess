package com.petstore;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class PetStoreWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.exceptionHandling().accessDeniedHandler(psAccessDeniedHandler());
	}

	/**
	 * Spring CSRF token does not work, when the request to be sent is a multipart request
	 * 
	 * Je ne sais pas pourquoi, les exceptions ne semblent pas passer par ici mais sont directement envoyés dans le GlobalControllerExceptionHandler ou j'ai
	 * refait le même traitement pour renvoyé des 401 ou 403 Je laisse quand même ce handler ici y a peut être certains moment ( le login peut être ? ) ou ça
	 * peut passer par là c'est un peu flou
	 * 
	 */
	@Bean
	public AccessDeniedHandler psAccessDeniedHandler() {
		AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandler() {

			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
					throws IOException, ServletException {
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				if (!response.isCommitted()) {
					if (auth == null) {
						System.out.println("<----- Access DENIED ----> No user logged");
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
					} else if (!(auth instanceof AnonymousAuthenticationToken)) {
						User userDetails = (User) auth.getPrincipal();
						userDetails.toString();
						System.out.println("<----- Access DENIED ---->" + userDetails.toString());
						System.out.println("<----- Access DENIED ---->" + accessDeniedException.toString());
						response.sendError(HttpServletResponse.SC_FORBIDDEN);
					}
				}

			}
		};
		return accessDeniedHandler;

	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.jdbcAuthentication().dataSource(dataSource)
		   .usersByUsernameQuery(
		    "select username, password, 1 as enabled from t_user where username=?")
			.authoritiesByUsernameQuery(
					"SELECT T_USER.username, T_ROLE.name as authorities " +
					"FROM T_USER " + 
					" JOIN T_USER_ROLE ON T_USER.idt_user = T_USER_ROLE.idt_user " +
					" JOIN T_ROLE ON T_USER_ROLE.idt_role = T_ROLE.idt_role "+
					" WHERE T_USER.username = ?");
		
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException, Exception {
		return authenticationManagerBean().authenticate(authentication);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return authenticationManager();
	}

}
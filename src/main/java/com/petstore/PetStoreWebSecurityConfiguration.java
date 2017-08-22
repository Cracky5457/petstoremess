package com.petstore;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class PetStoreWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
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
	
    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

}
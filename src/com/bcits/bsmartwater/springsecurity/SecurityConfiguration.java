package com.bcits.bsmartwater.springsecurity;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	CustomSuccessHandler customSuccessHandler;
	
	
	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth,HttpServletRequest req) throws Exception {
			auth.userDetailsService(userDetailsService);	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.csrf().disable().authorizeRequests()
	  	//.antMatchers("/", "/home").permitAll()
	  	.antMatchers("/", "/home").access("hasRole('USER')")
	  	.antMatchers("/admin**").access("hasRole('ADMIN')")
	  	.antMatchers("/dba**").access("hasRole('ADMIN') and hasRole('DBA')")
	  	//.and().formLogin().loginPage("/login")
	  	.and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
	  	.usernameParameter("ssoId").passwordParameter("password")
	  	.and().exceptionHandling().accessDeniedPage("/403");
	  
	  
	 /* http.authorizeRequests()
	  	.antMatchers("/", "/home").access("hasRole('USER')")
	  	.antMatchers("/admin**").access("hasRole('ADMIN')")
	  	.antMatchers("/dba**").access("hasRole('ADMIN') and hasRole('DBA')")
	  	.and().formLogin().loginPage("/login").successHandler(customSuccessHandler)
	  	.usernameParameter("ssoId").passwordParameter("password")
	  	.and().csrf()
	  	.and().exceptionHandling().accessDeniedPage("/403");*/
	  
	}

}

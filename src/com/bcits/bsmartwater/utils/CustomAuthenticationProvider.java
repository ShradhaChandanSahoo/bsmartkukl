package com.bcits.bsmartwater.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bcits.bsmartwater.model.UserMaster;
import com.bcits.bsmartwater.service.UserMasterService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Override
	public Authentication authenticate(Authentication authentication){
		try{
			String name = authentication.getName();
			ServletRequestAttributes reqAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpServletRequest request = reqAttributes.getRequest();
			String schemaName=(String)request.getSession().getAttribute("schemaName");
			UserMaster userMaster=userMasterService.findUser(name,request);
			
			String password = authentication.getCredentials().toString();
			
			String pwd = userMaster.getPassword();
			if (password.equals(pwd)) {
				List<GrantedAuthority> grantedAuths = new ArrayList<>();
				grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
				Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
				return auth;
			} else {
				//request.getSession().setAttribute("schemaName",null);
				throw new BadCredentialsException("Invalid credentials");
			}
			
		} catch(Exception e){
			throw new BadCredentialsException("Invalid credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}

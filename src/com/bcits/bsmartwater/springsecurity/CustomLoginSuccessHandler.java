package com.bcits.bsmartwater.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomLoginSuccessHandler  implements AuthenticationSuccessHandler
{
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,	HttpServletResponse response, Authentication auth) throws IOException,ServletException 
	{	
		request.getSession().setAttribute("location",request.getParameter("location"));
		System.out.println("coming to success Class ");		
		//request.getSession().setAttribute("displayDeptId",request.getParameter("deptId"));	
		//request.getSession().setAttribute("displayDeptIdName",request.getParameter("deptIdName"));
		
		redirectStrategy.sendRedirect(request, response, "/login");
		
	}
	
}

package com.bcits.bsmartwater.springsecurity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class AuthenticationFailureCheckDB extends SimpleUrlAuthenticationFailureHandler
{	
	
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
  

    private static String userName;
    private static String password;
    private static String loginAs;
    
   // @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        //We inherited that method:
        saveException(request, exception); 
        setUserName(request.getParameter("j_username"));
        setPassword(request.getParameter("j_password"));
        request.getSession().setAttribute("loginAs",request.getParameter("loginAs"));
        request.getSession().setAttribute("displayDeptId",request.getParameter("deptId"));
        request.getSession().setAttribute("displayDeptIdName",request.getParameter("deptIdName"));
        
        redirectStrategy.sendRedirect(request, response, "/login");
        
    }

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		AuthenticationFailureCheckDB.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		AuthenticationFailureCheckDB.password = password;
	}
}

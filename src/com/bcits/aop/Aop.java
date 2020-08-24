package com.bcits.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.bcits.bsmartwater.service.ModuleMasterService;
import com.bcits.bsmartwater.service.UserMasterService;

/*@Aspect*/
public class Aop {
	
	/*@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private ModuleMasterService moduleMasterService; 
	
	@After("execution(* com.bcits.bsmartwater.controller.*.*(..)) ")
	public void removeSessionForWebreq(JoinPoint joinPoint)
	{
		
	}*/
}

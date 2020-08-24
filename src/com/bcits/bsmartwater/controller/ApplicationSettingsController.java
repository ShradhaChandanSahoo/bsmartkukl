package com.bcits.bsmartwater.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApplicationSettingsController {

	@RequestMapping(value="/dateTimeSettings",method={RequestMethod.POST,RequestMethod.GET})
	public String tableBasic(Model model,HttpServletRequest request){	
		
		
		return "applicationSettings/dateTimeSettings";	
	}
}

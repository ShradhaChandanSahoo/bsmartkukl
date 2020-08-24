package com.bcits.bsmartwater.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EnquiryController 
{
	@RequestMapping(value="/consumerEnquiry",method={RequestMethod.POST,RequestMethod.GET})
	public String wardDetails(Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Enquiry");
		model.addAttribute("childHead1", "Customer Enquiry");
		return "enquiry/consumerEnquiry";	
	}
}

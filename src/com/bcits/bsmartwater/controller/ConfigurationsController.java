package com.bcits.bsmartwater.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bsmartwater.model.BankDetailsEntity;
import com.bcits.bsmartwater.model.BranchDetailsEntity;
import com.bcits.bsmartwater.service.BankDetailsService;
import com.bcits.bsmartwater.service.BranchDetailsService;


@Controller
public class ConfigurationsController {

	@Autowired
	private BankDetailsService bankDetailsService;
	
	@Autowired 
	private BranchDetailsService  branchDetailsService;
	
	/*Begin of Disconnection Rules*/ 
	
	@RequestMapping(value="/disconnectionRules",method={RequestMethod.POST,RequestMethod.GET})
	public String consumerApproval(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Disconnection Rules");
		
		return "configurations/disconnectionRules";
	}
	
	/*@RequestMapping(value = "/saveDisconnectionRules", method = {RequestMethod.GET, RequestMethod.POST})
	public String saveDisconnectionRules(@ModelAttribute("disconnectionRules")DisconnectionRulesEntity disconnectionRules,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		System.out.println(" ***************** ");
		disconnectionRules.setAddedBy((String)req.getSession(false).getAttribute("userName"));
		disconnectionRules.setAddedDateTime(new Date());
		disconnectionRulesService.save(disconnectionRules);
		System.out.println(" **** ");
		//userTypeService.save(userType);
		//createflag=1;
		//return "redirect:/usertype";
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Disconnection Rules");
		return "configurations/disconnectionRules";
		
	}*/
	
	/*End of Disconnection Rules*/ 
	
	
	/*Begin of Bank Details*/
	
	@RequestMapping(value="/bankDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String bankDetails(@ModelAttribute("bankDetails") BankDetailsEntity bankDetailsEntity, BindingResult bingingResult, Model model,HttpServletRequest request)
	{	
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Bank Details");
		
		List<BankDetailsEntity> bank = bankDetailsService.getAllRecords();
		model.addAttribute("bank",bank);
		
		return "configurations/bankDetails"; 
	}
	
	@RequestMapping(value = "/addBankDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String addBankDetails(@ModelAttribute("bankDetails") BankDetailsEntity bankDetailsEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  
	{
		//System.out.println(" *********** inside addBankDetails ************ ");
		try 
		{
			//System.out.println("Name ===> "+bankDetailsEntity.getBankName());
			Long flag = bankDetailsService.checkForBankNameAvailability(bankDetailsEntity.getBankName());
			if(flag > 0)
			{
				model.addAttribute("mag","This Bank Name Alreday Exists!!! Please Try Other.");
			}
			else
			{
				bankDetailsEntity.setAddedBy((String)request.getSession(false).getAttribute("userName"));
				bankDetailsEntity.setAddedDateTime(new Date());
			    bankDetailsService.save(bankDetailsEntity);
			    model.addAttribute("mag","Bank Details Sumitted Successfully.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<BankDetailsEntity> bank = bankDetailsService.getAllRecords();
		model.addAttribute("bank",bank);
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Bank Details");
	    
		return "configurations/bankDetails";
		
	}
	
	@RequestMapping(value="/getBankDataForEditing/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getBankDataForEditing(@PathVariable String id,HttpServletResponse response,HttpServletRequest request) 
	{
		List<BankDetailsEntity> bankData = bankDetailsService.getBankDataForEditing(id);
		return bankData;
	}
	
	@RequestMapping(value = "/updateBankDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateBankDetails(@ModelAttribute("bankDetails") BankDetailsEntity bankDetailsEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  
	{
		//System.out.println(" *********** inside updateBankDetails ************ ");
		try 
		{
			//System.out.println(" id ===> "+bankDetailsEntity.getId());
			BankDetailsEntity bankData = bankDetailsService.find(bankDetailsEntity.getId());
			if(bankData.getBankName().equalsIgnoreCase(bankDetailsEntity.getBankName())) // jsp == database
			{
				bankDetailsEntity.setAddedBy(bankData.getAddedBy());
				bankDetailsEntity.setAddedDateTime(bankData.getAddedDateTime());
				bankDetailsEntity.setUpdatedBy((String)request.getSession(false).getAttribute("userName"));
				bankDetailsEntity.setAddedDateTime(new Date());
			    bankDetailsService.update(bankDetailsEntity);
			    model.addAttribute("mag","Bank Details Modified Successfully.");
			}
			else
			{
				Long flag = bankDetailsService.checkForBankNameAvailability(bankDetailsEntity.getBankName());
				if(flag > 0)
				{
					model.addAttribute("mag","This Bank Name Alreday Exists!!! Please Try Other.");
				}
				else
				{
					bankDetailsEntity.setAddedBy(bankData.getAddedBy());
					bankDetailsEntity.setAddedDateTime(bankData.getAddedDateTime());
					bankDetailsEntity.setUpdatedBy((String)request.getSession(false).getAttribute("userName"));
					bankDetailsEntity.setAddedDateTime(new Date());
				    bankDetailsService.update(bankDetailsEntity);
				    model.addAttribute("mag","Bank Details Modified Successfully.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<BankDetailsEntity> bank = bankDetailsService.getAllRecords();
		model.addAttribute("bank",bank);
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Bank Details");
	    
		return "configurations/bankDetails";
		
	}
	
	@RequestMapping(value = "/deleteBankDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteBankDetails(@ModelAttribute("bankDetails") BankDetailsEntity bankDetailsEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  
	{
		//System.out.println(" *********** inside deleteBankDetails ************ ");
		try 
		{
			//System.out.println(bankDetailsEntity.getId());
			 bankDetailsService.delete(bankDetailsEntity.getId());
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<BankDetailsEntity> bank = bankDetailsService.getAllRecords();
		model.addAttribute("bank",bank);
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Bank Details");
	    
		return "configurations/bankDetails";
		
	}
	
	/*End of Bank Details*/
	
	
	/*Begin of Branch Details*/ 
	
	@RequestMapping(value="/branchDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String branchDetails(@ModelAttribute("branchDetails") BranchDetailsEntity branchDetailsEntity,BindingResult bindingResult, Model model,HttpServletRequest request){	
		
		List<BranchDetailsEntity> branch=branchDetailsService.getAllBranchRecords();
		model.addAttribute("branch",branch);
		model.addAttribute("branchDetails",new BranchDetailsEntity());
		model.addAttribute("bankData",bankDetailsService.getBankDetailsEntity());
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Branch Details");
		
		return "configurations/branchDetails";
		
	}
	
	@RequestMapping(value = "/addBranchDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String addBranchDetails(@ModelAttribute("branchDetails") BranchDetailsEntity branchDetailsEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  
	{
		//System.out.println(" *********** inside addBranchDetails ************ ");
		HttpSession session=request.getSession(false);
		try {
			
			//System.out.println("Name ===> "+branchDetailsEntity.getBranchName());
			Long flag = branchDetailsService.checkForBranchNameAvailability(branchDetailsEntity.getBranchName());
			if(flag > 0){
				session.setAttribute("msg","This Bank Name Alreday Exists!!! Please Try Other.");
			}
			else{
				int code=branchDetailsService.checkForBranchCodeAvailability(branchDetailsEntity.getBranchCode());
				if(code>0){
					session.setAttribute("msg","This Bank Code Alreday Exists!!! Please Try Other.");
					// model.addAttribute("msg","This Bank Code Alreday Exists!!! Please Try Other.");
				}
				else{
			        branchDetailsService.save(branchDetailsEntity);
			        session.setAttribute("msg","Branch Details Sumitted Successfully.");
			       // model.addAttribute("msg","Branch Details Sumitted Successfully.");
				}
			}
			
		} catch (Exception e) {
		e.printStackTrace();
		}
		List<BranchDetailsEntity> branch = branchDetailsService.getAllBranchRecords();
		model.addAttribute("branch",branch);
		model.addAttribute("branchDetails",new BranchDetailsEntity());
		session.setAttribute("mainHead", "Configurations");
		session.setAttribute("childHead1", "Branch Details");
		return "redirect:/branchDetails";
	}
	
	@RequestMapping(value="getBranchDataForEditing/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getBranchDataForEditing(@PathVariable String id,HttpServletResponse response,HttpServletRequest request) 
	{
		List<BranchDetailsEntity> branchData = branchDetailsService.getBranchDataForEditing(id);
		return branchData;
	}
	
	@RequestMapping(value = "/updateBranchDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateBranchDetails(@ModelAttribute("branchDetails") BranchDetailsEntity branchDetailsEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  
	{
		//System.out.println(" *********** inside updateBranchDetails ************ ");
		HttpSession session=request.getSession(false);
		try {
			
			//System.out.println(" id ===> "+branchDetailsEntity.getId());
			Long code=branchDetailsService.checkBranchCodeForUpdate(branchDetailsEntity.getId(),branchDetailsEntity.getBranchCode());
			if (code>0)
			{
				session.setAttribute("msg","This Branch Code Alreday Exists!!! Please Try Other.");
			}
			else
			{
				Long flag = branchDetailsService.checkBranchNameForUpdate(branchDetailsEntity.getId(),branchDetailsEntity.getBranchName());
				if(flag > 0)
				{
					session.setAttribute("msg","This Branch Name Alreday Exists!!! Please Try Other.");
				}
				else {
					branchDetailsService.update(branchDetailsEntity);
					session.setAttribute("msg","Branch Details Modified Successfully.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<BranchDetailsEntity> branch = branchDetailsService.getAllBranchRecords();
		model.addAttribute("bankData",bankDetailsService.getBankDetailsEntity());
		model.addAttribute("branch",branch);
		model.addAttribute("branchDetails",new BranchDetailsEntity());
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Branch Details");
		return "redirect:/branchDetails";
	}
	
	
	@RequestMapping(value = "/deleteBranchDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteBranchDetails(@ModelAttribute("branchDetails") BranchDetailsEntity branchDetailsEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  
	{
		//System.out.println(" *********** inside deleteBranchDetails************ ");
		
		try 
		{
			//System.out.println(branchDetailsEntity.getId());
			 branchDetailsService.delete(branchDetailsEntity.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<BranchDetailsEntity> branch = branchDetailsService.getAllBranchRecords();
		model.addAttribute("branch",branch);
		model.addAttribute("branchDetails",new BranchDetailsEntity());
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Branch Details");
		return "redirect:/branchDetails";
	}
	
	/*End of Branch Details*/
	
	
	/*Begin of Slab Details*/
	
	@RequestMapping(value="/slabDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String slabDetails(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Configurations");
		model.addAttribute("childHead1", "Branch Details");
		
		return "configurations/slabDetails";
	}
	
	
	
	
	/*End of Slab Details*/
}

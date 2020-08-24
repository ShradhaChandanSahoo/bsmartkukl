package com.bcits.bsmartwater.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.bcits.bsmartwater.model.CounterDetails;
import com.bcits.bsmartwater.model.MuncipalityDetailsEntity;
import com.bcits.bsmartwater.model.WardDetailsEntity;
import com.bcits.bsmartwater.service.CounterDetailsService;
import com.bcits.bsmartwater.service.MuncipalityDetailsService;
import com.bcits.bsmartwater.service.WardDetailsService;
import com.sun.org.apache.bcel.internal.generic.NEW;



@Controller
public class AdministrationController 
{
	@Autowired
	WardDetailsService wardDetailsService;

	@Autowired
	CounterDetailsService counterDetailsService;
	
	@Autowired
	MuncipalityDetailsService muncipalityDetailsService;

	@RequestMapping(value = "/wardDetails", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String wardDetails(
			@ModelAttribute("wardDetails") WardDetailsEntity wardDetailsEntity,
			BindingResult bindingResult, Model model, HttpServletRequest request) 
	{
		System.out.println(" *********** inside WardDetails ************ ");
		HttpSession session = request.getSession(false);

		session.setAttribute("mainHead", "Administration");
		session.setAttribute("childHead1", "Ward Details");
		List<WardDetailsEntity> ward = wardDetailsService.getAllWardRecords();
		model.addAttribute("ward", ward);
		model.addAttribute("wardDetails", new WardDetailsEntity());
		List<MuncipalityDetailsEntity> municipal = muncipalityDetailsService.getAllMuncipalityRecords();
		model.addAttribute("municipal", municipal);
		return "administration/wardDetails";
	}

	@RequestMapping(value = "/addWardDetails", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addWardDetails(
			@ModelAttribute("wardDetails") WardDetailsEntity wardDetailsEntity,
			BindingResult bingingResult, ModelMap model,
			HttpServletRequest request) {
		System.out.println(" *********** inside addWardDetails ************ ");
		HttpSession session = request.getSession(false);
		try {
			System.out.println("Name ===> " + wardDetailsEntity.getWardName());

			
				int code = wardDetailsService.checkForWardNoAvailability(wardDetailsEntity.getWardNo());
				if (code > 0) {
					session.setAttribute("msg",
							"This Ward Number Alreday Exists!!! Please Try Other.");
				} 
				/*else
				{
				Long flag = wardDetailsService
						.checkForWardNameAvailability(wardDetailsEntity
								.getWardName());
				if (flag > 0) {
					session.setAttribute("msg",
							"This Ward Name Alreday Exists!!! Please Try Other.");
				}*/ else {
					
					wardDetailsService.save(wardDetailsEntity);
					session.setAttribute("msg",
							"Ward Details Submitted Successfully.");


				}
			
		} catch (Exception e) {
			session.setAttribute("msg",
					"Problem in adding Ward Details :( ");
			e.printStackTrace();
		}
		List<WardDetailsEntity> ward = wardDetailsService.getAllWardRecords();
		model.addAttribute("ward", ward);
		model.addAttribute("wardDetails", new WardDetailsEntity());
		session.setAttribute("mainHead", "Administration");
		session.setAttribute("childHead1", "Ward Details");
		return "redirect:/wardDetails";
	}

	@RequestMapping(value = "/getWardDataForEditing/{id}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	Object getBranchDataForEditing(@PathVariable int id) {
		return wardDetailsService.find(id);
	}

	@RequestMapping(value = "/updateWardDetails", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String updateBranchDetails(
			@ModelAttribute("wardDetails") WardDetailsEntity wardDetailsEntity,
			BindingResult bingingResult, ModelMap model,
			HttpServletRequest request) {

		System.out
		.println(" *********** inside updateWardDetails ************ ");
		HttpSession session = request.getSession(false);
		try {
			Long flag = wardDetailsService
					.checkForWardNoUpdate(wardDetailsEntity.getId(),wardDetailsEntity
							.getWardNo());
			if (flag > 0) {
				session.setAttribute("msg",
						"This Ward Name Alreday Exists!!! Please Try Other.");
			}

			 else {
					wardDetailsService.update(wardDetailsEntity);
					session.setAttribute("msg",
							"Ward Details Modified Successfully.");
				}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/wardDetails";

	}



	@RequestMapping(value="/counterDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String counterDetails(@ModelAttribute("counterDetails") CounterDetails counterDetails,
			BindingResult bindingResult, Model model, HttpServletRequest request)
	{
		System.out.println(" *********** inside counterDetails ************ ");
		HttpSession session = request.getSession(false);

		session.setAttribute("mainHead", "Administration");
		session.setAttribute("childHead1", "counter Details");
		List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
		model.addAttribute("counterDetails", new CounterDetails());
		return "administration/counterDetails";
	}


	@RequestMapping(value = "/addCounterDetails", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String addCounterDetails(
			@ModelAttribute("counterDetails") CounterDetails counterDetails,
			BindingResult bingingResult, ModelMap model,
			HttpServletRequest request)
	{

		System.out.println(" *********** inside addCounterDetails ************ ");
		HttpSession session = request.getSession(false);
		try {
			System.out.println("Name ===> " + counterDetails.getCounterName());

			Long flag = counterDetailsService.checkForCounterNameAvailability(counterDetails.getCounterName());
			if (flag > 0) {
				session.setAttribute("msg",
						"This Counter Name Alreday Exists!!! Please Try Other.");
			} else {
				int code = counterDetailsService.checkForCounterNumberAvailability(counterDetails.getCounterNumber());
				if (code > 0) {
					session.setAttribute("msg",
							"This Counter Number Alreday Exists!!! Please Try Other.");
				} else {
					counterDetailsService.save(counterDetails);
					session.setAttribute("msg",
							"Counter Details Submitted Successfully.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
		model.addAttribute("counterDetails", new CounterDetails());
		model.addAttribute("mainHead", "Administration");
		model.addAttribute("childHead1", "counter Details");
		return "redirect:/counterDetails";
	}


	@RequestMapping(value = "/getCounterDataForEditing/{id}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody Object getCounterDetailsForEditing(@PathVariable int id)
	{
		return counterDetailsService.find(id);

	}


	@RequestMapping(value = "/updateCounterDetails", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String updateCounterDetails(
			@ModelAttribute("counterDetails") CounterDetails counterDetails,
			BindingResult bingingResult, ModelMap model,
			HttpServletRequest request) {

		System.out
		.println(" *********** inside updateCounterDetails ************ ");
		HttpSession session = request.getSession(false);
		try {
			Long flag = counterDetailsService
					.checkCounterNameForUpdate(counterDetails.getId(), counterDetails
							.getCounterName());
			if (flag > 0) {
				session.setAttribute("msg",
						"This Counter Name Alreday Exists!!! Please Try Other.");
			}
		   else {
				long code = counterDetailsService
						.checkCounterNumberForUpdate(counterDetails.getId(),counterDetails
								.getCounterNumber());
				if (code > 0) {
					session.setAttribute("msg",
							"This Counter Number Alreday Exists!!! Please Try Other.");
				} else {
					counterDetailsService.update(counterDetails);
					session.setAttribute("msg",
							"Counter Details Modified Successfully.");
				}
		   }
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/counterDetails";

	}

	@RequestMapping(value="/databaseBackup",method={RequestMethod.POST,RequestMethod.GET})
	public String databaseBackup(Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Administration");
		model.addAttribute("childHead1", "Database Backup");
		return "administration/databaseBackup";	
	}
	
	
	@RequestMapping(value = "/muncipalityDetails", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String muncipalityDetails(
			@ModelAttribute("muncipalityDetails")  MuncipalityDetailsEntity muncipalityDetailsEntity,
			BindingResult bindingResult, Model model, HttpServletRequest request) 
	{
		System.out.println(" *********** inside MuncipalityDetails ************ ");
		
		List<MuncipalityDetailsEntity> muncipal = muncipalityDetailsService.getAllMuncipalityRecords();
		model.addAttribute("muncipal", muncipal);
		model.addAttribute("muncipalityDetails", new MuncipalityDetailsEntity());
		
		return "administration/municipalityMaster";
	}
	
	@RequestMapping(value = "/addMuncipalityDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public String addMuncipalityDetails(@ModelAttribute("muncipalityDetails") MuncipalityDetailsEntity muncipalityDetailsEntity,
			BindingResult bingingResult, ModelMap model,
			HttpServletRequest request)
	{

		System.out.println(" *********** inside addMuncipalityDetails ************ ");
		HttpSession session = request.getSession(false);
		try {
			System.out.println("Name ===> " + muncipalityDetailsEntity.getMuncipalityName());

			Long flag = muncipalityDetailsService.checkForMunicipalityNameAvailability(muncipalityDetailsEntity.getMuncipalityName());
			if (flag > 0) {
				session.setAttribute("msg",
						"This Municipality Name Alreday Exists!!! Please Try Other.");
			}  else {
					muncipalityDetailsService.save(muncipalityDetailsEntity);
					session.setAttribute("msg",
							"Municipality Details Submitted Successfully.");
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<MuncipalityDetailsEntity> municipal = muncipalityDetailsService.getAllMuncipalityRecords();
		model.addAttribute("municipal", municipal);
		model.addAttribute("muncipalityDetails", new MuncipalityDetailsEntity());
		session.setAttribute("mainHead", "Administration");
		session.setAttribute("childHead1", "Muncipality Details");
		return "redirect:/muncipalityDetails";
		
	}
	
	
	@RequestMapping(value = "/getMunicipalDataForEditing/{id}", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	Object getMunicipalDataForEditing(@PathVariable int id) {
		return muncipalityDetailsService.find(id);
	}

	@RequestMapping(value = "/updateMunicipalDetails", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String updateMunicipalDetails(
			@ModelAttribute("muncipalityDetails") MuncipalityDetailsEntity muncipalityDetailsEntity,
			BindingResult bingingResult, ModelMap model,
			HttpServletRequest request) {

		System.out.println(" *********** inside updateMunicipalDetails ************ ");
		HttpSession session = request.getSession(false);
		try {
			
			Long flag=muncipalityDetailsService.checkMunicipalityNames(muncipalityDetailsEntity.getId(),muncipalityDetailsEntity.getMuncipalityName());
			
			
			if (flag > 0) {
				session.setAttribute("msg",
						"This Municipality Name Alreday Exists!!! Please Try Other.");
			} else {
				muncipalityDetailsService.update(muncipalityDetailsEntity);
				session.setAttribute("msg",
						"Municipality Details Modified Successfully.");
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/muncipalityDetails";

	}

	@RequestMapping(value="/getWardDetails/{muncipal_Id}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getWardDetails(@PathVariable String muncipal_Id){
		return wardDetailsService.findByMunicipalityId(muncipal_Id);
	}
	
	
	@RequestMapping(value="/getMap",method={RequestMethod.GET,RequestMethod.POST})
	public String getMap(Model model){
		return "administration/map";
	}
	
}

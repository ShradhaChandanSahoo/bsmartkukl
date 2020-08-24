package com.bcits.bsmartwater.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bcits.bsmartwater.dao.ApplicationSettingsDAO;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.model.ApplicationSettingsEntity;
import com.bcits.bsmartwater.model.UserLog;
import com.bcits.bsmartwater.service.ApplicationSettingService;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.service.CloseMonthEndService;
import com.bcits.bsmartwater.service.ConsumerMasterService;
import com.bcits.bsmartwater.service.PaymentService;
import com.bcits.bsmartwater.service.UserLogService;
import com.bcits.bsmartwater.service.UserMasterService;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;
import com.bcits.bsmartwater.utils.SessionData;

@Controller
@PropertySource(value = { "classpath:application.properties" })
public class LoginController {
	
	Timestamp today=new Timestamp(new java.util.Date().getTime());
	
	@Autowired
	private ApplicationSettingService applicationSettingService; 
	
	@Autowired
	private ApplicationSettingsDAO applicationSettingsDAO;
	
	@Autowired
	BillingLedgerService billingLedgerService;
	
	@Autowired
	ConsumerMasterService consumerMasterService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	UserMasterService userMasterService;
	
	@Autowired
	private UserLogService userLogService;
	
	@Autowired
	CloseMonthEndService closeMonthEndService;
	@Autowired
	private MasterGenericDAO masterGenericDAO;
	
	int activeCheck=0;
	private static int expirydatestatus=0;
	
	
	@RequestMapping(value = {"/", "/index" },method={RequestMethod.POST,RequestMethod.GET})
	public String landingPage(Model model,HttpServletRequest request){
		//System.out.println("Landing Page");
		return "index";
	}
	
	@RequestMapping(value ="/landingpage",method={RequestMethod.POST,RequestMethod.GET})
	public String landingpage(Model model,HttpServletRequest request){
		//System.out.println("Landing Page");
		return "index";
	}
	
	
	@RequestMapping(value = {"/loginPage","/login"},method={RequestMethod.POST,RequestMethod.GET})
	public String login(Model model,HttpServletRequest request){
		String applicationSettingData = applicationSettingService.getSettingsData("themeLayout");
		applicationSettingService.getApplicationSettingsData();
		model.addAttribute("themeLayout", applicationSettingData);
		//code need to written
		model.addAttribute("locationName", applicationSettingData);
		HttpSession session = request.getSession();	
		if(activeCheck==1) {
			model.addAttribute("activeCheck", "User is Deactivated. Please activate User to login!");
			activeCheck=0;
		}
		if(expirydatestatus==1) {
			model.addAttribute("expirydate", "Software expired due to payment.for Renewal plz talk to Administrator");
			expirydatestatus=0;
		}
		
		if(session.getAttribute("authenticated")!=null && session.getAttribute("authenticated").equals(true)){
			return "redirect:/dashboard";
		} else{
			return "login";
		}
	}
	
	@RequestMapping(value = "/loginFailure",method={RequestMethod.POST,RequestMethod.GET})
	public String loginFailure(Model model,HttpServletRequest request,HttpServletResponse response){
		model.addAttribute("error", "Invalid username and password");
		String applicationSettingData = applicationSettingService.getSettingsData("themeLayout");
		model.addAttribute("themeLayout", applicationSettingData);
		return "login";		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response,Model model){
		//System.out.println("------inside Logout-------");
		String applicationSettingData = applicationSettingService.getSettingsData("themeLayout");
		String userName = (String)SessionData.getUserDetails().get("userID");
		model.addAttribute("themeLayout", applicationSettingData);		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	    	HttpSession session = request.getSession();
			session.removeAttribute("authenticated");
			BsmartWaterLogger.logger.info(userName+"----userName");
			UserLog userLog=userLogService.findLogByUserId(userName);
			BsmartWaterLogger.logger.info(userLog+"---"+userLog.getLogin_time());
			userLog.setLogout_time(new Date());
			userLog.setLogin_sts("OUT");
			userLogService.update(userLog);
			new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";
	}
	
	@RequestMapping("/logoutpage")
	public String logoutpage(HttpServletRequest request,HttpServletResponse response,Model model){
		
		try{
		String applicationSettingData = applicationSettingService.getSettingsData("themeLayout");
		String userName = (String)SessionData.getUserDetails().get("userID");
		model.addAttribute("themeLayout", applicationSettingData);		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	    	HttpSession session = request.getSession();
			session.removeAttribute("authenticated");
			UserLog userLog=userLogService.findLogByUserId(userName);
			BsmartWaterLogger.logger.info(userLog+"---"+userLog.getLogin_time());
			userLog.setLogout_time(new Date());
			userLog.setLogin_sts("OUT");
			userLogService.update(userLog);
			new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    	return "redirect:/login?logout";
		}catch(Exception e){
			return "redirect:/login?logout";	
		}
	}
	
	@RequestMapping(value="/dashboard",method={RequestMethod.POST,RequestMethod.GET})
	public String dashboard(Model model,HttpServletRequest request){
		try{

			model.addAttribute("childHead2", "Dashboard");
			//String applicationSettingData = applicationSettingService.getSettingsData("themeLayout");		
			HttpSession session = request.getSession(false);
			String userName = (String)SessionData.getUserDetails().get("userID");
			//session.setAttribute("defaultDate",new SimpleDateFormat(applicationSettingService.getDateDefaultFormat()).format(new Date()));
			session.setAttribute("defaultDate",new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
			session.setAttribute("userName", userName);	

			if(!"null".equalsIgnoreCase(userName) && userName!=null && !"".equalsIgnoreCase(userName)){
				Object[] userOfficialName1=userMasterService.getOfficialName(userName);
				session.setAttribute("userOfficialName1", userOfficialName1[0]);
				session.setAttribute("userbdesignation", userOfficialName1[1]);
			}
			Date dk=new Date();
			String engdate=new SimpleDateFormat("dd-MM-yyyy").format(dk);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String date=sdf.format(dk);
			Date d=sdf.parse(date);
			Date d1=sdf.parse("2020-12-28");//june 5
			Date d2=sdf.parse("2020-12-31");//june 10
			
			if(d.compareTo(d1)>=0  && d.compareTo(d2)<0  )
			{
				model.addAttribute("expirydate","Please Do Payment Otherwise Software will close from Aug 1st onwards.for Renewal plz talk to Administrator");
			}
			else if(d.compareTo(d2)>=0)
			{
				expirydatestatus=1;
				return "redirect:/login";
			}
				
			
			String english[]=engdate.split("-");
			int cday=Integer.parseInt(english[0]);
			
			if("02".equalsIgnoreCase(english[1])){
				cday=cday+2;
			}
			if("04".equalsIgnoreCase(english[1])){
				cday=cday+1;
			}
			if("06".equalsIgnoreCase(english[1])){
				cday=cday+1;
			}
			if("09".equalsIgnoreCase(english[1])){
				cday=cday+1;
			}
			if("11".equalsIgnoreCase(english[1])){
				cday=cday+1;
			}
			
			int cmonth=Integer.parseInt(english[1]);
			int cyear=Integer.parseInt(english[2]);
			//System.out.println(cday+"cday"+cmonth+"cmonth"+cyear);
			String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			

			session.setAttribute("authenticated",true);

			String bc=(String)session.getAttribute("branchcode");
			String activeStatus=(String)session.getAttribute("activeStatus");
			
			if(bc==null || "null".equalsIgnoreCase(bc)){
				return "redirect:/login";
			}
			if("Inactive".equalsIgnoreCase(activeStatus)){
				activeCheck=1;
				return "redirect:/logout";
			}
			
			
			
			String schemaName1=(String)session.getAttribute("schemaName1");
			if(schemaName1.equalsIgnoreCase("CORPORATE") || schemaName1.equalsIgnoreCase("CIRCLE") ){
				System.out.println("multiDash Board Methode==>"+schemaName1);
				model.addAttribute("branchcode",bc);
				session.setAttribute("logintype","CORPORATE");
				return "newCorporateDashBoard";
			}else{

				List<?> wardData=billingLedgerService.getWardWiseBillCount((String)session.getAttribute("branchcode"));
				model.addAttribute("wardData", wardData);

				List<?> wardDataun=billingLedgerService.getWardWiseBillCountUnMetered((String)session.getAttribute("branchcode"));
				model.addAttribute("wardDataun", wardDataun);
				session.setAttribute("logintype","BRANCH");
				String sitecode=(String)session.getAttribute("branchcode");

				if("1115".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Tripureshwor");
				}else if("1112".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Baneshwor");
				}
				else if("1118".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Lalitpur");
				}
				else if("1116".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Bhaktapur");
				}
				else if("1110".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Mahankalchaur");
				}
				else if("1111".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Maharajganj");
				}
				else if("1113".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Kamaladi");
				}
				else if("1114".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Chettrapati");
				}
				else if("1117".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Thimi");
				}
				else if("1119".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","Kirtipur");
				}
				else if("2222".equalsIgnoreCase(sitecode)){
					session.setAttribute("BranchName","TestLocation");
				}

				String monthYearNep1 = billingLedgerService.getLatestMonthYear((String)session.getAttribute("branchcode"));

				if(monthYearNep1!=null && !"".equalsIgnoreCase(monthYearNep1) && !"null".equalsIgnoreCase(monthYearNep1) ){

					String year=monthYearNep1.substring(0, 4);
					String month=monthYearNep1.substring(4, 6);
					model.addAttribute("monthYear",year+"-"+month);

				}

				String monthYearNep=closeMonthEndService.getLatestMonthYear();

				if(monthYearNep!=null && !"".equalsIgnoreCase(monthYearNep) && !"null".equalsIgnoreCase(monthYearNep) ){

					String yearc=monthYearNep.substring(0, 4);
					String monthc=monthYearNep.substring(4, 6);

					if("12".equalsIgnoreCase(monthc)){
						monthYearNep=(Integer.parseInt(yearc)+1)+"00";
					}
					String latestNepaliMonth=""+(Integer.parseInt(monthYearNep)+1);

					String monthyearNepLMY = (String)session.getAttribute("monthyearNepLMY");
					if(!"null".equalsIgnoreCase(monthyearNepLMY) && monthyearNepLMY!=null && !"".equalsIgnoreCase(monthyearNepLMY)){

					}else{
						session.setAttribute("monthyearNepLMY",latestNepaliMonth);
					}
				}


				String totalBilled = billingLedgerService.getTotalBilledCount(sitecode); 
				model.addAttribute("totalBilled", totalBilled==null?0:totalBilled);

				String totalDemand = billingLedgerService.getTotalDemand(sitecode); 
				model.addAttribute("totalDemand", totalDemand==null?0:totalDemand);

				String totalCollection = paymentService.getTotalCollection(sitecode); 
				model.addAttribute("totalCollection", totalCollection==null?0:totalCollection);

				Object[] obj=billingLedgerService.getMasterStatistics();

				if(obj!=null){

					model.addAttribute("dom",obj[0] );
					model.addAttribute("gov",obj[1] );
					model.addAttribute("indcom",obj[2] );
					model.addAttribute("met",obj[3] );
					model.addAttribute("unm",obj[4] );
					model.addAttribute("p05",obj[5] );
					model.addAttribute("p075",obj[6] );
					model.addAttribute("p1",obj[7] );
					model.addAttribute("p15",obj[8] );
					model.addAttribute("p2",obj[9] );
					model.addAttribute("p3",obj[10] );
					model.addAttribute("p4",obj[11] );
					model.addAttribute("po",obj[12] );
					model.addAttribute("totalConsumers", obj[13]);
					model.addAttribute("pno",obj[14] );
					model.addAttribute("ptem",obj[15] );
					model.addAttribute("pper",obj[16] );
					model.addAttribute("pdef",obj[17] );
					model.addAttribute("pcother",obj[18] );
					model.addAttribute("ncwap",obj[19] );
					model.addAttribute("ncapp",obj[20] );

				}
			}
			//session.setAttribute("themeLayout", applicationSettingData);
			session.setAttribute("themeLayout", "layout3");
		}catch(Exception e){
			e.printStackTrace();
			return "redirect:/login";
		}
		
		return "dashboard";	
		
	}
	
	
	/*@Async
	public void saveUserLog(String ssoId,String status,String ipAddress)  {
		try{
			UserLog userLog=new UserLog();
			if("success".equalsIgnoreCase(status)){
				userLog.setLogin_sts("IN");
				userLog.setRemarks("Success");
			}else{
				userLog.setLogout_time(new Date());
				userLog.setLogin_sts("OUT");
				userLog.setRemarks("invalid password");
			}
			userLog.setIp_address(ipAddress);
			userLog.setLogin_type("Normal");
			userLog.setUser_id(ssoId);
			userLog.setLogin_time(new Date());
			userLogService.save(userLog);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	@RequestMapping(value="/admintest",method={RequestMethod.POST,RequestMethod.GET})
	public String admintest(Model model,HttpServletRequest request){
		
		return "admin";	
	}
	
	@RequestMapping(value="/dbatest",method={RequestMethod.POST,RequestMethod.GET})
	public String usertest(Model model,HttpServletRequest request){
		
		return "dba";	
	}
	
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied(Principal user) {

		ModelAndView model = new ModelAndView();

		if (user != null) {
			model.addObject("msg", "Hi " + user.getName()
			+ ", you do not have permission to access this page!");
		} else {
			model.addObject("msg",
			"You do not have permission to access this page!");
		}

		model.setViewName("403");
		return model;

	}
	@RequestMapping(value="/applicationSettings",method={RequestMethod.POST,RequestMethod.GET})
	public String applicationSettings(Model model,HttpServletRequest request){
		model.addAttribute("alldata", applicationSettingService.getApplicationSettingsData());
		List<ApplicationSettingsEntity> data=applicationSettingService.getApplicationSettingsData();
		if(!data.isEmpty()){
			model.addAttribute("dateVal", data.get(0).getOptionValue());
			model.addAttribute("themeVal", data.get(1).getOptionValue());
		}
		model.addAttribute("mainHead", "Application Settings");
		return "applicationSettings";	
	}
	@RequestMapping(value="/updateApplicationSettings",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String updateApplicationSettings(@RequestParam String themeLayout,@RequestParam String dateFormate,HttpServletRequest request){
		
		ApplicationSettingsEntity dateSet=applicationSettingsDAO.find(1);
		dateSet.setOptionValue(dateFormate);
		applicationSettingsDAO.update(dateSet);
		
		ApplicationSettingsEntity themSet=applicationSettingsDAO.find(2);
		themSet.setOptionValue(themeLayout);
		applicationSettingsDAO.update(themSet);
		return "Update Successfull";
	}
	
	@RequestMapping(value="/getGraphicalViewForMonth",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getGraphicalViewForMonth(HttpServletResponse response,HttpServletRequest request)
	{
		HttpSession session=request.getSession(false);
		String sitecode=(String)session.getAttribute("branchcode");
		List<String> monthData =  paymentService.getGraphicalViewDataForMonth(sitecode);	
		BsmartWaterLogger.logger.info("monthData ===> "+monthData.size());
		return monthData;
	}
	
	
}
package com.bcits.bsmartwater.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bsmartwater.model.UserDesignationEntity;
import com.bcits.bsmartwater.model.UserMaster;
import com.bcits.bsmartwater.model.UserRolesEntity;
import com.bcits.bsmartwater.model.UserTypeEntity;
import com.bcits.bsmartwater.service.ModuleMasterService;
import com.bcits.bsmartwater.service.UserDesignationService;
import com.bcits.bsmartwater.service.UserMasterService;
import com.bcits.bsmartwater.service.UserRoleService;
import com.bcits.bsmartwater.service.UserTypeService;

@Controller
public class UserManagementController {
	
	@Autowired
	private UserTypeService userTypeService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private UserDesignationService userDesignationService;
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private ModuleMasterService masterService;
	
	int createflag=0;
	int createRoles=0;
	int createDesignation=0;
	int createUser=0;
	int passwordchange=0;
	
	//------------------------------------------------------usertype-------------------------------------------------------
	@RequestMapping(value = {"/usertype"},method={RequestMethod.POST,RequestMethod.GET})
	public String usertype(Model model, HttpServletRequest request,final Locale locale){
		model.addAttribute("userTypeEntity",new UserTypeEntity());
		model.addAttribute("userList",userTypeService.readUserType());
		if(createflag==1){
			model.addAttribute("msg","User Type Created Successfully");
			createflag=0;
		}
		if(createflag==2){
			model.addAttribute("msg","User Type Updated Successfully");
			createflag=0;
		}
		return "usermanagement/usertype";
	}
	
	@RequestMapping(value = "/createUserType", method = {RequestMethod.GET, RequestMethod.POST})
	public String createUserType(@ModelAttribute("userType")UserTypeEntity userType,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		userTypeService.save(userType);
		createflag=1;
		return "redirect:/usertype";
		
	}
	@RequestMapping(value = "/editUserType", method = {RequestMethod.GET, RequestMethod.POST})
	public String editUserType(@ModelAttribute("userType")UserTypeEntity userType,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		userTypeService.update(userType);
		createflag=2;
		return "redirect:/usertype";
		
	}
	@RequestMapping(value="/uniqueUserType/{usertype}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueUserType(@PathVariable String usertype,HttpServletRequest request){
		String str=userTypeService.uniqueUserType(usertype);
		return str;
	}
	@RequestMapping(value="/uniqueUserTypeForEdit/{usertype}/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueUserTypeForEdit(@PathVariable String usertype,@PathVariable int id,HttpServletRequest request){
		String str=userTypeService.uniqueUserTypeForEdit(usertype,id);
		return str;
	}
	/*---------------------------------------------------user role-----------------------------------------------------------*/
	@RequestMapping(value = {"/userRoles"},method={RequestMethod.POST,RequestMethod.GET})
	public String userRoles(Model model,HttpServletRequest request){
		if(createRoles==1){
			model.addAttribute("msg","User Roles Created Successfully");
			createRoles=0;
		}
		if(createRoles==2){
			model.addAttribute("msg","User Roles Updated Successfully");
			createRoles=0;
		}
		
		model.addAttribute("moduleData",masterService.getModuleNames());
		model.addAttribute("userRolesEntity",new UserRolesEntity());
		model.addAttribute("userTypeData",userTypeService.getUserTypeWithId());
		model.addAttribute("userRoleData",userRoleService.getUserRolesData());
		return "usermanagement/userRoles";
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = {"/resetUsersPwd"},method={RequestMethod.POST,RequestMethod.GET})
	public String resetUsersPwd(Model model,HttpServletRequest request){
		/*if(createRoles==1){
			model.addAttribute("msg","User Roles Created Successfully");
			createRoles=0;
		}
		if(createRoles==2){
			model.addAttribute("msg","User Roles Updated Successfully");
			createRoles=0;
		}
		
		model.addAttribute("moduleData",masterService.getModuleNames());
		model.addAttribute("userRolesEntity",new UserRolesEntity());
		model.addAttribute("userTypeData",userTypeService.getUserTypeWithId()); */
		model.addAttribute("userMasterData",userMasterService.getUserMasterData());
		
		return "usermanagement/resetUsersPwd";
	}
	
	@RequestMapping(value = {"/resetUsersPwdSubmit"},method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String resetUsersPwdSubmit(Model model,HttpServletRequest request){

		int uid=Integer.parseInt(request.getParameter("uid"));
		System.out.println(uid+"---");
		String defaultPwd="kukl@123";
		UserMaster umaster=userMasterService.editUserMasterData(uid);
		umaster.setPassword(defaultPwd);
		userMasterService.update(umaster);
		
		return "Password Reset to kukl@123 !";
	}
	
	
	
	
	//------------------------------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping(value = "/createUserRoles", method = {RequestMethod.GET, RequestMethod.POST})
	public String createUserRoles(@ModelAttribute("userRolesEntity")UserRolesEntity userRolesEntity,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		userRoleService.save(userRolesEntity);
		createRoles=1;
		return "redirect:/userRoles";
		
	}
	@RequestMapping(value = "/editUserRoles", method = {RequestMethod.GET, RequestMethod.POST})
	public String editUserType(@ModelAttribute("userType")UserRolesEntity userRolesEntity,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		userRoleService.update(userRolesEntity);
		createRoles=2;
		return "redirect:/userRoles";
		
	}
	@RequestMapping(value="/uniqueUserRoleType/{usertypeid}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueUserRoleType(@PathVariable Integer usertypeid,HttpServletRequest request){
		String str=userRoleService.uniqueUserRoleType(usertypeid);
		return str;
	}
	@RequestMapping(value="/uniqueUserRolesForEdit/{usertype}/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueUserRolesForEdit(@PathVariable int usertype,@PathVariable int id,HttpServletRequest request){
		String str=userRoleService.uniqueUserRolesForEdit(usertype,id);
		return str;
	}
	/*===================================================user designations=======================================================*/
	@RequestMapping(value = {"/userdesignation"},method={RequestMethod.POST,RequestMethod.GET})
	public String userdesignation(Model model, HttpServletRequest request,final Locale locale){
		model.addAttribute("userDesignationEntity",new UserDesignationEntity());
		model.addAttribute("userList",userDesignationService.readUserDesignation());
		if(createDesignation==1){
			model.addAttribute("msg","User Designation Created Successfully");
			createDesignation=0;
		}
		if(createDesignation==2){
			model.addAttribute("msg","User Designation Updated Successfully");
			createDesignation=0;
		}
		return "usermanagement/userdesignation";
	}
	
	@RequestMapping(value = "/createUserDesignation", method = {RequestMethod.GET, RequestMethod.POST})
	public String createUserDesignation(@ModelAttribute("userDesignationEntity")UserDesignationEntity userDesignationEntity,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		userDesignationService.save(userDesignationEntity);
		createDesignation=1;
		return "redirect:/userdesignation";
		
	}
	@RequestMapping(value = "/editUserDesignation", method = {RequestMethod.GET, RequestMethod.POST})
	public String editUserDesignation(@ModelAttribute("userDesignationEntity")UserDesignationEntity userDesignationEntity,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		userDesignationService.update(userDesignationEntity);
		createDesignation=2;
		return "redirect:/userdesignation";
		
	}
	@RequestMapping(value="/uniqueUserDesignation/{userdesignation}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueUserDesignation(@PathVariable String userdesignation,HttpServletRequest request){
		String str=userDesignationService.uniqueUserDesignation(userdesignation);
		return str;
	}
	@RequestMapping(value="/uniqueUserDesignationForEdit/{userdesignation}/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueUserDesignationForEdit(@PathVariable String userdesignation,@PathVariable int id,HttpServletRequest request){
		String str=userDesignationService.uniqueUserDesignationForEdit(userdesignation,id);
		return str;
	}
	
	
	
	/*===================================================User Master===========================================================*/
	
	@RequestMapping(value = {"/usermaster"},method={RequestMethod.POST,RequestMethod.GET})
	public String userMaster(Model model,HttpServletRequest request){
		if(createUser==1){
			model.addAttribute("msg","User Created Successfully");
			createUser=0;
		}
		if(createUser==2){
			model.addAttribute("msg","User Updated Successfully");
			createUser=0;
		}
		if(createUser==3){
			model.addAttribute("msg","User Deleted Successfully");
			createUser=0;
		}
		model.addAttribute("userMaster",new UserMaster());
		model.addAttribute("userRolesData",userMasterService.getUserRoles());
		model.addAttribute("userDesignations",userDesignationService.readUserDesignation());
		model.addAttribute("userMasterData",userMasterService.getUserMasterData());
		return "usermanagement/usermaster";
	}
	
	@RequestMapping(value = "/createUserMaster", method = {RequestMethod.GET, RequestMethod.POST})
	public String createUserMaster(@ModelAttribute("userMaster")UserMaster userMaster,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		userMasterService.save(userMaster);
		createUser=1;
		return "redirect:/usermaster";
		
	}
	@RequestMapping(value="/uniqueUserName/{userloginName}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueUserName(@PathVariable String userloginName,HttpServletRequest request){
		String str=userMasterService.uniqueUserName(userloginName);
		return str;
	}
	@RequestMapping(value="/editUserMaster/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody UserMaster uniqueUserName(@PathVariable int id,HttpServletRequest request){
		UserMaster userMaster=userMasterService.editUserMasterData(id);
		return userMaster;
	}
	
	@RequestMapping(value="/changeUserStatus/{id}/{status}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String changeUserStatus(@PathVariable int id,@PathVariable String status,HttpServletRequest request){
		try{
		
			UserMaster userMaster=userMasterService.editUserMasterData(id);
			if("Inactive".equalsIgnoreCase(status)){
				userMaster.setStatus("Active");
				userMasterService.update(userMaster);
				return "User Activated Successfully";
				
			}else{
				userMaster.setStatus("Inactive");
				userMasterService.update(userMaster);
				return "User DeActivated Successfully";
			}
		}catch(Exception e){
			e.printStackTrace();
			return "Something went wring Please try again..";
		}
	}
	
	
	
	@RequestMapping(value="/uniqueUserMasterForEdit/{user_login_name}/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String uniqueUserMasterForEdit(@PathVariable String user_login_name,@PathVariable int id,HttpServletRequest request){
		String str=userMasterService.uniqueUserMasterForEdit(user_login_name,id);
		return str;
	}
	@RequestMapping(value = "/updateUserMaster", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateUserMaster(@ModelAttribute("userMaster")UserMaster userMaster,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		userMasterService.update(userMaster);
		createUser=2;
		return "redirect:/usermaster";
		
	}
	@RequestMapping(value = "/deleteUserData", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteUserData(@ModelAttribute("userMaster")UserMaster userMaster,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		System.out.println(userMaster.getId());
		userMasterService.deleteUser(userMaster.getId());
		createUser=3;
		return "redirect:/usermaster";
		
	}
	//User password change 
	@RequestMapping(value = {"/userprofile"},method={RequestMethod.POST,RequestMethod.GET})
	public String userprofile(Model model,HttpServletRequest request){
		if(passwordchange==1){
			model.addAttribute("msg","Password Changed Successfully. Please Logout And Login Again");
			passwordchange=0;
		}else if(passwordchange==2){
			model.addAttribute("msg","Entered Current Password Is Wrong");
			passwordchange=0;
		}else if(passwordchange==3){
			model.addAttribute("msg","Unable To Change Password. Please Try With Diffrent New Password");
			passwordchange=0;
		}
		return "usermanagement/userprofile";
	}
	
	
	@RequestMapping(value = "/changepassword", method = {RequestMethod.GET, RequestMethod.POST})
	public String changepassword(ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		String username=req.getParameter("username");
		UserMaster userMaster=userMasterService.findUser(username, req);
		String currentpassword=req.getParameter("currentpassword");
		try{
		if(currentpassword.equals(userMaster.getPassword())){
			String newpassword=req.getParameter("newpassword");
			userMaster.setPassword(newpassword);
			userMasterService.update(userMaster);
			passwordchange=1;
		}else{
			passwordchange=2;
		}
		}catch(Exception e){
			passwordchange=3;
		}
		
		return "redirect:/userprofile";
		
	}
	
	@RequestMapping(value = "/userLog", method = RequestMethod.GET)
	public String userlog(HttpServletRequest req)
	{
		  return "reports/userLog";
	}
	
	@RequestMapping(value = "/users/monitorUsers", method = RequestMethod.GET)
	public @ResponseBody List<?> monitorUsers(HttpServletRequest req) throws ParseException
	{
		  List<?> result=userMasterService.monitorUsers(new SimpleDateFormat("dd-MM-YYYY").format(new Date()));
		  return result;
	}
}

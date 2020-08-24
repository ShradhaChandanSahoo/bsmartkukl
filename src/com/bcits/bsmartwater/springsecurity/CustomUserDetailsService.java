package com.bcits.bsmartwater.springsecurity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bcits.bsmartwater.model.UserLog;
import com.bcits.bsmartwater.model.UserMaster;
import com.bcits.bsmartwater.service.ModuleMasterService;
import com.bcits.bsmartwater.service.UserLogService;
import com.bcits.bsmartwater.service.UserMasterService;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private ModuleMasterService moduleMasterService; 
	
	@Autowired
	private UserLogService userLogService;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String ssoId){
		
		try{
		ServletRequestAttributes reqAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = reqAttributes.getRequest();
		String location=(String)request.getParameter("location");
		
		HttpSession session=request.getSession(true);
	    //session.setAttribute("schemaName", location);
	    //String str[]=location.split("_");
	    //session.setAttribute("branch", str[0]);
	   // session.setAttribute("branchcode", str[1]);
	    String corporate=(String)request.getParameter("location2");
	    
	    
	    
	    if(corporate.equalsIgnoreCase("CORPORATE")){
			session.setAttribute("schemaName", "KUKL_BSMART");
			session.setAttribute("schemaName1", corporate);
			session.setAttribute("branch", "KUKL_BSMART");
			 session.setAttribute("branchcode","KUKL_BSMART");
		}else if(corporate.equalsIgnoreCase("BRANCH")){
			 session.setAttribute("schemaName", location);
			 session.setAttribute("schemaName1", corporate);
			 String str[]=location.split("_");
			 session.setAttribute("branch", str[0]);
			 session.setAttribute("branchcode", str[1]);
		}
	    
	    UserMaster userMaster=null;
	    try{
	    	userMaster=userMasterService.findUser(ssoId,request);
	    }catch(Exception e){
	    	userMaster=null;
	    	e.printStackTrace();
	    }
	    
	    String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
		
	    if(userMaster==null){
			String status="fail";
			session.setAttribute("invalidUNP","Invalid User Name / Password .Please try again..");
			saveUserLog(ssoId,status,ip);
			throw new UsernameNotFoundException("Username not found");
		}else{
			String status="success";
			saveUserLog(ssoId,status,ip);
		}
	    
		  
		
		  String roles=userMaster.getUserRolesEntity().getUser_roles();
		  String type=userMaster.getUserRolesEntity().getUserTypeEntity().getUser_type();
		  String[] modules=roles.split(",");
		  Set<String> modulenames=new HashSet<>();
		  for(String module:modules){
			  modulenames.add(module);
		  }
		  
		session.setAttribute("activeStatus",userMaster.getStatus());	
		session.setAttribute("activeModulesNew",modulenames);	
		session.setAttribute("userRoleType",type);	
		session.setAttribute("activeModules",moduleMasterService.getAllModulesForLeftMenu(modulenames));	
		
		return new org.springframework.security.core.userdetails.User(userMaster.getUser_login_name(), userMaster.getPassword(), 
				 true, true, true, true, getGrantedAuthorities(userMaster));
		}catch(Exception e){
			e.printStackTrace();
			throw new UsernameNotFoundException("Username not found");
		}
	}


	private void saveUserLog(String ssoId,String status,String ip)  {
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
			
			userLog.setUser_id(ssoId);
			userLog.setLogin_time(new Date());
			userLog.setIp_address(ip);
			userLog.setLogin_type("Normal");
			userLogService.save(userLog);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private List<GrantedAuthority> getGrantedAuthorities(UserMaster userMaster){
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();		
		/*List<UserRole> list=userRoleService.findUserRoleBasedONUserId(user.getUserId());
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			UserRole userRole = (UserRole) iterator.next();
			authorities.add(new SimpleGrantedAuthority("ROLE_"+userRole.getRoleName()));
		}	*/	
		
		return authorities;
	}
	
	
	
	
	
}

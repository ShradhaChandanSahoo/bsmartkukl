package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.UserMasterDAO;
import com.bcits.bsmartwater.model.UserMaster;
import com.bcits.bsmartwater.service.UserMasterService;

@Service
public class UserMasterServiceImpl implements UserMasterService {

	@Autowired
	private UserMasterDAO userMasterDAO;
	
	@Override
	public void save(UserMaster userMaster) {
		userMasterDAO.customsave(userMaster);
		
	}

	@Override
	public List<UserMaster> getUserMasterData() {
		return userMasterDAO.getUserMasterData();
	}

	@Override
	public List<?> getUserRoles() {
		List<?> list=userMasterDAO.getUserRoles();
		List<Map<String, Object>> result=new ArrayList<>();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> data=new HashMap<>();
			data.put("userRoleId", (Integer)obj[0]);
			data.put("userType", (String)obj[1]);
			result.add(data);
		}
		
		return result;
	}

	@Override
	public String uniqueUserName(String userloginName) {
		return userMasterDAO.uniqueUserName(userloginName);
	}

	@Override
	public UserMaster editUserMasterData(int id) {
		return userMasterDAO.editUserMasterData(id);
	}

	@Override
	public String getUserDesignationFromChecks() {
		return userMasterDAO.getUserDesignationFromChecks();
	}

	@Override
	public String uniqueUserMasterForEdit(String user_login_name, int id) {
		return userMasterDAO.uniqueUserMasterForEdit(user_login_name,id);
	}

	@Override
	public void update(UserMaster userMaster) {
		userMasterDAO.customupdate(userMaster);
	}

	@Override
	public void deleteUser(int id) {
		userMasterDAO.deleteUser(id);
	}

	@Override
	public UserMaster findUser(String ssoId) {
		return userMasterDAO.findUser(ssoId);
	}

	@Override
	public UserMaster findUser(String ssoId, HttpServletRequest request) {
		return userMasterDAO.findUser(ssoId,request);
	}

	@Override
	public Object[] getOfficialName(String user_login_name) {
		return userMasterDAO.getOfficialName(user_login_name);
	}

	@Override
	public List<?> monitorUsers(String logindate) {
		//final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss.S aa");
		int slNo = 1;
		List<Map<String, Object>> result=new ArrayList<>();
		for(Iterator<?> iterator=userMasterDAO.monitorUsers(logindate).iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> data=new HashMap<>();
			data.put("slNo", slNo++);
			data.put("name", obj[0]);
			data.put("designation", obj[1]);
			
			if(obj[2]==null){
				
			}else{
				data.put("logintime", obj[2]);
			}
			if(obj[3]==null){
				data.put("logouttime","");
			}else{
				data.put("logouttime", obj[3]);
			}
			
			if(obj[4]!=null){
				String s=(String)obj[4];
				
				if("IN".equalsIgnoreCase(s)){
					data.put("loginstatus", "Active");
				}else{
					data.put("loginstatus", "Inactive");
				    }
			}
			data.put("logintype", obj[5]);
			data.put("duration", "");
			result.add(data);
			
			
		}
		
		return result;
	}

}

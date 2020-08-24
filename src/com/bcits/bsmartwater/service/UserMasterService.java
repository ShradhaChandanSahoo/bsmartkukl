package com.bcits.bsmartwater.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bsmartwater.model.UserMaster;

public interface UserMasterService {

	void save(UserMaster userMaster);

	List<UserMaster> getUserMasterData();

	List<?> getUserRoles();

	String uniqueUserName(String userloginName);

	UserMaster editUserMasterData(int id);

	String getUserDesignationFromChecks();

	String uniqueUserMasterForEdit(String user_login_name, int id);

	void update(UserMaster userMaster);

	void deleteUser(int id);

	UserMaster findUser(String ssoId, HttpServletRequest request);

	UserMaster findUser(String ssoId);

	Object[] getOfficialName(String user_login_name);

	List<?> monitorUsers(String logindate);

}

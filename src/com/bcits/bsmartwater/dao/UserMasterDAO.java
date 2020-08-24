package com.bcits.bsmartwater.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bsmartwater.model.UserMaster;

public interface UserMasterDAO extends GenericDAO<UserMaster> {

	List<UserMaster> getUserMasterData();

	List<?> getUserRoles();

	String uniqueUserName(String userloginName);

	UserMaster editUserMasterData(int id);

	String getUserDesignationFromChecks();

	String uniqueUserMasterForEdit(String user_login_name, int id);

	void deleteUser(int id);

	UserMaster findUser(String ssoId);

	UserMaster findUser(String ssoId, HttpServletRequest request);

	Object[] getOfficialName(String user_login_name);

	List<?> monitorUsers(String logindate);

}

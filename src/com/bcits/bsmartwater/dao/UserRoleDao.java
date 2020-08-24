package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.UserRolesEntity;

public interface UserRoleDao extends GenericDAO<UserRolesEntity> {

	
	List<UserRolesEntity> getUserRolesData();

	String uniqueUserRoleType(Integer usertype);

	String uniqueUserRoleType(int usertype, int id);

}

package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.UserRolesEntity;


public interface UserRoleService {

	void save(UserRolesEntity userRolesEntity);

	List<UserRolesEntity> getUserRolesData();

	String uniqueUserRoleType(Integer usertypeid);

	String uniqueUserRolesForEdit(int usertype, int id);

	void update(UserRolesEntity userRolesEntity);

}

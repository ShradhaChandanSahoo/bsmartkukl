package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.UserTypeEntity;

public interface UserTypeDAO extends GenericDAO<UserTypeEntity>{

	List<UserTypeEntity> readUserType();

	String uniqueUserType(String usertype);

	String uniqueUserTypeForEdit(String usertype, int id);

	List<?> getUserTypeWithId();

}

package com.bcits.bsmartwater.service;


import java.util.List;

import com.bcits.bsmartwater.model.UserTypeEntity;

public interface UserTypeService {

	void save(UserTypeEntity userType);

	List<UserTypeEntity> readUserType();

	String uniqueUserType(String usertype);

	void update(UserTypeEntity userType);

	String uniqueUserTypeForEdit(String usertype, int id);

	List<?> getUserTypeWithId();

}

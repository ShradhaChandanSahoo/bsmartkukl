package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.UserDesignationEntity;

public interface UserDesignationDAO extends GenericDAO<UserDesignationEntity> {

	List<?> readUserDesignation();

	String uniqueUserDesignation(String userdesignation);

	String uniqueUserDesignationForEdit(String userdesignation, int id);

}

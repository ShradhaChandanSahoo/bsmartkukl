package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.UserDesignationEntity;

public interface UserDesignationService {

	List<?> readUserDesignation();

	void save(UserDesignationEntity userDesignationEntity);

	void update(UserDesignationEntity userDesignationEntity);

	String uniqueUserDesignation(String userdesignation);

	String uniqueUserDesignationForEdit(String userdesignation, int id);

}

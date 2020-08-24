package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.UserDesignationDAO;
import com.bcits.bsmartwater.model.UserDesignationEntity;
import com.bcits.bsmartwater.service.UserDesignationService;
@Service
public class UserDesignationServiceImpl implements UserDesignationService {

	@Autowired
	private UserDesignationDAO userDesignationDAO;
	
	@Override
	public List<?> readUserDesignation() {
		return userDesignationDAO.readUserDesignation();
	}

	@Override
	public void save(UserDesignationEntity userDesignationEntity) {
		userDesignationDAO.customsave(userDesignationEntity);
		
	}

	@Override
	public void update(UserDesignationEntity userDesignationEntity) {
		userDesignationDAO.customupdate(userDesignationEntity);
		
	}

	@Override
	public String uniqueUserDesignation(String userdesignation) {
		return userDesignationDAO.uniqueUserDesignation(userdesignation);
	}

	@Override
	public String uniqueUserDesignationForEdit(String userdesignation, int id) {
		return userDesignationDAO.uniqueUserDesignationForEdit(userdesignation,id);
	}

}

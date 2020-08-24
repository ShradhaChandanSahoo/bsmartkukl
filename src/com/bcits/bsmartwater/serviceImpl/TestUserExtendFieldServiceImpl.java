package com.bcits.bsmartwater.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.TestUserExtendFieldDAO;
import com.bcits.bsmartwater.model.TestUserExtendedField;
import com.bcits.bsmartwater.service.TestUserExtendFieldService;

@Service
public class TestUserExtendFieldServiceImpl implements TestUserExtendFieldService {

	@Autowired
	private TestUserExtendFieldDAO testUserExtendFieldDAO;
	@Override
	public TestUserExtendedField save( TestUserExtendedField testUserExtendedField) {
		return testUserExtendFieldDAO.save(testUserExtendedField);
	}
}

package com.bcits.bsmartwater.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.TestUserDAO;
import com.bcits.bsmartwater.model.TestUser;
import com.bcits.bsmartwater.service.TestUserService;
@Service
public class TestUserServiceImpl implements TestUserService {

	@Autowired
	private TestUserDAO testUserDAO;
	
	@Override
	public TestUser save(TestUser testuser) {
		return testUserDAO.save(testuser);
	}

}

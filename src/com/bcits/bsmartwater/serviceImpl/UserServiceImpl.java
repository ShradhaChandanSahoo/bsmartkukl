package com.bcits.bsmartwater.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.UserServiceDao;
import com.bcits.bsmartwater.model.User;
import com.bcits.bsmartwater.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserServiceDao userServiceDao;

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return  userServiceDao.findById(id);
	}

	@Override
	public User findBySso(String sso) {
		// TODO Auto-generated method stub
		return userServiceDao.findBySso(sso);
	}

}

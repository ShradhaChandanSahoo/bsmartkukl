package com.bcits.bsmartwater.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.UserLogDao;
import com.bcits.bsmartwater.model.UserLog;
import com.bcits.bsmartwater.service.UserLogService;

@Service
public class UserLogServiceImpl implements UserLogService {

	@Autowired
	private UserLogDao userLogDao;
	
	@Override
	public void save(UserLog userLog) {
		userLogDao.customsave(userLog);
	}

	@Override
	public UserLog findLogByUserId(String userName) {
		return userLogDao.findLogByUserId(userName);
	}

	@Override
	public void update(UserLog userLog) {
		userLogDao.customupdate(userLog);		
	}

}

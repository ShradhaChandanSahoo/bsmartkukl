package com.bcits.bsmartwater.dao;

import com.bcits.bsmartwater.model.UserLog;

public interface UserLogDao extends GenericDAO<UserLog>{

	UserLog findLogByUserId(String userName);

}

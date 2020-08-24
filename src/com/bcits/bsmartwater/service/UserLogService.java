package com.bcits.bsmartwater.service;

import com.bcits.bsmartwater.model.UserLog;

public interface UserLogService {

	void save(UserLog userLog);

	UserLog findLogByUserId(String userName);

	void update(UserLog userLog);

}

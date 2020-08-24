package com.bcits.bsmartwater.dao;

import com.bcits.bsmartwater.model.User;

public interface UserServiceDao extends GenericDAO<User> {

	User findById(int id);

	User findBySso(String sso);

}

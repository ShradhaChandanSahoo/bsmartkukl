package com.bcits.bsmartwater.daoImpl;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.UserServiceDao;
import com.bcits.bsmartwater.model.User;
@Repository
public class UserServiceDaoImpl extends GenericDAOImpl<User> implements UserServiceDao {

	@Override
	public User findById(int id) {
		return null;
	}

	@Override
	public User findBySso(String sso) {		
		return (User) entityManager.createNamedQuery("User.findUserObject").setParameter("userName", sso).getSingleResult();
	}

}

package com.bcits.bsmartwater.daoImpl;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.UserLogDao;
import com.bcits.bsmartwater.model.UserLog;

@Repository
public class UserLogDaoImpl extends GenericDAOImpl<UserLog> implements UserLogDao {

	@Override
	public UserLog findLogByUserId(String user_id) {
		try{
		return getCustomEntityManager().createNamedQuery("UserLog.findLogByUserId",UserLog.class).setParameter("user_id", user_id.trim().toUpperCase()).getSingleResult();
		}catch(Exception e){
			return null;
			
		}
		}
	
	
}

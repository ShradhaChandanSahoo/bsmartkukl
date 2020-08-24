package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.UserRoleDao;
import com.bcits.bsmartwater.model.UserRolesEntity;
import com.bcits.bsmartwater.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDao userRoleDAO;

	@Override
	public void save(UserRolesEntity userRolesEntity) {
		userRoleDAO.customsave(userRolesEntity);
	}

	@Override
	public List<UserRolesEntity> getUserRolesData() {
		return userRoleDAO.getUserRolesData();
	}

	@Override
	public String uniqueUserRoleType(Integer usertype) {
		return userRoleDAO.uniqueUserRoleType(usertype);
	}

	@Override
	public String uniqueUserRolesForEdit(int usertype, int id) {
		return userRoleDAO.uniqueUserRoleType(usertype,id);
	}

	@Override
	public void update(UserRolesEntity userRolesEntity) {
		userRoleDAO.customupdate(userRolesEntity);
	}


}

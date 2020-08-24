package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.UserTypeDAO;
import com.bcits.bsmartwater.model.UserTypeEntity;
import com.bcits.bsmartwater.service.UserTypeService;

@Service
public class UserTypeServiceImpl implements UserTypeService {

	@Autowired
	private UserTypeDAO userTypeDAO;

	@Override
	public void save(UserTypeEntity userType) {
		 userTypeDAO.customsave(userType);
	}

	@Override
	public List<UserTypeEntity> readUserType() {
		return userTypeDAO.readUserType();
	}

	@Override
	public String uniqueUserType(String usertype) {
		return userTypeDAO.uniqueUserType(usertype);
	}

	@Override
	public void update(UserTypeEntity userType) {
		userTypeDAO.customupdate(userType);
		
	}

	@Override
	public String uniqueUserTypeForEdit(String usertype, int id) {
		return userTypeDAO.uniqueUserTypeForEdit(usertype,id);
	}

	@Override
	public List<?> getUserTypeWithId() {
		List<?> list=userTypeDAO.getUserTypeWithId();
		List<Map<String, Object>> result=new ArrayList<>();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> data=new HashMap<>();
			data.put("userTypeId", (Integer)obj[0]);
			data.put("userType", (String)obj[1]);
			result.add(data);
		}
		
		return result;
	}

}

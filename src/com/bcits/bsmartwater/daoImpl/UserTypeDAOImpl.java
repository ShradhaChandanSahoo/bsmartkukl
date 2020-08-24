package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.UserTypeDAO;
import com.bcits.bsmartwater.model.UserTypeEntity;

@Repository
public class UserTypeDAOImpl extends GenericDAOImpl<UserTypeEntity> implements UserTypeDAO {

	@Override
	public List<UserTypeEntity> readUserType() {
		return getCustomEntityManager().createNamedQuery("UserTypeEntity.readUserType",UserTypeEntity.class).getResultList();
	}

	@Override
	public String uniqueUserType(String usertype) {
		List<UserTypeEntity> list=getCustomEntityManager().createNamedQuery("UserTypeEntity.uniqueUserType",UserTypeEntity.class).setParameter("usertype",usertype).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}

	@Override
	public String uniqueUserTypeForEdit(String usertype, int id) {
		List<UserTypeEntity> list=getCustomEntityManager().createNamedQuery("UserTypeEntity.uniqueUserTypeForEdit",UserTypeEntity.class).setParameter("usertype",usertype).setParameter("id", id).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}

	@Override
	public List<?> getUserTypeWithId() {
		return getCustomEntityManager().createNamedQuery("UserTypeEntity.getUserTypeWithId").getResultList();
	}

}

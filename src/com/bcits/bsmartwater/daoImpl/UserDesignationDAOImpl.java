package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.UserDesignationDAO;
import com.bcits.bsmartwater.model.UserDesignationEntity;

@Repository
public class UserDesignationDAOImpl extends GenericDAOImpl<UserDesignationEntity> implements UserDesignationDAO {

	@Override
	public List<?> readUserDesignation() {
		return getCustomEntityManager().createNamedQuery("UserDesignationEntity.readUserDesignation",UserDesignationEntity.class).getResultList();
	}

	@Override
	public String uniqueUserDesignation(String userdesignation) {
		List<UserDesignationEntity> list=getCustomEntityManager().createNamedQuery("UserDesignationEntity.uniqueUserDesignation",UserDesignationEntity.class).setParameter("userdesignation",userdesignation).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}

	@Override
	public String uniqueUserDesignationForEdit(String userdesignation, int id) {
		List<UserDesignationEntity> list=getCustomEntityManager().createNamedQuery("UserDesignationEntity.uniqueUserDesignationForEdit",UserDesignationEntity.class).setParameter("userdesignation",userdesignation).setParameter("id", id).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}

}

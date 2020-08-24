package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.UserRoleDao;
import com.bcits.bsmartwater.model.UserRolesEntity;

@Repository
public class UserRoleDaoImpl extends GenericDAOImpl<UserRolesEntity> implements UserRoleDao {

	
	@Override
	public List<UserRolesEntity> getUserRolesData() {
		return getCustomEntityManager().createNamedQuery("UserRolesEntity.getUserRolesData",UserRolesEntity.class).getResultList();
	}

	@Override
	public String uniqueUserRoleType(Integer usertypeid) {
		List<UserRolesEntity> list=getCustomEntityManager().createNamedQuery("UserRolesEntity.uniqueUserRoleType",UserRolesEntity.class).setParameter("usertypeid",usertypeid).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}

	@Override
	public String uniqueUserRoleType(int usertype, int id) {
		List<UserRolesEntity> list=getCustomEntityManager().createNamedQuery("UserRolesEntity.uniqueUserRoleTypeForEdit",UserRolesEntity.class).setParameter("usertypeid",usertype).setParameter("id", id).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}

}

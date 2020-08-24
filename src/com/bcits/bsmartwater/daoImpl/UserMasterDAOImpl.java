package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.dao.UserMasterDAO;
import com.bcits.bsmartwater.model.UserMaster;

@Repository
public class UserMasterDAOImpl extends GenericDAOImpl<UserMaster> implements UserMasterDAO {

	@Override
	public List<UserMaster> getUserMasterData() {
		return getCustomEntityManager().createNamedQuery("UserMaster.getUserMasterData",UserMaster.class).getResultList();
	}

	@Override
	public List<?> getUserRoles() {
		return getCustomEntityManager().createNamedQuery("UserRolesEntity.getUserRoles").getResultList();
	}

	@Override
	public String uniqueUserName(String userloginName) {
		List<UserMaster> list=getCustomEntityManager().createNamedQuery("UserMaster.uniqueUserName",UserMaster.class).setParameter("userloginName",userloginName).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}

	@Override
	public UserMaster editUserMasterData(int id) {
		return getCustomEntityManager().createNamedQuery("UserMaster.getUserMaster",UserMaster.class).setParameter("id", id).getSingleResult();
	}

	@Override
	public String getUserDesignationFromChecks() {
		return getCheckConstraints("BSW_USERS", "DESIGNATION_CHECKS");
	}

	@Override
	public String uniqueUserMasterForEdit(String user_login_name, int id) {
		List<UserMaster> list=getCustomEntityManager().createNamedQuery("UserMaster.uniqueUserMasterForEdit",UserMaster.class).setParameter("user_login_name",user_login_name).setParameter("id", id).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}

	@Override
	public void deleteUser(int id) {
		getCustomEntityManager().createNamedQuery("UserMaster.deleteUser").setParameter("id", id).executeUpdate();
	}

	@Override
	public UserMaster findUser(String ssoId) {
		
		return getCustomEntityManager().createNamedQuery("UserMaster.getUserMasterLogin",UserMaster.class).setParameter("user_login_name", ssoId).getSingleResult();
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public UserMaster findUser(String ssoId, HttpServletRequest request) {
		  
		HttpSession session=request.getSession(false);
		String schemaName1=(String)session.getAttribute("schemaName1");
		
		if(("CORPORATE").equalsIgnoreCase(schemaName1)){
			return entityManager.createNamedQuery("UserMaster.getUserMasterLogin",UserMaster.class).setParameter("user_login_name", ssoId).getSingleResult();

		}else{
			return getCustomEntityManager().createNamedQuery("UserMaster.getUserMasterLogin",UserMaster.class).setParameter("user_login_name", ssoId).getSingleResult();

		}
	}

	@Override
	public Object[] getOfficialName(String user_login_name) {
		try{  
		return (Object[]) getCustomEntityManager().createNamedQuery("UserMaster.getOfficialName").setParameter("user_login_name", user_login_name.toUpperCase().trim()).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		}

	@Override
	public List<?> monitorUsers(String logindate) {
		String query="SELECT UM.USER_NAME,DE.DESIGNATION,TO_CHAR(UL.LOGIN_TIME, 'DD-MM-YYYY HH:MI:SS AM'),TO_CHAR(UL.LOGOUT_TIME, 'DD-MM-YYYY HH:MI:SS AM'),UL.LOGIN_STS,UL.LOGIN_TYPE FROM BSW_USER_LOG UL,BSW_USERS UM,BSW_DESIGNATION DE "
					 +" WHERE UPPER(UL.USER_ID)=UPPER(UM.USER_LOGIN_NAME) AND UM.DESIGNATION=DE.ID AND TRUNC(UL.LOGIN_TIME)=TO_DATE('"+logindate+"', 'dd-MM-yyyy')";
		System.out.println(query);
		return getCustomEntityManager().createNativeQuery(query).getResultList();

	}

}

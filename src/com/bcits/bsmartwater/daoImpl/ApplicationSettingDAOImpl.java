package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.ApplicationSettingsDAO;
import com.bcits.bsmartwater.model.ApplicationSettingsEntity;

@Repository
public class ApplicationSettingDAOImpl extends GenericDAOImpl<ApplicationSettingsEntity> implements ApplicationSettingsDAO {

	@Override
	public String getSettingsData(String themeLayout) {
		try{
			return entityManager.createNamedQuery("ApplicationSettingsEntity.getSettingsData",String.class).setParameter("themeLayout", themeLayout).setMaxResults(1).getSingleResult();
		} catch(Exception e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationSettingsEntity> getApplicationSettingsData() {
		return entityManager.createNamedQuery("ApplicationSettingsEntity.getApplicationSettingsData").getResultList();
	}

}

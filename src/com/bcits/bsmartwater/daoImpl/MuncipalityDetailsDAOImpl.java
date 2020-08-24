package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.MuncipalityDetailsDAO;
import com.bcits.bsmartwater.model.MuncipalityDetailsEntity;

@Repository
public class MuncipalityDetailsDAOImpl extends GenericDAOImpl<MuncipalityDetailsEntity> implements MuncipalityDetailsDAO
{

	@SuppressWarnings("unchecked")
	@Override
	public List<MuncipalityDetailsEntity> getAllMuncipalityRecords() {
		
		List<MuncipalityDetailsEntity> municipal = null;
		try {
			municipal = getCustomEntityManager().createNamedQuery("MuncipalityDetailsEntity.getAllMuncipalityRecords").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return municipal;
	}

	@Override
	public Long checkForMunicipalityNameAvailability(String muncipalityName) {
	
		Long i = null;
		try {
			i = Long.parseLong(String.valueOf(getCustomEntityManager()
					.createNamedQuery(
							"MuncipalityDetailsEntity.checkForMunicipalityNameAvailability")
					.setParameter("muncipalityName", muncipalityName).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public long checkMunicipalityNames(int id, String muncipalityName) {
			Long data=null;
			try {
				data=Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("MuncipalityDetailsEntity.checkMunicipalityNames").setParameter("id", id).setParameter("muncipalityName", muncipalityName).getSingleResult()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		return data;
	}

}

package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.ObservationMasterDAO;
import com.bcits.bsmartwater.model.ObservationEntity;

@Repository
public class observationMasterDAOImpl extends GenericDAOImpl<ObservationEntity> implements ObservationMasterDAO
{

	@SuppressWarnings("unchecked")
	@Override
	public List<ObservationEntity> getAllObservationRecords() {
		
		List<ObservationEntity> observe = null;
		try {
			observe = getCustomEntityManager().createNamedQuery(
					"ObservationEntity.getAllObservationRecords").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return observe;
		
	}

	@Override
	public Long checkForObservationNameAvailability(String observationName) {
		Long i = null ;
		try {
			i = Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("ObservationEntity.checkForObservationNameAvailability").setParameter("observationName", observationName).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ObservationEntity> getObserevationDataForEditing(String id) {
		
		List<ObservationEntity> editObservationDetails=null;
		try {
			editObservationDetails = getCustomEntityManager().createNamedQuery("ObservationEntity.editObservationDetails").setParameter("id",Integer.parseInt(id)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return editObservationDetails;
	}


	@Override
	public String findNameById(Integer id) {
	     try{
	    	 System.out.println(id+"----");
	    	 return getCustomEntityManager().createNamedQuery("ObservationEntity.findNameById",String.class).setParameter("id", id).getSingleResult();
	     }catch(Exception e){
	    	 e.printStackTrace();
	    	 return null;
	     }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getAllObservationRecordsBill() {
		List<ObservationEntity> observe = null;
		try {
			observe = getCustomEntityManager().createNamedQuery(
					"ObservationEntity.getAllObservationRecordsBill").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return observe;
	}

}

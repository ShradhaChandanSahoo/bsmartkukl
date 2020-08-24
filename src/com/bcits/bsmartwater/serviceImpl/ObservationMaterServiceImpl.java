package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.ObservationMasterDAO;
import com.bcits.bsmartwater.model.ObservationEntity;
import com.bcits.bsmartwater.service.ObservationMasterService;

@Service
public class ObservationMaterServiceImpl implements ObservationMasterService
{

	@Autowired
	ObservationMasterDAO observationMasterDAO;
	@Override
	public List<ObservationEntity> getAllObservationRecords() {
		List<ObservationEntity> observe = observationMasterDAO.getAllObservationRecords();
		return observe;
	}
	@Override
	public Long checkForObservationNameAvailability(String observationName) {
		Long l = observationMasterDAO.checkForObservationNameAvailability(observationName);
		return l;
	}
	@Override
	public void save(ObservationEntity observationEntity) {
		observationMasterDAO.customsave(observationEntity);
		
	}


	
	@Override
	public List<ObservationEntity> getObserevationDataForEditing(String id) {
		
		List<ObservationEntity> editObservationDetails=observationMasterDAO.getObserevationDataForEditing(id);
		return editObservationDetails;
	}
	@Override
	public void update(ObservationEntity observationEntity) {
		
		observationMasterDAO.customupdate(observationEntity);
	}
	@Override
	public ObservationEntity findById(int id) {
		return 	observationMasterDAO.customfind(id);

	}
	@Override
	public List<?> getAllObservationRecordsBill() {
		return observationMasterDAO.getAllObservationRecordsBill();
		
	}
	


}

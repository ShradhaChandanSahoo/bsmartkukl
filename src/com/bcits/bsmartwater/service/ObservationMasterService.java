package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.ObservationEntity;

public interface ObservationMasterService
{

	List<ObservationEntity> getAllObservationRecords();

	Long checkForObservationNameAvailability(String observationName);

	void save(ObservationEntity observationEntity);

	List<ObservationEntity> getObserevationDataForEditing(String id);

	void update(ObservationEntity observationEntity);

	ObservationEntity findById(int mcstatus);

	List<?> getAllObservationRecordsBill();


}

package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.ObservationEntity;

public interface ObservationMasterDAO extends GenericDAO<ObservationEntity> 
{

	List<ObservationEntity> getAllObservationRecords();

	Long checkForObservationNameAvailability(String observationName);

	List<ObservationEntity> getObserevationDataForEditing(String id);

	String findNameById(Integer integer);

	List<?> getAllObservationRecordsBill();

	

}

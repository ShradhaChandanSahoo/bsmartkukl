package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.WardDetailsEntity;

public interface WardDetailsDAO extends GenericDAO<WardDetailsEntity>
{

	List<WardDetailsEntity> getAllWardRecords();

//	Long checkForWardNameAvailability(String wardName);

	int checkForWardNoAvailability(String wardNo);

	List<WardDetailsEntity> getWardData();

	int uniqueWardNo(int wardNo);


	List<WardDetailsEntity> findByMunicipalityId(String muncipal_Id);

	String getWardNoById(int parseInt);

	Long checkForWardNoUpdate(int id, String wardNo);

	

	




}

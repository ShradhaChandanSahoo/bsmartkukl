package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.WardDetailsEntity;

public interface WardDetailsService 
{

	List<WardDetailsEntity> getAllWardRecords();

	//Long checkForWardNameAvailability(String wardName);

	int checkForWardNoAvailability(String wardNo);

	void save(WardDetailsEntity wardDetailsEntity);

	void update(WardDetailsEntity wardDetailsEntity);

	WardDetailsEntity find(int id);

	List<WardDetailsEntity> getWardData();

	int uniqueWardNo(int wardNo);

	Object findByMunicipalityId(String muncipal_Id);

	String getWardNoById(String ward_no);

	Long checkForWardNoUpdate(int id, String wardNo);

	

	

}

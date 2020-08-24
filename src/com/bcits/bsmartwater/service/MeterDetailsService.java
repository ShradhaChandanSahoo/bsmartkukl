package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.MeterDetailsEntity;

public interface MeterDetailsService 
{
	void save(MeterDetailsEntity meterDetailsEntity);

	List<?> viewConnMeterDetails(String connectionno);

	List<MeterDetailsEntity> getMeterDetailsDataByConnectionNum(String connId);

	void customSave(MeterDetailsEntity meterDetailsEntity);

	
	//List<MeterDetailsEntity> readMeterDetails();

}

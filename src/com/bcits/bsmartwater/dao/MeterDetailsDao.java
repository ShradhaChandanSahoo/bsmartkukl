package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.MeterDetailsEntity;

public interface MeterDetailsDao extends GenericDAO<MeterDetailsEntity> 
{

	String uniqueMeterDetails(String connectionno);

	List<MeterDetailsEntity> readMeterDetails();

	String uniqueMeterDetailsForEdit(String connectionno, int id);

	String checkConnMeterDetails(String connectionno);

	List<MeterDetailsEntity> viewConnMeterDetails(String connectionno);

	List<MeterDetailsEntity> getMeterDetailsDataByConnectionNum(String connId);

	List<MeterDetailsEntity> findByConnNo(String connectionno);

	String uniqueMeterNoChk(String meterNo);

}

package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.MeterObservationEntity;

public interface MeterObservationDao extends GenericDAO<MeterObservationEntity> 
{

	String uniqueObsNo(String observationno);

	List<MeterObservationEntity> searchConnNo(String connectionno);

	String uniqueObsSearchNoForEdit(String observationno, int id);

}

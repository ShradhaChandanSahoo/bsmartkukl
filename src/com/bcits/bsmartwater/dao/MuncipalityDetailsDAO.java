package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.MuncipalityDetailsEntity;

public interface MuncipalityDetailsDAO extends GenericDAO<MuncipalityDetailsEntity>
{

	List<MuncipalityDetailsEntity> getAllMuncipalityRecords();

	Long checkForMunicipalityNameAvailability(String muncipalityName);

	long checkMunicipalityNames(int id, String muncipalityName);

}

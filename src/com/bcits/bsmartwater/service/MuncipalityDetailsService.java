package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.MuncipalityDetailsEntity;

public interface MuncipalityDetailsService {

	List<MuncipalityDetailsEntity> getAllMuncipalityRecords();

	Long checkForMunicipalityNameAvailability(String muncipalityName);

	void save(MuncipalityDetailsEntity muncipalityDetailsEntity);

	Object find(int id);

	void update(MuncipalityDetailsEntity muncipalityDetailsEntity);

	Long checkMunicipalityNames(int id, String muncipalityName);

}

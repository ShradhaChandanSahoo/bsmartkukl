package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.MuncipalityDetailsDAO;
import com.bcits.bsmartwater.model.MuncipalityDetailsEntity;
import com.bcits.bsmartwater.service.MuncipalityDetailsService;



@Service
public class MuncipalityDetailsServiceImpl implements MuncipalityDetailsService
{
	
	@Autowired
	MuncipalityDetailsDAO muncipalityDetailsDAO;

	@Override
	public List<MuncipalityDetailsEntity> getAllMuncipalityRecords() {
	
		List<MuncipalityDetailsEntity> municipal = muncipalityDetailsDAO.getAllMuncipalityRecords();
		return municipal;
	}

	@Override
	public Long checkForMunicipalityNameAvailability(String muncipalityName) {
	
		Long l = muncipalityDetailsDAO.checkForMunicipalityNameAvailability(muncipalityName);
		return l;
	}

	@Override
	public void save(MuncipalityDetailsEntity muncipalityDetailsEntity) {
		muncipalityDetailsDAO.customsave(muncipalityDetailsEntity);
		
	}

	@Override
	public Object find(int id) {
		
		return muncipalityDetailsDAO.customfind(id);
	}

	@Override
	public void update(MuncipalityDetailsEntity muncipalityDetailsEntity) {
		
		muncipalityDetailsDAO.customupdate(muncipalityDetailsEntity);
	}

	@Override
	public Long checkMunicipalityNames(int id, String muncipalityName) {
       long l=muncipalityDetailsDAO.checkMunicipalityNames(id,muncipalityName);
		return l;
	}

}

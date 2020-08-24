package com.bcits.bsmartwater.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.MeterObservationDao;
import com.bcits.bsmartwater.model.MeterObservationEntity;
import com.bcits.bsmartwater.service.MeterObservationService;

@Service
public class MeterObservationServiceImpl implements MeterObservationService 
{
	@Autowired
	MeterObservationDao meterObservationDao;
	
	@Override
	public void save(MeterObservationEntity meterObservationEntity) 
	{
		meterObservationDao.customsave(meterObservationEntity);
	}
}

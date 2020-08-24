package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.MeterDetailsDao;
import com.bcits.bsmartwater.model.MeterDetailsEntity;
import com.bcits.bsmartwater.service.MeterDetailsService;

@Service
public class MeterDetailsServiceImpl implements MeterDetailsService 
{
	@Autowired
	MeterDetailsDao meterDetailsDao;
	
	@Override
	public void save(MeterDetailsEntity meterDetailsEntity) 
	{
		meterDetailsDao.customsave(meterDetailsEntity);
	}
	
	@SuppressWarnings("serial")
	@Override
	public List<?> viewConnMeterDetails(String connectionno) {

		List<MeterDetailsEntity> list= meterDetailsDao.viewConnMeterDetails(connectionno);
		List<Map<String, Object>> viewconnectionDetailsMap =  new ArrayList<Map<String, Object>>();         
		for (final MeterDetailsEntity record :list) 
		  {
			viewconnectionDetailsMap.add(new HashMap<String, Object>() {	 
			{  
				put("metcon_date_nep", record.getMetcon_date_nep());
				put("metcon_date_eng", record.getMetcon_date_eng());
				put("meter_no", record.getMeter_no());
				put("meter_capacity", record.getMeter_capacity());
				put("meter_make", record.getMeter_make());
				put("meter_own", record.getMeter_own());
				put("ir", record.getIr());
				put("ins_date_nep", record.getIns_date_nep());
				put("ins_date_eng", record.getIns_date_eng());
				put("cal_date_nep", record.getCal_date_nep());
				put("cal_date_eng", record.getCal_date_eng());
				put("calibrated_officer", record.getCalibrated_officer());
				
			}});
	 }
		return viewconnectionDetailsMap;
	 }

	@Override
	public List<MeterDetailsEntity> getMeterDetailsDataByConnectionNum(String connId) {
		return meterDetailsDao.getMeterDetailsDataByConnectionNum(connId);
	}

	@Override
	public void customSave(MeterDetailsEntity meterDetailsEntity) {
		meterDetailsDao.customsave(meterDetailsEntity);
	}
}

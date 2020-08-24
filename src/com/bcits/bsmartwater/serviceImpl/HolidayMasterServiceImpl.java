package com.bcits.bsmartwater.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.HolidayMasterDao;
import com.bcits.bsmartwater.model.HolidayMaster;
import com.bcits.bsmartwater.service.HolidayMasterService;

@Service
public class HolidayMasterServiceImpl implements HolidayMasterService {
	@Autowired
	HolidayMasterDao holidayMasterDao;
	
	@Override
	public HolidayMaster getRecordByDate(Date date) {
		return holidayMasterDao.getRecordByDate(date);
	}

}

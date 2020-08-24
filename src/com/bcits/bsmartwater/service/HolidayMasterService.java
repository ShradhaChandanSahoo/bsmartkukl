package com.bcits.bsmartwater.service;

import java.util.Date;

import com.bcits.bsmartwater.model.HolidayMaster;

public interface HolidayMasterService {

	HolidayMaster getRecordByDate(Date date);

}

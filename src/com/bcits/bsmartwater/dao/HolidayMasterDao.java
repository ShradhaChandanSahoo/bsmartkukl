package com.bcits.bsmartwater.dao;

import java.util.Date;

import com.bcits.bsmartwater.model.HolidayMaster;

public interface HolidayMasterDao extends GenericDAO<HolidayMaster>{

	HolidayMaster getRecordByDate(Date date);

}

package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.WardToMrEntity;

public interface WardToMrService {
	
	void save(WardToMrEntity wardToMrEntity);
	void update(WardToMrEntity wardToMrEntity);
	List<?> getAllMeterReader();
	String uniqueWardToMrDetails(String ward_no, int reading_day);
	WardToMrEntity getReaderByWardNoReadingDay(String wardNo, int readingday);
}

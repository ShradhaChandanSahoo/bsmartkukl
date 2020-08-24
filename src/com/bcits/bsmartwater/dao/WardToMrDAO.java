package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.WardToMrEntity;

public interface WardToMrDAO extends GenericDAO<WardToMrEntity>{

	List<?> getAllMeterReader();

	String uniqueWardToMrDetails(String ward_no, int reading_day);

	WardToMrEntity getReaderByWardNoReadingDay(String wardNo, int readingday);
}

package com.bcits.bsmartwater.serviceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.WardToMrDAO;
import com.bcits.bsmartwater.model.WardToMrEntity;
import com.bcits.bsmartwater.service.WardToMrService;

@Service
public class WardToMrServiceImpl implements WardToMrService{

	@Autowired
	WardToMrDAO wardToMrDao;
	
	@Override
	public void save(WardToMrEntity wardToMrEntity) {
		wardToMrDao.customsave(wardToMrEntity);
	}

	@Override
	public void update(WardToMrEntity wardToMrEntity) {
		wardToMrDao.customupdate(wardToMrEntity);
	}

	@Override
	public List<?> getAllMeterReader() {
		List<?> list=wardToMrDao.getAllMeterReader();
		List<Map<String, Object>> result = new ArrayList<>();
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", obj[0]);
			data.put("ward_no", obj[1]);
			data.put("reading_day", obj[2]);
			data.put("mr_id", obj[3]);
			data.put("mr_name", obj[4]);
			data.put("added_by", obj[5]);
			data.put("added_date", obj[6]);
			data.put("mrid", obj[7]);
			result.add(data);
		}
		return result;
	}
	
	@Override
	public String uniqueWardToMrDetails(String ward_no, int reading_day) {
		return wardToMrDao.uniqueWardToMrDetails(ward_no, reading_day);
	}

	@Override
	public WardToMrEntity getReaderByWardNoReadingDay(String wardNo, int readingday) {
		return wardToMrDao.getReaderByWardNoReadingDay(wardNo, readingday);
	}

}

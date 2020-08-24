package com.bcits.bsmartwater.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.TariffRateMasterDao;
import com.bcits.bsmartwater.model.BranchDetailsEntity;
import com.bcits.bsmartwater.model.TariffRateMaster;
import com.bcits.bsmartwater.service.TariffRateMasterService;

@Service
public class TariffRateMasterServiceImpl implements TariffRateMasterService {

	@Autowired
	TariffRateMasterDao tariffRateMasterDao;
	
	
/*	@SuppressWarnings("serial")
	@Override
	public List<?> getTariffRates() {
		
		List<?> tariffList= tariffRateMasterDao.getTariffRates();
		List<Map<String, Object>> tariff = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = tariffList.iterator(); i.hasNext();) {
			tariff.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("diameter_tap", (BigDecimal)values[0]);
					put("min_consumption", (BigDecimal)values[1]);
					put("minimum_charages", (BigDecimal)values[2]);
					put("rate_per_1000_ltr", (BigDecimal)values[3]);
					put("monthly_charges", (BigDecimal)values[4]);
					put("sw_charge_percent",(BigDecimal)values[5]);
					
					
				}
			});
		}
		return tariff;
	
	}*/

	@Override
	public TariffRateMaster getTariffRate(double pipesize,String connectionType) {
		
		return tariffRateMasterDao.getTariffRate(pipesize,connectionType);
	}
	

	@Override
	public void save(TariffRateMaster tariffRateMaster) {
		tariffRateMasterDao.save(tariffRateMaster);
	}


	@Override
	public List<TariffRateMaster> getTariffRates() {
		List<TariffRateMaster> tariff = tariffRateMasterDao.getTariffRates();
		return tariff;
	}


	@Override
	public void update(TariffRateMaster tariffRateMaster) {
		
		tariffRateMasterDao.update(tariffRateMaster);
		
	}


	@Override
	public TariffRateMaster find(int id) {
		
		return  tariffRateMasterDao.find(id);
	}


	@Override
	public TariffRateMaster checkTapAndMeterType(double diameter_tap,
			String meter_type) {
		
		return tariffRateMasterDao.checkTapAndMeterType(diameter_tap,meter_type);
	}


	@Override
	public TariffRateMaster getById(int id, double diameter_tap, String meter_type) {

		return tariffRateMasterDao.getById(id,diameter_tap,meter_type);
	}


}

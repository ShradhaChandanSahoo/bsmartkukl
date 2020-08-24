package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.TariffRateConversionDAO;
import com.bcits.bsmartwater.model.TariffRateConversion;
import com.bcits.bsmartwater.service.TariffRateConversionService;

@Service
public class TariffRateConversionServiceImpl implements TariffRateConversionService {

	
	@Autowired
	private TariffRateConversionDAO tariffRateConversionDAO;
	
	@Override
	public void save(TariffRateConversion tariffRateConversion) {
		tariffRateConversionDAO.customsave(tariffRateConversion);
		
	}

	@Override
	public List<TariffRateConversion> getTariffDetails() {
		List<TariffRateConversion> tariffDetails=tariffRateConversionDAO.getTariffDetails();
		return tariffDetails ;
	}

	@Override
	public Object find(int id) {
		
		return tariffRateConversionDAO.customfind(id);
	}

	@Override
	public void update(TariffRateConversion tariffRateConversion) {
	
		tariffRateConversionDAO.customupdate(tariffRateConversion);
	}

}

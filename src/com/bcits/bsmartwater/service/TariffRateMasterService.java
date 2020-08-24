package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.TariffRateMaster;

public interface TariffRateMasterService {

	List<TariffRateMaster> getTariffRates();

	TariffRateMaster getTariffRate(double pipesize,String connectionType);

	

	void save(TariffRateMaster tariffRateMaster);

	void update(TariffRateMaster tariffRateMaster);

	TariffRateMaster find(int id);

//	Long checkTapAndMeterType(Double diameter_tap, String meter_type);

	TariffRateMaster checkTapAndMeterType(double diameter_tap, String meter_type);

	TariffRateMaster getById(int id, double diameter_tap, String meter_type);

}

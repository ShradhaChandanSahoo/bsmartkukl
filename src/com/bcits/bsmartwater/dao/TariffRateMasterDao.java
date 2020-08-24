package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.TariffRateMaster;

public interface TariffRateMasterDao extends GenericDAO<TariffRateMaster>{

	List<TariffRateMaster> getTariffRates();

	TariffRateMaster getTariffRate(double pipesize,String connectionType);

	//Long checkTapAndMeterType(Double diameter_tap, String meter_type);

	TariffRateMaster checkTapAndMeterType(double diameter_tap, String meter_type);

	

	TariffRateMaster getById(int id, double diameter_tap, String meter_type);

}

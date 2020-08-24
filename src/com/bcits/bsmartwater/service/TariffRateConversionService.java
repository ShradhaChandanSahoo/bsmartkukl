package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.TariffRateConversion;

public interface TariffRateConversionService {

	void save(TariffRateConversion tariffRateConversion);

	List<TariffRateConversion> getTariffDetails();

	Object find(int id);

	void update(TariffRateConversion tariffRateConversion);

}

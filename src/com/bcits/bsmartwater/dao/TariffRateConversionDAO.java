package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.TariffRateConversion;


public interface TariffRateConversionDAO extends GenericDAO<TariffRateConversion>{

	List<TariffRateConversion> getTariffDetails();

}

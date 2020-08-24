package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.TariffRateConversionDAO;
import com.bcits.bsmartwater.model.TariffRateConversion;

@Repository
public class TariffRateConversionDAOImpl extends GenericDAOImpl<TariffRateConversion> implements TariffRateConversionDAO {

	@Override
	public List<TariffRateConversion> getTariffDetails() {
	List<TariffRateConversion> list=null;
		try {
			list=getCustomEntityManager().createNamedQuery("TariffRateConversion.getTariffDetails",TariffRateConversion.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}

package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.TariffRateMasterDao;
import com.bcits.bsmartwater.model.TariffRateMaster;

@Repository
public class TariffRateMasterDaoImpl extends GenericDAOImpl<TariffRateMaster> implements TariffRateMasterDao {

	

	@Override
	public TariffRateMaster getTariffRate(double pipesize,String connectionType) {
		
		try{
			return entityManager.createNamedQuery("TariffRateMaster.getTariffRate",TariffRateMaster.class).setParameter("diameter_tap",pipesize).setParameter("meter_type",connectionType.toUpperCase().trim()).getSingleResult();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}


	@Override
	public List<TariffRateMaster> getTariffRates() {
		 
		List<TariffRateMaster> tariff=null;
		try {
			tariff = entityManager.createNamedQuery("TariffRateMaster.getTariffRates",TariffRateMaster.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tariff;
	}


	@Override
	public TariffRateMaster checkTapAndMeterType(double diameter_tap,
			String meter_type) {
		try{
			return entityManager.createNamedQuery("TariffRateMaster.checkTapAndMeterType",TariffRateMaster.class).setParameter("diameter_tap",diameter_tap).setParameter("meter_type", meter_type).getSingleResult();
			}catch(Exception e){
				return null;
			}
		
	
	}


	@Override
	public TariffRateMaster getById(int id, double diameter_tap,
			String meter_type) {
		try{
			return entityManager.createNamedQuery("TariffRateMaster.getById",TariffRateMaster.class).setParameter("id",id).setParameter("diameter_tap", diameter_tap).setParameter("diameter_tap", diameter_tap).getSingleResult();
			}catch(Exception e){ e.printStackTrace();
				return null;
			}
	}
}

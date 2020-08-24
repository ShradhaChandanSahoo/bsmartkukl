package com.bcits.bsmartwater.daoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.HolidayMasterDao;
import com.bcits.bsmartwater.model.HolidayMaster;

@Repository
public class HolidayMasterDaoImpl extends GenericDAOImpl<HolidayMaster> implements HolidayMasterDao {

	@Override
	public HolidayMaster getRecordByDate(Date holiday_date) {
		
		try{
		    return entityManager.createNamedQuery("HolidayMaster.getRecordByDate",HolidayMaster.class).setParameter("h_date", new SimpleDateFormat("dd-MM-YYYY").format(holiday_date)).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		}

}

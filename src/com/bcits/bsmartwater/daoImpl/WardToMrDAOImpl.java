package com.bcits.bsmartwater.daoImpl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.WardToMrDAO;
import com.bcits.bsmartwater.model.MeterDetailsEntity;
import com.bcits.bsmartwater.model.WardToMrEntity;

@Repository
public class WardToMrDAOImpl extends GenericDAOImpl<WardToMrEntity> implements WardToMrDAO {

	@Override
	public List<?> getAllMeterReader() {
		return getCustomEntityManager().createNamedQuery("WardToMrEntity.getAllWardToMeterReader").getResultList();
	}

	@Override
	public String uniqueWardToMrDetails(String ward_no, int reading_day) {
		List<WardToMrEntity> list=getCustomEntityManager().createNamedQuery("WardToMrEntity.uniqueWardToMrDetails",WardToMrEntity.class).setParameter("ward_no",ward_no).setParameter("reading_day",reading_day).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}

	@Override
	public WardToMrEntity getReaderByWardNoReadingDay(String wardNo, int readingday) {
		try {
			WardToMrEntity entity=getCustomEntityManager().createNamedQuery("WardToMrEntity.uniqueWardToMrDetails",WardToMrEntity.class).setParameter("ward_no",wardNo).setParameter("reading_day",readingday).getSingleResult();
			return entity;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

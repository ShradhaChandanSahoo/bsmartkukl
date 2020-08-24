package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.MeterChangeDetailsDao;
import com.bcits.bsmartwater.model.MeterChangeDetailsEntity;

@Repository
public class MeterChangeDetailsDaoImpl extends GenericDAOImpl<MeterChangeDetailsEntity> implements MeterChangeDetailsDao
{

	@SuppressWarnings("unchecked")
	@Override
	public List<MeterChangeDetailsEntity> getMeterChangeDetailsByConnNum(String connId) {
		List<MeterChangeDetailsEntity> mChange = null;
		try {
			mChange = getCustomEntityManager().createNamedQuery("MeterChangeDetailsEntity.getMeterChangeDetailsByConnNum").setParameter("connId", connId).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mChange;
	}

}

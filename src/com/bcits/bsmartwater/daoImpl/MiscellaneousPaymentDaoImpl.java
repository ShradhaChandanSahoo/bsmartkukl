package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.MiscellaneousPaymentDao;
import com.bcits.bsmartwater.model.MiscellaneousPayment;

@Repository
public class MiscellaneousPaymentDaoImpl  extends GenericDAOImpl<MiscellaneousPayment> implements MiscellaneousPaymentDao {

	@Override
	public List<MiscellaneousPayment> getSyncToPGRS(int status) {
		return entityManager.createNamedQuery("MiscellaneousPayment.getSyncToPGRS",MiscellaneousPayment.class).setParameter("status", status).getResultList();
	}

}

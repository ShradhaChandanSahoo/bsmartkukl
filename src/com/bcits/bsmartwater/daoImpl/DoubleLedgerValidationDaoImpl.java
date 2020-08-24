package com.bcits.bsmartwater.daoImpl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.DoubleLedgerValidationDao;
import com.bcits.bsmartwater.model.DoubleLedgerValidation;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;

@Repository
public class DoubleLedgerValidationDaoImpl extends GenericDAOImpl<DoubleLedgerValidation> implements DoubleLedgerValidationDao {

	@Override
	public DoubleLedgerValidation getRecordByWardAndRdngDay(String wardNo, int readingday) {
		try {
			return getCustomEntityManager().createNamedQuery("DoubleLedgerValidation.getRecordByWardAndRdngDay",DoubleLedgerValidation.class)
					.setParameter("ward_no", wardNo).setParameter("reading_day", readingday).getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public DoubleLedgerValidation getRecordByWardAndRdngDay1(String wardNo, int readingday,String schemaName) {
		try {
			return getCustomEntityManager(schemaName).createNamedQuery("DoubleLedgerValidation.getRecordByWardAndRdngDay",DoubleLedgerValidation.class)
					.setParameter("ward_no", wardNo).setParameter("reading_day", readingday).getSingleResult();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public void customupdate(DoubleLedgerValidation doubleLedgerValidation1, String schemaName) {
		Session session = getCustomEntityManager(schemaName).unwrap(Session.class);
		BsmartWaterLogger.logger.info("updating  instance");
		try {
			session.merge(doubleLedgerValidation1);
			session.flush();
			session.clear();
			
		} catch (RuntimeException re) {
			throw re;
		}
		
	}
	
	
}

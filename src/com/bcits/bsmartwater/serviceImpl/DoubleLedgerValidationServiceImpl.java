package com.bcits.bsmartwater.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.DoubleLedgerValidationDao;
import com.bcits.bsmartwater.model.DoubleLedgerValidation;
import com.bcits.bsmartwater.service.DoubleLedgerValidationService;

@Service("doubleLedgerValidationService")
public class DoubleLedgerValidationServiceImpl implements DoubleLedgerValidationService {

	@Autowired
	DoubleLedgerValidationDao doubleLedgerValidationDao;
	
	
	@Override
	public void save(DoubleLedgerValidation doubleLedgerValidation) {
		doubleLedgerValidationDao.customsave(doubleLedgerValidation);
		
	}

	@Override
	public void update(DoubleLedgerValidation doubleLedgerValidation) {
		doubleLedgerValidationDao.customupdate(doubleLedgerValidation);
		
	}

	@Override
	public DoubleLedgerValidation getRecordByWardAndRdngDay(String wardNo, int readingday) {
		return doubleLedgerValidationDao.getRecordByWardAndRdngDay(wardNo,readingday);
	}

}

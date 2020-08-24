package com.bcits.bsmartwater.dao;

import com.bcits.bsmartwater.model.DoubleLedgerValidation;

public interface DoubleLedgerValidationDao extends GenericDAO<DoubleLedgerValidation>{

	DoubleLedgerValidation getRecordByWardAndRdngDay(String wardNo, int readingday);
	DoubleLedgerValidation getRecordByWardAndRdngDay1(String wardNo, int readingday,String schemaName);
	void customupdate(DoubleLedgerValidation doubleLedgerValidation1, String schemaName);

}

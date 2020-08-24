package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.FiscalYearAdvanceBalance;

public interface FiscalYearAdvanceBalanceService {

	void save(FiscalYearAdvanceBalance fiscalYearAdvanceBalance);
	void update(FiscalYearAdvanceBalance fiscalYearAdvanceBalance);
	void delete(int id);
	int updateDataFromFiscalBalance(List<?> monthEndSummary);
	int submitAsyncAdvanceReportUpdate(List<?> monthEndSummary, String schemaName, String monthyearnep, String sitecode,
			String userName);
	
}

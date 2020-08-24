package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.FiscalYearAdvanceBalance;

public interface FiscalYearAdvanceBalanceDAO extends GenericDAO<FiscalYearAdvanceBalance> {

	int updateDataFromFiscalBalance(List<?> monthEndSummary);

	int submitAsyncAdvanceReportUpdate(List<?> monthEndSummary, String schemaName, String monthyearnep, String sitecode,
			String userName);

}

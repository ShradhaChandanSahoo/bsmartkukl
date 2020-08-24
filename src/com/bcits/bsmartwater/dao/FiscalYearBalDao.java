package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.FiscalYearBalance;

public interface FiscalYearBalDao extends GenericDAO<FiscalYearBalance>{

	long checkMonthYearExists(int monthyearnep);

	int submitAsyncMonthEndAsynchronousUpdate(List<?> monthEndSummary, String schemaName, String monthyearnep, String sitecode, String userName);

	int submitMonthEndAsynchronousUpdateManual(List<?> monthEndSummary,String schemaName, String monthyearnep, String sitecode,String userName);

	long getTotalRecords();

	List<?> getAllRecordsByMyn(String monthyearnep);

	

}

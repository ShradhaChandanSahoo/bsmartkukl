package com.bcits.bsmartwater.service;

import java.util.List;

public interface FiscalYearBalService {

	long checkMonthYearExists(int parseInt);

	int submitAsyncMonthEndAsynchronousUpdate(List<?> monthEndSummary, String schemaName, String monthyearnep, String sitecode, String userName);

	int submitMonthEndAsynchronousUpdateManual(List<?> monthEndSummary,String schemaName, String monthyearnep, String sitecode,String userName);

	long getTotalRecords();

	List<?> getAllRecordsByMyn(String monthyearnep);

}

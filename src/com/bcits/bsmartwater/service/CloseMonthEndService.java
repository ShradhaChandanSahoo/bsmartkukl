package com.bcits.bsmartwater.service;

import com.bcits.bsmartwater.model.CloseMonthEnd;

public interface CloseMonthEndService {

	void save(CloseMonthEnd c);

	long checkCloseMonthExists(String monthyearnep);

	String getLatestMonthYear();
	
	String checkForBillingForSewageChange(String con,String mthyr);

}

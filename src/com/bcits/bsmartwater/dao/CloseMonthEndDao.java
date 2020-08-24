package com.bcits.bsmartwater.dao;

import com.bcits.bsmartwater.model.CloseMonthEnd;


public interface CloseMonthEndDao extends GenericDAO<CloseMonthEnd>{

	long checkCloseMonthExists(String monthyearnep);

	String getLatestMonthYear();
	
	String checkForBillingForSewageChange(String con,String mthyr);

}

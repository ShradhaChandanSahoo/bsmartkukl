package com.bcits.bsmartwater.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.CloseMonthEndDao;
import com.bcits.bsmartwater.model.CloseMonthEnd;
import com.bcits.bsmartwater.service.CloseMonthEndService;

@Service
public class CloseMonthEndServiceImpl implements CloseMonthEndService {

	@Autowired
	CloseMonthEndDao closeMonthEndDao;
	
	@Override
	public void save(CloseMonthEnd c) {
		
		closeMonthEndDao.customsave(c);
	}

	@Override
	public long checkCloseMonthExists(String monthyearnep) {
		
		return closeMonthEndDao.checkCloseMonthExists(monthyearnep);
	}

	@Override
	public String getLatestMonthYear() {
		
		return closeMonthEndDao.getLatestMonthYear();
	}

	@Override
	public String checkForBillingForSewageChange(String con, String mthyr) {
		return closeMonthEndDao.checkForBillingForSewageChange(con,mthyr);
	}

}

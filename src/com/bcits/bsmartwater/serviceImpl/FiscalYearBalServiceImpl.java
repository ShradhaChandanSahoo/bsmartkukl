package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.dao.FiscalYearBalDao;
import com.bcits.bsmartwater.service.FiscalYearBalService;

@Service
public class FiscalYearBalServiceImpl implements FiscalYearBalService {

	@Autowired
	FiscalYearBalDao fiscalYearBalDao;
	
	@Override
	public long checkMonthYearExists(int monthyearnep) {
		return fiscalYearBalDao.checkMonthYearExists(monthyearnep);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int submitAsyncMonthEndAsynchronousUpdate(List<?> monthEndSummary, String schemaName,String monthyearnep,String sitecode,String userName) {
		try {
			return fiscalYearBalDao.submitAsyncMonthEndAsynchronousUpdate(monthEndSummary,schemaName,monthyearnep,sitecode,userName);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int submitMonthEndAsynchronousUpdateManual(List<?> monthEndSummary,String schemaName, String monthyearnep, String sitecode,String userName) {
		try {
			return fiscalYearBalDao.submitMonthEndAsynchronousUpdateManual(monthEndSummary,schemaName,monthyearnep,sitecode,userName);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long getTotalRecords() {
		return fiscalYearBalDao.getTotalRecords();
	}

	@Override
	public List<?> getAllRecordsByMyn(String monthyearnep) {
		return fiscalYearBalDao.getAllRecordsByMyn(monthyearnep);
	}

}

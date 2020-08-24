package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.FiscalYearAdvanceBalanceDAO;
import com.bcits.bsmartwater.model.FiscalYearAdvanceBalance;
import com.bcits.bsmartwater.service.FiscalYearAdvanceBalanceService;

@Service
public class FiscalYearAdvanceBalanceServiceImpl implements FiscalYearAdvanceBalanceService {

	@Autowired
	FiscalYearAdvanceBalanceDAO advanceBalanceDAO;
	
	@Override
	public void save(FiscalYearAdvanceBalance fiscalYearAdvanceBalance) {
		advanceBalanceDAO.customsave(fiscalYearAdvanceBalance);
	}

	@Override
	public void update(FiscalYearAdvanceBalance fiscalYearAdvanceBalance) {
		advanceBalanceDAO.customupdate(fiscalYearAdvanceBalance);
	}

	@Override
	public void delete(int id) {
		advanceBalanceDAO.customdelete(id);
	}

	@Override
	public int updateDataFromFiscalBalance(List<?> monthEndSummary) {
		return advanceBalanceDAO.updateDataFromFiscalBalance(monthEndSummary);
	}

	@Override
	public int submitAsyncAdvanceReportUpdate(List<?> monthEndSummary, String schemaName, String monthyearnep,
			String sitecode, String userName) {

		try {
			return advanceBalanceDAO.submitAsyncAdvanceReportUpdate(monthEndSummary,schemaName,monthyearnep,sitecode,userName);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}

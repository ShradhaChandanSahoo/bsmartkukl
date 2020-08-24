package com.bcits.bsmartwater.serviceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.CashCounterDayCloseDAO;
import com.bcits.bsmartwater.model.CashCounterDayClose;
import com.bcits.bsmartwater.service.CashCounterDayCloseService;
@Service
public class CashCounterDayCloseServiceImpl implements CashCounterDayCloseService {

	@Autowired
	private CashCounterDayCloseDAO cashCounterDayCloseDAO;
	
	@Override
	public CashCounterDayClose save(CashCounterDayClose cashCounterDayClose) {
		return cashCounterDayCloseDAO.customsave(cashCounterDayClose);
	}

	@Override
	public CashCounterDayClose getMaxDayForDayClose(Integer counter_no, Date date) {
		return cashCounterDayCloseDAO.getMaxDayForDayClose(counter_no,date);
	}

	@Override
	public String getDayCloseDataOncounterNoAndDate(Integer counter_no,Date date) {
		return cashCounterDayCloseDAO.getDayCloseDataOncounterNoAndDate(counter_no,date);
	}

	@Override
	public Object checkDayAlreadyClosed(Integer counter_no,String counterdate1) {
		return cashCounterDayCloseDAO.checkDayAlreadyClosed(counter_no,counterdate1);
	}

}

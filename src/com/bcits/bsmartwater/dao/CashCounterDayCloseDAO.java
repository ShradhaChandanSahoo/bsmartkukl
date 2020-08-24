package com.bcits.bsmartwater.dao;

import java.util.Date;

import com.bcits.bsmartwater.model.CashCounterDayClose;

public interface CashCounterDayCloseDAO extends GenericDAO<CashCounterDayClose> {

	CashCounterDayClose getMaxDayForDayClose(Integer counter_no, Date date);

	String getDayCloseDataOncounterNoAndDate(Integer counter_no, Date date);

	Object checkDayAlreadyClosed(Integer counter_no,
			String counterdate1);

}

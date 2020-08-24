package com.bcits.bsmartwater.service;

import java.util.Date;

import com.bcits.bsmartwater.model.CashCounterDayClose;

public interface CashCounterDayCloseService {

	CashCounterDayClose save(CashCounterDayClose cashCounterDayClose);

	CashCounterDayClose getMaxDayForDayClose(Integer counter_no, Date date);

	String getDayCloseDataOncounterNoAndDate(Integer counter_no, Date date);

	Object checkDayAlreadyClosed(Integer counter_no,String counterdate1);

}

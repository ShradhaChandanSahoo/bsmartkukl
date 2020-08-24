package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.CounterDetails;

public interface CounterDetailsDAO extends GenericDAO<CounterDetails>
{

	List<CounterDetails> getAllCounterRecords();

	Long checkForCounterNameAvailability(String counterName);

	int checkForCounterNumberAvailability(String counterNumber);
	
	Object getcounterName(String counter_no);

	Long checkCounterNameForUpdate(int id, String counterName);

	Long checkCounterNumberForUpdate(int id, String counterNumber);

	List<CounterDetails> getAllCounterRecordsNA();

}

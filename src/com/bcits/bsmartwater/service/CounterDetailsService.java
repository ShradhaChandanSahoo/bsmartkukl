package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.CounterDetails;

public interface CounterDetailsService 
{

	List<CounterDetails> getAllCounterRecords();

	Long checkForCounterNameAvailability(String counterName);

	int checkForCounterNumberAvailability(String counterNumber);

	void save(CounterDetails counterDetails);

	CounterDetails find(int id);

	
	void update(CounterDetails counterDetails);

	Object getcounterName(String counter_no);

	Long checkCounterNameForUpdate(int id, String counterName);

	Long checkCounterNumberForUpdate(int id, String counterNumber);

	List<CounterDetails> getAllCounterRecordsNA();


}

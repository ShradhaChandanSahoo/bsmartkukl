package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.CounterDetailsDAO;
import com.bcits.bsmartwater.model.CounterDetails;
import com.bcits.bsmartwater.service.CounterDetailsService;

@Service
public class CounterDetailsServiceImpl implements CounterDetailsService
{
	
	@Autowired
	CounterDetailsDAO counterDetailsDAO;

	@Override
	public List<CounterDetails> getAllCounterRecords() {
		List<CounterDetails> counter=counterDetailsDAO.getAllCounterRecords();
		return counter;
	}

	@Override
	public Long checkForCounterNameAvailability(String counterName) {
	Long l=counterDetailsDAO.checkForCounterNameAvailability(counterName);
		return l;
	}

	@Override
	public int checkForCounterNumberAvailability(String counterNumber) {
		int l = counterDetailsDAO.checkForCounterNumberAvailability(counterNumber);
		return l;
	}

	@Override
	public void save(CounterDetails counterDetails) {
	counterDetailsDAO.customsave(counterDetails);
		
	}

	@Override
	public CounterDetails find(int id) {
		
		return counterDetailsDAO.customfind(id);
	}

	

	@Override
	public void update(CounterDetails counterDetails) {
	
		counterDetailsDAO.customupdate(counterDetails);
	}


	@Override
	public Object getcounterName(String counter_no) {
		
		return counterDetailsDAO.getcounterName(counter_no);
	}

	@Override
	public Long checkCounterNameForUpdate(int id, String counterName) {
		Long l = counterDetailsDAO.checkCounterNameForUpdate(id, counterName);
		return l;
	}

	@Override
	public Long checkCounterNumberForUpdate(int id, String counterNumber) {
		Long l = counterDetailsDAO.checkCounterNumberForUpdate(id, counterNumber);
		return l;
	}

	@Override
	public List<CounterDetails> getAllCounterRecordsNA() {
		List<CounterDetails> counter=counterDetailsDAO.getAllCounterRecordsNA();
		return counter;
	}

	
}

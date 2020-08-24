package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.CounterDetailsDAO;
import com.bcits.bsmartwater.model.CounterDetails;

@Repository
public class CounterDetailsDAOImpl extends GenericDAOImpl<CounterDetails> implements CounterDetailsDAO
{

	@SuppressWarnings("unchecked")
	@Override
	public List<CounterDetails> getAllCounterRecords() 
	{
		List<CounterDetails> counter=null;
		try {
			counter=getCustomEntityManager().createNamedQuery("CounterDetails.getAllCounterRecords").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}

	@Override
	public Long checkForCounterNameAvailability(String counterName) {
		Long l=null;
		try {
			l=Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("CounterDetails.checkForCounterNameAvailability").setParameter("counterName",counterName).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public int checkForCounterNumberAvailability(String counterNumber) {
		
		int i = 0;
		try {
			System.out.println(counterNumber);
			i = Integer.parseInt(String.valueOf(getCustomEntityManager()
					.createNamedQuery(
							"CounterDetails.checkForCounterNumberAvailability")
					.setParameter("counterNumber", counterNumber).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	@Override
	public Object getcounterName(String counter_no) {
		return getCustomEntityManager().createNamedQuery("CounterDetails.getcounterName").setParameter("counter_no", counter_no).getSingleResult();
		
	}

	@Override
	public Long checkCounterNameForUpdate(int id, String counterName) {
		Long i=null;
		try {
			i=Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("CounterDetails.checkCounterNameForUpdate").setParameter("id", id).setParameter("counterName", counterName).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public Long checkCounterNumberForUpdate(int id, String counterNumber) {
		Long i=null;
		try {
			i=Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("CounterDetails.checkCounterNumberForUpdate").setParameter("id", id).setParameter("counterNumber", counterNumber).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CounterDetails> getAllCounterRecordsNA() {
		
		String query="SELECT COUNTERNUMBER FROM COUNTER_TAB WHERE COUNTERNUMBER NOT IN(SELECT COUNTER_NO FROM BSW_CASH_MASTER_CONFIG)";
		List<?> counterNos=getCustomEntityManager().createNativeQuery(query).getResultList();
		System.out.println(counterNos);
		List<CounterDetails> counter=null;
		try {
			counter=getCustomEntityManager().createNamedQuery("CounterDetails.getAllCounterRecordsNA").setParameter("counterNos", counterNos).getResultList();
		} catch (Exception e) {
		}
		return counter;
	}
}

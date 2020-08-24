package com.bcits.bsmartwater.daoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.CashCounterDayCloseDAO;
import com.bcits.bsmartwater.model.CashCounterDayClose;

@Repository
public class CashCounterDayCloseDAOImpl extends GenericDAOImpl<CashCounterDayClose> implements CashCounterDayCloseDAO {

	@Override
	public CashCounterDayClose getMaxDayForDayClose(Integer counter_no, Date date) {
		try{
		return getCustomEntityManager().createNamedQuery("CashCounterDayClose.getMaxDayForDayClose",CashCounterDayClose.class).setParameter("counter_no",counter_no).setParameter("date", date).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public String getDayCloseDataOncounterNoAndDate(Integer counter_no,Date date) {
			 List<?> list=getCustomEntityManager().createNamedQuery("CashCounterDayClose.getDayCloseDataOncounterNoAndDate").setParameter("counter_no",counter_no).setParameter("date", new SimpleDateFormat("yyyy-MM-dd").format(date) ).getResultList();
			 if(list!=null && list.size()>0){
				 System.out.println("Already Day Closed");
				 return "Already Day Closed";
			 }
			 System.out.println("Can Get Data");
		return "Can Get Data";
	}

	@Override
	public Object checkDayAlreadyClosed(Integer counter_no,String counterdate1) {

		String query="SELECT COUNTER_NO FROM BSW_CASHCOUNTERDAYCLOSE WHERE COUNTER_NO='"+counter_no+"' AND trunc(DAYCLOSEDATE)=TO_DATE('"+counterdate1+"', 'MM/dd/yyyy')";
		try 
		{
			System.out.println(query);
			return getCustomEntityManager().createNativeQuery(query).getSingleResult();
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
}

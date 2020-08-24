package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.CloseMonthEndDao;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.CloseMonthEnd;

@Repository
public class CloseMonthEndDaoImpl extends GenericDAOImpl<CloseMonthEnd> implements CloseMonthEndDao {

	@Override
	public long checkCloseMonthExists(String monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("CloseMonthEnd.checkCloseMonthExists").setParameter("monthyearnep", monthyearnep).getSingleResult();

		}catch(Exception e){
			e.printStackTrace();
			return 1010;
		}
	}

	@Override
	public String getLatestMonthYear() {
		try{
			return (String) getCustomEntityManager().createNamedQuery("CloseMonthEnd.getLatestMonthYear").getSingleResult();
		}catch(Exception e){
			return null;
		}
		
	}

	@Override
	public String checkForBillingForSewageChange(String con, String mthyr) {
		
		
		try{
			int mthyrFrontEnd=Integer.parseInt(mthyr);
			BillingLedgerEntity s= (BillingLedgerEntity) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getBillGenerationStatus").setParameter("connection_no", con).getSingleResult();
			
			if(s.getBillno()==null || s.getBillno().equalsIgnoreCase("NULL"))  
			{
				return "Yes";
			}
			else if(s.getBillno()!=null)
			{
				int ledMy=Integer.parseInt(s.getMonthyearnep());
				if(mthyrFrontEnd>ledMy)
				{
					return "Yes";
				}
				else {
					return "No";
				}
			}
			else
			{
				return "No";
			}
		}catch(Exception e){
			e.printStackTrace();
			}return "No";
	
	}
	
	
}

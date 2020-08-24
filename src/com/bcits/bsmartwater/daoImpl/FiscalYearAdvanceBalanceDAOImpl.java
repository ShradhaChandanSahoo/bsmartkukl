package com.bcits.bsmartwater.daoImpl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.FiscalYearAdvanceBalanceDAO;
import com.bcits.bsmartwater.model.FiscalYearAdvanceBalance;
import com.bcits.bsmartwater.model.FiscalYearBalance;

@Repository
public class FiscalYearAdvanceBalanceDAOImpl extends GenericDAOImpl<FiscalYearAdvanceBalance> implements FiscalYearAdvanceBalanceDAO {

	@Override
	public int updateDataFromFiscalBalance(List<?> monthEndSummary) {
		
		/*DecimalFormat df = new DecimalFormat("####0.00");
		int insertedCount = 0;
		
		for (Iterator<?> i = monthEndSummary.iterator(); i.hasNext();) {
			
			try{
				FiscalYearBalance values = (FiscalYearBalance)i.next();

				int fiscal_year_month=0;
				String sitecode="";
				String con_type="";
				String ward_no="";
				String con_category="";
				String denoted_by="";
				double opening_balance=0;
				double water_cost=0;
				double sewerage_cost=0;
				double mrent_cost=0;
				double adjustment=0;
				double current_adv_amt=0;
				double current_adv_rebate=0;
				double closing_bal=0;
				
				double tot_bill=0;

				fiscal_year_month=values.getFiscal_year_month();
				sitecode=values.getSitecode();
				con_type=values.getCon_type();
				ward_no=values.getWard_no();
				con_category=values.getCon_category();
				denoted_by=values.getDenoted_by();
				water_cost=values.getWater_cost();
				sewerage_cost=values.getSewerage_cost();
				mrent_cost=values.getMrent_cost();
				tot_bill=water_cost+sewerage_cost+mrent_cost;
				adjustment=values.get;
				
				
				opening_balance=values.getAdv_reb()+;
				current_adv_amt=values.getAd
				current_adv_rebate=values.get
				closing_bal=values.get


				
				
				FiscalYearAdvanceBalance fiscalYearAdvanceBalance=new FiscalYearAdvanceBalance();
				
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		
		}*/
		
		return 0;
	}

	@Override
	public int submitAsyncAdvanceReportUpdate(List<?> monthEndSummary, String schemaName, String monthyearnep,
			String sitecode, String userName) {

		Session session = getCustomEntityManager(schemaName).unwrap(Session.class);
		int insertedCount = 0;
		for (final Iterator<?> i = monthEndSummary.iterator(); i.hasNext();) {
			
			try{
				final Object[] values = (Object[])i.next();
				double openingBal=0;
				double adv=0;
				double adv_reb=0;
				double adjustment=0;
				double waterCh=0;
				double swCh=0;
				double mtrRent=0;
				double netBill=0;
				double closing_bal=0;
				
				FiscalYearAdvanceBalance fiscalYearBalance=new FiscalYearAdvanceBalance();
				fiscalYearBalance.setFiscal_year_month(Integer.parseInt(monthyearnep));
				fiscalYearBalance.setSitecode(sitecode);
				
				if(values[0]==null && values[1]==null){
					
					if(values[17]==null && values[18]==null){
						fiscalYearBalance.setWard_no((String)values[21]);
						fiscalYearBalance.setDenoted_by((String)values[22]);
						fiscalYearBalance.setCon_type((String)values[23]);
						fiscalYearBalance.setCon_category((String)values[24]);
					}else{
						fiscalYearBalance.setWard_no((String)values[17]);
						fiscalYearBalance.setDenoted_by((String)values[18]);
						fiscalYearBalance.setCon_type((String)values[19]);
						fiscalYearBalance.setCon_category((String)values[20]);
					}
					
				}else{
					
					if(values[17]==null && values[18]==null){
						
						if(values[21]==null && values[22]==null){
							fiscalYearBalance.setWard_no((String)values[0]);
							fiscalYearBalance.setDenoted_by((String)values[1]);
							fiscalYearBalance.setCon_type((String)values[15]);
							fiscalYearBalance.setCon_category((String)values[16]);
						}
						else{
							fiscalYearBalance.setWard_no((String)values[21]);
							fiscalYearBalance.setDenoted_by((String)values[22]);
							fiscalYearBalance.setCon_type((String)values[23]);
							fiscalYearBalance.setCon_category((String)values[24]);
						}
						
						
					}else{
						
						fiscalYearBalance.setWard_no((String)values[0]);
						fiscalYearBalance.setDenoted_by((String)values[1]);
						fiscalYearBalance.setCon_type((String)values[15]);
						fiscalYearBalance.setCon_category((String)values[16]);
					}
				}
				fiscalYearBalance.setOpening_balance(((java.math.BigDecimal)values[2]).doubleValue());
				
				fiscalYearBalance.setWater_cost(((java.math.BigDecimal)values[3]).doubleValue());
				fiscalYearBalance.setSewerage_cost(((java.math.BigDecimal)values[4]).doubleValue());
				fiscalYearBalance.setMrent_cost(((java.math.BigDecimal)values[6]).doubleValue());
				
				openingBal=((java.math.BigDecimal)values[2]).doubleValue();
				waterCh=((java.math.BigDecimal)values[3]).doubleValue();
				swCh=((java.math.BigDecimal)values[4]).doubleValue();
				mtrRent=((java.math.BigDecimal)values[6]).doubleValue();
				netBill=waterCh+swCh+mtrRent;
				if(openingBal>netBill) {
					adjustment=netBill;
				} else {
					adjustment=openingBal;
				}
				adv=((java.math.BigDecimal)values[13]).doubleValue();
				adv_reb=((java.math.BigDecimal)values[14]).doubleValue();
				
				fiscalYearBalance.setAdjustment(adjustment);
				fiscalYearBalance.setCurrent_adv_amt(adv);
				fiscalYearBalance.setCurrent_adv_rebate(adv_reb);
				//closing_bal=-adv-adv_reb;
				if("207403".equalsIgnoreCase(monthyearnep)) {
					closing_bal=adv+adv_reb;
				} else {
					closing_bal=openingBal-adjustment+adv+adv_reb;
				}
				
				
				fiscalYearBalance.setClosing_bal(closing_bal);
				fiscalYearBalance.setCreate_by(userName);
				fiscalYearBalance.setCreate_date(new Date());
				session.save(fiscalYearBalance);
				session.flush();
				session.clear();
			
		} catch (RuntimeException re) {
		    throw re;
	}
	insertedCount++;
		
	}
		return insertedCount;
		
	}

	

}

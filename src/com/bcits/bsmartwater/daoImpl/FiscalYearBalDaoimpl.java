package com.bcits.bsmartwater.daoImpl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.dao.FiscalYearBalDao;
import com.bcits.bsmartwater.model.FiscalYearBalance;

@Repository
public class FiscalYearBalDaoimpl extends GenericDAOImpl<FiscalYearBalance> implements FiscalYearBalDao {

	@Override
	public long checkMonthYearExists(int monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("FiscalYearBalance.checkMonthYearExists").setParameter("fiscal_year_month", monthyearnep).getSingleResult();

		}catch(Exception e){
			
			return 2121;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int submitAsyncMonthEndAsynchronousUpdate(List<?> monthEndSummary, String schemaName,String monthyearnep,String sitecode,String userName) {
		DecimalFormat df = new DecimalFormat("####0.00");
		Session session = getCustomEntityManager(schemaName).unwrap(Session.class);
		int insertedCount = 0;
		for (final Iterator<?> i = monthEndSummary.iterator(); i.hasNext();) {
			
			try{
				final Object[] values = (Object[])i.next();
				double rebate=0;
				double adv=0;
				double adv_reb=0;
				
				FiscalYearBalance fiscalYearBalance=new FiscalYearBalance();
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
				fiscalYearBalance.setMisc_cost(((java.math.BigDecimal)values[5]).doubleValue());
				fiscalYearBalance.setMrent_cost(((java.math.BigDecimal)values[6]).doubleValue());
				fiscalYearBalance.setAdd_pen_cost(((java.math.BigDecimal)values[7]).doubleValue());
				fiscalYearBalance.setPenalty(((java.math.BigDecimal)values[8]).doubleValue()+((java.math.BigDecimal)values[10]).doubleValue());
				fiscalYearBalance.setPay_towards("BILL PAYMENT");
				
				double adjustment=0;
				
				double adjcwop1=((java.math.BigDecimal)values[27]).doubleValue();
				double adjcwop2=((java.math.BigDecimal)values[28]).doubleValue();
				double voucheramt=((java.math.BigDecimal)values[26]).doubleValue();
				
				double adjcwop=adjcwop1+adjcwop2;
				
				/*if("1110".equalsIgnoreCase(sitecode) || "1113".equalsIgnoreCase(sitecode)){
					
					if(((java.math.BigDecimal)values[25]).doubleValue()>0){
						fiscalYearBalance.setPos_adjustment(((java.math.BigDecimal)values[25]).doubleValue());
						adjustment=((java.math.BigDecimal)values[25]).doubleValue();
					}else{
						fiscalYearBalance.setNeg_adjustment(((java.math.BigDecimal)values[25]).doubleValue());
						adjustment=((java.math.BigDecimal)values[25]).doubleValue();
					}
				}else{*/
					if(((java.math.BigDecimal)values[9]).doubleValue()>0){
						adjustment=((java.math.BigDecimal)values[9]).doubleValue();
						adjustment=adjustment+((java.math.BigDecimal)values[26]).doubleValue();
						fiscalYearBalance.setPos_adjustment(adjustment+adjcwop);
					}else{
						adjustment=((java.math.BigDecimal)values[9]).doubleValue();
						adjustment=adjustment+((java.math.BigDecimal)values[26]).doubleValue();
						fiscalYearBalance.setNeg_adjustment(adjustment+adjcwop);
					}
				//}
				fiscalYearBalance.setAdj_voucher(voucheramt);
				fiscalYearBalance.setAdjcwop(adjcwop);
				rebate=((java.math.BigDecimal)values[12]).doubleValue();
				adv=((java.math.BigDecimal)values[13]).doubleValue();
				adv_reb=((java.math.BigDecimal)values[14]).doubleValue();
				
				fiscalYearBalance.setRebate(rebate);
				fiscalYearBalance.setAdv_reb(adv_reb);
				fiscalYearBalance.setReceived_amount(((java.math.BigDecimal)values[11]).doubleValue());
				fiscalYearBalance.setClosing_balance(Double.parseDouble(df.format((((java.math.BigDecimal)values[2]).doubleValue()+((java.math.BigDecimal)values[3]).doubleValue()
						+((java.math.BigDecimal)values[4]).doubleValue())+((java.math.BigDecimal)values[5]).doubleValue()
						+((java.math.BigDecimal)values[6]).doubleValue()+((java.math.BigDecimal)values[7]).doubleValue()+((java.math.BigDecimal)values[8]).doubleValue()+adjustment+adjcwop+((java.math.BigDecimal)values[10]).doubleValue()
				-(((java.math.BigDecimal)values[11]).doubleValue()+rebate+(adv*0.03)))));
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

	@Override
	public int submitMonthEndAsynchronousUpdateManual(List<?> monthEndSummary,String schemaName, String monthyearnep, String sitecode,String userName) {
		

		DecimalFormat df = new DecimalFormat("####0.00");
		Session session = getCustomEntityManager(schemaName).unwrap(Session.class);
		int insertedCount = 0;
		for (final Iterator<?> i = monthEndSummary.iterator(); i.hasNext();) {
			
			try{
				final Object[] values = (Object[])i.next();
				double rebate=0;
				double adv=0;
				
				FiscalYearBalance fiscalYearBalance=new FiscalYearBalance();
				fiscalYearBalance.setFiscal_year_month(Integer.parseInt(monthyearnep));
				fiscalYearBalance.setSitecode(sitecode);
				fiscalYearBalance.setWard_no((String)values[0]);
				fiscalYearBalance.setDenoted_by((String)values[1]);
				fiscalYearBalance.setCon_type((String)values[2]);
				fiscalYearBalance.setCon_category((String)values[3]);
				fiscalYearBalance.setOpening_balance(((java.math.BigDecimal)values[4]).doubleValue());
				fiscalYearBalance.setWater_cost(((java.math.BigDecimal)values[5]).doubleValue());
				fiscalYearBalance.setSewerage_cost(((java.math.BigDecimal)values[6]).doubleValue());
				fiscalYearBalance.setMrent_cost(((java.math.BigDecimal)values[7]).doubleValue());
				fiscalYearBalance.setMisc_cost(((java.math.BigDecimal)values[8]).doubleValue());
				fiscalYearBalance.setAdd_pen_cost(0.0);
				fiscalYearBalance.setPenalty(((java.math.BigDecimal)values[9]).doubleValue());
				fiscalYearBalance.setPos_adjustment(0.0);
				
				rebate=((java.math.BigDecimal)values[10]).doubleValue();
				adv=((java.math.BigDecimal)values[12]).doubleValue();
				
				fiscalYearBalance.setRebate(rebate);
				fiscalYearBalance.setAdv_reb(adv*0.03);
				fiscalYearBalance.setReceived_amount(((java.math.BigDecimal)values[11]).doubleValue());
				
				fiscalYearBalance.setClosing_balance(Double.parseDouble(df.format((((java.math.BigDecimal)values[4]).doubleValue()+((java.math.BigDecimal)values[5]).doubleValue()
						+((java.math.BigDecimal)values[6]).doubleValue())+((java.math.BigDecimal)values[7]).doubleValue()
						+((java.math.BigDecimal)values[8]).doubleValue()+((java.math.BigDecimal)values[9]).doubleValue()
				-(((java.math.BigDecimal)values[11]).doubleValue()+rebate+(adv*0.03)))));
				
				fiscalYearBalance.setCreate_by(userName);
				fiscalYearBalance.setCreate_date(new Date());
				fiscalYearBalance.setPay_towards("BILL PAYMENT");
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

	@Override
	public long getTotalRecords() {
		try{
		   return (long) getCustomEntityManager().createNamedQuery("FiscalYearBalance.getTotalRecords").getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public List<?> getAllRecordsByMyn(String monthyearnep) {
		try{
			   return getCustomEntityManager().createNamedQuery("FiscalYearBalance.getAllRecordsByMyn").setParameter("fiscal_year_month", monthyearnep).getResultList();
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}

	
	
	
	
	
}

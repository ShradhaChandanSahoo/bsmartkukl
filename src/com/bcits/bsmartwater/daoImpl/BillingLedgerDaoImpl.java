package com.bcits.bsmartwater.daoImpl;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.bsmartwater.dao.BillingLedgerDao;
import com.bcits.bsmartwater.dao.DoubleLedgerValidationDao;
import com.bcits.bsmartwater.dao.TariffRateMasterDao;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.DoubleLedgerValidation;
import com.bcits.bsmartwater.model.PaymentEntity;
import com.bcits.bsmartwater.model.TariffRateMaster;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;

@Repository
public class BillingLedgerDaoImpl extends GenericDAOImpl<BillingLedgerEntity> implements BillingLedgerDao {
	
	@Autowired
	TariffRateMasterDao tariffRateMasterDao;
	
	@Autowired
	DoubleLedgerValidationDao doubleLedgerValidationDao;
	//Timestamp today=new Timestamp(new java.util.Date().getTime());
	Date today=new Date();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BillingLedgerEntity> findByConnectionNo(String connectionNo) {
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findByConnectionNo").setParameter("connection_no", connectionNo.trim().toUpperCase()).getResultList();
	}

	@Override
	public BillingLedgerEntity getBillByConNoAndMY(String connection_no,int monthyear) {
		try{
			//System.out.println("con-"+connection_no+"--mon-"+monthyear);
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getBillByConNoAndMY",BillingLedgerEntity.class).setParameter("connection_no", connection_no.trim().toUpperCase()).setParameter("monthyear", monthyear).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public BillingLedgerEntity getBillByConNoAndMY1(String connection_no,String monthyearnep) {
		try{
			//System.out.println("conhhh-"+connection_no+"--monhhhh-"+monthyearnep);
			List<BillingLedgerEntity> bill= getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getBillByConNoAndMY1",BillingLedgerEntity.class).setParameter("connection_no", connection_no.trim().toUpperCase()).setParameter("monthyearnep", monthyearnep).getResultList();
			if(bill!=null && bill.size()>0){
				return bill.get(0);
			}else{
				return null;
			}
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long ledgerCountByWardNo(String wardNo,String monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.ledgerCountByWardNo").setParameter("wardno", wardNo.toUpperCase()).setParameter("monthyearnep",monthyearnep).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public long ledgerCountByConCategory(String con_category1,String monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.ledgerCountByConCategory").setParameter("con_category", con_category1.toUpperCase()).setParameter("monthyearnep",monthyearnep).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long billedLedgerCountByWardNo(String wardNo,String monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerCountByWardNo").setParameter("wardno", wardNo.toUpperCase().trim()).setParameter("monthyearnep",monthyearnep ).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public long billedLedgerCountByConCategory(String con_category1,String monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerCountByConCategory").setParameter("con_category", con_category1.toUpperCase().trim()).setParameter("monthyearnep",monthyearnep ).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long unbilledLedgerCountByWardNo(String wardNo,String monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.unbilledLedgerCountByWardNo").setParameter("wardno", wardNo.toUpperCase()).setParameter("monthyearnep",monthyearnep ).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public long unbilledLedgerCountByConCategory(String con_category1,String monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.unbilledLedgerCountByConCategory").setParameter("con_category", con_category1.toUpperCase()).setParameter("monthyearnep",monthyearnep ).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<?> getReadingEntryUnbilled(String wardNo) {
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getReadingEntryUnbilled").setParameter("wardno", wardNo.toUpperCase()).getResultList();
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void updatePresReadAndMCStatusByBillId(double present_reading,int mc_status, int billid) {
		try{
		getCustomEntityManager().createNamedQuery("BillingLedgerEntity.updatePresReadAndMCStatusByBillId").setParameter("billid", billid).setParameter("present_reading", present_reading).setParameter("mc_status", mc_status).executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingLedgerEntity> updateMonthEndProcess(String monthyear) {
		try{
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.updateMonthEndProcess").setParameter("monthyear", Integer.parseInt(monthyear)).getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getMaxMonthYear() {
		try{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getMaxMonthYear",Integer.class).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long monthEndValid(int monthYear) {
		try{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.monthEndValid",Long.class).setParameter("monthyear", monthYear).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingLedgerEntity> getBillListByMYAndWardNo(int monthyear,String wardNo) {
		
		try{
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getBillListByMYAndWardNo").setParameter("monthyear", monthyear).setParameter("wardno", wardNo.toUpperCase().trim()).getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getMaxBillNoBysiteCode(String billno) {
		
		try{
		    return (String) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getMaxBillNoBysiteCode").setParameter("billno", billno).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		

	}

	@Override
	public BillingLedgerEntity findBillingLedgerBasedOnConnectionNo(String connectionNo) {
		try{
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.BillingLedgerBasedOnConnectionNo",BillingLedgerEntity.class).setParameter("connection_no", connectionNo.trim().toUpperCase()).getSingleResult();//.setMaxResults(1)
		}catch(Exception e){
			
			return null;
		}
	}


	

	@Override
	public BillingLedgerEntity getLastMonthRecord(String connectionNo) {
		try{
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getLastMonthRecord",BillingLedgerEntity.class).setParameter("connection_no", connectionNo).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
		@SuppressWarnings("unchecked")
		@Override
		public List<Object[]> generateReports(String neplaliFMonth, String engFmonth,String neplaliTMonth, String engTmonth, String counterNoSel,String mrcodeSel, String reportName, ModelMap model,HttpServletRequest request)
		{
			try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(engTmonth));
			c.add(Calendar.DATE, 1);  // number of days to add
			engTmonth = sdf.format(c.getTime()); 
			}catch(Exception e){
				
			}
			
			HttpSession session=request.getSession(false);
			String schemaName=(String)session.getAttribute("schemaName"); 
			
			List<Object[]> list=null;
			String query="";
			String wardNo=(String) model.get("wardnoForm");
			if(wardNo.equalsIgnoreCase("0"))
			{
				wardNo="%";
			}
			if(counterNoSel.equalsIgnoreCase("0"))
			{
				counterNoSel="%";
			}
			
			
			try 
			{
				if(reportName.equalsIgnoreCase("MonthlyLedger"))
				{
					/*query="SELECT " +" "+ 
							"CONNECTION_NO,BILL_DATE,TO_CHAR(DUE_DATE,'DD-MM-YYYY')AS DUE_EDATE,DUE_DATE_NEP," +" "+
							"MR_ID,TO_CHAR(RDNG_DATE,'DD-MM-YYYY')AS R_EDATE,RDNG_MTH,PRESENT_READING," +" "+
							"PREVIOUS_READING,CONSUMPTION,WATER_CHARGES,SERVICE_CHARGE, ARREARS," +" "+
							"NET_AMOUNT" +" "+
							"FROM BSW_LEDGER WHERE RDNG_DATE IS NOT  NULL AND RDNG_DATE BETWEEN " +" "+
							"TO_DATE('"+engFmonth+"','dd-MM-yyyy') AND TO_DATE('"+engTmonth+"','dd-MM-yyyy') ORDER BY RDNG_DATE DESC";*/
					query="SELECT  CONNECTION_NO ,TO_CHAR(RDNG_DATE,'DD-MM-YYYY')AS R_EDATE,PRESENT_READING, PREVIOUS_READING,CONSUMPTION,WATER_CHARGES,SERVICE_CHARGE, " +" "+
							" ARREARS, NET_AMOUNT,TO_CHAR(RDNG_DATE,'DD-MM-YYYY') as BILL_DATE,WARDNO FROM "+schemaName+".BSW_LEDGER WHERE RDNG_DATE IS NOT  NULL AND RDNG_DATE BETWEEN  TO_DATE('"+engFmonth+"','dd-MM-yyyy') " +" "+
							" AND TO_DATE('"+engTmonth+"','dd-MM-yyyy') AND NET_AMOUNT>0 AND WARDNO LIKE '"+wardNo+"' ORDER BY RDNG_DATE DESC";
					
					list=getCustomEntityManager().createNativeQuery(query).getResultList();
					//System.out.println(query);
					if(list.size()>0)
					{
						model.addAttribute("arrayLength",list.get(0).length);
						model.addAttribute("listData",list);
						model.addAttribute("reportName","Monthly Ledger Report");
					}
					else
					{
						model.addAttribute("msg","No Data available in table.");
					}
				}
				if(reportName.equalsIgnoreCase("DaywiseMeterReading"))
				{
					query="SELECT RDNG_DATE||'' as RDATE,COUNT(CONNECTION_NO)CONNECTIONS_BILLED,sum(CONSUMPTION)AS TOTAL_CONSUMPION,SUM(NET_AMOUNT)AS TOTAL_NET" +" "+
							"FROM "+schemaName+".BSW_LEDGER " +" "+
							"WHERE RDNG_DATE IS NOT NULL AND RDNG_DATE" +" "+
							"BETWEEN  TO_DATE('"+engFmonth+"','dd-MM-yyyy') AND TO_DATE('"+engTmonth+"','dd-MM-yyyy') " +" "+
							"GROUP BY RDNG_DATE,MR_ID" +" "+
							"ORDER BY RDNG_DATE DESC";
					list=getCustomEntityManager().createNativeQuery(query).getResultList();
					//System.out.println(query);
					if(list.size()>0)
					{
						model.addAttribute("DaywiseMeterReadingLength",list.get(0).length);
						model.addAttribute("DaywiseMeterReading",list);
						model.addAttribute("reportName","Daywise Meter Reading Report");
					}
					else
					{
						model.addAttribute("msg","No Data available in table.");
					}
				}
				if(reportName.equalsIgnoreCase("WeeklyMeterReading"))
				{
					query="SELECT  AA.DAY,BB.* FROM" +" "+ 
							"(SELECT" +
							"(to_date('"+engFmonth+"','DD-MM-YYYY') + (level-1) )||'' AS day,level" +" "+
							"FROM" +" "+
							"dual" +" "+
							"CONNECT BY LEVEL <= (to_date('"+engTmonth+"','DD-MM-YYYY') - to_date('"+engFmonth+"','DD-MM-YYYY') + 0)" +" "+
							")AA LEFT JOIN " +" "+
							"(" +" "+
							"SELECT RDNG_DATE||'' as RDATE,COUNT(CONNECTION_NO) as CONNECTIONS_BILLED,sum(CONSUMPTION)AS " +" "+
							"TOTAL_CONSUMPION,SUM(NET_AMOUNT)AS TOTAL_NET FROM "+schemaName+".BSW_LEDGER WHERE RDNG_DATE IS NOT NULL " +" "+
							"AND RDNG_DATE BETWEEN  TO_DATE('"+engFmonth+"','dd-MM-yyyy') " +" "+
							"AND TO_DATE('"+engTmonth+"','dd-MM-yyyy') GROUP BY RDNG_DATE ORDER BY RDNG_DATE" +" "+
							") " +" "+
							"BB ON AA.DAY= BB.RDATE ORDER BY TO_DATE(AA.DAY ,'dd-MM-yyyy')";
					list=getCustomEntityManager().createNativeQuery(query).getResultList();
					//System.out.println(query);
					if(list.size()>0)
					{
						model.addAttribute("WeeklyMeterReadingLength",list.get(0).length);
						model.addAttribute("WeeklyMeterReading",list);
						model.addAttribute("reportName","Weekly Meter Reading Report");
					}
					else
					{
						model.addAttribute("msg","No Data available in table.");
					}
				}
				
				if(reportName.equalsIgnoreCase("CounterwisedailyCollection"))
				{
					query="SELECT AA.COUNTERNO,TO_CHAR(AA.RDATE,'dd-MM-yyyy')||'' AS RDATE, SUM(AA.AMOUNT)AS TOTAL_AMOUNT,COUNT(RECEIPT_NO)AS TOTAL_RECEIPTS,COUNT(CASE WHEN AA.PAY_MODE=1 THEN AA.RECEIPT_NO END )AS CASH,COUNT(CASE WHEN AA.PAY_MODE=2 THEN AA.RECEIPT_NO END )AS CHEQUE," +
							"COUNT(CASE WHEN AA.PAY_MODE=3 THEN AA.RECEIPT_NO END )AS DD  FROM" +
							"(" +
							"SELECT COUNTERNO,AMOUNT,RECEIPT_NO,PAY_MODE,RDATE FROM "+schemaName+".BSW_PAYMENTS " +
							"WHERE RDATE BETWEEN TO_DATE('"+engFmonth+"','dd-MM-yyyy') AND TO_DATE('"+engTmonth+"','dd-MM-yyyy')  AND COUNTERNO IS NOT NULL AND COUNTERNO LIKE '"+counterNoSel+"'" +
							")AA GROUP BY AA.COUNTERNO,TO_CHAR(AA.RDATE,'dd-MM-yyyy') " +
							"ORDER BY TO_CHAR(AA.RDATE,'dd-MM-yyyy')DESC";
					
					list=getCustomEntityManager().createNativeQuery(query).getResultList();
					//System.out.println(query);
					if(list.size()>0)
					{
						model.addAttribute("CounterwisedailyCollectionLength",list.get(0).length);
						model.addAttribute("CounterwisedailyCollection",list);
						model.addAttribute("reportName","Counter wise daily Collection Report");
					}
					else
					{
						model.addAttribute("msg","No Data available in table.");
					}
				
				}
				if(reportName.equalsIgnoreCase("MonthlyCollectionSummary"))
				{
					query="SELECT AA.RDATE AS RDATE,  " +" "+
							"SUM(AA.AMOUNT)AS TOTAL_AMOUNT,COUNT(RECEIPT_NO)AS TOTAL_RECEIPTS, " +" "+
							"COUNT(CASE WHEN AA.PAY_MODE=1 THEN AA.RECEIPT_NO END )AS CASH, " +" "+
							"COUNT(CASE WHEN AA.PAY_MODE=2 THEN AA.RECEIPT_NO END )AS " +" "+
							" CHEQUE,COUNT(CASE WHEN AA.PAY_MODE=3 THEN AA.RECEIPT_NO END )AS DD  " +" "+
							" FROM " +" "+
							"( " +" "+
							"SELECT TO_CHAR(RDATE,'MON-YYYY')AS RDATE,AMOUNT,RECEIPT_NO,PAY_MODE FROM "+schemaName+".BSW_PAYMENTS WHERE RDATE BETWEEN TO_DATE('"+engFmonth+"','dd-MM-yyyy') " +" "+
							"AND TO_DATE('"+engTmonth+"','dd-MM-yyyy')  AND COUNTERNO IS NOT NULL " +" "+
							")AA GROUP BY AA.RDATE ";
							
					list=getCustomEntityManager().createNativeQuery(query).getResultList();
					if(list.size()>0)
					{
						model.addAttribute("MonthlyCollectionSummaryLength",list.get(0).length);
						model.addAttribute("MonthlyCollectionSummary",list);
						model.addAttribute("reportName","Monthly Collection Summary");
					}
					else
					{
						model.addAttribute("msg","No Data available in table.");
					}
				}
				
				if(reportName.equalsIgnoreCase("DailyCollection"))
				{
					query="SELECT AA.RMONTH AS RMONTH,  " +
							"SUM(AA.AMOUNT)AS TOTAL_AMOUNT,COUNT(RECEIPT_NO)AS TOTAL_RECEIPTS, " +
							"COUNT(CASE WHEN AA.PAY_MODE=1 THEN AA.RECEIPT_NO END )AS CASH, " +
							"COUNT(CASE WHEN AA.PAY_MODE=2 THEN AA.RECEIPT_NO END )AS " +
							" CHEQUE,COUNT(CASE WHEN AA.PAY_MODE=3 THEN AA.RECEIPT_NO END )AS DD  " +
							" FROM " +
							"( " +
							"SELECT TO_CHAR(RDATE,'dd-MM-yyyy')AS RMONTH,AMOUNT,RECEIPT_NO,PAY_MODE,TOWARDS,BANKNAME FROM "+schemaName+".BSW_PAYMENTS WHERE RDATE BETWEEN TO_DATE('"+engFmonth+"','dd-MM-yyyy') " +
							"AND TO_DATE('"+engTmonth+"','dd-MM-yyyy')  AND COUNTERNO IS NOT NULL " +
							")AA GROUP BY AA.RMONTH ORDER BY  AA.RMONTH DESC";

							
					list=getCustomEntityManager().createNativeQuery(query).getResultList();
					if(list.size()>0)
					{
						model.addAttribute("DailyCollectionLength",list.get(0).length);
						model.addAttribute("DailyCollection",list);
						model.addAttribute("reportName","Daily Collection");
					}
					else
					{
						model.addAttribute("msg","No Data available in table.");
					}
				}
				
				
				if(reportName.equalsIgnoreCase("MonthlyCollectionDetail"))
				{
					query="SELECT TO_CHAR(RDATE,'dd-MM-yyyy')AS RADTE,CONNECTION_NO,AMOUNT,RECEIPT_NO,PAY_MODE,BANKNAME,TOWARDS FROM "+schemaName+".BSW_PAYMENTS WHERE RDATE BETWEEN TO_DATE('"+engFmonth+"','dd-MM-yyyy')" +" "+
							" AND TO_DATE('"+engTmonth+"','dd-MM-yyyy') AND COUNTERNO IS NOT NULL";

							
					list=getCustomEntityManager().createNativeQuery(query).getResultList();
					if(list.size()>0)
					{
						model.addAttribute("MonthlyCollectionDetailLength",list.get(0).length);
						model.addAttribute("MonthlyCollectionDetail",list);
						model.addAttribute("reportName","Monthly Collection Details");
					}
					else
					{
						model.addAttribute("msg","No Data available in table.");
					}
				}
				
				if(reportName.equalsIgnoreCase("MonthwiseMeterReading"))
				{
					query="SELECT TO_CHAR(RDNG_DATE,'MON-YYYY') as RDATE, " +" "+
							"COUNT(CONNECTION_NO)CONNECTIONS_BILLED, " +" "+
							"sum(CONSUMPTION)AS TOTAL_CONSUMPION,SUM(NET_AMOUNT)AS TOTAL_NET  " +" "+
							"FROM "+schemaName+".BSW_LEDGER  WHERE RDNG_DATE IS NOT NULL AND RDNG_DATE " +" "+
							"BETWEEN  TO_DATE('"+engFmonth+"','dd-MM-yyyy') AND TO_DATE('"+engTmonth+"','dd-MM-yyyy')  " +" "+
							"GROUP BY TO_CHAR(RDNG_DATE,'MON-YYYY') ORDER BY TO_DATE(TO_CHAR(RDNG_DATE,'MON-YYYY'),'MON-YYYY')  DESC";
					list=getCustomEntityManager().createNativeQuery(query).getResultList();
					//System.out.println(query);
					if(list.size()>0)
					{
						model.addAttribute("MonthwiseMeterReadingLength",list.get(0).length);
						model.addAttribute("MonthwiseMeterReading",list);
						model.addAttribute("reportName","Month wise MeterReading");
					}
					else
					{
						model.addAttribute("msg","No Data available in table.");
					}
				}
				if(reportName.equalsIgnoreCase("DetailedCollectionSummary"))
				{
					query="SELECT CONNECTION_NO,AMOUNT,RECEIPT_NO,RDATE,COUNTERNO,TOWARDS,RECORDTYPE,PAY_MODE,CDNO,WATER_CHARGES,SW_CHARGES,METER_RENT,MISCELLANEOUS_COST,PENALTY,REBATE,FRECAMOUNT,ADVANCE,ADVANCE_REBATE,NVL(OLD_BALANCE,0) "
							+ "FROM BSW_PAYMENTS WHERE RDATE >=TO_DATE('"+engFmonth+"','dd-MM-yyyy') AND RDATE <=TO_DATE('"+engTmonth+"','dd-MM-yyyy')  AND COUNTERNO IS NOT NULL AND COUNTERNO LIKE '"+counterNoSel+"'ORDER BY RECEIPT_NO ASC";
					
					//System.out.println(query);
					list=getCustomEntityManager().createNativeQuery(query).getResultList();
					//System.out.println(query);
					if(list.size()>0)
					{
						model.addAttribute("DetailedCollectionSummarylength",list.get(0).length);
						model.addAttribute("DetailedCollectionSummary",list);
						model.addAttribute("reportName","Detailed Cash Collection Report");
					}
					else
					{
						model.addAttribute("msg","No Data available in table.");
					}
				}
				
				
				//System.out.println(query);
				
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return list;
		}
		
		
		
		@SuppressWarnings("unchecked")
		@Override
		public List<String> getDistinctWardNo(String schemaName)
		{
			List<String> list=null;
			try
			{
				list=getCustomEntityManager().createNativeQuery("SELECT DISTINCT WARDNO  FROM "+schemaName+".BSW_LEDGER ORDER BY WARDNO").getResultList();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
			return list;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<String> getDistinctCounter(String schemaName)
		{
			List<String> list=null;
			try
			{
				list=getCustomEntityManager().createNativeQuery("SELECT DISTINCT COUNTERNO FROM "+schemaName+".BSW_PAYMENTS WHERE COUNTERNO IS NOT NULL ORDER BY COUNTERNO").getResultList();
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return null;
			}
			return list;
		}
		
	

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingLedgerEntity> getLedgerDataByConnectionNum(String connId) {
		List<BillingLedgerEntity> ledger = null;
		try {
			ledger = getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getLedgerDataByConnectionNum").setParameter("connId", connId.trim().toUpperCase()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ledger;
	}

	@Override
	public List<?> getWardWiseBillCount(String sitecode) {
		try{
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getWardWiseBillCount").getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<?> getConnectionHistory(String wardNo,int readingday,int value) {
		
		if(value==0){
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getConnectionHistoryUnbilled").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day", readingday).getResultList();
		}else{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getConnectionHistorybilled").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day", readingday).getResultList();
		}
		
		
		
		

	}

	
	@Override
	public List<?> billedLedgerByWardNo(String wardNo,int reading_day, int reading_month, double pipesize,String concategory) {
		
		if(pipesize==0.5){
		
		if("All".equalsIgnoreCase(concategory)){
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerByWardNoSA").
					setParameter("wardno", wardNo.toUpperCase().trim()).
					setParameter("reading_day", reading_day).
					setParameter("reading_month", ""+reading_month).setParameter("pipe_size", pipesize).getResultList();
		}else{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerByWardNoSACC").
					setParameter("wardno", wardNo.toUpperCase().trim()).
					setParameter("reading_day", reading_day).
					setParameter("reading_month", ""+reading_month).setParameter("pipe_size", pipesize).setParameter("con_category", concategory).getResultList();
		}
		}else{
			
			if("All".equalsIgnoreCase(concategory)){
				return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerByWardNoTHA").
						setParameter("wardno", wardNo.toUpperCase().trim()).
						setParameter("reading_day", reading_day).
						setParameter("reading_month", ""+reading_month).setParameter("pipe_size", pipesize).getResultList();
			}else{
				return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerByWardNoTHACC").
						setParameter("wardno", wardNo.toUpperCase().trim()).
						setParameter("reading_day", reading_day).
						setParameter("reading_month", ""+reading_month).setParameter("pipe_size", pipesize).setParameter("con_category", concategory).getResultList();
			}
			
		}
	}

	@Override
	public long checkConnect_noInMaster(String connection_no) {
		try{
			return (long)getCustomEntityManager().createNamedQuery("BillingLedgerEntity.checkConnect_noInLedger").setParameter("connection_no", connection_no).getSingleResult();
		}catch(Exception e){
			
			return 0;
		}
	}

	@Override
	public String getTotalBillingCount(String siteCode) {
		String billCount = null;
		try {

			billCount = String.valueOf(getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getTotalBillingCount").setParameter("siteCode", siteCode).getSingleResult());

		} catch (Exception e) {
			return "0";
		}
		return billCount;
	}

	@Override
	public String getTotalDemand(String siteCode) {
		String totalDemand = null;
		try {

			totalDemand = String.valueOf(getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getTotalDemand").setParameter("siteCode", siteCode).getSingleResult());


		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalDemand;
	}

	@Override
	public String getLatestMonthYear(String siteCode) {
		try{
		return String.valueOf(getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getLatestMonthYear").getSingleResult());
		}catch(Exception e){
			return null;
		}
		}

	@Override
	public List<?> billedLedgerByConnectionNoCM(String connection_no) {
		
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerByConnectionNoCM").setParameter("connection_no", connection_no.toUpperCase().trim()).getResultList();

	}

	@Override
	public Date getMaxPaymentDateByConNo(String connectionNo) {
		try{
			return (Date) getCustomEntityManager().createNamedQuery("PaymentEntity.getMaxPaymentDateByConNo").setParameter("connection_no", connectionNo.toUpperCase().trim()).getSingleResult();

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Date getMinReadDateByConNo(String connectionNo) {
		try{
			return (Date) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getMinReadDateByConNo").setParameter("connection_no", connectionNo.toUpperCase().trim()).getSingleResult();

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<?> viewBillLedgertHistory(String connectionNo) {
		try {
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.viewBillLedgertHistory").setParameter("connection_no", connectionNo.trim().toUpperCase()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<?> findBillsByReceiptNull(String connectionNo) {
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findBillsByReceiptNull").setParameter("connection_no", connectionNo.trim().toUpperCase()).getResultList();
	}

	@Override
	public BillingLedgerEntity findMaxRecordNotNullReceipt(String connectionNo) {

		try{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findMaxRecordNotNullReceipt",BillingLedgerEntity.class).setParameter("connection_no", connectionNo.trim()).getSingleResult();
		}catch(Exception e){
			
			return null;
		}
	}

	@Override
	public double getAvgConsumption(String connectionNo) {
		try{
			return (double) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getAvgConsumption").setParameter("connection_no", connectionNo.trim()).getSingleResult();

		}catch(Exception e){
			
			return 0;
		}
	}

	@Override
	public long ledgerCountByWardNoRdayPS(String wardNo, Integer readingday,
			double pipesize,String monthyear,String concategory) {
		try{
			if(pipesize==0.5){

				if("All".equalsIgnoreCase(concategory)){
			    return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.ledgerCountByWardNoRdayPSSA").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).getSingleResult();
			
				}else{
				    return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.ledgerCountByWardNoRdayPSSACC").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).setParameter("con_category", concategory).getSingleResult();

				}
			}else{
				
				if("All".equalsIgnoreCase(concategory)){
				return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.ledgerCountByWardNoRdayPSTHA").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).getSingleResult();
				}else{
					return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.ledgerCountByWardNoRdayPSTHACC").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).setParameter("con_category", concategory).getSingleResult();
	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return 0;
			
		}
	}

	@Override
	public long billedLedgerCountByWardNoRdayPS(String wardNo,Integer readingday, double pipesize,String monthyear,String concategory) {
		
		try{
			
			if(pipesize==0.5){
				if("All".equalsIgnoreCase(concategory)){
				return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerCountByWardNoRdayPSSA").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).getSingleResult();
				}else{
					return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerCountByWardNoRdayPSSACC").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).setParameter("con_category", concategory).getSingleResult();

				}
				
				}else{
					if("All".equalsIgnoreCase(concategory)){
					
					return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerCountByWardNoRdayPSTHA").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).getSingleResult();
					}else{
						return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.billedLedgerCountByWardNoRdayPSTHACC").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).setParameter("con_category", concategory).getSingleResult();

					}
			}
		}catch(Exception e){
			e.printStackTrace();	
			return 0;
				
		}
	}

	@Override
	public long unbilledLedgerCountByWardNoRdayPS(String wardNo,
			Integer readingday, double pipesize,String monthyear,String concategory) {
		try{
			if(pipesize==0.5){
			
				if("All".equalsIgnoreCase(concategory)){
				return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.unbilledLedgerCountByWardNoRdayPSSA").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).getSingleResult();
				}else{
					return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.unbilledLedgerCountByWardNoRdayPSSACC").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).setParameter("con_category", concategory).getSingleResult();

				}
				
				}else{
				
					if("All".equalsIgnoreCase(concategory)){
					return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.unbilledLedgerCountByWardNoRdayPSTHA").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).getSingleResult();
					}
					else{
						return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.unbilledLedgerCountByWardNoRdayPSTHACC").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size", pipesize).setParameter("monthyearnep", monthyear).setParameter("con_category", concategory).getSingleResult();

					}
			}
		 }catch(Exception e){
			e.printStackTrace();	
			 return 0;
				
			}
	}

	@Override
	public List<?> getReadingEntryUnbilled(String wardNo, int reading_day,double pipe_size) {
		//System.out.println(pipe_size+"-----"+pipe_size);
		if(pipe_size==0.5){
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getReadingEntryUnbilledSA").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",reading_day).setParameter("pipe_size", pipe_size).getResultList();
		}else{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getReadingEntryUnbilledTHA").setParameter("wardno", wardNo.toUpperCase()).setParameter("reading_day",reading_day).setParameter("pipe_size", pipe_size).getResultList();
	
		}
	}

	@Override
	public List<BillingLedgerEntity> getListByConNoAndRECNo(String connectionNo, String receiptNo) {
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getListByConNoAndRECNo",BillingLedgerEntity.class).setParameter("connection_no", connectionNo.toUpperCase()).setParameter("receipt_no",receiptNo.toUpperCase().trim()).getResultList();
	}

	@Override
	public List<?> viewBillLedgertHistoryForReading(String connectionNo) {
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.viewBillLedgertHistoryForReading").setParameter("connection_no", connectionNo.trim().toUpperCase()).getResultList();

	}

	@Override
	public long checkEntriesExist(String monthyearnep) {
		try{
			return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.checkEntriesExist").setParameter("monthyearnep", monthyearnep).getSingleResult();

		}catch(Exception e){
			e.printStackTrace();
			return 2121;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> updateBulkBillingUnmetered() {
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.updateBulkBillingUnmetered").getResultList();

	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int custombatchUpdateBillingLedgerEntity(List<BillingLedgerEntity> billlist, PaymentEntity paymentdata,String schemaName) {
		Session session = getCustomEntityManager(schemaName).unwrap(Session.class);
		int insertedCount = 0;
		for (int i = 0; i < billlist.size(); ++i) {
			//System.out.println("===="+i);
			try {
				BillingLedgerEntity b=billlist.get(i);
				if(i!=0){
					b.setClose_balance(null);	
				}else{
					b.setClose_balance(
							 (b.getClose_balance()==null?0:b.getClose_balance()
							-((paymentdata.getFrecamount()==null?0:paymentdata.getFrecamount())
							-(paymentdata.getAmount()==null?0:paymentdata.getAmount())
							-(paymentdata.getAdvance_rebate()==null?0:paymentdata.getAdvance_rebate()))));
				}
					b.setLast_paid_amount(null);
					b.setReceipt_no(null);
					b.setReceipt_date(null);
					b.setPenalty(null);
					b.setRebate(null);
					b.setBill_adj_amount(null);
					b.setPenalty_adj_amount(null);
				
				    session.merge(b);
					session.flush();
					session.clear();
				    BsmartWaterLogger.logger.info(billlist.get(i) + " saved successful");
				    
			} catch (RuntimeException re) {
				    BsmartWaterLogger.logger.error("saving " + billlist.get(i)+ " failed", re);
				    throw re;
			}
			insertedCount++;
		}
		return insertedCount;
		
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int custombatchUpdateBillingLedgerEntity(List<BillingLedgerEntity> billlist,Double bPenalty, Double bRebate, Double bOther, String bReceiptNo, Double bReceivedAmount, Double bFrecamount, Double bAdvanceRebate,Timestamp today,String schemaName,Double bill_adj_amount, Double penalty_adj_amount,Date rdate,String adjtype) {
		Session session = getCustomEntityManager(schemaName).unwrap(Session.class);
		int insertedCount = 0;
		for (int i = 0; i < billlist.size(); ++i) {
			
			try {
				BillingLedgerEntity bill=billlist.get(i);
				if(i==0){
					   bill.setPenalty(bPenalty);
					   bill.setRebate(bRebate);
					   bill.setOther(bOther);
					   bill.setReceipt_no(bReceiptNo);
					   bill.setReceipt_date(rdate);
					   
					   if("BAC".equalsIgnoreCase(adjtype)){
						   bill.setLast_paid_amount(bReceivedAmount);
						   bill.setClose_balance((bFrecamount==null?0:bFrecamount)-(bReceivedAmount==null?0:bReceivedAmount)-(bAdvanceRebate==null?0:bAdvanceRebate));
					   }else{
						  
						   /*if("BDJ".equalsIgnoreCase(adjtype)){
							   
							   if("TA".equalsIgnoreCase(bill.getStatus())){
								   bill.setClose_balance((bFrecamount==null?0:bFrecamount)-(bReceivedAmount==null?0:bReceivedAmount)-(bAdvanceRebate==null?0:bAdvanceRebate)-(bill_adj_amount));
   
							   }else if("HA".equalsIgnoreCase(bill.getStatus())){
								   bill.setClose_balance((bFrecamount==null?0:bFrecamount)-(bReceivedAmount==null?0:bReceivedAmount)-(bAdvanceRebate==null?0:bAdvanceRebate));
							   }else{
								   bill.setClose_balance((bFrecamount==null?0:bFrecamount)-(bReceivedAmount==null?0:bReceivedAmount)-(bAdvanceRebate==null?0:bAdvanceRebate)-(bill_adj_amount));
							   }
							   
						   }else{
					               bill.setClose_balance((bFrecamount==null?0:bFrecamount)-(bReceivedAmount==null?0:bReceivedAmount)-(bAdvanceRebate==null?0:bAdvanceRebate)-(bill_adj_amount));
						   }*/
						   
						   //bill.setBill_adj_amount(bill_adj_amount);
						   //bill.setPenalty_adj_amount(penalty_adj_amount);
						   bill.setLast_paid_amount(bReceivedAmount);
						   bill.setClose_balance((bFrecamount==null?0:bFrecamount)-(bReceivedAmount==null?0:bReceivedAmount)-(bAdvanceRebate==null?0:bAdvanceRebate)-(bill_adj_amount));
					   }
					  
					   
				   }else{
					   bill.setReceipt_no(bReceiptNo);
					   bill.setReceipt_date(rdate);
				   }
				    session.merge(bill);
					session.flush();
					session.clear();
					
				    
				    
			} catch (RuntimeException re) {
				    BsmartWaterLogger.logger.error("saving " + billlist.get(i)+ " failed", re);
				    throw re;
			}
			insertedCount++;
		}
		return insertedCount;
	}

	@Override
	public long checkLedgerReceiptExists(String connectionno) {

		try{
			return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.checkLedgerReceiptExists").setParameter("connection_no", connectionno.trim().toUpperCase()).getSingleResult();

		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String getLatestNepaliMonthYear() {
		try{
			return String.valueOf(getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getLatestNepaliMonthYear").getSingleResult());
			}catch(Exception e){
				return null;
			}
		  }

	@Override
	public List<?> getLedgerDataByWardMonthEnd(String monthyear,String fromdate, String todate) {

		
		int year=Integer.parseInt(monthyear.substring(0, 4));
		int month=Integer.parseInt(monthyear.substring(4, 6));
		//System.out.println(month+"----month");
		
		if(month==1){
			month=12;
			year=year-1;
		}else{
			month=month-1;
		}
		
		String month1=""+month;
		
		if(month1.length()==1){
			month1="0"+month;
		}
		//System.out.println(month1+"----month1");
		
		int monthyearp=Integer.parseInt(year+""+month1);
		
		List<?> list=null;
		try
		{
			
			
			String sql="SELECT BWARD, BDEN,NVL(FCBAL,0),NVL(BWC,0),NVL(BSWC,0),NVL(PMISC,0),NVL(BMR,0),NVL(ADDPEN,0),NVL(PPEN,0),NVL(PBLA,0),NVL(PPA,0),NVL(PRA,0),NVL(PREB,0),NVL(PADV,0), NVL(PA,0), BCT, BCC,"
						+" FWARD, FDEN, FCT, FCC, PWARD, PDEN, PCT ,PCC,NVL(PSNG,0),NVL(SUSP,0),NVL(WOP,0),NVL(AFP,0)"
						+" FROM"
						+" (SELECT BWC,BSWC, BMR, BWARD, BDEN, BCT, BCC, PWARD, PDEN, PCT ,PCC, PMISC, PPEN, PBLA, PPA, PRA, PREB, PADV, PA,ADDPEN,PSNG,SUSP,WOP,AFP FROM"
						+" (SELECT SUM(nvl(l.WATER_CHARGES,0)) AS BWC,SUM(nvl(l.SW_CHARGES,0)) AS BSWC,SUM(nvl(l.MTR_RENT,0)) AS BMR,"
						+" (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END) AS BWARD,m.DENOTED_BY AS BDEN,"
						+" m.CON_TYPE AS BCT,m.CON_CATEGORY AS BCC,SUM(nvl(l.DR_AMOUNT,0)),SUM(nvl(l.DR_AMOUNT,0)) as ADDPEN,SUM(nvl(l.SUNDRY_AMOUNT,0)) as PSNG,SUM(nvl(l.SUSPENSE,0)) as SUSP,SUM(CASE WHEN l.LAST_PAID_AMOUNT IS NULL AND l.RECEIPT_NO IS NULL AND l.MONTHYEARNEP="+monthyear+" THEN l.SERVICE_CHARGE END) AS WOP,SUM(CASE WHEN l.LAST_PAID_AMOUNT IS NOT NULL AND l.RECEIPT_NO IS NOT NULL AND l.MONTHYEARNEP="+monthyear+"  AND l.REMARKS LIKE 'Correction approved after payment%' THEN l.SERVICE_CHARGE END) AS AFP FROM BSW_LEDGER l,BSW_MASTER m"
						+" WHERE MONTHYEARNEP="+monthyear+" AND m.CONNECTION_NO=l.CONNECTION_NO GROUP BY"
						+" (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END),m.DENOTED_BY,m.CON_CATEGORY,m.CON_TYPE ORDER BY m.CON_TYPE)A"
						
						+" FULL OUTER JOIN"
						
						+" (SELECT (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END) AS PWARD,p.DENOTED_BY AS PDEN,m.CON_TYPE AS PCT,"
						+" m.CON_CATEGORY AS PCC,SUM(nvl(p.MISCELLANEOUS_COST,0)) AS PMISC,SUM(nvl(p.PENALTY,0)) AS PPEN,"
						+" SUM(nvl(p.BILL_ADJ_AMOUNT,0)) AS PBLA,"
						+" SUM(nvl(p.PENALTY_ADJ_AMOUNT,0)) AS PPA,SUM(nvl(p.AMOUNT,0)) AS PRA,SUM(nvl(p.REBATE,0)) AS PREB,SUM(nvl(p.ADVANCE,0)) AS PADV,"
						+" SUM(nvl(p.ADVANCE_REBATE,0)) AS PA FROM BSW_PAYMENTS p,BSW_MASTER m"
						+" WHERE trunc(RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy') AND trunc(RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy')"
						+" AND UPPER(M .CONNECTION_NO) = UPPER(P .CONNECTION_NO) AND p.CANCELLEDREMARKS IS NULL AND UPPER(p.TOWARDS)='BILL PAYMENT' GROUP BY"
						+" (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END),p.DENOTED_BY,m.CON_TYPE,m.CON_CATEGORY ORDER BY m.CON_TYPE)B"
						
						+" ON A.BWARD=B.PWARD AND A.BDEN=B.PDEN AND A.BCT=B.PCT AND A.BCC=B.PCC)C"
						+" FULL OUTER JOIN "
						+" (SELECT b.WARD_NO as FWARD,b.DENOTED_BY AS FDEN,b.CON_TYPE AS FCT,b.CON_CATEGORY AS FCC,"
						+" SUM(NVL(b.CLOSING_BALANCE,0)) AS FCBAL FROM FISCAL_YEAR_BALANCE b WHERE B.FISCAL_YEAR_MONTH="+monthyearp+" GROUP BY"
						+" b.WARD_NO,b.DENOTED_BY,b.CON_TYPE,b.CON_CATEGORY ORDER BY b.CON_TYPE ASC)D"
						+" ON C.BWARD=D.FWARD AND C.BDEN=D.FDEN AND C.BCT=D.FCT AND C.BCC=D.FCC";
		
			//System.err.println("SQL***********"+sql);
			
			list=getCustomEntityManager().createNativeQuery(sql).getResultList();
		
		
		}
		
		
		
		
		
		
		
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return list;
	}

	@Override
	public BillingLedgerEntity getLatestRecordByConnectionNo(String connection_no) {
		try{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getLatestRecordByConnectionNo",BillingLedgerEntity.class).setParameter("connection_no", connection_no.trim().toUpperCase()).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	

	@Override
	public long getMissedBillsInLedger(String wardNo, int readingday, double pipe_size, String monthyearnep) {
		try{
			if(pipe_size==0.5){
			return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getMissedBillsInLedgerSA").setParameter("ward_no", wardNo.trim().toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size",pipe_size).setParameter("monthyearnep", monthyearnep).getSingleResult();
		
			}else{
				return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getMissedBillsInLedgerTHA").setParameter("ward_no", wardNo.trim().toUpperCase()).setParameter("reading_day",readingday).setParameter("pipe_size",pipe_size).setParameter("monthyearnep", monthyearnep).getSingleResult();
			}
		
		}catch(Exception e){
			return 1010;
		}
		}

	@Override
	public String getMaxBillNoByWardNo(String string) {

		try{
			return (String) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getMaxBillNoByWardNo").setParameter("billno", string.trim().toUpperCase()).getSingleResult();
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> getLedgetInsertByWardNoRday(String wardNo,int reading_day, double pipe_size,String monthyearnep) {
		try{
			if(pipe_size==0.5){
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getLedgetInsertByWardNoRdaySA").setParameter("ward_no", wardNo.trim().toUpperCase()).setParameter("reading_day",reading_day).setParameter("pipe_size",pipe_size).setParameter("monthyearnep", monthyearnep).getResultList();
		
			}else{
				return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getLedgetInsertByWardNoRdayTHA").setParameter("ward_no", wardNo.trim().toUpperCase()).setParameter("reading_day",reading_day).setParameter("pipe_size",pipe_size).setParameter("monthyearnep", monthyearnep).getResultList();
			}
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingLedgerEntity> findBillsByReceiptNullAll(
			String bConnectionNo) {
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findBillsByReceiptNullAll").setParameter("connection_no", bConnectionNo.trim().toUpperCase()).getResultList();

	}

	@Override
	public int saveAsyncBulkBillUnmetered(String userName, String monthyearnep,
			List<ConsumerMaster> consumerMasters, int cmonth, int cyear,
			String nepalidate, String schemaName,String sitecode) {
		


		Session session = getCustomEntityManager(schemaName).unwrap(Session.class);
		int insertedCount = 0;
		for (int i = 0; i < consumerMasters.size(); ++i) {
			//System.out.println("===="+i);
			try {
				

				ConsumerMaster cs=consumerMasters.get(i);
				
				TariffRateMaster tariffRateMaster=tariffRateMasterDao.getTariffRate(cs.getPipe_size(),cs.getCon_type());
				
				if(tariffRateMaster!=null){
					double minimum_charges=0;
					double sewageCharges=0;
					double arrears=0;
					double waterCharges=0;
					BillingLedgerEntity ble=new BillingLedgerEntity();
					
					if("Defaulter".equalsIgnoreCase(cs.getCon_satatus()) || "Temporary".equalsIgnoreCase(cs.getCon_satatus())){
						ble.setMc_status(11);
					}else{
					
					   if("LWS".equalsIgnoreCase(cs.getBill_remarksum())){
						   minimum_charges=100;
						   waterCharges=100;
					   }else{
						   minimum_charges=tariffRateMaster.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
						   waterCharges=tariffRateMaster.getMonthly_charges()*1;  
					   }																			//Water Charges for UnMeterd //*(billperiod)
					
					if("Yes".equalsIgnoreCase(cs.getSewage_used())){
						sewageCharges=0.5*waterCharges;										//SeweRage Charges
					}
					ble.setMc_status(15);
					
					}
					
					ble.setConnection_no(cs.getConnection_no());
					ble.setMr_id(null);
					ble.setRdng_mth(cmonth);
					ble.setPrevious_reading((double) 0);
					ble.setPresent_reading((double) 0);
					ble.setConsumption((double) 0);
					ble.setWater_charges(waterCharges);
					ble.setService_charge((double) 0);
					ble.setMinimum_charges(minimum_charges);
					ble.setBill_period(1.0);
					ble.setSitecode(sitecode);
					BsmartWaterLogger.logger.info(cs.getConnection_no()+"connectionNo");
					BillingLedgerEntity b=null;
					
					try{
						b=getCustomEntityManager(schemaName).createNamedQuery("BillingLedgerEntity.getLatestRecordByConnectionNo",BillingLedgerEntity.class).setParameter("connection_no", cs.getConnection_no().trim().toUpperCase()).getSingleResult();
					}catch(Exception e){
						b=null;
					}
					
					if(b!=null){
						//System.out.println(">>>>>>>>>>>>>>Inside Arrears>>>>>>>>>>>>>>>>>");
						//if(b.getReceipt_no()==null || "".equalsIgnoreCase(b.getReceipt_no())){
						if(b.getReceipt_date()==null){
							arrears=b.getNet_amount();
						}else{
							arrears=b.getClose_balance()==null?0:b.getClose_balance();
						}
						
						
						if(b.getMc_status()!=null){
							
							if(b.getMc_status()==18 || b.getMc_status()==19){
								b.setWater_charges(0.0);
								b.setSw_charges(0.0);
								b.setMc_status(ble.getMc_status());
								waterCharges=0;
								sewageCharges=0;
								
							}
						}
					}
					ble.setArrears(arrears);
					ble.setInterest((double) 0);
					ble.setLatefee((double) 0);
					ble.setNet_amount(waterCharges+sewageCharges+arrears);
					ble.setMcunits((double) 0);
					ble.setPenalty(0.0);
					ble.setPrevious_read_date(null);
					ble.setUser_id(userName);
					ble.setRebate((double) 0);
					ble.setTotalamt((double) 0);
					ble.setSuspense((double) 0);
					ble.setWardno(cs.getWard_no());
					ble.setSw_charges(sewageCharges);
					ble.setOther(0.0);
					ble.setSitecode(cs.getBranch());
					ble.setSundry_amount((double) 0);
					ble.setAvg_units((double) 0);
					ble.setDl_count(0);
					ble.setMth_dl_count(0);
				    ble.setMth_dl_units((double) 0);
				    ble.setDl_units((double) 0);
				    ble.setAdded_by(userName);
				    ble.setDr_amount((double) 0);
				    ble.setMtr_rent(0.0);
				    ble.setOpen_balance(0.0);
				    ble.setExcess_charges(0.0);
				    ble.setAdditional_charges(0.0);
				    ble.setMinimum_charges(minimum_charges);
				    ble.setBill_date(new Date());
				    ble.setRdng_date(new Date());
				    ble.setRdng_date_nep(nepalidate);
				    ble.setBill_date_nep(nepalidate);
				    ble.setPipe_size(cs.getPipe_size());
				    
				    if(cs.getPipe_size()==0.5){
				    	ble.setDenoted_by("SA");
				    }else{
				    	ble.setDenoted_by("THA");
				    }
				    
				    ble.setBillno(monthyearnep+""+i);
				    
				    String newMonth="";
				    if(String.valueOf(cmonth).length()==1){
				    	newMonth="0"+cmonth;
				    }else{
				    	newMonth=""+cmonth;
				    }
				    
				    //System.out.println("NEW MONTH ------"+newMonth);
				    ble.setMonthyear(Integer.parseInt(cyear+""+newMonth));
				    ble.setMonthyearnep(monthyearnep);
				    ble.setCreated_date(new Date());
				    ble.setCashposdate(null);
				    ble.setPcashposdate(null);
				    ble.setCon_category(cs.getCon_category());
				    ble.setCon_type(cs.getCon_type());
				    ble.setEarrears(arrears);
				
				    session.save(ble);
					session.flush();
					session.clear();
				    BsmartWaterLogger.logger.info(consumerMasters.get(i) + " saved successful");
				}
				
				    
				    
				    
			} catch (RuntimeException re) {
				    BsmartWaterLogger.logger.error("saving " + consumerMasters.get(i)+ " failed", re);
				    throw re;
			}
			insertedCount++;
		}
		
		return insertedCount;
	
		
		
		
		
	}

	

	@Override
	public List<?> printBillUnmeteredBilled(String wardNo,String reading_month) {
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.printBillUnmeteredBilled").
				setParameter("wardno", wardNo.toUpperCase().trim()).
				setParameter("reading_month", ""+reading_month).getResultList();
	}
	@Override
	public List<?> printBillGovt(String con_category1,String reading_monthg) {
		try{
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.printBillGovt").
				setParameter("con_category", con_category1.trim().toUpperCase()).
				setParameter("reading_month", ""+reading_monthg).getResultList();
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public BillingLedgerEntity findByConnectionNoByMYN(String connectionNo,String monthyearnep) {
		try{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findByConnectionNoByMYN",BillingLedgerEntity.class).setParameter("connection_no",connectionNo.trim().toUpperCase()).setParameter("monthyearnep", monthyearnep).getSingleResult();
		}catch(Exception e){
			
			return null;
		}
	}

	@Override
	public List<?> getWardWiseBillCountUnMetered(String sitecode) {
		try{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getWardWiseBillCountUnMetered").getResultList();
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public List<?> getConnectionHistoryUn(String wardNo, int value) {
		try{
		if(value==0){
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getConnectionHistoryUnMUnUnbilled").setParameter("wardno", wardNo.toUpperCase()).getResultList();
		}else{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getConnectionHistoryUnMbilled").setParameter("wardno", wardNo.toUpperCase()).getResultList();
		}
		}catch(Exception e){
			e.printStackTrace();
			return null;
			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findByMYN() {
		try{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findByMYN").getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String findObsByConnectionNoByMYN(String connection_no, String monthyearnep) {
		
		try{
		return (String) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findObsByConnectionNoByMYN")
				.setParameter("connection_no", connection_no.toUpperCase().trim()).setParameter("monthyearnep", monthyearnep).getSingleResult();

		}catch(Exception e){
			return null;
		}
	}

	@Override
	public Object[] getMasterStatistics() {
	
		try{
			/*return (Object[]) getCustomEntityManager().createNativeQuery("SELECT nvl(COUNT(CASE when CON_CATEGORY='Domestic' then 1 end),0) as dom,"
			+ "nvl(COUNT(CASE when CON_CATEGORY='Government' then 1 end),0) as gov,"
			+ "	nvl(COUNT(CASE when CON_CATEGORY='Industry and Company' then 1 end),0) as indcom,"
			+ "	nvl(COUNT(CASE when CON_TYPE='Metered' then 1 end),0) as met,"
			+ "	nvl(COUNT(CASE when CON_TYPE='Unmetered' then 1 end),0) as unm,"
			+ "	nvl(COUNT(CASE when PIPE_SIZE=0.5 then 1 end),0) as p05,"
			+ "	nvl(COUNT(CASE when PIPE_SIZE=0.75 then 1 end),0) as p075,"
			+ "	nvl(COUNT(CASE when PIPE_SIZE=1 then 1 end),0) as p1,"
			+ "	nvl(COUNT(CASE when PIPE_SIZE=1.5 then 1 end),0) as p15,"
			+ "	nvl(COUNT(CASE when PIPE_SIZE=2 then 1 end),0) as p2,"
			+ " nvl(COUNT(CASE when PIPE_SIZE=3 then 1 end),0) as p3,"
			+ " nvl(COUNT(CASE when PIPE_SIZE=4 then 1 end),0) as p4,nvl(COUNT(CASE when PIPE_SIZE NOT IN(0.5,0.75,1,1.5,2,3,4) then 1 end),0) as p5,"
			+ " COUNT(*),nvl(COUNT(CASE when CON_SATATUS='Normal' then 1 end),0) as pno,nvl(COUNT(CASE when CON_SATATUS='Temporary' then 1 end),0) as ptem,"
			+ " nvl(COUNT(CASE when CON_SATATUS='Permanent' then 1 end),0) as pper,nvl(COUNT(CASE when CON_SATATUS='Defaulter' then 1 end),0) as pdef,"
			+ " nvl(COUNT(CASE when CON_SATATUS NOT IN('Normal','Temporary','Permanent','Defaulter') then 1 end),0) as pcother,nvl(COUNT(CASE when STATUS='0' then 1 end),0) as wap,nvl(COUNT(CASE when STATUS='1' then 1 end),0) as app FROM BSW_MASTER").getSingleResult();*/
			
			return (Object[]) getCustomEntityManager().createNativeQuery("SELECT nvl(COUNT(CASE when CON_CATEGORY='Domestic' then 1 end),0) as dom,"
					+ "nvl(COUNT(CASE when CON_CATEGORY='Government' then 1 end),0) as gov,"
					+ "	nvl(COUNT(CASE when CON_CATEGORY='Industry and Company' then 1 end),0) as indcom,"
					+ "	nvl(COUNT(CASE when CON_TYPE='Metered' then 1 end),0) as met,"
					+ "	nvl(COUNT(CASE when CON_TYPE='Unmetered' then 1 end),0) as unm,"
					+ "	nvl(COUNT(CASE when PIPE_SIZE=0.5 then 1 end),0) as p05,"
					+ "	nvl(COUNT(CASE when PIPE_SIZE=0.75 then 1 end),0) as p075,"
					+ "	nvl(COUNT(CASE when PIPE_SIZE=1 then 1 end),0) as p1,"
					+ "	nvl(COUNT(CASE when PIPE_SIZE=1.5 then 1 end),0) as p15,"
					+ "	nvl(COUNT(CASE when PIPE_SIZE=2 then 1 end),0) as p2,"
					+ " nvl(COUNT(CASE when PIPE_SIZE=3 then 1 end),0) as p3,"
					+ " nvl(COUNT(CASE when PIPE_SIZE=4 then 1 end),0) as p4,nvl(COUNT(CASE when PIPE_SIZE NOT IN(0.5,0.75,1,1.5,2,3,4) then 1 end),0) as p5,"
					+ " COUNT(*),nvl(COUNT(CASE when CON_SATATUS='Normal' then 1 end),0) as pno,nvl(COUNT(CASE when CON_SATATUS='Temporary' then 1 end),0) as ptem,"
					+ " nvl(COUNT(CASE when CON_SATATUS='Permanent' then 1 end),0) as pper,nvl(COUNT(CASE when CON_SATATUS='Defaulter' then 1 end),0) as pdef,"
					+ " nvl(COUNT(CASE when CON_SATATUS NOT IN('Normal','Temporary','Permanent','Defaulter','Processing in New Connection') then 1 end),0) as pcother,nvl(COUNT(CASE when STATUS='0' then 1 end),0) as wap,nvl(COUNT(CASE when STATUS='1' then 1 end),0) as app FROM BSW_MASTER WHERE CONNECTION_NO NOT IN(SELECT CONNECTION_NO FROM BSW_MASTER WHERE \"LENGTH\"(CONNECTION_NO)=10 AND CON_SATATUS ='Processing in New Connection' AND STATUS='0')  ").getSingleResult();
					
		}catch(Exception e){
			return null;
		}
	
	}


	@Override
	public List<?> findBillsByReceiptNullByMonthYear(String connectionNo,
			int yearmntfr, int yearmntto) {
		
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findBillsByReceiptNullByMonthYear").setParameter("connection_no", connectionNo.trim().toUpperCase())
				.setParameter("yearmntfr", yearmntfr).setParameter("yearmntto",yearmntto ).getResultList();

	}


	@Override
	public long checkLedgerLatestExists(String connectionno) {
		try{
		return (long) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.checkLedgerLatestExists").setParameter("connection_no", connectionno).getSingleResult();
		}catch(Exception e){
		return 0;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<BillingLedgerEntity> findBillsByReceiptNullAllMYN(String bConnectionNo, Integer tomonthyear) {
		
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findBillsByReceiptNullAllMYN").setParameter("connection_no", bConnectionNo.trim().toUpperCase()).setParameter("monthyearnep", tomonthyear).getResultList();

	}


	@Override
	public int getMaxMonthYearNepali() {
		try{
			String mynep= getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getMaxMonthYearNepali",String.class).getSingleResult();
		return Integer.parseInt(mynep);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Object[] getDoorLockCount(String connectionNo) {
		try{
			return (Object[]) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getDoorLockCount").setParameter("connection_no", connectionNo).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingLedgerEntity> getLedgerDataByConnNumNew(String connId,String schemaName) {
		List<BillingLedgerEntity> ledger = null;
		try {
			ledger = getCustomEntityManager(schemaName).createNamedQuery("BillingLedgerEntity.getLedgerDataByConnectionNum").setParameter("connId", connId.trim().toUpperCase()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ledger;
	}

	@Override
	public Object[] getOpeningBalanceByConNo(String connectionNo) {
		try{
			return (Object[]) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getOpeningBalanceByConNo").setParameter("connection_no", connectionNo.trim().toUpperCase()).getSingleResult();
			}catch(Exception e){
				return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> updateBulkBillingUnmetered(String monthyearnep) {
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.updateBulkBillingUnmeteredMY").setParameter("monthyearnep", monthyearnep).getResultList();
	}

	@Override
	public List<?> getLedgerDataByWardMonthEndSubmitManual(String monthyearnep,String fromdate, String todate) {
		
		List<?> list=null;
		String sql="SELECT A.WARDNO,A.DENOTED_BY,A.CON_TYPE,A.CON_CATEGORY,nvl(A.OPB,0) as OB,nvl(A.WATER_CHARGES1,0) as WC,nvl(A.SW_CHARGES1,0) as SWC,nvl(A.MTR_RENT1,0) as MR,nvl(B.misc1,0),"
					+" nvl(B.penalty1,0),nvl(B.rebate1,0),nvl(B.ramount1,0),NVL(B.adv1,0) FROM "
					
					+"(SELECT l.WARDNO,L.DENOTED_BY,m.CON_CATEGORY,m.CON_TYPE,nvl(SUM(l.ARREARS),0) AS OPB,"
					+"nvl(SUM(l.WATER_CHARGES),0) AS WATER_CHARGES1,nvl(SUM(l.SW_CHARGES),0) AS SW_CHARGES1,"
					+"nvl(SUM(l.MTR_RENT),0) AS MTR_RENT1 "
					+" from BSW_LEDGER l,BSW_MASTER m WHERE m.CONNECTION_NO=l.CONNECTION_NO AND l.MONTHYEARNEP="+monthyearnep+" "
					+"GROUP BY l.WARDNO,L.DENOTED_BY,m.CON_CATEGORY,m.CON_TYPE)A "

					+"FULL OUTER JOIN (SELECT m.WARD_NO,m.DENOTED_BY,l.CON_CATEGORY,l.CON_TYPE,nvl(SUM(m.MISCELLANEOUS_COST),0) AS misc1,"
					+"nvl(SUM(m.PENALTY),0) AS penalty1,nvl(SUM(m.REBATE),0) AS REBATE1,nvl(SUM(m.AMOUNT),0) AS ramount1,nvl(SUM(m.ADVANCE),0) AS adv1 "
					+"from BSW_MASTER l,BSW_PAYMENTS m WHERE m.CONNECTION_NO=l.CONNECTION_NO AND trunc(m.RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy') AND trunc(m.RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy') AND m.TOWARDS='BILL PAYMENT'"
					+ "GROUP BY m.WARD_NO,m.DENOTED_BY,l.CON_CATEGORY,l.CON_TYPE)B ON A.WARDNO=B.WARD_NO AND A.CON_CATEGORY=B.CON_CATEGORY AND A.DENOTED_BY=B.DENOTED_BY"
					+" AND A.CON_TYPE=B.CON_TYPE";


		list=getCustomEntityManager().createNativeQuery(sql).getResultList();
		return list;
	}

	@Override
	public BillingLedgerEntity findLatestLedgerBYCNSchema(String connectionno,String testLocation) {
		try{
		return getCustomEntityManager(testLocation).createNamedQuery("BillingLedgerEntity.findLatestLedgerBYCNSchema",BillingLedgerEntity.class).setParameter("connection_no", connectionno.trim().toUpperCase()).getSingleResult();//.setMaxResults(1)
		}catch(Exception e){
			e.printStackTrace();
			return null;
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingLedgerEntity> findBillsFromMaxReceiptNo(String connectionno,String testLocation) {
		return getCustomEntityManager(testLocation).createNamedQuery("BillingLedgerEntity.findBillsFromMaxReceiptNo").setParameter("connection_no", connectionno.trim().toUpperCase()).getResultList();

	}

	@Override
	public BillingLedgerEntity findLastMonthRecordByConNo(String connectionNo) {
		try{
		return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findLastMonthRecordByConNo",BillingLedgerEntity.class).setParameter("connection_no", connectionNo.trim().toUpperCase()).getSingleResult();
		}catch(Exception e){
			return null;
			
		}
	}

	@Override
	public BillingLedgerEntity findRecordByConNoMYN(String connection_no,Integer monthyearnep) {
		try{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.findRecordByConNoMYN",BillingLedgerEntity.class).setParameter("connection_no", connection_no.trim().toUpperCase()).setParameter("monthyearnep", ""+monthyearnep).getSingleResult();
			}catch(Exception e){
				e.printStackTrace();
				return null;
				
		}
	}

	@Override
	public List<?> getconnectionMasterLedgerDetails(String conNo) {
		try{
			String sql = "select m.NAME_ENG,m.AREA_NO,m.PIPE_SIZE, m.CON_CATEGORY, l.ARREARS, l.WATER_CHARGES, "

					+ "l.SW_CHARGES, l.MTR_RENT,l.NET_AMOUNT,l.MONTHYEARNEP,m.BALANCE,m.CON_TYPE,m.WARD_NO,m.DENOTED_BY,m.CONNECTION_NO FROM BSW_MASTER m, BSW_LEDGER l WHERE m.CONNECTION_NO=l.CONNECTION_NO AND "

					+ "UPPER(l.CONNECTION_NO)=UPPER('"+conNo+"') AND MONTHYEARNEP = (SELECT MAX(MONTHYEARNEP) FROM BSW_LEDGER WHERE UPPER(CONNECTION_NO)=UPPER('"+conNo+"') AND BILLNO IS NOT NULL)";
			
			return getCustomEntityManager().createNativeQuery(sql).getResultList();
			}catch(Exception e){
				return null;
				
		}
	}

	@Override
	public BillingLedgerEntity getLatestBillNotNull(String connection_no) {
		try{
			return getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getLatestBillNotNull",BillingLedgerEntity.class).setParameter("connection_no", connection_no.trim().toUpperCase()).getSingleResult();
		}catch(Exception e){
			return null;
		}
		
	}

	@Override
	public double getArrearsByConNoBM(String conNo) {
		try {
			double m= (Double) getCustomEntityManager().createNamedQuery("BillingLedgerEntity.getArrearsByConNoBM").setParameter("connection_no", conNo.trim().toUpperCase()).getSingleResult();
			return m;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long checkBillGenerationPending(String monthyear) {
		try {
			/*String qry="SELECT COUNT(*) FROM BSW_MASTER WHERE CON_SATATUS NOT IN ('Permanent') AND CONNECTION_NO NOT IN (SELECT	CONNECTION_NO FROM BSW_LEDGER WHERE	MONTHYEARNEP ="+monthyear+")";
			String qry1="SELECT COUNT(*) FROM BSW_MASTER WHERE CON_SATATUS NOT IN('Permanent') AND UPPER(CONNECTION_NO) IN ( SELECT UPPER(CONNECTION_NO) FROM BSW_LEDGER WHERE MONTHYEARNEP="+monthyear+" AND BILLNO IS NULL)";*/
			
			String qry="SELECT COUNT(*) FROM BSW_MASTER WHERE CON_SATATUS NOT IN ('Permanent','Processing in New Connection') AND CONNECTION_NO NOT IN (SELECT	CONNECTION_NO FROM BSW_LEDGER WHERE	MONTHYEARNEP ="+monthyear+")";
			String qry1="SELECT COUNT(*) FROM BSW_MASTER WHERE CON_SATATUS NOT IN('Permanent','Processing in New Connection') AND UPPER(CONNECTION_NO) IN ( SELECT UPPER(CONNECTION_NO) FROM BSW_LEDGER WHERE MONTHYEARNEP="+monthyear+" AND BILLNO IS NULL)";
			
			
			BigDecimal b= (BigDecimal) getCustomEntityManager().createNativeQuery(qry).getSingleResult();
			BigDecimal b1= (BigDecimal) getCustomEntityManager().createNativeQuery(qry1).getSingleResult();
			System.out.println("count bill pending====="+b);
			long m1=b.longValue();
			long m2=b1.longValue();
			long m=m1+m2;
			return m;
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<?> getLedgerDataByWardAdvanceSubmit(String monthyear, String fromdate, String todate) {


		int year=Integer.parseInt(monthyear.substring(0, 4));
		int month=Integer.parseInt(monthyear.substring(4, 6));
		//System.out.println(month+"----month");
		
		if(month==1){
			month=12;
			year=year-1;
		}else{
			month=month-1;
		}
		
		String month1=""+month;
		
		if(month1.length()==1){
			month1="0"+month;
		}
		//System.out.println(month1+"----month1");
		
		int monthyearp=Integer.parseInt(year+""+month1);
		
		List<?> list=null;
		try
		{
			
			
			String sql="SELECT BWARD, BDEN,NVL(FCBAL,0),NVL(BWC,0),NVL(BSWC,0),NVL(PMISC,0),NVL(BMR,0),NVL(ADDPEN,0),NVL(PPEN,0),NVL(PBLA,0),NVL(PPA,0),NVL(PRA,0),NVL(PREB,0),NVL(PADV,0), NVL(PA,0), BCT, BCC,"
						+" FWARD, FDEN, FCT, FCC, PWARD, PDEN, PCT ,PCC,NVL(PSNG,0),NVL(SUSP,0),NVL(WOP,0),NVL(AFP,0)"
						+" FROM"
						+" (SELECT BWC,BSWC, BMR, BWARD, BDEN, BCT, BCC, PWARD, PDEN, PCT ,PCC, PMISC, PPEN, PBLA, PPA, PRA, PREB, PADV, PA,ADDPEN,PSNG,SUSP,WOP,AFP FROM"
						+" (SELECT SUM(nvl(l.WATER_CHARGES,0)) AS BWC,SUM(nvl(l.SW_CHARGES,0)) AS BSWC,SUM(nvl(l.MTR_RENT,0)) AS BMR,"
						+" (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END) AS BWARD,m.DENOTED_BY AS BDEN,"
						+" m.CON_TYPE AS BCT,m.CON_CATEGORY AS BCC,SUM(nvl(l.DR_AMOUNT,0)),SUM(nvl(l.DR_AMOUNT,0)) as ADDPEN,SUM(nvl(l.SUNDRY_AMOUNT,0)) as PSNG,SUM(nvl(l.SUSPENSE,0)) as SUSP,SUM(CASE WHEN l.LAST_PAID_AMOUNT IS NULL AND l.RECEIPT_NO IS NULL AND l.MONTHYEARNEP="+monthyear+" THEN l.SERVICE_CHARGE END) AS WOP,SUM(CASE WHEN l.LAST_PAID_AMOUNT IS NOT NULL AND l.RECEIPT_NO IS NOT NULL AND l.MONTHYEARNEP="+monthyear+"  AND l.REMARKS LIKE 'Correction approved after payment%' THEN l.SERVICE_CHARGE END) AS AFP FROM BSW_LEDGER l,BSW_MASTER m"
						+" WHERE MONTHYEARNEP="+monthyear+" AND m.CONNECTION_NO=l.CONNECTION_NO GROUP BY"
						+" (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END),m.DENOTED_BY,m.CON_CATEGORY,m.CON_TYPE ORDER BY m.CON_TYPE)A"
						
						+" FULL OUTER JOIN"
						
						+" (SELECT (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END) AS PWARD,p.DENOTED_BY AS PDEN,m.CON_TYPE AS PCT,"
						+" m.CON_CATEGORY AS PCC,SUM(nvl(p.MISCELLANEOUS_COST,0)) AS PMISC,SUM(nvl(p.PENALTY,0)) AS PPEN,"
						+" SUM(nvl(p.BILL_ADJ_AMOUNT,0)) AS PBLA,"
						+" SUM(nvl(p.PENALTY_ADJ_AMOUNT,0)) AS PPA,SUM(nvl(p.AMOUNT,0)) AS PRA,SUM(nvl(p.REBATE,0)) AS PREB,SUM(nvl(p.ADVANCE,0)) AS PADV,"
						+" SUM(nvl(p.ADVANCE_REBATE,0)) AS PA FROM BSW_PAYMENTS p,BSW_MASTER m"
						+" WHERE trunc(RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy') AND trunc(RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy')"
						+" AND UPPER(M .CONNECTION_NO) = UPPER(P .CONNECTION_NO) AND p.CANCELLEDREMARKS IS NULL AND UPPER(p.TOWARDS)='BILL PAYMENT' GROUP BY"
						+" (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END),p.DENOTED_BY,m.CON_TYPE,m.CON_CATEGORY ORDER BY m.CON_TYPE)B"
						
						+" ON A.BWARD=B.PWARD AND A.BDEN=B.PDEN AND A.BCT=B.PCT AND A.BCC=B.PCC)C"
						+" FULL OUTER JOIN "
						+" (SELECT b.WARD_NO as FWARD,b.DENOTED_BY AS FDEN,b.CON_TYPE AS FCT,b.CON_CATEGORY AS FCC,"
						+" SUM(NVL(b.CLOSING_BAL,0)) AS FCBAL FROM FISCAL_YEAR_ADVANCE_BALANCE b WHERE B.FISCAL_YEAR_MONTH="+monthyearp+" GROUP BY"
						+" b.WARD_NO,b.DENOTED_BY,b.CON_TYPE,b.CON_CATEGORY ORDER BY b.CON_TYPE ASC)D"
						+" ON C.BWARD=D.FWARD AND C.BDEN=D.FDEN AND C.BCT=D.FCT AND C.BCC=D.FCC";
		
			//System.out.println("SQL***********"+sql);
			
			list=getCustomEntityManager().createNativeQuery(sql).getResultList();
		
		
		}
		
		
		
		
		
		
		
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		return list;
		
		
	}

	@Override
	public BillingLedgerEntity getLatestRecordByConnectionNo(String conNo, String branch) {
		try{
			return getCustomEntityManager(branch).createNamedQuery("BillingLedgerEntity.getLatestRecordByConnectionNo",BillingLedgerEntity.class).setParameter("connection_no", conNo.trim().toUpperCase()).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void update(BillingLedgerEntity billingLedgerEntity, String branch) {
		try {
			Session session = getCustomEntityManager(branch).unwrap(Session.class);
			session.update(billingLedgerEntity);
			session.flush();
			session.clear();
		} catch (HibernateException e) {
			throw e;
		}
		
	}

	
		
	
	
}


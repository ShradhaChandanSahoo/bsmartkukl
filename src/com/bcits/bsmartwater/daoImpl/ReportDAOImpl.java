package com.bcits.bsmartwater.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.bcits.bsmartwater.dao.ReportDAO;
import com.bcits.bsmartwater.model.DailyCashCounterReport;
import com.bcits.bsmartwater.model.FiscalYearAdvanceBalance;
import com.bcits.bsmartwater.model.MeterChangeDetailsEntity;
import com.bcits.bsmartwater.utils.DataSourceRequest;
import com.bcits.bsmartwater.utils.DataSourceRequest.GroupDescriptor;
import com.bcits.bsmartwater.utils.DataSourceResult;

@Repository
public class ReportDAOImpl extends GenericDAOImpl<DailyCashCounterReport> implements ReportDAO {

	@Override
	public DataSourceResult dailyCashCounterRead(DataSourceRequest request) {
		Session session = getCustomEntityManager().unwrap(Session.class);
		Class<?> clazz = DailyCashCounterReport.class;
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.isNotNull("counterno"));
		DataSourceResult dataSourceResult = request.toDataSourceResult(criteria,clazz,session);
		List<GroupDescriptor> groups = request.getGroup();  
		if (groups != null && !groups.isEmpty()) {        	
			dataSourceResult.setData(request.group(dataItems(criteria), session,clazz));
		} else {
			dataSourceResult.setData(dataItems(criteria));
		}
		return dataSourceResult;
	}
	@SuppressWarnings("unchecked")
	private List<DailyCashCounterReport> dataItems(Criteria criteria){
		List<DailyCashCounterReport> data = (List<DailyCashCounterReport>)criteria.list();
		return data;
	}
	@Override
	public List<?> categoryWiseSalesReportReadData(Date fromdate, Date todate,String mnthyrnep) {
		return getCustomEntityManager().createNamedQuery("DailyCashCounterReport.categoryWiseSalesReportReadData").setParameter("monthyearnep", mnthyrnep).getResultList();
	}
	
	@Override
	public List<?> wardWiseSalesReportReadData(String fromdate, String todate) 
	{
		System.out.print("<=====Inside wardWiseSalesReportReadData=== fromdate ====="+fromdate+"=== todate ==="+todate);
		return getCustomEntityManager().createNamedQuery("DailyCashCounterReport.wardWiseSalesReportReadData").setParameter("fromdate", fromdate).setParameter("todate", todate).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> monthlyObservationReportReadData(String yearnep, String month,Model model) 
	{
		//System.out.println("<=====Inside monthlyObservationReportReadData=== yearnep ====="+yearnep+"=== month ==="+month);
		 List<Object[]> list2=null;
		 Object ob="";
		 Object wd="";
		 String cn="";
		 String cn1="";
		 
		 String myn=yearnep+""+month;
		 
		 List<String> saThaList=new ArrayList<String>();
		 
		 /*String sql2="SELECT DISTINCT(l.WARDNO) "
						+ "FROM OBSERVATION_MASTER o,BSW_LEDGER l,BSW_MASTER m "
						+ "WHERE o.ID=l.MC_STATUS and l.CONNECTION_NO=m.CONNECTION_NO "; */
		 
		 String sql2="SELECT m.WARD_NO,l.MC_STATUS,COUNT(CASE WHEN l.PIPE_SIZE=0.5 THEN 1 END),"
		 		+ "COUNT(CASE WHEN l.PIPE_SIZE>0.5 THEN 1 END),m.CON_TYPE FROM BSW_MASTER M,BSW_LEDGER L "
		 		+ "WHERE M.CONNECTION_NO=L.CONNECTION_NO AND L.BILLNO IS NOT NULL "
		 		+ " AND MONTHYEARNEP="+myn+" GROUP BY m.WARD_NO,m.CON_TYPE,l.MC_STATUS ORDER BY m.WARD_NO ASC";
		 //System.out.println("<=====Inside monthlyObservationReportReadData=== sql2 ====="+sql2);
		 list2=getCustomEntityManager().createNativeQuery(sql2).getResultList();
		 
		 
		 
		 /*System.out.println("<=====Inside monthlyObservationReportReadData=== sql2 ====="+sql2);
		 String value="";
		 for (int i = 0; i < list2.size(); i++)
			{
				//Object data=list2.get(i);
				wd=list2.get(i)+"";
				for (int j = 0; j < data.length; j++) 
				{
					if(j==0)
					{
						wd=data[j]+"";
					}
				}
				String saValue=wd+"SA";
				System.out.println("===saValue==="+saValue);
				saThaList.add(saValue);
				String thaValue=wd+"THA";
				System.out.println("===thaValue==="+thaValue);
				saThaList.add(thaValue);
				cn="count(CASE WHEN l.WARDNO='"+wd+"' AND m.PIPE_SIZE=0.5 THEN 0 END) as"+"\"" + saValue + "\",";
				System.out.println("===cn==="+cn);
			 	cn1="count(CASE WHEN l.WARDNO='"+wd+"' AND m.PIPE_SIZE>0.5 THEN 0 END) as"+"\"" + thaValue + "\",";
			 	System.out.println("===cn1==="+cn1);
			 	value+=cn+cn1;
			}
		 
		 value = value.replaceAll(",$", "");
		 
		 System.out.println("==value=="+value);
		String sql="SELECT DISTINCT(o.OBSERVATION_NAME),"+value+" "
					+ "FROM OBSERVATION_MASTER o,BSW_LEDGER l,BSW_MASTER m "
					+ "WHERE o.ID=l.MC_STATUS and l.CONNECTION_NO=m.CONNECTION_NO "
					+ "and l.MONTHYEARNEP BETWEEN '"+fromdate+"' and '"+todate+"'  "
					+ "GROUP BY o.OBSERVATION_NAME";
		System.out.print("<=====Inside monthlyObservationReportReadData=== sql ====="+sql);
		list2=getCustomEntityManager().createNativeQuery(sql).getResultList();
		
		model.addAttribute("saThaList",saThaList);
		model.addAttribute("list",list2);
		model.addAttribute("length",list2.get(0).length);*/
		//return getCustomEntityManager().createNamedQuery("DailyCashCounterReport.monthlyObservationReportReadData").setParameter("fromdate", fromdate).setParameter("todate", todate).getResultList();
		return list2;
	}
	
	@Override
	public List<?> wardWiseMrReaderReportRead(String yearnep, String month) {
		//System.out.println("<=====Inside wardWiseMrReaderReportRead=== yearnep ====="+yearnep+"=== month ==="+month);
		 String myn=yearnep+""+month;
		 
		 String sql="SELECT m.WARD_NO,l.MC_STATUS,r.MRNAME,COUNT(MC_STATUS) FROM BSW_MASTER M,BSW_LEDGER L, BSW_METER_READER R "
		 		+ "WHERE M.CONNECTION_NO=L.CONNECTION_NO AND l.MR_ID=r.ID AND L.BILLNO IS NOT NULL  "
		 		+ "AND MONTHYEARNEP="+myn+"  GROUP BY m.WARD_NO,l.MC_STATUS,r.MRNAME ORDER BY m.WARD_NO ASC";
		 //System.out.println("<=====Inside wardWiseMrReaderReportRead=== sql ====="+sql);
		 
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<?> missedBillsReportRead(String yearnep, String month) {
		 String myn=yearnep+""+month;
		/* String sql="SELECT DISTINCT CONNECTION_NO,NAME_ENG,WARD_NO,READING_DAY,AREA_NO,PIPE_SIZE,CON_TYPE FROM BSW_MASTER "
		 		+ "WHERE CON_SATATUS NOT IN('Permanent') AND UPPER(CONNECTION_NO) IN ( "
		 		+ "SELECT UPPER(CONNECTION_NO) FROM BSW_LEDGER WHERE MONTHYEARNEP="+myn+" AND BILLNO IS NULL)";*/
		 String sql1="SELECT DISTINCT CONNECTION_NO,NAME_ENG,WARD_NO,READING_DAY,AREA_NO,PIPE_SIZE,CON_TYPE FROM BSW_MASTER "
		 		+ "WHERE CON_SATATUS NOT IN('Permanent') AND CON_SATATUS NOT IN('Processing in New Connection') AND UPPER(CONNECTION_NO) IN ( "
		 		+ "SELECT UPPER(CONNECTION_NO) FROM BSW_LEDGER WHERE MONTHYEARNEP="+myn+" AND BILLNO IS NULL)";
		 
		return getCustomEntityManager().createNativeQuery(sql1).getResultList();
	}
	@Override
	public List<?> cashCollectiontthRead(String fromdate, String todate) {
		
		String sql ="SELECT C.RECEIPT_NO,C.RECEIPT_DATE,C.NAME_ENG,A.CONNECTION_NO,A.N1,B.N2, "
				+ "C.N3,C.PENL,NVL(C.LAST_PAID_AMOUNT, 0) FROM (SELECT CONNECTION_NO,NET_AMOUNT AS N1  "
				+ "FROM BSW_LEDGER WHERE CONNECTION_NO IN(SELECT CONNECTION_NO FROM BSW_PAYMENTS  "
				+ "WHERE TRUNC(RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy') AND TRUNC(RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy')  "
				+ "AND AMOUNT>=10000) AND MONTHYEARNEP=206410)A,(SELECT CONNECTION_NO,NET_AMOUNT AS N2 FROM BSW_LEDGER "
				+ "WHERE CONNECTION_NO IN(SELECT CONNECTION_NO FROM BSW_PAYMENTS WHERE TRUNC(RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy')  "
				+ "AND TRUNC(RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy') AND AMOUNT>=10000) AND MONTHYEARNEP=207209)B, "
				+ "(SELECT L.CONNECTION_NO,L.NET_AMOUNT AS N3,(NVL(L.PENALTY,0)+NVL(L.PENALTY_ADJ_AMOUNT,0))  "
				+ "AS penl,L.RECEIPT_NO,L.RECEIPT_DATE,M.NAME_ENG,L.LAST_PAID_AMOUNT FROM BSW_LEDGER L,BSW_MASTER M WHERE (L.CONNECTION_NO,L.MONTHYEARNEP)  "
				+ "IN(SELECT CONNECTION_NO,MAX(TO_MON_YEAR) FROM BSW_PAYMENTS WHERE TRUNC(RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy') "
				+ " AND TRUNC(RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy') AND AMOUNT>=10000 GROUP BY CONNECTION_NO)  "
				+ "AND M.CONNECTION_NO=L.CONNECTION_NO)C WHERE A.CONNECTION_NO=B.CONNECTION_NO AND B.CONNECTION_NO=C.CONNECTION_NO  "
				+ "AND  C.CONNECTION_NO=A.CONNECTION_NO";
		 
		//System.out.println("====CashCollectiontt"+sql);		 
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	
	@Override
	public List<?> missedBillsReportLedger(String yearnep, String month) {
		 
		 String myn=yearnep+""+month;
	/*	 String sql="SELECT CONNECTION_NO,NAME_ENG,WARD_NO,READING_DAY,AREA_NO,PIPE_SIZE,CON_TYPE FROM BSW_MASTER "
		 		+ "WHERE CON_SATATUS NOT IN('Permanent') AND CONNECTION_NO NOT IN ( "
		 		+ "SELECT CONNECTION_NO FROM BSW_LEDGER WHERE MONTHYEARNEP="+myn+")";*/
		 
		 String sql1="SELECT CONNECTION_NO,NAME_ENG,WARD_NO,READING_DAY,AREA_NO,PIPE_SIZE,CON_TYPE FROM BSW_MASTER "
		 		+ "WHERE CON_SATATUS NOT IN('Permanent') AND CON_SATATUS NOT IN('Processing in New Connection') AND CONNECTION_NO NOT IN ( "
		 		+ "SELECT CONNECTION_NO FROM BSW_LEDGER WHERE MONTHYEARNEP="+myn+")";
		//System.out.println(sql);		 
		return getCustomEntityManager().createNativeQuery(sql1).getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> monthlySalesSummaryReportReadData(String fromdate,String todate,String fdate, String tdate) 
	{
		System.out.print("<=====Inside wardWiseSalesReportReadData=== fromdate ====="+fromdate+"=== todate ==="+todate);
		 
		List<Object[]> list2=null;
		 
		 
		 /*String sql="SELECT A.WARDNO,A.PIPE_SIZE,A.openingBalance,A.WATER_CHARGES1,A.SW_CHARGES1,A.MTR_RENT1,A.TotalBill,A.NET_AMOUNT1,B.misc1,B.penalty1,B.rebate1,B.ramount1,B.WARD_NO FROM " +
				"(SELECT l.WARDNO,m.pipe_size,nvl(SUM(l.ARREARS),0) AS openingBalance,nvl(SUM(l.WATER_CHARGES),0) AS WATER_CHARGES1,nvl(SUM(l.SW_CHARGES),0) " +
				"AS SW_CHARGES1,nvl(SUM(l.MTR_RENT),0) AS MTR_RENT1, " +
				"nvl(SUM(l.WATER_CHARGES),0)+nvl(SUM(l.SW_CHARGES),0)+nvl(SUM(l.MTR_RENT),0) as TotalBill, " +
				"nvl(SUM(l.NET_AMOUNT),0) AS NET_AMOUNT1 from BSW_LEDGER l,BSW_MASTER m WHERE m.CONNECTION_NO=l.CONNECTION_NO AND l.MONTHYEARNEP="+fromdate+" " +
				"GROUP BY l.WARDNO,m.pipe_size)A, " +
				"(SELECT m.WARD_NO,m.pipe_size,nvl(SUM(m.MISCELLANEOUS_COST),0) AS misc1, " +
				"nvl(SUM(m.PENALTY),0) AS penalty1,nvl(SUM(m.REBATE),0) AS REBATE1,nvl(SUM(m.AMOUNT),0) AS ramount1 " +
				"from BSW_LEDGER l,BSW_PAYMENTS m WHERE m.CONNECTION_NO=l.CONNECTION_NO  " +
				" and l.MONTHYEARNEP="+fromdate+" AND trunc(m.RDATE)>=TO_DATE('"+fdate+"', 'dd-MM-yyyy') AND trunc(m.RDATE)<=TO_DATE('"+tdate+"', 'dd-MM-yyyy')" +
				"GROUP BY m.WARD_NO,m.pipe_size)B WHERE A.WARDNO=B.WARD_NO AND A.PIPE_SIZE=B.PIPE_SIZE";*/
		 
		String sql="SELECT (A.WARDNO || A.DENOTED_BY) AS wardnum,'',nvl(A.openingBalance,0),nvl(A.WATER_CHARGES1,0),nvl(A.SW_CHARGES1,0),nvl(A.MTR_RENT1,0),nvl(A.TotalBill,0),nvl(A.NET_AMOUNT1,0),nvl(B.misc1,0),"
				+"nvl(B.penalty1,0),nvl(B.rebate1,0),nvl(B.ramount1,0),B.WARD_NO,NVL(B.adv1,0) FROM "
				+"(SELECT l.WARDNO,L.DENOTED_BY,nvl(SUM(l.ARREARS),0) AS openingBalance,"
				+"nvl(SUM(l.WATER_CHARGES),0) AS WATER_CHARGES1,nvl(SUM(l.SW_CHARGES),0) AS SW_CHARGES1,nvl(SUM(l.MTR_RENT),0) AS MTR_RENT1, "
				+"nvl(SUM(l.WATER_CHARGES),0)+nvl(SUM(l.SW_CHARGES),0)+nvl(SUM(l.MTR_RENT),0) as TotalBill, nvl(SUM(l.NET_AMOUNT),0) "
				+"AS NET_AMOUNT1 from BSW_LEDGER l,BSW_MASTER m WHERE m.CONNECTION_NO=l.CONNECTION_NO AND l.MONTHYEARNEP="+fromdate+" "
				+"GROUP BY l.WARDNO,L.DENOTED_BY)A LEFT OUTER JOIN (SELECT m.WARD_NO,m.DENOTED_BY,nvl(SUM(m.MISCELLANEOUS_COST),0) AS misc1, nvl(SUM(m.PENALTY),0) "
				+"AS penalty1,nvl(SUM(m.REBATE),0) AS REBATE1,nvl(SUM(m.AMOUNT),0) AS ramount1,nvl(SUM(m.ADVANCE),0) AS adv1 from BSW_LEDGER l,BSW_PAYMENTS m "
				+"WHERE m.CONNECTION_NO=l.CONNECTION_NO  and l.MONTHYEARNEP="+fromdate+" AND trunc(m.RDATE)>=TO_DATE('"+fdate+"', 'dd-MM-yyyy') "
				+"AND trunc(m.RDATE)<=TO_DATE('"+tdate+"', 'dd-MM-yyyy')GROUP BY m.WARD_NO,m.DENOTED_BY)B ON A.WARDNO=B.WARD_NO AND "
				+"A.DENOTED_BY=B.DENOTED_BY";
		 
	
		
	System.out.print("<=====Inside monthlyObservationReportReadData=== sql ====="+sql);
	list2=getCustomEntityManager().createNativeQuery(sql).getResultList();
	
	return list2;
		
		//return getCustomEntityManager().createNamedQuery("DailyCashCounterReport.monthlySalesSummaryReportReadData").setParameter("fromdate", fromdate).setParameter("todate", todate).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> detailedCashCollReportReadData(Date fromdate, Date todate) 
	{
		System.out.print("<=====Inside detailedCashCollReportReadData=== fromdate ====="+fromdate+"=== todate ==="+todate);
		 List<Object[]> list2=null;
		String sql="SELECT CONNECTION_NO,AMOUNT,RECEIPT_NO,RDATE,COUNTERNO,TOWARDS,RECORDTYPE,PAY_MODE,CDNO, " +
				"WATER_CHARGES,SW_CHARGES,METER_RENT,MISCELLANEOUS_COST,PENALTY,REBATE,FRECAMOUNT,ADVANCE, " +
				"ADVANCE_REBATE,NVL(OLD_BALANCE,0) FROM BSW_PAYMENTS WHERE " +
				" l.MONTHYEARNEP BETWEEN '"+fromdate+"' and '"+todate+"' " +
			//	"RDATE >=TO_DATE('02-01-2017','dd-MM-yyyy') AND RDATE <=TO_DATE('06-02-2017','dd-MM-yyyy')  " +
				"AND COUNTERNO IS NOT NULL AND COUNTERNO LIKE '1'ORDER BY RECEIPT_NO ASC";
		
		System.out.print("<=====Inside detailedCashCollReportReadData=== sql ====="+sql);
		list2=getCustomEntityManager().createNativeQuery(sql).getResultList();
	
	return list2;
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> detailedCashCollReportReadDataNew(String fromDate, String toDate) 
	{
		try{
		//System.out.println(fromDate+"fromDate");
		//System.out.println(toDate+"toDate");
		
		List<Object[]> list2=null;
		
		 String sql="SELECT CONNECTION_NO,AMOUNT,RECEIPT_NO,RDATE,COUNTERNO,TOWARDS,PAY_MODE,CDNO, " +
				"WATER_CHARGES,SW_CHARGES,METER_RENT,MISCELLANEOUS_COST,(PENALTY+PENALTY_ADJ_AMOUNT),REBATE,FRECAMOUNT,ADVANCE, " +
				"ADVANCE_REBATE,NVL(BALANCE_AMOUNT,0),NVL(OLD_BALANCE,0),USER_ID,NEPALI_DATE FROM BSW_PAYMENTS WHERE " +
				"trunc(RDATE) >=TO_DATE('"+fromDate+"','MM/dd/yyyy') AND trunc(RDATE) <=TO_DATE('"+toDate+"','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS<>'MISCELLANEOUS'" ;
		 //System.out.println(sql);
		 list2=getCustomEntityManager().createNativeQuery(sql).getResultList();
	
	return list2;
	}catch(Exception e){
		e.printStackTrace();
		return null;
		
	}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> detailedMiscCollReportReadData(String fromDate, String toDate) {
		try {
			//System.out.println(fromDate + "fromDate");
			//System.out.println(toDate + "toDate");

			List<Object[]> list2 = null;

			String sql = "SELECT CONNECTION_NO, AMOUNT, RECEIPT_NO, RDATE, REMARKS, COUNTERNO, PAY_MODE, MISCELLANEOUS_COST, "
					+ "ADVANCE, NCTAP, NCDEPOSIT, MVALUE, TEMPHOLEAMT, NAMECHANGEAMT, NC_INST, USER_ID,"
					+ " NVL(CASE WHEN OTHER_PAID_TYPE IN('Hole Change') THEN OTHER_PAID_AMT END,0) AS HC,"
					+ " NVL(CASE WHEN OTHER_PAID_TYPE IN('Hole Maintenance') THEN OTHER_PAID_AMT END,0) AS HM, "
					+ " NVL(CASE WHEN OTHER_PAID_TYPE IN('Application Charge') THEN OTHER_PAID_AMT END,0) AS AC, "
					+ " NVL(CASE WHEN OTHER_PAID_TYPE IN('Tender/Sealed Quotation') THEN OTHER_PAID_AMT END,0) AS TSQ, "
					+ " NVL(CASE WHEN OTHER_PAID_TYPE IN('Card Charge') THEN OTHER_PAID_AMT END,0) AS CC, "
					+ " NVL(CASE WHEN OTHER_PAID_TYPE IN('Others') THEN OTHER_PAID_AMT END,0) AS OTH,ILG_CON_AMT "
					+ " FROM BSW_PAYMENTS WHERE "
					+ "trunc(RDATE) >=TO_DATE('" + fromDate + "','MM/dd/yyyy') AND trunc(RDATE) <=TO_DATE('" + toDate
					+ "','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS='MISCELLANEOUS'";
			//System.out.println(sql);
			list2 = getCustomEntityManager().createNativeQuery(sql).getResultList();

			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
	
	@Override
	public List<?> meterReadingListReportRead(String wardno, int readingday, String monthyearnep,double pipesize) {
		
		String yearc=monthyearnep.substring(0, 4);
		String monthc=monthyearnep.substring(4, 6);
		int mnthyrnep=Integer.parseInt(monthyearnep)-1;
		
		if("01".equalsIgnoreCase(monthc)){
			yearc=""+(Integer.parseInt(yearc)-1);
			monthc="12";
			mnthyrnep=Integer.parseInt(yearc+""+monthc);
		}
		//System.out.println(mnthyrnep+"---mnthyrnep");
		if(pipesize==0.5){
String sql1="SELECT A.col_0_0_,A.col_1_0_,A.COL_2_0_,A.COL_3_0_,A.COL_4_0_,A.COL_5_0_,A.COL_6_0_,A.COL_7_0_,B.METER_NO,B.IR FROM "+
"("+
"SELECT"+" "+
	"consumerma0_.CONNECTION_NO AS col_0_0_,"+
	"consumerma0_.AREA_NO AS col_1_0_,"+
	"consumerma0_.NAME_NEP AS col_2_0_,"+
	"consumerma0_.NAME_ENG AS col_3_0_,"+
	"consumerma0_.ADDRESS_NEP AS col_4_0_,"+
	"consumerma0_.ADDRESS_ENG AS col_5_0_,"+
	"billingled1_.MC_STATUS AS col_6_0_,"+
	"consumerma0_.PIPE_SIZE AS col_7_0_ "+
"FROM"+
	" BSW_MASTER consumerma0_ cross "+
	" JOIN BSW_LEDGER billingled1_  "+
"WHERE"+
"	upper( consumerma0_.CON_TYPE ) = 'METERED' "+ 
	"AND ("+
	" upper( consumerma0_.CON_SATATUS ) IN ( 'NORMAL' ))"+ " "+
	" AND ( consumerma0_.WARD_NO ) ='"+wardno+"' "+
	" AND consumerma0_.READING_DAY ="+readingday+" "+
	" AND billingled1_.CONNECTION_NO = consumerma0_.CONNECTION_NO"+" "+ 
	" AND billingled1_.MONTHYEARNEP ="+mnthyrnep+" "+
	" AND consumerma0_.PIPE_SIZE ="+pipesize+
	" )A"+
	" Left JOIN "+
	"("+
	"select METER_NO,IR,CONNECTIONNO from BSW_METER_MASTER where status=0 "+
	")B"+ 
	" ON B.CONNECTIONNO=A.col_0_0_  ORDER BY A.col_1_0_";

			
return getCustomEntityManager().createNativeQuery(sql1).getResultList();

/*return getCustomEntityManager().createNamedQuery("ConsumerMaster.meterReadingListReportReadSA").setParameter("ward_no", wardno.trim().toUpperCase()).
					setParameter("reading_day", readingday)
					.setParameter("monthyearnep", ""+mnthyrnep)
					.setParameter("pipe_size", pipesize)
					.getResultList();*/
			
			
			//String query1="";

		}else{
			
			String sql1="SELECT A.col_0_0_,A.col_1_0_,A.COL_2_0_,A.COL_3_0_,A.COL_4_0_,A.COL_5_0_,A.COL_6_0_,A.COL_7_0_,B.METER_NO,B.IR FROM "+
					"("+
					"SELECT"+" "+
						"consumerma0_.CONNECTION_NO AS col_0_0_,"+
						"consumerma0_.AREA_NO AS col_1_0_,"+
						"consumerma0_.NAME_NEP AS col_2_0_,"+
						"consumerma0_.NAME_ENG AS col_3_0_,"+
						"consumerma0_.ADDRESS_NEP AS col_4_0_,"+
						"consumerma0_.ADDRESS_ENG AS col_5_0_,"+
						"billingled1_.MC_STATUS AS col_6_0_,"+
						"consumerma0_.PIPE_SIZE AS col_7_0_ "+
					"FROM"+
						" BSW_MASTER consumerma0_ cross "+
						" JOIN BSW_LEDGER billingled1_  "+
					"WHERE"+
					"	upper( consumerma0_.CON_TYPE ) = 'METERED' "+ 
						"AND ("+
						" upper( consumerma0_.CON_SATATUS ) IN ( 'NORMAL' ))"+ " "+
						" AND ( consumerma0_.WARD_NO ) ='"+wardno+"' "+
						" AND consumerma0_.READING_DAY ="+readingday+" "+
						" AND billingled1_.CONNECTION_NO = consumerma0_.CONNECTION_NO"+" "+ 
						" AND billingled1_.MONTHYEARNEP ="+mnthyrnep+" "+
						" AND consumerma0_.PIPE_SIZE >="+pipesize+
						" )A"+
						" Left JOIN "+
						"("+
						"select METER_NO,IR,CONNECTIONNO from BSW_METER_MASTER where status=0 "+
						")B"+ 
						" ON B.CONNECTIONNO=A.col_0_0_  ORDER BY A.col_1_0_";

								
					return getCustomEntityManager().createNativeQuery(sql1).getResultList();

			
			/*return getCustomEntityManager().createNamedQuery("ConsumerMaster.meterReadingListReportReadTHA").setParameter("ward_no", wardno.trim().toUpperCase()).
					setParameter("reading_day", readingday)
					.setParameter("monthyearnep", ""+mnthyrnep)
					.setParameter("pipe_size", pipesize)
					.getResultList();*/
		}
		
		

	}

	@Override
	public List<?> consumerBalanceReportRead(String amount, String criteria, String monthYear, String flag) {
		
		String sql="";
		//System.out.println(flag+"--flag");
		if("2".equalsIgnoreCase(flag)){
			
			 sql="SELECT m.CONNECTION_NO,m.NAME_ENG,nvl(l.WATER_CHARGES,0),nvl(l.SW_CHARGES,0),nvl(l.MTR_RENT,0),"
					 + "nvl(l.ARREARS,0),nvl(l.NET_AMOUNT,0),m.PIPE_SIZE,m.AREA_NO, m.WARD_NO, m.READING_DAY FROM BSW_MASTER m,BSW_LEDGER l WHERE m.CONNECTION_NO=l.CONNECTION_NO"
					 + " AND l.NET_AMOUNT"+criteria+""+Double.parseDouble(amount)+" AND l.RECEIPT_NO IS NULL AND l.MONTHYEARNEP="+monthYear+"";
					
					 
			
		}else{
			
			sql="SELECT m.CONNECTION_NO,m.NAME_ENG,nvl(l.WATER_CHARGES,0),nvl(l.SW_CHARGES,0),nvl(l.MTR_RENT,0),"
			+ "nvl(l.ARREARS,0),nvl(l.NET_AMOUNT,0),m.PIPE_SIZE,m.AREA_NO, m.WARD_NO, m.READING_DAY FROM BSW_MASTER m,BSW_LEDGER l WHERE m.CONNECTION_NO=l.CONNECTION_NO AND l.CONNECTION_NO IN("
		    + "SELECT A.conno FROM("
    		+ "(SELECT COUNT(*) as countno,CONNECTION_NO as conno FROM BSW_LEDGER WHERE RECEIPT_NO IS NULL GROUP BY CONNECTION_NO HAVING COUNT(*)"+criteria+""+Double.parseDouble(amount)+""
    		+ ")A)) AND l.RECEIPT_NO IS NULL AND  l.MONTHYEARNEP="+monthYear+"";
			
		}
		
		
		
		//System.out.println("cashCollectionListReport======"+sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	
		
	}
	
	@Override
	public List<?> multiPaymentRead(String amount, String criteria, String date1, String date2, String flag,String con_type) {
		
		String sql="";
		//System.out.println(flag+"--flag");
		if("1".equalsIgnoreCase(flag)){
			//System.out.println(con_type+"con_type--");
			
			if("All".equalsIgnoreCase(con_type)){
				 
				sql="SELECT p.CONNECTION_NO,m.NAME_ENG,p.RECEIPT_NO,p.RDATE,p.BALANCE_AMOUNT,"
					 		+ "p.WATER_CHARGES,p.SW_CHARGES,p.METER_RENT,p.MISCELLANEOUS_COST,"
					 		+ "p.AMOUNT FROM BSW_PAYMENTS p,BSW_MASTER m WHERE m.CONNECTION_NO=p.CONNECTION_NO "
					 		+ "AND trunc(RDATE)>=TO_DATE('"+date1+"', 'dd-MM-yyyy') "
					 		+ "AND trunc(RDATE)<=TO_DATE('"+date2+"', 'dd-MM-yyyy') "
					 		+ "AND p.CANCELLEDREMARKS IS NULL AND p.CONNECTION_NO IN ( "
					 		+ "SELECT A.conno from (SELECT COUNT(*) as count1,p.CONNECTION_NO as conno "
					 		+ "FROM BSW_PAYMENTS p,BSW_MASTER m WHERE m.CONNECTION_NO=p.CONNECTION_NO "
					 		+ "AND trunc(RDATE)>=TO_DATE('"+date1+"', 'dd-MM-yyyy') "
					 		+ "AND trunc(RDATE)<=TO_DATE('"+date2+"', 'dd-MM-yyyy') "
					 		+ "AND p.CANCELLEDREMARKS IS NULL GROUP BY p.CONNECTION_NO HAVING COUNT(*)"+criteria+""+Double.parseDouble(amount)+")A ) "
					 		+ "ORDER BY p.CONNECTION_NO,p.RDATE ASC";
			}else{
				 sql="SELECT p.CONNECTION_NO,m.NAME_ENG,p.RECEIPT_NO,p.RDATE,p.BALANCE_AMOUNT,"
					 		+ "p.WATER_CHARGES,p.SW_CHARGES,p.METER_RENT,p.MISCELLANEOUS_COST,"
					 		+ "p.AMOUNT FROM BSW_PAYMENTS p,BSW_MASTER m WHERE m.CONNECTION_NO=p.CONNECTION_NO "
					 		+ "AND trunc(RDATE)>=TO_DATE('"+date1+"', 'dd-MM-yyyy') "
					 		+ "AND trunc(RDATE)<=TO_DATE('"+date2+"', 'dd-MM-yyyy') "
					 		+ "AND p.CANCELLEDREMARKS IS NULL AND p.CONNECTION_NO IN ( "
					 		+ "SELECT A.conno from (SELECT COUNT(*) as count1,p.CONNECTION_NO as conno "
					 		+ "FROM BSW_PAYMENTS p,BSW_MASTER m WHERE m.CONNECTION_NO=p.CONNECTION_NO AND m.CON_TYPE='"+con_type+"'"
					 		+ "AND trunc(RDATE)>=TO_DATE('"+date1+"', 'dd-MM-yyyy') "
					 		+ "AND trunc(RDATE)<=TO_DATE('"+date2+"', 'dd-MM-yyyy') "
					 		+ "AND p.CANCELLEDREMARKS IS NULL GROUP BY p.CONNECTION_NO HAVING COUNT(*)"+criteria+""+Double.parseDouble(amount)+")A ) "
					 		+ "ORDER BY p.CONNECTION_NO,p.RDATE ASC";
			}
			
			
			
		}else{
			
			if("All".equalsIgnoreCase(con_type)){
				
				sql="SELECT p.CONNECTION_NO,m.NAME_ENG,p.RECEIPT_NO,p.RDATE,p.BALANCE_AMOUNT,p.WATER_CHARGES,"
						+ "p.SW_CHARGES,p.METER_RENT ,p.MISCELLANEOUS_COST,p.AMOUNT FROM BSW_PAYMENTS p,BSW_MASTER m "
						+ "WHERE m.CONNECTION_NO=p.CONNECTION_NO AND trunc(RDATE)>=TO_DATE('"+date1+"', 'dd-MM-yyyy') "
						+ "AND trunc(RDATE)<=TO_DATE('"+date2+"', 'dd-MM-yyyy') AND p.CANCELLEDREMARKS IS NULL "
						+ "AND p.AMOUNT"+criteria+""+Double.parseDouble(amount)+"";
			}else{
				sql="SELECT p.CONNECTION_NO,m.NAME_ENG,p.RECEIPT_NO,p.RDATE,p.BALANCE_AMOUNT,p.WATER_CHARGES,"
						+ "p.SW_CHARGES,p.METER_RENT ,p.MISCELLANEOUS_COST,p.AMOUNT FROM BSW_PAYMENTS p,BSW_MASTER m "
						+ "WHERE m.CONNECTION_NO=p.CONNECTION_NO AND m.CON_TYPE='"+con_type+"' AND trunc(RDATE)>=TO_DATE('"+date1+"', 'dd-MM-yyyy') "
						+ "AND trunc(RDATE)<=TO_DATE('"+date2+"', 'dd-MM-yyyy') AND p.CANCELLEDREMARKS IS NULL "
						+ "AND p.AMOUNT"+criteria+""+Double.parseDouble(amount)+"";
			}
			
			
			
		}
		//System.out.println("multiPaymentRead======"+sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
		
	}
	
	@Override
	public List<?> cashCollectionListReportRead(String fromdate, String todate) 
	{
		/*String sql="SELECT p.CONNECTION_NO,m.NAME_ENG,NVL(p.WATER_CHARGES,0),NVL(p.SW_CHARGES,0),NVL(p.METER_RENT,0),"
				+ "NVL(P.BALANCE_AMOUNT,0),NVL(p.MISCELLANEOUS_COST,0),NVL(p.PENALTY,0),NVL(p.AMOUNT,0),TRUNC(p.RDATE),p.RECEIPT_NO,"
				+ "p.FROM_MON_YEAR,p.TO_MON_YEAR FROM BSW_PAYMENTS p,BSW_MASTER m WHERE p.CONNECTION_NO=m.CONNECTION_NO  "
				+ "AND trunc(RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy') AND "
				+ "trunc(RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy') AND CANCELLEDREMARKS IS NULL AND "
				+ "FROM_MON_YEAR<206411";
		
		//System.out.println("cashCollectionListReport======"+sql);*/
		
		
		
		/*String sql="SELECT p.RECEIPT_NO,TRUNC(p.RDATE),p.CONNECTION_NO,m.NAME_ENG,NVL(p.AMOUNT,0)-(NVL(p.PENALTY,0)+NVL(p.PENALTY_ADJ_AMOUNT,0)),"
				+ "(NVL(p.PENALTY,0)+NVL(p.PENALTY_ADJ_AMOUNT,0)),NVL(p.AMOUNT,0),p.FROM_MON_YEAR,p.TO_MON_YEAR FROM BSW_PAYMENTS p,"
				+ "BSW_MASTER m WHERE p.CONNECTION_NO=m.CONNECTION_NO  "
				+ "AND trunc(RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy')" 
				+ "AND trunc(RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy') AND CANCELLEDREMARKS IS NULL "
				+ "AND FROM_MON_YEAR<206411 ORDER BY TRUNC(p.RDATE) ASC";*/
		
		String sql="SELECT p.RECEIPT_NO,p.RDATE,m.CONNECTION_NO,m.NAME_ENG,BM.COLL_UPTO2064,BM.PENALTY,BM.AMOUNT,p.FROM_MON_YEAR,p.TO_MON_YEAR "
				+ "FROM BOARD_2064MAGH bm,BSW_PAYMENTS p,BSW_MASTER m WHERE bm.CONNECTION_NO=p.CONNECTION_NO "
				+ "AND BM.CONNECTION_NO=m.CONNECTION_NO AND p.CONNECTION_NO=m.CONNECTION_NO "
				+ "AND trunc(p.RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy') AND trunc(RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy') "
				+ "AND p.CANCELLEDREMARKS IS NULL";
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<?> cashCollectionListReportReadAll(String fromdate, String todate) {
		String sql="SELECT SUM(p.AMOUNT),nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0) "
				+ "FROM BSW_PAYMENTS p WHERE trunc(p.RDATE)>=TO_DATE('"+fromdate+"', 'dd-MM-yyyy') "
				+ "AND trunc(p.RDATE)<=TO_DATE('"+todate+"', 'dd-MM-yyyy') AND p.CANCELLEDREMARKS IS NULL ";
		//System.out.println(sql+"==========read all");
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	
	@Override
	public List<?> consumerListReportReadData() {
		String sql="SELECT m.connection_no,m.area_no,m.name_nep,m.address_nep,m.con_type,m.pipe_size,m.name_eng,m.address_eng,m.ward_no,mm.meter_no FROM BSW_MASTER m left JOIN BSW_METER_MASTER mm on mm.CONNECTIONNO=m.CONNECTION_NO ORDER BY mm.meter_no";
		//return getCustomEntityManager().createNamedQuery("ConsumerMaster.consumerListReportReadData").getResultList();
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	@Override
	public List<?> holeBlockReportReadData(String con_status) {
		
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.holeBlockReportReadData").setParameter("con_status", con_status).getResultList();
	}
	@Override
	public List<?> generatedailyCashCounterCollReport(String fromDatenep, String toDatenep,String counterno,String wardno ,String pipesize ) {
		
		
		List<?> lst = null;
		String sql="";
		
		if(counterno.equalsIgnoreCase("0") &&  wardno.equalsIgnoreCase("0") && pipesize.equalsIgnoreCase("0"))
		{
		sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0), "
				+ "nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.counterno,a.boardpayment,(nvl(a.total,0)-nvl(a.boardpayment,0)) as kuklamt, a.boardpenalty, a.myn  from( "
				+ " select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "
				+ " coalesce(SUM(AMOUNT),0) as total, "
				+ " coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,COUNTERNO as counterno,nvl(sum(case when TOWARDS='BOARD PAYMENT' then AMOUNT end),0) as boardpayment,"
				+ " (nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY end),0)+nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY_ADJ_AMOUNT end),0)) as boardpenalty, MONTH_YEAR_NEP as myn"
				+ " from BSW_PAYMENTS where  trunc(RDATE)>=TO_DATE('" + fromDatenep
				+ "','MM/dd/yyyy') and  trunc(RDATE)<= TO_DATE('" + toDatenep
				+ "','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT')  GROUP BY COUNTERNO, MONTH_YEAR_NEP ORDER BY COUNTERNO ASC)a";
          System.out.println(sql);
		}
		else 
		{
			if(!counterno.equalsIgnoreCase("0") && wardno.equalsIgnoreCase("0") && pipesize.equalsIgnoreCase("0"))
			{
			 sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0), "
					+ "nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.counterno,a.boardpayment,(nvl(a.total,0)-nvl(a.boardpayment,0)) as kuklamt, a.boardpenalty, a.myn  from( "
					+ " select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "
					+ " coalesce(SUM(AMOUNT),0) as total, "
					+ " coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,COUNTERNO as counterno,nvl(sum(case when TOWARDS='BOARD PAYMENT' then AMOUNT end),0) as boardpayment,"
					+ " (nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY end),0)+nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY_ADJ_AMOUNT end),0)) as boardpenalty, MONTH_YEAR_NEP as myn"
					+ " from BSW_PAYMENTS where  trunc(RDATE)>=TO_DATE('" + fromDatenep
					+ "','MM/dd/yyyy') and  trunc(RDATE)<= TO_DATE('" + toDatenep
					+ "','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT')  AND COUNTERNO="+Integer.parseInt(counterno)+"  GROUP BY COUNTERNO, MONTH_YEAR_NEP ORDER BY COUNTERNO ASC)a";
	          System.out.println(sql);
			}else if(!counterno.equalsIgnoreCase("0") && !wardno.equalsIgnoreCase("0") && pipesize.equalsIgnoreCase("0"))
			{
			sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0), "
					+ "nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.counterno,a.boardpayment,(nvl(a.total,0)-nvl(a.boardpayment,0)) as kuklamt, a.boardpenalty, a.myn  from( "
					+ " select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "
					+ " coalesce(SUM(AMOUNT),0) as total, "
					+ " coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,COUNTERNO as counterno,nvl(sum(case when TOWARDS='BOARD PAYMENT' then AMOUNT end),0) as boardpayment,"
					+ " (nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY end),0)+nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY_ADJ_AMOUNT end),0)) as boardpenalty, MONTH_YEAR_NEP as myn"
					+ " from BSW_PAYMENTS where  trunc(RDATE)>=TO_DATE('" + fromDatenep
					+ "','MM/dd/yyyy') and  trunc(RDATE)<= TO_DATE('" + toDatenep
					+ "','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT')  AND COUNTERNO="+Integer.parseInt(counterno)+" and ward_no="+"'"+wardno+"'"+"  GROUP BY COUNTERNO, MONTH_YEAR_NEP ORDER BY COUNTERNO ASC)a";
	          System.out.println(sql);
			}
			else if(!counterno.equalsIgnoreCase("0") && wardno.equalsIgnoreCase("0") && !pipesize.equalsIgnoreCase("0"))
			{
			 sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0), "
					+ "nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.counterno,a.boardpayment,(nvl(a.total,0)-nvl(a.boardpayment,0)) as kuklamt, a.boardpenalty, a.myn  from( "
					+ " select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "
					+ " coalesce(SUM(AMOUNT),0) as total, "
					+ " coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,COUNTERNO as counterno,nvl(sum(case when TOWARDS='BOARD PAYMENT' then AMOUNT end),0) as boardpayment,"
					+ " (nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY end),0)+nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY_ADJ_AMOUNT end),0)) as boardpenalty, MONTH_YEAR_NEP as myn"
					+ " from BSW_PAYMENTS where  trunc(RDATE)>=TO_DATE('" + fromDatenep
					+ "','MM/dd/yyyy') and  trunc(RDATE)<= TO_DATE('" + toDatenep
					+ "','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT')  AND COUNTERNO="+Integer.parseInt(counterno)+" and pipe_size="+pipesize+"  GROUP BY COUNTERNO, MONTH_YEAR_NEP ORDER BY COUNTERNO ASC)a";
	          System.out.println(sql);
			}
			else if(counterno.equalsIgnoreCase("0") && !wardno.equalsIgnoreCase("0") && !pipesize.equalsIgnoreCase("0"))
			{
			 sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0), "
					+ "nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.counterno,a.boardpayment,(nvl(a.total,0)-nvl(a.boardpayment,0)) as kuklamt, a.boardpenalty, a.myn  from( "
					+ " select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "
					+ " coalesce(SUM(AMOUNT),0) as total, "
					+ " coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,COUNTERNO as counterno,nvl(sum(case when TOWARDS='BOARD PAYMENT' then AMOUNT end),0) as boardpayment,"
					+ " (nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY end),0)+nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY_ADJ_AMOUNT end),0)) as boardpenalty, MONTH_YEAR_NEP as myn"
					+ " from BSW_PAYMENTS where  trunc(RDATE)>=TO_DATE('" + fromDatenep
					+ "','MM/dd/yyyy') and  trunc(RDATE)<= TO_DATE('" + toDatenep
					+ "','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT')  AND ward_no="+"'"+wardno+"'"+" and pipe_size="+pipesize+"  GROUP BY COUNTERNO, MONTH_YEAR_NEP ORDER BY COUNTERNO ASC)a";
	          System.out.println(sql);
			}
			else if(counterno.equalsIgnoreCase("0") && !wardno.equalsIgnoreCase("0") && pipesize.equalsIgnoreCase("0"))
			{
			 sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0), "
					+ "nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.counterno,a.boardpayment,(nvl(a.total,0)-nvl(a.boardpayment,0)) as kuklamt, a.boardpenalty, a.myn  from( "
					+ " select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "
					+ " coalesce(SUM(AMOUNT),0) as total, "
					+ " coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,COUNTERNO as counterno,nvl(sum(case when TOWARDS='BOARD PAYMENT' then AMOUNT end),0) as boardpayment,"
					+ " (nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY end),0)+nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY_ADJ_AMOUNT end),0)) as boardpenalty, MONTH_YEAR_NEP as myn"
					+ " from BSW_PAYMENTS where  trunc(RDATE)>=TO_DATE('" + fromDatenep
					+ "','MM/dd/yyyy') and  trunc(RDATE)<= TO_DATE('" + toDatenep
					+ "','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT')  and ward_no="+"'"+wardno+"'"+"  GROUP BY COUNTERNO, MONTH_YEAR_NEP ORDER BY COUNTERNO ASC)a";
	          System.out.println(sql);
			}
			else if(counterno.equalsIgnoreCase("0") && wardno.equalsIgnoreCase("0") && !pipesize.equalsIgnoreCase("0"))
			{
		    sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0), "
					+ "nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.counterno,a.boardpayment,(nvl(a.total,0)-nvl(a.boardpayment,0)) as kuklamt, a.boardpenalty, a.myn  from( "
					+ " select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "
					+ " coalesce(SUM(AMOUNT),0) as total, "
					+ " coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,COUNTERNO as counterno,nvl(sum(case when TOWARDS='BOARD PAYMENT' then AMOUNT end),0) as boardpayment,"
					+ " (nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY end),0)+nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY_ADJ_AMOUNT end),0)) as boardpenalty, MONTH_YEAR_NEP as myn"
					+ " from BSW_PAYMENTS where  trunc(RDATE)>=TO_DATE('" + fromDatenep
					+ "','MM/dd/yyyy') and  trunc(RDATE)<= TO_DATE('" + toDatenep
					+ "','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT')  and pipe_size="+pipesize+"  GROUP BY COUNTERNO, MONTH_YEAR_NEP ORDER BY COUNTERNO ASC)a";
	          System.out.println(sql);
			}
			else if(!counterno.equalsIgnoreCase("0") && !wardno.equalsIgnoreCase("0") && !pipesize.equalsIgnoreCase("0"))
			{
		 sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0), "
					+ "nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.counterno,a.boardpayment,(nvl(a.total,0)-nvl(a.boardpayment,0)) as kuklamt, a.boardpenalty, a.myn  from( "
					+ " select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "
					+ " coalesce(SUM(AMOUNT),0) as total, "
					+ " coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,COUNTERNO as counterno,nvl(sum(case when TOWARDS='BOARD PAYMENT' then AMOUNT end),0) as boardpayment,"
					+ " (nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY end),0)+nvl(sum(case when TOWARDS='BOARD PAYMENT' then PENALTY_ADJ_AMOUNT end),0)) as boardpenalty, MONTH_YEAR_NEP as myn"
					+ " from BSW_PAYMENTS where  trunc(RDATE)>=TO_DATE('" + fromDatenep
					+ "','MM/dd/yyyy') and  trunc(RDATE)<= TO_DATE('" + toDatenep
					+ "','MM/dd/yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT')  and counterno="+Integer.parseInt(counterno)+" and ward_no="+"'"+wardno+"'"+" and  pipe_size="+pipesize+"  GROUP BY COUNTERNO, MONTH_YEAR_NEP ORDER BY COUNTERNO ASC)a";
	          System.out.println(sql);
			}
		
		}

		try {
			//System.out.println(sql);
			lst =  getCustomEntityManager().createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(" sql ===> "+sql);
		return lst;
	}
	@Override
	public List<?> conTypeReportReadData(String con_Type) {
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.conTypeReportReadData").setParameter("con_Type", con_Type).getResultList();
		
	}
	@Override
	public List<?> dailyRevenueReportRead(String fromdate, String todate,String counter_no) 
	{
		String sql="";
		if("All".equalsIgnoreCase(counter_no)){
			 sql="select a.ward,NVL(a.arrears,0),NVL(a.wc,0),NVL(a.sc,0),NVL(a.mr,0),NVL((a.arrears+a.wc+a.sc+a.mr),0),NVL(a.pen,0),NVL(a.reb,0),NVL(a.mc,0),NVL((a.arrears+a.wc+a.sc+a.mr+a.pen+a.mc-a.reb),0),NVL(a.total,0),NVL(a.advance,0),NVL(a.advancerebate,0),NVL(((a.total+a.advance+a.advancerebate)-(a.arrears+a.wc+a.sc+a.mr+a.pen+a.mc-a.reb)),0),a.redate from( "+
					" select NVL(SUM(WATER_CHARGES),0) as wc,NVL(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,NVL(SUM(METER_RENT),0) as mr,(NVL(SUM(PENALTY),0)+NVL(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,NVL(SUM(REBATE),0) as reb, "+ 
					" coalesce(SUM(AMOUNT),0) as total, "+
					" coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,WARD_NO as ward,trunc(RDATE) as redate from BSW_PAYMENTS where TRUNC(RDATE)> = to_date('"+fromdate+"','dd-MM-yyyy') AND TRUNC(RDATE)< = to_date('"+todate+"','dd-MM-yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT') GROUP BY WARD_NO, trunc(RDATE))a" ;

		}else{
			 sql="select a.ward,NVL(a.arrears,0),NVL(a.wc,0),NVL(a.sc,0),NVL(a.mr,0),NVL((a.arrears+a.wc+a.sc+a.mr),0),NVL(a.pen,0),NVL(a.reb,0),NVL(a.mc,0),NVL((a.arrears+a.wc+a.sc+a.mr+a.pen+a.mc-a.reb),0),NVL(a.total,0),NVL(a.advance,0),NVL(a.advancerebate,0),NVL(((a.total+a.advance+a.advancerebate)-(a.arrears+a.wc+a.sc+a.mr+a.pen+a.mc-a.reb)),0),a.redate from( "+
					" select NVL(SUM(WATER_CHARGES),0) as wc,NVL(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,NVL(SUM(METER_RENT),0) as mr,(NVL(SUM(PENALTY),0)+NVL(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,NVL(SUM(REBATE),0) as reb, "+ 
					" coalesce(SUM(AMOUNT),0) as total, "+
					" coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,WARD_NO as ward,trunc(RDATE) as redate from BSW_PAYMENTS where TRUNC(RDATE)> = to_date('"+fromdate+"','dd-MM-yyyy') AND TRUNC(RDATE)< = to_date('"+todate+"','dd-MM-yyyy') AND COUNTERNO="+Integer.parseInt(counter_no)+" AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT') GROUP BY WARD_NO,trunc(RDATE))a" ;

		}
		//System.out.println(sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();

	}
	@Override
	public List<?> revenueReportRead(String fromdate, String todate,String counter_no) {
		String sql="";
		if("All".equalsIgnoreCase(counter_no)){
			  sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0),nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.rdate from( "+
						" select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "+ 
						" coalesce(SUM(AMOUNT),0) as total, "+
						" coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,trunc(RDATE) as rdate from BSW_PAYMENTS where TRUNC(RDATE)> = to_date('"+fromdate+"','dd-MM-yyyy') AND TRUNC(RDATE)< = to_date('"+todate+"','dd-MM-yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT') GROUP BY trunc(RDATE) ORDER BY trunc(RDATE) ASC )a" ;
		}else{
			
			
			sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0),nvl((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb)),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.rdate from( "+
					" select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "+ 
					" coalesce(SUM(AMOUNT),0) as total, "+
					" coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,trunc(RDATE) as rdate from BSW_PAYMENTS where TRUNC(RDATE)> = to_date('"+fromdate+"','dd-MM-yyyy') AND TRUNC(RDATE)< = to_date('"+todate+"','dd-MM-yyyy') AND COUNTERNO="+Integer.parseInt(counter_no)+" AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT') GROUP BY trunc(RDATE) ORDER BY trunc(RDATE) ASC )a" ;
	
		
		}
		System.out.println(sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	@Override
	public List<?> wardWiseConsumerCountReport() {
		String sql="SELECT WARD_NO,COUNT(*),READING_DAY FROM BSW_MASTER GROUP BY WARD_NO,READING_DAY ORDER BY WARD_NO ASC" ;
		//System.out.println(sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	@Override
	public List<?> custObservationReportRead(String monthYearNep, int mc_status) {
		 
		 String sql="SELECT m.CONNECTION_NO,m.NAME_ENG,m.AREA_NO,m.PIPE_SIZE,NVL(l.PRESENT_READING,0),NVL(l.PREVIOUS_READING,0),l.arrears FROM BSW_MASTER m,BSW_LEDGER l "
		 		+ " WHERE m.CONNECTION_NO=l.CONNECTION_NO AND m.CON_SATATUS NOT IN('Permanent') AND  l.MONTHYEARNEP="+monthYearNep+" AND l.MC_STATUS="+mc_status+"";
		 //System.out.println(sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}

	@Override
	public List<?> categoryCollectionReportRead(String from, String to, String category) {
		String sql = "SELECT SUM(p.BILL_AMOUNT),sum((NVL(p.PENALTY,0)+NVL(p.PENALTY_ADJ_AMOUNT,0))) AS PENL,SUM (NVL(CASE WHEN p.TOWARDS='BILL PAYMENT' THEN p.AMOUNT END, 0)) as BILLPAID,SUM (NVL(CASE WHEN p.TOWARDS='BOARD PAYMENT' THEN p.AMOUNT END, 0)) as BOARDPAID,m.CON_CATEGORY FROM BSW_PAYMENTS p,BSW_MASTER m "
				+ "WHERE " + " trunc(p.RDATE)>=TO_DATE('" + from + "', 'dd-MM-yyyy')  AND trunc(p.RDATE)<=TO_DATE('"
				+ to + "', 'dd-MM-yyyy') "
				+ "AND p.CANCELLEDREMARKS IS NULL AND m.CONNECTION_NO=p.CONNECTION_NO AND p.TOWARDS IN('BILL PAYMENT','BOARD PAYMENT') GROUP BY m.CON_CATEGORY";
		//System.out.println(sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}

	@Override
	public List<?> monthlySalesReadData(String monthyearnep) {

		/*String sql = "SELECT b.FISCAL_YEAR_MONTH,b.WARD_NO,b.DENOTED_BY,SUM(NVL(OPENING_BALANCE,0)),SUM(NVL(WATER_COST,0)),SUM(NVL(SEWERAGE_COST,0)),SUM(NVL(MRENT_COST,0)),SUM(NVL(MISC_COST,0)),"
				+ "SUM(NVL(RECEIVED_AMOUNT,0)),SUM(NVL(ADD_PEN_COST,0)),SUM(NVL(PENALTY,0)),SUM(NVL(POS_ADJUSTMENT,0)),SUM(NVL(NEG_ADJUSTMENT,0)),SUM(NVL(REBATE,0)),SUM(NVL(CLOSING_BALANCE,0)),SUM(NVL(SUSPENSE,0)),SUM(NVL(ADV_REB,0)),SUM(NVL(ADJ_VOUCHER,0))"
				+ "FROM FISCAL_YEAR_BALANCE b WHERE B.FISCAL_YEAR_MONTH=" + monthyearnep
				+ " GROUP BY b.WARD_NO,b.DENOTED_BY,b.FISCAL_YEAR_MONTH ORDER BY b.WARD_NO,b.DENOTED_BY ASC";*/
		
		String sql="SELECT d.fmonth, d.ward, d.denoted, d.ob, d.wc, d.Sc,d.mr, d.mc,d.ra,d.apc,d.pen, d.padj, d.nadj,d.reb, d.cb, d.sus, "
				+ "d.adv_reb, d.av,nvl(A.caa,0), nvl(a.car,0) FROM( SELECT b.FISCAL_YEAR_MONTH as fmonth, b.WARD_NO as ward, "
				+ "b.DENOTED_BY as denoted, SUM (NVL(OPENING_BALANCE, 0)) as ob, SUM (NVL(WATER_COST, 0))as wc, "
				+ "SUM (NVL(SEWERAGE_COST, 0)) as sc, SUM (NVL(MRENT_COST, 0)) as mr, SUM (NVL(MISC_COST, 0)) as mc, "
				+ "SUM (NVL(RECEIVED_AMOUNT, 0)) as ra, SUM (NVL(ADD_PEN_COST, 0)) as apc, SUM (NVL(PENALTY, 0)) as pen, "
				+ "SUM (NVL(POS_ADJUSTMENT, 0)) as padj, SUM (NVL(NEG_ADJUSTMENT, 0))as nadj, SUM (NVL(REBATE, 0)) as reb, "
				+ "SUM (NVL(CLOSING_BALANCE, 0)) as cb, SUM (NVL(SUSPENSE, 0)) as sus, SUM (NVL(ADV_REB, 0))as adv_reb, "
				+ "SUM (NVL(ADJ_VOUCHER, 0)) as av FROM FISCAL_YEAR_BALANCE b WHERE B.FISCAL_YEAR_MONTH = "+monthyearnep+" "
				+ "GROUP BY b.WARD_NO, b.DENOTED_BY, b.FISCAL_YEAR_MONTH ORDER BY b.WARD_NO, b.DENOTED_BY ASC) D LEFT JOIN "
				+ "( SELECT b.FISCAL_YEAR_MONTH as fmonth, b.WARD_NO as ward, b.DENOTED_BY as denoted, SUM (NVL(b.CURRENT_ADV_AMT, 0)) as caa,"
				+ " SUM ( NVL (b.CURRENT_ADV_REBATE, 0) ) as car FROM FISCAL_YEAR_ADVANCE_BALANCE b WHERE B.FISCAL_YEAR_MONTH = "+monthyearnep+" "
				+ "GROUP BY b.WARD_NO, b.DENOTED_BY, b.FISCAL_YEAR_MONTH ORDER BY b.WARD_NO, b.DENOTED_BY ASC )A ON d.fmonth=a.fmonth "
				+ "AND d.ward=a.ward and d.denoted=a.denoted ORDER BY d.ward, d.denoted ASC";
		
		//System.out.println(sql);

		return getCustomEntityManager().createNativeQuery(sql).getResultList();

	}

	@Override
	public List<?> newMonthlySalesCategory(String monthyearnep, String con_category) {
		String sql = "SELECT b.FISCAL_YEAR_MONTH,b.WARD_NO,b.DENOTED_BY,b.CON_TYPE,SUM(NVL(OPENING_BALANCE,0)),SUM(NVL(WATER_COST,0)),SUM(NVL(SEWERAGE_COST,0)),SUM(NVL(MRENT_COST,0)),SUM(NVL(MISC_COST,0)),"
				+ "SUM(NVL(RECEIVED_AMOUNT,0)),SUM(NVL(ADD_PEN_COST,0)),SUM(NVL(PENALTY,0)),SUM(NVL(POS_ADJUSTMENT,0)),SUM(NVL(NEG_ADJUSTMENT,0)),SUM(NVL(REBATE,0)),SUM(NVL(CLOSING_BALANCE,0)),SUM(NVL(SUSPENSE,0)),SUM(NVL(ADV_REB,0)) "
				+ "FROM FISCAL_YEAR_BALANCE b WHERE B.FISCAL_YEAR_MONTH=" + monthyearnep + " AND B.CON_CATEGORY='"
				+ con_category
				+ "' GROUP BY b.WARD_NO,b.DENOTED_BY,b.FISCAL_YEAR_MONTH,b.CON_TYPE ORDER BY b.WARD_NO,b.DENOTED_BY ASC";

		//System.out.println(sql);

		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}

	
	@Override
	public List<?> counterRevenueReportRead(String fromdate, String todate) {
		String sql="select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,"
				+ "nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0),nvl((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb)),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0),a.rdate, a.COUNTERNO from( "+
				" select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,"
				+ "coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "+ 
				" coalesce(SUM(AMOUNT),0) as total, "+
				" coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,"
				+ "coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,"
				+ "coalesce(SUM(BALANCE_AMOUNT),0) as arrears,trunc(RDATE) as rdate, COUNTERNO from BSW_PAYMENTS "
				+ "where TRUNC(RDATE)> = to_date('"+fromdate+"','dd-MM-yyyy') AND TRUNC(RDATE)< = to_date('"+todate+"','dd-MM-yyyy') "
						+ "AND CANCELLEDREMARKS IS NULL AND TOWARDS IN ('BOARD PAYMENT','BILL PAYMENT') GROUP BY trunc(RDATE),COUNTERNO ORDER BY trunc(RDATE) ASC )a" ;
		
		//System.out.println(sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<?> ledgerVerificationRead(String wardno, String monthyear,String reading_day) {
		
/*		
		
		String sql1 = "SELECT b.CONNECTION_NO, m.NAME_ENG, b.WATER_CHARGES, b.SW_CHARGES, b.MTR_RENT, b.ARREARS, b.NET_AMOUNT, b.PENALTY, "
				+ "b.REBATE, b.LAST_PAID_AMOUNT, b.CLOSE_BALANCE, b.RECEIPT_NO, b.RECEIPT_DATE, b.OTHER, m.BALANCE, m.AREA_NO, b.PREVIOUS_READING, b.PRESENT_READING, b.CONSUMPTION from BSW_LEDGER b, BSW_MASTER m "
				+ "WHERE b.CONNECTION_NO=m.CONNECTION_NO AND MONTHYEARNEP="+monthyear+"";
		
		String sql2= " AND WARDNO='"+wardno+"'";
		String sql3= " AND m.READING_DAY="+reading_day+"";
		String sql4= " ORDER BY m.AREA_NO";
		String sql="";
		if("ALL".equalsIgnoreCase(wardno)){
			sql=sql+sql1+sql4;
		}
		else
		{
			sql=sql+sql1+sql2+sql3+sql4;
		}
		
		//System.out.println("Ledger Verification------------\n"+sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();*/
		
		if(reading_day.contains("@")){
			String conCatagory[]=reading_day.split("@");
			
			String sql1 = "SELECT b.CONNECTION_NO, m.NAME_ENG, b.WATER_CHARGES, b.SW_CHARGES, b.MTR_RENT, b.ARREARS, b.NET_AMOUNT, b.PENALTY, "
					+ "b.REBATE, b.LAST_PAID_AMOUNT, b.CLOSE_BALANCE, b.RECEIPT_NO, b.RECEIPT_DATE, b.OTHER, m.BALANCE, m.AREA_NO, b.PREVIOUS_READING, b.PRESENT_READING, b.CONSUMPTION from BSW_LEDGER b, BSW_MASTER m "
					+ "WHERE b.CONNECTION_NO=m.CONNECTION_NO AND MONTHYEARNEP="+monthyear+"";
			
			String sql2= " AND WARDNO='"+wardno+"'";
			String sql3= " AND m.READING_DAY="+conCatagory[0]+"";
			String sql4= " ORDER BY m.AREA_NO";
			String sql5= " AND m.CON_CATEGORY= '"+conCatagory[1]+"'";
			String sql="";
			if("ALL".equalsIgnoreCase(wardno)){
				sql=sql+sql1+sql5+sql4;
			}
			else
			{
				sql=sql+sql1+sql2+sql3+sql5+sql4;
			}
			
			//System.out.println("Ledger Verification------------\n"+sql);
			return getCustomEntityManager().createNativeQuery(sql).getResultList();
			}
			else{
				String sql1 = "SELECT b.CONNECTION_NO, m.NAME_ENG, b.WATER_CHARGES, b.SW_CHARGES, b.MTR_RENT, b.ARREARS, b.NET_AMOUNT, b.PENALTY, "
						+ "b.REBATE, b.LAST_PAID_AMOUNT, b.CLOSE_BALANCE, b.RECEIPT_NO, b.RECEIPT_DATE, b.OTHER, m.BALANCE, m.AREA_NO, b.PREVIOUS_READING, b.PRESENT_READING, b.CONSUMPTION from BSW_LEDGER b, BSW_MASTER m "
						+ "WHERE b.CONNECTION_NO=m.CONNECTION_NO AND MONTHYEARNEP="+monthyear+"";
				
				String sql2= " AND WARDNO='"+wardno+"'";
				String sql3= " AND m.READING_DAY="+reading_day+"";
				String sql4= " ORDER BY m.AREA_NO";
				String sql="";
				if("ALL".equalsIgnoreCase(wardno)){
					sql=sql+sql1+sql4;
				}
				else
				{
					sql=sql+sql1+sql2+sql3+sql4;
				}
				
				//System.out.println("Ledger Verification------------\n"+sql);
				return getCustomEntityManager().createNativeQuery(sql).getResultList();
			}
		
	}
	
	@Override
	public List<?> wardWiseCustomerBillingReportRead(String wardNo, String myn, String category) {
		
		String sql ="SELECT l.CONNECTION_NO, m.NAME_ENG, m.AREA_NO, m.PIPE_SIZE,l.ARREARS, l.WATER_CHARGES, l.SW_CHARGES,l.MTR_RENT,l.NET_AMOUNT "
				+ "from BSW_MASTER m, BSW_LEDGER l WHERE m.CONNECTION_NO=l.CONNECTION_NO AND l.CON_CATEGORY='"+category+"' "
				+ "AND l.WARDNO='"+wardNo+"' AND l.MONTHYEARNEP="+myn+"";
		
		//System.out.println("wardWiseCustomerBillingReportRead------------\n"+sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<?> monthlyAdjustmentReportRead(String myn) {
		String sql = "SELECT a.CONNECTION_NO,m.NAME_ENG,m.AREA_NO, (CASE WHEN a.ADJ_TYPE='BAC' THEN a.BILL_ADJ_AMOUNT END) as Arcorr,  "
				+ "(CASE WHEN a.ADJ_TYPE='BAC' THEN a.PENALTY_ADJ_AMOUNT END) as Pecorr, "
				+ "(CASE WHEN a.ADJ_TYPE='BDJ' THEN a.BILL_ADJ_AMOUNT END) as BillAdj, "
				+ "(CASE WHEN a.ADJ_TYPE='BDJ' THEN a.PENALTY_ADJ_AMOUNT END) as PeAdj, "
				+ "a.SUBMIT_BY, a.SUBMIT_DATE, a.APPROVED_BY, a.APPROVED_DATE, a.APPROVED_BY1,a.APPROVED_DATE1, A.ADJ_TYPE "
				+ "from BSW_MASTER m, BSW_LEDGER l, BILL_PENALTY_ADJ a WHERE "
				+ "m.CONNECTION_NO=l.CONNECTION_NO AND m.CONNECTION_NO=a.CONNECTION_NO AND  "
				+ "l.MONTHYEARNEP="+myn+" AND a.APPROVE_STATUS IN(1,3) AND a.TO_MON_YEAR="+myn;
		
		System.out.println("monthlyAdjustmentReportRead------------\n"+sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<?> categoryBoardCollectionReportRead(String from, String to) {
		String sql = "SELECT m.CON_CATEGORY, m.CON_TYPE, SUM(i.INSTALL_AMOUNT+i.REM_BALANCE) as OpeningBal, SUM(i.INSTALL_AMOUNT) AS Arrears, SUM(i.PENALTY) AS Penalty, "
				+ "SUM(i.PAID_AMOUNT) AS TOTAL, SUM(i.REM_BALANCE), SUM(i.INSTALL_AMOUNT+i.PENALTY-i.PAID_AMOUNT) as penAdj"
				+ " FROM BSW_MASTER m, BOARD_INSTALLMENT i WHERE m.CONNECTION_NO=i.CONNECTION_NO AND "
				+ " TRUNC(i.SUBMIT_DATE)>=TO_DATE('"+from+"', 'dd-MM-yyyy') AND "
				+ " TRUNC(i.SUBMIT_DATE)<=TO_DATE('"+to+"', 'dd-MM-yyyy') GROUP BY m.CON_CATEGORY, m.CON_TYPE";
		
		//System.out.println("categoryBoardCollectionReportRead------------\n"+sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<?> cancelReceiptReportRead(String from, String to) {
		String sql = "SELECT p.CONNECTION_NO, m.NAME_ENG, m.AREA_NO, p.RECEIPT_NO, p.RDATE, p.AMOUNT, p.REBATE, p.PENALTY, p.BILL_AMOUNT, p.RBALANCE"
				+ " FROM BSW_PAYMENTS p, BSW_MASTER m WHERE m.CONNECTION_NO=p.CONNECTION_NO AND TRUNC(p.RDATE)>=TO_DATE('"+from+"', 'dd-MM-yyyy') AND  "
				+ " TRUNC(p.RDATE)<=TO_DATE('"+to+"', 'dd-MM-yyyy')"
				+ " AND p.CANCELLEDREMARKS IS NOT NULL ORDER BY p.CONNECTION_NO";

		//System.out.println("cancelReceiptReportRead------------\n"+sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<?> boardSalesReportRead(String from, String to) {
		
		String sql = "SELECT a.ward, (b.close_bal+(a.paid-(a.penalty+a.penaltyadj))) as opening_bal,(a.paid-(a.penalty+a.penaltyadj)) as arrears,"
				+ " a.penalty,a.penaltyadj,a.paid, b.close_bal FROM ( SELECT m.WARD_NO as ward,  NVL(SUM(CASE WHEN P.TOWARDS='BOARD PAYMENT' THEN p.AMOUNT END),0) "
				+ " as paid, NVL(SUM(CASE WHEN P.TOWARDS='BOARD PAYMENT' THEN p.PENALTY END),0) as penalty , "
				+ " NVL(SUM(CASE WHEN P.TOWARDS='BOARD PAYMENT' THEN p.PENALTY_ADJ_AMOUNT END),0) as penaltyadj,"
				+ " NVL(SUM(BALANCE),0) as close_bal FROM BSW_MASTER m, BSW_PAYMENTS p  WHERE p.CONNECTION_NO=m.CONNECTION_NO AND  "
				+ " trunc(RDATE)>=TO_DATE('"+from+"','dd/MM/yyyy')  and  trunc(RDATE)<= TO_DATE('"+to+"','dd/MM/yyyy') GROUP BY M.WARD_NO )A,"
				+ " ( SELECT WARD_NO AS ward, NVL(SUM(balance),0) as close_bal  from BSW_MASTER GROUP BY WARD_NO )B WHERE a.ward=b.ward";

		//System.out.println("boardSalesReportRead------------\n"+sql);
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
	@Override
	public List<?> advanceCollectionReportRead(String monthyearnep) {
		int year=Integer.parseInt(monthyearnep.substring(0, 4));
		int month=Integer.parseInt(monthyearnep.substring(4, 6));
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
		
		String sql = "SELECT BWARD, BDEN,NVL(FCBAL,0) as advopenbal,NVL(BWC,0) as watercharge,NVL(BSWC,0) as swcharge,NVL(BMR,0) as mtrrent,NVL(PADV,0) as advance, "
				+ " NVL(PA,0) as advreb, FWARD, FDEN, PWARD, PDEN FROM "
				+ " (SELECT BWC,BSWC, BMR, BWARD, BDEN, PWARD, PDEN, PADV, PA FROM "
				+ " (SELECT SUM(nvl(l.WATER_CHARGES,0)) AS BWC,SUM(nvl(l.SW_CHARGES,0)) AS BSWC,SUM(nvl(l.MTR_RENT,0)) AS BMR, "
				+ " (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END) AS BWARD,m.DENOTED_BY AS BDEN "
				+ " FROM BSW_LEDGER l,BSW_MASTER m WHERE MONTHYEARNEP="+monthyearnep+" AND m.CONNECTION_NO=l.CONNECTION_NO "
				+ " GROUP BY (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END),m.DENOTED_BY)A "
				+ " FULL OUTER JOIN (SELECT (CASE WHEN m.CON_TYPE='Unmetered' THEN 'NM' ELSE m.WARD_NO END) "
				+ " AS PWARD,p.DENOTED_BY AS PDEN,SUM(nvl(p.ADVANCE,0)) AS PADV, SUM(nvl(p.ADVANCE_REBATE,0)) AS PA "
				+ " FROM BSW_PAYMENTS p,BSW_MASTER m WHERE p.MONTH_YEAR_NEP="+monthyearnep+" AND m.CONNECTION_NO=p.CONNECTION_NO AND "
				+ " p.CANCELLEDREMARKS IS NULL AND UPPER(p.TOWARDS)='BILL PAYMENT' GROUP BY (CASE WHEN m.CON_TYPE='Unmetered' "
				+ " THEN 'NM' ELSE m.WARD_NO END),p.DENOTED_BY)B  ON A.BWARD=B.PWARD  AND A.BDEN=B.PDEN)C "
				+ " FULL OUTER JOIN  (SELECT b.WARD_NO as FWARD,b.DENOTED_BY AS FDEN, "
				+ " ROUND((SUM(NVL(b.ADV_REB,0))*100)/3,2) AS FCBAL FROM FISCAL_YEAR_BALANCE b WHERE B.FISCAL_YEAR_MONTH="+monthyearp+""
				+ " GROUP BY b.WARD_NO,b.DENOTED_BY )D ON C.BWARD=D.FWARD  AND C.BDEN=D.FDEN";
		
		//System.out.println("advanceCollectionReportRead------------\n"+sql);
		try{
			return getCustomEntityManager().createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	@Override
	public List<?> generatedailyMiscColReport(String fdate, String tdate,String counterNO) {
		String sql ="";
		if(counterNO.equalsIgnoreCase("All")){
		
		 sql = "SELECT"
				+ " NVL (A .NCT, 0),"
				+ " NVL (A .NCD, 0),"
				+ " NVL (A .NC_IN, 0),"
				+ " NVL (A .MVAL, 0),"
				+ " NVL (A .TH, 0),"
				+ " NVL (A .NCH, 0),"
				+ " NVL (A .ILLG, 0),NVL (A .MISC, 0),NVL (A .ADV, 0),"
				+ " NVL (A .AMT, 0),"
			    + " A .COUNTERNO,"
			    + "NVL (A .HC, 0),"
			    + "NVL (A .HM, 0),"
			    + "NVL (A .AC, 0),"
			    + "NVL (A .TSQ, 0),"
			    + "NVL (A .CC, 0),"
			    + "NVL (A .OTH, 0)"
			    + " FROM"
				+ " ("
					+ " SELECT"
						+ " NVL (SUM(NCTAP), 0) AS NCT,"
						+ " NVL (SUM(NCDEPOSIT), 0) AS NCD,"
						+ " COALESCE (SUM(NC_INST), 0) AS NC_IN,"
						+ " COALESCE (SUM(MVALUE), 0) AS MVAL,"
						+ " COALESCE (SUM(TEMPHOLEAMT), 0) AS TH,"
						+ " COALESCE (SUM(NAMECHANGEAMT), 0) AS NCH,"
						+ " COALESCE (SUM(ILG_CON_AMT), 0) AS ILLG,COALESCE (SUM(MISCELLANEOUS_COST), 0) AS MISC,COALESCE (SUM(ADVANCE), 0) AS ADV,COALESCE (SUM(AMOUNT), 0) AS AMT,"
						+ " COUNTERNO AS COUNTERNO, "
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Hole Change') THEN OTHER_PAID_AMT END,0)) AS HC,"
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Hole Maintenance') THEN OTHER_PAID_AMT END,0)) AS HM, "
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Application Charge') THEN OTHER_PAID_AMT END,0)) AS AC, "
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Tender/Sealed Quotation') THEN OTHER_PAID_AMT END,0)) AS TSQ, "
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Card Charge') THEN OTHER_PAID_AMT END,0)) AS CC, "
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Others') THEN OTHER_PAID_AMT END,0)) AS OTH "
						
					+ " FROM"
					 + " BSW_PAYMENTS WHERE TRUNC (RDATE) >= TO_DATE ('"+fdate+"', 'MM/DD/YYYY') AND TRUNC (RDATE) <= TO_DATE ('"+tdate+"', 'MM/DD/YYYY') AND CANCELLEDREMARKS IS NULL AND TOWARDS ='MISCELLANEOUS' GROUP BY COUNTERNO ORDER BY COUNTERNO ASC) A";
		}
		
		else{
			int counteNo=Integer.parseInt(counterNO);
			 sql = "SELECT"
					+ " NVL (A .NCT, 0),"
					+ " NVL (A .NCD, 0),"
					+ " NVL (A .NC_IN, 0),"
					+ " NVL (A .MVAL, 0),"
					+ " NVL (A .TH, 0),"
					+ " NVL (A .NCH, 0),"
					+ " NVL (A .ILLG, 0),NVL (A .MISC, 0),NVL (A .ADV, 0),"
					+ " NVL (A .AMT, 0),"
				    + " A .COUNTERNO,"
				    + "NVL (A .HC, 0),"
				    + "NVL (A .HM, 0),"
				    + "NVL (A .AC, 0),"
				    + "NVL (A .TSQ, 0),"
				    + "NVL (A .CC, 0),"
				    + "NVL (A .OTH, 0)"
				    + " FROM"
					+ " ("
						+ " SELECT"
							+ " NVL (SUM(NCTAP), 0) AS NCT,"
							+ " NVL (SUM(NCDEPOSIT), 0) AS NCD,"
							+ " COALESCE (SUM(NC_INST), 0) AS NC_IN,"
							+ " COALESCE (SUM(MVALUE), 0) AS MVAL,"
							+ " COALESCE (SUM(TEMPHOLEAMT), 0) AS TH,"
							+ " COALESCE (SUM(NAMECHANGEAMT), 0) AS NCH,"
							+ " COALESCE (SUM(ILG_CON_AMT), 0) AS ILLG,COALESCE (SUM(MISCELLANEOUS_COST), 0) AS MISC,COALESCE (SUM(ADVANCE), 0) AS ADV,COALESCE (SUM(AMOUNT), 0) AS AMT,"
							+ " COUNTERNO AS COUNTERNO, "
							+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Hole Change') THEN OTHER_PAID_AMT END,0)) AS HC,"
							+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Hole Maintenance') THEN OTHER_PAID_AMT END,0)) AS HM, "
							+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Application Charge') THEN OTHER_PAID_AMT END,0)) AS AC, "
							+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Tender/Sealed Quotation') THEN OTHER_PAID_AMT END,0)) AS TSQ, "
							+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Card Charge') THEN OTHER_PAID_AMT END,0)) AS CC, "
							+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Others') THEN OTHER_PAID_AMT END,0)) AS OTH "
							
						+ " FROM"
						 + " BSW_PAYMENTS WHERE TRUNC (RDATE) >= TO_DATE ('"+fdate+"', 'MM/DD/YYYY') AND TRUNC (RDATE) <= TO_DATE ('"+tdate+"', 'MM/DD/YYYY') AND CANCELLEDREMARKS IS NULL AND TOWARDS ='MISCELLANEOUS' AND COUNTERNO="+counteNo+" GROUP BY COUNTERNO ORDER BY COUNTERNO ASC) A";
			
			
		}
		
		
		
		//System.out.println("MISCELLANEOUS------------\n"+sql);
		try{
			return getCustomEntityManager().createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	
	@Override
	public List<?> getAbnormalBillingReport(String monthyearnep, String diff) {
		int year=Integer.parseInt(monthyearnep.substring(0, 4));
		int month=Integer.parseInt(monthyearnep.substring(4, 6));
		int difrnc=Integer.parseInt(diff);
		
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
		
		int monthyearp1=Integer.parseInt(year+""+month1);
		
		if(month==1){
			month=12;
			year=year-1;
		}else{
			month=month-1;
		}
		String month2=""+month;
		
		if(month2.length()==1){
			month2="0"+month;
		}
		int monthyearp2=Integer.parseInt(year+""+month2);
		
		if(month==1){
			month=12;
			year=year-1;
		}else{
			month=month-1;
		}
		String month3=""+month;
		
		if(month3.length()==1){
			month3="0"+month;
		}
		int monthyearp3=Integer.parseInt(year+""+month3);
		
		System.out.println(monthyearnep+"====="+monthyearp1+"======"+monthyearp2+"======"+monthyearp3);
		
		String sql = "SELECT A.CONNECTION_NO,A.CBILLAMT,((B.PBILLAMT+C.PBILLAMT1+D.PBILLAMT2)/3) as AVG,(A.CBILLAMT-((B.PBILLAMT+C.PBILLAMT1+D.PBILLAMT2)/3)) AS DIFF FROM " + 
				"(select CONNECTION_NO,(WATER_CHARGES+SW_CHARGES+MTR_RENT) AS CBILLAMT FROM BSW_LEDGER WHERE MONTHYEARNEP="+monthyearnep+" AND BILLNO IS NOT NULL)A," + 
				"(select CONNECTION_NO,(WATER_CHARGES+SW_CHARGES+MTR_RENT) AS PBILLAMT FROM BSW_LEDGER WHERE MONTHYEARNEP="+monthyearp1+")B," + 
				"(select CONNECTION_NO,(WATER_CHARGES+SW_CHARGES+MTR_RENT) AS PBILLAMT1 FROM BSW_LEDGER WHERE MONTHYEARNEP="+monthyearp2+")C," + 
				"(select CONNECTION_NO,(WATER_CHARGES+SW_CHARGES+MTR_RENT) AS PBILLAMT2 FROM BSW_LEDGER WHERE MONTHYEARNEP="+monthyearp3+")D " + 
				"WHERE A.CONNECTION_NO=B.CONNECTION_NO AND B.CONNECTION_NO=C.CONNECTION_NO AND C.CONNECTION_NO=D.CONNECTION_NO AND ABS(A.CBILLAMT-((B.PBILLAMT+C.PBILLAMT1+D.PBILLAMT2)/3))>"+difrnc;
		
		//System.out.println("advanceCollectionReportRead------------\n"+sql);
		try{
			return getCustomEntityManager().createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<?> categoryTypeReportReadData(String con_category) {
		try{
			return getCustomEntityManager().createNamedQuery("ConsumerMaster.categoryTypeReportReadData").setParameter("con_category", con_category).getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<?> advanceCollectionReportReadNew(String monthyearnep) {
		
		String sql = "SELECT b.FISCAL_YEAR_MONTH,b.WARD_NO,b.DENOTED_BY,SUM(NVL(OPENING_BALANCE,0)),SUM(NVL(WATER_COST,0)),SUM(NVL(SEWERAGE_COST,0)),SUM(NVL(MRENT_COST,0)), " + 
				"SUM(NVL(b.ADJUSTMENT,0)),SUM(NVL(b.CURRENT_ADV_AMT,0)),SUM(NVL(b.CURRENT_ADV_REBATE,0)),SUM(NVL(B.CLOSING_BAL,0)) " + 
				"FROM FISCAL_YEAR_ADVANCE_BALANCE b WHERE B.FISCAL_YEAR_MONTH="+monthyearnep+ 
				" GROUP BY b.WARD_NO,b.DENOTED_BY,b.FISCAL_YEAR_MONTH ORDER BY b.WARD_NO,b.DENOTED_BY ASC";

		//System.out.println(sql);

		return getCustomEntityManager().createNativeQuery(sql).getResultList();
		
		
	}
	@Override
	public List<?> customerWiseAdvCollRepDetails(String monthyearnep) {
		String sql="SELECT A .CONNECTION_NO,M .area_no,A .ob,A .wc,A .sc,A .mr,A .na,A .cb,A .adv,A .adv_reb FROM "
				+ "( SELECT P .CONNECTION_NO AS CONNECTION_NO, l.OPEN_BALANCE AS ob, l.WATER_CHARGES AS wc, l.SW_CHARGES AS sc,l.MTR_RENT AS mr,"
				+ "l.NET_AMOUNT AS na,l.CLOSE_BALANCE AS cb,P .ADVANCE AS adv,P .ADVANCE_REBATE AS adv_reb FROM	BSW_PAYMENTS P,	BSW_LEDGER l "
				+ "WHERE 	P .CONNECTION_NO = l.CONNECTION_NO 		AND P .ADVANCE > 0		AND P .MONTH_YEAR_NEP = "+monthyearnep+"		"
						+ "AND l.MONTHYEARNEP = P .TO_MON_YEAR	) A,	BSW_MASTER M WHERE 	A .CONNECTION_NO = M .CONNECTION_NO";
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	//AND LENGTH(CONNECTION_NO)=10
	@Override
	public List<?> newConnectionReportDetails(String frmDt, String toDt, String siteCode, int catagory) 
	{
		if(catagory==1){
		//String sql1="SELECT CONNECTION_NO, NAME_ENG, AREA_NO STATUS, LAST_UPDATED_BY, LAST_UPDATED_DT FROM BSW_MASTER WHERE CONNECTION_NO LIKE '"+siteCode+"%' AND CONNECTION_NO IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER) AND LAST_UPDATED_DT >= TO_DATE('"+frmDt+"','dd/MM/yyyy')  AND LAST_UPDATED_DT <=TO_DATE('"+toDt+"','dd/MM/yyyy')  AND STATUS = '1' ORDER BY LAST_UPDATED_DT DESC";
		String sql1="SELECT m.CONNECTION_NO, m.NAME_ENG, m.AREA_NO STATUS, m.LAST_UPDATED_BY, m.LAST_UPDATED_DT, u.USER_NAME FROM BSW_MASTER m, BSW_USERS u WHERE m.LAST_UPDATED_BY=u.USER_LOGIN_NAME AND m.CONNECTION_NO LIKE '"+siteCode+"%' AND LENGTH(CONNECTION_NO)=10  AND m.CONNECTION_NO IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER WHERE APPROVE_STATUS=1) AND m.LAST_UPDATED_DT >= TO_DATE ('"+frmDt+"', 'dd/MM/yyyy') AND m.LAST_UPDATED_DT <= TO_DATE ('"+toDt+"', 'dd/MM/yyyy') AND m.STATUS = '1' ORDER BY m.LAST_UPDATED_DT DESC";
		return getCustomEntityManager().createNativeQuery(sql1).getResultList();
	  
		
		}
		else{
		String sql2="SELECT m.CONNECTION_NO, m.NAME_ENG, m.AREA_NO STATUS, m.LAST_UPDATED_BY, m.LAST_UPDATED_DT, u.USER_NAME FROM BSW_MASTER m, BSW_USERS u WHERE m.LAST_UPDATED_BY=u.USER_LOGIN_NAME AND m.CONNECTION_NO LIKE '"+siteCode+"%' AND LENGTH(CONNECTION_NO)=10 AND m.CONNECTION_NO NOT IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER WHERE APPROVE_STATUS=1) AND m.LAST_UPDATED_DT >= TO_DATE ('"+frmDt+"', 'dd/MM/yyyy') AND m.LAST_UPDATED_DT <= TO_DATE ('"+toDt+"', 'dd/MM/yyyy') AND m.STATUS = '1' ORDER BY m.LAST_UPDATED_DT DESC";
			//String sql2="SELECT CONNECTION_NO, NAME_ENG, AREA_NO STATUS, LAST_UPDATED_BY, LAST_UPDATED_DT FROM BSW_MASTER WHERE CONNECTION_NO IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER) ";
		return getCustomEntityManager().createNativeQuery(sql2).getResultList();
		   }
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<?> boarReportDetailstReadData(String fromdate, String todate) 
	{
		
		
			try{
			//System.out.println(fromdate+"fromDate");
			//System.out.println(todate+"toDate");
			
			List<Object[]> list2=null;
			
			 String query="SELECT P .CONNECTION_NO, P .AMOUNT, P .RECEIPT_NO, P .RDATE, P .COUNTERNO, p.PAY_MODE, P .CDNO, p.CDDATE, b.PENALTY, b.BOARD_ADJ_AMT, b.PENALTY_ADJ_AMT FROM BSW_PAYMENTS P , BOARD_INSTALLMENT b where P.RECEIPT_NO = b.RECEIPT_NO AND TRUNC (p.RDATE) >= TO_DATE ('"+fromdate+"', 'MM/dd/yyyy')  AND TRUNC (p.RDATE) <= TO_DATE ('"+todate+"', 'MM/dd/yyyy') AND p.TOWARDS='BOARD PAYMENT' ORDER BY P.RDATE";
			 list2=getCustomEntityManager().createNativeQuery(query).getResultList();
		
		return list2;
		}catch(Exception e){
			e.printStackTrace();
			return null;
			
		
	}
	}
	@Override
	public List<?> boardBalAdjCorrReportDetails(String myn, String col_catagory) {
		if(col_catagory.equals("BOARD")) {
			String sql1="SELECT A .CONNECTION_NO, M .NAME_ENG, M .AREA_NO, A.BOARD_ADJ,M.CON_CATEGORY, M.balance,A .SUBMIT_BY, A .SUBMIT_DATE, A .APPROVED_BY, A .APPROVED_DATE, A .APPROVED_BY1, A .APPROVED_DATE1, A .ADJ_TYPE FROM BSW_MASTER M, BSW_LEDGER l, BILL_PENALTY_ADJ A WHERE M .CONNECTION_NO = l.CONNECTION_NO AND M .CONNECTION_NO = A .CONNECTION_NO AND l.MONTHYEARNEP = "+myn+" AND A .APPROVE_STATUS =3 AND ADJ_TYPE='"+col_catagory+"' AND A .TO_MON_YEAR = "+myn+"";
			return getCustomEntityManager().createNativeQuery(sql1).getResultList();
		}
		
		String sql2="SELECT A .CONNECTION_NO, M .NAME_ENG, M .AREA_NO, A.BOARD_ADJ,M.CON_CATEGORY,M.balance, A .SUBMIT_BY, A .SUBMIT_DATE, A .APPROVED_BY, A .APPROVED_DATE, A .APPROVED_BY1, A .APPROVED_DATE1, A .ADJ_TYPE FROM BSW_MASTER M, BSW_LEDGER l, BILL_PENALTY_ADJ A WHERE M .CONNECTION_NO = l.CONNECTION_NO AND M .CONNECTION_NO = A .CONNECTION_NO AND l.MONTHYEARNEP = "+myn+" AND A .APPROVE_STATUS =1 AND ADJ_TYPE='"+col_catagory+"' AND A .TO_MON_YEAR = "+myn+"";
		

		return getCustomEntityManager().createNativeQuery(sql2).getResultList();
	}
	
	@Override
	public List<?> newConnectionReportDetailsCat(String frmDt, String toDt,
			String siteCode, int catagory, String concategory) {
		if(catagory==1){
			//String sql1="SELECT CONNECTION_NO, NAME_ENG, AREA_NO STATUS, LAST_UPDATED_BY, LAST_UPDATED_DT FROM BSW_MASTER WHERE CONNECTION_NO LIKE '"+siteCode+"%' AND CONNECTION_NO IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER) AND LAST_UPDATED_DT >= TO_DATE('"+frmDt+"','dd/MM/yyyy')  AND LAST_UPDATED_DT <=TO_DATE('"+toDt+"','dd/MM/yyyy')  AND STATUS = '1' ORDER BY LAST_UPDATED_DT DESC";
			String sql1="SELECT m.CONNECTION_NO, m.NAME_ENG, m.AREA_NO STATUS, m.LAST_UPDATED_BY, m.LAST_UPDATED_DT, u.USER_NAME FROM BSW_MASTER m, BSW_USERS u WHERE m.LAST_UPDATED_BY=u.USER_LOGIN_NAME AND m.CONNECTION_NO LIKE '"+siteCode+"%' AND LENGTH(CONNECTION_NO)=10  AND m.CONNECTION_NO IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER WHERE APPROVE_STATUS=1) AND TRUNC(m.LAST_UPDATED_DT) >= TO_DATE ('"+frmDt+"', 'dd/MM/yyyy') AND TRUNC(m.LAST_UPDATED_DT) <= TO_DATE ('"+toDt+"', 'dd/MM/yyyy') AND m.STATUS = '1' AND m.CON_CATEGORY='"+concategory+"' ORDER BY m.LAST_UPDATED_DT DESC";
			return getCustomEntityManager().createNativeQuery(sql1).getResultList();
		  
			
			}
			else{
			String sql2="SELECT m.CONNECTION_NO, m.NAME_ENG, m.AREA_NO STATUS, m.LAST_UPDATED_BY, m.LAST_UPDATED_DT, u.USER_NAME FROM BSW_MASTER m, BSW_USERS u WHERE m.LAST_UPDATED_BY=u.USER_LOGIN_NAME AND m.CONNECTION_NO LIKE '"+siteCode+"%' AND LENGTH(CONNECTION_NO)=10 AND m.CONNECTION_NO NOT IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER WHERE APPROVE_STATUS=1) AND TRUNC(m.LAST_UPDATED_DT) >= TO_DATE ('"+frmDt+"', 'dd/MM/yyyy') AND TRUNC(m.LAST_UPDATED_DT) <= TO_DATE ('"+toDt+"', 'dd/MM/yyyy') AND m.STATUS = '1' and m.CON_CATEGORY='"+concategory+"' ORDER BY m.LAST_UPDATED_DT DESC";
				//String sql2="SELECT CONNECTION_NO, NAME_ENG, AREA_NO STATUS, LAST_UPDATED_BY, LAST_UPDATED_DT FROM BSW_MASTER WHERE CONNECTION_NO IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER) ";
			return getCustomEntityManager().createNativeQuery(sql2).getResultList();
			   }
	}
	@Override
	public List<?> categoryNewConnectionReportDetailsCat(String frmDt,
			String toDt, String siteCode, String conType, String concategory,
			String pipesize) {
		
			//String sql1="SELECT CONNECTION_NO, NAME_ENG, AREA_NO STATUS, LAST_UPDATED_BY, LAST_UPDATED_DT FROM BSW_MASTER WHERE CONNECTION_NO LIKE '"+siteCode+"%' AND CONNECTION_NO IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER) AND LAST_UPDATED_DT >= TO_DATE('"+frmDt+"','dd/MM/yyyy')  AND LAST_UPDATED_DT <=TO_DATE('"+toDt+"','dd/MM/yyyy')  AND STATUS = '1' ORDER BY LAST_UPDATED_DT DESC";
			//String sql1="SELECT m.CONNECTION_NO, m.NAME_ENG, m.AREA_NO STATUS, m.NEW_CON_APPROVED_BY, m.NEW_CON_APPROVED_DATE, u.USER_NAME,m.PIPE_SIZE FROM BSW_MASTER m, BSW_USERS u WHERE m.NEW_CON_APPROVED_BY=u.USER_LOGIN_NAME AND m.CONNECTION_NO LIKE '"+siteCode+"%' AND LENGTH(CONNECTION_NO)=10  AND m.CONNECTION_NO NOT IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER WHERE APPROVE_STATUS=1) AND TRUNC(m.NEW_CON_APPROVED_DATE) >= TO_DATE ('"+frmDt+"', 'yyyy/MM/dd') AND TRUNC(m.NEW_CON_APPROVED_DATE) <= TO_DATE ('"+toDt+"', 'yyyy/MM/dd') AND m.STATUS = '1' AND m.CON_CATEGORY='"+concategory+"' AND m.con_type='Metered' ORDER BY m.NEW_CON_APPROVED_DATE DESC";
		
		
		
	        String sql3="SELECT M .CONNECTION_NO, M. con_type, M. con_category, M .PIPE_SIZE FROM BSW_MASTER M, BSW_USERS U WHERE M .LAST_UPDATED_BY = U .USER_LOGIN_NAME AND M .CONNECTION_NO LIKE '"+siteCode+"%' AND LENGTH(CONNECTION_NO) = 10 AND M .CONNECTION_NO not IN ( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER WHERE APPROVE_STATUS = 1) AND TRUNC (M .LAST_UPDATED_DT) >= TO_DATE ('"+frmDt+"', 'yyyy/MM/dd') AND TRUNC (M .LAST_UPDATED_DT) <= TO_DATE ('"+toDt+"', 'yyyy/MM/dd') AND M .STATUS = '1' GROUP BY M.CON_TYPE,M.PIPE_SIZE,M.CON_CATEGORY,M.CONNECTION_NO ORDER BY CON_TYPE"	;
			return getCustomEntityManager().createNativeQuery(sql3).getResultList();
		  
			
		
			/*else{
			String sql2="SELECT m.CONNECTION_NO, m.NAME_ENG, m.AREA_NO STATUS, m.NEW_CON_APPROVED_BY, m.NEW_CON_APPROVED_DATE, u.USER_NAME,m.PIPE_SIZE FROM BSW_MASTER m, BSW_USERS u WHERE m.NEW_CON_APPROVED_BY=u.USER_LOGIN_NAME AND m.CONNECTION_NO LIKE '"+siteCode+"%' AND LENGTH(CONNECTION_NO)=10 AND m.CONNECTION_NO NOT IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER WHERE APPROVE_STATUS=1) AND TRUNC(m.NEW_CON_APPROVED_DATE) >= TO_DATE ('"+frmDt+"', 'yyyy/MM/dd') AND TRUNC(m.NEW_CON_APPROVED_DATE) <= TO_DATE ('"+toDt+"', 'yyyy/MM/dd') AND m.STATUS = '1' and m.CON_CATEGORY='"+concategory+"' AND m.con_type='Unmetered' ORDER BY m.NEW_CON_APPROVED_DATE DESC";
				//String sql2="SELECT CONNECTION_NO, NAME_ENG, AREA_NO STATUS, LAST_UPDATED_BY, LAST_UPDATED_DT FROM BSW_MASTER WHERE CONNECTION_NO IN( SELECT CONNECTION_NO FROM BSW_APPROVE_MASTER) ";
			return getCustomEntityManager().createNativeQuery(sql2).getResultList();
			   }*/
	
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<MeterChangeDetailsEntity> meterChangeDetailsReport() {
		return getCustomEntityManager().createNamedQuery("MeterChangeDetailsEntity.getMeterChangeDetails").getResultList();
	}
	@Override
	public List<?> boardRevenueReportRead(String fromdate, String todate, String counter_no) {
		String sql="";
		if("All".equalsIgnoreCase(counter_no)){
			 sql="select a.ward,NVL(a.arrears,0),NVL(a.wc,0),NVL(a.sc,0),NVL(a.mr,0),NVL((a.arrears+a.wc+a.sc+a.mr),0),NVL(a.pen,0),NVL(a.reb,0),NVL(a.mc,0),NVL((a.arrears+a.wc+a.sc+a.mr+a.pen+a.mc-a.reb),0),NVL(a.total,0),NVL(a.advance,0),NVL(a.advancerebate,0),NVL(((a.total+a.advance+a.advancerebate)-(a.arrears+a.wc+a.sc+a.mr+a.pen+a.mc-a.reb)),0),a.redate from( "+
					" select NVL(SUM(WATER_CHARGES),0) as wc,NVL(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,NVL(SUM(METER_RENT),0) as mr,(NVL(SUM(PENALTY),0)+NVL(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,NVL(SUM(REBATE),0) as reb, "+ 
					" coalesce(SUM(AMOUNT),0) as total, "+
					" coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,WARD_NO as ward,trunc(RDATE) as redate from BSW_PAYMENTS where TRUNC(RDATE)> = to_date('"+fromdate+"','dd-MM-yyyy') AND TRUNC(RDATE)< = to_date('"+todate+"','dd-MM-yyyy') AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BOARD PAYMENT') GROUP BY WARD_NO, trunc(RDATE))a" ;

		}else{
			 sql="select a.ward,NVL(a.arrears,0),NVL(a.wc,0),NVL(a.sc,0),NVL(a.mr,0),NVL((a.arrears+a.wc+a.sc+a.mr),0),NVL(a.pen,0),NVL(a.reb,0),NVL(a.mc,0),NVL((a.arrears+a.wc+a.sc+a.mr+a.pen+a.mc-a.reb),0),NVL(a.total,0),NVL(a.advance,0),NVL(a.advancerebate,0),NVL(((a.total+a.advance+a.advancerebate)-(a.arrears+a.wc+a.sc+a.mr+a.pen+a.mc-a.reb)),0),a.redate from( "+
					" select NVL(SUM(WATER_CHARGES),0) as wc,NVL(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,NVL(SUM(METER_RENT),0) as mr,(NVL(SUM(PENALTY),0)+NVL(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,NVL(SUM(REBATE),0) as reb, "+ 
					" coalesce(SUM(AMOUNT),0) as total, "+
					" coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears,WARD_NO as ward,trunc(RDATE) as redate from BSW_PAYMENTS where TRUNC(RDATE)> = to_date('"+fromdate+"','dd-MM-yyyy') AND TRUNC(RDATE)< = to_date('"+todate+"','dd-MM-yyyy') AND COUNTERNO="+Integer.parseInt(counter_no)+" AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BOARD PAYMENT') GROUP BY WARD_NO,trunc(RDATE))a" ;

		}
		
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
		
	}
	
	
	
}

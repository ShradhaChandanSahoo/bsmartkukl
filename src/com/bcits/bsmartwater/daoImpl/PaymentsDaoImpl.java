package com.bcits.bsmartwater.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bsmartwater.dao.PaymentsDao;
import com.bcits.bsmartwater.model.PaymentEntity;

@Repository
public class PaymentsDaoImpl extends GenericDAOImpl<PaymentEntity> implements PaymentsDao
{

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> getCashDetails(String conectionVal, String receiptVal,
			String payTowardsVal, String amount, String nepalDate,
			String englishDate, String bankname, String chequeNo,
			String chequedateNepal, String paymode, String chequedateEng,
			String wardno, HttpServletRequest request) 
	{
		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName"); 
		 List<Object[]> list=null;
		try 
		{
			String data="";
			List<String> listData=new ArrayList<String>();
			StringBuilder sb=new StringBuilder();
			StringBuilder ward=new StringBuilder();
			StringBuilder wardQuery=new StringBuilder();
			sb.append("SELECT CONNECTION_NO,RECEIPT_NO,RDATE,CDNO,PAY_MODE,AMOUNT,BANKNAME,TOWARDS from "+schemaName+".BSW_PAYMENTS WHERE");
					if(!conectionVal.equalsIgnoreCase("empty"))
					{
						data="CONNECTION_NO like '"+conectionVal+"'";
						listData.add(data);
					}
					if(!receiptVal.equalsIgnoreCase("empty"))
					{
						data="RECEIPT_NO like '"+receiptVal+"'";
						listData.add(data);
					}
					if(!payTowardsVal.equalsIgnoreCase("empty"))
					{
						data="TOWARDS like '"+payTowardsVal+"'";
						listData.add(data);
					}
					if(!amount.equalsIgnoreCase("empty"))
					{
						data="AMOUNT like '"+amount+"'";
						listData.add(data);
					}
					if(!nepalDate.equalsIgnoreCase("empty"))
					{
						data="nepalDate like '"+nepalDate+"'";
						listData.add(data);
					}
					if(!englishDate.equalsIgnoreCase("empty"))
					{
						data="TO_CHAR(RDATE,'DD-MM-YYYY') like '"+englishDate+"'";
						listData.add(data);
					}
					if(!bankname.equalsIgnoreCase("empty"))
					{
						data="BANKNAME like '"+bankname+"'";
						listData.add(data);
					}
					if(!chequeNo.equalsIgnoreCase("empty"))
					{
						data="ChequeNo like '"+chequeNo+"'";
						listData.add(data);
					}
					if(!paymode.equalsIgnoreCase("empty"))
					{
						data="PAY_MODE like '"+paymode+"'";
						listData.add(data);
					}
					if(!chequedateEng.equalsIgnoreCase("empty"))
					{
						data="CDDATE like '"+chequedateEng+"'";
						listData.add(data);
					}
					if(!wardno.equalsIgnoreCase("empty"))
					{
						ward.append("WARD_NO like '"+wardno+"'");
					}
					for (int i = 0; i < listData.size(); i++) 
					{
						if(i!=listData.size()-1)
		                {
							sb.append(" "+listData.get(i)+" "+"OR");
		                }
		                else
		                {
		                	sb.append(" "+listData.get(i));
		                } 
					}
					wardQuery.append("SELECT TRIM(AA.CONNECTION_NO)  ,AA.RECEIPT_NO  ,AA.RDATE,COALESCE(AA.CDNO,' ')  ,AA.PAY_MODE  ,AA.AMOUNT  ,COALESCE(BANKNAME, ' ')AS BANKNAME  ,AA.TOWARDS,COALESCE((SELECT WARD_NO FROM "+schemaName+".BSW_MASTER WHERE CONNECTION_NO=AA.CONNECTION_NO "+ward+"),' ')AS WARDNO FROM (");
					final String searchQuery=wardQuery+""+sb+" "+")AA";
					//System.out.println(searchQuery);
					list=entityManager.createNativeQuery(searchQuery).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
		
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Object[]> searchByConnectionNo(String connectionNo,String schema, HttpServletRequest request)
	{
		String query="SELECT TRIM(CONNECTION_NO),RECEIPT_NO,TO_CHAR(RDATE,'DD-MM-YYYY')AS RDATE,COALESCE(CDNO,' '),PAY_MODE,AMOUNT,COALESCE(BANKNAME,' '),TOWARDS,TO_CHAR(RDATE,'DDMMYYYY')AS RDATE1,MONTH_YEAR_NEP FROM"+" "+schema+"."+"BSW_PAYMENTS WHERE TRIM(CONNECTION_NO) LIKE '"+connectionNo+"' AND CANCELLEDREMARKS IS NULL ORDER BY ID DESC";
		List<Object[]> list=null;
		try 
		{
			list=entityManager.createNativeQuery(query).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Object[]> wrongPostingData(String connectionNo, String rdate,String schema,HttpServletRequest request)
	{
		String query="SELECT TRIM(CONNECTION_NO),RECEIPT_NO,TO_CHAR(RDATE,'MM/DD/YYYY')AS RDATE,COALESCE(CDNO,' '),PAY_MODE,AMOUNT,COALESCE(BANKNAME,' '),TOWARDS,(SELECT 'CR/'||MAX(ID) FROM "+schema+".BSW_PAYMENTS)AS ADJUSTMENTNO,TO_CHAR(CDDATE,'YYYY-MM-DD') FROM"+" "+schema+"."+"BSW_PAYMENTS WHERE TRIM(CONNECTION_NO) LIKE '"+connectionNo+"' AND TO_CHAR(RDATE,'DDMMYYYY') LIKE '"+rdate+"'";
		//String query="SELECT  AA.*,(SELECT 'CR/'||MAX(ID) FROM "+schema+".BSWPAYMENTS)AS ADJUSTMENTNO FROM"+" "+schema+"."+"BSWPAYMENTS AA WHERE TRIM(CONNECTION_NO) LIKE '"+connectionNo+"' AND TO_CHAR(RDATE,'DDMMYYYY') LIKE '"+rdate+"'";
		List<Object[]> list=null;
		try 
		{
			list=entityManager.createNativeQuery(query).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Object updateWrongPost(Map<String, String> map, String schema,HttpServletRequest request)
	{


		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName"); 
		

		/*String sql="UPDATE "+schema+".BSW_PAYMENTS " +" "+
				"SET  CONNECTION_NO ='"+map.get("fromConnectionNo")+"', RDATE =TO_TIMESTAMP('"+map.get("engRdate")+"', 'YYYY-MM-DD HH24:MI:SS'),  AMOUNT ='"+Integer.parseInt(map.get("receiptAmnt"))+"',"
						+ " " +" "+
				"RECEIPT_NO ='"+map.get("receiptNo")+"', BRANCH_CODE ='MANUAL',  PAY_MODE ='"+map.get("payMode")+"',  TOWARDS ='"+map.get("towards")+"',  REMARKS ="+map.get("fromRemarks")+", " +" "+
				"EDATE =TO_TIMESTAMP('2015-06-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),  USER_ID ='BCITS'," +" "+
				"UPDATED_BY ='"+request.getSession(false).getAttribute("userName")+"'";*/
		int toConnectionUpdate=0;
		int fromConnectionUpdate=0;
		try 
		{
			if(map.get("payMode").equalsIgnoreCase("1"))
			{
				String rdateNew=new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(map.get("engRdate")));
				String adateNew=new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(map.get("engAdate")));
				String eDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				String fromConnectionSql="INSERT INTO  "+schemaName+".BSW_PAYMENTS " +" "+
				" ( CONNECTION_NO , "
				+ " RDATE , "
				+ " AMOUNT ,  "
				+ "RECEIPT_NO , "
				+ " BRANCH_CODE ,  "
				+ "PAY_MODE ,"
				+ "  TOWARDS ,  "
				+ "REMARKS ,  "
				+ "EDATE ,  "
				+ "USER_ID , "
				+ " UPDATED_BY ) " +" "+
				"VALUES " +
				"('"+map.get("fromConnectionNo")+"',"
				+ "TO_TIMESTAMP('"+rdateNew+"', 'YYYY-MM-DD HH24:MI:SS'),"
				+ ""+Integer.parseInt(map.get("receiptAmnt"))+","
				+ "'"+map.get("receiptNo")+"',"
				+ "'MANUAL','"+map.get("payMode")+"',"
				+ "'"+map.get("towards")+"',"
				+ "'"+map.get("fromRemarks")+"',"
				+ "TO_TIMESTAMP('"+eDate+"', 'YYYY-MM-DD HH24:MI:SS'),"
				+ "'"+request.getSession(true).getAttribute("userName")+"',"
				+ "'"+request.getSession(true).getAttribute("userName")+"')";
				
				 fromConnectionUpdate=entityManager.createNativeQuery(fromConnectionSql).executeUpdate();
				
				String toConnectionSql="INSERT INTO  "+schemaName+".BSW_PAYMENTS " +" "+
						" ( CONNECTION_NO ,  RDATE ,  AMOUNT ,  RECEIPT_NO ,  BRANCH_CODE ,  PAY_MODE ,  TOWARDS ,  REMARKS ,  EDATE ,  USER_ID ,  UPDATED_BY ) " +" "+
						"VALUES " +
						"('"+map.get("toConnectionNo")+"',TO_TIMESTAMP('"+adateNew+"', 'YYYY-MM-DD HH24:MI:SS'),"+Integer.parseInt(map.get("adjustmentAmnt"))+",'"+map.get("adjustmentNo")+"','MANUAL','"+map.get("payMode")+"','"+map.get("towards")+"','"+map.get("toRemarks")+"',TO_TIMESTAMP('"+eDate+"', 'YYYY-MM-DD HH24:MI:SS'),'"+request.getSession(true).getAttribute("userName")+"','"+request.getSession(true).getAttribute("userName")+"')";
				
			 toConnectionUpdate=entityManager.createNativeQuery(toConnectionSql).executeUpdate();
			}
			if(map.get("payMode").equalsIgnoreCase("2")||map.get("payMode").equalsIgnoreCase("3"))
			{
				String rdateNew=new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(map.get("engRdate")));
				String adateNew=new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(map.get("engAdate")));
				
				String cdDate=new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(map.get("engAdate")));
				
				String eDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				String fromConnectionSql="INSERT INTO  "+schemaName+".BSW_PAYMENTS " +" "+
				" ( CONNECTION_NO ,  RDATE ,  AMOUNT ,  RECEIPT_NO ,  BRANCH_CODE ,  PAY_MODE ,  TOWARDS ,  REMARKS ,  EDATE ,  USER_ID ,  UPDATED_BY,CDNO,BANKNAME,CDDATE ) " +" "+
				"VALUES " +
				"('"+map.get("fromConnectionNo")+"',TO_TIMESTAMP('"+rdateNew+"', 'YYYY-MM-DD HH24:MI:SS'),"+Integer.parseInt(map.get("receiptAmnt"))+",'"+map.get("receiptNo")+"','MANUAL','"+map.get("payMode")+"','"+map.get("towards")+"','"+map.get("fromRemarks")+"',TO_TIMESTAMP('"+eDate+"', 'YYYY-MM-DD HH24:MI:SS'),'BCITS','"+request.getSession().getAttribute("userName")+"', '"+map.get("cdNo")+"','"+map.get("bankName")+"' , TO_TIMESTAMP('"+cdDate+"', 'YYYY-MM-DD HH24:MI:SS'))";
				 fromConnectionUpdate=entityManager.createNativeQuery(fromConnectionSql).executeUpdate();
				
				//ent
				
				String toConnectionSql="INSERT INTO  "+schemaName+".BSW_PAYMENTS " +" "+
						" ( CONNECTION_NO ,  RDATE ,  AMOUNT ,  RECEIPT_NO ,  BRANCH_CODE ,  PAY_MODE ,  TOWARDS ,  REMARKS ,  EDATE ,  USER_ID ,  UPDATED_BY,CDNO,BANKNAME,CDDATE ) " +" "+
						"VALUES " +
						"('"+map.get("toConnectionNo")+"',TO_TIMESTAMP('"+adateNew+"', 'YYYY-MM-DD HH24:MI:SS'),"+Integer.parseInt(map.get("adjustmentAmnt"))+",'"+map.get("adjustmentNo")+"','MANUAL','"+map.get("payMode")+"','"+map.get("towards")+"','"+map.get("toRemarks")+"',TO_TIMESTAMP('"+eDate+"', 'YYYY-MM-DD HH24:MI:SS'),'BCITS','"+request.getSession().getAttribute("userName")+"','"+map.get("cdNo")+"','"+map.get("bankName")+"' , TO_TIMESTAMP('"+cdDate+"', 'YYYY-MM-DD HH24:MI:SS'))";
				
				 toConnectionUpdate=entityManager.createNativeQuery(toConnectionSql).executeUpdate();
			}
		
			if(fromConnectionUpdate>0 && toConnectionUpdate>0)
			{
				return "success";
			}
			else
			{
				return "failure";
			}
			
		}
		catch (Exception e)
		{
			return "error while updating";
		}
	}

	@Override
	public List<?> viewPaymentHistory(String connectionNo) {
	
		return getCustomEntityManager().createNamedQuery("PaymentEntity.viewPaymentHistory").setParameter("connectionNo", connectionNo.trim().toUpperCase()).getResultList();
	}


	@Override
	public PaymentEntity getLatestBillReceipt(String connectionNo) {
		
		try{
			return getCustomEntityManager().createNamedQuery("PaymentEntity.getLatestBillReceipt",PaymentEntity.class).setParameter("connectionNo", connectionNo).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public double getSumOfAmoutBWPcashposAndCashPosDate(String connection_no,Date pcashPostedDate, String schemaLocation) {
		try{
		 
			String sql="SELECT CASH_POSTED_DATE FROM BSW_CASH_MASTER";
			Object obj=getCustomEntityManager().createNativeQuery(sql).getSingleResult();
			
			if(pcashPostedDate!=null){
				
				String sql1="select SUM(NVL(AMOUNT,0)) from BSW_PAYMENTS WHERE CONNECTION_NO='"+connection_no+"' AND RDATE>TO_DATE('"+new SimpleDateFormat("dd/MM/yyyy").format(pcashPostedDate)+"','dd/MM/yyyy') AND RDATE<=TO_DATE('"+new SimpleDateFormat("dd/MM/yyyy").format((Date)obj)+"','dd/MM/yyyy')";
				Object obj1=getCustomEntityManager().createNativeQuery(sql1).getSingleResult();
				return ((java.math.BigDecimal)obj1).doubleValue();
			}
			else{
				return 0;
			}
		    //return (double) getCustomEntityManager().createNamedQuery("PaymentEntity.getSumOfAmoutBWPcashposAndCashPosDate").setParameter("connectionNo", connection_no).setParameter("fdate", new SimpleDateFormat("dd/MM/yyyy").format(pcashPostedDate)).setParameter("tdate", new SimpleDateFormat("dd/MM/yyyy").format((Date)obj)).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}


	@Override
	public Integer getpaymentMaxId() {
		return getCustomEntityManager().createNamedQuery("PaymentEntity.getpaymentMaxId",Integer.class).getSingleResult();
	}

	@Override
	public PaymentEntity getpaymentBasedOnId(Integer id) {
		return getCustomEntityManager().createNamedQuery("PaymentEntity.getpaymentBasedOnId",PaymentEntity.class).setParameter("id",(Integer)id).getSingleResult();
	}


	@Override
	public List<?> getTodayCashSummery(Integer counterNo) {
		return getCustomEntityManager().createNamedQuery("PaymentEntity.getTodayCashSummery").setParameter("counterNo",""+counterNo).getResultList();
	}

	@Override
	public PaymentEntity getPaymentEntityBasedOnReceiptNo(String receiptNo) {
		try{
		return getCustomEntityManager().createNamedQuery("PaymentEntity.getPaymentEntityBasedOnReceiptNo",PaymentEntity.class).setParameter("receiptNo",receiptNo).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getPaymentsDataByConnectionNum(String connId) {
		List<PaymentEntity> paymets = null;
		try {
			paymets = getCustomEntityManager().createNamedQuery("PaymentEntity.getPaymentsDataByConnectionNum").setParameter("connId", connId.trim().toUpperCase()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymets;
	}
	@Override
	public List<?> getdataforDayClose(Integer counterNo, Date rDate) {
		return getCustomEntityManager().createNamedQuery("PaymentEntity.getdataforDayClose").setParameter("counterNo",""+counterNo).setParameter("rdate", rDate).getResultList();
	}


	@Override
	public Date getMaxDayForDayClose(Integer counter_no) {
		return getCustomEntityManager().createNamedQuery("PaymentEntity.getMaxDayForDayClose",Date.class).setParameter("counter_no",""+counter_no).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override

	public List<String> getGraphicalViewDataForMonth(String siteCode) {
		return getCustomEntityManager().createNamedQuery("PaymentEntity.getGraphicalViewDataForMonth").setParameter("siteCode", siteCode).getResultList();

	}

	@Override
	public String getTotalCollection(String siteCode) {
		double totalCollection =0;
		//System.out.println(siteCode+"---siteCode");
		try {
			totalCollection = (double) (getCustomEntityManager().createNamedQuery("PaymentEntity.getTotalCollection").setParameter("siteCode", siteCode).getSingleResult());
		} catch (Exception e) {
			
			return "0";
		}
		return ""+totalCollection;
	}

	@Override
	public Double getDayClosePaymentDetails(Integer counterNo, String date) {
		Double totalPay = null;
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		String d="";
		try {
			d = sdf2.format(sdf1.parse(date));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		String sql = "select sum(AMOUNT) from BSW_PAYMENTS where RECORDTYPE = 'ORIGINAL' and TO_CHAR(RDATE,'DD/MM/YYYY') = '"+d+"' and COUNTERNO = "+counterNo+" ";
		//String sql = "select sum(AMOUNT) from TRIPUR_1115.BSW_PAYMENTS where RECORDTYPE = 'ORIGINAL' and TO_CHAR(RDATE,'DD/MM/YYYY') = '"+d+"' ";
		try {
			//System.out.println(""+sdf2.format(sdf1.parse(date)));
			/*totalPay = String.valueOf(getCustomEntityManager().createNamedQuery("PaymentEntity.getDayClosePaymentDetails").setParameter("counterNo", counterNo).setParameter("rDate",date).getSingleResult());*/
			totalPay = Double.valueOf(String.valueOf((getCustomEntityManager().createNativeQuery(sql).getSingleResult())));
			//System.out.println(" totalPay ===> "+totalPay);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(" sql ===> "+sql);
		return totalPay;
	}

	@Override
	public List<?> getDayClosePaymentDetails1(Integer counterNo, String date) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
		String d="";
		try {
			d = sdf2.format(sdf1.parse(date));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		List<?> lst = null;
	
		String sql = "select nvl(a.wc,0),nvl(a.sc,0),nvl(a.mc,0),nvl(a.mr,0),nvl(a.pen,0),nvl(a.reb,0),nvl(a.total,0),nvl(a.advance,0) ,nvl((a.total+a.advance+a.advancerebate),0),nvl(a.advancerebate,0),nvl(((a.total-(a.wc+a.sc+a.mc+a.mr+a.pen-a.reb))-a.advance),0),nvl((a.wc+a.sc+a.mc+a.mr+a.pen-a.reb),0),nvl(a.arrears,0) from( "+
				" select nvl(SUM(WATER_CHARGES),0) as wc,nvl(SUM(SW_CHARGES),0) as sc,coalesce(SUM(MISCELLANEOUS_COST),0) as mc,nvl(SUM(METER_RENT),0) as mr,(nvl(SUM(PENALTY),0)+nvl(SUM(PENALTY_ADJ_AMOUNT),0)) as pen,nvl(SUM(REBATE),0) as reb, "+ 
				" coalesce(SUM(AMOUNT),0) as total, "+
				" coalesce(SUM(ADVANCE),0) as advance,coalesce(SUM(ADVANCE_REBATE),0) as advancerebate,coalesce(SUM(OLD_BALANCE),0) as oldbalance,coalesce(SUM(FRECAMOUNT),0) as frecamount,coalesce(SUM(BALANCE_AMOUNT),0) as arrears from BSW_PAYMENTS where TO_CHAR(RDATE,'dd/MM/yyyy') = '"+d+"' and COUNTERNO ='"+counterNo+"' AND CANCELLEDREMARKS IS NULL AND TOWARDS IN('BILL PAYMENT','BOARD PAYMENT'))a" ;

		
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
	public List<?> getPaymentEntityBasedOnReceiptNo(String date,Integer counter_no) {
		
			
			return getCustomEntityManager().createNamedQuery("PaymentEntity.getPaymentEntityBasedOnReceiptNoCounterNo").setParameter("date",date).setParameter("counterno",""+counter_no).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentEntity> getPaymentDataByConnNumNew(String connId,String schemaName) {
		List<PaymentEntity> paymets = null;
		try {
			paymets = getCustomEntityManager(schemaName).createNamedQuery("PaymentEntity.getPaymentsDataByConnectionNum").setParameter("connId", connId.trim().toUpperCase()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymets;
	}

	@Override
	public long getMaxReceiptNo(String receiptno) {
		try{
			return Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("PaymentEntity.getMaxReceiptNo").setParameter("receiptNo",receiptno).getSingleResult()));
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public List<?> viewMyPaymentHistory(Integer counterno, String rdate) {
		try{
			//System.out.println(rdate);
			return getCustomEntityManager().createNamedQuery("PaymentEntity.viewMyPaymentHistory").setParameter("counterno", ""+counterno).setParameter("rdate", rdate).getResultList();
		}catch(Exception e){
			e.printStackTrace();
			return null;
			
		}
	}

	@Override
	public PaymentEntity getPaymentEntityBasedOnReceiptNo(String receiptNo,String fromConnectionNo) {
		
		try{
			return getCustomEntityManager().createNamedQuery("PaymentEntity.getPaymentEntityBasedOnReceiptNoConNo",PaymentEntity.class).setParameter("receiptNo", receiptNo).setParameter("connectionNo", fromConnectionNo).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public long getMaxReceiptNo(String receiptno, Integer counterNo) {
		try{
			return Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("PaymentEntity.getMaxReceiptNoNew").setParameter("receiptNo",receiptno).setParameter("counterno", ""+counterNo).getSingleResult()));
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public long getTotalPaymentsByConNoMYN(String connectionNo,Integer to_mon_year) {
			try{
				return (long) getCustomEntityManager().createNamedQuery("PaymentEntity.getTotalPaymentsByConNoMYN").setParameter("connectionNo",connectionNo.trim().toUpperCase()).setParameter("to_mon_year", to_mon_year).getSingleResult();
			}catch(Exception e){
				e.printStackTrace();
				return 0;
			}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getLastPaymentDetailsByCNNoMYN(String connectionNo,Integer to_mon_year) {
		
		String query="SELECT * FROM(SELECT ID,CONNECTION_NO,RECEIPT_NO,RDATE,AMOUNT,PENALTY,REBATE,RBALANCE, row_number() over (order by RDATE desc) RNum "
				+ " FROM BSW_PAYMENTS WHERE CONNECTION_NO='"+connectionNo+"' AND TO_MON_YEAR="+to_mon_year+" AND CANCELLEDREMARKS IS NULL"
				+ ")x"
				+ " WHERE RNum =2";
		List<Object[]> list=null;
		try 
		{
			list=getCustomEntityManager().createNativeQuery(query).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getLastPaymentDetailsByCNNoMYN1(String connectionNo,Integer to_mon_year) {
		
		String query="SELECT * FROM(SELECT ID,CONNECTION_NO,RECEIPT_NO,RDATE,AMOUNT,PENALTY,REBATE,RBALANCE,MISCELLANEOUS_COST, row_number() over (order by RDATE desc) RNum "
				+ " FROM BSW_PAYMENTS WHERE CONNECTION_NO='"+connectionNo+"' AND TO_MON_YEAR="+to_mon_year+" AND CANCELLEDREMARKS IS NULL"
				+ ")x"
				+ " WHERE RNum =1";
		List<Object[]> list=null;
		try 
		{
			list=getCustomEntityManager().createNativeQuery(query).getResultList();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<?> generateDayCloseMisc(Integer counterNo, String date) {
		


		String sql = "SELECT"
				+ " NVL (A .NCT, 0),"
				+ " NVL (A .NCD, 0),"
				+ " NVL (A .NC_IN, 0),"
				+ " NVL (A .MVAL, 0),"
				+ " NVL (A .TH, 0),"
				+ " NVL (A .NCH, 0),"
				+ " NVL (A .ILLG, 0),NVL (A .MISC, 0),NVL (A .ADV, 0),"
				+ " NVL (A .AMT, 0),"
			    + counterNo+","
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
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Hole Change') THEN OTHER_PAID_AMT END,0)) AS HC,"
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Hole Maintenance') THEN OTHER_PAID_AMT END,0)) AS HM, "
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Application Charge') THEN OTHER_PAID_AMT END,0)) AS AC, "
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Tender/Sealed Quotation') THEN OTHER_PAID_AMT END,0)) AS TSQ, "
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Card Charge') THEN OTHER_PAID_AMT END,0)) AS CC, "
						+ " SUM(NVL(CASE WHEN OTHER_PAID_TYPE IN('Others') THEN OTHER_PAID_AMT END,0)) AS OTH "
						
					+ " FROM"
					 + " BSW_PAYMENTS WHERE TRUNC (RDATE)= TO_DATE ('"+date+"', 'MM/DD/YYYY') AND CANCELLEDREMARKS IS NULL AND TOWARDS ='MISCELLANEOUS' AND COUNTERNO='"+counterNo+"') A";
		
		//System.out.println("MISCELLANEOUS------------\n"+sql);
		try{
			return getCustomEntityManager().createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	
	@Override
	public List<?> getTransactionDataByConnNum(String connId, int myn) {
		List<?> paymets = null;
		try {
			paymets = getCustomEntityManager().createNamedQuery("PaymentEntity.getTransactionDataByConnNum").setParameter("connId", connId.trim().toUpperCase()).setParameter("to_mon_year", myn).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paymets;
	}

	@Override
	public List<?> getPaymentEntityBasedOnReceiptNoNew(String date, Integer counter_no) {
		String qry="SELECT * FROM "+
				"(SELECT p.CONNECTION_NO,m.NAME_ENG,p.RECEIPT_NO,p.RDATE,p.BILL_AMOUNT, "+
				"p.AMOUNT,p.TOWARDS,p.PAY_MODE,p.CANCELLEDREMARKS, "+
				"RANK() OVER (PARTITION BY p.CONNECTION_NO ORDER BY p.RDATE DESC) ranking "+
				"FROM BSW_PAYMENTS p, BSW_MASTER m WHERE UPPER(p.CONNECTION_NO)=upper(m.CONNECTION_NO) AND TO_CHAR(p.RDATE,'dd/MM/yyyy')=to_char(to_date('"+date+"', 'dd/MM/yyyy'),'dd/MM/yyyy') "+
				"AND p.COUNTERNO='"+counter_no+"' AND p.RECORDTYPE='ORIGINAL' AND p.TOWARDS='BILL PAYMENT' AND p.CANCELLEDREMARKS IS NULL)A WHERE A.ranking=1";
		try {
			return getCustomEntityManager().createNativeQuery(qry).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public PaymentEntity getMisReceiptDetails(String recptNo, Date date)
	{
		/*String qry="select * from BSW_PAYMENTS WHERE trunc(RDATE)=TO_DATE('"+ date+"', 'yyyy-mm-dd') and TOWARDS='MISCELLANEOUS' AND REMARKS is NULL and RECEIPT_NO='" +recptNo+"'";
		
		try {
			return getCustomEntityManager().createNativeQuery(qry).getResultList();
		} catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}*/
		
		try{
			return (PaymentEntity) getCustomEntityManager().createNamedQuery("PaymentEntity.getPaymentEntityDetails").setParameter("receiptNo",recptNo).setParameter("rdate",date).setParameter("towards", "MISCELLANEOUS").getSingleResult();
		
		
		}catch(Exception e){
			e.getMessage();
			return null;
		}
	}





}

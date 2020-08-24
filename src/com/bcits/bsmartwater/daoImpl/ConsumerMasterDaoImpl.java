package com.bcits.bsmartwater.daoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.bcits.bsmartwater.dao.ConsumerMasterDao;
import com.bcits.bsmartwater.model.ConsumerMaster;


@Repository
public class ConsumerMasterDaoImpl extends GenericDAOImpl<ConsumerMaster> implements ConsumerMasterDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> findByConnectionNo(String connectionNo) {
		
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.findByConnectionNo").setParameter("connectionNo", connectionNo.trim().toUpperCase()).getResultList();
	}

	@Override
	public long countByWardNo(String wardNo) {
		
		try{
			return (long) getCustomEntityManager().createNamedQuery("ConsumerMaster.countByWardNo").setParameter("ward_no", wardNo.toUpperCase()).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	@Override
	public long countByConCategory(String con_category1) {
		
		try{
			return (long) getCustomEntityManager().createNamedQuery("ConsumerMaster.countByConCategory").setParameter("con_category", con_category1.toUpperCase()).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<String> getDistictWardNos() {
		
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictWardNos",String.class).getResultList();
		
	}
	
	@Override
	public List<String> getDistictAllWardNos() {
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictAllWardNos",String.class).getResultList();
	}
	
	@Override
	public ConsumerMaster findconsumer(String connectionNo) {
		//System.out.println(connectionNo+"----CONNECTIONNOWOT");
		//System.out.println(connectionNo.toUpperCase().trim()+"----CONNECTIONNO");
		try{
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.findByConnectionNo",ConsumerMaster.class).setParameter("connectionNo", connectionNo.toUpperCase().trim()).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public List<String> getDistictMrNos() {
		
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictMrNos",String.class).getResultList();
	}
	
	@Override
	public List<Integer> getDistictReadingDays() {
		
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictReadingDays",Integer.class).getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> viewRtPlanGen(String mrCode, int readingDay, int wardNo,String areaNumber) 
	{
		if(areaNumber.equals("Noval"))
		{
			areaNumber="%";
		}
		
		List<ConsumerMaster> list=null;
		String sql=null;
		sql="SELECT CONNECTION_NO,coalesce(MTR_SER_NO,' '),READING_DAY,WARD_NO,coalesce(REGEXP_SUBSTR(SEQ_NO, '[^A-Za-z]+', 1, 1),' ') as SEQ_NO,coalesce(REGEXP_SUBSTR(SEQ_NO, '[^0-9]+', 1, 1),' ') as Seq_Abbr,AREA_NO,ID FROM BSW_MASTER where MTR_READER like '"+mrCode+"' and READING_DAY like "+readingDay+" and WARD_NO like "+wardNo+" and AREA_NO like '"+areaNumber+"'";
		list=getCustomEntityManager().createNativeQuery(sql).getResultList();
		return list;
		//return entityManager.createNamedQuery("ConsumerMaster.viewRtPlanGen",Object[].class).setParameter("mrCode",mrCode).setParameter("readingDay",readingDay).setParameter("wardNo",wardNo).setParameter("areaNumber",areaNumber).getResultList();
	}


	@Override
	public List<String> getDistictAreaNos() {
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictAreaNos",String.class).getResultList();
	}

	
	public List<ConsumerMaster> findByConnNo(String connectionNo) 
	{
		List<ConsumerMaster> list=getCustomEntityManager().createNamedQuery("ConsumerMaster.findByConnectionNo",ConsumerMaster.class).setParameter("connectionNo",connectionNo.trim().toUpperCase()).getResultList();
		/*if(list==null || list.size()==0){
			return "Connection No.: "+connectionNo+" does not exists";
		}else{
			return list;
		}*/
		return list;
	}

	@Override
	public Object[] getPipeSizeAndConType(String connection_no) {
		try{
		return (Object[]) getCustomEntityManager().createNamedQuery("ConsumerMaster.getPipeSizeAndConType").setParameter("connection_no",connection_no).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public List<String> getDistictConnCategory() {
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictConnCategory",String.class).getResultList();
	}
	
	@Override
	public List<String> getDistictConnType() {
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictConnType",String.class).getResultList();
	}
	
	@Override
	public List<Double> getDistictpipeSize() {
		
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictpipeSize",Double.class).getResultList();
	}
	
	@Override
	public List<Object[]> genDisConnList(String wardNo, String connCat, Double pipeSize,Double cutoffAmtfrm, Double cutoffAmtto)
	{
		//System.out.println("=====genDisConnList==>>wardNo==="+wardNo+"==connCat=="+connCat+"===pipeSize==="+pipeSize+"===cutoffAmtfrm=="+cutoffAmtfrm+"===cutoffAmtto=="+cutoffAmtto);
		
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.genDisConnList").setParameter("wardNo", wardNo).setParameter("connCat", connCat).setParameter("pipeSize", pipeSize).setParameter("cutoffAmtfrm", cutoffAmtfrm).setParameter("cutoffAmtto", cutoffAmtto).getResultList();
	}

	@Override
	public long checkConnect_noInMaster(String connection_no) {
		
		try{
			return (long)getCustomEntityManager().createNamedQuery("ConsumerMaster.checkConnect_noInMaster").setParameter("connection_no", connection_no).getSingleResult();
		}catch(Exception e){
			
			return 0;
		}
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> getMasterDataByConnectionNum(String conNum) {
		List<ConsumerMaster> consumer = null;
		try {
			consumer = getCustomEntityManager().createNamedQuery("ConsumerMaster.getMasterDataByConnectionNum").setParameter("conNum", conNum.trim().toUpperCase()+"%").getResultList();
			//System.out.println("consumer conNum ===> "+consumer.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumer;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> getMasterDataByName(String name) {
		List<ConsumerMaster> consumers = null;
		//String sql = " select * from BSW_MASTER where NAME_ENG LIKE '%"+name+"%' ";
		try {
			consumers = getCustomEntityManager().createNamedQuery("ConsumerMaster.getMasterDataByName")
					.setParameter("name", "%"+name.toUpperCase()+"%")
					.getResultList();
			//consumers = entityManager.createNativeQuery(sql).getResultList();
			//System.out.println("consumers name ===> "+consumers.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> getMasterDataByWardNum(String wardNum) {
		List<ConsumerMaster> consumer = null;
		try {
			consumer = getCustomEntityManager().createNamedQuery("ConsumerMaster.getMasterDataByWardNum").setParameter("wardNum", wardNum.trim().toUpperCase()).getResultList();
			//System.out.println("consumer conNum ** ===> "+consumer.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumer;
	}
	@Override
	public String getTotalConsumersCount(String siteCode) {
		String count = null;
		try {

			count = String.valueOf(getCustomEntityManager().createNamedQuery("ConsumerMaster.getTotalConsumersCount").setParameter("siteCode", siteCode).getSingleResult());


		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> getMasterDataByAreaNo(String areaNo) {
		List<ConsumerMaster> consumers = null;
		try {
			consumers = getCustomEntityManager().createNamedQuery("ConsumerMaster.getMasterDataByAreaNo")
					.setParameter("area_no", "%"+areaNo.toUpperCase()+"%")
					.getResultList();
			//System.out.println("consumers name ===> "+consumers.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> getMasterDataByPhoneNum(String phoneNum) {
		//System.out.println("phoneNum ===> "+phoneNum);
		List<ConsumerMaster> consumers = null;
		try {
			consumers = getCustomEntityManager().createNamedQuery("ConsumerMaster.getMasterDataByPhoneNum")
					.setParameter("phoneNo", "%"+phoneNum+"%")
					.getResultList();
			//System.out.println(consumers.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumers;
	}

	@Override
	public long countByWardNoRdayPS(String wardNo, Integer readingday,double pipesize,String concategory) {
		try{
			if(pipesize==0.5){
			
				if("All".equalsIgnoreCase(concategory)){
				return (long) getCustomEntityManager().createNamedQuery("ConsumerMaster.countByWardNoRdayPSSA").setParameter("ward_no", wardNo.toUpperCase()).setParameter("reading_day", readingday).setParameter("pipe_size", pipesize).getSingleResult();
			
				}else{
					return (long) getCustomEntityManager().createNamedQuery("ConsumerMaster.countByWardNoRdayPSSACC").setParameter("ward_no", wardNo.toUpperCase()).setParameter("reading_day", readingday).setParameter("pipe_size", pipesize).setParameter("con_category", concategory).getSingleResult();

				}
				}else{
				
					if("All".equalsIgnoreCase(concategory)){
					return (long) getCustomEntityManager().createNamedQuery("ConsumerMaster.countByWardNoRdayPSTHA").setParameter("ward_no", wardNo.toUpperCase()).setParameter("reading_day", readingday).setParameter("pipe_size", pipesize).getSingleResult();
					}else{
						return (long) getCustomEntityManager().createNamedQuery("ConsumerMaster.countByWardNoRdayPSTHACC").setParameter("ward_no", wardNo.toUpperCase()).setParameter("reading_day", readingday).setParameter("pipe_size", pipesize).setParameter("con_category", concategory).getSingleResult();

					}
			}
			}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> getMasterDataByOldConnNum(String oldConNum) {
		List<ConsumerMaster> consumers = null;
		try {
			consumers = getCustomEntityManager().createNamedQuery("ConsumerMaster.getMasterDataByOldConnNum")
					.setParameter("oldConNum", "%"+oldConNum.toUpperCase()+"%")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return consumers;
	}

	@Override
	public List<String> getDistictWardNosUnmetered() {
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictWardNosUnmetered",String.class).getResultList();

	}

	@Override
	public List<String> getDistictMonthYearNep() {
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getDistictMonthYearNep",String.class).getResultList();

	}

	@Override
	public long getMaxConsumerId() 
	{
		try{
			return (long) getCustomEntityManager().createNamedQuery("ConsumerMaster.getMaxCustomerId").getSingleResult();
		}
		catch (Exception e) {
			return (long) 0;
		}
	}

	@Override
	public List<?> getPendingConnForBilling(int approve_status,HttpServletRequest request) {
		HttpSession session=request.getSession(false);
		String sitecode=(String)session.getAttribute("branchcode");
		//System.out.println(sitecode);
		return getCustomEntityManager().createNamedQuery("ConsumerMaster.getPendingConnForBilling").setParameter("status", ""+approve_status).setParameter("sitecode", sitecode+"%").getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsumerMaster> getMasterDataByConnectionNumNew(String conNum,String schemaName) {
		try{
			return getCustomEntityManager(schemaName).createNamedQuery("ConsumerMaster.findByConnectionNo").setParameter("connectionNo", conNum.toUpperCase().trim()).getResultList();
			}catch(Exception e){
				e.printStackTrace();
				return null;
		 }
	}

	@Override
	public List<?> findByApplicationById(long applicationId) {
		

		//step1 load the driver class  
		ArrayList<String> arrayList = new ArrayList<String>();
		Connection connection = null;
		try{
	    Class.forName("org.postgresql.Driver");  
	    //step2 create  the connection object 
	    String databaseURL="jdbc:postgresql://192.168.20.2:5432/bsmartgrievance";
	    String username="postgres";
	    String password="post123";
	    
	    connection=  DriverManager.getConnection(databaseURL,username,password);
	    
	    if(connection!=null){
	    	//System.out.println("Connection Established");
	    }
	    
	    //Save Customer

	    Statement stmt=connection.createStatement(); 
	    String sql="SELECT applicationid,name,wardno,connectiontype,pipediameter,connection_no,recno,amount,status FROM kuklgrievance.ncpt_application WHERE applicationid="+applicationId+"";
	    ResultSet rs = stmt.executeQuery(sql);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    
	    int columnsNumber = rsmd.getColumnCount();
	    while (rs.next()) {
	        for (int i = 1; i <= columnsNumber; i++) {
	            if (i > 1) System.out.print(",  ");
	            String columnValue = rs.getString(i);
	            arrayList.add(columnValue);
	        }
	    }
	    
			  

	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	
	    	try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
		return arrayList;
	}

	@Override
	public List<?> getAllSchemaData() {
		
		
		String qry=" select COUNT(*),BRANCH FROM KUKL_BSMART.MASTER_MV GROUP BY BRANCH ";
		//System.out.println(qry);
		
		
		
		return getCustomEntityManager().createNativeQuery(qry).getResultList();
	}

	@Override
	public List<?> getMonthWisecCollection() {
		String qry="SELECT SUM(AMOUNT),to_char(RDATE, 'YYYY-MM') FROM KUKL_BSMART.PAYMENTS_MV WHERE to_char(RDATE, 'YYYY')= " +
				" (SELECT max(to_char(RDATE, 'YYYY')) FROM KUKL_BSMART.PAYMENTS_MV) GROUP BY To_char(RDATE, 'YYYY-MM') " +
				" ORDER BY to_char(RDATE, 'YYYY-MM') ASC";
		//System.out.println(qry);
		
		 return getCustomEntityManager().createNativeQuery(qry).getResultList();
	}

	@Override
	public List<?> getBranchWiseData() {
		
		String qry="SELECT SITECODE,MONTHYEARNEP,COUNT(CASE WHEN BILLNO IS NULL THEN 1 END) AS UNBILLED,COUNT(CASE WHEN BILLNO IS NOT NULL THEN 1 END) AS BILLED, " +
				" COALESCE(SUM(WATER_CHARGES),0) as WC,COALESCE(SUM(SW_CHARGES),0) as SWC,COALESCE(SUM(MTR_RENT),0) as MRT,(COALESCE(SUM(WATER_CHARGES),0)+COALESCE(SUM(SW_CHARGES),0)+COALESCE(SUM(MTR_RENT),0)) AS TOT FROM KUKL_BSMART.LEDGER_MV " +
				" WHERE MONTHYEARNEP=(SELECT MAX(MONTHYEARNEP) FROM KUKL_BSMART.LEDGER_MV WHERE MONTHYEARNEP IS NOT NULL) " +
				" GROUP BY SITECODE,MONTHYEARNEP ORDER BY MONTHYEARNEP DESC";
		return getCustomEntityManager().createNativeQuery(qry).getResultList();
	}

	@Override
	public Object[] getTariffData(String connectionNo) {
		try{
		return (Object[]) getCustomEntityManager().createNamedQuery("ConsumerMaster.getTariffData").setParameter("connectionNo", connectionNo.trim().toUpperCase()).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		}

	@Override
	public List<?> getEstimationData(long applicationId) {
		

		//step1 load the driver class  
		ArrayList<String> arrayList = new ArrayList<String>();
		Connection connection = null;
		Connection connection1 = null;
		try{
	    Class.forName("org.postgresql.Driver");  
	    //step2 create  the connection object 
	    String databaseURL="jdbc:postgresql://192.168.20.2:5432/bsmartgrievance";
	    String username="postgres";
	    String password="post123";
	    
	    connection=  DriverManager.getConnection(databaseURL,username,password);
	    
	    
	    if(connection!=null){
	    	//System.out.println("Connection Established");
	    }
	    
        boolean flag=false;
	   
	    Statement stmt1=connection.createStatement(); 
	    String sqlquery="select recno,recdate,towards,amount from  kuklgrievance.ncpt_depositpaid where applicationid='"+applicationId+"' and towards='New Connection Deposit'";
	    ResultSet rs1= stmt1.executeQuery(sqlquery);
	    if(rs1.next())
	    {
	    	flag=true;
	    }
	    if(flag==false) {
	    	 Statement stmt=connection.createStatement(); 
	    String sql="SELECT applicationid,nctap,ncdeposit,mvalue,miscellaneous_cost,advance FROM kuklgrievance.ncpt_estimation WHERE applicationid="+applicationId+" AND status=1";
	    ResultSet rs = stmt.executeQuery(sql);
	    ResultSetMetaData rsmd = rs.getMetaData();
	    
	    int columnsNumber = rsmd.getColumnCount();
	    while (rs.next()) {
	        for (int i = 1; i <= columnsNumber; i++) {
	            if (i > 1) System.out.print(",  ");
	            String columnValue = rs.getString(i);
	            arrayList.add(columnValue);
	        }
	    }
	    }
			  

	    }catch(Exception e){
	    	e.printStackTrace();
	    }finally{
	    	
	    	try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    }
		return arrayList;
	}

	@Override
	public List<?> getNewConnectionApproveList(String sitecode) 
	{
		
		
	 //String query="SELECT NAME_ENG,CONNECTION_NO,AREA_NO,LAST_UPDATED_BY,LAST_UPDATED_DT,CON_TYPE FROM BSW_MASTER WHERE CONNECTION_NO LIKE '"+sitecode+"%' AND STATUS='1' AND CONNECTION_NO NOT IN(SELECT DISTINCT CONNECTION_NO FROM BSW_APPROVE_MASTER WHERE APPROVE_STATUS=1 )";
	 String query="SELECT A.NAME_ENG, A.CONNECTION_NO, A.AREA_NO, B.ADDED_BY, B.ADDED_DATE, A.CON_TYPE FROM BSW_MASTER A, BSW_METER_MASTER B WHERE A.CONNECTION_NO LIKE '"+sitecode+"%' AND A.STATUS = '1' AND A.CONNECTION_NO NOT IN( SELECT DISTINCT CONNECTION_NO FROM BSW_APPROVE_MASTER WHERE APPROVE_STATUS = 1) AND A.CONNECTION_NO=B.CONNECTIONNO ORDER BY b.added_date desc";
	 try{
	 return getCustomEntityManager().createNativeQuery(query).getResultList();
	 }catch(Exception e)
	 {
		 e.getMessage();
		 return null;
	 }
	}

	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, value="txManagerChhetra")
	public String updateTables() 
	{
		String[] conn={"704T1",
				"5264",
				"3467",
				"703T1",
				"13346",
				"20947",
				"6326",
				"11793",
				"3837",
				"17377",
				"17345T1"
	
};

		//String[] con={"16980"};
		for(String c:conn)
		{
			try{
			
			String schema="LALITPUR_1118";
			
		/*	String q00="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207403 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207404 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q00).executeUpdate();
			
			String q01="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207404 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q01).executeUpdate();
			
			
			
			String q001="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207404 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207405 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q001).executeUpdate();
			
			String q011="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207405 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q011).executeUpdate();
			
			
			String q0011="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207405 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207406 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q0011).executeUpdate();
			
			String q0111="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207406 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q0111).executeUpdate();
			
			
			
			
			String q002="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207406 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207407 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q002).executeUpdate();
			
			String q012="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207407 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q012).executeUpdate();
			
			
			
			
			
			String q003="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207407 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207408 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q003).executeUpdate();
			
			String q013="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207408 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q013).executeUpdate();
			
			
			
			
			
			String q1="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207408 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207409 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q1).executeUpdate();
			
			String q2="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207409 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q2).executeUpdate();
			
			
			String q3="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207409 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207410 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q3).executeUpdate();
			
			String q4="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207410 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q4).executeUpdate();
			*/
			
			
			String q5="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207410 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207411 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q5).executeUpdate();
			
			String q6="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207411 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q6).executeUpdate();
			
			
			String q7="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207411 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207412 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q7).executeUpdate();
			
			String q8="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207412 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q8).executeUpdate();
			
			
			String q9="UPDATE BSW_LEDGER SET ARREARS=( SELECT NET_AMOUNT FROM BSW_LEDGER WHERE MONTHYEARNEP=207412 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL) WHERE MONTHYEARNEP=207501 AND CONNECTION_NO='"+c+"' AND RECEIPT_NO IS NULL";
			getCustomEntityManager(schema).createNativeQuery(q9).executeUpdate();
			
			String q10="UPDATE BSW_LEDGER SET NET_AMOUNT= ARREARS+SW_CHARGES+WATER_CHARGES+MTR_RENT WHERE MONTHYEARNEP=207501 AND CONNECTION_NO='"+c+"'";
			getCustomEntityManager(schema).createNativeQuery(q10).executeUpdate();
			
			
			}
			
			catch(Exception e){
			
			//getCustomEntityManager().close();
			//getCustomEntityManager().
			e.printStackTrace();
			}
			
			
			
		}
	
		return "success";
	}

}

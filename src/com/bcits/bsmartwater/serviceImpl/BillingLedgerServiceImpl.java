package com.bcits.bsmartwater.serviceImpl;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Session;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.bsmartwater.asyncupdate.AppConfig;
import com.bcits.bsmartwater.asyncupdate.UpdateAsynchronous;
import com.bcits.bsmartwater.dao.BillingLedgerDao;
import com.bcits.bsmartwater.dao.ConsumerMasterDao;
import com.bcits.bsmartwater.dao.DoubleLedgerValidationDao;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.dao.MeterReaderDao;
import com.bcits.bsmartwater.dao.ObservationMasterDAO;
import com.bcits.bsmartwater.dao.Penalty_RebateDao;
import com.bcits.bsmartwater.dao.TariffRateMasterDao;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.PaymentEntity;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;
import com.bcits.bsmartwater.utils.DateConverter;
import com.bcits.bsmartwater.utils.InvalidBsDayOfMonthException;
import com.bcits.bsmartwater.utils.StaticNepaliMonths;

@Service
public class BillingLedgerServiceImpl implements BillingLedgerService {
	
	DecimalFormat df = new DecimalFormat("####0.00");
	Timestamp today=new Timestamp(new java.util.Date().getTime());
	
	@Autowired
	BillingLedgerDao billingLedgerDao;
	
	@Autowired
	Penalty_RebateDao penalty_RebateDao;
	
	@Autowired
	ObservationMasterDAO observationMasterDAO;
	
	@Autowired
	TariffRateMasterDao tariffRateMasterDao;
	
	@Autowired
	MasterGenericDAO masterGenericDAO;
	
	@Autowired
	ConsumerMasterDao consumerMasterDao;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	MeterReaderDao meterReaderDao;
	
	@SuppressWarnings("serial")
	@Override
	public List<?> findByConnectionNo(String connectionNo) {
		
		List<BillingLedgerEntity> list= billingLedgerDao.findByConnectionNo(connectionNo);
		List<Map<String, Object>> billingDetailsMap =  new ArrayList<Map<String, Object>>(); 
		
		for (final BillingLedgerEntity record :list) 
		  {
			billingDetailsMap.add(new HashMap<String, Object>() {	 
			{  
				put("billid", record.getId());
				put("connection_no", record.getConnection_no());
				put("previous_reading", record.getPrevious_reading()==null?0:record.getPrevious_reading());
				put("present_reading",  record.getPresent_reading()==null?0:record.getPresent_reading());
				put("consumption", (record.getPresent_reading()==null?0:record.getPresent_reading())-(record.getPrevious_reading()==null?0:record.getPrevious_reading()));
				
						
				if(record.getRdng_date()!=null){
					put("rdng_date", billingLedgerDao.getDate3(record.getRdng_date()));
					
					String rdate=new SimpleDateFormat("dd-MM-yyyy").format(record.getRdng_date());
					String english[]=rdate.split("-");
					int cday=Integer.parseInt(english[0]);
					int cmonth=Integer.parseInt(english[1]);
					
					if("02".equalsIgnoreCase(english[1])){
						cday=cday+3;
					}
					if("04".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					if("06".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					if("09".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					if("11".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					int cyear=Integer.parseInt(english[2]);
					BsmartWaterLogger.logger.info(cday+"cday"+cmonth+"cmonth"+cyear);
					String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
					put("rdng_date_nep", nepalimonthyear);
					
				}else{
				
				    put("rdng_date_nep", record.getRdng_date_nep());
				    
				    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					String[] dateArray1 = record.getRdng_date_nep().split("-"); //yyyy-mm-dd
					Date lastBilledDate_N_to_E = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
					put("rdng_date", sdf.format(lastBilledDate_N_to_E));
					
				}
				
				if(record.getBill_date()!=null){
					put("bill_date", billingLedgerDao.getDate3(record.getBill_date()));
				}
				put("bill_date_nep", record.getBill_date_nep());
				
				put("water_charges", record.getWater_charges());
				put("arrears", record.getArrears());
				put("net_amount", record.getNet_amount());
				put("penalty", record.getPenalty());
				put("mc_status", record.getMc_status());
				
				/*if(record.getMc_status()!=null){
					
					String observationName=observationMasterDAO.findNameById(record.getMc_status());
					if(observationName!=null){
						put("mc_statusname", observationName);

					}
				}*/
				put("other", record.getOther());
				put("rebate", record.getRebate());
				put("monthyear", record.getMonthyear());
				put("monthyearnep", record.getMonthyearnep());
				put("mr_code", record.getMr_id());
				
				put("sw_charges", record.getSw_charges());
				put("excess_charges", record.getExcess_charges()==null?0:record.getExcess_charges());
				put("minimum_charges", record.getMinimum_charges());
				put("additional_charges", record.getAdditional_charges());
				put("mtr_rent", record.getMtr_rent()==null?0:record.getMtr_rent());
				
				put("open_balance", record.getOpen_balance());
				put("avg_units", record.getAvg_units());
				put("remarks", record.getRemarks());
				
				
				
				if(record.getDue_date()!=null){
					put("due_date", billingLedgerDao.getDate3(record.getDue_date()));
				}
					put("due_date_nep", record.getDue_date_nep());
				
				if(String.valueOf(record.getMonthyear()).length()>=6){
					put("rdng_mth", String.valueOf(record.getMonthyear()).substring(4, 6));
				}
				if(record.getPrevious_read_date()!=null){
					put("previous_read_date", billingLedgerDao.getDate3(record.getPrevious_read_date()));
				}
				
				/*if(record.getPrevious_read_date()!=null && record.getRdng_date()!=null){
					long diff = record.getRdng_date().getTime() - record.getPrevious_read_date().getTime();
				    long days= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				    put("bill_period", df.format((days/30.0)));  
					
				}*/
				put("bill_period", 1);
				put("billnumber", record.getBillno());
				put("receipt_no", record.getReceipt_no());
				put("receipt_date", record.getReceipt_date());
				
				
			}});
	 }
		return billingDetailsMap;
	 
	}

	@Override
	public long ledgerCountByWardNo(String wardNo,String monthyearnep) {
		
		return billingLedgerDao.ledgerCountByWardNo(wardNo,monthyearnep);
	}
	@Override
	public long ledgerCountByConCategory(String con_category1,String monthyearnep) {
		
		return billingLedgerDao.ledgerCountByConCategory(con_category1,monthyearnep);
	}

	@Override
	public long billedLedgerCountByWardNo(String wardNo,String monthyearnep) {
		
		return billingLedgerDao.billedLedgerCountByWardNo(wardNo,monthyearnep);
	}
	@Override
	public long billedLedgerCountByConCategory(String con_category1,String monthyearnep) {
		
		return billingLedgerDao.billedLedgerCountByConCategory(con_category1,monthyearnep);
	}

	@Override
	public long unbilledLedgerCountByWardNo(String wardNo,String monthyearnep) {
		
		return billingLedgerDao.unbilledLedgerCountByWardNo(wardNo,monthyearnep);
	}
	@Override
	public long unbilledLedgerCountByConCategory(String con_category1,String monthyearnep) {
		
		return billingLedgerDao.unbilledLedgerCountByConCategory(con_category1,monthyearnep);
	}

	@Override
	public BillingLedgerEntity getBillByConNoAndMY(String connection_no,int monthyear) {
		
		return billingLedgerDao.getBillByConNoAndMY(connection_no,monthyear);
	}
	

	@Override
	public void update(BillingLedgerEntity billingLedgerEntity) {
		
		billingLedgerDao.customupdate(billingLedgerEntity);
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getReadingEntryUnbilled(String wardNo) {
		
		List<?> billAppList =billingLedgerDao.getReadingEntryUnbilled(wardNo);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("billid", (int)values[0]);
					put("connection_no", (String)values[1]);
					put("previous_reading", values[2]==null?0:(Double)values[2]);
					put("present_reading", values[3]==null?0:(Double)values[3]);
					put("ward_no", (String)values[4]);
					
					
					
					
					/*if((Date)values[5]!=null){
					 put("billtodate", billingLedgerDao.getDate3((Date)values[5]));
					}
					if((Date)values[5]!=null){
					 put("duedate", billingLedgerDao.getDate3((Date)values[6]));
					}*/
					
					if((Integer)values[7]!=null){
						
						String observationName=observationMasterDAO.findNameById((Integer)values[7]);
						if(observationName!=null){
							put("mc_status", observationName);

						}
					}
					put("duedatenep", (String)values[8]);
					put("readdatenep", (String)values[9]);
					put("con_type", (String)values[10]);
					
				}
			});
		}
		return bills;
	}

	@Override
	public void updatePresReadAndMCStatusByBillId(double present_reading,int mc_status, int billid) {
		billingLedgerDao.updatePresReadAndMCStatusByBillId(present_reading,mc_status,billid);
	}

	@Override
	public String updateMonthEndProcess(String monthyear,String nepMY,String userName) {
		
		
		try{
		
		int year=Integer.parseInt(monthyear.substring(0, 4));
		int month=Integer.parseInt(monthyear.substring(4, 6));
		
		if(month==12){
			month=01;
			year=year+1;
		}else{
			month=month+1;
		}
		
		List<BillingLedgerEntity> billingLedgerList= new ArrayList<>();
		List<BillingLedgerEntity> billingLedgerEntities=billingLedgerDao.updateMonthEndProcess(monthyear);

		for (BillingLedgerEntity billingLedgerEntity : billingLedgerEntities) {
			
			    BillingLedgerEntity ble=new BillingLedgerEntity();
			
				ble.setConnection_no(billingLedgerEntity.getConnection_no());
				ble.setMr_id(billingLedgerEntity.getMr_id());
				ble.setRdng_mth(month);
				ble.setPrevious_reading(billingLedgerEntity.getPresent_reading());
				ble.setPresent_reading(billingLedgerEntity.getPresent_reading());
				ble.setConsumption((double) 0);
				ble.setWater_charges((double) 0);
				ble.setService_charge((double) 0);
				
				if(billingLedgerEntity.getClose_balance()==null){
					ble.setArrears(billingLedgerEntity.getArrears());
				}else{
					ble.setArrears(billingLedgerEntity.getClose_balance());
				}
				
				ble.setInterest((double) 0);
				ble.setLatefee((double) 0);
				ble.setNet_amount((double) 0);
				ble.setMcunits((double) 0);
				ble.setPenalty(0.0);
				ble.setPrevious_read_date(billingLedgerEntity.getRdng_date());
				ble.setUser_id(userName);
				ble.setRebate((double) 0);
				ble.setTotalamt((double) 0);
				ble.setSuspense((double) 0);
				ble.setWardno(billingLedgerEntity.getWardno());
				ble.setSw_charges((double) 0);
				ble.setOther(0.0);
				ble.setSitecode(billingLedgerEntity.getSitecode());
				ble.setSundry_amount((double) 0);
				ble.setAvg_units((double) 0);
				ble.setDl_count(0);
				ble.setMth_dl_count(0);
			    ble.setMth_dl_units((double) 0);
			    ble.setDl_units((double) 0);
			    ble.setAdded_by("");
			    ble.setDr_amount((double) 0);
			    ble.setMtr_rent(0.0);
			    ble.setOpen_balance(0.0);
			    ble.setExcess_charges(0.0);
			    ble.setAdditional_charges(0.0);
			    ble.setMinimum_charges(0.0);
			    
			    String newMonth="";
			    if(String.valueOf(month).length()==1){
			    	newMonth="0"+month;
			    }else{
			    	newMonth=""+month;
			    }
			    
			    BsmartWaterLogger.logger.info("NEW MONTH ------"+newMonth);
			    ble.setMonthyear(Integer.parseInt(year+""+newMonth));
			    ble.setMonthyearnep(nepMY);
			    ble.setCreated_date(today);
			    ble.setCashposdate(null);
			    ble.setPcashposdate(billingLedgerEntity.getCashposdate());
			    billingLedgerList.add(ble);
			
		}
		
		  billingLedgerDao.custombatchSave(billingLedgerList);
		  
		  return "Month End Process done Successfully";
		}catch(Exception e){
			e.printStackTrace();
			return "OOPS,Month End Process is not happened Something went wrong Please try again";
		}
		
	}

	@Override
	public int getMaxMonthYear() {
		
		return billingLedgerDao.getMaxMonthYear();
	}

	@Override
	public long monthEndValid(int monthYear) {
		
		return billingLedgerDao.monthEndValid(monthYear);
	}

	@Override
	public void updateRdngDateAndDueDate(int monthyear, String wardNo,String rdng_date, String rdng_date_nep, String due_date,String due_date_nep) {
		
		try{
		List<BillingLedgerEntity> billingLedgerList= new ArrayList<>();
		List<BillingLedgerEntity> billingLedgerEntities=billingLedgerDao.getBillListByMYAndWardNo(monthyear,wardNo);
		int i=1;
		for (BillingLedgerEntity billingLedgerEntity : billingLedgerEntities) {
			
			//System.out.println("Due Date Updation---"+i);
			billingLedgerEntity.setRdng_date(billingLedgerDao.getDate2(rdng_date));
			billingLedgerEntity.setRdng_date_nep(rdng_date_nep);
			
			billingLedgerEntity.setBill_date(billingLedgerDao.getDate2(rdng_date));
			billingLedgerEntity.setBill_date_nep(rdng_date_nep);
			
			
			billingLedgerEntity.setDue_date(billingLedgerDao.getDate2(due_date));
			billingLedgerEntity.setDue_date_nep(due_date_nep);
			billingLedgerList.add(billingLedgerEntity);
			i++;
		}
		
	    int updated=billingLedgerDao.custombatchUpdate(billingLedgerList);
	    //System.out.println(updated+"--------------Updated Count");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public String getMaxBillNoBysiteCode(String sitecode) {
		return billingLedgerDao.getMaxBillNoBysiteCode(sitecode);
	}

	@Override
	public BillingLedgerEntity findById(int billid) {
		return billingLedgerDao.customfind(billid);
	}


	@Override
	public BillingLedgerEntity findBillingLedgerBasedOnConnectionNo(String connectionNo) {
		return billingLedgerDao.findBillingLedgerBasedOnConnectionNo(connectionNo);
	}


	@Override
	public BillingLedgerEntity getLastMonthRecord(String connectionNo) {
		return billingLedgerDao.getLastMonthRecord(connectionNo);
	}
	
	public List<Object[]> generateReports(String neplaliFMonth, String engFmonth,String neplaliTMonth, String engTmonth, String counterNoSel,String mrcodeSel, String reportName, ModelMap model,HttpServletRequest request)
	{
		return billingLedgerDao.generateReports( neplaliFMonth,  engFmonth, neplaliTMonth,  engTmonth,  counterNoSel, mrcodeSel,  reportName,  model, request);
	}

	public List<String> getDistinctWardNo(String schemaName)
	{
		return billingLedgerDao.getDistinctWardNo(schemaName);
	}
	
	public List<String> getDistinctCounter(String schemaName)
	{
		return billingLedgerDao.getDistinctCounter(schemaName);
	}
	@Override
	public List<BillingLedgerEntity> getLedgerDataByConnectionNum(String connId) {
		return billingLedgerDao.getLedgerDataByConnectionNum(connId);
	}
	@SuppressWarnings("serial")
	@Override
	public List<?> getWardWiseBillCount(String sitecode) {
		
		List<?> list= billingLedgerDao.getWardWiseBillCount(sitecode);
		
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("totalbilled", values[0]);
					put("billed", values[1]);
					put("unbilled", values[2]);
					put("wardno", (String)values[3]);
					put("reading_day", (Integer)values[4]);
					
				}
			});
		}
		return bills;
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getConnectionHistory(String wardNo,int readingday, int value) {
		List<?> list= billingLedgerDao.getConnectionHistory(wardNo,readingday,value);
		//System.out.println("Inside ServiceImpl");
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", values[0]);
					put("name_eng", values[1]);
					if((String)values[2]==null){
						put("name_nep", "");
					}else{
					put("name_nep", values[2]);
					}
					if((String)values[3]==null){
						put("address_eng","");					
					}else{
						put("address_eng", values[3]);
					}
					if((String)values[4]==null){
						put("mobile_no", "");	
					}else{
						put("mobile_no", values[4]);
					}
					
					
					put("pipe_size", values[5]);
					put("connection_category", values[6]);
					put("connection_type", values[7]);
					put("area_no", values[8]);
					put("reading_day", values[9]);

					
				}
			});
		}
		return bills;
		
		
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> billedLedgerByWardNo(String wardNo,int reading_day, int reading_month, double pipesize,String concategory) {
		
		
		List<?> billAppList= billingLedgerDao.billedLedgerByWardNo(wardNo,reading_day,reading_month,pipesize,concategory);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
	
		for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					
					put("connection_no", (String)values[0]);
					
					if((String)values[1]==null){
						put("name_eng", (String)values[2]);	
					}else{
						put("name_eng", WordUtils.capitalizeFully((String)values[1]));	
					}
					
					if((String)values[27]==null){
						put("address_eng", (values[30]==null?"":(String)values[30])+""+(values[30]==null?"":",")+""+(String)values[3]);
					}else{
					
					put("address_eng", (values[30]==null?"":(String)values[30])+""+(values[30]==null?"":",")+""+(String)values[27]);
					}
					
					put("billno", (String)values[4]);
					put("con_type", (String)values[21]);
					
					if("Metered".equalsIgnoreCase((String)values[21])){
						
						double additionalUnits=0;
						put("previous_reading", values[5]==null?0:(Double)values[5]);
						put("present_reading",  values[6]==null?0:(Double)values[6]);
						
						double consumption=(values[6]==null?0:(Double)values[6])-(values[5]==null?0:(Double)values[5]);
						
						if(consumption>10){
							additionalUnits=consumption-10;
						}
						
						put("consumption", consumption);
						put("additionalUnits",  additionalUnits);
						
						if(consumption<0){
							
							put("previous_reading", values[6]==null?0:(Double)values[6]);
							put("present_reading",  values[6]==null?0:(Double)values[6]);
							put("consumption", 0);
							put("additionalUnits", 0);
							
						}

						
					}else{
					
						put("previous_reading", "NA");
						put("present_reading", "NA");
						put("consumption", "NA");
						put("additionalUnits",  "NA");
					}
					put("minimum_charges", values[8]==null?0:(Double)values[8]);
					put("additional_charges", values[9]==null?0:(Double)values[9]);
					put("water_charges", values[10]==null?0:(Double)values[10]);
					put("sw_charges", values[11]==null?0:(Double)values[11]);
					put("mtr_rent", values[12]==null?0:(Double)values[12]);
					put("penalty", values[13]==null?0:(Double)values[13]);
					put("rebate", values[14]==null?0:(Double)values[14]);
					put("arrears", values[15]==null?0:(Double)values[15]);
					put("net_amount", values[16]==null?0:(Double)values[16]);
					
					if((String)values[17]==null){
						
						if((Date)values[23]!=null){
							
							String rdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)values[23]);
							String english[]=rdate.split("-");
							int cday=Integer.parseInt(english[0]);
							int cmonth=Integer.parseInt(english[1]);
							if("02".equalsIgnoreCase(english[1])){
								cday=cday+3;
							}
							if("04".equalsIgnoreCase(english[1])){
								cday=cday+1;
							}
							if("06".equalsIgnoreCase(english[1])){
								cday=cday+1;
							}
							if("09".equalsIgnoreCase(english[1])){
								cday=cday+1;
							}
							if("11".equalsIgnoreCase(english[1])){
								cday=cday+1;
							}
							int cyear=Integer.parseInt(english[2]);
							
							String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
							put("rdng_date_nep", nepalimonthyear);
						}
						
					}else{
					  put("rdng_date_nep", (String)values[17]);
					}

					put("due_date_nep", (String)values[18]);
					put("pipe_size", (Double)values[19]);
					put("con_category", (String)values[22]);
					
					/*Date creadingdt =  (Date) values[23];
					Date oldrdngdate =  (Date) values[24];
					DateTime dt1 = null;
					DateTime dt2 =null;
					
						dt2 = new DateTime(oldrdngdate);
						dt1 = new DateTime(creadingdt);
					
					
					final Period period = new Period(dt2,dt1);
					String noOfMonths="";
					if(period.getYears()!=0){
						if(period.getYears()==1){
							noOfMonths=period.getYears()+" Year, ";
						}else{
						noOfMonths=period.getYears()+" Years, ";
						}
					}
					if(period.getMonths()!=0){
						if(period.getMonths()==1){
							noOfMonths=noOfMonths+""+period.getMonths()+" Month, ";
						}else{
						noOfMonths=noOfMonths+""+period.getMonths()+" Months, ";
						}
					}
					if(period.getWeeks()!=0){
						if(period.getWeeks()==1){
							noOfMonths=noOfMonths+""+period.getWeeks()+" Week, ";
						}else{
							noOfMonths=noOfMonths+""+period.getWeeks()+" Weeks, ";
						}
					}
					noOfMonths=noOfMonths+" "+period.getDays()+" Days";
					put("bill_period", noOfMonths);*/
					
					put("bill_period", 1);
					put("area_no", (String)values[25]);
					
					
					if(values[26]!=null){
						String mrname=meterReaderDao.getMRName((Integer)values[26]);
						if(mrname!=null){
							put("mrname",WordUtils.capitalizeFully(mrname));
						}
					}else{
						put("mrname", "");
					}
					
					if(values[28]==null){
						put("observation", "");
					}else{
						
							String obname=observationMasterDAO.findNameById((Integer)values[28]);
							if(obname==null){
								put("observation", "");
							}else{
								
								put("observation",obname);
							}
						
					}
					
					String latestNepaliMonth=(String)values[29];
					
					if(latestNepaliMonth!=null){
						String year=latestNepaliMonth.substring(0, 4);
						String month=latestNepaliMonth.substring(4, 6);
						
						if(StaticNepaliMonths.monthcode1.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep1);
						}else if(StaticNepaliMonths.monthcode2.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep2);
						}else if(StaticNepaliMonths.monthcode3.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep3);
						}else if(StaticNepaliMonths.monthcode4.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep4);
						}else if(StaticNepaliMonths.monthcode5.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep5);
						}else if(StaticNepaliMonths.monthcode6.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep6);
						}else if(StaticNepaliMonths.monthcode7.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep7);
						}else if(StaticNepaliMonths.monthcode8.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep8);
						}else if(StaticNepaliMonths.monthcode9.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep9);
						}else if(StaticNepaliMonths.monthcode10.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep10);
						}else if(StaticNepaliMonths.monthcode11.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep11);
						}else if(StaticNepaliMonths.monthcode12.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep12);
						}

					}
						put("boardbalance", values[31]);
					
				}
			});
		}
		return bills;
		
		
	}

	@Override
	public String getTotalBilledCount(String siteCode) {
		return billingLedgerDao.getTotalBillingCount(siteCode);
	}

	@Override
	public String getTotalDemand(String siteCode) {
		return billingLedgerDao.getTotalDemand(siteCode);
	}

	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public String getLatestMonthYear(String siteCode) {
		return billingLedgerDao.getLatestMonthYear(siteCode);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> billedLedgerByConnectionNoCM(String connection_no) {
		

		
		List<?> billAppList= billingLedgerDao.billedLedgerByConnectionNoCM(connection_no);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		
		for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", (String)values[0]);
					if((String)values[1]==null){
						put("name_eng", (String)values[2]);	
					}else{
						put("name_eng", WordUtils.capitalizeFully((String)values[1]));	
					}
					if((String)values[23]==null){
						put("address_eng", (values[27]==null?"":(String)values[27])+""+(values[27]==null?"":",")+""+(String)values[3]);
					}else{
					
					put("address_eng", (values[27]==null?"":(String)values[27])+""+(values[27]==null?"":",")+""+(String)values[23]);
					}
					
					put("area_no", (String)values[4]);
					put("con_type", (String)values[5]);
					put("pipe_size", (Double)values[6]);
					put("billno", (String)values[7]);
					
					if("Metered".equalsIgnoreCase((String)values[5])){
						
						double additionalUnits=0;
						
						double consumption=(values[9]==null?0:(Double)values[9])-(values[8]==null?0:(Double)values[8]);
						
						if(consumption>10){
							additionalUnits=consumption-10;
						}
						
						put("previous_reading", values[8]==null?0:(Double)values[8]);
						put("present_reading",  values[9]==null?0:(Double)values[9]);
						put("consumption", consumption);
						put("additionalUnits", additionalUnits);
						
					    if(consumption<0){
							
							put("previous_reading", values[9]==null?0:(Double)values[9]);
							put("present_reading",  values[9]==null?0:(Double)values[9]);
							put("consumption", 0);
							put("additionalUnits", 0);
							
						}
						
						
						
						if(values[24]!=null){
							String mrname=meterReaderDao.getMRName((Integer)values[24]);
							if(mrname!=null){
								put("mrname",WordUtils.capitalizeFully(mrname));
							}
						}else{
							put("mrname", "");
						}
						
					}else{
						
						put("previous_reading", "NA");
						put("present_reading", "NA");
						put("consumption", "NA");
						put("additionalUnits", "NA");
						put("mrname", "");
					}
					
					
					put("minimum_charges", values[10]==null?0:(Double)values[10]);
					put("additional_charges", values[11]==null?0:(Double)values[11]);
					put("water_charges", (Double)values[12]);
					put("sw_charges", (Double)values[13]);
					put("mtr_rent", (Double)values[14]);
					put("other", values[15]==null?0:(Double)values[15]);
					
					put("arrears", (Double)values[16]);
					put("net_amount", (Double)values[17]);
					
					if((String)values[18]==null){
						
						if((Date)values[21]!=null){
							
							String rdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)values[21]);
							String english[]=rdate.split("-");
							int cday=Integer.parseInt(english[0]);
							int cmonth=Integer.parseInt(english[1]);
							if("02".equalsIgnoreCase(english[1])){
								cday=cday+3;
							}
							if("04".equalsIgnoreCase(english[1])){
								cday=cday+1;
							}
							if("06".equalsIgnoreCase(english[1])){
								cday=cday+1;
							}
							if("09".equalsIgnoreCase(english[1])){
								cday=cday+1;
							}
							if("11".equalsIgnoreCase(english[1])){
								cday=cday+1;
							}
							int cyear=Integer.parseInt(english[2]);
							String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
							put("rdng_date_nep", nepalimonthyear);
						}
						
					}else{
					put("rdng_date_nep", (String)values[18]);
					}
					
					put("due_date_nep", (String)values[19]);
					
					
					/*Date creadingdt =  (Date) values[21];
					Date oldrdngdate =  (Date) values[20];
					DateTime dt1 = null;
					DateTime dt2 =null;
					
						dt2 = new DateTime(oldrdngdate);
						dt1 = new DateTime(creadingdt);
					
					
					final Period period = new Period(dt2,dt1);
					String noOfMonths="";
					if(period.getYears()!=0){
						if(period.getYears()==1){
							noOfMonths=period.getYears()+" Year, ";
						}else{
						noOfMonths=period.getYears()+" Years, ";
						}
					}
					if(period.getMonths()!=0){
						if(period.getMonths()==1){
							noOfMonths=noOfMonths+""+period.getMonths()+" Month, ";
						}else{
						noOfMonths=noOfMonths+""+period.getMonths()+" Months, ";
						}
					}
					if(period.getWeeks()!=0){
						if(period.getWeeks()==1){
							noOfMonths=noOfMonths+""+period.getWeeks()+" Week, ";
						}else{
							noOfMonths=noOfMonths+""+period.getWeeks()+" Weeks, ";
						}
					}
					noOfMonths=noOfMonths+" "+period.getDays()+" Days";
					put("bill_period", noOfMonths);*/
					put("bill_period", 1);
					//System.out.println("values[24]"+values[24]);
					
					
					String latestNepaliMonth=(String)values[25];
					
					if(latestNepaliMonth!=null){
						String year=latestNepaliMonth.substring(0, 4);
						String month=latestNepaliMonth.substring(4, 6);
						
						if(StaticNepaliMonths.monthcode1.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep1);
						}else if(StaticNepaliMonths.monthcode2.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep2);
						}else if(StaticNepaliMonths.monthcode3.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep3);
						}else if(StaticNepaliMonths.monthcode4.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep4);
						}else if(StaticNepaliMonths.monthcode5.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep5);
						}else if(StaticNepaliMonths.monthcode6.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep6);
						}else if(StaticNepaliMonths.monthcode7.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep7);
						}else if(StaticNepaliMonths.monthcode8.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep8);
						}else if(StaticNepaliMonths.monthcode9.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep9);
						}else if(StaticNepaliMonths.monthcode10.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep10);
						}else if(StaticNepaliMonths.monthcode11.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep11);
						}else if(StaticNepaliMonths.monthcode12.equalsIgnoreCase(month)){
							put("monthDesc", year+"-"+StaticNepaliMonths.monthnep12);
						}

					}
					
					
					if(values[26]==null){
						put("observation", "");
					}else{
						
							String obname=observationMasterDAO.findNameById((Integer)values[26]);
							if(obname==null){
								put("observation", "");
							}else{
								
								put("observation",obname);
							}
						
					}
					put("boardbalance", values[28]);
					put("customer_id", values[29]);
					//System.out.println("boardbalance------- "+values[28]);
				}
			});
		}
		return bills;
		
		
	
	}

	@Override
	public Object[] getPenaltyRebatePercentByConNo(String connectionNo) {
		
		Date date=billingLedgerDao.getMaxPaymentDateByConNo(connectionNo);
		
		if(date!=null){
			
			DateTime dt1 = new DateTime(date);
			DateTime dt2 = new DateTime(new Date());
			Days diffInDays = Days.daysBetween(dt1, dt2);
			Object[] obj=penalty_RebateDao.getPenaltyRebateByDays(diffInDays.getDays());
			return obj;
			
		}else{
			Date minreaddate=billingLedgerDao.getMinReadDateByConNo(connectionNo);
			DateTime dt1 = new DateTime(minreaddate);
			DateTime dt2 = new DateTime(new Date());
			Days diffInDays = Days.daysBetween(dt1, dt2);
			Object[] obj=penalty_RebateDao.getPenaltyRebateByDays(diffInDays.getDays());
			return obj;
		}
		
	}

	@Override
	public List<?> findBillsByReceiptNull(String connectionNo) {
		return billingLedgerDao.findBillsByReceiptNull(connectionNo);
	}

	@Override
	public BillingLedgerEntity findMaxRecordNotNullReceipt(String connectionNo) {
		return billingLedgerDao.findMaxRecordNotNullReceipt(connectionNo);

	}

	@Override
	public double getAvgConsumption(String connectionNo) {
		return billingLedgerDao.getAvgConsumption(connectionNo);
	}

	@Override
	public long ledgerCountByWardNoRdayPS(String wardNo, Integer readingday,
			double pipesize,String monthyear,String concategory) {
		return billingLedgerDao.ledgerCountByWardNoRdayPS(wardNo, readingday, pipesize,monthyear,concategory);

	}

	@Override
	public long billedLedgerCountByWardNoRdayPS(String wardNo,
			Integer readingday, double pipesize,String monthyear,String concategory) {
		return billingLedgerDao.billedLedgerCountByWardNoRdayPS(wardNo,readingday,pipesize,monthyear,concategory);

	}

	@Override
	public long unbilledLedgerCountByWardNoRdayPS(String wardNo,
			Integer readingday, double pipesize,String monthyear,String concategory) {
		return billingLedgerDao.unbilledLedgerCountByWardNoRdayPS(wardNo,readingday,pipesize,monthyear,concategory);

	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getReadingEntryUnbilled(String wardNo, int reading_day,double pipe_size) {

		
		List<?> billAppList =billingLedgerDao.getReadingEntryUnbilled(wardNo,reading_day,pipe_size);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("billid", (int)values[0]);
					put("connection_no", (String)values[1]);
					put("previous_reading", values[2]==null?0:(Double)values[2]);
					put("present_reading", values[2]==null?0:(Double)values[2]);
					put("ward_no", (String)values[4]);
					
					
					if((Integer)values[5]!=null){
						
						String observationName=observationMasterDAO.findNameById((Integer)values[5]);
						if(observationName!=null){
							put("mc_status", observationName);

						}
					}
					put("duedatenep", (String)values[6]);
					put("readdatenep", (String)values[7]);
					put("con_type", (String)values[8]);
					put("arrears", values[9]==null?0:(Double)values[9]);
					put("pipe_size", (Double)values[10]);
					put("consumption", (values[2]==null?0:(Double)values[2])-(values[2]==null?0:(Double)values[2]));
					put("area_no", (String)values[11]);

					
				}
			});
		}
		return bills;
	
	}

	@Override
	public List<BillingLedgerEntity> getListByConNoAndRECNo(String connectionNo, String receiptNo) {
		return billingLedgerDao.getListByConNoAndRECNo(connectionNo,receiptNo);
	}

	@Override
	public int custombatchUpdate(List<BillingLedgerEntity> billingLedgerList) {
		return billingLedgerDao.custombatchUpdate(billingLedgerList);
	}

	@Override
	public long checkEntriesExist(String monthyearnep) {
		return billingLedgerDao.checkEntriesExist(monthyearnep);

	}

	@Override
	public String updateBulkBillingUnmetered(String userName,String monthyearnep,String schemaName,String sitecode,DoubleLedgerValidationDao doubleLedgerValidationDao) {
		
		try{
		List<ConsumerMaster> consumerMasters=billingLedgerDao.updateBulkBillingUnmetered(monthyearnep);
		
			//System.out.println("Connections Unmetered List >>>>>>Sitecode>>>>>"+sitecode+">>>>>"+consumerMasters.size());
		
		    String rdate=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String english[]=rdate.split("-");
			int cday=Integer.parseInt(english[0]);
			int cmonth=Integer.parseInt(english[1]);
			if("02".equalsIgnoreCase(english[1])){
				cday=cday+3;
			}
			if("04".equalsIgnoreCase(english[1])){
				cday=cday+1;
			}
			if("06".equalsIgnoreCase(english[1])){
				cday=cday+1;
			}
			if("09".equalsIgnoreCase(english[1])){
				cday=cday+1;
			}
			if("11".equalsIgnoreCase(english[1])){
				cday=cday+1;
			}
			int cyear=Integer.parseInt(english[2]);
			//System.out.println(cday+"cday"+cmonth+"cmonth"+cyear);
			String nepalidate=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
		
			//System.out.println("about to run");
			ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	      	UpdateAsynchronous updateasynchronous = context.getBean(UpdateAsynchronous.class);
	        Future<Boolean> future = updateasynchronous.saveAsyncBulkBillUnmetered(userName, monthyearnep, consumerMasters, cmonth, cyear,nepalidate,schemaName,billingLedgerDao,sitecode,doubleLedgerValidationDao);
	        //System.out.println("this will run immediately.");
		  
		  return "Bill Generation for Unmetered Connections started Successfully, Please check dashboard after sometime";
		}catch(Exception e){
			e.printStackTrace();
			return "OOPS,Something went wrong Please try again";
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void updateBillLedger(String bConnectionNo, Double bPenalty,
			Double bRebate, Double bOther, String bReceiptNo,
			Double bReceivedAmount, Double bFrecamount,
			Double bAdvanceRebate,Double bOldbalance,String schemaName,Integer tomonthyear,Double bill_adj_amount, Double penalty_adj_amount,Date rdate,String adjtype) {
		try{
				
			    //List<BillingLedgerEntity> list=billingLedgerDao.findBillsByReceiptNullAll(bConnectionNo);
			    
			    BillingLedgerEntity billingLedgerEntity=billingLedgerDao.findBillingLedgerBasedOnConnectionNo(bConnectionNo);
			    List<BillingLedgerEntity> list=billingLedgerDao.findBillsByReceiptNullAllMYN(bConnectionNo,tomonthyear);
			
				//System.out.println(list.size()+"======================================");
				
				int i=1;
				//List<BillingLedgerEntity> billingLedgerList= new ArrayList<>();
				
				if(!list.isEmpty()){
				
					
					if(billingLedgerEntity!=null){
						
						if(billingLedgerEntity.getMonthyearnep().equalsIgnoreCase(""+tomonthyear)){
							
						}
						else{
							
							if(billingLedgerEntity.getReceipt_date()==null){
								billingLedgerEntity.setReceipt_date(rdate);
								billingLedgerEntity.setLast_paid_amount(bReceivedAmount);
								billingLedgerEntity.setClose_balance(billingLedgerEntity.getNet_amount()-(bReceivedAmount==null?0:bReceivedAmount)-(bRebate==null?0:bRebate)+(bOther==null?0:bOther)+(bPenalty==null?0:bPenalty)+(penalty_adj_amount==null?0:penalty_adj_amount));
								billingLedgerDao.customupdate(billingLedgerEntity);
							}else{
								
								billingLedgerEntity.setReceipt_date(rdate);
								billingLedgerEntity.setLast_paid_amount(bReceivedAmount);
								billingLedgerEntity.setClose_balance(billingLedgerEntity.getClose_balance()-(bReceivedAmount==null?0:bReceivedAmount)-(bRebate==null?0:bRebate)+(bOther==null?0:bOther)+(bPenalty==null?0:bPenalty)+(penalty_adj_amount==null?0:penalty_adj_amount));
								billingLedgerDao.customupdate(billingLedgerEntity);
							}
						}
					}
					
					/*for(BillingLedgerEntity bill:list){
							   
							   if(i==1){
								   bill.setPenalty(bPenalty);
								   bill.setRebate(bRebate);
								   bill.setOther(bOther);
								   bill.setReceipt_no(bReceiptNo);
								   bill.setReceipt_date(today);
								   bill.setLast_paid_amount(bReceivedAmount);
								   bill.setClose_balance((bFrecamount==null?0:bFrecamount)-(bReceivedAmount==null?0:bReceivedAmount)-(bAdvanceRebate==null?0:bAdvanceRebate));
							   }else{
								   bill.setReceipt_no(bReceiptNo);
								   bill.setReceipt_date(today);
							   }
								
							   //billingLedgerList.add(bill);
							   updateBillLedger(bill);
							  
							   
							   i++;
					  }*/
				    //billingLedgerDao.custombatchUpdate(billingLedgerList);
				    //billingLedgerDao.custombatchUpdateBillingLedgerEntity(list,bPenalty, bRebate, bOther, bReceiptNo,bReceivedAmount, bFrecamount,bAdvanceRebate,today,schemaName);
					//System.out.println("about to run");
					ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
			      	UpdateAsynchronous updateasynchronous = context.getBean(UpdateAsynchronous.class);
			        Future<Boolean> future = updateasynchronous.addPaymentAsynchronous(list,bPenalty, bRebate, bOther, bReceiptNo,bReceivedAmount, bFrecamount,bAdvanceRebate,today,billingLedgerDao,schemaName,bill_adj_amount, penalty_adj_amount,rdate, adjtype);
			        //System.out.println("this will run immediately.");
				}else{
				    
					//BillingLedgerEntity billingLedgerEntity=billingLedgerDao.findBillingLedgerBasedOnConnectionNo(bConnectionNo);
				 
					if(billingLedgerEntity!=null){
					 billingLedgerEntity.setReceipt_no(bReceiptNo);
					 billingLedgerEntity.setReceipt_date(rdate);
					 billingLedgerEntity.setLast_paid_amount(bReceivedAmount);
					 billingLedgerEntity.setClose_balance((bOldbalance==null?0:bOldbalance)-(bReceivedAmount==null?0:bReceivedAmount)-(bAdvanceRebate==null?0:bAdvanceRebate));
			         billingLedgerDao.customupdate(billingLedgerEntity);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Async
	@Transactional
	public void updateBillLedger(BillingLedgerEntity bill) {
		billingLedgerDao.customupdate(bill);
	}
	

	
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int custombatchUpdateBillingLedgerEntity( List<BillingLedgerEntity> billlist, PaymentEntity paymentdata,String schemaName) {
		try {
			
			
			return billingLedgerDao.custombatchUpdateBillingLedgerEntity(billlist,paymentdata,schemaName);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long checkLedgerReceiptExists(String connectionno) {
		return billingLedgerDao.checkLedgerReceiptExists(connectionno);
	}

	@Override
	public String getLatestNepaliMonthYear() {
		return billingLedgerDao.getLatestNepaliMonthYear();
	}

	@Override
	public List<?> getLedgerDataByWardMonthEnd(final String monthyear,String fromdate, String todate) {
		
		List<?> list=billingLedgerDao.getLedgerDataByWardMonthEnd(monthyear, fromdate, todate);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		int slNo=1;
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			
					final Object[] values = (Object[])i.next();
					Map<String,Object> map=new HashMap<String, Object>();
					double rebate=0;
					map.put("slNo", slNo++);
					
					if(values[0]==null && values[1]==null){
						map.put("wardNo", values[17]+" "+values[18]);
					}else{
						map.put("wardNo", values[0]+" "+values[1]);
					}
					
					map.put("opening_balance",values[2]);
					map.put("water_charges", values[3]);
					map.put("sw_charges", values[4]);
					map.put("misc", values[5]);
					map.put("mtr_rent", values[6]);
					map.put("adpenalty", values[7]);
					map.put("penalty", values[8]);
					map.put("posadj", values[9]);
					map.put("negadj", values[10]);
					
					map.put("totalbill",  Double.parseDouble(df.format((((java.math.BigDecimal)values[2]).doubleValue()+((java.math.BigDecimal)values[3]).doubleValue()
							+((java.math.BigDecimal)values[4]).doubleValue())+((java.math.BigDecimal)values[5]).doubleValue()
							+((java.math.BigDecimal)values[6]).doubleValue()+((java.math.BigDecimal)values[7]).doubleValue()+((java.math.BigDecimal)values[8]).doubleValue()+((java.math.BigDecimal)values[9]).doubleValue()+((java.math.BigDecimal)values[10]).doubleValue()
					)));
					map.put("ramount", values[11]);
					
					rebate=((java.math.BigDecimal)values[12]).doubleValue();
					
					map.put("rebate", rebate);
					map.put("adv", values[13]);
					map.put("advreb", ((((java.math.BigDecimal)values[13]).doubleValue())*0.03));
					
					map.put("cbalance", Double.parseDouble(df.format((((java.math.BigDecimal)values[2]).doubleValue()+((java.math.BigDecimal)values[3]).doubleValue()
							+((java.math.BigDecimal)values[4]).doubleValue())+((java.math.BigDecimal)values[5]).doubleValue()
							+((java.math.BigDecimal)values[6]).doubleValue()+((java.math.BigDecimal)values[7]).doubleValue()+((java.math.BigDecimal)values[8]).doubleValue()+((java.math.BigDecimal)values[9]).doubleValue()+((java.math.BigDecimal)values[10]).doubleValue()
					-(((java.math.BigDecimal)values[11]).doubleValue()+rebate+((((java.math.BigDecimal)values[13]).doubleValue())*0.03)))));
					
					map.put("contype", values[15]);
					map.put("concat", values[16]);
					
					bills.add(map);
				
			}
		
		return bills;
	}

	@Override
	public void generateLedgerByWardRdngPipeSize(String wardNo, int readingday,double pipesize, String userName,String monthyearnep,String sitecode) {
		
		try{
		
		int yearnep=Integer.parseInt(monthyearnep.substring(0, 4));
		String monthnep=monthyearnep.substring(4, 6);
	    
	    String newnepday="";
	    if(String.valueOf(readingday).length()==1){
	    	newnepday="0"+readingday;
	    }else{
	    	newnepday=""+readingday;
	    }
	    String nepalibillDate=yearnep+"-"+monthnep+"-"+newnepday;
	    Date billDate_N_to_E;
	    try {
	    	//System.out.println("INSIDE TRY");
	    	billDate_N_to_E = dateConverter.convertBsToAd(newnepday+monthnep+yearnep);
	    }catch (InvalidBsDayOfMonthException e) {
	    	e.printStackTrace();
	    	//System.out.println("INSIDE CATCH");
	    	int i=Integer.parseInt(newnepday)-2;
	    	String newRdate="";
	    	if(String.valueOf(i).length()==1){
	    		newRdate="0"+i;
		    }else{
		    	newRdate=""+i;
		    }
	    	billDate_N_to_E = dateConverter.convertBsToAd(newRdate+monthnep+yearnep);
		}
		
		List<BillingLedgerEntity> billingLedgerList= new ArrayList<>();	
		
		List<ConsumerMaster> consumerMasters=billingLedgerDao.getLedgetInsertByWardNoRday(wardNo,readingday,pipesize,monthyearnep);
		
		for(ConsumerMaster cs:consumerMasters){
				
				double minimum_charges=0;
				double sewageCharges=0;
				double arrears=0;
				double waterCharges=0;
				double mtr_rent=0;
				double arrcoradj=0;
				double sundryamt=0;
				double billadj=0;
				double penaltyadj=0;
				double pending_adj_approve=0;
				BillingLedgerEntity ble=new BillingLedgerEntity();
				
				BillingLedgerEntity b=billingLedgerDao.getLatestRecordByConnectionNo(cs.getConnection_no());
				
				if(b!=null){
					
					if(b.getReceipt_date()==null){
						
						if(b.getService_charge()==null){
							
						}else{
							arrcoradj=b.getService_charge();
						}
						
						if(b.getSundry_amount()==null){
							
							if("TA".equalsIgnoreCase(b.getStatus())){
								billadj=b.getBill_adj_amount()==null?0:b.getBill_adj_amount();
							}
							
						}else{
							sundryamt=b.getSundry_amount();
						}
						
						arrears=b.getNet_amount()+arrcoradj+sundryamt+billadj;
						
					}else{
						
						if(b.getLast_paid_amount()==null){
							
							if(b.getService_charge()==null){
								
							}else{
								arrcoradj=b.getService_charge();
							}
							if(b.getSundry_amount()==null){
								
							}else{
							
							if("TA".equalsIgnoreCase(b.getStatus())){
									billadj=b.getBill_adj_amount()==null?0:b.getBill_adj_amount();
							}
							else{
								sundryamt=b.getSundry_amount();
							}
							}
							
							arrears=b.getNet_amount()+arrcoradj+sundryamt+billadj;
						}else{
							
							if(b.getSundry_amount()==null){
								
							}else{
								sundryamt=b.getSundry_amount();
							}
							arrears=(b.getClose_balance()==null?0:b.getClose_balance())+sundryamt;
						}
					}
					try {
						pending_adj_approve=b.getPending_adj_approve()==null?0:b.getPending_adj_approve();
					}catch (Exception e) {
						pending_adj_approve=0;
					}
					
				}

				if("Defaulter".equalsIgnoreCase(cs.getCon_satatus()) || "Temporary".equalsIgnoreCase(cs.getCon_satatus())){
					
					long billNo=0;
					String maxBillNo=billingLedgerDao.getMaxBillNoByWardNo(monthyearnep+""+newnepday+"%");
					
					if(maxBillNo==null){
						billNo=10000*Integer.parseInt(monthyearnep+""+newnepday);
					}else{
						
						boolean b1=NumberUtils.isNumber(maxBillNo);
						if(b1){
						    billNo=(Long.parseLong(maxBillNo)+1);
						}else{
							 billNo=(Long.parseLong(monthyearnep+""+newnepday)+1);
						}
					}
					
					ble.setBillno(""+billNo);
					ble.setMc_status(11);
					
				}else{
					
					if(b!=null){
						
						if(b.getMc_status()!=null){
							
							if(b.getMc_status()==18 || b.getMc_status()==19){
								
								long billNo=0;
								String maxBillNo=billingLedgerDao.getMaxBillNoByWardNo(monthyearnep+""+newnepday+"%");
								
								if(maxBillNo==null){
									billNo=10000*Integer.parseInt(monthyearnep+""+newnepday);
								}else{
									
									boolean b1=NumberUtils.isNumber(maxBillNo);
									if(b1){
									    billNo=(Long.parseLong(maxBillNo)+1);
									}else{
										 billNo=(Long.parseLong(monthyearnep+""+newnepday)+1);
									}
								}
								
								ble.setBillno(""+billNo);
								ble.setMc_status(b.getMc_status());
							}
						}
					}
				}
				
				ble.setConnection_no(cs.getConnection_no());
				ble.setMr_id(null);
				ble.setRdng_mth(1);
				
				if(b!=null){
					ble.setPrevious_reading(b.getPresent_reading()==null?0:b.getPresent_reading());
					ble.setPresent_reading(b.getPresent_reading()==null?0:b.getPresent_reading());
					ble.setPrevious_read_date(b.getRdng_date());
					ble.setDl_count(b.getDl_count());
					ble.setMth_dl_count(b.getMth_dl_count());
					ble.setPending_adj_approve(pending_adj_approve);
				}else{
					
					ble.setPrevious_reading(0.0);
					ble.setPresent_reading(0.0);
					ble.setPrevious_read_date(null);
				}
				ble.setConsumption((double) 0);
				ble.setWater_charges(waterCharges);
				ble.setService_charge((double) 0);
				ble.setMinimum_charges(minimum_charges);
				
				ble.setArrears(arrears);
				ble.setInterest((double) 0);
				ble.setLatefee((double) 0);
				ble.setNet_amount(waterCharges+sewageCharges+mtr_rent+arrears);
				ble.setMcunits((double) 0);
				ble.setPenalty(0.0);
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
			    ble.setOpen_balance(arrears);
			    ble.setExcess_charges(0.0);
			    ble.setAdditional_charges(0.0);
			    ble.setMinimum_charges(minimum_charges);
			    ble.setBill_date(today);
			    ble.setRdng_date(billDate_N_to_E);
			    ble.setRdng_date_nep(nepalibillDate);
			    ble.setBill_date_nep(nepalibillDate);
			    ble.setSitecode(sitecode);
			    ble.setReading_day(cs.getReading_day());
			    ble.setPipe_size(cs.getPipe_size());
			    ble.setCon_category(cs.getCon_category());
			    ble.setCon_type(cs.getCon_type());
			    ble.setEarrears(arrears);
			    
			    if(cs.getPipe_size()==0.5){
			     ble.setDenoted_by("SA");	
			    }else{
			    	ble.setDenoted_by("THA");	
			    }
			    
			    
			    String s= new SimpleDateFormat("MM/dd/yyyy").format(billDate_N_to_E);
			    String se[]=s.split("/");
			    String newMonth="";
			    if(String.valueOf(se[0]).length()==1){
			    	newMonth="0"+se[0];
			    }else{
			    	newMonth=""+se[0];
			    }
			    
			    BsmartWaterLogger.logger.info(se[2]+""+newMonth+"se[2]+yearMonth");
			    ble.setMonthyear(Integer.parseInt(se[2]+""+newMonth));
			    ble.setMonthyearnep(monthyearnep);
			    ble.setCreated_date(new Date());
			    ble.setCashposdate(null);
			    ble.setPcashposdate(null);
			    billingLedgerList.add(ble);
		}
		//System.out.println("----Start Batch Save----------------------");
		billingLedgerDao.custombatchSave(billingLedgerList);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Override
	public long getCheckBillsExist(String wardNo, int reading_day,double pipe_size, String monthyearnep) {
		return billingLedgerDao.getMissedBillsInLedger(wardNo,reading_day,pipe_size,monthyearnep);
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> printBillUnmeteredBilled(String wardNo,final String reading_monthu) {
		List<?> billAppList= billingLedgerDao.printBillUnmeteredBilled(wardNo,reading_monthu);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					
					String latestNepaliMonth=reading_monthu;
					
					
					String monthDesc="";
					if(latestNepaliMonth!=null){
						String year=latestNepaliMonth.substring(0, 4);
						String month=latestNepaliMonth.substring(4, 6);
						
						if(StaticNepaliMonths.monthcode1.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep1;
						}else if(StaticNepaliMonths.monthcode2.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep2;
						}else if(StaticNepaliMonths.monthcode3.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep3;
						}else if(StaticNepaliMonths.monthcode4.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep4;
						}else if(StaticNepaliMonths.monthcode5.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep5;
						}else if(StaticNepaliMonths.monthcode6.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep6;
						}else if(StaticNepaliMonths.monthcode7.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep7;
						}else if(StaticNepaliMonths.monthcode8.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep8;
						}else if(StaticNepaliMonths.monthcode9.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep9;
						}else if(StaticNepaliMonths.monthcode10.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep10;
						}else if(StaticNepaliMonths.monthcode11.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep11;
						}else if(StaticNepaliMonths.monthcode12.equalsIgnoreCase(month)){
							monthDesc= year+"-"+StaticNepaliMonths.monthnep12;
						}

					}
					
					
					put("connection_no", (String)values[0]);
					
					if((String)values[1]==null){
						put("name_eng", (String)values[2]);	
					}else{
						put("name_eng", (String)values[1]);	
					}
					
					if((String)values[3]==null){
						put("address_eng", (values[17]==null?"":(String)values[17])+""+(values[17]==null?"":",")+""+(String)values[14]);
					}else{
					
					    put("address_eng", (values[17]==null?"":(String)values[17])+""+(values[17]==null?"":",")+""+(String)values[3]);
					}
					
					put("billno", (String)values[4]);
					put("con_type", "Unmetered");
					
					put("previous_reading", "NA");
					put("present_reading", "NA");
					put("consumption", "NA");
					
					put("minimum_charges", values[5]==null?0:(Double)values[5]);
					put("additional_charges", values[6]==null?0:(Double)values[6]);
					put("water_charges", values[7]==null?0:(Double)values[7]);
					put("sw_charges", values[8]==null?0:(Double)values[8]);
					put("arrears", values[9]==null?0:(Double)values[9]);
					put("net_amount", values[10]==null?0:(Double)values[10]);
					
					if(values[11]==null){
							if((Date)values[15]!=null){
								
								String rdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)values[15]);
								String english[]=rdate.split("-");
								int cday=Integer.parseInt(english[0]);
								int cmonth=Integer.parseInt(english[1]);
								if("02".equalsIgnoreCase(english[1])){
									cday=cday+3;
								}
								
								if("04".equalsIgnoreCase(english[1])){
									cday=cday+1;
								}
								if("06".equalsIgnoreCase(english[1])){
									cday=cday+1;
								}
								if("09".equalsIgnoreCase(english[1])){
									cday=cday+1;
								}
								if("11".equalsIgnoreCase(english[1])){
									cday=cday+1;
								}
								int cyear=Integer.parseInt(english[2]);
								BsmartWaterLogger.logger.info(cday+"cday"+cmonth+"cmonth"+cyear);
								String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
								put("rdng_date_nep", nepalimonthyear);
							
						}
					}else{
					put("rdng_date_nep", (String)values[11]);
					}
					put("con_category", (String)values[12]);
					
					
					put("bill_period", 1);
					put("area_no", values[13]==null?"":(String)values[13]);
					put("mrname", "NA");
					put("monthDesc", monthDesc);
					put("pipe_size", values[16]);
					
					
				}
			});
		}
		return bills;
		
		
	
	
	
	
	
	}
	@SuppressWarnings("serial")
	@Override
	public List<?> printBillGovt(String con_category1,final String reading_monthg) {
		
		try {
			List<?> billAppList= billingLedgerDao.printBillGovt(con_category1,reading_monthg);
			List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
			for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
				bills.add(new HashMap<String, Object>() {
					{
						final Object[] values = (Object[])i.next();
						
						String latestNepaliMonth=reading_monthg;
						String monthDesc="";
						if(latestNepaliMonth!=null){
							String year=latestNepaliMonth.substring(0, 4);
							String month=latestNepaliMonth.substring(4, 6);
							
							if(StaticNepaliMonths.monthcode1.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep1;
							}else if(StaticNepaliMonths.monthcode2.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep2;
							}else if(StaticNepaliMonths.monthcode3.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep3;
							}else if(StaticNepaliMonths.monthcode4.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep4;
							}else if(StaticNepaliMonths.monthcode5.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep5;
							}else if(StaticNepaliMonths.monthcode6.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep6;
							}else if(StaticNepaliMonths.monthcode7.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep7;
							}else if(StaticNepaliMonths.monthcode8.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep8;
							}else if(StaticNepaliMonths.monthcode9.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep9;
							}else if(StaticNepaliMonths.monthcode10.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep10;
							}else if(StaticNepaliMonths.monthcode11.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep11;
							}else if(StaticNepaliMonths.monthcode12.equalsIgnoreCase(month)){
								monthDesc= year+"-"+StaticNepaliMonths.monthnep12;
							}
							
						}
						
						
						put("connection_no", (String)values[0]);
						
						if((String)values[1]==null){
							put("name_eng", (String)values[2]);	
						}else{
							put("name_eng", (String)values[1]);	
						}
						
						if((String)values[3]==null){
							put("address_eng", (values[17]==null?"":(String)values[17])+""+(values[17]==null?"":",")+""+(String)values[14]);
						}else{
							
							put("address_eng", (values[17]==null?"":(String)values[17])+""+(values[17]==null?"":",")+""+(String)values[3]);
						}
						
						put("billno", (String)values[4]);
						put("con_type", (String)values[18]);
						
						put("previous_reading", "NA");
						put("present_reading", "NA");
						put("consumption", "NA");
						
						put("minimum_charges", values[5]==null?0:(Double)values[5]);
						put("additional_charges", values[6]==null?0:(Double)values[6]);
						put("water_charges", values[7]==null?0:(Double)values[7]);
						put("sw_charges", values[8]==null?0:(Double)values[8]);
						put("arrears", values[9]==null?0:(Double)values[9]);
						put("net_amount", values[10]==null?0:(Double)values[10]);
						
						if(values[11]==null){
							if((Date)values[15]!=null){
								
								String rdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)values[15]);
								String english[]=rdate.split("-");
								int cday=Integer.parseInt(english[0]);
								int cmonth=Integer.parseInt(english[1]);
								if("02".equalsIgnoreCase(english[1])){
									cday=cday+3;
								}
								
								if("04".equalsIgnoreCase(english[1])){
									cday=cday+1;
								}
								if("06".equalsIgnoreCase(english[1])){
									cday=cday+1;
								}
								if("09".equalsIgnoreCase(english[1])){
									cday=cday+1;
								}
								if("11".equalsIgnoreCase(english[1])){
									cday=cday+1;
								}
								int cyear=Integer.parseInt(english[2]);
								BsmartWaterLogger.logger.info(cday+"cday"+cmonth+"cmonth"+cyear);
								String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
								put("rdng_date_nep", nepalimonthyear);
								
							}
						}else{
							put("rdng_date_nep", (String)values[11]);
						}
						put("con_category", (String)values[12]);
						
						
						put("bill_period", 1);
						put("area_no", values[13]==null?"":(String)values[13]);
						put("mrname", "NA");
						put("monthDesc", monthDesc);
						put("pipe_size", values[16]);
						
						
					}
				});
			}
			return bills;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public BillingLedgerEntity findByConnectionNoByMYN(String connectionNo,String monthyearnep) {
		return billingLedgerDao.findByConnectionNoByMYN(connectionNo,monthyearnep);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getWardWiseBillCountUnMetered(String sitecode) {
		

		
		List<?> list= billingLedgerDao.getWardWiseBillCountUnMetered(sitecode);
		
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("totalbilled", values[0]);
					put("billed", values[1]);
					put("unbilled", values[2]);
					put("wardno", (String)values[3]);
					
				}
			});
		}
		return bills;
		
	
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getConnectionHistory(String wardNo, int value) {

		List<?> list= billingLedgerDao.getConnectionHistoryUn(wardNo,value);
		//System.out.println("Inside ServiceImpl");
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", values[0]);
					put("name_eng", values[1]);
					if((String)values[2]==null){
						put("name_nep", "");
					}else{
					put("name_nep", values[2]);
					}
					if((String)values[3]==null){
						put("address_eng","");					
					}else{
						put("address_eng", values[3]);
					}
					if((String)values[4]==null){
						put("mobile_no", "");	
					}else{
						put("mobile_no", values[4]);
					}
					
					
					put("pipe_size", values[5]);
					put("connection_category", values[6]);
					put("connection_type", values[7]);
					put("area_no", values[8]);
					put("reading_day", "");

					
				}
			});
		}
		return bills;
		
		
		
	
	}

	@Override
	public Object[] getMasterStatistics() {
		
		return billingLedgerDao.getMasterStatistics();
	}


	@Override
	public List<?> findBillsByReceiptNullByMonthYear(String connectionNo,int yearmntfr, int yearmntto) {
		return billingLedgerDao.findBillsByReceiptNullByMonthYear(connectionNo,yearmntfr,yearmntto);
	}


	@Override
	public long checkLedgerLatestExists(String connectionno) {
		return billingLedgerDao.checkLedgerLatestExists(connectionno);
	}

	@Override
	public BillingLedgerEntity getLatestRecordByConnectionNo(String connectionNo) {
		return billingLedgerDao.getLatestRecordByConnectionNo(connectionNo);
	}

	@Override
	public Object[] getDoorLockCount(String connectionNo) {
		return billingLedgerDao.getDoorLockCount(connectionNo);
	}

	@Override
	public void save(BillingLedgerEntity billLedgerEntity) {
		billingLedgerDao.customsave(billLedgerEntity);
		
	}

	@Override
	public List<BillingLedgerEntity> getLedgerDataByConnNumNew(String connId,String schemaName) {
		return billingLedgerDao.getLedgerDataByConnNumNew(connId,schemaName);
	}

	@Override
	public Object[] getOpeningBalanceByConNo(String connectionNo) {
		return billingLedgerDao.getOpeningBalanceByConNo(connectionNo);

	}

	@Override
	public List<?> getLedgerDataByWardMonthEndSubmit(String monthyearnep, String fromdate, String todate) {
		return billingLedgerDao.getLedgerDataByWardMonthEnd(monthyearnep, fromdate, todate);
	}

	@Override
	public List<?> getLedgerDataByWardMonthEndSubmitManual(String monthyearnep,String fromdate, String todate) {
		return billingLedgerDao.getLedgerDataByWardMonthEndSubmitManual(monthyearnep, fromdate, todate);
	}

	@Override
	public BillingLedgerEntity findLastMonthRecordByConNo(String connectionNo) {
		return billingLedgerDao.findLastMonthRecordByConNo(connectionNo);
	}

	@Override
	public BillingLedgerEntity findRecordByConNoMYN(String connection_no,Integer monthyearnep) {
		return billingLedgerDao.findRecordByConNoMYN(connection_no,monthyearnep);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getconnectionMasterLedgerDetails(final String conNo,final HttpServletRequest request) {

		List<?> list = billingLedgerDao.getconnectionMasterLedgerDetails(conNo);

		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[]) i.next();
					
					//HttpServletRequest request=null;
					HttpSession session=request.getSession(false);
					String sitecode=(String)session.getAttribute("branchcode");
					String schemaName=(String)session.getAttribute("schemaName");
					
					put("name", values[0]);
					put("area", values[1]);
					put("pipe", values[2]);
					put("category", values[3]);
					put("arrears", values[4]);
					put("waterCh", values[5]);
					put("swCh", values[6]);
					put("mtrRent", values[7]);
					put("netAmt", values[8]);
					put("monthyr", values[9]);
					put("board_amt", values[10]);
					put("con_type", values[11]);
					put("ward_no", values[12]);
					put("denoted_by", values[13]);
					
					
					
					
					//Penalty Start			
					double totalpenlaty=0.0;
					double totalrebate=0.0;
					String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
					String english[]=date.split("-");
					int cday=Integer.parseInt(english[0]);

					if("02".equalsIgnoreCase(english[1])){
						cday=cday+3;
					}

					if("04".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					if("06".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					if("09".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					if("11".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					int cmonth=Integer.parseInt(english[1]);
					int cyear=Integer.parseInt(english[2]);
					String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
					String[] s=nepalimonthyear.split("-");
					String startmonth=s[0]+""+s[1];
					
					String yearc=startmonth.substring(0, 4);
					String monthc=startmonth.substring(4, 6);
					
					String monthYearNep1=null;
					String monthYearNep2=null;
					String monthYearNep3=null;
					String monthYearNep4=null;
					String monthYearNep5=null;
					String monthYearNep6=null;
					
					
					if("01".equalsIgnoreCase(monthc)){
						monthYearNep1=startmonth;
						String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
						monthYearNep2=nmonthYearNep;
						monthYearNep3=""+(Integer.parseInt(nmonthYearNep)-1);
						monthYearNep4=""+(Integer.parseInt(nmonthYearNep)-2);
						monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-3);
						monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-4);
						
					}else if("02".equalsIgnoreCase(monthc)){
						monthYearNep1=startmonth;
						monthYearNep2=""+(Integer.parseInt(startmonth)-1);
						String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
						monthYearNep3=nmonthYearNep;
						monthYearNep4=""+(Integer.parseInt(nmonthYearNep)-1);
						monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-2);
						monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-3);
					}else if("03".equalsIgnoreCase(monthc)){
						monthYearNep1=startmonth;
						monthYearNep2=""+(Integer.parseInt(startmonth)-1);
						monthYearNep3=""+(Integer.parseInt(startmonth)-2);
						String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
						monthYearNep4=nmonthYearNep;
						monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-1);
						monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-2);
					}else if("04".equalsIgnoreCase(monthc)){
						monthYearNep1=startmonth;
						monthYearNep2=""+(Integer.parseInt(startmonth)-1);
						monthYearNep3=""+(Integer.parseInt(startmonth)-2);
						monthYearNep4=""+(Integer.parseInt(startmonth)-3);
						String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
						monthYearNep5=nmonthYearNep;
						monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-1);
					}else if("05".equalsIgnoreCase(monthc)){
						monthYearNep1=startmonth;
						monthYearNep2=""+(Integer.parseInt(startmonth)-1);
						monthYearNep3=""+(Integer.parseInt(startmonth)-2);
						monthYearNep4=""+(Integer.parseInt(startmonth)-3);
						monthYearNep5=""+(Integer.parseInt(startmonth)-4);
						String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
						monthYearNep6=nmonthYearNep;
					}else{
						monthYearNep1=startmonth;
						monthYearNep2=""+(Integer.parseInt(startmonth)-1);
						monthYearNep3=""+(Integer.parseInt(startmonth)-2);
						monthYearNep4=""+(Integer.parseInt(startmonth)-3);
						monthYearNep5=""+(Integer.parseInt(startmonth)-4);
						monthYearNep6=""+(Integer.parseInt(startmonth)-5);
					}
					
			
					

					List<?> list1=billingLedgerDao.findBillsByReceiptNull(conNo);

					if(!list1.isEmpty()){

						for (final Iterator<?> ii = list1.iterator(); ii.hasNext();) {
							Object[] values1 = (Object[])ii.next();
							double waterCharge=values1[0]==null?0:(Double)values1[0];
							double swCharge=values1[1]==null?0:(Double)values1[1];
							double mtrrent=values1[2]==null?0:(Double)values1[2];
							double netamt=values1[3]==null?0:(Double)values1[3];
							String monthyrnep=values1[4]==null?"":(String)values1[4];		


							double billamount=(waterCharge)+swCharge+(mtrrent);
							

							if((monthYearNep1).equalsIgnoreCase(monthyrnep) || (monthYearNep2).equalsIgnoreCase(monthyrnep) || (monthYearNep3).equalsIgnoreCase(monthyrnep)){
								   
								 if(netamt>0){
									  
									 if(billamount>netamt){
										  totalrebate+=(netamt*3)/100;
									 }else{
									      totalrebate+=(billamount*3)/100;
									 }
								   }
								   
							   }
								   
								   
							   else if((monthYearNep4).equalsIgnoreCase(monthyrnep)){
								  
							   }else if((monthYearNep5).equalsIgnoreCase(monthyrnep)){
									   if(netamt>0){
									   
										   if(billamount>netamt){
											      totalpenlaty+=(netamt*10)/100;
										   }else{
										          totalpenlaty+=(billamount*10)/100;
										   }
									   }
							   }else if((monthYearNep6).equalsIgnoreCase(monthyrnep)){
								   if(netamt>0){
									   
									   if(billamount>netamt){
										      totalpenlaty+=(netamt*20)/100;
									   }else{
									          totalpenlaty+=(billamount*20)/100;
									   }
								   }
							   }else{
								   if(netamt>0){
									   if(billamount>netamt){
										      totalpenlaty+=(netamt*50)/100;
									   }else{
									          totalpenlaty+=(billamount*50)/100;
									   }
								   }
							   }
						
							}
	
					//Penalty End
					
					

				}
					put("totalpenlaty", totalpenlaty);
					put("totalrebate", totalrebate);
					put("sitecode",sitecode);
					//System.err.println(sitecode);
				}
			});
		}
		return bills;

	}

	@Override
	public BillingLedgerEntity getLatestBillNotNull(String connection_no) {
		return billingLedgerDao.getLatestBillNotNull(connection_no);
	}

	@Override
	public void customSave(BillingLedgerEntity ble) {
		billingLedgerDao.customsave(ble);	
	}

	@Override
	public double getArrearsByConNoBM(String con) {
		return billingLedgerDao.getArrearsByConNoBM(con);	
	}

	@Override
	public long checkBillGenerationPending(String monthyear) {
		return billingLedgerDao.checkBillGenerationPending(monthyear);	
	}

	@Override
	public List<?> getLedgerDataByWardAdvanceSubmit(String monthyearnep, String format, String format2) {
		return billingLedgerDao.getLedgerDataByWardAdvanceSubmit(monthyearnep,format,format2);	
	}

	@Override
	public BillingLedgerEntity getLatestRecordByConnectionNo(String conNo, String branch) {
		return billingLedgerDao.getLatestRecordByConnectionNo(conNo,branch);
	}

	@Override
	public void update(BillingLedgerEntity billingLedgerEntity, String branch) {
		billingLedgerDao.update(billingLedgerEntity,branch);
		
		
		
	}

	@Override
	public BillingLedgerEntity getBillByConNoAndMY1(String connectionNo, Integer to_mon_year) {
		return billingLedgerDao.getBillByConNoAndMY1(connectionNo,to_mon_year+"");
	}
}

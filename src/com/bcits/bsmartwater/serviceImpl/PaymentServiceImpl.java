package com.bcits.bsmartwater.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.BillingLedgerDao;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.dao.PaymentsDao;
import com.bcits.bsmartwater.model.PaymentEntity;
import com.bcits.bsmartwater.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired
	private PaymentsDao paymentsDao;
	
	@Autowired
	BillingLedgerDao billingLedgerDao;

	@Autowired
	private MasterGenericDAO masterGenericDAO;
	
	@Override
	public List<?> getCashDetails(String conectionVal, String receiptVal, String payTowardsVal, String amount,
			String nepalDate, String englishDate, String bankname, String chequeNo, String chequedateNepal,
			String paymode, String chequedateEng, String wardno, HttpServletRequest request) {

		List<?> ledgerList = paymentsDao.getCashDetails(conectionVal, receiptVal, payTowardsVal, amount, nepalDate,
				englishDate, bankname, chequeNo, chequedateNepal, paymode, chequedateEng, wardno, request);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = ledgerList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[]) i.next();
					put("connection_no", (String) values[0]);
					put("receipt_no", (String) values[1]);
					// put("receipt_date", values[2]==null?0:(Double)values[2]);
					if (values[2] == null) {
						put("receipt_date", "");
					} else {

						String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)values[2]);
						String english[] = date.split("-");
						int cday = Integer.parseInt(english[0]);
						if ("02".equalsIgnoreCase(english[1])) {
							cday = cday + 3;
						}
						if ("04".equalsIgnoreCase(english[1])) {
							cday = cday + 1;
						}
						if ("06".equalsIgnoreCase(english[1])) {
							cday = cday + 1;
						}
						if ("09".equalsIgnoreCase(english[1])) {
							cday = cday + 1;
						}
						if("11".equalsIgnoreCase(english[1])){
							cday=cday+1;
						}
						int cmonth = Integer.parseInt(english[1]);
						int cyear = Integer.parseInt(english[2]);
						String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
						put("receipt_date", nepalimonthyear);
					}

					put("cd_no", values[3] == null ? "" : values[3]);
					put("pay_mode", values[4] == null ? "" : values[4]);
					put("amount", values[5] == null ? "" : values[5]);
					put("bank_nam", values[6] == null ? "" : values[6]);
					put("towards", values[7] == null ? "" : values[7]);
					put("ward_no", values[8] == null ? "" : values[8]);

				}
			});
		}
		return bills;
	}

	@Override
	public List<Object[]> searchByConnectionNo(String connectionNo,
			String schema, HttpServletRequest request) {
		return paymentsDao.searchByConnectionNo(connectionNo, schema, request);

	}
	
	
	
	

	@Override
	public List<Object[]> wrongPostingData(String connectionNo, String schema,
			String rdate, HttpServletRequest request) {
		// rdateNew=new SimpleDateFormat("yyyy-MM-dd").format(new
		// SimpleDateFormat("dd-MM-yyyy").parse(rdate));
		return paymentsDao.wrongPostingData(connectionNo, rdate, schema,
				request);

	}

	@Override
	public Object updateWrongPost(Map<String, String> map, String schema,HttpServletRequest request) {
		return paymentsDao.updateWrongPost(map, schema, request);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> viewPaymentHistory(String connectionNo) {

		List<?> paymentList = paymentsDao.viewPaymentHistory(connectionNo);
		List<Map<String, Object>> payments = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = paymentList.iterator(); i.hasNext();) {
			payments.add(new HashMap<String, Object>() {
				{

				

					final Object[] values = (Object[])i.next();
					put("amount", (Double)values[0]);
					put("receiptNo", (String)values[1]);
					put("pdateeng", values[2]==null?"":paymentsDao.getDate3((Date)values[2]));
					put("pdatenep", (String)values[3]);
					put("remarks", values[4]==null?"":(String)values[4]);

					put("penalty", values[5]==null?0:(Double)values[5]);
					put("rebate", values[6]==null?0:(Double)values[6]);
					if((String)values[7]!=null){
						if(((String)values[7]).equalsIgnoreCase("2")){
							put("paymode", "Cheque");

						}else if(((String)values[7]).equalsIgnoreCase("3")){
							put("paymode", "DD");

						}else{
							put("paymode", "Cash");
	
						}
					}
				}
			});
		}
		return payments;

	}

	@Override
	public PaymentEntity getLatestBillReceipt(String connectionNo) {

		return paymentsDao.getLatestBillReceipt(connectionNo);
	}

	/*@Override
	public double getSumOfAmoutBWLastRDTandToday(String connection_no,
			Date previous_read_date, Date today) {

		return paymentsDao.getSumOfAmoutBWLastRDTandToday(connection_no,
				previous_read_date, today);
	}*/

	public double getSumOfAmoutBWPcashposAndCashPosDate(String connection_no,Date pcashPostedDate,String schemaName) {
		
		return paymentsDao.getSumOfAmoutBWPcashposAndCashPosDate(connection_no,pcashPostedDate,schemaName);

	}

	@Override
	public Integer getpaymentMaxId() {
		return paymentsDao.getpaymentMaxId();
	}

	@Override
	public PaymentEntity getpaymentBasedOnId(Integer id) {
		return paymentsDao.getpaymentBasedOnId(id);
	}

	@Override
	public PaymentEntity save(PaymentEntity paymentEntity) {
		
		return paymentsDao.customsave(paymentEntity);

	}

	@Override
	public List<?> getTodayCashSummery(Integer counterNo) {
		List<?> list = paymentsDao.getTodayCashSummery(counterNo);
		List<Map<String, Object>> result = new ArrayList<>();

		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<>();

			data.put("cashcount", obj[0]);
			data.put("cdcount", obj[1]);
			data.put("cashamount", obj[2]);
			data.put("cdamount", obj[3]);
			data.put("totalcount", ((Long) obj[0]) + ((Long) obj[1]));
			data.put("totalAmount", (Double) obj[2] + (Double) obj[3]);

			result.add(data);
		}

		return result;
	}

	@Override
	public PaymentEntity getPaymentEntityBasedOnReceiptNo(String receiptNo) {
		return paymentsDao.getPaymentEntityBasedOnReceiptNo(receiptNo);
	}

	@Override
	public List<?> getPaymentsDataByConnectionNum(String connId) {
		// return paymentsDao.getPaymentsDataByConnectionNum(connId);

		List<?> list = paymentsDao.getPaymentsDataByConnectionNum(connId);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			
			if (obj[0] == null) {
				data.put("rdate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[0]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 2;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if("11".equalsIgnoreCase(english[1])){
					cday=cday+1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("rdate", nepalimonthyear);
			}
			data.put("receiptNo", obj[1]);
			data.put("amount", obj[2]);
			data.put("advance", obj[3]);
			data.put("advance_rebate", obj[4]);
			data.put("towards", obj[5]);
			data.put("payMode", obj[6]);
			data.put("penalty", obj[7]);
			data.put("rebate", obj[8]);
			data.put("fmy", obj[9]);
			data.put("tmy", obj[10]);
			data.put("billAmt", obj[11]);
			data.put("balanceAmt", obj[12]);
			data.put("finalRecvAmt", obj[13]);
			data.put("rbalance", obj[14]);
			data.put("water_charges", obj[15]);
			data.put("sw_charges", obj[16]);
			data.put("meter_rent", obj[17]);
			data.put("miscellaneous_cost", obj[18]);
			data.put("bill_adj_amount", obj[19]);
			data.put("penalty_adj_amount", obj[20]);
			data.put("cancelledremarks", obj[21]);
			data.put("old_balance", obj[22]);
			result.add(data);

		}
		return result;
	}
		
		

	@Override
	public List<?> getdataforDayClose(Integer counterNo, Date rDate) {
		List<?> list= paymentsDao.getdataforDayClose(counterNo,rDate);
		List<Map<String, Object>> result=new ArrayList<>();
		
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> data=new HashMap<>();

			System.out.println((Double) obj[2]+"===(Double) obj[2]"+(Double) obj[3]+"===="+(Double) obj[3]);
			data.put("cashcount", obj[0]);
			data.put("cdcount", obj[1]);
			data.put("cashamount", obj[2]);
			data.put("cdamount", obj[3]);
			data.put("totalcount", ((Long) obj[0]) + ((Long) obj[1]));
			data.put("totalAmount", (Double) obj[2] + (Double) obj[3]);

			result.add(data);
		}

		return result;
	}

	@Override
	public Date getMaxDayForDayClose(Integer counter_no) {
		return paymentsDao.getMaxDayForDayClose(counter_no);
	}

	@Override
	public List<String> getGraphicalViewDataForMonth(String siteCode) {
		return paymentsDao.getGraphicalViewDataForMonth(siteCode);
	}

	@Override
	public String getTotalCollection(String siteCode) {
		return paymentsDao.getTotalCollection(siteCode);
	}


	@SuppressWarnings("serial")
	@Override
	public List<?> viewBillLedgertHistory(String connectionNo) {
		List<?> ledgerList =billingLedgerDao.viewBillLedgertHistory(connectionNo);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = ledgerList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", (String)values[0]);
					put("billno", (String)values[1]);
					put("water_charges", values[2]==null?0:(Double)values[2]);
					put("sw_charges", values[3]==null?0:(Double)values[3]);
					put("lastarrears", values[4]==null?0:(Double)values[4]);
					put("mtr_rent", values[5]==null?0:(Double)values[5]);
					put("net_amount", values[6]==null?0:(Double)values[6]);
					put("monthyearnep", (String)values[7]);
					put("close_balnce", values[8]==null?"":(Double)values[8]);
					put("receipt_no", values[9]==null?"":(String)values[9]);
					
					if(values[10]==null){
						put("receipt_date","");
					}else{
						
						String date=new SimpleDateFormat("dd-MM-yyyy").format((Date)values[10]);
						String english[]=date.split("-");
						int cday=Integer.parseInt(english[0]);
						if("02".equalsIgnoreCase(english[1])){
							cday=cday+2;
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
						put("receipt_date", nepalimonthyear);
					}
					
					//put("receipt_date", values[10]==null?"":billingLedgerDao.getDate3((Date)values[10]));
					
					put("receipt_amount", values[11]==null?"":(Double)values[11]);
					put("penalty", values[12]==null?"":(Double)values[12]);
					put("rebate", values[13]==null?"":(Double)values[13]);
					
					put("lr", values[14]==null?"":(Double)values[14]);
					put("cr", values[15]==null?"":(Double)values[15]);
					put("units", values[16]==null?"":(Double)values[16]);
					put("pipe_size", values[17]);
					put("bill_adj", values[18]==null?"":(Double)values[18]);
					put("penalty_adj", values[19]==null?"":(Double)values[19]);
					put("misc", values[20]==null?"":(Double)values[20]);
					put("arrear_correction", values[21]==null?"":(Double)values[21]);
					put("penalty_correction", values[22]==null?"":(Double)values[22]);
					put("rebate_correction", values[23]==null?"":(Double)values[23]);

					
				}
			});
		}
		return bills;
	}

	@Override
	public void customupdate(PaymentEntity paymentdata) {
		paymentsDao.customupdate(paymentdata);
		
	}

	@Override
	public Double getDayClosePaymentDetails(Integer counterNo, String date) {
		return paymentsDao.getDayClosePaymentDetails(counterNo, date);
	}

	@Override
	public List<?> getDayClosePaymentDetails1(Integer counterNo, String date) {
		return paymentsDao.getDayClosePaymentDetails1(counterNo, date);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> viewBillLedgertHistoryForReading(String connectionNo) {

		List<?> ledgerList =billingLedgerDao.viewBillLedgertHistoryForReading(connectionNo);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = ledgerList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", (String)values[0]);
					put("billno", (String)values[1]);
					put("water_charges", values[2]==null?0:(Double)values[2]);
					put("sw_charges", values[3]==null?0:(Double)values[3]);
					put("lastarrears", values[4]==null?0:(Double)values[4]);
					put("mtr_rent", values[5]==null?0:(Double)values[5]);
					put("net_amount", values[6]==null?0:(Double)values[6]);
					put("monthyearnep", (String)values[7]);
					put("close_balnce", values[8]==null?"":(Double)values[8]);
					put("receipt_no", values[9]==null?"":(String)values[9]);
					put("receipt_date", values[10]==null?"":billingLedgerDao.getDate3((Date)values[10]));
					put("receipt_amount", values[11]==null?"":(Double)values[11]);
					put("name_eng", values[12]==null?"":(String)values[12]);
					put("pipe_size", values[13]);
					put("name_nep", values[14]==null?"":(String)values[14]);
					
					if(values[15]==null){
						put("obname", "");
					}else{
						
						if((Integer)values[15]==1){
							put("obname", "Door Lock");
						}else if((Integer)values[15]==2){
							put("obname", "Meter Block");
						}else if((Integer)values[15]==3){
							put("obname", "Meter Burried");
						}else if((Integer)values[15]==4){
							put("obname", "Meter Damaged");
						}else if((Integer)values[15]==5){
							put("obname", "House not found");
						}else if((Integer)values[15]==6){
							put("obname", "No Water Supply");
						}else if((Integer)values[15]==7){
							put("obname", "Meter Removed(No Meter)");
						}else if((Integer)values[15]==8){
							put("obname", "Low Water Supply");
						}else if((Integer)values[15]==9){
							put("obname", "Dog Presence");
						}else if((Integer)values[15]==10){
							put("obname", "Meter Sheild Broken");
						}else if((Integer)values[15]==11){
							put("obname", "Temporary hole block");
						}else if((Integer)values[15]==12){
							put("obname", "Permanent hole block");
						}else if((Integer)values[15]==13){
							put("obname", "Service Line disconnected");
						}else if((Integer)values[15]==14){
							put("obname", "House Collapse(Earthquake)");
						}else if((Integer)values[15]==15){
							put("obname", "Unmetered");
						}else if((Integer)values[15]==16){
							put("obname", "Reading");
						}
						else if((Integer)values[15]==17){
							put("obname", "No Reading");
						}
						else if((Integer)values[15]==18){
							put("observation", "Dual Record");
						}
						else if((Integer)values[15]==19){
					        put("observation", "PID");
						}
						
					}
					double lr=values[16]==null?0:(double)values[16];
					double cr=values[17]==null?0:(double)values[17];
					put("previous_reading", lr);
					put("present_reading", cr);
					put("units", (cr-lr));
					put("area_no", values[18]);
				}
			});
		}
		return bills;
	
	}

	@Override
	public List<?> getPaymentEntityBasedOnReceiptNo(String date,Integer counter_no) {
		List<?> list= paymentsDao.getPaymentEntityBasedOnReceiptNo(date,counter_no);
		
		List<Map<String, Object>> result=new ArrayList<>();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			//p.connectionNo,c.name_eng,p.receiptNo,p.rdate,p.bill_amount,p.amount,p.towards,p.payMode,p.cancelledremarks
			Map<String, Object> data=new HashMap<>();
			data.put("connectionNo", (String)obj[0]);
			data.put("name_eng", (String)obj[1]);
			data.put("receiptNo", (String)obj[2]);
			data.put("rdate", new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[3]));
			data.put("bill_amount", obj[4]);
			data.put("amount", obj[5]);
			data.put("towards", obj[6]);
			data.put("payMode", obj[7]);
			data.put("cancelledremarks", (String)obj[8]);
			
			String rdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[3]);
			String english[]=rdate.split("-");
			int cday=Integer.parseInt(english[0]);
			int cmonth=Integer.parseInt(english[1]);
			int cyear=Integer.parseInt(english[2]);
			System.out.println(cday+"cday"+cmonth+"cmonth"+cyear);
			String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			System.out.println(nepalimonthyear+"===============nepalimonthyear");
			data.put("nepalimonthyear", nepalimonthyear);
			result.add(data);
		}
		return result;
	}

	@Override
	public List<PaymentEntity> getPaymentDataByConnNumNew(String connId,String schemaName) {
		return paymentsDao.getPaymentDataByConnNumNew(connId,schemaName);

	}

	@Override
	public long getMaxReceiptNo(String receiptno) {
		return paymentsDao.getMaxReceiptNo(receiptno);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> viewMyPaymentHistory(Integer counterno, String rdate) {

	    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh.mm.ss.S aa");
		List<?> ledgerList =paymentsDao.viewMyPaymentHistory(counterno,rdate);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = ledgerList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					
					final Object[] values = (Object[])i.next();
					put("connection_no", values[0]);
					put("name_eng", values[1]);
					put("ward_no", values[2]);
					put("balance_amount", values[3]);
					put("water_charges", values[4]);
					put("sw_charges", values[5]);
					put("meter_rent", values[6]);
					put("bill_amount", values[7]);
					put("miscellaneous_cost",values[8]);
					put("penalty", values[9]);
					put("rebate", values[10]);
					put("bill_adj_amount", values[11]);
					put("penalty_adj_amount", values[12]);
					put("amount", values[13]);
					put("rdate", dateFormat.format((Date)values[14]).toString());
					put("receiptNo", values[15]);
					
				}
			});
		}
		return bills;
	
	
	}

	@Override
	public PaymentEntity getPaymentEntityBasedOnReceiptNo(String receiptNo,String fromConnectionNo) {
		return paymentsDao.getPaymentEntityBasedOnReceiptNo(receiptNo,fromConnectionNo);
	}

	@Override
	public long getMaxReceiptNo(String receiptno, Integer counterNo) {
		return paymentsDao.getMaxReceiptNo(receiptno,counterNo);
	}

	@Override
	public long getTotalPaymentsByConNoMYN(String connectionNo,Integer to_mon_year) {
		
		return paymentsDao.getTotalPaymentsByConNoMYN(connectionNo,to_mon_year);
	}

	@Override
	public List<Object[]> getLastPaymentDetailsByCNNoMYN(String connectionNo,Integer to_mon_year) {
		return paymentsDao.getLastPaymentDetailsByCNNoMYN(connectionNo,to_mon_year);
	}
	@Override
	public List<Object[]> getLastPaymentDetailsByCNNoMYN1(String connectionNo,Integer to_mon_year) {
		return paymentsDao.getLastPaymentDetailsByCNNoMYN1(connectionNo,to_mon_year);
	}

	@Override
	public List<?> generateDayCloseMisc(Integer counterNo, String date) {
		return paymentsDao.generateDayCloseMisc(counterNo, date);
	}


	public List<?> getTransactionDataByConnNum(String connId, int myn) {
		// return paymentsDao.getPaymentsDataByConnectionNum(connId);

		List<?> list = paymentsDao.getTransactionDataByConnNum(connId,myn);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			
			if (obj[0] == null) {
				data.put("rdate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[0]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 3;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if("11".equalsIgnoreCase(english[1])){
					cday=cday+1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("rdate", nepalimonthyear);
			}
			data.put("receiptNo", obj[1]);
			data.put("amount", obj[2]);
			data.put("advance", obj[3]);
			data.put("advance_rebate", obj[4]);
			data.put("towards", obj[5]);
			data.put("payMode", obj[6]);
			data.put("penalty", obj[7]);
			data.put("rebate", obj[8]);
			data.put("fmy", obj[9]);
			data.put("tmy", obj[10]);
			data.put("billAmt", obj[11]);
			data.put("balanceAmt", obj[12]);
			data.put("finalRecvAmt", obj[13]);
			data.put("rbalance", obj[14]);
			data.put("water_charges", obj[15]);
			data.put("sw_charges", obj[16]);
			data.put("meter_rent", obj[17]);
			data.put("miscellaneous_cost", obj[18]);
			data.put("bill_adj_amount", obj[19]);
			data.put("penalty_adj_amount", obj[20]);
			data.put("cancelledremarks", obj[21]);
			data.put("old_balance", obj[22]);
			result.add(data);

		}
		return result;
	}

	@Override
	public List<?> getPaymentEntityBasedOnReceiptNoNew(String date, Integer counter_no) {
		List<?> list= paymentsDao.getPaymentEntityBasedOnReceiptNoNew(date,counter_no);
		
		List<Map<String, Object>> result=new ArrayList<>();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			//p.connectionNo,c.name_eng,p.receiptNo,p.rdate,p.bill_amount,p.amount,p.towards,p.payMode,p.cancelledremarks
			Map<String, Object> data=new HashMap<>();
			data.put("connectionNo", (String)obj[0]);
			data.put("name_eng", (String)obj[1]);
			data.put("receiptNo", (String)obj[2]);
			data.put("rdate", new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[3]));
			data.put("bill_amount", obj[4]);
			data.put("amount", obj[5]);
			data.put("towards", obj[6]);
			data.put("payMode", obj[7]);
			data.put("cancelledremarks", (String)obj[8]);
			
			String rdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[3]);
			String english[]=rdate.split("-");
			int cday=Integer.parseInt(english[0]);
			int cmonth=Integer.parseInt(english[1]);
			int cyear=Integer.parseInt(english[2]);
			System.out.println(cday+"cday"+cmonth+"cmonth"+cyear);
			String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			System.out.println(nepalimonthyear+"===============nepalimonthyear");
			data.put("nepalimonthyear", nepalimonthyear);
			result.add(data);
		}
		return result;
	}

	@Override
	public PaymentEntity getMisReceiptDetails(String recptNo, Date date) {
		
		return paymentsDao.getMisReceiptDetails(recptNo, date);
	}
	
}

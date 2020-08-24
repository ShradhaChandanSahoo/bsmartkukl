package com.bcits.bsmartwater.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bcits.bsmartwater.model.PaymentEntity;

public interface PaymentsDao extends GenericDAO<PaymentEntity>
{
	List<Object[]> getCashDetails(String conectionVal, String receiptVal,
			String payTowardsVal, String amount, String nepalDate,
			String englishDate, String bankname, String chequeNo,
			String chequedateNepal, String paymode, String chequedateEng,
			String wardno, HttpServletRequest request);

	List<Object[]> searchByConnectionNo(String connectionNo, String schema,
			HttpServletRequest request);

	List<Object[]> wrongPostingData(String connectionNo,String rdate, String schema,HttpServletRequest request);

	Object updateWrongPost(Map<String, String> map, String schema,HttpServletRequest request);

	List<?> viewPaymentHistory(String connectionNo);


	PaymentEntity getLatestBillReceipt(String connectionNo);

	double getSumOfAmoutBWPcashposAndCashPosDate(String connection_no,Date previous_read_date,String schemaName);


	Integer getpaymentMaxId();

	PaymentEntity getpaymentBasedOnId(Integer id);
	PaymentEntity getMisReceiptDetails(String recptNo,Date date);

	List<?> getTodayCashSummery(Integer counterNo);

	PaymentEntity getPaymentEntityBasedOnReceiptNo(String receiptNo);

	List<?> getPaymentsDataByConnectionNum(String connId);
	List<?> getTransactionDataByConnNum(String connId, int monthYear);
	List<?> getdataforDayClose(Integer counterNo, Date rDate);

	Date getMaxDayForDayClose(Integer counter_no);

	List<String> getGraphicalViewDataForMonth(String siteCode);

	String getTotalCollection(String siteCode);

	Double getDayClosePaymentDetails(Integer counterNo, String date);

	List<?> getDayClosePaymentDetails1(Integer counterNo, String date);

	List<?> getPaymentEntityBasedOnReceiptNo(String date,Integer counter_no);

	List<PaymentEntity> getPaymentDataByConnNumNew(String connId,String schemaName);

	long getMaxReceiptNo(String receiptno);

	List<?> viewMyPaymentHistory(Integer counterno, String rdate);

	PaymentEntity getPaymentEntityBasedOnReceiptNo(String receiptNo,String fromConnectionNo);

	long getMaxReceiptNo(String receiptno, Integer counterNo);

	long getTotalPaymentsByConNoMYN(String connectionNo, Integer to_mon_year);

	List<Object[]> getLastPaymentDetailsByCNNoMYN(String connectionNo,Integer to_mon_year);

	List<?> generateDayCloseMisc(Integer counterNo, String date);

	List<Object[]> getLastPaymentDetailsByCNNoMYN1(String connectionNo, Integer to_mon_year);

	List<?> getPaymentEntityBasedOnReceiptNoNew(String date, Integer counter_no);
	





}

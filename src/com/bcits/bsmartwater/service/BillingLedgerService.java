package com.bcits.bsmartwater.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.bsmartwater.dao.DoubleLedgerValidationDao;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.PaymentEntity;

public interface BillingLedgerService {

	List<?> findByConnectionNo(String connectionNo);

	long ledgerCountByWardNo(String wardNo, String monthyearnep);

	long billedLedgerCountByWardNo(String wardNo, String monthyearnep);

	long unbilledLedgerCountByWardNo(String wardNo, String monthyearnep);

	BillingLedgerEntity getBillByConNoAndMY(String connection_no, int monthyear);

	void update(BillingLedgerEntity billingLedgerEntity);

	List<?> getReadingEntryUnbilled(String wardNo);

	void updatePresReadAndMCStatusByBillId(double present_reading, int mc_status,int billid);

	String updateMonthEndProcess(String monthyear,String nepMY,String userName);

	int getMaxMonthYear();

	long monthEndValid(int engMY);

	void updateRdngDateAndDueDate(int parseInt, String wardNo,String rdng_date, String rdng_date_nep, String due_date,String due_date_nep);

	String getMaxBillNoBysiteCode(String string);

	BillingLedgerEntity findById(int billid);


	BillingLedgerEntity findBillingLedgerBasedOnConnectionNo(String connectionNo);

	BillingLedgerEntity getLastMonthRecord(String connectionNo);

	List<Object[]> generateReports(String neplaliFMonth, String engFmonth,String neplaliTMonth, String engTmonth, String counterNoSel,String mrcodeSel, String reportName, ModelMap model,HttpServletRequest request);
	List<BillingLedgerEntity> getLedgerDataByConnectionNum(String connId);
	List<?> getWardWiseBillCount(String sitecode);

	List<String> getDistinctWardNo(String schemaName);
	List<?> getConnectionHistory(String wardNo,int readingday, int value);

	List<String> getDistinctCounter(String schemaName);



	List<?> billedLedgerByWardNo(String wardNo, int reading_day, int reading_month, double pipesize, String concategory);

	String getTotalBilledCount(String siteCode);

	String getTotalDemand(String siteCode);

	String getLatestMonthYear(String string);

	List<?> billedLedgerByConnectionNoCM(String connection_no);

	Object[] getPenaltyRebatePercentByConNo(String connectionNo);

	List<?> findBillsByReceiptNull(String connectionNo);

	BillingLedgerEntity findMaxRecordNotNullReceipt(String connectionNo);

	double getAvgConsumption(String connectionNo);

	long ledgerCountByWardNoRdayPS(String wardNo, Integer readingday,
			double pipesize, String monthyear, String concategory);

	long billedLedgerCountByWardNoRdayPS(String wardNo, Integer readingday,
			double pipesize, String monthyear, String concategory);

	long unbilledLedgerCountByWardNoRdayPS(String wardNo, Integer readingday,
			double pipesize, String monthyear, String concategory);

	List<?> getReadingEntryUnbilled(String wardNo, int reading_day,
			double pipe_size);

	List<BillingLedgerEntity> getListByConNoAndRECNo(String connectionNo,
			String receiptNo);

	int custombatchUpdate(List<BillingLedgerEntity> billingLedgerList);

	long checkEntriesExist(String monthyearnep);

	String updateBulkBillingUnmetered(String userName, String monthyearnep,String schemaName,String sitecode, DoubleLedgerValidationDao doubleLedgerValidationDao);

	void updateBillLedger(String bConnectionNo, Double bPenalty,
			Double bRebate, Double bOther, String bReceiptNo,
			Double bReceivedAmount, Double bFrecamount,
			Double bAdvanceRebate, Double bOldbalance, String schemaName, Integer tomonthyear, Double bill_adj_amount, Double penalty_adj_amount, Date rdate, String adjtype);

	int custombatchUpdateBillingLedgerEntity(
			List<BillingLedgerEntity> billlist, PaymentEntity paymentdata, String schemaName);

	long checkLedgerReceiptExists(String connectionno);

	String getLatestNepaliMonthYear();

	List<?> getLedgerDataByWardMonthEnd(String string, String fromdate, String todate);

	void generateLedgerByWardRdngPipeSize(String wardNo, int parseInt,double parseDouble, String userName,String nepmonthyear,String sitecode);

	long getCheckBillsExist(String wardNo, int reading_day,double pipe_size, String nepmonthyear);

	List<?> printBillUnmeteredBilled(String wardNo, String reading_monthu);

	BillingLedgerEntity findByConnectionNoByMYN(String connectionNo, String monthyearnep);

	List<?> getWardWiseBillCountUnMetered(String attribute);

	List<?> getConnectionHistory(String wardNo, int value);

	Object[] getMasterStatistics();


	List<?> findBillsByReceiptNullByMonthYear(String connectionNo,int yearmntfr, int yearmntto);

	long checkLedgerLatestExists(String connectionno);

	BillingLedgerEntity getLatestRecordByConnectionNo(String connectionNo);

	Object[] getDoorLockCount(String connectionNo);

	void save(BillingLedgerEntity billLedgerEntity);

	List<BillingLedgerEntity> getLedgerDataByConnNumNew(String connId,String schemaName);

	Object[] getOpeningBalanceByConNo(String connectionNo);

	List<?> getLedgerDataByWardMonthEndSubmit(String monthyearnep, String format, String format2);

	List<?> getLedgerDataByWardMonthEndSubmitManual(String monthyearnep,String format, String format2);

	BillingLedgerEntity findLastMonthRecordByConNo(String connectionNo);

	BillingLedgerEntity findRecordByConNoMYN(String connection_no,Integer monthyearnep);

	List<?> getconnectionMasterLedgerDetails(String conNo,HttpServletRequest req);

	BillingLedgerEntity getLatestBillNotNull(String string);

	void customSave(BillingLedgerEntity ble);

	List<?> printBillGovt(String con_category1, String reading_monthg);

	long ledgerCountByConCategory(String con_category1, String monthyearnep);

	long billedLedgerCountByConCategory(String con_category1,
			String monthyearnep);

	long unbilledLedgerCountByConCategory(String con_category1,
			String monthyearnep);

	double getArrearsByConNoBM(String connectionNo);

	long checkBillGenerationPending(String monthyear);

	List<?> getLedgerDataByWardAdvanceSubmit(String monthyearnep, String format, String format2);

	BillingLedgerEntity getLatestRecordByConnectionNo(String conNo, String branch);

	void update(BillingLedgerEntity billingLedgerEntity, String branch);

	BillingLedgerEntity getBillByConNoAndMY1(String connectionNo, Integer to_mon_year);




}

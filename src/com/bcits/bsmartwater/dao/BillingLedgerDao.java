package com.bcits.bsmartwater.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.PaymentEntity;

public interface BillingLedgerDao extends GenericDAO<BillingLedgerEntity>{

	List<BillingLedgerEntity> findByConnectionNo(String connectionNo);

	BillingLedgerEntity getBillByConNoAndMY(String connection_no, int monthyear);
	
	BillingLedgerEntity getBillByConNoAndMY1(String connection_no, String monthyearnep);

	long ledgerCountByWardNo(String wardNo, String monthyearnep);

	long billedLedgerCountByWardNo(String wardNo, String monthyearnep);

	long unbilledLedgerCountByWardNo(String wardNo, String monthyearnep);

	List<?> getReadingEntryUnbilled(String wardNo);
	

	void updatePresReadAndMCStatusByBillId(double present_reading,int mc_status, int billid);

	List<BillingLedgerEntity> updateMonthEndProcess(String monthyear);

	int getMaxMonthYear();

	long monthEndValid(int monthYear);

	List<BillingLedgerEntity> getBillListByMYAndWardNo(int monthyear,String wardNo);

	String getMaxBillNoBysiteCode(String sitecode);


	BillingLedgerEntity findBillingLedgerBasedOnConnectionNo(String connectionNo);


	BillingLedgerEntity getLastMonthRecord(String connectionNo);

	List<Object[]> generateReports(String neplaliFMonth, String engFmonth,String neplaliTMonth, String engTmonth, String counterNoSel,String mrcodeSel, String reportName, ModelMap model,HttpServletRequest request);

	List<String> getDistinctWardNo(String schemaName);

	List<String> getDistinctCounter(String schemaName);

	List<BillingLedgerEntity> getLedgerDataByConnectionNum(String connId);

	List<?> getWardWiseBillCount(String sitecode);

	List<?> getConnectionHistory(String wardNo, int readingday,int value);


	long checkConnect_noInMaster(String connection_no);

	List<?> billedLedgerByWardNo(String wardNo, int reading_day, int reading_month, double pipesize, String concategory);

	String getTotalBillingCount(String siteCode);

	String getTotalDemand(String siteCode);

	String getLatestMonthYear(String siteCode);

	List<?> billedLedgerByConnectionNoCM(String connection_no);

	Date getMaxPaymentDateByConNo(String connectionNo);

	Date getMinReadDateByConNo(String connectionNo);

	List<?> viewBillLedgertHistory(String connectionNo);

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

	List<BillingLedgerEntity> getListByConNoAndRECNo(String connectionNo,String receiptNo);

	List<?> viewBillLedgertHistoryForReading(String connectionNo);

	long checkEntriesExist(String monthyearnep);

	List<ConsumerMaster> updateBulkBillingUnmetered();

	int custombatchUpdateBillingLedgerEntity( List<BillingLedgerEntity> billlist, PaymentEntity paymentdata, String schemaName);

	int custombatchUpdateBillingLedgerEntity(List<BillingLedgerEntity> billingLedgerList, Double bPenalty, Double bRebate, Double bOther, String bReceiptNo, Double bReceivedAmount, Double bFrecamount, Double bAdvanceRebate, Timestamp today, String schemaName, Double bill_adj_amount, Double penalty_adj_amount, Date rdate, String adjtype);

	long checkLedgerReceiptExists(String connectionno);

	String getLatestNepaliMonthYear();

	List<?> getLedgerDataByWardMonthEnd(String monthyear, String fromdate, String todate);

	BillingLedgerEntity getLatestRecordByConnectionNo(String connection_no);

	long getMissedBillsInLedger(String wardNo, int readingday, double pipesize, String nepmonthyear);

	String getMaxBillNoByWardNo(String string);

	List<ConsumerMaster> getLedgetInsertByWardNoRday(String wardNo,int readingday, double pipesize, String monthyearnep);

	List<BillingLedgerEntity> findBillsByReceiptNullAll(String bConnectionNo);

	int saveAsyncBulkBillUnmetered(String userName, String monthyearnep,
			List<ConsumerMaster> consumerMasters, int cmonth, int cyear,
			String nepalidate, String schemaName,String sitecode);

	List<?> printBillUnmeteredBilled(String wardNo, String reading_monthu);

	BillingLedgerEntity findByConnectionNoByMYN(String connectionNo,String monthyearnep);

	List<String> findByMYN();
	
	List<?> getWardWiseBillCountUnMetered(String sitecode);
	
	 
	List<?> getConnectionHistoryUn(String wardNo, int value);

	String findObsByConnectionNoByMYN(String string, String string2);

	Object[] getMasterStatistics();


	List<?> findBillsByReceiptNullByMonthYear(String connectionNo,
			int yearmntfr, int yearmntto);


	long checkLedgerLatestExists(String connectionno);


	List<BillingLedgerEntity> findBillsByReceiptNullAllMYN(String bConnectionNo, Integer tomonthyear);

	int getMaxMonthYearNepali();

	Object[] getDoorLockCount(String connectionNo);

	List<BillingLedgerEntity> getLedgerDataByConnNumNew(String connId,String schemaName);

	Object[] getOpeningBalanceByConNo(String connectionNo);

	List<ConsumerMaster> updateBulkBillingUnmetered(String monthyearnep);

	List<?> getLedgerDataByWardMonthEndSubmitManual(String monthyearnep,String fromdate, String todate);

	BillingLedgerEntity findLatestLedgerBYCNSchema(String connectionno,String testLocation);

	List<BillingLedgerEntity> findBillsFromMaxReceiptNo(String connectionno,String testLocation);

	BillingLedgerEntity findLastMonthRecordByConNo(String connectionNo);

	BillingLedgerEntity findRecordByConNoMYN(String connection_no,Integer monthyearnep);

	List<?> getconnectionMasterLedgerDetails(String conNo);

	BillingLedgerEntity getLatestBillNotNull(String connection_no);

	List<?> printBillGovt(String con_category1, String reading_monthg);

	long ledgerCountByConCategory(String con_category1, String monthyearnep);

	long billedLedgerCountByConCategory(String con_category1,
			String monthyearnep);

	long unbilledLedgerCountByConCategory(String con_category1,
			String monthyearnep);

	double getArrearsByConNoBM(String con);

	long checkBillGenerationPending(String monthyear);

	List<?> getLedgerDataByWardAdvanceSubmit(String monthyearnep, String format, String format2);

	BillingLedgerEntity getLatestRecordByConnectionNo(String conNo, String branch);

	void update(BillingLedgerEntity billingLedgerEntity, String branch);




}

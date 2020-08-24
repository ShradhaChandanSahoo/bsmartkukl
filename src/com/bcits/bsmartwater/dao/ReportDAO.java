package com.bcits.bsmartwater.dao;

import java.util.Date;
import java.util.List;

import org.springframework.ui.Model;

import com.bcits.bsmartwater.model.DailyCashCounterReport;
import com.bcits.bsmartwater.model.MeterChangeDetailsEntity;
import com.bcits.bsmartwater.utils.DataSourceRequest;
import com.bcits.bsmartwater.utils.DataSourceResult;

public interface ReportDAO extends GenericDAO<DailyCashCounterReport> {

	DataSourceResult dailyCashCounterRead(DataSourceRequest request);

	List<?> categoryWiseSalesReportReadData(Date fromdate, Date todate, String mnthyrnep);

	List<?> wardWiseSalesReportReadData(String fromdate, String todate);

	List<?> monthlyObservationReportReadData(String fromdate, String todate,Model model);

	List<?> monthlySalesSummaryReportReadData(String fromdate, String todate, String fdate, String tdate);

	List<?> detailedCashCollReportReadData(Date fromdate, Date todate);

	List<?> meterReadingListReportRead(String wardno, int readingday, String monthyearnep,double pipesize);

	List<?> consumerBalanceReportRead(String amount, String criteria, String monthYear, String flag);

	List<?> cashCollectionListReportRead(String fromdate, String todate);
	
	List<?> cashCollectionListReportReadAll(String fromdate, String todate);
	
	List<?> detailedCashCollReportReadDataNew(String fromdate, String todate);
	
	public List<?> boarReportDetailstReadData(String fromdate, String todate);

	List<?> consumerListReportReadData();
	List<?> boardBalAdjCorrReportDetails(String myn,String col_catagory);
	
	public List<?> customerWiseAdvCollRepDetails(String monthyearnep);

	List<?> holeBlockReportReadData(String con_status);


	List<?> conTypeReportReadData(String con_Type);

	List<?> newConnectionReportDetails(String frmDt, String toDt,String citeCode,int catagory);
	
	List<?> dailyRevenueReportRead(String fromdate, String todate,String counter_no);

	List<?> revenueReportRead(String fromdate, String todate, String counter_no);

	List<?> wardWiseConsumerCountReport();

	List<?> multiPaymentRead(String amount, String criteria, String date1, String date2, String flag,String con_type);

	List<?> wardWiseMrReaderReportRead(String yearnep, String month);

	List<?> missedBillsReportRead(String yearnep, String month);

	List<?> custObservationReportRead(String monthYearNep, int mc_status);

	List<?> missedBillsReportLedger(String yearnep, String month);

	List<?> categoryCollectionReportRead(String from, String to, String category);

	List<?> cashCollectiontthRead(String fromdate, String todate);

	List<?> monthlySalesReadData(String monthyearnep);

	List<?> newMonthlySalesCategory(String monthyearnep, String con_category);

	List<?> counterRevenueReportRead(String fromdate, String todate);

	List<?> ledgerVerificationRead(String wardno, String monthyear, String reading_day);

	List<?> wardWiseCustomerBillingReportRead(String wardNo, String myn, String category);

	List<?> monthlyAdjustmentReportRead(String myn);
	
	List<?> categoryBoardCollectionReportRead(String from, String to);

	List<?> cancelReceiptReportRead(String from, String to);

	List<?> boardSalesReportRead(String from, String to);

	List<?> advanceCollectionReportRead(String monthyearnep);

	List<?> generatedailyMiscColReport(String fdate, String tdate,String counterNO);

	List<?> detailedMiscCollReportReadData(String fromDate, String toDate);

	List<?> getAbnormalBillingReport(String monthyearnep, String diff);

	List<?> categoryTypeReportReadData(String con_category);

	List<?> advanceCollectionReportReadNew(String monthyearnep);
	List<?> newConnectionReportDetailsCat(String frmDt, String toDt,String citeCode,int catagory,String concategory);
	
	List<?> categoryNewConnectionReportDetailsCat(String frmDt, String toDt,String citeCode,String conType,String concategory,String pipesize);
	List<MeterChangeDetailsEntity> meterChangeDetailsReport();
	
	List<?> boardRevenueReportRead(String fromdate, String todate, String counter_no);
	public List<?> generatedailyCashCounterCollReport(String fromDatenep, String toDatenep,String counterno,String pipesize ,String wardno ) ;
}

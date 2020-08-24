package com.bcits.bsmartwater.service;

import java.util.Date;
import java.util.List;

import org.springframework.ui.Model;

import com.bcits.bsmartwater.utils.DataSourceRequest;
import com.bcits.bsmartwater.utils.DataSourceResult;

public interface ReportService {

	DataSourceResult dailyCashCounterRead(DataSourceRequest request);

	List<?> categoryWiseSalesReportReadData(Date fromdate, Date todate, String mnthyrnep);

	List<?> wardWiseSalesReportReadData(String fromdate, String todate);
	
	List<?> monthlyObservationReportReadData(String fromdate, String todate,Model model);

	Object monthlySalesSummaryReportReadData(String date1, String date2, String fdate, String tdate);

	Object detailedCashCollReportReadData(String fromdate, String todate);

	Object boarReportDetailstReadData(String fromdate, String todate);

	Object meterReadingListReportRead(String wardno, int readinday, String monthyearnep,double pipesize);

	Object consumerBalanceReportRead(String amount, String criteria, String monthYear, String flag);
	
	List<?> multiPaymentRead(String amount, String criteria, String date1, String date2, String flag, String con_type);
	
	Object cashCollectionListReportRead(String date1, String date2);
	public List<?> customerWiseAdvCollRepDetails(String monthyearnep);
	
	Object cashCollectionListReportReadAll(String date1, String date2);

	Object consumerListReportReadData();

	Object holeBlockReportReadData(String con_status);

	List<?> generatedailyCashCounterCollReport(String fromDatenep, String toDatenep,String counterno,String wardno,String pipesize);

	Object conTypeReportReadData(String con_Type);
	List<?> newConnectionReportDetails(String frmDt, String toDt,String citeCode,int catagory);

	Object dailyRevenueReportRead(String date1, String date2, String counter_no);

	Object revenueReportRead(String format, String format2, String counter_no, String monthYearN);

	Object wardWiseConsumerCountReport();

	List<?> wardWiseMrReaderReportRead(String yearnep, String month);

	List<?> missedBillsReportRead(String yearnep, String month);

	Object custObservationReportRead(String monthYear, int parseInt);

	Object missedBillsReportLedger(String yearnep, String month);

	List<?> categoryCollectionReportRead(String from, String to, String category);

	List<?> cashCollectiontthRead(String from, String to);

	List<?> monthlySalesReadData(String monthyearnep);

	List<?> newMonthlySalesCategory(String monthyearnep, String con_category);

	 List<?> counterRevenueReportRead(String format, String format2, String monthYearN);

	List<?> ledgerVerificationRead(String wardno, String monthyear, String reading_day);

	List<?> wardWiseCustomerBillingReportRead(String wardNo, String myn, String category);

	List<?> monthlyAdjustmentReportRead(String myn);
	
	List<?> boardBalAdjCorrReportDetails(String myn,String col_catagory);

	List<?> categoryBoardCollectionReportRead(String from, String to);

	List<?> cancelReceiptReportRead(String format, String format2);

	List<?> boardSalesReportRead(String format, String format2);

	List<?> advanceCollectionReportRead(String monthyearnep);

	List<?> generatedailyMiscColReport(String fdate, String tdate,String counterNo);

	Object detailedMiscCollReportReadData(String fromDate, String toDate);

	List<?> getAbnormalBillingReport(String monthyearnep, String diff);

	List<?> categoryTypeReportReadData(String con_category);

	List<?> advanceCollectionReportReadNew(String monthyearnep);
	
	List<?> newConnectionReportDetailsCat(String frmDt, String toDt,String citeCode,int catagory,String concategory);
	
	List<?> categoryNewConnectionReportDetailsCat(String frmDt, String toDt,String citeCode,String conType,String concategory,String pipesize);
	Object meterChangeDetailsReport();
	
	Object dailyBoardRevenueReportRead(String format, String format2, String counter_no, String monthYearN);


}

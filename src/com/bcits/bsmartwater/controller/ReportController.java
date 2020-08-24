package com.bcits.bsmartwater.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bsmartwater.dao.BillingLedgerDao;
import com.bcits.bsmartwater.dao.ConsumerMasterDao;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.dao.ReportDAO;
import com.bcits.bsmartwater.model.BillApproveEntity;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.CounterDetails;
import com.bcits.bsmartwater.model.WardDetailsEntity;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.service.ConTypeChangeService;
import com.bcits.bsmartwater.service.ConsumerMasterService;
import com.bcits.bsmartwater.service.CounterDetailsService;
import com.bcits.bsmartwater.service.ObservationMasterService;
import com.bcits.bsmartwater.service.OwnerShipChangeService;
import com.bcits.bsmartwater.service.PaymentService;
import com.bcits.bsmartwater.service.ReportService;
import com.bcits.bsmartwater.service.SewageUsedChangeService;
import com.bcits.bsmartwater.service.WardDetailsService;
import com.bcits.bsmartwater.utils.DataSourceRequest;
import com.bcits.bsmartwater.utils.DataSourceResult;
import com.bcits.bsmartwater.utils.DateConverter;


@Controller
public class ReportController
{
	

	@Autowired
	private SewageUsedChangeService sewageUsedChangeService;
	
	@Autowired
	private BillingLedgerService billingLedgerService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	ConsumerMasterService consumerMasterService;
	
	@Autowired
	BillingLedgerDao billingLedgerDao;
	
	@Autowired
	ReportDAO reportDAO;
	
	@Autowired
	ConsumerMasterDao consumerMasterDao;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	WardDetailsService wardDetailsService;
	
	@Autowired
	CounterDetailsService counterDetailsService;
	
	@Autowired
	ObservationMasterService observationMasterService;
	
	@Autowired
	OwnerShipChangeService ownerShipChangeService;
	
	@Autowired
	ConTypeChangeService conTypeChangeService;
	
	@RequestMapping(value="/viewReports",method={RequestMethod.POST,RequestMethod.GET})
	public String viewReports(ModelMap model,HttpServletRequest request)
	{
		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName");
		model.addAttribute("wardList",billingLedgerService.getDistinctWardNo(schemaName));
		model.addAttribute("counterList",billingLedgerService.getDistinctCounter(schemaName));
		model.addAttribute("mainHead", "Reports");
		return "reports/viewReports";	
	}
	@RequestMapping(value="/dailyCashCounterReport",method={RequestMethod.POST,RequestMethod.GET})
	public String dayclosereport(ModelMap model,HttpServletRequest request)
	{
		return "reports/dailyCashCounterReport";	
	}
	@RequestMapping(value="/categoryWiseSalesReport",method={RequestMethod.POST,RequestMethod.GET})
	public String categoryWiseSalesReport(ModelMap model,HttpServletRequest request)
	{
		return "reports/categoryWiseSalesReport";	
	}
	
	@RequestMapping(value="/generateReports",method={RequestMethod.POST,RequestMethod.GET})
	public  String generateReports(ModelMap model,HttpServletRequest request)
	{
		String neplaliFMonth=request.getParameter("neplaliFMonth");
		String engFmonth=request.getParameter("engFmonth");
		String neplaliTMonth=request.getParameter("neplaliTMonth");
		String engTmonth=request.getParameter("engTmonth");
		String counterNoSel=request.getParameter("counterNoSel");
		String mrcodeSel=request.getParameter("mrcodeSel");
		String reportName=request.getParameter("reportName");
		String wardnoForm=request.getParameter("wardnoForm");
		model.put("wardnoForm", wardnoForm);
		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName");
		model.addAttribute("wardList",billingLedgerService.getDistinctWardNo(schemaName));
		model.addAttribute("counterList",billingLedgerService.getDistinctCounter(schemaName));
		List<Object[]> list=billingLedgerService.generateReports(neplaliFMonth,engFmonth,neplaliTMonth,engTmonth,counterNoSel,mrcodeSel,reportName,model,request);
		return "reports/viewReports";	
	}
	//daily cash counter report=======================
	@RequestMapping(value = "/dailycashcounter/dailyCashCounterRead", method = { RequestMethod.GET, RequestMethod.POST })
    public @ResponseBody DataSourceResult dailyCashCounterRead(@RequestBody DataSourceRequest request,HttpServletRequest req) {
			return reportService.dailyCashCounterRead(request);	
    }
	//
	@RequestMapping(value = "/categoryWiseSalesReport/categoryWiseSalesReportReadData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object categoryWiseSalesReportRead(HttpServletRequest request) {
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		String mnthyrnep=request.getParameter("monthyearnep");
		Date fromdate = null;
		Date todate = null;
		try {
			fromdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			todate=new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String date1=new SimpleDateFormat("yyyyMM").format(fromdate);
		String date2=new SimpleDateFormat("yyyyMM").format(todate);
		return reportService.categoryWiseSalesReportReadData(fromdate,todate,mnthyrnep);
	}
	
	@RequestMapping(value="/wardWiseSalesReport",method={RequestMethod.POST,RequestMethod.GET})
	public String wardWiseSalesReport(ModelMap model,HttpServletRequest request)
	{
		return "reports/wardWiseSalesReport";	
	}
	
	@RequestMapping(value = "/newConnectionReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String newConnectionReport(HttpServletRequest request,Model model) {
		return "reports/newConnectionReport";
	}
	
	/*@RequestMapping(value = "/sewageChangedReportDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public  @ResponseBody Object sewageChangedReprots(HttpServletRequest request,Model model) {
		
		
		
		return "reports/sewageChangedReports";
	}*/
	
	@RequestMapping(value = "/sewageChangedReprots", method = { RequestMethod.GET, RequestMethod.POST })
	public String sewageChangedReprotsDetails(HttpServletRequest request,Model model) {
		return "reports/sewageChangedReports";
	}
	
	@RequestMapping(value = "/newConnectionReportDetails")
	public @ResponseBody Object newConnReportDetails(HttpServletRequest request) 
	{
		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName");
	
		String[] sitecode=schemaName.split("_");
		String code=sitecode[1];
		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");
		int category1=Integer.parseInt(request.getParameter("category"));
		String category = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); // yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2] + dateArray1[1] + dateArray1[0]);

		String[] dateArray2 = toDate.split("-"); // yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2] + dateArray2[1] + dateArray2[0]);
		//String frmDate=new SimpleDateFormat("yyyy-mm-dd").parse(new SimpleDateFormat().format(new Date()));
		
		//return reportService.newConnectionReportDetails(sdf.format(frmDt).toString(), sdf.format(toDt).toString(), code, category1);
		String concategory=request.getParameter("concategory");
		return reportService.newConnectionReportDetailsCat(sdf.format(frmDt).toString(), sdf.format(toDt).toString(), code, category1,concategory);

	}
	
	@RequestMapping(value = "/categoryNewConnectionReportDetails")
	public @ResponseBody Object categoryNewConReportDetails(HttpServletRequest request) throws Exception 
	{
		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName");
	
		String[] sitecode=schemaName.split("_");
		String code=sitecode[1];
		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");
		String conType= request.getParameter("conType");
		String pipesize= request.getParameter("pipesize");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String[] dateArray1 = fromDate.split("-"); // yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2] + dateArray1[1] + dateArray1[0]);

		String[] dateArray2 = toDate.split("-"); // yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2] + dateArray2[1] + dateArray2[0]);
		
		//Date toDt = dateConverter.convertBsToAd(dateArray2[2] + dateArray2[1] + dateArray2[0]);
		//String frmDate=new SimpleDateFormat("yyyy-mm-dd").parse(new SimpleDateFormat().format(new Date()));
		String concategory=request.getParameter("concategory");
		
		
		//return reportService.newConnectionReportDetails(sdf.format(frmDt).toString(), sdf.format(toDt).toString(), code, category1);
		return reportService.categoryNewConnectionReportDetailsCat(sdf.format(frmDt).toString(), sdf.format(toDt).toString(), code, conType,concategory,pipesize);

	}
	
	
	@RequestMapping(value="/meterChangedDetailsReport")
	public @ResponseBody Object meterChangedDetailsReport(HttpServletRequest request)
	{
		
		return reportService.meterChangeDetailsReport();
	}
	
	
	
	@RequestMapping(value = "/sewageChangedReportDetails")
	public @ResponseBody Object sewageChangedReportDetails(HttpServletRequest request) 
	{
		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName");
	
		String[] sitecode=schemaName.split("_");
		String code=sitecode[1];
		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");
		int category1=Integer.parseInt(request.getParameter("category"));
		String category = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); // yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2] + dateArray1[1] + dateArray1[0]);

		String[] dateArray2 = toDate.split("-"); // yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2] + dateArray2[1] + dateArray2[0]);
		//String frmDate=new SimpleDateFormat("yyyy-mm-dd").parse(new SimpleDateFormat().format(new Date()));
		
		System.out.println(sdf.format(frmDt)+"--"+sdf.format(toDt)+"--"+code+"--"+category1);
		return sewageUsedChangeService.getSewageApprovalReprots(sdf.format(frmDt).toString(), sdf.format(toDt).toString(), code, category1);
 
	}
	
	
	
	@RequestMapping(value = "/wardWiseSalesReport/wardWiseSalesReportReadData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object wardWiseSalesReportRead(HttpServletRequest request) {
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		/*Date fromdate = null;
		Date todate = null;
		try {
			fromdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			todate=new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		String[] from1=fromDate.split("-");
		String[] to1=toDate.split("-");
		String date1=from1[0]+from1[1];
		String date2=to1[0]+to1[1];
		/*String date1=new SimpleDateFormat("yyyyMM").format(fromdate);
		String date2=new SimpleDateFormat("yyyyMM").format(todate);*/
		return reportService.wardWiseSalesReportReadData(date1,date2);
	}
	
	@RequestMapping(value="/monthlyObservationReport",method={RequestMethod.POST,RequestMethod.GET})
	public String monthlyObservationReport(ModelMap model,HttpServletRequest request)
	{
		try{
			List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
			Map<String, Object> appIdMap1 = null;
			List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
			
			for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
				 String year =(String)(iterator.next());
				 appIdMap1 = new HashMap<String, Object>();
				 appIdMap1.put("year", year);
				 result1.add(appIdMap1);
			}
				model.addAttribute("yearList", result1);
				return "reports/monthlyObservationReport";	
			}catch(Exception e){
				e.printStackTrace();
				return "redirect:/login";
			}
	}
	
	@RequestMapping(value = "/monthlyObservationReport/monthlyObservationReportReadData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object monthlyObservationReportRead(HttpServletRequest request,Model model) {
		String yearnep=request.getParameter("yearnep");
		String month=request.getParameter("month");
		return reportService.monthlyObservationReportReadData(yearnep,month,model);
	}
	
	@RequestMapping(value="/wardWiseMrReaderReport",method={RequestMethod.POST,RequestMethod.GET})
	public String wardWiseMrReaderReport(ModelMap model,HttpServletRequest request)
	{
		try{
			List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
			Map<String, Object> appIdMap1 = null;
			List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
			
			for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
				 String year =(String)(iterator.next());
				 appIdMap1 = new HashMap<String, Object>();
				 appIdMap1.put("year", year);
				 result1.add(appIdMap1);
			}
				model.addAttribute("yearList", result1);
				return "reports/wardWiseMrReaderReport";	
			}catch(Exception e){
				e.printStackTrace();
				return "redirect:/login";
			}
	}
	
	@RequestMapping(value = "/wardWiseMrReaderReport/wardWiseMrReaderReportRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object wardWiseMrReaderReportRead(HttpServletRequest request) {
		String yearnep=request.getParameter("yearnep");
		String month=request.getParameter("month");
		return reportService.wardWiseMrReaderReportRead(yearnep,month);
	}
	
	@RequestMapping(value="/missedBillsReport",method={RequestMethod.POST,RequestMethod.GET})
	public String missedBillsReport(ModelMap model,HttpServletRequest request)
	{
		try{
			List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
			Map<String, Object> appIdMap1 = null;
			List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
			
			for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
				 String year =(String)(iterator.next());
				 appIdMap1 = new HashMap<String, Object>();
				 appIdMap1.put("year", year);
				 result1.add(appIdMap1);
			}
				model.addAttribute("yearList", result1);
				return "reports/missedBills";	
			}catch(Exception e){
				e.printStackTrace();
				return "redirect:/login";
			}
	}
	
	@RequestMapping(value = "/missedBillsReport/missedBillsReportRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object missedBillsReportRead(HttpServletRequest request) {
		String yearnep=request.getParameter("yearnep");
		String month=request.getParameter("month");
		return reportService.missedBillsReportRead(yearnep,month);
	}
	
	@RequestMapping(value="/cashCollectiontth",method={RequestMethod.POST,RequestMethod.GET})
	public String cashCollectiontth(ModelMap model, HttpServletRequest request) {

		return "reports/cashCollectiontth";

	}
	
	@RequestMapping(value = "/cashCollectiontth/cashCollectiontthRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object cashCollectiontthRead(HttpServletRequest request) {
		String fromDate=request.getParameter("from_date");
 		String toDate=request.getParameter("to_date");
 		String category=request.getParameter("category");
 		
 		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
 		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
 		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
 		
 		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
 		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
 		return reportService.cashCollectiontthRead(sdf.format(frmDt),sdf.format(toDt));
		
	}

	@RequestMapping(value = "/missedBillsReport/missedBillsReportRead2", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object missedBillsReportRead2(HttpServletRequest request) {
		String yearnep=request.getParameter("yearnep");
		String month=request.getParameter("month");
		return reportService.missedBillsReportLedger(yearnep,month);
	}
	

	@RequestMapping(value="/monthlySalesSummaryReport",method={RequestMethod.POST,RequestMethod.GET})
	public String monthlySalesSummaryReport(ModelMap model,HttpServletRequest request)
	{
		return "reports/monthlySalesSummaryReport";	
	}
	
	@RequestMapping(value="/newMonthlySalesReport",method={RequestMethod.POST,RequestMethod.GET})
	public String newMonthlySalesReport(ModelMap model,HttpServletRequest request)
	{
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
		
		model.addAttribute("yearList", result1);
		return "reports/newMonthlySalesReport";	
	}
	
	@RequestMapping(value="/categoryMonthlySalesReport",method={RequestMethod.POST,RequestMethod.GET})
	public String categoryMonthlySalesReport(ModelMap model,HttpServletRequest request)
	{
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
		
		model.addAttribute("yearList", result1);
		return "reports/categoryMonthlySalesReport";	
	}
	
	//Ledger Verification
	@RequestMapping(value="/ledgerVerification",method={RequestMethod.POST,RequestMethod.GET})
	public String ledgerVerification(ModelMap model,HttpServletRequest request)
	{
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
		
		model.addAttribute("yearList", result1);
		
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap = null;
		
		
		List<String> wardNos=consumerMasterService.getDistictWardNos();
		
		for (Iterator<String> iterator = wardNos.iterator(); iterator.hasNext();) {
			 String wardNo =(String)(iterator.next());
			 appIdMap = new HashMap<String, Object>();
			 appIdMap.put("wardNo", wardNo);
			 result.add(appIdMap);
		}
		model.addAttribute("wardNoList", result);
		List<Integer> readingDayList=consumerMasterService.getDistictReadingDays();
		List<Map<String, Object>> readingResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> readingMap = null;
		for (Iterator<Integer> iterator = readingDayList.iterator(); iterator.hasNext();) {
			Integer readingDay =(Integer)(iterator.next());
			 readingMap = new HashMap<String, Object>();
			 readingMap.put("readingDay", readingDay);
			 readingResult.add(readingMap);
		}
		model.addAttribute("readingDayList", readingResult);
		
		
		return "reports/ledgerVerification";	
	}
	
	
	@RequestMapping(value = "/ledgerVerification/ledgerVerificationRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object ledgerVerificationRead(HttpServletRequest request) {
		String wardno=request.getParameter("wardno");
		String monthyear=request.getParameter("monthyear");
		String reading_day=request.getParameter("reading_day");
		String catagory=request.getParameter("catagory");
		return reportService.ledgerVerificationRead(wardno,monthyear, reading_day+"@"+catagory);
		//return reportService.ledgerVerificationRead(wardno,monthyear, reading_day);
	}
	
	
	
	@RequestMapping(value = "/newMonthlySales/monthlySalesReadData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object monthlySalesReadData(HttpServletRequest request) {
		
		
		String monthyearnep=request.getParameter("monthyear");
		
		return reportService.monthlySalesReadData(monthyearnep);
	}
	
	@RequestMapping(value = "/newMonthlySalesCategory/monthlySalesReadData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object newMonthlySalesCategory(HttpServletRequest request) {
		
		
		String monthyearnep=request.getParameter("monthyear");
		String con_category=request.getParameter("con_category");
		
		return reportService.newMonthlySalesCategory(monthyearnep,con_category);
	}
	
	
	
	@RequestMapping(value = "/monthlySalesSummaryReport/monthlySalesSummaryReportReadData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object monthlySalesSummaryReportRead(HttpServletRequest request) {
		
		
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
		Date lastBilledDate_N_to_E = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		String fdate= sdf.format(lastBilledDate_N_to_E);
		
		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
		Date lastBilledDate_N_to_E1 = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		String tdate= sdf.format(lastBilledDate_N_to_E1);
		
		
		Date fromdate = null;
		Date todate = null;
		try {
			fromdate = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			todate=new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String date1=new SimpleDateFormat("yyyyMM").format(fromdate);
		String date2=new SimpleDateFormat("yyyyMM").format(todate);
		return reportService.monthlySalesSummaryReportReadData(date1,date2,fdate,tdate);
	}
	
	@RequestMapping(value="/detailedCashCollReport",method={RequestMethod.POST,RequestMethod.GET})
	public String detailedCashCollReport(ModelMap model,HttpServletRequest request)
	{
		return "reports/detailedCashCollReport";	
	}
	
	
	@RequestMapping(value = "/detailedCashCollReport/detailedCashCollReportReadData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object detailedCashCollReportRead(HttpServletRequest request) {
		try{
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		
			if(fromDate!=null && !"null".equalsIgnoreCase(fromDate) && toDate!=null && !"null".equalsIgnoreCase(toDate)){
			
			
			
			return reportService.detailedCashCollReportReadData(fromDate,toDate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value = "/detailedCashCollReport/boarReportDetails", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object boarReportDetails(HttpServletRequest request) {
		try{
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		
			if(fromDate!=null && !"null".equalsIgnoreCase(fromDate) && toDate!=null && !"null".equalsIgnoreCase(toDate)){
			
			
			
			return reportService.boarReportDetailstReadData(fromDate,toDate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	@RequestMapping(value="/detailedMiscCollReport",method={RequestMethod.POST,RequestMethod.GET})
	public String detailedMiscCollReport(ModelMap model,HttpServletRequest request)
	{
		return "reports/detailedMiscCollectionReport";	
	}
	
	@RequestMapping(value = "/detailedMiscCollReport/detailedMiscCollReportReadData", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object detailedMiscCollReportReadData(HttpServletRequest request) {
		try{
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		
			if(fromDate!=null && !"null".equalsIgnoreCase(fromDate) && toDate!=null && !"null".equalsIgnoreCase(toDate)){
			
			
			
			return reportService.detailedMiscCollReportReadData(fromDate,toDate);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@RequestMapping(value="/meterReadingListReport",method={RequestMethod.POST,RequestMethod.GET})
	public String meterReadingListReport(ModelMap model,HttpServletRequest request)
	{
		try{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap = null;
		
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		
		List<String> wardNos=consumerMasterService.getDistictWardNos();
		
		for (Iterator<String> iterator = wardNos.iterator(); iterator.hasNext();) {
			 String wardNo =(String)(iterator.next());
			 appIdMap = new HashMap<String, Object>();
			 appIdMap.put("wardNo", wardNo);
			 result.add(appIdMap);
		}
		model.addAttribute("wardNoList", result);
		
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
			model.addAttribute("yearList", result1);
			return "reports/meterReadingList";	
		}catch(Exception e){
			e.printStackTrace();
			return "redirect:/login";
		}
	}
	
	@RequestMapping(value = "/meterReadingListReport/meterReadingListReportRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object meterReadingListReportRead(HttpServletRequest request) {
		
		try {
		String wardNo=request.getParameter("wardNo");
		String reading_day=request.getParameter("reading_day");
		String yearnep=request.getParameter("yearnep");
		String month=request.getParameter("month");
		String pipet=request.getParameter("pipeSize");
		double pipeSize=Double.parseDouble(pipet);
		
		if(wardNo!=null && !"null".equalsIgnoreCase(wardNo) && reading_day!=null && !"null".equalsIgnoreCase(reading_day)){
			
			
				return reportService.meterReadingListReportRead(wardNo,Integer.parseInt(reading_day),yearnep+""+month,pipeSize);
			
		}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	
	@RequestMapping(value = "/meterReadingListReport/meterReadingListReportRead1", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object meterReadingListReportRead1(HttpServletRequest request) {

		return null;
	}
	
	@RequestMapping(value="/consumerBalanceReport",method={RequestMethod.GET,RequestMethod.POST})
	public String consumerBalanceReport(HttpServletRequest request, Model model)
	{
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		List<String> monthyearneplist = consumerMasterService
				.getDistictMonthYearNep();

		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator
				.hasNext();) {
			String year = (String) (iterator.next());
			appIdMap1 = new HashMap<String, Object>();
			appIdMap1.put("year", year);
			result1.add(appIdMap1);
		}
		model.addAttribute("yearList", result1);
		return "reports/consumerBalanceReport";
	}
	
	@RequestMapping(value="/consumerBalanceReport/consumerBalanceReportRead")
	public @ResponseBody Object consumerBalanceReportRead(HttpServletRequest request)
	{
		String amount=request.getParameter("amount");
		String criteria=request.getParameter("criteria");
		String monthYear=request.getParameter("monthYear");
		String flag=request.getParameter("flag");
		return reportService.consumerBalanceReportRead(amount,criteria,monthYear,flag);
	}
	
	@RequestMapping(value="/multiPayment",method={RequestMethod.GET,RequestMethod.POST})
	public String multiPayment(HttpServletRequest request, Model model)
	{
		return "reports/multiplepayment";
	}
	
	@RequestMapping(value="/multiPayment/multiPaymentRead")
	public @ResponseBody Object multiPaymentRead(HttpServletRequest request)
	{
		String amount=request.getParameter("amount");
		String criteria=request.getParameter("criteria");
		String flag=request.getParameter("flag");
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		String con_type=request.getParameter("con_type");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
		Date lastBilledDate_N_to_E = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		String fdate= sdf.format(lastBilledDate_N_to_E);
		
		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
		Date lastBilledDate_N_to_E1 = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		String tdate= sdf.format(lastBilledDate_N_to_E1);
		
		
		
		return reportService.multiPaymentRead(amount,criteria,fdate,tdate,flag,con_type);
	}
	
	@RequestMapping(value="/custObservation",method={RequestMethod.GET,RequestMethod.POST})
	public String custObservation(HttpServletRequest request, Model model)
	{
		
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		List<String> monthyearneplist = consumerMasterService.getDistictMonthYearNep();

		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator
				.hasNext();) {
			String year = (String) (iterator.next());
			appIdMap1 = new HashMap<String, Object>();
			appIdMap1.put("year", year);
			result1.add(appIdMap1);
		}
		model.addAttribute("yearList", result1);
		
		List<?> observationList=observationMasterService.getAllObservationRecordsBill();

		model.addAttribute("observationList", observationList);
		
		return "reports/customerObservationReport";
	}
	
	
	@RequestMapping(value="/custObservation/custObservationReportRead")
	public @ResponseBody Object custObservationReportRead(HttpServletRequest request)
	{
		String monthYear=request.getParameter("monthYear");
		String mc_status=request.getParameter("mc_status");
		return reportService.custObservationReportRead(monthYear,Integer.parseInt(mc_status));
	}
	
	
	
	
	
	
	@RequestMapping(value="/cashCollectionList",method={RequestMethod.GET,RequestMethod.POST})
	public String cashCollectionList(HttpServletRequest request)
	{
		return "reports/cashCollectionList";
	}
	
	@RequestMapping(value="/cashCollectionList/cashCollectionListReportRead")
	public @ResponseBody Object cashCollectionListReportRead(HttpServletRequest request)
	{
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		
		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		
	
		return reportService.cashCollectionListReportRead(sdf.format(frmDt),sdf.format(toDt));
		/*String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		Date fromdate=null;
		Date todate=null;
		try {
			
			fromdate=new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
			todate=new SimpleDateFormat("yyyy-MM-dd").parse(toDate);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		String date1=new SimpleDateFormat("yyyyMM").format(fromdate);
		String date2=new SimpleDateFormat("yyyyMM").format(todate);
		return reportService.cashCollectionListReportRead(date1,date2);*/
	}
	
	@RequestMapping(value="/cashCollectionList/cashCollectionListReportReadAll")
	public @ResponseBody Object cashCollectionListReportReadAll(HttpServletRequest request)
	{
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		
		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
	
		return reportService.cashCollectionListReportReadAll(sdf.format(frmDt),sdf.format(toDt));
	}
	
	
	
	
	@RequestMapping(value="/consumerListReport",method={RequestMethod.GET,RequestMethod.POST})
	public String consumerListReport(HttpServletRequest request)
	{
		
		return "reports/consumerListReport";
	}
	
	@RequestMapping(value="/consumerListReport/consumerListReportReadData")
	public @ResponseBody Object consumerListReportReadData(HttpServletRequest request)
	{
		
		return reportService.consumerListReportReadData();
	}
	
	
	@RequestMapping(value="/WardCountReport",method={RequestMethod.GET,RequestMethod.POST})
	public String WardCountReport(HttpServletRequest request)
	{
		
		return "reports/wardWiseCount";
	}
	
	@RequestMapping(value="/wardWiseCount/consumerCountReport")
	public @ResponseBody Object wardWiseConsumerCountReport(HttpServletRequest request)
	{
		
		return reportService.wardWiseConsumerCountReport();
	}
	
	
	
	@RequestMapping(value="/holeBlockReport",method={RequestMethod.GET,RequestMethod.POST})
	public String holeBlockReport(HttpServletRequest request)
	{
		return "reports/holeBlockReport";
	}
	
   @RequestMapping(value="/holeBlockReport/holeBlockReportReadData/{con_status}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object holeBlockReportReadData(@PathVariable String con_status,HttpServletRequest request)
	{
		
		return reportService.holeBlockReportReadData(con_status);
	}
   @RequestMapping(value="/dailyCashCollectionReport",method={RequestMethod.GET,RequestMethod.POST})
   public String dailyCashColReport(ModelMap model,HttpServletRequest request)
   {
		model.addAttribute("wardList", wardDetailsService.getAllWardRecords());
		List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
		return "reports/dailyCashCollectionReport";
   }
   
   //Miscellaneous Collection
   
   @RequestMapping(value="/miscellaneousCollectionReport",method={RequestMethod.GET,RequestMethod.POST})
   public String miscellaneousCollectionReport(ModelMap model,HttpServletRequest request)
   {
		List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
		return "reports/miscellaneousCollection";
   }
   
   
   
   @RequestMapping(value="/generatedailyCashCounterCollReport",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object generatedailyCashCounterCollReport(HttpServletRequest request)
	{
		
	    String fromDatenep=request.getParameter("fromDatenep");
	    String toDatenep=request.getParameter("toDatenep");
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] dateArray1 = fromDatenep.split("-"); //yyyy-mm-dd
		Date lastBilledDate_N_to_E = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		String fdate =sdf.format(lastBilledDate_N_to_E);
		
		String[] dateArray2 = toDatenep.split("-"); //yyyy-mm-dd
		Date lastBilledDate_N_to_E1 = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		String tdate = sdf.format(lastBilledDate_N_to_E1);
		String counterno=request.getParameter("counter_no");
		String wardno=request.getParameter("ward_no");
		String pipesize=request.getParameter("pipe_size");
		List<?> values = reportService.generatedailyCashCounterCollReport(fdate,tdate,counterno,wardno,pipesize);
		System.out.println(values);
		return values;
	}
   
   //Generate Miscellaneous Collection Report
   
   @RequestMapping(value="/generatedailyMiscColReport",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object generatedailyMiscColReport(HttpServletRequest request)
	{
		
	    String fromDatenep=request.getParameter("fromDatenep");
	    String toDatenep=request.getParameter("toDatenep");
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String[] dateArray1 = fromDatenep.split("-"); //yyyy-mm-dd
		Date lastBilledDate_N_to_E = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		String fdate =sdf.format(lastBilledDate_N_to_E);
		
		String[] dateArray2 = toDatenep.split("-"); //yyyy-mm-dd
		Date lastBilledDate_N_to_E1 = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		String tdate = sdf.format(lastBilledDate_N_to_E1);
		
          String counterno=request.getParameter("counter_no");
		
		List<?> values = reportService.generatedailyMiscColReport(fdate,tdate,counterno);
		//List<?> values = reportService.generatedailyMiscColReport(fdate,tdate);
		
		return values;
	}
   
   
  @RequestMapping(value="/conTypeReport/conTypeReportReadData/{con_Type}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object conTypeReportReadData(@PathVariable String con_Type,HttpServletRequest request)
	{
		
		return reportService.conTypeReportReadData(con_Type);
	}
  
  @RequestMapping(value="/conTypeReport",method={RequestMethod.GET,RequestMethod.POST})
	public String connectionTypeReport(HttpServletRequest request)
	{
		return "reports/connectionTypeReport";
	}
 
  @RequestMapping(value="/dailyRevenueReport",method={RequestMethod.GET,RequestMethod.POST})
	public String dailyRevenueReport(HttpServletRequest request,ModelMap model)
	{
	    List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
		return "reports/dailyRevenueReport";
	}
  
  @RequestMapping(value="/dailyRevenueReport/dailyRevenueReportRead")
	public @ResponseBody Object dailyRevenueReportRead(HttpServletRequest request)
	{
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		String counter_no=request.getParameter("counter_no");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		
		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		return reportService.dailyRevenueReportRead(sdf.format(frmDt),sdf.format(toDt),counter_no);
	}
  @RequestMapping(value="/revenueReport",method={RequestMethod.GET,RequestMethod.POST})
	public String revenueReport(HttpServletRequest request,ModelMap model)
	{
	  List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
		return "reports/revenueReport";
	}
  
  @RequestMapping(value="/revenueReport/dailyRevenueReportRead")
	public @ResponseBody Object revenueReportRead(HttpServletRequest request)
	{
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		String counter_no=request.getParameter("counter_no");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		
		String monthYearN=dateArray1[0]+dateArray1[1];
		//System.out.println("MonthYear Nepali: "+monthYearN);
		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		return reportService.revenueReportRead(sdf.format(frmDt),sdf.format(toDt),counter_no,monthYearN);
	}
  @RequestMapping(value="/categoryCollectionReport",method={RequestMethod.GET,RequestMethod.POST})
	public String categoryCollectionReport(HttpServletRequest request)
	{
		return "reports/categoryCollectionReport";
	}
  @RequestMapping(value="/categoryCollectionReport/categoryCollectionReportRead")
 	public @ResponseBody Object categoryCollectionReportRead(HttpServletRequest request)
 	{
 		String fromDate=request.getParameter("from_date");
 		String toDate=request.getParameter("to_date");
 		//String category=request.getParameter("category");
 		String category="";
 		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
 		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
 		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
 		
 		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
 		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
 		return reportService.categoryCollectionReportRead(sdf.format(frmDt),sdf.format(toDt),category);

 	}
  
  @RequestMapping(value="/counterRevenueReport",method={RequestMethod.GET,RequestMethod.POST})
	public String categoryRevenueReport(HttpServletRequest request,ModelMap model)
	{
	  
		return "reports/counterRevenueReport";
	}
  @RequestMapping(value="/counterRevenueReport/counterRevenueReportRead")
	public @ResponseBody Object counterRevenueReportRead(HttpServletRequest request)
	{
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		String counter_no=request.getParameter("counter_no");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		String monthYearN=dateArray1[0]+dateArray1[1];
		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		return reportService.counterRevenueReportRead(sdf.format(frmDt),sdf.format(toDt),monthYearN);
	}
  
  @RequestMapping(value="/wardWiseCustomerBillingReport",method={RequestMethod.POST,RequestMethod.GET})
	public String wardCustomerReport(ModelMap model,HttpServletRequest request)
	{
		try{
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap = null;
		
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		
		List<String> wardNos=consumerMasterService.getDistictAllWardNos();
		
		for (Iterator<String> iterator = wardNos.iterator(); iterator.hasNext();) {
			 String wardNo =(String)(iterator.next());
			 appIdMap = new HashMap<String, Object>();
			 appIdMap.put("wardNo", wardNo);
			 result.add(appIdMap);
		}
		model.addAttribute("wardNoList", result);
		
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
			model.addAttribute("yearList", result1);
			return "reports/wardWiseCustomerBilling";	
		}catch(Exception e){
			e.printStackTrace();
			return "redirect:/login";
		}
	}
  @RequestMapping(value = "/wardWiseCustomerBillingReport/wardWiseCustomerBillingReportRead1", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object wardWiseCustomerBillingReportRead1(HttpServletRequest request) {

		return null;
	}
  
  @RequestMapping(value = "/wardWiseCustomerBillingReport/wardWiseCustomerBillingReportRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object wardWiseCustomerBillingReportRead(HttpServletRequest request) {

	  try {
			String wardNo=request.getParameter("wardNo");
			String myn=request.getParameter("myn");
			String category=request.getParameter("category");
			
			
			if(wardNo!=null && !"null".equalsIgnoreCase(wardNo) && myn!=null && !"null".equalsIgnoreCase(category)){
				
				
					return reportService.wardWiseCustomerBillingReportRead(wardNo,myn,category);
				
			}
				
			} catch (Exception e) {
				e.printStackTrace();
				
			}
	  		return null;
	}
  
  
  @RequestMapping(value="/monthlyAdjustmentReport",method={RequestMethod.POST,RequestMethod.GET})
	public String monthlyAdjustmentReport(ModelMap model,HttpServletRequest request)
	{
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
		
		model.addAttribute("yearList", result1);
		return "reports/monthlyAdjustmentReport";	
	}

  // ram boardCorre/adj report**************
  @RequestMapping(value="/boardBalAdjCorrReport",method={RequestMethod.POST,RequestMethod.GET})
	public String boardBalAdjCorrReport(ModelMap model,HttpServletRequest request)
	{
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
		
		model.addAttribute("yearList", result1);
		return "reports/boardBalAdjCorrReport";	
	}
  
  
  
  @RequestMapping(value="/monthlyAdjustmentReportRead",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object monthlyAdjustmentReportRead(HttpServletRequest request)
	{
		String myn=request.getParameter("monthyear");
		return reportService.monthlyAdjustmentReportRead(myn);
	}
  
    @RequestMapping(value="/boardBalAdjCorrReportDetails",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object boardBalAdjCorrReportDetails(HttpServletRequest request)
	{
		String myn=request.getParameter("monthyear");
		String con_category=request.getParameter("con_category");
		System.out.println(myn+"==="+con_category);
		return reportService.boardBalAdjCorrReportDetails(myn,con_category);
	}

	@RequestMapping(value = "/categoryBoardCollectionReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String categoryBoardCollectionReport(HttpServletRequest request) {
		return "reports/categoryBoardCollectionReport";
	}
	
	@RequestMapping(value = "/detailedBoardCollReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String boardReport(HttpServletRequest request) {
		return "reports/boardReport";
	}

	@RequestMapping(value = "/categoryBoardCollectionReport/categoryBoardCollectionReportRead")
	public @ResponseBody Object categoryBoardCollectionReportRead(HttpServletRequest request) {
		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");
		// String category=request.getParameter("category");
		String category = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); // yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2] + dateArray1[1] + dateArray1[0]);

		String[] dateArray2 = toDate.split("-"); // yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2] + dateArray2[1] + dateArray2[0]);
		return reportService.categoryBoardCollectionReportRead(sdf.format(frmDt), sdf.format(toDt));

	}
  
	@RequestMapping(value = "/cancelReceiptReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String cancelReceiptReport(HttpServletRequest request) {
		return "reports/cancelReceiptReport";
	}
	
	
	
	
	@RequestMapping(value = "/cancelReceiptReport/cancelReceiptReportRead")
	public @ResponseBody Object cancelReceiptReportRead(HttpServletRequest request) {
		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");
		// String category=request.getParameter("category");
		String category = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); // yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2] + dateArray1[1] + dateArray1[0]);

		String[] dateArray2 = toDate.split("-"); // yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2] + dateArray2[1] + dateArray2[0]);
		return reportService.cancelReceiptReportRead(sdf.format(frmDt), sdf.format(toDt));

	}
	
	@RequestMapping(value = "/customerWiseAdvCollReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String customerWiseAdvCollReport(HttpServletRequest request,Model model) {
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> appIdMap1 = null;
		
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
		
		model.addAttribute("yearList", result1);
		
		return "reports/customerWiseAdvCollReport";
	}
	@RequestMapping(value = "/customerWiseAdvCollReportDetails")
	public @ResponseBody Object customerWiseAdvCollReportDetails(HttpServletRequest request) 
	{
		String monthyear = request.getParameter("monthyear");
		return reportService.customerWiseAdvCollRepDetails(monthyear);

	}
	
	
	
	@RequestMapping(value = "/boardSalesReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String boardSalesReport(HttpServletRequest request) {
		return "reports/boardSalesReport";
	}
	
	@RequestMapping(value = "/boardSalesReport/boardSalesReportRead")
	public @ResponseBody Object boardSalesReportRead(HttpServletRequest request) {
		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");
		// String category=request.getParameter("category");
		String category = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); // yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2] + dateArray1[1] + dateArray1[0]);

		String[] dateArray2 = toDate.split("-"); // yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2] + dateArray2[1] + dateArray2[0]);
		return reportService.boardSalesReportRead(sdf.format(frmDt), sdf.format(toDt));

	}
	
	@RequestMapping(value="/advanceCollectionReport",method={RequestMethod.POST,RequestMethod.GET})
	public String advanceCollectionReport(ModelMap model,HttpServletRequest request)
	{
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
		
		model.addAttribute("yearList", result1);
		return "reports/advanceCollectionReport";	
	}
	
	@RequestMapping(value = "/advanceCollectionReport/advanceCollectionReportRead", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object advanceCollectionReportRead(HttpServletRequest request) {
		
		
		String monthyearnep=request.getParameter("monthyear");
		
		return reportService.advanceCollectionReportRead(monthyearnep);
	}
	@RequestMapping(value = "/advanceCollectionReport/advanceCollectionReportReadNew", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Object advanceCollectionReportReadNew(HttpServletRequest request) {
		
		
		String monthyearnep=request.getParameter("monthyear");
		
		return reportService.advanceCollectionReportReadNew(monthyearnep);
	}
	
	@RequestMapping(value="/advanceCollectionReportNew",method={RequestMethod.POST,RequestMethod.GET})
	public String advanceCollectionReportNew(ModelMap model,HttpServletRequest request)
	{
		
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
		
		model.addAttribute("yearList", result1);
		return "reports/advanceCollectionReportNew";	
	}
	
	
	@RequestMapping(value = "/ownerShipChangeReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String ownerShipChangeReport(HttpServletRequest request) {
		return "reports/ownerShipChangeReport";
	}
	
	@RequestMapping(value="/ownerShipChangeReport/ownerShipChangeReportRead")
 	public @ResponseBody Object ownerShipChangeReportRead(HttpServletRequest request)
 	{
 		String fromDate=request.getParameter("from_date");
 		String toDate=request.getParameter("to_date");
 		//String category=request.getParameter("category");
 		String category="";
 		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
 		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
 		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
 		
 		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
 		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
 		
 		String status=request.getParameter("status");
 		
 		return ownerShipChangeService.ownerShipChangeReportRead(sdf.format(frmDt),sdf.format(toDt), Integer.parseInt(status));

 	}
	@RequestMapping(value = "/conTypeChngReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String conTypeChngReport(HttpServletRequest request) {
		return "reports/conTypeChngReport";
	}
	@RequestMapping(value="/conTypeChngReport/conTypeChngReportRead")
	public @ResponseBody Object conTypeChngReportRead(HttpServletRequest request)
	{
		String fromDate=request.getParameter("from_date");
		String toDate=request.getParameter("to_date");
		//String category=request.getParameter("category");
		String category="";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		
		String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		
		String status=request.getParameter("status");
		
		return conTypeChangeService.conTypeChngReportRead(sdf.format(frmDt),sdf.format(toDt), Integer.parseInt(status));
		
	}
	
	
	@RequestMapping(value = "/abnormalBillingReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String abnormalBillingReport(ModelMap model, HttpServletRequest request) {
		List<String> monthyearneplist=consumerMasterService.getDistictMonthYearNep();
		List<Map<String, Object>> result1 = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap1 = null;
		for (Iterator<String> iterator = monthyearneplist.iterator(); iterator.hasNext();) {
			 String year =(String)(iterator.next());
			 appIdMap1 = new HashMap<String, Object>();
			 appIdMap1.put("year", year);
			 result1.add(appIdMap1);
		}
		
		model.addAttribute("yearList", result1);
		return "reports/abnormalBilling";
	}
	@RequestMapping(value="/abnormalBillingReport/getAbnormalBillingReport")
	public @ResponseBody Object getAbnormalBillingReport(HttpServletRequest request)
	{
		String monthyearnep=request.getParameter("monthyear");
		String diff=request.getParameter("diff");
		
		return reportService.getAbnormalBillingReport(monthyearnep,diff);
		
	}
	
	@RequestMapping(value="/categoryTypeReport",method={RequestMethod.GET,RequestMethod.POST})
	public String categoryTypeReport(HttpServletRequest request, Model model)
	{
		return "reports/categoryTypeReport";
	}
	
	@RequestMapping(value="/categoryTypeReport/categoryTypeReportReadData/{con_category}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object categoryTypeReportReadData(@PathVariable String con_category,HttpServletRequest request)
	{
		try{
			return reportService.categoryTypeReportReadData(con_category);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping(value="/meterChangeDetailsReport",method={RequestMethod.GET,RequestMethod.POST})
	public String meterChangeDetailsReport(HttpServletRequest request)
	{
		
		return "reports/meterChangeDetailsReport";
	}
	
	@RequestMapping(value = "/categoryNewConnectionReport", method = { RequestMethod.GET, RequestMethod.POST })
	public String categoryNewConnectionReport(HttpServletRequest request,Model model) {
		return "reports/categoryNewConnectionReport";
	}
	
	@RequestMapping(value="/boardRevenueReport",method={RequestMethod.GET,RequestMethod.POST})
	public String boardRevenueReport(HttpServletRequest request,ModelMap model)
	{
	  List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
		return "reports/boardRevenueReport";
	}
	
	  @RequestMapping(value="/revenueReport/dailyBoardRevenueReportRead")
		public @ResponseBody Object BoardRevenueReportRead(HttpServletRequest request)
		{
			String fromDate=request.getParameter("from_date");
			String toDate=request.getParameter("to_date");
			String counter_no=request.getParameter("counter_no");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String[] dateArray1 = fromDate.split("-"); //yyyy-mm-dd
			Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
			
			String monthYearN=dateArray1[0]+dateArray1[1];
			//System.out.println("MonthYear Nepali: "+monthYearN);
			String[] dateArray2 = toDate.split("-"); //yyyy-mm-dd
			Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
			return reportService.dailyBoardRevenueReportRead(sdf.format(frmDt),sdf.format(toDt),counter_no,monthYearN);
		}
	
}


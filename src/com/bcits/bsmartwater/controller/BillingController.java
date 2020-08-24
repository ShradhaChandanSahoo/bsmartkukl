
package com.bcits.bsmartwater.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
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

import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bsmartwater.asyncupdate.AppConfig;
import com.bcits.bsmartwater.asyncupdate.UpdateAsynchronous;
import com.bcits.bsmartwater.dao.BillCorrectionApproveDao;
import com.bcits.bsmartwater.dao.DoubleLedgerValidationDao;
import com.bcits.bsmartwater.dao.MeterChangeApproveDao;
import com.bcits.bsmartwater.dao.MeterDetailsDao;
import com.bcits.bsmartwater.model.AdjustmentVoucher;
import com.bcits.bsmartwater.model.BillApproveEntity;
import com.bcits.bsmartwater.model.BillPenaltyAdjustment;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.CloseMonthEnd;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.DoubleLedgerValidation;
import com.bcits.bsmartwater.model.MeterChangeApproveEntity;
import com.bcits.bsmartwater.model.MeterDetailsEntity;
import com.bcits.bsmartwater.model.ObservationEntity;
import com.bcits.bsmartwater.model.SewageChangeEntity;
import com.bcits.bsmartwater.model.TariffRateMaster;
import com.bcits.bsmartwater.model.WardToMrEntity;
import com.bcits.bsmartwater.service.AdjustmentVoucherService;
import com.bcits.bsmartwater.service.BillCorrectionApproveService;
import com.bcits.bsmartwater.service.BillPenaltyAdjService;
import com.bcits.bsmartwater.service.BillPenaltyCorrectionAdjService;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.service.BoardInstallmentService;
import com.bcits.bsmartwater.service.CloseMonthEndService;
import com.bcits.bsmartwater.service.ConsumerMasterApprovalService;
import com.bcits.bsmartwater.service.ConsumerMasterService;
import com.bcits.bsmartwater.service.DoubleLedgerValidationService;
import com.bcits.bsmartwater.service.FiscalYearAdvanceBalanceService;
import com.bcits.bsmartwater.service.FiscalYearBalService;
import com.bcits.bsmartwater.service.MeterChangeApproveService;
import com.bcits.bsmartwater.service.MeterReaderService;
import com.bcits.bsmartwater.service.ObservationMasterService;
import com.bcits.bsmartwater.service.PaymentService;
import com.bcits.bsmartwater.service.SewageUsedChangeService;
import com.bcits.bsmartwater.service.TariffRateMasterService;
import com.bcits.bsmartwater.service.UserMasterService;
import com.bcits.bsmartwater.service.WardToMrService;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;
import com.bcits.bsmartwater.utils.DateConverter;
import com.bcits.bsmartwater.utils.InvalidBsDayOfMonthException;
import com.bcits.bsmartwater.utils.StaticNepaliMonths;



@Controller
public class BillingController {

	@Autowired
	UserMasterService userMasterService;
	
	@Autowired
    SewageUsedChangeService sewageUsedChangeService;
	
	@Autowired
	ConsumerMasterService consumerMasterService;
	
	@Autowired
	BillCorrectionApproveDao billCorrectionApproveDao;
	
	@Autowired
	BillingLedgerService billingLedgerService;
	
	@Autowired
	BillCorrectionApproveService billCorrectionApproveService;
	
	@Autowired
	TariffRateMasterService tariffRateMasterService;
	
	@Autowired
	ConsumerMasterApprovalService consumerMasterApprovalService;
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	MeterChangeApproveService meterChangeApproveService;
	
	@Autowired
	MeterChangeApproveDao meterChangeApproveDao;
	
	@Autowired
	MeterDetailsDao meterDetailsDao;
	
	@Autowired
	ObservationMasterService observationMasterService;
	
	@Autowired
	MeterReaderService meterReaderService;
	
	@Autowired
	CloseMonthEndService closeMonthEndService;
	
	@Autowired
	BillPenaltyAdjService billPenaltyAdjService;
	
	@Autowired
	BoardInstallmentService boardInstallmentService;
	
	@Autowired
	AdjustmentVoucherService adjustmentVoucherService;
	
	@Autowired
	FiscalYearBalService fiscalYearBalService;
	
	@Autowired
	BillPenaltyCorrectionAdjService billPenaltyCorrectionAdjService;
	
	@Autowired
	private WardToMrService wardToMrService;
	
	@Autowired
	DoubleLedgerValidationService doubleLedgerValidationService;
	
	@Autowired
	DoubleLedgerValidationDao doubleLedgerValidationDao;
	
	@Autowired
	FiscalYearAdvanceBalanceService advanceBalanceService;
	
	
	int createflag=0;
	int createflagBA=0;
	int createflagDD=0;
	int createflagBB=0;
	int createflagc=0;
	int createflagbpa=0;
	int createflagbr=0;
	int createflagav=0;
	DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	DecimalFormat df = new DecimalFormat("####0.00");
	Timestamp today=new Timestamp(new java.util.Date().getTime());
	/*Reading Entry Start*/
	
	@RequestMapping(value="/readingEntry",method={RequestMethod.POST,RequestMethod.GET})
	public String readingEntry(Model model,HttpServletRequest request){	
		model.addAttribute("mainHead", "Billing");
		model.addAttribute("childHead1", "Reading Entry");
		//HttpSession session=request.getSession(false);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap = null;
		
		List<Map<String, Object>> readingResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> readingMap = null;
		
		try{
		
		List<String> wardNos=consumerMasterService.getDistictWardNos();
		List<Integer> readingDayList=consumerMasterService.getDistictReadingDays();

		model.addAttribute("billApproveEntity",new BillApproveEntity());
		
		for (Iterator<String> iterator = wardNos.iterator(); iterator.hasNext();) {
			String wardNo =(String)(iterator.next());
			 appIdMap = new HashMap<String, Object>();
			 appIdMap.put("wardNo", wardNo);
			 result.add(appIdMap);
		}
		
		for (Iterator<Integer> iterator = readingDayList.iterator(); iterator.hasNext();) {
			Integer readingDay =(Integer)(iterator.next());
			if(readingDay<1){
			continue;
			}
			 readingMap = new HashMap<String, Object>();
			 readingMap.put("readingDay", readingDay);
			 readingResult.add(readingMap);
		}
		if(createflagBB==1){
			model.addAttribute("msg","Bill Generated Successfully");
			createflagBB=0;
		}
		
		if(createflagBB==2){
			model.addAttribute("msg","OOPS,Bills are not Generated Successfully Please try again");
			createflagBB=0;
		}
		
		List<?> observationList=observationMasterService.getAllObservationRecordsBill();
		List<?> meterReaderList=meterReaderService.readMrDetails();
		
		String nepaliMonthYr=closeMonthEndService.getLatestMonthYear();
		
		String yearc=nepaliMonthYr.substring(0, 4);
		String monthc=nepaliMonthYr.substring(4, 6);
		
		if("12".equalsIgnoreCase(monthc)){
			nepaliMonthYr=(Integer.parseInt(yearc)+1)+"00";
		}
		
		String latestNepaliMonth=""+(Integer.parseInt(nepaliMonthYr)+1);
		
		if(latestNepaliMonth!=null){
			model.addAttribute("latestNepaliMonth", latestNepaliMonth);
			
			String year=latestNepaliMonth.substring(0, 4);
			String month=latestNepaliMonth.substring(4, 6);
			
			if(StaticNepaliMonths.monthcode1.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep1);
			}else if(StaticNepaliMonths.monthcode2.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep2);
			}else if(StaticNepaliMonths.monthcode3.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep3);
			}else if(StaticNepaliMonths.monthcode4.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep4);
			}else if(StaticNepaliMonths.monthcode5.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep5);
			}else if(StaticNepaliMonths.monthcode6.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep6);
			}else if(StaticNepaliMonths.monthcode7.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep7);
			}else if(StaticNepaliMonths.monthcode8.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep8);
			}else if(StaticNepaliMonths.monthcode9.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep9);
			}else if(StaticNepaliMonths.monthcode10.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep10);
			}else if(StaticNepaliMonths.monthcode11.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep11);
			}else if(StaticNepaliMonths.monthcode12.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep12);
			}

		}

		
		model.addAttribute("wardNoList", result);
		model.addAttribute("observationList", observationList);
		model.addAttribute("readingDayList", readingResult);
		model.addAttribute("meterReaderList", meterReaderList);

		return "billing/readingEntry";	
		}catch(Exception e){
			e.printStackTrace();
			return "redirect:/login";	
		}
	}
	
	@RequestMapping(value="/billing/readingEntryu/{wardNo}/{monthyearnep}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getReadingEntryByAreaNo(@PathVariable String wardNo,@PathVariable String monthyearnep,HttpServletRequest request){
		
		long masterCount=consumerMasterService.countByWardNo(wardNo);
		long ledgerCount=billingLedgerService.ledgerCountByWardNo(wardNo,monthyearnep);
		long billedLedgerCount=billingLedgerService.billedLedgerCountByWardNo(wardNo,monthyearnep);
		long unbilledLedgerCount=billingLedgerService.unbilledLedgerCountByWardNo(wardNo,monthyearnep);
		
		List<Map<String, Object>> result=new ArrayList<>();
		Map<String, Object> data=new HashMap<>();
		data.put("masterCount", masterCount);
		data.put("ledgerCount", ledgerCount);
		data.put("billedLedgerCount", billedLedgerCount);
		data.put("unbilledLedgerCount", unbilledLedgerCount);
		
		result.add(data);
		
		return result;
	}
	@RequestMapping(value="/billing/readingEntryGovt/{con_category1}/{monthyearnep}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> readingEntryGovt(@PathVariable String con_category1,@PathVariable String monthyearnep,HttpServletRequest request){
		
		long masterCount=consumerMasterService.countByConCategory(con_category1);
		long ledgerCount=billingLedgerService.ledgerCountByConCategory(con_category1,monthyearnep);
		long billedLedgerCount=billingLedgerService.billedLedgerCountByConCategory(con_category1,monthyearnep);
		long unbilledLedgerCount=billingLedgerService.unbilledLedgerCountByConCategory(con_category1,monthyearnep);
		
		List<Map<String, Object>> result=new ArrayList<>();
		Map<String, Object> data=new HashMap<>();
		data.put("masterCount", masterCount);
		data.put("ledgerCount", ledgerCount);
		data.put("billedLedgerCount", billedLedgerCount);
		data.put("unbilledLedgerCount", unbilledLedgerCount);
		
		result.add(data);
		
		return result;
	}
	
	@RequestMapping(value="/billing/readingEntry/{wardNo}/{readingday}/{pipesize}/{monthyear}/{concategory}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getReadingEntryByWardRdayPipeSize(@PathVariable String wardNo,@PathVariable int readingday,@PathVariable double pipesize,@PathVariable String monthyear,@PathVariable String concategory,HttpServletRequest request){
		
		String newpipesize=request.getParameter("pipesize");
		//System.out.println(pipesize+"------"+newpipesize);
		Double newPipeSize=Double.parseDouble(newpipesize);
		long masterCount=consumerMasterService.countByWardNoRdayPS(wardNo,readingday,newPipeSize,concategory);
		long ledgerCount=billingLedgerService.ledgerCountByWardNoRdayPS(wardNo,readingday,newPipeSize,monthyear,concategory);
		long billedLedgerCount=billingLedgerService.billedLedgerCountByWardNoRdayPS(wardNo,readingday,newPipeSize,monthyear,concategory);
		long unbilledLedgerCount=billingLedgerService.unbilledLedgerCountByWardNoRdayPS(wardNo,readingday,newPipeSize,monthyear,concategory);
		
		List<Map<String, Object>> result=new ArrayList<>();
		Map<String, Object> data=new HashMap<>();
		data.put("masterCount", masterCount);
		data.put("ledgerCount", ledgerCount);
		data.put("billedLedgerCount", billedLedgerCount);
		data.put("unbilledLedgerCount", unbilledLedgerCount);
		
		result.add(data);
		
		return result;
	}
	@RequestMapping(value="/billing/getMeterReader/{wardNo}/{readingday}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMeterReader(@PathVariable String wardNo,@PathVariable int readingday,HttpServletRequest request){
		WardToMrEntity entity=wardToMrService.getReaderByWardNoReadingDay(wardNo,readingday);
		return entity;
	}
	
	@RequestMapping(value="/getMonthEndCloseBalanceReport",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getLedgerDataByWardMonthEnd(HttpServletRequest request){
		
		String monthyear=request.getParameter("monthyear");
		String from_date=request.getParameter("from_date");
		
		String to_date=request.getParameter("to_date");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
 		String[] dateArray1 = from_date.split("-"); //yyyy-mm-dd
 		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
 		
 		String[] dateArray2 = to_date.split("-"); //yyyy-mm-dd
 		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
		
		List<?> wardDemandSummary=billingLedgerService.getLedgerDataByWardMonthEnd(monthyear,sdf.format(frmDt),sdf.format(toDt));
		
		return wardDemandSummary;
	}
	
	@RequestMapping(value="/checkBillCorrectionPending",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkBillCorrectionPending(HttpServletRequest request){
		String response="";
		try{
		String monthyear=request.getParameter("monthyear");
		
		long pendingcount=billCorrectionApproveService.pendingCount(monthyear);
		
		if(pendingcount>0){
			response="Yes";
		}else{
			response="No";
		}
		
		return response;
		}catch(Exception e){
			e.printStackTrace();
			return "Exception";
		}
	}
	
	@RequestMapping(value="/checkBillPenaltyAdjPending",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkBillPenaltyAdjPending(HttpServletRequest request){
		String response="";
		try{
		String monthyear=request.getParameter("monthyear");
		
		long pendingcount=billPenaltyAdjService.pendingCount(monthyear);
		
		if(pendingcount>0){
			response="Yes";
		}else{
			response="No";
		}
		
		return response;
		}catch(Exception e){
			e.printStackTrace();
			return "Exception";
		}
	}
	
	@RequestMapping(value="/checkBillGenerationPending",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkBillGenerationPending(HttpServletRequest request){
		String response="";
		try{
			String monthyear=request.getParameter("monthyear");
			
			long pendingcount=billingLedgerService.checkBillGenerationPending(monthyear);
			
			if(pendingcount>0){
				response="Yes";
			}else{
				response="No";
			}
			
			return response;
		}catch(Exception e){
			e.printStackTrace();
			return "Exception";
		}
	}
	
	
	@RequestMapping(value="/checkMonthEndExists",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkMonthEndExists(HttpServletRequest request){
		
		String monthyearnep=request.getParameter("monthyear");
		//System.out.println(monthyearnep+"---monthyearnep");
		String response="";
		long monthcheck=fiscalYearBalService.checkMonthYearExists(Integer.parseInt(monthyearnep));
		
		if(monthcheck==0){
			response="Yes";
		}else if(monthcheck==2121){
			response="Exception";
		}else{
			response="No";
		}
		
		return response;
	}
	
	
	
	@RequestMapping(value="/billing/getReadingEntryUnbilled/{wardNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getReadingEntryUnbilled(@PathVariable String wardNo,HttpServletRequest request){
		List<?> readingEntryList=billingLedgerService.getReadingEntryUnbilled(wardNo);
		
		return readingEntryList;
	}
	
	@RequestMapping(value="/billing/getReadingEntryUnbilled/{wardNo}/{reading_day}/{pipe_size}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getReadingEntryUnbilled(@PathVariable String wardNo,@PathVariable int reading_day,@PathVariable double pipe_size,HttpServletRequest request){
		
		String pipesize=request.getParameter("pipesize");
		List<?> readingEntryList=billingLedgerService.getReadingEntryUnbilled(wardNo,reading_day,Double.parseDouble(pipesize));
		
		return readingEntryList;
	}
	
	
	@RequestMapping(value="/insertReadingEntry",method={RequestMethod.POST,RequestMethod.GET})
	public String insertReadingEntry(ModelMap model,HttpServletRequest request){
		
		int readingCount =0;
		try{
			
			
		    HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			
			String readingEntryCount=request.getParameter("readingEntryCount");
			
			 if(readingEntryCount!=null && !readingEntryCount.equalsIgnoreCase("")){
				   readingCount=Integer.parseInt(readingEntryCount);
			 }

			String buttontype=request.getParameter("buttontype");
			
			if("Update".equalsIgnoreCase(buttontype)){
				for (int i = 1; i <=readingCount; i++) {
				    String billid=request.getParameter("billid"+i);
				    
				    String present_reading=request.getParameter("present_reading"+i);
				    String previous_reading=request.getParameter("previous_reading"+i);
				    String mc_status=request.getParameter("mc_status"+i);
				    if(mc_status!=null && !mc_status.equalsIgnoreCase("") && present_reading!=null && !present_reading.equalsIgnoreCase("")){ 
				    BillingLedgerEntity bl=billingLedgerService.findById(Integer.parseInt(billid));
				    bl.setPrevious_reading(Double.parseDouble(previous_reading));
				    bl.setPresent_reading(Double.parseDouble(present_reading));
				    bl.setMc_status(Integer.parseInt(mc_status));
				    billingLedgerService.update(bl);
				    }
				    
				    /*if(mc_status!=null && !mc_status.equalsIgnoreCase("") && present_reading!=null && !present_reading.equalsIgnoreCase("")){
						  
				    	billingLedgerService.updatePresReadAndMCStatusByBillId(Double.parseDouble(present_reading),Integer.parseInt(mc_status),Integer.parseInt(billid));
					}*/
				    
			    }
			}else{
				
				//Billing Logic Bulk
				for (int i = 1; i <=readingCount; i++) {
				    
					int billid=Integer.parseInt(request.getParameter("billid"+i));
					BillingLedgerEntity bl=billingLedgerService.findById(billid);
					
					long billNo=0;
					String sitecode=(String)session.getAttribute("branchcode");
					String maxBillNo=billingLedgerService.getMaxBillNoBysiteCode(sitecode+""+new SimpleDateFormat("MMYY").format(new Date())+"%");
					
					if(maxBillNo==null){
						billNo=1000000*Integer.parseInt(sitecode+""+new SimpleDateFormat("MMYY").format(new Date()));
					}else{
						billNo=(Long.parseLong(maxBillNo)+1);
					}
					
					
				    bl.setBillno(""+billNo);
				    
				    Object[] obj=consumerMasterService.getPipeSizeAndConType(bl.getConnection_no());
				    
				    double minimum_charges=0.0;
					double additionalCharges=0.0;
					double waterCharges=0.0;
					double sewageCharges=0.0;
					double netAmount=0.0;
					double excessCharges=0.0;
					double meterRent=0.0;
					double arrears=0.0;
					double others=0.0;
					double average=0.0;
					double penalty=0.0;
					double rebate=0.0;
					double openingBalance=0.0;
					double rateper1000Ltr=0.0;
					double wcsc=0.0;
					
				    
				if(obj!=null){
					
					TariffRateMaster tariffRateMaster=tariffRateMasterService.getTariffRate((double)obj[0],(String)obj[1]);
					ConsumerMaster consumerMaster=consumerMasterService.findconsumer(bl.getConnection_no());
					
					if(tariffRateMaster!=null){
						
						double unitsMaster=tariffRateMaster.getMin_consumption();
						
						
						/*if(bl.getPrevious_read_date()!=null && bl.getRdng_date()!=null){
							long diff = bl.getRdng_date().getTime() - bl.getPrevious_read_date().getTime();
						    long days= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
						    double billperiod= Double.parseDouble(df.format((days/30.0))); */ 
							
						
							if("Metered".equalsIgnoreCase((String)obj[1])){
								
								ObservationEntity ob=observationMasterService.findById(bl.getMc_status());
								
								//Newly Added
								
								if(ob!=null){
									
									if("Low Water Supply".equalsIgnoreCase(ob.getObservationName()) || "No Water Supply".equalsIgnoreCase(ob.getObservationName()) ||
									   "Door Lock".equalsIgnoreCase(ob.getObservationName()) || "House not found".equalsIgnoreCase(ob.getObservationName())
									   || "Dog Presence".equalsIgnoreCase(ob.getObservationName())){
										
										
										/*if(unitsConsumer>unitsMaster){
											double difference=unitsConsumer-unitsMaster;
											double nooftimes=(double) (difference/1000);
											rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
										}*/
										
										minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
										additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
										waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
										
										if(consumerMaster!=null){
											
											if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
												sewageCharges=0.5*waterCharges;										//SeweRage Charges
											}
											if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
												meterRent=consumerMaster.getMeterRentCharges();	
																	    //Meter Rent Charges
											}
										}
										
										
									}else if("Meter Block".equalsIgnoreCase(ob.getObservationName()) || "Meter Burried".equalsIgnoreCase(ob.getObservationName())){
										
										double avgconsumption=billingLedgerService.getAvgConsumption(bl.getConnection_no());
										double avgcons=avgconsumption/3;
										
										if(avgcons>unitsMaster){
											double difference=avgcons-unitsMaster;
											double nooftimes=(double) (difference/1000);
											rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
										}
										
										minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
										additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
										waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
										
										if(consumerMaster!=null){
											
											if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
												sewageCharges=0.5*waterCharges;										//SeweRage Charges
											}
											if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
												try{
													meterRent=consumerMaster.getMeterRentCharges();	
													}catch(NullPointerException e)//Meter Rent Charges
													{
														meterRent=0;
													}				    //Meter Rent Charges
											}
										}
										
										
									}else if("Meter Sheild Broken".equalsIgnoreCase(ob.getObservationName())
									   || "Meter Damaged".equalsIgnoreCase(ob.getObservationName()) 
									   || "Meter Removed(No Meter)".equalsIgnoreCase(ob.getObservationName())){
										
										TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate((double)obj[0],"Unmetered");
										minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
										waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)
									
										if(consumerMaster!=null){
											
											if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
												sewageCharges=0.5*waterCharges;										//SeweRage Charges
											}
											if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
												try{
													meterRent=consumerMaster.getMeterRentCharges();	
													}catch(NullPointerException e)//Meter Rent Charges
													{
														meterRent=0;
													}					    //Meter Rent Charges
											}					    //Meter Rent Charges
											
										}
										if("Meter Removed(No Meter)".equalsIgnoreCase(ob.getObservationName())){
											others=0;
											bl.setRemarks("Meter Removed from Line(No Meter)");
										}
									}
									else if("Temporary hole block".equalsIgnoreCase(ob.getObservationName())
											   || "Permanent hole block".equalsIgnoreCase(ob.getObservationName()) 
											   || "Service Line disconnected".equalsIgnoreCase(ob.getObservationName()) 
											   || "House Collapse(Earthquake)".equalsIgnoreCase(ob.getObservationName())
											   || "Dual Record".equalsIgnoreCase(ob.getObservationName())
											   || "PID".equalsIgnoreCase(ob.getObservationName())){
												
										
										minimum_charges=0;            //Minimum Charges for Metered //*(billperiod)
										additionalCharges=0;                                  //Additional Charges for Metered //*(billperiod)
										waterCharges=0;//Water Charges for Metered  //*(billperiod)
										sewageCharges=0;
										meterRent=0;	//Meter Rent Charges

								    }
									
									
									
									
									
								}
								
								
							//Old Code
							/*	double units=(bl.getPresent_reading()-bl.getPrevious_reading());
								double unitsConsumer=units*1000;
								if(unitsConsumer>unitsMaster){
									
									double difference=unitsConsumer-unitsMaster;
									double nooftimes=(double) (difference/1000);
									rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
								}
								
								waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered //*(billperiod)
								minimum_charges=(tariffRateMaster.getMinimum_charages())*1;//Minimum Charges for Metered //*(billperiod)
								additionalCharges=(rateper1000Ltr)*1;//Additional Charges for Metered //*(billperiod)
							*/
							}
							
							else{
								waterCharges=tariffRateMaster.getMonthly_charges()*1;//Water Charges for UnMeterd //*(billperiod)
								minimum_charges=tariffRateMaster.getMonthly_charges()*1;//Minimum Charges for UnMeterd //*(billperiod)
								
								if(consumerMaster!=null){
									
									if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
										sewageCharges=0.5*waterCharges;										//SeweRage Charges
									}
									
									/*if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
										meterRent=consumerMaster.getMeterRentCharges();					    //Meter Rent Charges
									}*/
									meterRent=0;					    //Meter Rent Charges

								}
							
							}
							
						//}
						

						
						
						
						
						wcsc=waterCharges+sewageCharges;//WCSC Charges
						
						//Arrears Calculation
						
						/*BillingLedgerEntity billingLedgerEntity=billingLedgerService.getLastMonthRecord(bl.getConnection_no());
							
							if(billingLedgerEntity!=null){
								String schemaName=(String)session.getAttribute("schemaName");
								double sumofPayment=paymentService.getSumOfAmoutBWPcashposAndCashPosDate(billingLedgerEntity.getConnection_no(),billingLedgerEntity.getCashposdate(),schemaName);
								double lastmonthNetAmount=billingLedgerEntity.getNet_amount();
								arrears=lastmonthNetAmount-sumofPayment;
							}else{
								arrears=0;
							}*/
						
						
						if(bl!=null){
							arrears=bl.getArrears();
						}
						
						//NET AMOUNT
						netAmount=wcsc+meterRent+arrears+others;//Net Amount
						
						bl.setMinimum_charges(minimum_charges);
						bl.setAdditional_charges(additionalCharges);
						bl.setWater_charges(waterCharges);	 
						bl.setSw_charges(sewageCharges);	
						bl.setExcess_charges(excessCharges);
						bl.setMtr_rent(meterRent);
						bl.setArrears(arrears);
						bl.setOther(others);
						bl.setAvg_units(average);
						bl.setPenalty(penalty);
						bl.setRebate(rebate);
						bl.setOpen_balance(openingBalance);
						bl.setArrears(arrears);
						bl.setNet_amount(netAmount);
						bl.setUser_id(userName);
						bl.setBill_date(new Date());
						billingLedgerService.update(bl);
					
					
					}
					
							
				
				
			}
			
		}
		
				createflagBB=1;
		
		}
	  }catch(Exception e){
		  createflagBB=2;
		  e.printStackTrace();
		  return "redirect:/readingEntry";	
		}
	  return "redirect:/readingEntry";
	}
	
	
	/*Reading Entry End*/
	
	
	
	
	
	
	//New Reading Entry
	
	
	
	@RequestMapping(value="/insertReadingEntryNew",method={RequestMethod.POST,RequestMethod.GET})
	public String insertReadingEntryNew(ModelMap model,HttpServletRequest request){
		
		int readingCount =0;
		try{
			
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			
			String readingEntryCount=request.getParameter("readingEntryCount");
		    String mr_id=request.getParameter("mr_id");
		    String nepmonthyear=request.getParameter("nepmonthyear");
		    String dayofread=request.getParameter("dayofread");
		    String wardNo=request.getParameter("wardNo");
		    String spipe_size=request.getParameter("spipe_size");
		   
		    
		    int yearnep=Integer.parseInt(nepmonthyear.substring(0, 4));
			String monthnep=nepmonthyear.substring(4, 6);
			
		    String newnepday="";
		    if(String.valueOf(dayofread).length()==1){
		    	newnepday="0"+dayofread;
		    }else{
		    	newnepday=""+dayofread;
		    }

		    String nepalibillDate=yearnep+"-"+monthnep+"-"+newnepday;
			Date billDate_N_to_E = dateConverter.convertBsToAd(newnepday+monthnep+yearnep);
			
			List<BillingLedgerEntity> billingLedgerList= new ArrayList<>();
			
		    
			 if(readingEntryCount!=null && !readingEntryCount.equalsIgnoreCase("")){
				   readingCount=Integer.parseInt(readingEntryCount);
			 }

			String buttontype=request.getParameter("buttontype");
			
			if("Update".equalsIgnoreCase(buttontype)){
				
				session.setAttribute("wardNoR",wardNo );
				session.setAttribute("dayofreadR",dayofread);
				session.setAttribute("nepmonthyearR", nepmonthyear);
				session.setAttribute("spipe_size", spipe_size);
				BsmartWaterLogger.logger.info(readingEntryCount+"---------------readingEntryCount");
				for (int i = 1; i <=readingCount; i++) {
				    BsmartWaterLogger.logger.info("billid"+i);
					String billid=request.getParameter("billid"+i);
				    double present_rdng=0;
				    double prev_rdng=0;
				    
				    String present_reading=request.getParameter("present_reading"+i);
				    String previous_reading=request.getParameter("previous_reading"+i);
				    String mc_status=request.getParameter("mc_status"+i);
				    String minimum_charges=request.getParameter("minimum_charges"+i);
				    String additional_charges=request.getParameter("additional_charges"+i);

				    String arrears=request.getParameter("arrears"+i);
				    String water_charges=request.getParameter("water_charges"+i);
				    String sw_charges=request.getParameter("sw_charges"+i);
				    String mtr_rent=request.getParameter("mtr_rent"+i);
				    String net_amount=request.getParameter("net_amount"+i);
				    String excess_charges=request.getParameter("excess_charges"+i);
				    if(mc_status!=null && !mc_status.equalsIgnoreCase("") && present_reading!=null && !present_reading.equalsIgnoreCase("")){ 
				    
				    BillingLedgerEntity bl=billingLedgerService.findById(Integer.parseInt(billid));
				    
				    present_rdng=Double.parseDouble(present_reading);
				    prev_rdng=Double.parseDouble(previous_reading);
				    
				   
				    bl.setMc_status(Integer.parseInt(mc_status));
				    bl.setMinimum_charges(Double.parseDouble(minimum_charges));
				    bl.setAdditional_charges(Double.parseDouble(additional_charges));
				    bl.setArrears(Double.parseDouble(arrears));
				    bl.setWater_charges(Double.parseDouble(water_charges));
				    bl.setSw_charges(Double.parseDouble(sw_charges));
				    bl.setMtr_rent(Double.parseDouble(mtr_rent));
				    bl.setExcess_charges(Double.parseDouble(excess_charges));
				    bl.setNet_amount(Double.parseDouble(net_amount));
				    bl.setUpdated_by(userName);
				    bl.setMr_id(Integer.parseInt(mr_id));
				    bl.setBill_date_nep(nepalibillDate);
				    bl.setRdng_date(billDate_N_to_E);
				    bl.setBill_date(billDate_N_to_E);
				    bl.setRdng_date_nep(nepalibillDate);
				    bl.setBill_period(1.0);
				    bl.setBillno(bl.getMonthyearnep()+""+bl.getId());
				    
				    if("1".equalsIgnoreCase(""+mc_status) || "3".equalsIgnoreCase(""+mc_status) || "9".equalsIgnoreCase(""+mc_status)){
						int dlcount=bl.getDl_count()==null?0:bl.getDl_count();
						dlcount=dlcount+1;
						bl.setDl_count(dlcount);
						bl.setDl_units(10.00*dlcount);
						bl.setMth_dl_count(1);
						bl.setMth_dl_units(10.00);
						present_rdng=(present_rdng)+(double)10;
					}
				    bl.setPrevious_reading(prev_rdng);
				    bl.setPresent_reading(present_rdng);
				    bl.setConsumption(present_rdng-prev_rdng);
				    billingLedgerList.add(bl);
				    
				    //billingLedgerService.update(bl);
				    
				    }
				    /*if(mc_status!=null && !mc_status.equalsIgnoreCase("") && present_reading!=null && !present_reading.equalsIgnoreCase("")){
				    	billingLedgerService.updatePresReadAndMCStatusByBillId(Double.parseDouble(present_reading),Integer.parseInt(mc_status),Integer.parseInt(billid));
					}*/
			    }
				
				billingLedgerService.custombatchUpdate(billingLedgerList);
				createflagBB=1;
			}
	  }catch(Exception e){
		  createflagBB=2;
		  e.printStackTrace();
		  return "redirect:/readingEntry";	
		}
	  return "redirect:/readingEntry";
	}
	
	
	
	@RequestMapping(value="/insertReadingEntryNew1",method={RequestMethod.POST,RequestMethod.GET})
	public String insertReadingEntryNew1(ModelMap model,HttpServletRequest request){
		
		int readingCount =0;
		try{
			
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			
			String readingEntryCount=request.getParameter("readingEntryCount");
		    String mr_id=request.getParameter("mr_id");
		    String nepmonthyear=request.getParameter("nepmonthyear");
		    String dayofread=request.getParameter("dayofread");
		    String wardNo=request.getParameter("wardNo");
		    String spipe_size=request.getParameter("spipe_size");
		   
		    
		    int yearnep=Integer.parseInt(nepmonthyear.substring(0, 4));
			String monthnep=nepmonthyear.substring(4, 6);
			
		    String newnepday="";
		    if(String.valueOf(dayofread).length()==1){
		    	newnepday="0"+dayofread;
		    }else{
		    	newnepday=""+dayofread;
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
			
		    
			 if(readingEntryCount!=null && !readingEntryCount.equalsIgnoreCase("")){
				   readingCount=Integer.parseInt(readingEntryCount);
			 }

			String buttontype=request.getParameter("buttontype");
			
			if("Update".equalsIgnoreCase(buttontype)){
				
				session.setAttribute("wardNoR",wardNo );
				session.setAttribute("dayofreadR",dayofread);
				session.setAttribute("nepmonthyearR", nepmonthyear);
				session.setAttribute("spipe_size", spipe_size);
				BsmartWaterLogger.logger.info(readingEntryCount+"---------------readingEntryCount");
				for (int i = 1; i <=readingCount; i++) {
					String billid=request.getParameter("billid"+i);
				    double present_rdng=0;
				    double prev_rdng=0;
				    
				    String present_reading=request.getParameter("present_reading"+i);
				    String previous_reading=request.getParameter("previous_reading"+i);
				    String mc_status=request.getParameter("mc_status"+i);
				    BsmartWaterLogger.logger.info("MC STATUS---"+mc_status);
				    String minimum_charges=request.getParameter("minimum_charges"+i);
				    String additional_charges=request.getParameter("additional_charges"+i);

				    String arrears=request.getParameter("arrears"+i);
				    String water_charges=request.getParameter("water_charges"+i);
				    String sw_charges=request.getParameter("sw_charges"+i);
				    String mtr_rent=request.getParameter("mtr_rent"+i);
				    String net_amount=request.getParameter("net_amount"+i);
				    String excess_charges=request.getParameter("excess_charges"+i);
				    if(mc_status!=null && !mc_status.equalsIgnoreCase("") && present_reading!=null && !present_reading.equalsIgnoreCase("")){ 
				    
				    BillingLedgerEntity bl=billingLedgerService.findById(Integer.parseInt(billid));
				    
				    present_rdng=Double.parseDouble(present_reading);
				    prev_rdng=Double.parseDouble(previous_reading);
				    
				    String mcsta[]=mc_status.split("-");
				    bl.setMc_status(Integer.parseInt(mcsta[0]));
				    bl.setMinimum_charges(Double.parseDouble(minimum_charges));
				    bl.setAdditional_charges(Double.parseDouble(additional_charges));
				    bl.setArrears(Double.parseDouble(arrears));
				    bl.setWater_charges(Double.parseDouble(water_charges));
				    bl.setSw_charges(Double.parseDouble(sw_charges));
				    bl.setMtr_rent(Double.parseDouble(mtr_rent));
				    bl.setExcess_charges(Double.parseDouble(excess_charges));
				    bl.setNet_amount(Double.parseDouble(net_amount));
				    bl.setUpdated_by(userName);
				    bl.setMr_id(Integer.parseInt(mr_id));
				    bl.setBill_date_nep(nepalibillDate);
				    bl.setRdng_date(billDate_N_to_E);
				    bl.setBill_date(billDate_N_to_E);
				    bl.setRdng_date_nep(nepalibillDate);
				    bl.setBill_period(1.0);
				    bl.setBillno(bl.getMonthyearnep()+""+bl.getId());
				    
				    if("1".equalsIgnoreCase(""+mcsta[0]) || "3".equalsIgnoreCase(""+mcsta[0]) || "9".equalsIgnoreCase(""+mcsta[0])){
						int dlcount=bl.getDl_count()==null?0:bl.getDl_count();
						dlcount=dlcount+1;
						bl.setDl_count(dlcount);
						bl.setDl_units(10.00*dlcount);
						bl.setMth_dl_count(1);
						bl.setMth_dl_units(10.00);
						present_rdng=(present_rdng)+(double)0;
					}
				    bl.setPrevious_reading(prev_rdng);
				    bl.setPresent_reading(present_rdng);
				    bl.setConsumption(present_rdng-prev_rdng);
				    billingLedgerList.add(bl);
				    
				    //billingLedgerService.update(bl);
				    
				    }
				    /*if(mc_status!=null && !mc_status.equalsIgnoreCase("") && present_reading!=null && !present_reading.equalsIgnoreCase("")){
				    	billingLedgerService.updatePresReadAndMCStatusByBillId(Double.parseDouble(present_reading),Integer.parseInt(mc_status),Integer.parseInt(billid));
					}*/
			    }
				
				billingLedgerService.custombatchUpdate(billingLedgerList);
				createflagBB=1;
			}
	  }catch(Exception e){
		  createflagBB=2;
		  e.printStackTrace();
		  return "redirect:/readingEntry";	
		}
	  return "redirect:/readingEntry";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	//End Reading Entry
	
	
	/*Bill Generation Start*/
	
	/* Consumer Approval */
	
	
	
	
	/* Consumer Approval */
	
	@RequestMapping(value="/billPrint",method={RequestMethod.POST,RequestMethod.GET})
	public String billGeneration(Model model,HttpServletRequest request){	
		
		try{
		model.addAttribute("mainHead", "Billing");
		model.addAttribute("childHead1", "Bill Generation");
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap = null;
		
		List<Map<String, Object>> resultu = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMapu = null;

		
		List<Map<String, Object>> readingResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> readingMap = null;
		
		List<String> wardNos=consumerMasterService.getDistictWardNos();
		for (Iterator<String> iterator = wardNos.iterator(); iterator.hasNext();) {
			String wardNo =(String)(iterator.next());
			 appIdMap = new HashMap<String, Object>();
			 appIdMap.put("wardNo", wardNo);
			 result.add(appIdMap);
		}
		model.addAttribute("wardNoList", result);
		
		
		
		List<String> wardNosu=consumerMasterService.getDistictWardNosUnmetered();
		for (Iterator<String> iterator = wardNosu.iterator(); iterator.hasNext();) {
			String wardNou =(String)(iterator.next());
			 appIdMapu = new HashMap<String, Object>();
			 appIdMapu.put("wardNou", wardNou);
			 resultu.add(appIdMapu);
		}
		model.addAttribute("wardNoListu", resultu);
		
		
		List<Integer> readingDayList=consumerMasterService.getDistictReadingDays();

		model.addAttribute("billApproveEntity",new BillApproveEntity());
		
		for (Iterator<Integer> iterator = readingDayList.iterator(); iterator.hasNext();) {
			Integer readingDay =(Integer)(iterator.next());
			 readingMap = new HashMap<String, Object>();
			 readingMap.put("readingDay", readingDay);
			 readingResult.add(readingMap);
		}
		model.addAttribute("readingDayList", readingResult);
		
		/*String nepaliMonthYr=closeMonthEndService.getLatestMonthYear();
		
		if(nepaliMonthYr!=null){
			
			String latestNepaliMonth=""+(Integer.parseInt(nepaliMonthYr)+1);
			
			model.addAttribute("latestNepaliMonth", latestNepaliMonth);
			
			String year=latestNepaliMonth.substring(0, 4);
			String month=latestNepaliMonth.substring(4, 6);
			
			if(StaticNepaliMonths.monthcode1.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep1);
			}else if(StaticNepaliMonths.monthcode2.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep2);
			}else if(StaticNepaliMonths.monthcode3.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep3);
			}else if(StaticNepaliMonths.monthcode4.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep4);
			}else if(StaticNepaliMonths.monthcode5.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep5);
			}else if(StaticNepaliMonths.monthcode6.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep6);
			}else if(StaticNepaliMonths.monthcode7.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep7);
			}else if(StaticNepaliMonths.monthcode8.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep8);
			}else if(StaticNepaliMonths.monthcode9.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep9);
			}else if(StaticNepaliMonths.monthcode10.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep10);
			}else if(StaticNepaliMonths.monthcode11.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep11);
			}else if(StaticNepaliMonths.monthcode12.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep12);
			}
		}
		
		*/
		
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
		return "billing/billGeneration";	
		}catch(Exception e){
			return "redirect:/login";
		}
	}
	
	
	@RequestMapping(value="/billing/printBill/{wardNo}/{reading_day}/{reading_month}/{concategory}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object generateBillByAreaNo(@PathVariable String wardNo,@PathVariable int reading_day,@PathVariable int reading_month,@PathVariable String concategory,HttpServletRequest request){
		try{
			
			
			
			
			double pipesize=Double.parseDouble(request.getParameter("pipesize"));
			List<?> billedList=billingLedgerService.billedLedgerByWardNo(wardNo,reading_day,reading_month,pipesize,concategory);
		    return billedList;
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	


	
	@RequestMapping(value="/billing/printBillu/{wardNo}/{reading_monthu}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object printBillUnmetered(@PathVariable String wardNo,@PathVariable String reading_monthu,HttpServletRequest request){
		try{
			
			List<?> billedList=billingLedgerService.printBillUnmeteredBilled(wardNo,reading_monthu);
		    return billedList;
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value="/billing/printBillGovt/{con_category1}/{reading_monthg}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object printBillGovt(@PathVariable String con_category1,@PathVariable String reading_monthg,HttpServletRequest request){
		try{
			
			List<?> billedList=billingLedgerService.printBillGovt(con_category1,reading_monthg);
		    return billedList;
		    
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/billing/printBillByConNo/{connection_no}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object printBillByConNo(@PathVariable String connection_no,HttpServletRequest request){
		try{
			
			List<?> billedList=billingLedgerService.billedLedgerByConnectionNoCM(connection_no);
		    return billedList;
		    
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*Bill Generation End*/
	
	
	/*Bill Correction Start*/
	
	@RequestMapping(value="/billCorrection",method={RequestMethod.POST,RequestMethod.GET})
	public String billCorrection(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Billing");
		model.addAttribute("childHead1", "Bill Correction");
		model.addAttribute("billApproveEntity",new BillApproveEntity());
		
		if(createflag==1){
			model.addAttribute("msg","Bill is generated and sent for approval");
			createflag=0;
		}
		
		if(createflag==2){
			model.addAttribute("msg","OOPS,Sorry Something went wrong Please try again...");
			createflag=0;
		}
		
		List<?> observationList=observationMasterService.getAllObservationRecordsBill();
		model.addAttribute("observationList", observationList);
		
		
		return "billing/billCorrection";	
	}
	
	@RequestMapping(value="/blankRecordBill",method={RequestMethod.POST,RequestMethod.GET})
	public String blankRecordBill(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Billing");
		model.addAttribute("childHead1", "Bill Blank Record");
		model.addAttribute("billLedgerEntity",new BillingLedgerEntity());
		
		if(createflagbr==1){
			model.addAttribute("msg","Blank Record Inserted");
			createflagbr=0;
		}
		return "billing/blankRecordBill";	
	}
	
	
	//Save Bill Correction
	@RequestMapping(value = "/createBillApprove", method = {RequestMethod.GET, RequestMethod.POST})
	public String createUserType(@ModelAttribute("billApprove")BillApproveEntity billApprove,BindingResult bingingResult,ModelMap model,HttpServletRequest request){
		
		
		String previous_read_date=request.getParameter("previous_read_date");
		String rdng_date=request.getParameter("rdng_date");
		int billid=Integer.parseInt(request.getParameter("billid"));
		HttpSession session=request.getSession(false);
		String sitecode=(String)session.getAttribute("branchcode");
		String userName=(String) session.getAttribute("userName");
		if(sitecode==null){
			return "redirect:/login";
		}
		
	   
	  try{
		
	  BillingLedgerEntity billingLedgerEntity=billingLedgerService.findById(billid);
		
	  if(billingLedgerEntity!=null){
		
		if(previous_read_date!=null && !previous_read_date.isEmpty()){
			billApprove.setPrevious_read_date(billCorrectionApproveDao.getDate2(previous_read_date));
		}
		if(rdng_date!=null && !rdng_date.isEmpty()){
			billApprove.setRdng_date(billCorrectionApproveDao.getDate2(rdng_date));
		}
		
		if(billingLedgerEntity.getBillno()==null){
			billApprove.setBillno(billingLedgerEntity.getMonthyearnep()+""+billingLedgerEntity.getId());
			billingLedgerEntity.setBillno(billingLedgerEntity.getMonthyearnep()+""+billingLedgerEntity.getId());
		}else{
			billApprove.setBillno(billingLedgerEntity.getBillno());
		}
		
		billApprove.setBill_app_status(0);
		billApprove.setService_charge(0.0);
		billApprove.setBill_date(new Date());
		billApprove.setAdded_by(userName);
		
		
		if("1".equalsIgnoreCase(""+billApprove.getMc_status()) || "3".equalsIgnoreCase(""+billApprove.getMc_status()) || "9".equalsIgnoreCase(""+billApprove.getMc_status())){
			
			
			double mthdlunits=billingLedgerEntity.getMth_dl_units()==null?0:billingLedgerEntity.getMth_dl_units();
			if(mthdlunits>0){
				
				
			}else{
				int dlcount=billingLedgerEntity.getDl_count()==null?0:billingLedgerEntity.getDl_count();
				dlcount=dlcount+1;
				billApprove.setDl_count(dlcount);
				billApprove.setDl_units(10.00*dlcount);
				billApprove.setMth_dl_count(1);
				billApprove.setPresent_reading(billApprove.getPresent_reading()==null?0:(billApprove.getPresent_reading()));
				billApprove.setConsumption(0.0);
			}

			billApprove.setMth_dl_units(10.00);
		}
		
			billCorrectionApproveService.save(billApprove);
			billingLedgerService.update(billingLedgerEntity);
		
			createflag=1;
			return "redirect:/billCorrection";
		}else{
			createflag=2;
			return "redirect:/billCorrection";
		}
		
		
		
	  }catch(Exception e){
		  createflag=2;
		  e.printStackTrace();
		  return "redirect:/billCorrection";
	  }
		
	}
	
	
	@RequestMapping(value = "/createblankRecord", method = {RequestMethod.GET, RequestMethod.POST})
	public String createblankRecord(@ModelAttribute("billLedgerEntity")BillingLedgerEntity billLedgerEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request)
	{/*
		
		HttpSession session=request.getSession(false);
		String sitecode=(String)session.getAttribute("branchcode");
		String userName=(String) session.getAttribute("userName");
		if(sitecode==null){
			return "redirect:/login";
		}
		
		String nepaliMonthYr=closeMonthEndService.getLatestMonthYear();
		String yearc=nepaliMonthYr.substring(0, 4);
		String monthc=nepaliMonthYr.substring(4, 6);
		
		if("12".equalsIgnoreCase(monthc)){
			nepaliMonthYr=(Integer.parseInt(yearc)+1)+"00";
		}
		
		String latestNepaliMonth=""+(Integer.parseInt(nepaliMonthYr)+1);
		
		String[] dateArray1 = billLedgerEntity.getRdng_date_nep().split("-"); //yyyy-mm-dd
		Date lastBilledDate_N_to_E = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
		
		String rdate=new SimpleDateFormat("dd-MM-yyyy").format(lastBilledDate_N_to_E);
		
		String english[]=rdate.split("-");
		int cday=Integer.parseInt(english[0]);
		int cmonth=Integer.parseInt(english[1]);
		int cyear=Integer.parseInt(english[2]);
		
		  String newMonth="";
		    if(String.valueOf(cmonth).length()==1){
		    	newMonth="0"+cmonth;
		    }else{
		    	newMonth=""+cmonth;
		    }
		    
		BsmartWaterLogger.logger.info("NEW MONTH ------"+newMonth);
		billLedgerEntity.setAdded_by(userName);
		billLedgerEntity.setBill_date_nep(billLedgerEntity.getRdng_date_nep());
		billLedgerEntity.setMonthyear(Integer.parseInt(cyear+""+newMonth));
		billLedgerEntity.setRdng_mth(cmonth);
		billLedgerEntity.setMonthyearnep(latestNepaliMonth);
		billLedgerEntity.setSitecode(sitecode);
		billLedgerEntity.setUser_id(userName);
		billLedgerEntity.setCreated_date(new Date());
		
		if(billLedgerEntity.getPipe_size()==0.5){
			billLedgerEntity.setDenoted_by("SA");
		}else{
			billLedgerEntity.setDenoted_by("THA");
		}
		
		billingLedgerService.save(billLedgerEntity);
		
		createflagbr=1;*/
		return "redirect:/blankRecordBill";
		
	}
	
	
	
	//Get Connection and Bill Details
	@RequestMapping(value="/billing/connectionDetails/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object connectionDetails(@PathVariable String connectionNo,HttpServletRequest request){
		
			List<List<?>> result=new ArrayList<>();
			
			List<Map<String, Object>> result3=new ArrayList<>();
			Map<String, Object> data=new HashMap<>();
		
		    List<?> connectionData=consumerMasterService.findByConnectionNo(connectionNo);
			result.add(connectionData);
			
			List<?> billLedgerData=billingLedgerService.findByConnectionNo(connectionNo);
			long connectionNoCount=billCorrectionApproveService.findCountByConNoBillStatus(connectionNo,0);
			
			result.add(billLedgerData);
			data.put("connectionNoCount", connectionNoCount);
			result3.add(data);
			result.add(result3);
		
		
		return result;
	}
	
	@RequestMapping(value="/billing/connectionDetailsBR/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object connectionDetailsBR(@PathVariable String connectionNo,HttpServletRequest request){
		
			List<List<?>> result=new ArrayList<>();
			
			List<Map<String, Object>> result3=new ArrayList<>();
			Map<String, Object> data=new HashMap<>();
		
		    List<?> connectionData=consumerMasterService.findByConnectionNo(connectionNo);
			result.add(connectionData);
			
			String nepaliMonthYr=closeMonthEndService.getLatestMonthYear();
			String yearc=nepaliMonthYr.substring(0, 4);
			String monthc=nepaliMonthYr.substring(4, 6);
			
			if("12".equalsIgnoreCase(monthc)){
				nepaliMonthYr=(Integer.parseInt(yearc)+1)+"00";
			}
			String latestNepaliMonth=""+(Integer.parseInt(nepaliMonthYr)+1);
			
			
			long connectionNoCount=0;
			BillingLedgerEntity b=billingLedgerService.findByConnectionNoByMYN(connectionNo, latestNepaliMonth);
			if(b==null){
				
			}else{
			  connectionNoCount=1;
			}
			data.put("connectionNoCount", connectionNoCount);
			result3.add(data);
			result.add(result3);
		
		
		return result;
	}
	
	@RequestMapping(value = "/billing/viewPaymentHistory/{connectionNo}", method = RequestMethod.GET)
	public @ResponseBody List<?> viewPaymentHistory(@PathVariable String connectionNo,HttpServletRequest req) throws ParseException
	{
		  List<?> result=paymentService.viewPaymentHistory(connectionNo);
		  return result;
	}
	
	@RequestMapping(value = "/billing/viewBillLedgertHistory/{connectionNo}", method = RequestMethod.GET)
	public @ResponseBody List<?> viewBillLedgertHistory(@PathVariable String connectionNo,HttpServletRequest req) throws ParseException
	{
		  List<?> result=paymentService.viewBillLedgertHistory(connectionNo);
		  return result;
	}
	
	@RequestMapping(value = "/billing/viewBoardLedgertHistory/{connectionNo}", method = RequestMethod.GET)
	public @ResponseBody List<?> viewBoardLedgertHistory(@PathVariable String connectionNo,HttpServletRequest req) throws ParseException
	{
		  List<?> result=boardInstallmentService.viewBoardLedgertHistory(connectionNo);
		  return result;
	}
	
	//Counter Payment History
	@RequestMapping(value = "/billing/viewMyPaymentHistory/{counterno}", method = RequestMethod.GET)
	public @ResponseBody List<?> viewMyPaymentHistory(@PathVariable Integer counterno,HttpServletRequest req) throws ParseException
	{
		  
		  List<?> result=paymentService.viewMyPaymentHistory(counterno,new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		  return result;
	}
	
	@RequestMapping(value = "/billing/viewBillLedgertHistoryForReading/{connectionNo}", method = RequestMethod.GET)
	public @ResponseBody List<?> viewBillLedgertHistoryForReading(@PathVariable String connectionNo,HttpServletRequest req) throws ParseException
	{
		 
		
		List<?> result=paymentService.viewBillLedgertHistoryForReading(connectionNo);
		  return result;
	}
	@RequestMapping(value = "/checkSession", method = RequestMethod.GET)
	public @ResponseBody String checkSeasson(HttpServletRequest request) 
	{
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		HttpSession session = request.getSession(false);
		Boolean s=(Boolean) session.getAttribute("authenticated");
		System.out.println("Authentication:  "+s);
	    if (s == null || s==false){    
	    	return "false";
	    } else {
	    	return "true";
	    }
	}
	
	
	
	@RequestMapping(value="/billing/calculateBill/{connectionNo}/{pipesize}/{connectionType}/{unitsConsumer}/{mcstatus}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> calculateBill(@PathVariable String connectionNo,@PathVariable double pipesize,@PathVariable String connectionType,
			@PathVariable double unitsConsumer,@PathVariable int mcstatus,HttpServletRequest request){
		
		double minimum_charges=0.0;
		double additionalCharges=0.0;
		double waterCharges=0.0;
		double sewageCharges=0.0;
		double netAmount=0.0;
		double excessCharges=0.0;
		double meterRent=0.0;
		double arrears=0.0;
		double others=0.0;
		double average=0.0;
		double penalty=0.0;
		double rebate=0.0;
		double openingBalance=0.0;
		double rateper1000Ltr=0.0;
		double wcsc=0.0;
		double dlunits=0.0;
		
		ConsumerMaster consumerMaster=consumerMasterService.findconsumer(connectionNo);
		//System.err.println(consumerMaster.getSewage_used()+consumerMaster.getMeterRentCharges());
		
		try{
		
		List<Map<String, Object>> result=new ArrayList<>();
		TariffRateMaster tariffRateMaster=tariffRateMasterService.getTariffRate(pipesize,connectionType);
		
		
		Map<String, Object> data=new HashMap<>();
		
		if(tariffRateMaster!=null){
			
			double unitsMaster=tariffRateMaster.getMin_consumption();
			unitsConsumer=unitsConsumer*1000;
			ObservationEntity ob=observationMasterService.findById(mcstatus);
			
			if("Metered".equalsIgnoreCase(connectionType)){
		
			
			//Newly Added
			
			if(ob!=null){
					
				if("Reading".equalsIgnoreCase(ob.getObservationName()) || "Self Reading".equalsIgnoreCase(ob.getObservationName())){
						if(unitsConsumer>unitsMaster){
							double difference=unitsConsumer-unitsMaster;
							double nooftimes=(double) (difference/1000);
							rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
						}
						
						minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
						additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
						waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
						
						if(consumerMaster!=null){
							
							if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
								sewageCharges=0.5*waterCharges;										//SeweRage Charges
								//System.out.println(sewageCharges);
							}
							if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
								meterRent=consumerMaster.getMeterRentCharges();		
								//Meter Rent Charges
							}
						}
			      }
				else if("Low Water Supply".equalsIgnoreCase(ob.getObservationName()) || "No Water Supply".equalsIgnoreCase(ob.getObservationName()) ||
				    "House not found".equalsIgnoreCase(ob.getObservationName()) || "No Reading".equalsIgnoreCase(ob.getObservationName())){
					
					
					/*if(unitsConsumer>unitsMaster){
						double difference=unitsConsumer-unitsMaster;
						double nooftimes=(double) (difference/1000);
						rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
					}*/
					
					minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							
								meterRent=consumerMaster.getMeterRentCharges();	
											    //Meter Rent Charges
						}
					}
					
					
				}else if("Door Lock".equalsIgnoreCase(ob.getObservationName()) || "Dog Presence".equalsIgnoreCase(ob.getObservationName()) || 
						"Meter Burried".equalsIgnoreCase(ob.getObservationName())){
							
							
							Object[] obj=billingLedgerService.getDoorLockCount(connectionNo);
							
							if(obj!=null){
								int doorlock=(int)obj[0];
								String billno=(String) obj[1];
								if("".equalsIgnoreCase(billno) || "null".equalsIgnoreCase(billno)){
									doorlock=doorlock+1;
								}
								if(doorlock>3){
									BsmartWaterLogger.logger.info(doorlock+"--doorlock");
									if("0.5".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
										minimum_charges=785;            
										waterCharges=785;
									}
									if("0.75".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
										minimum_charges=4595;            
										waterCharges=4595;
									}
									if("1".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
										minimum_charges=9540;            
										waterCharges=9540;								
									}
									if("1.5".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
										minimum_charges=26228;            
										waterCharges=26228;
									}
									if("2".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
										minimum_charges=54255;            
										waterCharges=54255;
									}
									if("3".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
										minimum_charges=149415;            
										waterCharges=149415;								
									}
									if("4".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
										minimum_charges=306880;            
										waterCharges=306880;
									}
									additionalCharges=0;                                 
								}
								
								else{
									
									minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
									additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
									waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
								}
							}else{
								
								minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
								additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
								waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
							}
							
							if(consumerMaster!=null){
								
								if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
									sewageCharges=0.5*waterCharges;										//SeweRage Charges
								}
								if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
								
										meterRent=consumerMaster.getMeterRentCharges();	
													    //Meter Rent Charges
								}
							}
							dlunits=10;
							
				}else if("Meter Block".equalsIgnoreCase(ob.getObservationName())){
					
					double avgconsumption=billingLedgerService.getAvgConsumption(connectionNo);
					double avgcons=avgconsumption/3;
					
					if(avgcons>unitsMaster){
						double difference=avgcons-unitsMaster;
						double nooftimes=(double) (difference/1000);
						rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
					}
					
					minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							
								meterRent=consumerMaster.getMeterRentCharges();	
											    //Meter Rent Charges
						}
					}
					
					
				}else if("Meter Sheild Broken".equalsIgnoreCase(ob.getObservationName())
				   || "Meter Damaged".equalsIgnoreCase(ob.getObservationName()) 
				   || "Meter Removed(No Meter)".equalsIgnoreCase(ob.getObservationName())){
					
					TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(pipesize,"Unmetered");
					minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
					waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)
				
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							
								meterRent=consumerMaster.getMeterRentCharges();	
											    //Meter Rent Charges
						}					    //Meter Rent Charges
						
					}
					
					
					if("Meter Removed(No Meter)".equalsIgnoreCase(ob.getObservationName())){
						others=0;
						data.put("remarksothers","Meter Removed from Line(No Meter)");
						

					}
					
				}
				else if("Temporary hole block".equalsIgnoreCase(ob.getObservationName())
						   || "Permanent hole block".equalsIgnoreCase(ob.getObservationName()) 
						   || "Service Line disconnected".equalsIgnoreCase(ob.getObservationName()) 
						   || "House Collapse(Earthquake)".equalsIgnoreCase(ob.getObservationName())
						   || "Dual Record".equalsIgnoreCase(ob.getObservationName())
						   || "PID".equalsIgnoreCase(ob.getObservationName())){
							
					
					minimum_charges=0;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=0;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=0;//Water Charges for Metered  //*(billperiod)
					sewageCharges=0;
					meterRent=0;	//Meter Rent Charges
					data.put("disconnectedstage","No bill");

			    }
				
				
				
			}
			
		
			}else{
				
				if("Low Water Supply".equalsIgnoreCase(ob.getObservationName())){
					minimum_charges=100;                //Minimum Charges for UnMeterd //*(billperiod)
					waterCharges=100;                   //Water Charges for UnMeterd //*(billperiod)
				}else if("Temporary hole block".equalsIgnoreCase(ob.getObservationName())){
					minimum_charges=0;                  //Minimum Charges for UnMeterd //*(billperiod)
					waterCharges=0;                     //Water Charges for UnMeterd //*(billperiod)
				}else if("Service Line disconnected".equalsIgnoreCase(ob.getObservationName())){
					minimum_charges=0;                  //Minimum Charges for UnMeterd //*(billperiod)
					waterCharges=0;                     //Water Charges for UnMeterd //*(billperiod)
				}else if("Permanent hole block".equalsIgnoreCase(ob.getObservationName())){
					minimum_charges=0;                  //Minimum Charges for UnMeterd //*(billperiod)
					waterCharges=0;                     //Water Charges for UnMeterd //*(billperiod)
				}else if("Dual Record".equalsIgnoreCase(ob.getObservationName()) || "PID".equalsIgnoreCase(ob.getObservationName())){
					minimum_charges=0;                  //Minimum Charges for UnMeterd //*(billperiod)
					waterCharges=0;                     //Water Charges for UnMeterd //*(billperiod)
				}else{
					minimum_charges=tariffRateMaster.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
					waterCharges=tariffRateMaster.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)
				}
				
				if(consumerMaster!=null){
					
					if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
						sewageCharges=0.5*waterCharges;										//SeweRage Charges
					}
					meterRent=0;					    //Meter Rent Charges
				}
			
			}
			
			    //WCSC Charges
			    wcsc=waterCharges+sewageCharges;	
			
			    //Arrears Calculation
			
				/*BillingLedgerEntity billingLedgerEntity=billingLedgerService.findBillingLedgerBasedOnConnectionNo(connectionNo);
				
				
				if(billingLedgerEntity!=null){
					arrears=billingLedgerEntity.getArrears();
				}*/
			    
			    
			    BillingLedgerEntity billingLedgerEntity=billingLedgerService.findLastMonthRecordByConNo(connectionNo);
			    
			    //System.out.println("Connection No "+billingLedgerEntity.getConnection_no()+"--MonthYearNep "+billingLedgerEntity.getMonthyearnep());
			    double arrcoradj=0;
			    double sundryamt=0;
			    if(billingLedgerEntity!=null){
					   
			    	    if(billingLedgerEntity.getReceipt_date()==null){
							
			    	    	if(billingLedgerEntity.getService_charge()==null){
								
							}else{
								arrcoradj=billingLedgerEntity.getService_charge();
							}
			    	    	
			    	    	if(billingLedgerEntity.getSundry_amount()==null){
								
							}else{
								sundryamt=billingLedgerEntity.getSundry_amount();
							}
						   
						   arrears=billingLedgerEntity.getNet_amount();
						}else{
							if(billingLedgerEntity.getClose_balance()==null){
								if(billingLedgerEntity.getService_charge()==null){
									
								}else{
									arrcoradj=billingLedgerEntity.getService_charge();
								}
								
								if(billingLedgerEntity.getSundry_amount()==null){
									
								}else{
									sundryamt=billingLedgerEntity.getSundry_amount();
								} 
								arrears=billingLedgerEntity.getNet_amount();
							}else{
								arrears=billingLedgerEntity.getClose_balance();
							}
						}
				}else {
					
					double arrears1=billingLedgerService.getArrearsByConNoBM(connectionNo);
					arrears=arrears1;
				}
				
				
				
			
				//NET AMOUNT
				netAmount=wcsc+meterRent+arrears+others+arrcoradj+sundryamt;//Net Amount
			    //System.out.println("Arrears----"+arrears);
				//System.out.println("Net Amount---"+netAmount);

				
			
				data.put("minimum_charges",minimum_charges);
				data.put("additionalCharges",additionalCharges);
				data.put("waterCharges",waterCharges);
				data.put("sewageCharges",sewageCharges);
	
				data.put("excess_charges",excessCharges);
				data.put("meterRent",meterRent);
				data.put("arrears",arrears+arrcoradj+sundryamt);
				data.put("others",others);
				data.put("average",average);
				
				data.put("penalty",penalty);
				data.put("rebate",rebate);
				data.put("openingBalance",openingBalance);
				data.put("netAmount",netAmount);
				data.put("dlunits",dlunits);
			
				if("Metered".equalsIgnoreCase(connectionType)){
					data.put("key1","0 - "+tariffRateMaster.getMin_consumption()+" Minimum Consumption");
					data.put("key2",tariffRateMaster.getMinimum_charages());
					
					if(unitsConsumer>unitsMaster){
						data.put("key3",tariffRateMaster.getMin_consumption()+" - "+unitsConsumer+" for every 1000 ltr Rs. "+tariffRateMaster.getRate_per_1000_ltr());
						data.put("key4",rateper1000Ltr);
					}
				}
				else{
					data.put("key1","0-"+tariffRateMaster.getMin_consumption());
					data.put("key2",tariffRateMaster.getMonthly_charges());
				}
				
			
				result.add(data);
			}
		
		
			return result;
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		
	}

		
	
	/*Month End Process Start*/
	
	@RequestMapping(value="/monthEndProcess",method={RequestMethod.POST,RequestMethod.GET})
	public String monthEndProcess(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "configurations");
		model.addAttribute("childHead1", "Month End Process");
		
		//String latestNepaliMonth=billingLedgerService.getLatestNepaliMonthYear();
		
		String nepaliMonthYr=closeMonthEndService.getLatestMonthYear();
		String yearc=nepaliMonthYr.substring(0, 4);
		String monthc=nepaliMonthYr.substring(4, 6);
		
		if("12".equalsIgnoreCase(monthc)){
			nepaliMonthYr=(Integer.parseInt(yearc)+1)+"00";
		}
		String latestNepaliMonth=""+(Integer.parseInt(nepaliMonthYr)+1);
			
		if(latestNepaliMonth!=null){
			model.addAttribute("latestNepaliMonth", latestNepaliMonth);
			
			String year=latestNepaliMonth.substring(0, 4);
			String month=latestNepaliMonth.substring(4, 6);
			
			if(StaticNepaliMonths.monthcode1.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep1);
			}else if(StaticNepaliMonths.monthcode2.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep2);
			}else if(StaticNepaliMonths.monthcode3.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep3);
			}else if(StaticNepaliMonths.monthcode4.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep4);
			}else if(StaticNepaliMonths.monthcode5.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep5);
			}else if(StaticNepaliMonths.monthcode6.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep6);
			}else if(StaticNepaliMonths.monthcode7.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep7);
			}else if(StaticNepaliMonths.monthcode8.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep8);
			}else if(StaticNepaliMonths.monthcode9.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep9);
			}else if(StaticNepaliMonths.monthcode10.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep10);
			}else if(StaticNepaliMonths.monthcode11.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep11);
			}else if(StaticNepaliMonths.monthcode12.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep12);
			}

		}
		
		if(createflagc==1){
			model.addAttribute("msg", "Month End Process Done Successfully");
			createflagc=0;
		}
		if(createflagc==2){
			model.addAttribute("msg", "OOPS,Something went wrong Please try again");
			createflagc=0;
		}
		if(createflagc==3){
			model.addAttribute("msg", "Month End Process already done for Selected Month Year");
			createflagc=0;
		}
		
		return "billing/monthEndProcess";	
	}
	
	@RequestMapping(value="/billing/updateMonthEndProcess",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String updateMonthEndProcess(HttpServletRequest request){
		try{
		
		String monthyear=request.getParameter("monthyear");
		
		
		
		if(monthyear!=null && !monthyear.equalsIgnoreCase("") ){


			String [] stringParts = monthyear.split("-");
			String engMY=stringParts[0];
			String nepMY=stringParts[1];
			
			int year=Integer.parseInt(engMY.substring(0, 4));
			int month=Integer.parseInt(engMY.substring(4, 6));
			
			if(month==12){
				month=01;
				year=year+1;
			}else{
				month=month+1;
			}
			
			long monthEndValid=billingLedgerService.monthEndValid(Integer.parseInt(year+""+month));
			
			if(monthEndValid>0){
				return "Month End Process already done for the Selected Month Year";
			}else{
				HttpSession session=request.getSession(false);
				String userName=(String) session.getAttribute("userName");
				billingLedgerService.updateMonthEndProcess(engMY,nepMY,userName);
				return "Month End Process done Successfully";
			}
		}else{
			    return "Please Select Month Year";
		}
		
		}catch(Exception e){
			
			return "OOPS,Month End Process is not happened Please try again";
		}
		
	}
	
	
	@RequestMapping(value="/billing/generateLedgerByWardRdngPipeSize",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String generateLedgerByWardRdngPipeSize(HttpServletRequest request){
		try{
		HttpSession session=request.getSession(false);	
		String wardNo=request.getParameter("wardNo");
		String reading_day=request.getParameter("reading_day");
		String pipe_size=request.getParameter("pipe_size");
		String nepmonthyear=request.getParameter("nepmonthyear");
		String sitecode=(String)session.getAttribute("branchcode");
		
		if(wardNo!=null && !wardNo.equalsIgnoreCase("") || reading_day!=null && !reading_day.equalsIgnoreCase("")
				|| pipe_size!=null && !pipe_size.equalsIgnoreCase("") || nepmonthyear!=null && !nepmonthyear.equalsIgnoreCase("")){

				
				String userName=(String) session.getAttribute("userName");
				long checkBillsExist=billingLedgerService.getCheckBillsExist(wardNo,Integer.parseInt(reading_day),Double.parseDouble(pipe_size),nepmonthyear);
				//System.out.println(checkBillsExist+"====checkBillsExist");
				
				/*if(checkBillsExist==1010){
					return "OOPS,Ledger Entry is not happened Please try again";
				}else if(checkBillsExist==0){
					return "Ledger is already inserted for the Selected Ward No :"+wardNo+" and Reading Day : "+reading_day;
				}else{*/
					
					DoubleLedgerValidation doubleLedgerValidation=doubleLedgerValidationService.getRecordByWardAndRdngDay(wardNo,Integer.parseInt(reading_day));
					
					if(doubleLedgerValidation==null) {
						DoubleLedgerValidation d=new DoubleLedgerValidation();
						d.setWard_no(wardNo);
						d.setReading_day(Integer.parseInt(reading_day));
						d.setFlag(1);
						try {
							doubleLedgerValidationService.save(d);
						}catch (Exception e) {
						}
						
						billingLedgerService.generateLedgerByWardRdngPipeSize(wardNo,Integer.parseInt(reading_day),Double.parseDouble(pipe_size),userName,nepmonthyear,sitecode);
						DoubleLedgerValidation doubleLedgerValidation1=doubleLedgerValidationService.getRecordByWardAndRdngDay(wardNo,Integer.parseInt(reading_day));
						
						if(doubleLedgerValidation1!=null) {
							doubleLedgerValidation1.setFlag(0);
							try {
								doubleLedgerValidationService.update(doubleLedgerValidation1);
							}catch (Exception e) {
							}
						}
					} else {
						if((doubleLedgerValidation.getFlag()==null?0:doubleLedgerValidation.getFlag())==1) {
							return "Ledger generation for Ward No: "+wardNo+",and Reading Day: "+reading_day+" is in progress, please wait!!";
						} else {
							
							doubleLedgerValidation.setFlag(1); 
							try {
								doubleLedgerValidationService.update(doubleLedgerValidation);
							}catch (Exception e) {
							}
							billingLedgerService.generateLedgerByWardRdngPipeSize(wardNo,Integer.parseInt(reading_day),Double.parseDouble(pipe_size),userName,nepmonthyear,sitecode);
							
							DoubleLedgerValidation doubleLedgerValidation1=doubleLedgerValidationService.getRecordByWardAndRdngDay(wardNo,Integer.parseInt(reading_day));
							if(doubleLedgerValidation1!=null) {
								doubleLedgerValidation1.setFlag(0);
								try {
									doubleLedgerValidationService.update(doubleLedgerValidation1);
								}catch (Exception e) {
								}
							}
							
						}
						
						
					}
					return "Ledger generated Successfully";
				}
				
			
		/*}else{
			    
			if(wardNo!=null && !wardNo.equalsIgnoreCase("")){
				return "Please Select Ward Year";
			}else if(reading_day!=null && !reading_day.equalsIgnoreCase("")){
				return "Please Select Reading Day";
			}else if(pipe_size!=null && !pipe_size.equalsIgnoreCase("")){
				return "Please Select Pipe Size";
			}else if(nepmonthyear!=null && !nepmonthyear.equalsIgnoreCase("")){
				return "Please Select Month Year";
			}
			return null;
		}*/
		
		}catch(Exception e){
			
			return "OOPS,Ledger Entry is not happened Please try again";
		}
		return null;
		
	}
	
	
	@RequestMapping(value="/billing/updateBulkBillingUnmetered",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String updateBulkBillingUnmetered(HttpServletRequest request){
		try{
		
		String monthyearnep=request.getParameter("monthyear");
		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName");
		String sitecode=(String)session.getAttribute("branchcode");
		String userName=(String) session.getAttribute("userName");
		if(monthyearnep!=null && !monthyearnep.equalsIgnoreCase("") ){

			long monthEndValid=billingLedgerService.checkEntriesExist(monthyearnep);
			
			if(monthEndValid==0){
				return "Billing already done for Selected Month Year : "+monthyearnep;
			}else if(monthEndValid==2121){
				return "OOPS,Something went wrong Please try again ... ";
			}else{
				
				DoubleLedgerValidation doubleLedgerValidation=doubleLedgerValidationService.getRecordByWardAndRdngDay("UNMETERED",0);
				if(doubleLedgerValidation==null) {
					DoubleLedgerValidation d=new DoubleLedgerValidation();
					d.setWard_no("UNMETERED");
					d.setReading_day(0);
					d.setFlag(1);
					try {
						doubleLedgerValidationService.save(d);
					}catch (Exception e) {
					 e.printStackTrace();
					}
					
				String s=billingLedgerService.updateBulkBillingUnmetered(userName,monthyearnep,schemaName,sitecode,doubleLedgerValidationDao);
				return s+" for Month Year :"+monthyearnep;
				} else {
					if((doubleLedgerValidation.getFlag()==null?0:doubleLedgerValidation.getFlag())==1) {
						return "Unmetered Bill generation is already in progress, please wait!!";
					} else {
						
						doubleLedgerValidation.setFlag(1);
						try {
							doubleLedgerValidationService.update(doubleLedgerValidation);
						}catch (Exception e) {
						}
						String s=billingLedgerService.updateBulkBillingUnmetered(userName,monthyearnep,schemaName,sitecode,doubleLedgerValidationDao);
						return s+" for Month Year : "+monthyearnep;
					}
				}
			}
		}else{
			    return "Please Select Month Year";
		}
		
		}catch(Exception e){
			
			return "OOPS,Something went wrong Please try again";
		}
		
	}
	
	
	/*Month End Process End*/
	
	
	/*Update Due date Start*/
	
	@RequestMapping(value="/dueDateUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public String dueDateUpdate(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Billing");
		model.addAttribute("childHead1", "Due Date Update");
		
		int maxMonthYear=billingLedgerService.getMaxMonthYear();
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
		model.addAttribute("maxMonthYear", maxMonthYear);
		
		if(createflagDD==1){
			model.addAttribute("msg","Due Date updated Successfully");
			createflagDD=0;
		}
		return "billing/dueDateUpdate";	
	}
	
	
	
	@RequestMapping(value="/billing/dueDateUpdate",method={RequestMethod.POST,RequestMethod.GET})
	public  String dueDateUpdateInsert(HttpServletRequest request){
		
		try{
		
			String monthyear=request.getParameter("monthyear");
			String wardNo=request.getParameter("wardNo");
			
			//String rdng_date=request.getParameter("rdng_date");
			String rdng_date_nep=request.getParameter("rdng_date_nep");
							
			String[] dateArray1 = rdng_date_nep.split("-"); //yyyy-mm-dd
			Date lastBilledDate_N_to_E = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
			
			//String due_date=request.getParameter("due_date");
			String due_date_nep=request.getParameter("due_date_nep");
			
			String[] dateArray2 = due_date_nep.split("-"); //yyyy-mm-dd
			Date dueDateDate_N_to_E = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
			
			
			if(monthyear!=null && !monthyear.equalsIgnoreCase("")){
				billingLedgerService.updateRdngDateAndDueDate(Integer.parseInt(monthyear),wardNo, DateFormatUtils.format(lastBilledDate_N_to_E, "MM/dd/yyyy HH:mm:SS"),rdng_date_nep,DateFormatUtils.format(dueDateDate_N_to_E, "MM/dd/yyyy HH:mm:SS"),due_date_nep);
			}
			createflagDD=1;
			
		
		}catch(Exception e){
			
		}
		return "redirect:/dueDateUpdate";
	}
	
	
	
	@RequestMapping(value="/billing/onChangeNepaliDate",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String onChangeNepaliDate(HttpServletRequest request){
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			String rdng_date_nep=request.getParameter("date_nep");
			String[] dateArray1 = rdng_date_nep.split("-"); //yyyy-mm-dd
			Date lastBilledDate_N_to_E = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
			return sdf.format(lastBilledDate_N_to_E);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/billing/getBillPeriod",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String getBillPeriod(HttpServletRequest request){
		
		try{
			
			String previous_read_date=request.getParameter("previous_read_date");
			String present_read_date=request.getParameter("present_read_date");
			DateTime dt1 = new DateTime(formatter.parse(previous_read_date));
			DateTime dt2 = new DateTime(formatter.parse(present_read_date));
			
			Days diffInDays = Days.daysBetween(dt1, dt2);
			
			return df.format(diffInDays.getDays()/30.0);
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*Update Due date End*/
	
	
	/*Bill Approval Start*/
	
	@RequestMapping(value="/billApproval",method={RequestMethod.POST,RequestMethod.GET})
	public String billApproval(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Approval");
		model.addAttribute("childHead1", "Bill Approval");
		List<?> pendingBillsApproval=billCorrectionApproveService.getBillPendingApproval();
		model.addAttribute("pendingBillsApproval", pendingBillsApproval);
		
		if(createflagBA==1){
			model.addAttribute("msg","Bill Approved Successfully");
			createflagBA=0;
		}
		
		if(createflagBA==2){
			model.addAttribute("msg","Bill is Rejected");
			createflagBA=0;
		}
		if(createflagBA==3){
			model.addAttribute("msg","OOPS,Something went wrong Please try again..");
			createflagBA=0;
		}
		if(createflagBA==4){
			model.addAttribute("msg","Payment is already done. Can't Approve, Bill Rejected Automatically..");
			createflagBA=0;
		}
		if(createflagBA==5){
			model.addAttribute("msg","Bill Month already closed. Can't Approve, Bill Rejected Automatically..");
			createflagBA=0;
		}
		
		
		
		return "approval/billApproval";	
	}
	
	
	@RequestMapping(value = "/billApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String approveBill(HttpServletRequest request){
		
		
		try{
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		String docNum[]=new String[1];
		
		String billIds=request.getParameter("billId");
		int billStatus=Integer.parseInt(request.getParameter("billStatus"));
		if(billIds.contains(","))
		  {
			docNum=billIds.split(",");
		  }
		else
		  {
			docNum[0]=billIds;
			
		  }
		String maxMYN=closeMonthEndService.getLatestMonthYear();
		int year=Integer.parseInt(maxMYN.substring(0, 4));
		int month=Integer.parseInt(maxMYN.substring(4, 6));
	
		String months="";
		if(month==12){
			months="01";
			year=year+1;
		}else{
			month=month+1;
			months=month+"";
			//System.out.println(months.length());
			if(months.length()==1){
				months="0"+month;
		 	}
		}
		int maxMonthYear=Integer.parseInt(year+""+months);
		//System.out.println("monthyearfromClosemonthend===="+maxMonthYear);
   	  for(int i=0;i<docNum.length;i++)
   		{
   		
	   		BillApproveEntity billApproveEntity=billCorrectionApproveService.findById(Integer.parseInt(docNum[i]));
			
	   		BillingLedgerEntity billingLedgerEntity=billingLedgerService.findRecordByConNoMYN(billApproveEntity.getConnection_no(),Integer.parseInt(billApproveEntity.getMonthyearnep()));
	   		
	   		//BillingLedgerEntity billingLedgerEntity=billingLedgerService.getBillByConNoAndMY(billApproveEntity.getConnection_no(),billApproveEntity.getMonthyear());
		   	if(billingLedgerEntity!=null ){
		   		
		   		if(billStatus==1){
		   			//System.out.println(" monthyear from entity==="+Integer.parseInt(billApproveEntity.getMonthyearnep()));
		   			if(maxMonthYear==Integer.parseInt(billApproveEntity.getMonthyearnep())){
			   			if(billingLedgerEntity.getReceipt_no()==null && billingLedgerEntity.getLast_paid_amount()==null){
	
	
			   				billingLedgerEntity.setBill_period(1.0);
			   				billingLedgerEntity.setMinimum_charges(billApproveEntity.getMinimum_charges());
			   				billingLedgerEntity.setPrevious_reading(billApproveEntity.getPrevious_reading());
			   				billingLedgerEntity.setPresent_reading(billApproveEntity.getPresent_reading());
			   				billingLedgerEntity.setConsumption(billApproveEntity.getConsumption());
			   				billingLedgerEntity.setArrears(billApproveEntity.getArrears());
			   				billingLedgerEntity.setAvg_units(billApproveEntity.getAvg_units());
			   				billingLedgerEntity.setPenalty(billApproveEntity.getPenalty());
			   				billingLedgerEntity.setOther(billApproveEntity.getOther());
			   				billingLedgerEntity.setNet_amount(billApproveEntity.getNet_amount());
			   				billingLedgerEntity.setWater_charges(billApproveEntity.getWater_charges());
			   				billingLedgerEntity.setSw_charges(billApproveEntity.getSw_charges());
			   				billingLedgerEntity.setRemarks(billApproveEntity.getRemarks());
			   				billingLedgerEntity.setBill_date(billApproveEntity.getBill_date());
			   				billingLedgerEntity.setRdng_date_nep(billApproveEntity.getRdng_date_nep());
			   				if(billApproveEntity.getRdng_date()!=null){
			   					billingLedgerEntity.setRdng_date(billApproveEntity.getRdng_date());
			   				}
			   				billingLedgerEntity.setAdditional_charges(billApproveEntity.getAdditional_charges());
			   				billingLedgerEntity.setExcess_charges(billApproveEntity.getExcess_charges());
			   				billingLedgerEntity.setMtr_rent(billApproveEntity.getMtr_rent());
			   				billingLedgerEntity.setOpen_balance(billApproveEntity.getOpen_balance());
			   				billingLedgerEntity.setMc_status(billApproveEntity.getMc_status());
			   				billingLedgerEntity.setDue_date(billApproveEntity.getDue_date());
			   				billingLedgerEntity.setDue_date_nep(billApproveEntity.getDue_date_nep());
			   				billingLedgerEntity.setExcess_charges(billApproveEntity.getExcess_charges());
			   				billingLedgerEntity.setDl_count(billApproveEntity.getDl_count());
			   				billingLedgerEntity.setDl_units(billApproveEntity.getDl_units());
			   				billingLedgerEntity.setMth_dl_count(billApproveEntity.getMth_dl_count());
			   				billingLedgerEntity.setMth_dl_units(billApproveEntity.getMth_dl_units());
	
			   				billingLedgerService.update(billingLedgerEntity);
	
			   				billApproveEntity.setUpdated_by(userName);
			   				billApproveEntity.setBill_app_status(billStatus);
			   				billApproveEntity.setBank_due_date(new Date());
			   				billCorrectionApproveService.update(billApproveEntity);
			   				
			   				createflagBA=1;
			   				
	
			   			}else{
			   				billApproveEntity.setUpdated_by(userName);
							billApproveEntity.setBill_app_status(2);
							billApproveEntity.setBank_due_date(new Date());
							billCorrectionApproveService.update(billApproveEntity);
							createflagBA=4;
		   			}
		   			
		   			}else{
		   				billApproveEntity.setUpdated_by(userName);
						billApproveEntity.setBill_app_status(2);
						billApproveEntity.setBank_due_date(new Date());
						billCorrectionApproveService.update(billApproveEntity);
						createflagBA=5;
		   			}
		   		}else{
		   			billApproveEntity.setUpdated_by(userName);
					billApproveEntity.setBill_app_status(2);
					billApproveEntity.setBank_due_date(new Date());
					billCorrectionApproveService.update(billApproveEntity);
					createflagBA=2;
		   		}
		   	}
			/*if(billStatus==1){
				createflagBA=1;
			}else{
				createflagBA=2;
			}*/
   	   }
		
		}catch(Exception e){
			createflagBA=3;
			e.printStackTrace();
			return "redirect:/billApproval";
		}
		
		return "redirect:/billApproval";
		
	}
	
	
	//Get Connection and Bill Details
		@RequestMapping(value="/billing/approveBillDetails/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody Object approveBillDetails(@PathVariable String connectionNo,HttpServletRequest request){
			
			List<List<?>> result=new ArrayList<>();
			
			List<?> connectionData=consumerMasterService.findByConnectionNo(connectionNo);
			if(connectionData.isEmpty()){
				
			}else{
				result.add(connectionData);
				List<?> billLedgerData=billCorrectionApproveService.findByConnectionNo(connectionNo);
				result.add(billLedgerData);
			}
			
			return result;
		}
		
		
	
	/*Bill Approval End*/
	
	/*Tariff Management Start*/
	
	@RequestMapping(value="/tariffManagement",method={RequestMethod.POST,RequestMethod.GET})
	public String tariffManagement(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Billing");
		model.addAttribute("childHead1", "Tariff Management");
		return "billing/tariffManagement";	
	}
	
	/*Tariff Management End*/
	
	/*Customer Approval Start*/
	
	@RequestMapping(value="/consumerApproval",method={RequestMethod.POST,RequestMethod.GET})
	public String consumerApproval(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Approval");
		model.addAttribute("childHead1", "Customer Approval");
		
		List<?> masterListApproval=consumerMasterApprovalService.getPendingConsumersToApprove();
		model.addAttribute("masterListApproval", masterListApproval);
		
		return "approval/consumerApproval";	
	}
	
	/*Customer Approval End*/
	@RequestMapping(value="/sewageApproval",method={RequestMethod.POST,RequestMethod.GET})
	public String sewageApproval(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Approval");
		model.addAttribute("childHead1", "Customer Approval");
		 List<SewageChangeEntity> list=sewageUsedChangeService.getAllSewChanEntityData();
	       model.addAttribute("list",list);
		
		return "approval/sewageApproval";	
	}
	
	/*Connection Approval End*/
	
	
	
	@RequestMapping(value="/connectionApproval",method={RequestMethod.POST,RequestMethod.GET})
	public String connectionApproval(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Approval");
		model.addAttribute("childHead1", "ReConnection/DisConnection Approval");
		return "approval/connectionApproval";	
	}
	
	/*Connection Approval End*/
	
	
	/*Meter Approval Start*/
	
	@RequestMapping(value="/meterApproval",method={RequestMethod.POST,RequestMethod.GET})
	public String meterApproval(@ModelAttribute("meterChangeApproveEntity")MeterChangeApproveEntity meterChangeApproveEntity,BindingResult bingingResult,Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Approval");
		model.addAttribute("childHead1", "Meter Approval");
		
		List<?> pendingMetersApproval=meterChangeApproveService.getMeterPendingApproval();
		model.addAttribute("pendingMetersApproval", pendingMetersApproval);
		
		if(createflagBA==1){
			model.addAttribute("msg","Meter Approved Successfully");
			createflagBA=0;
		}
		
		if(createflagBA==2){
			model.addAttribute("msg","Meter is Rejected");
			createflagBA=0;
		}
		
		return "approval/meterApproval";	
	}
	
	/*Meter Approval End*/
	
	
	/*Meter Change Approval starts*/
	
	@RequestMapping(value="/meterChangeApproval",method={RequestMethod.POST,RequestMethod.GET})
	public String meterChangeApproval(@ModelAttribute("meterChangeApproveEntity")MeterChangeApproveEntity meterChangeApproveEntity,BindingResult bingingResult,Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Approval");
		model.addAttribute("childHead1", "Meter Approval");
		
		List<?> pendingMetersApproval=meterChangeApproveService.getMeterPendingApproval();
		model.addAttribute("pendingMetersApproval", pendingMetersApproval);
		
		if(createflagBA==1){
			model.addAttribute("msg","Meter Approved Successfully");
			createflagBA=0;
		}
		
		if(createflagBA==2){
			model.addAttribute("msg","Meter is Rejected");
			createflagBA=0;
		}
		
		return "approval/meterApproval";	
	}
	
	/*Meter Change Approval Ends*/
	
	
	
	

   /*Tariff Rate Master Start*/
	
	@RequestMapping(value="/tariffRateMaster",method={RequestMethod.POST,RequestMethod.GET})
	public String tariffRateMaster(@ModelAttribute("tariffRateMaster")TariffRateMaster tariffRateMaster,Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Administration");
		model.addAttribute("childHead1", "Tariff Rate Master");
		
		model.addAttribute("tariffRateMaster",new TariffRateMaster());
		List<TariffRateMaster> tarifRates=tariffRateMasterService.getTariffRates();	
		model.addAttribute("tarifRates", tarifRates);
		
		return "billing/tariffRateMaster";	
	}
	
	
	@RequestMapping(value = "/addTariffMasterDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String addTariffMasterDetails(@ModelAttribute("tariffRateMaster")TariffRateMaster tariffRateMaster,ModelMap model,HttpServletRequest request)  
	{
		//System.out.println(" *********** inside addTariffDetails ************ ");
		try {
			Double diameter_tap=Double.parseDouble(request.getParameter("diameter_tap"));
			String meter_type=request.getParameter("meter_type");
			tariffRateMasterService.save(tariffRateMaster);
			model.addAttribute("msg","Branch Details Submitted Successfully.");
		
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("tariffRateMaster",new TariffRateMaster());
		List<TariffRateMaster> tarifRates=tariffRateMasterService.getTariffRates();	
		model.addAttribute("tarifRates", tarifRates);
		model.addAttribute("mainHead", "Administration");
		model.addAttribute("childHead1", "Tariff Rate Master");
		/*return "billing/tariffRateMaster";*/
		return "redirect:/tariffRateMaster";
	}

	@RequestMapping(value = "/getTariffDetailsForEditing/{Id}", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody Object getTariffDetailsForEditing(@PathVariable int Id)
	{

		
		return tariffRateMasterService.find(Id);
	
	}
	
	@RequestMapping(value = "/updateTariffRateDetails", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateTariffRateDetails(@ModelAttribute("tariffRateMaster") TariffRateMaster tariffRateMaster,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  
	{
		
		TariffRateMaster tariffData = tariffRateMasterService.getById(tariffRateMaster.getId(),tariffRateMaster.getDiameter_tap(),tariffRateMaster.getMeter_type());
		if(tariffData!=null&&tariffData.getMeter_type()!=null)
		{
			model.addAttribute("msg","This Tariff Data Alreday Exists!!! Please Try Other.");
		}
		else
		{
			tariffRateMasterService.update(tariffRateMaster);
			model.addAttribute("msg","Tariff Details Sumitted Successfully.");
		}
		
		 model.addAttribute("msg","Tariff Details Updated Successfully.");
		 model.addAttribute("mainHead", "Administration");
		 model.addAttribute("childHead1", "Tariff Rate Master");
		
		 return "redirect:/tariffRateMaster";
	
}
	 /*Tariff Rate Master End*/
	

	@RequestMapping(value = "/meterApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String approveMeter(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		
		String docNum[]=new String[1];
		
		
		String meterIds=request.getParameter("meteridkey");
		int meterStatus=Integer.parseInt(request.getParameter("meterStatus"));
		
		if(meterIds.contains(","))
		  {
			docNum=meterIds.split(",");
		  }
		else
		  {
			docNum[0]=meterIds;
			
		  }
	   	for(int i=0;i<docNum.length;i++)
	   	{
	   		 MeterChangeApproveEntity meterChangeApproveEntity=meterChangeApproveService.findById(Integer.parseInt(docNum[i]));
	   		 String conNo=meterChangeApproveEntity.getConnectionno();
	   		 System.out.println("---------------"+conNo+"------------");
	   		 BillingLedgerEntity ble=billingLedgerService.getLastMonthRecord(conNo);
	   		 if(meterStatus==1) {
	   			 if(meterChangeApproveEntity!=null && ble!=null) {
	   				 if((ble.getMonthyearnep()).equals(meterChangeApproveEntity.getMonthyearnep())) {
	   					 MeterDetailsEntity meterDetailsEntity=new MeterDetailsEntity();
	   					 meterDetailsEntity.setConnectionno(meterChangeApproveEntity.getConnectionno());
	   					 meterDetailsEntity.setMetcon_date_nep(meterChangeApproveEntity.getNew_metcon_date_nep());
	   					 meterDetailsEntity.setMetcon_date_eng(meterChangeApproveEntity.getNew_metcon_date_eng());
	   					 meterDetailsEntity.setMeter_no(meterChangeApproveEntity.getNew_meter_no());
	   					 meterDetailsEntity.setMeter_capacity(meterChangeApproveEntity.getNew_meter_capacity());
	   					 meterDetailsEntity.setMeter_make(meterChangeApproveEntity.getNew_meter_make());
	   					 meterDetailsEntity.setMeter_own(meterChangeApproveEntity.getNew_meter_own());
	   					 meterDetailsEntity.setIr(meterChangeApproveEntity.getIr());
	   					 meterDetailsEntity.setIns_date_nep(meterChangeApproveEntity.getNew_ins_date_nep());
	   					 meterDetailsEntity.setIns_date_eng(meterChangeApproveEntity.getNew_ins_date_eng());
	   					 meterDetailsEntity.setCal_date_nep(meterChangeApproveEntity.getNew_cal_date_nep());
	   					 meterDetailsEntity.setCal_date_eng(meterChangeApproveEntity.getNew_cal_date_eng());
	   					 meterDetailsEntity.setCalibrated_officer(meterChangeApproveEntity.getNew_calibrated_officer());
	   					 meterDetailsEntity.setRemarks(meterChangeApproveEntity.getRemarks());
	   					 meterDetailsDao.customsave(meterDetailsEntity);
	   					 
	   					 /*ble.setMcunits(meterChangeApproveEntity.getMcunits()==null?0.0:meterChangeApproveEntity.getMcunits());
	   					 if(meterChangeApproveEntity.getNew_ins_date_eng()!=null) {
	   						 ble.setMc_date(meterChangeApproveEntity.getNew_ins_date_eng());
	   					 }
	   					 billingLedgerService.update(ble);*/
	   					 
	   					 meterChangeApproveEntity.setMtr_change_app_status(1);
	   					 meterChangeApproveEntity.setApproved_by(userName);
	   					 meterChangeApproveEntity.setApproved_date(new Date());
	   					 meterChangeApproveService.update(meterChangeApproveEntity);
	   					 createflagBA=1;
	   					 
	   				 } else {
	   					meterChangeApproveEntity.setMtr_change_app_status(2);
	   					meterChangeApproveEntity.setApproved_by(userName);
	   					meterChangeApproveEntity.setApproved_date(new Date());
				   		meterChangeApproveService.update(meterChangeApproveEntity);
				   		createflagBA=2;
	   				 }
	   				 
	   			 } else {
	   				meterChangeApproveEntity.setMtr_change_app_status(2);
	   				meterChangeApproveEntity.setApproved_by(userName);
  					meterChangeApproveEntity.setApproved_date(new Date());
			   		meterChangeApproveService.update(meterChangeApproveEntity);
			   		createflagBA=2;
	   			 }
	   			 
	   			 
		   		
	   	   }  else {
		   		meterChangeApproveEntity.setMtr_change_app_status(2);
		   		meterChangeApproveEntity.setApproved_by(userName);
				meterChangeApproveEntity.setApproved_date(new Date());
		   		meterChangeApproveService.update(meterChangeApproveEntity);
		   		createflagBA=2;
	   	  }
   	  }
		
		return "redirect:/meterApproval";
		
	}

	@RequestMapping(value="/billing/viewConnectionHistory/{wardNo}/{readingday}/{value}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> viewConnectionHistory(@PathVariable String wardNo,@PathVariable int readingday,@PathVariable int value,HttpServletRequest request){
		
		List<?> connectionHistory=billingLedgerService.getConnectionHistory(wardNo,readingday,value);
		return connectionHistory;
	}
	
	@RequestMapping(value="/billing/viewConnectionHistory/{wardNo}/{value}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> viewConnectionHistoryUn(@PathVariable String wardNo,@PathVariable int value,HttpServletRequest request){
		
		List<?> connectionHistory=billingLedgerService.getConnectionHistory(wardNo,value);
		return connectionHistory;
	}
	
	
	
	//New Bulk Bill Generation
	@RequestMapping(value="/billing/calculateNetAmount/{connectionNo}/{pipesize}/{unitsConsumer}/{mcstatus}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> calculateNetAmount(@PathVariable String connectionNo,@PathVariable double pipesize,
			@PathVariable double unitsConsumer,@PathVariable int mcstatus,HttpServletRequest request){
		
		
		double minimum_charges=0.0;
		double additionalCharges=0.0;
		double waterCharges=0.0;
		double sewageCharges=0.0;
		double netAmount=0.0;
		double meterRent=0.0;
		double arrears=0.0;
		double others=0.0;
		double rateper1000Ltr=0.0;
		double wcsc=0.0;
		
		ConsumerMaster consumerMaster=consumerMasterService.findconsumer(connectionNo);
		List<Map<String, Object>> result=new ArrayList<>();
		
		try{
		
		if(consumerMaster!=null){
		
		TariffRateMaster tariffRateMaster=tariffRateMasterService.getTariffRate(pipesize,consumerMaster.getCon_type());
		
		Map<String, Object> data=new HashMap<>();
		
		if(tariffRateMaster!=null){
			
			double unitsMaster=tariffRateMaster.getMin_consumption();
			unitsConsumer=unitsConsumer*1000;
			
			if("Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
			ObservationEntity ob=observationMasterService.findById(mcstatus);
			
			//Newly Added
			
			if(ob!=null){
				
				  if("Reading".equalsIgnoreCase(ob.getObservationName())){
							if(unitsConsumer>unitsMaster){
								double difference=unitsConsumer-unitsMaster;
								double nooftimes=(double) (difference/1000);
								rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
							}
							
							minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
							additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
							waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
							
							if(consumerMaster!=null){
								
								if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
									sewageCharges=0.5*waterCharges;										//SeweRage Charges
								}
								if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
								
										meterRent=consumerMaster.getMeterRentCharges();	
														    //Meter Rent Charges
								}
							}
				 }
			     else if("Low Water Supply".equalsIgnoreCase(ob.getObservationName()) || "No Water Supply".equalsIgnoreCase(ob.getObservationName()) ||
				   "House not found".equalsIgnoreCase(ob.getObservationName())
				  || "No Reading".equalsIgnoreCase(ob.getObservationName())){
					
					
					/*if(unitsConsumer>unitsMaster){
						double difference=unitsConsumer-unitsMaster;
						double nooftimes=(double) (difference/1000);
						rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
					}*/
					
					minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							
								meterRent=consumerMaster.getMeterRentCharges();	
											    //Meter Rent Charges
						}
					}
					
					
				}else if("Door Lock".equalsIgnoreCase(ob.getObservationName()) || "Dog Presence".equalsIgnoreCase(ob.getObservationName()) || 
						"Meter Burried".equalsIgnoreCase(ob.getObservationName())){
					
					
					Object[] obj=billingLedgerService.getDoorLockCount(connectionNo);
					
					if(obj!=null){
						int doorlock=(int)obj[0];
						String billno=(String) obj[1];
						if("".equalsIgnoreCase(billno) || "null".equalsIgnoreCase(billno)){
							doorlock=doorlock+1;
						}
						if(doorlock>3){
							BsmartWaterLogger.logger.info(doorlock+"--doorlock");
							if("0.5".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=785;            
								waterCharges=785;
							}
							if("0.75".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=4595;            
								waterCharges=4595;
							}
							if("1".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=9540;            
								waterCharges=9540;								
							}
							if("1.5".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=26228;            
								waterCharges=26228;
							}
							if("2".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=54255;            
								waterCharges=54255;
							}
							if("3".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=149415;            
								waterCharges=149415;								
							}
							if("4".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=306880;            
								waterCharges=306880;
							}
							additionalCharges=0;                                 
						}
						
						else{
							
							minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
							additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
							waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
						}
					}else{
						
						minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
						additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
						waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					}
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							
								meterRent=consumerMaster.getMeterRentCharges();	
											    //Meter Rent Charges
						}
					}
					
		          }else if("Meter Block".equalsIgnoreCase(ob.getObservationName())){
					
					double avgconsumption=billingLedgerService.getAvgConsumption(connectionNo);
					double avgcons=avgconsumption/3;
					
					if(avgcons>unitsMaster){
						double difference=avgcons-unitsMaster;
						double nooftimes=(double) (difference/1000);
						rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
					}
					
					minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							
								meterRent=consumerMaster.getMeterRentCharges();	
												    //Meter Rent Charges
						}
					}
					
					
				}else if("Meter Sheild Broken".equalsIgnoreCase(ob.getObservationName())
				   || "Meter Damaged".equalsIgnoreCase(ob.getObservationName()) 
				   || "Meter Removed(No Meter)".equalsIgnoreCase(ob.getObservationName())){
					
					TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(pipesize,"Unmetered");
					minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
					waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)
				
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							
								meterRent=consumerMaster.getMeterRentCharges();	
									    //Meter Rent Charges
						}					    //Meter Rent Charges
						
					}
					
					
					if("Meter Removed(No Meter)".equalsIgnoreCase(ob.getObservationName())){
						others=0;
						data.put("remarksothers","Meter Removed from Line(No Meter)");

					}
					
				}
				else if("Temporary hole block".equalsIgnoreCase(ob.getObservationName())
						   || "Permanent hole block".equalsIgnoreCase(ob.getObservationName()) 
						   || "Service Line disconnected".equalsIgnoreCase(ob.getObservationName()) 
						   || "House Collapse(Earthquake)".equalsIgnoreCase(ob.getObservationName())
						   || "Dual Record".equalsIgnoreCase(ob.getObservationName())
						   || "PID".equalsIgnoreCase(ob.getObservationName())){
							
					
					minimum_charges=0;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=0;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=0;//Water Charges for Metered  //*(billperiod)
					sewageCharges=0;
					meterRent=0;	//Meter Rent Charges
					data.put("disconnectedstage","No bill");

			    }
				else if("Unmetered".equalsIgnoreCase(ob.getObservationName())){
							
				TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(pipesize,"Unmetered");	
				minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
				waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)
				
				if(consumerMaster!=null){
					
					if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
						sewageCharges=0.5*waterCharges;										//SeweRage Charges
					}
					
					if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
						
						meterRent=consumerMaster.getMeterRentCharges();	
						
					}else{
					  meterRent=0;					    									//Meter Rent Charges
					}
				}

			    }
				
				
			}
			
			}else{
				minimum_charges=tariffRateMaster.getMonthly_charges()*1;                	//Minimum Charges for UnMeterd //*(billperiod)
				waterCharges=tariffRateMaster.getMonthly_charges()*1;                   	//Water Charges for UnMeterd //*(billperiod)
			
				if(consumerMaster!=null){
					
					if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
						sewageCharges=0.5*waterCharges;										//SeweRage Charges
					}
					meterRent=0;					    									//Meter Rent Charges
				}
			
			}
			
			   //WCSC Charges
			   wcsc=waterCharges+sewageCharges;	
			
			   Object[] obj=billingLedgerService.getOpeningBalanceByConNo(connectionNo);
			   
			   /*BillingLedgerEntity billingLedgerEntity=billingLedgerService.findBillingLedgerBasedOnConnectionNo(connectionNo);
			   
			   if(billingLedgerEntity!=null){
				   if(billingLedgerEntity.getReceipt_date()==null){
						arrears=billingLedgerEntity.getNet_amount();
					}else{
						if(billingLedgerEntity.getClose_balance()==null){
							arrears=billingLedgerEntity.getNet_amount();
						}else{
							arrears=billingLedgerEntity.getClose_balance();
						}
					}
			   }*/
			   
			   
			   if(obj!=null){
				   if(obj[0]==null){
						arrears=(double)obj[1];
					}else{
						if(obj[3]==null){
							arrears=(double)obj[1];
						}else{
							arrears=(double)obj[2];
						}
					}
			   }
			   
			   //NET AMOUNT
			   netAmount=wcsc+meterRent+arrears+others;//Net Amount
			
			   BsmartWaterLogger.logger.info("ARREARS****"+arrears);
			   BsmartWaterLogger.logger.info("NET***"+netAmount);

				
			
				data.put("minimum_charges",df.format(minimum_charges));
				data.put("additionalCharges",df.format(additionalCharges));
				data.put("waterCharges",df.format(waterCharges));
				data.put("sewageCharges",df.format(sewageCharges));
				data.put("meterRent",meterRent);
				data.put("arrears",arrears);
				data.put("others",others);
				data.put("totalamt",df.format(waterCharges+sewageCharges+meterRent));
				data.put("netAmount",df.format(netAmount));
			
				
			
				result.add(data);
			}
		
		
		}
		
		return result;
		}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		
	}

	
	
	
	
	//Newly Added Bill Calculation on 17-july-17
	
	
	
	@RequestMapping(value="/billing/calculateNetAmount2/{connectionNo}/{pipesize}/{unitsConsumer}/{mcstatus}/{observation}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> calculateNetAmount2(@PathVariable String connectionNo,@PathVariable double pipesize,
		   @PathVariable double unitsConsumer,@PathVariable int mcstatus,@PathVariable String observation,HttpServletRequest request){
		
		double minimum_charges=0.0;
		double additionalCharges=0.0;
		double waterCharges=0.0;
		double sewageCharges=0.0;
		double netAmount=0.0;
		double meterRent=0.0;
		double arrears=0.0;
		double others=0.0;
		double rateper1000Ltr=0.0;
		double wcsc=0.0;
		
		Object[] consumerMaster=consumerMasterService.getTariffData(connectionNo);
		
		List<Map<String, Object>> result=new ArrayList<>();
		
		try{
		
		if(consumerMaster!=null){
		
		TariffRateMaster tariffRateMaster=tariffRateMasterService.getTariffRate(pipesize,(String)consumerMaster[1]);
		
		Map<String, Object> data=new HashMap<>();
		
		if(tariffRateMaster!=null){
			
			double unitsMaster=tariffRateMaster.getMin_consumption();
			unitsConsumer=unitsConsumer*1000;
			
			if("Metered".equalsIgnoreCase((String)consumerMaster[1])){
			
			if(observation!=null && !"null".equalsIgnoreCase(observation)){
				
				  if("Reading".equalsIgnoreCase(observation)){
							if(unitsConsumer>unitsMaster){
								double difference=unitsConsumer-unitsMaster;
								double nooftimes=(double) (difference/1000);
								rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
							}
							
							minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
							additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
							waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
							
							if(consumerMaster!=null){
								
								if("Yes".equalsIgnoreCase((String)consumerMaster[3])){
									sewageCharges=0.5*waterCharges;										//SeweRage Charges
								}
								if("Yes".equalsIgnoreCase((String)consumerMaster[4]) && "Metered".equalsIgnoreCase((String)consumerMaster[1])){
									meterRent=(double)consumerMaster[5];					    //Meter Rent Charges
								}
							}
				 }
			     else if("Low Water Supply".equalsIgnoreCase(observation) || "No Water Supply".equalsIgnoreCase(observation) ||
				         "House not found".equalsIgnoreCase(observation) || "No Reading".equalsIgnoreCase(observation)){
					
					
					minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase((String)consumerMaster[3])){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase((String)consumerMaster[4]) && "Metered".equalsIgnoreCase((String)consumerMaster[1])){
							meterRent=(double)consumerMaster[5];					    //Meter Rent Charges
						}
					}
					
					
				}else if("Door Lock".equalsIgnoreCase(observation) || "Dog Presence".equalsIgnoreCase(observation) || 
						"Meter Burried".equalsIgnoreCase(observation)){
					
					
					Object[] obj=billingLedgerService.getDoorLockCount(connectionNo);
					
					if(obj!=null){
						int doorlock=(int)obj[0];
						String billno=(String) obj[1];
						if("".equalsIgnoreCase(billno) || "null".equalsIgnoreCase(billno)){
							doorlock=doorlock+1;
						}
						if(doorlock>3){
							BsmartWaterLogger.logger.info(doorlock+"--doorlock");
							if("0.5".equalsIgnoreCase(""+(double)consumerMaster[2])){
								minimum_charges=785;            
								waterCharges=785;
							}
							if("0.75".equalsIgnoreCase(""+(double)consumerMaster[2])){
								minimum_charges=4595;            
								waterCharges=4595;
							}
							if("1".equalsIgnoreCase(""+(double)consumerMaster[2])){
								minimum_charges=9540;            
								waterCharges=9540;								
							}
							if("1.5".equalsIgnoreCase(""+(double)consumerMaster[2])){
								minimum_charges=26228;            
								waterCharges=26228;
							}
							if("2".equalsIgnoreCase(""+(double)consumerMaster[2])){
								minimum_charges=54255;            
								waterCharges=54255;
							}
							if("3".equalsIgnoreCase(""+(double)consumerMaster[2])){
								minimum_charges=149415;            
								waterCharges=149415;								
							}
							if("4".equalsIgnoreCase(""+(double)consumerMaster[2])){
								minimum_charges=306880;            
								waterCharges=306880;
							}
							additionalCharges=0;                                 
						}
						
						else{
							
							minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
							additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
							waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
						}
					}else{
						
						minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
						additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
						waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					}
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase((String)consumerMaster[3])){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase((String)consumerMaster[4]) && "Metered".equalsIgnoreCase((String)consumerMaster[1])){
							meterRent=(double)consumerMaster[5];					    //Meter Rent Charges
						}
					}
					
		          }else if("Meter Block".equalsIgnoreCase(observation)){
					
					double avgconsumption=billingLedgerService.getAvgConsumption(connectionNo);
					double avgcons=avgconsumption/3;
					
					if(avgcons>unitsMaster){
						double difference=avgcons-unitsMaster;
						double nooftimes=(double) (difference/1000);
						rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
					}
					
					minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase((String)consumerMaster[3])){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase((String)consumerMaster[4]) && "Metered".equalsIgnoreCase((String)consumerMaster[1])){
							meterRent=(double)consumerMaster[5];					    //Meter Rent Charges
						}
					}
					
					
				}else if("Meter Sheild Broken".equalsIgnoreCase(observation)
					   || "Meter Damaged".equalsIgnoreCase(observation) 
					   || "Meter Removed(No Meter)".equalsIgnoreCase(observation)){
						
						TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(pipesize,"Unmetered");
						minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
						waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)
					
						if(consumerMaster!=null){
							
							if("Yes".equalsIgnoreCase((String)consumerMaster[3])){
								sewageCharges=0.5*waterCharges;										//SeweRage Charges
							}
							if("Yes".equalsIgnoreCase((String)consumerMaster[4]) && "Metered".equalsIgnoreCase((String)consumerMaster[1])){
								meterRent=(double)consumerMaster[5];					    //Meter Rent Charges
							
						}
						
						
						if("Meter Removed(No Meter)".equalsIgnoreCase(observation)){
							others=0;
							data.put("remarksothers","Meter Removed from Line(No Meter)");
	
						}
						
					}
				}
				else if("Temporary hole block".equalsIgnoreCase(observation)
						   || "Permanent hole block".equalsIgnoreCase(observation) 
						   || "Service Line disconnected".equalsIgnoreCase(observation) 
						   || "House Collapse(Earthquake)".equalsIgnoreCase(observation)){
							
					
					minimum_charges=0;             //Minimum Charges for Metered //*(billperiod)
					additionalCharges=0;           //Additional Charges for Metered //*(billperiod)
				    waterCharges=0;				   //Water Charges for Metered  //*(billperiod)
					sewageCharges=0;
					meterRent=0;	               //Meter Rent Charges
					data.put("disconnectedstage","No bill");

			    }
				else if("Unmetered".equalsIgnoreCase(observation)){
							
				TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(pipesize,"Unmetered");	
				minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
				waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)
				
				if(consumerMaster!=null){
					
					if("Yes".equalsIgnoreCase((String)consumerMaster[3])){
						sewageCharges=0.5*waterCharges;									  //SeweRage Charges
					}
					
					if("Yes".equalsIgnoreCase((String)consumerMaster[4]) && "Metered".equalsIgnoreCase((String)consumerMaster[1])){
						meterRent=(double)consumerMaster[5];					    //Meter Rent Charges
					}else{
					  meterRent=0;					    									//Meter Rent Charges
					}
				}

			    }
				
				
			}
			
			}else{
				minimum_charges=tariffRateMaster.getMonthly_charges()*1;                	//Minimum Charges for UnMeterd //*(billperiod)
				waterCharges=tariffRateMaster.getMonthly_charges()*1;                   	//Water Charges for UnMeterd //*(billperiod)
			
				if(consumerMaster!=null){
					
					if("Yes".equalsIgnoreCase((String)consumerMaster[3])){
						sewageCharges=0.5*waterCharges;										//SeweRage Charges
					}
					meterRent=0;					    									//Meter Rent Charges
				}
			
			}
			
			   //WCSC Charges
			   wcsc=waterCharges+sewageCharges;	
			   double arrcoradj=0;
			   double sundryamt=0;
			   Object[] obj=billingLedgerService.getOpeningBalanceByConNo(connectionNo);
			   if(obj!=null){
				   if(obj[0]==null){
						arrears=(double)obj[1];
						arrcoradj=(double)obj[4];
						sundryamt=(double)obj[5];
					}else{
						if(obj[3]==null){
							arrears=(double)obj[1];
							arrcoradj=(double)obj[4];
							sundryamt=(double)obj[5];
						}else{
							arrears=(double)obj[2];
							sundryamt=(double)obj[5];
						}
					}
			   }
			   
			   //NET AMOUNT
			    netAmount=wcsc+meterRent+arrears+others+arrcoradj+sundryamt;//Net Amount
			
			    BsmartWaterLogger.logger.info("ARREARS****"+arrears);
			    BsmartWaterLogger.logger.info("NET***"+netAmount);
			
				data.put("minimum_charges",df.format(minimum_charges));
				data.put("additionalCharges",df.format(additionalCharges));
				data.put("waterCharges",df.format(waterCharges));
				data.put("sewageCharges",df.format(sewageCharges));
				data.put("meterRent",meterRent);
				data.put("arrears",arrears+arrcoradj+sundryamt);
				data.put("others",others);
				data.put("totalamt",df.format(waterCharges+sewageCharges+meterRent));
				data.put("netAmount",df.format(netAmount));
				result.add(data);
			}
		}
		return result;
		}catch(Exception e){
				e.printStackTrace();
				return null;
		}
	}
	
	//End Calculation

	
	//End Calculation
	
	//New Calculation Bulk Bill To Speed
	
		
	@RequestMapping(value="/billing/calculateNetAmount1/{connectionNo}/{pipesize}/{unitsConsumer}/{mcstatus}/{observation}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> calculateNetAmount1(@PathVariable String connectionNo,@PathVariable double pipesize,
		   @PathVariable double unitsConsumer,@PathVariable int mcstatus,@PathVariable String observation,HttpServletRequest request){
		
		double minimum_charges=0.0;
		double additionalCharges=0.0;
		double waterCharges=0.0;
		double sewageCharges=0.0;
		double netAmount=0.0;
		double meterRent=0.0;
		double arrears=0.0;
		double others=0.0;
		double rateper1000Ltr=0.0;
		double wcsc=0.0;
		
		BsmartWaterLogger.logger.info("Observation : "+observation);
		
		ConsumerMaster consumerMaster=consumerMasterService.findconsumer(connectionNo);
		List<Map<String, Object>> result=new ArrayList<>();
		
		try{
		
		if(consumerMaster!=null){
		TariffRateMaster tariffRateMaster=tariffRateMasterService.getTariffRate(pipesize,consumerMaster.getCon_type());
		
		Map<String, Object> data=new HashMap<>();
		
		if(tariffRateMaster!=null){
			
			double unitsMaster=tariffRateMaster.getMin_consumption();
			unitsConsumer=unitsConsumer*1000;
			
			if("Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
			
			if(observation!=null && !"null".equalsIgnoreCase(observation)){
				
				  if("Reading".equalsIgnoreCase(observation) || "Self Reading".equalsIgnoreCase(observation)){
							if(unitsConsumer>unitsMaster){
								double difference=unitsConsumer-unitsMaster;
								double nooftimes=(double) (difference/1000);
								rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
							}
							
							minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
							additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
							waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
							
							if(consumerMaster!=null){
								
								if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
									sewageCharges=0.5*waterCharges;										//SeweRage Charges
								}
								if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
									try{
									meterRent=consumerMaster.getMeterRentCharges();	
									//Meter Rent Charges
									}catch(NullPointerException e)
									{
										meterRent =0;
									}
								}
							}
				 }
			     else if("Low Water Supply".equalsIgnoreCase(observation) || "No Water Supply".equalsIgnoreCase(observation) ||
				         "House not found".equalsIgnoreCase(observation) || "No Reading".equalsIgnoreCase(observation)){
					
					
					minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							try{
							meterRent=consumerMaster.getMeterRentCharges();//Meter Rent Charges
							}catch(NullPointerException e)
							{
								meterRent=0;
							}
						}
					}
					
					
				}else if("Door Lock".equalsIgnoreCase(observation) || "Dog Presence".equalsIgnoreCase(observation) || 
						"Meter Burried".equalsIgnoreCase(observation)){
					
					
					Object[] obj=billingLedgerService.getDoorLockCount(connectionNo);
					
					if(obj!=null){
						int doorlock=(int)obj[0];
						String billno=(String) obj[1];
						if("".equalsIgnoreCase(billno) || "null".equalsIgnoreCase(billno)){
							doorlock=doorlock+1;
						}
						if(doorlock>3){
							BsmartWaterLogger.logger.info(doorlock+"--doorlock");
							if("0.5".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=785;            
								waterCharges=785;
							}
							if("0.75".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=4595;            
								waterCharges=4595;
							}
							if("1".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=9540;            
								waterCharges=9540;								
							}
							if("1.5".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=26228;            
								waterCharges=26228;
							}
							if("2".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=54255;            
								waterCharges=54255;
							}
							if("3".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=149415;            
								waterCharges=149415;								
							}
							if("4".equalsIgnoreCase(""+consumerMaster.getPipe_size())){
								minimum_charges=306880;            
								waterCharges=306880;
							}
							additionalCharges=0;                                 
						}
						
						else{
							
							minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
							additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
							waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
						}
					}else{
						
						minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
						additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
						waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					}
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							try{
							meterRent=consumerMaster.getMeterRentCharges();	
							//Meter Rent Charges
							}catch(NullPointerException e)
							{
								meterRent=0;
							}
						}
					}
					
		          }else if("Meter Block".equalsIgnoreCase(observation)){
					
					double avgconsumption=billingLedgerService.getAvgConsumption(connectionNo);
					double avgcons=avgconsumption/3;
					
					if(avgcons>unitsMaster){
						double difference=avgcons-unitsMaster;
						double nooftimes=(double) (difference/1000);
						rateper1000Ltr=Math.ceil(nooftimes)*tariffRateMaster.getRate_per_1000_ltr();
					}
					
					minimum_charges=(tariffRateMaster.getMinimum_charages())*1;            //Minimum Charges for Metered //*(billperiod)
					additionalCharges=(rateper1000Ltr)*1;                                  //Additional Charges for Metered //*(billperiod)
					waterCharges=(tariffRateMaster.getMinimum_charages()+rateper1000Ltr)*1;//Water Charges for Metered  //*(billperiod)
					
					if(consumerMaster!=null){
						
						if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
							sewageCharges=0.5*waterCharges;										//SeweRage Charges
						}
						if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
							try{
							meterRent=consumerMaster.getMeterRentCharges();					    //Meter Rent Charges
							}catch(NullPointerException e)
							{
								meterRent=0;
							}
						}
					}
					
					
				}else if("Meter Sheild Broken".equalsIgnoreCase(observation)
					   || "Meter Damaged".equalsIgnoreCase(observation) 
					   || "Meter Removed(No Meter)".equalsIgnoreCase(observation)){
						
						TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(pipesize,"Unmetered");
						minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
						waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)
					
						if(consumerMaster!=null){
							
							if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
								sewageCharges=0.5*waterCharges;										//SeweRage Charges
							}
							if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
								try{
								meterRent=consumerMaster.getMeterRentCharges();	//Meter Rent Charges
								}catch(NullPointerException e)
								{
									meterRent=0;
								}
							
						}
						
						
						if("Meter Removed(No Meter)".equalsIgnoreCase(observation)){
							others=0;
							data.put("remarksothers","Meter Removed from Line(No Meter)");
	
						}
						
					}
				}
				else if("Temporary hole block".equalsIgnoreCase(observation)
						   || "Permanent hole block".equalsIgnoreCase(observation) 
						   || "Service Line disconnected".equalsIgnoreCase(observation) 
						   || "House Collapse(Earthquake)".equalsIgnoreCase(observation)){
							
					
					minimum_charges=0;             //Minimum Charges for Metered //*(billperiod)
					additionalCharges=0;           //Additional Charges for Metered //*(billperiod)
				    waterCharges=0;				   //Water Charges for Metered  //*(billperiod)
					sewageCharges=0;
					meterRent=0;	               //Meter Rent Charges
					data.put("disconnectedstage","No bill");

			    }
				else if("Unmetered".equalsIgnoreCase(observation)){
							
				TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(pipesize,"Unmetered");	
				minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
				waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)
				
				if(consumerMaster!=null){
					
					if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
						sewageCharges=0.5*waterCharges;									  //SeweRage Charges
					}
					
					if("Yes".equalsIgnoreCase(consumerMaster.getMeterHired()) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
						try{
						meterRent=consumerMaster.getMeterRentCharges();	
						//Meter Rent Charges
						}catch(NullPointerException e)
						{
							meterRent=0;
						}
					}else{
					  meterRent=0;					    									//Meter Rent Charges
					}
				}

			    }
				
				
			}
			
			}else{
				minimum_charges=tariffRateMaster.getMonthly_charges()*1;                	//Minimum Charges for UnMeterd //*(billperiod)
				waterCharges=tariffRateMaster.getMonthly_charges()*1;                   	//Water Charges for UnMeterd //*(billperiod)
			
				if(consumerMaster!=null){
					
					if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
						sewageCharges=0.5*waterCharges;										//SeweRage Charges
					}
					meterRent=0;					    									//Meter Rent Charges
				}
			
			}
			
			   //WCSC Charges
			   wcsc=waterCharges+sewageCharges;	
			   double arrcoradj=0;
			   double sundryamt=0;
			   Object[] obj=billingLedgerService.getOpeningBalanceByConNo(connectionNo);
			   if(obj!=null){
				   if(obj[0]==null){
						arrears=(double)obj[1];
						arrcoradj=(double)obj[4];
						sundryamt=(double)obj[5];
					}else{
						if(obj[3]==null){
							arrears=(double)obj[1];
							arrcoradj=(double)obj[4];
							sundryamt=(double)obj[5];
						}else{
							arrears=(double)obj[2];
							sundryamt=(double)obj[5];
						}
					}
			   }
			   
			   //NET AMOUNT
			    netAmount=wcsc+meterRent+arrears+others+arrcoradj+sundryamt;//Net Amount
			
			    BsmartWaterLogger.logger.info("ARREARS****"+arrears);
			    BsmartWaterLogger.logger.info("NET***"+netAmount);
			
				data.put("minimum_charges",df.format(minimum_charges));
				data.put("additionalCharges",df.format(additionalCharges));
				data.put("waterCharges",df.format(waterCharges));
				data.put("sewageCharges",df.format(sewageCharges));
				data.put("meterRent",meterRent);
				data.put("arrears",arrears+arrcoradj+sundryamt);
				data.put("others",others);
				data.put("totalamt",df.format(waterCharges+sewageCharges+meterRent));
				data.put("netAmount",df.format(netAmount));
				result.add(data);
			}
		}
		return result;
		}catch(Exception e){
				e.printStackTrace();
				return null;
		}
	}
	
	//End Calculation
	
	
	
/*Bulk Billing UnMetered*/
	
	@RequestMapping(value="/bulkBillUnmetered",method={RequestMethod.POST,RequestMethod.GET})
	public String bulkBillUnmetered(Model model,HttpServletRequest request){	
		
		String nepaliMonthYr=closeMonthEndService.getLatestMonthYear();
		String yearc=nepaliMonthYr.substring(0, 4);
		String monthc=nepaliMonthYr.substring(4, 6);
		
		if("12".equalsIgnoreCase(monthc)){
			nepaliMonthYr=(Integer.parseInt(yearc)+1)+"00";
		}
		
		if(nepaliMonthYr!=null){
			
			String latestNepaliMonth=""+(Integer.parseInt(nepaliMonthYr)+1);
			
			model.addAttribute("latestNepaliMonth", latestNepaliMonth);
			
			String year=latestNepaliMonth.substring(0, 4);
			String month=latestNepaliMonth.substring(4, 6);
			
			if(StaticNepaliMonths.monthcode1.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep1);
			}else if(StaticNepaliMonths.monthcode2.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep2);
			}else if(StaticNepaliMonths.monthcode3.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep3);
			}else if(StaticNepaliMonths.monthcode4.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep4);
			}else if(StaticNepaliMonths.monthcode5.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep5);
			}else if(StaticNepaliMonths.monthcode6.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep6);
			}else if(StaticNepaliMonths.monthcode7.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep7);
			}else if(StaticNepaliMonths.monthcode8.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep8);
			}else if(StaticNepaliMonths.monthcode9.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep9);
			}else if(StaticNepaliMonths.monthcode10.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep10);
			}else if(StaticNepaliMonths.monthcode11.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep11);
			}else if(StaticNepaliMonths.monthcode12.equalsIgnoreCase(month)){
				model.addAttribute("monthDesc", year+"-"+StaticNepaliMonths.monthnep12);
			}
		}
		
		model.addAttribute("mainHead", "Billing");
		model.addAttribute("childHead1", "Bulk Bill Unmetered");
		return "billing/bulkBillUnmetered";	
	}

	
	   //Close Month End Process
		@RequestMapping(value = "/closeMonthEndProcess", method = {RequestMethod.GET, RequestMethod.POST})
		public String closeMonthEndProcess(HttpServletRequest request){
			
			try{
			
			HttpSession session=request.getSession(false);
			String sitecode=(String)session.getAttribute("branchcode");
			
			if(sitecode==null){
				return "redirect:/login";
			}
			
			String from_date=request.getParameter("fromdt");
			String to_date=request.getParameter("todt");
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	 		String[] dateArray1 = from_date.split("-"); //yyyy-mm-dd
	 		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
	 		
	 		String[] dateArray2 = to_date.split("-"); //yyyy-mm-dd
	 		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
	 		
			String userName=(String) session.getAttribute("userName");	
			String monthyearnep=request.getParameter("monthyearnep");
			
			long closeMonthExists=closeMonthEndService.checkCloseMonthExists(monthyearnep);
			
			if(closeMonthExists==1010){
				createflagc=2;
			}else if(closeMonthExists>0){
				createflagc=3;
			}else{
			
				String tot_arrears=request.getParameter("tot_arrears");
				String tot_water_charges=request.getParameter("tot_water_charges");
				String tot_sw_charges=request.getParameter("tot_sw_charges");
				String tot_mtr_charges=request.getParameter("tot_mtr_charges");
				String tot_miscellaneous=request.getParameter("tot_miscellaneous");
				String tot_penalty=request.getParameter("tot_penalty");
				String tot_rebate=request.getParameter("tot_rebate");
				String tot_net_amount=request.getParameter("tot_net_amount");
				String tot_collection=request.getParameter("tot_collection");
				String tot_close_bal=request.getParameter("tot_close_bal");
				
				
				CloseMonthEnd c=new CloseMonthEnd();
				c.setMonth_year(monthyearnep);
				c.setTot_arrears(Double.parseDouble(tot_arrears));
				c.setTot_water_charges(Double.parseDouble(tot_water_charges));
				c.setTot_sw_charges(Double.parseDouble(tot_sw_charges));
				c.setTot_mtr_charges(Double.parseDouble(tot_mtr_charges));
				c.setTot_miscellaneous(Double.parseDouble(tot_miscellaneous));
				c.setTot_penalty(Double.parseDouble(tot_penalty));
				c.setTot_rebate(Double.parseDouble(tot_rebate));
				c.setTot_net_amount(Double.parseDouble(tot_net_amount));
				c.setTot_collection(Double.parseDouble(tot_collection));
				c.setTot_close_bal(Double.parseDouble(tot_close_bal));
				c.setUser_id(userName);
				c.setCreated_date(new Date());
				closeMonthEndService.save(c);
				
				List<?> monthEndSummary=billingLedgerService.getLedgerDataByWardMonthEndSubmit(monthyearnep,sdf.format(frmDt),sdf.format(toDt));
				ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
				UpdateAsynchronous updateasynchronous = context.getBean(UpdateAsynchronous.class);
				//System.out.println("about to run");
				String schemaName=(String)session.getAttribute("schemaName");
				Future<Boolean> future = updateasynchronous.submitMonthEndAsynchronousUpdate(monthEndSummary,fiscalYearBalService,schemaName,monthyearnep,sitecode,userName);
				//System.out.println("this will run immediately.");
				
			}
			
			   createflagc=1;
			   return "redirect:/monthEndProcess";	
			}catch(Exception e){
				e.printStackTrace();
				createflagc=2;
				return "redirect:/monthEndProcess";	
			}
			
		}

		@RequestMapping(value = "/advanceCollectionReportGenerate", method = { RequestMethod.GET, RequestMethod.POST })
		public @ResponseBody String advanceCollectionReportGenerate(HttpServletRequest request) {
			
			HttpSession session=request.getSession(false);
			String sitecode=(String)session.getAttribute("branchcode");
			String userName=(String) session.getAttribute("userName");
			if(sitecode==null){
				return "redirect:/login";
			}
			
			String from_date=request.getParameter("fromdt");
			String to_date=request.getParameter("todt");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	 		String[] dateArray1 = from_date.split("-"); //yyyy-mm-dd
	 		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
	 		
	 		String[] dateArray2 = to_date.split("-"); //yyyy-mm-dd
	 		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
			String monthyearnep=request.getParameter("monthyear");
			String from=request.getParameter("from_date");
			String to=request.getParameter("to_date");
			//List<?> monthEndSummary=fiscalYearBalService.getAllRecordsByMyn(monthyearnep);
			
			List<?> monthEndSummary=billingLedgerService.getLedgerDataByWardAdvanceSubmit(monthyearnep,sdf.format(frmDt),sdf.format(toDt));
			
			
			ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
			UpdateAsynchronous updateasynchronous = context.getBean(UpdateAsynchronous.class);
			//System.out.println("about to run");
			String schemaName=(String)session.getAttribute("schemaName");
			Future<Boolean> future = updateasynchronous.updateDataFromFiscalBalance(monthEndSummary,advanceBalanceService,schemaName,monthyearnep,sitecode,userName);
			
			//int count=advanceBalanceService.updateDataFromFiscalBalance(monthEndSummary);
			
			
			return "success";
		}
		
		
		
		//Close Month End Process NEW
		// BY ANJUM
				@RequestMapping(value = "/closeMonthEndProcessNew", method = {RequestMethod.GET, RequestMethod.POST})
				public String closeMonthEndProcessNew(HttpServletRequest request){
					
					try{
					
					HttpSession session=request.getSession(false);
					String sitecode=(String)session.getAttribute("branchcode");
					String schemaName=(String)session.getAttribute("schemaName");
					if(sitecode==null){
						return "redirect:/login";
					}
					try {
						billCorrectionApproveService.updateMasterDataMonthEnd(schemaName);
					}catch (Exception e) {
						e.printStackTrace();
					}
					String from_date=request.getParameter("fromdt");
					String to_date=request.getParameter("todt");
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			 		String[] dateArray1 = from_date.split("-"); //yyyy-mm-dd
			 		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
			 		
			 		String[] dateArray2 = to_date.split("-"); //yyyy-mm-dd
			 		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
			 		
					String userName=(String) session.getAttribute("userName");	
					String monthyearnep=request.getParameter("monthyearnep");
					
					long closeMonthExists=closeMonthEndService.checkCloseMonthExists(monthyearnep);
					
					if(closeMonthExists==1010){
						createflagc=2;
					}else if(closeMonthExists>0){
						createflagc=3;
					}else{
					
						String tot_arrears=request.getParameter("tot_arrears");
						String tot_water_charges=request.getParameter("tot_water_charges");
						String tot_sw_charges=request.getParameter("tot_sw_charges");
						String tot_mtr_charges=request.getParameter("tot_mtr_charges");
						String tot_miscellaneous=request.getParameter("tot_miscellaneous");
						String tot_penalty=request.getParameter("tot_penalty");
						String tot_rebate=request.getParameter("tot_rebate");
						String tot_net_amount=request.getParameter("tot_net_amount");
						String tot_collection=request.getParameter("tot_collection");
						String tot_close_bal=request.getParameter("tot_close_bal");
						
						
						CloseMonthEnd c=new CloseMonthEnd();
						c.setMonth_year(monthyearnep);
						c.setTot_arrears(Double.parseDouble(tot_arrears));
						c.setTot_water_charges(Double.parseDouble(tot_water_charges));
						c.setTot_sw_charges(Double.parseDouble(tot_sw_charges));
						c.setTot_mtr_charges(Double.parseDouble(tot_mtr_charges));
						c.setTot_miscellaneous(Double.parseDouble(tot_miscellaneous));
						c.setTot_penalty(Double.parseDouble(tot_penalty));
						c.setTot_rebate(Double.parseDouble(tot_rebate));
						c.setTot_net_amount(Double.parseDouble(tot_net_amount));
						c.setTot_collection(Double.parseDouble(tot_collection));
						c.setTot_close_bal(Double.parseDouble(tot_close_bal));
						c.setUser_id(userName);
						c.setCreated_date(new Date());
						closeMonthEndService.save(c);
						
						List<?> monthEndSummary=billingLedgerService.getLedgerDataByWardMonthEndSubmit(monthyearnep,sdf.format(frmDt),sdf.format(toDt));
						List<?> advanceSummary=billingLedgerService.getLedgerDataByWardAdvanceSubmit(monthyearnep,sdf.format(frmDt),sdf.format(toDt));
						ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
						UpdateAsynchronous updateasynchronous = context.getBean(UpdateAsynchronous.class);
						//System.out.println("about to run");
						
						Future<Boolean> future = updateasynchronous.submitMonthEndAsynchronousUpdate(monthEndSummary,fiscalYearBalService,schemaName,monthyearnep,sitecode,userName);
						try {
							Future<Boolean> future1 = updateasynchronous.updateDataFromFiscalBalance(advanceSummary,advanceBalanceService,schemaName,monthyearnep,sitecode,userName);
						} catch (Exception e) {
							e.printStackTrace();
						}
						//System.out.println("this will run immediately.");
						
					}
					
					   createflagc=1;
					   return "redirect:/monthEndProcess";	
					}catch(Exception e){
						e.printStackTrace();
						createflagc=2;
						return "redirect:/monthEndProcess";	
					}
					
				}
		//CLOSE MONTHEND NEW END
		
		
		
		
		@RequestMapping(value="/billPenaltyAdjApprove",method={RequestMethod.POST,RequestMethod.GET})
		public String billPenaltyAdjApprove(Model model,HttpServletRequest request){
			model.addAttribute("mainHead", "Approval");
			model.addAttribute("childHead1", "Bill Penalty Approval");
			int approve_status=1;
			if(createflagbpa==1){
				model.addAttribute("msg","Application(s) Approved");
				createflagbpa=0;
			}
			if(createflagbpa==2){
				model.addAttribute("msg","Application(s) Rejected");
				createflagbpa=0;
			}
			List<?> listApproval=billPenaltyAdjService.getPendingConnectionsToApprove(approve_status,"BDJ");
			model.addAttribute("masterListApproval", listApproval);
			return "cashCounter/billpenaltyadjust";	
		}
		
		
		@RequestMapping(value="/billPenaltyAdjTrancApprove",method={RequestMethod.POST,RequestMethod.GET})
		public String billPenaltyAdjTrancApprove(Model model,HttpServletRequest request){
			model.addAttribute("mainHead", "Approval");
			model.addAttribute("childHead1", "Bill Penalty Approval");
			int approve_status=0;
			String adj_type="BDJ";
			if(createflagbpa==1){
				model.addAttribute("msg","Application(s) Approved");
				createflagbpa=0;
			}
			if(createflagbpa==2){
				model.addAttribute("msg","Application(s) Rejected");
				createflagbpa=0;
			}
			List<?> listApproval=billPenaltyAdjService.getPendingConnectionsToApprove(approve_status, adj_type);
			model.addAttribute("masterListApproval", listApproval);
			return "cashCounter/billPenaltyAdjTrancApprove";	
		}
		
		
		
		
		
		@RequestMapping(value="/billpenalty/sendToApprove",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String billPenaltyApprove(HttpServletRequest request){
			try{
				
			String connection_no=request.getParameter("connection_no");
			String bill_adj_amount=request.getParameter("bill_adj_amount");
			String penalty_adj_amount=request.getParameter("penalty_adj_amount");
			String arrears=request.getParameter("arrears");
			String net_amt=request.getParameter("net_amt");
			String to_mon_year=request.getParameter("to_mon_year");
			String remarks=request.getParameter("remarks");
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			
			String con_category=request.getParameter("con_category");
			String pipe_size=request.getParameter("pipe_size");
			String con_type=request.getParameter("con_type");
			String ward_no=request.getParameter("ward_no");
			String denoted_by=request.getParameter("denoted_by");
			
			
			
			BillPenaltyAdjustment billAdjustment=billPenaltyAdjService.checkPendingRequests(connection_no,0,"BDJ");
			
			//BillPenaltyAdjustment billAdjustment=billPenaltyAdjService.getConTypeByConNo(connection_no, 0);
			
			if(billAdjustment!=null){
				return "Last Request Still not Approved for this Connection No.After approved you can send another request";
			}else{
			
			BillPenaltyAdjustment billPenaltyAdjustment=new BillPenaltyAdjustment();
			billPenaltyAdjustment.setConnection_no(connection_no);
			billPenaltyAdjustment.setBill_adj_amount(Double.parseDouble(bill_adj_amount));
			billPenaltyAdjustment.setPenalty_adj_amount(Double.parseDouble(penalty_adj_amount));
			billPenaltyAdjustment.setArrears(Double.parseDouble(arrears));
			billPenaltyAdjustment.setNet_amount(Double.parseDouble(net_amt));
			billPenaltyAdjustment.setFrom_mon_year(Integer.parseInt(to_mon_year));
			billPenaltyAdjustment.setTo_mon_year(Integer.parseInt(to_mon_year));
			billPenaltyAdjustment.setSubmit_by(userName);
			billPenaltyAdjustment.setSubmit_date(new Date());
			billPenaltyAdjustment.setApprove_status(0);
			billPenaltyAdjustment.setRemarks(remarks);
			billPenaltyAdjustment.setAdj_type("BDJ");
			billPenaltyAdjustment.setWard_no(ward_no);
			billPenaltyAdjustment.setPipe_size(pipe_size);
			billPenaltyAdjustment.setCon_category(con_category);
			billPenaltyAdjustment.setCon_type(con_type);
			billPenaltyAdjustment.setDenoted_by(denoted_by);
			
			
			billPenaltyAdjService.save(billPenaltyAdjustment);
			
			return "Request send for Adjustment Approval Successfully";
			}
			
			}catch(Exception e){
				
				return "OOPS,Something went wrong Please try again";
			}
			
		}

		
		@RequestMapping(value="/billpenalty/sendToCorrectionApprove",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String billPenaltycorrectionApprove(HttpServletRequest request){
			try{
				
			String connection_no=request.getParameter("connection_no");
			String bill_adj_amount=request.getParameter("bill_adj_amount");
			String penalty_adj_amount=request.getParameter("penalty_adj_amount");
			String rebate_adj_amount=request.getParameter("rebate_adj_amount");
			double rebateAdjDbl=rebate_adj_amount==null?0:Double.parseDouble(rebate_adj_amount);
			String arrears=request.getParameter("arrears");
			String net_amt=request.getParameter("net_amt");
			String to_mon_year=request.getParameter("to_mon_year");
			String remarks=request.getParameter("remarks");
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			
			String con_category=request.getParameter("con_category");
			String pipe_size=request.getParameter("pipe_size");
			String con_type=request.getParameter("con_type");
			String ward_no=request.getParameter("ward_no");
			String denoted_by=request.getParameter("denoted_by");
			
			BillPenaltyAdjustment billPenaltyAdjustment=new BillPenaltyAdjustment();
			
			BillPenaltyAdjustment billAdjustment=billPenaltyAdjService.checkPendingRequests(connection_no, 0,"BAC");
			BillPenaltyAdjustment billAdjustment1=billPenaltyAdjService.checkPendingRequestsSameMonth(connection_no, to_mon_year);
			if(billAdjustment!=null){
				return "Last Request Still not Approved for this Connection No.After approved you can send another request!!";
			} else if(billAdjustment1!=null){
				return "You Cannot do Arrear Correction twice in same Month for same Customer!!";
			}
			else{
			
			//BillPenaltyCorrectionAjdutment billPenaltyAdjustment=new BillPenaltyCorrectionAjdutment();
			
			billPenaltyAdjustment.setConnection_no(connection_no);
			billPenaltyAdjustment.setBill_adj_amount(Double.parseDouble(bill_adj_amount));
			billPenaltyAdjustment.setPenalty_adj_amount(Double.parseDouble(penalty_adj_amount));
			//billPenaltyAdjustment.setRebate_adj_amount(Double.parseDouble(rebate_adj_amount));
			billPenaltyAdjustment.setArrears(Double.parseDouble(arrears));
			billPenaltyAdjustment.setNet_amount(Double.parseDouble(net_amt));
			billPenaltyAdjustment.setFrom_mon_year(Integer.parseInt(to_mon_year));
			billPenaltyAdjustment.setTo_mon_year(Integer.parseInt(to_mon_year));
			billPenaltyAdjustment.setSubmit_by(userName);
			billPenaltyAdjustment.setSubmit_date(new Date());
			billPenaltyAdjustment.setApprove_status(0);
			billPenaltyAdjustment.setRemarks(remarks);
			billPenaltyAdjustment.setAdj_type("BAC");
			billPenaltyAdjustment.setWard_no(ward_no);
			billPenaltyAdjustment.setPipe_size(pipe_size);
			billPenaltyAdjustment.setCon_category(con_category);
			billPenaltyAdjustment.setCon_type(con_type);
			billPenaltyAdjustment.setDenoted_by(denoted_by);
			billPenaltyAdjustment.setRebate_adj_amount(rebateAdjDbl);
			billPenaltyAdjService.save(billPenaltyAdjustment);
			return "Arrears Correction Adjustment Approval sent to Branch Manager Successfully";
			
			}
			
			}catch(Exception e){
				e.printStackTrace();
				return "OOPS,Something went wrong Please try again!!";
			}
			
		}

		
		
		@RequestMapping(value="/adjustmentVoucher",method={RequestMethod.POST,RequestMethod.GET})
		public String adjustmentVoucher(Model model,HttpServletRequest request){
			
			try{
				HttpSession session=request.getSession(false);
				model.addAttribute("mainHead", "Adjustment");
				model.addAttribute("childHead1", "Adjustment Module");
				
				model.addAttribute("adjustmentVoucherEntity", new AdjustmentVoucher());
				if(createflagav==1){
					model.addAttribute("msg","Adjustment Done Succesfully");
					createflagav=0;
				}
				if(createflagav==2){
					model.addAttribute("msg","Oops! Something went wrong Please try again");
					createflagav=0;
				}
				String monthyrnep=(String)session.getAttribute("monthyearNepLMY");
				String branchcode=(String)session.getAttribute("branchcode");
				String branchcode1=branchcode.substring(branchcode.length()-2,branchcode.length());
				long adjustmentNo=0;
				long newadjustmentNo=adjustmentVoucherService.getMaxAdjustmentNo(monthyrnep+""+branchcode1+"%");
				/*System.out.println(newadjustmentNo);
				System.out.println("New adjustment Number : "+newadjustmentNo);
				System.out.println("MonthYearNepali : "+monthyrnep);*/
				if(newadjustmentNo==0){
					adjustmentNo=Long.parseLong(monthyrnep+""+branchcode1+String.format("%04d", 1));
				}else{
					adjustmentNo=(newadjustmentNo+1);
				}
				//paymentEntity.setReceiptNo(branchcode+""+new SimpleDateFormat("MMYY").format(new Date())+counterNo+String.format("%07d", id+1));
				
				//paymentEntity.setReceiptNo(""+adjustmentNo);
				model.addAttribute("adjustmentNo", adjustmentNo);
				//System.out.println("adjustmentNo-----------: "+adjustmentNo);
				return "billing/adjustmentVoucher";	
			}
			catch(Exception e){
				e.printStackTrace();
				return "billing/adjustmentVoucher";
			}
			
		}
		
		@RequestMapping(value = "/createAdjustment", method = {RequestMethod.GET, RequestMethod.POST})
		public String createAdjustmentVoucher(@ModelAttribute("adjustmentVoucherEntity")AdjustmentVoucher adjustmentVoucherEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request){
			
			try{
				
				HttpSession session=request.getSession(false);
				
				String userName=(String) session.getAttribute("userName");
				String adjustment_date=request.getParameter("adjust_date");
				
				String[] dateArray1 = adjustment_date.split("-"); //yyyy-mm-dd
				Date adjustment_date_eng = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
				
				adjustmentVoucherEntity.setPrepared_by(userName);
				adjustmentVoucherEntity.setCreated_by(userName);
				adjustmentVoucherEntity.setCreated_date(new Date());
				adjustmentVoucherEntity.setAdjustment_date(adjustment_date_eng);
				adjustmentVoucherEntity.setStatus(0);
				
				
				   if(adjustmentVoucherEntity.getAdjustment_amount()!=0){
   		  			
   		  			    BillingLedgerEntity billingLedgerEntity=billingLedgerService.getLatestRecordByConnectionNo(adjustmentVoucherEntity.getConnection_no());

   		  			    if(billingLedgerEntity!=null){
   		  			    	  billingLedgerEntity.setVoucher_adj(adjustmentVoucherEntity.getAdjustment_amount());
   		  			    	  billingLedgerEntity.setNet_amount(adjustmentVoucherEntity.getAdjustment_amount()+billingLedgerEntity.getNet_amount());
   		  			    	  billingLedgerEntity.setSuspense(adjustmentVoucherEntity.getAdjustment_amount());
   		  			    	  billingLedgerService.update(billingLedgerEntity);
	   		  			}
		  			}
				
				
				
				adjustmentVoucherService.save(adjustmentVoucherEntity);
				createflagav=1;
				return "redirect:/adjustmentVoucher";

			}catch(Exception e){
				e.printStackTrace();
				createflagav=2;
				return "redirect:/adjustmentVoucher";

			}
			
			
			
		}
		
		@RequestMapping(value="/getAdjustmentList",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody Object getPendingConnectionsToApprove(){
			int approve_status=0;
			return adjustmentVoucherService.getAdjustmentList(approve_status);
		}
		
		
		
		@RequestMapping(value="/getBillCorrectionChangeList",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody Object getBillCorrectionChangeList(){
			return billCorrectionApproveService.getBillCorrectionChangeList();
		}
		
		@Scheduled(cron = "0 10 18 * * ?")
		public void updateDailyMD(){
			BsmartWaterLogger.logger.info(">>>>>>>>>>>>>>>>>Inside Cron Job>>>>>>>>>>>>>>>>>");
	   		//System.out.println(">>>>>>>>>>>>>>>>>Inside Cron Job>>>>>>>>>>>>>>>>>");
	   		billCorrectionApproveService.updateMasterDataDaily();
	   	}
		
		@Scheduled(cron = "0 10 17 * * ?")
		public void updateDailyPaymentByMonthSelectionBalanceDaily(){
	   		BsmartWaterLogger.logger.info(">>>>>>>>>>>>>>>>>Inside Cron Job updateDailyPaymentByMonthSelectionBalanceDaily>>>>>>>>>>>>>>>>>");
	   		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	   		billCorrectionApproveService.updateDailyPaymentByMonthSelectionBalanceDaily(sdf.format(new Date()));
	   	}
		
		@Scheduled(cron = "0 0 19 * * ?")
		public void dailyMisPostedBalanceUpdate(){
			billCorrectionApproveService.recalculateAllLedger();
		}
		
		
       //Close Month End Process
		 
		@RequestMapping(value="/clsMEManual",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String clsMEManual(HttpServletRequest request){
		
		String response="";
		try{
		
		HttpSession session=request.getSession(false);
		String sitecode=(String)session.getAttribute("branchcode");
		
		String from_date=request.getParameter("from_date");
		String to_date=request.getParameter("to_date");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
 		String[] dateArray1 = from_date.split("-"); //yyyy-mm-dd
 		Date frmDt = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
 		
 		String[] dateArray2 = to_date.split("-"); //yyyy-mm-dd
 		Date toDt = dateConverter.convertBsToAd(dateArray2[2]+dateArray2[1]+dateArray2[0]);
 		
		String userName=(String) session.getAttribute("userName");	
		String monthyearnep=request.getParameter("monthyearnep");
		long count=fiscalYearBalService.getTotalRecords();
		
		if(count>0){
			response= "Month End Data already inserted first time for This Branch";
		}else{
		
			List<?> monthEndSummary=billingLedgerService.getLedgerDataByWardMonthEndSubmitManual(monthyearnep,sdf.format(frmDt),sdf.format(toDt));
			ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
			UpdateAsynchronous updateasynchronous = context.getBean(UpdateAsynchronous.class);
			//System.out.println("about to run");
			String schemaName=(String)session.getAttribute("schemaName");
			Future<Boolean> future = updateasynchronous.submitMonthEndAsynchronousUpdateManual(monthEndSummary,fiscalYearBalService,schemaName,monthyearnep,sitecode,userName);
			//System.out.println("this will run immediately.");
			response= "Month End Process Done Successfully for Manual Branch First Time";
		}
		  
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return response;
	}
		
		/*Bill Recalcultion Start*/
		
		@RequestMapping(value="/recalculate",method={RequestMethod.POST,RequestMethod.GET})
		public String recalculate(Model model,HttpServletRequest request){	
			
			model.addAttribute("mainHead", "Billing");
			model.addAttribute("childHead1", "Bill ReCalculate");
			
			return "billing/recalculate";	
		}	
		
		@RequestMapping(value="/recalculateArrears/{connectionno}",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody String getPendingConnectionsToApprove(@PathVariable String connectionno,HttpServletRequest request){
			HttpSession session=request.getSession(false);
			String schemaName=(String)session.getAttribute("schemaName");
			String userName=(String) session.getAttribute("userName");
			try{
				billCorrectionApproveService.updateRecalculateArrears(connectionno,schemaName,userName);
				return "Arrears Recalculated";
			
			}catch(Exception e){
				e.printStackTrace();
				return "OOps,Something went wrong.Please try again...";
			}
		}
		
		@RequestMapping(value="/billPenaltyCorrApprove",method={RequestMethod.POST,RequestMethod.GET})
		public String billPenaltyCorrApprove(Model model,HttpServletRequest request){
			model.addAttribute("mainHead", "Approval");
			model.addAttribute("childHead1", "Bill Penalty Correction Approval");
			int approve_status=0;
			if(createflagbpa==1){
				model.addAttribute("msg","Application(s) Approved");
				createflagbpa=0;
			}
			if(createflagbpa==2){
				model.addAttribute("msg","Application(s) Rejected");
				createflagbpa=0;
			}
			
			List<?> listApproval=billPenaltyAdjService.getPendingConnectionsToApprove(approve_status,"BAC");
			
			//List<?> listApproval=billPenaltyCorrectionAdjService.getPendingConnectionsToApprove(approve_status);
			
			model.addAttribute("masterListApproval", listApproval);
			return "cashCounter/billPenaltyCorrAdjApprove";	
		}
		
		
		@RequestMapping(value="/boardCorrApprove",method={RequestMethod.POST,RequestMethod.GET})
		public String boardCorrApprove(Model model,HttpServletRequest request){
			model.addAttribute("mainHead", "Approval");
			model.addAttribute("childHead1", "Board Correction Approval");
			int approve_status=0;
			if(createflagbpa==1){
				model.addAttribute("msg","Application(s) Approved");
				createflagbpa=0;
			}
			if(createflagbpa==2){
				model.addAttribute("msg","Application(s) Rejected");
				createflagbpa=0;
			}
			
			if(createflagbpa==3){
				model.addAttribute("msg","Oops,board correction not Performed,Board Bal is Zero");
				createflagbpa=0;
			}
			
			List<?> listApproval=billPenaltyAdjService.getPendingBrdCrConnectionsToApprove(approve_status,"BRDCR");
			
			//List<?> listApproval=billPenaltyCorrectionAdjService.getPendingConnectionsToApprove(approve_status);
			
			model.addAttribute("masterListApproval", listApproval);
			return "cashCounter/boardCorrApp";	
		}
		
		
		
		
		@RequestMapping(value = "/billpenaltyCorrApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
		public String billpenaltyCorrApproveStatus(HttpServletRequest request){
			
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			String docNum[]=new String[1];
			
			String billIds=request.getParameter("connectId");
			
			int conStatus=Integer.parseInt(request.getParameter("conStatus"));
			
			BsmartWaterLogger.logger.info(billIds+"billIds");
			if(billIds.contains(","))
			  {
				docNum=billIds.split(",");
			  }
			else
			  {
				docNum[0]=billIds;
				
			  }
	   	
	   	  for(int i=0;i<docNum.length;i++)
	   		{
	   		
	   		  	BsmartWaterLogger.logger.info(docNum[i]);
		   		
	   		  	int approve_status=0;
	   		  	
	   		  	BillPenaltyAdjustment billPenaltyAdjustment=billPenaltyAdjService.checkPendingRequests(docNum[i],approve_status,"BAC");

	   		  	//BillPenaltyCorrectionAjdutment billPenaltyAdjustment=billPenaltyCorrectionAdjService.getConTypeByConNo(docNum[i],approve_status);
	   		  	
	   		  	if(conStatus==1){
	   		  		if(billPenaltyAdjustment!=null){
	   		  			
		   		  			BillingLedgerEntity billingLedgerEntity=billingLedgerService.getLatestBillNotNull(docNum[i]);
		   		  			
		   		  			if(billingLedgerEntity!=null){
		   		  			    
		   		  				Double close_balance=billingLedgerEntity.getClose_balance();
		   		  				Double lastPaid=billingLedgerEntity.getLast_paid_amount();
		   		  				String recNo=billingLedgerEntity.getReceipt_no();
		   		  				
		   		  				if(close_balance!=null && lastPaid!=null && recNo!=null){
		   		  				  billingLedgerEntity.setClose_balance(close_balance+billPenaltyAdjustment.getBill_adj_amount());
		   		  				  billingLedgerEntity.setService_charge(billPenaltyAdjustment.getBill_adj_amount());
		   		  				  //billingLedgerEntity.setTotalamt(billPenaltyAdjustment.getPenalty_adj_amount());
		   		  				  billingLedgerEntity.setCorr_id(billPenaltyAdjustment.getId());
		   		  				  
		   		  				  billingLedgerEntity.setRemarks("Correction approved after payment");
		   		  				  billingLedgerEntity.setStatus("AC APRVD AFTR PMT");
		   		  				  billingLedgerService.update(billingLedgerEntity);
		   		  				}else{
		   		  					
		   		  				//SERVICE CHARGE WE ARE USING AS ARREAR CORRECTION FIELD.
		   		  				//TOTAL AMT FIELD WE ARE USING AS PENALTY CORRECTION FIELD.
		   		  				//LATEFEE FIELD WE ARE USING AS REBATE CORRECTION FIELD.
		   		  					
	   		  					  billingLedgerEntity.setService_charge(billPenaltyAdjustment.getBill_adj_amount());
		   		  				  billingLedgerEntity.setTotalamt(billPenaltyAdjustment.getPenalty_adj_amount());
		   		  				  billingLedgerEntity.setLatefee(billPenaltyAdjustment.getRebate_adj_amount());
		   		  				  billingLedgerEntity.setCorr_id(billPenaltyAdjustment.getId());
		   		  				  billingLedgerEntity.setRemarks("Correction approved before payment");
		   		  				  billingLedgerEntity.setStatus("AC APRVD BFR PMT");
		   		  			      billingLedgerService.update(billingLedgerEntity);
		   		  				}
		   		  				
		   		  			}
	   		  			
	   		  			
	   		  			billPenaltyAdjustment.setApprove_status(1);
	   		  		    billPenaltyAdjustment.setApproved_by(userName);
	   		  		    billPenaltyAdjustment.setApproved_date(new Date());
	   		  		    billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}else{
	   		  		
		   		  	if(billPenaltyAdjustment!=null){
	   		  			billPenaltyAdjustment.setApprove_status(2);
	   		  			billPenaltyAdjustment.setApproved_by(userName);
	   		  		    billPenaltyAdjustment.setApproved_date(new Date());
	   		  		    billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}
	   	   }
		   	if(conStatus==1){
				createflagbpa=1;
			}else{
				createflagbpa=2;
			}
	   	
			return "redirect:/billPenaltyCorrApprove";
			
		}
		//********************************** BOARD CORRECTION APPROVE STATUS *******************************
		
		@RequestMapping(value = "/boardCorrApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
		public String boardCorrApproveStatus(HttpServletRequest request){
			
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			String docNum[]=new String[1];
			
			String billIds=request.getParameter("connectId");
			
			int conStatus=Integer.parseInt(request.getParameter("conStatus"));
			
			BsmartWaterLogger.logger.info(billIds+"billIds");
			if(billIds.contains(","))
			  {
				docNum=billIds.split(",");
			  }
			else
			  {
				docNum[0]=billIds;
				
			  }
	   	
	   	  for(int i=0;i<docNum.length;i++)
	   		{
	   		
	   		  	BsmartWaterLogger.logger.info(docNum[i]);
		   		
	   		  	int approve_status=0;
	   		  	BillPenaltyAdjustment billPenaltyAdjustment=billPenaltyAdjService.getPendingBoardCrByConNo(docNum[i],approve_status);
	   		  	
	   		  	if(conStatus==1){
	   		  		if(billPenaltyAdjustment!=null){
	   		  			
	   		  			ConsumerMaster consumerMaster=consumerMasterService.findconsumer(docNum[i]);
	   		  			if(consumerMaster!=null){
	   		  			try {
	   		  			double balance= consumerMaster.getBalance();
	   		  			
	   		  			double newboard=balance+billPenaltyAdjustment.getBoard_adj();
	   		  			
	   		  			//System.out.println("Board balance= "+balance);
	   		  			//System.out.println("New Board balance= "+newboard);
	   		  			consumerMaster.setBalance(newboard);
	   		  			consumerMaster.setRemarks(billPenaltyAdjustment.getRemarks());
	   		  			consumerMasterService.update(consumerMaster);
	   		  			
	   		  			
	   		  			
	   		  			
	   		  			billPenaltyAdjustment.setApprove_status(1);
	   		  		    billPenaltyAdjustment.setApproved_by1(userName);
	   		  		    billPenaltyAdjustment.setApproved_date1(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		  		}catch(NullPointerException e)
   		  			{
	   		  		createflagbpa=3;
	   		  		return "redirect:/boardCorrApprove";
   		  			}
   		  				
   		  			}
	   		        
	   		  		}
	   		  	}else{
	   		  		
		   		  	if(billPenaltyAdjustment!=null){
	   		  			billPenaltyAdjustment.setApprove_status(2);
	   		  			billPenaltyAdjustment.setApproved_by1(userName);
	   		  		    billPenaltyAdjustment.setApproved_date1(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}
	   	   }
		   	if(conStatus==1){
				createflagbpa=1;
			}else{
				createflagbpa=2;
			}
	   	
			return "redirect:/boardCorrApprove";
			
		}		
		
	
		//******************************** BOARD CORRECTION APPROVE STATUS ***********************************
		@RequestMapping(value="/arrearsCorrection",method={RequestMethod.POST,RequestMethod.GET})
		public String arrearsCorrection(Model model,HttpServletRequest request){
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			Object[] desig=userMasterService.getOfficialName(userName);
			//System.out.println(desig[0]+"----"+desig[1]);
			model.addAttribute("designation", desig[1]);
			
			return "billing/arrearsCorrection";
			
		}
		
		@RequestMapping(value="/getconnectionMasterLedgerDetails/{conNo}")
		public @ResponseBody Object getconnectionMasterLedgerDetails(@PathVariable String conNo, HttpServletRequest request)
		{
			//String conNo=request.getParameter("");
			return billingLedgerService.getconnectionMasterLedgerDetails(conNo,request);
		}
		
		@RequestMapping(value = "/billpenaltyAdjApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
		public String billpenaltyAdjApproveStatus(HttpServletRequest request){
			
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			String docNum[]=new String[1];
			
			String billIds=request.getParameter("connectId");
			int conStatus=Integer.parseInt(request.getParameter("conStatus"));
			
			BsmartWaterLogger.logger.info(billIds+"billIds");
			if(billIds.contains(","))
			  {
				docNum=billIds.split(",");
			  }
			else
			  {
				docNum[0]=billIds;
				
			  }
	   	
	   	  for(int i=0;i<docNum.length;i++)
	   		{
	   		
	   		  	BsmartWaterLogger.logger.info(docNum[i]);
		   		
	   		  	int approve_status=1;
	   		  	BillPenaltyAdjustment billPenaltyAdjustment=billPenaltyAdjService.getConTypeByConNo(docNum[i],approve_status);
	   		  	BillingLedgerEntity billingLedgerEntity=billingLedgerService.getLatestRecordByConnectionNo(docNum[i]);
	   		  	
	   		  	if(conStatus==1){
	   		  		
	   		  		if(billPenaltyAdjustment!=null){
	   		  			
	   		  			if(billPenaltyAdjustment.getBill_adj_amount()!=0 || billPenaltyAdjustment.getPenalty_adj_amount()!=0){
	   		  			
		   		  			
		   		  			if(billingLedgerEntity!=null){
		   		  				
		   		  				billingLedgerEntity.setSundry_amount(billPenaltyAdjustment.getBill_adj_amount());
		   		  				billingLedgerEntity.setStatus("HA");
		   		  				double pending_adj_approve=0;
		   		  				try{
		   		  					pending_adj_approve=billingLedgerEntity.getPending_adj_approve();
		   		  				}catch(Exception e){
		   		  					pending_adj_approve=0;
		   		  				}
		   		  				
		   		  				if(pending_adj_approve!=0){
		   		  					billingLedgerEntity.setPending_adj_approve(billingLedgerEntity.getPending_adj_approve()-billPenaltyAdjustment.getBill_adj_amount());
		   		  				}
		   		  			    billingLedgerService.update(billingLedgerEntity);
		   		  			    
		   		  			}
	   		  			}
	   		  			
	   		  			billPenaltyAdjustment.setApprove_status(3);
	   		  		    billPenaltyAdjustment.setApproved_by1(userName);
	   		  		    billPenaltyAdjustment.setApproved_date1(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}else{
	   		  		
	   		  		if(billingLedgerEntity!=null){
	   		  			//billingLedgerEntity.setSundry_amount(-billPenaltyAdjustment.getBill_adj_amount());
	   		  			billingLedgerEntity.setStatus("HR");
		   		  		double pending_adj_approve=0;
		  				try{
		  					pending_adj_approve=billingLedgerEntity.getPending_adj_approve();
		  				}catch(Exception e){
		  					pending_adj_approve=0;
		  				}
		  				
		  				if(pending_adj_approve!=0){
		  					billingLedgerEntity.setPending_adj_approve(billingLedgerEntity.getPending_adj_approve()-billPenaltyAdjustment.getBill_adj_amount());
		  				}
	   		  			
	   		  			billingLedgerService.update(billingLedgerEntity);
	   		  			
	   		  		}
	   		  		
		   		  	if( billPenaltyAdjustment!=null){
	   		  			billPenaltyAdjustment.setApprove_status(2);
	   		  			billPenaltyAdjustment.setApproved_by1(userName);
	   		  		    billPenaltyAdjustment.setApproved_date1(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		  		}
	   		  	}
	   	   }
		   	if(conStatus==1){
				createflagbpa=1;
			}else{
				createflagbpa=2;
			}
	   	
			return "redirect:/billPenaltyAdjApprove";
			
		}
		
		
		@RequestMapping(value = "/billpenaltyAdjApproveStatusCorporate", method = {RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody String billpenaltyAdjApproveStatusCorporate(HttpServletRequest request){
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			
			String conNo=request.getParameter("conNo");
			String branch=request.getParameter("branch");
			String status=request.getParameter("status");
			
			int approve_status=1;
			BillPenaltyAdjustment billPenaltyAdjustment=billPenaltyAdjService.getConTypeByConNo(conNo,approve_status,branch);
			BillingLedgerEntity billingLedgerEntity=billingLedgerService.getLatestRecordByConnectionNo(conNo,branch);
			
			if("1".equalsIgnoreCase(status)) {
				
		   		 if(billPenaltyAdjustment!=null){
			  			
			  			if(billPenaltyAdjustment.getBill_adj_amount()!=0 || billPenaltyAdjustment.getPenalty_adj_amount()!=0){
			  			
		   		  			
		   		  			if(billingLedgerEntity!=null){
		   		  				
		   		  				billingLedgerEntity.setSundry_amount(billPenaltyAdjustment.getBill_adj_amount());
		   		  				billingLedgerEntity.setStatus("HA");
		   		  				double pending_adj_approve=0;
		   		  				try{
		   		  					pending_adj_approve=billingLedgerEntity.getPending_adj_approve();
		   		  				}catch(Exception e){
		   		  					pending_adj_approve=0;
		   		  				}
		   		  				
		   		  				if(pending_adj_approve!=0){
		   		  					billingLedgerEntity.setPending_adj_approve(billingLedgerEntity.getPending_adj_approve()-billPenaltyAdjustment.getBill_adj_amount());
		   		  				}
		   		  			    billingLedgerService.update(billingLedgerEntity,branch);
		   		  			    
		   		  			}
			  			}
			  			
			  			billPenaltyAdjustment.setApprove_status(3);
			  		    billPenaltyAdjustment.setApproved_by1(userName);
			  		    billPenaltyAdjustment.setApproved_date1(new Date());
			  			billPenaltyAdjService.update(billPenaltyAdjustment,branch);
			        
			  		}
					return "Approved";
				} else{
	   		  		
	   		  		if(billingLedgerEntity!=null){
	   		  			//billingLedgerEntity.setSundry_amount(-billPenaltyAdjustment.getBill_adj_amount());
	   		  			billingLedgerEntity.setStatus("HR");
	   		  			double pending_adj_approve=0;
		  				try{
		  					pending_adj_approve=billingLedgerEntity.getPending_adj_approve();
		  				}catch(Exception e){
		  					pending_adj_approve=0;
		  				}
		  				
		  				if(pending_adj_approve!=0){
		  					billingLedgerEntity.setPending_adj_approve(billingLedgerEntity.getPending_adj_approve()-billPenaltyAdjustment.getBill_adj_amount());
		  				}
	   		  			billingLedgerService.update(billingLedgerEntity,branch);
	   		  			
	   		  		}
	   		  		
		   		  	if( billPenaltyAdjustment!=null){
	   		  			billPenaltyAdjustment.setApprove_status(2);
	   		  			billPenaltyAdjustment.setApproved_by1(userName);
	   		  		    billPenaltyAdjustment.setApproved_date1(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment,branch);
	   		  		}
		   		  	return "Rejected";
	   		  	}
			
		}
		
		
		@RequestMapping(value = "/billpenaltyAdjTrancApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
		public String billpenaltyAdjTrancApproveStatus(HttpServletRequest request){
			
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			String docNum[]=new String[1];
			
			String billIds=request.getParameter("connectId");
			
			int conStatus=Integer.parseInt(request.getParameter("conStatus"));
			
			BsmartWaterLogger.logger.info(billIds+"billIds");
			if(billIds.contains(","))
			  {
				docNum=billIds.split(",");
			  }
			else
			  {
				docNum[0]=billIds;
				
			  }
	   	
	   	  for(int i=0;i<docNum.length;i++)
	   		{
	   		
	   		  	BsmartWaterLogger.logger.info(docNum[i]);
		   		
	   		  	int approve_status=0;
	   		  	BillPenaltyAdjustment billPenaltyAdjustment=billPenaltyAdjService.getConTypeByConNo(docNum[i],approve_status);
	   		  	
	   		  	if(conStatus==1){
	   		  		if(billPenaltyAdjustment!=null){
	   		  			
	   		  			/*if(billPenaltyAdjustment.getBill_adj_amount()!=0){
	   		  			
	   		  			}*/
		   		  			BillingLedgerEntity billingLedgerEntity=billingLedgerService.getLatestBillNotNull(docNum[i]);
		   		  			
		   		  			if(billingLedgerEntity!=null){
		   		  				
		   		  				billingLedgerEntity.setBill_adj_amount(billPenaltyAdjustment.getBill_adj_amount());
		   		  				billingLedgerEntity.setPenalty_adj_amount(billPenaltyAdjustment.getPenalty_adj_amount());
		   		  				billingLedgerEntity.setAdj_id(billPenaltyAdjustment.getId());
		   		  				billingLedgerEntity.setPending_adj_approve(billPenaltyAdjustment.getBill_adj_amount());
		   		  				billingLedgerEntity.setStatus("TA");
		   		  			    billingLedgerService.update(billingLedgerEntity);
		   		  				
		   		  			}
	   		  			
	   		  			billPenaltyAdjustment.setApprove_status(1);
	   		  		    billPenaltyAdjustment.setApproved_by(userName);
	   		  		    billPenaltyAdjustment.setApproved_date(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}else{
	   		  		
		   		  	if(billPenaltyAdjustment!=null){
	   		  			billPenaltyAdjustment.setApprove_status(2);
	   		  			billPenaltyAdjustment.setApproved_by(userName);
	   		  		    billPenaltyAdjustment.setApproved_date(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}
	   	   }
		   	if(conStatus==1){
				createflagbpa=1;
			}else{
				createflagbpa=2;
			}
	   	
			return "redirect:/billPenaltyAdjTrancApprove";
			
		}
		
		@RequestMapping(value="/getAdjCorrList")
		public @ResponseBody Object getAdjCorrList(HttpServletRequest request)
		{
			return billPenaltyAdjService.getAdjCorrList();
		}
		
		@RequestMapping(value="/getAdjTransList")
		public @ResponseBody Object getAdjTransList(HttpServletRequest request)
		{
			return billPenaltyAdjService.getAdjTransList("BDJ");
		}
		
		@RequestMapping(value="/getArrearCorrList")
		public @ResponseBody Object getArrearCorrList(HttpServletRequest request)
		{
			//return billPenaltyAdjService.getArrearCorrList("BRDCR");
			return billPenaltyAdjService.getArrearCorrList("BAC");
		}
		
		@RequestMapping(value="/getBoardTransList")
		public @ResponseBody Object getBoardTransList(HttpServletRequest request)
		{
			return billPenaltyAdjService.getAdjTransList("BOARD");
		}
		
		
		
		@RequestMapping(value="/billpenalty/sendToBoardApprove",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String sendToBoardApprove(HttpServletRequest request){
			try{
				
			String connection_no=request.getParameter("connection_no");
			//String bill_adj_amount=request.getParameter("bill_adj_amount");
			String board_adj_amount=request.getParameter("board_adj_amount");
			String board_penalty_adj=request.getParameter("board_penalty_adj");
			String arrears=request.getParameter("arrears");
			String net_amt=request.getParameter("net_amt");
			String to_mon_year=request.getParameter("to_mon_year");
			String remarks=request.getParameter("remarks");
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			
			
			
			/*BillPenaltyAdjustment billAdjustment=billPenaltyAdjService.checkPendingRequests(connection_no, 0,"BOARD");
			BillPenaltyAdjustment billAdjustment1=billPenaltyAdjService.checkPendingRequests(connection_no, 1,"BOARD");
			BillPenaltyAdjustment billAdjustment2=billPenaltyAdjService.checkPendingRequests(connection_no, 3,"BOARD");
			//BillPenaltyAdjustment billAdjustment1=billPenaltyAdjService.checkPendingRequestsSameMonth(connection_no, to_mon_year);
			if(billAdjustment!=null){
				return "Connection Number : "+connection_no+" pending for Board Transaction Approval.";
			} else if(billAdjustment1!=null){
				return "Connection Number : "+connection_no+" pending for Head Office Aproval.";
			} else if(billAdjustment2!=null){
				return "Board Adjustment is allowed only once. Connection Number : "+connection_no+" alread Approved from Head Office.";
			}*/
			
			Object[] obj=billPenaltyAdjService.getTransactionCount(connection_no);
			BsmartWaterLogger.logger.info(obj+"----------------------------");
			
			if(obj!=null && (((java.math.BigDecimal) (obj[0])).intValue()!= 0 || ((java.math.BigDecimal) (obj[1])).intValue()!= 0 || ((java.math.BigDecimal) (obj[2])).intValue()!= 0)){

				if (((java.math.BigDecimal) (obj[0])).intValue() == 1) {
					return "Connection Number : " + connection_no + " pending for Board Transaction Approval.";
				} else if (((java.math.BigDecimal) (obj[1])).intValue() == 1) {
					return "Connection Number : "+connection_no+" pending for Head Office Aproval.";
				} else if (((java.math.BigDecimal) (obj[2])).intValue() == 1) {
					return "Board Adjustment is allowed only once. Connection Number : "+connection_no+" alread Approved from Head Office.";
				}
				
			}
			
			
			else{
			
			//BillPenaltyCorrectionAjdutment billPenaltyAdjustment=new BillPenaltyCorrectionAjdutment();
				BsmartWaterLogger.logger.info(obj+"----------else------------------");
			BillPenaltyAdjustment billPenaltyAdjustment=new BillPenaltyAdjustment();
			billPenaltyAdjustment.setConnection_no(connection_no);
			//billPenaltyAdjustment.setBill_adj_amount(Double.parseDouble(bill_adj_amount));
			//billPenaltyAdjustment.setPenalty_adj_amount(Double.parseDouble(penalty_adj_amount));
			billPenaltyAdjustment.setBoard_adj(Double.parseDouble(board_adj_amount));
			billPenaltyAdjustment.setPenalty_adj_amount(Double.parseDouble(board_penalty_adj));
			billPenaltyAdjustment.setArrears(Double.parseDouble(arrears));
			billPenaltyAdjustment.setNet_amount(Double.parseDouble(net_amt));
			billPenaltyAdjustment.setFrom_mon_year(Integer.parseInt(to_mon_year));
			billPenaltyAdjustment.setTo_mon_year(Integer.parseInt(to_mon_year));
			billPenaltyAdjustment.setSubmit_by(userName);
			billPenaltyAdjustment.setSubmit_date(new Date());
			billPenaltyAdjustment.setApprove_status(0);
			billPenaltyAdjustment.setRemarks(remarks);
			billPenaltyAdjustment.setAdj_type("BOARD");
			billPenaltyAdjService.save(billPenaltyAdjustment);
			return "Board Adjustment sent to Branch Manager for Approval Successfully";
			
			}
			
			}catch(Exception e){
				e.printStackTrace();
				return "OOPS,Something went wrong Please try again!!";
			}
			return null;
		}
		// ##############################      ***********Board Correction Code ****************************************
		@RequestMapping(value="/billpenalty/sendToBoradCorrectionApprove",method={RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody String sendToBoardCorrectionApprove(HttpServletRequest request){
			try{
				
			String connection_no=request.getParameter("connection_no");
			String bill_adj_amount=request.getParameter("bill_adj_amount");
			if(bill_adj_amount==null || bill_adj_amount=="" )
			{
				bill_adj_amount="0.0";
			}
			String penalty_adj_amount=request.getParameter("penalty_adj_amount");
			if(penalty_adj_amount==null || penalty_adj_amount=="")
			{
				penalty_adj_amount="0.0";
			}
			String board_adj_amount=request.getParameter("board_adj_amount");
			String board_penalty_adj=request.getParameter("board_penalty_adj");
			String arrears=request.getParameter("arrears");
			String net_amt=request.getParameter("net_amt");
			String to_mon_year=request.getParameter("to_mon_year");
			String remarks=request.getParameter("remarks");
			String adjType=request.getParameter("adjtype");
			
			String con_category=request.getParameter("con_category");
			String denoted_by=request.getParameter("denoted_by");
			String ward_no=request.getParameter("ward_no");
			String pipe_size=request.getParameter("pipe_size");
			
			
			
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			
			System.out.println(connection_no+"--------------"+board_adj_amount+"-----------"+remarks+"-------"+adjType);
			
			BillPenaltyAdjustment billAdjustment=billPenaltyAdjService.checkPendingRequests(connection_no, 0,"BRDCR");
			BillPenaltyAdjustment billAdjustment1=billPenaltyAdjService.checkPendingRequests(connection_no, 1,"BRDCR");
			BillPenaltyAdjustment billAdjustment2=billPenaltyAdjService.checkPendingRequests(connection_no, 3,"BRDCR");
			//BillPenaltyAdjustment billAdjustment1=billPenaltyAdjService.checkPendingRequestsSameMonth(connection_no, to_mon_year);
			if(billAdjustment!=null){
				return "Connection Number : "+connection_no+" pending for Board Correction Approval.";
			} else if(billAdjustment1!=null){
				return "Connection Number : "+connection_no+" pending for Head Office Aproval.";
			} else if(billAdjustment2!=null){
				return "Board Correction is allowed only once. Connection Number : "+connection_no+" alread Approved from Head Office.";
			}
			
			Object[] obj=billPenaltyAdjService.getTransactionCountForBrdCr(connection_no);
			BsmartWaterLogger.logger.info(obj+"----------------------------");
			
			if(obj!=null && (((java.math.BigDecimal) (obj[0])).intValue()!= 0 || ((java.math.BigDecimal) (obj[1])).intValue()!= 0 || ((java.math.BigDecimal) (obj[2])).intValue()!= 0)){

				if (((java.math.BigDecimal) (obj[0])).intValue() == 1) {
					return "Connection Number : " + connection_no + " pending for Board Transaction Approval.";
				} else if (((java.math.BigDecimal) (obj[1])).intValue() == 1) {
					return "Connection Number : "+connection_no+" pending for Head Office Aproval.";
				} else if (((java.math.BigDecimal) (obj[2])).intValue() == 1) {
					return "Board Adjustment is allowed only once. Connection Number : "+connection_no+" alread Approved from Head Office.";
				}
				
			}
			
			
			else{
			
			//BillPenaltyCorrectionAjdutment billPenaltyAdjustment=new BillPenaltyCorrectionAjdutment();
			BsmartWaterLogger.logger.info(obj+"----------else------------------");
			BillPenaltyAdjustment billPenaltyAdjustment=new BillPenaltyAdjustment();
			billPenaltyAdjustment.setConnection_no(connection_no);
			billPenaltyAdjustment.setBill_adj_amount(Double.parseDouble(bill_adj_amount));
			billPenaltyAdjustment.setPenalty_adj_amount(Double.parseDouble(penalty_adj_amount));
			billPenaltyAdjustment.setBoard_adj(Double.parseDouble(board_adj_amount));
			billPenaltyAdjustment.setPenalty_adj_amount(Double.parseDouble(board_penalty_adj));
			billPenaltyAdjustment.setArrears(Double.parseDouble(arrears));
			billPenaltyAdjustment.setNet_amount(Double.parseDouble(net_amt));
			billPenaltyAdjustment.setFrom_mon_year(Integer.parseInt(to_mon_year));
			billPenaltyAdjustment.setTo_mon_year(Integer.parseInt(to_mon_year));
			billPenaltyAdjustment.setSubmit_by(userName);
			billPenaltyAdjustment.setSubmit_date(new Date());
			billPenaltyAdjustment.setApprove_status(0);
			billPenaltyAdjustment.setRemarks(remarks);
			billPenaltyAdjustment.setAdj_type("BRDCR");
			billPenaltyAdjustment.setCon_category(con_category);
			billPenaltyAdjustment.setDenoted_by(denoted_by);
			billPenaltyAdjustment.setWard_no(ward_no);
			billPenaltyAdjustment.setPipe_size(pipe_size);
			billPenaltyAdjService.save(billPenaltyAdjustment);
			return "Board Correction sent to Branch Manager for Approval Successfully";
			
			}
			
			}catch(Exception e){
				e.printStackTrace();
				return "OOPS,Something went wrong Please try again!!";
			}
			return null;
		}
		// ##############################      ***********Board Correction Code ****************************************
		
		
		@RequestMapping(value="/boardAdjTransApprove",method={RequestMethod.POST,RequestMethod.GET})
		public String boardAdjTransApprove(Model model,HttpServletRequest request){
			model.addAttribute("mainHead", "Approval");
			model.addAttribute("childHead1", "Board Transaction Approval");
			int approve_status=0;
			if(createflagbpa==1){
				model.addAttribute("msg","Application(s) Approved");
				createflagbpa=0;
			}
			if(createflagbpa==2){
				model.addAttribute("msg","Application(s) Rejected");
				createflagbpa=0;
			}
			
			List<?> listApproval=billPenaltyAdjService.getPendingConnectionsToApprove(approve_status,"BOARD");
			
			//List<?> listApproval=billPenaltyCorrectionAdjService.getPendingConnectionsToApprove(approve_status);
			
			model.addAttribute("masterListApproval", listApproval);
			return "cashCounter/boardAdjTransApprove";	
		}
		
		@RequestMapping(value = "/boardAdjTransApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
		public String boardAdjTransApproveStatus(HttpServletRequest request){
			
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			String docNum[]=new String[1];
			
			String billIds=request.getParameter("connectId");
			
			int conStatus=Integer.parseInt(request.getParameter("conStatus"));
			
			BsmartWaterLogger.logger.info(billIds+"billIds");
			if(billIds.contains(","))
			  {
				docNum=billIds.split(",");
			  }
			else
			  {
				docNum[0]=billIds;
				
			  }
	   	
	   	  for(int i=0;i<docNum.length;i++)
	   		{
	   		
	   		  	BsmartWaterLogger.logger.info(docNum[i]);
		   		
	   		  	int approve_status=0;
	   		  	BillPenaltyAdjustment billPenaltyAdjustment=billPenaltyAdjService.getPendingBoardByConNo(docNum[i],approve_status);
	   		  	
	   		  	if(conStatus==1){
	   		  		if(billPenaltyAdjustment!=null){
	   		  			
	   		  				   		  			
	   		  			billPenaltyAdjustment.setApprove_status(1);
	   		  		    billPenaltyAdjustment.setApproved_by(userName);
	   		  		    billPenaltyAdjustment.setApproved_date(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}else{
	   		  		
		   		  	if(billPenaltyAdjustment!=null){
	   		  			billPenaltyAdjustment.setApprove_status(2);
	   		  			billPenaltyAdjustment.setApproved_by(userName);
	   		  		    billPenaltyAdjustment.setApproved_date(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}
	   	   }
		   	if(conStatus==1){
				createflagbpa=1;
			}else{
				createflagbpa=2;
			}
	   	
			return "redirect:/boardAdjTransApprove";
			
		}
		
		@RequestMapping(value="/boardAdjApprove",method={RequestMethod.POST,RequestMethod.GET})
		public String boardAdjApprove(Model model,HttpServletRequest request){
			model.addAttribute("mainHead", "Approval");
			model.addAttribute("childHead1", "Board Transaction Approval");
			int approve_status=1;
			if(createflagbpa==1){
				model.addAttribute("msg","Application(s) Approved");
				createflagbpa=0;
			}
			if(createflagbpa==2){
				model.addAttribute("msg","Application(s) Rejected");
				createflagbpa=0;
			}
			
			List<?> listApproval=billPenaltyAdjService.getPendingConnectionsToApprove(approve_status,"BOARD");
			
			//List<?> listApproval=billPenaltyCorrectionAdjService.getPendingConnectionsToApprove(approve_status);
			
			model.addAttribute("masterListApproval", listApproval);
			return "cashCounter/boardAdjApprove";	
		}
		
		@RequestMapping(value = "/boardAdjApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
		public String boardAdjApproveStatus(HttpServletRequest request){
			
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			String docNum[]=new String[1];
			
			String billIds=request.getParameter("connectId");
			
			int conStatus=Integer.parseInt(request.getParameter("conStatus"));
			
			BsmartWaterLogger.logger.info(billIds+"billIds");
			if(billIds.contains(","))
			  {
				docNum=billIds.split(",");
			  }
			else
			  {
				docNum[0]=billIds;
				
			  }
	   	
	   	  for(int i=0;i<docNum.length;i++)
	   		{
	   		
	   		  	BsmartWaterLogger.logger.info(docNum[i]);
		   		
	   		  	int approve_status=1;
	   		  	BillPenaltyAdjustment billPenaltyAdjustment=billPenaltyAdjService.getPendingBoardByConNo(docNum[i],approve_status);
	   		  	
	   		  	if(conStatus==1){
	   		  		if(billPenaltyAdjustment!=null){
	   		  			
	   		  			ConsumerMaster consumerMaster=consumerMasterService.findconsumer(docNum[i]);
	   		  			if(consumerMaster!=null){
	   		  			double balance= consumerMaster.getBalance();
	   		  			double newboard=balance+billPenaltyAdjustment.getBoard_adj();
	   		  			//System.out.println("Board balance= "+balance);
	   		  			//System.out.println("New Board balance= "+newboard);
	   		  			consumerMaster.setBalance(newboard);
	   		  			consumerMaster.setRemarks(billPenaltyAdjustment.getRemarks());
	   		  			consumerMasterService.update(consumerMaster);
	   		  			
	   		  			}
	   		  			
	   		  			
	   		  			billPenaltyAdjustment.setApprove_status(3);
	   		  		    billPenaltyAdjustment.setApproved_by1(userName);
	   		  		    billPenaltyAdjustment.setApproved_date1(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}else{
	   		  		
		   		  	if(billPenaltyAdjustment!=null){
	   		  			billPenaltyAdjustment.setApprove_status(2);
	   		  			billPenaltyAdjustment.setApproved_by1(userName);
	   		  		    billPenaltyAdjustment.setApproved_date1(new Date());
	   		  			billPenaltyAdjService.update(billPenaltyAdjustment);
	   		        
	   		  		}
	   		  	}
	   	   }
		   	if(conStatus==1){
				createflagbpa=1;
			}else{
				createflagbpa=2;
			}
	   	
			return "redirect:/boardAdjApprove";
			
		}
}

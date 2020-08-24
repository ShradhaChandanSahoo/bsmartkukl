package com.bcits.bsmartwater.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.ehcache.hibernate.HibernateUtil;
import oracle.sql.BLOB;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.util.IOUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bcits.bsmartwater.dao.BillingLedgerDao;
import com.bcits.bsmartwater.dao.ConsumerMasterDao;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.model.BillApproveEntity;
import com.bcits.bsmartwater.model.BillPenaltyAdjustment;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.ConTypeChangeEntity;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.ConsumerMasterApproval;
import com.bcits.bsmartwater.model.MeterChangeDetailsEntity;
import com.bcits.bsmartwater.model.MeterDetailsEntity;
import com.bcits.bsmartwater.model.MuncipalityDetailsEntity;
import com.bcits.bsmartwater.model.NC_MasterSync;
import com.bcits.bsmartwater.model.OwnerShipChange;
import com.bcits.bsmartwater.model.PaymentEntity;
import com.bcits.bsmartwater.model.SewageChangeEntity;
import com.bcits.bsmartwater.model.SqlEditorExecutedQry;
import com.bcits.bsmartwater.model.TariffRateConversion;
import com.bcits.bsmartwater.model.TariffRateMaster;
import com.bcits.bsmartwater.service.BillPenaltyAdjService;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.service.CloseMonthEndService;
import com.bcits.bsmartwater.service.ConTypeChangeService;
import com.bcits.bsmartwater.service.ConsumerMasterApprovalService;
import com.bcits.bsmartwater.service.ConsumerMasterService;
import com.bcits.bsmartwater.service.MeterChangeDetailsService;
import com.bcits.bsmartwater.service.MeterDetailsService;
import com.bcits.bsmartwater.service.MeterReaderService;
import com.bcits.bsmartwater.service.MuncipalityDetailsService;
import com.bcits.bsmartwater.service.NC_MasterSyncService;
import com.bcits.bsmartwater.service.ObservationMasterService;
import com.bcits.bsmartwater.service.OwnerShipChangeService;
import com.bcits.bsmartwater.service.PaymentService;
import com.bcits.bsmartwater.service.SewageUsedChangeService;
import com.bcits.bsmartwater.service.SqlEditorExecutedQryService;
import com.bcits.bsmartwater.service.TariffRateConversionService;
import com.bcits.bsmartwater.service.TariffRateMasterService;
import com.bcits.bsmartwater.service.WardDetailsService;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;
import com.bcits.bsmartwater.utils.DateConverter;
import com.bcits.bsmartwater.utils.StaticNepaliMonths;

@Controller
public class ConsumerController {
	
	@Autowired
    SewageUsedChangeService sewageUsedChangeService;
	
	@Autowired
	CloseMonthEndService closeMonthEndService;
	
	@Autowired
	private ConsumerMasterService consumerMasterService;
	
	@Autowired
	private ConsumerMasterApprovalService consumerMasterApprovalService;
	
	@Autowired
	private BillingLedgerService billingLedgerService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private MeterDetailsService meterDetailsService;
	
	@Autowired
	private MeterReaderService meterReaderService;
	
	@Autowired
	private MuncipalityDetailsService muncipalityDetailsService;
	
	@Autowired
	private ConsumerMasterDao consumerMasterDao;
	
	@Autowired
	private BillingLedgerDao billingLedgerDao;
	
	@Autowired
	private MeterChangeDetailsService meterChangeDetailsService;
	
	@Autowired
	private WardDetailsService wardDetailsService;
	
	@Autowired
	private ObservationMasterService observationMasterService;

	@Autowired
	private TariffRateConversionService tariffRateConversionService;
	
	@Autowired
	private ConTypeChangeService conTypeChangeService;
	
	@Autowired
	private MasterGenericDAO masterGenericDAO;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	private OwnerShipChangeService ownerShipChangeService;
	
	@Autowired
	private BillPenaltyAdjService billPenaltyAdjService;
	
	@Autowired
	private NC_MasterSyncService nc_MasterSyncService;
	
	@Autowired
	TariffRateMasterService tariffRateMasterService;
	
	@Autowired
	SqlEditorExecutedQryService executedQryService;
	
	int createbillFlag=0;
	int createflagct=0;
	int createflagcta=0;
	int createflagnta=0;
	
	int createflagoct=0;
	int createflagocta=0;
	
	
	Timestamp today=new Timestamp(new java.util.Date().getTime());


	@RequestMapping(value="/consumerMaster",method={RequestMethod.POST,RequestMethod.GET})
	public String consumerMasterIndex(Model model,HttpServletRequest request){
		
		HttpSession session=request.getSession(false);
		String sitecode=(String) session.getAttribute("siteCode");
		
		model.addAttribute("wardList",wardDetailsService.getAllWardRecords());
		model.addAttribute("consumerMaster", new ConsumerMasterApproval());
		model.addAttribute("mtrReaderList",meterReaderService.readMrDetails());
		List<MuncipalityDetailsEntity> municipal = muncipalityDetailsService.getAllMuncipalityRecords();
		model.addAttribute("municipal", municipal);
		long customer_id=consumerMasterService.getMaxConsumerId();
		if(customer_id==0){
			model.addAttribute("customer_id", Integer.parseInt(sitecode)*1000000);
		}else
		{
			model.addAttribute("customer_id", (customer_id+1));
		}
		
		model.addAttribute("mainHead", "Consumer Module");
		model.addAttribute("childHead1", "Consumer Master");
		return "consumerModule/consumerMaster";	
	}
	
	
	@RequestMapping(value="/consumerGroups",method={RequestMethod.POST,RequestMethod.GET})
	public String consumerGroupsIndex(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Customer Module");
		model.addAttribute("childHead1", "Customer Groups");
		return "consumerModule/consumerGroups";	
	}
	
	@RequestMapping(value="/consumerTransfer",method={RequestMethod.POST,RequestMethod.GET})
	public String consumerTransferIndex(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Customer Module");
		model.addAttribute("childHead1", "Customer Transfer");
		return "consumerModule/consumerTransfer";	
	}
	@RequestMapping(value = "/addConsumerMaster", method = {RequestMethod.GET, RequestMethod.POST})
	public String addConsumerMaster(@ModelAttribute("consumerMaster") ConsumerMasterApproval consumerMasterApproval,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  {
		long maxId=consumerMasterService.getMaxConsumerId();
		consumerMasterApproval.setCustomer_id(maxId+1);
		consumerMasterService.saveConsumerMasterDetails(consumerMasterApproval,model,request);
		return "redirect:/consumerMaster";
		
	}
	@RequestMapping(value = "/updateConsumerMaster", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateConsumerMaster(@ModelAttribute("consumerMaster") ConsumerMaster consumerMaster,BindingResult bingingResult,ModelMap model,HttpServletRequest request)  {
		consumerMasterService.updateConsumerMasterDetails(consumerMaster,model,request);
		return "redirect:/consumerMaster";
		
	}
	/*@RequestMapping(value="/getConsumerMasterDetails/{consumerId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ConsumerMasterApproval getConsumerDetail(@PathVariable String consumerId){
		//return consumerMasterService.findByConsumerId(consumerId);
		return consumerMasterService.findByConsumerId(consumerId);
	}*/
	
	@RequestMapping(value="/getConsumerMasterDetails/{consumerId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ConsumerMaster getConsumerDetail(@PathVariable String consumerId){
		//return consumerMasterService.findByConsumerId(consumerId);
		return consumerMasterService.findconsumer(consumerId);
	}
	
	@RequestMapping(value="/getConsumerBoardAdjAmt/{consumerId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getConsumerBoardAdjAmt(@PathVariable String consumerId){
		BillPenaltyAdjustment billPenaltyAdjustment=billPenaltyAdjService.getPendingBoardByConNo(consumerId,0);
		if(billPenaltyAdjustment!=null){
			List<Object> result=new ArrayList<>();
			result.add("PENDING");
			return result;
		} else{
		return billPenaltyAdjService.getPendingBoardByConNo(consumerId,1);
		}
	}
	
	@RequestMapping(value="/getConsumerMasterDetails1/{consumerId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getConsumerMasterDetails1(@PathVariable String consumerId){
		/*HttpSession session=request.getSession(false);
		String sitecode=(String)session.getAttribute("branchcode");	
		return consumerMasterService.findByConnectionNo(consumerId+"@"+sitecode);*/
		return consumerMasterService.findByConnectionNo(consumerId);
	}
	
	@RequestMapping(value="/checkOwnerShipChngPendingStatus/{consumerId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object checkOwnerShipChngPendingStatus(@PathVariable String consumerId){
		OwnerShipChange ownerShipChange= ownerShipChangeService.getRecordByConnectionNo(consumerId,0);
		if(ownerShipChange==null){
			return "Can Add";
		} else{
			return "Pending";
		}
	}
	
	//New Connection Application ID Web Service
	@RequestMapping(value="/getApplicationById/{applicationId}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getApplicationById(@PathVariable long applicationId){
		List<?> obj= consumerMasterService.findByApplicationById(applicationId);
	    return obj;
	
	}
	
	//New Connection Application ID Web Service
		@RequestMapping(value="/getEstimationData/{applicationId}",method={RequestMethod.GET,RequestMethod.POST})
		public @ResponseBody Object getEstimationData(@PathVariable long applicationId,Model model){
			List<?> obj= consumerMasterService.getEstimationData(applicationId);
			Map<String,Object> map=new HashMap<String,Object>();
			if(obj==null || obj.size()==0 || obj.isEmpty())
			{
				//model.addAttribute("paidamount","Sir ,You alreday Paid That Amount");
				map.put("data", obj);
				map.put("data1", "Sir ,You alreday Paid That Amount");

				
			}
		    return map;
		
	}
	
	
	@RequestMapping(value="/approveConsumerDetails",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String approveConsumerDetails(HttpServletRequest request){
		return consumerMasterService.approveConsumerDetails(request);
	}
	
	
	@RequestMapping(value="/sewageApprovalByManager",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String sewageApprvalDetails(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		
		
		String ids=request.getParameter("masterId");
           if(ids!=null&&!ids.isEmpty()){
			
			if(ids.contains(",")){
				
				String []masIds=ids.split(",");
				for(String s:masIds){

				
		    SewageChangeEntity sce=(SewageChangeEntity) sewageUsedChangeService.findSewageChangeEntityByAppId(Integer.parseInt(s));
		
		    String con=sce.getConnectionNO();
			ConsumerMaster cm=consumerMasterService.findconsumer(con);
			cm.setSewage_used(sce.getSewage_Used_New());
			consumerMasterService.update(cm);
			sce.setStatus(1);
			sce.setApproved_by(userName);
			sce.setApproved_date(new Date());
			sewageUsedChangeService.update(sce);
			
				}
			}else {
				 SewageChangeEntity sce=(SewageChangeEntity) sewageUsedChangeService.findSewageChangeEntityByAppId(Integer.parseInt(ids));
					
				    String con=sce.getConnectionNO();
					ConsumerMaster cm=consumerMasterService.findconsumer(con);
					cm.setSewage_used(sce.getSewage_Used_New());
					consumerMasterService.update(cm);
					sce.setStatus(1);
					sce.setApproved_by(userName);
					sce.setApproved_date(new Date());
					sewageUsedChangeService.update(sce);
			}
}
		
		
		
		return "Sewage Used Approved Successfully!";
		
	}
	
	@RequestMapping(value="/rejectSewageUsed",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String rejectSewageUsed(HttpServletRequest request){
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		
		String ids=request.getParameter("masterId");
		
		try {

           if(ids!=null&&!ids.isEmpty()){
			
			if(ids.contains(",")){
				
		   String []masIds=ids.split(",");
		   for(String s:masIds){
		   SewageChangeEntity sce=(SewageChangeEntity) sewageUsedChangeService.findSewageChangeEntityByAppId(Integer.parseInt(s));
			sce.setApproved_by(userName);
			sce.setApproved_date(new Date());
			sce.setStatus(2);
			sewageUsedChangeService.update(sce);
		   }
		  
			}else {
				SewageChangeEntity sce=(SewageChangeEntity) sewageUsedChangeService.findSewageChangeEntityByAppId(Integer.parseInt(ids));
				
			    String con=sce.getConnectionNO();
				ConsumerMaster cm=consumerMasterService.findconsumer(con);
				cm.setSewage_used(sce.getSewage_Used_New());
				consumerMasterService.update(cm);
				sce.setStatus(1);
				sce.setApproved_by(userName);
				sce.setApproved_date(new Date());
				sewageUsedChangeService.update(sce);
			}
}
		}catch(Exception e)	
		{
			e.printStackTrace();
			return null;
			
		}
		
		
		return "Sewage Used Rejected Successfully!";
		
	}
	
	@RequestMapping(value="/getSewageApproveList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getSewageApproveList(){
		return sewageUsedChangeService.getSewageApproveList();
	}
	
	
	@RequestMapping(value="/rejectConsumerDetails",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String rejectConsumerDetails(HttpServletRequest request){
		return consumerMasterService.rejectConsumerDetails(request);
	}
	
	@RequestMapping(value="/consumerHistory",method={RequestMethod.POST,RequestMethod.GET})
	public String consumerHistory(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Customer Module");
		model.addAttribute("childHead1", "Customer History");
		
		return "consumerModule/consumerHistory";
	}
	//UPDATE TABLE
	@RequestMapping(value="/updateTables",method={RequestMethod.POST,RequestMethod.GET})
	public String updateTables(Model model,HttpServletRequest request){	
		
		System.err.println("Gng to update TAbles");
		String obj= consumerMasterService.updateTables();
		return "success";
	}
	
	//UPDATE TABLE
	@RequestMapping(value="/consumerBillingEntry",method={RequestMethod.POST,RequestMethod.GET})
	public String consumerBillingEntry(@ModelAttribute("updateAddConBillEntry")ConsumerMaster consumerMaster,BindingResult bingingResult,Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Customer Module");
		model.addAttribute("childHead1", "Customer Billing Entry");
		
		if(createbillFlag==1){
			model.addAttribute("msg","Ledger Generated Successfully");
			createbillFlag=0;
		}
		if(createbillFlag==2){
			model.addAttribute("msg","Oops! Something went wrong Ledger is not updated Please try again");
			createbillFlag=0;
		}
		
		List<?> observationList=observationMasterService.getAllObservationRecordsBill();
		model.addAttribute("observationList", observationList);
		
		return "consumerModule/consumerBillingEntry";
	}
	
	@RequestMapping(value="/getMasterDataByConnectionNum/{conNum}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMasterDataByConnectionNum(@PathVariable String conNum,HttpServletResponse response,HttpServletRequest request) 
	{
		List<ConsumerMaster> consumerMaster = consumerMasterService.getMasterDataByConnectionNum(conNum);
		return consumerMaster;
	}
	
	
	
	@RequestMapping(value="/getMasterDataByName/{name}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMasterDataByName(@PathVariable String name,HttpServletResponse response,HttpServletRequest request) 
	{
		//System.out.println("name ===> "+name);
		List<ConsumerMaster> consumerMaster = consumerMasterService.getMasterDataByName(name);
		return consumerMaster;
	}
	
	@RequestMapping(value="/getMasterDataByWardNum/{wardNum}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMasterDataByWardNum(@PathVariable String wardNum,HttpServletResponse response,HttpServletRequest request) 
	{
		//System.out.println("wardNum ===> "+wardNum);
		List<ConsumerMaster> consumerMaster = consumerMasterService.getMasterDataByWardNum(wardNum);
		return consumerMaster;
	}
	
	@RequestMapping(value="/getLedgerDataByConnNum/{connId}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getLedgerDataByConnNum(@PathVariable String connId,HttpServletResponse response,HttpServletRequest request) 
	{
		List<BillingLedgerEntity> consumerLedger = billingLedgerService.getLedgerDataByConnectionNum(connId);
		return consumerLedger;
	}
	
	@RequestMapping(value="/getPaymentDataByConnNum/{connId}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getPaymentDataByConnNum(@PathVariable String connId,HttpServletResponse response,HttpServletRequest request) 
	{
		List<?> consumerPayments = paymentService.getPaymentsDataByConnectionNum(connId);
		return consumerPayments;
	}
	
	@RequestMapping(value="/getMeterDataByConnNum/{connId}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMeterDataByConnNum(@PathVariable String connId,HttpServletResponse response,HttpServletRequest request) 
	{
		List<MeterDetailsEntity> meter = meterDetailsService.getMeterDetailsDataByConnectionNum(connId);
		//System.out.println("meter ===> "+meter.size());
		return meter;
	}
	
	@RequestMapping(value="/getMeterChangeDetailsByConnNum/{connId}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMeterChangeDetailsByConnNum(@PathVariable String connId,HttpServletResponse response,HttpServletRequest request) 
	{
		List<MeterChangeDetailsEntity> meterChange = meterChangeDetailsService.getMeterChangeDetailsByConnNum(connId);
		//System.out.println("meter change ===> "+meterChange.size());
		return meterChange;
	}
	
	//Web services Connection Data
	
	@RequestMapping(value="/getMasterDataByConnectionNum/{conNum}/{sitecode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMasterDataByConnectionNum(@PathVariable String conNum,@PathVariable String sitecode,HttpServletResponse response,HttpServletRequest request) 
	{
		List<ConsumerMaster> consumerMaster = consumerMasterService.getMasterDataByConnectionNum(conNum);
		return consumerMaster;
	}
	
	@RequestMapping(value="/getMasterDataByConnectionNumNew/{conNum}/{sitecode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMasterDataByConnectionNumNew(@PathVariable String conNum,@PathVariable String sitecode,HttpServletResponse response,HttpServletRequest request) 
	{
		
		String schemaName="";
		
		if("1110".equalsIgnoreCase(sitecode)){
			schemaName="MAHAN_1110";
		}else if("1119".equalsIgnoreCase(sitecode)){
			schemaName="KIRTIPUR_1119";
		}
		else if("1111".equalsIgnoreCase(sitecode)){
			schemaName="MAHAR_1111";			
					}
		else if("1112".equalsIgnoreCase(sitecode)){
			schemaName="BANESH_1112";
		}
		else if("1113".equalsIgnoreCase(sitecode)){
			schemaName="KAMALADI_1113";
		}
		else if("1114".equalsIgnoreCase(sitecode)){
			schemaName="CHHETRA_1114";
		}
		else if("1115".equalsIgnoreCase(sitecode)){
			schemaName="TRIPUR_1115";
		}
		else if("1116".equalsIgnoreCase(sitecode)){
			schemaName="BHAKTAPUR_1116";
		}
		else if("1117".equalsIgnoreCase(sitecode)){
			schemaName="THIMI_1117";
		}
		else if("1118".equalsIgnoreCase(sitecode)){
			schemaName="LALITPUR_1118";
		}
		
		List<ConsumerMaster> consumerMaster = consumerMasterService.getMasterDataByConnectionNumNew(conNum,schemaName);
		return consumerMaster;
	}
	
	
	
	
	
	
	@RequestMapping(value="/getLedgerDataByConnNum/{connId}/{sitecode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getLedgerDataByConnNum(@PathVariable String connId,@PathVariable String sitecode,HttpServletResponse response,HttpServletRequest request) 
	{
		List<BillingLedgerEntity> consumerLedger = billingLedgerService.getLedgerDataByConnectionNum(connId);
		return consumerLedger;
	}
	
	@RequestMapping(value="/getLedgerDataByConnNumNew/{connId}/{sitecode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getLedgerDataByConnNumNew(@PathVariable String connId,@PathVariable String sitecode,HttpServletResponse response,HttpServletRequest request) 
	{
		
		String schemaName="";
		
		if("1110".equalsIgnoreCase(sitecode)){
			schemaName="MAHAN_1110";
		}else if("1119".equalsIgnoreCase(sitecode)){
			schemaName="KIRTIPUR_1119";
		}
		else if("1111".equalsIgnoreCase(sitecode)){
			schemaName="MAHAR_1111";			
					}
		else if("1112".equalsIgnoreCase(sitecode)){
			schemaName="BANESH_1112";
		}
		else if("1113".equalsIgnoreCase(sitecode)){
			schemaName="KAMALADI_1113";
		}
		else if("1114".equalsIgnoreCase(sitecode)){
			schemaName="CHHETRA_1114";
		}
		else if("1115".equalsIgnoreCase(sitecode)){
			schemaName="TRIPUR_1115";
		}
		else if("1116".equalsIgnoreCase(sitecode)){
			schemaName="BHAKTAPUR_1116";
		}
		else if("1117".equalsIgnoreCase(sitecode)){
			schemaName="THIMI_1117";
		}
		else if("1118".equalsIgnoreCase(sitecode)){
			schemaName="LALITPUR_1118";
		}
		
		
		List<BillingLedgerEntity> consumerLedger = billingLedgerService.getLedgerDataByConnNumNew(connId,schemaName);
		return consumerLedger;
	}
	
	
	@RequestMapping(value="/getPaymentDataByConnNum/{connId}/{sitecode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getPaymentDataByConnNum(@PathVariable String connId,@PathVariable String sitecode,HttpServletResponse response,HttpServletRequest request) 
	{
		List<?> consumerPayments = paymentService.getPaymentsDataByConnectionNum(connId);
		return consumerPayments;
	}
	@RequestMapping(value="/getTransactionDataByConnNum/{connId}/{monthYear}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> getTransactionDataByConnNum(@PathVariable String connId,@PathVariable String monthYear,HttpServletResponse response,HttpServletRequest request) 
	{
		List<?> consumerPayments = paymentService.getTransactionDataByConnNum(connId,Integer.parseInt(monthYear));
		return consumerPayments;
	}
	
	@RequestMapping(value="/getPaymentDataByConnNumNew/{connId}/{sitecode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getPaymentDataByConnNumNew(@PathVariable String connId,@PathVariable String sitecode,HttpServletResponse response,HttpServletRequest request) 
	{
String schemaName="";
		
		if("1110".equalsIgnoreCase(sitecode)){
			schemaName="MAHAN_1110";
		}else if("1119".equalsIgnoreCase(sitecode)){
			schemaName="KIRTIPUR_1119";
		}
		else if("1111".equalsIgnoreCase(sitecode)){
			schemaName="MAHAR_1111";			
					}
		else if("1112".equalsIgnoreCase(sitecode)){
			schemaName="BANESH_1112";
		}
		else if("1113".equalsIgnoreCase(sitecode)){
			schemaName="KAMALADI_1113";
		}
		else if("1114".equalsIgnoreCase(sitecode)){
			schemaName="CHHETRA_1114";
		}
		else if("1115".equalsIgnoreCase(sitecode)){
			schemaName="TRIPUR_1115";
		}
		else if("1116".equalsIgnoreCase(sitecode)){
			schemaName="BHAKTAPUR_1116";
		}
		else if("1117".equalsIgnoreCase(sitecode)){
			schemaName="THIMI_1117";
		}
		else if("1118".equalsIgnoreCase(sitecode)){
			schemaName="LALITPUR_1118";
		}
		
		List<PaymentEntity> consumerPayments = paymentService.getPaymentDataByConnNumNew(connId,schemaName);
		return consumerPayments;
	}
	
	@RequestMapping(value="/getMeterDataByConnNum/{connId}/{sitecode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMeterDataByConnNum(@PathVariable String connId,@PathVariable String sitecode,HttpServletResponse response,HttpServletRequest request) 
	{
		List<MeterDetailsEntity> meter = meterDetailsService.getMeterDetailsDataByConnectionNum(connId);
		//System.out.println("meter ===> "+meter.size());
		return meter;
	}
	
	@RequestMapping(value="/getMeterChangeDetailsByConnNum/{connId}/{sitecode}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMeterChangeDetailsByConnNum(@PathVariable String connId,@PathVariable String sitecode,HttpServletResponse response,HttpServletRequest request) 
	{
		List<MeterChangeDetailsEntity> meterChange = meterChangeDetailsService.getMeterChangeDetailsByConnNum(connId);
		//System.out.println("meter change ===> "+meterChange.size());
		return meterChange;
	}
	@RequestMapping(value="/corporateDashboard1",method={RequestMethod.POST,RequestMethod.GET})
	public String corporateDashboard1(Model model,HttpServletRequest request){
		
		
		List<?>  data=consumerMasterService.getAllSchemaData();
		if(data!=null)
		{
		for (Iterator<?> iterator = data.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			
			
			if("1110".equalsIgnoreCase((String) obj[1].toString()))
			{
				model.addAttribute("MAHAN_1110", obj[0]);
			}else if("1119".equalsIgnoreCase((String) obj[1].toString())){
				model.addAttribute("KIRTIPUR_1119", obj[0]);
			}else if("1111".equalsIgnoreCase((String) obj[1].toString())){
				model.addAttribute("MAHAR_1111", obj[0]);
			}else if("1112".equalsIgnoreCase((String) obj[1].toString())){
				model.addAttribute("BANESH_1112", obj[0]);
			}else if("1113".equalsIgnoreCase((String) obj[1].toString())){
				model.addAttribute("KAMALADI_1113", obj[0]);
			}else if("1114".equalsIgnoreCase((String) obj[1].toString())){
				model.addAttribute("CHHETRA_1114", obj[0]);
			}else if("1115".equalsIgnoreCase((String) obj[1].toString())){
				model.addAttribute("TRIPUR_1115", obj[0]);
			}else if("1116".equalsIgnoreCase((String) obj[1].toString())){
				model.addAttribute("BHAKTAPUR_1116", obj[0]);
			}else if("1117".equalsIgnoreCase((String) obj[1].toString())){
				model.addAttribute("THIMI_1117", obj[0]);
			}else if("1118".equalsIgnoreCase((String) obj[1].toString())){
				model.addAttribute("LALITPUR_1118", obj[0]);
			}
		
		}
		}
		
		double unBilled=0;
		double billed=0;
		
		double unBilled_MAHAN=0;
		double billed_MAHAN=0;
		
		double unBilled_KIRTIPUR=0;
		double billed_KIRTIPUR=0;
		
		double unBilled_MAHAR=0;
		double billed_MAHAR=0;
		
		double unBilled_BANESH=0;
		double billed_BANESH=0;
		
		double unBilled_KAMALADI=0;
		double billed_KAMALADI=0;
		
		double unBilled_CHHETRA=0;
		double billed_CHHETRA=0;
		
		double unBilled_TRIPUR=0;
		double billed_TRIPUR=0;
		
		double unBilled_BHAKTAPUR=0;
		double billed_BHAKTAPUR=0;
		
		double unBilled_THIMI=0;
		double billed_THIMI=0;
		
		double unBilled_LALITPUR=0;
		double billed_LALITPUR=0;
		
		DecimalFormat df=new DecimalFormat("##.##");
		
		List<?> monthlyWiseProgess=consumerMasterService.getBranchWiseData();
		
	     for (Iterator<?> iterator = monthlyWiseProgess.iterator(); iterator.hasNext();) {
			
	    	 Object[] obj = (Object[]) iterator.next();
			unBilled=unBilled+Integer.parseInt(String.valueOf(obj[2]));
			billed=billed+Integer.parseInt(String.valueOf(obj[3]));
			BsmartWaterLogger.logger.info("unbillid obj value"+Integer.parseInt(String.valueOf(obj[2])));
			BsmartWaterLogger.logger.info("billid obj value"+Integer.parseInt(String.valueOf(obj[3])));
			
			if("1110".equalsIgnoreCase((String) obj[0].toString()))
			{
				unBilled_MAHAN	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_MAHAN	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_MAHAN", df.format(unBilled_MAHAN));
				model.addAttribute("billed_MAHAN",df.format(billed_MAHAN));
			}else if("1119".equalsIgnoreCase((String) obj[0].toString())){
				
				unBilled_KIRTIPUR	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_KIRTIPUR	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_KIRTIPUR",df.format( unBilled_KIRTIPUR));
				model.addAttribute("billed_KIRTIPUR",df.format(billed_KIRTIPUR));
				
			}else if("1111".equalsIgnoreCase((String) obj[0].toString())){

				unBilled_MAHAR	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_MAHAR	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_MAHAR", df.format(unBilled_MAHAR));
				model.addAttribute("billed_MAHAR",df.format(billed_MAHAR));
				
			}else if("1112".equalsIgnoreCase((String) obj[0].toString())){
				
				unBilled_BANESH	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_BANESH	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_BANESH", df.format(unBilled_BANESH));
				model.addAttribute("billed_BANESH",df.format(billed_BANESH));
				
			}else if("1113".equalsIgnoreCase((String) obj[0].toString())){
				
				unBilled_KAMALADI	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_KAMALADI	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_KAMALADI", df.format(unBilled_KAMALADI));
				model.addAttribute("billed_KAMALADI",df.format(billed_KAMALADI));
				
			}else if("1114".equalsIgnoreCase((String) obj[0].toString())){

				unBilled_CHHETRA	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_CHHETRA	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_CHHETRA", df.format(unBilled_CHHETRA));
				model.addAttribute("billed_CHHETRA",df.format(billed_CHHETRA));
				
			}else if("1115".equalsIgnoreCase((String) obj[0].toString())){
				
				unBilled_TRIPUR	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_TRIPUR	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_TRIPUR", df.format(unBilled_TRIPUR));
				model.addAttribute("billed_TRIPUR",df.format(billed_TRIPUR));
				
			}else if("1116".equalsIgnoreCase((String) obj[0].toString())){

				unBilled_BHAKTAPUR	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_BHAKTAPUR	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_BHAKTAPUR", df.format(unBilled_BHAKTAPUR));
				model.addAttribute("billed_BHAKTAPUR",df.format(billed_BHAKTAPUR));
				
			}else if("1117".equalsIgnoreCase((String) obj[0].toString())){
				
				unBilled_THIMI	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_THIMI	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_THIMI", df.format(unBilled_THIMI));
				model.addAttribute("billed_THIMI",df.format(billed_THIMI));
				
			}else if("1118".equalsIgnoreCase((String) obj[0].toString())){
				
				unBilled_LALITPUR	=(Double.parseDouble(String.valueOf(obj[2]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				billed_LALITPUR	=(Double.parseDouble(String.valueOf(obj[3]))/(Double.parseDouble(String.valueOf(obj[2]))+Double.parseDouble(String.valueOf(obj[3]))))*100;
				
				model.addAttribute("unBilled_LALITPUR", df.format(unBilled_LALITPUR));
				model.addAttribute("billed_LALITPUR",df.format(billed_LALITPUR));
				
			}
			
			
		}
	 	
	     BsmartWaterLogger.logger.info("unBilled_MAHAN========================"+df.format(unBilled_MAHAN));
	     BsmartWaterLogger.logger.info("billed_MAHAN========================"+df.format(billed_MAHAN));
	     BsmartWaterLogger.logger.info("unBilled_KIRTIPUR======================"+df.format(billed_KIRTIPUR));
	     BsmartWaterLogger.logger.info("unBilled_MAHAR========================"+df.format(unBilled_MAHAR));
	     BsmartWaterLogger.logger.info("billed_MAHAR============================="+df.format(unBilled_KIRTIPUR));
	     BsmartWaterLogger.logger.info("billed_KIRTIPUR====================="+df.format(billed_MAHAR));
	     BsmartWaterLogger.logger.info("unBilled_BANESH========================"+df.format(unBilled_BANESH));
	     BsmartWaterLogger.logger.info("billed_BANESH========================"+df.format(billed_BANESH));
	     BsmartWaterLogger.logger.info("unBilled_KAMALADI========================"+df.format(unBilled_KAMALADI));
	     BsmartWaterLogger.logger.info("billed_KAMALADI========================"+df.format(billed_KAMALADI));
	     BsmartWaterLogger.logger.info("unBilled_CHHETRA========================"+df.format(unBilled_CHHETRA));
	     BsmartWaterLogger.logger.info("billed_CHHETRA========================"+df.format(billed_CHHETRA));
	     BsmartWaterLogger.logger.info("unBilled_TRIPUR========================"+df.format(unBilled_TRIPUR));
	     BsmartWaterLogger.logger.info("billed_TRIPUR========================"+df.format(billed_TRIPUR));
	     BsmartWaterLogger.logger.info("unBilled_BHAKTAPUR========================"+df.format(unBilled_BHAKTAPUR));
	     BsmartWaterLogger.logger.info("billed_BHAKTAPUR========================"+df.format(billed_BHAKTAPUR));
	     BsmartWaterLogger.logger.info("unBilled_THIMI========================"+df.format(unBilled_THIMI));
	     BsmartWaterLogger.logger.info("billed_THIMI========================"+df.format(billed_THIMI));
	     BsmartWaterLogger.logger.info("unBilled_LALITPUR========================"+df.format(unBilled_LALITPUR));
	     BsmartWaterLogger.logger.info("billed_LALITPUR========================"+df.format(billed_LALITPUR));
	     
	     double monthlyWiseProgessValue=((billed-unBilled)/(billed+unBilled))*100;
	     model.addAttribute("monthlyWiseProgessValue",df.format(monthlyWiseProgessValue));
		
		return "corporateDashboard";	
	}
	
	@RequestMapping(value="/getDataforHighCharts",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getDataforHighCharts(ModelMap model,HttpServletRequest request)
	{
		Double totalRevenue=0.0;
		 List<?>  monthwisecollection=consumerMasterService.getMonthWisecCollection();
		
		 Map<String,Object> map=new HashMap<String, Object>();
		
	
		 
		 
		
		 if(monthwisecollection!=null)
		 {
			 for (Iterator<?> iterator = monthwisecollection.iterator(); iterator.hasNext();) 
			 {
				Object[] obj = (Object[]) iterator.next();
				totalRevenue=totalRevenue+Double.parseDouble((String.valueOf (obj[0])));
			}
		 }
		
		 map.put("listvalues", monthwisecollection);
		 map.put("totalRevenue", totalRevenue);
		 
		 return map;
	}
	
	@RequestMapping(value="/getWardNoById/{ward_no1}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getWardNoById(@PathVariable String ward_no1,HttpServletResponse response,HttpServletRequest request) 
	{
		String wardNo = wardDetailsService.getWardNoById(ward_no1); 
		return  wardNo;
	}
	
	@RequestMapping(value="/getMasterDataByAreaNo/{areaNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMasterDataByAreaNo(@PathVariable String areaNo,HttpServletResponse response,HttpServletRequest request) 
	{
		//System.out.println("areaNo ===> "+areaNo);
		List<ConsumerMaster> consumerMaster = consumerMasterService.getMasterDataByAreaNo(areaNo);
		return consumerMaster;
	}
	
	@RequestMapping(value="/getMasterDataByPhoneNum/{phoneNum}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMasterDataByPhoneNum(@PathVariable String phoneNum,HttpServletResponse response,HttpServletRequest request) 
	{
		//System.out.println("phoneNum ===> "+phoneNum);
		List<ConsumerMaster> consumerMaster = consumerMasterService.getMasterDataByPhoneNum(phoneNum);
		//System.out.println(consumerMaster.size());
		return consumerMaster;
	}
	
	@RequestMapping(value="/getMasterDataByOldConnectionNum/{oldConNum}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMasterDataByOldConnectionNum(@PathVariable String oldConNum,HttpServletResponse response,HttpServletRequest request) 
	{
		//System.out.println("oldConNum ===> "+oldConNum);
		List<ConsumerMaster> consumerMaster = consumerMasterService.getMasterDataByOldConnNum(oldConNum);
		//System.out.println(consumerMaster.size());
		return consumerMaster;
	}
	
	
	@RequestMapping(value="/consumer/connectionDetails/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody List<?> connectionDetails(@PathVariable String connectionNo,HttpServletRequest request)
	{
		List<List<?>> result=new ArrayList<>();
		List<Object> result1=new ArrayList<>();
		List<ConsumerMaster> str=consumerMasterDao.findByConnNo(connectionNo);
		String monthyearnep="207303";
		BillingLedgerEntity billLedgerData=billingLedgerService.findByConnectionNoByMYN(connectionNo,monthyearnep);
		result.add(str);
		if(billLedgerData!=null){
		result1.add(billLedgerData);
		}else{
		result1.add(null);
		}
		result.add(result1);
		
		return result;
	}
	@RequestMapping(value="/updateAddConBillEntry",method={RequestMethod.POST,RequestMethod.GET})
	public String updateAddConBillEntry(@ModelAttribute("updateAddConBillEntry")ConsumerMaster consumerMaster,BindingResult bingingResult,Model model,HttpServletRequest request){	
		
		
		try{
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
		
		String conneNo=request.getParameter("connection_no");
		String tot_tamt=request.getParameter("tot_tamt");
		String meterHired=request.getParameter("meterHired");
		String sewage_used=request.getParameter("sewage_used");
		String con_type=request.getParameter("con_type");
		String pipe_size=request.getParameter("pipe_size");
		String address_eng=request.getParameter("address_eng");
		
		String balance=request.getParameter("balance");

		String previous_reading=request.getParameter("previous_reading");
		String present_reading=request.getParameter("present_reading");
		String consumption=request.getParameter("consumption");
		String mc_status=request.getParameter("mc_status");
		
		
		String previous_reading2=request.getParameter("previous_reading2");
		String present_reading2=request.getParameter("present_reading2");
		String consumption2=request.getParameter("consumption2");
		String mc_status2=request.getParameter("mc_status2");
		
		
		String previous_reading3c=request.getParameter("previous_reading3c");
		String present_reading3c=request.getParameter("present_reading3c");
		String consumption3c=request.getParameter("consumption3c");
		String mc_status3c=request.getParameter("mc_status3c");
		
		String previous_reading3cb=request.getParameter("previous_reading3cb");
		String present_reading3cb=request.getParameter("present_reading3cb");
		String consumption3cb=request.getParameter("consumption3cb");
		String mc_status3cb=request.getParameter("mc_status3cb");
		
		//System.out.println(previous_reading+"previous_reading");
		//System.out.println(present_reading+"present_reading");
		//System.out.println(consumption+"consumption");
		
		String bhaarr=request.getParameter("bhaarr");
		String asharr=request.getParameter("asharr");
		String kararr=request.getParameter("kararr");
		String mangarr=request.getParameter("mangarr");
		String pouarr=request.getParameter("pouarr");
		String magarr=request.getParameter("magarr");
		String magarrf=request.getParameter("magarrf");
		String magarrfc=request.getParameter("magarrfc");
		String magarrfcb=request.getParameter("magarrfcb");
		
		
		
		String aub=request.getParameter("aub");
		double arrears=0.0;
		if(aub!=null && !aub.equals("")){
			arrears=Double.parseDouble(aub);
		}

		
		
		
		ConsumerMaster customer=consumerMasterService.findconsumer(conneNo);
		if(customer!=null){
			
			customer.setSewage_used(sewage_used);
			customer.setMeterHired(meterHired);
			customer.setCon_type(con_type);
			customer.setAddress_eng(address_eng);
			
			if("".equalsIgnoreCase(pipe_size) || "null".equalsIgnoreCase(pipe_size) || pipe_size==null){
				
			}else{
				customer.setPipe_size(Double.parseDouble(pipe_size));
				
				if(Double.parseDouble(pipe_size)==0.5){
					customer.setMeterRentCharges(5.0);
				}else if(Double.parseDouble(pipe_size)==0.75){
					customer.setMeterRentCharges(17.0);
				}
				else if(Double.parseDouble(pipe_size)==1){
					customer.setMeterRentCharges(33.0);
				}
				else if(Double.parseDouble(pipe_size)==1.5){
					customer.setMeterRentCharges(67.0);
				}
				else if(Double.parseDouble(pipe_size)==2){
					customer.setMeterRentCharges(133.0);
				}
				else if(Double.parseDouble(pipe_size)==3){
					customer.setMeterRentCharges(250.0);
				}
				else if(Double.parseDouble(pipe_size)==4){
					customer.setMeterRentCharges(500.0);
				}
				
			}
			
			if(balance!=null && !balance.equals("")){
				customer.setBalance(Double.parseDouble(balance));
			}
			
			consumerMasterService.update(customer);
		}
		
	double nextArrears=0;
	for(int i=1;i<=11;i++){
		String my1=request.getParameter("monYearNep"+i);	
		
		Double wtr1=Double.parseDouble(request.getParameter("wtr_charges"+i));
		Double swr1=Double.parseDouble(request.getParameter("swr_charges"+i));
		Double mtr1=Double.parseDouble(request.getParameter("mtr_rent"+i));
		Double tamt1=Double.parseDouble(request.getParameter("tot_amt"+i));
		Double pen1=Double.parseDouble(request.getParameter("penalty"+i));
		Double reb1=Double.parseDouble(request.getParameter("rebate"+i));
		
		BsmartWaterLogger.logger.info("==="+wtr1+"==="+swr1+"==="+mtr1+"==="+tamt1+"=="+pen1+"==="+reb1);
		
		BillingLedgerEntity billingLedgerEntity=billingLedgerDao.getBillByConNoAndMY1(conneNo,my1);
		
		
		if(billingLedgerEntity!=null){
			
		if("207303".equalsIgnoreCase(billingLedgerEntity.getMonthyearnep())){
			
			billingLedgerEntity.setWater_charges(arrears);
			billingLedgerEntity.setSw_charges(swr1);
			billingLedgerEntity.setMtr_rent(mtr1);
			billingLedgerEntity.setNet_amount(arrears+swr1+mtr1);
			nextArrears=arrears+swr1+mtr1;
			billingLedgerEntity.setPenalty(0.0);
			billingLedgerEntity.setRebate(0.0);
			billingLedgerEntity.setArrears(0.0);
			billingLedgerEntity.setBillno(my1+""+billingLedgerEntity.getId());
			billingLedgerEntity.setUpdated_by(userName);
			billingLedgerEntity.setCreated_date(today);
			
			if(billingLedgerEntity.getNet_amount()>0){
				billingLedgerEntity.setReceipt_no(null);
			}
			billingLedgerDao.customupdate(billingLedgerEntity);
			
		}else{
			
			
			billingLedgerEntity.setWater_charges(wtr1);
			billingLedgerEntity.setSw_charges(swr1);
			billingLedgerEntity.setMtr_rent(mtr1);
			billingLedgerEntity.setArrears(nextArrears);
			billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+nextArrears);
			billingLedgerEntity.setPenalty(0.0);
			billingLedgerEntity.setRebate(0.0);
			billingLedgerEntity.setBillno(my1+""+billingLedgerEntity.getId());
			nextArrears=nextArrears+wtr1+swr1+mtr1;
			
			if("207305".equalsIgnoreCase(my1)){
				if(Double.parseDouble(bhaarr)>0 || Double.parseDouble(bhaarr)<0){
					billingLedgerEntity.setArrears(Double.parseDouble(bhaarr));
					billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+Double.parseDouble(bhaarr));
					nextArrears=Double.parseDouble(bhaarr)+wtr1+swr1+mtr1;
				}
				
			}else if("207306".equalsIgnoreCase(my1)){
				if(Double.parseDouble(asharr)>0 || Double.parseDouble(asharr)<0){
					billingLedgerEntity.setArrears(Double.parseDouble(asharr));
					billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+Double.parseDouble(asharr));
					nextArrears=Double.parseDouble(asharr)+wtr1+swr1+mtr1;
				}
			}else if("207307".equalsIgnoreCase(my1)){
				if(Double.parseDouble(kararr)>0 || Double.parseDouble(kararr)<0){
					billingLedgerEntity.setArrears(Double.parseDouble(kararr));
					billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+Double.parseDouble(kararr));
					nextArrears=Double.parseDouble(kararr)+wtr1+swr1+mtr1;
				}
			}else if("207308".equalsIgnoreCase(my1)){
				if(Double.parseDouble(mangarr)>0 || Double.parseDouble(mangarr)<0){
					billingLedgerEntity.setArrears(Double.parseDouble(mangarr));
					billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+Double.parseDouble(mangarr));
					nextArrears=Double.parseDouble(mangarr)+wtr1+swr1+mtr1;
				}
			}else if("207309".equalsIgnoreCase(my1)){
				if(Double.parseDouble(pouarr)>0 || Double.parseDouble(pouarr)<0){
					billingLedgerEntity.setArrears(Double.parseDouble(pouarr));
					billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+Double.parseDouble(pouarr));
					nextArrears=Double.parseDouble(pouarr)+wtr1+swr1+mtr1;
				}
			}
			else if("207310".equalsIgnoreCase(my1)){
				if(Double.parseDouble(magarr)>0 || Double.parseDouble(magarr)<0){
					billingLedgerEntity.setArrears(Double.parseDouble(magarr));
					billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+Double.parseDouble(magarr));
					nextArrears=Double.parseDouble(magarr)+wtr1+swr1+mtr1;
				}
			}
			else if("207311".equalsIgnoreCase(my1)){
				if(Double.parseDouble(magarrf)>0 || Double.parseDouble(magarrf)<0){
					billingLedgerEntity.setArrears(Double.parseDouble(magarrf));
					billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+Double.parseDouble(magarrf));
					nextArrears=Double.parseDouble(magarrf)+wtr1+swr1+mtr1;
				}
			}
			else if("207312".equalsIgnoreCase(my1)){
				if(Double.parseDouble(magarrfc)>0 || Double.parseDouble(magarrfc)<0){
					billingLedgerEntity.setArrears(Double.parseDouble(magarrfc));
					billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+Double.parseDouble(magarrfc));
					nextArrears=Double.parseDouble(magarrfc)+wtr1+swr1+mtr1;
				}
			}
			
			else if("207401".equalsIgnoreCase(my1)){
				if(Double.parseDouble(magarrfcb)>0 || Double.parseDouble(magarrfcb)<0){
					billingLedgerEntity.setArrears(Double.parseDouble(magarrfcb));
					billingLedgerEntity.setNet_amount((wtr1+swr1+mtr1)+Double.parseDouble(magarrfcb));
					nextArrears=Double.parseDouble(magarrfcb)+wtr1+swr1+mtr1;
				}
			}
			
			
			
			
			
			
			billingLedgerEntity.setUpdated_by(userName);
			billingLedgerEntity.setCreated_date(today);
			
			if("207310".equalsIgnoreCase(my1)){
				
				if("".equalsIgnoreCase(present_reading) || "null".equalsIgnoreCase(present_reading) || present_reading==null || 
						"".equalsIgnoreCase(previous_reading) || "null".equalsIgnoreCase(previous_reading) || previous_reading==null){
					
				}else{
					
					if(Double.parseDouble(previous_reading)>=0 && Double.parseDouble(present_reading)>0 ){
						billingLedgerEntity.setPrevious_reading(Double.parseDouble(previous_reading));
						billingLedgerEntity.setPresent_reading(Double.parseDouble(present_reading));
						billingLedgerEntity.setConsumption(Double.parseDouble(consumption));
						billingLedgerEntity.setMc_status(16);
					}else{
						billingLedgerEntity.setPrevious_reading(0.0);
						billingLedgerEntity.setPresent_reading(0.0);
						billingLedgerEntity.setConsumption(0.0);
						
						if("".equalsIgnoreCase(mc_status) || "null".equalsIgnoreCase(mc_status) || mc_status==null){
							billingLedgerEntity.setMc_status(null);
						}else{
							billingLedgerEntity.setMc_status(Integer.parseInt(mc_status));
						}
						
					}
				}
				
				
			}
			
			
			if("207311".equalsIgnoreCase(my1)){
				
				if("".equalsIgnoreCase(present_reading2) || "null".equalsIgnoreCase(present_reading2) || present_reading2==null || 
						"".equalsIgnoreCase(previous_reading2) || "null".equalsIgnoreCase(previous_reading2) || previous_reading2==null){
					
				}else{
					
					if(Double.parseDouble(previous_reading2)>=0 && Double.parseDouble(present_reading2)>0 ){
						billingLedgerEntity.setPrevious_reading(Double.parseDouble(previous_reading2));
						billingLedgerEntity.setPresent_reading(Double.parseDouble(present_reading2));
						billingLedgerEntity.setConsumption(Double.parseDouble(consumption2));
						billingLedgerEntity.setMc_status(16);
					}else{
						billingLedgerEntity.setPrevious_reading(0.0);
						billingLedgerEntity.setPresent_reading(0.0);
						billingLedgerEntity.setConsumption(0.0);
						
						if("".equalsIgnoreCase(mc_status2) || "null".equalsIgnoreCase(mc_status2) || mc_status2==null){
							billingLedgerEntity.setMc_status(null);
						}else{
							billingLedgerEntity.setMc_status(Integer.parseInt(mc_status2));
						}
						
					}
				}
				
				
			}
			
			
			
			if("207312".equalsIgnoreCase(my1)){
				
				if("".equalsIgnoreCase(present_reading3c) || "null".equalsIgnoreCase(present_reading3c) || present_reading3c==null || 
						"".equalsIgnoreCase(previous_reading3c) || "null".equalsIgnoreCase(previous_reading3c) || previous_reading3c==null){
					
				}else{
					
					if(Double.parseDouble(previous_reading3c)>=0 && Double.parseDouble(present_reading3c)>0 ){
						billingLedgerEntity.setPrevious_reading(Double.parseDouble(previous_reading3c));
						billingLedgerEntity.setPresent_reading(Double.parseDouble(present_reading3c));
						billingLedgerEntity.setConsumption(Double.parseDouble(consumption3c));
						billingLedgerEntity.setMc_status(16);
					}else{
						billingLedgerEntity.setPrevious_reading(0.0);
						billingLedgerEntity.setPresent_reading(0.0);
						billingLedgerEntity.setConsumption(0.0);
						
						if("".equalsIgnoreCase(mc_status3c) || "null".equalsIgnoreCase(mc_status3c) || mc_status3c==null){
							billingLedgerEntity.setMc_status(null);
						}else{
							billingLedgerEntity.setMc_status(Integer.parseInt(mc_status3c));
						}
						
					}
				}
				
				
			}
			
			
			if("207401".equalsIgnoreCase(my1)){
				
				if("".equalsIgnoreCase(present_reading3cb) || "null".equalsIgnoreCase(present_reading3cb) || present_reading3cb==null || 
						"".equalsIgnoreCase(previous_reading3cb) || "null".equalsIgnoreCase(previous_reading3cb) || previous_reading3cb==null){
					
				}else{
					
					if(Double.parseDouble(previous_reading3cb)>=0 && Double.parseDouble(present_reading3cb)>0 ){
						billingLedgerEntity.setPrevious_reading(Double.parseDouble(previous_reading3cb));
						billingLedgerEntity.setPresent_reading(Double.parseDouble(present_reading3cb));
						billingLedgerEntity.setConsumption(Double.parseDouble(consumption3cb));
						billingLedgerEntity.setMc_status(16);
					}else{
						billingLedgerEntity.setPrevious_reading(0.0);
						billingLedgerEntity.setPresent_reading(0.0);
						billingLedgerEntity.setConsumption(0.0);
						
						if("".equalsIgnoreCase(mc_status3cb) || "null".equalsIgnoreCase(mc_status3cb) || mc_status3cb==null){
							billingLedgerEntity.setMc_status(null);
						}else{
							billingLedgerEntity.setMc_status(Integer.parseInt(mc_status3cb));
						}
						
					}
				}
				
				
			}

			if(billingLedgerEntity.getNet_amount()>0){
				billingLedgerEntity.setReceipt_no(null);
			}
			billingLedgerDao.customupdate(billingLedgerEntity);
			
		}
	}
}
		createbillFlag=1;
		}catch(Exception e){
			createbillFlag=2;
			e.printStackTrace();
		}
		return "redirect:/consumerBillingEntry";
	}
	
	
	
	
	@RequestMapping(value="/billing/checkLedgerReceiptExists/{connectionno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkLedgerReceiptExists(@PathVariable String connectionno,HttpServletRequest request){
		try{
			long count=billingLedgerService.checkLedgerReceiptExists(connectionno);
			if(count>0){
				return "Yes";
			}else{
				return "No";
			     }
			
		}catch(Exception e){
			
		}
		return null;
		
	}
	
	@RequestMapping(value="/billing/checkLedgerLatestExists/{connectionno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String checkLedgerLatestExists(@PathVariable String connectionno,HttpServletRequest request){
		try{
			long count=billingLedgerService.checkLedgerLatestExists(connectionno);
			if(count>0){
				return "Yes";
			}else{
				return "No";
			}
			
		}catch(Exception e){
			
		}
		return null;
		
	}
	
	@RequestMapping(value="/tariffRateConversion",method={RequestMethod.POST,RequestMethod.GET})
	public String tariffRateConversion(@ModelAttribute("tariffRateConversionEntity")TariffRateConversion tariffRateConversion, ModelMap model){
	
		List<TariffRateConversion> tariffDetails=tariffRateConversionService.getTariffDetails();
		model.addAttribute("tariffDetails",tariffDetails );
		return "consumerModule/tariffRateConversion";
		
	}
	@RequestMapping(value="/addtariffRateConversion",method={RequestMethod.POST,RequestMethod.GET})
	public String addtariffRateConversion(@ModelAttribute("tariffRateConversionEntity")TariffRateConversion tariffRateConversion,HttpServletRequest request,ModelMap model){
		
		tariffRateConversionService.save(tariffRateConversion);
		List<TariffRateConversion> tariffDetails=tariffRateConversionService.getTariffDetails();
		model.addAttribute("tariffDetails",tariffDetails );
        model.addAttribute("tariffRateConversionEntity", new TariffRateConversion());
		return "consumerModule/tariffRateConversion";
		
	}
	@RequestMapping(value="/editTariffDetails/{id}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object addtariffRateConversion(@PathVariable int id,HttpServletRequest request,ModelMap model){
		
		return tariffRateConversionService.find(id);
		
	}
	@RequestMapping(value="/updateTariffDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String updateTariffDetails(@ModelAttribute("tariffRateConversionEntity")TariffRateConversion tariffRateConversion,HttpServletRequest request,ModelMap model){
		
		 tariffRateConversionService.update(tariffRateConversion);
		 return "redirect:/tariffRateConversion";
		
	}

	@RequestMapping(value="/conTypeChangeReq",method={RequestMethod.POST,RequestMethod.GET})
	public String conTypeChangeReq(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Customer Module");
		model.addAttribute("childHead1", "Connection Type Change");
		
		model.addAttribute("conTypeChangeEntity", new ConTypeChangeEntity());
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> appIdMap = null;
		List<Map<String, Object>> readingResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> readingMap = null;
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
			if(readingDay<=0)
			{
				continue;
			}
			 readingMap = new HashMap<String, Object>();
			 readingMap.put("readingDay", readingDay);
			 readingResult.add(readingMap);
		}
		//model.addAttribute("wardNoList", result);
		model.addAttribute("wardNoList",wardDetailsService.getAllWardRecords());
		model.addAttribute("readingDayList", readingResult);
		
		if(createflagct==1){
			model.addAttribute("msg","Application Sent for Approval");
			createflagct=0;
		}
		if(createflagct==2){
			model.addAttribute("msg","Oops! Something went wrong Please try again");
			createflagct=0;
		}
		if(createflagct==3){
			model.addAttribute("msg","This connection no Already Pending for approval! ");
			createflagct=0;
		}
		return "consumerModule/conTypeChangeReq";	
	}
	
	@RequestMapping(value = "/createConTypeApprove", method = {RequestMethod.GET, RequestMethod.POST})
	public String createConTypeApprove(@ModelAttribute("conTypeChangeEntity")ConTypeChangeEntity conTypeChangeEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request){
		
		try{
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			String new_con_type=request.getParameter("new_con_type");
			String new_con_status=request.getParameter("new_con_status");
			String new_pipe_size=request.getParameter("new_pipe_size");
			//int new_pipe_size=Integer.parseInt(new_pipe_size1);
			conTypeChangeEntity.setConnection_no(conTypeChangeEntity.getConnection_no().trim());
			conTypeChangeEntity.setNew_con_type(new_con_type);
			conTypeChangeEntity.setNew_con_status(new_con_status);
			conTypeChangeEntity.setNew_pipe_size(new_pipe_size);
			String con_no=conTypeChangeEntity.getConnection_no();
			
			ConTypeChangeEntity existConnection=conTypeChangeService.getConTypeByConNo(con_no, 0);
			if (existConnection == null) {
				//System.out.println("New con type--" + conTypeChangeEntity.getNew_con_type());
				//System.out.println("New con status--" + conTypeChangeEntity.getNew_con_status());
				//System.out.println("New pipe size--" + conTypeChangeEntity.getNew_pipe_size());
				conTypeChangeEntity.setSubmit_by(userName);
				conTypeChangeEntity.setSubmit_date_eng(today);
				String rdate = new SimpleDateFormat("dd-MM-yyyy").format(today);
				String english[] = rdate.split("-");
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
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				BsmartWaterLogger.logger.info(cday + "cday" + cmonth + "cmonth" + cyear);
				String nepalidate = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);

				String[] dateArray1 = conTypeChangeEntity.getEfected_date_nep().split("-"); // yyyy-mm-dd
				Date eff_date_eng = dateConverter.convertBsToAd(dateArray1[2] + dateArray1[1] + dateArray1[0]);
				conTypeChangeEntity.setEfected_date_eng(eff_date_eng);

				conTypeChangeEntity.setSubmit_date_nep(nepalidate);
				conTypeChangeEntity.setApprove_status(0);

				conTypeChangeService.save(conTypeChangeEntity);
				createflagct = 1;
				return "redirect:/conTypeChangeReq";
			} else {
				createflagct = 3;
				return "redirect:/conTypeChangeReq";
			}
		}catch(Exception e){
			e.printStackTrace();
			createflagct=2;
			return "redirect:/conTypeChangeReq";

		}
		
		
		
	}
	
	
	@RequestMapping(value="/conTypeChangeApprove",method={RequestMethod.POST,RequestMethod.GET})
	public String conTypeChangeApprove(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Customer Module");
		model.addAttribute("childHead1", "Connection Type Change");
		int approve_status=0;
		if(createflagcta==1){
			model.addAttribute("msg","Application(s) Approved");
			createflagcta=0;
		}
		if(createflagcta==2){
			model.addAttribute("msg","Application(s) Rejected");
			createflagcta=0;
		}
		List<?> listApproval=conTypeChangeService.getPendingConnectionsToApprove(approve_status);
		model.addAttribute("masterListApproval", listApproval);
		return "consumerModule/conTypeChangeApproval";	
	}
	
	
	
	@RequestMapping(value = "/conTypeApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String conTypeApproveStatus(HttpServletRequest request){
		
		
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		String rdate=new SimpleDateFormat("dd-MM-yyyy").format(today);
		String english[]=rdate.split("-");
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
		BsmartWaterLogger.logger.info(cday+"cday"+cmonth+"cmonth"+cyear);
		String nepalidate=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
		
		
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
		  	ConTypeChangeEntity conTypeChangeEntity=conTypeChangeService.getConTypeByConNo(docNum[i].replaceAll("\\s", ""),approve_status);
   		  	
   		  	if(conStatus==1){
   		  		
   		  		if(conTypeChangeEntity!=null){
	   		  		conTypeChangeEntity.setApprove_status(1);
	   		  	    conTypeChangeEntity.setApproved_by(userName);
	   		        conTypeChangeEntity.setApproved_date_eng(today);
	   		        conTypeChangeEntity.setApproved_date_nep(nepalidate);
	   		        conTypeChangeService.update(conTypeChangeEntity);
   		        
   		  		}
   		        ConsumerMaster consumerMaster=consumerMasterService.findconsumer(docNum[i]);
   		        
   		        if(consumerMaster!=null){
   		        	if(conTypeChangeEntity.getNew_area_no()!=null){
   		        		String areaNo=conTypeChangeEntity.getNew_area_no();
   		        		String [] all=areaNo.split("-");
   		        		try{
   		        			if(all.length==3){
   	   		        			consumerMaster.setWard_no(all[0]);
   	   		        			consumerMaster.setReading_day(Integer.parseInt(all[1]));
   	   		        			consumerMaster.setSeq_no(all[2]);
   	   		        		} 
   		        		} catch(Exception e){
   		        			e.printStackTrace();
   		        		}
   		        		consumerMaster.setArea_no(conTypeChangeEntity.getNew_area_no());
   		        		
   		        		
   		        	}
	   		        consumerMaster.setCon_type(conTypeChangeEntity.getNew_con_type());
	   		        consumerMaster.setCon_satatus(conTypeChangeEntity.getNew_con_status());
	   		        consumerMaster.setPipe_size(Double.parseDouble(conTypeChangeEntity.getNew_pipe_size()));
	   		        consumerMasterService.update(consumerMaster);
   		        }
   		        
   		        
   		  	}else{
   		  		
   		  	   if(conTypeChangeEntity!=null){
   		  		conTypeChangeEntity.setApprove_status(2);
   		  	    conTypeChangeEntity.setApproved_by(userName);
   		        conTypeChangeEntity.setApproved_date_eng(today);
   		        conTypeChangeEntity.setApproved_date_nep(nepalidate);
   		        conTypeChangeService.update(conTypeChangeEntity);
		        
		  		}
   		  	}
   	   }
		
	   	if(conStatus==1){
			createflagcta=1;
		}else{
			createflagcta=2;
		}
   	
		return "redirect:/conTypeChangeApprove";
		
	}
	
	@RequestMapping(value="/getPendingConnectionsToApprove",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getPendingConnectionsToApprove(){
		int approve_status=0;
		return conTypeChangeService.getPendingConnectionsToApprove(approve_status);
	}
	
	
	

	@RequestMapping(value="/newConnectionApproval",method={RequestMethod.POST,RequestMethod.GET})
	public String newConnectionApproval(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Customer Module");
		model.addAttribute("childHead1", "New Connection Approve");
		int approve_status=0;
		if(createflagnta==1){
			model.addAttribute("msg","Application(s) Approved");
			createflagnta=0;
		}
		if(createflagnta==2){
			model.addAttribute("msg","Application(s) Rejected");
			createflagnta=0;
		}
		List<?> listApproval=consumerMasterService.getPendingConnForBilling(approve_status,request);
		model.addAttribute("masterListApproval", listApproval);
		List<?> observationList=observationMasterService.getAllObservationRecordsBill();
		model.addAttribute("observationList", observationList);
		return "consumerModule/newConnectionApproval";	
	}
	
	
	
	@RequestMapping(value = "/newConnApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String newConnApproveStatus(HttpServletRequest request){
		
		try{
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			String rdate=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String english[]=rdate.split("-");
			int cday=Integer.parseInt(english[0]);
			int month=Integer.parseInt(english[1]);
			int year=Integer.parseInt(english[2]);
			BsmartWaterLogger.logger.info(cday+"cday"+month+"cmonth"+year);
			String nepalidate=masterGenericDAO.getNepaliDateFromEnglishDate(year, month, cday);
			String denoted_by=null;
		


			String docNum[]=new String[1];

			String conIds=request.getParameter("connectId");

			int conStatus=Integer.parseInt(request.getParameter("conStatus"));

			BsmartWaterLogger.logger.info(conIds+"conIds");
			if(conIds.contains(","))
			{
				docNum=conIds.split(",");
			}
			else
			{
				docNum[0]=conIds;

			}

			for(int i=0;i<docNum.length;i++)
			{

				BsmartWaterLogger.logger.info(docNum[i]);
				ConsumerMaster consumerMaster=consumerMasterService.findconsumer(docNum[i]);


				if(conStatus==1){
					if(consumerMaster!=null){

						consumerMaster.setStatus(""+1);
						
						double pipe=consumerMaster.getPipe_size()==null?0:consumerMaster.getPipe_size();
						
						if(pipe==0.5){
							denoted_by="SA";
							consumerMaster.setDenoted_by(denoted_by);
						} else if(pipe>0.5){
							denoted_by="THA";
							consumerMaster.setDenoted_by(denoted_by);
						} else{
							
						}
						
						consumerMaster.setUpdatedBy(userName);
						consumerMaster.setLastUpdatedDate(today);
						consumerMaster.setCon_satatus("Normal");
						consumerMasterService.update(consumerMaster);

						double arrears=0;
						double initialrdng=0;
						List<NC_MasterSync> nc_MasterSync=nc_MasterSyncService.findAllByConNOSC(consumerMaster.getConnection_no(),consumerMaster.getBranch());

						if(!nc_MasterSync.isEmpty()){

							try{
								arrears=nc_MasterSync.get(0).getAdvance();
								initialrdng=nc_MasterSync.get(0).getInitial_rdng();
							}catch(Exception e){
								arrears=0;
							}

						}



						//Ledger Save Operation Start

						BillingLedgerEntity ble=new BillingLedgerEntity();

						ble.setConnection_no(consumerMaster.getConnection_no());
						ble.setMr_id(consumerMaster.getMtr_reader());
						ble.setPrevious_reading(initialrdng);
						ble.setPresent_reading(0.0);
						ble.setConsumption((double) 0);
						ble.setWater_charges((double) 0);
						ble.setService_charge((double) 0);
						ble.setArrears(-(arrears));
						ble.setInterest((double) 0);
						ble.setLatefee((double) 0);
						ble.setNet_amount((double) 0);
						ble.setMcunits((double) 0);
						ble.setPenalty(0.0);
						ble.setUser_id(userName);
						ble.setRebate((double) 0);
						ble.setTotalamt((double) 0);
						ble.setSuspense((double) 0);

						ble.setWardno(consumerMaster.getWard_no());
						ble.setReading_day((consumerMaster.getReading_day()));
						ble.setPipe_size(consumerMaster.getPipe_size());
						ble.setDenoted_by(denoted_by);
						ble.setSw_charges((double) 0);
						ble.setOther(0.0);
						if(consumerMaster.getBranch()!=null&&!consumerMaster.getBranch().isEmpty()){

							ble.setSitecode(consumerMaster.getBranch());	
						}

						ble.setSundry_amount((double) 0);
						ble.setAvg_units((double) 0);

						String ss[]=nepalidate.split("-");
						String newnepmonth="";

						if(String.valueOf(ss[1]).length()==1){
							newnepmonth="0"+ss[1];
						}else{
							newnepmonth=""+ss[1];
						}

						if("Metered".equalsIgnoreCase(consumerMaster.getCon_type())){

							String newnepday="";
							if(String.valueOf(consumerMaster.getReading_day()).length()==1){
								newnepday="0"+consumerMaster.getReading_day();
							}else{
								newnepday=""+consumerMaster.getReading_day();
							}

							Date billDate_N_to_E = dateConverter.convertBsToAd(newnepday+newnepmonth+ss[0]);
							String s1= new SimpleDateFormat("MM/dd/yyyy").format(billDate_N_to_E);
							String se[]=s1.split("/");
							String newMonth="";
							if(String.valueOf(se[0]).length()==1){
								newMonth="0"+se[0];
							}else{
								newMonth=""+se[0];
							}

							ble.setRdng_date(billDate_N_to_E);
							ble.setBill_date(billDate_N_to_E);
							ble.setRdng_date_nep(ss[0]+"-"+newnepmonth+"-"+newnepday);
							ble.setBill_date_nep(ss[0]+"-"+newnepmonth+"-"+newnepday);
							ble.setMonthyear(Integer.parseInt(year+""+newMonth));
							ble.setRdng_mth(Integer.parseInt(newMonth));
							ble.setPrevious_read_date(billDate_N_to_E);
						}else{
							ble.setRdng_date(new Date());
							ble.setBill_date(new Date());
							ble.setRdng_date_nep(nepalidate);
							ble.setBill_date_nep(nepalidate);
							ble.setMonthyear(Integer.parseInt(year+""+month));
							ble.setRdng_mth(month);
							ble.setPrevious_read_date(new Date());
						}


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
						ble.setBill_period(1.0);

						String monthyearnepali=ss[0]+""+newnepmonth;
						ble.setMonthyearnep(monthyearnepali);

						int monthyearnep=billingLedgerDao.getMaxMonthYearNepali();
						if(monthyearnepali.equalsIgnoreCase(""+monthyearnep)){
							billingLedgerService.customSave(ble);
						}

						//Ledger Save Operation End
						
						
						//Meter Master Save Operation
						
						MeterDetailsEntity meterDetailsEntity=new MeterDetailsEntity();
						
						meterDetailsEntity.setConnectionno(consumerMaster.getConnection_no());
						meterDetailsEntity.setIr((int)initialrdng);
						
						if(!nc_MasterSync.isEmpty()){

							try{
								meterDetailsEntity.setMeter_make(nc_MasterSync.get(0).getMeter_make());
								meterDetailsEntity.setMeter_no(nc_MasterSync.get(0).getMeter_slno());
								meterDetailsEntity.setMeter_capacity("");
								meterDetailsEntity.setMeter_own(nc_MasterSync.get(0).getMeter_own());
								meterDetailsEntity.setIns_date_eng(nc_MasterSync.get(0).getMtr_ins_date());
								meterDetailsEntity.setMetcon_date_eng(nc_MasterSync.get(0).getMtr_ins_date());
								meterDetailsEntity.setEnt_date_eng(new Date());
								meterDetailsEntity.setEnt_date_nep(nepalidate);
								meterDetailsEntity.setCalibrated_officer(nc_MasterSync.get(0).getCreated_by());
								meterDetailsEntity.setAdded_by(userName);
								meterDetailsEntity.setAdded_date(new Date());
								meterDetailsEntity.setStatus(1);
								
								meterDetailsService.customSave(meterDetailsEntity);
								
							}catch(Exception e){
								
							}

						}
						
						
						//Meter Master Save Operation end


					}
				}else{

					if(consumerMaster!=null){
						consumerMaster.setStatus(""+2);
						consumerMaster.setUpdatedBy(userName);
						consumerMaster.setLastUpdatedDate(today);
						consumerMasterService.update(consumerMaster);
					}
				}
			}

			if(conStatus==1){
				createflagnta=1;
			}else{
				createflagnta=2;
			}
		}catch(Exception e){
			createflagnta=2;
		}
   	
		return "redirect:/newConnectionApproval";
		
	}
	
	
	//Approve New Connection Start
	
	
	@RequestMapping(value = "/approveNewConnection", method = {RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String approveNewConnection(HttpServletRequest request){
		
		try{
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			String rdate=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String english[]=rdate.split("-");
			int cday=Integer.parseInt(english[0]);
			int month=Integer.parseInt(english[1]);
			int year=Integer.parseInt(english[2]);
			BsmartWaterLogger.logger.info(cday+"cday"+month+"cmonth"+year);
			String nepalidate=masterGenericDAO.getNepaliDateFromEnglishDate(year, month, cday);

			Double pr=0.0;
			String connectionno=request.getParameter("connectionno");
			
			//String prev=request.getParameter("prev");
			
			/*try{
			 pr=Double.parseDouble(request.getParameter("pr"));
			}catch(Exception e){
				pr=0.0;
			}*/
			
			//String mc=request.getParameter("mc");
			//String unitsConsume=request.getParameter("unitsConsume");
			
			//String[] mcs=mc.split("-");
			
			//String observationname=(String)mcs[1];
			//double unitsConsumer=Double.parseDouble(unitsConsume);

			ConsumerMaster consumerMaster=consumerMasterService.findconsumer(connectionno);
			
			
			
			double minimum_charges=0.0;
			double additionalCharges=0.0;
			double waterCharges=0.0;
			double sewageCharges=0.0;
			double netAmount=0.0;
			double excessCharges=0.0;
			double meterRent=0.0;
			double rateper1000Ltr=0.0;
			double others=0;
			
			//Billing Logic

			if(consumerMaster!=null){
			  
			  TariffRateMaster tariffRateMaster=tariffRateMasterService.getTariffRate(consumerMaster.getPipe_size(),consumerMaster.getCon_type());
			  double arrears=0;
			  double initialrdng=0;
			  String meter_hired="No";
			  
			  
				  

					  List<NC_MasterSync> nc_MasterSync=nc_MasterSyncService.findAllByConNOSC(consumerMaster.getConnection_no(),consumerMaster.getBranch());

					  if(!nc_MasterSync.isEmpty()){

						  try{
							  arrears=nc_MasterSync.get(0).getAdvance();
							  initialrdng=nc_MasterSync.get(0).getInitial_rdng();
						  }catch(Exception e){
							  arrears=0;
						  }

					  }

					  
					  //Meter Master Save Operation
					  

					  MeterDetailsEntity meterDetailsEntity=new MeterDetailsEntity();

					  meterDetailsEntity.setConnectionno(consumerMaster.getConnection_no());
					  meterDetailsEntity.setIr((int)initialrdng);
					  if(!nc_MasterSync.isEmpty()){   //if it goes inside then stop 

						  try{
							  meterDetailsEntity.setMeter_make(nc_MasterSync.get(0).getMeter_make());
							  meterDetailsEntity.setMeter_no(nc_MasterSync.get(0).getMeter_slno());
							  meterDetailsEntity.setMeter_capacity("");
							  meterDetailsEntity.setMeter_own(nc_MasterSync.get(0).getMeter_own());
							  meterDetailsEntity.setIns_date_eng(nc_MasterSync.get(0).getMtr_ins_date());
							  meterDetailsEntity.setMetcon_date_eng(nc_MasterSync.get(0).getMtr_ins_date());
							  meterDetailsEntity.setEnt_date_eng(new Date());
							  meterDetailsEntity.setEnt_date_nep(nepalidate);
							  meterDetailsEntity.setCalibrated_officer(nc_MasterSync.get(0).getCreated_by());
							  meterDetailsEntity.setAdded_by(userName);
							  meterDetailsEntity.setAdded_date(new Date());
							  meterDetailsEntity.setStatus(1);
							  		
							  
							  meterDetailsService.customSave(meterDetailsEntity);

						  }catch(Exception e){

						  }

					  }

					  //Meter Master Save Operation end
					  
					 /* if(tariffRateMaster!=null){  
						  billGeneration(connectionno, observationname, unitsConsumer,
								  consumerMaster, waterCharges, rateper1000Ltr,
								  tariffRateMaster, meter_hired);

					  }*/
				  

					  //Ledger Save Operation Start
					 // waterCharges=785;
					//  sewageCharges=392.5;
	
					// changes asked by kukl team 3-05-2019 by ram
					  TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(consumerMaster.getPipe_size(),"UNMETERED");
					  try {  
						  Double pipe_size=consumerMaster.getPipe_size();
						 
						  if(pipe_size == tariffRateMaster1.getDiameter_tap())
						  {
						  waterCharges=tariffRateMaster1.getMonthly_charges();
						
						  sewageCharges=waterCharges * (tariffRateMaster1.getSw_charge_percent()/100.0);
						    
						  }
						 
						  else 
						  {
							  waterCharges=785;
							  sewageCharges=392.5;
						  }
						  
						}catch(Exception e) {
							e.printStackTrace();
						}
					  
					  
					  
					  try {
						  if("No".equalsIgnoreCase(consumerMaster.getSewage_used())) {
							  sewageCharges=0;
						  } else {
							  //sewageCharges=392.5;
						  }
					  } catch (Exception e) {
						  e.printStackTrace();
					}
					  
					// 03-05-2019 
					  
					  meterRent=0;
					  BillingLedgerEntity ble=new BillingLedgerEntity();

					  ble.setConnection_no(consumerMaster.getConnection_no());
					  ble.setMr_id(consumerMaster.getMtr_reader());
					  ble.setPrevious_reading(initialrdng);
					  ble.setPresent_reading(pr);
					  ble.setConsumption(0.0);
					  ble.setWater_charges(waterCharges);
					  ble.setSw_charges(sewageCharges);
					  ble.setMtr_rent(meterRent);
					  ble.setService_charge((double) 0);
					  ble.setArrears(-(arrears));
					  ble.setInterest((double) 0);
					  ble.setLatefee((double) 0);
					  ble.setNet_amount((-(arrears))+waterCharges+sewageCharges+meterRent);
					  ble.setMcunits((double) 0);
					  ble.setPenalty(0.0);
					  ble.setUser_id(userName);
					  ble.setRebate((double) 0);
					  ble.setTotalamt((double) 0);
					  ble.setSuspense((double) 0);
					  ble.setMc_status(15);
					  ble.setWardno(consumerMaster.getWard_no());
					  ble.setReading_day((consumerMaster.getReading_day()));
					  ble.setPipe_size(consumerMaster.getPipe_size());
					  ble.setOther(0.0);
					  ble.setCreated_date(new Date());
					  ble.setAdded_by(userName);
					  if(consumerMaster.getBranch()!=null&&!consumerMaster.getBranch().isEmpty()){
						  ble.setSitecode(consumerMaster.getBranch());	
					  }

					  ble.setSundry_amount((double) 0);
					  ble.setAvg_units((double) 0);

					  String ss[]=nepalidate.split("-");
					  String newnepmonth="";

					  if(String.valueOf(ss[1]).length()==1){
						  newnepmonth="0"+ss[1];
					  }else{
						  newnepmonth=""+ss[1];
					  }

					  String newnepday="";
					 /* if("Metered".equalsIgnoreCase(consumerMaster.getCon_type())){

						  if(String.valueOf(consumerMaster.getReading_day()).length()==1){
							  newnepday="0"+consumerMaster.getReading_day();
						  }else{
							  newnepday=""+consumerMaster.getReading_day();
						  }

						  Date billDate_N_to_E = dateConverter.convertBsToAd(newnepday+newnepmonth+ss[0]);
						  String s1= new SimpleDateFormat("MM/dd/yyyy").format(billDate_N_to_E);
						  String se[]=s1.split("/");
						  String newMonth="";
						  if(String.valueOf(se[0]).length()==1){
							  newMonth="0"+se[0];
						  }else{
							  newMonth=""+se[0];
						  }

						  ble.setRdng_date(billDate_N_to_E);
						  ble.setBill_date(billDate_N_to_E);
						  ble.setRdng_date_nep(ss[0]+"-"+newnepmonth+"-"+newnepday);
						  ble.setBill_date_nep(ss[0]+"-"+newnepmonth+"-"+newnepday);
						  ble.setMonthyear(Integer.parseInt(year+""+newMonth));
						  ble.setRdng_mth(Integer.parseInt(newMonth));
						  ble.setPrevious_read_date(billDate_N_to_E);
					  }else{*/
						  ble.setRdng_date(new Date());
						  ble.setBill_date(new Date());
						  ble.setRdng_date_nep(nepalidate);
						  ble.setBill_date_nep(nepalidate);
						  ble.setMonthyear(Integer.parseInt(year+""+month));
						  ble.setRdng_mth(month);
						  ble.setPrevious_read_date(new Date());
					  //}


						  ble.setDl_count(0);
						  ble.setMth_dl_count(0);
						  ble.setMth_dl_units((double) 0);
						  ble.setDl_units((double) 0);
						  ble.setAdded_by("");
						  ble.setDr_amount((double) 0);
						  ble.setMtr_rent(0.0);
						  ble.setOpen_balance(-(arrears));
						  ble.setExcess_charges(0.0);
						  ble.setAdditional_charges(additionalCharges);
						  ble.setMinimum_charges(minimum_charges);
						  ble.setBill_period(1.0);

					 // String monthyearnepali=ss[0]+""+newnepmonth;
					  int monthyearnepali=billingLedgerDao.getMaxMonthYearNepali();
					  ble.setMonthyearnep(""+monthyearnepali);
					  long billNo=0;
						String maxBillNo=billingLedgerDao.getMaxBillNoByWardNo(monthyearnepali+"%");
						
						if(maxBillNo==null){
							billNo=10000*monthyearnepali;
						}else{
							
							boolean b1=NumberUtils.isNumber(maxBillNo);
							if(b1){
							    billNo=(Long.parseLong(maxBillNo)+1);
							}else{
								 billNo=((long)(monthyearnepali)+1);
							}
						}
					  ble.setBillno(""+billNo);

					  //int monthyearnep=billingLedgerDao.getMaxMonthYearNepali();
					  //if(monthyearnepali.equalsIgnoreCase(""+monthyearnep)){
						billingLedgerService.customSave(ble);
					  //}
							
					  //Ledger Save Operation End



					  consumerMaster.setStatus(""+1);
					  consumerMaster.setUpdatedBy(userName);
					  consumerMaster.setLastUpdatedDate(new Timestamp(new Date().getTime()));
					  consumerMaster.setNew_con_approved_by(userName);
					  consumerMaster.setNew_con_approved_date(new Timestamp(new Date().getTime()));
					  consumerMaster.setCon_satatus("Normal");
					  consumerMaster.setMeterHired(meter_hired);
					  consumerMasterService.update(consumerMaster);

					  return "Approved Successfully";
				  }
			  
				
			return "OOPS!! Something went wrong Please try again";
			
		}catch(Exception e){
			
			return "OOPS!! Something went wrong Please try again";
		}
   	
		
		
	}

	private void billGeneration(String connectionno, String observationname,
			double unitsConsumer, ConsumerMaster consumerMaster,
			double waterCharges, double rateper1000Ltr,
			TariffRateMaster tariffRateMaster, String meter_hired) {
		double minimum_charges;
		double additionalCharges;
		double sewageCharges;
		double meterRent;
		double others;
		double unitsMaster=tariffRateMaster.getMin_consumption();
		  if("Reading".equalsIgnoreCase(observationname)){

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
				  if("Yes".equalsIgnoreCase(meter_hired) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
					  meterRent=consumerMaster.getMeterRentCharges();					    //Meter Rent Charges
				  }
			  }
		  }
		  else if("Low Water Supply".equalsIgnoreCase(observationname) || "No Water Supply".equalsIgnoreCase(observationname) ||
				  "House not found".equalsIgnoreCase(observationname)
				  || "No Reading".equalsIgnoreCase(observationname)){


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
				  if("Yes".equalsIgnoreCase(meter_hired) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
					  meterRent=consumerMaster.getMeterRentCharges();					    //Meter Rent Charges
				  }
			  }


		  }else if("Door Lock".equalsIgnoreCase(observationname) || "Dog Presence".equalsIgnoreCase(observationname) || 
				  "Meter Burried".equalsIgnoreCase(observationname)){


			  Object[] obj=billingLedgerService.getDoorLockCount(connectionno);

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
				  if("Yes".equalsIgnoreCase(meter_hired) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
					  meterRent=consumerMaster.getMeterRentCharges();					    //Meter Rent Charges
				  }
			  }

		  }else if("Meter Block".equalsIgnoreCase(observationname)){

			  double avgconsumption=billingLedgerService.getAvgConsumption(connectionno);
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
				  if("Yes".equalsIgnoreCase(meter_hired) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
					  meterRent=consumerMaster.getMeterRentCharges();					    //Meter Rent Charges
				  }
			  }


		  }else if("Meter Sheild Broken".equalsIgnoreCase(observationname)
				  || "Meter Damaged".equalsIgnoreCase(observationname) 
				  || "Meter Removed(No Meter)".equalsIgnoreCase(observationname)){

			  TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(consumerMaster.getPipe_size(),"Unmetered");
			  minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
			  waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)

			  if(consumerMaster!=null){

				  if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
					  sewageCharges=0.5*waterCharges;										//SeweRage Charges
				  }
				  if("Yes".equalsIgnoreCase(meter_hired) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
					  meterRent=consumerMaster.getMeterRentCharges();					    //Meter Rent Charges
				  }					    //Meter Rent Charges

			  }


			  if("Meter Removed(No Meter)".equalsIgnoreCase(observationname)){
				  others=0;

			  }

		  }
		  else if("Temporary hole block".equalsIgnoreCase(observationname)
				  || "Permanent hole block".equalsIgnoreCase(observationname) 
				  || "Service Line disconnected".equalsIgnoreCase(observationname) 
				  || "House Collapse(Earthquake)".equalsIgnoreCase(observationname)
				  || "Dual Record".equalsIgnoreCase(observationname)
				  || "PID".equalsIgnoreCase(observationname)){


			  minimum_charges=0;            //Minimum Charges for Metered //*(billperiod)
			  additionalCharges=0;                                  //Additional Charges for Metered //*(billperiod)
			  waterCharges=0;//Water Charges for Metered  //*(billperiod)
			  sewageCharges=0;
			  meterRent=0;	//Meter Rent Charges

		  }
		  else if("Unmetered".equalsIgnoreCase(observationname)){

			  TariffRateMaster tariffRateMaster1=tariffRateMasterService.getTariffRate(consumerMaster.getPipe_size(),"Unmetered");	
			  minimum_charges=tariffRateMaster1.getMonthly_charges()*1;                //Minimum Charges for UnMeterd //*(billperiod)
			  waterCharges=tariffRateMaster1.getMonthly_charges()*1;                   //Water Charges for UnMeterd //*(billperiod)

			  if(consumerMaster!=null){

				  if("Yes".equalsIgnoreCase(consumerMaster.getSewage_used())){
					  sewageCharges=0.5*waterCharges;										//SeweRage Charges
				  }

				  if("Yes".equalsIgnoreCase(meter_hired) && "Metered".equalsIgnoreCase(consumerMaster.getCon_type())){
					  meterRent=consumerMaster.getMeterRentCharges();					    //Meter Rent Charges
				  }else{
					  meterRent=0;					    									//Meter Rent Charges
				  }
			  }

		  }

		  //Billing Logic End
	}
	
	
	//Approve New Connection End
	
	
	
	
	
	
	

	@RequestMapping(value="/downloadBLUserManual/download",method = { RequestMethod.GET,RequestMethod.POST })
	public String downloadComplainceUserManual(HttpServletRequest request,Model model,HttpServletResponse response) throws Exception
	{
	//System.out.println("inside download download Billing User Manual");
	String docName="Billing_UserManual";
	InputStream file = this.getClass().getClassLoader().getResourceAsStream("files/Billing_UserManual.pdf");

	try {
	response.setHeader("Content-Disposition", "inline;filename=\"" +docName+ "\"");
	OutputStream out = response.getOutputStream();
	IOUtils.copy(file, out);
	out.flush();
	out.close();
	}
	catch (IOException e)
	{
	e.printStackTrace();
	} catch (Exception e)
	{
	e.printStackTrace();
	}
	return null;
	}
	@RequestMapping(value="/downloadAbbreviationList/download",method = { RequestMethod.GET,RequestMethod.POST })
	public String downloadAbbreviationList(HttpServletRequest request,Model model,HttpServletResponse response) throws Exception
	{
		//System.out.println("inside download download Billing User Manual");
		String docName="Abbreviation_List";
		InputStream file = this.getClass().getClassLoader().getResourceAsStream("files/AbrList.pdf");
		
		try {
			response.setHeader("Content-Disposition", "inline;filename=\"" +docName+ "\"");
			OutputStream out = response.getOutputStream();
			IOUtils.copy(file, out);
			out.flush();
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	//OwnerShip Change
	
	
	@RequestMapping(value="/ownerShipChangeReq",method={RequestMethod.POST,RequestMethod.GET})
	public String ownerShipChangeReq(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Customer Module");
		model.addAttribute("childHead1", "OwnerShip Change");
		model.addAttribute("ownerShipChangeEntity", new OwnerShipChange());
		
		if(createflagoct==1){
			model.addAttribute("msg","Application Sent for Approval");
			createflagoct=0;
		}
		if(createflagoct==2){
			model.addAttribute("msg","Oops! Something went wrong Please try again");
			createflagoct=0;
		}
		return "consumerModule/ownerShipChangeRequest";	
	}
	
	@RequestMapping(value = "/addOwnerShipChangeApprove", method = {RequestMethod.GET, RequestMethod.POST})
	public String addOwnerShipChangeApprove(@ModelAttribute("ownerShipChangeEntity")OwnerShipChange ownerShipChangeEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request,
															@RequestParam("public_notice_doc") MultipartFile public_notice_doc){
		
		try{
			
			HttpSession session=request.getSession(false);
			String userName=(String) session.getAttribute("userName");
			ownerShipChangeEntity.setRequest_by(userName);
			ownerShipChangeEntity.setRequest_date(new Date());
			ownerShipChangeEntity.setApprove_status(0);
			ownerShipChangeEntity.setOld_name_nep(new String(ownerShipChangeEntity.getOld_name_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
			ownerShipChangeEntity.setOld_fname_nep(new String(ownerShipChangeEntity.getOld_fname_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
			ownerShipChangeEntity.setOld_gfname_nep(new String(ownerShipChangeEntity.getOld_gfname_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
			ownerShipChangeEntity.setNew_name_nep(new String(ownerShipChangeEntity.getNew_name_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
			ownerShipChangeEntity.setNew_fname_nep(new String(ownerShipChangeEntity.getNew_fname_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
			ownerShipChangeEntity.setNew_gfname_nep(new String(ownerShipChangeEntity.getNew_gfname_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
			
			if(public_notice_doc.getSize()>0){
				ownerShipChangeEntity.setPublic_notice_doc(public_notice_doc.getBytes());
			}
			
			ownerShipChangeService.save(ownerShipChangeEntity);
			createflagoct=1;
			return "redirect:/ownerShipChangeReq";

		}catch(Exception e){
			e.printStackTrace();
			createflagoct=2;
			return "redirect:/ownerShipChangeReq";

		}
		
	}
	
	
	private String getFileType(String fe) {
		 String fileExtension="";
		if("jpg".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim()) || "jpeg".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim()) || "png".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim()) || "gif".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim()) || "bmp".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim())){
			   fileExtension="Image";
		   }
		   if("pdf".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim()) ){
			   fileExtension="PDF";
		   }
		   if("doc".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim()) || "docx".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim()) ){
			   fileExtension="Word";
		   }
		   if("xls".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim()) || "xlsx".equalsIgnoreCase(fe.substring(fe.lastIndexOf(".") + 1).trim()) ){
			   fileExtension="Excel";
		 }
		return fileExtension;
	}
	
	@RequestMapping(value="/ownerShipApprove",method={RequestMethod.POST,RequestMethod.GET})
	public String ownerShipApprove(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Master Approval");
		model.addAttribute("childHead1", "OwnerShip Change Approval");
		int approve_status=0;
		if(createflagocta==1){
			model.addAttribute("msg","Application(s) Approved");
			createflagocta=0;
		}
		if(createflagocta==2){
			model.addAttribute("msg","Application(s) Rejected");
			createflagocta=0;
		}
		if(createflagocta==3){
			model.addAttribute("msg","OOPS,Something Went Wrong Please try again...");
			createflagocta=0;
		}
		List<?> listApproval=ownerShipChangeService.getPendingConnectionsToApprove(approve_status);
		model.addAttribute("masterListApproval", listApproval);
		return "consumerModule/ownerShipChangeApproval";	
	}
	
	
	
	@RequestMapping(value = "/ownershipChangeDocument/{conNo}", method = {RequestMethod.GET, RequestMethod.POST})
	public String ownershipChangeDocument(@PathVariable("conNo") String conNo, HttpServletResponse response) {
		OwnerShipChange ownerShipChangeEntity = ownerShipChangeService.getRecordByConnectionNo(conNo, 0);
		byte[] doc = ownerShipChangeEntity.getPublic_notice_doc();
		try {
			if (doc != null) {
				response.setHeader("Content-Disposition", "inline;filename=\""+ conNo + " change doc" + "\"");
				OutputStream out = response.getOutputStream();
				InputStream is = new ByteArrayInputStream(doc);
				org.apache.commons.io.IOUtils.copy(is, out);
				out.flush();
				out.close();
			} else {
				OutputStream out = response.getOutputStream();
				org.apache.commons.io.IOUtils.write("File Not Found", out);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/ownerShipApproveStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String ownerShipChangeApproveStatus(HttpServletRequest request){
		
		
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
   		    
   		  	OwnerShipChange ownerShipChange=ownerShipChangeService.getRecordByConnectionNo(docNum[i],approve_status);
   		  	
   		  	if(conStatus==1){
   		  		
   		  		if(ownerShipChange!=null){
   		  			ownerShipChange.setApprove_status(1);
   		  			ownerShipChange.setApprove_by(userName);
   		  			ownerShipChange.setApprove_date(new Date());
   		  			ownerShipChangeService.update(ownerShipChange);
   		        
	   		  		ConsumerMaster consumerMaster=consumerMasterService.findconsumer(docNum[i]);
	   		        
	   		        if(consumerMaster!=null){
	   		        	consumerMaster.setConsumer_title(ownerShipChange.getNew_consumer_title());
	   		        	consumerMaster.setName_eng(ownerShipChange.getNew_name_eng());
	   		        	consumerMaster.setName_nep(ownerShipChange.getNew_name_nep());
	   		        	consumerMaster.setFname_eng(ownerShipChange.getNew_fname_eng());
	   		        	consumerMaster.setFname_nep(ownerShipChange.getNew_fname_nep());
	   		        	consumerMaster.setGfname_eng(ownerShipChange.getNew_gfname_eng());
	   		        	consumerMaster.setGfname_nep(ownerShipChange.getNew_gfname_nep());
	   		        	consumerMaster.setCitizenship_no(ownerShipChange.getNew_citizenshipno());
	   		            consumerMasterService.update(consumerMaster);
	   		        }
   		  	  }else{
   		  		createflagocta=3;
   		  	  }
   		        
   		        
   		  	}else{
   		  		
   		  	   if(ownerShipChange!=null){
   		  		  ownerShipChange.setApprove_status(2);
   		  		  ownerShipChange.setApprove_by(userName);
   		  		  ownerShipChange.setApprove_date(new Date());
   		  		  ownerShipChangeService.update(ownerShipChange);
		        
		  		}else{
		  			createflagocta=3;  
	   		  	}
   		  	}
   	   }
		
	   	if(conStatus==1){
	   		createflagocta=1;
		}else{
			createflagocta=2;
		}
   	
		return "redirect:/ownerShipApprove";
		
	}
	
	@RequestMapping(value="/getOwnerShipChangeList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getOwnerShipList(){
		return ownerShipChangeService.getOwnerShipChangeList();
	}
	
	
	
	
	//End OwnerShip Change
	
	@RequestMapping(value="/getConsumerApproveList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getConsumerApproveList(){
		return consumerMasterApprovalService.getConsumerApproveList();
	}
	
	
	
	
	@RequestMapping(value="/getConnectionTypeApproveList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getConnectionTypeApproveList(){
		return conTypeChangeService.getConnectionTypeApproveList();
	}
	
	@RequestMapping(value="/getNewConnectionApproveList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getNewConnectionApproveList(HttpServletRequest req)
	{
		HttpSession session=req.getSession(false);
		String schemaName=(String) session.getAttribute("schemaName");
		String s[]=schemaName.split("_");	
	    List<?> list= consumerMasterService.getNewConnectionApproveList(s[1]);
		return list;
	}
	
	
	@RequestMapping(value="/getOwnershipChangeListAll",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getOwnershipChangeListAll(){
		return ownerShipChangeService.getOwnerShipChangeList();
	}
	
//============================sql editor==========================
	
	@RequestMapping(value="/sqlEditor",method={RequestMethod.POST,RequestMethod.GET})  
    public  String sqlEditor(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
      {
      
		return "consumerModule/sqlEditor";
      }
	private int checkStatus=0;
	@RequestMapping(value="/sewageChangeOne",method={RequestMethod.POST,RequestMethod.GET})  
    public  String severageChangeButton(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
      {
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
      
		String con=request.getParameter("connection_no");
		String mthyr=request.getParameter("mthyr1");
		String sewage_used=request.getParameter("sewage_used");
		String remarks=request.getParameter("remarks1");
		
		
			ConsumerMaster cm=consumerMasterService.findconsumer(con);
			SewageChangeEntity sce=new SewageChangeEntity();
			sce.setConnectionNO(con);
			sce.setMonthYr(mthyr);
			sce.setSewage_Used_Old(cm.getSewage_used());
			sce.setSewage_Used_New(sewage_used);
			sce.setAddBy(userName);
			sce.setAddDate(new Date());
			sce.setRemarks(remarks);
			sce.setStatus(0);
			sewageUsedChangeService.save(sce);
			cm.setSewage_used(sewage_used);
			checkStatus=1;
			
		
				return "redirect:/sewageChange";
      }
	
	
	
	@RequestMapping(value="/sewageChange",method={RequestMethod.POST,RequestMethod.GET})  
    public  String severageChange(HttpServletRequest request,HttpServletResponse response,ModelMap model) 
      {
		if(checkStatus==1)
		{
			model.addAttribute("msgVal","Sent for sewage Approval!");
			checkStatus=0;
			
		}
		else if(checkStatus==2)
		{
			model.addAttribute("msgVal","OPPS,Something went wrong plz try again");
			checkStatus=0;
		}
		else
		{
			model.addAttribute("msgVal","");
		}
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
       List<SewageChangeEntity> list=sewageUsedChangeService.getAllSewChanEntityData();
       model.addAttribute("list",list);
		return "consumerModule/severageChange";
      }
	
	
	@SuppressWarnings("finally")
	@RequestMapping(value="/getReport",method={RequestMethod.POST,RequestMethod.GET})  
    public  String getReport(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws IOException, SQLException, ClassNotFoundException
      {
		
		HttpSession session=request.getSession(false);
		String qry=request.getParameter("qry").trim().toUpperCase();
		String userName=(String) session.getAttribute("userName");
        if(qry!=""||qry!=null)
        {
		String schemaName=(String) session.getAttribute("schemaName");
		Connection conn = null;
	   	   ResultSet rs=null;
		try {
			
			//System.out.println("qry============================="+qry);
				if (qry.startsWith("select") || qry.startsWith("SELECT")) 
				{
					if (qry.toUpperCase().indexOf("BSW_USERS") == -1 && qry.toLowerCase().indexOf("bsw_users") == -1) 
					{
						Class.forName("oracle.jdbc.driver.OracleDriver");
						try {
							conn = DriverManager.getConnection(
									"jdbc:oracle:thin:@192.168.20.2:1521:ORCL",
									schemaName, "sticb_BcitsK4");
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (conn == null) {
							// System.out.println(" no connection");
						} else {

							BsmartWaterLogger.logger
									.info("Connection Established");
						}

						java.sql.Statement statement = conn.createStatement();
						rs = statement.executeQuery(qry);

						/*
						 * List<Map<String, Object>> rows = new
						 * ArrayList<Map<String, Object>>(); ResultSetMetaData
						 * metaData = resultSet.getMetaData(); int columnCount =
						 * metaData.getColumnCount();
						 * 
						 * while (resultSet.next()) { Map<String, Object>
						 * columns = new LinkedHashMap<String, Object>();
						 * 
						 * for (int i = 1; i <= columnCount; i++) {
						 * columns.put(metaData.getColumnLabel(i),
						 * resultSet.getObject(i)); }
						 * 
						 * rows.add(columns); }
						 */

						ResultSetMetaData md = rs.getMetaData();
						int count = md.getColumnCount();
						// System.out.println("count ===> "+count);

						List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
						while (rs.next()) {
							Map<String, Object> columns = new LinkedHashMap<String, Object>();
							for (int column = 1; column <= count; column++) {

								String type = md.getColumnTypeName(column);
								if (type.equalsIgnoreCase("Date")
										|| (type).equalsIgnoreCase("TimeStamp")) {
									String date1 = "";
									SimpleDateFormat date = new SimpleDateFormat(
											"dd-MM-yyyy");
									if (rs.getDate(column) != null) {
										date1 = date.format(rs.getDate(column));
										columns.put(md.getColumnLabel(column),
												date1);
									} else {
										date1 = "N/A";
									}

								} else {
									columns.put(md.getColumnLabel(column),
											rs.getObject(column));
								}
							}
							rows.add(columns);
							model.addAttribute("rows", rows);

						}
						try {
							//System.out.println("Inside TRY");
							SqlEditorExecutedQry editorExecutedQry =new SqlEditorExecutedQry();
							editorExecutedQry.setSql_qry(qry);
							editorExecutedQry.setExecuted_by(userName);
							editorExecutedQry.setExecuted_date(new Date());
							executedQryService.save(editorExecutedQry);
						}catch (Exception e){
							e.printStackTrace();
						}
						
						//System.out.println("Returning Success");
						return "succes";
					}else{
						model.addAttribute("msg", "Sorry!!! You Cannot access this Information");
						return "consumerModule/sqlEditor";
					}
				}
			else
			{
				 model.addAttribute("msg", "Sorry!!! you have to write only SELECT queries...");
				return "consumerModule/sqlEditor";
			}

		}  
		catch(Exception e)
	   	 {
			e.printStackTrace();
	   		  model.addAttribute("msg","Sorry!!! some errors is there in query please check...");
	   		  return "consumerModule/sqlEditor";
	   	 }
	   	 finally{
	   		 BsmartWaterLogger.logger.info("***********In finally Block************************");
	   		 if(conn!=null)
	   		 conn.close();
	   		 if(rs!=null)
	   		 rs.close();
	   		  return "consumerModule/sqlEditor";
	   	 }
	   	   
	}
		return "success";
    
      }
	
}

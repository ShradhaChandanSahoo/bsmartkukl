package com.bcits.bsmartwater.controller;

import java.sql.Timestamp;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
import com.bcits.bsmartwater.dao.ApplicationSettingsDAO;
import com.bcits.bsmartwater.dao.CashAdjApproveDAO;
import com.bcits.bsmartwater.dao.WrongPostingDao;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.CashAdjApproveEntity;
import com.bcits.bsmartwater.model.PaymentEntity;
import com.bcits.bsmartwater.model.WrongPosting;
import com.bcits.bsmartwater.service.ApplicationSettingService;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.service.CashAdjApproveService;
import com.bcits.bsmartwater.service.ConsumerMasterService;
import com.bcits.bsmartwater.service.PaymentService;
import com.bcits.bsmartwater.service.WrongPostingService;

@Controller
public class CollectionsController
{
	@Autowired
	private ApplicationSettingService applicationSettingService; 
	
	@Autowired
	private PaymentService  paymentService;	
	
	@Autowired
	private WrongPostingService wrongPostingService;
	
	@Autowired
	private WrongPostingDao wrongPostingDao;
	
	@Autowired
	private CashAdjApproveDAO cashAdjApproveDAO;
	
	@Autowired
	private CashAdjApproveService cashAdjApproveService;
	
	@Autowired
	private ApplicationSettingsDAO applicationSettingsDAO;
	
	@Autowired
	private  BillingLedgerService billingLedgerService;
	
	@Autowired
	private ConsumerMasterService consumerMasterService;
	
	int flagc=0;
	int flagca=0;
	
	@RequestMapping(value="/wrongPosting",method={RequestMethod.POST,RequestMethod.GET})
	public String wrongPosting(Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Collections");
		model.addAttribute("childHead1", "WrongPosting");
		model.addAttribute("wrongPost",new WrongPosting());
		
		if(flagc==1){
			model.addAttribute("msg","Wrong Posting Adjustment done Succesfully & sent for Approval");
			flagc=0;
		}
		if(flagc==2){
			model.addAttribute("msg","Oops! Something went wrong Please try again");
			flagc=0;
		}
		
		return "collections/viewWrongPosting";	
	}
	
	@RequestMapping(value="/viewCashDepositDetails",method={RequestMethod.POST,RequestMethod.GET})
	public String viewCashDepositDetails(Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Collections");
		model.addAttribute("childHead1", "DepositDetails");
		return "collections/viewCashDepositDetails";	
	}
	
	@RequestMapping(value="/viewCashSearch",method={RequestMethod.POST,RequestMethod.GET})
	public String viewCashSearch(Model model,HttpServletRequest request)
	{
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
		model.addAttribute("mainHead", "Collections");
		model.addAttribute("childHead1", "CashSearch");
		return "collections/viewCashSearch";	
	}
	
	@RequestMapping(value="/viewChequeBounce",method={RequestMethod.POST,RequestMethod.GET})
	public String viewChequeBounce(@ModelAttribute("paymentsEntity")PaymentEntity paymentEntity,BindingResult bindingResult,Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Collections");
		model.addAttribute("childHead1", "ChequeBounce");
		model.addAttribute("paymentsEntity", new PaymentEntity());
		return "collections/viewChequeBounce";	
	}
	
	@RequestMapping(value="/searchCashDetails/{conectionVal}/{receiptVal}/{payTowardsVal}/{amount}/{nepalDate}/{englishDate}/{bankname}/{ChequeNo}/{ChequedateNepal}/{paymode}/{ChequedateEng}/{wardno}",method=RequestMethod.GET)
	@ResponseBody
	public Object searchCashDetails(
			@PathVariable String conectionVal,@PathVariable String receiptVal,
			@PathVariable String payTowardsVal,@PathVariable String amount,@PathVariable String nepalDate,
			@PathVariable String englishDate,@PathVariable String bankname,@PathVariable String ChequeNo,
			@PathVariable String ChequedateNepal,@PathVariable String paymode,@PathVariable String ChequedateEng,
			@PathVariable String wardno,HttpServletRequest request) 
	{

		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName"); 
		return paymentService.getCashDetails(conectionVal,receiptVal,payTowardsVal,amount,nepalDate,englishDate,bankname,ChequeNo,ChequedateNepal,paymode,ChequedateEng,wardno,request);
				
	}
	
	@RequestMapping(value="/searchByConnectionNo/{connectionNo}",method=RequestMethod.GET)
	@ResponseBody
	public Object searchByConnectionNo(@PathVariable String connectionNo,HttpServletRequest request) 
	{
		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName"); 
		
		String schema=schemaName;
		return  paymentService.searchByConnectionNo(connectionNo,schema,request);
	}
	
	@RequestMapping(value="/wrongPostingData/{connectionNo}/{rdate}",method=RequestMethod.GET)
	@ResponseBody
	public Object wrongPostingData(@PathVariable String connectionNo,@PathVariable String rdate,HttpServletRequest request) 
	{
		HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName"); 
		String schema=schemaName;
		return paymentService.wrongPostingData(connectionNo,schema,rdate,request);
				
	}
	
	/*@RequestMapping(value="/updateWrongPost",method={RequestMethod.GET,RequestMethod.POST,})
	@ResponseStatus(value=HttpStatus.OK)
	public String updateWrongPost(HttpServletRequest request) 
	{
	HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName"); 
		String schema="schemaName";
		System.out.println("fromConnectionNo"+request.getParameter("fromConnectionNo"));
		//return paymentService.wrongPostingData(connectionNo,schema,rdate,request);
		return "collections/viewWrongPosting";
				
	}*/
	
	@RequestMapping(value="/updateWrongPost",method={RequestMethod.POST,})
	public String createUserType(@ModelAttribute("wrongPost")WrongPosting wrongPost,BindingResult bingingResult,ModelMap model,HttpServletRequest request){
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		try{
		
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		String rdate=request.getParameter("engRdate");
		String adjdate=request.getParameter("engAdate");
		
		if(rdate!=null && !rdate.isEmpty()){
			wrongPost.setEngRdate(wrongPostingDao.getDate2(rdate));
		}
		if(adjdate!=null && !adjdate.isEmpty()){
			wrongPost.setEngAdate(wrongPostingDao.getDate2(adjdate));
		}
		
		wrongPost.setAdded_by(userName);
		wrongPost.setAdded_date(new Date());
		wrongPost.setStatus(0);
		wrongPostingService.save(wrongPost);
		flagc=1;
		
		}catch(Exception e){
			e.printStackTrace();
			flagc=2;
		}
		
		/*HttpSession session=request.getSession(false);
		String schemaName=(String)session.getAttribute("schemaName"); 
		String schema=schemaName;
		Map<String, String> map=new HashMap<String, String>();
		map.put("fromConnectionNo", request.getParameter("fromConnectionNo"));
		map.put("toConnectionNo", request.getParameter("toConnectionNo"));
		map.put("receiptNo", request.getParameter("receiptNo"));
		map.put("adjustmentNo", request.getParameter("adjustmentNo"));
		map.put("engRdate", request.getParameter("engRdate"));
		map.put("engAdate", request.getParameter("engAdate"));
		map.put("receiptAmnt", request.getParameter("receiptAmnt"));
		map.put("adjustmentAmnt", request.getParameter("adjustmentAmnt"));
		map.put("payMode", request.getParameter("payMode"));
		map.put("towards", request.getParameter("towards"));
		map.put("fromRemarks", request.getParameter("fromRemarks"));
		map.put("toRemarks", request.getParameter("toRemarks"));
		map.put("cdNo", request.getParameter("cdNo"));
		map.put("cdDate", request.getParameter("cdDate"));
		map.put("bankName", request.getParameter("bankName"));
		
		
		paymentService.updateWrongPost(map,schema,request);*/
		
		
		return "redirect:/wrongPosting";	
		
	}
	
	
	@RequestMapping(value="/wrongPostingApproval",method={RequestMethod.POST,RequestMethod.GET})
	public String wrongPaostingApproval(Model model,HttpServletRequest request){	
		
		model.addAttribute("mainHead", "Wrong Posting");
		model.addAttribute("childHead1", "Approval");
		int status=0;
		List<?> wrongPostList =wrongPostingService.getWrongPostApproveList(status);
		model.addAttribute("wrongPostList", wrongPostList);
		
		if(flagca==1){
			model.addAttribute("msg","Approved");
			flagca=0;
		}
		if(flagca==2){
			model.addAttribute("msg","Rejected");
			flagca=0;
		}
		
		
		
		return "cashCounter/wrongPostApproval";	
	}
	
	@RequestMapping(value="/wrongPostApproveList",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object wrongPostApproveList(){
		List<?> wrongPostList =wrongPostingService.getWrongPostApproveList();
		return wrongPostList;
	}
	
	@RequestMapping(value="/cashAdjustment",method={RequestMethod.POST,RequestMethod.GET})
	public String cashAdjustment(Model model,HttpServletRequest request)
	{
		model.addAttribute("mainHead", "Collections");
		model.addAttribute("childHead1", "CashAdjustment");
		model.addAttribute("cashAdjApprove",new CashAdjApproveEntity());
		return "collections/cashAdjustment";	
	}
	
	@RequestMapping(value="/updateCashAdjustment",method={RequestMethod.POST,})
	public String updateCashAdjustment(@ModelAttribute("cashAdjApprove")CashAdjApproveEntity cashAdjApproveEntity,BindingResult bingingResult,ModelMap model,HttpServletRequest request){
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		String rdate=request.getParameter("engRdate");
		String adjdate=request.getParameter("engAdate");
		System.out.println("Receipt No-----------"+cashAdjApproveEntity.getReceiptNo());
		if(rdate!=null && !rdate.isEmpty()){
			cashAdjApproveEntity.setEngRdate(cashAdjApproveDAO.getDate2(rdate));
		}
		if(adjdate!=null && !adjdate.isEmpty()){
			cashAdjApproveEntity.setEngAdate(cashAdjApproveDAO.getDate2(adjdate));
		}
		
		cashAdjApproveEntity.setCreated_by(userName);
		cashAdjApproveEntity.setCreated_date(new Date());
		cashAdjApproveEntity.setStatus(0);
		cashAdjApproveService.save(cashAdjApproveEntity);
		
		return "redirect:/cashAdjustment";	
		
	}
	
	
	@RequestMapping(value="/cashAdjustmentApproval",method={RequestMethod.POST,RequestMethod.GET})
	public String cashAdjustmentApproval(Model model,HttpServletRequest request)
	{
		
		
		return "approval/cashAdjustmentApproval";	
	}
	
	
	@RequestMapping(value = "/wrongPostUpdateStatus", method = {RequestMethod.GET, RequestMethod.POST})
	public String conTypeApproveStatus(HttpServletRequest request){
		
		
		HttpSession session=request.getSession(false);
		String userName=(String) session.getAttribute("userName");
		
		try{
		String docNum[]=new String[1];
		
		String billIds=request.getParameter("connectId");
		
		int conStatus=Integer.parseInt(request.getParameter("conStatus"));
		
		System.out.println(billIds+"billIds");
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
   		
   		  	System.out.println(docNum[i]);
	   		
   		  	int approve_status=0;
   		  	
   		  	WrongPosting wrongPosting=wrongPostingService.getRecordByConNo(docNum[i],approve_status);
   		  	
   		  	if(conStatus==1){
   		  		if(wrongPosting!=null){
   		  			
   		  		PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(wrongPosting.getReceiptNo(),wrongPosting.getFromConnectionNo());
   		  		PaymentEntity paymentEntity=new PaymentEntity();
   				paymentEntity.setConnectionNo(paymentdata.getConnectionNo());
   				paymentEntity.setRdate(paymentdata.getRdate());
   				paymentEntity.setAmount((paymentdata.getAmount()*(-1)));
   				paymentEntity.setBranchCode(paymentdata.getBranchCode());
   				paymentEntity.setCdno(paymentdata.getCdno());
   				paymentEntity.setBankname(paymentdata.getBankname());
   				paymentEntity.setCddate(paymentdata.getCddate());
   				paymentEntity.setRemarks(paymentdata.getRemarks());
   				paymentEntity.setEdate(paymentdata.getEdate());
   				paymentEntity.setAmountCurrTotal(paymentdata.getAmountCurrTotal());
   				paymentEntity.setParticulars(paymentdata.getParticulars());
   				paymentEntity.setUser_id(paymentdata.getUser_id());
   				paymentEntity.setPayMode(paymentdata.getPayMode());
   				paymentEntity.setPaymentId(paymentdata.getPaymentId());
   				paymentEntity.setDemandId(paymentdata.getDemandId());
   				paymentEntity.setBankId(paymentdata.getBankId());
   				paymentEntity.setReceiptNo(paymentdata.getReceiptNo());
   				paymentEntity.setSms(paymentdata.getSms());
   				paymentEntity.setTowards(paymentdata.getTowards());
   				paymentEntity.setBillno(paymentdata.getBillno());
   				paymentEntity.setCounterno(paymentdata.getCounterno());
   				paymentEntity.setNepali_date(paymentdata.getNepali_date());
   				paymentEntity.setSessionId(paymentdata.getSessionId());
   				paymentEntity.setAddedBy(paymentdata.getAddedBy());
   				paymentEntity.setUpdatedBy(paymentdata.getUpdatedBy());
   				paymentEntity.setSiteCode(paymentdata.getSiteCode());
   				paymentEntity.setMonthYearEng(paymentdata.getMonthYearEng());
   				paymentEntity.setPenalty((paymentdata.getPenalty()==null?0:paymentdata.getPenalty()*(-1)));
   				paymentEntity.setRebate((paymentdata.getRebate()==null?0:paymentdata.getRebate()*(-1)));
   				paymentEntity.setFrecamount((paymentdata.getFrecamount()==null?0:paymentdata.getFrecamount())*(-1));
   				paymentEntity.setBalance_amount((paymentdata.getBalance_amount()==null?0:paymentdata.getBalance_amount())*(-1));
   				paymentEntity.setTender_cash((paymentdata.getTender_cash()==null?0:paymentdata.getTender_cash())*(-1));
   				paymentEntity.setChange((paymentdata.getChange()==null?0:paymentdata.getChange())*(-1));
   				paymentEntity.setAdvance((paymentdata.getAdvance()==null?0:paymentdata.getAdvance())*(-1));
   				paymentEntity.setWater_charges((paymentdata.getWater_charges()==null?0:paymentdata.getWater_charges())*(-1));
   				paymentEntity.setSw_charges((paymentdata.getSw_charges()==null?0:paymentdata.getSw_charges())*(-1));
   				paymentEntity.setMeter_rent((paymentdata.getMeter_rent()==null?0:paymentdata.getMeter_rent()*(-1)));
   				paymentEntity.setMiscellaneous_cost((paymentdata.getMiscellaneous_cost()==null?0:paymentdata.getMiscellaneous_cost())*(-1));
   				paymentEntity.setAdvance_rebate((paymentdata.getAdvance_rebate()==null?0:paymentdata.getAdvance_rebate())*(-1));
   				paymentEntity.setBill_amount((paymentdata.getBill_amount()==null?0:paymentdata.getBill_amount())*(-1));
   				paymentEntity.setOld_balance((paymentEntity.getOld_balance()==null?0:paymentEntity.getOld_balance())*(-1));
   				paymentEntity.setRbalance((paymentEntity.getRbalance()==null?0:paymentEntity.getRbalance())*(-1));
   				paymentEntity.setPenalty_adj_amount((paymentdata.getPenalty_adj_amount()==null?0:paymentdata.getPenalty_adj_amount()*(-1)));
   				paymentEntity.setBill_adj_amount((paymentdata.getBill_adj_amount()==null?0:paymentdata.getBill_adj_amount()*(-1)));
   				paymentEntity.setFrom_mon_year(paymentdata.getFrom_mon_year());
   				paymentEntity.setTo_mon_year(paymentdata.getTo_mon_year());
   				paymentEntity.setCancelledremarks("CANCELLED(WRONG POSTING)");
   				paymentEntity.setRecordtype("DUPLICATE");
   				paymentEntity.setPipe_size(paymentdata.getPipe_size());
   				paymentEntity.setDenoted_by(paymentdata.getDenoted_by());
   				paymentEntity.setWard_no(paymentdata.getWard_no());
   				paymentEntity.setReading_day(paymentdata.getReading_day());
   				paymentEntity.setCancelled_by(userName);
   				paymentdata.setRemarks("Wrong Posting Adjustment");
   				paymentEntity.setCancelled_date(new Timestamp(new java.util.Date().getTime()));
   		  			
	  			wrongPosting.setStatus(1);
	  			wrongPosting.setApproved_by(userName);
	  			wrongPosting.setApproved_date(new Date());
	  			wrongPostingService.update(wrongPosting);
	  			paymentService.save(paymentEntity);	
	  			
	  			BillingLedgerEntity bill=billingLedgerService.getLatestBillNotNull(paymentdata.getConnectionNo());
	  			
	  			if(bill!=null){
	  					
	  					System.out.println("inside  cancel bill");
	  					bill.setLast_paid_amount(null);
	  					bill.setReceipt_date(null);
	  					bill.setClose_balance(null);
	  					billingLedgerService.update(bill);
	  			}
	  			
  			    List<BillingLedgerEntity> billlist=billingLedgerService.getListByConNoAndRECNo(paymentdata.getConnectionNo(),paymentdata.getReceiptNo());
  				ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
  				UpdateAsynchronous updateasynchronous = context.getBean(UpdateAsynchronous.class);
  				System.out.println("about to run");
  				String schemaName=(String)session.getAttribute("schemaName");
  				Future<Boolean> future = updateasynchronous.cancelReceiptAsynchronousUpdate(paymentdata, billlist,billingLedgerService,schemaName);
  				System.out.println("this will run immediately.");
	  			
  				
  				//New Connection Transfer
  				
  				BillingLedgerEntity bill1=billingLedgerService.getLatestBillNotNull(wrongPosting.getTo_connection_no());
  				
  				if(bill1!=null){
  					
  					
  					PaymentEntity paymentEntity1=new PaymentEntity();
  	   				paymentEntity1.setConnectionNo(wrongPosting.getTo_connection_no());
  	   				paymentEntity1.setRdate(paymentdata.getRdate());
  	   				paymentEntity1.setAmount((paymentdata.getAmount()));
  	   				paymentEntity1.setBranchCode(paymentdata.getBranchCode());
  	   				paymentEntity1.setCdno(paymentdata.getCdno());
  	   				paymentEntity1.setBankname(paymentdata.getBankname());
  	   				paymentEntity1.setCddate(paymentdata.getCddate());
  	   				paymentEntity1.setRemarks(null);
  	   				paymentEntity1.setEdate(new Date());
  	   				
  	   				paymentEntity1.setParticulars("Wrong Posting");
  	   				paymentEntity1.setUser_id(paymentdata.getUser_id());
  	   				paymentEntity1.setPayMode(paymentdata.getPayMode());
  	   				paymentEntity1.setPaymentId(paymentdata.getPaymentId());
  	   				paymentEntity1.setDemandId(paymentdata.getDemandId());
  	   				paymentEntity1.setBankId(paymentdata.getBankId());
  	   				
  	   				long nReceiptNo=0;
  	   				long newreceiptno=paymentService.getMaxReceiptNo(paymentdata.getBranchCode()+""+new SimpleDateFormat("MMYY").format(new Date())+paymentdata.getCounterno()+"%",Integer.parseInt(paymentdata.getCounterno()));
  	   				System.out.println("New Receipt Number : "+newreceiptno);
  	   				
  	   				if(newreceiptno==0){
  	   					nReceiptNo=Long.parseLong(paymentdata.getBranchCode()+""+new SimpleDateFormat("MMYY").format(new Date())+paymentdata.getCounterno()+String.format("%07d", 1));
  	   				}else{
  	   					nReceiptNo=newreceiptno+1;
  	   				}
  	   				
  	   				
  	   				paymentEntity1.setReceiptNo(""+nReceiptNo);
  	   				paymentEntity1.setSms(paymentdata.getSms());
  	   				paymentEntity1.setTowards(paymentdata.getTowards());
  	   				paymentEntity1.setBillno(paymentdata.getBillno());
  	   				
  	   				paymentEntity1.setCounterno(paymentdata.getCounterno());
  	   				paymentEntity1.setNepali_date(paymentdata.getNepali_date());
  	   				paymentEntity1.setSessionId(paymentdata.getSessionId());
  	   				paymentEntity1.setAddedBy(userName);
  	   				paymentEntity1.setSiteCode(paymentdata.getSiteCode());
  	   				paymentEntity1.setMonth_year_nep(paymentdata.getMonth_year_nep());
  	   				
  	   				paymentEntity1.setPenalty(0.0);
  	   				paymentEntity1.setRebate(0.0);
  	   				paymentEntity1.setFrecamount(0.0);
  	   				paymentEntity1.setBalance_amount(bill1.getArrears());
  	   				paymentEntity1.setAdvance(0.0);
  	   				paymentEntity1.setWater_charges(bill1.getWater_charges());
  	   				paymentEntity1.setSw_charges(bill1.getSw_charges());
  	   				paymentEntity1.setMeter_rent(bill1.getMtr_rent());
  	   				paymentEntity1.setMiscellaneous_cost(paymentdata.getMiscellaneous_cost());
  	   				paymentEntity1.setAdvance_rebate(0.0);
  	   				paymentEntity1.setBill_amount(bill1.getNet_amount());
  	   				paymentEntity1.setOld_balance(0.0);
  	   				paymentEntity1.setRbalance(0.0);
  	   				paymentEntity1.setPenalty_adj_amount(paymentdata.getPenalty_adj_amount());
  	   				paymentEntity1.setBill_adj_amount(paymentdata.getBill_adj_amount());
  	   				paymentEntity1.setCancelledremarks("");
  	   				paymentEntity1.setPipe_size(bill1.getPipe_size());
  	   				paymentEntity1.setDenoted_by(bill1.getDenoted_by());
  	   				paymentEntity1.setWard_no(bill1.getWardno());
  	   				paymentEntity1.setReading_day(bill1.getReading_day());
  	   				paymentService.save(paymentEntity1);
  	   				
  	   			if(paymentEntity.getAmount()>0){
  	  			  billingLedgerService.updateBillLedger(wrongPosting.getTo_connection_no(),paymentEntity1.getPenalty(),paymentEntity1.getRebate(),paymentEntity1.getMiscellaneous_cost(),paymentEntity1.getReceiptNo(),paymentEntity1.getAmount(),(bill1.getNet_amount()-(paymentEntity1.getAmount()+paymentEntity1.getPenalty_adj_amount())),paymentEntity1.getAdvance_rebate(),paymentEntity1.getOld_balance(),schemaName,paymentEntity1.getTo_mon_year(),paymentEntity1.getBill_adj_amount(),paymentEntity1.getPenalty_adj_amount(),paymentEntity.getRdate(),"");
  	  		    }
  	   				
  				}
  				
  				
  				
  				
  				
   		        
   		  		}
   		  	}else{
   		  	   if(wrongPosting!=null){
   		  		   	wrongPosting.setStatus(2);
		  			wrongPosting.setApproved_by(userName);
		  			wrongPosting.setApproved_date(new Date());
		  			wrongPostingService.update(wrongPosting);
		        
		  		}
   		  	}
   	   }
		
	   	if(conStatus==1){
	   		flagca=1;
		}else{
			flagca=2;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
   	
		return "redirect:/wrongPostingApproval";
		
	}
	
}

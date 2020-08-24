package com.bcits.bsmartwater.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.bcits.bsmartwater.asyncupdate.AppConfig;
import com.bcits.bsmartwater.asyncupdate.UpdateAsynchronous;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.BoardInstallment;
import com.bcits.bsmartwater.model.CashCounterDayClose;
import com.bcits.bsmartwater.model.CashMasterConfig;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.CounterDetails;
import com.bcits.bsmartwater.model.HolidayMaster;
import com.bcits.bsmartwater.model.MiscellaneousPayment;
import com.bcits.bsmartwater.model.PaymentEntity;
import com.bcits.bsmartwater.model.UserLog;
import com.bcits.bsmartwater.service.BillPenaltyAdjService;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.service.BoardInstallmentService;
import com.bcits.bsmartwater.service.CashCounterConfigService;
import com.bcits.bsmartwater.service.CashCounterDayCloseService;
import com.bcits.bsmartwater.service.CloseMonthEndService;
import com.bcits.bsmartwater.service.ConsumerMasterService;
import com.bcits.bsmartwater.service.CounterDetailsService;
import com.bcits.bsmartwater.service.HolidayMasterService;
import com.bcits.bsmartwater.service.MiscellaneousPaymentService;
import com.bcits.bsmartwater.service.PaymentService;
import com.bcits.bsmartwater.service.UserLogService;
import com.bcits.bsmartwater.service.UserMasterService;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;
import com.bcits.bsmartwater.utils.DateConverter;
import com.bcits.bsmartwater.utils.NumberToWords;

@Controller
public class CashCounterController {

	@Autowired
	private CashCounterConfigService cashCounterConfigService; 
	
	@Autowired
	private UserMasterService userMasterService;
	
	@Autowired
	private ConsumerMasterService consumerMasterService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private BillingLedgerService billingLedgerService;
	
	@Autowired
	private CashCounterDayCloseService cashCounterDayCloseService;
	
	/*@Autowired
	private BankDetailsService bankDetailsService;*/
	
	@Autowired
	private CounterDetailsService counterDetailsService;
	
	@Autowired
	private MasterGenericDAO masterGenericDAO;
	
	@Autowired
	DateConverter dateConverter;
	
	@Autowired
	CloseMonthEndService closeMonthEndService;
	
	
	@Autowired
	BoardInstallmentService boardInstallmentService;
	
	@Autowired
	private UserLogService userLogService;
	
	@Autowired
	private BillPenaltyAdjService billPenaltyAdjService;
	
	@Autowired
	private MiscellaneousPaymentService miscellaneousPaymentService;
	
	@Autowired
	private HolidayMasterService holidayMasterService;
	
	int createcashconfig=0;
	int createpayment=0;
	int createnpayment=0;
	String paymentReceiptNo="";
	String npaymentReceiptNo="";
	int cashcounterDayClose=0;
	int createpaymentb=0;
	int expirydatestatus=0;
	
	 public static final String ip 		= "192.168.10.5";
		public static final String calledIP	= "http://192.168.10.5:8080";
	
	@RequestMapping(value="/cashCounterConfiguration",method={RequestMethod.POST,RequestMethod.GET})
	public String consumerMasterIndex(Model model,HttpServletRequest request){
		if(createcashconfig==1){
			model.addAttribute("msg","Cash Counter Configuration Created Successfully");
			createcashconfig=0;
		}
		if(createcashconfig==2){
			model.addAttribute("msg","Cash Counter Configuration Updated Successfully");
			createcashconfig=0;
		}
		if(createcashconfig==3){
			model.addAttribute("msg","Cash Counter Configuration Deleted Successfully");
			createcashconfig=0;
		}
		model.addAttribute("cashMasterConfigData",cashCounterConfigService.getcashMasterConfigData());
		model.addAttribute("cashMasterConfig", new CashMasterConfig());
		model.addAttribute("mainHead", "Cash Counter");
		model.addAttribute("childHead1", "Configuration");
		model.addAttribute("userMasterData",userMasterService.getUserMasterData());
		
		List<CounterDetails> counter = counterDetailsService.getAllCounterRecordsNA();
		model.addAttribute("counter", counter);
		return "cashCounter/cashCounterConfiguration";	
	}
	@RequestMapping(value = "/createCashMasterConfig", method = {RequestMethod.GET, RequestMethod.POST})
	public String createCashMasterConfig(@ModelAttribute("cashMasterConfig")CashMasterConfig cashMasterConfig,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		
		cashCounterConfigService.save(cashMasterConfig);
		createcashconfig=1;
		return "redirect:/cashCounterConfiguration";
		
	}
	@RequestMapping(value="/getCounterDetails/{counter_no}",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody Object getCounterDetails(@PathVariable String counter_no){
		return counterDetailsService.getcounterName(counter_no);
	}
	
	
	
	@RequestMapping(value = "/updateCashMasterConfig", method = {RequestMethod.GET, RequestMethod.POST})
	public String updateCashMasterConfig(@ModelAttribute("cashMasterConfig")CashMasterConfig cashMasterConfig,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		cashCounterConfigService.update(cashMasterConfig);
		createcashconfig=2;
		return "redirect:/cashCounterConfiguration";
		
	}
	@RequestMapping(value = "/deleteCashMasterConfig", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteCashMasterConfig(@ModelAttribute("cashMasterConfig")CashMasterConfig cashMasterConfig,ModelMap model,HttpServletRequest req, SessionStatus sessionStatus,final Locale locale)  {
		//System.out.println(cashMasterConfig.getId());
		cashCounterConfigService.delete(cashMasterConfig);
		createcashconfig=3;
		return "redirect:/cashCounterConfiguration";
		
	}
	/*======================================================cash counter payment=====================================*/
	@RequestMapping(value="/cashCounterPayments",method={RequestMethod.POST,RequestMethod.GET})
	public String cashCounterPayments(Model model,HttpServletRequest request){
		try{
		HttpSession session=request.getSession(false);
		model.addAttribute("mainHead", "Cash Counter");
		model.addAttribute("childHead1", "Payments");
		PaymentEntity paymentEntity=new PaymentEntity();
		
		/*Integer id=paymentService.getpaymentMaxId();
		if(id==null){
			id=1;
		}else{
			model.addAttribute("prevPayment",paymentService.getpaymentBasedOnId(id));
		}*/
		
		model.addAttribute("currDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		Object obj=null;
		if(session!=null){
		 obj=session.getAttribute("cashcounterauthorise");
		}
		Date dk=new Date();
		String engdate=new SimpleDateFormat("dd-MM-yyyy").format(dk);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String date=sdf.format(dk);
		Date d=sdf.parse(date);
		Date d1=sdf.parse("2020-12-28");//june 5
		Date d2=sdf.parse("2020-12-31");//june 10
		
		if(d.compareTo(d1)>=0  && d.compareTo(d2)<0  )
		{
			model.addAttribute("expirydate","Please Do Payment Otherwise Software will close from Aug 1st onwards.for Renewal plz talk to Administrator");
		}
		else if(d.compareTo(d2)>=0)
		{
			expirydatestatus=1;
			return "redirect:/login";
		}
			
		if(session!=null && obj!=null && session.getAttribute("cashcounterauthorise").equals(true)){
			
			Object dayclose=session.getAttribute("dayclose");
			if(dayclose!=null && ((boolean)dayclose)==true){
				return "redirect:/cashCounterDayClose";
			}
			
			Integer counterNo=(Integer)session.getAttribute("counterNo");
			String branchcode=(String)session.getAttribute("branchcode");
			
			Date engDate=new Date();
			String engFdate=new SimpleDateFormat("dd-MM-yyyy").format(engDate);
			String englishF[]=engFdate.split("-");
			int cday1=Integer.parseInt(englishF[0]);
			
			if("02".equalsIgnoreCase(englishF[1])){
				cday1=cday1+3;
			}
			
			if("04".equalsIgnoreCase(englishF[1])){
				cday1=cday1+1;
			}
			
			if("06".equalsIgnoreCase(englishF[1])){
				cday1=cday1+1;
			}
			
			if("09".equalsIgnoreCase(englishF[1])){
				cday1=cday1+1;
			}
			
			int cmonth1=Integer.parseInt(englishF[1]);
			int cyear1=Integer.parseInt(englishF[2]);
			String nepalidate1=masterGenericDAO.getNepaliDateFromEnglishDate(cyear1, cmonth1, cday1);
			//System.out.println("new receipt format  : Nepdate :"+nepalidate1);
			
			String [] mynF=nepalidate1.split("-");
			String mynNepali=mynF[1]+mynF[0].substring(2,4);
			//System.out.println("new receipt format  : Nepali MonthYear :"+mynNepali);
			
			
			long nReceiptNo=0;
			//long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+new SimpleDateFormat("MMYY").format(new Date())+counterNo+"%");
			long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+mynNepali+"%",counterNo);
			//System.out.println("New Receipt Number : "+newreceiptno);
			
			if(newreceiptno==0){
				nReceiptNo=Long.parseLong(branchcode+""+mynNepali+counterNo+String.format("%07d", 1));
			}else{
				nReceiptNo=newreceiptno+1;
			}
			//paymentEntity.setReceiptNo(branchcode+""+new SimpleDateFormat("MMYY").format(new Date())+counterNo+String.format("%07d", id+1));
			
			paymentEntity.setReceiptNo(""+nReceiptNo);
			model.addAttribute("paymentEntity", paymentEntity);
			//model.addAttribute("bankData",bankDetailsService.getBankDetailsEntity());
			
			if(createpayment==1){
				model.addAttribute("msg","Cash Counter Payment Created Successfully");
				PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(paymentReceiptNo);
				ConsumerMaster consumerMaster=consumerMasterService.findconsumer(paymentdata.getConnectionNo());
				BillingLedgerEntity billingLedgerEntity=billingLedgerService.findBillingLedgerBasedOnConnectionNo(paymentdata.getConnectionNo());
				model.addAttribute("peEntity",paymentdata);
				model.addAttribute("consumerMaster",consumerMaster);
				model.addAttribute("billLedger",billingLedgerEntity);
				
				
				if(paymentdata.getRdate()!=null){
				    model.addAttribute("date",new SimpleDateFormat("dd-MMM-yyyy").format(paymentdata.getRdate()));
				    String rdate=new SimpleDateFormat("dd-MM-yyyy").format(paymentdata.getRdate());
					String english[]=rdate.split("-");
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
					String nepalidate=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				    model.addAttribute("nepdate",nepalidate);

				}
				paymentReceiptNo="";
				createpayment=0;
			}  
			
			
			if(createpaymentb==1){
				
				model.addAttribute("msg1","Cash Counter Payment Created Successfully");
				PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(paymentReceiptNo);
				Object[] board=boardInstallmentService.getBoardInstallment(paymentdata.getConnectionNo(),paymentReceiptNo);
				
				if(board!=null){
					
					model.addAttribute("peEntity",paymentdata);
					model.addAttribute("connection_no",board[0]);
					model.addAttribute("paid_amount",board[1]);
					model.addAttribute("install_amount",board[2]);
					model.addAttribute("penalty",board[3]);
					model.addAttribute("rem_balance",board[4]);
					model.addAttribute("name_eng",board[5]);
					model.addAttribute("area_no",board[6]);
					model.addAttribute("customer_id",board[7]);
					model.addAttribute("install_due_date",board[8]);
				}
				
				if(paymentdata.getRdate()!=null){
				    model.addAttribute("date",new SimpleDateFormat("dd-MMM-yyyy").format(paymentdata.getRdate()));
				    String rdate=new SimpleDateFormat("dd-MM-yyyy").format(paymentdata.getRdate());
					String english[]=rdate.split("-");
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
					//System.out.println(cday+"cday"+cmonth+"cmonth"+cyear);
					String nepalidate=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				    model.addAttribute("nepdate",nepalidate);

				}
				paymentReceiptNo="";
				createpaymentb=0;
			}
			
			
			
		   //model.addAttribute("cashsummery",paymentService.getTodayCashSummery(counterNo));
			model.addAttribute("cashsummery","");
			
		    Integer counterno=(Integer)session.getAttribute("counterNo");
			List<?> list=paymentService.getdataforDayClose(counterno,new Date());
			if(!list.isEmpty()){
			 model.addAttribute("datalist",list);
			}
			
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
			
			return "cashCounter/cashCounterPayments";	
		}else{
			cashcounterDayClose=2;
			return "redirect:/cashCounterLogin";	
		}
		}catch(Exception e){
			createpayment=0;
			createpaymentb=0;
			return "cashCounter/cashCounterPayments";
		}
	}
	
	@RequestMapping(value="/getConsumerDataAndLedgerData/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getConsumerDataAndLedgerData(@PathVariable String connectionNo,HttpServletRequest request){
		
		HttpSession session=request.getSession(false);
		String branchcode=(String)session.getAttribute("branchcode");
		
		try{
		
			String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String english[]=date.split("-");
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
			String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			String[] s=nepalimonthyear.split("-");
			int startmonth=Integer.parseInt(s[0]+""+s[1]);
			BsmartWaterLogger.logger.info(startmonth+"--startmonth");
			
			if("1115".equalsIgnoreCase(branchcode)){	
				
		    ConsumerMaster consumerMaster=consumerMasterService.findconsumer(connectionNo);
			List<Object> result=new ArrayList<>();
			result.add(consumerMaster);//0
			
			//System.out.println("After Consumer Master");
			
			BillingLedgerEntity billingLedgerEntity=billingLedgerService.findBillingLedgerBasedOnConnectionNo(connectionNo);
			
			//System.out.println("After Billing Ledger");
			
			double totalwatercahrges=0.0;
			double totalswcahrges=0.0;
			double totalmtrcahrges=0.0;
			double totalpenlaty=0.0;
			double totalrebate=0.0;
			double closingbalance=0.0;
			String tomonthYear=null;
			String fromMonthYear=null;
			
			List<?> list=billingLedgerService.findBillsByReceiptNull(connectionNo);
			
			//System.out.println("After Penalty Ledger");
	
			int i=1;
			
			if(!list.isEmpty()){
			
			/*for(BillingLedgerEntity bill:list){
				
				totalwatercahrges+=bill.getWater_charges()==null?0:bill.getWater_charges();
				totalswcahrges+=bill.getSw_charges()==null?0:bill.getSw_charges();
				totalmtrcahrges+=bill.getMtr_rent()==null?0:bill.getMtr_rent();
	
				double billamount=(bill.getWater_charges()==null?0:bill.getWater_charges())+(bill.getSw_charges()==null?0:bill.getSw_charges())+(bill.getMtr_rent()==null?0:bill.getMtr_rent());
				
						if(bill.getRdng_date()!=null){
							
							DateTime dt1 = new DateTime(bill.getRdng_date());
							DateTime dt2 = new DateTime(new Date());
							Days diffInDays = Days.daysBetween(dt1, dt2);
							
							if(diffInDays.getDays()>0 && diffInDays.getDays()<=90){
								totalrebate=(billamount*3)/100;
							}else if(diffInDays.getDays()>91 && diffInDays.getDays()<=120){
			
							}
							else if(diffInDays.getDays()>91 && diffInDays.getDays()<=120){
								totalpenlaty+=(billamount*10)/100;
							}
							else if(diffInDays.getDays()>121 && diffInDays.getDays()<=150){
								totalpenlaty+=(billamount*20)/100;
							}else{
								totalpenlaty+=(billamount*50)/100;
							}
							
						}
						
						
						if(list.size()==i){
							fromMonthYear=bill.getMonthyearnep();
						}
						
						if(list.size()==1){
							fromMonthYear=bill.getMonthyearnep();
						}
						
						
						if(billingLedgerEntity!=null){
							
							
							 if((""+startmonth).equalsIgnoreCase(bill.getMonthyearnep()) || (""+(startmonth-1)).equalsIgnoreCase(bill.getMonthyearnep()) 
									 || (""+(startmonth-2)).equalsIgnoreCase(bill.getMonthyearnep())){
								   
								 if((bill.getNet_amount()==null?0:bill.getNet_amount())>0){
									  
									 if(billamount>bill.getNet_amount()){
										  totalrebate+=(bill.getNet_amount()*3)/100;
									 }else{
									      totalrebate+=(billamount*3)/100;
									 }
								   }
								   
							   }
							   
							   
							   else if((""+(startmonth-3)).equalsIgnoreCase(bill.getMonthyearnep())){
								  
							   }else if((""+(startmonth-4)).equalsIgnoreCase(bill.getMonthyearnep())){
								   if((bill.getNet_amount()==null?0:bill.getNet_amount())>0){
								   
									   if(billamount>bill.getNet_amount()){
										      totalpenlaty+=(bill.getNet_amount()*10)/100;
									   }else{
									          totalpenlaty+=(billamount*10)/100;
									   }
								   }
							   }else if((""+(startmonth-5)).equalsIgnoreCase(bill.getMonthyearnep())){
								   if((bill.getNet_amount()==null?0:bill.getNet_amount())>0){
									   
									   if(billamount>bill.getNet_amount()){
										      totalpenlaty+=(bill.getNet_amount()*20)/100;
									   }else{
									          totalpenlaty+=(billamount*20)/100;
									   }
								   }
							   }else{
								   if((bill.getNet_amount()==null?0:bill.getNet_amount())>0){
									   if(billamount>bill.getNet_amount()){
										      totalpenlaty+=(bill.getNet_amount()*50)/100;
									   }else{
									          totalpenlaty+=(billamount*50)/100;
									   }
								   }
							   }
					
						}
					  i++;
			}*/
			
			
			for (final Iterator<?> ii = list.iterator(); ii.hasNext();) {
					
							Object[] values = (Object[])ii.next();
				
							double waterCharge=values[0]==null?0:(Double)values[0];
							double swCharge=values[1]==null?0:(Double)values[1];
							double mtrrent=values[2]==null?0:(Double)values[2];
							double netamt=values[3]==null?0:(Double)values[3];
							String monthyrnep=values[4]==null?"":(String)values[4];		
	
					
							totalwatercahrges+=waterCharge;
							totalswcahrges+=swCharge;
							totalmtrcahrges+=mtrrent;
			
							double billamount=(waterCharge)+swCharge+(mtrrent);
					
							
							
							
							if(list.size()==i){
								fromMonthYear=monthyrnep;
							}
							
							if(list.size()==1){
								fromMonthYear=monthyrnep;
							}
							
							
							if(billingLedgerEntity!=null){
								
								
								 if((""+startmonth).equalsIgnoreCase(monthyrnep) || (""+(startmonth-1)).equalsIgnoreCase(monthyrnep) 
										 || (""+(startmonth-2)).equalsIgnoreCase(monthyrnep)){
									   
									 if(netamt>0){
										  
										 if(billamount>netamt){
											  totalrebate+=(netamt*3)/100;
										 }else{
										      totalrebate+=(billamount*3)/100;
										 }
									   }
									   
								   }
								   
								   
								   else if((""+(startmonth-3)).equalsIgnoreCase(monthyrnep)){
									  
								   }else if((""+(startmonth-4)).equalsIgnoreCase(monthyrnep)){
									   if(netamt>0){
									   
										   if(billamount>netamt){
											      totalpenlaty+=(netamt*10)/100;
										   }else{
										          totalpenlaty+=(billamount*10)/100;
										   }
									   }
								   }else if((""+(startmonth-5)).equalsIgnoreCase(monthyrnep)){
									   if(netamt>0){
										   
										   if(billamount>netamt){
											      totalpenlaty+=(netamt*20)/100;
										   }else{
										          totalpenlaty+=(billamount*20)/100;
										   }
									   }
								   }else{
									   if(netamt>0){
										   if(billamount>netamt){
											      totalpenlaty+=(netamt*50)/100;
										   }else{
										          totalpenlaty+=(billamount*50)/100;
										   }
									   }
								   }
						
							}
						  i++;
				
				
				
			}
			
			}
			
			double penaltypercent=0;
			double rebatepercent=0;
			double lastbalanceAmount=0;
			
			if(billingLedgerEntity!=null){
	
				if(fromMonthYear==null){
					fromMonthYear=billingLedgerEntity.getMonthyearnep();
					
				}
				
				if(tomonthYear==null){
					tomonthYear=billingLedgerEntity.getMonthyearnep();
				}
				
				result.add(billingLedgerEntity);//1
				if(billingLedgerEntity.getRdng_date()!=null){
					String rdng_date=new SimpleDateFormat("dd/MM/yyyy").format(billingLedgerEntity.getRdng_date());
					result.add(rdng_date);//2
				}else{
					result.add(null);//2
				}
				
			}else{
				result.add(null);//2
				
			}
				
			
				result.add(penaltypercent);//3
				result.add(rebatepercent);//4
				result.add(lastbalanceAmount);//5
				
				result.add(totalwatercahrges);//6
				result.add(totalswcahrges);//7
				result.add(totalmtrcahrges);//8
				result.add(totalpenlaty);//9
				result.add(totalrebate);//10
				result.add(totalwatercahrges+totalswcahrges+totalmtrcahrges);//11
	
			if(billingLedgerEntity!=null){
				closingbalance=billingLedgerEntity.getClose_balance()==null?0:billingLedgerEntity.getClose_balance();
				result.add(closingbalance);//12
				result.add(fromMonthYear);//13
				
				result.add(tomonthYear);//14
				
				result.add(billingLedgerEntity.getWater_charges());//15
				result.add(billingLedgerEntity.getSw_charges());//16
				result.add(billingLedgerEntity.getMtr_rent());//17
				
				String month=tomonthYear.substring(4, 6);
				result.add(month);//18
				BsmartWaterLogger.logger.info("MonthNepali------------"+month);
				
			}
			
	
			result.add(null);//19
			
			long adjtransaction=billPenaltyAdjService.getTransactionPending(connectionNo);
			BsmartWaterLogger.logger.info(adjtransaction+"adjtransaction");
			
			if(adjtransaction>0){
				result.add("TA");
			}
			
			return result;
			
			
			
			}else{
			
			
		    ConsumerMaster consumerMaster=consumerMasterService.findconsumer(connectionNo);
			List<Object> result=new ArrayList<>();
			result.add(consumerMaster);//0
			
			BillingLedgerEntity billingLedgerEntity=billingLedgerService.findBillingLedgerBasedOnConnectionNo(connectionNo);
			
			double totalwatercahrges=0.0;
			double totalswcahrges=0.0;
			double totalmtrcahrges=0.0;
			double totalpenlaty=0.0;
			double totalrebate=0.0;
			double closingbalance=0.0;
			String tomonthYear=null;
			String fromMonthYear=null;
			
			List<?> list=billingLedgerService.findBillsByReceiptNull(connectionNo);

			int i=1;
			
			if(!list.isEmpty()){
			
			/*for(BillingLedgerEntity bill:list){
				
				totalwatercahrges+=bill.getWater_charges()==null?0:bill.getWater_charges();
				totalswcahrges+=bill.getSw_charges()==null?0:bill.getSw_charges();
				totalmtrcahrges+=bill.getMtr_rent()==null?0:bill.getMtr_rent();
				double billamount=(bill.getWater_charges()==null?0:bill.getWater_charges())+(bill.getSw_charges()==null?0:bill.getSw_charges())+(bill.getMtr_rent()==null?0:bill.getMtr_rent());
				
						
						
						if(list.size()==i){
							fromMonthYear=bill.getMonthyearnep();
						}
						
						if(list.size()==1){
							fromMonthYear=bill.getMonthyearnep();
							tomonthYear=bill.getMonthyearnep();
						}
						
						
						if(billingLedgerEntity!=null){
							
							
							 if((""+startmonth).equalsIgnoreCase(bill.getMonthyearnep()) || (""+(startmonth-1)).equalsIgnoreCase(bill.getMonthyearnep()) 
									 || (""+(startmonth-2)).equalsIgnoreCase(bill.getMonthyearnep())){
								   
								 if((bill.getNet_amount()==null?0:bill.getNet_amount())>0){
									  
									 if(billamount>bill.getNet_amount()){
										  totalrebate+=(bill.getNet_amount()*3)/100;
									 }else{
									      totalrebate+=(billamount*3)/100;
									 }
								   }
								   
							   }
							   
							   
							   else if((""+(startmonth-3)).equalsIgnoreCase(bill.getMonthyearnep())){
								  
							   }else if((""+(startmonth-4)).equalsIgnoreCase(bill.getMonthyearnep())){
								   if((bill.getNet_amount()==null?0:bill.getNet_amount())>0){
								   
									   if(billamount>bill.getNet_amount()){
										      totalpenlaty+=(bill.getNet_amount()*10)/100;
									   }else{
									          totalpenlaty+=(billamount*10)/100;
									   }
								   }
							   }else if((""+(startmonth-5)).equalsIgnoreCase(bill.getMonthyearnep())){
								   if((bill.getNet_amount()==null?0:bill.getNet_amount())>0){
									   
									   if(billamount>bill.getNet_amount()){
										      totalpenlaty+=(bill.getNet_amount()*20)/100;
									   }else{
									          totalpenlaty+=(billamount*20)/100;
									   }
								   }
							   }else{
								   if((bill.getNet_amount()==null?0:bill.getNet_amount())>0){
									   if(billamount>bill.getNet_amount()){
										      totalpenlaty+=(bill.getNet_amount()*50)/100;
									   }else{
									          totalpenlaty+=(billamount*50)/100;
									   }
								   }
							   }
					
						}
					   		   
					   i++;
			}*/
			
				
				for (final Iterator<?> ii = list.iterator(); ii.hasNext();) {
					
					Object[] values = (Object[])ii.next();
		
					double waterCharge=values[0]==null?0:(Double)values[0];
					double swCharge=values[1]==null?0:(Double)values[1];
					double mtrrent=values[2]==null?0:(Double)values[2];
					double netamt=values[3]==null?0:(Double)values[3];
					String monthyrnep=values[4]==null?"":(String)values[4];		

			
					totalwatercahrges+=waterCharge;
					totalswcahrges+=swCharge;
					totalmtrcahrges+=mtrrent;
	
					double billamount=(waterCharge)+swCharge+(mtrrent);
			
					
					
					
					if(list.size()==i){
						fromMonthYear=monthyrnep;
					}
					
					if(list.size()==1){
						fromMonthYear=monthyrnep;
					}
					
					
					if(billingLedgerEntity!=null){
						
						
						 if((""+startmonth).equalsIgnoreCase(monthyrnep) || (""+(startmonth-1)).equalsIgnoreCase(monthyrnep) 
								 || (""+(startmonth-2)).equalsIgnoreCase(monthyrnep)){
							   
							 if(netamt>0){
								  
								 if(billamount>netamt){
									  totalrebate+=(netamt*3)/100;
								 }else{
								      totalrebate+=(billamount*3)/100;
								 }
							   }
							   
						   }
						   
						   
						   else if((""+(startmonth-3)).equalsIgnoreCase(monthyrnep)){
							  
						   }else if((""+(startmonth-4)).equalsIgnoreCase(monthyrnep)){
							   if(netamt>0){
							   
								   if(billamount>netamt){
									      totalpenlaty+=(netamt*10)/100;
								   }else{
								          totalpenlaty+=(billamount*10)/100;
								   }
							   }
						   }else if((""+(startmonth-5)).equalsIgnoreCase(monthyrnep)){
							   if(netamt>0){
								   
								   if(billamount>netamt){
									      totalpenlaty+=(netamt*20)/100;
								   }else{
								          totalpenlaty+=(billamount*20)/100;
								   }
							   }
						   }else{
							   if(netamt>0){
								   if(billamount>netamt){
									      totalpenlaty+=(netamt*50)/100;
								   }else{
								          totalpenlaty+=(billamount*50)/100;
								   }
							   }
						   }
				
					}
				  i++;
		
		
		
	}
			
			
			}
			
			double penaltypercent=0;
			double rebatepercent=0;
			double lastbalanceAmount=0;
			
			if(billingLedgerEntity!=null){
				
				if(fromMonthYear==null){
					fromMonthYear=billingLedgerEntity.getMonthyearnep();
					
				}
				
				if(tomonthYear==null){
					tomonthYear=billingLedgerEntity.getMonthyearnep();
				}
				
				result.add(billingLedgerEntity);//1
				if(billingLedgerEntity.getRdng_date()!=null){
				 String rdng_date=new SimpleDateFormat("dd/MM/yyyy").format(billingLedgerEntity.getRdng_date());
				 result.add(rdng_date);//2
				}else{
					result.add(null);//2
				}
			}else{
				result.add(null);//2
				
			}
			
			
			result.add(penaltypercent);//3
			result.add(rebatepercent);//4
			result.add(lastbalanceAmount);//5
			result.add(totalwatercahrges);//6
			result.add(totalswcahrges);//7
			result.add(totalmtrcahrges);//8
			result.add(totalpenlaty);//9
			result.add(totalrebate);//10
			result.add(totalwatercahrges+totalswcahrges+totalmtrcahrges);//11


			if(billingLedgerEntity!=null){
				
				closingbalance=billingLedgerEntity.getClose_balance()==null?0:billingLedgerEntity.getClose_balance();
				result.add(closingbalance);//12
				result.add(fromMonthYear);//13
				result.add(billingLedgerEntity.getMonthyearnep());//14
				result.add(billingLedgerEntity.getWater_charges());//15
				result.add(billingLedgerEntity.getSw_charges());//16
				result.add(billingLedgerEntity.getMtr_rent());//17
				
				String month=tomonthYear.substring(4, 6);
				result.add(month);//18
				
				BsmartWaterLogger.logger.info("MonthNepali------------"+month);
			}
			

			result.add(null);//19
			
			long adjtransaction=billPenaltyAdjService.getTransactionPending(connectionNo);
			//System.out.println(adjtransaction+"----------adjtransaction");
			
			if(adjtransaction>0){
				result.add("TA");
			}
			return result;
			
		}
		
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	
	
	
	@RequestMapping(value="/getConsumerDataAndLedgerDataNew/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getConsumerDataAndLedgerDataNew(@PathVariable String connectionNo,HttpServletRequest request){
		
		
		try{
		
			String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String english[]=date.split("-");
			
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
			String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			String[] s=nepalimonthyear.split("-");
			String startmonth=s[0]+""+s[1];
			
			//System.out.println(startmonth+"--startmonth");
				
		    ConsumerMaster consumerMaster=consumerMasterService.findconsumer(connectionNo);
			List<Object> result=new ArrayList<>();
			result.add(consumerMaster);//0
			
			//System.out.println("After Consumer Master");
			
			BillingLedgerEntity billingLedgerEntity=billingLedgerService.findBillingLedgerBasedOnConnectionNo(connectionNo);
			
			//System.out.println("After Billing Ledger");
			
			double totalwatercahrges=0.0;
			double totalswcahrges=0.0;
			double totalmtrcahrges=0.0;
			double totalpenlaty=0.0;
			double totalrebate=0.0;
			double closingbalance=0.0;
			String tomonthYear=null;
			String fromMonthYear=null;
			
			String monthYearNep1=null;
			String monthYearNep2=null;
			String monthYearNep3=null;
			String monthYearNep4=null;
			String monthYearNep5=null;
			String monthYearNep6=null;
			
			List<?> list=billingLedgerService.findBillsByReceiptNull(connectionNo);
			
			//System.out.println("After Penalty Ledger");
			
			String yearc=startmonth.substring(0, 4);
			String monthc=startmonth.substring(4, 6);
			
			if("01".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep2=nmonthYearNep;
				monthYearNep3=""+(Integer.parseInt(nmonthYearNep)-1);
				monthYearNep4=""+(Integer.parseInt(nmonthYearNep)-2);
				monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-3);
				monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-4);
				
			}else if("02".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep3=nmonthYearNep;
				monthYearNep4=""+(Integer.parseInt(nmonthYearNep)-1);
				monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-2);
				monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-3);
			}else if("03".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				monthYearNep3=""+(Integer.parseInt(startmonth)-2);
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep4=nmonthYearNep;
				monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-1);
				monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-2);
			}else if("04".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				monthYearNep3=""+(Integer.parseInt(startmonth)-2);
				monthYearNep4=""+(Integer.parseInt(startmonth)-3);
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep5=nmonthYearNep;
				monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-1);
			}else if("05".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				monthYearNep3=""+(Integer.parseInt(startmonth)-2);
				monthYearNep4=""+(Integer.parseInt(startmonth)-3);
				monthYearNep5=""+(Integer.parseInt(startmonth)-4);
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep6=nmonthYearNep;
			}else{
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				monthYearNep3=""+(Integer.parseInt(startmonth)-2);
				monthYearNep4=""+(Integer.parseInt(startmonth)-3);
				monthYearNep5=""+(Integer.parseInt(startmonth)-4);
				monthYearNep6=""+(Integer.parseInt(startmonth)-5);
			}
			
			
	
			int i=1;
			
			if(!list.isEmpty()){
			
			for (final Iterator<?> ii = list.iterator(); ii.hasNext();) {
					
							Object[] values = (Object[])ii.next();
				
							double waterCharge=values[0]==null?0:(Double)values[0];
							double swCharge=values[1]==null?0:(Double)values[1];
							double mtrrent=values[2]==null?0:(Double)values[2];
							double netamt=values[3]==null?0:(Double)values[3];
							String monthyrnep=values[4]==null?"":(String)values[4];		
	
					
							totalwatercahrges+=waterCharge;
							totalswcahrges+=swCharge;
							totalmtrcahrges+=mtrrent;
			
							double billamount=(waterCharge)+swCharge+(mtrrent);
					
							
							
							
							if(list.size()==i){
								fromMonthYear=monthyrnep;
							}
							
							if(list.size()==1){
								fromMonthYear=monthyrnep;
							}
							
							
							if(billingLedgerEntity!=null){
								
								
								
							  if((monthYearNep1).equalsIgnoreCase(monthyrnep) || (monthYearNep2).equalsIgnoreCase(monthyrnep) || (monthYearNep3).equalsIgnoreCase(monthyrnep)){
								   
								 if(netamt>0){
									  
									 if(billamount>netamt){
										  totalrebate+=(netamt*3)/100;
									 }else{
									      totalrebate+=(billamount*3)/100;
									 }
								   }
								   
							   }
								   
								   
							   else if((monthYearNep4).equalsIgnoreCase(monthyrnep)){
								  
							   }else if((monthYearNep5).equalsIgnoreCase(monthyrnep)){
									   if(netamt>0){
									   
										   if(billamount>netamt){
											      totalpenlaty+=(netamt*10)/100;
										   }else{
										          totalpenlaty+=(billamount*10)/100;
										   }
									   }
							   }else if((monthYearNep6).equalsIgnoreCase(monthyrnep)){
								   if(netamt>0){
									   
									   if(billamount>netamt){
										      totalpenlaty+=(netamt*20)/100;
									   }else{
									          totalpenlaty+=(billamount*20)/100;
									   }
								   }
							   }else{
								   if(netamt>0){
									   if(billamount>netamt){
										      totalpenlaty+=(netamt*50)/100;
									   }else{
									          totalpenlaty+=(billamount*50)/100;
									   }
								   }
							   }
						
							}
						  i++;
				
				
				
			}
			
			}
			
			double penaltypercent=0;
			double rebatepercent=0;
			double lastbalanceAmount=0;
			
			if(billingLedgerEntity!=null){
	
				if(fromMonthYear==null){
					fromMonthYear=billingLedgerEntity.getMonthyearnep();
					
				}
				
				if(tomonthYear==null){
					tomonthYear=billingLedgerEntity.getMonthyearnep();
				}
				
				result.add(billingLedgerEntity);//1
				if(billingLedgerEntity.getRdng_date()!=null){
					String rdng_date=new SimpleDateFormat("dd/MM/yyyy").format(billingLedgerEntity.getRdng_date());
					result.add(rdng_date);//2
				}else{
					result.add(null);//2
				}
				
			}else{
				result.add(null);//2
				
			}
				
			
				result.add(penaltypercent);//3
				result.add(rebatepercent);//4
				result.add(lastbalanceAmount);//5
				
				result.add(totalwatercahrges);//6
				result.add(totalswcahrges);//7
				result.add(totalmtrcahrges);//8
				result.add(totalpenlaty);//9
				result.add(totalrebate);//10
				result.add(totalwatercahrges+totalswcahrges+totalmtrcahrges);//11
	
			if(billingLedgerEntity!=null){
				closingbalance=billingLedgerEntity.getClose_balance()==null?0:billingLedgerEntity.getClose_balance();
				result.add(closingbalance);//12
				result.add(fromMonthYear);//13
				
				result.add(tomonthYear);//14
				
				result.add(billingLedgerEntity.getWater_charges());//15
				result.add(billingLedgerEntity.getSw_charges());//16
				result.add(billingLedgerEntity.getMtr_rent());//17
				
				String month=tomonthYear.substring(4, 6);
				result.add(month);//18
				System.out.println("MonthNepali------------"+month);
				
			}
			
	
			result.add(null);//19
			
			long adjtransaction=billPenaltyAdjService.getTransactionPending(connectionNo);
			System.out.println(adjtransaction+"----------adjtransaction");
			
			long pendingCorr=billPenaltyAdjService.pendingForArrCorr(connectionNo);
			System.out.println("Pending arraer Correction Approve  : --------: "+pendingCorr);
			
			
			if(adjtransaction>0){
				result.add("TA");
			}
			if(pendingCorr>0){
				result.add("ACP");
				System.out.println("Result added");
			}
			
			for (Object object : result) {
				System.out.println(object);
			}
			
			return result;
			
			
			
			
		
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	@RequestMapping(value="/addPaymentEntity", method = {RequestMethod.GET, RequestMethod.POST})
	public String addPaymentEntity(@ModelAttribute("paymentEntity")PaymentEntity paymentEntity,BindingResult bindingResult,Model model,HttpServletRequest request)  {
		try{
		HttpSession session=request.getSession(false);
		String date=request.getParameter("cddate");
		String adjtype=request.getParameter("adjtype");
		String currentNMonth=(String)session.getAttribute("currentNMonth");
		
		String arrear_corr=request.getParameter("arrear_corr")==null?"0": request.getParameter("arrear_corr");
		
		if(date!=null && !"null".equalsIgnoreCase(date) && !"".equalsIgnoreCase(date)){
			try {
				Date date1=new SimpleDateFormat("MM/dd/yyyy").parse((String)request.getParameter("cddate"));
				paymentEntity.setCddate(new java.sql.Date(date1.getTime()));
			} catch (ParseException e) {
				
			}
		}
		
		String branchcode=(String)session.getAttribute("branchcode");
		Integer counterNo=(Integer)session.getAttribute("counterNo");
		
		paymentEntity.setConnectionNo(paymentEntity.getConnectionNo().toUpperCase());
		paymentEntity.setRdate(new Date());
		paymentEntity.setEdate(new Date());
		paymentEntity.setCounterno(""+counterNo);
		paymentEntity.setUser_id((String)session.getAttribute("counterUserName"));
		paymentEntity.setSiteCode(branchcode);
		if(currentNMonth==null){
			
		}else{
			paymentEntity.setMonth_year_nep(Integer.parseInt(currentNMonth));
		}
		
		Date engDate=new Date();
		String engFdate=new SimpleDateFormat("dd-MM-yyyy").format(engDate);
		String englishF[]=engFdate.split("-");
		int cday1=Integer.parseInt(englishF[0]);
		
		if("02".equalsIgnoreCase(englishF[1])){
			cday1=cday1+3;
		}
		
		if("04".equalsIgnoreCase(englishF[1])){
			cday1=cday1+1;
		}
		
		if("06".equalsIgnoreCase(englishF[1])){
			cday1=cday1+1;
		}
		
		if("09".equalsIgnoreCase(englishF[1])){
			cday1=cday1+1;
		}
		
		int cmonth1=Integer.parseInt(englishF[1]);
		int cyear1=Integer.parseInt(englishF[2]);
		String nepalidate1=masterGenericDAO.getNepaliDateFromEnglishDate(cyear1, cmonth1, cday1);
		//System.out.println("new receipt format  : Nepdate :"+nepalidate1);
		
		String [] mynF=nepalidate1.split("-");
		String mynNepali=mynF[1]+mynF[0].substring(2,4);
		
		
		long nReceiptNo=0;
		
		//long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+new SimpleDateFormat("MMYY").format(new Date())+counterNo+"%");
		long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+mynNepali+counterNo+"%",counterNo);
		BsmartWaterLogger.logger.info("New Receipt Number : "+newreceiptno);
		
		if(newreceiptno==0){
			nReceiptNo=Long.parseLong(branchcode+""+mynNepali+counterNo+String.format("%07d", 1));
		}else{
			nReceiptNo=newreceiptno+1;
		}
		
		paymentEntity.setAddedBy((String)session.getAttribute("counterUserName"));
		paymentEntity.setUpdatedBy((String)session.getAttribute("counterUserName"));
		paymentEntity.setRecordtype("ORIGINAL");
		paymentEntity.setReceiptNo(""+nReceiptNo);
		BsmartWaterLogger.logger.info("N Receipt Number : "+nReceiptNo);
		
		if(paymentEntity.getPipe_size()==0.5){
			paymentEntity.setDenoted_by("SA");
		}else{
			paymentEntity.setDenoted_by("THA");
		}
		
		
		if("2222".equalsIgnoreCase(branchcode) || "1117".equalsIgnoreCase(branchcode) || "1119".equalsIgnoreCase(branchcode)){
			
			String mdate=request.getParameter("m_date");
			BsmartWaterLogger.logger.info(mdate+"--mdate");
			if(mdate!=null && !"null".equalsIgnoreCase(mdate) && !"".equalsIgnoreCase(mdate)){
				
				try{
					
					String[] dateArray1 = mdate.split("-"); //YYYY-MM-DD
					Date lastBilledDate_N_to_E = dateConverter.convertBsToAd(dateArray1[2]+dateArray1[1]+dateArray1[0]);
					paymentEntity.setRdate(lastBilledDate_N_to_E);
					
					//System.out.println(lastBilledDate_N_to_E+"lastBilledDate_N_to_E");
					
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
				
			}
		}
		
		double mis_cost=paymentEntity.getMiscellaneous_cost()==null?0:paymentEntity.getMiscellaneous_cost();
		if(mis_cost>0){
			paymentEntity.setRemarks("Card Payment Charges");
		}
		
		try{
		   
			if(paymentEntity.getAmount()>0){
			   paymentService.save(paymentEntity);
			}
			
			
		}catch(Exception e){
			createpayment=0;
			e.printStackTrace();
			return "redirect:/cashCounterPayments";
		}
		
		String bConnectionNo=paymentEntity.getConnectionNo();
		Double bPenalty=paymentEntity.getPenalty();
		Double bRebate=paymentEntity.getRebate();
		Double bOther=paymentEntity.getMiscellaneous_cost();
		String bReceiptNo=paymentEntity.getReceiptNo();
		Double bReceivedAmount=paymentEntity.getAmount();
		Double bFrecamount=paymentEntity.getFrecamount();
		Double bAdvanceRebate=paymentEntity.getAdvance_rebate();
		Double bOldbalance=paymentEntity.getOld_balance();
		Double bill_adj_amount=paymentEntity.getBill_adj_amount()-Double.parseDouble(arrear_corr);
		Double penalty_adj_amount=paymentEntity.getPenalty_adj_amount();

		String schemaName=(String)session.getAttribute("schemaName");
		
		if(paymentEntity.getAmount()>0){
			billingLedgerService.updateBillLedger(bConnectionNo,bPenalty,bRebate,bOther,bReceiptNo,bReceivedAmount,bFrecamount,bAdvanceRebate,bOldbalance,schemaName,paymentEntity.getTo_mon_year(),bill_adj_amount,penalty_adj_amount,paymentEntity.getRdate(),adjtype);
		}
		
		//For Billing Closing Balance
		/*List<BillingLedgerEntity> list=billingLedgerService.findBillsByReceiptNull(paymentEntity.getConnectionNo());
		
		int i=1;
		List<BillingLedgerEntity> billingLedgerList= new ArrayList<>();

		if(!list.isEmpty()){
		
		for(BillingLedgerEntity bill:list){
			System.out.println("Month Year"+bill.getMonthyearnep());
				   
				   if(i==1){
					   bill.setPenalty(paymentEntity.getPenalty());
					   bill.setRebate(paymentEntity.getRebate());
					   bill.setOther(paymentEntity.getMiscellaneous_cost());
					   bill.setReceipt_no(paymentEntity.getReceiptNo());
					   bill.setReceipt_date(new java.sql.Date(new Date().getTime()));
					   bill.setLast_paid_amount(paymentEntity.getAmount());
					   bill.setClose_balance((paymentEntity.getFrecamount()==null?0:paymentEntity.getFrecamount())-(paymentEntity.getAmount()==null?0:paymentEntity.getAmount())-(paymentEntity.getAdvance_rebate()==null?0:paymentEntity.getAdvance_rebate()));
					   //billingLedgerService.update(bill);
				   }else{
					   bill.setReceipt_no(paymentEntity.getReceiptNo());
					   bill.setReceipt_date(new java.sql.Date(new Date().getTime()));
					   //billingLedgerService.update(bill);
				   }
					
				   billingLedgerList.add(bill);
				   
				   i++;
		  }
		
		billingLedgerService.custombatchUpdate(billingLedgerList);

	    }else{
			 BillingLedgerEntity billingLedgerEntity=billingLedgerService.findBillingLedgerBasedOnConnectionNo(paymentEntity.getConnectionNo());
			 if(billingLedgerEntity!=null){
				 billingLedgerEntity.setClose_balance((paymentEntity.getOld_balance()==null?0:paymentEntity.getOld_balance())-(paymentEntity.getAmount()==null?0:paymentEntity.getAmount())-(paymentEntity.getAdvance_rebate()==null?0:paymentEntity.getAdvance_rebate()));
		         billingLedgerService.update(billingLedgerEntity);
			}
		}
*/		
		paymentReceiptNo=paymentEntity.getReceiptNo();
		createpayment=1;
		}catch(Exception e){
			createpayment=0;
			e.printStackTrace();
		}
		return "redirect:/cashCounterPayments";
		
	}
	
	
	
	
	
	
	
	/*---------------------------------------------------Cash Counter  Login-----------------------------------------------*/
	@RequestMapping(value="/cashCounterLogin",method={RequestMethod.POST,RequestMethod.GET})
	public String cashCounterLogin(Model model,HttpServletRequest request){
		//model.addAttribute("mainHead", "Cash Counter Login");
		//model.addAttribute("childHead1", "Configuration");
		//model.addAttribute("cashMasterConfigData",cashCounterConfigService.getcashMasterConfigData());
		return "cashCounter/cashCounterLogin";	
	}
	@RequestMapping(value="/cashCounterLogout",method={RequestMethod.POST,RequestMethod.GET})
	public String cashCounterLogout(Model model,HttpServletRequest request){
		try{ 
		HttpSession session = request.getSession(false);
		 String cashCountUserName=(String)session.getAttribute("counterUserName");
		 if(session!=null){
			UserLog userLog=userLogService.findLogByUserId(cashCountUserName);
			userLog.setLogout_time(new Date());
			userLog.setLogin_sts("OUT");
			userLogService.update(userLog);
			session.invalidate();
		 }
		 return "redirect:/cashCounterLogin";	
		}catch(Exception e){
		 return "redirect:/cashCounterLogin";	
		}
	}
	
	@RequestMapping(value="/getAllCounters/{location}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getAllCounters(@PathVariable String location,HttpServletRequest request) throws ParseException{
		List<?> list=cashCounterConfigService.getAllCounters(location);
		return list;
	}
	
	
	@RequestMapping(value = "/loginValidate", method = {RequestMethod.GET, RequestMethod.POST})
	public String cashMasterLogin(Model model,HttpServletRequest req) throws ParseException  {
		try{
		Integer counter_no=Integer.parseInt(req.getParameter("counter_no"));
		String user_login_name=req.getParameter("user_login_name");  
		String location=req.getParameter("location");
		HttpSession session=req.getSession(true);
		session.setAttribute("schemaName", location);
		
		String ip = req.getHeader("X-Forwarded-For");
		
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
           
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
		
		CashMasterConfig cashMasterConfig= cashCounterConfigService.getConfigDataOnuserName(counter_no,user_login_name);
		String password=req.getParameter("password");  
		
		
		if(cashMasterConfig!=null){
		if(password.equals(cashMasterConfig.getUserMaster().getPassword())){
			
			if("Inactive".equalsIgnoreCase(cashMasterConfig.getUserMaster().getStatus())){
				session.setAttribute("invalidUNPCC","User is not Activated.Please Activate and then try again..");
				String status="User Not Activated";
				saveUserLog(user_login_name,status,ip);
				return "redirect:/cashCounterLogin";
			}
			
			session.setAttribute("cashcounterauthorise", true);
			session.setAttribute("counterNo", counter_no);
			session.setAttribute("counterName", cashMasterConfig.getCounter_name());
			session.setAttribute("counterUserName",user_login_name);
			Object[] userOfficialName=userMasterService.getOfficialName(user_login_name);
			session.setAttribute("counteruserId",cashMasterConfig.getUser_id());
			session.setAttribute("userName", user_login_name);
			
			session.setAttribute("userOfficialName", userOfficialName[0]);
			session.setAttribute("usercdesignation", userOfficialName[1]);

			session.setAttribute("defaultDate",new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
			String str[]=location.split("_");
			session.setAttribute("branch", str[0]);
			
			//Current Nepali Month Year
			String rdate=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
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
			String nepalidate=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
		    
			String nep[]=nepalidate.split("-");
			session.setAttribute("currentNMonth", nep[0]+""+nep[1]);
			
			String sitecode=(String)str[1];
			
			if("1115".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Tripureshwor");
			}else if("1112".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Baneshwor");
			}
			else if("1118".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Lalitpur");
			}
			else if("1116".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Bhaktapur");
			}
			else if("1110".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Mahankalchaur");
			}
			else if("1111".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Maharajganj");
			}
			else if("1113".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Kamaladi");
			}
			else if("1114".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Chettrapati");
			}
			else if("1117".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Thimi");
			}
			else if("1119".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","Kirtipur");
			}
			else if("2222".equalsIgnoreCase(sitecode)){
				session.setAttribute("CBranchName","TestLocation");
			}
			
			Object cashCounterDayClose=cashCounterDayCloseService.checkDayAlreadyClosed(counter_no,new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
			
			if(cashCounterDayClose==null){
				
			}/*else{
				session.setAttribute("counterAClose"," Today Counter No: "+counter_no+" already Closed.You cannot login once Counter is Closed for Today....");
				return "redirect:/cashCounterLogin";
			}*/
			
			String nepaliMonthYr=closeMonthEndService.getLatestMonthYear();
			String yearc=nepaliMonthYr.substring(0, 4);
			String monthc=nepaliMonthYr.substring(4, 6);
			
			if("12".equalsIgnoreCase(monthc)){
				nepaliMonthYr=(Integer.parseInt(yearc)+1)+"00";
			}
			String latestNepaliMonth=""+(Integer.parseInt(nepaliMonthYr)+1);
			session.setAttribute("latestNepaliMonthS",latestNepaliMonth);
			
			
			session.setAttribute("branchcode", str[1]);		
			Date maxpaymentDate=paymentService.getMaxDayForDayClose(counter_no);
			if(maxpaymentDate!=null){
				String date2=new SimpleDateFormat("dd-MM-yyyy").format(maxpaymentDate);
				Date maxdate=new SimpleDateFormat("dd-MM-yyyy").parse(date2);
				CashCounterDayClose cashCounterDayClose126=cashCounterDayCloseService.getMaxDayForDayClose(counter_no,maxdate);
				BsmartWaterLogger.logger.info("cashCounterDayClose126----"+cashCounterDayClose126 +"------------maxdate"+maxpaymentDate+"--counter"+counter_no+"maxdate"+maxdate);
				if(cashCounterDayClose126==null){
					
					String date1=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
					Date currentDate=new SimpleDateFormat("dd-MM-yyyy").parse(date1);
					BsmartWaterLogger.logger.info(maxdate+"==========="+maxdate.compareTo(currentDate)+"=================="+currentDate);
					if(maxdate.compareTo(currentDate)==0){
						session.setAttribute("dayclose",false);
					}else{
						session.setAttribute("dayclose",true);
						session.setAttribute("dayclosedate",maxpaymentDate);
					}
				}else{
					session.setAttribute("dayclose",false);
				}
			}
			if(cashcounterDayClose==1){
				cashcounterDayClose=0;
				return "redirect:/cashCounterDayClose";
			}
			String status="success";
			saveUserLog(user_login_name,status,ip);
			
			//Holiday Master Start
			
				HolidayMaster holidayMaster=holidayMasterService.getRecordByDate(new Date());
				
				if(holidayMaster!=null){
					session.setAttribute("holidayExist","Yes");
				}
				
			//Holiday Master End
				
				
			//Checking Time 9-5
				
				String currentTime=null;
				try{
				  currentTime=new SimpleDateFormat("HH:mm").format(new Date());
				}catch(Exception e){
					
				}
				int limit1 = 0;
				int limit2 = 5000;
				int now=0;
				try{
				   now=Integer.parseInt(currentTime.replaceAll(":", ""));
				}catch(Exception e){
					
				}
				
				if(now>=limit1 && now<=limit2){
				
				}else{
					session.setAttribute("timeoutcounter","Yes");
				}
			//End
			
			return "redirect:/cashCounterPayments";
			
		}else{
			session.setAttribute("invalidUNPCC","Invalid User Name / Password .Please try again..");
			String status="fail";
			saveUserLog(user_login_name,status,ip);
			return "redirect:/cashCounterLogin";
		}
	   }else{
		   String status="fail";
		   saveUserLog(user_login_name,status,ip); 
		   session.setAttribute("invalidUNPCC"," Invalid User Name / Password .Please try again..");
	   }
		
		session.setAttribute("cashcounterauthorise", false);
		return "redirect:/cashCounterLogin";
		}catch(Exception e){
			e.printStackTrace();		
			return "redirect:/cashCounterLogin";

		}
		
	}
	
	private void saveUserLog(String ssoId,String status,String ip)  {
		try{
			UserLog userLog=new UserLog();
			if("success".equalsIgnoreCase(status)){
				userLog.setLogin_sts("IN");
				userLog.setRemarks("Success");
			}else{
				userLog.setLogout_time(new Date());
				userLog.setLogin_sts("OUT");
				userLog.setRemarks("invalid password");
			}
			userLog.setLogin_type("Cash Counter");
			userLog.setUser_id(ssoId);
			userLog.setLogin_time(new Date());
			userLog.setIp_address(ip);
			userLogService.save(userLog);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*=================================================Day Close==================================================*/
	@RequestMapping(value="/cashCounterDayClose",method={RequestMethod.POST,RequestMethod.GET})
	public String cashCounterDayClose(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Day Close");
		model.addAttribute("childHead1", "Configuration");
		HttpSession session=request.getSession(false);
		if(session!=null && session.getAttribute("cashcounterauthorise")!=null && session.getAttribute("cashcounterauthorise").equals(true)){
			Integer counterno=(Integer)session.getAttribute("counterNo");
			String counterName=(String)session.getAttribute("counterName");
			model.addAttribute("counterno",counterno);
			model.addAttribute("counterName",counterName);
			
			Object dayclose=session.getAttribute("dayclose");
			if(dayclose!=null && ((boolean)dayclose)==true){
				Date closedate=(Date)session.getAttribute("dayclosedate");
				String dayCloseDate=new SimpleDateFormat("MM/dd/yyyy").format(closedate);
				model.addAttribute("msgdayclose","Please Close Previous Day Cash Counter Day Close");
				model.addAttribute("dayclosedate",dayCloseDate);
				List<?> list=paymentService.getdataforDayClose(counterno,closedate);
				model.addAttribute("datalist",list);
			
			}else{
				String dayCloseDate=new SimpleDateFormat("MM/dd/yyyy").format(new Date());
				model.addAttribute("dayclosedate",dayCloseDate);
				List<?> list=paymentService.getdataforDayClose(counterno,new Date());
				model.addAttribute("datalist",list);
			}
		return "cashCounter/cashCounterDayClose";	
		
		
		
		}else{
			cashcounterDayClose=1;
			return "redirect:/cashCounterLogin";	
		}
	}
	@RequestMapping(value="/getdataforDayClose",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getdataforDayClose(@RequestParam Integer counterno,@RequestParam String counterdate,HttpServletRequest request) throws ParseException{
	List<?> result=paymentService.getdataforDayClose(counterno,new SimpleDateFormat("MM/dd/yyyy").parse(counterdate));
		return result;
	}
	@RequestMapping(value="/closeReceiptForForDay",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object closeReceiptForForDay(@RequestParam Integer counter_no,@RequestParam String counter_name,@RequestParam String cddate,@RequestParam Double amount_in_hand,@RequestParam Double diff,@RequestParam Double totalAmount,HttpServletRequest request) throws ParseException{
		
		
		CashCounterDayClose cashCounterDayClose=new CashCounterDayClose();
		cashCounterDayClose.setAmount_in_hand(amount_in_hand);
		cashCounterDayClose.setCounter_no(counter_no);
		cashCounterDayClose.setCountername(counter_name);
		Date date1=new SimpleDateFormat("MM/dd/yyy").parse(cddate);
		cashCounterDayClose.setDayclosedate(date1);
		cashCounterDayClose.setTotalamount(totalAmount);
		HttpSession session=request.getSession(false);
		session.setAttribute("dayclose",false);
		String cashCountUserName=(String)session.getAttribute("counterUserName");
		cashCounterDayClose.setUsername(cashCountUserName);
		cashCounterDayClose.setCreatedBy(cashCountUserName);
	
		
		//passing data to swagger api
		
		try{
			String branchcode=(String)session.getAttribute("branchcode");
			
			//String user=(String)session.getAttribute("user");
			
			if(branchcode.equalsIgnoreCase("2222") ){
				
				JSONObject json=new JSONObject();
				try{
					
					json.put("tranNo", counter_no);
					json.put("officeCode", branchcode);
					json.put("costCenter", counter_no);
					json.put("fiscalYear","207512");
					json.put("tranDate", cddate);
					json.put("voucherType", 8);
					json.put("voucherCategory", 8);
					json.put("voucherNumber",String.valueOf(counter_no));
					json.put("drCr",String.valueOf(totalAmount));
					json.put("amount",String.valueOf(amount_in_hand));
					json.put("description","day close data");
					json.put("naration","Testlocation");
					json.put("tranSeq",6);
					json.put("checkNumber",branchcode+String.valueOf(counter_no));
					json.put("bankName",counter_name);
					json.put("isDayClose",1);
					json.put("entryBy",cashCountUserName);
					json.put("entryDate",cddate);
					
					callSecureWeb("/api/AdvanceIncome", json.toString());	
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			
      if(branchcode.equalsIgnoreCase("1118") ){
				
				JSONObject json=new JSONObject();
				try{
					
					json.put("tranNo", counter_no);
					json.put("officeCode", branchcode);
					json.put("costCenter", counter_no);
					json.put("fiscalYear","207512");
					json.put("tranDate", cddate);
					json.put("voucherType", 8);
					json.put("voucherCategory", 8);
					json.put("voucherNumber",String.valueOf(counter_no));
					json.put("drCr",String.valueOf(totalAmount));
					json.put("amount",String.valueOf(amount_in_hand));
					json.put("description","day close data");
					json.put("naration","LALITPUR");
					json.put("tranSeq",6);
					json.put("checkNumber",branchcode+String.valueOf(counter_no));
					json.put("bankName",counter_name);
					json.put("isDayClose",1);
					json.put("entryBy",cashCountUserName);
					json.put("entryDate",cddate);
					
					callSecureWeb("/api/AdvanceIncome", json.toString());	
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
  if(branchcode.equalsIgnoreCase("1119") ){
	
	JSONObject json=new JSONObject();
	try{
		
		json.put("tranNo", counter_no);
		json.put("officeCode", branchcode);
		json.put("costCenter", counter_no);
		json.put("fiscalYear","207512");
		json.put("tranDate", cddate);
		json.put("voucherType", 8);
		json.put("voucherCategory", 8);
		json.put("voucherNumber",String.valueOf(counter_no));
		json.put("drCr",String.valueOf(totalAmount));
		json.put("amount",String.valueOf(amount_in_hand));
		json.put("description","day close data");
		json.put("naration","KRITIPUR");
		json.put("tranSeq",6);
		json.put("checkNumber",branchcode+String.valueOf(counter_no));
		json.put("bankName",counter_name);
		json.put("isDayClose",1);
		json.put("entryBy",cashCountUserName);
		json.put("entryDate",cddate);
		
		callSecureWeb("/api/AdvanceIncome", json.toString());	
	}catch(Exception e){
		e.printStackTrace();
	}
	
}
  
  if(branchcode.equalsIgnoreCase("1116") ){
		
		JSONObject json=new JSONObject();
		try{
			
			json.put("tranNo", counter_no);
			json.put("officeCode", branchcode);
			json.put("costCenter", counter_no);
			json.put("fiscalYear","207512");
			json.put("tranDate", cddate);
			json.put("voucherType", 8);
			json.put("voucherCategory", 8);
			json.put("voucherNumber",String.valueOf(counter_no));
			json.put("drCr",String.valueOf(totalAmount));
			json.put("amount",String.valueOf(amount_in_hand));
			json.put("description","day close  data");
			json.put("naration","BHAKTAPUR");
			json.put("tranSeq",6);
			json.put("checkNumber",branchcode+String.valueOf(counter_no));
			json.put("bankName",counter_name);
			json.put("isDayClose",1);
			json.put("entryBy",cashCountUserName);
			json.put("entryDate",cddate);
			
			callSecureWeb("/api/AdvanceIncome", json.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
  
  if(branchcode.equalsIgnoreCase("1117") ){
		
		JSONObject json=new JSONObject();
		try{
			
			json.put("tranNo", counter_no);
			json.put("officeCode", branchcode);
			json.put("costCenter", counter_no);
			json.put("fiscalYear","207512");
			json.put("tranDate", cddate);
			json.put("voucherType", 8);
			json.put("voucherCategory", 8);
			json.put("voucherNumber",String.valueOf(counter_no));
			json.put("drCr",String.valueOf(totalAmount));
			json.put("amount",String.valueOf(amount_in_hand));
			json.put("description","day close data");
			json.put("naration","THIMI");
			json.put("tranSeq",6);
			json.put("checkNumber",branchcode+String.valueOf(counter_no));
			json.put("bankName",counter_name);
			json.put("isDayClose",1);
			json.put("entryBy",cashCountUserName);
			json.put("entryDate",cddate);
			
			callSecureWeb("/api/AdvanceIncome", json.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
  if(branchcode.equalsIgnoreCase("1115") ){
		
		JSONObject json=new JSONObject();
		try{
			
			json.put("tranNo", counter_no);
			json.put("officeCode", branchcode);
			json.put("costCenter", counter_no);
			json.put("fiscalYear","207512");
			json.put("tranDate", cddate);
			json.put("voucherType", 8);
			json.put("voucherCategory", 8);
			json.put("voucherNumber",String.valueOf(counter_no));
			json.put("drCr",String.valueOf(totalAmount));
			json.put("amount",String.valueOf(amount_in_hand));
			json.put("description","day close data");
			json.put("naration","TRIPUR");
			json.put("tranSeq",6);
			json.put("checkNumber",branchcode+String.valueOf(counter_no));
			json.put("bankName",counter_name);
			json.put("isDayClose",1);
			json.put("entryBy",cashCountUserName);
			json.put("entryDate",cddate);
			
			callSecureWeb("/api/AdvanceIncome", json.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
  if(branchcode.equalsIgnoreCase("1111") ){
		
		JSONObject json=new JSONObject();
		try{
			
			json.put("tranNo", counter_no);
			json.put("officeCode", branchcode);
			json.put("costCenter", counter_no);
			json.put("fiscalYear","207512");
			json.put("tranDate", cddate);
			json.put("voucherType", 8);
			json.put("voucherCategory", 8);
			json.put("voucherNumber",String.valueOf(counter_no));
			json.put("drCr",String.valueOf(totalAmount));
			json.put("amount",String.valueOf(amount_in_hand));
			json.put("description","day close random data");
			json.put("naration","MAHARAJGUNJ");
			json.put("tranSeq",6);
			json.put("checkNumber",branchcode+String.valueOf(counter_no));
			json.put("bankName",counter_name);
			json.put("isDayClose",1);
			json.put("entryBy",cashCountUserName);
			json.put("entryDate",cddate);
			
			callSecureWeb("/api/AdvanceIncome", json.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
  if(branchcode.equalsIgnoreCase("1110") ){
		
		JSONObject json=new JSONObject();
		try{
			
			json.put("tranNo", counter_no);
			json.put("officeCode", branchcode);
			json.put("costCenter", counter_no);
			json.put("fiscalYear","207512");
			json.put("tranDate", cddate);
			json.put("voucherType", 8);
			json.put("voucherCategory", 8);
			json.put("voucherNumber",String.valueOf(counter_no));
			json.put("drCr",String.valueOf(totalAmount));
			json.put("amount",String.valueOf(amount_in_hand));
			json.put("description","day close random data");
			json.put("naration","MAHANKACHUR");
			json.put("tranSeq",6);
			json.put("checkNumber",branchcode+String.valueOf(counter_no));
			json.put("bankName",counter_name);
			json.put("isDayClose",1);
			json.put("entryBy",cashCountUserName);
			json.put("entryDate",cddate);
			
			callSecureWeb("/api/AdvanceIncome", json.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
  
  if(branchcode.equalsIgnoreCase("1113") ){
		
		JSONObject json=new JSONObject();
		try{
			
			json.put("tranNo", counter_no);
			json.put("officeCode", branchcode);
			json.put("costCenter", counter_no);
			json.put("fiscalYear","207512");
			json.put("tranDate", cddate);
			json.put("voucherType", 8);
			json.put("voucherCategory", 8);
			json.put("voucherNumber",String.valueOf(counter_no));
			json.put("drCr",String.valueOf(totalAmount));
			json.put("amount",String.valueOf(amount_in_hand));
			json.put("description","day close data");
			json.put("naration","KAMALADI");
			json.put("tranSeq",6);
			json.put("checkNumber",branchcode+String.valueOf(counter_no));
			json.put("bankName",counter_name);
			json.put("isDayClose",1);
			json.put("entryBy",cashCountUserName);
			json.put("entryDate",cddate);
			
			callSecureWeb("/api/AdvanceIncome", json.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
  
  if(branchcode.equalsIgnoreCase("1112") ){
		
		JSONObject json=new JSONObject();
		try{
			
			json.put("tranNo", counter_no);
			json.put("officeCode", branchcode);
			json.put("costCenter", counter_no);
			json.put("fiscalYear","207512");
			json.put("tranDate", cddate);
			json.put("voucherType", 8);
			json.put("voucherCategory", 8);
			json.put("voucherNumber",String.valueOf(counter_no));
			json.put("drCr",String.valueOf(totalAmount));
			json.put("amount",String.valueOf(amount_in_hand));
			json.put("description","day close data");
			json.put("naration","BANESHWAR");
			json.put("tranSeq",6);
			json.put("checkNumber",branchcode+String.valueOf(counter_no));
			json.put("bankName",counter_name);
			json.put("isDayClose",1);
			json.put("entryBy",cashCountUserName);
			json.put("entryDate",cddate);
			
			callSecureWeb("/api/AdvanceIncome", json.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
  
  if(branchcode.equalsIgnoreCase("1114") ){
		
		JSONObject json=new JSONObject();
		try{
			
			json.put("tranNo", counter_no);
			json.put("officeCode", branchcode);
			json.put("costCenter", counter_no);
			json.put("fiscalYear","207512");
			json.put("tranDate", cddate);
			json.put("voucherType", 8);
			json.put("voucherCategory", 8);
			json.put("voucherNumber",String.valueOf(counter_no));
			json.put("drCr",String.valueOf(totalAmount));
			json.put("amount",String.valueOf(amount_in_hand));
			json.put("description","day close random data");
			json.put("naration","CHHETRAPATI");
			json.put("tranSeq",6);
			json.put("checkNumber",branchcode+String.valueOf(counter_no));
			json.put("bankName",counter_name);
			json.put("isDayClose",1);
			json.put("entryBy",cashCountUserName);
			json.put("entryDate",cddate);
			
			callSecureWeb("/api/AdvanceIncome", json.toString());	
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

		}catch(Exception e){
			e.getMessage();
		}
		
		
		
		try{
			cashCounterDayCloseService.save(cashCounterDayClose);
			String date=new SimpleDateFormat("dd-MMM-yyyy").format(date1);
			return date;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		
		
	}
	// calling webservice to swagger api
	@SuppressWarnings({ "deprecation", "resource" })
	public  String callSecureWeb(String url,String jsonparam) {
		try{
			StringEntity input = new StringEntity(jsonparam);
			HttpClient client  = new DefaultHttpClient();
			HttpPost httpRequest = new HttpPost(calledIP + url);
			httpRequest.setHeader("Content-Type", "application/json");
			httpRequest.setEntity(input);
			HttpResponse response = client.execute(httpRequest);
			String resoponce = new BasicResponseHandler().handleResponse(response);
			
			System.out.println("Res " + response.getStatusLine());
			System.out.println();
			return resoponce;
		}
		catch(Exception e){
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	
	
	
	
	
	@RequestMapping(value="/checkCloseReceiptForForDay",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody String closeReceiptForForDay(HttpServletRequest request){
		
		int counter_no=Integer.parseInt(request.getParameter("counter_no"));
		String counterdate1=request.getParameter("counterdate1");
		
		Object cashCounterDayClose=cashCounterDayCloseService.checkDayAlreadyClosed(counter_no,counterdate1);
		
		if(cashCounterDayClose==null){
			return "0";
		}else{
			return "1";
		}
	}
	
	
	@RequestMapping(value="/dayClosureReport",method={RequestMethod.POST,RequestMethod.GET})
	public String dayClosureReport(Model model,HttpServletRequest request){
		return "cashCounter/dayCloseReport";	
	}
	@RequestMapping(value="/closeAdminReport",method={RequestMethod.POST,RequestMethod.GET})
	public String adminReportController(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Cash Counter");
		model.addAttribute("childHead1", "Configuration");
		List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
		return "cashCounter/closeAdminReport";	
	}
	@RequestMapping(value="/generateDayClosureReport",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object generateDayClosureReport(@RequestParam String date,HttpServletRequest request)
	{
		BsmartWaterLogger.logger.info("==== date ====>"+date);
		Integer counterNo = null;
		try {
			 counterNo = (Integer)request.getSession(false).getAttribute("counterNo");
			 //System.out.println("counter no "+counterNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<?> values = paymentService.getDayClosePaymentDetails1(counterNo, date);
		
		return values;
	}
	
	@RequestMapping(value="/generateDayCloseMisc",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object generateDayCloseMisc(@RequestParam String date,HttpServletRequest request)
	{
		
		Integer counterNo = null;
		try {
			 counterNo = (Integer)request.getSession(false).getAttribute("counterNo");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<?> values = paymentService.generateDayCloseMisc(counterNo, date);
		
		return values;
	}
	
	
	@RequestMapping(value="/generateDayClosureReportForAdmin/{counter_no}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object generateDayClosureReportForAdmin(@RequestParam String date,@PathVariable Integer counter_no,HttpServletRequest request)
	{
		//System.out.println("==== date ====>"+date);
		Integer counterNo = counter_no;
		List<Object> data=new ArrayList<>();
		
		Object[] str=cashCounterConfigService.findUserByCounterNo(counterNo); 
		
		List<?> values = paymentService.getDayClosePaymentDetails1(counterNo, date);
		
		if(str!=null){
			data.add(values);
			data.add(str[0]);
			data.add(str[1]);
		}else{
			data.add(values);
			data.add("");
			data.add("");
		}
		
		return data;
	}
	
	
	@RequestMapping(value="/numberToWords",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object numberToWords(@RequestParam String date,HttpServletRequest request)
	{
		//System.out.println(" numberToWords ===> "+date);
		String s = String.valueOf(date);
		String finalWordings = "";
		String wordings1 = "";
		String wordings2 = "";
		if(s.contains("."))
		{
			String s1 = s.replace(".","-");
			String[] str = s1.split("-");
			//System.out.println(""+str[0]);
			//System.out.println(""+str[1]);
			Integer firstPart = Integer.parseInt(str[0]);
			Integer secondPart = Integer.parseInt(str[1]);
			wordings1 = NumberToWords.convert(firstPart);
			wordings2 = NumberToWords.convert(secondPart);
			finalWordings = wordings1 +" Rupees and "+wordings2+" Paisa Only.";
		}
		else
		{
			wordings1 = NumberToWords.convert(Integer.parseInt(date));
			finalWordings = wordings1 +" Rupees Only.";
		}
		//System.out.println(" final ===> "+wordings1 +" Rupees and "+wordings2+" Paisa Only ");
		return finalWordings;
	}
	
	
	 /*======================================================cancel receipt=============================================*/
	@RequestMapping(value="/cancelReceipt",method={RequestMethod.POST,RequestMethod.GET})
	public String cancelReceipt(Model model,HttpServletRequest request){
		return "cashCounter/cancelReceipt";	
	}
	
	@RequestMapping(value="/receiptCancel",method={RequestMethod.POST,RequestMethod.GET})
	public String adminCancelReceiptController(Model model,HttpServletRequest request,HttpSession session){
		model.addAttribute("mainHead", "Cash Counter");
		model.addAttribute("childHead1", "Configuration");
	   String designation=(String) session.getAttribute("userbdesignation");
	   if(designation.equalsIgnoreCase("Counter Incharge") ||  designation.equalsIgnoreCase("Revenue Incharge")  || designation.equalsIgnoreCase("Developer") || designation.equalsIgnoreCase("Asst Revenue Incharge") )
	   {
		List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
	   }
	   else {
		   return "redirect:/login";
	   }
		return "cashCounter/adminCancelReceipt";	
	}
	
	
	@RequestMapping(value="/misReceiptCancel",method={RequestMethod.POST,RequestMethod.GET})
	public String misReceiptCancel(Model model,HttpServletRequest request){
		model.addAttribute("mainHead", "Cash Counter");
		model.addAttribute("childHead1", "Configuration");
		List<CounterDetails> counter = counterDetailsService.getAllCounterRecords();
		model.addAttribute("counter", counter);
		return "cashCounter/misCancelReceipt";	
	}
	
	
	
	
	
	
	
	@RequestMapping(value="/searchReceiptNo/{receiptno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object searchConnNo(@PathVariable String receiptno,HttpServletRequest request)
	{
		//System.out.println("====searchConnNo====>"+receiptno);
		PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(receiptno);
		List<Object> data=new ArrayList<>();
		if(paymentdata!=null){
			data.add("Receipt Exist");//0
			data.add(paymentdata);//1
			ConsumerMaster consumerMaster=consumerMasterService.findconsumer(paymentdata.getConnectionNo());
			//BillingLedgerEntity billingLedgerEntity=billingLedgerService.findBillingLedgerBasedOnConnectionNo(paymentdata.getConnectionNo());
			
			data.add(consumerMaster);//2
			//data.add(billingLedgerEntity);//3
			
			String rdate=new SimpleDateFormat("dd-MM-yyyy").format(paymentdata.getRdate());
			String english[]=rdate.split("-");
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
			//System.out.println(cday+"cday"+cmonth+"cmonth"+cyear);
			String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			//System.out.println(nepalimonthyear+"===============nepalimonthyear");
			data.add(nepalimonthyear);//3
			//data.add(new SimpleDateFormat("dd-MM-yyyy").format(paymentdata.getRdate()));//3

		}else{
			data.add("Receipt Doesn't Exist");
		}
		return data;

	}
	
	@RequestMapping(value="/searchReceiptNoBoard/{receiptno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object searchReceiptNoBoard(@PathVariable String receiptno,HttpServletRequest request)
	{
		PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(receiptno);
		List<Object> data=new ArrayList<>();
		
		if(paymentdata!=null){
			data.add("Receipt Exist");//0
			data.add(paymentdata);//1
			Object[] board=boardInstallmentService.getBoardInstallment(paymentdata.getConnectionNo(),receiptno);
			data.add(board);//2
			String rdate=new SimpleDateFormat("dd-MM-yyyy").format(paymentdata.getRdate());
			String english[]=rdate.split("-");
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
			//System.out.println(cday+"cday"+cmonth+"cmonth"+cyear);
			String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			data.add(nepalimonthyear);//3

		}else{
			data.add("Receipt Doesn't Exist");
		}
		return data;

	}

	
	
	@RequestMapping(value="/adminSearchReceiptNo/{counter_no}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object adminSearchReceiptNo(@PathVariable Integer counter_no,@RequestParam String searchdate,HttpServletRequest request) throws ParseException
	{
		//System.out.println(counter_no+"====searchConnNo====>"+searchdate);
		
		Date date=new SimpleDateFormat("MM/dd/yyyy").parse(searchdate);
		//System.out.println(date+"========================");
		String dayClose=cashCounterDayCloseService.getDayCloseDataOncounterNoAndDate(counter_no,date);
		//System.out.println(dayClose+"==========================");
		List<Object> data=new ArrayList<>();
		if(dayClose.equalsIgnoreCase("Can Get Data")){
			List<?> paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(new SimpleDateFormat("dd/MM/yyyy").format(date),counter_no);
			data.add(dayClose);
			data.add(paymentdata);
		}else{
			data.add(dayClose);
		}
		
		return data;
		
	}
	@RequestMapping(value="/adminSearchReceiptNoNew/{counter_no}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object adminSearchReceiptNoNew(@PathVariable Integer counter_no,@RequestParam String searchdate,HttpServletRequest request) throws ParseException
	{
		//System.out.println(counter_no+"====searchConnNo====>"+searchdate);
		
		Date date=new SimpleDateFormat("MM/dd/yyyy").parse(searchdate);
		//System.out.println(date+"========================");
		String dayClose=cashCounterDayCloseService.getDayCloseDataOncounterNoAndDate(counter_no,date);
		//System.out.println(dayClose+"==========================");
		List<Object> data=new ArrayList<>();
		if(dayClose.equalsIgnoreCase("Can Get Data")){
			List<?> paymentdata=paymentService.getPaymentEntityBasedOnReceiptNoNew(new SimpleDateFormat("dd/MM/yyyy").format(date),counter_no);
			data.add(dayClose);
			data.add(paymentdata);
		}else{
			data.add(dayClose);
		}
		
		return data;
		
	}
	
	@RequestMapping(value="/getMisReceiptDetails",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getMisReceiptDetails(HttpServletRequest request,HttpServletResponse response) throws ParseException
	{
		
		String receiptNo=request.getParameter("receiptNo");
		String da=new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(da);
		
		
        PaymentEntity payment= paymentService.getMisReceiptDetails(receiptNo,date1);
        if(payment!=null) {
        	System.out.println(payment.getBillno());
    		Date rdate=payment.getRdate();
    		
    		if (rdate == null) {
    			
    		} else {
    			String date = new SimpleDateFormat("dd-MM-yyyy").format(rdate);
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
    			int cmonth = Integer.parseInt(english[1]);
    			int cyear = Integer.parseInt(english[2]);
    			String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
    			Date newRdate=new SimpleDateFormat("yyyy-MM-dd").parse(nepalimonthyear);
    			System.out.println("nepali dt string : "+nepalimonthyear);
    			System.out.println("rdate dt : "+newRdate);
    			payment.setRdate(newRdate);
    		}
        }
		
		
		
		return payment;
		
	}
	
	
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value="/cancelReceipt/{connectionno}/{receiptno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object searchConnNo(@PathVariable String connectionno,@PathVariable String receiptno,HttpServletRequest request)
	{
		HttpSession session=request.getSession(false);
		//System.out.println("====searchConnNo====>"+receiptno);
		PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(receiptno);
		List<Object> data=new ArrayList<>();
		
		if(paymentdata!=null){
			
			long paymentcount=paymentService.getTotalPaymentsByConNoMYN(paymentdata.getConnectionNo(),paymentdata.getTo_mon_year());
			
			//System.out.println(paymentcount+"----paymentcount");
			
			if(paymentcount>1){
			
				List<Object[]> obj=paymentService.getLastPaymentDetailsByCNNoMYN(paymentdata.getConnectionNo(),paymentdata.getTo_mon_year());
				
				BillingLedgerEntity bill=billingLedgerService.getLatestRecordByConnectionNo(paymentdata.getConnectionNo());
				
				
				PaymentEntity paymentEntity = saveCancelledPayment(session, paymentdata);
				
				try{
				
				paymentService.customupdate(paymentdata);
				paymentService.save(paymentEntity);
				
				
				for (Iterator<?> iterator = obj.iterator(); iterator.hasNext();) {
					Object[] objects = (Object[]) iterator.next();
					
					String recno=(String) objects[2];
					Date rdate=(Date) objects[3];
					double amount=((java.math.BigDecimal)objects[4]).doubleValue();
					double penalty=((java.math.BigDecimal)objects[5]).doubleValue();
					double rebate=((java.math.BigDecimal)objects[6]).doubleValue();
					double rbalance=((java.math.BigDecimal)objects[7]).doubleValue();
					
					
					if(bill!=null){
							
							//System.out.println("inside  cancel bill More than one Payment..");
							bill.setReceipt_no(recno);
							bill.setLast_paid_amount(amount);
							bill.setReceipt_date(rdate);
							bill.setClose_balance(rbalance);
							bill.setPenalty(penalty);
							bill.setRebate(rebate);
							billingLedgerService.update(bill);
							data.add("Receipt No: "+receiptno+" Cancelled Successfully");
					}
				}
				}catch(Exception e){
					e.printStackTrace();
					data.add("Unable to Cancel Receipt No : "+receiptno);
				}
				
			}else{
				
				
				PaymentEntity paymentEntity = saveCancelledPayment(session, paymentdata);
				try{
				paymentService.customupdate(paymentdata);
				paymentService.save(paymentEntity);
				
				
				BillingLedgerEntity bill=billingLedgerService.getLatestRecordByConnectionNo(paymentdata.getConnectionNo());
				
				//System.out.println("bill Last Paid Amount"+bill.getLast_paid_amount());
				//System.out.println("Payment Paid Amount"+paymentdata.getAmount());
				if(bill!=null){
					//if(paymentdata.getAmount()==bill.getLast_paid_amount()){
						
						//System.out.println("inside  cancel bill");
						bill.setLast_paid_amount(null);
						bill.setReceipt_date(null);
						bill.setClose_balance(null);
						billingLedgerService.update(bill);
					//}
				}
				
				List<BillingLedgerEntity> billlist=billingLedgerService.getListByConNoAndRECNo(paymentdata.getConnectionNo(),paymentdata.getReceiptNo());
				/*List<BillingLedgerEntity> billingLedgerList= new ArrayList<>();
				int i=1;
				System.out.println(billlist.size()+"======================================");
				if(!billlist.isEmpty()){
					
					for(BillingLedgerEntity b:billlist){
						
						if(i==1){
							
							b.setClose_balance(
									 (b.getClose_balance()==null?0:b.getClose_balance()
									-((paymentdata.getFrecamount()==null?0:paymentdata.getFrecamount())
									-(paymentdata.getAmount()==null?0:paymentdata.getAmount())
									-(paymentdata.getAdvance_rebate()==null?0:paymentdata.getAdvance_rebate()))));
							b.setLast_paid_amount(null);
							b.setReceipt_no(null);
							b.setReceipt_date(null);
							//billingLedgerService.update(b);
						}else{
							b.setReceipt_no(null);
							b.setReceipt_date(null);
							b.setClose_balance(null);
							b.setLast_paid_amount(null);
							//billingLedgerService.update(b);
						}
						billingLedgerList.add(b);
						i++;
					}
					billingLedgerService.custombatchUpdate(billingLedgerList);
					
					}*/
					//billingLedgerService.custombatchUpdate(billingLedgerList);

				    

				    //int updatedcount=billingLedgerService.custombatchUpdateBillingLedgerEntity(billlist,paymentdata);
					//System.out.println(updatedcount+"=================="+billlist.size());
					
					ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

					UpdateAsynchronous updateasynchronous = context.getBean(UpdateAsynchronous.class);
					//System.out.println("about to run");
					String schemaName=(String)session.getAttribute("schemaName");
					Future<Boolean> future = updateasynchronous.cancelReceiptAsynchronousUpdate(paymentdata, billlist,billingLedgerService,schemaName);
					//System.out.println("this will run immediately.");
					
					data.add("Receipt No: "+receiptno+" Cancelled Successfully");
				}

				
				/*BillingLedgerEntity billingLedger=billingLedgerService.findMaxRecordNotNullReceipt(paymentdata.getConnectionNo());
				if(billingLedger!=null){
					billingLedger.setClose_balance(
							 (billingLedger.getClose_balance()==null?0:billingLedger.getClose_balance()
							-((paymentdata.getFrecamount()==null?0:paymentdata.getFrecamount())
							-(paymentdata.getAmount()==null?0:paymentdata.getAmount())
							-(paymentdata.getAdvance_rebate()==null?0:paymentdata.getAdvance_rebate()))));
					
					billingLedgerService.update(billingLedger);
					
					
					
				}*/
				catch(Exception e){
					e.printStackTrace();
					data.add("Unable to Cancel Receipt No : "+receiptno);
				}
			}
		}
		return data;
		

		
	}
	
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value="/cancelReceiptNew/{connectionno}/{receiptno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object cancelReceiptNew(@PathVariable String connectionno,@PathVariable String receiptno,HttpServletRequest request)
	{
		HttpSession session=request.getSession(false);
		PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(receiptno);
		List<Object> data=new ArrayList<>();
		
		if(paymentdata!=null){
			
			long paymentcount=paymentService.getTotalPaymentsByConNoMYN(paymentdata.getConnectionNo(),paymentdata.getTo_mon_year());
			
			if(paymentcount>1){
				
				PaymentEntity paymentEntity = saveCancelledPayment(session, paymentdata);
				
			try{
				paymentService.customupdate(paymentdata);
				paymentService.save(paymentEntity);
			
				List<Object[]> obj=paymentService.getLastPaymentDetailsByCNNoMYN1(paymentdata.getConnectionNo(),paymentdata.getTo_mon_year());
				BillingLedgerEntity bill=billingLedgerService.getBillByConNoAndMY1(paymentdata.getConnectionNo(),paymentdata.getTo_mon_year());
				
				for (Iterator<?> iterator = obj.iterator(); iterator.hasNext();) {
					Object[] objects = (Object[]) iterator.next();
					
					String recno=(String) objects[2];
					Date rdate=(Date) objects[3];
					double amount=((java.math.BigDecimal)objects[4]).doubleValue();
					double penalty=((java.math.BigDecimal)objects[5]).doubleValue();
					double rebate=((java.math.BigDecimal)objects[6]).doubleValue();
					double rbalance=((java.math.BigDecimal)objects[7]).doubleValue();
					double misc=((java.math.BigDecimal)objects[7]).doubleValue();
					
					if(bill!=null){
						bill.setReceipt_no(recno);
						bill.setLast_paid_amount(amount);
						bill.setReceipt_date(rdate);
						bill.setOther(misc);
						bill.setClose_balance(rbalance);
						bill.setPenalty(penalty);
						bill.setRebate(rebate);
						billingLedgerService.update(bill);
						data.add("Receipt No: "+receiptno+" Cancelled Successfully");
					}
				}
				}catch(Exception e){
					e.printStackTrace();
					data.add("Unable to Cancel Receipt No : "+receiptno);
				}
				
			}else{
				
				PaymentEntity paymentEntity = saveCancelledPayment(session, paymentdata);
				try{
				paymentService.customupdate(paymentdata);
				paymentService.save(paymentEntity);
				
				
				BillingLedgerEntity bill=billingLedgerService.getLatestRecordByConnectionNo(paymentdata.getConnectionNo());
				
				if(bill!=null){
						bill.setLast_paid_amount(null);
						bill.setReceipt_date(null);
						bill.setClose_balance(null);
						billingLedgerService.update(bill);
				}
				
				List<BillingLedgerEntity> billlist=billingLedgerService.getListByConNoAndRECNo(paymentdata.getConnectionNo(),paymentdata.getReceiptNo());
				ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
				UpdateAsynchronous updateasynchronous = context.getBean(UpdateAsynchronous.class);
				String schemaName=(String)session.getAttribute("schemaName");
				Future<Boolean> future = updateasynchronous.cancelReceiptAsynchronousUpdate(paymentdata, billlist,billingLedgerService,schemaName);
				data.add("Receipt No: "+receiptno+" Cancelled Successfully");
				}

				catch(Exception e){
					e.printStackTrace();
					data.add("Unable to Cancel Receipt No : "+receiptno);
				}
			}
		}
		return data;
		
	}
	
	
	
	
	
	
	
	private PaymentEntity saveCancelledPayment(HttpSession session,PaymentEntity paymentdata) {
		
		String userName=(String)session.getAttribute("userName");
		paymentdata.setCancelledremarks("CANCELLED");
		paymentdata.setCancelled_by(userName);
		paymentdata.setCancelled_date(new Timestamp(new java.util.Date().getTime()));
		
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
		paymentEntity.setMonth_year_nep(paymentdata.getMonth_year_nep());
		paymentEntity.setCancelledremarks("CANCELLED");
		paymentEntity.setRecordtype("DUPLICATE");
		paymentEntity.setPipe_size(paymentdata.getPipe_size());
		paymentEntity.setDenoted_by(paymentdata.getDenoted_by());
		paymentEntity.setWard_no(paymentdata.getWard_no());
		paymentEntity.setReading_day(paymentdata.getReading_day());
		paymentEntity.setCancelled_by(userName);
		paymentEntity.setCancelled_date(new Timestamp(new java.util.Date().getTime()));
		paymentEntity.setCon_category(paymentdata.getCon_category());
		paymentEntity.setCon_type(paymentdata.getCon_type());
		
		return paymentEntity;
	}
	/*===================================================Duplicate Receipt=========================================================*/
	@RequestMapping(value="/duplicateReceipt",method={RequestMethod.POST,RequestMethod.GET})
	public String duplicateReceipt(Model model,HttpServletRequest request){
		return "cashCounter/duplicateReceipt";	
	}
	
	
	//New Method to Calculate Penalty And Rebate
	@RequestMapping(value="/getPenaltyAndRebateByMonthYear/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getPenaltyAndRebateByMonthYear(@PathVariable String connectionNo,HttpServletRequest request){
		
		
		String yearmntfr=request.getParameter("yearmntfr");
		String yearmntto=request.getParameter("yearmntto");
		  
		try{
		
			String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			BsmartWaterLogger.logger.info(date+"date NOW");
			String english[]=date.split("-");
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
			String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			BsmartWaterLogger.logger.info(nepalimonthyear+"nepalimonthyear");
			
			String[] s=nepalimonthyear.split("-");
			String startmonth=s[0]+""+s[1];
			BsmartWaterLogger.logger.info(startmonth+"--startmonth");
			
			
		List<Object> result=new ArrayList<>();
		
		BsmartWaterLogger.logger.info(yearmntto+"---yearmntto");
		
		BillingLedgerEntity billingLedgerEntity=billingLedgerService.findByConnectionNoByMYN(connectionNo, yearmntto);
		
		double totalpenlaty=0.0;
		double totalrebate=0.0;
		double closingbalance=0.0;
		String tomonthYear=null;
		String fromMonthYear=null;
		
		String monthYearNep1=null;
		String monthYearNep2=null;
		String monthYearNep3=null;
		String monthYearNep4=null;
		String monthYearNep5=null;
		String monthYearNep6=null;
		
		
		String yearc=startmonth.substring(0, 4);
		String monthc=startmonth.substring(4, 6);
		
		if("01".equalsIgnoreCase(monthc)){
			monthYearNep1=startmonth;
			String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
			monthYearNep2=nmonthYearNep;
			monthYearNep3=""+(Integer.parseInt(nmonthYearNep)-1);
			monthYearNep4=""+(Integer.parseInt(nmonthYearNep)-2);
			monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-3);
			monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-4);
			
		}else if("02".equalsIgnoreCase(monthc)){
			monthYearNep1=startmonth;
			monthYearNep2=""+(Integer.parseInt(startmonth)-1);
			String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
			monthYearNep3=nmonthYearNep;
			monthYearNep4=""+(Integer.parseInt(nmonthYearNep)-1);
			monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-2);
			monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-3);
		}else if("03".equalsIgnoreCase(monthc)){
			monthYearNep1=startmonth;
			monthYearNep2=""+(Integer.parseInt(startmonth)-1);
			monthYearNep3=""+(Integer.parseInt(startmonth)-2);
			String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
			monthYearNep4=nmonthYearNep;
			monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-1);
			monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-2);
		}else if("04".equalsIgnoreCase(monthc)){
			monthYearNep1=startmonth;
			monthYearNep2=""+(Integer.parseInt(startmonth)-1);
			monthYearNep3=""+(Integer.parseInt(startmonth)-2);
			monthYearNep4=""+(Integer.parseInt(startmonth)-3);
			String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
			monthYearNep5=nmonthYearNep;
			monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-1);
		}else if("05".equalsIgnoreCase(monthc)){
			monthYearNep1=startmonth;
			monthYearNep2=""+(Integer.parseInt(startmonth)-1);
			monthYearNep3=""+(Integer.parseInt(startmonth)-2);
			monthYearNep4=""+(Integer.parseInt(startmonth)-3);
			monthYearNep5=""+(Integer.parseInt(startmonth)-4);
			String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
			monthYearNep6=nmonthYearNep;
		}else{
			monthYearNep1=startmonth;
			monthYearNep2=""+(Integer.parseInt(startmonth)-1);
			monthYearNep3=""+(Integer.parseInt(startmonth)-2);
			monthYearNep4=""+(Integer.parseInt(startmonth)-3);
			monthYearNep5=""+(Integer.parseInt(startmonth)-4);
			monthYearNep6=""+(Integer.parseInt(startmonth)-5);
		}
		
		
		List<?> list=billingLedgerService.findBillsByReceiptNullByMonthYear(connectionNo,Integer.parseInt(yearmntfr),Integer.parseInt(yearmntto));

		int i=1;
		
		if(!list.isEmpty()){
		
		  for (final Iterator<?> ii = list.iterator(); ii.hasNext();) {
				
						Object[] values = (Object[])ii.next();
			
						double waterCharge=values[0]==null?0:(Double)values[0];
						double swCharge=values[1]==null?0:(Double)values[1];
						double mtrrent=values[2]==null?0:(Double)values[2];
						double netamt=values[3]==null?0:(Double)values[3];
						String monthyrnep=values[4]==null?"":(String)values[4];		

						double billamount=(waterCharge)+swCharge+(mtrrent);
						
						if(list.size()==i){
							fromMonthYear=monthyrnep;
						}
						
						if(list.size()==1){
							fromMonthYear=monthyrnep;
						}
						
						
						if(billingLedgerEntity!=null){
							
							
							 if((monthYearNep1).equalsIgnoreCase(monthyrnep) || (monthYearNep2).equalsIgnoreCase(monthyrnep) 
									 || (monthYearNep3).equalsIgnoreCase(monthyrnep)){
								   
								 if(netamt>0){
									  
									 if(billamount>netamt){
										  totalrebate+=(netamt*3)/100;
									 }else{
									      totalrebate+=(billamount*3)/100;
									 }
								   }
								   
							   }
							   
							   
							   else if((monthYearNep4).equalsIgnoreCase(monthyrnep)){
								  
							   }else if((monthYearNep5).equalsIgnoreCase(monthyrnep)){
								   if(netamt>0){
								   
									   if(billamount>netamt){
										      totalpenlaty+=(netamt*10)/100;
									   }else{
									          totalpenlaty+=(billamount*10)/100;
									   }
								   }
							   }else if((monthYearNep6).equalsIgnoreCase(monthyrnep)){
								   if(netamt>0){
									   
									   if(billamount>netamt){
										      totalpenlaty+=(netamt*20)/100;
									   }else{
									          totalpenlaty+=(billamount*20)/100;
									   }
								   }
							   }else{
								   if(netamt>0){
									   if(billamount>netamt){
										      totalpenlaty+=(netamt*50)/100;
									   }else{
									          totalpenlaty+=(billamount*50)/100;
									   }
								   }
							   }
					
						}
					  i++;
			
			
			
		}
		
		}
		
		
		
		if(billingLedgerEntity!=null){

			if(fromMonthYear==null){
				fromMonthYear=billingLedgerEntity.getMonthyearnep();
			}
			
			if(tomonthYear==null){
				tomonthYear=billingLedgerEntity.getMonthyearnep();
				
				
			}
			
			result.add(billingLedgerEntity);//0
			
			
		}else{
			result.add(null);//0
			
		}
			
			result.add(totalpenlaty);//1
			result.add(totalrebate);//2

		if(billingLedgerEntity!=null){
			closingbalance=billingLedgerEntity.getClose_balance()==null?0:billingLedgerEntity.getClose_balance();
			result.add(closingbalance);//3
			
			result.add(fromMonthYear);//4
			result.add(tomonthYear);//5
			
		}
		
		return result;
		
		
		
		
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/getPenaltyAndRebateByMonthYearNew/{connectionNo}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object getPenaltyAndRebateByMonthYearNew(@PathVariable String connectionNo,HttpServletRequest request){
		
		String yearmntfr=request.getParameter("yearmntfr");
		String yearmntto=request.getParameter("yearmntto");
		  
		try{
		
			String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			BsmartWaterLogger.logger.info(date+"date NOW");
			String english[]=date.split("-");
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
			String nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			BsmartWaterLogger.logger.info(nepalimonthyear+"nepalimonthyear");
			
			String[] s=nepalimonthyear.split("-");
			String startmonth=s[0]+""+s[1];
			BsmartWaterLogger.logger.info(startmonth+"--startmonth");
			
			String tomonthYear=null;
			String fromMonthYear=null;
			String monthYearNep1=null;
			String monthYearNep2=null;
			String monthYearNep3=null;
			String monthYearNep4=null;
			String monthYearNep5=null;
			String monthYearNep6=null;
			
			String yearc=startmonth.substring(0, 4);
			String monthc=startmonth.substring(4, 6);
			
			if("01".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep2=nmonthYearNep;
				monthYearNep3=""+(Integer.parseInt(nmonthYearNep)-1);
				monthYearNep4=""+(Integer.parseInt(nmonthYearNep)-2);
				monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-3);
				monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-4);
				
			}else if("02".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep3=nmonthYearNep;
				monthYearNep4=""+(Integer.parseInt(nmonthYearNep)-1);
				monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-2);
				monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-3);
			}else if("03".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				monthYearNep3=""+(Integer.parseInt(startmonth)-2);
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep4=nmonthYearNep;
				monthYearNep5=""+(Integer.parseInt(nmonthYearNep)-1);
				monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-2);
			}else if("04".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				monthYearNep3=""+(Integer.parseInt(startmonth)-2);
				monthYearNep4=""+(Integer.parseInt(startmonth)-3);
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep5=nmonthYearNep;
				monthYearNep6=""+(Integer.parseInt(nmonthYearNep)-1);
			}else if("05".equalsIgnoreCase(monthc)){
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				monthYearNep3=""+(Integer.parseInt(startmonth)-2);
				monthYearNep4=""+(Integer.parseInt(startmonth)-3);
				monthYearNep5=""+(Integer.parseInt(startmonth)-4);
				String nmonthYearNep=(Integer.parseInt(yearc)-1)+"12";
				monthYearNep6=nmonthYearNep;
			}else{
				monthYearNep1=startmonth;
				monthYearNep2=""+(Integer.parseInt(startmonth)-1);
				monthYearNep3=""+(Integer.parseInt(startmonth)-2);
				monthYearNep4=""+(Integer.parseInt(startmonth)-3);
				monthYearNep5=""+(Integer.parseInt(startmonth)-4);
				monthYearNep6=""+(Integer.parseInt(startmonth)-5);
			}
			
			
		List<Object> result=new ArrayList<>();
		
		BsmartWaterLogger.logger.info(yearmntto+"---yearmntto");
		BillingLedgerEntity billingLedgerEntity=billingLedgerService.findByConnectionNoByMYN(connectionNo, yearmntto);
		
		double totalpenlaty=0.0;
		double totalrebate=0.0;
		double totalnetamount=0;
		double wc=0;
		double swc=0;
		double mtr=0;
		
		List<?> list=billingLedgerService.findBillsByReceiptNullByMonthYear(connectionNo,Integer.parseInt(yearmntfr),Integer.parseInt(yearmntto));

		int i=1;
		
		if(!list.isEmpty()){
		
		  for (final Iterator<?> ii = list.iterator(); ii.hasNext();) {
				
						Object[] values = (Object[])ii.next();
			
						double waterCharge=values[0]==null?0:(Double)values[0];
						double swCharge=values[1]==null?0:(Double)values[1];
						double mtrrent=values[2]==null?0:(Double)values[2];
						double netamt=values[3]==null?0:(Double)values[3];
						String monthyrnep=values[4]==null?"":(String)values[4];	
						double arrearsamt=values[5]==null?0:(Double)values[5];

						double billamount=(waterCharge)+swCharge+(mtrrent);
						
						if(list.size()==i){
							fromMonthYear=monthyrnep;
						}
						
						if(list.size()==1){
							fromMonthYear=monthyrnep;
						}
							
						if(billingLedgerEntity!=null){
						 if((monthYearNep1).equalsIgnoreCase(monthyrnep) || (monthYearNep2).equalsIgnoreCase(monthyrnep) || (monthYearNep3).equalsIgnoreCase(monthyrnep)){
							   
							 if(netamt>0){
								  
								 if(billamount>netamt){
									  totalrebate+=(netamt*3)/100;
								 }else{
								      totalrebate+=(billamount*3)/100;
								 }
							   }
							   
						   }
							   
							   
						   else if((monthYearNep4).equalsIgnoreCase(monthyrnep)){
							  
						   }else if((monthYearNep5).equalsIgnoreCase(monthyrnep)){
							   if(netamt>0){
							   
								   if(billamount>netamt){
									      totalpenlaty+=(netamt*10)/100;
								   }else{
								          totalpenlaty+=(billamount*10)/100;
								   }
							   }
						   }else if((monthYearNep6).equalsIgnoreCase(monthyrnep)){
							   if(netamt>0){
								   
								   if(billamount>netamt){
									      totalpenlaty+=(netamt*20)/100;
								   }else{
								          totalpenlaty+=(billamount*20)/100;
								   }
							   }
						   }else{
							   if(netamt>0){
								   if(billamount>netamt){
									      totalpenlaty+=(netamt*50)/100;
								   }else{
								          totalpenlaty+=(billamount*50)/100;
								   }
							   }
						   }
						 
						 if(list.size()==i){
							 if(netamt>billamount){
								 totalnetamount=totalnetamount+arrearsamt;
							 }
						 }
						 
						 if(netamt>billamount){
							 totalnetamount=totalnetamount+billamount;
						 }else{
							 if(netamt>0 || netamt<0){
							   totalnetamount=totalnetamount+netamt;
							 }
						 }
						
						
						 
		          }
					
				  i++;
		
		
		
		  }
	
		}
		
		if(billingLedgerEntity!=null){
			wc=billingLedgerEntity.getWater_charges()==null?0:billingLedgerEntity.getWater_charges();
			swc=billingLedgerEntity.getSw_charges()==null?0:billingLedgerEntity.getSw_charges();
			mtr=billingLedgerEntity.getMtr_rent()==null?0:billingLedgerEntity.getMtr_rent();
		}
		
		result.add(billingLedgerEntity);//0
		result.add(totalpenlaty);//1
		result.add(totalrebate);//2
		result.add(billingLedgerEntity.getClose_balance()==null?0:billingLedgerEntity.getClose_balance());//3
		result.add(fromMonthYear);//4
		result.add(tomonthYear);//5
		result.add(totalnetamount);//6
		result.add(totalnetamount-(wc+swc+mtr));//7
		result.add(wc);//8
		result.add(swc);//9
		result.add(mtr);//10
		
		//System.out.println(wc+"wc");
		//System.out.println(swc+"swc");
		//System.out.println(mtr+"mtr");
		//System.out.println(totalnetamount+"tna");
		//System.out.println(totalnetamount-(wc+swc+mtr)+"arr");
		return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	

	//New Connection Counter Start
	@RequestMapping(value="/newConnectionCounter",method={RequestMethod.POST,RequestMethod.GET})
	public String newConnectionCounter(Model model,HttpServletRequest request){

		try{
		
		HttpSession session=request.getSession(false);
		model.addAttribute("mainHead", "Cash Counter");
		model.addAttribute("childHead1", "Payments");
		
		PaymentEntity paymentEntity=new PaymentEntity();
		/*Integer id=paymentService.getpaymentMaxId();
		
		System.out.println("ID======================="+id);
		
		
		if(id==null){
			id=1;
		}else{
		}*/
		
			model.addAttribute("currDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
			Integer counterNo=(Integer)session.getAttribute("counterNo");
			String branchcode=(String)session.getAttribute("branchcode");
			
			Date engDate=new Date();
			String engFdate=new SimpleDateFormat("dd-MM-yyyy").format(engDate);
			String englishF[]=engFdate.split("-");
			int cday1=Integer.parseInt(englishF[0]);
			
			if("02".equalsIgnoreCase(englishF[1])){
				cday1=cday1+3;
			}
			
			if("04".equalsIgnoreCase(englishF[1])){
				cday1=cday1+1;
			}
			
			if("06".equalsIgnoreCase(englishF[1])){
				cday1=cday1+1;
			}
			
			if("09".equalsIgnoreCase(englishF[1])){
				cday1=cday1+1;
			}
			
			int cmonth1=Integer.parseInt(englishF[1]);
			int cyear1=Integer.parseInt(englishF[2]);
			String nepalidate1=masterGenericDAO.getNepaliDateFromEnglishDate(cyear1, cmonth1, cday1);
			//System.out.println("new receipt format  : Nepdate :"+nepalidate1);
			
			String [] mynF=nepalidate1.split("-");
			String mynNepali=mynF[1]+mynF[0].substring(2,4);
			//System.out.println("new receipt format  : Nepali MonthYear :"+mynNepali);
			
			
			long nReceiptNo=0;
			//long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+new SimpleDateFormat("MMYY").format(new Date())+counterNo+"%");
			long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+mynNepali+counterNo+"%",counterNo);
			//System.out.println("New Receipt Number : "+newreceiptno);
			
			if(newreceiptno==0){
				nReceiptNo=Long.parseLong(branchcode+""+mynNepali+counterNo+String.format("%07d", 1));
			}else{
				nReceiptNo=newreceiptno+1;
			}
			
			//paymentEntity.setReceiptNo(branchcode+""+new SimpleDateFormat("MMYY").format(new Date())+counterNo+String.format("%07d", id+1));
			
			paymentEntity.setReceiptNo(""+nReceiptNo);
			model.addAttribute("npaymentEntity", paymentEntity);
			//model.addAttribute("bankData",bankDetailsService.getBankDetailsEntity());
			
			if(createnpayment==1){
				
				model.addAttribute("msg","Cash Counter Payment Created Successfully");
				
				PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(npaymentReceiptNo);
				
				if("NC".equalsIgnoreCase(paymentdata.getConnectionNo()) || (paymentdata.getRemarks()!=null && !"".equals(paymentdata.getRemarks()) && !"null".equalsIgnoreCase(paymentdata.getRemarks()))){
					List<?> obj= consumerMasterService.findByApplicationById(Long.parseLong(paymentdata.getRemarks()));
					model.addAttribute("consumerMaster",null);
					model.addAttribute("objnc",obj);
					
				}else{
					ConsumerMaster consumerMaster=consumerMasterService.findconsumer(paymentdata.getConnectionNo());
					model.addAttribute("consumerMaster",consumerMaster);
				}
				
				
				model.addAttribute("peEntity",paymentdata);
				
				if(paymentdata.getRdate()!=null){
				    model.addAttribute("date",new SimpleDateFormat("dd-MMM-yyyy").format(paymentdata.getRdate()));
				    String rdate=new SimpleDateFormat("dd-MM-yyyy").format(paymentdata.getRdate());
					String english[]=rdate.split("-");
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
					String nepalidate=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				    model.addAttribute("nepdate",nepalidate);

				}
				npaymentReceiptNo="";
				createnpayment=0;
			} 
			
			List<?> list=paymentService.getdataforDayClose(counterNo,new Date());
			
			if(!list.isEmpty()){
			 model.addAttribute("datalist",list);
			}

				
			return "cashCounter/newConnectionCounter";
		}
		catch(Exception e){
			createnpayment=0;
			return "cashCounter/newConnectionCounter";
		}
			
	}
	
	
	@RequestMapping(value="/cashCounterCustomerHistory", method = {RequestMethod.GET, RequestMethod.POST})
	public String cashCounterCustomerHistory(Model model,HttpServletRequest request)  {
	
	return "cashCounter/cashCounterCustomerHistory";
	}
	
	
	@RequestMapping(value="/addNewConnectionPaymentEntity", method = {RequestMethod.GET, RequestMethod.POST})
	public String addNewConnectionPaymentEntity(@ModelAttribute("npaymentEntity")PaymentEntity npaymentEntity,BindingResult bindingResult,Model model,HttpServletRequest request)  {
		
		String applicationId=request.getParameter("applicationId");
		try{
		
			HttpSession session=request.getSession(false);
			String currentNMonth=(String)session.getAttribute("currentNMonth");
			if("NC".equalsIgnoreCase(npaymentEntity.getConnectionNo()) || (applicationId!=null && !"".equals(applicationId) && !"null".equalsIgnoreCase(applicationId))){
				npaymentEntity.setRemarks(applicationId);
			}
			
			npaymentEntity.setWater_charges(0.0);
			npaymentEntity.setSw_charges(0.0);
			npaymentEntity.setMeter_rent(0.0);
			npaymentEntity.setFrecamount(0.0);
			npaymentEntity.setBalance_amount(0.0);
			npaymentEntity.setTender_cash(0.0);
			npaymentEntity.setChange(0.0);
			npaymentEntity.setPenalty(0.0);
			npaymentEntity.setRebate(0.0);
			npaymentEntity.setBalance_amount(0.0);
			npaymentEntity.setBill_amount(0.0);
			npaymentEntity.setOld_balance(0.0);
			npaymentEntity.setRbalance(0.0);
			//npaymentEntity.setTowards("MISCELLANEOUS");
			npaymentEntity.setRdate(new Date());
			npaymentEntity.setEdate(new Date());
			npaymentEntity.setCounterno(""+(Integer)session.getAttribute("counterNo"));
			String counteruser=(String)session.getAttribute("counterUserName");
			npaymentEntity.setUser_id(counteruser);
			String branchcode=(String)session.getAttribute("branchcode");
			
			npaymentEntity.setSiteCode(branchcode);
			npaymentEntity.setAddedBy(counteruser);
			npaymentEntity.setUpdatedBy(counteruser);
			npaymentEntity.setRecordtype("ORIGINAL");
			
			if(currentNMonth==null){
				
			}else{
				npaymentEntity.setMonth_year_nep(Integer.parseInt(currentNMonth));
			}
			double total=0;
			total=npaymentEntity.getNctap()+npaymentEntity.getNc_inst()+npaymentEntity.getNcdeposit()+npaymentEntity.getMvalue()
					+npaymentEntity.getAdvance()+npaymentEntity.getTempholeamt()+npaymentEntity.getNameChangeAmt()+npaymentEntity.getMiscellaneous_cost()
					+npaymentEntity.getIlg_con_amt()+npaymentEntity.getOther_paid_amt();
			if(total!=npaymentEntity.getAmount()){
				npaymentEntity.setAmount(total);
			}
			
			Date engDate=new Date();
			String engFdate=new SimpleDateFormat("dd-MM-yyyy").format(engDate);
			String englishF[]=engFdate.split("-");
			int cday1=Integer.parseInt(englishF[0]);
			
			if("02".equalsIgnoreCase(englishF[1])){
				cday1=cday1+3;
			}
			
			if("04".equalsIgnoreCase(englishF[1])){
				cday1=cday1+1;
			}
			
			if("06".equalsIgnoreCase(englishF[1])){
				cday1=cday1+1;
			}
			
			if("09".equalsIgnoreCase(englishF[1])){
				cday1=cday1+1;
			}
			
			int cmonth1=Integer.parseInt(englishF[1]);
			int cyear1=Integer.parseInt(englishF[2]);
			String nepalidate1=masterGenericDAO.getNepaliDateFromEnglishDate(cyear1, cmonth1, cday1);
			//System.out.println("new receipt format  : Nepdate :"+nepalidate1);
			
			String [] mynF=nepalidate1.split("-");
			String mynNepali=mynF[1]+mynF[0].substring(2,4);
			long nReceiptNo=0;
			Integer counterNo=(Integer)session.getAttribute("counterNo");
			//long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+new SimpleDateFormat("MMYY").format(new Date())+counterNo+"%");
			long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+mynNepali+counterNo+"%",counterNo);
			BsmartWaterLogger.logger.info("New Receipt Number : "+newreceiptno);
			
			if(newreceiptno==0){
				nReceiptNo=Long.parseLong(branchcode+""+mynNepali+counterNo+String.format("%07d", 1));
			}else{
				nReceiptNo=newreceiptno+1;
			}
			
			npaymentEntity.setReceiptNo(""+nReceiptNo);
			
		    paymentService.save(npaymentEntity);
		    
		    
		    MiscellaneousPayment miscellaneousPayment=new MiscellaneousPayment();
		    miscellaneousPayment.setConnectionNo(npaymentEntity.getConnectionNo());
		    miscellaneousPayment.setSitecode(branchcode);
		    miscellaneousPayment.setNctap(npaymentEntity.getNctap());
		    miscellaneousPayment.setNcdeposit(npaymentEntity.getNcdeposit());
		    miscellaneousPayment.setMvalue(npaymentEntity.getMvalue());
		    miscellaneousPayment.setTempholeamt(npaymentEntity.getTempholeamt());
		    miscellaneousPayment.setNameChangeAmt(npaymentEntity.getNameChangeAmt());
		    miscellaneousPayment.setIlg_con_amt(npaymentEntity.getIlg_con_amt());
		    miscellaneousPayment.setUserid(counteruser);
		    miscellaneousPayment.setCreated_date(new Date());
		    if(applicationId!=null && !"".equalsIgnoreCase(applicationId)){
		    	miscellaneousPayment.setApplication_id(Long.valueOf(applicationId));
		    }
		    miscellaneousPayment.setStatus(0);
		    miscellaneousPayment.setRecno(npaymentEntity.getReceiptNo());
		    miscellaneousPayment.setAmount(npaymentEntity.getAmount());
		    miscellaneousPaymentService.save(miscellaneousPayment);
		  
		
		
		
		npaymentReceiptNo=npaymentEntity.getReceiptNo();
		createnpayment=1;
		}catch(Exception e){
			createnpayment=0;
			e.printStackTrace();
		}
		return "redirect:/newConnectionCounter";
		
	}
	
	
	
	@RequestMapping(value="/searchReceiptNoNewCounter/{receiptno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object searchReceiptNoNewCounter(@PathVariable String receiptno,HttpServletRequest request)
	{
		//System.out.println("====searchConnNo====>"+receiptno);
		PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(receiptno);
		
		List<Object> data=new ArrayList<>();
		if(paymentdata!=null){
			
			data.add("Receipt Exist");//0
			data.add(paymentdata);//1
			
			List<?> obj=null;
			if("NC".equalsIgnoreCase(paymentdata.getConnectionNo()) || (paymentdata.getRemarks()!=null && !"".equals(paymentdata.getRemarks()) && !"null".equalsIgnoreCase(paymentdata.getRemarks()))){
				obj= consumerMasterService.findByApplicationById(Long.parseLong(paymentdata.getRemarks()));
				data.add(null);//2
			}else{
				ConsumerMaster consumerMaster=consumerMasterService.findconsumer(paymentdata.getConnectionNo());
				data.add(consumerMaster);//2
			}
			
			
			
			
			
			String rdate=new SimpleDateFormat("dd-MM-yyyy").format(paymentdata.getRdate());
			String english[]=rdate.split("-");
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
			data.add(nepalimonthyear);//3
			data.add(obj);//3
			

		}else{
			data.add("Receipt Doesn't Exist");
		}
		return data;

	}
	
	
	
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value="/cancelReceiptNewConnection/{connectionno}/{receiptno}",method={RequestMethod.POST,RequestMethod.GET})
	public @ResponseBody Object cancelReceiptNewConnection(@PathVariable String connectionno,@PathVariable String receiptno,HttpServletRequest request)
	{
		HttpSession session=request.getSession(false);
		//System.out.println("====searchConnNo====>"+receiptno);
		
		Integer counterNo=(Integer)session.getAttribute("counterNo");
		List<Object> data=new ArrayList<>();
		
		PaymentEntity paymentdata=paymentService.getPaymentEntityBasedOnReceiptNo(receiptno);
		
		if(counterNo!=null){
		
			if(!counterNo.equals(paymentdata.getCounterno())){
				data.add("Sorry You Cannot cancel the Receipt which is done by Counter No  : "+paymentdata.getCounterno());
			}
		}else{
		paymentdata.setCancelledremarks("CANCELLED");
		
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
		paymentEntity.setMonth_year_nep(paymentdata.getMonth_year_nep());
		paymentEntity.setIlg_con_amt((paymentdata.getIlg_con_amt()==null?0:paymentdata.getIlg_con_amt()*(-1)));
		paymentEntity.setNctap((paymentdata.getNctap()==null?0:paymentdata.getNctap()*(-1)));
		paymentEntity.setNcdeposit((paymentdata.getNcdeposit()==null?0:paymentdata.getNcdeposit())*(-1));
		
		paymentEntity.setMvalue((paymentdata.getMvalue()==null?0:paymentdata.getMvalue())*(-1));
		paymentEntity.setTempholeamt((paymentdata.getTempholeamt()==null?0:paymentdata.getTempholeamt())*(-1));
		
		paymentEntity.setNameChangeAmt((paymentdata.getNameChangeAmt()==null?0:paymentdata.getNameChangeAmt())*(-1));
		
		paymentEntity.setMiscellaneous_cost((paymentdata.getMiscellaneous_cost()==null?0:paymentdata.getMiscellaneous_cost())*(-1));
		paymentEntity.setAdvance((paymentdata.getAdvance()==null?0:paymentdata.getAdvance())*(-1));
		
		paymentEntity.setAmount((paymentEntity.getAmount()==null?0:paymentEntity.getAmount())*(-1));
		
		
		paymentEntity.setFrom_mon_year(paymentdata.getFrom_mon_year());
		paymentEntity.setTo_mon_year(paymentdata.getTo_mon_year());
		
		
		
		paymentEntity.setCancelledremarks("CANCELLED");
		paymentEntity.setRecordtype("DUPLICATE");
		String userName=(String)session.getAttribute("userName");
		paymentEntity.setCancelled_by(userName);
		paymentEntity.setCancelled_date(new Timestamp(new java.util.Date().getTime()));
		
		paymentEntity.setCon_category(paymentdata.getCon_category());
		paymentEntity.setCon_type(paymentdata.getCon_type());
		
		try{
			paymentService.customupdate(paymentdata);
			paymentService.save(paymentEntity);
			
			data.add("Receipt No: "+receiptno+" Cancelled Successfully");
		}

		
		
		catch(Exception e){
			e.printStackTrace();
			data.add("Unable to Cancel Receipt No : "+receiptno);
		}
		
		}
		return data;
		
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	@RequestMapping(value="/addBoardPaymentEntity", method = {RequestMethod.GET, RequestMethod.POST})
	public String addBoardPaymentEntity(@ModelAttribute("paymentEntity")PaymentEntity paymentEntity,BindingResult bindingResult,Model model,HttpServletRequest request)  {
		try{
		
		HttpSession session=request.getSession(false);
		String currentNMonth=(String)session.getAttribute("currentNMonth");
		String date=request.getParameter("cddate");
		String inst_board_balance=request.getParameter("inst_board_balance");
		String inst_penalty=request.getParameter("inst_penalty");
		String rem_balance=request.getParameter("rem_balance");
		String inst_nepali_date=request.getParameter("inst_nepali_date");
		String board_adj_id=request.getParameter("board_adj_id");
		String branchcode=(String)session.getAttribute("branchcode");
		Integer counterNo=(Integer)session.getAttribute("counterNo");
		
		
		if(date!=null && !"null".equalsIgnoreCase(date) && !"".equalsIgnoreCase(date)){
			try {
				Date date1=new SimpleDateFormat("MM/dd/yyyy").parse((String)request.getParameter("cddate"));
				paymentEntity.setCddate(new java.sql.Date(date1.getTime()));
			} catch (ParseException e) {
				
			}
		}
		
		Date engDate=new Date();
		String engFdate=new SimpleDateFormat("dd-MM-yyyy").format(engDate);
		String englishF[]=engFdate.split("-");
		int cday1=Integer.parseInt(englishF[0]);
		
		if("02".equalsIgnoreCase(englishF[1])){
			cday1=cday1+3;
		}
		
		if("04".equalsIgnoreCase(englishF[1])){
			cday1=cday1+1;
		}
		
		if("06".equalsIgnoreCase(englishF[1])){
			cday1=cday1+1;
		}
		
		if("09".equalsIgnoreCase(englishF[1])){
			cday1=cday1+1;
		}
		
		int cmonth1=Integer.parseInt(englishF[1]);
		int cyear1=Integer.parseInt(englishF[2]);
		String nepalidate1=masterGenericDAO.getNepaliDateFromEnglishDate(cyear1, cmonth1, cday1);
		//System.out.println("new receipt format  : Nepdate :"+nepalidate1);
		
		String [] mynF=nepalidate1.split("-");
		String mynNepali=mynF[1]+mynF[0].substring(2,4);
		
		long nReceiptNo=0;
		//long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+new SimpleDateFormat("MMYY").format(new Date())+counterNo+"%");
		long newreceiptno=paymentService.getMaxReceiptNo(branchcode+""+mynNepali+counterNo+"%",counterNo);
		//System.out.println("New Receipt Number : "+newreceiptno);
		
		if(newreceiptno==0){
			nReceiptNo=Long.parseLong(branchcode+""+mynNepali+counterNo+String.format("%07d", 1));
		}else{
			nReceiptNo=newreceiptno+1;
		}
		
		paymentEntity.setReceiptNo(""+nReceiptNo);
		paymentEntity.setRdate(new Date());
		paymentEntity.setEdate(new Date());
		paymentEntity.setCounterno(""+(Integer)session.getAttribute("counterNo"));
		paymentEntity.setUser_id((String)session.getAttribute("counterUserName"));
		paymentEntity.setSiteCode((String)session.getAttribute("branchcode"));
		paymentEntity.setAddedBy((String)session.getAttribute("counterUserName"));
		paymentEntity.setUpdatedBy((String)session.getAttribute("counterUserName"));
		paymentEntity.setRecordtype("ORIGINAL");
		paymentEntity.setPenalty(Double.parseDouble(inst_penalty));
		paymentEntity.setRebate(0.0);
		paymentEntity.setAdvance(0.0);
		paymentEntity.setAdvance_rebate(0.0);
		paymentEntity.setWater_charges(0.0);
		paymentEntity.setSw_charges(0.0);
		paymentEntity.setMeter_rent(0.0);
		paymentEntity.setMiscellaneous_cost(0.0);
		paymentEntity.setBill_amount(0.0);
		paymentEntity.setOld_balance(0.0);
		paymentEntity.setRbalance(0.0);
		paymentEntity.setFrecamount(0.0);
		paymentEntity.setBalance_amount(0.0);
		if(currentNMonth==null){
			
		}else{
			paymentEntity.setMonth_year_nep(Integer.parseInt(currentNMonth));
		}
		
		try{
		   paymentService.save(paymentEntity);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		BoardInstallment boardInstallment=new BoardInstallment();
		boardInstallment.setSubmit_by((String)session.getAttribute("counterUserName"));
		boardInstallment.setSubmit_date(new Date());
		boardInstallment.setInstall_amount(Double.parseDouble(inst_board_balance));
		boardInstallment.setPenalty(Double.parseDouble(inst_penalty));
		boardInstallment.setConnection_no(paymentEntity.getConnectionNo());
		double rem_bal=Double.parseDouble(rem_balance)-paymentEntity.getBill_adj_amount();
		boardInstallment.setRem_balance(rem_bal);
		boardInstallment.setReceipt_no(paymentEntity.getReceiptNo());
		boardInstallment.setPaid_amount(paymentEntity.getAmount());
		boardInstallment.setPenalty_adj_amt(paymentEntity.getPenalty_adj_amount());
		boardInstallment.setBoard_adj_amt(paymentEntity.getBill_adj_amount());
		boardInstallment.setAdj_id((board_adj_id==null?null:Integer.parseInt(board_adj_id)));
		boardInstallmentService.save(boardInstallment);
		
		updateCustomer(paymentEntity.getConnectionNo(),rem_bal,inst_nepali_date);
		
		
		
		paymentReceiptNo=paymentEntity.getReceiptNo();
		createpaymentb=1;
		}catch(Exception e){
			createpaymentb=0;
			e.printStackTrace();
		}
		return "redirect:/cashCounterPayments";
		
	}
	
	//@Async
	public void updateCustomer(String connectionNo, double rem_balance,String inst_nepali_date) {
		ConsumerMaster consumerMaster=consumerMasterService.findconsumer(connectionNo);
		if(consumerMaster!=null){
			//System.out.println("-------------inside --------------");
			consumerMaster.setBalance(rem_balance);
			consumerMaster.setInstall_due_date(inst_nepali_date);
			consumerMasterService.update(consumerMaster);
		}
		
	}
	
	
	
	
	
	

}

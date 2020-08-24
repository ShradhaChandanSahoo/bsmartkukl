package com.bcits.bsmartwater.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bcits.bsmartwater.service.BillPenaltyAdjService;
import com.bcits.bsmartwater.service.BillingLedgerService;
import com.bcits.bsmartwater.service.BoardInstallmentService;
import com.bcits.bsmartwater.service.ConsumerMasterService;
import com.bcits.bsmartwater.service.MiscellaneousPaymentService;
import com.bcits.bsmartwater.service.PaymentService;



@Controller
public class DashBoardController {
	@Autowired
	private BillingLedgerService ledgerService;
	
	@Autowired
	private ConsumerMasterService masterService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private MiscellaneousPaymentService miscellaneousPaymentService;
	
	@Autowired
	private BoardInstallmentService boardInstallmentService;
	
	@Autowired
	private BillPenaltyAdjService billPenaltyAdjService;
	
	@PersistenceContext(unitName="defaultPersistenceUnit")
	protected EntityManager entityManager;
	
	/*@RequestMapping(value="/getliveDashBoard",method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getliveDashBoard(HttpServletRequest request){
		List<Map<String,String>> resultList=new ArrayList<>();
		String sitecode=getSiteCode(request);
		Map<String,String>	mapList= (Map<String, String>) trmCommonController.getDashboardLiveCommon(sitecode);
		System.out.println("mapList "+mapList);
		resultList.add(mapList);
		return resultList;
	}*/
	
	@RequestMapping(value="/getAssessmentandPayments",method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getAssessmentandPayments(Model model,HttpServletRequest request) throws JSONException{
		System.out.println("in side getAssessmentandPayments methode");
		List<Map<String, String>> resultList=new ArrayList<>();
		Map<String,String>	mapList= getPaymentData();
		resultList.add(mapList);
		return resultList;
	}
	
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getPaymentData(){
		System.out.println("In side getMeterStatuswise methode....");
		List<Object> resultList=null;
		List<Object> resultList1=null;
		try {
		Map<String, String> map=new HashMap<>();
		  /*resultList=entityManager.createNativeQuery(arrearsList).getResultList();
		  if(resultList.size()>0){
			 Object[] res=(Object[]) resultList.get(0);
			  map.put("totalInst",(null==res[0]) ?"0":res[0].toString());
			  map.put("totalarrears", (null==res[1]) ? "0" :res[1].toString());
		  }*/
		  String all_coll_qry="SELECT COUNT(CONNECTION_NO), SUM(AMOUNT), SUM(NVL(CASE WHEN TOWARDS='BILL PAYMENT' THEN AMOUNT END, 0)) as bill_payment,  "
		  		+ "SUM(NVL(CASE WHEN TOWARDS='BOARD PAYMENT' THEN AMOUNT END, 0)) AS BOARD,  SUM(NVL(CASE WHEN TOWARDS='MISCELLANEOUS' THEN AMOUNT END, 0)) "
		  		+ "AS MISC,  SUM(NVL(CASE WHEN TOWARDS='BILL PAYMENT' THEN REBATE END,0)) AS REBATE, "
		  		+ "(SUM(NVL(CASE WHEN TOWARDS='BILL PAYMENT' THEN PENALTY END,0))+SUM(NVL(CASE WHEN TOWARDS='BILL PAYMENT' THEN PENALTY_ADJ_AMOUNT END,0))) AS PENALTY,"
		  		+ " (SUM(NVL(CASE WHEN TOWARDS='BOARD PAYMENT' THEN PENALTY END,0))+SUM(NVL(CASE WHEN TOWARDS='BOARD' THEN PENALTY_ADJ_AMOUNT END,0))) AS BOARD_PENALTY  "
		  		+ "FROM KUKL_BSMART.PAYMENT_TODAY_V";
			
			  resultList=entityManager.createNativeQuery(all_coll_qry).getResultList();
			  if(resultList.size()>0){
				  for(int i=0;i<resultList.size();i++){
					  Object[] res=(Object[]) resultList.get(i);
					  map.put("total_customer_paid",(null==res[0]) ?"0":res[0].toString());
					  map.put("total_coll", (null==res[1]) ? "0" :res[1].toString());
					  map.put("bill", (null==res[2]) ?"0":res[2].toString());
					  map.put("board", (null==res[3]) ?"0":res[3].toString());
					  map.put("misc", (null==res[4]) ?"0":res[4].toString());
					  map.put("rebate", (null==res[5]) ?"0":res[5].toString());
					  map.put("penalty", (null==res[6]) ?"0":res[6].toString());
					  map.put("b_penalty", (null==res[7]) ?"0":res[7].toString());
					  
				  }
			  }
			  
			  String all_coll_qry_month="SELECT COUNT(CONNECTION_NO), SUM(AMOUNT), SUM(NVL(CASE WHEN TOWARDS='BILL PAYMENT' THEN AMOUNT END, 0)) as bill_payment,  "
				  		+ "SUM(NVL(CASE WHEN TOWARDS='BOARD PAYMENT' THEN AMOUNT END, 0)) AS BOARD,  SUM(NVL(CASE WHEN TOWARDS='MISCELLANEOUS' THEN AMOUNT END, 0)) "
				  		+ "AS MISC,  SUM(NVL(CASE WHEN TOWARDS='BILL PAYMENT' THEN REBATE END,0)) AS REBATE, "
				  		+ "(SUM(NVL(CASE WHEN TOWARDS='BILL PAYMENT' THEN PENALTY END,0))+SUM(NVL(CASE WHEN TOWARDS='BILL PAYMENT' THEN PENALTY_ADJ_AMOUNT END,0))) AS PENALTY,"
				  		+ " (SUM(NVL(CASE WHEN TOWARDS='BOARD PAYMENT' THEN PENALTY END,0))+SUM(NVL(CASE WHEN TOWARDS='BOARD' THEN PENALTY_ADJ_AMOUNT END,0))) AS BOARD_PENALTY  "
				  		+ "FROM KUKL_BSMART.PAYMENT_MONTH_V";
			  
			  resultList1=entityManager.createNativeQuery(all_coll_qry_month).getResultList();
			  if(resultList1.size()>0){
				  for(int i=0;i<resultList1.size();i++){
					  Object[] res=(Object[]) resultList1.get(i);
					  map.put("total_customer_paid_m",(null==res[0]) ?"0":res[0].toString());
					  map.put("total_coll_m", (null==res[1]) ? "0" :res[1].toString());
					  map.put("bill_m", (null==res[2]) ?"0":res[2].toString());
					  map.put("board_m", (null==res[3]) ?"0":res[3].toString());
					  map.put("misc_m", (null==res[4]) ?"0":res[4].toString());
					  map.put("rebate_m", (null==res[5]) ?"0":res[5].toString());
					  map.put("penalty_m", (null==res[6]) ?"0":res[6].toString());
					  map.put("b_penalty_m", (null==res[7]) ?"0":res[7].toString());
					  
				  }
			  }
			  
		return map;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/getMiscelleneousPaymentData",method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getMiscelleneousPaymentData(Model model,HttpServletRequest request) throws JSONException{
		System.out.println("in side getMiscelleneousPaymentData methode");
		List<Map<String, String>> resultList=new ArrayList<>();
		Map<String,String>	mapList= getMiscPaymentData();
		resultList.add(mapList);
		return resultList;
	}
	
	
	
	private Map<String, String> getMiscPaymentData() {
		System.out.println("In side getMeterStatuswise methode....");
		List<Object> resultList=null;
		List<Object> resultList1=null;
		try {
		Map<String, String> map=new HashMap<>();
		  String all_coll_qry="SELECT COUNT(CONNECTION_NO),SUM(NVL(AMOUNT,0)), SUM(NVL(NCTAP,0)),SUM(NVL(NCDEPOSIT,0)),SUM(NVL(MVALUE,0)), " + 
		  		"SUM(NVL(TEMPHOLEAMT,0)),SUM(NVL(NAMECHANGEAMT,0)),SUM(NVL(ILG_CON_AMT,0)),SUM(NVL(NC_INST,0)), SUM(NVL(OTHER_PAID_AMT,0)) " + 
		  		"FROM KUKL_BSMART.PAYMENT_TODAY_V WHERE TOWARDS='MISCELLANEOUS'";
			
			  resultList=entityManager.createNativeQuery(all_coll_qry).getResultList();
			  if(resultList.size()>0){
				  for(int i=0;i<resultList.size();i++){
					  Object[] res=(Object[]) resultList.get(i);
					  map.put("total_customer_paid",(null==res[0]) ?"0":res[0].toString());
					  map.put("total_coll", (null==res[1]) ? "0" :res[1].toString());
					  map.put("nc_tap", (null==res[2]) ?"0":res[2].toString());
					  map.put("nc_deposit", (null==res[3]) ?"0":res[3].toString());
					  map.put("m_value", (null==res[4]) ?"0":res[4].toString());
					  map.put("hole_block", (null==res[5]) ?"0":res[5].toString());
					  map.put("name_change", (null==res[6]) ?"0":res[6].toString());
					  map.put("illigal_con", (null==res[7]) ?"0":res[7].toString());
					  map.put("nc_inst", (null==res[8]) ?"0":res[8].toString());
					  map.put("other", (null==res[9]) ?"0":res[9].toString());
					  
				  }
			  }
			  
			  String all_coll_qry_month="SELECT COUNT(CONNECTION_NO),SUM(NVL(AMOUNT,0)), SUM(NVL(NCTAP,0)),SUM(NVL(NCDEPOSIT,0)),SUM(NVL(MVALUE,0)), " + 
				  		"SUM(NVL(TEMPHOLEAMT,0)),SUM(NVL(NAMECHANGEAMT,0)),SUM(NVL(ILG_CON_AMT,0)),SUM(NVL(NC_INST,0)), SUM(NVL(OTHER_PAID_AMT,0)) " + 
				  		"FROM KUKL_BSMART.PAYMENT_MONTH_V WHERE TOWARDS='MISCELLANEOUS'";
			  
			  resultList1=entityManager.createNativeQuery(all_coll_qry_month).getResultList();
			  if(resultList1.size()>0){
				  for(int i=0;i<resultList1.size();i++){
					  Object[] res=(Object[]) resultList1.get(i);
					  map.put("total_customer_paid_m",(null==res[0]) ?"0":res[0].toString());
					  map.put("total_coll_m",(null==res[1]) ? "0" :res[1].toString());
					  map.put("nc_tap_m",(null==res[2]) ?"0":res[2].toString());
					  map.put("nc_deposit_m",(null==res[3]) ?"0":res[3].toString());
					  map.put("m_value_m",(null==res[4]) ?"0":res[4].toString());
					  map.put("hole_block_m",(null==res[5]) ?"0":res[5].toString());
					  map.put("name_change_m",(null==res[6]) ?"0":res[6].toString());
					  map.put("illigal_con_m",(null==res[7]) ?"0":res[7].toString());
					  map.put("nc_inst_m",(null==res[8]) ?"0":res[8].toString());
					  map.put("other_m",(null==res[9]) ?"0":res[9].toString());
					  
				  }
			  }
			  
		return map;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}


	@RequestMapping(value="/getMeterStatusWise",method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getMeterStatusWise(Model model,HttpServletRequest request) throws JSONException{
		//System.out.println("in side getAssessmentandPayments methode");
		List<Map<String, Integer>> resultList=new ArrayList<>();
		Map<String,Integer>	mapList= getMcData();
		resultList.add(mapList);
		return resultList;
	}
	
	public Map<String, Integer> getMcData(){
		System.out.println("In side getMeterStatuswise methode....");
		List<Object> resultList=null;
		try {
		String arrearsList="";
		Map<String, Integer> map=new HashMap<>();
		String all_coll_qry="SELECT MC_STATUS, SUM(COUNT) FROM KUKL_BSMART.MC_STATUS_WISE GROUP BY MC_STATUS ORDER BY MC_STATUS";
		String all_con="SELECT COUNT(*) FROM KUKL_BSMART.MASTER_V";
		Integer all;
			
			  resultList=entityManager.createNativeQuery(all_coll_qry).getResultList();
			  
			  
			  if(resultList.size()>0){
				  System.out.println("ResultList Size: "+resultList.size());
				  
				  for(int i=0;i<resultList.size();i++){
					  Object[] res=(Object[]) resultList.get(i);
					  
					  System.out.println("MC STATUS---"+res[0]+"  COUNT---"+res[1]);
					  map.put("MC"+res[0],((BigDecimal) ((null==res[1]) ?"0":res[1])).intValue());
				  }
			  }
			  
			  all=((BigDecimal)entityManager.createNativeQuery(all_con).getSingleResult()).intValue();
			  map.put("total_consumer", all);
		return map;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/getMtrLocationWiseDrillDown",method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getMtrLocationWiseDrillDown(Model model,HttpServletRequest request) throws JSONException{
		//System.out.println("in side getAssessmentandPayments methode");
		List<Map<String, ArrayList>> resultList=new ArrayList<>();
		Map<String,ArrayList>	mapList=getLocationMcStatus();
		resultList.add(mapList);
		return resultList;
	}


	private Map<String, ArrayList> getLocationMcStatus() {
		System.out.println("In side getLocationMcStatus methode....>>>>>>>");
		List<Object> resultList=null;
		try {
		String arrearsList="";
		Map<String, ArrayList> map=new HashMap<>();
		String all_coll_qry="SELECT SITECODE, NVL(SUM((CASE WHEN MC_STATUS=1 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=2 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=3 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=4 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=5 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=6 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=7 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=8 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=9 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=10 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=11 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=12 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=13 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=14 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=15 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=16 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=17 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=18 THEN COUNT END)),0), NVL(SUM((CASE WHEN MC_STATUS=19 THEN COUNT END)),0) FROM KUKL_BSMART.MC_STATUS_WISE GROUP BY SITECODE ORDER BY SITECODE";
			  resultList=entityManager.createNativeQuery(all_coll_qry).getResultList();
			  
			  
			  if(resultList.size()>0){
				  System.out.println("ResultList Size: "+resultList.size());
				  
				  for(int i=0;i<resultList.size();i++){
					  Object[] res=(Object[]) resultList.get(i);
					  
					  
					  ArrayList<Integer> l=new ArrayList<Integer>();
					  for (int j = 1; j < res.length; j++) {
						  System.out.println(">>>>>>>>"+res[j]);
						  l.add(((BigDecimal) ((null==res[j])?"0":res[j])).intValue());
					  }
					  map.put(""+res[0], l);
				  }
				  for (String key : map.keySet()) {
					  ArrayList<Integer> l=new ArrayList<Integer>();
					  l=map.get(key);
					  for (Iterator iterator = l.iterator(); iterator.hasNext();) {
						Integer i = (Integer) iterator.next();
						System.out.print(map.get(key)+"----"+i);
					}
					System.out.println();
				  }
			  }
			  
		return map;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/getCollectionLocationWiseMonthlyCollection",method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getCollectionLocationWiseMonthlyCollection(HttpServletRequest request) throws JSONException{
		List<Map<String, Object>> resultList=new ArrayList<>();
		resultList= getLocationMonthlyData();
		return resultList;
	}


	private List<Map<String, Object>> getLocationMonthlyData() {
		List<Object> resultList=null;
		try {
		  /*resultList=entityManager.createNativeQuery(arrearsList).getResultList();
		  if(resultList.size()>0){
			 Object[] res=(Object[]) resultList.get(0);
			  map.put("totalInst",(null==res[0]) ?"0":res[0].toString());
			  map.put("totalarrears", (null==res[1]) ? "0" :res[1].toString());
		  }*/
		  String all_coll_qry="SELECT COUNT (CONNECTION_NO), SUM (AMOUNT), SUM ( NVL ( CASE WHEN TOWARDS = 'BILL PAYMENT' THEN AMOUNT END, 0 ) ) AS bill_payment, "
		  		+ "SUM ( NVL ( CASE WHEN TOWARDS = 'BOARD' THEN AMOUNT END, 0 ) ) AS BOARD, SUM ( NVL ( CASE WHEN TOWARDS = 'MISCELLANEOUS' THEN AMOUNT END, 0 ) ) AS MISC, "
		  		+ "SUM ( NVL ( CASE WHEN TOWARDS = 'BILL PAYMENT' THEN REBATE END, 0 ) ) AS REBATE, "
		  		+ "( SUM ( NVL ( CASE WHEN TOWARDS = 'BILL PAYMENT' THEN PENALTY END, 0 ) ) + SUM ( NVL ( CASE WHEN TOWARDS = 'BILL PAYMENT' THEN PENALTY_ADJ_AMOUNT END, 0 ) ) ) AS PENALTY, "
		  		+ "( SUM ( NVL ( CASE WHEN TOWARDS = 'BOARD' THEN PENALTY END, 0 ) ) + SUM ( NVL ( CASE WHEN TOWARDS = 'BOARD' THEN PENALTY_ADJ_AMOUNT END, 0 ) ) ) AS BOARD_PENALTY, SITECODE "
		  		+ "FROM KUKL_BSMART.PAYMENT_MONTH_V GROUP BY SITECODE";
			
			  resultList=entityManager.createNativeQuery(all_coll_qry).getResultList();
			  List<Map<String, Object>> list=new ArrayList<>();
			  
			  if(resultList.size()>0){
				  for(int i=0;i<resultList.size();i++){
					  Map<String, Object> map=new HashMap<>();
					  Object[] res=(Object[]) resultList.get(i);
					  map.put("total_customer_paid",(null==res[0]) ?"0":res[0].toString());
					  map.put("total_coll", (null==res[1]) ? "0" :res[1].toString());
					  map.put("bill", (null==res[2]) ?"0":res[2].toString());
					  map.put("board", (null==res[3]) ?"0":res[3].toString());
					  map.put("misc", (null==res[4]) ?"0":res[4].toString());
					  map.put("rebate", (null==res[5]) ?"0":res[5].toString());
					  map.put("penalty", (null==res[6]) ?"0":res[6].toString());
					  map.put("b_penalty", (null==res[7]) ?"0":res[7].toString());
					  String site=(String) res[8];
					  if("1115".equals(site)){
						  map.put("brach", "TRIPURESHWOR");
					  } else if("1112".equals(site)){
						  map.put("brach", "BANESHWOR");
					  } else if("1116".equals(site)){
						  map.put("brach", "BHAKTAPUR");
					  } else if("1114".equals(site)){
						  map.put("brach", "CHHETRAPATI");
					  } else if("1113".equals(site)){
						  map.put("brach", "KAMALADI");
					  } else if("1119".equals(site)){
						  map.put("brach", "KIRTIPUR");
					  } else if("1118".equals(site)){
						  map.put("brach", "LALITPUR");
					  } else if("1110".equals(site)){
						  map.put("brach", "MAHANKALCHAUR");
					  } else if("1111".equals(site)){
						  map.put("brach", "MAHARAJGUNJ");
					  } else if("1117".equals(site)){
						  map.put("brach", "THIMI");
					  } else{
						  map.put("brach", "");
					  }
					  list.add(map);
				  }
			  }
			  
		return list;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/getCollectionLocationWiseMonthlyCollectionMisc",method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getCollectionLocationWiseMonthlyCollectionMisc(HttpServletRequest request) throws JSONException{
		List<Map<String, Object>> resultList=new ArrayList<>();
		resultList= getLocationMonthlyPaymentMisc();
		return resultList;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getLocationMonthlyPaymentMisc() {
		List<Object> resultList=null;
		try {
			/*resultList=entityManager.createNativeQuery(arrearsList).getResultList();
		  if(resultList.size()>0){
			 Object[] res=(Object[]) resultList.get(0);
			  map.put("totalInst",(null==res[0]) ?"0":res[0].toString());
			  map.put("totalarrears", (null==res[1]) ? "0" :res[1].toString());
		  }*/
			String all_coll_qry="SELECT COUNT(CONNECTION_NO),SUM(NVL(AMOUNT,0)), SUM(NVL(NCTAP,0)),SUM(NVL(NCDEPOSIT,0)),SUM(NVL(MVALUE,0)), " + 
					"SUM(NVL(TEMPHOLEAMT,0)),SUM(NVL(NAMECHANGEAMT,0)),SUM(NVL(ILG_CON_AMT,0)),SUM(NVL(NC_INST,0)), " + 
					"SUM(NVL(OTHER_PAID_AMT,0)),SITECODE FROM KUKL_BSMART.PAYMENT_MONTH_V WHERE TOWARDS='MISCELLANEOUS' GROUP BY SITECODE";
			resultList=entityManager.createNativeQuery(all_coll_qry).getResultList();
			List<Map<String, Object>> list=new ArrayList<>();
			
			if(resultList.size()>0){
				for(int i=0;i<resultList.size();i++){
					Map<String, Object> map=new HashMap<>();
					Object[] res=(Object[]) resultList.get(i);
					  map.put("total_customer_paid",(null==res[0]) ?"0":res[0].toString());
					  map.put("total_coll", (null==res[1]) ? "0" :res[1].toString());
					  map.put("nc_tap", (null==res[2]) ?"0":res[2].toString());
					  map.put("nc_deposit", (null==res[3]) ?"0":res[3].toString());
					  map.put("m_value", (null==res[4]) ?"0":res[4].toString());
					  map.put("hole_block", (null==res[5]) ?"0":res[5].toString());
					  map.put("name_change", (null==res[6]) ?"0":res[6].toString());
					  map.put("illigal_con", (null==res[7]) ?"0":res[7].toString());
					  map.put("nc_inst", (null==res[8]) ?"0":res[8].toString());
					  map.put("other", (null==res[9]) ?"0":res[9].toString());
					String site=(String) res[10];
					if("1115".equals(site)){
						map.put("brach", "TRIPURESHWOR");
					} else if("1112".equals(site)){
						map.put("brach", "BANESHWOR");
					} else if("1116".equals(site)){
						map.put("brach", "BHAKTAPUR");
					} else if("1114".equals(site)){
						map.put("brach", "CHHETRAPATI");
					} else if("1113".equals(site)){
						map.put("brach", "KAMALADI");
					} else if("1119".equals(site)){
						map.put("brach", "KIRTIPUR");
					} else if("1118".equals(site)){
						map.put("brach", "LALITPUR");
					} else if("1110".equals(site)){
						map.put("brach", "MAHANKALCHAUR");
					} else if("1111".equals(site)){
						map.put("brach", "MAHARAJGUNJ");
					} else if("1117".equals(site)){
						map.put("brach", "THIMI");
					} else{
						map.put("brach", "");
					}
					list.add(map);
				}
			}
			
			return list;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/getAdjustmentDetails",method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody List<?> getAdjustmentDetails(HttpServletRequest request) throws JSONException{
		List<Map<String, String>> resultList=new ArrayList<>();
		Map<String,String>	mapList= getAdjustmentDetailsForCorporate();
		resultList.add(mapList);
		return resultList;
	}


	private Map<String, String> getAdjustmentDetailsForCorporate() {
		
		List<Object> resultList=null;
		List<Object> resultList1=null;
		try {
		Map<String, String> map=new HashMap<>();
		  String all_coll_qry="SELECT count(CASE WHEN APPROVE_STATUS=1 THEN CONNECTION_NO END) as total_pending, " + 
								"count(CASE WHEN APPROVE_STATUS=2 AND APPROVED_BY1 IS NOT NULL THEN CONNECTION_NO END) AS rejected , " + 
								"count(CASE WHEN APPROVE_STATUS=3 THEN CONNECTION_NO END) as approved, " + 
								"count(CASE WHEN APPROVE_STATUS =2 AND APPROVED_BY1 IS NULL THEN CONNECTION_NO END) as branch_reject , " + 
								"NVL(SUM(CASE WHEN APPROVE_STATUS=3 THEN BILL_ADJ_AMOUNT END),0) " + 
								"FROM KUKL_BSMART.ADJUSTMENT_V";
			
			  resultList=entityManager.createNativeQuery(all_coll_qry).getResultList();
			  if(resultList.size()>0){
				  for(int i=0;i<resultList.size();i++){
					  Object[] res=(Object[]) resultList.get(i);
					  map.put("total_pending",(null==res[0]) ?"0":res[0].toString());
					  map.put("rejected", (null==res[1]) ? "0" :res[1].toString());
					  map.put("approved", (null==res[2]) ?"0":res[2].toString());
					  map.put("branch_reject", (null==res[3]) ?"0":res[3].toString());
					  map.put("approved_amount", (null==res[4]) ?"0":res[4].toString());
					  
				  }
			  }
			  
			  //String all_coll_qry_month="SELECT SITECODE,count(*) FROM KUKL_BSMART.ADJUSTMENT_V WHERE APPROVE_STATUS=1 GROUP BY SITECODE";
			  String all_coll_qry_month="SELECT SITECODE,SUM(CASE WHEN APPROVE_STATUS=1 THEN APPROVE_STATUS END) FROM KUKL_BSMART.ADJUSTMENT_V GROUP BY SITECODE";
			  
			  resultList1=entityManager.createNativeQuery(all_coll_qry_month).getResultList();
			  if(resultList1.size()>0){
				  for(int i=0;i<resultList1.size();i++){
					  Object[] res=(Object[]) resultList1.get(i);
					  //map.put("count",(null==res[1]) ?"0":res[1].toString());
					  String site=(String) res[0];
						if("1115".equals(site)){
							map.put("TRIPURESHWOR",(null==res[1]) ?"0":res[1].toString());
						} else if("1112".equals(site)){
							map.put("BANESHWOR",(null==res[1]) ?"0":res[1].toString());
						} else if("1116".equals(site)){
							map.put("BHAKTAPUR",(null==res[1]) ?"0":res[1].toString());
						} else if("1114".equals(site)){
							map.put("CHHETRAPATI",(null==res[1]) ?"0":res[1].toString());
						} else if("1113".equals(site)){
							map.put("KAMALADI",(null==res[1]) ?"0":res[1].toString());
						} else if("1119".equals(site)){
							map.put("KIRTIPUR",(null==res[1]) ?"0":res[1].toString());
						} else if("1118".equals(site)){
							map.put("LALITPUR",(null==res[1]) ?"0":res[1].toString());
						} else if("1110".equals(site)){
							map.put("MAHANKALCHAUR",(null==res[1]) ?"0":res[1].toString());
						} else if("1111".equals(site)){
							map.put("MAHARAJGUNJ",(null==res[1]) ?"0":res[1].toString());
						} else if("1117".equals(site)){
							map.put("THIMI",(null==res[1]) ?"0":res[1].toString());
						} else{
							map.put("brach", "");
						}
					  
				  }
			  }
			  
		return map;
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
		
	}
	
	
	@RequestMapping(value="/getAdjTransListByBranch")
	public @ResponseBody Object getAdjTransListByBranch(HttpServletRequest request)
	{
		String branch=request.getParameter("branchCode");
		
		return billPenaltyAdjService.getAdjTransListByBranch(branch);
	}
	

}

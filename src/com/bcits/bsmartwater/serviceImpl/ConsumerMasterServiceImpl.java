package com.bcits.bsmartwater.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.bcits.bsmartwater.dao.BillingLedgerDao;
import com.bcits.bsmartwater.dao.ConsumerMasterApprovalDao;
import com.bcits.bsmartwater.dao.ConsumerMasterDao;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.dao.TariffRateMasterDao;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.ConsumerMasterApproval;
import com.bcits.bsmartwater.model.TariffRateMaster;
import com.bcits.bsmartwater.service.ConsumerMasterService;
import com.bcits.bsmartwater.utils.BsmartWaterLogger;
import com.bcits.bsmartwater.utils.DateConverter;

@Service
public class ConsumerMasterServiceImpl implements ConsumerMasterService {

	@Autowired
	ConsumerMasterDao consumerMasterDao;
	
	@Autowired
	TariffRateMasterDao tariffRateMasterDao;
	
	@Autowired
	private ConsumerMasterApprovalDao consumerMasterApprovalDao;
	
	@Autowired
	private BillingLedgerDao billingLedgerDao;
	
	@Autowired
	MasterGenericDAO masterGenericDAO;
	
	@Autowired
	DateConverter dateConverter;
	
	
	
	@SuppressWarnings("serial")
	@Override
	public List<?> findByConnectionNo(String connectionNo) {
     
		
		List<ConsumerMaster> list= consumerMasterDao.findByConnectionNo(connectionNo);
		List<Map<String, Object>> connectionDetailsMap =  new ArrayList<Map<String, Object>>();         
		for (final ConsumerMaster record :list) 
		  {
			connectionDetailsMap.add(new HashMap<String, Object>() {	 
			{  

				put("sewage_used",record.getSewage_used());
				put("connection_no", record.getConnection_no());
				put("name_eng", record.getName_eng());
				put("ledgerno", record.getLedgerno());
				put("folio", record.getFolio());
				put("name_nep", record.getName_nep());
				put("con_category", record.getCon_category());
				put("con_type", record.getCon_type());
				put("pipe_size", record.getPipe_size());
				put("con_satatus", record.getCon_satatus());
				put("address_eng", record.getAddress_eng());
				put("customer_id", record.getCustomer_id());
				put("balance", record.getBalance());
				put("install_due_date", record.getInstall_due_date());
				put("reading_day", record.getReading_day());
				put("ward_no", record.getWard_no());
				put("mtr_rent", record.getMeterRentCharges());
				
				put("area_no", record.getArea_no());
				put("mtr_reader", record.getMtr_reader());
				
				//added illegalConPenalty 6-4-2019 by ram n sah
				put("illegalConPenalty",record.getIllegalConPenalty());
				
				TariffRateMaster rateMaster=tariffRateMasterDao.getTariffRate(record.getPipe_size(), record.getCon_type());
				if(rateMaster!=null){
				if("Metered".equalsIgnoreCase(record.getCon_type())){
					put("minimum_charges", rateMaster.getMinimum_charages());
				}else{
					put("minimum_charges", rateMaster.getMonthly_charges());
				}
				}
				
				put("fname_eng", record.getFname_eng());
				put("fname_nep", record.getFname_nep());
				put("gfname_eng", record.getGfname_eng());
				put("gfname_nep", record.getGfname_nep());
				put("title", record.getConsumer_title());
				put("citizenshipno", record.getCitizenship_no());
				put("branch",record.getBranch());//added by ram
			}});
	 }
		return connectionDetailsMap;
	 }


	@Override
	public long countByWardNo(String wardNo) {
		return consumerMasterDao.countByWardNo(wardNo);
	}
	@Override
	public long countByConCategory(String con_category1) {
		return consumerMasterDao.countByConCategory(con_category1);
	}


	@Override
	public void saveConsumerMasterDetails(ConsumerMasterApproval consumerMasterApproval,
			ModelMap model, HttpServletRequest req) {
		
		String ent_date_eng=req.getParameter("ent_date_eng");
		String connc_date_eng=req.getParameter("connc_date_eng");
		consumerMasterApproval.setArea_no(req.getParameter("area_no1"));
		HttpSession session=req.getSession(false);
	

		String schemaName=(String) session.getAttribute("branchcode");
		consumerMasterApproval.setBranch(schemaName);
		if(ent_date_eng!=null&&!ent_date_eng.isEmpty()){
			consumerMasterApproval.setEnt_date_eng(consumerMasterDao.getDate2(ent_date_eng));
		}
		if(connc_date_eng!=null&&!connc_date_eng.isEmpty()){
			consumerMasterApproval.setConnc_date_eng(consumerMasterDao.getDate2(connc_date_eng));
		}
	    consumerMasterApproval.setName_nep(new String(consumerMasterApproval.getName_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
	    consumerMasterApproval.setFname_nep(new String(consumerMasterApproval.getFname_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
	    consumerMasterApproval.setGfname_nep(new String(consumerMasterApproval.getGfname_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

	    consumerMasterApproval.setAddress_nep(new String(consumerMasterApproval.getAddress_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
	    consumerMasterApproval.setTole_name_nep(new String(consumerMasterApproval.getTole_name_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
	    consumerMasterApproval.setMpc_name_nep(new String(consumerMasterApproval.getMpc_name_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

	    consumerMasterApproval.setUsername(session.getAttribute("userName")+"");
	    
	    if(consumerMasterApproval.getPipe_size()==0.5){
	    	consumerMasterApproval.setDenoted_by("SA");
	    }else{
	    	consumerMasterApproval.setDenoted_by("THA");
	    }
	    
        consumerMasterApproval.setApprove_status(0);
		
		consumerMasterApprovalDao.customsave(consumerMasterApproval);
		  session.setAttribute("consumerMaterOp", consumerMasterApproval.getConnection_no()+" Added Successfully");
		
	}

	@Override
	public ConsumerMasterApproval findByConsumerId(String consumerId) {
		
		
		List<ConsumerMasterApproval> res=consumerMasterApprovalDao.findByConnectionNo(consumerId);
		if(res!=null&&res.size()>0&&!res.isEmpty()){
			ConsumerMasterApproval cons=consumerMasterApprovalDao.findByConnectionNo(consumerId).get(0);
		if(cons.getEnt_date_eng()!=null){
			cons.setEnt_trans_endDate(consumerMasterDao.getDate3(cons.getEnt_date_eng()));
		}
		if(cons.getConnc_date_eng()!=null){
			cons.setCon_trans_date(consumerMasterDao.getDate3(cons.getConnc_date_eng()));
		}
		return cons;
		}
		else
			return new ConsumerMasterApproval();
	}


	@Override
	public List<String> getDistictWardNos() {
	
		return consumerMasterDao.getDistictWardNos();
	}
	
	@Override
	public List<String> getDistictAllWardNos() {
		return consumerMasterDao.getDistictAllWardNos();
	}
	
	@Override
	public List<String> getDistictMrNos() {
		
		return consumerMasterDao.getDistictMrNos();
	}

	@Override
	public List<Integer> getDistictReadingDays() {
		
		return consumerMasterDao.getDistictReadingDays();
	}
	


	@Override
	public ConsumerMaster findconsumer(String connectionNo) {
		return consumerMasterDao.findconsumer(connectionNo);
	}


	@Override
	public List<String> getDistictAreaNos() {
		return consumerMasterDao.getDistictAreaNos();
	}



	@Override
	public void updateConsumerMasterDetails(
			ConsumerMaster consumerMaster, ModelMap model,
			HttpServletRequest request) {
		
		ConsumerMaster oldMaster=consumerMasterDao.customfind(consumerMaster.getId());
		consumerMaster.setConnection_no(oldMaster.getConnection_no());
		
		HttpSession session=request.getSession(false);
		consumerMaster.setUsername(session.getAttribute("userName")+"");
		String schemaName=(String) session.getAttribute("branchcode");
		consumerMaster.setBranch(schemaName);
		String ent_date_eng=request.getParameter("ent_date_eng");
		String connc_date_eng=request.getParameter("connc_date_eng");
	
		consumerMaster.setArea_no(request.getParameter("area_no1"));
		if(ent_date_eng!=null&&!ent_date_eng.isEmpty()){
			consumerMaster.setEnt_date_eng(consumerMasterDao.getDate2(ent_date_eng));
		}
		if(connc_date_eng!=null&&!connc_date_eng.isEmpty()){
			consumerMaster.setConnc_date_eng(consumerMasterDao.getDate2(connc_date_eng));
		}
		consumerMaster.setName_nep(new String(consumerMaster.getName_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
		consumerMaster.setFname_nep(new String(consumerMaster.getFname_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
		consumerMaster.setGfname_nep(new String(consumerMaster.getGfname_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));

		consumerMaster.setAddress_nep(new String(consumerMaster.getAddress_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
		consumerMaster.setTole_name_nep(new String(consumerMaster.getTole_name_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
		consumerMaster.setMpc_name_nep(new String(consumerMaster.getMpc_name_nep().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
		
		if(consumerMaster.getPipe_size()==0.5){
			consumerMaster.setDenoted_by("SA");
		}else{
			consumerMaster.setDenoted_by("THA");
		}
		
	    session.setAttribute("consumerMaterOp", consumerMaster.getConnection_no()+" Modified Successfully");
	   // consumerMaster.setApprove_status(0);
	    consumerMaster.setLastUpdatedDate(oldMaster.getLastUpdatedDate());
	    consumerMaster.setUpdatedBy(oldMaster.getUpdatedBy());	    
		consumerMasterDao.customupdate(consumerMaster);
	}



	@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public String approveConsumerDetails(HttpServletRequest request) {
		
		HttpSession session=request.getSession(false);
		String schemaName=(String) session.getAttribute("schemaName");
		BsmartWaterLogger.logger.info("---------Schema Name-------------"+schemaName);
		String ids=request.getParameter("masterId");
	    
	    String rdate=new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		String english[]=rdate.split("-");
		int cday=Integer.parseInt(english[0]);
		int month=Integer.parseInt(english[1]);
		int year=Integer.parseInt(english[2]);
		BsmartWaterLogger.logger.info(cday+"cday"+month+"cmonth"+year);
		String nepalidate=masterGenericDAO.getNepaliDateFromEnglishDate(year, month, cday);
	    
		if(ids!=null&&!ids.isEmpty()){
			
			if(ids.contains(",")){
				
				String []masIds=ids.split(",");
				
				List<BillingLedgerEntity> billingLedgerList= new ArrayList<>();
				List<ConsumerMaster> consumerMastersList=new ArrayList<>();
				
				for(String s:masIds){
					
					ConsumerMasterApproval consumerMasterApproval=consumerMasterApprovalDao.findCustomerById(Integer.parseInt(s));
					consumerMasterApproval.setApprove_status(1);
					consumerMasterApprovalDao.customupdate(consumerMasterApproval);

					// Pushing approved consumer into Master Data
					
					long checkConsumerNoInmaster=consumerMasterDao.checkConnect_noInMaster(consumerMasterApproval.getConnection_no());
					
					
					if(checkConsumerNoInmaster==0){
						
						ConsumerMaster consumerMaster=new ConsumerMaster();

						consumerMaster.setBranch(consumerMasterApproval.getBranch());
						consumerMaster.setConnection_no(consumerMasterApproval.getConnection_no());
						consumerMaster.setName_eng(consumerMasterApproval.getName_eng()) ;

						consumerMaster.setName_nep(consumerMasterApproval.getName_nep());
						consumerMaster.setFname_eng(consumerMasterApproval.getFname_eng());

						consumerMaster.setFname_nep(consumerMasterApproval.getFname_nep());

						consumerMaster.setGfname_eng(consumerMasterApproval.getGfname_eng()) ;

						consumerMaster.setGfname_nep(consumerMasterApproval.getGfname_nep());

						consumerMaster.setAddress_eng(consumerMasterApproval.getAddress_eng());

						consumerMaster.setAddress_nep(consumerMasterApproval.getAddress_nep()) ;

						consumerMaster.setTole_name_eng(consumerMasterApproval.getTole_name_eng());

						consumerMaster.setTole_name_nep(consumerMasterApproval.getTole_name_eng());
						consumerMaster.setRoad_street_eng(consumerMasterApproval.getRoad_street_eng());
						
						try {
							long customer_id=getMaxConsumerId();
							consumerMaster.setCustomer_id(customer_id+1);
						} catch (Exception e) {
							e.getMessage();
							consumerMaster.setCustomer_id(consumerMasterApproval.getCustomer_id());
						}
						
						consumerMaster.setRoad_street_nep(consumerMasterApproval.getRoad_street_nep());
						consumerMaster.setMpc_name_eng(consumerMasterApproval.getMpc_name_eng());
						consumerMaster.setMpc_name_nep(consumerMasterApproval.getMpc_name_nep()) ;

						consumerMaster.setWard_no(consumerMasterApproval.getWard_no());

						consumerMaster.setArea_no(consumerMasterApproval.getArea_no());
						consumerMaster.setPhone_no(consumerMasterApproval.getPhone_no()) ;

						consumerMaster.setMobile_no(consumerMasterApproval.getMobile_no()) ;

						consumerMaster.setCitizenship_no(consumerMasterApproval.getCitizenship_no());

						consumerMaster.setMtr_condate_eng(consumerMasterApproval.getMtr_condate_eng());

						consumerMaster.setMtr_condate_nep(consumerMasterApproval.getMtr_condate_nep());

						consumerMaster.setMtr_ser_no(consumerMasterApproval.getMtr_ser_no());
						consumerMaster.setMtr_rdng(consumerMasterApproval.getMtr_rdng());
						consumerMaster.setMtr_reader(consumerMasterApproval.getMtr_reader());
						consumerMaster.setReading_day(consumerMasterApproval.getReading_day());
						consumerMaster.setCon_category(consumerMasterApproval.getCon_category());
						consumerMaster.setCon_type(consumerMasterApproval.getCon_type()) ;
						consumerMaster.setCon_satatus(consumerMasterApproval.getCon_satatus());
						consumerMaster.setAverage(consumerMasterApproval.getAverage());
						consumerMaster.setSewage_used(consumerMasterApproval.getSewage_used()) ;
						consumerMaster.setPlot_no(consumerMasterApproval.getPlot_no());
						consumerMaster.setLoc_no(consumerMasterApproval.getLoc_no()) ;
						consumerMaster.setArea_type(consumerMasterApproval.getArea_type());
						consumerMaster.setDisdate_eng(consumerMasterApproval.getDisdate_eng()) ;
						consumerMaster.setDisdate_nep(consumerMasterApproval.getDisdate_nep());
						consumerMaster.setDis_reason(consumerMasterApproval.getDis_reason()) ;
						consumerMaster.setRemarks(consumerMasterApproval.getRemarks());
						consumerMaster.setDenoted_by(consumerMasterApproval.getDenoted_by()) ;
						consumerMaster.setEnt_date_eng(consumerMasterApproval.getEnt_date_eng()) ;
						consumerMaster.setEnt_date_nep(consumerMasterApproval.getEnt_date_nep());
						consumerMaster.setUsername(consumerMasterApproval.getUsername()) ;
						consumerMaster.setBalance(consumerMasterApproval.getBalance());
						consumerMaster.setLedgerno(consumerMasterApproval.getLedgerno()) ;
						consumerMaster.setFolio(consumerMasterApproval.getFolio()) ;
						consumerMaster.setPipe_size(consumerMasterApproval.getPipe_size()) ;
						consumerMaster.setSeq_no(consumerMasterApproval.getSeq_no()) ;
						consumerMaster.setFixedCharges(consumerMasterApproval.getFixedCharges());
						consumerMaster.setConnc_date_eng(consumerMasterApproval.getConnc_date_eng());
						consumerMaster.setConc_date_nep(consumerMasterApproval.getConc_date_nep());
						consumerMaster.setCostumerGroup(consumerMasterApproval.getCostumerGroup()) ;
						consumerMaster.setConsumer_title(consumerMasterApproval.getConsumer_title());
						consumerMaster.setStatus(null);
						consumerMastersList.add(consumerMaster);

					}
					
					// Inserting Blank record Into Ledger Table

					long checkConsumerNoInledger=billingLedgerDao.checkConnect_noInMaster(consumerMasterApproval.getConnection_no());
					
					//System.out.println("-----checkConsumerNoInledger------"+checkConsumerNoInledger);
					
					if(checkConsumerNoInledger==0){
					    
						
						
						BillingLedgerEntity ble=new BillingLedgerEntity();
					
						ble.setConnection_no(consumerMasterApproval.getConnection_no());
						ble.setMr_id(consumerMasterApproval.getMtr_reader());
						
						ble.setPrevious_reading(0.0);
						ble.setPresent_reading(0.0);
						ble.setConsumption((double) 0);
						ble.setWater_charges((double) 0);
						ble.setService_charge((double) 0);
						ble.setArrears((double) 0);
						ble.setInterest((double) 0);
						ble.setLatefee((double) 0);
						ble.setNet_amount((double) 0);
						ble.setMcunits((double) 0);
						ble.setPenalty(0.0);
						
						ble.setUser_id("");
						ble.setRebate((double) 0);
						ble.setTotalamt((double) 0);
						ble.setSuspense((double) 0);
						
						ble.setWardno(consumerMasterApproval.getWard_no());
						ble.setReading_day((consumerMasterApproval.getReading_day()));
						ble.setPipe_size(consumerMasterApproval.getPipe_size());
						
						ble.setSw_charges((double) 0);
						ble.setOther(0.0);
						if(consumerMasterApproval.getBranch()!=null&&!consumerMasterApproval.getBranch().isEmpty()){
							
							ble.setSitecode(consumerMasterApproval.getBranch());	
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
						    
						if("Metered".equalsIgnoreCase(consumerMasterApproval.getCon_type())){
							
							    String newnepday="";
							    if(String.valueOf(consumerMasterApproval.getReading_day()).length()==1){
							    	newnepday="0"+consumerMasterApproval.getReading_day();
							    }else{
							    	newnepday=""+consumerMasterApproval.getReading_day();
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
					    	billingLedgerList.add(ble);
					    }
					    
					}
					
				}
				consumerMasterDao.custombatchSave(consumerMastersList);
				
				if(!billingLedgerList.isEmpty()){
				 billingLedgerDao.custombatchSave(billingLedgerList);
				}
			}
			else{
				//ConsumerMasterApproval consumerMasterApproval=consumerMasterApprovalDao.customfind(Integer.parseInt(ids));
				List<BillingLedgerEntity> billingLedgerList= new ArrayList<>();
				ConsumerMasterApproval cm =  consumerMasterApprovalDao.findCustomerById(Integer.parseInt(ids));
				if(cm!=null){
				List<ConsumerMasterApproval> c=new ArrayList<>();
				c.add(cm);
				for (ConsumerMasterApproval consumerMasterApproval : c) 
				{
					BsmartWaterLogger.logger.info("is ===> "+consumerMasterApproval.getConnection_no());
					consumerMasterApproval.setApprove_status(1);
					consumerMasterApprovalDao.customupdate(consumerMasterApproval);
					//consumerMasterApprovalDao.pushDataFromApprovTab(Integer.parseInt(ids),schemaName);
					
					long checkConsumerNoInmaster=consumerMasterDao.checkConnect_noInMaster(consumerMasterApproval.getConnection_no());
					
					if(checkConsumerNoInmaster==0){
					ConsumerMaster consumerMaster=new ConsumerMaster();
					
					consumerMaster.setBranch(consumerMasterApproval.getBranch());
					consumerMaster.setConnection_no(consumerMasterApproval.getConnection_no());
					consumerMaster.setName_eng(consumerMasterApproval.getName_eng()) ;

					consumerMaster.setName_nep(consumerMasterApproval.getName_nep());
					consumerMaster.setFname_eng(consumerMasterApproval.getFname_eng());

					consumerMaster.setFname_nep(consumerMasterApproval.getFname_nep());

					consumerMaster.setGfname_eng(consumerMasterApproval.getGfname_eng()) ;

					consumerMaster.setGfname_nep(consumerMasterApproval.getGfname_nep());

					consumerMaster.setAddress_eng(consumerMasterApproval.getAddress_eng());

					consumerMaster.setAddress_nep(consumerMasterApproval.getAddress_nep()) ;

					consumerMaster.setTole_name_eng(consumerMasterApproval.getTole_name_eng());

					consumerMaster.setTole_name_nep(consumerMasterApproval.getTole_name_eng());
					consumerMaster.setRoad_street_eng(consumerMasterApproval.getRoad_street_eng());

					consumerMaster.setRoad_street_nep(consumerMasterApproval.getRoad_street_nep());
					consumerMaster.setMpc_name_eng(consumerMasterApproval.getMpc_name_eng());
					consumerMaster.setMpc_name_nep(consumerMasterApproval.getMpc_name_nep()) ;

					consumerMaster.setWard_no(consumerMasterApproval.getWard_no()); // check this value
					consumerMaster.setCustomer_id(consumerMasterApproval.getCustomer_id());
					consumerMaster.setArea_no(consumerMasterApproval.getArea_no());
					consumerMaster.setPhone_no(consumerMasterApproval.getPhone_no()) ;

					consumerMaster.setMobile_no(consumerMasterApproval.getMobile_no()) ;

					consumerMaster.setCitizenship_no(consumerMasterApproval.getCitizenship_no());

					consumerMaster.setMtr_condate_eng(consumerMasterApproval.getMtr_condate_eng());

					consumerMaster.setMtr_condate_nep(consumerMasterApproval.getMtr_condate_nep());

					consumerMaster.setMtr_ser_no(consumerMasterApproval.getMtr_ser_no());
					consumerMaster.setMtr_rdng(consumerMasterApproval.getMtr_rdng());
					consumerMaster.setMtr_reader(consumerMasterApproval.getMtr_reader());
					consumerMaster.setReading_day(consumerMasterApproval.getReading_day());
					consumerMaster.setCon_category(consumerMasterApproval.getCon_category());
					consumerMaster.setCon_type(consumerMasterApproval.getCon_type()) ;
					consumerMaster.setCon_satatus(consumerMasterApproval.getCon_satatus());
					consumerMaster.setAverage(consumerMasterApproval.getAverage());
					consumerMaster.setSewage_used(consumerMasterApproval.getSewage_used()) ;
					consumerMaster.setPlot_no(consumerMasterApproval.getPlot_no());
					consumerMaster.setLoc_no(consumerMasterApproval.getLoc_no()) ;
					consumerMaster.setArea_type(consumerMasterApproval.getArea_type());
					consumerMaster.setDisdate_eng(consumerMasterApproval.getDisdate_eng()) ;
					consumerMaster.setDisdate_nep(consumerMasterApproval.getDisdate_nep());
					consumerMaster.setDis_reason(consumerMasterApproval.getDis_reason()) ;
					consumerMaster.setRemarks(consumerMasterApproval.getRemarks());
					consumerMaster.setDenoted_by(consumerMasterApproval.getDenoted_by()) ;
					consumerMaster.setEnt_date_eng(consumerMasterApproval.getEnt_date_eng()) ;
					consumerMaster.setEnt_date_nep(consumerMasterApproval.getEnt_date_nep());
					consumerMaster.setUsername(consumerMasterApproval.getUsername()) ;
					consumerMaster.setBalance(consumerMasterApproval.getBalance());
					consumerMaster.setLedgerno(consumerMasterApproval.getLedgerno()) ;
					consumerMaster.setFolio(consumerMasterApproval.getFolio()) ;
					consumerMaster.setPipe_size(consumerMasterApproval.getPipe_size()) ;
					consumerMaster.setSeq_no(consumerMasterApproval.getSeq_no()) ;
					consumerMaster.setFixedCharges(consumerMasterApproval.getFixedCharges());
					consumerMaster.setConnc_date_eng(consumerMasterApproval.getConnc_date_eng());
					consumerMaster.setConc_date_nep(consumerMasterApproval.getConc_date_nep());
					consumerMaster.setCostumerGroup(consumerMasterApproval.getCostumerGroup()) ;
					consumerMaster.setMeterHired(consumerMasterApproval.getMeterHired());
					consumerMaster.setMeterRentCharges(consumerMasterApproval.getMeterRentCharges());
					consumerMaster.setConsumer_title(consumerMasterApproval.getConsumer_title());
					consumerMasterDao.customsave(consumerMaster);
					}
					long checkConsumerNoInledger=billingLedgerDao.checkConnect_noInMaster(consumerMasterApproval.getConnection_no());
					
					
					if(checkConsumerNoInledger==0){
					    
						BillingLedgerEntity ble=new BillingLedgerEntity();
					
						ble.setConnection_no(consumerMasterApproval.getConnection_no());
						ble.setMr_id(consumerMasterApproval.getMtr_reader());
						ble.setRdng_mth(month);
						ble.setPrevious_reading(0.0);
						ble.setPresent_reading(0.0);
						ble.setConsumption((double) 0);
						ble.setWater_charges((double) 0);
						ble.setService_charge((double) 0);
						ble.setArrears((double) 0);
						ble.setInterest((double) 0);
						ble.setLatefee((double) 0);
						ble.setNet_amount((double) 0);
						ble.setMcunits((double) 0);
						ble.setPenalty(0.0);
						
						ble.setUser_id("");
						ble.setRebate((double) 0);
						ble.setTotalamt((double) 0);
						ble.setSuspense((double) 0);
						
						ble.setWardno(consumerMasterApproval.getWard_no());
						ble.setReading_day((consumerMasterApproval.getReading_day()));
						ble.setPipe_size(consumerMasterApproval.getPipe_size());
						
						ble.setSw_charges((double) 0);
						ble.setOther(0.0);
						if(consumerMasterApproval.getBranch()!=null&&!consumerMasterApproval.getBranch().isEmpty()){
							
							ble.setSitecode(consumerMasterApproval.getBranch());	
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
						    
						if("Metered".equalsIgnoreCase(consumerMasterApproval.getCon_type())){
							

							
						    String newnepday="";
						    if(String.valueOf(consumerMasterApproval.getReading_day()).length()==1){
						    	newnepday="0"+consumerMasterApproval.getReading_day();
						    }else{
						    	newnepday=""+consumerMasterApproval.getReading_day();
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
							ble.setPrevious_read_date(new Date());
							
							String newmonth="";
						    if(String.valueOf(month).length()==1){
						    	newmonth="0"+month;
						    }else{
						    	newmonth=""+month;
						    }
						    ble.setRdng_mth(Integer.parseInt(newmonth));
							ble.setMonthyear(Integer.parseInt(year+""+newmonth));
						}
						
						
						if("Temporary".equalsIgnoreCase(consumerMasterApproval.getCon_satatus()) 
								|| "Defaulter".equalsIgnoreCase(consumerMasterApproval.getCon_satatus())){
							
							ble.setBillno(ss[0]+""+newnepmonth+""+consumerMasterApproval.getConnection_no());
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
					    
					    String monthyearnepali=ss[0]+""+newnepmonth;
					    ble.setMonthyearnep(ss[0]+""+newnepmonth);
					    ble.setBill_period(1.0);
					   
					    int monthyearnep=billingLedgerDao.getMaxMonthYearNepali();
					    if(monthyearnepali.equalsIgnoreCase(""+monthyearnep)){
					    	billingLedgerList.add(ble);
					    }
					}
				
				
				
				
				}
				// Inserting Blank record Into Ledger Table
				if(!billingLedgerList.isEmpty()){
				 billingLedgerDao.custombatchSave(billingLedgerList);
				}
				}	
			}
			
			
			return "Approved Successfully";
		}
		return null;
	}
	@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Override
	public String rejectConsumerDetails(HttpServletRequest request) {
		
		//HttpSession session=request.getSession(false);
	//	String schemaName=(String) session.getAttribute("schemaName");
		//BsmartWaterLogger.logger.info("---------Schema Name-------------"+schemaName);
		String ids=request.getParameter("masterId");
	    
	    
		if(ids!=null&&!ids.isEmpty()){
			
			if(ids.contains(",")){
				
				String []masIds=ids.split(",");
				
				for(String s:masIds){
					
					ConsumerMasterApproval consumerMasterApproval=consumerMasterApprovalDao.findCustomerById(Integer.parseInt(s));
					consumerMasterApproval.setApprove_status(2);
					consumerMasterApprovalDao.customupdate(consumerMasterApproval);
				                   }
				return "Rejected Successfully";
							
			} else{
			ConsumerMasterApproval consumerMasterApproval=consumerMasterApprovalDao.findCustomerById(Integer.parseInt(ids));
			consumerMasterApproval.setApprove_status(2);
			consumerMasterApprovalDao.customupdate(consumerMasterApproval);
			
			return "Rejected Successfully";
			}
		}
		return null;
	}
	@Override
	public List<ConsumerMaster> getMasterDataByConnectionNum(String conNum) {
		List<ConsumerMaster> conNumBased = consumerMasterDao.getMasterDataByConnectionNum(conNum);
		return conNumBased;
	}


	@Override
	public List<ConsumerMaster> getMasterDataByName(String name) {
		List<ConsumerMaster> namebased = consumerMasterDao.getMasterDataByName(name);
		return namebased;
	}


	@Override
	public List<ConsumerMaster> getMasterDataByWardNum(String wardNum) {
		List<ConsumerMaster> wardNumBased = consumerMasterDao.getMasterDataByWardNum(wardNum);
		return wardNumBased;
	}
	
	@Override
	public Object[] getPipeSizeAndConType(String connection_no) {
		
		return consumerMasterDao.getPipeSizeAndConType(connection_no);
	}
	
	@Override
	public List<String> getDistictConnCategory() {
		return consumerMasterDao.getDistictConnCategory();
	}
	
	@Override
	public List<Double> getDistictpipeSize() {
		
		return consumerMasterDao.getDistictpipeSize();
	}
	@Override
	public String getTotalConsumersCount(String siteCode) {
		return consumerMasterDao.getTotalConsumersCount(siteCode);
	}

	@Override
	public List<ConsumerMaster> getMasterDataByAreaNo(String areaNo) {
		List<ConsumerMaster> areaNoBased = consumerMasterDao.getMasterDataByAreaNo(areaNo);
		return areaNoBased;
	}


	@Override
	public List<ConsumerMaster> getMasterDataByPhoneNum(String phoneNum) {
		List<ConsumerMaster> phoneNoBased = consumerMasterDao.getMasterDataByPhoneNum(phoneNum);
		return phoneNoBased;
	}


	@Override
	public void update(ConsumerMaster customer) {
		consumerMasterDao.customupdate(customer);
		
	}


	@Override
	public long countByWardNoRdayPS(String wardNo, Integer readingday,
			double pipesize,String concategory) {
		return consumerMasterDao.countByWardNoRdayPS(wardNo,readingday,pipesize,concategory);
	}


	@Override
	public List<ConsumerMaster> getMasterDataByOldConnNum(String oldConNum) {
		List<ConsumerMaster> oldConnNoBased = consumerMasterDao.getMasterDataByOldConnNum(oldConNum);
		return oldConnNoBased;
	}


	@Override
	public List<String> getDistictWardNosUnmetered() {
		return consumerMasterDao.getDistictWardNosUnmetered();
	}


	@Override
	public List<String> getDistictMonthYearNep() {
		return consumerMasterDao.getDistictMonthYearNep();
	}


	@Override
	public long getMaxConsumerId() 
	{
		
		return consumerMasterDao.getMaxConsumerId();
	}


	@Override
	public List<?> getPendingConnForBilling(int approve_status,HttpServletRequest req) {
		return consumerMasterDao.getPendingConnForBilling(approve_status,req);
	}


	@Override
	public List<ConsumerMaster> getMasterDataByConnectionNumNew(String conNum,String schemaName) {
		List<ConsumerMaster> conNumBased = consumerMasterDao.getMasterDataByConnectionNumNew(conNum,schemaName);
		return conNumBased;
	}


	@Override
	public List<?> findByApplicationById(long applicationId) {
		return consumerMasterDao.findByApplicationById(applicationId);
	}


	@Override
	public List<?> getAllSchemaData() {
		
		return consumerMasterDao.getAllSchemaData();
	}


	@Override
	public List<?> getMonthWisecCollection() {
		
		return consumerMasterDao.getMonthWisecCollection();
	}


	@Override
	public List<?> getBranchWiseData() {
		return consumerMasterDao.getBranchWiseData();
	}


	@Override
	public Object[] getTariffData(String connectionNo) {
		
		return consumerMasterDao.getTariffData(connectionNo);
	}


	@Override
	public List<?> getEstimationData(long applicationId) {
		return consumerMasterDao.getEstimationData(applicationId);
	}


	@Override
	public List<?> getNewConnectionApproveList(String sitecode) {
     
		List<?> list=consumerMasterDao.getNewConnectionApproveList(sitecode);
		List<Map<String,Object>> result=new ArrayList<>();
		
		for(Iterator<?> itr=list.iterator();itr.hasNext();)
		{
			Map<String,Object> map=new LinkedHashMap<>();
			Object[] obj=(Object[]) itr.next();
			
			map.put("name", obj[0]);
			map.put("conn", obj[1]);
			map.put("areaNo", obj[2]);
			map.put("lastUpdatedBy", obj[3]);
			if(obj[4]!= null){
				String rdate = new SimpleDateFormat("dd-MM-yyyy").format(obj[4]);
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
			if ("11".equalsIgnoreCase(english[1])) {
				cday = cday + 1;
			}
			int cmonth = Integer.parseInt(english[1]);
			int cyear = Integer.parseInt(english[2]);
			
			BsmartWaterLogger.logger.info(cday + "cday" + cmonth + "cmonth" + cyear);
			//System.err.println(cday + "cday" + cmonth + "cmonth" + cyear);
			
			String nepalidate = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
			map.put("lastUpdatedDate",nepalidate);
			
			}
			else
			{
				map.put("lastUpdatedDate","");
				
			}
			
			map.put("connType", obj[5]);
			
			
			result.add(map);
		
		   
	}
		return result;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public String updateTables() {
		
		return consumerMasterDao.updateTables();
	}
}




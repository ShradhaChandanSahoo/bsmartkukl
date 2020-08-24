package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.BillCorrectionApproveDao;
import com.bcits.bsmartwater.model.BillApproveEntity;
import com.bcits.bsmartwater.service.BillCorrectionApproveService;

@Service
public class BillCorrectionApproveServiceImpl implements BillCorrectionApproveService {

	@Autowired
	BillCorrectionApproveDao billCorrectionApproveDao;
	
	@SuppressWarnings("serial")
	@Override
	public List<?> getBillPendingApproval() {
		
		List<?> billAppList= billCorrectionApproveDao.getBillPendingApproval();
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", (String)values[0]);
					put("name_eng", (String)values[1]);
					put("name_nep", (String)values[2]);
					put("con_category", (String)values[3]);
					put("mobile_no", (String)values[4]);
					put("con_type", (String)values[5]);
					put("billid", (int)values[6]);
					put("monthyear", (Integer)values[7]);
					put("remarks", values[8]);
				}
			});
		}
		return bills;
		
	}

	@Override
	public void save(BillApproveEntity billApproveEntity) {
		billCorrectionApproveDao.customsave(billApproveEntity);
		
	}

	@Override
	public void setBillApprove(int billId,int billStatus) {

		billCorrectionApproveDao.setBillApprove(billId,billStatus);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> findByConnectionNo(String connectionNo) {
		
		List<BillApproveEntity> list= billCorrectionApproveDao.findByConnectionNo(connectionNo);
		List<Map<String, Object>> billingDetailsMap =  new ArrayList<Map<String, Object>>(); 
		
		for (final BillApproveEntity record :list) 
		  {
			billingDetailsMap.add(new HashMap<String, Object>() {	 
			{  
				put("connection_no", record.getConnection_no());
				put("previous_reading", record.getPrevious_reading());
				put("present_reading", record.getPresent_reading());
				put("consumption", record.getPresent_reading()-record.getPrevious_reading());
				put("rdng_date", billCorrectionApproveDao.getDate3(record.getRdng_date()));
				put("rdng_date_nep", record.getRdng_date_nep());
				if(record.getBill_date()!=null){
					put("bill_date", billCorrectionApproveDao.getDate3(record.getBill_date()));
				}
				put("bill_date_nep", record.getBill_date_nep());
				if(record.getPrevious_read_date()!=null){
				put("previous_read_date", billCorrectionApproveDao.getDate3(record.getPrevious_read_date()));
				}
				
				put("water_charges", record.getWater_charges());
				put("arrears", record.getArrears());
				put("net_amount", record.getNet_amount());
				put("penalty", record.getPenalty());
				if(record.getMc_status()==1){
					put("mc_status", "Door Lock");
				}else if(record.getMc_status()==2){
					put("mc_status", "Meter Block");
				}else if(record.getMc_status()==3){
					put("mc_status", "Meter Burried");
				}else if(record.getMc_status()==4){
					put("mc_status", "Meter Damaged");
				}else if(record.getMc_status()==5){
					put("mc_status", "House not found");
				}else if(record.getMc_status()==6){
					put("mc_status", "No Water Supply");
				}else if(record.getMc_status()==7){
					put("mc_status", "Meter Removed(No Meter)");
				}else if(record.getMc_status()==8){
					put("mc_status", "Low Water Supply");
				}else if(record.getMc_status()==9){
					put("mc_status", "Dog Presence");
				}else if(record.getMc_status()==10){
					put("mc_status", "Meter Sheild Broken");
				}else if(record.getMc_status()==11){
					put("mc_status", "Temporary hole block");
				}else if(record.getMc_status()==12){
					put("mc_status", "Permanent hole block");
				}else if(record.getMc_status()==13){
					put("mc_status", "Service Line disconnected");
				}else if(record.getMc_status()==14){
					put("mc_status", "House Collapse(Earthquake)");
				}else if(record.getMc_status()==15){
					put("mc_status", "Unmetered");
				}else if(record.getMc_status()==16){
					put("mc_status", "Reading");
				}
				else if(record.getMc_status()==56){
					put("mc_status", "Self Reading");
				}
				else if(record.getMc_status()==17){
					put("mc_status", "No Reading");
				}
				else if(record.getMc_status()==18){
					put("observation", "Dual Record");
				}
				else if(record.getMc_status()==19){
					put("observation", "PID");
				}
				put("other", record.getOther());
				put("rebate", record.getRebate());
				put("monthyear", record.getMonthyear());
			    put("bill_period", record.getBill_period());  
			   
			    put("sw_charges", record.getSw_charges());
			    put("additional_charges", record.getAdditional_charges());
			    put("minimum_charges", record.getMinimum_charges());
			    put("excess_charges", record.getExcess_charges());
			    put("mtr_rent", record.getMtr_rent());
			    put("avg_units", record.getAvg_units());
			    put("open_balance", record.getOpen_balance());
			    put("remarks", record.getRemarks());
			    
			}});
	 }
		return billingDetailsMap;
	 
	}

	@Override
	public BillApproveEntity findById(int billId) {
	
		return billCorrectionApproveDao.customfind(billId);
	}

	@Override
	public void update(BillApproveEntity billApproveEntity) {
		
		billCorrectionApproveDao.customupdate(billApproveEntity);
		
	}

	@Override
	public long findCountByConNoBillStatus(String connectionNo, int billstatus) {
		return billCorrectionApproveDao.findCountByConNoBillStatus(connectionNo,billstatus);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getBillCorrectionChangeList() {
		
		List<?> billAppList= billCorrectionApproveDao.getBillCorrectionChangeList();
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", (String)values[0]);
					put("name_eng", (String)values[1]);
					put("con_type", (String)values[2]);
					put("wardno", (String)values[3]);
					put("remarks",(String)values[4]);
					put("billno", (String)values[5]);
					
					put("arrears", values[6]);
					put("water_charges", values[7]);
					put("sw_charges", values[8]);
					put("mtr_rent", values[9]);
					put("net_amount",values[10]);
					
					put("approve_status",values[11]);
					put("added_by",values[12]);
					put("updated_by",values[13]);
				}
			});
		}
		return bills;
		
	}

	@Override
	public void updateMasterDataDaily() {

		billCorrectionApproveDao.updateMasterDataDaily();
	}
	
	@Override
	public void updateMasterDataMonthEnd(String schema) {
		billCorrectionApproveDao.updateMasterDataMonthEnd(schema);
		
	}

	@Override
	public void updateDailyPaymentByMonthSelectionBalanceDaily(String currentdate) {
		billCorrectionApproveDao.updateDailyPaymentByMonthSelectionBalanceDaily(currentdate);
		
	}
	
	@Override
	public void recalculateAllLedger() {
		billCorrectionApproveDao.recalculateAllLedger();
		
	}

	@Override
	public void updateRecalculateArrears(String connectionno, String schemaName,String userName) {
		billCorrectionApproveDao.updateRecalculateArrears(connectionno,schemaName,userName);
		
	}

	@Override
	public long pendingCount(String monthyearnep) {
		return billCorrectionApproveDao.pendingCount(monthyearnep);
	}

	

}

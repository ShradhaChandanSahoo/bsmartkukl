package com.bcits.bsmartwater.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.BillPenaltyAdjDao;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.model.BillPenaltyAdjustment;
import com.bcits.bsmartwater.service.BillPenaltyAdjService;

@Service
public class BillPenaltyAdjServiceImpl implements BillPenaltyAdjService {

	@Autowired
	BillPenaltyAdjDao billPenaltyAdjDao;
	
	@Autowired
	private MasterGenericDAO masterGenericDAO;
	
	@SuppressWarnings("serial")
	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status) {

		List<?> list= billPenaltyAdjDao.getPendingConnectionsToApprove(approve_status);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", values[0]);
					put("name_eng", values[1]);
					put("area_no",values[2]);					
					put("pipe_size", values[3]);
					put("bill_adj_amount", values[4]);
					put("penalty_adj_amount", values[5]);
					put("from_mon_year", values[6]);
					put("to_mon_year", values[7]);
					put("submit_by", values[8]);
					put("submit_date", billPenaltyAdjDao.getDate3((Date)values[9]));
					put("remakrs",values[10]);
				}
			});
		}
		return bills;
		
		
		
	
	}

	@Override
	public BillPenaltyAdjustment getConTypeByConNo(String connection_no,int approve_status) {
		return billPenaltyAdjDao.getConTypeByConNo(connection_no,approve_status);
	}
	
	@Override
	public BillPenaltyAdjustment getPendingBoardByConNo(String connection_no,int approve_status) {
		return billPenaltyAdjDao.getPendingBoardByConNo(connection_no,approve_status);
	}

	@Override
	public void update(BillPenaltyAdjustment billPenaltyAdjustment) {
		billPenaltyAdjDao.customupdate(billPenaltyAdjustment);
		
	}

	@Override
	public void save(BillPenaltyAdjustment billPenaltyAdjustment) {
		billPenaltyAdjDao.customsave(billPenaltyAdjustment);
		
	}
	
	@Override
	public BillPenaltyAdjustment checkPendingRequests(String connection_no, int approve_status, String adj_type) {
		return billPenaltyAdjDao.checkPendingRequests(connection_no,approve_status,adj_type);

	}
	
	@Override
	public BillPenaltyAdjustment checkPendingRequestsSameMonth(String connection_no, String monthyear) {
		return billPenaltyAdjDao.checkPendingRequestsSameMonth(connection_no,monthyear);
	}
	
	@SuppressWarnings("serial")
	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status,String adj_type) {
		List<?> list=  billPenaltyAdjDao.getPendingConnectionsToApprove(approve_status,adj_type);
		

		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", values[0]);
					put("name_eng", values[1]);
					put("area_no",values[2]);					
					put("pipe_size", values[3]);				
					put("bill_adj_amount", values[4]);
					put("penalty_adj_amount", values[5]);
					put("from_mon_year", values[6]);
					put("to_mon_year", values[7]);
					put("submit_by", values[8]);
					put("submit_date", billPenaltyAdjDao.getDate3((Date)values[9]));
					put("remarks", values[10]);
					put("board_adj_amt", values[11]);
					//System.out.println(values[12]);
					if(values[12]==null) {
						put("rebate_adj_amount","0.0");
					}
					put("rebate_adj_amount", values[12]);
				}
			});
		}
		return bills;
	
	}

	@Override
	public long getTransactionPending(String connectionNo) {
		return billPenaltyAdjDao.getTransactionPending(connectionNo);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getAdjCorrList() {
		try{
			
			List<?> list=  billPenaltyAdjDao.getAdjCorrList();
			List<Map<String, Object>> adjustments = new ArrayList<Map<String, Object>>();
			for (final Iterator<?> i = list.iterator(); i.hasNext();) {
				
				adjustments.add(new HashMap<String, Object>() {
					{
						final Object[] values = (Object[])i.next();
						int status=(int) values[13];
						
						put("connection_no", values[0]);
						put("name_eng", values[1]);
						put("area_no", values[2]==null?"":(String)values[2]);
						put("bill_adj_amount", values[3]==null?0:(Double)values[3]);
						put("penalty_adj_amount", values[4]==null?0:(Double)values[4]);
						String adj_type=(String)values[5];
						if("BAC".equals(adj_type)){
							put("adj_type", "Arrear Correction");
							if(status==0){
								put("approve_status", "Pending for Arrear Correction Approve");
							} else if(status==1){
								put("approve_status", "Approved");
							}
							else{
								put("approve_status", "Rejected");
							}
						} else if("BDJ".equals(adj_type)){
							put("adj_type", "Arrear Adjustment");
							if(status==0){
								put("approve_status", "Pending for Transaction Approve");
							} else if(status==1){
								put("approve_status", "Pending in Head Office");
							} else if (status==3){
								put("approve_status", "Approved in Head Office");
							} else {
								put("approve_status", "Rejected");
							}
						} else if("BOARD".equals(adj_type)){
							put("adj_type", "Arrear Adjustment");
							if(status==0){
								put("approve_status", "Pending for Transaction Approve");
							} else if(status==1){
								put("approve_status", "Pending in Head Office");
							} else if (status==3){
								put("approve_status", "Approved in Head Office");
							} else {
								put("approve_status", "Rejected");
							}
						} else{
							put("adj_type", "");
							if(status==0){
								put("approve_status", "Pending for Transaction Approve");
							} else if(status==1){
								put("approve_status", "Pending in Head Office");
							} else if (status==3){
								put("approve_status", "Approved in Head Office");
							} else {
								put("approve_status", "Rejected");
							}
						}
						put("to_mon_year", values[6]);
						put("submit_by", values[7]);
						if(values[8]!=null){
							put("submit_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[8]));
						} else {
							put("submit_date", "");
						}
						
						put("trans_approve", values[9]);
						
						if(values[10]!=null){
							put("trans_approve_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[10]));
						} else {
							put("trans_approve", "");
						}
						
						put("approve_by", values[11]);
						
						if(values[12]!=null){
							put("approve_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[12]));
						} else {
							put("approve_date", "");
						}
						
						
						
						
						put("remarks", (String)values[14]);
					}
				});
			}
			return adjustments;
		} catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<?> getAdjTransList(String adj_type) {
		try {
			List<?> list = billPenaltyAdjDao.getAdjTransList(adj_type);
			List<Map<String, Object>> adjustments = new ArrayList<Map<String, Object>>();
			for (final Iterator<?> i = list.iterator(); i.hasNext();) {
				
				adjustments.add(new HashMap<String, Object>() {
					{
						final Object[] values = (Object[])i.next();
						int status=(int) values[13];
						
						put("connection_no", values[0]);
						put("name_eng", values[1]);
						put("area_no", values[2]==null?"":(String)values[2]);
						put("bill_adj_amount", values[3]==null?0:(Double)values[3]);
						put("penalty_adj_amount", values[4] == null ? 0 : (Double) values[4]);
						//String adj_type = (String) values[5];
						if (status == 0) {
							put("approve_status", "Pending for Transaction Approve");
						} else if (status == 1) {
							put("approve_status", "Trans Approved Pending in Head Office");
						} else if (status == 3) {
							put("approve_status", "Approved in Head Office");
						} else {
							if(values[11]==null){
								put("approve_status", "Rejected");
							}
							else{
								put("approve_status", "Rejected By Head Office");
							}
							
						}
						put("to_mon_year", values[6]);
						put("submit_by", values[7]);
						if(values[8]!=null){
							put("submit_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[8]));
						} else {
							put("submit_date", "");
						}
						
						put("trans_approve", values[9]);
						
						if(values[10]!=null){
							put("trans_approve_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[10]));
						} else {
							put("trans_approve", "");
						}
						
						put("approve_by", values[11]);
						
						if(values[12]!=null){
							put("approve_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[12]));
						} else {
							put("approve_date", "");
						}
						put("remarks", (String)values[14]);
						put("board_adj", values[15]==null?0:(Double)values[15]);
					}
				});
			}
			return adjustments;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// **********************brd correction
	
	@Override
	public List<?> getBrdCorrList(String adj_type) {
		try {
			List<?> list = billPenaltyAdjDao.getBrdCorrList(adj_type);
			List<Map<String, Object>> adjustments = new ArrayList<Map<String, Object>>();
			for (final Iterator<?> i = list.iterator(); i.hasNext();) {
				
				adjustments.add(new HashMap<String, Object>() {
					{
						final Object[] values = (Object[])i.next();
						int status=(int) values[13];
						
						put("connection_no", values[0]);
						put("name_eng", values[1]);
						put("area_no", values[2]==null?"":(String)values[2]);
						put("board_adj_amount", values[3]==null?0:(Double)values[3]);
						put("bill_adj_amount", values[4] == null ? 0 : (Double) values[4]);
						// String adj_type = (String) values[5];
						if (status == 0) {
							put("approve_status", "Pending for Approve");
						} else if (status == 1) {
							put("approve_status", "Approved");
						} else {
							put("approve_status", "Rejected");
						}
						put("to_mon_year", values[6]);
						put("submit_by", values[7]);
						/*if (values[8] != null) {
							put("submit_date", new SimpleDateFormat("dd-MM-yyyy").format((Date) values[8]));
						} else {
							put("submit_date", "");
						}*/
						
						
						if (values[8] == null) {
							put("submit_date", "");
						} else {
							String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)values[8]);
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
							put("submit_date", nepalimonthyear);
						}
						
						
						
						/*if(values[10]!=null){
							put("trans_approve_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[10]));
						} else {
							put("trans_approve", "");
						}*/
						
						
						if (values[12] == null) {
							put("trans_approve_date", "");
							put("trans_approve", "");
						} else {
							String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)values[12]);
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
							put("trans_approve_date", nepalimonthyear);
							put("trans_approve", values[11]);
						}
						
						
						put("remarks", (String)values[14]);
						put("rebate_adj_amount", values[16] == null ? 0 : (Double) values[16]);
					}
				});
			}
			return adjustments;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	
// *************************brd correction
	@Override
	public List<?> getArrearCorrList(String adj_type) {
		try {
			List<?> list = billPenaltyAdjDao.getAdjTransList(adj_type);
			List<Map<String, Object>> adjustments = new ArrayList<Map<String, Object>>();
			for (final Iterator<?> i = list.iterator(); i.hasNext();) {
				
				adjustments.add(new HashMap<String, Object>() {
					{
						final Object[] values = (Object[])i.next();
						int status=(int) values[13];
						
						put("connection_no", values[0]);
						put("name_eng", values[1]);
						put("area_no", values[2]==null?"":(String)values[2]);
						put("bill_adj_amount", values[3]==null?0:(Double)values[3]);
						put("penalty_adj_amount", values[4] == null ? 0 : (Double) values[4]);
						// String adj_type = (String) values[5];
						if (status == 0) {
							put("approve_status", "Pending for Approve");
						} else if (status == 1) {
							put("approve_status", "Approved");
						} else {
							put("approve_status", "Rejected");
						}
						put("to_mon_year", values[6]);
						put("submit_by", values[7]);
						/*if (values[8] != null) {
							put("submit_date", new SimpleDateFormat("dd-MM-yyyy").format((Date) values[8]));
						} else {
							put("submit_date", "");
						}*/
						
						
						if (values[8] == null) {
							put("submit_date", "");
						} else {
							String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)values[8]);
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
							put("submit_date", nepalimonthyear);
						}
						
						
						
						/*if(values[10]!=null){
							put("trans_approve_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[10]));
						} else {
							put("trans_approve", "");
						}*/
						
						
						if (values[10] == null) {
							put("trans_approve_date", "");
							put("trans_approve", "");
						} else {
							String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)values[10]);
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
							put("trans_approve_date", nepalimonthyear);
							put("trans_approve", values[9]);
						}
						
						
						put("remarks", (String)values[14]);
						put("rebate_adj_amount", values[16] == null ? 0 : (Double) values[16]);
					}
				});
			}
			return adjustments;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	@Override
	public long pendingCount(String monthyearnep) {
		
		return billPenaltyAdjDao.pendingCount(monthyearnep);
	}
	
	@Override
	public Object[] getTransactionCount(String connection_no) {
		return billPenaltyAdjDao.getTransactionCount(connection_no);
	}

	@Override
	public long pendingForArrCorr(String connectionNo) {
		return billPenaltyAdjDao.pendingForArrCorr(connectionNo);
	}

	@Override
	public List<?> getAdjTransListByBranch(String branch) {

		try {
			List<?> list = billPenaltyAdjDao.getAdjTransListByBranch(branch);
			List<Map<String, Object>> adjustments = new ArrayList<Map<String, Object>>();
			for (final Iterator<?> i = list.iterator(); i.hasNext();) {
				
				adjustments.add(new HashMap<String, Object>() {
					{
						final Object[] values = (Object[])i.next();
						int status=(int) values[13];
						
						put("connection_no", values[0]);
						put("name_eng", values[1]);
						put("area_no", values[2]==null?"":(String)values[2]);
						put("bill_adj_amount", values[3]==null?0:(Double)values[3]);
						put("penalty_adj_amount", values[4] == null ? 0 : (Double) values[4]);
						//String adj_type = (String) values[5];
						if (status == 0) {
							put("approve_status", "Pending for Transaction Approve");
						} else if (status == 1) {
							put("approve_status", "Trans Approved Pending in Head Office");
						} else if (status == 3) {
							put("approve_status", "Approved in Head Office");
						} else {
							if(values[11]==null){
								put("approve_status", "Rejected");
							}
							else{
								put("approve_status", "Rejected By Head Office");
							}
							
						}
						put("to_mon_year", values[6]);
						put("submit_by", values[7]);
						if(values[8]!=null){
							put("submit_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[8]));
						} else {
							put("submit_date", "");
						}
						
						put("trans_approve", values[9]);
						
						if(values[10]!=null){
							put("trans_approve_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[10]));
						} else {
							put("trans_approve", "");
						}
						
						put("approve_by", values[11]);
						
						if(values[12]!=null){
							put("approve_date", new SimpleDateFormat("dd-MM-yyyy").format((Date)values[12]));
						} else {
							put("approve_date", "");
						}
						put("remarks", (String)values[14]);
						put("board_adj", values[15]==null?0:(Double)values[15]);
					}
				});
			}
			return adjustments;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}

	@Override
	public BillPenaltyAdjustment getConTypeByConNo(String conNo, int approve_status, String branch) {
		return billPenaltyAdjDao.getConTypeByConNo(conNo,approve_status,branch);
	}

	@Override
	public void update(BillPenaltyAdjustment billPenaltyAdjustment, String branch) {
		billPenaltyAdjDao.update(billPenaltyAdjustment,branch);
		
	}

	@Override
	public Object[] getTransactionCountForBrdCr(String connection_no) {
		return billPenaltyAdjDao.getTransactionCountForBrdCr(connection_no);
	}

	@Override
	public List<?> getPendingBrdCrConnectionsToApprove(int approve_status,String adj_type) {
    List<?> list=  billPenaltyAdjDao.getPendingBrdCrConnectionsToApprove(approve_status,adj_type);
		

		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", values[0]);
					put("name_eng", values[1]);
					put("area_no",values[2]);					
					put("pipe_size", values[3]);
					put("board_adj_amount", values[4]);
					put("penalty_adj_amount", values[5]);
					put("from_mon_year", values[6]);
					put("to_mon_year", values[7]);
					put("submit_by", values[8]);
					put("submit_date", billPenaltyAdjDao.getDate3((Date)values[9]));
					put("remarks", values[10]);
					put("board_adj_amt", values[11]);
				//	System.err.println(values[12]+"rebate_adj_amt");
					if(values[12]==null)
					{
						put("rebate_adj_amount","0.0");
					}else {
					put("rebate_adj_amount", values[12]);
					}
				}
			});
		}
		return bills;
	}

	@Override
	public BillPenaltyAdjustment getPendingBoardCrByConNo(String string, int approve_status) {
		return billPenaltyAdjDao.getPendingBoardCrByConNo(string,approve_status);
	}
}

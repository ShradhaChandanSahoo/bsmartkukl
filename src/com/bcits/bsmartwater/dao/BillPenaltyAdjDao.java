package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.BillPenaltyAdjustment;

public interface BillPenaltyAdjDao extends GenericDAO<BillPenaltyAdjustment>{

	List<?> getPendingConnectionsToApprove(int approve_status);
	List<?> getPendingBrdCrConnectionsToApprove(int approve_status,String adj_type);
	BillPenaltyAdjustment getConTypeByConNo(String connection_no,int approve_status);
	BillPenaltyAdjustment checkPendingRequests(String connection_no,int approve_status, String adj_type);
	List<?> getPendingConnectionsToApprove(int approve_status, String adj_type);
	long getTransactionPending(String connectionNo);
	List<?> getAdjCorrList();
	List<?> getAdjTransList(String adj_type);
	List<?> getBrdCorrList(String adj_type);
	BillPenaltyAdjustment checkPendingRequestsSameMonth(String connection_no, String monthyear);
	long pendingCount(String monthyearnep);
	BillPenaltyAdjustment getPendingBoardByConNo(String string, int approve_status);
	BillPenaltyAdjustment getPendingBoardCrByConNo(String string, int approve_status);
	Object[] getTransactionCount(String connection_no);
	
	Object[] getTransactionCountForBrdCr(String connection_no);
	long pendingForArrCorr(String connectionNo);
	List<?> getAdjTransListByBranch(String branch);
	BillPenaltyAdjustment getConTypeByConNo(String conNo, int approve_status, String branch);
	void update(BillPenaltyAdjustment billPenaltyAdjustment, String branch);

}

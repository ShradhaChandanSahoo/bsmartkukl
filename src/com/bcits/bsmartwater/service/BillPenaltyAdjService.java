package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.BillPenaltyAdjustment;

public interface BillPenaltyAdjService {

	List<?> getPendingConnectionsToApprove(int approve_status);
	
	List<?> getPendingBrdCrConnectionsToApprove(int approve_status,String adjType);

	BillPenaltyAdjustment getConTypeByConNo(String string, int approve_status);

	void update(BillPenaltyAdjustment billPenaltyAdjustment);

	void save(BillPenaltyAdjustment billPenaltyAdjustment);

	BillPenaltyAdjustment checkPendingRequests(String connection_no,int i, String string);

	List<?> getPendingConnectionsToApprove(int approve_status, String string);

	long getTransactionPending(String connectionNo);

	List<?> getAdjCorrList();
	
	List<?> getBrdCorrList(String adj_type);
	List<?> getAdjTransList(String adj_type);
	
	List<?> getArrearCorrList(String adj_type);
	
	BillPenaltyAdjustment checkPendingRequestsSameMonth(String connection_no, String monthyear);

	long pendingCount(String monthyear);
	
	BillPenaltyAdjustment getPendingBoardByConNo(String string, int approve_status);
	BillPenaltyAdjustment getPendingBoardCrByConNo(String string, int approve_status);

	Object[] getTransactionCount(String connection_no);
	
	Object[] getTransactionCountForBrdCr(String connection_no);

	long pendingForArrCorr(String connectionNo);

	List<?> getAdjTransListByBranch(String branch);

	BillPenaltyAdjustment getConTypeByConNo(String conNo, int approve_status, String branch);

	void update(BillPenaltyAdjustment billPenaltyAdjustment, String branch);
}

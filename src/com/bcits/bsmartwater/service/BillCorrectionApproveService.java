package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.BillApproveEntity;

public interface BillCorrectionApproveService {

	List<?> getBillPendingApproval();

	void save(BillApproveEntity billApproveEntity);

	void setBillApprove(int billId, int billStatus);

	List<?> findByConnectionNo(String connectionNo);

	BillApproveEntity findById(int billId);

	void update(BillApproveEntity billApproveEntity);

	long findCountByConNoBillStatus(String connectionNo, int i);

	List<?> getBillCorrectionChangeList();

	void updateMasterDataDaily();
	
	void updateMasterDataMonthEnd(String schema);

	void updateDailyPaymentByMonthSelectionBalanceDaily(String currentdate);

	void updateRecalculateArrears(String connectionno, String schemaName, String userName);

	long pendingCount(String monthyear);

	void recalculateAllLedger();

}

package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.BillApproveEntity;

public interface BillCorrectionApproveDao extends GenericDAO<BillApproveEntity>{

	List<?> getBillPendingApproval();

	void setBillApprove(int billId, int billStatus);

	List<BillApproveEntity> findByConnectionNo(String connectionNo);

	long findCountByConNoBillStatus(String connectionNo, int billstatus);

	List<?> getBillCorrectionChangeList();

	void updateMasterDataDaily();

	void updateDailyPaymentByMonthSelectionBalanceDaily(String currentdate);

	void updateRecalculateArrears(String connectionno, String schemaName, String userName);

	long pendingCount(String monthyearnep);

	void recalculateAllLedger();

	void updateMasterDataMonthEnd(String schema);

}

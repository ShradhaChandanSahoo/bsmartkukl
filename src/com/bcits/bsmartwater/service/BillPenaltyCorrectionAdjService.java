package com.bcits.bsmartwater.service;

import java.util.List;



import com.bcits.bsmartwater.model.BillPenaltyCorrectionAjdutment;

public interface BillPenaltyCorrectionAdjService {
	List<?> getPendingConnectionsToApprove(int approve_status);

	BillPenaltyCorrectionAjdutment getConTypeByConNo(String string, int approve_status);

	void update(BillPenaltyCorrectionAjdutment billPenaltyCorrectionDao);

	void save(BillPenaltyCorrectionAjdutment billPenaltyCorrectionDao);

	
	
}

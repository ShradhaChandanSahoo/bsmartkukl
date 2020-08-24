package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.OwnerShipChange;

public interface OwnerShipChangeService {

	void save(OwnerShipChange ownerShipChangeEntity);

	List<?> getPendingConnectionsToApprove(int approve_status);
	
	List<?> ownerShipChangeReportRead(String from, String to, int approve_status);

	OwnerShipChange getRecordByConnectionNo(String string, int approve_status);

	void update(OwnerShipChange ownerShipChange);

	List<?> getOwnerShipChangeList();

	

}

package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.OwnerShipChange;

public interface OwnerShipChangeDao extends GenericDAO<OwnerShipChange>{

	List<?> getPendingConnectionsToApprove(int approve_status);

	OwnerShipChange getRecordByConnectionNo(String connection_no, int approve_status);

	List<?> getOwnerShipChangeList();

	List<?> ownerShipChangeReportRead(String from, String to, int approve_status);



}

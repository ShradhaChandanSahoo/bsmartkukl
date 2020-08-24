package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.ConTypeChangeEntity;

public interface ConTypeChangeDao extends GenericDAO<ConTypeChangeEntity>{

	List<?> getPendingConnectionsToApprove(int approve_status);

	ConTypeChangeEntity getConTypeByConNo(String connection_no,
			int approve_status);

	List<?> getConnectionTypeApproveList();

	List<?> conTypeChngReportRead(String from, String to, int status);

}

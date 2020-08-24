package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.ConTypeChangeEntity;

public interface ConTypeChangeService {

	void save(ConTypeChangeEntity conTypeChangeEntity);

	List<?> getPendingConnectionsToApprove(int approve_status);

	ConTypeChangeEntity getConTypeByConNo(String string, int approve_status);

	void update(ConTypeChangeEntity conTypeChangeEntity);

	List<?> getConnectionTypeApproveList();

	List<?> conTypeChngReportRead(String from, String to, int status);

}

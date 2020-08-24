package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.BillPenaltyCorrectionAjdutment;


public interface BillPenaltyCorrectionAdjusmtmentDAO  extends GenericDAO<BillPenaltyCorrectionAjdutment>
{
	List<?> getPendingConnectionsToApprove(int approve_status);
	BillPenaltyCorrectionAjdutment getConTypeByConNo(String connection_no,int approve_status);
}

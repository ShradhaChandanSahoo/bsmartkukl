package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.MiscellaneousPayment;

public interface MiscellaneousPaymentDao extends GenericDAO<MiscellaneousPayment>{

	List<MiscellaneousPayment> getSyncToPGRS(int status);

}

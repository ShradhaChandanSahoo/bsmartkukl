package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.MiscellaneousPayment;

public interface MiscellaneousPaymentService {

	void save(MiscellaneousPayment miscellaneousPayment);

	List<MiscellaneousPayment> getSyncToPGRS(int status);

	void update(MiscellaneousPayment miscellaneousPayment);

}

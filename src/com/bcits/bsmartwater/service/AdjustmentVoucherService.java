package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.AdjustmentVoucher;

public interface AdjustmentVoucherService {

	void save(AdjustmentVoucher adjustmentVoucherEntity);

	List<?> getAdjustmentList(int approve_status);
	long getMaxAdjustmentNo(String adjustmentNo);
}

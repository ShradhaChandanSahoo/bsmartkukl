package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.AdjustmentVoucher;

public interface AdjustmentVoucherDao extends GenericDAO<AdjustmentVoucher>{

	List<?> getAdjustmentList(int status);

	long getMaxAdjustmentNo(String adjustmentNo);

}

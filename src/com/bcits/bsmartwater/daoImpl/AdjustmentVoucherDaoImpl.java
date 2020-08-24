package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.AdjustmentVoucherDao;
import com.bcits.bsmartwater.model.AdjustmentVoucher;

@Repository
public class AdjustmentVoucherDaoImpl extends GenericDAOImpl<AdjustmentVoucher> implements AdjustmentVoucherDao {

	@Override
	public List<?> getAdjustmentList(int status) {
		return getCustomEntityManager().createNamedQuery("AdjustmentVoucher.getAdjustmentList").setParameter("status", status).getResultList();
	}
	
	@Override
	public long getMaxAdjustmentNo(String adjustmentNo) {
		try{
			return Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("AdjustmentVoucher.getMaxAdjustmentNo").setParameter("adjustment_no",adjustmentNo).getSingleResult()));
		}catch(Exception e){
			return 0;
		}
		
	}
}

package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.BillPenaltyCorrectionAdjusmtmentDAO;
import com.bcits.bsmartwater.model.BillPenaltyCorrectionAjdutment;

@Repository
public class BillPenaltyCorrectionAdjustmentDAOImpl extends GenericDAOImpl<BillPenaltyCorrectionAjdutment> implements BillPenaltyCorrectionAdjusmtmentDAO  {

	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status) {
		return 	getCustomEntityManager().createNamedQuery("BillPenaltyCorrectionAjdutment.getPendingConnectionsToApprove").setParameter("approve_status", approve_status).getResultList();

	}

	@Override
	public BillPenaltyCorrectionAjdutment getConTypeByConNo(String connection_no,int approve_status) {
		System.out.println("con No---"+connection_no+" Status----"+approve_status);
		try{
		return 	getCustomEntityManager().createNamedQuery("BillPenaltyCorrectionAjdutment.getConTypeByConNo",BillPenaltyCorrectionAjdutment.class).setParameter("connection_no", connection_no).setParameter("approve_status", approve_status).getSingleResult();
		}catch(Exception e){
		e.printStackTrace();
			return null;
		}
	}

	
	
}

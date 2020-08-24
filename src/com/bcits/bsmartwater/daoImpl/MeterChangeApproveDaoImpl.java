package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.MeterChangeApproveDao;
import com.bcits.bsmartwater.model.BillApproveEntity;
import com.bcits.bsmartwater.model.MeterChangeApproveEntity;

@Repository
public class MeterChangeApproveDaoImpl extends GenericDAOImpl<MeterChangeApproveEntity> implements MeterChangeApproveDao
{
	@Override
	public List<?> getMeterPendingApproval() {
		
		return getCustomEntityManager().createNamedQuery("MeterChangeApproveEntity.getMeterPendingApproval").getResultList();
	}
	
	@Override
	public List<MeterChangeApproveEntity> findByConnectionNo(String connectionNo) {
		return getCustomEntityManager().createNamedQuery("MeterChangeApproveEntity.findByConnectionNo").setParameter("connection_no", connectionNo).getResultList();
	}
	
	@Override
	public List<MeterChangeApproveEntity> findByConnNo(String connNo) {
		return getCustomEntityManager().createNamedQuery("MeterChangeApproveEntity.findByConnNo").setParameter("connNo", connNo).getResultList();
	}
	
	@Override
	public List<?> getMeterApproveList() {
		return getCustomEntityManager().createNamedQuery("MeterChangeApproveEntity.getMeterApproveList").getResultList();
	}

	@Override
	public MeterChangeApproveEntity getByConnectionNo(String conNo) {
		try {
			System.out.println("MeterChangeApproveEntity===============conNo===="+conNo);
			return getCustomEntityManager().createNamedQuery("MeterChangeApproveEntity.getByConnectionNo",MeterChangeApproveEntity.class).setParameter("connNo", conNo.toUpperCase()).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}

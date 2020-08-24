package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.MeterChangeApproveEntity;

public interface MeterChangeApproveDao extends GenericDAO<MeterChangeApproveEntity> 
{

	List<?> getMeterPendingApproval();

	List<MeterChangeApproveEntity> findByConnectionNo(String connectionNo);

	List<MeterChangeApproveEntity> findByConnNo(String connNo);

	List<?> getMeterApproveList();

	MeterChangeApproveEntity getByConnectionNo(String conNo);

}

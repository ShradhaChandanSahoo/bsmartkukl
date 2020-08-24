package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.MeterChangeApproveEntity;

public interface MeterChangeApproveService {

	List<?> getMeterPendingApproval();

	List<?> findByConnectionNo(String connectionNo);

	void update(MeterChangeApproveEntity meterChangeApproveEntity);

	MeterChangeApproveEntity findById(int meterId);

	List<MeterChangeApproveEntity> findByConnNo(String connNo);
	List<?> getMeterApproveList();

	MeterChangeApproveEntity getByConnectionNo(String string);

}

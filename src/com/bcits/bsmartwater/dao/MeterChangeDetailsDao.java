package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.MeterChangeDetailsEntity;

public interface MeterChangeDetailsDao extends GenericDAO<MeterChangeDetailsEntity>
{

	List<MeterChangeDetailsEntity> getMeterChangeDetailsByConnNum(String connId);

}

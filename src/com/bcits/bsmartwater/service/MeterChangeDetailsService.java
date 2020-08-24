package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.MeterChangeDetailsEntity;

public interface MeterChangeDetailsService 
{

	List<MeterChangeDetailsEntity> getMeterChangeDetailsByConnNum(String connId);

}

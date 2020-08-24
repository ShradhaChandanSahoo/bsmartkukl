package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.BillingLedgerDao;
import com.bcits.bsmartwater.dao.MeterChangeDetailsDao;
import com.bcits.bsmartwater.model.MeterChangeDetailsEntity;
import com.bcits.bsmartwater.service.MeterChangeDetailsService;

@Service
public class MeterChangeDetailsServiceImpl implements MeterChangeDetailsService 
{
	@Autowired
	MeterChangeDetailsDao meterChangeDetailsDao;
	
	@Override
	public List<MeterChangeDetailsEntity> getMeterChangeDetailsByConnNum(String connId) {
		return meterChangeDetailsDao.getMeterChangeDetailsByConnNum(connId);
	}

}

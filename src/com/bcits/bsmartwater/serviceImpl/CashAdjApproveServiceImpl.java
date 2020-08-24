package com.bcits.bsmartwater.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.CashAdjApproveDAO;
import com.bcits.bsmartwater.model.CashAdjApproveEntity;
import com.bcits.bsmartwater.service.CashAdjApproveService;
@Service
public class CashAdjApproveServiceImpl implements CashAdjApproveService {

	@Autowired
	private CashAdjApproveDAO cashAdjApproveDAO;
	
	@Override
	public void save(CashAdjApproveEntity adjApproveEntity) 
	{
		cashAdjApproveDAO.customsave(adjApproveEntity);
	}
}

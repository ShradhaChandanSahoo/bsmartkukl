package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.MiscellaneousPaymentDao;
import com.bcits.bsmartwater.model.MiscellaneousPayment;
import com.bcits.bsmartwater.service.MiscellaneousPaymentService;

@Service
public class MiscellaneousPaymentServiceImpl implements	MiscellaneousPaymentService {

	@Autowired
	MiscellaneousPaymentDao miscellaneousPaymentDao;
	
	@Override
	public void save(MiscellaneousPayment miscellaneousPayment) {

		miscellaneousPaymentDao.save(miscellaneousPayment);
	}

	@Override
	public List<MiscellaneousPayment> getSyncToPGRS(int status) {
		return miscellaneousPaymentDao.getSyncToPGRS(status);
	}

	@Override
	public void update(MiscellaneousPayment miscellaneousPayment) {
		miscellaneousPaymentDao.update(miscellaneousPayment);
		
	}

}

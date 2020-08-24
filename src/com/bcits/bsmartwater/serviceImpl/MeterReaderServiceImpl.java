package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.MeterReaderDao;
import com.bcits.bsmartwater.model.BillApproveEntity;
import com.bcits.bsmartwater.model.BillingLedgerEntity;
import com.bcits.bsmartwater.model.MeterReaderEntity;
import com.bcits.bsmartwater.model.UserTypeEntity;
import com.bcits.bsmartwater.service.MeterReaderService;

@Service
public class MeterReaderServiceImpl implements MeterReaderService
{
	@Autowired
	MeterReaderDao meterReaderDao;
	
	@Override
	public void save(MeterReaderEntity meterReaderEntity) 
	{
		meterReaderDao.customsave(meterReaderEntity);
	}
	
	@Override
	public List<MeterReaderEntity> readMrDetails() {
		return meterReaderDao.readMrDetails();
	}

	@Override
	public MeterReaderEntity getMeterReaderEntityById(int id,String mrcode) {
		
		return meterReaderDao.getMeterReaderEntityById(id,mrcode);
	}

	

	
	/*@Override
	public int meterReaderUpdate(int idVal,String mrcodeVal,String mrnameVal,String mraddressVal,String mrmobilenoVal) {
		//return meterReaderDao.meterReaderUpdate(idVal,mrcodeVal,mrnameVal,mraddressVal,mrmobilenoVal);
		return meterReaderDao.customupdate();
	}*/
	
	/*@Override
	public void delete(MeterReaderEntity meterReaderEntity) 
	{
		meterReaderDao.delete(meterReaderEntity);
	}*/
}

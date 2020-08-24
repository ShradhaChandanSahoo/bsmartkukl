package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.MeterReaderEntity;
import com.bcits.bsmartwater.model.UserTypeEntity;

public interface MeterReaderService 
{
	void save(MeterReaderEntity meterReaderEntity);

	List<MeterReaderEntity> readMrDetails();

     public MeterReaderEntity getMeterReaderEntityById(int id,String mrcode);

	//int meterReaderUpdate(int idVal,String mrcodeVal,String mrnameVal,String mraddressVal,String mrmobilenoVal);

	//void delete(MeterReaderEntity meterReaderEntity);
}

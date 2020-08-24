package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.MeterReaderEntity;

public interface MeterReaderDao extends GenericDAO<MeterReaderEntity> {

	MeterReaderEntity customsave(MeterReaderEntity meterReaderEntity);
	
	List<MeterReaderEntity> readMrDetails();

	//int meterReaderUpdate(int idVal,String mrcodeVal,String mrnameVal,String mraddressVal,String mrmobilenoVal);

	String uniqueMtrReader(String mrCode);

	int meterReaderDelete(int idVal);

	String getMRName(Integer integer);
    public MeterReaderEntity getMeterReaderEntityById(int id,String mrcode);

	
		
	

}

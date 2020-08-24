package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.MeterObservationDao;
import com.bcits.bsmartwater.model.MeterDetailsEntity;
import com.bcits.bsmartwater.model.MeterObservationEntity;
import com.bcits.bsmartwater.model.MeterReaderEntity;

@Repository
public class MeterObservationDaoImpl extends GenericDAOImpl<MeterObservationEntity> implements MeterObservationDao
{
	public String uniqueObsNo(String observationno) 
	{
		List<MeterObservationEntity> list=getCustomEntityManager().createNamedQuery("MeterObservationEntity.uniqueObsNo",MeterObservationEntity.class).setParameter("observationno",observationno).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}
	
	@Override
	public List<MeterObservationEntity> searchConnNo(String connectionno) {
		return getCustomEntityManager().createNamedQuery("MeterObservationEntity.searchConnNo",MeterObservationEntity.class).setParameter("connectionno",connectionno).getResultList();
	}
	
	public String uniqueObsSearchNoForEdit(String observationno,int id) 
	{
		List<MeterObservationEntity> list=getCustomEntityManager().createNamedQuery("MeterObservationEntity.uniqueObsSearchNoForEdit",MeterObservationEntity.class).setParameter("observationno",observationno).setParameter("id",id).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}
	
}

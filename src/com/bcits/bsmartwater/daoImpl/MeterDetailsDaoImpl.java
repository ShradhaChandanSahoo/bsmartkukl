package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.MeterDetailsDao;
import com.bcits.bsmartwater.dao.MeterReaderDao;
import com.bcits.bsmartwater.model.MeterDetailsEntity;
import com.bcits.bsmartwater.model.MeterReaderEntity;

@Repository
public class MeterDetailsDaoImpl extends GenericDAOImpl<MeterDetailsEntity> implements MeterDetailsDao 
{
	public String uniqueMeterDetails(String connectionno) 
	{
		List<MeterDetailsEntity> list=getCustomEntityManager().createNamedQuery("MeterDetailsEntity.uniqueMeterDetails",MeterDetailsEntity.class).setParameter("connectionno",connectionno).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}
	
	public String checkConnMeterDetails(String connectionno) 
	{
		List<MeterDetailsEntity> list=getCustomEntityManager().createNamedQuery("MeterDetailsEntity.uniqueMeterDetails",MeterDetailsEntity.class).setParameter("connectionno",connectionno).getResultList();
		if(list==null || list.size()==0){
			return "Does not Exist";
		}else{
			return "Exist";
		}
	}
	
	public List<MeterDetailsEntity> viewConnMeterDetails(String connectionno) 
	{
		return getCustomEntityManager().createNamedQuery("MeterDetailsEntity.viewConnMeterDetails",MeterDetailsEntity.class).setParameter("connectionno",connectionno).getResultList();
	}
	
	public String uniqueMeterDetailsForEdit(String connectionno,int id) 
	{
		List<MeterDetailsEntity> list=getCustomEntityManager().createNamedQuery("MeterDetailsEntity.uniqueMeterDetailsForEdit",MeterDetailsEntity.class).setParameter("connectionno",connectionno).setParameter("id",id).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}
	
	@Override
	public List<MeterDetailsEntity> readMeterDetails() {
		return getCustomEntityManager().createNamedQuery("MeterDetailsEntity.readMeterDetails",MeterDetailsEntity.class).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MeterDetailsEntity> getMeterDetailsDataByConnectionNum(String connId) {
		List<MeterDetailsEntity> meter = null;
		try {
			meter = getCustomEntityManager().createNamedQuery("MeterDetailsEntity.getMeterDetailsDataByConnectionNum").setParameter("connId", connId.toUpperCase()).getResultList();
			System.out.println("meter ===> "+meter.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return meter;
	}
	
	public List<MeterDetailsEntity> findByConnNo(String connectionno)
	{
		return getCustomEntityManager().createNamedQuery("MeterDetailsEntity.findByConnNo").setParameter("connectionno",connectionno).getResultList();
	}

	@Override
	public String uniqueMeterNoChk(String meter_no) {
		List<MeterDetailsEntity> list=getCustomEntityManager().createNamedQuery("MeterDetailsEntity.uniqueMeterNoChk",MeterDetailsEntity.class).setParameter("meter_no",meter_no.toUpperCase()).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}
}

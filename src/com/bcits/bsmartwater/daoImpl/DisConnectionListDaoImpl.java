package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.DisConnectionListDao;
import com.bcits.bsmartwater.model.ConsumerMaster;
import com.bcits.bsmartwater.model.DisConnectionListEntity;
import com.bcits.bsmartwater.model.MeterDetailsEntity;
@Repository
public class DisConnectionListDaoImpl extends GenericDAOImpl<DisConnectionListEntity> implements DisConnectionListDao
{
	@Override
	public List<DisConnectionListEntity> genDisList() 
	{
		try
		{
			return getCustomEntityManager().createNamedQuery("DisConnectionListEntity.genDisList",DisConnectionListEntity.class).getResultList();
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public String uniqueDisConnNo(String connectionno) 
	{
		List<DisConnectionListEntity> list=getCustomEntityManager().createNamedQuery("DisConnectionListEntity.uniqueDisConnNo",DisConnectionListEntity.class).setParameter("connectionno",connectionno).getResultList();
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}
	
	public List<DisConnectionListEntity> getListDetailsByConnNo(String connectionno) 
	{
		List<DisConnectionListEntity> list=getCustomEntityManager().createNamedQuery("DisConnectionListEntity.getListDetailsByConnNo",DisConnectionListEntity.class).setParameter("connectionno",connectionno).getResultList();
		return list;
	}
	
	public List<DisConnectionListEntity> uniqueDisConnEntry(String connectionno) 
	{
		List<DisConnectionListEntity> list=getCustomEntityManager().createNamedQuery("DisConnectionListEntity.uniqueDisConnEntry",DisConnectionListEntity.class).setParameter("connectionno",connectionno).getResultList();
		return list;
	}
	
	public List<DisConnectionListEntity> uniqueRecConnEntry(String connectionno) 
	{
		List<DisConnectionListEntity> list=getCustomEntityManager().createNamedQuery("DisConnectionListEntity.uniqueRecConnEntry",DisConnectionListEntity.class).setParameter("connectionno",connectionno).getResultList();
		return list;
	}
}

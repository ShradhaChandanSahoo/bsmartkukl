package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.MeterReaderDao;
import com.bcits.bsmartwater.model.MeterReaderEntity;

@Repository
public class MeterReaderDaoImpl extends GenericDAOImpl<MeterReaderEntity> implements MeterReaderDao
{
	@Override
	public List<MeterReaderEntity> readMrDetails() {
		return getCustomEntityManager().createNamedQuery("MeterReaderEntity.readMrDetails",MeterReaderEntity.class).getResultList();
	}
	
	@Override
	public String uniqueMtrReader(String mrCode) 
	{
		List<MeterReaderEntity> list=getCustomEntityManager().createNamedQuery("MeterReaderEntity.uniqueMtrReader",MeterReaderEntity.class).setParameter("mrCode",mrCode).getResultList();
		//System.out.println("====uniqueMtrReader====>"+mrCode+"===="+list.size());
		if(list==null || list.size()==0){
			return "CanAdd";
		}else{
			return "AlreadyExist";
		}
	}
	  
	@Override
	public int meterReaderDelete(int idVal) 
	{
		int val=0;
		try{
			//System.out.println("==idVal 2 DAAo serimpl==="+idVal+"==="+mrcodeVal+"==="+mrnameVal+"==="+mraddressVal+"==="+mrmobilenoVal);
			 val=getCustomEntityManager().createNamedQuery("MeterReaderEntity.deleteMrDetails").setParameter("idVal",idVal).executeUpdate();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return val;
	}

	@Override
	public String getMRName(Integer id) {
		try{
		return (String) getCustomEntityManager().createNamedQuery("MeterReaderEntity.getMRName").setParameter("id", id).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public MeterReaderEntity getMeterReaderEntityById(int id,String mrcode) 
	{
		try{
		return (MeterReaderEntity) getCustomEntityManager().createNamedQuery("MeterReaderEntity.getMeterReaderEntity").setParameter("idVal", id).setParameter("mrCode", mrcode).getSingleResult();
	      }catch(Exception e)
	      {
		e.printStackTrace();return null;
	      }
		
		
	}

	
}

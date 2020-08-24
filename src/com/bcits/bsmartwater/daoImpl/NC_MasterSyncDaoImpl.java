package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.NC_MasterSyncDao;
import com.bcits.bsmartwater.model.NC_MasterSync;

@Repository
public class NC_MasterSyncDaoImpl extends GenericDAOImpl<NC_MasterSync> implements NC_MasterSyncDao {

	@Override
	public List<NC_MasterSync> findAllByConNOSC(String connection_no,String sitecode) {
		return entityManager.createNamedQuery("NC_MasterSync.findAllByConNOSC",NC_MasterSync.class).setParameter("connection_no", connection_no).setParameter("sitecode", sitecode).getResultList();
	}
	
	
}

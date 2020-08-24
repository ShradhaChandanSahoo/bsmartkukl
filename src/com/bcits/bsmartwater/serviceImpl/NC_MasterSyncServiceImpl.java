package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.NC_MasterSyncDao;
import com.bcits.bsmartwater.model.NC_MasterSync;
import com.bcits.bsmartwater.service.NC_MasterSyncService;

@Service
public class NC_MasterSyncServiceImpl implements NC_MasterSyncService {

	@Autowired
	NC_MasterSyncDao nc_MasterSyncDao;
	
	@Override
	public List<NC_MasterSync> findAllByConNOSC(String connection_no,String branch) {
		return nc_MasterSyncDao.findAllByConNOSC(connection_no,branch);
	}

}

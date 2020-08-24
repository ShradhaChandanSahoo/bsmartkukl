package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.NC_MasterSync;

public interface NC_MasterSyncDao extends GenericDAO<NC_MasterSync>{

	List<NC_MasterSync> findAllByConNOSC(String connection_no, String branch);

}

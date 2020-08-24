package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.NC_MasterSync;

public interface NC_MasterSyncService {

	List<NC_MasterSync> findAllByConNOSC(String connection_no, String branch);

}

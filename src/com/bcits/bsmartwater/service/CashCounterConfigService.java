package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.CashMasterConfig;

public interface CashCounterConfigService {

	void save(CashMasterConfig cashMasterConfig);
	
	void update(CashMasterConfig cashMasterConfig);
	
	List<?> getcashMasterConfigData();

	void delete(CashMasterConfig cashMasterConfig);

	CashMasterConfig getConfigDataOnuserName(Integer counter_no, String user_login_name);

	List<?> getAllCounters(String location);

	Object[] findUserByCounterNo(Integer counterNo);

}

package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.CashMasterConfig;

public interface CashCounterConfigDAO extends GenericDAO<CashMasterConfig>{

	List<?> getcashMasterConfigData();

	CashMasterConfig getConfigDataOnuserName(Integer counter_no,String user_login_name);

	List<?> getAllCounters(String location);

	Object[] findUserByCounterNo(Integer counterNo);

}

package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.CashCounterConfigDAO;
import com.bcits.bsmartwater.model.CashMasterConfig;
import com.bcits.bsmartwater.service.CashCounterConfigService;

@Service
public class CashCounterConfigServiceImpl implements CashCounterConfigService {

	@Autowired
	private CashCounterConfigDAO cashCounterConfigDAO;
	
	@Override
	public void save(CashMasterConfig cashMasterConfig) {
		cashCounterConfigDAO.customsave(cashMasterConfig);
	}

	@Override
	public void update(CashMasterConfig cashMasterConfig) {
		cashCounterConfigDAO.customupdate(cashMasterConfig);
	}

	@Override
	public List<?> getcashMasterConfigData() {
		return cashCounterConfigDAO.getcashMasterConfigData();
	}

	@Override
	public void delete(CashMasterConfig cashMasterConfig) {
		cashCounterConfigDAO.customdelete(cashMasterConfig.getId());
	}

	@Override
	public CashMasterConfig getConfigDataOnuserName(Integer counter_no, String user_login_name) {
		return cashCounterConfigDAO.getConfigDataOnuserName(counter_no,user_login_name);
	}

	@Override
	public List<?> getAllCounters(String location) {
		List<?> list=cashCounterConfigDAO.getAllCounters(location);
		List<Map<String, Object>> result=new ArrayList<>();
		for(Iterator<?> iterator=list.iterator();iterator.hasNext();){
			Object[] obj=(Object[]) iterator.next();
			Map<String, Object> data=new HashMap<String, Object>();
			data.put("counterno", obj[0]);
			data.put("countername", (String)obj[1]);
			result.add(data);
		}
		return result;
	}

	@Override
	public Object[] findUserByCounterNo(Integer counterNo) {
		
		return cashCounterConfigDAO.findUserByCounterNo(counterNo);
	}

}

package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.BillCorrectionApproveDao;
import com.bcits.bsmartwater.dao.ConsumerMasterApprovalDao;
import com.bcits.bsmartwater.daoImpl.BillCorrectionApproveDaoImpl;
import com.bcits.bsmartwater.service.ConsumerMasterApprovalService;

@Service
public class ConsumerMasterApprovalServiceImpl implements ConsumerMasterApprovalService {

	@Autowired
	ConsumerMasterApprovalDao consumerMasterApprovalDao;
	@SuppressWarnings("serial")
	@Override
	public List<?> getPendingConsumersToApprove() {
		
		List<?> list =consumerMasterApprovalDao.getPendingConsumersToApprove();
		List<Map<String, Object>> consumer = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			consumer.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", (String)values[0]);
					put("name_eng", (String)values[1]);
					put("name_nep", (String)values[2]);
					put("con_category", (String)values[3]);
					put("con_type", (String)values[4]);
					put("id", values[5]);
					put("remarks", values[6]);
					
					
				}
			});
		}
		return consumer;
	
		
	}

	@Override
	public List<?> getConsumerApproveList() 
	{
		List<?> billAppList= consumerMasterApprovalDao.getConsumerApproveList();
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", (String)values[0]);
					put("name_eng", (String)values[1]);
					put("name_nep", (String)values[2]);
					put("con_category", (String)values[3]);
					put("con_type", (String)values[4]);
					put("approve_status", values[5]);
					put("remarks", values[6]);
					
				}
			});
		}
		return bills;
	}
	
}

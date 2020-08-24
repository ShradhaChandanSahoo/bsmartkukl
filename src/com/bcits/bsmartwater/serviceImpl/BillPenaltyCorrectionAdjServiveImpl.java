package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.BillPenaltyCorrectionAdjusmtmentDAO;
import com.bcits.bsmartwater.model.BillPenaltyAdjustment;
import com.bcits.bsmartwater.model.BillPenaltyCorrectionAjdutment;
import com.bcits.bsmartwater.service.BillPenaltyCorrectionAdjService;

@Service
public class BillPenaltyCorrectionAdjServiveImpl implements BillPenaltyCorrectionAdjService{

	@Autowired
	BillPenaltyCorrectionAdjusmtmentDAO billPenaltyCorrectionAdjDao;

	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status) {
		List<?> list= billPenaltyCorrectionAdjDao.getPendingConnectionsToApprove(approve_status);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", values[0]);
					put("name_eng", values[1]);
					put("area_no",values[2]);					
					put("pipe_size", values[3]);
					put("bill_adj_amount", values[4]);
					put("penalty_adj_amount", values[5]);
					put("from_mon_year", values[6]);
					put("to_mon_year", values[7]);
					put("submit_by", values[8]);
					put("submit_date", billPenaltyCorrectionAdjDao.getDate3((Date)values[9]));
					put("remarks", values[10]);
				}
			});
		}
		return bills;
	}

	@Override
	public BillPenaltyCorrectionAjdutment getConTypeByConNo(String connection_no, int approve_status) {
		return billPenaltyCorrectionAdjDao.getConTypeByConNo(connection_no,approve_status);
	}

	@Override
	public void update(BillPenaltyCorrectionAjdutment billPenaltyCorrection) {
		billPenaltyCorrectionAdjDao.customupdate(billPenaltyCorrection);
	}

	@Override
	public void save(BillPenaltyCorrectionAjdutment billPenaltyCorrection) {
		try{
		billPenaltyCorrectionAdjDao.customsave(billPenaltyCorrection);
		}catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	

	
	
}

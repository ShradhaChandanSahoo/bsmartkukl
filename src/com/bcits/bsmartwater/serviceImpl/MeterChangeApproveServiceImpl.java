package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.MeterChangeApproveDao;
import com.bcits.bsmartwater.model.BillApproveEntity;
import com.bcits.bsmartwater.model.MeterChangeApproveEntity;
import com.bcits.bsmartwater.service.MeterChangeApproveService;
@Service
public class MeterChangeApproveServiceImpl implements MeterChangeApproveService
{
	@Autowired
	MeterChangeApproveDao meterChangeApproveDao;
	
	
	@SuppressWarnings("serial")
	@Override
	public List<?> getMeterPendingApproval() {
		
		List<?> meterAppList= meterChangeApproveDao.getMeterPendingApproval();
		List<Map<String, Object>> meters = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = meterAppList.iterator(); i.hasNext();) {
			meters.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", (String)values[0]);
					put("new_meter_no", (String)values[1]);
					put("fr", (Integer)values[2]);
					put("ir", (Integer)values[3]);
					put("mcunits", (Double)values[4]);
					put("new_ins_date_nep", (String)values[5]);
					put("new_ins_date_eng", (Date)values[6]);
					put("id", (Integer)values[7]);
				}
			});
		}
		return meters;
		
	}
	
	@Override
	public List<?> findByConnectionNo(String connectionNo) {
		
		List<MeterChangeApproveEntity> list= meterChangeApproveDao.findByConnectionNo(connectionNo);
		List<Map<String, Object>> meteringDetailsMap =  new ArrayList<Map<String, Object>>(); 
		
		for (final MeterChangeApproveEntity record :list) 
		  {
			meteringDetailsMap.add(new HashMap<String, Object>() {	 
			{  
				put("connection_no", record.getConnectionno());
				put("new_meter_no", record.getNew_meter_no());
				put("fr", record.getFr());
				put("ir", record.getIr());
				put("new_ins_date_nep", record.getNew_ins_date_nep());
				put("new_ins_date_eng", meterChangeApproveDao.getDate3(record.getNew_ins_date_eng()));
				put("mcunits", record.getMcunits());
			    put("remarks", record.getRemarks());
			}
			});
		  }
		return meteringDetailsMap;
	}
	
	@Override
	public MeterChangeApproveEntity findById(int meterId) {
	
		return meterChangeApproveDao.customfind(meterId);
	}
	
	@Override
	public void update(MeterChangeApproveEntity meterChangeApproveEntity) {
		
		meterChangeApproveDao.customupdate(meterChangeApproveEntity);
		
	}
	
	@Override
	public List<MeterChangeApproveEntity> findByConnNo(String connNo) {
	
		return meterChangeApproveDao.findByConnNo(connNo);
	}
	
	@Override
	public List<?> getMeterApproveList() {
		List<?> billAppList= meterChangeApproveDao.getMeterApproveList();
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = billAppList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					//c.connection_no,b.new_meter_no,b.fr,b.ir,b.mcunits,b.new_ins_date_nep,b.new_ins_date_eng,b.mtr_change_app_status
					final Object[] values = (Object[])i.next();
					/*put("connection_no", (String)values[0]);
					put("new_meter_no", values[1]);
					put("fr",values[2]);
					put("ir", values[3]);
					put("mcunits", values[4]);
					put("new_ins_date_nep", values[5]);
					put("new_ins_date_eng", values[6]);
					put("approve_status", values[7]);
					
					*/
					put("connection_no", (String)values[0]);
					put("new_meter_no", (String)values[1]);
					put("fr", (Integer)values[2]);
					put("ir", (Integer)values[3]);
					put("mcunits", (Double)values[4]);
					put("new_ins_date_nep", (String)values[5]);
					put("new_ins_date_eng", (Date)values[6]);
					put("approve_status", (Integer)values[7]);
				}
			});
		}
		return bills;
	}

	@Override
	public MeterChangeApproveEntity getByConnectionNo(String conNo) {
		return meterChangeApproveDao.getByConnectionNo(conNo);
	}
	
}

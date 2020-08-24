package com.bcits.bsmartwater.serviceImpl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.ConTypeChangeDao;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.model.ConTypeChangeEntity;
import com.bcits.bsmartwater.service.ConTypeChangeService;

@Service
public class ConTypeChangeServiceImpl implements ConTypeChangeService {

	@Autowired
	private ConTypeChangeDao conTypeChangeDao;
	
	@Autowired
	MasterGenericDAO masterGenericDAO;
	
	@Override
	public void save(ConTypeChangeEntity conTypeChangeEntity) {
		conTypeChangeDao.customsave(conTypeChangeEntity);
	}

	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status) {
		return conTypeChangeDao.getPendingConnectionsToApprove(approve_status);
	}

	@Override
	public ConTypeChangeEntity getConTypeByConNo(String connection_no,
			int approve_status) {
	
		return conTypeChangeDao.getConTypeByConNo(connection_no,approve_status);
	}

	@Override
	public void update(ConTypeChangeEntity conTypeChangeEntity) {
		conTypeChangeDao.customupdate(conTypeChangeEntity);
		
	}
	
	@Override
	public List<?> getConnectionTypeApproveList() {
		return conTypeChangeDao.getConnectionTypeApproveList();
	}

	@Override
	public List<?> conTypeChngReportRead(String from, String to, int status) {
		List<?> list= conTypeChangeDao.conTypeChngReportRead(from,to,status);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("slNo", slNo++);
			data.put("con_no", (String) obj[0]);
			data.put("name", (String) obj[1]);
			data.put("area", obj[2]);
			data.put("pipe_size", obj[3]);
			data.put("new_pipe_size", obj[4]);
			data.put("con_type", obj[5]);
			data.put("new_con_type", obj[6]);
			data.put("con_status", obj[7]);
			data.put("new_con_status", obj[8]);
			data.put("remarks", obj[9]);
			data.put("req_by", obj[10]);
			data.put("req_date", obj[11]);
			data.put("approve_by", obj[12]);
			data.put("approve_date", obj[13]);
			int st=((BigDecimal)obj[14]).intValue();
			if(st==0){
				data.put("approve_status", "Pending");
			} else if(st==1){
				data.put("approve_status", "Approved");
			} else{
				data.put("approve_status", "Rejected");
			}
			data.put("new_area", obj[15]);
			result.add(data);
		}
		
		
		

		return result;
	}
}

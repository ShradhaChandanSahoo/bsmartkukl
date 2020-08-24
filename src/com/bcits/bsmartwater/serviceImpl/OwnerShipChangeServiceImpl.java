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

import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.dao.OwnerShipChangeDao;
import com.bcits.bsmartwater.model.OwnerShipChange;
import com.bcits.bsmartwater.service.OwnerShipChangeService;

@Service
public class OwnerShipChangeServiceImpl implements OwnerShipChangeService {

	@Autowired
	private OwnerShipChangeDao ownerShipChangeDao;
	
	@Autowired
	private MasterGenericDAO masterGenericDAO;

	@Override
	public void save(OwnerShipChange ownerShipChangeEntity) {
		ownerShipChangeDao.customsave(ownerShipChangeEntity);
	}

	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status) {
		return ownerShipChangeDao.getPendingConnectionsToApprove(approve_status);
	}

	@Override
	public OwnerShipChange getRecordByConnectionNo(String connection_no, int approve_status) {
		return ownerShipChangeDao.getRecordByConnectionNo(connection_no,approve_status);
	}

	@Override
	public void update(OwnerShipChange ownerShipChange) {
		ownerShipChangeDao.customupdate(ownerShipChange);
		
	}

	@Override
	public List<?> getOwnerShipChangeList() {
		return ownerShipChangeDao.getOwnerShipChangeList();
	}

	@Override
	public List<?> ownerShipChangeReportRead(String from, String to,int approve_status) {
		List<?> list= ownerShipChangeDao.ownerShipChangeReportRead(from, to, approve_status);
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		DecimalFormat df = new DecimalFormat("#.##");
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("slNo", slNo++);
			data.put("con_no", (String) obj[0]);
			data.put("old_name", (String) obj[1]);
			data.put("new_name", obj[2]);
			data.put("old_name_nep", obj[3]);
			data.put("new_name_nep", obj[4]);
			data.put("old_fname", obj[5]);
			data.put("new_fname", obj[6]);
			data.put("old_fname_nep", obj[7]);
			data.put("new_fname_nep", obj[8]);
			data.put("old_citizenship", obj[9]);
			data.put("new_citizenship", obj[10]);
			data.put("reason", obj[11]);
			data.put("req_by", obj[12]);
			data.put("approve_by", obj[14]);
			int st=((BigDecimal)obj[16]).intValue();
			if(st==0){
				data.put("approve_status", "Pending");
			} else if(st==1){
				data.put("approve_status", "Approved");
			} else{
				data.put("approve_status", "Rejected");
			}
			if (obj[13] == null) {
				data.put("req_date", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[13]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 3;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("req_date", nepalimonthyear);
			}
			
			if (obj[15] == null) {
				data.put("approve_date", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date) obj[15]);
				String english[] = date.split("-");
				int cday = Integer.parseInt(english[0]);
				if ("02".equalsIgnoreCase(english[1])) {
					cday = cday + 3;
				}
				if ("04".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("06".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				if ("09".equalsIgnoreCase(english[1])) {
					cday = cday + 1;
				}
				int cmonth = Integer.parseInt(english[1]);
				int cyear = Integer.parseInt(english[2]);
				String nepalimonthyear = masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
				data.put("approve_date", nepalimonthyear);
			}
			
			result.add(data);
		}

		return result;
	}
	
	
}

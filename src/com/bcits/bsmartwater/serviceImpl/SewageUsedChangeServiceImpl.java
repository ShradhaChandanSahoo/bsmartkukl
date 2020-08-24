package com.bcits.bsmartwater.serviceImpl;

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
import com.bcits.bsmartwater.dao.SewageUsedChangeDAO;
import com.bcits.bsmartwater.model.SewageChangeEntity;
import com.bcits.bsmartwater.service.SewageUsedChangeService;

@Service
public class SewageUsedChangeServiceImpl implements SewageUsedChangeService {

	@Autowired
	private MasterGenericDAO masterGenericDAO;
	
	@Autowired
	 SewageUsedChangeDAO sewageUsedChangeDAO;
	
	@Override
	public void save(SewageChangeEntity sewageChangeEntity) 
	{
		sewageUsedChangeDAO.customsave(sewageChangeEntity);

	}

	@Override
	public List<SewageChangeEntity> getAllSewChanEntityData() {
		
		return sewageUsedChangeDAO.getAllSewChanEntityData();
	}

	@Override
	public SewageChangeEntity findSewageChangeEntityByAppId(int id) {
		return (SewageChangeEntity) sewageUsedChangeDAO.findSewageChangeEntityByAppId(id);
		
	}

	@Override
	public void update(SewageChangeEntity sewageChangeEntity) {
		sewageUsedChangeDAO.customupdate(sewageChangeEntity);
		
	}

	@Override
	public List<SewageChangeEntity> getSewageApproveList() {
		
		return sewageUsedChangeDAO.getSewageApproveList();
	}

	@Override
	public List<?> getSewageApprovalReprots(String frmDate, String toDate, String sitecode, int catagory) {
		
	List<?> list= sewageUsedChangeDAO.getSewageApprovalReprots(frmDate, toDate, sitecode, catagory);
		
		List<Map<String, Object>> result = new ArrayList<>();
		int slNo = 1;
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {

			Object[] obj = (Object[]) iterator.next();
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("slNo", slNo++);
			data.put("connNo", obj[0]);
			data.put("monthyr", obj[1]);
			data.put("scOld", obj[2]);
			data.put("scNew", obj[3]);
			data.put("approvedBy", obj[4]);
			//data.put("approvedDate",new SimpleDateFormat("yyyy-MM-dd").format(obj[4]));
			if (obj[5] == null) {
				data.put("approvedDate", "");
			} else {
				String date = new SimpleDateFormat("dd-MM-yyyy").format((Date)obj[5]);
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
				data.put("approvedDate", nepalimonthyear);
			}
			data.put("remarks", obj[6]);
			
			result.add(data);
			
		}
		return result;
	}
	}
	

	



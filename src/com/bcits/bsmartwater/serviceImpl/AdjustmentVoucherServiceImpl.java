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

import com.bcits.bsmartwater.dao.AdjustmentVoucherDao;
import com.bcits.bsmartwater.dao.MasterGenericDAO;
import com.bcits.bsmartwater.model.AdjustmentVoucher;
import com.bcits.bsmartwater.service.AdjustmentVoucherService;

@Service
public class AdjustmentVoucherServiceImpl implements AdjustmentVoucherService {

	@Autowired
	AdjustmentVoucherDao adjustmentVoucherDao;
	
	@Autowired
	MasterGenericDAO masterGenericDAO;

	@Override
	public void save(AdjustmentVoucher adjustmentVoucherEntity) {
		
		adjustmentVoucherDao.customsave(adjustmentVoucherEntity);
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> getAdjustmentList(int status) {
		

		List<Map<String, Object>> adjustments = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = adjustmentVoucherDao.getAdjustmentList(status).iterator(); i.hasNext();) {
			
			adjustments.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					
					put("connection_no", values[0]);
					put("name_eng", values[1]);
					put("area_no", values[2]==null?"":(String)values[2]);
					put("pipe_size", values[3]==null?0:(Double)values[3]);
					put("con_type", values[4]);
					put("adjustment_no", values[5]);
					put("adjustment_amount",values[6]);
					String nepalimonthyear="";
					if(values[7]!=null){
					String adjdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)values[7]);
					String english[]=adjdate.split("-");
					int cday=Integer.parseInt(english[0]);
					int cmonth=Integer.parseInt(english[1]);
					if("02".equalsIgnoreCase(english[1])){
						cday=cday+3;
					}
					if("04".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					if("06".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					if("09".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					if("11".equalsIgnoreCase(english[1])){
						cday=cday+1;
					}
					int cyear=Integer.parseInt(english[2]);
					
						nepalimonthyear=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
					
					}else{
						
					}
					put("adjustment_date", nepalimonthyear);
					put("remarks",values[8]);
					put("prepared_by",values[9]);

					
				}
			});
		}
		return adjustments;
	}

	
	@Override
	public long getMaxAdjustmentNo(String adjustmentNo) {
		
		return adjustmentVoucherDao.getMaxAdjustmentNo(adjustmentNo);
	}
	
	
	

}

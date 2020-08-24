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
import com.bcits.bsmartwater.dao.WrongPostingDao;
import com.bcits.bsmartwater.model.WrongPosting;
import com.bcits.bsmartwater.service.WrongPostingService;

@Service
public class WrongPostingServiceImpl implements WrongPostingService {

	@Autowired
	private WrongPostingDao wrongPostingDao;
	
	@Autowired
	MasterGenericDAO masterGenericDAO;
	
	@Override
	public void save(WrongPosting wrongPost) {
		wrongPostingDao.customsave(wrongPost);
		
	}

	@Override
	public List<?> getWrongPostApproveList(int status) {
		List<?> list=wrongPostingDao.getWrongPostApproveList(status);
		List<?> adj=getWPList(list);
		return adj;
	
	}

	@SuppressWarnings("serial")
	private List<?> getWPList( List<?> list) {
		
		List<Map<String, Object>> adjustments = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			
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
					String nepalimonthyear1="";
					String nepalimonthyear2="";
					String nepalimonthyear3="";
					
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
					
					put("to_connection_no",values[10]);
					put("receiptNo",values[11]);
					
					if(values[12]!=null){
						
						String adjdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)values[12]);
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
						
							nepalimonthyear1=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
						
					}else{
							
					}
					
					
					if(values[13]!=null){
						
						String adjdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)values[13]);
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
						
							nepalimonthyear2=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
						
						}else{
							
					}
					
					
					put("engRdate",nepalimonthyear1);
					put("added_date",nepalimonthyear2);
					
					put("appby",values[14]);
					
					if(values[15]!=null){
						
						String adjdate=new SimpleDateFormat("dd-MM-yyyy").format((Date)values[15]);
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
						
							nepalimonthyear3=masterGenericDAO.getNepaliDateFromEnglishDate(cyear, cmonth, cday);
						
						}else{
							
					}

					put("appdate",nepalimonthyear3);
					put("status",values[16]);
					put("monthyearnep", values[17]);
					

					
				}
			});
		}
		return adjustments;
	}

	@Override
	public List<?> getWrongPostApproveList() {
		List<?> list= wrongPostingDao.getWrongPostApproveList();
		List<?> adj=getWPList(list);
		return adj;
	}

	@Override
	public WrongPosting getRecordByConNo(String conno, int approve_status) {
		return wrongPostingDao.getRecordByConNo(conno,approve_status);
	}

	@Override
	public void update(WrongPosting wrongPosting) {
		
		wrongPostingDao.customupdate(wrongPosting);
		
	}

}

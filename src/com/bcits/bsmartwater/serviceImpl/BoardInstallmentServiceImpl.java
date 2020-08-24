package com.bcits.bsmartwater.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.BoardInstallmentDao;
import com.bcits.bsmartwater.model.BoardInstallment;
import com.bcits.bsmartwater.service.BoardInstallmentService;

@Service
public class BoardInstallmentServiceImpl implements BoardInstallmentService {

	@Autowired
	BoardInstallmentDao boardInstallmentDao;
	
	@Override
	public void save(BoardInstallment boardInstallment) {
		boardInstallmentDao.customsave(boardInstallment);
		
	}

	@SuppressWarnings("serial")
	@Override
	public List<?> viewBoardLedgertHistory(String connectionNo) {
		
		List<?> ledgerList =boardInstallmentDao.viewBoardLedgertHistory(connectionNo);
		List<Map<String, Object>> bills = new ArrayList<Map<String, Object>>();
		for (final Iterator<?> i = ledgerList.iterator(); i.hasNext();) {
			bills.add(new HashMap<String, Object>() {
				{
					final Object[] values = (Object[])i.next();
					put("connection_no", (String)values[0]);
					put("install_amount", (Double)values[1]);
					put("penalty", values[2]==null?0:(Double)values[2]);
					put("submit_by", values[3]==null?0:(String)values[3]);
					put("submit_date", values[4]==null?0:boardInstallmentDao.getDate3((Date)values[4]));
					put("rem_balance", values[5]==null?0:(Double)values[5]);
					put("name_eng", (String)values[6]);
					put("area_no", (String)values[7]);
					put("customer_id", (Long)values[8]);
					put("install_due_date", (String)values[9]);
					
				}
			});
		}
		return bills;
	
	}

	@Override
	public Object[] getBoardInstallment(
			String connectionNo, String paymentReceiptNo) {
		return boardInstallmentDao.getBoardInstallment(connectionNo,paymentReceiptNo);
	}

	

}

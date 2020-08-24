package com.bcits.bsmartwater.daoImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.WrongPostingDao;
import com.bcits.bsmartwater.model.WrongPosting;

@Repository
public class WrongPostingDaoImpl extends GenericDAOImpl<WrongPosting> implements WrongPostingDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getWrongPostApproveList(int status) {
		
		return getCustomEntityManager().createNamedQuery("WrongPosting.getWrongPostApproveList").setParameter("status", status).getResultList();

	}

	@Override
	public List<?> getWrongPostApproveList() {
		
		return getCustomEntityManager().createNamedQuery("WrongPosting.getWrongPostApproveListAll").getResultList();

	}

	@Override
	public WrongPosting getRecordByConNo(String conno, int status) {
		try{
			return getCustomEntityManager().createNamedQuery("WrongPosting.getRecordByConNo",WrongPosting.class).setParameter("connection_no", conno).setParameter("status", status).getSingleResult();

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}

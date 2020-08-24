package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.BoardInstallmentDao;
import com.bcits.bsmartwater.model.BoardInstallment;

@Repository
public class BoardInstallmentDaoImpl extends GenericDAOImpl<BoardInstallment> implements BoardInstallmentDao {

	@Override
	public List<?> viewBoardLedgertHistory(String connectionNo) {
		
		return getCustomEntityManager().createNamedQuery("BoardInstallment.viewBoardLedgertHistory").setParameter("connection_no", connectionNo.trim().toUpperCase()).getResultList();

	}

	@Override
	public Object[] getBoardInstallment(String connectionNo,
			String receipt_no) {
		try{
		return (Object[]) getCustomEntityManager().createNamedQuery("BoardInstallment.getBoardInstallment").setParameter("connection_no", connectionNo.trim().toUpperCase()).setParameter("receipt_no", receipt_no).getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}

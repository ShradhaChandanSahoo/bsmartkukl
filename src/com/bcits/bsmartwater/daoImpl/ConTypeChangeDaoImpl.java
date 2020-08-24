package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.ConTypeChangeDao;
import com.bcits.bsmartwater.model.ConTypeChangeEntity;

@Repository
public class ConTypeChangeDaoImpl extends GenericDAOImpl<ConTypeChangeEntity> implements ConTypeChangeDao {

	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status) {
		return 	getCustomEntityManager().createNamedQuery("ConTypeChangeEntity.getPendingConnectionsToApprove").setParameter("approve_status", approve_status).getResultList();

	}

	@Override
	public ConTypeChangeEntity getConTypeByConNo(String connection_no,
			int approve_status) {
		try{
		return 	getCustomEntityManager().createNamedQuery("ConTypeChangeEntity.getConTypeByConNo",ConTypeChangeEntity.class).setParameter("approve_status", approve_status).setParameter("connection_no", connection_no.trim().toUpperCase()).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public List<?> getConnectionTypeApproveList() 
	{
		return 	getCustomEntityManager().createNamedQuery("ConTypeChangeEntity.getConnectionTypeApproveList").getResultList();
	}

	@Override
	public List<?> conTypeChngReportRead(String from, String to, int status) {
		
		 /*String sql="select CONNECTION_NO,NAME_ENG, AREA_NO,PIPE_SIZE, NEW_PIPE_SIZE, EXIST_CON_TYPE, NEW_CON_TYPE, EXIST_CON_STATUS, "
		 		+ "NEW_CON_STATUS,REMARKS,SUBMIT_BY, SUBMIT_DATE_NEP, APPROVED_BY, APPROVED_DATE_NEP, APPROVE_STATUS "
		 		+ "from CON_TYPE_CHANGE WHERE trunc(SUBMIT_DATE_ENG) BETWEEN TO_DATE('"+from+"', 'dd-MM-yyyy') "
		 		+ "AND TO_DATE('"+to+"', 'dd-MM-yyyy') AND APPROVE_STATUS="+status+"";*/
		
		String sql="select C.CONNECTION_NO,C.NAME_ENG, C.AREA_NO,C.PIPE_SIZE, C.NEW_PIPE_SIZE, C.EXIST_CON_TYPE, C.NEW_CON_TYPE, C.EXIST_CON_STATUS, "
		 		+ "C.NEW_CON_STATUS,C.REMARKS,C.SUBMIT_BY, C.SUBMIT_DATE_NEP, C.APPROVED_BY, C.APPROVED_DATE_NEP, C.APPROVE_STATUS,M.AREA_NO AS new_area1 "
		 		+ "from CON_TYPE_CHANGE C,BSW_MASTER M WHERE trunc(SUBMIT_DATE_ENG) BETWEEN TO_DATE('"+from+"', 'dd-MM-yyyy') "
		 		+ "AND TO_DATE('"+to+"', 'dd-MM-yyyy') AND APPROVE_STATUS="+status+""+" AND C.CONNECTION_NO=M.CONNECTION_NO";
			 //System.out.println("<=====Inside wardWiseMrReaderReportRead=== sql ====="+sql);
		 
		
			 
			return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
}

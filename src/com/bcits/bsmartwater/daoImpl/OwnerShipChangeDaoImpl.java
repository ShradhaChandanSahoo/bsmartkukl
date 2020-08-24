package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.OwnerShipChangeDao;
import com.bcits.bsmartwater.model.OwnerShipChange;

@Repository
public class OwnerShipChangeDaoImpl extends GenericDAOImpl<OwnerShipChange> implements OwnerShipChangeDao {

	@Override
	public List<?> getPendingConnectionsToApprove(int approve_status) {
		return 	getCustomEntityManager().createNamedQuery("OwnerShipChange.getPendingConnectionsToApprove").setParameter("approve_status", approve_status).getResultList();

	}

	@Override
	public OwnerShipChange getRecordByConnectionNo(String connection_no, int approve_status) {
		try{
			return 	getCustomEntityManager().createNamedQuery("OwnerShipChange.getRecordByConnectionNo",OwnerShipChange.class).setParameter("approve_status", approve_status).setParameter("connection_no", connection_no.trim().toUpperCase()).getSingleResult();

		}catch(Exception e){
			System.err.println(e.getMessage());
			return null;
		}
	}

	@Override
	public List<?> getOwnerShipChangeList() {
		return 	getCustomEntityManager().createNamedQuery("OwnerShipChange.getOwnerShipChangeList").getResultList();
	}

	@Override
	public List<?> ownerShipChangeReportRead(String from, String to,int approve_status) {
		//System.out.println("<=====Inside wardWiseMrReaderReportRead=== yearnep ====="+yearnep+"=== month ==="+month);
		 
		 String sql="SELECT CONNECTION_NO, OLD_NAME_ENG, NEW_NAME_ENG, OLD_NAME_NEP, NEW_NAME_NEP, OLD_FNAME_ENG, NEW_FNAME_ENG, "
		 		+ "OLD_FNAME_NEP, NEW_FNAME_NEP, OLD_CITIZENSHIPNO, NEW_CITIZENSHIPNO, REASON, REQUEST_BY, REQUEST_DATE, "
		 		+ "APPROVE_BY, APPROVE_DATE,APPROVE_STATUS FROM BSW_OWNERSHIP_CHANGE WHERE trunc(REQUEST_DATE) BETWEEN "
		 		+ "TO_DATE('"+from+"', 'dd-MM-yyyy') AND TO_DATE('"+to+"', 'dd-MM-yyyy') AND APPROVE_STATUS="+approve_status+"";
		 //System.out.println("<=====Inside wardWiseMrReaderReportRead=== sql ====="+sql);
		 
		return getCustomEntityManager().createNativeQuery(sql).getResultList();
	}
	
}

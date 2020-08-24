package com.bcits.bsmartwater.daoImpl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.SewageUsedChangeDAO;
import com.bcits.bsmartwater.model.SewageChangeEntity;

@Repository
public class SewageUsedChangeDAOImpl extends GenericDAOImpl<SewageChangeEntity> implements SewageUsedChangeDAO<SewageChangeEntity> 
{

	@Override
	public List<SewageChangeEntity> getAllSewChanEntityData() {
		
		return getCustomEntityManager().createNamedQuery("SewageChangeEntity.getAllRecords").getResultList();
	}

	@Override
	public SewageChangeEntity findSewageChangeEntityByAppId(int id) {
		
		return (SewageChangeEntity) getCustomEntityManager().createNamedQuery("SewageChangeEntity.findCustomerById").setParameter("id", id).getSingleResult();
	}

	@Override
	public List<SewageChangeEntity> getSewageApproveList() {
		
		return getCustomEntityManager().createNamedQuery("SewageChangeEntity.getSewageApprovalList").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> getSewageApprovalReprots(String frmDate, String toDate, String sitecode, int catagory) {
		
		
		if(catagory==1){
			
			String sql1="SELECT CONNECTION_NO,MONTHYR,SEWAGE_USED_OLD,SEWAGE_USED_NEW,APPROVED_BY,APPROVED_DATE,REMARKS FROM SEWAGE_USED WHERE TRUNC(APPROVED_DATE) >=TO_DATE('"+frmDate+"', 'dd/MM/yyyy') AND TRUNC(APPROVED_DATE) <= TO_DATE('"+toDate+"', 'dd/MM/yyyy') AND STATUS=1 AND SEWAGE_USED_NEW='Yes' ORDER BY APPROVED_DATE DESC";
			return getCustomEntityManager().createNativeQuery(sql1).getResultList();
		  
			
			}
		else if(catagory==2) {
			String sql1="SELECT CONNECTION_NO,MONTHYR,SEWAGE_USED_OLD,SEWAGE_USED_NEW,APPROVED_BY,APPROVED_DATE,REMARKS FROM SEWAGE_USED WHERE TRUNC(APPROVED_DATE) >=TO_DATE('"+frmDate+"', 'dd/MM/yyyy') AND TRUNC(APPROVED_DATE) <= TO_DATE('"+toDate+"', 'dd/MM/yyyy') AND STATUS=1 AND SEWAGE_USED_NEW='No' ORDER BY APPROVED_DATE DESC";
			return getCustomEntityManager().createNativeQuery(sql1).getResultList();
		}
			else{
				String sql1="SELECT CONNECTION_NO,MONTHYR,SEWAGE_USED_OLD,SEWAGE_USED_NEW,APPROVED_BY,APPROVED_DATE,REMARKS FROM SEWAGE_USED WHERE TRUNC(APPROVED_DATE) >=TO_DATE('"+frmDate+"', 'dd/MM/yyyy') AND TRUNC(APPROVED_DATE) <= TO_DATE('"+toDate+"', 'dd/MM/yyyy') AND STATUS=1 ORDER BY APPROVED_DATE DESC";
				return getCustomEntityManager().createNativeQuery(sql1).getResultList();
			   }
	}
	

}

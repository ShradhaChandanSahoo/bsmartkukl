package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.CashCounterConfigDAO;
import com.bcits.bsmartwater.model.CashMasterConfig;

@Repository
public class CashCounterConfigDAOImpl extends GenericDAOImpl<CashMasterConfig> implements CashCounterConfigDAO {

	@Override
	public List<CashMasterConfig> getcashMasterConfigData() {
		return  getCustomEntityManager().createNamedQuery("CashMasterConfig.getcashMasterConfigData",CashMasterConfig.class).getResultList();
	}

	@Override
	public CashMasterConfig getConfigDataOnuserName(Integer counter_no, String user_login_name) {
		try{
		return getCustomEntityManager().createNamedQuery("CashMasterConfig.getConfigDataOnuserName",CashMasterConfig.class).setParameter("counterNo",counter_no).setParameter("ulname", user_login_name).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<?> getAllCounters(String location) {
		return entityManager.createNativeQuery("SELECT B.COUNTER_NO,B.COUNTER_NAME FROM "+location+".BSW_CASH_MASTER_CONFIG B ORDER BY COUNTER_NO ASC").getResultList();
	}

	@Override
	public Object[] findUserByCounterNo(Integer counterNo) {
		try{
			return (Object[]) getCustomEntityManager().createNamedQuery("CashMasterConfig.findUserByCounterNo").setParameter("counterNo",counterNo).getSingleResult();
			}catch(Exception e){
			    e.printStackTrace();
				return null;
		}
	}

}

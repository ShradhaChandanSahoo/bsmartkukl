package com.bcits.bsmartwater.daoImpl;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.Penalty_RebateDao;
import com.bcits.bsmartwater.model.Penalty_Rebate;

@Repository
public class Penalty_RebateDaoImpl extends GenericDAOImpl<Penalty_Rebate> implements Penalty_RebateDao {

	@Override
	public Object[] getPenaltyRebateByDays(int days) {

		return (Object[]) getCustomEntityManager().createNamedQuery("Penalty_Rebate.getPenaltyRebateByDays").setParameter("days", days).getSingleResult();
	}}

package com.bcits.bsmartwater.dao;

import com.bcits.bsmartwater.model.Penalty_Rebate;

public interface Penalty_RebateDao extends GenericDAO<Penalty_Rebate>{

	Object[] getPenaltyRebateByDays(int days);

}

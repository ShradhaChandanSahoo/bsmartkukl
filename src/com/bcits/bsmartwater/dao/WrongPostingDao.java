package com.bcits.bsmartwater.dao;

import java.util.List;
import java.util.Map;

import com.bcits.bsmartwater.model.WrongPosting;

public interface WrongPostingDao extends GenericDAO<WrongPosting>{

	List<Map<String, Object>> getWrongPostApproveList(int status);

	List<?> getWrongPostApproveList();

	WrongPosting getRecordByConNo(String conno, int approve_status);

}

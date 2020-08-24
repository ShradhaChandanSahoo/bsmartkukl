package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.WrongPosting;

public interface WrongPostingService {

	void save(WrongPosting wrongPost);

	List<?> getWrongPostApproveList(int status);

	List<?> getWrongPostApproveList();

	WrongPosting getRecordByConNo(String string, int approve_status);

	void update(WrongPosting wrongPosting);

}

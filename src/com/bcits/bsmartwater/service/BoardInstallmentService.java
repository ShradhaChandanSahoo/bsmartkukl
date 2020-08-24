package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.BoardInstallment;

public interface BoardInstallmentService {

	
	void save(BoardInstallment boardInstallment);

	List<?> viewBoardLedgertHistory(String connectionNo);

	Object[] getBoardInstallment(String connectionNo,String paymentReceiptNo);


}

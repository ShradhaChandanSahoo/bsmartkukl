package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.BoardInstallment;

public interface BoardInstallmentDao extends GenericDAO<BoardInstallment>{

	List<?> viewBoardLedgertHistory(String connectionNo);

	Object[] getBoardInstallment(
			String connectionNo, String paymentReceiptNo);

}

package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.BankDetailsEntity;

public interface BankDetailsDAO extends GenericDAO<BankDetailsEntity>{

	List<BankDetailsEntity> getAllRecord();

	List<BankDetailsEntity> getBankDataForEditing(String id);

	Long checkForBankNameAvailability(String bankName);

	int deleteById(int id);

	List<BankDetailsEntity> getBankDetailsEntity();

}

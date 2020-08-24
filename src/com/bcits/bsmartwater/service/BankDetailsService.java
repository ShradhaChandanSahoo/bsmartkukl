package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.BankDetailsEntity;

public interface BankDetailsService {

	void save(BankDetailsEntity bankDetailsEntity);

	List<BankDetailsEntity> getAllRecords();

	List<BankDetailsEntity> getBankDataForEditing(String id);

	BankDetailsEntity find(int id);

	void update(BankDetailsEntity bankDetailsEntity);

	Long checkForBankNameAvailability(String bankName);

	void delete(int id);

	List<BankDetailsEntity> getBankDetailsEntity();

	

}

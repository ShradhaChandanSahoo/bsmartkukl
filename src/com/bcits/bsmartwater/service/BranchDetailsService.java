package com.bcits.bsmartwater.service;

import java.util.List;

import com.bcits.bsmartwater.model.BranchDetailsEntity;

public interface BranchDetailsService  {

	void delete(int id);

	List<BranchDetailsEntity> getAllBranchRecords();

	Long checkForBranchNameAvailability(String branchName);

	void save(BranchDetailsEntity branchDetailsEntity);

	List<BranchDetailsEntity> getBranchDataForEditing(String id);

	void update(BranchDetailsEntity branchDetailsEntity);

	int checkForBranchCodeAvailability(String string);

	Long checkBranchNameForUpdate(int id, String branchName);

	Long checkBranchCodeForUpdate(int id, String branchCode);

}

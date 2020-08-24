package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.BranchDetailsEntity;

public interface BranchDetailsDAO extends GenericDAO<BranchDetailsEntity>{

	List<BranchDetailsEntity> getAllBranchRecords();

	Long checkForBankNameAvailability(String branchName);

	List<BranchDetailsEntity> getBranchDataForEditing(String id);

	int checkForBranchCodeAvailability(String branchCode);

	Long checkBranchNameForUpdate(int id, String branchName);

	Long checkBranchCodeForUpdate(int id, String branchCode);

}

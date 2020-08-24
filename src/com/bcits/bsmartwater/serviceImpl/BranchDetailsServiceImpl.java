package com.bcits.bsmartwater.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.BranchDetailsDAO;
import com.bcits.bsmartwater.model.BranchDetailsEntity;
import com.bcits.bsmartwater.service.BranchDetailsService;

@Service
public class BranchDetailsServiceImpl implements BranchDetailsService{
	
	@Autowired
	private BranchDetailsDAO branchDetailsDAO;

	@Override
	public void delete(int id)
	{
		try {
			System.out.println(" id ===> "+id);
			branchDetailsDAO.customdelete(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<BranchDetailsEntity> getAllBranchRecords() {
	
		List<BranchDetailsEntity> branch = branchDetailsDAO.getAllBranchRecords();
		return branch;
	}

	@Override
	public Long checkForBranchNameAvailability(String branchName) {

		Long l = branchDetailsDAO.checkForBankNameAvailability(branchName);
		return l;
	}

	@Override
	public void save(BranchDetailsEntity branchDetailsEntity)
	{
		branchDetailsDAO.customsave(branchDetailsEntity);
	}

	@Override
	public List<BranchDetailsEntity> getBranchDataForEditing(String id) {

		List<BranchDetailsEntity> editBranchDetails=branchDetailsDAO.getBranchDataForEditing(id);
		return editBranchDetails;
	}

	@Override
	public void update(BranchDetailsEntity branchDetailsEntity) 
	{
		branchDetailsDAO.customupdate(branchDetailsEntity);
	}

	@Override
	public int checkForBranchCodeAvailability(String branchCode) {
		int l = branchDetailsDAO.checkForBranchCodeAvailability(branchCode);
		return l;
	}

	@Override
	public Long checkBranchNameForUpdate(int id, String branchName) {

		Long l = branchDetailsDAO.checkBranchNameForUpdate(id,branchName);
		return l;
	}

	@Override
	public Long checkBranchCodeForUpdate(int id, String branchCode) {
		Long l=branchDetailsDAO.checkBranchCodeForUpdate(id,branchCode);
		return l;
	}


}

package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.BranchDetailsDAO;
import com.bcits.bsmartwater.model.BranchDetailsEntity;

@Repository
public class BranchDetailsDAOImpl extends GenericDAOImpl<BranchDetailsEntity>
implements BranchDetailsDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<BranchDetailsEntity> getAllBranchRecords() {

		List<BranchDetailsEntity> branch = null;
		try {

			branch = getCustomEntityManager().createNamedQuery("BranchDetailsEntity.getAllBranchRecords").getResultList();
		} catch (Exception e) {
		}
		return branch;
	}

	@Override
	public Long checkForBankNameAvailability(String branchName) {
		Long i =null;
		try {
			i = Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("BranchDetailsEntity.checkForBranchNameAvailability").setParameter("branchName", branchName).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BranchDetailsEntity> getBranchDataForEditing(String id) {
		
		List<BranchDetailsEntity> editBranchDetails=null;
		try {
			editBranchDetails = getCustomEntityManager().createNamedQuery("BranchDetailsEntity.getBranchDataForEditing").setParameter("id",Integer.parseInt(id)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return editBranchDetails;
	}

	@Override
	public int checkForBranchCodeAvailability(String branchCode) {
		long i =0;
		try {
			i = (Long) getCustomEntityManager().createNamedQuery("BranchDetailsEntity.checkForBranchCodeAvailability").setParameter("branchCode", branchCode).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) i;
	}

	@Override
	public Long checkBranchNameForUpdate(int id, String branchName) {
		Long i =null;
		try {
			i = Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("BranchDetailsEntity.checkBranchNameForUpdate").setParameter("id", id).setParameter("branchName", branchName).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public Long checkBranchCodeForUpdate(int id, String branchCode) {
		Long i=null;
		try {
			i=Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("BranchDetailsEntity.checkBranchCodeForUpdate").setParameter("id", id).setParameter("branchCode", branchCode).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}
	
	
}

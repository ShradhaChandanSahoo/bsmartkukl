package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.BankDetailsDAO;
import com.bcits.bsmartwater.model.BankDetailsEntity;

@Repository
public class BankDetailsDAOImpl extends GenericDAOImpl<BankDetailsEntity> implements BankDetailsDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<BankDetailsEntity> getAllRecord(){
		List<BankDetailsEntity> bank = null;
		try {
			bank = getCustomEntityManager().createNamedQuery("BankDetailsEntity.getAllRecords").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bank;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankDetailsEntity> getBankDataForEditing(String id) {
		List<BankDetailsEntity> edit = null;
		try {
			edit = getCustomEntityManager().createNamedQuery("BankDetailsEntity.getBankDataForEditing").setParameter("id",Integer.parseInt(id)).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return edit;
	}

	@Override
	public Long checkForBankNameAvailability(String bankName) {
		Long i = null ;
		try {
			i = Long.parseLong(String.valueOf(getCustomEntityManager().createNamedQuery("BankDetailsEntity.checkForBankNameAvailability").setParameter("bankName", bankName).getSingleResult()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int deleteById(int id) {
		int i = 0;
		try {
			i = (int) getCustomEntityManager().createNamedQuery("BankDetailsEntity.deleteById").setParameter("id",id).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	
	@Override
	public List<BankDetailsEntity> getBankDetailsEntity() {
		
		return getCustomEntityManager().createNamedQuery("BankDetailsEntity.getBankDetailsEntity",BankDetailsEntity.class).getResultList();
	}
}

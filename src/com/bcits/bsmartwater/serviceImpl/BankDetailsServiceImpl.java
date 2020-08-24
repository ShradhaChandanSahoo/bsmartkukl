package com.bcits.bsmartwater.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.BankDetailsDAO;
import com.bcits.bsmartwater.model.BankDetailsEntity;
import com.bcits.bsmartwater.service.BankDetailsService;

@Service
public class BankDetailsServiceImpl implements BankDetailsService{

	@Autowired
	private BankDetailsDAO bankDetailsDAO;
	
	@Override
	public void save(BankDetailsEntity bankDetailsEntity) {
		bankDetailsDAO.customsave(bankDetailsEntity);
	}

	@Override
	public List<BankDetailsEntity> getAllRecords() {
		List<BankDetailsEntity> bank = bankDetailsDAO.getAllRecord();
		return bank;
	}

	@Override
	public List<BankDetailsEntity> getBankDataForEditing(String id) {
		List<BankDetailsEntity> edit = bankDetailsDAO.getBankDataForEditing(id);
		return edit;
	}

	@Override
	public BankDetailsEntity find(int id) {
		BankDetailsEntity edit = null;
		try {
			System.out.println("id ===> "+id);
			edit = bankDetailsDAO.customfind(id);
			//System.out.println("edit ===> "+edit.getBankAbbreviation());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return edit;
	}

	@Override
	public void update(BankDetailsEntity bankDetailsEntity) {
		bankDetailsDAO.customupdate(bankDetailsEntity);
	}

	@Override
	public Long checkForBankNameAvailability(String bankName) {
		Long l = bankDetailsDAO.checkForBankNameAvailability(bankName);
		return l;
	}

	@Override
	public void delete(int id) {
	
		try {
			System.out.println(" id ===> "+id);
			//int i = bankDetailsDAO.deleteById(id);
			bankDetailsDAO.customdelete(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	}

	@Override
	public List<BankDetailsEntity> getBankDetailsEntity() {
	
		return bankDetailsDAO.getBankDetailsEntity();
	}

}

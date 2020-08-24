package com.bcits.bsmartwater.serviceImpl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.ModuleMasterDAO;
import com.bcits.bsmartwater.model.ModuleMaster;
import com.bcits.bsmartwater.service.ModuleMasterService;

@Service
public class ModuleMasterServiceImpl implements ModuleMasterService {

	@Autowired
	private ModuleMasterDAO moduleMasterDAO; 
	
	@Override
	public List<String> getModuleNames() {
		return moduleMasterDAO.getModuleNames();
	}

	@Override
	public List<ModuleMaster> getAllModulesForLeftMenu(Set<String> modules) {
		return moduleMasterDAO.getAllModulesForLeftMenu(modules);
	}

}

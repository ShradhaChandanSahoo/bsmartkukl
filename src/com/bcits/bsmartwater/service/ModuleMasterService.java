package com.bcits.bsmartwater.service;

import java.util.List;
import java.util.Set;

import com.bcits.bsmartwater.model.ModuleMaster;

public interface ModuleMasterService {

	List<String> getModuleNames();

	List<ModuleMaster> getAllModulesForLeftMenu(Set<String> modulenames);

}

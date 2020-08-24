package com.bcits.bsmartwater.dao;

import java.util.List;
import java.util.Set;

import com.bcits.bsmartwater.model.ModuleMaster;

public interface ModuleMasterDAO extends GenericDAO<ModuleMaster>{

	List<String> getModuleNames();

	List<ModuleMaster> getAllModulesForLeftMenu(Set<String> modules);

}

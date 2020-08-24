package com.bcits.bsmartwater.daoImpl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.ModuleMasterDAO;
import com.bcits.bsmartwater.model.ModuleMaster;

@Repository
public class ModuleMasterDAOImpl extends GenericDAOImpl<ModuleMaster> implements ModuleMasterDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getModuleNames() {
		return entityManager.createNamedQuery("ModuleMaster.getModuleNames").getResultList();
	}

	@Override
	public List<ModuleMaster> getAllModulesForLeftMenu(Set<String> modules) {
		return entityManager.createNamedQuery("ModuleMaster.getAllModulesForLeftMenu",ModuleMaster.class).setParameter("module_name", modules).getResultList();
	}

}

package com.bcits.bsmartwater.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bcits.bsmartwater.dao.EmployeeDAO;
import com.bcits.bsmartwater.model.EmployeeEntity;

@Repository
public class EmployeeDAOImpl extends GenericDAOImpl<EmployeeEntity> implements EmployeeDAO {

	@Override
	public List<EmployeeEntity> getEmployeeList() {
		return entityManager.createNamedQuery("EmployeeEntity.getEmployeeList",EmployeeEntity.class).getResultList();
	}

}

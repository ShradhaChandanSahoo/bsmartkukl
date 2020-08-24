package com.bcits.bsmartwater.dao;

import java.util.List;

import com.bcits.bsmartwater.model.EmployeeEntity;

public interface EmployeeDAO extends GenericDAO<EmployeeEntity> {

	List<EmployeeEntity> getEmployeeList();

}

package com.bcits.bsmartwater.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcits.bsmartwater.dao.EmployeeDAO;
import com.bcits.bsmartwater.model.EmployeeEntity;
import com.bcits.bsmartwater.service.EmployeeService;
import com.bcits.bsmartwater.utils.DateTimeCalender;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private DateTimeCalender dateTimeCalender; 
	
	@Override
	public List<?> getEmployeeList() {
		return getData(employeeDAO.getEmployeeList());
	}
	
	private List<?> getData(List<EmployeeEntity> data){
		
		SimpleDateFormat sdf = dateTimeCalender.getGenericDateFormate();
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> employeeMap = null;
		for (EmployeeEntity employeeEntity : data){
			
			employeeMap = new HashMap<String, Object>();
			
			employeeMap.put("empId", employeeEntity.getEmpId());
			employeeMap.put("empCode", employeeEntity.getEmpCode());
			employeeMap.put("empName", employeeEntity.getEmpName());			
			employeeMap.put("dob", sdf.format(employeeEntity.getDob()));
			employeeMap.put("emailId", employeeEntity.getEmailId());			
			employeeMap.put("mobileNo", employeeEntity.getMobileNo());
			employeeMap.put("doj", sdf.format(employeeEntity.getDoj()));			
			employeeMap.put("netSalary", employeeEntity.getNetSalary());
			employeeMap.put("city", employeeEntity.getCity());
			employeeMap.put("country", employeeEntity.getCountry());
			
			result.add(employeeMap);
	     }
		return result;
	}

}

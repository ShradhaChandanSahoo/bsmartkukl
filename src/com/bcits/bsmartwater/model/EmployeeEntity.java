package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
@NamedQueries({
    @NamedQuery(name="EmployeeEntity.getEmployeeList", query="SELECT e FROM EmployeeEntity e ORDER BY e.empId DESC"),
})
public class EmployeeEntity {

		
	private int empId;	
	private String empCode;	
	private String empName;	
	private Date dob;	
	private String emailId;	
	private long mobileNo;	
	private Date doj;	
	private double netSalary;	
	private String city;	
	private String country;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public int getEmpId() {
		return empId;
	}
	
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	
	@Column(name="EMP_CODE")
	public String getEmpCode() {
		return empCode;
	}
	
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	
	@Column(name="EMP_NAME")
	public String getEmpName() {
		return empName;
	}
	
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	@Column(name="DOB")
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}
	
	@Column(name="EMAIL_ID")
	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	@Column(name="MOBILE_NO")
	public long getMobileNo() {
		return mobileNo;
	}
	
	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@Column(name="DOJ")
	public Date getDoj() {
		return doj;
	}
	
	public void setDoj(Date doj) {
		this.doj = doj;
	}
	
	@Column(name="NET_SALARY")
	public double getNetSalary() {
		return netSalary;
	}
	
	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}
	
	@Column(name="CITY")
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name="COUNTRY")
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
}

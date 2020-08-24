package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name= "SEWAGE_USED")
@NamedQueries({
@NamedQuery(name = "SewageChangeEntity.getAllRecords", query = "SELECT b FROM SewageChangeEntity b where b.status =0"),
@NamedQuery(name = "SewageChangeEntity.getSewageApprovalList", query = "SELECT b FROM SewageChangeEntity b where b.status =1 OR b.status=2 order by approved_date"),
@NamedQuery(name="SewageChangeEntity.findCustomerById",query="SELECT c FROM SewageChangeEntity c WHERE c.id=:id")

})
public class SewageChangeEntity 
{
@Id
@SequenceGenerator(name = "sewage_used_seq", sequenceName = "SEWAGE_USED_SEQ",allocationSize=1) 
@GeneratedValue(generator = "sewage_used_seq") 
@Column(name = "ID", nullable = false, precision = 6, scale = 0)
private int id;

@Column(name="CONNECTION_NO")
private String connectionNO;

@Column(name="MONTHYR")
private String monthYr;
@Column(name="SEWAGE_USED_NEW")
private String sewage_Used_New;
@Column(name="SEWAGE_USED_OLD")
private String sewage_Used_Old;
@Column(name="ADDED_BY")
private String addBy;
@Column(name="ADDED_DATE")
private Date addDate;

@Column(name="REMARKS")
private String remarks;

@Column(name="STATUS")
private int status;

@Column(name="APPROVED_BY")
private String approved_by;

@Column(name="APPROVED_DATE")
private Date approved_date;

public String getApproved_by() {
	return approved_by;
}
public void setApproved_by(String approved_by) {
	this.approved_by = approved_by;
}
public Date getApproved_date() {
	return approved_date;
}
public void setApproved_date(Date approved_date) {
	this.approved_date = approved_date;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getConnectionNO() {
	return connectionNO;
}
public void setConnectionNO(String connectionNO) {
	this.connectionNO = connectionNO;
}
public String getMonthYr() {
	return monthYr;
}
public void setMonthYr(String monthYr) {
	this.monthYr = monthYr;
}
public String getSewage_Used_New() {
	return sewage_Used_New;
}
public void setSewage_Used_New(String sewage_Used_New) {
	this.sewage_Used_New = sewage_Used_New;
}
public String getSewage_Used_Old() {
	return sewage_Used_Old;
}
public void setSewage_Used_Old(String sewage_Used_Old) {
	this.sewage_Used_Old = sewage_Used_Old;
}
public String getAddBy() {
	return addBy;
}
public void setAddBy(String addBy) {
	this.addBy = addBy;
}
public Date getAddDate() {
	return addDate;
}
public void setAddDate(Date addDate) {
	this.addDate = addDate;
}

}
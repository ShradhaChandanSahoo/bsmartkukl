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
@Table(name = "HOLIDAY_MASTER")

@NamedQueries({
  @NamedQuery(name="HolidayMaster.getRecordByDate", query="SELECT h FROM HolidayMaster h WHERE TRUNC(h.h_date)=TO_DATE(:h_date,'dd-MM-YYYY')"),
})
public class HolidayMaster {

	
	@Id
	@SequenceGenerator(name = "holiday_master_seq", sequenceName = "HOLIDAY_MASTER_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "holiday_master_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="HOLIDAY_NAME")
	private String holiday_name;
	
	@Column(name="H_DATE")
	private Date h_date;
	
	@Column(name="CREATED_BY")
	private String created_by;
	
	@Column(name="CREATED_DATE")
	private Date created_date;
	
	@Column(name="H_MONTH")
	private String h_month;
	
	@Column(name="H_DAY")
	private String h_day;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHoliday_name() {
		return holiday_name;
	}

	public void setHoliday_name(String holiday_name) {
		this.holiday_name = holiday_name;
	}
	
	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getH_date() {
		return h_date;
	}

	public void setH_date(Date h_date) {
		this.h_date = h_date;
	}

	public String getH_month() {
		return h_month;
	}

	public void setH_month(String h_month) {
		this.h_month = h_month;
	}

	public String getH_day() {
		return h_day;
	}

	public void setH_day(String h_day) {
		this.h_day = h_day;
	}
	
	
	
	
	
}

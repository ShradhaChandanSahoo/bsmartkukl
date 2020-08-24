package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table (name = "BSW_WARD_TO_MR")

@NamedQueries({
	@NamedQuery(name="WardToMrEntity.getAllWardToMeterReader",query="SELECT w.id, w.ward_no,w.reading_day,w.mr_id,m.mrName,w.added_by,w.added_date,w.mrid FROM WardToMrEntity w, MeterReaderEntity m WHERE w.mr_id=m.mrCode"),
	@NamedQuery(name="WardToMrEntity.uniqueWardToMrDetails", query="SELECT w FROM WardToMrEntity w WHERE ward_no=:ward_no AND reading_day=:reading_day"),
})

public class WardToMrEntity {
	
	@Id
	@SequenceGenerator(name = "bsw_ward_to_mr_seq", sequenceName = "BSW_WARD_TO_MR_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "bsw_ward_to_mr_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="WARD_NO")
	private String ward_no;
	
	@Column(name="READING_DAY")
	private Integer reading_day;
	
	@Column(name="MR_ID")
	private String mr_id;
	
	@Column(name="MRID")
	private Integer mrid;
	
	public Integer getMrid() {
		return mrid;
	}

	public void setMrid(Integer mrid) {
		this.mrid = mrid;
	}

	public MeterReaderEntity getMeterReaderEntity() {
		return meterReaderEntity;
	}

	public void setMeterReaderEntity(MeterReaderEntity meterReaderEntity) {
		this.meterReaderEntity = meterReaderEntity;
	}

	@OneToOne
	@JoinColumn(name = "MRID", referencedColumnName = "ID", insertable = false, updatable = false)
	private MeterReaderEntity meterReaderEntity;
	
	@Column(name="ADDED_BY")
	private String added_by;
	
	@Column(name="ADDED_DATE")
	private Date added_date;
	
	@Column(name="UPDATED_BY")
	private String updated_by;
	
	@Column(name="UPDATED_DATE")
	private Date updated_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWard_no() {
		return ward_no;
	}

	public void setWard_no(String ward_no) {
		this.ward_no = ward_no;
	}

	public Integer getReading_day() {
		return reading_day;
	}

	public void setReading_day(Integer reading_day) {
		this.reading_day = reading_day;
	}

	public String getMr_id() {
		return mr_id;
	}

	public void setMr_id(String mr_id) {
		this.mr_id = mr_id;
	}

	public String getAdded_by() {
		return added_by;
	}

	public void setAdded_by(String added_by) {
		this.added_by = added_by;
	}

	public Date getAdded_date() {
		return added_date;
	}

	public void setAdded_date(Date added_date) {
		this.added_date = added_date;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}
	
	
	
	
	
}

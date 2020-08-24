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
@Table(name="BSW_METER_MASTER")
@NamedQueries({
	
	@NamedQuery(name="MeterDetailsEntity.uniqueMeterDetails",query="SELECT M FROM MeterDetailsEntity M WHERE M.connectionno=:connectionno ORDER BY M.id DESC"),
	@NamedQuery(name="MeterDetailsEntity.readMeterDetails",query="SELECT U FROM MeterDetailsEntity U ORDER BY U.id ASC"),
	@NamedQuery(name="MeterDetailsEntity.uniqueMeterDetailsForEdit",query="SELECT M FROM MeterDetailsEntity M WHERE M.connectionno=:connectionno AND M.id=:id ORDER BY M.id DESC"),
	@NamedQuery(name="MeterDetailsEntity.viewConnMeterDetails",query="SELECT M FROM MeterDetailsEntity M WHERE M.connectionno=:connectionno ORDER BY M.id DESC"),
	@NamedQuery(name="MeterDetailsEntity.getMeterDetailsDataByConnectionNum",query="SELECT M FROM MeterDetailsEntity M WHERE UPPER(M.connectionno)=:connId ORDER BY M.id DESC"),
	@NamedQuery(name="MeterDetailsEntity.findByConnNo",query="SELECT M FROM MeterDetailsEntity M WHERE M.connectionno=:connectionno"),
	@NamedQuery(name="MeterDetailsEntity.uniqueMeterNoChk", query="SELECT M FROM MeterDetailsEntity M WHERE UPPER(M.meter_no)=:meter_no")
	
})
public class MeterDetailsEntity
{
	@Id
	@SequenceGenerator(name = "bsw_meter_master_seq", sequenceName = "BSW_METER_MASTER_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_meter_master_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="CONNECTIONNO")
	private String connectionno;
	
	@Column(name="IR")
	private int ir;
	
	@Column(name="METER_MAKE")
	private String meter_make;
	
	@Column(name="METER_NO")
	private String meter_no;
	
	@Column(name="METER_CAPACITY")
	private String meter_capacity;
	
	@Column(name="METER_OWN")
	private String meter_own;
	
	@Column(name="INS_DATE_ENG")
	private Date ins_date_eng;
	
	@Column(name="INS_DATE_NEP")
	private String ins_date_nep;
	
	@Column(name="METCON_DATE_ENG")
	private Date metcon_date_eng;
	
	@Column(name="METCON_DATE_NEP")
	private String metcon_date_nep;
	
	@Column(name="CAL_DATE_ENG")
	private Date cal_date_eng;
	
	@Column(name="CAL_DATE_NEP")
	private String cal_date_nep;
	
	@Column(name="ENT_DATE_ENG")
	private Date ent_date_eng;
	
	@Column(name="ENT_DATE_NEP")
	private String ent_date_nep;
	
	@Column(name="CALIBRATED_OFFICER")
	private String calibrated_officer;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ADDED_BY")
	private String added_by;
	
	@Column(name="ADDED_DATE")
	private Date added_date;
	
	@Column(name="STATUS")
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConnectionno() {
		return connectionno;
	}

	public void setConnectionno(String connectionno) {
		this.connectionno = connectionno;
	}

	public int getIr() {
		return ir;
	}

	public void setIr(int ir) {
		this.ir = ir;
	}

	public String getMeter_make() {
		return meter_make;
	}

	public void setMeter_make(String meter_make) {
		this.meter_make = meter_make;
	}

	public String getMeter_no() {
		return meter_no;
	}

	public void setMeter_no(String meter_no) {
		this.meter_no = meter_no;
	}

	public String getMeter_capacity() {
		return meter_capacity;
	}

	public void setMeter_capacity(String meter_capacity) {
		this.meter_capacity = meter_capacity;
	}

	public String getMeter_own() {
		return meter_own;
	}

	public void setMeter_own(String meter_own) {
		this.meter_own = meter_own;
	}

	public Date getIns_date_eng() {
		return ins_date_eng;
	}

	public void setIns_date_eng(Date ins_date_eng) {
		this.ins_date_eng = ins_date_eng;
	}

	public String getIns_date_nep() {
		return ins_date_nep;
	}

	public void setIns_date_nep(String ins_date_nep) {
		this.ins_date_nep = ins_date_nep;
	}

	public Date getMetcon_date_eng() {
		return metcon_date_eng;
	}

	public void setMetcon_date_eng(Date metcon_date_eng) {
		this.metcon_date_eng = metcon_date_eng;
	}

	public String getMetcon_date_nep() {
		return metcon_date_nep;
	}

	public void setMetcon_date_nep(String metcon_date_nep) {
		this.metcon_date_nep = metcon_date_nep;
	}

	public Date getCal_date_eng() {
		return cal_date_eng;
	}

	public void setCal_date_eng(Date cal_date_eng) {
		this.cal_date_eng = cal_date_eng;
	}

	public String getCal_date_nep() {
		return cal_date_nep;
	}

	public void setCal_date_nep(String cal_date_nep) {
		this.cal_date_nep = cal_date_nep;
	}

	public Date getEnt_date_eng() {
		return ent_date_eng;
	}

	public void setEnt_date_eng(Date ent_date_eng) {
		this.ent_date_eng = ent_date_eng;
	}

	public String getEnt_date_nep() {
		return ent_date_nep;
	}

	public void setEnt_date_nep(String ent_date_nep) {
		this.ent_date_nep = ent_date_nep;
	}

	public String getCalibrated_officer() {
		return calibrated_officer;
	}

	public void setCalibrated_officer(String calibrated_officer) {
		this.calibrated_officer = calibrated_officer;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}

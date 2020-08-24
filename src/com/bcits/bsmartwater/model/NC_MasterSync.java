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
@Table(name = "NC_MASTER")
@NamedQueries({
	@NamedQuery(name="NC_MasterSync.findAllByConNOSC",query="SELECT n from NC_MasterSync n where n.connection_no=:connection_no and n.sitecode=:sitecode")
})
public class NC_MasterSync {

	@Id
	@SequenceGenerator(name = "nc_master_seq", sequenceName = "NC_MASTER_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "nc_master_seq") 
	@Column(name="ID")
	private int id;

	@Column(name="SITECODE")
	private String sitecode;
	
	@Column(name="APPLICATION_ID")
	private Long application_id;
	
	@Column(name="CONNECTION_NO")
	private String connection_no;
	
	@Column(name="METER_SLNO")
	private String meter_slno;
	
	@Column(name="INITIAL_RDNG")
	private Double initial_rdng;
		
	@Column(name="METER_MAKE")
	private String meter_make;
	
	@Column(name="MTR_INS_DATE")
	private Date mtr_ins_date;
	
	@Column(name="METER_OWN")
	private String meter_own;
	
	@Column(name="ADVANCE")
	private Double advance;
	
	@Column(name="NCTAP")
	private Double nctap;
	
	@Column(name="NCDEPOSIT")
	private Double ncdeposit;
	
	@Column(name="MVALUE")
	private Double mvalue;
	
	@Column(name="TOTAL_AMT")
	private Double total_amt;
	
	@Column(name="CREATED_BY")
	private String created_by;
	
	@Column(name="CREATED_DATE")
	private String created_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public Long getApplication_id() {
		return application_id;
	}

	public void setApplication_id(Long application_id) {
		this.application_id = application_id;
	}

	public String getConnection_no() {
		return connection_no;
	}

	public void setConnection_no(String connection_no) {
		this.connection_no = connection_no;
	}

	public String getMeter_slno() {
		return meter_slno;
	}

	public void setMeter_slno(String meter_slno) {
		this.meter_slno = meter_slno;
	}

	public Double getInitial_rdng() {
		return initial_rdng;
	}

	public void setInitial_rdng(Double initial_rdng) {
		this.initial_rdng = initial_rdng;
	}

	public String getMeter_make() {
		return meter_make;
	}

	public void setMeter_make(String meter_make) {
		this.meter_make = meter_make;
	}

	public Date getMtr_ins_date() {
		return mtr_ins_date;
	}

	public void setMtr_ins_date(Date mtr_ins_date) {
		this.mtr_ins_date = mtr_ins_date;
	}

	public String getMeter_own() {
		return meter_own;
	}

	public void setMeter_own(String meter_own) {
		this.meter_own = meter_own;
	}

	public Double getAdvance() {
		return advance;
	}

	public void setAdvance(Double advance) {
		this.advance = advance;
	}

	public Double getNctap() {
		return nctap;
	}

	public void setNctap(Double nctap) {
		this.nctap = nctap;
	}

	public Double getNcdeposit() {
		return ncdeposit;
	}

	public void setNcdeposit(Double ncdeposit) {
		this.ncdeposit = ncdeposit;
	}

	public Double getMvalue() {
		return mvalue;
	}

	public void setMvalue(Double mvalue) {
		this.mvalue = mvalue;
	}

	public Double getTotal_amt() {
		return total_amt;
	}

	public void setTotal_amt(Double total_amt) {
		this.total_amt = total_amt;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	

}

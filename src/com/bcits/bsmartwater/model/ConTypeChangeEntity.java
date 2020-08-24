package com.bcits.bsmartwater.model;

import java.sql.Timestamp;
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
@Table(name = "CON_TYPE_CHANGE")

@NamedQueries({
	@NamedQuery(name="ConTypeChangeEntity.getPendingConnectionsToApprove",query="SELECT c FROM ConTypeChangeEntity c WHERE c.approve_status=:approve_status"),
	@NamedQuery(name="ConTypeChangeEntity.getConTypeByConNo",query="SELECT c FROM ConTypeChangeEntity c WHERE c.approve_status=:approve_status AND UPPER(c.connection_no)=:connection_no"),
	@NamedQuery(name="ConTypeChangeEntity.getConnectionTypeApproveList",query="SELECT c FROM ConTypeChangeEntity c"),
	
	
})

public class ConTypeChangeEntity {
	
	@Id
	@SequenceGenerator(name = "contypechange_seq", sequenceName = "CONTYPECHANGE_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "contypechange_seq") 
	@Column(name="ID")
	private int id;

	@Column(name="CONNECTION_NO")
	private String connection_no;

	@Column(name="PIPE_SIZE")
	private Double pipe_size;
	
	@Column(name="AREA_NO")
	private String area_no;
	
	@Column(name="EXIST_CON_TYPE")
	private String exist_con_type;
	
	@Column(name="NEW_CON_TYPE")
	private String new_con_type;

	@Column(name="EXIST_CON_STATUS")
	private String exist_con_status;

	@Column(name="NEW_CON_STATUS")
	private String new_con_status;
	
	@Column(name="EFECTED_DATE_NEP")
	private String efected_date_nep;
	
	@Column(name="EFECTED_DATE_ENG")
	private Date efected_date_eng;
	
	@Column(name="SUBMIT_BY")
	private String submit_by;
	
	@Column(name="SUBMIT_DATE_NEP")
	private String submit_date_nep;
	
	@Column(name="SUBMIT_DATE_ENG")
	private Timestamp submit_date_eng;
	
	@Column(name="APPROVED_BY")
	private String approved_by;
	
	@Column(name="APPROVED_DATE_ENG")
	private Date approved_date_eng;
	
	@Column(name="APPROVED_DATE_NEP")
	private String approved_date_nep;
	
	@Column(name="APPROVE_STATUS")
	private Integer approve_status;
	
	@Column(name="NAME_ENG")
	private String name_eng;

	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="NEW_PIPE_SIZE")
	private String new_pipe_size;

	@Column(name="NEW_AREA_NO")
	private String new_area_no;
	
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getName_eng() {
		return name_eng;
	}

	public void setName_eng(String name_eng) {
		this.name_eng = name_eng;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConnection_no() {
		return connection_no;
	}

	public void setConnection_no(String connection_no) {
		this.connection_no = connection_no;
	}

	public Double getPipe_size() {
		return pipe_size;
	}

	public void setPipe_size(Double pipe_size) {
		this.pipe_size = pipe_size;
	}

	public String getArea_no() {
		return area_no;
	}

	public void setArea_no(String area_no) {
		this.area_no = area_no;
	}


	public String getExist_con_type() {
		return exist_con_type;
	}

	public void setExist_con_type(String exist_con_type) {
		this.exist_con_type = exist_con_type;
	}

	public String getNew_con_type() {
		return new_con_type;
	}

	public void setNew_con_type(String new_con_type) {
		this.new_con_type = new_con_type;
	}

	public String getExist_con_status() {
		return exist_con_status;
	}

	public void setExist_con_status(String exist_con_status) {
		this.exist_con_status = exist_con_status;
	}

	public String getNew_con_status() {
		return new_con_status;
	}

	public void setNew_con_status(String new_con_status) {
		this.new_con_status = new_con_status;
	}

	public String getEfected_date_nep() {
		return efected_date_nep;
	}

	public void setEfected_date_nep(String efected_date_nep) {
		this.efected_date_nep = efected_date_nep;
	}

	public Date getEfected_date_eng() {
		return efected_date_eng;
	}

	public void setEfected_date_eng(Date efected_date_eng) {
		this.efected_date_eng = efected_date_eng;
	}

	public String getSubmit_by() {
		return submit_by;
	}

	public void setSubmit_by(String submit_by) {
		this.submit_by = submit_by;
	}

	public String getSubmit_date_nep() {
		return submit_date_nep;
	}

	public void setSubmit_date_nep(String submit_date_nep) {
		this.submit_date_nep = submit_date_nep;
	}

	public Timestamp getSubmit_date_eng() {
		return submit_date_eng;
	}

	public void setSubmit_date_eng(Timestamp submit_date_eng) {
		this.submit_date_eng = submit_date_eng;
	}

	public String getApproved_by() {
		return approved_by;
	}

	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}

	public Date getApproved_date_eng() {
		return approved_date_eng;
	}

	public void setApproved_date_eng(Date approved_date_eng) {
		this.approved_date_eng = approved_date_eng;
	}

	public String getApproved_date_nep() {
		return approved_date_nep;
	}

	public void setApproved_date_nep(String approved_date_nep) {
		this.approved_date_nep = approved_date_nep;
	}

	public Integer getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(Integer approve_status) {
		this.approve_status = approve_status;
	}

	public String getNew_pipe_size() {
		return new_pipe_size;
	}

	public void setNew_pipe_size(String new_pipe_size) {
		this.new_pipe_size = new_pipe_size;
	}

	public String getNew_area_no() {
		return new_area_no;
	}

	public void setNew_area_no(String new_area_no) {
		this.new_area_no = new_area_no;
	}

	



	

			

}

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
@Table(name = "BSW_ADJ_VOUCHER")

@NamedQueries({
	
 @NamedQuery(name="AdjustmentVoucher.getAdjustmentList",query="SELECT c.connection_no,c.name_eng,c.area_no,c.pipe_size,c.con_type,b.adjustment_no,b.adjustment_amount,b.adjustment_date,b.remarks,b.prepared_by FROM AdjustmentVoucher b,ConsumerMaster c WHERE b.connection_no=c.connection_no AND b.status=:status"),
 @NamedQuery(name="AdjustmentVoucher.getMaxAdjustmentNo", query="SELECT MAX(a.adjustment_no) FROM AdjustmentVoucher a WHERE a.adjustment_no LIKE :adjustment_no"),
})
public class AdjustmentVoucher {

	
	@Id
	@SequenceGenerator(name = "bsw_adj_voucher_seq", sequenceName = "BSW_ADJ_VOUCHER_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "bsw_adj_voucher_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="CONNECTION_NO")
	private String connection_no;
	
	@Column(name="ADJUSTMENT_NO")
	private String adjustment_no;
	
	@Column(name="ADJUSTMENT_DATE")
	private Date adjustment_date;
	
	@Column(name="ADJUSTMENT_AMOUNT")
	private Double adjustment_amount;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="PREPARED_BY")
	private String prepared_by;
	
	@Column(name="CREATED_BY")
	private String created_by;

	@Column(name="CREATED_DATE")
	private Date created_date;
	
	@Column(name="UPDATED_BY")
	private String updated_by;
	
	@Column(name="UPDATED_DATE")
	private Date updated_date;
	
	@Column(name="ADJUSTED_BY")
	private String adjusted_by;
	
	@Column(name="STATUS")
	private Integer status;

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

	public String getAdjustment_no() {
		return adjustment_no;
	}

	public void setAdjustment_no(String adjustment_no) {
		this.adjustment_no = adjustment_no;
	}

	public Date getAdjustment_date() {
		return adjustment_date;
	}

	public void setAdjustment_date(Date adjustment_date) {
		this.adjustment_date = adjustment_date;
	}

	public Double getAdjustment_amount() {
		return adjustment_amount;
	}

	public void setAdjustment_amount(Double adjustment_amount) {
		this.adjustment_amount = adjustment_amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPrepared_by() {
		return prepared_by;
	}

	public void setPrepared_by(String prepared_by) {
		this.prepared_by = prepared_by;
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

	public String getAdjusted_by() {
		return adjusted_by;
	}

	public void setAdjusted_by(String adjusted_by) {
		this.adjusted_by = adjusted_by;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}

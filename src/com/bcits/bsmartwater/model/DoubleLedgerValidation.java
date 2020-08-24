package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "BSW_DBL_LEDVAL")
@NamedQueries({
	@NamedQuery(name="DoubleLedgerValidation.getRecordByWardAndRdngDay", query="select d from DoubleLedgerValidation d where d.ward_no=:ward_no and d.reading_day=:reading_day"),
})
public class DoubleLedgerValidation {

	
	@Id
	@SequenceGenerator(name = "dl_ledval_seq", sequenceName = "BSW_DBL_LEDVAL_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "dl_ledval_seq") 
	@Column(name="ID")
	private int id;

	@Column(name="WARD_NO")
	private String ward_no;
	
	@Column(name="READING_DAY")
	private Integer reading_day;
	
	@Column(name="FLAG")
	private Integer flag;

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

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	
	
	
	
	
}

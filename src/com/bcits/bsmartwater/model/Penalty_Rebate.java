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
@Table(name = "BSW_PENALTY_REBATE")

@NamedQueries({
	
	@NamedQuery(name="Penalty_Rebate.getPenaltyRebateByDays",query="SELECT pr.penalty_percent,pr.rebate_precent FROM Penalty_Rebate pr WHERE pr.from_days<=:days AND pr.to_days>=:days")
})
public class Penalty_Rebate {

	@Id
	@SequenceGenerator(name = "bsw_penalty_rebate_seq", sequenceName = "BSW_PENALTY_REBATE_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_penalty_rebate_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="FROM_DAYS")
	private  Integer from_days;
	
	@Column(name="TO_DAYS")
	private Integer to_days;
	
	@Column(name="PENALTY_PERCENT")
	private Double penalty_percent;
	
	@Column(name="REBATE_PERCENT")
	private Double rebate_precent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getFrom_days() {
		return from_days;
	}

	public void setFrom_days(Integer from_days) {
		this.from_days = from_days;
	}

	public Integer getTo_days() {
		return to_days;
	}

	public void setTo_days(Integer to_days) {
		this.to_days = to_days;
	}

	public Double getPenalty_percent() {
		return penalty_percent;
	}

	public void setPenalty_percent(Double penalty_percent) {
		this.penalty_percent = penalty_percent;
	}

	public Double getRebate_precent() {
		return rebate_precent;
	}

	public void setRebate_precent(Double rebate_precent) {
		this.rebate_precent = rebate_precent;
	}
	
	
	
	
	
	
	
	
	
}

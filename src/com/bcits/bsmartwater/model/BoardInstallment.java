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
@Table(name = "BOARD_INSTALLMENT")

@NamedQueries({
	
	@NamedQuery(name="BoardInstallment.viewBoardLedgertHistory",query="SELECT b.connection_no,b.install_amount,b.penalty,b.submit_by,b.submit_date,b.rem_balance,c.name_eng,c.area_no,c.customer_id,c.install_due_date FROM BoardInstallment b,ConsumerMaster c WHERE UPPER(b.connection_no)=:connection_no AND b.connection_no=c.connection_no"),
	@NamedQuery(name="BoardInstallment.getBoardInstallment",query="SELECT b.connection_no,b.paid_amount,b.install_amount,b.penalty,b.rem_balance,c.name_eng,c.area_no,c.customer_id,c.install_due_date FROM BoardInstallment b,ConsumerMaster c WHERE UPPER(b.connection_no)=:connection_no AND b.receipt_no=:receipt_no AND b.connection_no=c.connection_no"),

	
	
})
public class BoardInstallment {

	@Id
	@SequenceGenerator(name = "board_installment_seq", sequenceName = "BOARD_INSTALLMENT_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "board_installment_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="CONNECTION_NO")
	private String connection_no;
	
	@Column(name="INSTALL_AMOUNT")
	private Double install_amount;
	
	@Column(name="PENALTY")
	private Double penalty;
	
	@Column(name="REM_BALANCE")
	private Double rem_balance;
	
	@Column(name="PAID_AMOUNT")
	private Double paid_amount;
	
	@Column(name="RECEIPT_NO")
	private String receipt_no;
	
	@Column(name="PENALTY_ADJ_AMT")
	private Double penalty_adj_amt;
	
	@Column(name="BOARD_ADJ_AMT")
	private Double board_adj_amt;
	
	@Column(name="ADJ_ID")
	private Integer adj_id;
	
	public Double getPenalty_adj_amt() {
		return penalty_adj_amt;
	}

	public void setPenalty_adj_amt(Double penalty_adj_amt) {
		this.penalty_adj_amt = penalty_adj_amt;
	}

	public Double getBoard_adj_amt() {
		return board_adj_amt;
	}

	public void setBoard_adj_amt(Double board_adj_amt) {
		this.board_adj_amt = board_adj_amt;
	}

	public Integer getAdj_id() {
		return adj_id;
	}

	public void setAdj_id(Integer adj_id) {
		this.adj_id = adj_id;
	}

	public Double getPaid_amount() {
		return paid_amount;
	}

	public void setPaid_amount(Double paid_amount) {
		this.paid_amount = paid_amount;
	}

	public String getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no(String receipt_no) {
		this.receipt_no = receipt_no;
	}

	public Double getRem_balance() {
		return rem_balance;
	}

	public void setRem_balance(Double rem_balance) {
		this.rem_balance = rem_balance;
	}

	@Column(name="SUBMIT_BY")
	private String submit_by;
	
	@Column(name="SUBMIT_DATE")
	private Date submit_date;

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

	public Double getInstall_amount() {
		return install_amount;
	}

	public void setInstall_amount(Double install_amount) {
		this.install_amount = install_amount;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public String getSubmit_by() {
		return submit_by;
	}

	public void setSubmit_by(String submit_by) {
		this.submit_by = submit_by;
	}

	public Date getSubmit_date() {
		return submit_date;
	}

	public void setSubmit_date(Date submit_date) {
		this.submit_date = submit_date;
	}


	
	
	
	
	
}

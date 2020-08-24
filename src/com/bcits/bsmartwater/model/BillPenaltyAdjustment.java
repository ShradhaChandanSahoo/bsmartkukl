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
@Table(name = "BILL_PENALTY_ADJ")

@NamedQueries({
	@NamedQuery(name="BillPenaltyAdjustment.getPendingConnectionsToApprove",query="SELECT b.connection_no,c.name_eng,c.area_no,c.pipe_size,b.bill_adj_amount,b.penalty_adj_amount,b.from_mon_year,b.to_mon_year,b.submit_by,b.submit_date, b.remarks FROM BillPenaltyAdjustment b,ConsumerMaster c WHERE UPPER(c.connection_no)=UPPER(b.connection_no) AND b.approve_status=:approve_status"),
	@NamedQuery(name="BillPenaltyAdjustment.getConTypeByConNo",query="SELECT b FROM BillPenaltyAdjustment b WHERE b.approve_status=:approve_status AND UPPER(b.connection_no)=:connection_no"),
	@NamedQuery(name="BillPenaltyAdjustment.getConTypeByConNo1",query="SELECT b FROM BillPenaltyAdjustment b WHERE b.approve_status=:approve_status AND UPPER(b.connection_no)=:connection_no AND b.adj_type IN('BDJ')"),
	@NamedQuery(name="BillPenaltyAdjustment.getPendingBoardByConNo", query="SELECT b FROM BillPenaltyAdjustment b WHERE b.approve_status=:approve_status AND UPPER(b.connection_no)=:connection_no AND b.adj_type IN('BOARD')"),
	@NamedQuery(name="BillPenaltyAdjustment.getPendingBoardCrByConNo", query="SELECT b FROM BillPenaltyAdjustment b WHERE b.approve_status=:approve_status AND UPPER(b.connection_no)=:connection_no AND b.adj_type IN('BRDCR')"),
	@NamedQuery(name="BillPenaltyAdjustment.checkPendingRequests",query="SELECT b FROM BillPenaltyAdjustment b WHERE b.approve_status=:approve_status AND UPPER(b.connection_no)=:connection_no AND b.adj_type=:adj_type"),
	@NamedQuery(name="BillPenaltyAdjustment.getPendingConnectionsToApprove1",query="SELECT b.connection_no,c.name_eng,c.area_no,c.pipe_size,b.bill_adj_amount,b.penalty_adj_amount,b.from_mon_year,b.to_mon_year,b.submit_by,b.submit_date,b.remarks,b.board_adj,b.rebate_adj_amount FROM BillPenaltyAdjustment b,ConsumerMaster c WHERE UPPER(c.connection_no)=UPPER(b.connection_no) AND b.approve_status=:approve_status AND b.adj_type=:adj_type"),
	@NamedQuery(name="BillPenaltyAdjustment.getPendingConnectionsToApprove2",query="SELECT b.connection_no,c.name_eng,c.area_no,c.pipe_size,b.board_adj,b.penalty_adj_amount,b.from_mon_year,b.to_mon_year,b.submit_by,b.submit_date,b.remarks,b.board_adj,b.rebate_adj_amount FROM BillPenaltyAdjustment b,ConsumerMaster c WHERE UPPER(c.connection_no)=UPPER(b.connection_no) AND b.approve_status=:approve_status AND b.adj_type=:adj_type"),
	@NamedQuery(name="BillPenaltyAdjustment.checkPendingRequestsT",query="SELECT b FROM BillPenaltyAdjustment b WHERE b.approve_status IN(0,1) AND UPPER(b.connection_no)=:connection_no AND b.adj_type=:adj_type"),
	@NamedQuery(name="BillPenaltyAdjustment.getTransactionPending",query="SELECT COUNT(*) FROM BillPenaltyAdjustment b WHERE b.approve_status IN(0) AND UPPER(b.connection_no)=:connectionNo AND b.adj_type IN('BDJ')"),
	@NamedQuery(name="BillPenaltyAdjustment.pendingForArrCorr",query="SELECT COUNT(*) FROM BillPenaltyAdjustment b WHERE b.approve_status IN(0) AND UPPER(b.connection_no)=:connectionNo AND b.adj_type IN('BAC')"),
	@NamedQuery(name="BillPenaltyAdjustment.getAdjCorrList", query="SELECT b.connection_no, c.name_eng, c.area_no, b.bill_adj_amount, b.penalty_adj_amount, b.adj_type, b.to_mon_year, b.submit_by, b.submit_date, b.approved_by, b.approved_date, b.approved_by1, b.approved_date1, b.approve_status, b.remarks FROM BillPenaltyAdjustment b,ConsumerMaster c WHERE c.connection_no=b.connection_no"),
	@NamedQuery(name="BillPenaltyAdjustment.getAdjTransList1", query="SELECT b.connection_no, c.name_eng, c.area_no, b.board_adj, b.penalty_adj_amount, b.adj_type, b.to_mon_year, b.submit_by, b.submit_date, b.approved_by, b.approved_date, b.approved_by1, b.approved_date1, b.approve_status, b.remarks,b.board_adj,b.rebate_adj_amount FROM BillPenaltyAdjustment b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND b.adj_type=:adj_type AND b.approve_status=1"),
	@NamedQuery(name="BillPenaltyAdjustment.getAdjTransList", query="SELECT b.connection_no, c.name_eng, c.area_no, b.bill_adj_amount, b.penalty_adj_amount, b.adj_type, b.to_mon_year, b.submit_by, b.submit_date, b.approved_by, b.approved_date, b.approved_by1, b.approved_date1, b.approve_status, b.remarks,b.board_adj,b.rebate_adj_amount FROM BillPenaltyAdjustment b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND b.adj_type=:adj_type AND b.approve_status=1  order by  b.submit_date desc, b.approved_date desc,b.approved_date1 "),
	@NamedQuery(name="BillPenaltyAdjustment.getAdjTransListByBranch", query="SELECT b.connection_no, c.name_eng, c.area_no, b.bill_adj_amount, b.penalty_adj_amount, b.adj_type, b.to_mon_year, b.submit_by, b.submit_date, b.approved_by, b.approved_date, b.approved_by1, b.approved_date1, b.approve_status, b.remarks,b.board_adj,b.rebate_adj_amount FROM BillPenaltyAdjustment b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND b.adj_type='BDJ' AND b.approve_status=1"),
	@NamedQuery(name="BillPenaltyAdjustment.checkPendingRequestsSameMonth", query="SELECT b FROM BillPenaltyAdjustment b WHERE b.approve_status IN(0,1) AND UPPER(b.connection_no)=:connection_no AND b.to_mon_year=:to_mon_year AND b.adj_type='BAC'"),
	@NamedQuery(name="BillPenaltyAdjustment.pendingCount", query="SELECT COUNT(*) FROM BillPenaltyAdjustment b WHERE b.approve_status IN(0) AND b.from_mon_year=:monthyearnep"),


})
public class BillPenaltyAdjustment {

	@Id
	@SequenceGenerator(name = "bill_penalty_adj_seq", sequenceName = "BILL_PENALTY_ADJ_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "bill_penalty_adj_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="CONNECTION_NO")
	private String connection_no;
	
	@Column(name="BILL_ADJ_AMOUNT")
	private Double bill_adj_amount;
	
	@Column(name="PENALTY_ADJ_AMOUNT")
	private Double penalty_adj_amount;
	
	@Column(name="FROM_MON_YEAR")
	private Integer from_mon_year;
	
	@Column(name="TO_MON_YEAR")
	private Integer to_mon_year;
	
	@Column(name="SUBMIT_BY")
	private String submit_by;
	
	@Column(name="SUBMIT_DATE")
	private Date submit_date;
	
	@Column(name="APPROVED_BY")
	private String approved_by;
	
	@Column(name="APPROVED_DATE")
	private Date approved_date;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="ADJ_TYPE")
	private String adj_type;
	
	@Column(name="APPROVE_STATUS")
	private Integer approve_status;
	
	@Column(name="ARREARS")
	private Double arrears;
	
	@Column(name="NET_AMOUNT")
	private Double net_amount;
	
	@Column(name="APPROVED_BY1")
	private String approved_by1;
	
	@Column(name="APPROVED_DATE1")
	private Date approved_date1;
	

	@Column(name="BOARD_ADJ")
	private Double board_adj;


	@Column(name="CON_CATEGORY")
	private String con_category;
	
	@Column(name="REBATE_ADJ_AMOUNT")
	private Double rebate_adj_amount;

	

	public Double getBoard_adj() {
		return board_adj;
	}

	public void setBoard_adj(Double board_adj) {
		this.board_adj = board_adj;
	}



	@Column(name="CON_TYPE")
	private String con_type;
	
	@Column(name="PIPE_SIZE")
	private String pipe_size;
	
	@Column(name="DENOTED_BY")
	private String denoted_by;
	
	@Column(name="WARD_NO")
	private String ward_no;
	
	

	public Integer getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(Integer approve_status) {
		this.approve_status = approve_status;
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

	public Double getBill_adj_amount() {
		return bill_adj_amount;
	}

	public void setBill_adj_amount(Double bill_adj_amount) {
		this.bill_adj_amount = bill_adj_amount;
	}

	public Double getPenalty_adj_amount() {
		return penalty_adj_amount;
	}

	public void setPenalty_adj_amount(Double penalty_adj_amount) {
		this.penalty_adj_amount = penalty_adj_amount;
	}

	public Integer getFrom_mon_year() {
		return from_mon_year;
	}

	public void setFrom_mon_year(Integer from_mon_year) {
		this.from_mon_year = from_mon_year;
	}

	public Integer getTo_mon_year() {
		return to_mon_year;
	}

	public void setTo_mon_year(Integer to_mon_year) {
		this.to_mon_year = to_mon_year;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public Double getArrears() {
		return arrears;
	}

	public void setArrears(Double arrears) {
		this.arrears = arrears;
	}

	public Double getNet_amount() {
		return net_amount;
	}

	public String getAdj_type() {
		return adj_type;
	}

	public void setAdj_type(String adj_type) {
		this.adj_type = adj_type;
	}

	public void setNet_amount(Double net_amount) {
		this.net_amount = net_amount;
	}

	public String getApproved_by1() {
		return approved_by1;
	}

	public void setApproved_by1(String approved_by1) {
		this.approved_by1 = approved_by1;
	}

	public Date getApproved_date1() {
		return approved_date1;
	}

	public void setApproved_date1(Date approved_date1) {
		this.approved_date1 = approved_date1;
	}

	public String getCon_category() {
		return con_category;
	}

	public void setCon_category(String con_category) {
		this.con_category = con_category;
	}

	public String getCon_type() {
		return con_type;
	}

	public void setCon_type(String con_type) {
		this.con_type = con_type;
	}

	public String getPipe_size() {
		return pipe_size;
	}

	public void setPipe_size(String pipe_size) {
		this.pipe_size = pipe_size;
	}

	public String getDenoted_by() {
		return denoted_by;
	}

	public void setDenoted_by(String denoted_by) {
		this.denoted_by = denoted_by;
	}

	public String getWard_no() {
		return ward_no;
	}

	public void setWard_no(String ward_no) {
		this.ward_no = ward_no;
	}

	public Double getRebate_adj_amount() {
		return rebate_adj_amount;
	}

	public void setRebate_adj_amount(Double rebate_adj_amount) {
		this.rebate_adj_amount = rebate_adj_amount;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

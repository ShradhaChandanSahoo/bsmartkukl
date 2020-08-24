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
@Table(name="BSW_PAYMENTS")
@NamedQueries({

	@NamedQuery(name="PaymentEntity.viewPaymentHistory",query="SELECT p.amount,p.receiptNo,p.rdate,p.remarks,p.remarks,p.penalty,p.rebate,p.payMode FROM PaymentEntity p WHERE UPPER(p.connectionNo)=:connectionNo ORDER BY p.rdate DESC"),
	@NamedQuery(name="PaymentEntity.getLatestBillReceipt",query="SELECT p FROM PaymentEntity p WHERE p.connectionNo=:connectionNo AND p.id IN(SELECT MAX(p.id) FROM PaymentEntity p WHERE p.connectionNo=:connectionNo)"),
	
	//@NamedQuery(name="PaymentEntity.getSumOfAmoutBWPcashposAndCashPosDate",query="SELECT SUM(p.amount) FROM PaymentEntity p WHERE p.connectionNo=:connectionNo AND date(p.rdate)>TO_DATE(:fdate,'dd/MM/YYYY') AND date(p.rdate)<=TO_DATE(:tdate,'dd/MM/YYYY')"),
	
	@NamedQuery(name="PaymentEntity.getpaymentMaxId",query="SELECT MAX(p.id) FROM PaymentEntity p"),
	@NamedQuery(name="PaymentEntity.getpaymentBasedOnId",query="SELECT p FROM PaymentEntity p WHERE p.id=:id"),
	@NamedQuery(name="PaymentEntity.getPaymentsDataByConnectionNum",query="SELECT p.rdate, p.receiptNo, p.amount,p.advance,p.advance_rebate, p.towards, p.payMode, p.penalty, p.rebate, p.from_mon_year, p.to_mon_year,p.bill_amount,p.balance_amount,p.frecamount,p.rbalance,p.water_charges,p.sw_charges,p.meter_rent,p.miscellaneous_cost,p.bill_adj_amount,p.penalty_adj_amount,p.cancelledremarks,p.old_balance FROM PaymentEntity p WHERE UPPER(p.connectionNo)=:connId ORDER BY p.to_mon_year DESC, p.rdate DESC"),
	@NamedQuery(name="PaymentEntity.getTransactionDataByConnNum",query="SELECT p.rdate, p.receiptNo, p.amount,p.advance,p.advance_rebate, p.towards, p.payMode, p.penalty, p.rebate, p.from_mon_year, p.to_mon_year,p.bill_amount,p.balance_amount,p.frecamount,p.rbalance,p.water_charges,p.sw_charges,p.meter_rent,p.miscellaneous_cost,p.bill_adj_amount,p.penalty_adj_amount,p.cancelledremarks,p.old_balance FROM PaymentEntity p WHERE UPPER(p.connectionNo)=:connId and p.to_mon_year=:to_mon_year ORDER BY p.to_mon_year DESC, p.rdate DESC"),
	@NamedQuery(name="PaymentEntity.getPaymentEntityBasedOnReceiptNo",query="SELECT p FROM PaymentEntity p WHERE p.receiptNo=:receiptNo AND p.recordtype='ORIGINAL'"),

	@NamedQuery(name="PaymentEntity.getPaymentEntityBasedOnReceiptNoCounterNo",query="SELECT p.connectionNo,c.name_eng,p.receiptNo,p.rdate,p.bill_amount,"
			+ "p.amount,p.towards,p.payMode,p.cancelledremarks FROM PaymentEntity p,ConsumerMaster c "
			+ "WHERE UPPER(p.connectionNo)=UPPER(c.connection_no) AND  TO_CHAR(p.rdate,'dd/MM/yyyy')=to_char(to_date(:date, 'dd/MM/yyyy'),'dd/MM/yyyy')  "
			+ "AND p.counterno=:counterno AND p.recordtype='ORIGINAL' AND p.towards='BILL PAYMENT' AND CANCELLEDREMARKS IS NULL"),

	@NamedQuery(name="PaymentEntity.getTodayCashSummery",query="SELECT nvl(count(case when p.payMode=1 then 1 end),0) as cashcount,nvl(count(case when p.payMode!=1 then 1 end),0) as cdcount,"
			+" nvl(sum(case when p.payMode=1 then p.amount end),0) as cashamount,nvl(sum(case when p.payMode!=1 then p.amount end),0) as cdamount"
			+" FROM PaymentEntity p WHERE p.counterno=:counterNo AND TO_CHAR(p.rdate,'dd/MM/yyyy')=TO_CHAR(SYSDATE,'dd/MM/yyyy')"
			+" GROUP BY p.rdate"),
	@NamedQuery(name="PaymentEntity.getdataforDayClose",query="SELECT nvl(count(case when p.payMode=1 then 1 end),0) as cashcount,nvl(count(case when p.payMode!=1 then 1 end),0) as cdcount,"
					+" nvl(sum(case when p.payMode=1 then p.amount end),0) as cashamount,nvl(sum(case when p.payMode!=1 then p.amount end),0) as cdamount"
					+" FROM PaymentEntity p WHERE p.counterno=:counterNo AND TO_CHAR(p.rdate,'dd/MM/yyyy')=TO_CHAR(:rdate,'dd/MM/yyyy') AND p.cancelledremarks IS NULL"),

					
	@NamedQuery(name="PaymentEntity.getMaxDayForDayClose",query="SELECT MAX(c.rdate) FROM PaymentEntity c WHERE c.counterno=:counter_no"),

					
	@NamedQuery(name="PaymentEntity.getGraphicalViewDataForMonth",query="SELECT TO_CHAR(p.rdate,'DD-MM-YYYY') as newDate, SUM(p.amount) FROM PaymentEntity p "
			+ " WHERE p.siteCode=:siteCode and TO_CHAR(p.rdate,'YYYYMM') = (SELECT MAX(CONCAT(TO_CHAR(p1.rdate, 'yyyy'),TO_CHAR(p1.rdate, 'MM'))) from PaymentEntity p1) GROUP BY TO_CHAR(p.rdate,'DD-MM-YYYY') ORDER BY TO_CHAR(p.rdate,'DD-MM-YYYY') ASC"),
	
	@NamedQuery(name="PaymentEntity.getTotalCollection",query="SELECT SUM(NVL(p.amount,0)) FROM PaymentEntity p WHERE p.siteCode=:siteCode AND p.month_year_nep = (select max(b.month_year_nep) from PaymentEntity b WHERE b.month_year_nep IS NOT NULL)"),
	
	
	@NamedQuery(name="PaymentEntity.getMaxPaymentDateByConNo",query="SELECT MAX(p.rdate) FROM PaymentEntity p WHERE p.connectionNo=:connection_no"),
	@NamedQuery(name="PaymentEntity.getDayClosePaymentDetails",query="SELECT SUM(p.amount) FROM PaymentEntity p WHERE p.counterno=:counterNo and to_char(p.rdate,'DD-MM_YYYY')=:rDate and p.recordtype='ORIGINAL'" ),
	@NamedQuery(name="PaymentEntity.getMaxReceiptNo",query="SELECT MAX(p.receiptNo) FROM PaymentEntity p WHERE p.receiptNo LIKE :receiptNo"),
	
	@NamedQuery(name="PaymentEntity.getMaxReceiptNoNew",query="SELECT MAX(p.receiptNo) FROM PaymentEntity p WHERE p.receiptNo LIKE :receiptNo AND p.counterno=:counterno"),
	
	
	@NamedQuery(name="PaymentEntity.viewMyPaymentHistory",query="SELECT p.connectionNo,c.name_eng,c.ward_no,NVL(p.balance_amount,0),NVL(p.water_charges,0),NVL(p.sw_charges,0),"
	+ "NVL(p.meter_rent,0),NVL(p.bill_amount,0),NVL(p.miscellaneous_cost,0),NVL(p.penalty,0),NVL(p.rebate,0),NVL(p.bill_adj_amount,0),NVL(p.penalty_adj_amount,0),NVL(p.amount,0),p.rdate,p.receiptNo"
	+ " FROM PaymentEntity p,ConsumerMaster c WHERE UPPER(c.connection_no)=UPPER(p.connectionNo) AND p.counterno=:counterno and TRUNC(p.rdate)=TO_DATE(:rdate,'dd-MM-YYYY') ORDER BY p.rdate DESC"),
	
	@NamedQuery(name="PaymentEntity.getPaymentEntityBasedOnReceiptNoConNo",query="SELECT c FROM PaymentEntity c WHERE c.receiptNo=:receiptNo AND c.connectionNo=:connectionNo"),
	
	@NamedQuery(name="PaymentEntity.getTotalPaymentsByConNoMYN",query="SELECT COUNT(*) FROM PaymentEntity c WHERE UPPER(c.connectionNo)=:connectionNo AND c.to_mon_year=:to_mon_year AND c.cancelledremarks IS NULL"),
	
	@NamedQuery(name="PaymentEntity.getPaymentEntityDetails",query="SELECT c FROM PaymentEntity c WHERE c.receiptNo=:receiptNo AND TO_CHAR(c.rdate,'dd/MM/yyyy')=TO_CHAR(:rdate,'dd/MM/yyyy') AND c.towards=:towards AND c.remarks IS NULL"),
	
	
})
public class PaymentEntity 
{
 
	@Id
	@SequenceGenerator(name = "bsw_payments_seq", sequenceName = "BSW_PAYMENTS_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_payments_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="CONNECTION_NO")
	private String connectionNo;
	
	@Column(name="RDATE")
	private Date rdate;
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@Column(name="BRANCH_CODE")
	private String branchCode;
	
	@Column(name="CDNO")
	private String cdno;
	
	@Column(name="BANKNAME")
	private String bankname;
	
	@Column(name="CDDATE")
	private Date cddate;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="EDATE")
	private Date edate;
	
	@Column(name="AMOUNT_CURR_TOTAL")
	private Double amountCurrTotal;
	
	@Column(name="PARTICULARS")
	private String particulars;
	
	@Column(name="USER_ID")
	private String user_id;
	
	@Column(name="PAY_MODE")
	private String payMode;
	
	@Column(name="PAYMENT_ID")
	private Integer paymentId;
	
	@Column(name="DEMAND_ID")
	private Integer demandId;
	
	@Column(name="BANK_ID")
	private Integer bankId;
	
	@Column(name="RECEIPT_NO")
	private String receiptNo;
	
	@Column(name="SMS")
	private Integer sms;
	
	@Column(name="TOWARDS")
	private String towards;
		
	@Column(name="BILLNO")
	private String billno;
	
	@Column(name="COUNTERNO")
	private String counterno;
	
	@Column(name="NEPALI_DATE")
	private String nepali_date;
	
	@Column(name="SESSION_ID")
	private String sessionId;
	
	@Column(name="ADDED_BY")
	private String addedBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	@Column(name="SITECODE")
	private String siteCode;
	
	@Column(name="MONTH_YEAR_ENG")
	private Integer monthYearEng;
	
	@Column(name="MONTH_YEAR_NEP")
	private Integer month_year_nep;
	
	@Column(name="PENALTY")
	private Double penalty;
	
	public Integer getMonth_year_nep() {
		return month_year_nep;
	}

	public void setMonth_year_nep(Integer month_year_nep) {
		this.month_year_nep = month_year_nep;
	}

	@Column(name="REBATE")
	private Double rebate;
	
	@Column(name="FRECAMOUNT")
	private Double frecamount;
	
	@Column(name="BALANCE_AMOUNT")
	private Double balance_amount;
	
	@Column(name="TENDER_CASH")
	private Double tender_cash;
	
	@Column(name="CHANGE")
	private Double change;
	
	@Column(name="ADVANCE")
	private Double advance;

	@Column(name="CANCELLEDREMARKS")
	private String cancelledremarks;

	@Column(name="RECORDTYPE")
	private String recordtype;
	
	
	@Column(name="FROM_MON_YEAR")
	private Integer from_mon_year;
	
	@Column(name="TO_MON_YEAR")
	private Integer to_mon_year;
	
	@Column(name="WATER_CHARGES")
	private Double water_charges;
	
	@Column(name="SW_CHARGES")
	private Double sw_charges;
	
	@Column(name="METER_RENT")
	private Double meter_rent;
	
	@Column(name="MISCELLANEOUS_COST")
	private Double miscellaneous_cost;
	
	@Column(name="ADVANCE_REBATE")
	private Double advance_rebate;

	@Column(name="CANCELLED_BY")
	private String cancelled_by;

	@Column(name="CANCELLED_DATE")
	private Date cancelled_date;
	
	@Column(name="OLD_RECEIVED_AMOUNT")
	private Double old_received_amount;
	
	@Column(name="BILL_AMOUNT")
	private Double bill_amount;
	
	@Column(name="OLD_BALANCE")
	private Double old_balance;
	
	@Column(name="RBALANCE")
	private Double rbalance;
	
	@Column(name="WARD_NO")
	private String ward_no;
	
	@Column(name="READING_DAY")
	private Integer reading_day;
	
	@Column(name="PIPE_SIZE")
	private Double pipe_size;
	
	@Column(name="DENOTED_BY")	
	private String denoted_by;
	
	@Column(name="NC_INST")
	private Double nc_inst;
	
	@Column(name="CON_TYPE")
	private String con_type;
	
	@Column(name="CON_CATEGORY")
	private String con_category;
	
	
	//New Connection And Other Payments
	
	public String getDenoted_by() {
		return denoted_by;
	}

	public void setDenoted_by(String denoted_by) {
		this.denoted_by = denoted_by;
	}

	@Column(name="NCTAP")
	private Double nctap;
	
	@Column(name="NCDEPOSIT")
	private Double ncdeposit;
	
	@Column(name="MVALUE")
	private Double mvalue;
	
	@Column(name="TEMPHOLEAMT")
	private Double tempholeamt;
	
	@Column(name="NAMECHANGEAMT")
	private Double nameChangeAmt;
	
	@Column(name="ILG_CON_AMT")
	private Double ilg_con_amt;
	
	
	@Column(name="BILL_ADJ_AMOUNT")
	private Double bill_adj_amount;
	
	@Column(name="PENALTY_ADJ_AMOUNT")
	private Double penalty_adj_amount;
	
	@Column(name="OTHER_PAID_TYPE")
	private String other_paid_type;
	
	@Column(name="OTHER_PAID_AMT")
	private Double other_paid_amt;
	
	@Column(name="REBATE_ADJ_AMT")
	private Double rebate_adj_amt;
	
	
	
	public String getOther_paid_type() {
		return other_paid_type;
	}

	public void setOther_paid_type(String other_paid_type) {
		this.other_paid_type = other_paid_type;
	}

	public Double getOther_paid_amt() {
		return other_paid_amt;
	}

	public void setOther_paid_amt(Double other_paid_amt) {
		this.other_paid_amt = other_paid_amt;
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

	public Double getTempholeamt() {
		return tempholeamt;
	}

	public void setTempholeamt(Double tempholeamt) {
		this.tempholeamt = tempholeamt;
	}

	public Double getNameChangeAmt() {
		return nameChangeAmt;
	}

	public void setNameChangeAmt(Double nameChangeAmt) {
		this.nameChangeAmt = nameChangeAmt;
	}

	public Double getIlg_con_amt() {
		return ilg_con_amt;
	}

	public void setIlg_con_amt(Double ilg_con_amt) {
		this.ilg_con_amt = ilg_con_amt;
	}

	public Double getRbalance() {
		return rbalance;
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

	public Double getPipe_size() {
		return pipe_size;
	}

	public void setPipe_size(Double pipe_size) {
		this.pipe_size = pipe_size;
	}

	public void setRbalance(Double rbalance) {
		this.rbalance = rbalance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getOld_balance() {
		return old_balance;
	}

	public void setOld_balance(Double old_balance) {
		this.old_balance = old_balance;
	}

	public String getConnectionNo() {
		return connectionNo;
	}

	public void setConnectionNo(String connectionNo) {
		this.connectionNo = connectionNo;
	}

	public Date getRdate() {
		return rdate;
	}

	public void setRdate(Date rdate) {
		this.rdate = rdate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCdno() {
		return cdno;
	}

	public void setCdno(String cdno) {
		this.cdno = cdno;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public Date getCddate() {
		return cddate;
	}

	public void setCddate(Date cddate) {
		this.cddate = cddate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	public Double getAmountCurrTotal() {
		return amountCurrTotal;
	}

	public void setAmountCurrTotal(Double amountCurrTotal) {
		this.amountCurrTotal = amountCurrTotal;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public Integer getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}

	public Integer getDemandId() {
		return demandId;
	}

	public void setDemandId(Integer demandId) {
		this.demandId = demandId;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Integer getSms() {
		return sms;
	}

	public void setSms(Integer sms) {
		this.sms = sms;
	}

	public String getTowards() {
		return towards;
	}

	public void setTowards(String towards) {
		this.towards = towards;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getCounterno() {
		return counterno;
	}

	public void setCounterno(String counterno) {
		this.counterno = counterno;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getNepali_date() {
		return nepali_date;
	}

	public void setNepali_date(String nepali_date) {
		this.nepali_date = nepali_date;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public Integer getMonthYearEng() {
		return monthYearEng;
	}

	public void setMonthYearEng(Integer monthYearEng) {
		this.monthYearEng = monthYearEng;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Double getFrecamount() {
		return frecamount;
	}

	public void setFrecamount(Double frecamount) {
		this.frecamount = frecamount;
	}

	public Double getBalance_amount() {
		return balance_amount;
	}

	public void setBalance_amount(Double balance_amount) {
		this.balance_amount = balance_amount;
	}

	public Double getTender_cash() {
		return tender_cash;
	}

	public void setTender_cash(Double tender_cash) {
		this.tender_cash = tender_cash;
	}

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
	}

	public Double getAdvance() {
		return advance;
	}

	public void setAdvance(Double advance) {
		this.advance = advance;
	}

	public String getCancelledremarks() {
		return cancelledremarks;
	}

	public void setCancelledremarks(String cancelledremarks) {
		this.cancelledremarks = cancelledremarks;
	}

	public String getRecordtype() {
		return recordtype;
	}

	public void setRecordtype(String recordtype) {
		this.recordtype = recordtype;
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

	public Double getWater_charges() {
		return water_charges;
	}

	public void setWater_charges(Double water_charges) {
		this.water_charges = water_charges;
	}

	public Double getSw_charges() {
		return sw_charges;
	}

	public void setSw_charges(Double sw_charges) {
		this.sw_charges = sw_charges;
	}

	public Double getMeter_rent() {
		return meter_rent;
	}

	public void setMeter_rent(Double meter_rent) {
		this.meter_rent = meter_rent;
	}

	public Double getMiscellaneous_cost() {
		return miscellaneous_cost;
	}

	public void setMiscellaneous_cost(Double miscellaneous_cost) {
		this.miscellaneous_cost = miscellaneous_cost;
	}

	public Double getAdvance_rebate() {
		return advance_rebate;
	}

	public void setAdvance_rebate(Double advance_rebate) {
		this.advance_rebate = advance_rebate;
	}

	public String getCancelled_by() {
		return cancelled_by;
	}

	public void setCancelled_by(String cancelled_by) {
		this.cancelled_by = cancelled_by;
	}

	public Date getCancelled_date() {
		return cancelled_date;
	}

	public void setCancelled_date(Date cancelled_date) {
		this.cancelled_date = cancelled_date;
	}

	public Double getOld_received_amount() {
		return old_received_amount;
	}

	public void setOld_received_amount(Double old_received_amount) {
		this.old_received_amount = old_received_amount;
	}

	public Double getBill_amount() {
		return bill_amount;
	}

	public void setBill_amount(Double bill_amount) {
		this.bill_amount = bill_amount;
	}

	public Double getNc_inst() {
		return nc_inst;
	}

	public void setNc_inst(Double nc_inst) {
		this.nc_inst = nc_inst;
	}

	public String getCon_type() {
		return con_type;
	}

	public void setCon_type(String con_type) {
		this.con_type = con_type;
	}

	public String getCon_category() {
		return con_category;
	}

	public void setCon_category(String con_category) {
		this.con_category = con_category;
	}

	public Double getRebate_adj_amt() {
		return rebate_adj_amt;
	}

	public void setRebate_adj_amt(Double rebate_adj_amt) {
		this.rebate_adj_amt = rebate_adj_amt;
	}


}

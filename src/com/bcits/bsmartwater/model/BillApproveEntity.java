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
@Table(name = "BSW_APPROVE_BILLCORRECTION")

@NamedQueries({
	
	@NamedQuery(name="BillApproveEntity.setBillApprove",query="UPDATE BillApproveEntity b SET b.bill_app_status= :bill_app_status WHERE b.id= :billId"),
	@NamedQuery(name="BillApproveEntity.findByConnectionNo",query="SELECT bl FROM BillApproveEntity bl WHERE bl.connection_no=:connection_no  AND bl.bill_app_status=0"),//AND bl.monthyear in(SELECT MAX(ble.monthyear) FROM BillingLedgerEntity ble WHERE bl.connection_no=:connection_no)
	@NamedQuery(name="BillApproveEntity.findCountByConNoBillStatus",query="SELECT COUNT(*) FROM BillApproveEntity b WHERE b.connection_no= :connection_no AND b.bill_app_status= :bill_app_status"),
	@NamedQuery(name="BillApproveEntity.getBillCorrectionChangeList",query="SELECT c.connection_no,c.name_eng,c.con_type,c.ward_no,c.remarks,b.billno,NVL(b.arrears,0),NVL(b.water_charges,0),NVL(b.sw_charges,0),NVL(b.mtr_rent,0),NVL(b.net_amount,0),b.bill_app_status,b.added_by,b.updated_by FROM ConsumerMaster c,BillApproveEntity b WHERE c.connection_no=b.connection_no AND b.bill_app_status NOT IN(0)"),
	@NamedQuery(name="BillApproveEntity.pendingCount",query="SELECT COUNT(*) FROM BillApproveEntity b WHERE b.monthyearnep=:monthyearnep AND b.bill_app_status=0"),
	
})
public class BillApproveEntity {

	@Id
	@SequenceGenerator(name = "bsw_approve_bc_seq", sequenceName = "BSW_APPROVE_BC_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_approve_bc_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="CONNECTION_NO")
	private String connection_no;
	
	@Column(name="MR_ID")
	private Integer mr_id;
	
	@Column(name="RDNG_DATE")
	private Date rdng_date;
	
	@Column(name="RDNG_DATE_NEP")
	private String rdng_date_nep;
	
	@Column(name="RDNG_MTH")	
	private Integer rdng_mth;
	
	@Column(name="BILL_PERIOD")	
	private Double bill_period;

	@Column(name="PRESENT_READING")	
	private Double present_reading;
	
	@Column(name="PREVIOUS_READING")	
	private Double previous_reading;
	
	@Column(name="CONSUMPTION")	
	private Double consumption;
	
	@Column(name="WATER_CHARGES")
	private Double water_charges;
	
	@Column(name="SERVICE_CHARGE")	
	private Double service_charge;
	
	@Column(name="ARREARS")	
	private Double arrears;
	
	@Column(name="INTEREST")	
	private Double interest;
	
	@Column(name="LATEFEE")	
	private Double latefee;
	
	@Column(name="NET_AMOUNT")	
	private Double net_amount;
	
	@Column(name="STATUS")	
	private String status;
	
	@Column(name="MCUNITS")	
	private Double mcunits;
	
	@Column(name="VOUCHER_ADJ")	
	private Double voucher_adj;
    
	@Column(name="PENALTY")	
	private Double penalty;

	@Column(name="USER_ID")	
	private String user_id;
	
	@Column(name="PREVIOUS_READ_DATE")	
	private Date previous_read_date;

	@Column(name="MISC_FLAG")	
	private String misc_flag;

	@Column(name="REMARKS")	
	private String remarks;

	@Column(name="BOOK_NO")	
	private String book_no;

	@Column(name="LEAF_NO")
	private Double leaf_no;

	@Column(name="BILLNO")	
	private String billno;

	@Column(name="REBATE")
	private Double rebate;

	@Column(name="TOTALAMT")	
	private Double totalamt;

	@Column(name="SUSPENSE")	
	private Double suspense;

	@Column(name="WARDNO")	
	private String wardno;
	
	@Column(name="SBMNO")	
	private String sbmno;

	@Column(name="SW_CHARGES")	
	private Double sw_charges;
	
	@Column(name="BILL_DATE")	
	private Date bill_date;
	
	@Column(name="BILL_DATE_NEP")	
	private String bill_date_nep;
	
	@Column(name="DUE_DATE")	
	private Date due_date;
	
	@Column(name="BANK_DUE_DATE")	
	private Date bank_due_date;
	
	@Column(name="OTHER")	
	private Double other;
	
	@Column(name="SITECODE")	
	private Integer sitecode;
	
	@Column(name="MC_DATE")	
	private Date mc_date;

	@Column(name="SUNDRY_AMOUNT")	
	private Double sundry_amount;

	@Column(name="AVG_UNITS")	
	private Double avg_units;
	
	@Column(name="DL_COUNT")	
	private Integer dl_count;

	@Column(name="MTH_DL_COUNT")	
	private Integer mth_dl_count;
	
	@Column(name="MTH_DL_UNITS")	
	private Double mth_dl_units;

	@Column(name="DL_UNITS")	
	private Double dl_units;

	@Column(name="ADDED_BY")
	private String added_by;
	
	@Column(name="UPDATED_BY")
	private String updated_by;
	
	@Column(name="DR_AMOUNT")	
	private Double dr_amount;
	
	@Column(name="MC_STATUS")	
	private Integer mc_status;
	
	@Column(name="BILL_APP_STATUS")	
	private Integer bill_app_status;
	
	@Column(name="MONTHYEAR")	
	private Integer monthyear;
	
	@Column(name="MONTHYEARNEP")	
	private String monthyearnep;
	
	@Column(name="MTR_RENT")	
	private Double mtr_rent;
	
	@Column(name="OPEN_BALANCE")	
	private Double open_balance;
	
	@Column(name="EXCESS_CHARGES")	
	private Double excess_charges;
	
	@Column(name="ADDITIONAL_CHARGES")	
	private Double additional_charges;
	
	@Column(name="MINIMUM_CHARGES")	
	private Double minimum_charges;
	
	@Column(name="DUE_DATE_NEP")	
	private String due_date_nep;

	@Column(name="PREVIOUS_MC_STATUS")
	private Integer previous_mc_status;
	
	@Column(name="PREVIOUS_WATER_CHARGE")
	private Double previous_water_charge;
	
	@Column(name="PREVIOUS_SW_CHARGE")
	private Double previous_sw_charge;
	
	@Column(name="PREVIOUS_MTR_RENT")
	private Double previous_mtr_rent;
	
	@Column(name="PREVIOUS_ARREARS")
	private Double previous_arrears;
	
	@Column(name="PREVIOUS_NET_AMT")
	private Double previous_net_amt;
	
	
	
	
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



	public Integer getMr_id() {
		return mr_id;
	}

	public void setMr_id(Integer mr_id) {
		this.mr_id = mr_id;
	}

	public Date getRdng_date() {
		return rdng_date;
	}

	public void setRdng_date(Date rdng_date) {
		this.rdng_date = rdng_date;
	}

	public String getRdng_date_nep() {
		return rdng_date_nep;
	}

	public void setRdng_date_nep(String rdng_date_nep) {
		this.rdng_date_nep = rdng_date_nep;
	}

	public Integer getRdng_mth() {
		return rdng_mth;
	}

	public void setRdng_mth(Integer rdng_mth) {
		this.rdng_mth = rdng_mth;
	}

	public Double getBill_period() {
		return bill_period;
	}

	public void setBill_period(Double bill_period) {
		this.bill_period = bill_period;
	}

	public Double getPresent_reading() {
		return present_reading;
	}

	public void setPresent_reading(Double present_reading) {
		this.present_reading = present_reading;
	}

	public Double getPrevious_reading() {
		return previous_reading;
	}

	public void setPrevious_reading(Double previous_reading) {
		this.previous_reading = previous_reading;
	}

	public Double getConsumption() {
		return consumption;
	}

	public void setConsumption(Double consumption) {
		this.consumption = consumption;
	}

	public Double getWater_charges() {
		return water_charges;
	}

	public void setWater_charges(Double water_charges) {
		this.water_charges = water_charges;
	}

	public Double getService_charge() {
		return service_charge;
	}

	public void setService_charge(Double service_charge) {
		this.service_charge = service_charge;
	}

	public Double getArrears() {
		return arrears;
	}

	public void setArrears(Double arrears) {
		this.arrears = arrears;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getLatefee() {
		return latefee;
	}

	public void setLatefee(Double latefee) {
		this.latefee = latefee;
	}

	public Double getNet_amount() {
		return net_amount;
	}

	public void setNet_amount(Double net_amount) {
		this.net_amount = net_amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getMcunits() {
		return mcunits;
	}

	public void setMcunits(Double mcunits) {
		this.mcunits = mcunits;
	}

	

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public Date getPrevious_read_date() {
		return previous_read_date;
	}

	public void setPrevious_read_date(Date previous_read_date) {
		this.previous_read_date = previous_read_date;
	}

	

	public String getMisc_flag() {
		return misc_flag;
	}

	public void setMisc_flag(String misc_flag) {
		this.misc_flag = misc_flag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBook_no() {
		return book_no;
	}

	public void setBook_no(String book_no) {
		this.book_no = book_no;
	}

	public Double getLeaf_no() {
		return leaf_no;
	}

	public void setLeaf_no(Double leaf_no) {
		this.leaf_no = leaf_no;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}

	public Double getTotalamt() {
		return totalamt;
	}

	public void setTotalamt(Double totalamt) {
		this.totalamt = totalamt;
	}

	public Double getSuspense() {
		return suspense;
	}

	public void setSuspense(Double suspense) {
		this.suspense = suspense;
	}

	public String getWardno() {
		return wardno;
	}

	public void setWardno(String wardno) {
		this.wardno = wardno;
	}

	public String getSbmno() {
		return sbmno;
	}

	public void setSbmno(String sbmno) {
		this.sbmno = sbmno;
	}

	public Double getSw_charges() {
		return sw_charges;
	}

	public void setSw_charges(Double sw_charges) {
		this.sw_charges = sw_charges;
	}

	public Date getBill_date() {
		return bill_date;
	}

	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}

	public String getBill_date_nep() {
		return bill_date_nep;
	}

	public void setBill_date_nep(String bill_date_nep) {
		this.bill_date_nep = bill_date_nep;
	}

	public Date getDue_date() {
		return due_date;
	}

	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}

	public Date getBank_due_date() {
		return bank_due_date;
	}

	public void setBank_due_date(Date bank_due_date) {
		this.bank_due_date = bank_due_date;
	}

	public Double getOther() {
		return other;
	}

	public void setOther(Double other) {
		this.other = other;
	}

	public Integer getSitecode() {
		return sitecode;
	}

	public void setSitecode(Integer sitecode) {
		this.sitecode = sitecode;
	}

	public Date getMc_date() {
		return mc_date;
	}

	public void setMc_date(Date mc_date) {
		this.mc_date = mc_date;
	}

	public Double getSundry_amount() {
		return sundry_amount;
	}

	public void setSundry_amount(Double sundry_amount) {
		this.sundry_amount = sundry_amount;
	}

	public Double getAvg_units() {
		return avg_units;
	}

	public void setAvg_units(Double avg_units) {
		this.avg_units = avg_units;
	}

	public Integer getDl_count() {
		return dl_count;
	}

	public void setDl_count(Integer dl_count) {
		this.dl_count = dl_count;
	}

	public Integer getMth_dl_count() {
		return mth_dl_count;
	}

	public void setMth_dl_count(Integer mth_dl_count) {
		this.mth_dl_count = mth_dl_count;
	}

	public Double getMth_dl_units() {
		return mth_dl_units;
	}

	public void setMth_dl_units(Double mth_dl_units) {
		this.mth_dl_units = mth_dl_units;
	}

	public Double getDl_units() {
		return dl_units;
	}

	public void setDl_units(Double dl_units) {
		this.dl_units = dl_units;
	}

	public String getAdded_by() {
		return added_by;
	}

	public void setAdded_by(String added_by) {
		this.added_by = added_by;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Double getDr_amount() {
		return dr_amount;
	}

	public void setDr_amount(Double dr_amount) {
		this.dr_amount = dr_amount;
	}

	
	public Integer getMc_status() {
		return mc_status;
	}

	public void setMc_status(Integer mc_status) {
		this.mc_status = mc_status;
	}

	public Integer getBill_app_status() {
		return bill_app_status;
	}

	public void setBill_app_status(Integer bill_app_status) {
		this.bill_app_status = bill_app_status;
	}

	public Integer getMonthyear() {
		return monthyear;
	}

	public void setMonthyear(Integer monthyear) {
		this.monthyear = monthyear;
	}

	public Double getMtr_rent() {
		return mtr_rent;
	}

	public void setMtr_rent(Double mtr_rent) {
		this.mtr_rent = mtr_rent;
	}

	public Double getOpen_balance() {
		return open_balance;
	}

	public void setOpen_balance(Double open_balance) {
		this.open_balance = open_balance;
	}

	public Double getExcess_charges() {
		return excess_charges;
	}

	public void setExcess_charges(Double excess_charges) {
		this.excess_charges = excess_charges;
	}

	public Double getAdditional_charges() {
		return additional_charges;
	}

	public void setAdditional_charges(Double additional_charges) {
		this.additional_charges = additional_charges;
	}

	public Double getMinimum_charges() {
		return minimum_charges;
	}

	public void setMinimum_charges(Double minimum_charges) {
		this.minimum_charges = minimum_charges;
	}

	public String getDue_date_nep() {
		return due_date_nep;
	}

	public void setDue_date_nep(String due_date_nep) {
		this.due_date_nep = due_date_nep;
	}

	public Double getVoucher_adj() {
		return voucher_adj;
	}

	public void setVoucher_adj(Double voucher_adj) {
		this.voucher_adj = voucher_adj;
	}

	public String getMonthyearnep() {
		return monthyearnep;
	}

	public void setMonthyearnep(String monthyearnep) {
		this.monthyearnep = monthyearnep;
	}

	public Integer getPrevious_mc_status() {
		return previous_mc_status;
	}

	public void setPrevious_mc_status(Integer previous_mc_status) {
		this.previous_mc_status = previous_mc_status;
	}

	public Double getPrevious_water_charge() {
		return previous_water_charge;
	}

	public void setPrevious_water_charge(Double previous_water_charge) {
		this.previous_water_charge = previous_water_charge;
	}

	public Double getPrevious_sw_charge() {
		return previous_sw_charge;
	}

	public void setPrevious_sw_charge(Double previous_sw_charge) {
		this.previous_sw_charge = previous_sw_charge;
	}

	public Double getPrevious_mtr_rent() {
		return previous_mtr_rent;
	}

	public void setPrevious_mtr_rent(Double previous_mtr_rent) {
		this.previous_mtr_rent = previous_mtr_rent;
	}

	public Double getPrevious_arrears() {
		return previous_arrears;
	}

	public void setPrevious_arrears(Double previous_arrears) {
		this.previous_arrears = previous_arrears;
	}

	public Double getPrevious_net_amt() {
		return previous_net_amt;
	}

	public void setPrevious_net_amt(Double previous_net_amt) {
		this.previous_net_amt = previous_net_amt;
	}

	
	
	
}

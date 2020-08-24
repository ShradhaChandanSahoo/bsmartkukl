package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name = "DAILYCASHCOUNTER")
@NamedQueries({
	@NamedQuery(name="DailyCashCounterReport.categoryWiseSalesReportReadData",query="SELECT l.con_type,l.con_category,SUM(l.water_charges)as water_charges,SUM(l.sw_charges)as sw_charges,SUM(l.mtr_rent)as mtr_rent,SUM(l.arrears)as arrears,"
			 + "SUM(l.net_amount)as net_amount,nvl(SUM(l.dr_amount),0)as dr_amount from BillingLedgerEntity  l,ConsumerMaster m WHERE  m.connection_no=l.connection_no AND l.monthyearnep=:monthyearnep AND l.con_type is not NULL  GROUP BY l.con_type,l.con_category ORDER BY l.con_type,l.con_category"),
	@NamedQuery(name="DailyCashCounterReport.wardWiseSalesReportReadData",query="SELECT l.wardno,l.con_category,nvl(SUM(l.water_charges),0) AS water_charges, "
					+ " nvl(SUM(l.sw_charges),0)AS sw_charges,nvl(SUM(l.mtr_rent),0) AS mtr_rent,nvl(SUM(l.arrears),0) as arrears,nvl(SUM(l.net_amount),0) as net_amount,l.denoted_by,nvl(SUM(l.dr_amount),0)as dr_amount "
					+ " from BillingLedgerEntity l,ConsumerMaster m WHERE m.connection_no=l.connection_no "
					+ " AND (l.monthyearnep BETWEEN :fromdate AND :todate) "
					+ " GROUP BY l.wardno,l.con_category,l.denoted_by ORDER BY l.wardno,l.con_category ASC"),
    @NamedQuery(name="DailyCashCounterReport.monthlyObservationReportReadData",query="SELECT DISTINCT(o.observationName),l.wardno,COUNT(*) "
					+ "FROM ObservationEntity o,BillingLedgerEntity l,ConsumerMaster m "
					+ "WHERE o.id=l.mc_status and m.connection_no=l.connection_no "
					+ "and m.pipe_size>=0.5 and (l.monthyearnep BETWEEN :fromdate AND :todate) "  
					+ "GROUP BY l.wardno,o.observationName"),
			/*@NamedQuery(name="DailyCashCounterReport.monthlySalesSummaryReportReadData",query="SELECT A.wardno,A.pipe_size,A.openingBalance,A.WATER_CHARGES1,A.SW_CHARGES1,A.MTR_RENT1,A.TotalBill,A.NET_AMOUNT1,B.misc1,B.penalty1,B.rebate1,B.ramount1 FROM "
					+ "(SELECT l.wardno,m.pipe_size,nvl(SUM(l.arrears),0) AS openingBalance,nvl(SUM(l.water_charges),0) AS WATER_CHARGES1,nvl(SUM(l.sw_charges),0) AS SW_CHARGES1,nvl(SUM(l.mtr_rent),0) AS MTR_RENT1, nvl(SUM(l.water_charges),0)+nvl(SUM(l.sw_charges),0)+nvl(SUM(l.mtr_rent),0) as TotalBill, "
					+ "nvl(SUM(l.net_amount),0) AS NET_AMOUNT1 from BillingLedgerEntity l,ConsumerMaster m WHERE m.connection_no=l.connection_no AND l.monthyearnep=207309"
					+ " GROUP BY l.wardno,m.pipe_size)A,"
					+ "(SELECT m.ward_no,m.pipe_size,nvl(SUM(m.miscellaneous_cost),0) AS misc1,nvl(SUM(m.penalty),0) AS penalty1,nvl(SUM(m.rebate),0) AS REBATE1,nvl(SUM(m.amount),0) AS ramount1 from BillingLedgerEntity l,PaymentEntity m "
					+ "WHERE m.connectionNo=l.connection_no and l.monthyearnep=207309 AND TRUNC(m.RDATE) BETWEEN to_date('2016-12-01','YYYY-MM-DD') and to_date('2017-01-30','YYYY-MM-DD') "
					+ "GROUP BY m.ward_no,m.pipe_size)B WHERE A.wardno=B.ward_no AND A.pipe_size=B.pipe_size"),		*/
		
					
})
public class DailyCashCounterReport {
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="COUNTERNO")
	private long counterno;
	 
	@Column(name="RDATE")
	private String rdate;
	
	@Column(name="TOTAL_AMOUNT")
	private long total_amount;
	
	@Column(name="TOTAL_RECEIPTS")
	private long total_receipts;
	
	@Column(name="CASH")
	private long cash;
	
	@Column(name="CHEQUE")
	private long cheque;
	
	@Column(name="DD")
	private long dd;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCounterno() {
		return counterno;
	}

	public void setCounterno(long counterno) {
		this.counterno = counterno;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public long getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(long total_amount) {
		this.total_amount = total_amount;
	}

	public long getTotal_receipts() {
		return total_receipts;
	}

	public void setTotal_receipts(long total_receipts) {
		this.total_receipts = total_receipts;
	}

	public long getCash() {
		return cash;
	}

	public void setCash(long cash) {
		this.cash = cash;
	}

	public long getCheque() {
		return cheque;
	}

	public void setCheque(long cheque) {
		this.cheque = cheque;
	}

	public long getDd() {
		return dd;
	}

	public void setDd(long dd) {
		this.dd = dd;
	}
	
}

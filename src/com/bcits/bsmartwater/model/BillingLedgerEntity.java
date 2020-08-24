package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "BSW_LEDGER")

@NamedQueries({
	
	@NamedQuery(name="BillingLedgerEntity.findByConnectionNo",query="SELECT bl FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep in(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no)"),
	@NamedQuery(name="BillingLedgerEntity.getArrearsByConNoBM",query="SELECT nvl(bl.arrears,0) FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep in(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no)"),
	@NamedQuery(name="BillingLedgerEntity.getBillByConNoAndMY",query="SELECT bl FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.monthyear=:monthyear AND bl.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where UPPER(ble.connection_no)=:connection_no AND ble.monthyearnep is NOT NULL)"),
	@NamedQuery(name="BillingLedgerEntity.getBillByConNoAndMY1",query="SELECT bl FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep=:monthyearnep"),
	@NamedQuery(name="BillingLedgerEntity.ledgerCountByWardNo",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE b.connection_no=c.connection_no AND c.con_type='Unmetered' AND UPPER(b.wardno)=:wardno AND b.monthyearnep=:monthyearnep"),
	@NamedQuery(name="BillingLedgerEntity.ledgerCountByConCategory",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE b.connection_no=c.connection_no AND UPPER(b.con_category)=:con_category AND b.monthyearnep=:monthyearnep"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerCountByWardNo",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE b.connection_no=c.connection_no AND c.con_type='Unmetered' AND UPPER(b.wardno)=:wardno  AND b.monthyearnep=:monthyearnep AND b.billno IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerCountByConCategory",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE b.connection_no=c.connection_no AND UPPER(b.con_category)=:con_category  AND b.monthyearnep=:monthyearnep AND b.billno IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.unbilledLedgerCountByWardNo",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE b.connection_no=c.connection_no AND c.con_type='Unmetered' AND UPPER(b.wardno)=:wardno  AND b.monthyearnep=:monthyearnep AND b.billno IS NULL"),
	@NamedQuery(name="BillingLedgerEntity.unbilledLedgerCountByConCategory",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE b.connection_no=c.connection_no AND UPPER(b.con_category)=:con_category  AND b.monthyearnep=:monthyearnep AND b.billno IS NULL"),
	@NamedQuery(name="BillingLedgerEntity.getReadingEntryUnbilled",query="SELECT b.id,b.connection_no,b.previous_reading,b.present_reading,b.wardno,b.rdng_date,b.due_date,b.mc_status,b.due_date_nep,b.rdng_date_nep,c.con_type FROM BillingLedgerEntity b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND UPPER(b.wardno)=:wardno AND b.monthyear IN(SELECT MAX(ble.monthyear) FROM BillingLedgerEntity ble) AND b.billno IS NULL",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.updatePresReadAndMCStatusByBillId",query="UPDATE BillingLedgerEntity b SET b.present_reading= :present_reading,b.mc_status= :mc_status WHERE b.id= :billid"),
	@NamedQuery(name="BillingLedgerEntity.updateMonthEndProcess",query="SELECT bl FROM BillingLedgerEntity bl WHERE bl.monthyear=:monthyear"),
	@NamedQuery(name="BillingLedgerEntity.getMaxMonthYear",query="SELECT MAX(bl.monthyear) FROM BillingLedgerEntity bl"),
	@NamedQuery(name="BillingLedgerEntity.monthEndValid",query="SELECT COUNT(*) FROM BillingLedgerEntity bl WHERE bl.monthyear=:monthyear"),
	@NamedQuery(name="BillingLedgerEntity.getBillListByMYAndWardNo",query="SELECT bl FROM BillingLedgerEntity bl WHERE bl.monthyear=:monthyear AND UPPER(bl.wardno)=:wardno AND bl.billno IS NULL"),
	@NamedQuery(name="BillingLedgerEntity.getMaxBillNoBysiteCode",query="SELECT MAX(bl.billno) FROM BillingLedgerEntity bl WHERE bl.billno LIKE :billno"),
	@NamedQuery(name="BillingLedgerEntity.BillingLedgerBasedOnConnectionNo",query="SELECT l FROM BillingLedgerEntity l WHERE UPPER(l.connection_no)=:connection_no AND l.monthyearnep in(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no AND ble.billno IS NOT NULL)",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.getLastMonthRecord",query="SELECT bl FROM BillingLedgerEntity bl WHERE bl.connection_no=:connection_no AND bl.monthyear IN(SELECT MAX(ble.monthyear) FROM BillingLedgerEntity ble WHERE ble.monthyear NOT IN(SELECT MAX(ble.monthyear) FROM BillingLedgerEntity ble) AND ble.connection_no=:connection_no)",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.getLedgerDataByConnectionNum",query="SELECT l FROM BillingLedgerEntity l WHERE UPPER(l.connection_no)=:connId ORDER BY l.monthyearnep DESC "),
	@NamedQuery(name="BillingLedgerEntity.getWardWiseBillCount",query="SELECT count(bl),nvl(count(case when bl.billno IS NOT NULL then 1 end),0) as billed,"
			+" nvl(sum(case when bl.billno IS NULL then 1 end),0) as unbilled,bl.wardno,bl.reading_day"
			+" FROM BillingLedgerEntity bl,ConsumerMaster c WHERE bl.connection_no=c.connection_no AND c.reading_day is not null and c.con_type='Metered'  AND bl.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where ble.monthyearnep is NOT NULL)"
			+" GROUP BY bl.wardno,bl.reading_day ORDER BY bl.wardno,bl.reading_day"),
	@NamedQuery(name="BillingLedgerEntity.getWardWiseBillCountUnMetered",query="SELECT count(bl),nvl(count(case when bl.billno IS NOT NULL then 1 end),0) as billed,"
			+" nvl(sum(case when bl.billno IS NULL then 1 end),0) as unbilled,bl.wardno"
			+" FROM BillingLedgerEntity bl,ConsumerMaster c WHERE bl.connection_no=c.connection_no and c.con_type='Unmetered'  AND bl.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where ble.monthyearnep is NOT NULL)"
			+" GROUP BY bl.wardno ORDER BY bl.wardno"),
	@NamedQuery(name="BillingLedgerEntity.getConnectionHistoryUnbilled",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,c.mobile_no,c.pipe_size,c.con_category,c.con_type,c.area_no,c.reading_day FROM ConsumerMaster c,BillingLedgerEntity b WHERE UPPER(b.wardno)=:wardno AND c.connection_no=b.connection_no AND UPPER(c.con_type)='METERED' AND b.reading_day=:reading_day AND b.billno IS NULL AND b.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where ble.monthyearnep is not null)"),
	@NamedQuery(name="BillingLedgerEntity.getConnectionHistorybilled",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,c.mobile_no,c.pipe_size,c.con_category,c.con_type,c.area_no,c.reading_day FROM ConsumerMaster c,BillingLedgerEntity b WHERE UPPER(b.wardno)=:wardno AND c.connection_no=b.connection_no AND b.reading_day=:reading_day AND UPPER(c.con_type)='METERED' AND b.billno IS NOT NULL AND b.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where ble.monthyearnep is not null)"),
	@NamedQuery(name="BillingLedgerEntity.getConnectionHistoryUnMUnUnbilled",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,c.mobile_no,c.pipe_size,c.con_category,c.con_type,c.area_no,c.reading_day FROM ConsumerMaster c,BillingLedgerEntity b WHERE UPPER(b.wardno)=:wardno AND c.connection_no=b.connection_no AND UPPER(c.con_type)='UNMETERED' AND b.billno IS NULL AND b.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where ble.monthyearnep is not null)"),
	@NamedQuery(name="BillingLedgerEntity.getConnectionHistoryUnMbilled",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,c.mobile_no,c.pipe_size,c.con_category,c.con_type,c.area_no,c.reading_day FROM ConsumerMaster c,BillingLedgerEntity b WHERE UPPER(b.wardno)=:wardno AND c.connection_no=b.connection_no AND UPPER(c.con_type)='UNMETERED' AND b.billno IS NOT NULL AND b.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where ble.monthyearnep is not null)"),
	@NamedQuery(name="BillingLedgerEntity.checkConnect_noInLedger", query="SELECT COUNT(*) FROM BillingLedgerEntity bl WHERE bl.connection_no=:connection_no" ),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerByWardNoSA",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,b.billno,b.previous_reading,b.present_reading,b.consumption,b.minimum_charges,b.additional_charges,b.water_charges,b.sw_charges,b.mtr_rent,b.penalty,b.rebate,b.arrears,b.net_amount,b.rdng_date_nep,b.due_date_nep,c.pipe_size,b.bill_period,c.con_type,c.con_category,b.rdng_date,b.previous_read_date,c.area_no,b.mr_id,c.address_nep,b.mc_status,b.monthyearnep,c.road_street_eng,nvl(c.balance,0) FROM BillingLedgerEntity b,ConsumerMaster c WHERE  c.connection_no=b.connection_no AND UPPER(c.con_satatus) NOT IN ('PERMANENT','TEMPORARY') AND UPPER(b.wardno)=:wardno AND b.monthyearnep LIKE :reading_month AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size AND b.billno IS NOT NULL ORDER BY c.area_no ASC"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerByWardNoSACC",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,b.billno,b.previous_reading,b.present_reading,b.consumption,b.minimum_charges,b.additional_charges,b.water_charges,b.sw_charges,b.mtr_rent,b.penalty,b.rebate,b.arrears,b.net_amount,b.rdng_date_nep,b.due_date_nep,c.pipe_size,b.bill_period,c.con_type,c.con_category,b.rdng_date,b.previous_read_date,c.area_no,b.mr_id,c.address_nep,b.mc_status,b.monthyearnep,c.road_street_eng,nvl(c.balance,0) FROM BillingLedgerEntity b,ConsumerMaster c WHERE  c.connection_no=b.connection_no AND UPPER(c.con_satatus) NOT IN ('PERMANENT','TEMPORARY') AND UPPER(b.wardno)=:wardno AND b.monthyearnep LIKE :reading_month AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size AND c.con_category=:con_category AND b.billno IS NOT NULL ORDER BY c.area_no ASC"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerByWardNoTHA",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,b.billno,b.previous_reading,b.present_reading,b.consumption,b.minimum_charges,b.additional_charges,b.water_charges,b.sw_charges,b.mtr_rent,b.penalty,b.rebate,b.arrears,b.net_amount,b.rdng_date_nep,b.due_date_nep,c.pipe_size,b.bill_period,c.con_type,c.con_category,b.rdng_date,b.previous_read_date,c.area_no,b.mr_id,c.address_nep,b.mc_status,b.monthyearnep,c.road_street_eng,nvl(c.balance,0) FROM BillingLedgerEntity b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND UPPER(c.con_satatus) NOT IN ('PERMANENT','TEMPORARY') AND UPPER(b.wardno)=:wardno AND b.monthyearnep LIKE :reading_month AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size AND b.billno IS NOT NULL ORDER BY c.area_no ASC"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerByWardNoTHACC",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,b.billno,b.previous_reading,b.present_reading,b.consumption,b.minimum_charges,b.additional_charges,b.water_charges,b.sw_charges,b.mtr_rent,b.penalty,b.rebate,b.arrears,b.net_amount,b.rdng_date_nep,b.due_date_nep,c.pipe_size,b.bill_period,c.con_type,c.con_category,b.rdng_date,b.previous_read_date,c.area_no,b.mr_id,c.address_nep,b.mc_status,b.monthyearnep,c.road_street_eng,nvl(c.balance,0) FROM BillingLedgerEntity b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND UPPER(c.con_satatus) NOT IN ('PERMANENT','TEMPORARY') AND UPPER(b.wardno)=:wardno AND b.monthyearnep LIKE :reading_month AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size AND c.con_category=:con_category AND b.billno IS NOT NULL ORDER BY c.area_no ASC"),
	@NamedQuery(name="BillingLedgerEntity.getTotalBillingCount",query="SELECT COUNT(*) FROM BillingLedgerEntity bl where bl.sitecode=:siteCode AND bl.billno IS NOT NULL and bl.monthyearnep = (select max(b.monthyearnep) from BillingLedgerEntity b WHERE b.monthyearnep IS NOT NULL)"),
	@NamedQuery(name="BillingLedgerEntity.getTotalDemand",query="SELECT sum(bl.net_amount) FROM BillingLedgerEntity bl where bl.sitecode=:siteCode and bl.monthyearnep = (select max(b.monthyearnep) from BillingLedgerEntity b WHERE b.monthyearnep IS NOT NULL)"),
	@NamedQuery(name="BillingLedgerEntity.getLatestMonthYear",query="SELECT MAX(b.monthyearnep) FROM BillingLedgerEntity b where b.monthyearnep IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerByConnectionNoCM",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,c.area_no,c.con_type,c.pipe_size,b.billno,b.previous_reading,b.present_reading,b.minimum_charges,b.additional_charges,b.water_charges,b.sw_charges,b.mtr_rent,b.excess_charges,b.arrears,b.net_amount,b.rdng_date_nep,b.due_date_nep,b.previous_read_date,b.rdng_date,b.monthyear,c.address_nep,b.mr_id,b.monthyearnep,b.mc_status,c.road_street_eng,nvl(c.balance,0),c.customer_id FROM BillingLedgerEntity b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND b.connection_no=:connection_no AND b.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where ble.connection_no=:connection_no) AND b.billno IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.getMinReadDateByConNo",query="SELECT MIN(b.rdng_date) FROM BillingLedgerEntity b WHERE b.connection_no=:connection_no AND b.rdng_date IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.viewBillLedgertHistory",query="SELECT b.connection_no,b.billno,b.water_charges,b.sw_charges,b.arrears,b.mtr_rent,b.net_amount,b.monthyearnep,b.close_balance,b.receipt_no,b.receipt_date,b.last_paid_amount,b.penalty,b.rebate,b.previous_reading,b.present_reading,b.consumption,b.pipe_size,b.bill_adj_amount,b.penalty_adj_amount,b.other,b.service_charge, b.totalamt,b.latefee FROM BillingLedgerEntity b WHERE UPPER(b.connection_no)=:connection_no ORDER BY b.monthyearnep DESC"),
	//@NamedQuery(name="BillingLedgerEntity.findBillsByReceiptNull",query="SELECT b FROM BillingLedgerEntity b WHERE UPPER(b.connection_no)=:connection_no AND b.receipt_no IS NULL AND b.billno IS NOT NULL AND b.monthyearnep IS NOT NULL ORDER BY b.monthyearnep DESC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.findBillsByReceiptNull",query="SELECT b.water_charges,b.sw_charges,b.mtr_rent,b.net_amount,b.monthyearnep FROM BillingLedgerEntity b WHERE UPPER(b.connection_no)=:connection_no AND b.billno IS NOT NULL AND TO_NUMBER(b.monthyearnep)> (SELECT NVL(max(bl.monthyearnep),0) FROM BillingLedgerEntity bl WHERE bl.receipt_no IS NOT NULL AND UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep IS NOT NULL) ORDER BY b.monthyearnep DESC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.findBillsByReceiptNullAll",query="SELECT b FROM BillingLedgerEntity b WHERE UPPER(b.connection_no)=:connection_no AND b.receipt_no IS NULL AND b.billno IS NOT NULL AND b.monthyearnep IS NOT NULL ORDER BY b.monthyearnep DESC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	//Payment By Month Based
	@NamedQuery(name="BillingLedgerEntity.findBillsByReceiptNullByMonthYear",query="SELECT b.water_charges,b.sw_charges,b.mtr_rent,b.net_amount,b.monthyearnep,b.arrears FROM BillingLedgerEntity b WHERE UPPER(b.connection_no)=:connection_no AND b.billno IS NOT NULL AND TO_NUMBER(b.monthyearnep)>=:yearmntfr AND TO_NUMBER(b.monthyearnep)<=:yearmntto ORDER BY b.monthyearnep DESC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.findBillsByReceiptNullAllMYN",query="SELECT b FROM BillingLedgerEntity b WHERE UPPER(b.connection_no)=:connection_no AND b.receipt_no IS NULL AND b.billno IS NOT NULL AND b.monthyearnep IS NOT NULL AND TO_NUMBER(b.monthyearnep)<=:monthyearnep ORDER BY b.monthyearnep DESC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	//@NamedQuery(name="BillingLedgerEntity.findBillsByReceiptNull",query="SELECT b FROM BillingLedgerEntity b WHERE UPPER(b.connection_no)=:connection_no AND b.billno IS NOT NULL AND TO_NUMBER(b.monthyearnep)> (SELECT NVL(max(bl.monthyearnep),0) FROM BillingLedgerEntity bl WHERE bl.receipt_no IS NOT NULL AND UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep IS NOT NULL) ORDER BY b.monthyearnep DESC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.findMaxRecordNotNullReceipt",query="SELECT b FROM BillingLedgerEntity b WHERE b.connection_no=:connection_no AND b.monthyear in(SELECT MAX(ble.monthyear) FROM BillingLedgerEntity ble WHERE ble.connection_no=:connection_no AND ble.receipt_no IS NOT NULL AND ble.billno IS NOT NULL)"),
	@NamedQuery(name="BillingLedgerEntity.getAvgConsumption",query="SELECT NVL(b.consumption,0) FROM BillingLedgerEntity b WHERE b.connection_no=:connection_no AND b.monthyear IS NOT NULL)"),
	@NamedQuery(name="BillingLedgerEntity.ledgerCountByWardNoRdayPSSA",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size=:pipe_size AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND b.monthyearnep=:monthyearnep"),
	@NamedQuery(name="BillingLedgerEntity.ledgerCountByWardNoRdayPSSACC",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size=:pipe_size AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND b.monthyearnep=:monthyearnep AND c.con_category=:con_category"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerCountByWardNoRdayPSSA",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND b.monthyearnep=:monthyearnep AND b.billno IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerCountByWardNoRdayPSSACC",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND c.con_category=:con_category AND b.monthyearnep=:monthyearnep AND b.billno IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.unbilledLedgerCountByWardNoRdayPSSA",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND b.monthyearnep=:monthyearnep AND b.billno IS NULL"),
	@NamedQuery(name="BillingLedgerEntity.unbilledLedgerCountByWardNoRdayPSSACC",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND c.con_category=:con_category AND b.monthyearnep=:monthyearnep AND b.billno IS NULL"),
	@NamedQuery(name="BillingLedgerEntity.ledgerCountByWardNoRdayPSTHA",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size>=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND b.monthyearnep=:monthyearnep"),
	@NamedQuery(name="BillingLedgerEntity.ledgerCountByWardNoRdayPSTHACC",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size>=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND b.monthyearnep=:monthyearnep AND c.con_category=:con_category"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerCountByWardNoRdayPSTHA",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size>=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND b.monthyearnep=:monthyearnep AND b.billno IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.billedLedgerCountByWardNoRdayPSTHACC",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND b.reading_day=:reading_day AND b.pipe_size>=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND c.con_category=:con_category AND b.monthyearnep=:monthyearnep AND b.billno IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.unbilledLedgerCountByWardNoRdayPSTHA",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND b.monthyearnep=:monthyearnep AND b.billno IS NULL"),
	@NamedQuery(name="BillingLedgerEntity.unbilledLedgerCountByWardNoRdayPSTHACC",query="SELECT COUNT(*) FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.wardno)=:wardno AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size  AND c.ward_no=b.wardno AND b.connection_no=c.connection_no AND c.con_category=:con_category AND b.monthyearnep=:monthyearnep AND b.billno IS NULL"),
	@NamedQuery(name="BillingLedgerEntity.getReadingEntryUnbilledSA",query="SELECT b.id,b.connection_no,b.previous_reading,b.present_reading,b.wardno,b.mc_status,b.due_date_nep,b.rdng_date_nep,c.con_type,b.arrears,c.pipe_size,c.area_no FROM BillingLedgerEntity b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND UPPER(b.wardno)=:wardno AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size AND b.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where ble.monthyearnep IS NOT NULL) AND b.billno IS NULL ORDER BY c.area_no ASC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.getReadingEntryUnbilledTHA",query="SELECT b.id,b.connection_no,b.previous_reading,b.present_reading,b.wardno,b.mc_status,b.due_date_nep,b.rdng_date_nep,c.con_type,b.arrears,c.pipe_size,c.area_no FROM BillingLedgerEntity b,ConsumerMaster c WHERE c.connection_no=b.connection_no AND UPPER(b.wardno)=:wardno AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size AND b.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble where ble.monthyearnep IS NOT NULL) AND b.billno IS NULL ORDER BY c.area_no ASC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.getListByConNoAndRECNo",query="SELECT b FROM BillingLedgerEntity b WHERE UPPER(b.connection_no)=:connection_no AND UPPER(b.receipt_no)=:receipt_no ORDER BY b.monthyearnep DESC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.viewBillLedgertHistoryForReading",query="SELECT b.connection_no,b.billno,b.water_charges,b.sw_charges,b.arrears,b.mtr_rent,b.net_amount,b.monthyearnep,b.close_balance,b.receipt_no,b.receipt_date,b.last_paid_amount,c.name_eng,c.pipe_size,c.name_nep,b.mc_status,b.previous_reading,b.present_reading,c.area_no FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(b.connection_no)=:connection_no AND b.connection_no=c.connection_no AND b.monthyearnep IS NOT NULL ORDER BY b.monthyearnep DESC"),
	@NamedQuery(name="BillingLedgerEntity.checkEntriesExist",query="SELECT COUNT(*) FROM ConsumerMaster c WHERE UPPER(c.con_type)='UNMETERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND c.connection_no NOT IN(SELECT bl.connection_no FROM BillingLedgerEntity bl,ConsumerMaster c WHERE bl.monthyearnep=:monthyearnep AND bl.connection_no=c.connection_no AND UPPER(c.con_type)='UNMETERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT'))"),
	@NamedQuery(name="BillingLedgerEntity.updateBulkBillingUnmeteredMY",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.con_type)='UNMETERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND c.connection_no NOT IN(SELECT bl.connection_no FROM BillingLedgerEntity bl,ConsumerMaster c WHERE bl.monthyearnep=:monthyearnep AND bl.connection_no=c.connection_no AND UPPER(c.con_type)='UNMETERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT'))"),
	@NamedQuery(name="BillingLedgerEntity.updateBulkBillingUnmetered",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.con_type)='UNMETERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT')"),
	@NamedQuery(name="BillingLedgerEntity.checkLedgerReceiptExists",query="SELECT COUNT(*) FROM BillingLedgerEntity bl,PaymentEntity p WHERE UPPER(bl.connection_no)=:connection_no AND UPPER(p.connectionNo)=:connection_no AND UPPER(bl.connection_no)=UPPER(p.connectionNo) AND p.cancelledremarks IS NULL AND bl.receipt_no IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.getLatestNepaliMonthYear",query="SELECT MAX(b.monthyearnep) FROM BillingLedgerEntity b where b.monthyearnep IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.getLatestRecordByConnectionNo",query="SELECT bl FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep IN(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no AND ble.monthyearnep IS NOT NULL)"),
	@NamedQuery(name="BillingLedgerEntity.getMissedBillsInLedgerSA",query="SELECT COUNT(*) FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size AND c.connection_no NOT IN(SELECT bl.connection_no FROM BillingLedgerEntity bl WHERE bl.monthyearnep=:monthyearnep AND bl.connection_no IN(SELECT c.connection_no FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size))"),
	@NamedQuery(name="BillingLedgerEntity.getMissedBillsInLedgerTHA",query="SELECT COUNT(*) FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size AND c.connection_no NOT IN(SELECT bl.connection_no FROM BillingLedgerEntity bl WHERE bl.monthyearnep=:monthyearnep AND bl.connection_no IN(SELECT c.connection_no FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size))"),
	//@NamedQuery(name="BillingLedgerEntity.getMissedBillsInLedgerSA",query="SELECT COUNT(*) FROM BillingLedgerEntity bl WHERE bl.monthyearnep=:monthyearnep AND bl.connection_no IN(SELECT c.connection_no FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size)"),
	//@NamedQuery(name="BillingLedgerEntity.getMissedBillsInLedgerTHA",query="SELECT COUNT(*) FROM BillingLedgerEntity bl WHERE bl.monthyearnep=:monthyearnep AND bl.connection_no IN(SELECT c.connection_no FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size)"),
	@NamedQuery(name="BillingLedgerEntity.getMaxBillNoByWardNo",query="SELECT MAX(bl.billno) FROM BillingLedgerEntity bl WHERE bl.billno LIKE :billno"),
	@NamedQuery(name="BillingLedgerEntity.getLedgetInsertByWardNoRdaySA",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT','PROCESSING IN NEW CONNECTION') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size AND c.connection_no NOT IN(SELECT bl.connection_no FROM BillingLedgerEntity bl WHERE bl.monthyearnep=:monthyearnep AND bl.connection_no IN(SELECT c.connection_no FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT','PROCESSING IN NEW CONNECTION') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size))"),
	@NamedQuery(name="BillingLedgerEntity.getLedgetInsertByWardNoRdayTHA",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT','PROCESSING IN NEW CONNECTION') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size AND c.connection_no NOT IN(SELECT bl.connection_no FROM BillingLedgerEntity bl WHERE bl.monthyearnep=:monthyearnep AND bl.connection_no IN(SELECT c.connection_no FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT','PROCESSING IN NEW CONNECTION') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size))"),
	/*@NamedQuery(name="BillingLedgerEntity.getLedgetInsertByWardNoRdaySA",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size"),
	@NamedQuery(name="BillingLedgerEntity.getLedgetInsertByWardNoRdayTHA",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) NOT IN('PERMANENT') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size"),
	*/
	@NamedQuery(name="BillingLedgerEntity.printBillUnmeteredBilled",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,b.billno,b.minimum_charges,b.additional_charges,b.water_charges,b.sw_charges,b.arrears,b.net_amount,b.rdng_date_nep,c.con_category,c.area_no,c.address_nep,b.rdng_date,c.pipe_size,c.road_street_eng FROM BillingLedgerEntity b,ConsumerMaster c WHERE c.con_type='Unmetered' AND UPPER(c.con_satatus) NOT IN ('PERMANENT') AND c.connection_no=b.connection_no AND UPPER(b.wardno)=:wardno AND b.monthyearnep LIKE :reading_month AND b.billno IS NOT NULL ORDER BY c.area_no ASC"),
	@NamedQuery(name="BillingLedgerEntity.printBillGovt",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.address_eng,b.billno,b.minimum_charges,b.additional_charges,b.water_charges,b.sw_charges,b.arrears,b.net_amount,b.rdng_date_nep,c.con_category,c.area_no,c.address_nep,b.rdng_date,c.pipe_size,c.road_street_eng, c.con_type FROM BillingLedgerEntity b,ConsumerMaster c WHERE UPPER(c.con_satatus) NOT IN ('PERMANENT') AND c.connection_no=b.connection_no AND UPPER(b.con_category)=:con_category AND b.monthyearnep LIKE :reading_month AND b.billno IS NOT NULL ORDER BY c.area_no ASC"),
	@NamedQuery(name="BillingLedgerEntity.findByConnectionNoByMYN",query="SELECT bl FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep=:monthyearnep"),
	@NamedQuery(name="BillingLedgerEntity.findObsByConnectionNoByMYN",query="SELECT bl.observationEntity.observationName FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep=:monthyearnep"),
	@NamedQuery(name="BillingLedgerEntity.checkLedgerLatestExists",query="SELECT COUNT(*) FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep in(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no AND ble.monthyearnep IS NOT NULL) AND bl.billno IS NOT NULL "),
	@NamedQuery(name="BillingLedgerEntity.getMaxMonthYearNepali",query="SELECT MAX(bl.monthyearnep) FROM BillingLedgerEntity bl WHERE bl.monthyearnep IS NOT NULL"),
	@NamedQuery(name="BillingLedgerEntity.getDoorLockCount",query="SELECT NVL(b.dl_count,0),b.billno FROM BillingLedgerEntity b WHERE b.connection_no=:connection_no AND b.monthyearnep IN (SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no AND ble.monthyearnep IS NOT NULL)"),
	@NamedQuery(name="BillingLedgerEntity.getOpeningBalanceByConNo",query="SELECT l.receipt_date,nvl(l.net_amount,0),nvl(close_balance,0),l.last_paid_amount,nvl(service_charge,0),nvl(sundry_amount,0) FROM BillingLedgerEntity l WHERE UPPER(l.connection_no)=:connection_no AND l.monthyearnep in(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no AND ble.billno IS NOT NULL)",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),

	/*Payment By Month Selection Update Arrears Daily*/
	@NamedQuery(name="BillingLedgerEntity.findLatestLedgerBYCNSchema",query="SELECT l FROM BillingLedgerEntity l WHERE UPPER(l.connection_no)=:connection_no AND l.monthyearnep in(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no AND ble.billno IS NOT NULL AND ble.receipt_no IS NULL AND ble.last_paid_amount is not null and ble.receipt_date is not null)",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.findBillsFromMaxReceiptNo",query="SELECT b FROM BillingLedgerEntity b WHERE UPPER(b.connection_no)=:connection_no AND b.billno IS NOT NULL AND TO_NUMBER(b.monthyearnep)>= (SELECT NVL(max(bl.monthyearnep),0) FROM BillingLedgerEntity bl WHERE bl.receipt_no IS NOT NULL AND UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep IS NOT NULL) ORDER BY b.monthyearnep ASC",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),

	@NamedQuery(name="BillingLedgerEntity.findLastMonthRecordByConNo",query="SELECT l FROM BillingLedgerEntity l WHERE UPPER(l.connection_no)=:connection_no AND l.monthyearnep in(SELECT MAX(bl.monthyearnep) FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.monthyearnep<(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no))",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="BillingLedgerEntity.findRecordByConNoMYN",query="SELECT l FROM BillingLedgerEntity l WHERE UPPER(l.connection_no)=:connection_no AND l.monthyearnep=:monthyearnep"),
	@NamedQuery(name="BillingLedgerEntity.getLatestBillNotNull",query="SELECT bl FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no AND bl.billno IS NOT NULL AND bl.monthyearnep in(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no AND ble.billno IS NOT NULL)"),
	@NamedQuery(name="BillingLedgerEntity.getBillGenerationStatus",query="SELECT bl FROM BillingLedgerEntity bl WHERE UPPER(bl.connection_no)=:connection_no  AND bl.monthyearnep in(SELECT MAX(ble.monthyearnep) FROM BillingLedgerEntity ble WHERE UPPER(ble.connection_no)=:connection_no)"),
	
	
})
public class BillingLedgerEntity {

	@Id
	@SequenceGenerator(name = "bsw_ledger_seq", sequenceName = "BSW_LEDGER_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_ledger_seq") 
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
	
	@Column(name="WATER_CHARGES	")
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
	
	@Column(name="DENOTED_BY")	
	private String denoted_by;

	public String getDenoted_by() {
		return denoted_by;
	}

	public void setDenoted_by(String denoted_by) {
		this.denoted_by = denoted_by;
	}

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
	
	@Column(name="DUE_DATE_NEP")	
	private String due_date_nep;
	
	@Column(name="BANK_DUE_DATE")	
	private Date bank_due_date;
	
	@Column(name="OTHER")	
	private Double other;
	
	@Column(name="SITECODE")	
	private String sitecode;
	
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
	
	@Column(name="CREATED_DATE")
	private Date created_date;
	
	@Column(name="UPDATED_BY")
	private String updated_by;
	
	@Column(name="UPDATED_DATE")
	private Date updated_date;
	
	@Column(name="DR_AMOUNT")	
	private Double dr_amount;
	
	
	@Column(name="MC_STATUS")	
	private Integer mc_status;
	
	@OneToOne
	@JoinColumn(name = "MC_STATUS", referencedColumnName = "ID", insertable = false, updatable = false)
	private ObservationEntity observationEntity;

	
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
	
	@Column(name="MONTHYEAR")	
	private Integer monthyear;
	
	@Column(name="MONTHYEARNEP")	
	private String monthyearnep;
	
	@Column(name="PCASHPOSDATE")	
	private Date pcashposdate;
	
	@Column(name="CASHPOSDATE")	
	private Date cashposdate;
	
	@Column(name="RECEIPT_NO")	
	private String receipt_no;
	
	@Column(name="RECEIPT_DATE")	
	private Date receipt_date;
	
	@Column(name="LAST_PAID_AMOUNT")
	private Double last_paid_amount;
	
	@Column(name="CLOSE_BALANCE")
	private Double close_balance;
	
	@Column(name="READING_DAY")
	private Integer reading_day;
	
	@Column(name="PIPE_SIZE")
	private Double pipe_size;
	
	@Column(name="BILL_ADJ_AMOUNT")
	private Double bill_adj_amount;
	
	@Column(name="PENALTY_ADJ_AMOUNT")
	private Double penalty_adj_amount;
	
	@Column(name="ADJ_ID")
	private Integer adj_id;
	
	@Column(name="CORR_ID")
	private Integer corr_id;
	
	@Column(name="CON_TYPE")
	private String con_type;
	
	@Column(name="CON_CATEGORY")
	private String con_category;
	
	@Column(name="EARREARS")
	private Double earrears;
	
	@Column(name="PENDING_ADJ_APPROVE")
	private Double pending_adj_approve;
	
	
	
	
	public Integer getCorr_id() {
		return corr_id;
	}

	public void setCorr_id(Integer corr_id) {
		this.corr_id = corr_id;
	}

	public Integer getAdj_id() {
		return adj_id;
	}

	public void setAdj_id(Integer adj_id) {
		this.adj_id = adj_id;
	}

	public Double getClose_balance() {
		return close_balance;
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

	public void setClose_balance(Double close_balance) {
		this.close_balance = close_balance;
	}

	public int getId() {
		return id;
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

	public Double getLast_paid_amount() {
		return last_paid_amount;
	}

	public void setLast_paid_amount(Double last_paid_amount) {
		this.last_paid_amount = last_paid_amount;
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

	public Integer getRdng_mth() {
		return rdng_mth;
	}

	public void setRdng_mth(Integer rdng_mth) {
		this.rdng_mth = rdng_mth;
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


	public Double getVoucher_adj() {
		return voucher_adj;
	}

	public void setVoucher_adj(Double voucher_adj) {
		this.voucher_adj = voucher_adj;
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


	public String getRdng_date_nep() {
		return rdng_date_nep;
	}

	public void setRdng_date_nep(String rdng_date_nep) {
		this.rdng_date_nep = rdng_date_nep;
	}

	public String getBill_date_nep() {
		return bill_date_nep;
	}

	public void setBill_date_nep(String bill_date_nep) {
		this.bill_date_nep = bill_date_nep;
	}

	public Integer getMc_status() {
		return mc_status;
	}

	public void setMc_status(Integer mc_status) {
		this.mc_status = mc_status;
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

	public Integer getMonthyear() {
		return monthyear;
	}

	public void setMonthyear(Integer monthyear) {
		this.monthyear = monthyear;
	}

	public Double getBill_period() {
		return bill_period;
	}

	public void setBill_period(Double bill_period) {
		this.bill_period = bill_period;
	}

	public String getDue_date_nep() {
		return due_date_nep;
	}

	public void setDue_date_nep(String due_date_nep) {
		this.due_date_nep = due_date_nep;
	}

	

	public String getMonthyearnep() {
		return monthyearnep;
	}

	public void setMonthyearnep(String monthyearnep) {
		this.monthyearnep = monthyearnep;
	}

	public Double getPenalty() {
		return penalty;
	}

	public void setPenalty(Double penalty) {
		this.penalty = penalty;
	}

	public String getSitecode() {
		return sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getPcashposdate() {
		return pcashposdate;
	}

	public void setPcashposdate(Date pcashposdate) {
		this.pcashposdate = pcashposdate;
	}

	public Date getCashposdate() {
		return cashposdate;
	}

	public void setCashposdate(Date cashposdate) {
		this.cashposdate = cashposdate;
	}

	public String getReceipt_no() {
		return receipt_no;
	}

	public void setReceipt_no(String receipt_no) {
		this.receipt_no = receipt_no;
	}

	public Date getReceipt_date() {
		return receipt_date;
	}

	public void setReceipt_date(Date receipt_date) {
		this.receipt_date = receipt_date;
	}
	
	public ObservationEntity getObservationEntity() {
		return observationEntity;
	}

	public void setObservationEntity(ObservationEntity observationEntity) {
		this.observationEntity = observationEntity;
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

	public Double getEarrears() {
		return earrears;
	}

	public void setEarrears(Double earrears) {
		this.earrears = earrears;
	}


	public Double getPending_adj_approve() {
		return pending_adj_approve;
	}

	public void setPending_adj_approve(Double pending_adj_approve) {
		this.pending_adj_approve = pending_adj_approve;
	}



	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}


		
}

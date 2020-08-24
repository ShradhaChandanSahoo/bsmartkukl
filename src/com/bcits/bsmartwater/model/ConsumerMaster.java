package com.bcits.bsmartwater.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "BSW_MASTER")

@NamedQueries({
	
	@NamedQuery(name="ConsumerMaster.findByConnectionNo",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.connection_no)=:connectionNo"),
	@NamedQuery(name="BillApproveEntity.getBillPendingApproval",query="SELECT c.connection_no,c.name_eng,c.name_nep,c.con_category,c.mobile_no,c.con_type,b.id,b.monthyear,b.remarks FROM ConsumerMaster c,BillApproveEntity b WHERE UPPER(c.connection_no)=UPPER(b.connection_no) AND b.bill_app_status=0"),
	@NamedQuery(name="ConsumerMaster.countByWardNo",query="SELECT COUNT(*) FROM ConsumerMaster c WHERE UPPER(c.ward_no)=:ward_no AND c.con_type='Unmetered'"),
	@NamedQuery(name="ConsumerMaster.countByConCategory",query="SELECT COUNT(*) FROM ConsumerMaster c WHERE UPPER(c.con_category)=:con_category"),
	@NamedQuery(name="ConsumerMaster.getDistictWardNos",query="SELECT DISTINCT(c.ward_no) FROM ConsumerMaster c WHERE c.con_type NOT IN('Unmetered') ORDER BY c.ward_no ASC"),
	@NamedQuery(name="ConsumerMaster.getDistictAllWardNos", query="SELECT DISTINCT(c.ward_no) FROM ConsumerMaster c ORDER BY c.ward_no ASC"),
	@NamedQuery(name="ConsumerMaster.getDistictMrNos",query="SELECT DISTINCT(m.mrCode) FROM ConsumerMaster c,MeterReaderEntity m WHERE c.mtr_reader=m.id AND c.mtr_reader is not NULL "),
	@NamedQuery(name="ConsumerMaster.getDistictReadingDays",query="SELECT DISTINCT(c.reading_day) FROM ConsumerMaster c WHERE c.reading_day is not NULL ORDER BY c.reading_day ASC"),
	//@NamedQuery(name="ConsumerMaster.getDistictWardNos",query="SELECT DISTINCT(c.ward_no) FROM ConsumerMaster c WHERE c.ward_no is not NULL ORDER BY cast(c.ward_no as integer) ASC"),
	//@NamedQuery(name="ConsumerMaster.viewRtPlanGen",query="SELECT c.connection_no,c.mtr_ser_no,c.reading_day,c.ward_no,REGEXP_SUBSTR(c.seq_no, '[^A-Za-z]+', 1, 1) sq_no,REGEXP_SUBSTR(c.seq_no, '[^0-9]+', 1, 1) seq_abbr,c.area_no FROM ConsumerMaster c where c.mtr_reader like :mrCode and c.reading_day like :readingDay and c.ward_no like :wardNo and c.area_no like :areaNumber"),
	@NamedQuery(name="ConsumerMaster.getDistictAreaNos",query="SELECT DISTINCT(c.area_no) FROM ConsumerMaster c ORDER BY c.area_no ASC"),
	@NamedQuery(name="ConsumerMaster.getMasterDataByConnectionNum",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.connection_no) like :conNum"),
	@NamedQuery(name="ConsumerMaster.getMasterDataByName",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.name_eng) LIKE :name ORDER BY c.name_eng ASC"),
	@NamedQuery(name="ConsumerMaster.getMasterDataByWardNum",query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.ward_no)=:wardNum"),
	@NamedQuery(name="ConsumerMaster.getPipeSizeAndConType",query="SELECT c.pipe_size,c.con_type FROM ConsumerMaster c WHERE c.connection_no=:connection_no",hints={@QueryHint(name="org.hibernate.cacheable",value="true")}),
	@NamedQuery(name="ConsumerMaster.checkConnect_noInMaster", query="SELECT COUNT(*) FROM ConsumerMaster c WHERE c.connection_no=:connection_no"),
	@NamedQuery(name="ConsumerMaster.getDistictConnCategory",query="SELECT DISTINCT(c.con_category) FROM ConsumerMaster c ORDER BY c.con_category ASC"),
	@NamedQuery(name="ConsumerMaster.getDistictConnType",query="SELECT DISTINCT(c.con_type) FROM ConsumerMaster c ORDER BY c.con_type ASC"),
	@NamedQuery(name="ConsumerMaster.getDistictpipeSize",query="SELECT DISTINCT(c.pipe_size) FROM ConsumerMaster c WHERE c.pipe_size is not NULL ORDER BY c.pipe_size ASC"),
	@NamedQuery(name="ConsumerMaster.genDisConnList",query="SELECT c,b FROM ConsumerMaster c,BillingLedgerEntity b WHERE c.connection_no=b.connection_no and b.monthyear=to_char(SYSDATE,'yyyyMM') and c.ward_no=:wardNo and c.con_category=:connCat and c.pipe_size=:pipeSize and b.arrears between :cutoffAmtfrm and :cutoffAmtto"),
	@NamedQuery(name="ConsumerMaster.getTotalConsumersCount", query="SELECT COUNT(*) FROM ConsumerMaster c WHERE c.branch=:siteCode "),
	@NamedQuery(name="ConsumerMaster.getMasterDataByAreaNo", query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.area_no) like :area_no order by c.id desc"),
	@NamedQuery(name="ConsumerMaster.getMasterDataByPhoneNum", query="SELECT c FROM ConsumerMaster c WHERE c.phone_no like :phoneNo order by c.id desc"),

	@NamedQuery(name="ConsumerMaster.countByWardNoRdayPSSA",query="SELECT COUNT(*) FROM ConsumerMaster c WHERE UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size"),
	@NamedQuery(name="ConsumerMaster.countByWardNoRdayPSSACC",query="SELECT COUNT(*) FROM ConsumerMaster c WHERE UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size=:pipe_size AND c.con_category=:con_category"),

	
	@NamedQuery(name="ConsumerMaster.countByWardNoRdayPSTHA",query="SELECT COUNT(*) FROM ConsumerMaster c WHERE UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size"),
	@NamedQuery(name="ConsumerMaster.countByWardNoRdayPSTHACC",query="SELECT COUNT(*) FROM ConsumerMaster c WHERE UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND c.pipe_size>=:pipe_size AND c.con_category=:con_category"),

	
	@NamedQuery(name="ConsumerMaster.getMasterDataByOldConnNum", query="SELECT c FROM ConsumerMaster c WHERE UPPER(c.oldconnectionno) like :oldConNum order by c.id desc"),
	@NamedQuery(name="ConsumerMaster.getDistictWardNosUnmetered",query="SELECT DISTINCT(c.ward_no) FROM ConsumerMaster c WHERE c.con_type IN('Unmetered') ORDER BY c.ward_no ASC"),
	@NamedQuery(name="ConsumerMaster.getDistictMonthYearNep",query="SELECT DISTINCT substr(b.monthyearnep,0,4) FROM BillingLedgerEntity b where b.monthyearnep IS NOT NULL ORDER BY substr(b.monthyearnep,0,4) DESC"),
	//@NamedQuery(name="ConsumerMaster.meterReadingListReportRead",query="SELECT c.connection_no,c.area_no,c.name_nep,c.name_eng,c.address_nep,c.address_eng FROM ConsumerMaster c WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) IN('NORMAL') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day ORDER BY c.area_no ASC"),
	
	@NamedQuery(name="ConsumerMaster.meterReadingListReportReadSA",query="SELECT c.connection_no,c.area_no,c.name_nep,c.name_eng,c.address_nep,c.address_eng,l.mc_status,c.pipe_size FROM ConsumerMaster c,BillingLedgerEntity l WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) IN('NORMAL') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND "
	+ "l.connection_no=c.connection_no AND l.monthyearnep=:monthyearnep AND c.pipe_size=:pipe_size ORDER BY c.area_no ASC"),
	
	@NamedQuery(name="ConsumerMaster.meterReadingListReportReadTHA",query="SELECT c.connection_no,c.area_no,c.name_nep,c.name_eng,c.address_nep,c.address_eng,l.mc_status,c.pipe_size FROM ConsumerMaster c,BillingLedgerEntity l WHERE UPPER(c.con_type)='METERED' AND UPPER(c.con_satatus) IN('NORMAL') AND UPPER(c.ward_no)=:ward_no AND c.reading_day=:reading_day AND "
			+ "l.connection_no=c.connection_no AND l.monthyearnep=:monthyearnep AND c.pipe_size>=:pipe_size ORDER BY c.area_no ASC"),
			
	
	//@NamedQuery(name="ConsumerMaster.consumerListReportReadData",query="SELECT c.connection_no,c.area_no,c.name_nep,c.address_nep,c.con_type,c.pipe_size,c.name_eng,c.address_eng,c.ward_no FROM ConsumerMaster c"),
	@NamedQuery(name="ConsumerMaster.consumerListReportReadData",query="SELECT c.connection_no,c.area_no,c.name_nep,c.address_nep,c.con_type,c.pipe_size,c.name_eng,c.address_eng,c.ward_no,mc.meter_no FROM ConsumerMaster c ,MeterDetailsEntity mc where c.connection_no=mc.connectionno"),
	@NamedQuery(name="ConsumerMaster.holeBlockReportReadData",query="SELECT c.connection_no,c.area_no,c.name_eng,c.address_eng,c.con_type,c.pipe_size,c.con_satatus FROM ConsumerMaster c WHERE c.con_satatus=:con_status"),
	@NamedQuery(name="ConsumerMaster.categoryTypeReportReadData",query="SELECT c.connection_no,c.area_no,c.name_nep,c.address_nep,c.con_type,c.pipe_size,c.con_category,c.name_eng,c.address_eng FROM ConsumerMaster c WHERE c.con_category=:con_category"),
	@NamedQuery(name="ConsumerMaster.conTypeReportReadData",query="SELECT c.connection_no,c.area_no,c.name_nep,c.address_nep,c.con_type,c.pipe_size,c.con_type,c.name_eng,c.address_eng, c.con_category FROM ConsumerMaster c WHERE c.con_type=:con_Type"),
	@NamedQuery(name="ConsumerMaster.getMaxCustomerId", query="SELECT max(c.customer_id) FROM ConsumerMaster c"),
	@NamedQuery(name="ConsumerMaster.getPendingConnForBilling", query="SELECT c FROM ConsumerMaster c WHERE c.status=:status AND c.connection_no LIKE :sitecode"),
	@NamedQuery(name="ConsumerMaster.getTariffData",query="SELECT c.connection_no,c.con_type,c.pipe_size,c.sewage_used,c.meterHired,nvl(c.meterRentCharges,0) FROM ConsumerMaster c WHERE UPPER(c.connection_no)=:connectionNo"),
	
})
public class ConsumerMaster extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "bsw_master_seq", sequenceName = "BSW_MASTER_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_master_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="BRANCH")
	private String branch;
	
	@Column(name="CONNECTION_NO")
	private String connection_no;
	
	@Column(name="NAME_ENG")
	private String name_eng;
	
	@Column(name="NAME_NEP")
	private String name_nep;
	
	@Column(name="FNAME_ENG")
	private String fname_eng;
	
	@Column(name="FNAME_NEP")
	private String fname_nep;
	
	@Column(name="GFNAME_ENG")
	private String gfname_eng;
	
	@Column(name="GFNAME_NEP")
	private String gfname_nep;
	
	@Column(name="ADDRESS_ENG")
	private String address_eng;
	
	@Column(name="ADDRESS_NEP")
	private String address_nep;
	
	@Column(name="TOLE_NAME_ENG")
	private String tole_name_eng;
	
	@Column(name="TOLE_NAME_NEP")
	private String tole_name_nep;
	
	@Column(name="CUSTOMER_ID")
	private Long customer_id;
	
	@Column(name="ROAD_STREET_ENG")
	private String road_street_eng;
	
	@Column(name="ROAD_STREET_NEP")
	private String road_street_nep;
	
	@Column(name="MPC_NAME_ENG")
	private String mpc_name_eng;
	
	@Column(name="MPC_NAME_NEP")
	private String mpc_name_nep;
	
	@Column(name="WARD_NO")
	private String ward_no;
	
	@Column(name="AREA_NO")
	private String area_no;
	
	@Column(name="PHONE_NO")
	private String phone_no;
	
	@Column(name="MOBILE_NO")
	private String mobile_no;
	
	@Column(name="CITIZENSHIP_NO")
	private String citizenship_no;
	
	@Column(name="MTR_CONDATE_ENG")
	private Date mtr_condate_eng;

	@Column(name="MTR_CONDATE_NEP")
	private String mtr_condate_nep;
	
	@Column(name="METER_REMOVE")
	private String meterRemove;
	
	
	@Column(name="MTR_SER_NO")
	private String mtr_ser_no;
	
	@Column(name="MTR_RDNG")
	private Double mtr_rdng;

	@Column(name="MTR_READER")
	private Integer mtr_reader;
	
	@Column(name="READING_DAY")
	private Integer reading_day;
	
	@Column(name="CON_CATEGORY")
	private String con_category;
	
	@Column(name="CON_TYPE")
	private String con_type;
	
	@Column(name="CON_SATATUS")
	private String con_satatus;
	
	@Column(name="AVERAGE")
	private Integer average;
	
	
	@Column(name="SEWAGE_USED")
	private String sewage_used;
	
	@Column(name="PLOT_NO")
	private String plot_no;
	
	@Column(name="LOC_NO")
	private Integer loc_no;
	
	@Column(name="AREA_TYPE")
	private String area_type;
	
	@Column(name="DISDATE_ENG")
	private Date disdate_eng;

	@Column(name="DISDATE_NEP")
	private String disdate_nep;
	
	@Column(name="DIS_REASON")
	private String dis_reason;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="DENOTED_BY")
	private String denoted_by;

	@Column(name="ENT_DATE_ENG")
	private Date ent_date_eng;
	
	@Column(name="ENT_DATE_NEP")
	private String ent_date_nep;
	
	@Column(name="USERNAME")
	private String username;
	
	
	@Column(name="BALANCE")
	private Double balance;
	
	@Column(name="LEDGERNO")
	private String ledgerno;
	
	@Column(name="FOLIO")
	private String folio;
	
	@Column(name="PIPE_SIZE")
	private Double pipe_size;
	
	@Column(name="SEQ_NO")
	private String seq_no;
	
	@Column(name="FIXED_CHARGES")
	private Double fixedCharges;
	
	@Column(name="CONNEC_DATE_ENG")
	private Date connc_date_eng;
	
	@Column(name="CONNC_DATE_NEP")
	private String conc_date_nep;

	@Column(name="COSTUMER_GROUP")
	private String costumerGroup;
	
	
	
	
	@Transient
	private String ent_trans_endDate;
	
	@Transient
	private String con_trans_date;
	
	@Column(name="METER_IN_HIRE")
	private String meterHired;
	
	@Column(name="METER_RENT")
	private Double meterRentCharges;
	
	@Column(name="OLDCONNECTIONNO")
	private String oldconnectionno;
	
	@Column(name="MUNICIPALITY_ID")
	private Integer municipality_Id;
	
	@Column(name="CONSUMER_TITLE")
	private String consumer_title;
	
	@Column(name="BILL_REMARKSUM")
	private String bill_remarksum;
	
	@Column(name="INSTALL_DUE_DATE")
	private String install_due_date;
	
	@Column(name="NEW_CON_APPROVED_BY")
	private String new_con_approved_by;
	
	
	@Column(name="NEW_CON_APPROVED_DATE")
	private Timestamp new_con_approved_date;
	
	//added by ram 11-4-2019
	@Column(name="ILLEGAL_CON_PENALTY")
	private String illegalConPenalty;
	
	
	public String getBill_remarksum() {
		return bill_remarksum;
	}

	public String getInstall_due_date() {
		return install_due_date;
	}

	public void setInstall_due_date(String install_due_date) {
		this.install_due_date = install_due_date;
	}

	public void setBill_remarksum(String bill_remarksum) {
		this.bill_remarksum = bill_remarksum;
	}

	public String getConsumer_title() {
		return consumer_title;
	}

	public void setConsumer_title(String consumer_title) {
		this.consumer_title = consumer_title;
	}

	public Integer getMunicipality_Id() {
		return municipality_Id;
	}

	public void setMunicipality_Id(Integer municipality_Id) {
		this.municipality_Id = municipality_Id;
	}

	public String getOldconnectionno() {
		return oldconnectionno;
	}

	public void setOldconnectionno(String oldconnectionno) {
		this.oldconnectionno = oldconnectionno;
	}

	public String getMeterHired() {
		return meterHired;
	}

	public void setMeterHired(String meterHired) {
		this.meterHired = meterHired;
	}

	public Double getMeterRentCharges() {
		return meterRentCharges;
	}

	public void setMeterRentCharges(Double meterRentCharges) {
		this.meterRentCharges = meterRentCharges;
	}

	public String getEnt_trans_endDate() {
		return ent_trans_endDate;
	}

	public void setEnt_trans_endDate(String ent_trans_endDate) {
		this.ent_trans_endDate = ent_trans_endDate;
	}

	public String getCon_trans_date() {
		return con_trans_date;
	}

	public void setCon_trans_date(String con_trans_date) {
		this.con_trans_date = con_trans_date;
	}

	public String getCostumerGroup() {
		return costumerGroup;
	}

	public void setCostumerGroup(String costumerGroup) {
		this.costumerGroup = costumerGroup;
	}

	public String getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}

	public Double getFixedCharges() {
		return fixedCharges;
	}

	public void setFixedCharges(Double fixedCharges) {
		this.fixedCharges = fixedCharges;
	}

	public Date getConnc_date_eng() {
		return connc_date_eng;
	}

	public void setConnc_date_eng(Date connc_date_eng) {
		this.connc_date_eng = connc_date_eng;
	}

	public String getConc_date_nep() {
		return conc_date_nep;
	}

	public void setConc_date_nep(String conc_date_nep) {
		this.conc_date_nep = conc_date_nep;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	
	public String getName_eng() {
		return name_eng;
	}

	public void setName_eng(String name_eng) {
		this.name_eng = name_eng;
	}

	public String getName_nep() {
		return name_nep;
	}

	public void setName_nep(String name_nep) {
		this.name_nep = name_nep;
	}

	public String getFname_eng() {
		return fname_eng;
	}

	public void setFname_eng(String fname_eng) {
		this.fname_eng = fname_eng;
	}

	public String getFname_nep() {
		return fname_nep;
	}

	public void setFname_nep(String fname_nep) {
		this.fname_nep = fname_nep;
	}

	public String getGfname_eng() {
		return gfname_eng;
	}

	public void setGfname_eng(String gfname_eng) {
		this.gfname_eng = gfname_eng;
	}

	public String getGfname_nep() {
		return gfname_nep;
	}

	public void setGfname_nep(String gfname_nep) {
		this.gfname_nep = gfname_nep;
	}

	public String getAddress_eng() {
		return address_eng;
	}

	public void setAddress_eng(String address_eng) {
		this.address_eng = address_eng;
	}

	public String getAddress_nep() {
		return address_nep;
	}

	public void setAddress_nep(String address_nep) {
		this.address_nep = address_nep;
	}

	public String getTole_name_eng() {
		return tole_name_eng;
	}

	public void setTole_name_eng(String tole_name_eng) {
		this.tole_name_eng = tole_name_eng;
	}

	public String getTole_name_nep() {
		return tole_name_nep;
	}

	public void setTole_name_nep(String tole_name_nep) {
		this.tole_name_nep = tole_name_nep;
	}

	public String getRoad_street_eng() {
		return road_street_eng;
	}

	public void setRoad_street_eng(String road_street_eng) {
		this.road_street_eng = road_street_eng;
	}

	public String getRoad_street_nep() {
		return road_street_nep;
	}

	public void setRoad_street_nep(String road_street_nep) {
		this.road_street_nep = road_street_nep;
	}

	public String getMpc_name_eng() {
		return mpc_name_eng;
	}

	public void setMpc_name_eng(String mpc_name_eng) {
		this.mpc_name_eng = mpc_name_eng;
	}

	public String getMpc_name_nep() {
		return mpc_name_nep;
	}

	public void setMpc_name_nep(String mpc_name_nep) {
		this.mpc_name_nep = mpc_name_nep;
	}

	public String getWard_no() {
		return ward_no;
	}

	public void setWard_no(String ward_no) {
		this.ward_no = ward_no;
	}

	public String getArea_no() {
		return area_no;
	}

	public void setArea_no(String area_no) {
		this.area_no = area_no;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getCitizenship_no() {
		return citizenship_no;
	}

	public void setCitizenship_no(String citizenship_no) {
		this.citizenship_no = citizenship_no;
	}

	public Date getMtr_condate_eng() {
		return mtr_condate_eng;
	}

	public void setMtr_condate_eng(Date mtr_condate_eng) {
		this.mtr_condate_eng = mtr_condate_eng;
	}

	public String getMtr_condate_nep() {
		return mtr_condate_nep;
	}

	public void setMtr_condate_nep(String mtr_condate_nep) {
		this.mtr_condate_nep = mtr_condate_nep;
	}


	public String getMtr_ser_no() {
		return mtr_ser_no;
	}

	public void setMtr_ser_no(String mtr_ser_no) {
		this.mtr_ser_no = mtr_ser_no;
	}

	public Double getMtr_rdng() {
		return mtr_rdng;
	}

	public void setMtr_rdng(Double mtr_rdng) {
		this.mtr_rdng = mtr_rdng;
	}

	public Integer getMtr_reader() {
		return mtr_reader;
	}

	public void setMtr_reader(Integer mtr_reader) {
		this.mtr_reader = mtr_reader;
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

	public String getCon_satatus() {
		return con_satatus;
	}

	public void setCon_satatus(String con_satatus) {
		this.con_satatus = con_satatus;
	}

	public Integer getAverage() {
		return average;
	}

	public void setAverage(Integer average) {
		this.average = average;
	}



	public String getSewage_used() {
		return sewage_used;
	}

	public void setSewage_used(String sewage_used) {
		this.sewage_used = sewage_used;
	}

	public String getPlot_no() {
		return plot_no;
	}

	public void setPlot_no(String plot_no) {
		this.plot_no = plot_no;
	}

	public Integer getLoc_no() {
		return loc_no;
	}

	public void setLoc_no(Integer loc_no) {
		this.loc_no = loc_no;
	}

	public String getArea_type() {
		return area_type;
	}

	public void setArea_type(String area_type) {
		this.area_type = area_type;
	}

	public Date getDisdate_eng() {
		return disdate_eng;
	}

	public void setDisdate_eng(Date disdate_eng) {
		this.disdate_eng = disdate_eng;
	}

	public String getDisdate_nep() {
		return disdate_nep;
	}

	public void setDisdate_nep(String disdate_nep) {
		this.disdate_nep = disdate_nep;
	}

	public String getDis_reason() {
		return dis_reason;
	}

	public void setDis_reason(String dis_reason) {
		this.dis_reason = dis_reason;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	

	public String getDenoted_by() {
		return denoted_by;
	}

	public void setDenoted_by(String denoted_by) {
		this.denoted_by = denoted_by;
	}

	public Date getEnt_date_eng() {
		return ent_date_eng;
	}

	public void setEnt_date_eng(Date ent_date_eng) {
		this.ent_date_eng = ent_date_eng;
	}

	public String getEnt_date_nep() {
		return ent_date_nep;
	}

	public void setEnt_date_nep(String ent_date_nep) {
		this.ent_date_nep = ent_date_nep;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getConnection_no() {
		return connection_no;
	}

	public void setConnection_no(String connection_no) {
		this.connection_no = connection_no;
	}

	public String getLedgerno() {
		return ledgerno;
	}

	public void setLedgerno(String ledgerno) {
		this.ledgerno = ledgerno;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public Double getPipe_size() {
		return pipe_size;
	}

	public void setPipe_size(Double pipe_size) {
		this.pipe_size = pipe_size;
	}

	public Integer getReading_day() {
		return reading_day;
	}

	public void setReading_day(Integer reading_day) {
		this.reading_day = reading_day;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}

	public String getNew_con_approved_by() {
		return new_con_approved_by;
	}

	public void setNew_con_approved_by(String new_con_approved_by) {
		this.new_con_approved_by = new_con_approved_by;
	}

	public Timestamp getNew_con_approved_date() {
		return new_con_approved_date;
	}

	public void setNew_con_approved_date(Timestamp new_con_approved_date) {
		this.new_con_approved_date = new_con_approved_date;
	}

	public String getMeterRemove() {
		return meterRemove;
	}

	public void setMeterRemove(String meterRemove) {
		this.meterRemove = meterRemove;
	}

	public String getIllegalConPenalty() {
		return illegalConPenalty;
	}

	public void setIllegalConPenalty(String illegalConPenalty) {
		this.illegalConPenalty = illegalConPenalty;
	}

	

	
	
	
	
	
}

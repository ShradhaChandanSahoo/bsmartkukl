package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="BSW_CASH_MASTER_CONFIG")

@NamedQueries({
	@NamedQuery(name="CashMasterConfig.getcashMasterConfigData",query="SELECT c FROM CashMasterConfig c"),
	@NamedQuery(name="CashMasterConfig.getConfigDataOnuserName",query="SELECT c FROM CashMasterConfig c WHERE c.counter_no=:counterNo AND c.userMaster.user_login_name=:ulname"),
	@NamedQuery(name="CashMasterConfig.deleteCashMasterConfig",query="DELETE FROM CashMasterConfig c WHERE c.id=:id"),
	@NamedQuery(name="CashMasterConfig.findUserByCounterNo",query="SELECT u.user_name,u.userDesignationEntity.designation FROM CashMasterConfig c,UserMaster u WHERE c.user_id=u.id AND c.counter_no=:counterNo"),

	
})
public class CashMasterConfig extends BaseEntity {
	@Id
	@SequenceGenerator(name = "bsw_cashconfig_seq", sequenceName = "BSW_CASH_MASTER_CONFIG_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_cashconfig_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="COUNTER_NO")
	private Integer counter_no;
	
	@Column(name="COUNTER_NAME")
	private String counter_name;
	
	@Column(name="USER_ID")
	private String user_id;

	@OneToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private UserMaster userMaster;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCounter_no() {
		return counter_no;
	}

	public void setCounter_no(Integer counter_no) {
		this.counter_no = counter_no;
	}

	public String getCounter_name() {
		return counter_name;
	}

	public void setCounter_name(String counter_name) {
		this.counter_name = counter_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public UserMaster getUserMaster() {
		return userMaster;
	}

	public void setUserMaster(UserMaster userMaster) {
		this.userMaster = userMaster;
	}

}

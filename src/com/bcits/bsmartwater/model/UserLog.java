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
@Table(name = "BSW_USER_LOG")

@NamedQueries({
	@NamedQuery(name="UserLog.findLogByUserId",query="SELECT u FROM UserLog u  WHERE UPPER(u.user_id)=:user_id AND u.logout_time IS NULL AND u.login_time=(SELECT MAX(u.login_time) FROM UserLog u  WHERE UPPER(u.user_id)=:user_id AND u.logout_time IS NULL)"),
	
})
public class UserLog {

	@Id
	@SequenceGenerator(name = "bsw_user_log_seq", sequenceName = "BSW_USER_LOG_SEQ",allocationSize=1)  
	@GeneratedValue(generator = "bsw_user_log_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="USER_ID")
	private String user_id;
	
	@Column(name="LOGIN_TIME")
	private Date login_time;
	
	@Column(name="LOGOUT_TIME")
	private Date logout_time;
	
	@Column(name="LOGIN_STS")
	private String login_sts;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="IP_ADDRESS")
	private String ip_address;
	
	@Column(name="LOGIN_TYPE")
	private String login_type;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getLogin_sts() {
		return login_sts;
	}

	public void setLogin_sts(String login_sts) {
		this.login_sts = login_sts;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIp_address() {
		return ip_address;
	}

	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}

	public Date getLogin_time() {
		return login_time;
	}

	public void setLogin_time(Date login_time) {
		this.login_time = login_time;
	}

	public Date getLogout_time() {
		return logout_time;
	}

	public void setLogout_time(Date logout_time) {
		this.logout_time = logout_time;
	}

	public String getLogin_type() {
		return login_type;
	}

	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	
	
	
	
}

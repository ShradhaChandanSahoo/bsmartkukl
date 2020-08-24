package com.bcits.bsmartwater.model;

import java.io.Serializable;

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

import com.bcits.bsmartwater.utils.PasswordDecryptBase64;
import com.bcits.bsmartwater.utils.PasswordEncryptBase64;

@SuppressWarnings("serial")
@Entity
@Table(name="BSW_USERS")

@NamedQueries({
	@NamedQuery(name="UserMaster.getUserMasterData",query="SELECT ur FROM UserMaster ur ORDER BY ur.id ASC"),
	@NamedQuery(name="UserMaster.uniqueUserName",query="SELECT ur FROM UserMaster ur WHERE ur.user_login_name=:userloginName"),
	@NamedQuery(name="UserMaster.getUserMaster",query="SELECT ur FROM UserMaster ur WHERE ur.id=:id"),
	@NamedQuery(name="UserMaster.getUserMasterLogin",query="SELECT ur FROM UserMaster ur WHERE ur.user_login_name=:user_login_name"),
	@NamedQuery(name="UserMaster.deleteUser",query="DELETE FROM UserMaster u WHERE u.id=:id"),
	@NamedQuery(name="UserMaster.uniqueUserMasterForEdit",query="SELECT ur FROM UserMaster ur WHERE ur.id!=:id AND upper(ur.user_login_name) like upper(:user_login_name) "),
	@NamedQuery(name="UserMaster.getOfficialName",query="SELECT ur.user_name,d.designation FROM UserMaster ur INNER JOIN ur.userDesignationEntity d WHERE ur.designation=d.id AND upper(ur.user_login_name) LIKE upper(:user_login_name)"),

	
})
public class UserMaster extends BaseEntity implements Serializable{
	@Id
	@SequenceGenerator(name = "bsw_user_seq", sequenceName = "BSW_USERS_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "bsw_user_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="USER_ROLE_ID")
	private Integer user_role_id;
	
	@OneToOne
	@JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private UserRolesEntity userRolesEntity;
	
	@Column(name="EMAIL_ID")
	private String email_id;
	
	@Column(name="MOBILENO")
	private String mobileno;
	
	@Column(name="DESIGNATION")
	private Integer designation;
	
	@OneToOne
	@JoinColumn(name = "DESIGNATION", referencedColumnName = "ID", insertable = false, updatable = false)
	private UserDesignationEntity userDesignationEntity;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="USER_LOGIN_NAME")
	private String user_login_name;
	
	public UserDesignationEntity getUserDesignationEntity() {
		return userDesignationEntity;
	}

	public void setUserDesignationEntity(UserDesignationEntity userDesignationEntity) {
		this.userDesignationEntity = userDesignationEntity;
	}

	@Column(name="USER_NAME")
	private String user_name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserRolesEntity getUserRolesEntity() {
		return userRolesEntity;
	}

	public void setUserRolesEntity(UserRolesEntity userRolesEntity) {
		this.userRolesEntity = userRolesEntity;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public Integer getDesignation() {
		return designation;
	}

	public void setDesignation(Integer designation) {
		this.designation = designation;
	}

	public String getPassword() {
		if(password!=null){
			return (String)PasswordDecryptBase64.passwordDecode(password.getBytes());
		}
			return password;
	}

	public void setPassword(String password) {
		this.password = PasswordEncryptBase64.passwordEncode(password);
	}

	public String getUser_login_name() {
		return user_login_name;
	}

	public void setUser_login_name(String user_login_name) {
		this.user_login_name = user_login_name;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public Integer getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(Integer user_role_id) {
		this.user_role_id = user_role_id;
	}
	
}

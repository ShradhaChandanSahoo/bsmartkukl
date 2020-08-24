package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="app_user_role")

@NamedQueries({
	@NamedQuery(name="UserRole.findUserRoleBasedONUserId",query="SELECT ur from UserRole ur where ur.userId=:userId")
	
})
public class UserRole {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_roleid", unique = true, nullable = false, precision = 10, scale = 0)
	private int userroleId;
	
	@Column(name="user_id")
	private int userId;
	
	@Column(name="role_name")
	private String roleName;

	public int getUserroleId() {
		return userroleId;
	}

	public void setUserroleId(int userroleId) {
		this.userroleId = userroleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

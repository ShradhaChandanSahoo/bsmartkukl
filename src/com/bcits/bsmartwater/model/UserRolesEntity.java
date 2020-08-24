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
@Table(name="BSW_USER_ROLES")
@NamedQueries({
	@NamedQuery(name="UserRolesEntity.getUserRolesData",query="SELECT U FROM UserRolesEntity U ORDER BY U.id ASC"),
	@NamedQuery(name="UserRolesEntity.uniqueUserRoleType",query="SELECT U FROM UserRolesEntity U WHERE U.user_type_id=:usertypeid"),
	@NamedQuery(name="UserRolesEntity.uniqueUserRoleTypeForEdit",query="SELECT U FROM UserRolesEntity U WHERE U.user_type_id=:usertypeid AND U.id!=:id"),
	@NamedQuery(name="UserRolesEntity.getUserRoles",query="SELECT ur.id,ur.userTypeEntity.user_type FROM UserRolesEntity ur"),
})
public class UserRolesEntity extends BaseEntity {

	@Id
	@SequenceGenerator(name = "user_roles_seq", sequenceName = "BSW_USER_ROLES_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "user_roles_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="USER_TYPE_ID")
	private Integer user_type_id;
	
	@OneToOne
	@JoinColumn(name = "USER_TYPE_ID", referencedColumnName = "ID", insertable = false, updatable = false)
	private UserTypeEntity userTypeEntity;
	
	public UserTypeEntity getUserTypeEntity() {
		return userTypeEntity;
	}

	public void setUserTypeEntity(UserTypeEntity userTypeEntity) {
		this.userTypeEntity = userTypeEntity;
	}

	@Column(name="USER_ROLES")
	private String user_roles;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getUser_type_id() {
		return user_type_id;
	}

	public void setUser_type_id(Integer user_type_id) {
		this.user_type_id = user_type_id;
	}

	public String getUser_roles() {
		return user_roles;
	}

	public void setUser_roles(String user_roles) {
		this.user_roles = user_roles;
	}
	
}

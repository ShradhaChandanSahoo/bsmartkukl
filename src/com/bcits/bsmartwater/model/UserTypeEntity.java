package com.bcits.bsmartwater.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@SuppressWarnings("serial")
@Entity
@Table(name="BSW_USER_TYPE")
@NamedQueries({
	@NamedQuery(name="UserTypeEntity.readUserType",query="SELECT U FROM UserTypeEntity U ORDER BY U.id ASC"),
	@NamedQuery(name="UserTypeEntity.uniqueUserType",query="SELECT U FROM UserTypeEntity U WHERE upper(U.user_type) like upper(:usertype) ORDER BY U.id DESC"),
	@NamedQuery(name="UserTypeEntity.uniqueUserTypeForEdit",query="SELECT U FROM UserTypeEntity U WHERE upper(U.user_type) LIKE upper(:usertype) AND U.id!=:id ORDER BY U.id DESC"),
	@NamedQuery(name="UserTypeEntity.getUserTypeWithId",query="SELECT U.id,U.user_type FROM UserTypeEntity U ORDER BY U.user_type ASC"),
})
public class UserTypeEntity extends BaseEntity {

	@Id
	@SequenceGenerator(name = "user_type_seq", sequenceName = "BSW_USER_TYPE_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "user_type_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="USER_TYPE")
	private String user_type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	
}

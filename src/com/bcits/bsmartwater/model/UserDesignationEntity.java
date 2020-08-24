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
@Table(name="BSW_DESIGNATION")
@NamedQueries({
	
	@NamedQuery(name="UserDesignationEntity.readUserDesignation",query="SELECT U FROM UserDesignationEntity U ORDER BY U.id ASC"),
	@NamedQuery(name="UserDesignationEntity.uniqueUserDesignation",query="SELECT U FROM UserDesignationEntity U WHERE upper(U.designation) like upper(:userdesignation) ORDER BY U.id DESC"),
	@NamedQuery(name="UserDesignationEntity.uniqueUserDesignationForEdit",query="SELECT U FROM UserDesignationEntity U WHERE upper(U.designation) LIKE upper(:userdesignation) AND U.id!=:id ORDER BY U.id DESC"),
	@NamedQuery(name="UserDesignationEntity.getUserDesignationWithId",query="SELECT U.id,U.designation FROM UserDesignationEntity U ORDER BY U.designation ASC"),
})
public class UserDesignationEntity extends BaseEntity {

	@Id
	@SequenceGenerator(name = "user_desig_seq", sequenceName = "BSW_DESIGNATION_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "user_desig_seq") 
	@Column(name="ID")
	private int id;
	
	@Column(name="DESIGNATION")
	private String designation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

}

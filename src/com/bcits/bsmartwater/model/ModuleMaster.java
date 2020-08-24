package com.bcits.bsmartwater.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="BSW_MODULE_MASTER")

@NamedQueries({
	@NamedQuery(name="ModuleMaster.getModuleNames",query="SELECT m.module_name FROM ModuleMaster m"),
	@NamedQuery(name="ModuleMaster.getAllModulesForLeftMenu",query="SELECT m FROM ModuleMaster m WHERE m.module_name IN :module_name"),
})
public class ModuleMaster implements Serializable {
	
	@Id
	@Column(name="ID")
	private int id;
	
	@Column(name="MODULE_NAME")
	private String module_name;

	@Column(name="MODULE_ID")
	private String module_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModule_name() {
		return module_name;
	}

	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}

	public String getModule_id() {
		return module_id;
	}

	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}
	
}

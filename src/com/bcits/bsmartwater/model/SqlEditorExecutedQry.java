package com.bcits.bsmartwater.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="SQL_EDITOR_QUERY")
public class SqlEditorExecutedQry {
	
	@Id
	@SequenceGenerator(name = "sql_editor_query_seq", sequenceName = "SQL_EDITOR_QUERY_SEQ",allocationSize=1) 
	@GeneratedValue(generator = "sql_editor_query_seq") 
	@Column(name = "ID", nullable = false, precision = 6, scale = 0)
	private int id;
	
	@Column(name="SQL_QRY")
	private String sql_qry;
	
	@Column(name="EXECUTED_BY")
	private String executed_by;
	
	@Column(name="EXECUTED_DATE")
	private Date executed_date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSql_qry() {
		return sql_qry;
	}

	public void setSql_qry(String sql_qry) {
		this.sql_qry = sql_qry;
	}

	public String getExecuted_by() {
		return executed_by;
	}

	public void setExecuted_by(String executed_by) {
		this.executed_by = executed_by;
	}

	public Date getExecuted_date() {
		return executed_date;
	}

	public void setExecuted_date(Date executed_date) {
		this.executed_date = executed_date;
	}
	

}

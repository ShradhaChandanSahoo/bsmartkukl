package com.bcits.bsmartwater.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class SmsCredentialsDetailsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String number;
	
	private String userName;
	
	private String message;
	
	private String smsGatewayURL;
	
	private String smsGatewaySid;
	
	private String smsGatewayFL;
	
	private String smsGatewayGwid;
	
	private String smsGatewayUsername;
	
	private String smsGatewayPwd;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSmsGatewayURL() {
		return smsGatewayURL;
	}

	public String getSmsGatewaySid() {
		return smsGatewaySid;
	}

	public String getSmsGatewayFL() {
		return smsGatewayFL;
	}

	public String getSmsGatewayGwid() {
		return smsGatewayGwid;
	}

	public String getSmsGatewayUsername() {
		return smsGatewayUsername;
	}

	public String getSmsGatewayPwd() {
		return smsGatewayPwd;
	}

	public void setSmsGatewayURL(String smsGatewayURL) {
		this.smsGatewayURL = smsGatewayURL;
	}

	public void setSmsGatewaySid(String smsGatewaySid) {
		this.smsGatewaySid = smsGatewaySid;
	}

	public void setSmsGatewayFL(String smsGatewayFL) {
		this.smsGatewayFL = smsGatewayFL;
	}

	public void setSmsGatewayGwid(String smsGatewayGwid) {
		this.smsGatewayGwid = smsGatewayGwid;
	}

	public void setSmsGatewayUsername(String smsGatewayUsername) {
		this.smsGatewayUsername = smsGatewayUsername;
	}

	public void setSmsGatewayPwd(String smsGatewayPwd) {
		this.smsGatewayPwd = smsGatewayPwd;
	}
	
}

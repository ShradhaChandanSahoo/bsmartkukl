package com.bcits.bsmartwater.utils;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Component;

@Component
public class EmailCredentialsDetailsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EmailCredentialsDetailsBean()throws Exception{
		
	}
	
	private String toAddressEmail;
	
	private String messageContent;
	
	private String mailId;
	
	private String mailIdPwd;
	
	private String mailSmtpHost;
	
	private String mailSmtpSocketFactoryPort;
	
	private final String mailSmtpSocketFactoryClass = ResourceBundle.getBundle("configuration").getString("mail.smtp.socketFactory.class");
	
	private String mailSmtpAuth;
	
	private String mailSmtpPort;
	
	private final InternetAddress fromAddress = new InternetAddress(getMailId(),ResourceBundle.getBundle("configuration").getString("Email.gateWay.displayName"));
	
	private String mailSubject;

	public String getToAddressEmail() {
		return toAddressEmail;
	}

	public void setToAddressEmail(String toAddressEmail) {
		this.toAddressEmail = toAddressEmail;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	
	public String getMailId() {
		return mailId;
	}

	public String getMailIdPwd() {
		return mailIdPwd;
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public String getMailSmtpSocketFactoryPort() {
		return mailSmtpSocketFactoryPort;
	}

	public String getMailSmtpSocketFactoryClass() {
		return mailSmtpSocketFactoryClass;
	}

	public String getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	public String getMailSmtpPort() {
		return mailSmtpPort;
	}

	public InternetAddress getFromAddress() {
		return fromAddress;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public void setMailIdPwd(String mailIdPwd) {
		this.mailIdPwd = mailIdPwd;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

	public void setMailSmtpSocketFactoryPort(String mailSmtpSocketFactoryPort) {
		this.mailSmtpSocketFactoryPort = mailSmtpSocketFactoryPort;
	}

	public void setMailSmtpAuth(String mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}

	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}
	
}

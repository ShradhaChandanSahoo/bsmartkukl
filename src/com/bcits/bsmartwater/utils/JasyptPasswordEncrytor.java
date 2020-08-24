package com.bcits.bsmartwater.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptPasswordEncrytor {

	public static void main(String[] args) {
		StandardPBEStringEncryptor strongEncryptor = new StandardPBEStringEncryptor();
	    strongEncryptor.setPassword("bcits");
	    String encryptedText = strongEncryptor.encrypt("sticb_BcitsK4");
	    System.out.println("Encrypted text is: " + encryptedText);
	    
	}
}



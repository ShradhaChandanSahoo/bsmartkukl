package com.bcits.bsmartwater.utils;

import org.springframework.security.crypto.codec.Base64;

public class PasswordDecryptBase64 {

	public static String passwordDecode(byte[] password){
		byte[] valueDecoded= Base64.decode(password);
    	return new String(valueDecoded);
	}
}

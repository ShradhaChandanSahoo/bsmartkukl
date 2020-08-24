package com.bcits.bsmartwater.utils;

import org.springframework.security.crypto.codec.Base64;

public class PasswordEncryptBase64 {

	public static String passwordEncode(String password){
		byte[]   bytesEncoded = Base64.encode(password.getBytes());
		return new String(bytesEncoded);
	}
}

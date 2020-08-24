package com.bcits.bsmartwater.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionData {

	public static Map<String, Object> getUserDetails() {
		Map<String, Object> userSessionDetails = new HashMap<String, Object>();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		String userID = "";
		if(auth!=null)
			userID = auth.getName();
		else
			userID= "Robot User";
		userSessionDetails.put("userID", userID.toLowerCase());
		return userSessionDetails;
	}

}

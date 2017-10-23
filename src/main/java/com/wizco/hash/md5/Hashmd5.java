package com.wizco.hash.md5;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.wizco.entity.User;

public class Hashmd5 {
	public String convert(User user) throws NoSuchAlgorithmException {
		try {			
			MessageDigest m = MessageDigest.getInstance("SHA-256");
		    m.update(user.getPassword().getBytes("UTF-8"),0,user.getPassword().length());
		    String md5 = new BigInteger(1,m.digest()).toString(64);
		    return md5;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public String convertLogin(String password) throws NoSuchAlgorithmException {
		try {			
			MessageDigest m = MessageDigest.getInstance("SHA-256");
		    m.update(password.getBytes("UTF-8"),0,password.length());
		    String md5 = new BigInteger(1,m.digest()).toString(64);
		    return md5;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
}

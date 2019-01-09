package com.auth.SHA256Test;

import org.junit.Test;

import com.auth.service.SHA256;

public class TestSHA256 {

	@Test
	public void testSAH256() {
		
		SHA256 sha256 = SHA256.getInstance();
		
		String str = "hunter2";
		
		String sha256str = sha256.encodeSHA256(str);
		System.out.println(sha256str);
	}
}

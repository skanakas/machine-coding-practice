package com.digilock.service.common;

import org.apache.commons.lang3.RandomStringUtils;

public class SecretCodeGenerator {

	public static String generateSecretCode(int maxLength) {
		return RandomStringUtils.randomAlphanumeric(1, maxLength+1);
	}

}

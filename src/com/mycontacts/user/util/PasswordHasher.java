/**
 * Handles password hashing so plain passwords are never stored directly. Uses SHA-256
 * to produce a hex-encoded hash. Kept modular so hashing can be replaced if needed.
 */
package com.mycontacts.user.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordHasher {

	private PasswordHasher() {
	}

	public static String hash(String plainPassword) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = digest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexBuilder = new StringBuilder();
			for (byte b : hashedBytes) {
				hexBuilder.append(String.format("%02x", b));
			}
			return hexBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Error in hashing password", e);
		}
	}
}
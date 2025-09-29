package br.dev.farmes.modules.auth.util;

public class MD5 {

	public static String generateMD5(String input) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");

			byte[] messageDigest = md.digest(input.getBytes());

			java.math.BigInteger bigInt = new java.math.BigInteger(1, messageDigest);

			String hashtext = bigInt.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

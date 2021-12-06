package algorithms;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashAlgorithm {

	public static byte[] doHash(String data, String algorithm)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		byte[] hash = digest.digest(data.getBytes("UTF-8"));
		return hash;
	}

	public static String bytesToHex(byte[] hash) {
		StringBuilder hexString = new StringBuilder(2 * hash.length);
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String text = "Phung Minh Datxffg";
		byte[] hash = HashAlgorithm.doHash(text, "HMAC");
		System.out.println(Base64.getEncoder().encodeToString(hash));
	}
}

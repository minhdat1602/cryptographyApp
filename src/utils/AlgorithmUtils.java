package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class AlgorithmUtils {

	/**
	 * function to hashing data
	 * @param data
	 * @param algorithm
	 * @return hashed data are converted to Hexadecimal Number
	 */
	public static String doHash(String data, String algorithm)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		byte[] hash = digest.digest(data.getBytes());
		return bytesToHex(hash);
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

	/**
	 * function to generate key pair include Private and Public Key
	 * 
	 * @return KeyPair Object
	 */
	public static KeyPair generateKeyPair() {
		try {
			/* Generate a key pair */
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "SUN");
			SecureRandom random = SecureRandom.getInstanceStrong();
			keyGen.initialize(2048, random);
			KeyPair pair = keyGen.generateKeyPair();

			return pair;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * function to read data of the File path
	 * 
	 * @param file path
	 * @return data
	 */
	public static String readFile(String fileName) {
		File file = new File(fileName);
		return readFile(file);
	}

	/**
	 * function to read data of the File
	 * 
	 * @param File Object
	 * @return data
	 */
	public static String readFile(File file) {
		InputStream fis = null;
		try {
			fis = new FileInputStream(file);

			int bytesToRead = (int) file.length();
			int bytesRead = 0;
			byte[] in = new byte[bytesToRead];
			while (bytesRead < bytesToRead) {
				int result = fis.read(in, bytesRead, bytesToRead - bytesRead);
				if (result == -1)
					break;
				bytesRead += result;
			}

			fis.close();
			System.out.printf("len: %d | read %d\n", bytesToRead, bytesRead);

			return new String(in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * function to write data to File path
	 * 
	 * @param file  path
	 * @param bytes
	 */
	public static void writeFile(String filename, byte[] data) {
		File file = new File(filename);
		writeFile(file, data);
	}

	/**
	 * function to writing data to the File
	 * 
	 * @param File  Object
	 * @param bytes to write
	 */
	public static void writeFile(File file, byte[] data) {
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(file);

			fos.write(data);
			fos.flush();
			fos.close();

			System.out.printf("len: %d write: %d\n", data.length, file.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static PublicKey createPublicKey(byte[] publicKeyBytes, String cryptAlgorithm) {
		KeyFactory keyFactory;
		EncodedKeySpec publicKeySpec;
		try {
			publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			keyFactory = KeyFactory.getInstance(cryptAlgorithm);
			return keyFactory.generatePublic(publicKeySpec);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static PrivateKey createPrivateKey(byte[] privateKeyBytes, String cryptAlgorithm) {
		EncodedKeySpec privateKeySpec;
		KeyFactory keyFactory;
		PrivateKey privateKey = null;
		try {
			privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			keyFactory = KeyFactory.getInstance(cryptAlgorithm);
			privateKey = keyFactory.generatePrivate(privateKeySpec);
			return privateKey;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return null;
		}
	}
}

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
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class AlgorithmUtils {

	public static void generateKeyPair(String dpath) {
		try {
			/* Generate a key pair */
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "SUN");
			SecureRandom random = SecureRandom.getInstanceStrong();
			keyGen.initialize(2048, random);
			KeyPair pair = keyGen.generateKeyPair();

			PrivateKey privateKey = pair.getPrivate();
			PublicKey publicKey = pair.getPublic();

			writeFile(dpath + "/privateKey.txt", privateKey.getEncoded());
			writeFile(dpath + "/publicKey.txt", publicKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
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

	public static byte[] readFile(String fileName) {
		File file = new File(fileName);
		return readFile(file);
	}

	public static byte[] readFile(File file) {
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

			return in;
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

	public static void writeFile(String filename, byte[] data) {
		File file = new File(filename);
		writeFile(file, data);
	}

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
}

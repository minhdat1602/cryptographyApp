package algorithms;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import utils.AlgorithmUtils;

public class DigitalSignature {

	private static final String CRYPT_ALGORITHM = "RSA";
	private static final String SIGN_ARG = "SHA256withRSA";
	private static final int KEYSIZE = 2048;

	
	// SIGN SECTION
	/**
	 * function to generate signature from data's file
	 * @param dataPath: file path you want to sign
	 * @param privateKeyPath: private key file path
	 * @return signature
	 */
	public static String signFile(String dataPath, String privateKeyPath) {
		String dataStr = AlgorithmUtils.readFile(dataPath);
		String privateKeyBase64 = AlgorithmUtils.readFile(privateKeyPath);
		
		return signBase64(dataStr, privateKeyBase64); 
	}
	
	public static String signBase64(String inputStr, String privateKeyBase64) {
		try {
			byte[] dataBytes = inputStr.getBytes();
			byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);

			// create a Private Key from the Private Key File
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(CRYPT_ALGORITHM);
			PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

			return Base64.getEncoder().encodeToString(sign(dataBytes, privateKey));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static byte[] sign(byte[] dataBytes, PrivateKey privateKey) {
		try {
			/* Create a Signature object and initialize it with the private key */
			Signature dsaSign = Signature.getInstance(SIGN_ARG);
			dsaSign.initSign(privateKey);
			/* Update and sign the data */
			dsaSign.update(dataBytes);
			// Now that all the data to be signed has been read in, generate a signature for
			// it
			byte[] signBytes = dsaSign.sign();
			
			return signBytes;
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
			return null;
		}
	}
	
	// VERIFY SECTION
	public static boolean verifyFile(String dataPath, String signaturePath, String publicKeyPath) {
		String dataStr = AlgorithmUtils.readFile(dataPath);
		String signatureBase64 = AlgorithmUtils.readFile(signaturePath);
		String publicKeyBase64 = AlgorithmUtils.readFile(publicKeyPath);
		
		return verifyBase64(dataStr, signatureBase64, publicKeyBase64);
		
	}

	public static boolean verifyBase64(String dataStr, String signatureBase64, String publicKeyBase64) {
		try {

			byte[] dataBytes = dataStr.getBytes();
			byte[] signBytes = Base64.getDecoder().decode(signatureBase64);
			byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
			
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(CRYPT_ALGORITHM);

			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

			return verify(dataBytes, signBytes, publicKey);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean verify(byte[] dataBytes, byte[] signBytes, PublicKey publicKey) {
		try {
			/* create a Signature object and initialize it with the public key */
			Signature sig = Signature.getInstance(SIGN_ARG);
			sig.initVerify(publicKey);
			/* Update and verify the data */
			sig.update(dataBytes);

			boolean verifies = sig.verify(signBytes);
			System.out.println("signature verifies: " + verifies);
			return verifies;
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
			return false;
		}
	}

	public static void main(String[] args) throws IOException {
	}

}

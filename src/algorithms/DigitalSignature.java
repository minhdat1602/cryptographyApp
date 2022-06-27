package algorithms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

import utils.AlgorithmUtils;

public class DigitalSignature {
	private static final String HS = "/";
	private static final String PRIVATE_FNAME = "private.txt";
	private static final String PUBLIC_FNAME = "public.txt";
	private static final String SIGN_FNAME = "signature.txt";
	private static final String HASHSTR = "hashstr.txt";
	private static final String DPATH_DEFAULT = "src/files";
	private static final String CHARSET_DEFAULT = "UTF-8";

	private static final String CRYPT_ALGORITHM = "RSA";
	private static final String SIGN_ARG = "SHA256withRSA";
	private static final int KEYSIZE = 2048;

	private PrivateKey privateKey;
	private PublicKey publicKey;
	private static String dpath = "src";

	public DigitalSignature() {
		dpath = DPATH_DEFAULT;
	}

	public DigitalSignature(String dpath) {
		dpath = dpath;
	}

	void generateKeyPair() {
		/* Generate a key pair */
		KeyPairGenerator keyGen;
		try {
			keyGen = KeyPairGenerator.getInstance(CRYPT_ALGORITHM);
			SecureRandom random = SecureRandom.getInstanceStrong();
			keyGen.initialize(KEYSIZE, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();

			this.privateKey = privateKey;
			this.publicKey = publicKey;

			AlgorithmUtils.writeFile(dpath + HS + PRIVATE_FNAME, privateKey.getEncoded());
			AlgorithmUtils.writeFile(dpath + HS + PUBLIC_FNAME, publicKey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * function to create signature from Hashed Data and Generated Private Key
	 * 
	 * @param hashed  file path
	 * @param private key file path
	 */
	public static byte[] signFile(String dataFileName, String privateKeyFileName) {
		byte[] dataBytes = AlgorithmUtils.readFile(dataFileName);
		byte[] privateKeyBytes = AlgorithmUtils.readFile(privateKeyFileName);

		// create a Private Key from the Private Key File
		PrivateKey privateKey = AlgorithmUtils.createPrivateKey(privateKeyBytes, CRYPT_ALGORITHM);

		return sign(dataBytes, privateKey);
	}

	public static byte[] signBytes(byte[] dataBytes, byte[] privateKeyBytes) {

		// create a Private Key from the Private Key File
		PrivateKey privateKey = AlgorithmUtils.createPrivateKey(privateKeyBytes, CRYPT_ALGORITHM);

		return sign(dataBytes, privateKey);
	}

	public static byte[] signStr(String dateStr, String privateKyeStr) {
		try {
			byte[] dataBytes = dateStr.getBytes(CHARSET_DEFAULT);
			byte[] privateKeyBytes = privateKyeStr.getBytes(CHARSET_DEFAULT);

			// create a Private Key from the Private Key File
			PrivateKey privateKey = AlgorithmUtils.createPrivateKey(privateKeyBytes, CRYPT_ALGORITHM);

			return sign(dataBytes, privateKey);
		} catch (UnsupportedEncodingException e) {
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
			/* Save the signature in a file */
			AlgorithmUtils.writeFile(dpath + HS + SIGN_FNAME, signBytes);
			return signBytes;
		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
			return null;
		}
	}

	public static boolean verifyFile(String dataFileName, String signatureFileName, String publicKeyFileName) {
		byte[] dataBytes = AlgorithmUtils.readFile(dataFileName);
		byte[] signBytes = AlgorithmUtils.readFile(signatureFileName);
		byte[] publicKeyBytes = AlgorithmUtils.readFile(publicKeyFileName);

		try {
			PublicKey publicKey = AlgorithmUtils.createPublicKey(publicKeyBytes, CRYPT_ALGORITHM);

			return verify(dataBytes, signBytes, publicKey);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean verifyStr(String dataStr, String signatureStr, String publicKeyStr) {
		try {
			byte[] dataBytes = dataStr.getBytes(CHARSET_DEFAULT);
			byte[] signBytes = signatureStr.getBytes(CHARSET_DEFAULT);
			byte[] publicKeyBytes = publicKeyStr.getBytes(CHARSET_DEFAULT);

			PublicKey publicKey = AlgorithmUtils.createPublicKey(publicKeyBytes, CRYPT_ALGORITHM);

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
		String hashstr = "src/files/hashstr.txt";
		String privateKey = "src/files/private.txt";
		String publicKey = "src/files/public.txt";
		String signature = "src/files/signature.txt";
		
		byte[] signaturebytes = signFile(hashstr, privateKey);
		AlgorithmUtils.writeFile(signature, signaturebytes);
		
		AlgorithmUtils.writeFile(signature, "HEHE".getBytes());
		
		boolean verify = verifyFile(hashstr, signature, publicKey);
		System.out.println("verify: " + verify);
		
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public String getSpath() {
		return dpath;
	}

	public void setSpath(String dpath) {
		dpath = dpath;
	}
}

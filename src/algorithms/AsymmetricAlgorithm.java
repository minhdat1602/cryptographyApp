package algorithms;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class AsymmetricAlgorithm {

	private String algorithm;
	private int keySize;

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public int getKeySize() {
		return keySize;
	}

	public void setKeySize(int keySize) {
		this.keySize = keySize;
	}

	public AsymmetricAlgorithm(String algorithm, int keySize) {
		this.algorithm = algorithm;
		this.keySize = keySize;
	}

	public String getPrivateKey(PrivateKey privateKey) {
		return Base64.getEncoder().encodeToString(privateKey.getEncoded());
	}

	public String getPrivateKey(PublicKey publicKey) {
		return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	}

	public PrivateKey generatePrivateKey(String privateKeyStr) throws Exception {
		byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);

		EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		return keyFactory.generatePrivate(privateKeySpec);
	}

	public PublicKey generatePublicKey(String publicKeyStr) throws Exception {
		byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);

		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

		return keyFactory.generatePublic(publicKeySpec);
	}

	/**
	 * Generate public and private keys
	 * 
	 * @return KeyPair object
	 * @throws NoSuchAlgorithmException
	 */
	public KeyPair generateKey() throws NoSuchAlgorithmException {
		SecureRandom srandom = new SecureRandom();
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
		
		keyPairGenerator.initialize(keySize, srandom);

		return keyPairGenerator.generateKeyPair();
	}

	public String doEncryption(String plainText, String publicKeyStr) throws Exception {
		PublicKey publicKey = generatePublicKey(publicKeyStr); // convert String => privateKey

		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] result = cipher.doFinal(plainText.getBytes());

		return Base64.getEncoder().encodeToString(result); // Base64 encode byte[] => String
	}

	public String doDecryption(String cipherText, String privateKeyStr) throws Exception {
		PrivateKey privateKey = generatePrivateKey(privateKeyStr); // convert String => PublicKey

		byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText); // Base64 decode String => byte[]

		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] result = cipher.doFinal(cipherTextBytes);

		return new String(result);
	}

	public static void main(String[] args) throws Exception {
		AsymmetricAlgorithm asymmetric = new AsymmetricAlgorithm("RSA", 1024);
		KeyPair keypair = asymmetric.generateKey();
		String publicKey = Base64.getEncoder().encodeToString(keypair.getPublic().getEncoded());
		System.out.println("Public: " + publicKey);
		String privateKey = Base64.getEncoder().encodeToString(keypair.getPrivate().getEncoded());
		System.out.println("Private: " + privateKey);

		String plaintext = "Phùng Minh Đạt";
		System.out.println("PlainText: " + plaintext);

		String encrypt = asymmetric.doEncryption(plaintext, publicKey);
		System.out.println("Encrypt: " + encrypt);

		String decrypt = asymmetric.doDecryption(encrypt, privateKey);
		System.out.println("Decrypt: " + decrypt);
	}

}

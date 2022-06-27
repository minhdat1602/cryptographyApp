package algorithms;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SymmetricAlgorithm {
	private final String CHARSET = "UTF-8";

	private SecretKey key;

	private String algorithm;
	private int keySize;
	private String mode;
	private String padding;

	public SymmetricAlgorithm(String cryptoType, int keySize) {
		super();
		this.algorithm = cryptoType;
		this.keySize = keySize;
	}

	private String getInstanceKeyGenerator() {
		String result = "";
		result += algorithm;
		if (mode != null && !mode.equalsIgnoreCase("None")) {
			result += "/" + mode;
			if (padding != null)
				result += "/" + padding;
		}
		return result;
	}

	public IvParameterSpec generateIv(int size) {
		byte[] iv = new byte[size];
		new SecureRandom().nextBytes(iv);
		return new IvParameterSpec(iv);
	}

	public String generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		System.out.println("INSTANCE: " + getInstanceKeyGenerator());
		keyGenerator.init(keySize);
		key = keyGenerator.generateKey();
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public String encrypt(String text, String key) throws Exception {

		byte[] decodedKey = Base64.getDecoder().decode(key);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);

		Cipher cipher = Cipher.getInstance(getInstanceKeyGenerator());

		cipher.init(Cipher.ENCRYPT_MODE, originalKey);

		byte[] plaintext = text.getBytes(CHARSET);
		byte[] cipherText = cipher.doFinal(plaintext);

		return Base64.getEncoder().encodeToString(cipherText); // base64 encoding
	}

	public String decrypt(String text, String key) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(key);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);

		Cipher cipher = Cipher.getInstance(getInstanceKeyGenerator());

		cipher.init(Cipher.DECRYPT_MODE, originalKey);

		byte[] cipherText = Base64.getDecoder().decode(text); // base64 decoding
		byte[] plaintext = cipher.doFinal(cipherText);

		return new String(plaintext, CHARSET);
	}

	public static void main(String[] args) throws Exception {
		SymmetricAlgorithm crypto = new SymmetricAlgorithm();

		crypto.setAlgorithm("Blowfish");
		crypto.setKeySize(56);
//		crypto.setMode("ECB");
//		crypto.setPadding("NoPadding");
//		crypto.setPadding("PKCS5Padding");
//		crypto.setPadding("ISO10126Padding");
//		crypto.setPadding("BitPadding");

		String input = "Phùng Minh Đạt";
//		String input = FIleUtils.readFile(new File("D:\\MS001.jpg"));
		String key = crypto.generateKey();
		System.out.println("Key: " + key);

		String encrypt = crypto.encrypt(input, key);
		System.out.println("encrypt: " + encrypt);
		String decrypt = crypto.decrypt(encrypt, key);
		System.out.println("decrypt: " + decrypt);
//		FIleUtils.writeFile(decrypt,new File("D:\\MS003.jpg"));
	}

	public SymmetricAlgorithm() {
		super();
	}

	public SecretKey getKey() {
		return key;
	}

	public void setKey(SecretKey key) {
		this.key = key;
	}

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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getPadding() {
		return padding;
	}

	public void setPadding(String padding) {
		this.padding = padding;
	}

}

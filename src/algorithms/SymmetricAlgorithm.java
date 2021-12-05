package algorithms;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
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
		if(!mode.equalsIgnoreCase("None")) {
			if (mode != null)
				result += "/" + mode;
			if (padding != null)
				result += "/" + padding;
		}
		return result;
	}

	public String generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		System.out.println("INSTANCE: " + getInstanceKeyGenerator());
		keyGenerator.init(keySize);
		key = keyGenerator.generateKey();
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public String encrypt(String text) throws Exception {
		if (this.key == null)
			return null;

		Cipher cipher = Cipher.getInstance(getInstanceKeyGenerator());
		cipher.init(Cipher.ENCRYPT_MODE, this.key);

		byte[] plaintext = text.getBytes(CHARSET);
		byte[] cipherText = cipher.doFinal(plaintext);

		return Base64.getEncoder().encodeToString(cipherText); // base64 encoding
	}

	public String decrypt(String text, String key) throws Exception {
		if (key == null || text.trim().isEmpty())
			return null;
		
		Cipher cipher = Cipher.getInstance(getInstanceKeyGenerator());

		byte[] decodedKey = Base64.getDecoder().decode(key);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);

		cipher.init(Cipher.DECRYPT_MODE, originalKey);

		byte[] cipherText = Base64.getDecoder().decode(text); // base64 decoding
		byte[] plaintext = cipher.doFinal(cipherText);
		
		return new String(plaintext, CHARSET);
	}

	public static void main(String[] args) throws Exception {
		SymmetricAlgorithm crypto = new SymmetricAlgorithm();
		
		crypto.setAlgorithm("AES");
		crypto.setKeySize(192);
		crypto.setMode("ECB");
		crypto.setPadding("PKCS5Padding");
		System.out.println(crypto.getInstanceKeyGenerator());
		
		String input = "Phung Minh Dat";
		String key = crypto.generateKey();
		System.out.println("Key: " + key);
		
		String encrypt = crypto.encrypt(input);
		System.out.println("encrypt: " + encrypt);
		String decrypt = crypto.decrypt(encrypt, key);
		System.out.println("decrypt: " + decrypt);
	}

}

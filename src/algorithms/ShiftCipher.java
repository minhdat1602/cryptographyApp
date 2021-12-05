package algorithms;


public class ShiftCipher {
	
	private int key = 3;
	public ShiftCipher(int key) {
		this.key = key;
	}
	
	private final char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public String shiftEncrypt(String plaintext) {
		return shift(plaintext, 0);
	}

	public String shiftDecrypt(String plaintext) {
		return shift(plaintext, 1);
	}

	/**
	 * Dịch chuyển các ký tự trong một chuỗi bằng Mã hóa dịch chuyển.
	 * 
	 * @param planttext
	 * @param typeCrypt: 0 -> encrypt, 1 -> decrypt
	 * @return ciphertext
	 */
	public String shift(String plaintext, int typeCrypt) {
		if (plaintext == null || plaintext.trim().isEmpty())
			throw new NullPointerException("Plaintext is null or empty");

		char[] letters = plaintext.toCharArray();
		char[] result = new char[letters.length];

		for (int i = 0; i < letters.length; i++) {
			result[i] = isAlphabet(letters[i])
					? (typeCrypt == 0 ? encryptLetter(letters[i]) : decryptletter(letters[i]))
					: letters[i];
		}
		return new String(result);
	}

	private char encryptLetter(char letter) {
		return cryptLetter(letter, 0);
	}

	private char decryptletter(char letter) {
		return cryptLetter(letter, 1);
	}

	/**
	 * Dịch chuyển ký tự trong alphabet
	 * 
	 * @param vd: A, b, C, 1
	 * @return vd: X, y, Z, 1
	 */
	private char cryptLetter(char letter, int typeCrypt) {
		char upLetter = toUppercase(letter);
		int alphabetNum = alphabetNum(upLetter);
		char newLetter = 0;
		if (alphabetNum != -1) {
			if (typeCrypt == 0) {
				// encrypt: index + key % n
				newLetter = ALPHABET[(alphabetNum + key) % ALPHABET.length];
			} else {
				// decrypt: index - key % n
				newLetter = ALPHABET[(alphabetNum - key) % ALPHABET.length];
			}
		}
		return upLetter == letter ? newLetter : Character.toLowerCase(newLetter);
	}

	/**
	 * 
	 * @param a, b, c
	 * @return A, B, C
	 */
	private char toUppercase(char letter) {
		if (letter >= 'a' || letter <= 'z')
			return Character.toUpperCase(letter);
		return letter;
	}

	/**
	 * get index of letter in ALPHABET
	 * 
	 * @param letter
	 * @return 0-25 || -1(No Exist)
	 */
	private int alphabetNum(char letter) {
		for (int i = 0; i < ALPHABET.length; i++)
			if (ALPHABET[i] == letter)
				return i;
		return -1;
	}

	private boolean isAlphabet(char letter) {
		return ((letter >= 'a' && letter <= 'z') || (letter >= 'A' && letter <= 'Z'));
	}

	public static void main(String[] args) {
		int key = 3;
		ShiftCipher sc = new ShiftCipher(key);
		String letters = "ABC - Phung Minh Dat 123";

		String newLetter = sc.shiftEncrypt(letters);
		System.out.println("Encrypt: " + letters + " -> " + newLetter);

		String oldLetter = sc.shiftDecrypt(newLetter);
		System.out.println("Decrypt: " + newLetter + " -> " + oldLetter);
	}

}

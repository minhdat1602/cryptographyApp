package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class SubstitutionCipher {
	public SubstitutionCipher() {
		generateKey();
	}

	private final Character[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	Map<Character, Character> keyValue;
	Map<Character, Character> valueKey;

	public void generateKey() {
		List<Character> alphabet = new ArrayList<Character>(Arrays.asList(ALPHABET));
		keyValue = new HashMap<Character, Character>();
		valueKey = new HashMap<Character, Character>();

		Random random = new Random();

		int index = 0;
		while (alphabet.size() > 0) {
			int randomNum = random.nextInt(alphabet.size());

			valueKey.put(alphabet.get(randomNum), ALPHABET[index]);
			keyValue.put(ALPHABET[index++], alphabet.get(randomNum));
			alphabet.remove(alphabet.get(randomNum));
		}
		System.out.println(keyValue.toString());
		System.out.println(valueKey.toString());
	}

	public String substitutionEncrypt(String plaintext) {
		return substitution(plaintext, 0);
	}

	public String substitutionDecrypt(String plaintext) {
		return substitution(plaintext, 1);
	}

	public String substitution(String plaintext, int typeScrypt) {
		if (plaintext == null || plaintext.trim().isEmpty())
			throw new NullPointerException("Plaintext is null or empty");

		char[] letters = plaintext.toCharArray();
		char[] result = new char[letters.length];

		for (int i = 0; i < letters.length; i++) {
			if (isAlphabet(letters[i]))
				result[i] = typeScrypt == 0 ? keyValue.get(letters[i]) : valueKey.get(letters[i]);
			else
				result[i] = letters[i];
		}
		return new String(result);
	}

	private boolean isAlphabet(char letter) {
		return (letter >= 'A' && letter <= 'Z');
	}

	public static void main(String[] args) {
		SubstitutionCipher sc = new SubstitutionCipher();

		String letters = "ABC 18130305 GHJ XYZ";

		String newLetter = sc.substitutionEncrypt(letters);
		System.out.println("Encrypt: " + letters + " -> " + newLetter);

		String oldLetter = sc.substitutionDecrypt(newLetter);
		System.out.println("Decrypt: " + newLetter + " -> " + oldLetter);
	}
}

package algorithms;

import java.security.PrivateKey;

public interface IDigitalSignature {

	byte[] sign(byte[] data, PrivateKey privateKey, String algorithm);

	byte[] sign(byte[] data, PrivateKey privateKey);
}

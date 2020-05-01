package model.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

/**
 * Crypto.java
 * Interface class that has the following methods.
 */

public interface CryptoCipher {
    /**
     * Encrypt an arbitrary plaintext.
     * @param plaintext
     * @return ciphertext
     * @throws InvalidAlgorithmParameterException 
     * @throws InvalidKeyException 
     */
    byte[] encrypt(byte[] plaintext) throws InvalidKeyException, InvalidAlgorithmParameterException;

    /**
     * Decrypt an arbitrary ciphertext.
     * @param ciphertext
     * @return plaintext
     */
    byte[] decrypt(byte[] ciphertext);

}

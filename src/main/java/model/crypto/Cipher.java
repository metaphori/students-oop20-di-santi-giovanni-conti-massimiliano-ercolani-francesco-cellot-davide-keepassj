package model.crypto;

/**
 * Crypto.java
 * Interface class that has the following methods.
 */

public interface Cipher {
    /**
     * Encrypt an arbitrary plaintext.
     * @param plaintext
     * @return ciphertext
     */
    String encrypt(String plaintext);

    /**
     * Decrypt an arbitrary ciphertext.
     * @param ciphertext
     * @return plaintext
     */
    String decrypt(String ciphertext);

}

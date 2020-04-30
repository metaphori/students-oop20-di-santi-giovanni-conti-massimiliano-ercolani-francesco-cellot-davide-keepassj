package model.crypto;

/**
 * Crypto.java
 * Interface class that has the following methods.
 */

public interface Crypto {
    /**
     * Encrypt an arbitrary plaintext.
     * @param key
     * @param plaintext
     * @return ciphertext
     */
    String encrypt(byte[] key, String plaintext);

    /**
     * Decrypt an arbitrary ciphertext.
     * @param key
     * @param ciphertext
     * @return plaintext
     */
    String decrypt(byte[] key, String ciphertext);
}

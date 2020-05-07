package model.crypto;

/**
 * Crypto.java
 * Interface class that has the following methods.
 */

public interface CryptoCipher {
    /**
     * Encrypt an arbitrary plaintext.
     * @param plaintext This is the plaintext to encrypt.
     * @param iv This is the IV used in the encryption.
     * @return ciphertext.
     */
    byte[] encrypt(byte[] plaintext, byte[] iv);

    /**
     * Decrypt an arbitrary ciphertext.
     * @param ciphertext This is the ciphertext to decrypt.
     * @param iv This is the IV used in the decryption.
     * @return plaintext.
     */
    byte[] decrypt(byte[] ciphertext, byte[] iv);

}

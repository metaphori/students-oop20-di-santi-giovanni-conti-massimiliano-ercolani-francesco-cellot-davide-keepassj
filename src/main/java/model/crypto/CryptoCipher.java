package model.crypto;

import javax.crypto.AEADBadTagException;

/**
 * Crypto.java
 * Interface class that has the following methods.
 */

public interface CryptoCipher {

    /**
     * Encrypt an arbitrary plaintext.
     * @param plaintext This is the plaintext to encrypt.
     * @param iv This is the IV used in the encryption, must be different every time.
     * @return ciphertext.
     */
    byte[] encrypt(byte[] plaintext, byte[] iv);

    /**
     * Decrypt an arbitrary ciphertext.
     * @param ciphertext This is the ciphertext to decrypt.
     * @param iv This is the IV used in the decryption.
     * @return plaintext.
     * @throws AEADBadTagException When the data has not been authenticated.
     */
    byte[] decrypt(byte[] ciphertext, byte[] iv) throws AEADBadTagException;

    /**
     * Set Encryption key.
     * @param key This is the key.
     */
    void setKey(byte[] key);

    /**
     * Get IV size.
     * @return size.
     */
    int getIVSize();

    /**
     * get key size.
     * @return size.
     */
    int getKeySize();

    /**
     * Update data for AEAD cipher.
     * @param data This is the data that must be authenticated and not encrypted.
     */
    void updateAssociatedData(byte[] data);
}

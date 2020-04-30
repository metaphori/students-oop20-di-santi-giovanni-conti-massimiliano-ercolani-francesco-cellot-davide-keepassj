package model.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

public class CipherFactory {

    /**
     * Create an instance of Cipher.
     * @param cipherType
     * @param key
     * @return Cipher Object
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     */
    public CryptoCipher getCipher(final String cipherType, final byte[] key) throws NoSuchAlgorithmException,
        NoSuchPaddingException {
        if (cipherType == null) {
            return null;
        }
        if (cipherType.equalsIgnoreCase("AES")) {
            return new AES(key);
        } else if (cipherType.equalsIgnoreCase("ChaCha20")) {
            return new ChaCha20(key);
        }

        return null;
    }
}

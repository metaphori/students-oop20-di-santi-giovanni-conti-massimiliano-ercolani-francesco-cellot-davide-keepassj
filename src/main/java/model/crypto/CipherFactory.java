package model.crypto;

public class CipherFactory {

    /**
     * Create an instance of Cipher.
     * @param cipherType
     * @param key
     * @return Cipher Object
     */
    public Cipher getCipher(final String cipherType, final byte[] key) {
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

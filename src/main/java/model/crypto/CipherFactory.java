package model.crypto;

public final class CipherFactory {

    private CipherFactory() {
    }
    /**
     * Create an instance of Cipher.
     * @param cipherType This is the string of the cipher to use.
     * @return Cipher Object
     */
    public static CryptoCipher create(final String cipherType) {
        if (cipherType == null) {
            return null;
        }
        if ("AES".equalsIgnoreCase(cipherType)) {
            return new AES();
        } else if ("AESGCM".equalsIgnoreCase(cipherType)) {
            return new AESGCM();
        } else if ("ChaCha20Poly1305".equalsIgnoreCase(cipherType)) {
            return new ChaCha20Poly1305();
        }
        return null;
    }
}

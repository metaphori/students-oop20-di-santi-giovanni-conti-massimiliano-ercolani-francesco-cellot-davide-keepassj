package model.crypto;

public final class CipherFactory {

    private CipherFactory() {
    }
    /**
     * Create an instance of Cipher.
     * @param cipherType
     * @return Cipher Object
     */
    public static CryptoCipher create(final String cipherType) {
        if (cipherType == null) {
            return null;
        }
        if (cipherType.equalsIgnoreCase("AES")) {
            return new AES();
        } else if (cipherType.equalsIgnoreCase("ChaCha20")) {
            return new ChaCha20();
        } else if (cipherType.equalsIgnoreCase("AESGCM")) {
            return new AESGCM();
        } else if (cipherType.equalsIgnoreCase("ChaCha20Poly1305")) {
            return new ChaCha20Poly1305();
        }
        return null;
    }
}

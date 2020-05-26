package model.crypto;

public class CipherFactory {

    /**
     * Create an instance of Cipher.
     * @param cipherType
     * @param key
     * @return Cipher Object
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     */
    public CryptoCipher getCipher(final String cipherType) {
        if (cipherType == null) {
            return null;
        }
        if (cipherType.equalsIgnoreCase("AES")) {
            return new AES();
        } else if (cipherType.equalsIgnoreCase("ChaCha20")) {
            return new ChaCha20();
        }

        return null;
    }
}

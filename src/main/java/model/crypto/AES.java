package model.crypto;

public class AES implements Cipher {

    private byte[] key;

    /**
     * Construct an AES Object.
     * @param key
     */
    public AES(final byte[] key) {
        this.key = key;
    }

    /**
     * Return the AES key.
     * @return key
     */
    public final byte[] getKey() {
        return key;
    }

    /**
     * Set AES key.
     * @param key
     */
    public void setKey(final byte[] key) {
        this.key = key;
    }

    @Override
    public final String encrypt(final String plaintext) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final String decrypt(final String ciphertext) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Encrypt plaintext with AES 256 CBC.
     * @param key
     * @param plaintext
     * @return ciphertext
     */
    public final String shit(final byte[] key, final String plaintext) {
        //TODO
        return null;
    }

}

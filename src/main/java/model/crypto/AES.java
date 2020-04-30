package model.crypto;

public class AES implements Cipher {

    private byte[] key;
    /**
     * Return the AES key.
     * @return key
     */
    public byte[] getKey() {
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

}

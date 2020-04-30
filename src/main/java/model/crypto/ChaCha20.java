package model.crypto;

public class ChaCha20 implements Cipher {

    private byte[] key;

    /**
     * Construct an ChaCha20 Object.
     * @param key
     */
    public ChaCha20(final byte[] key) {
        this.key = key;
    }

    /**
     * Return the ChaCha20 key.
     * @return key
     */
    public final byte[] getKey() {
        return key;
    }

    /**
     * Set ChaCha20 key.
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

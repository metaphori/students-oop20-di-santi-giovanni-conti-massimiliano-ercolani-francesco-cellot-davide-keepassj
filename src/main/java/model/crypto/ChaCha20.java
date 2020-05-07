package model.crypto;

public class ChaCha20 implements CryptoCipher {

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
    public final byte[] encrypt(final byte[] plaintext, final byte[] iv) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final byte[] decrypt(final byte[] ciphertext, final byte[] iv) {
        // TODO Auto-generated method stub
        return null;
    }

}

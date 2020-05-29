package model.crypto;

public class ChaCha20 implements CryptoCipher {

    /**
     * IV_SIZE This is the IV size of ChaCha20.
     */
    public static final int IV_SIZE = 16;
    /**
     * KEY_SIZE This is the key size of ChaCha20.
     */
    public static final int KEY_SIZE = 32;
    private byte[] key;

    /**
     * Construct an ChaCha20 Object.
     */
    public ChaCha20() {
        ;
    }

    public final int getIVSize() {
        return ChaCha20.IV_SIZE;
    }

    public final int getKeySize() {
        return ChaCha20.KEY_SIZE;
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

    @Override
    public void setKey(byte[] key) {
        // TODO Auto-generated method stub
        
    }

}

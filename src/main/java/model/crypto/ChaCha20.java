package model.crypto;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class ChaCha20 implements CryptoCipher {

    /**
     * IV_SIZE This is the IV size of ChaCha20.
     */
    public static final int IV_SIZE = 16;
    /**
     * KEY_SIZE This is the key size of ChaCha20.
     */
    public static final int KEY_SIZE = 32;
    private SecretKeySpec key;
    private Cipher cipher;

    /**
     * Construct an ChaCha20 Object.
     */
    public ChaCha20() {
        try {
            this.cipher = Cipher.getInstance("ChaCha20/None/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
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
    public void setKey(final byte[] key) {
        this.key = new SecretKeySpec(key, "ChaCha20");
    }

}

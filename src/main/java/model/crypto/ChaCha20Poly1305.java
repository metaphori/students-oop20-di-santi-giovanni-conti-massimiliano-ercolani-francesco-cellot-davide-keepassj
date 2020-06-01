package model.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class ChaCha20Poly1305 implements CryptoCipher {

    private static final int IV_SIZE = 12;
    private static final int KEY_SIZE = 32;

    private Cipher cipher;
    private SecretKey chacha20poly1305key;

    public ChaCha20Poly1305() {
        initCipher();
    }
    @Override
    public final byte[] encrypt(final byte[] plaintext, final byte[] iv) {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.chacha20poly1305key, ivParameterSpec);
            return this.cipher.doFinal(plaintext);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException e) {
            System.out.println("Error ChaCha20-Poly1305 encryption: " + e.toString());
        }
        return null;
    }

    @Override
    public final byte[] decrypt(final byte[] ciphertext, final byte[] iv) {
        this.initCipher();
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.chacha20poly1305key, ivParameterSpec);
            return this.cipher.doFinal(ciphertext);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException e) {
            System.out.println("Error ChaCha20-Poly1305 decryption: " + e.toString());
        }
        return null;
    }

    @Override
    public final void setKey(final byte[] key) {
        this.chacha20poly1305key = new SecretKeySpec(key, "ChaCha20");
    }

    @Override
    public final int getIVSize() {
        return ChaCha20Poly1305.IV_SIZE;
    }

    @Override
    public final int getKeySize() {
        return ChaCha20Poly1305.KEY_SIZE;
    }

    private void initCipher() {
        try {
            this.cipher = Cipher.getInstance("ChaCha20-Poly1305/None/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Erorr building ChaCha20-Poly1305 object: " + e.toString());
        }
    }
    @Override
    public void updateAssociatedData(byte[] data) {
        // TODO Auto-generated method stub
        
    }
}

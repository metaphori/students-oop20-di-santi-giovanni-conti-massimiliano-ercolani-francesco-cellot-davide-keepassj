package model.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESGCM implements CryptoCipher {

    private static final int IV_SIZE = 12;
    // not an error, in reality the iv size is the nonce.
    private static final int IV_SIZE_BIT = 128;
    private static final int KEY_SIZE = 32;

    private Cipher cipher;
    private SecretKeySpec aesKey;

    public AESGCM() {
        try {
            this.cipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Error building AES-GCM object: " + e.toString());
        }
    }

    @Override
    public final byte[] encrypt(final byte[] plaintext, final byte[] iv) {
        GCMParameterSpec ivParameterSpec = new GCMParameterSpec(IV_SIZE_BIT, iv);
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, this.aesKey, ivParameterSpec);
            return this.cipher.doFinal(plaintext);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException e) {
            System.out.println("Error AES-GCM encryption: " + e.toString());
        }
        return null;
    }

    @Override
    public final byte[] decrypt(final byte[] ciphertext, final byte[] iv) {
        GCMParameterSpec ivParameterSpec = new GCMParameterSpec(IV_SIZE_BIT, iv);
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, this.aesKey, ivParameterSpec);
            return this.cipher.doFinal(ciphertext);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException e) {
            System.out.println("Error AES-GCM decryption: " + e.toString());
        }
        return null;
    }

    @Override
    public void setKey(final byte[] key) {
        this.aesKey = new SecretKeySpec(key, "AES");
    }

    @Override
    public int getIVSize() {
        return AESGCM.IV_SIZE;
    }

    @Override
    public int getKeySize() {
        return AESGCM.KEY_SIZE;
    }

    @Override
    public void updateAssociatedData(byte[] data) {
        // TODO Auto-generated method stub
        
    }

}

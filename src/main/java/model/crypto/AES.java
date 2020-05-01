package model.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES implements CryptoCipher {

    private static final int BLOCKSIZE = 16;
    private byte[] key;
    private byte[] iv;
    private int ivSize;
    private Cipher aes;
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpec;
    /**
     * Construct an AES Object.
     * @param key
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     */
    public AES(final byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.key = key;
        this.ivSize = BLOCKSIZE;
        this.aes = Cipher.getInstance("AES/CBC/NoPadding");
        this.iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(this.iv);
        this.ivParameterSpec = new IvParameterSpec(iv);
        this.secretKeySpec = new SecretKeySpec(this.key, "AES");
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
    public final byte[] encrypt(final String plaintext) {
        try {
            this.aes.init(Cipher.ENCRYPT_MODE, secretKeySpec, this.ivParameterSpec);
            byte[] encrypted = this.aes.doFinal(plaintext.getBytes());
            byte[] ciphertext = new byte[ivSize + encrypted.length];
            System.arraycopy(this.iv, 0, ciphertext, 0, this.ivSize);
            System.arraycopy(encrypted, 0, ciphertext, this.ivSize, encrypted.length);
            return ciphertext;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    @Override
    public final byte[] decrypt(final byte[] ciphertext) {
        try {
            System.arraycopy(ciphertext, 0, this.iv, 0, this.iv.length);
            int encryptedSize = ciphertext.length - this.ivSize;
            byte[] encrypted = new byte[encryptedSize];
            System.arraycopy(ciphertext, this.ivSize, encrypted, 0, encryptedSize);
            this.ivParameterSpec = new IvParameterSpec(this.iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.key, "AES");
            this.aes.init(Cipher.DECRYPT_MODE, secretKeySpec, this.ivParameterSpec);
            return this.aes.doFinal(encrypted);
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}

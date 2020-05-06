package model.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES implements CryptoCipher {

    /**
     * BLOCKSIZE This is the Block Size of AES.
     */
    public static final int BLOCKSIZE = 16;

    private byte[] key;
    private byte[] iv;
    private final Cipher aes;
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpec;
    private final SecureRandom random;
    /**
     * Construct an AES Object.
     * @param key
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     */
    public AES(final byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.key = key;
        this.aes = Cipher.getInstance("AES/CBC/NoPadding");
        this.iv = new byte[AES.BLOCKSIZE];
        this.random = new SecureRandom();
        this.random.nextBytes(this.iv);
        initialize(key, iv);
        /*
        this.ivParameterSpec = new IvParameterSpec(this.iv);
        this.secretKeySpec = new SecretKeySpec(this.key, "AES");
        */
    }

    private void initialize(final byte[] key, final byte[] iv) {
        this.key = key;
        this.iv = iv;
        this.ivParameterSpec = new IvParameterSpec(this.iv);
        this.secretKeySpec = new SecretKeySpec(this.key, "AES");
    }

    private void initialize(final byte[] key) {
        this.random.nextBytes(this.iv);
        initialize(key, iv);
    }
    /**
     * Return the AES key.
     * @return key This is an AES key.
     */
    public final byte[] getKey() {
        return key;
    }

    /**
     * Set AES key.
     * @param key 16/24/32 bytes key.
     */
    public void setKey(final byte[] key) {
        initialize(key);
    }

    @Override
    public final byte[] encrypt(final byte[] plaintext) {
        try {
            this.aes.init(Cipher.ENCRYPT_MODE, secretKeySpec, this.ivParameterSpec);
            byte[] plaintextPadded = Util.pad(plaintext, AES.BLOCKSIZE);
            byte[] encrypted = this.aes.doFinal(plaintextPadded);
            byte[] ciphertext = new byte[AES.BLOCKSIZE + encrypted.length];
            System.arraycopy(this.iv, 0, ciphertext, 0, AES.BLOCKSIZE);
            System.arraycopy(encrypted, 0, ciphertext, AES.BLOCKSIZE, encrypted.length);
            return ciphertext;
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    @Override
    public final byte[] decrypt(final byte[] ciphertext) {
        try {
            System.arraycopy(ciphertext, 0, this.iv, 0, AES.BLOCKSIZE);
            final int encryptedSize = ciphertext.length - AES.BLOCKSIZE;
            final byte[] encrypted = new byte[encryptedSize];
            System.arraycopy(ciphertext, AES.BLOCKSIZE, encrypted, 0, encryptedSize);
            this.ivParameterSpec = new IvParameterSpec(this.iv);
            this.aes.init(Cipher.DECRYPT_MODE, this.secretKeySpec, this.ivParameterSpec);
            return Util.unpad(this.aes.doFinal(encrypted));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }

}

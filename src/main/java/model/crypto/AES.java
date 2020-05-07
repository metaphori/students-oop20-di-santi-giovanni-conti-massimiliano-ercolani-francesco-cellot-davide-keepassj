package model.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES implements CryptoCipher {

    /**
     * BLOCKSIZE This is the Block Size of AES.
     */
    public static final int BLOCKSIZE = 16;

    private Cipher cipher;
    private SecureRandom random;
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec aesKey;

    /**
     * Construct an AES Object.
     * @param key This is 16/24/32 bytes key
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     */
    public AES(final byte[] key) {
        this.random = new SecureRandom();
        try {
            this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
            this.aesKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Error building AES object: " + e.toString());
        }
    }

    /**
     * Construct an AES Object.
     * @param key This is a 16/24/32 bytes key.
     * @param iv This is the IV to use for the encryption. If null than a random one is generated.
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
    public AES(final byte[] key, final byte[] iv) {
        this.cipher = null;
        this.random = new SecureRandom();
        this.iv = new byte[AES.BLOCKSIZE];
        try {
            this.random = new SecureRandom();
            this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
            if (iv == null) {
                this.random.nextBytes(this.iv);
            } else {
                System.arraycopy(iv, 0, this.iv, 0, iv.length);
            }
            this.ivParameterSpec = new IvParameterSpec(this.iv);
            this.aesKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Error building AES object: " + e.toString());
        }
    }
    */

    /**
     * Return the AES key.
     * @return key.
     */
    public final SecretKeySpec getKey() {
        return this.aesKey;
    }

    /**
     * Set AES key.
     * @param key 16/24/32 bytes key.
     */
    public void setKey(final byte[] key) {
        this.aesKey = new SecretKeySpec(key, "AES");
    }

    /**
     * Encrypt arbitrary with AES CBC plaintext.
     * @param plaintext This is the plaintext to encrypt.
     * @param iv This is the iv used in AES CBC to encrypt correctly the plaintext.
     * @return ciphertext.
     */
    @Override
    public final byte[] encrypt(final byte[] plaintext, final byte[] iv) {
        try {
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            this.cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
            return this.cipher.doFinal(Util.pad(plaintext, AES.BLOCKSIZE));
        } catch (InvalidKeyException | BadPaddingException  | IllegalBlockSizeException 
                | InvalidAlgorithmParameterException e) {
            System.out.println("Error AES encryption: " + e.toString());
        }
        return null;
    }

    /**
     * @param ciphertext This is the ciphertext to decrypt with AES CBC.
     * @param iv This is the iv used in AES CBC to decrypt correctly the ciphertext.
     * @return plaintext.
     */
    @Override
    public final byte[] decrypt(final byte[] ciphertext, final byte[] iv) {
        try {
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            this.cipher.init(Cipher.DECRYPT_MODE, this.aesKey, ivParameterSpec);
            return Util.unpad(this.cipher.doFinal(ciphertext));
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException
                | InvalidAlgorithmParameterException e) {
            System.out.println("Error AES decryption: " + e.toString());
        }
        return null;
    }

}

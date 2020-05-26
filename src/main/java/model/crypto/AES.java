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
     */
    public AES() {
        this.random = new SecureRandom();
        try {
            this.cipher = Cipher.getInstance("AES/CBC/NoPadding");
            // this.aesKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println("Error building AES object: " + e.toString());
        }
    }

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
     * @param iv This is the IV used in AES CBC to encrypt correctly the plaintext.
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
     * NOT READY TO USE, MUST EDIT ALSO THE INTERFACE
     * AES CBC Encrypt arbitrary plaintext. This method create a random IV and prepend it to the ciphertext.
     * @param plaintext This is the plaintext to encrypt.
     * @return ciphertext.
    public final byte[] encrypt(final byte[] plaintext) {
        final byte[] iv = new byte[AES.BLOCKSIZE];
        this.random.nextBytes(iv);
        byte[] encrypted = this.encrypt(plaintext, iv);
        byte[] ciphertext = new byte[AES.BLOCKSIZE + encrypted.length];
        System.arraycopy(iv, 0, ciphertext, 0, AES.BLOCKSIZE);
        System.arraycopy(encrypted, 0, ciphertext, AES.BLOCKSIZE, encrypted.length);
        return ciphertext;
    }
    */

    /**
     * AES CBC Decrypt arbitrary ciphertext.
     * @param ciphertext This is the ciphertext to decrypt with AES CBC.
     * @param iv This is the IV used in AES CBC to decrypt correctly the ciphertext.
     * @return plaintext.
     */
    @Override
    public final byte[] decrypt(final byte[] ciphertext, final byte[] iv) {
        try {
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            this.cipher.init(Cipher.DECRYPT_MODE, this.aesKey, ivParameterSpec);
            return Util.unpad(this.cipher.doFinal(ciphertext));
            // return this.cipher.doFinal(ciphertext);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException
                | InvalidAlgorithmParameterException e) {
            System.out.println("Error AES decryption: " + e.toString());
        }
        return null;
    }

    /**
     * NOT READY TO USE, MUST EDIT ALSO THE INTERFACE
     * AES CBC Decrypt arbitrary ciphertext. The first AES.BLOCKSIZE bytes are used as the IV
     * @param ciphertext This is the ciphertext to decrypt.
     * @return plaintext.
    public final byte[] decrypt(final byte[] ciphertext) {
        final byte[] iv = new byte[AES.BLOCKSIZE];
        final byte[] encrypted = new byte[ciphertext.length - AES.BLOCKSIZE];
        System.arraycopy(ciphertext, 0, iv, 0, AES.BLOCKSIZE);
        System.arraycopy(ciphertext, AES.BLOCKSIZE, encrypted, 0, encrypted.length);
        return this.decrypt(encrypted, iv);
    }
    */

}

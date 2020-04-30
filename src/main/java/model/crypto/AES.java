package model.crypto;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AES implements CryptoCipher {

    private byte[] key;
    private byte[] iv;
    private int ivSize;
    private Cipher aes;
    /**
     * Construct an AES Object.
     * @param key
     * @throws NoSuchPaddingException 
     * @throws NoSuchAlgorithmException 
     */
    public AES(final byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.key = key;
        this.ivSize = 16;
        this.aes = Cipher.getInstance("AES/CBC/PKCS7Padding");
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
            this.iv = new byte[ivSize];
            SecureRandom random = new SecureRandom();
            random.nextBytes(this.iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.key, "AES");
            this.aes.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return this.aes.doFinal(plaintext.getBytes());
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final byte[] decrypt(final byte[] ciphertext) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.key, "AES");
            this.aes.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return this.aes.doFinal(ciphertext);
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        // TODO Auto-generated method stub
        return null;
    }

}
